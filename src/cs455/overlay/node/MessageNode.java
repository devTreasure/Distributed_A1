package cs455.overlay.node;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import cs455.overlay.commands.DeRegistrationCommand;
import cs455.overlay.commands.Link;
import cs455.overlay.commands.LinkWeightsCommand;
import cs455.overlay.commands.MessageCommand;
import cs455.overlay.commands.MessaginNodesListCommand;
import cs455.overlay.commands.Node;
import cs455.overlay.commands.RegisterNeighbourCommand;
import cs455.overlay.commands.RegistrationCommand;
import cs455.overlay.commands.ResponseCommand;
import cs455.overlay.commands.TaskCompleteCommand;
import cs455.overlay.commands.TaskInitiateCommand;
import cs455.overlay.commands.TrafficSummaryCommand;

public class MessageNode implements Runnable {

	public static boolean continueOperations = true;
	private ServerSocket serverSocket = null;

	private String messageNodeName;
	private int messageNodePort;
	private String messageNodeIP;

	private int registryNodePort = 0;
	private String registryIP;

	private boolean taskInitiate = false;

	// Node, Weight
	private HashMap<Node, Integer> neighBours = new HashMap<Node, Integer>();

	// All Links in given Overlay
	private HashSet<Link> allLinks = new HashSet<Link>();

	// All nodes in this over-lay. Retrieved from allLinks.
	private HashSet<Node> allNodes = new HashSet<Node>();

	private Random random = new Random();

	// Statistics
	private int stat_sentMesssages;
	private int stat_receivedMessages;
	private int stat_relayedMessages;
	private double stat_sumOfSentPayload;
	private double stat_sumOfReceivedPayLoad;

	public MessageNode() {
	}

	public static void main(String[] args) throws Exception {

		if (args.length < 1) {
			System.out.println("Error: Please pass registry host and port number");
			System.exit(0);
		}
		String hostName = "";
		int port = 0;
		try {
			hostName = args[0];
			port = Integer.parseInt(args[1]);
		} catch (Exception e) {
			System.out.println("Error: Please provide the valid argument.");
			System.exit(0);
		}

		ServerSocket sc = new ServerSocket(0);

		InetAddress ip = InetAddress.getLocalHost();

		MessageNode messageNode = new MessageNode();
		messageNode.serverSocket = sc;
		messageNode.messageNodeIP = (ip.getHostAddress()).trim();
		messageNode.messageNodePort = sc.getLocalPort();
		messageNode.intializeMessgingNode(hostName, port);

		messageNode.createsSocketAndSendRegistrationRequest();
		Thread t = new Thread(messageNode);
		t.start();

		while (continueOperations) {
			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
			String inputCmd = br.readLine();
			System.out.println("Received command is:" + inputCmd);

			if ("register".equalsIgnoreCase(inputCmd)) {
				messageNode.createsSocketAndSendRegistrationRequest();
			} else if ("deregister".equalsIgnoreCase(inputCmd)) {
				messageNode.deregisterMessaginNode();
			} else if ("exit".equalsIgnoreCase(inputCmd)) {
				System.out.println("Exiting.");
				continueOperations = false;
			} else if ("print-stats".equals(inputCmd)) {
				messageNode.printStatistics();
			}

			else {
				System.out.println("Invalid command.");
			}
		}
		System.out.println("Bye.");
	}

	private void printStatistics() {
		System.out.println("Sent Messages:" + stat_sentMesssages);
		System.out.println("Received Messages:" + stat_receivedMessages);
		System.out.println("Related Messages:" + stat_relayedMessages);
		System.out.println("Sum of SENT PayLoad:" + stat_sumOfSentPayload);
		System.out.println("Sum of RECEIVED PayLoad:" + this.stat_sumOfReceivedPayLoad);
		System.out.println("");
	}

	public void deregisterMessaginNode() throws Exception {
		System.out.println(">>> Deregistering...");
		System.out.println(this.registryNodePort + ":" + this.registryIP);
		Socket sc = null;
		try {
			sc = new Socket(registryIP, registryNodePort);
			DeRegistrationCommand cmd = new DeRegistrationCommand(this.messageNodeIP, this.messageNodePort); // new
																												// DeRegistrationCommand("127.0.0.1",
																												// this.messageNodePort);
			TCPSender tcpSender = new TCPSender();
			tcpSender.sendData(sc, cmd.unpack());
		} catch (SocketException ex) {
			System.out.println(ex.getMessage());
		} finally {
			sc.close();
		}
	}

