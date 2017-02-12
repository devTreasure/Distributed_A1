package cs455.overlay.commands;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class MessageCommand {
	public final String cmd = "MESSAGE_FOR_SINK_NODE";

	public String toIpAddress;
	public int toPort;
	public String sincNodeIpAddress;
	public int sincNodePort;
	
	public int payload;

	public MessageCommand() {
	}

	public MessageCommand(String toIpAddress, int toPort, String sincNodeIpAddress, int sincNodePort, int payload) {
		super();
		this.toIpAddress = toIpAddress;
		this.toPort = toPort;
		this.sincNodeIpAddress = sincNodeIpAddress;
		this.sincNodePort = sincNodePort;
		this.payload = payload;
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
			dout.writeInt(toIpAddress.length());
			dout.write(toIpAddress.getBytes());
			dout.writeInt(toPort);
			dout.writeInt(sincNodeIpAddress.length());
			dout.write(sincNodeIpAddress.getBytes());
			dout.writeInt(sincNodePort);
			dout.writeInt(payload);
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
			toIpAddress = readString(din);
			toPort = din.readInt();
			sincNodeIpAddress = readString(din);
			sincNodePort = din.readInt();
			payload = din.readInt();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private String readString(DataInputStream din) throws IOException {
		int IP_length = din.readInt();
		byte[] IP_address = new byte[IP_length];
		din.readFully(IP_address);
		return new String(IP_address);
	}

	@Override
	public String toString() {
		return "MessageCommand [cmd=" + cmd + ", toIpAddress=" + toIpAddress + ", toPort=" + toPort
		        + ", sincNodeIpAddress=" + sincNodeIpAddress + ", sincNodePort=" + sincNodePort + ", payload=" + payload
		        + "]";
	}
	
}