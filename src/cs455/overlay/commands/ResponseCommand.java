package cs455.overlay.commands;


import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class ResponseCommand {
   
   public String messageType;
   public String status;
   public String aditionalInfo;

   public ResponseCommand() {
   }

   public ResponseCommand(String messageType, String status, String aditionalInfo) {
      this.messageType = messageType;
      this.status = status;
      this.aditionalInfo = aditionalInfo;
   }

   public byte[] unpack() {
      byte[] marshalledBytes = null;
      ByteArrayOutputStream baOutputStream = null;
      DataOutputStream dout = null;

      try {
         baOutputStream = new ByteArrayOutputStream();
         dout = new DataOutputStream(new BufferedOutputStream(baOutputStream));
         dout.writeInt(messageType.length());
         dout.write(messageType.getBytes());
         dout.writeInt(status.length());
         dout.write(status.getBytes());
         dout.writeInt(aditionalInfo.length());
         dout.write(aditionalInfo.getBytes());
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
         byte[] m = new byte[din.readInt()];
         din.readFully(m);
         this.status = new String(m);
         
         m = new byte[din.readInt()];
         din.readFully(m);
         this.aditionalInfo = new String(m);
      } catch (IOException e) {
         e.printStackTrace();
      }  
   }

   @Override
   public String toString() {
      return "ResponseCommand [messageType=" + messageType + ", status=" + status + ", aditionalInfo="
            + aditionalInfo + "]";
   }
   
}
