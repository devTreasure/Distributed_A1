package cs455.overlay.commands;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MessaginNodesListCommand {

	public final String cmd = "MESSAGING_NODES_LIST";
	public List<Node> nodes = new ArrayList<Node>();

	public MessaginNodesListCommand() {}
	public MessaginNodesListCommand(List<Node> nodes) {
		this.nodes = nodes;
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
			dout.writeInt(nodes.size());
			for (Node node : nodes) {
				dout.writeInt(node.ipAddress.length());
				dout.write(node.ipAddress.getBytes());
				dout.writeInt(node.port);
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
		try {
			nodes = new ArrayList<>();
			int numberOfNodes = din.readInt();
			for (int i = 0; i < numberOfNodes; i++) {
				Node n = new Node();
				int ipLength = 0;
				ipLength = din.readInt();
				byte[] ipAddress = new byte[ipLength];
				din.readFully(ipAddress);
				n.ipAddress = new String(ipAddress);
				n.port = din.readInt();
				nodes.add(n);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public String toString() {
		return "MessaginNodesListCommand [cmd=" + cmd + ", nodes=" + nodes + "]";
	}

}