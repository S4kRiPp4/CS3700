/*  
 * Alyssa Williams & Jesse Johnstone 
 * HW2 2023
*/

package HW2;

import java.io.*;
import java.net.*;
import java.util.*;

//TODO: Load Inventory CSV
//TODO: Wait to receive packet from client
//TODO: Once a packet is received from a Client, retrieve the information relevant to the requested Item ID
//TODO: from the data structure you used in Step 1 and send back such information to the Client
//TODO: Repeat waiting for packet from Client infinitely until exception is caught 
//TODO: Close datagram socket when exception is caught

public class InventoryServer {
     public static void main(String[] args) throws IOException {
         
        DatagramSocket udpServerSocket = new DatagramSocket(5678);
        BufferedReader in = null;
        DatagramPacket udpPacket = null, udpPacket2 = null;
        String fromClient = null, toClient = null;
        boolean morePackets = true;

        byte[] buf = new byte[256];
        
        while (morePackets) {
            try {

                // receive UDP packet from client
                udpPacket = new DatagramPacket(buf, buf.length);
                udpServerSocket.receive(udpPacket);

                fromClient = new String(
                udpPacket.getData(), 0, udpPacket.getLength(), "UTF-8");
                                                        
                // get the response
                toClient = fromClient.toUpperCase();
                                         
                // send the response to the client at "address" and "port"
                InetAddress address = udpPacket.getAddress();
                int port = udpPacket.getPort();
                byte[] buf2 = toClient.getBytes("UTF-8");
                udpPacket2 = new DatagramPacket(buf2, buf2.length, address, port);
                udpServerSocket.send(udpPacket2);

            } catch (IOException e) {
                e.printStackTrace();
                morePackets = false;
            }
        }
  
        udpServerSocket.close();

    }
}

    

