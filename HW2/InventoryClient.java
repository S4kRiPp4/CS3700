/*  
 * CS3700 - Networking and Distributed Computing - Spring 2023
 * Instructor: Dr. Weiying Zhu
 * Programmers: Alyssa Williams & Jesse Johnstone 
 * Description: HW02 - client program
*/

import java.io.*;
import java.net.*;
import java.util.*;

public class InventoryClient {
    public static void main(String[] args) throws IOException {

        // create a UDP socket
        DatagramSocket udpSocket = new DatagramSocket();

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String idInput, dnsInput, fromServer, resHeader, newReq, serverRes, rttstr;
        long sendTS, resTS, rtt;
        String id2 = "Item ID";
        String item2 = "Item Description";
        String price2 = "Unit Price";
        String inv2 = "Inventory";
        String rtt2 = "RTT of Query";

        // Inventory display table and id keys array
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
        dnsInput = reader.readLine().trim();

        // Display table from Server using CSV(display ID and item description)
        System.out.println(inventory);

        // Display message and ask user to input ID with item validation(ask user
        // to re-type if not valid ID, save ID as variable)
        System.out.print("Enter an Item ID to view the item information: ");
        while ((idInput = reader.readLine()) != "N") {
            if (!(inventoryID.contains(idInput))) {
                System.out.print("Item ID not valid, please re-type the Item ID: ");
            } else {

                // Record local time(timestamp) prior to sending request
                sendTS = new Date().getTime();

                // Send request message to server with item ID to destination address for quote
                InetAddress address = InetAddress.getByName(dnsInput);
                byte[] buf = idInput.getBytes();
                DatagramPacket udpPacket = new DatagramPacket(buf, buf.length, address,
                        5140); // 5140 Jesse, 5310 Alyssa
                udpSocket.send(udpPacket);

                // get response
                byte[] buf2 = new byte[256];
                DatagramPacket udpPacket2 = new DatagramPacket(buf2, buf2.length);
                udpSocket.receive(udpPacket2);

                // Record timestamp of received message
                resTS = new Date().getTime();

                // Compute RTT of Query (timestamp of received request - timestamp of
                // pending request, milliseconds)
                rtt = resTS - sendTS;

                // Once request is received display item information from server
                resHeader = String.format("%-20s" + "%-40s" + "%10s" + "%30s" + "%35s", id2, item2, price2, inv2, rtt2);
                fromServer = new String(udpPacket2.getData(), 0, udpPacket2.getLength());
                Long.toString(rtt);
                rttstr = String.format("%35s", Long.toString(rtt) + " ms");
                serverRes = fromServer + rttstr;
                // fromServer + "\t\t" + rtt + " ms";
                System.out.println(resHeader);
                System.out.println(serverRes);

                // Ask user to continue for new inventory query, if N, terminate program
                System.out.println("Request another quote? Y/N: ");
                newReq = reader.readLine().trim().toUpperCase();
                if (newReq.equals("N")) {
                    break;
                }
                System.out.println(inventory);
                // Display message and ask user to input ID with item validation(ask user
                // to re-type if not valid ID, save ID as variable)
                System.out.print("Enter an Item ID to view the item information: ");
            }
        }

        udpSocket.close();
    }
}
