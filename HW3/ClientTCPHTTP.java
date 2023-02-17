/*  
 * CS3700 - Networking and Distributed Computing - Spring 2023
 * Instructor: Dr. Weiying Zhu
 * Programmers: Alyssa Williams & Jesse Johnstone 
 * Description: HW03 - client program
*/
import java.io.*;
import java.net.*;
import java.util.*;

public class ClientTCPHTTP {
    public static void main(String[] args) throws IOException {
        Socket tcpSocket = null;
        PrintWriter socketOut = null;
        BufferedReader socketIn = null;
        String host, fromUser, fromServer, httpMethod, htmFile, httpVersion, userAgent;
        long beforeTCP, afterTCP, tcpRtt, beforeFT, afterFT, ftRTT;
        int port = 5310; // 5140 Jesse, 5310 Alyssa

        BufferedReader sysIn = new BufferedReader(new InputStreamReader(System.in));
        // Ask user for DNS/IP of HTTP server
        System.out.print("Please enter the DNS/IP address: ");
        fromUser = sysIn.readLine();
        host = fromUser;

        // Create TCP connection to HTTP server with the Host Name input by User at the
        // given port
        try {
            beforeTCP = new Date().getTime();
            tcpSocket = new Socket(host, port);
            socketOut = new PrintWriter(tcpSocket.getOutputStream(), true);
            socketIn = new BufferedReader(new InputStreamReader(tcpSocket.getInputStream()));
            afterTCP = new Date().getTime();

            // Display the RTT of TCP connection in ms(difference between the moments right
            // before and after creating socket object).
            tcpRtt = afterTCP - beforeTCP;
            System.out.println("RTT of TCP connection: " + Long.toString(tcpRtt) + " ms");

        } // Catch any exception, terminate the program, and display error messages to
          // terminal.
        catch (UnknownHostException e) {
            System.err.println("Unknown Host: " + host);
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for the connection to: " + host);
            System.exit(1);
        }
        // Ask the user to input the HTTP method type and save to string variable
        System.out.print("Enter the HTTP Method type: ");
        while ((fromUser = sysIn.readLine()) != null) {
            // set HTTP method type from user input 
            httpMethod = fromUser.toUpperCase();

            // Ask user to input name of the htm file requested and save to string variable
            System.out.print("Enter the name of the htm file: ");
            htmFile = " /" + sysIn.readLine();

            // Ask user to input HTTP Version and save to string variable
            System.out.print("Enter the HTTP Version: ");
            httpVersion = "HTTP/" + sysIn.readLine();
            
            // Ask user to input User-Agent and save to string variable
            System.out.print("Enter the User-Agent: ");
            userAgent = sysIn.readLine();

            // From user input, construct ONE HTTP request message
            fromUser = httpMethod + " " + htmFile + " " + httpVersion + "\r\n" + "Host: " + host + "\r\n"
                    + "User Agent: " + userAgent + "\r\n" + "\r\n";
            System.out.print(fromUser);

            // Capture timestamp before file is transmitted
            beforeFT = new Date().getTime();

            // Send request to the HTTP server program over the TCP connection
            socketOut.println(fromUser);

            // TODO: Receive and interpret the HTTP response message from the HTTP Server
            // program line by line
            if ((fromServer = socketIn.readLine()) != null)
            {
                System.out.println(fromServer);
            }
            else {
                System.out.println("Server replies nothing!");
                break;
            }

            // Capture timestamp after response is received
            afterFT = new Date().getTime();

            // TODO: Display the RTT (File Transmission Time may be included) of HTTP query
            // in ms as a single line (e.g., RTT = 1.089 ms)
            ftRTT = afterFT - beforeFT;
            System.out.println("File Transmission RTT: " + Long.toString(ftRTT) + " ms");

            // TODO: Display the status line and header lines of the HTTP response message
            // on the standard output
            

            // TODO: Save the data in the entity body to a .htm file to local directory if
            // there is any.

            // Display a message on the standard output to ask the User whether to
            // continue. If yes, repeat steps 3 through 6. Otherwise, close all i/o streams,
            // TCP connection, and terminate the Client program.
            System.out.print("Would you like to make another request? Y/N: ");
            fromUser = sysIn.readLine().toUpperCase();
            if (fromUser.equals("N")) {
                socketOut.close();
                socketIn.close();
                sysIn.close();
                tcpSocket.close();
                break;
            }
            System.out.print("Enter the HTTP Method type: ");
        }
    }

}