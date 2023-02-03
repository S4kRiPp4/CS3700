/*  
 * Alyssa Williams & Jesse Johnstone 
 * HW2 2023
*/

package HW2;

import java.io.*;
import java.net.*;
import java.util.*;

//TODO: Display message for user input for DNS of server(read user input as a string, save input as variable)
//TODO: Display table from Server using CSV(display ID and item description)
//TODO: Display message and ask user to input ID with item validation(ask user to re-type if not valid ID, save ID as variable)
//TODO: Send request message to server with item ID and DNS of server(destination address), for quote
//TODO: Record local time(timestamp) prior to sending request
//TODO: Record timestamp of received message
//TODO: Compute RTT of Query (timestamp of received request - timestamp of pending request, milliseconds)
//TODO: Once request is received display item information from Server(Item ID, Item Description, Unit Price, Inventory, RTT of Query)
//TODO: Ask user to continue for new inventory query, if yes repeat process else close socket and terminate client program

    

public class InventoryClient {
    public static void main(String[] args) throws IOException {

        if (args.length != 1) {
            System.out.println("Usage: java UDPClient <hostname>");
            return;
        }

        // create a UDP socket
        DatagramSocket udpSocket = new DatagramSocket();

        BufferedReader sysIn = new BufferedReader(new InputStreamReader(System.in));
        String fromServer;
        String fromUser;

        while ((fromUser = sysIn.readLine()) != null) {

            // display user input
            System.out.println("From Client: " + fromUser);

            // send request
            InetAddress address = InetAddress.getByName(args[0]);
            byte[] buf = fromUser.getBytes();
            DatagramPacket udpPacket = new DatagramPacket(buf, buf.length, address, 5678);
            udpSocket.send(udpPacket);

            // get response
            byte[] buf2 = new byte[256];
            DatagramPacket udpPacket2 = new DatagramPacket(buf2, buf2.length);
            udpSocket.receive(udpPacket2);

            // display response
            fromServer = new String(udpPacket2.getData(), 0, udpPacket2.getLength());
            System.out.println("From Server: " + fromServer);

            if (fromUser.equals("Bye."))
                break;
        }

        udpSocket.close();
    }
}
