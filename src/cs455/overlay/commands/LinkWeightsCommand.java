package cs455.overlay.commands;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.HashSet;

public class LinkWeightsCommand {
	
	public final String cmd ="LINK_WEIGHTS";
	public HashSet<Link> links;

	public LinkWeightsCommand() {
		links = new HashSet<Link>();
	}
	public LinkWeightsCommand(HashSet<Link> links) {
		this.links = links;
		if(links == null) {
			links = new HashSet<Link>();
		}
	}

	public byte[] unpack() {

		byte[] marshalledBytes = null;
		ByteArrayOutputStream baOutputStream = null;
		DataOutputStream dout = null;

		try {
			baOutputStream = new ByteArrayOutputStream();
			dout = new DataOutputStream(new BufferedOutputStream(baOutputStream));
			dout.writeInt(cmd.length());
			dout.write(cmd.getBytes());
			dout.writeInt(links.size());			
			for (Link link : links) {
				dout.writeInt(link.from.ipAddress.length());
				dout.write(link.from.ipAddress.getBytes());
				dout.writeInt(link.from.port);
				dout.writeInt(link.to.ipAddress.length());
				dout.write(link.to.ipAddress.getBytes());
				dout.writeInt(link.to.port);
				dout.writeInt(link.weight);
			}
			dout.flush();
			marshalledBytes = baOutputStream.toByteArray();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				baOutputStream.close();
				dout.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return marshalledBytes;
	}

	public void pack(DataInputStream din) {
		links = new HashSet<>();
		try {
			int numberOfLinks = din.readInt();
			for (int i = 0; i < numberOfLinks; i++) {
		        Node from = readNode(din);
		        Node to = readNode(din);
		        int weight = din.readInt();
		        links.add(new Link(from, to, weight));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private Node readNode(DataInputStream din) throws IOException {
		int IP_length = 0;
		 IP_length = din.readInt();
		 byte[] IP_address = new byte[IP_length];
		 din.readFully(IP_address);
		 String ipAddress = new String(IP_address);
		 int port = din.readInt();
		 return new Node(ipAddress,null, port);
	}
	@Override
	public String toString() {
		return "LinkWeightsCommand [cmd=" + cmd + ", links=" + links + "]";
	}
}
