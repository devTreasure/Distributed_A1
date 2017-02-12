package cs455.overlay.commands;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class RegisterNeighbourCommand {

	public final String cmd = "REGISTER_NEIGHBOUR_REQUEST";
	public String fromIpAddress;
	public int fromort;
	public boolean writingBack = false;

	public RegisterNeighbourCommand() {
	}

	public RegisterNeighbourCommand(String fromIpAddress, int fromPort, boolean writingBack) {
		this.fromIpAddress = fromIpAddress;
		this.fromort = fromPort;
		this.writingBack = writingBack;
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
			dout.writeInt(fromIpAddress.length());
			dout.write(fromIpAddress.getBytes());
			dout.writeInt(fromort);
			dout.writeBoolean(writingBack);
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
			int IP_length = 0;
			IP_length = din.readInt();
			byte[] IP_address = new byte[IP_length];
			din.readFully(IP_address);
			fromIpAddress = new String(IP_address);
			fromort = din.readInt();
			writingBack = din.readBoolean();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public String toString() {
		return "RegisterNeighbourCommand [cmd=" + cmd + ", fromIpAddress=" + fromIpAddress + ", fromort=" + fromort
		        + ", writingBack=" + writingBack + "]";
	}

}
