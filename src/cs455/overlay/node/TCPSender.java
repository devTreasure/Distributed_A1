package cs455.overlay.node;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.net.Socket;

public class TCPSender {

   public void sendData(Socket socket, byte[] data) throws Exception {
      BufferedOutputStream dout = null;
      try {
         dout = new BufferedOutputStream(socket.getOutputStream());
         dout.write(data);
         dout.close();
      } catch (IOException e) {
         e.printStackTrace();
      } finally {
         dout.close();
      }
   }
   
}
