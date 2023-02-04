/*  
 * Alyssa Williams & Jesse Johnstone 
 * HW2 2023
*/

package HW2;

import java.io.*;
import java.net.*;
import java.util.*;

public class InventoryClient {
    public static void main(String[] args) throws IOException {

        // create a UDP socket
        DatagramSocket udpSocket = new DatagramSocket();

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String fromServer;
        String idInput;
        String newReq;

        //Inventroy display table and id keys array 
        String inventory = "Item ID\t\tItem Description\n" +
                "00001\t\tNew Inspiron 15\n" +
                "00002\t\tNew Inspiron 17\n" +
                "00003\t\tNew Inspiron 15R\n" +
                "00004\t\tNew Inspiron 15z Ultrabook\n" +
                "00005\t\tXPS 14 Ultrabook\n" +
                "00006\t\tNew XPS 12 UltrabookXPS";
        ArrayList<String> inventoryID = new ArrayList<>(
                Arrays.asList("00001", "00002", "00003", "00004", "00005", "00006"));

        // Display message for user input for DNS of server(read user input as a string,
        // save input as variable)
        System.out.print("Input DNS or IP address of the Server: ");
        String dnsInput = reader.readLine();

        String serverIP = "147.153.10.87";
        dnsInput.trim();

        if (!(dnsInput.equals(serverIP))) {
            System.out.println("Invalid IP address, try again.");
        } else {
            // Display table from Server using CSV(display ID and item description)
            System.out.println("Welcome.");
            System.out.println(inventory);
            // Display message and ask user to input ID with item validation(ask user
            // to re-type if not valid ID, save ID as variable)
            System.out.print("Enter an Item ID to view the item information: ");
            while ((idInput = reader.readLine()) != "N") {
                if (!(inventoryID.contains(idInput))) {
                    System.out.println("Item ID not found, try again");
                } else {
                    System.out.println("Sending datagram to server");
                    // TODO: Record local time(timestamp) prior to sending request
                    long sendTS = new Date().getTime();
                    System.out.println(sendTS);
                    // TODO: Send request message to server with item ID and DNS of
                    // server(destination address), for quote
                    // send request
                    InetAddress address = InetAddress.getByName(args[0]);
                    byte[] buf = idInput.getBytes();
                    DatagramPacket udpPacket = new DatagramPacket(buf, buf.length, address,
                    5140); // 5140 Jesse, 5310 Alyssa
                    udpSocket.send(udpPacket);

                    // get response
                    byte[] buf2 = new byte[256];
                    DatagramPacket udpPacket2 = new DatagramPacket(buf2, buf2.length);
                    udpSocket.receive(udpPacket2);
                    // TODO: Record timestamp of received message
                    System.out.println(idInput);
                    long resTS = new Date().getTime();
                    System.out.println(resTS);

                    // TODO: Compute RTT of Query (timestamp of received request - timestamp of
                    // pending request, milliseconds)
                    long rtt = resTS - sendTS;
                    System.out.println(rtt);
                    // TODO: Once request is received display item information from Server(Item ID,
                    // Item Description, Unit Price, Inventory, RTT of Query)
                    // display response
                    fromServer = new String(udpPacket2.getData(), 0, udpPacket2.getLength());
                    System.out.println("From Server: " + fromServer);
                    // TODO: Ask user to continue for new inventory query, if yes repeat process
                    // else close socket and terminate client program
                    System.out.println("Request another quote? Y/N: ");
                    newReq = reader.readLine().trim();
                    if (newReq.equals("N")) {
                        break;
                    }
                    System.out.println(inventory);
                    // Display message and ask user to input ID with item validation(ask user
                    // to re-type if not valid ID, save ID as variable)
                    System.out.print("Enter an Item ID to view the item information: ");
                }
            }
        }

        udpSocket.close();
    }
}