	private void intializeMessgingNode(String IP_name, int port) throws IOException {
		/*
		 * BufferedReader br = new BufferedReader(new
		 * InputStreamReader(System.in));
		 * System.out.println("Enter Registry port : "); String input =
		 * br.readLine(); System.out.println("Enter registry IP  : "); String IP
		 * = br.readLine();
		 */

		this.registryIP = IP_name;
		this.registryNodePort = port;
	}

	public void createsSocketAndSendRegistrationRequest() throws Exception {

		System.out.println(">>> Registering with registry:");
		System.out.println(this.registryNodePort + ":" + this.registryIP);
		Socket sc = null;
		try {
			sc = new Socket(this.registryIP, this.registryNodePort);
			RegistrationCommand cmd = new RegistrationCommand(this.messageNodeIP, this.messageNodePort);
			TCPSender tcpSender = new TCPSender();
			tcpSender.sendData(sc, cmd.unpack());
		} catch (SocketException ex) {
			System.out.println(ex.getMessage());
		} finally {
			sc.close();
		}
	}

	public int getMessageNodePort() {
		return messageNodePort;
	}

	public void setMessageNodePort(int messageNodePort) {
		this.messageNodePort = messageNodePort;

	}

	public void connetWithTheNeighbours(List<Node> nodes) throws Exception {
		if (nodes == null || nodes.size() == 0) {
			System.out.println("No neighbours found.");
		}
		for (Node eachNode : nodes) {
			connectWithNeighBour(eachNode);
		}
	}

	public void connectWithNeighBour(Node toNode) throws UnknownHostException, IOException, Exception {
		Socket sc = null;
		try {
			System.out.println("Connecting with: " + toNode);
			sc = new Socket(toNode.ipAddress, toNode.port);
			RegisterNeighbourCommand cmd = new RegisterNeighbourCommand(this.messageNodeIP, this.messageNodePort,
					false);
			byte[] data = cmd.unpack();
			new TCPSender().sendData(sc, data);
		} catch (SocketException ex) {
			System.out.println(ex.getMessage());
		} finally {
			sc.close();
		}
	}

