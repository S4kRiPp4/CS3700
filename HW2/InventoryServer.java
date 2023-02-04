/*  
 * Alyssa Williams & Jesse Johnstone 
 * HW2 2023
*/

package HW2;

import java.io.*;
import java.net.*;
import java.util.*;


//TODO: Wait to receive packet from client
//TODO: Once a packet is received from a Client, retrieve the information relevant to the requested Item ID
//TODO: from the data structure you used in Step 1 and send back such information to the Client
//TODO: Repeat waiting for packet from Client infinitely until exception is caught 
//TODO: Close datagram socket when exception is caught

public class InventoryServer {
     public static void main(String[] args) throws IOException {
         
        DatagramSocket udpServerSocket = new DatagramSocket(5140); //5140 Jesse, 5310 Alyssa
        DatagramPacket udpPacket = null, udpPacket2 = null;
        
        String fromClient = null, toClient = null;
        boolean morePackets = true;
        
        String fileName = "C:\\Users\\jesse\\Desktop\\Network2023\\Repo\\CS3700\\HW2\\HW02Inventory.csv";
        File file = new File(fileName);
        FileReader fileReader = new FileReader(file);
        BufferedReader lineReader = new BufferedReader(fileReader);
        String line = "";

        while ((line = lineReader.readLine()) != null) {
            
            String row[] = line.split("\n");
            String[][] inventoryTable = new String[7][4];
                   
            for (int i = 0; i < row.length; i++) {

                

                for (int j = 0; j < 4; j++) {

                    inventoryTable[i][j] = row[i].split(",");
                    System.out.println(inventoryTable[i][j]);
                   

                }
                
                
                //String[][] inventoryTable = row[i].split(",");
                

            }
            
        }
        lineReader.close();
        


    //     byte[] buf = new byte[256];
        
    //     while (morePackets) {
    //         try {

    //             // receive UDP packet from client
    //             udpPacket = new DatagramPacket(buf, buf.length);
    //             udpServerSocket.receive(udpPacket);

    //             fromClient = new String(
    //             udpPacket.getData(), 0, udpPacket.getLength(), "UTF-8");
                                                        
    //             // get the response
    //             toClient = fromClient.toUpperCase();
                                         
    //             // send the response to the client at "address" and "port"
    //             InetAddress address = udpPacket.getAddress();
    //             int port = udpPacket.getPort();
    //             byte[] buf2 = toClient.getBytes("UTF-8");
    //             udpPacket2 = new DatagramPacket(buf2, buf2.length, address, port);
    //             udpServerSocket.send(udpPacket2);

    //         } catch (IOException e) {
    //             e.printStackTrace();
    //             morePackets = false;
    //         }
    //     }
  
    //     udpServerSocket.close();

     }
}

    

