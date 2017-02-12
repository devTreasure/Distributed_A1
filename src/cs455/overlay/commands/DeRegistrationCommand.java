package cs455.overlay.commands;


import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class DeRegistrationCommand {

   public final String cmd = "DEREGISTER_REQUEST";
   public String ipAddress;
   public int port;

   public DeRegistrationCommand() {}

   public DeRegistrationCommand(String ipAddress, int port) {
      this.ipAddress = ipAddress;
      this.port = port;
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
         dout.writeInt(port);
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
         ipAddress = new String(IP_address);
         port = din.readInt();
      } catch (IOException e) {
         e.printStackTrace();
      }
   }

   @Override
   public String toString() {
      return "DeRegistrationCommand [cmd=" + cmd + ", ipAddress=" + ipAddress + ", port=" + port
            + "]";
   }

}
