

/*  
 * CS3700 - Networking and Distributed Computing - Spring 2023
 * Instructor: Dr. Weiying Zhu
 * Programmers: Alyssa Williams & Jesse Johnstone 
 * Description: HW04 - Main Server program
*/

import java.net.*;
import java.io.*;

public class SMTPserver {
    public static void main(String[] args) throws IOException {
        ServerSocket serverTCPSocket = null;
        boolean listening = true;
        int port = 5140; // 5140 Jesse, 5310 Alyssa

        // Listen to the given port and wait for a connection request from a HTTP Client
        try {
            serverTCPSocket = new ServerSocket(port);

        } catch (IOException e) {
            System.err.println("Could not listen on the specified port: " + port);
            System.exit(-1);
        }
       
        // TODO: Create a new thread for every incoming TCP connection request from a SMTP
        
        while (listening) {
            new SMTPthread(serverTCPSocket.accept()).start();
        }

        // Close server socket
        serverTCPSocket.close();
    }
}
