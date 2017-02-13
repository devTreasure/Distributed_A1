package cs455.overlay.commands;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class TrafficSummaryCommand {

	public final String cmd = "TRAFFIC_SUMMARY";
	public String ipAddress;
	public int fromPort;
	public int numberofMessageSent;
	public int summationOfMessgeSent;
	public int numberOfMessageReceived;
	public int summationOfMessageReceived;
	public int messageRelayed;

	public TrafficSummaryCommand() {
	}

	public TrafficSummaryCommand(String ipAddress, int port, int number_of_message_sent, int sum_message_sent,
			int message_recevied, int sum_message_received, int message_relayed) {

		this.ipAddress = ipAddress;
		this.fromPort = port;
		this.numberofMessageSent = number_of_message_sent;
		this.summationOfMessgeSent = sum_message_sent;
		this.numberOfMessageReceived = message_recevied;
		this.summationOfMessageReceived = sum_message_received;
		this.messageRelayed = message_relayed;

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
			dout.writeInt(ipAddress.length());
			dout.write(ipAddress.getBytes());
			dout.writeInt(fromPort);
			dout.writeInt(numberofMessageSent);
			dout.writeInt(summationOfMessgeSent);
			dout.writeInt(numberOfMessageReceived);
			dout.writeInt(summationOfMessageReceived);
			dout.writeInt(messageRelayed);
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
			int message_length = 0;
			message_length = din.readInt();
			byte[] IP_address = new byte[message_length];
			din.readFully(IP_address);
			ipAddress = new String(IP_address);
			fromPort = din.readInt();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public String toString() {
		return "RegistrationCommand [cmd=" + cmd + ", ipAddress=" + ipAddress + ", fromPort=" + fromPort + "]";
	}

}
