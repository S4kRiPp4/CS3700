/*  
 * CS3700 - Networking and Distributed Computing - Spring 2023
 * Instructor: Dr. Weiying Zhu
 * Programmers: Alyssa Williams & Jesse Johnstone 
 * Description: HW03 - Main Server program
*/

import java.net.*; 
import java.io.*; 

public class ServerTCPHTTP{
    public static void main (String[] args) throws IOException{
        ServerSocket serverTCPSocket = null; 
        boolean listening = true; 
        int port = 5310; // 5140 Jesse, 5310 Alyssa

        // Listen to the given port and wait for a connection request from a HTTP Client
        try{
            serverTCPSocket = new ServerSocket(port); 

        }catch(IOException e){
            System.err.println("Could not listen on the specified port: " + port); 
            System.exit(-1); 
        }
        // Create a new thread for every incoming TCP connection request from a HTTP client
        while(listening){
            new TCPHTTPServerThread(serverTCPSocket.accept()).start();
        }

        // Close server socket 
        serverTCPSocket.close();
    }
}