	@Override
	public void run() {

		System.out.println("Messaging node  is listenning...at port : " + serverSocket.getLocalPort());

		while (continueOperations) {
			DataInputStream din = null;
			Socket socket = null;
			try {
				socket = serverSocket.accept(); // THis is where connection is
												// happening
				din = new DataInputStream(socket.getInputStream());
				int request_Typelength = 0;
				request_Typelength = din.readInt();
				byte[] request_Type = new byte[request_Typelength];
				din.readFully(request_Type);
				String str_request_type = new String(request_Type);
				System.out.println(str_request_type);

				if ("DEREGISTER_RESPONSE".equals(str_request_type) || "REGISTER_RESPONSE".equals(str_request_type)) {
					ResponseCommand cmd = new ResponseCommand();
					cmd.messageType = str_request_type;
					cmd.pack(din);
					System.out.println(cmd);
				} else if ("MESSAGING_NODES_LIST".equals(str_request_type)) {
					MessaginNodesListCommand cmd = new MessaginNodesListCommand();
					cmd.pack(din);
					System.out.println(cmd);
					connetWithTheNeighbours(cmd.nodes);
				} else if ("REGISTER_NEIGHBOUR_REQUEST".equals(str_request_type)) {
					RegisterNeighbourCommand cmd = new RegisterNeighbourCommand();
					cmd.pack(din);
					Node fromNode = new Node(cmd.fromIpAddress, null, cmd.fromort);
					if (!neighBours.keySet().contains(fromNode)) {
						neighBours.put(fromNode, 0);
					} else {
						System.out.println("Node already registerd.");
					}

					if (cmd.writingBack) {
						System.out.println("Register Back ....");
						connectWithNeighBour(fromNode);
					}
					System.out.println("my neighbours:" + neighBours.keySet().size());
					System.out.println(neighBours.keySet());
				} else if ("LINK_WEIGHTS".equals(str_request_type)) {
					LinkWeightsCommand cmd = new LinkWeightsCommand();
					cmd.pack(din);
					System.out.println(cmd);
					allLinks = cmd.links;
					allNodes = allNodes(allLinks);
					Node mysself = new Node(messageNodeIP, null, messageNodePort);

					for (Link link : allLinks) {
						if (link.from.equals(mysself)) {
							if (neighBours.containsKey(link.to)) {
								neighBours.put(link.to, link.weight);
							} else {
								System.out.println("??????? Should not happen.");
							}
						}
					}
					System.out.println(neighBours);
				} else if ("TASK_INITIATE".equals(str_request_type)) {
					taskInitiate = true;
					TaskInitiateCommand cmd = new TaskInitiateCommand();
					cmd.pack(din);
					System.out.println(cmd);
					initiateMessages(cmd.rounds);
				} else if ("MESSAGE_FOR_SINK_NODE".equals(str_request_type)) {
					MessageCommand cmd = new MessageCommand();
					cmd.pack(din);
					System.out.println("Received Message:" + cmd);
					if (cmd.sincNodePort == messageNodePort) {
						stat_receivedMessages++;
						stat_sumOfReceivedPayLoad += cmd.payload;
					} else {
						// Message is not for me relay it
						Node firstNeighbour = neighBours.keySet().iterator().next();
						stat_relayedMessages++;
						cmd.toIpAddress = firstNeighbour.ipAddress;
						cmd.toPort = firstNeighbour.port;
						sendMessages(cmd.toIpAddress, cmd.toPort, cmd.unpack());
					}
				} else if ("PULL_TRAFFIC_SUMMARY".equals(str_request_type)) {

					TrafficSummaryCommand cmd = new TrafficSummaryCommand();

					cmd.ipAddress = this.messageNodeIP;// "127.0.0.1";
					cmd.fromPort = this.messageNodePort;
					cmd.numberofMessageSent = this.stat_sentMesssages;
					cmd.summationOfMessgeSent = this.stat_sumOfSentPayload;
					cmd.numberOfMessageReceived = this.stat_receivedMessages;
					cmd.summationOfMessageReceived = this.stat_sumOfReceivedPayLoad;
					cmd.messageRelayed = this.stat_relayedMessages;
					System.out.println(cmd);
					sendMessages(registryIP, registryNodePort, cmd.unpack());
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				try {
					din.close();
					socket.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	private HashSet<Node> allNodes(HashSet<Link> links) {
		HashSet<Node> all = new HashSet<>();
		for (Link link : links) {
			all.add(link.from);
			all.add(link.to);
		}
		return all;
	}

	private void initiateMessages(int rounds) throws Exception {

		Node selfNode = new Node(messageNodeIP, null, messageNodePort); // new
																		// Node("127.0.0.1",
																		// null,
																		// messageNodePort);
		HashSet<Node> copyOfMemebers = new HashSet<>(allNodes);
		copyOfMemebers.remove(selfNode);

		Iterator<Node> iterator = copyOfMemebers.iterator();

		for (int i = 0; i < rounds; i++) {
			Node sink_node = null;
			if (iterator.hasNext()) {
				sink_node = iterator.next();
			} else {
				// restart iteration.
				iterator = copyOfMemebers.iterator();
				sink_node = iterator.next();
			}
			MessageCommand cmd = null;
			if (neighBours.keySet().contains(sink_node)) {
				// direct neighbor
				cmd = new MessageCommand(sink_node.ipAddress, sink_node.port, sink_node.ipAddress, sink_node.port,
						random.nextInt());
			} else {
				// no direct connection. so send via first neighbor.
				Node firstNeighbour = neighBours.keySet().iterator().next();
				cmd = new MessageCommand(firstNeighbour.ipAddress, firstNeighbour.port, sink_node.ipAddress,
						sink_node.port, random.nextInt());
			}
			System.out.println(cmd);
			this.stat_sentMesssages++;
			this.stat_sumOfSentPayload += cmd.payload;
			sendMessages(cmd.toIpAddress, cmd.toPort, cmd.unpack());
		}

		informTheRegistryTaskCompleted(selfNode.ipAddress, messageNodePort);
	}

	public void informTheRegistryTaskCompleted(String IP, int port) throws Exception {
		// TODO Auto-generated method stub
		try {
			TaskCompleteCommand cmd = new TaskCompleteCommand(IP, port);
			TCPSender tcpSender = new TCPSender();
			Socket sc = new Socket(this.registryIP, this.registryNodePort);
			tcpSender.sendData(sc, cmd.unpack());

		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}
	}

	public void sendMessages(String ipAddress, int port, byte[] data) throws IOException {

		Socket sc = null;
		System.out.println("Sending message to message node");
		try {
			sc = new Socket(ipAddress, port);
			new TCPSender().sendData(sc, data);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			sc.close();
		}
	}
}