/*  
 * Alyssa Williams & Jesse Johnstone 
 * HW2 2023
*/

package HW2;

import java.io.*;
import java.net.*;
import java.text.Format;
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
        
        //Buffered Reader variables to read the inventory csv 
        //Alyssa- /Users/alyssa/Desktop/MSU-Denver/enrolledCourses/CS-3700/Programs/CS3700/HW2/HW02Inventory.csv
        //Jesse - C://Users//jesse//Desktop//Network2023//Repo//CS3700//HW2//HW02Inventory.csv
        String fileName = "C://Users//jesse//Desktop//Network2023//Repo//CS3700//HW2//HW02Inventory.csv";
        File file = new File(fileName);
        FileReader fileReader = new FileReader(file);
        BufferedReader lineReader = new BufferedReader(fileReader);
        String line = "";

        // Inventory variables to create an array of inventory data from the Inventory class 
        String inventoryData[];
        Inventory inventoryTable[];

        while ((line = lineReader.readLine()) != null) {
            int rows; 
            String[] data;

            // Split the csv raw data into rows to parse through 
            inventoryData = line.split("\n");
            // Create an Array of Inventory objects with a length that fits the amount of rows of raw data 
            rows = inventoryData.length;
            inventoryTable = new Inventory[rows]; 

            // Split each row of data up by commas and create individual Inventory objects 
            for (int i = 0; i < rows; i++) {
                data = inventoryData[i].split(",");
                inventoryTable[i] = new Inventory(data[0], data[1], data[2], data[3]);
                
            }

            // Print the whole inventory table 
            for(int i=0; i<inventoryTable.length; i++){
                System.out.println(inventoryTable[i]);
                //System.out.printf(%s , i + 1, inventoryTable[i]);//Maaybe??
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

 
// Inventory class to create items in the Inventory List 
class Inventory {
    private String id, description, price, inventory; 
    
    public Inventory(String id, String description, String price, String inventory){
        this.id = id; 
        this.description = description; 
        this.price = price; 
        this.inventory = inventory; 
    } 

    // getters and setters 
    public String getId(){
        return id; 
    }

    public void setId(String id){
        this.id = id;
    }

    public String getDescription(){
        return description; 
    }

    public void setDescription(String description){
        this.description = description;
    }

    public String getPrice(){
        return price; 
    }

    public void setPrice(String price){
        this.price = price;
    }

    public String getInventory(){
        return inventory; 
    }

    public void setInventory(String inventory){
        this.inventory = inventory;
    }

     @Override
     public String toString(){
         return id +"\t\t" + description + "\t\t" + price + "\t\t" + inventory; 
     }

}