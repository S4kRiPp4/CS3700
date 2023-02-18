
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
        String host, fromUser, fromServer, httpMethod, file, httpVersion, userAgent, resHead, body;
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

            // Display the RTT of TCP connection in ms
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
            file = sysIn.readLine();

            // Ask user to input HTTP Version and save to string variable
            System.out.print("Enter the HTTP Version: ");
            httpVersion = "HTTP/" + sysIn.readLine();

            // Ask user to input User-Agent and save to string variable
            System.out.print("Enter the User-Agent: ");
            userAgent = sysIn.readLine();

            // From user input, construct ONE HTTP request message
            fromUser = httpMethod + " " + file + " " + httpVersion + "\r\n" + "Host: " + host + "\r\n"
                    + "User Agent: " + userAgent + "\r\n" + "\r\n";
            System.out.print(fromUser);

            // Capture timestamp before file is transmitted
            beforeFT = new Date().getTime();

            // Send request to the HTTP server program over the TCP connection
            socketOut.print(fromUser);
            socketOut.flush();

            // Receive and interpret the HTTP response message from the HTTP Server program
            // line by line
            if ((fromServer = socketIn.readLine()) != null) {
                // Capture timestamp after response is received
                afterFT = new Date().getTime();
                // Display the RTT (File Transmission Time may be included) of HTTP query in ms;
                ftRTT = afterFT - beforeFT;
                System.out.println("File Transmission RTT: " + Long.toString(ftRTT) + " ms");
                // Display the status line and header lines of the HTTP response message on the
                // standard output
                if (fromServer.contains("200")) {
                    resHead = fromServer + "\r\n" + socketIn.readLine() + "\r\n" + socketIn.readLine() + "\r\n"
                            + socketIn.readLine();
                    System.out.print(resHead);
                    // Save the data in the entity body to a .htm file to local directory if there
                    // is any.
                    File outFile = new File(file);
                    BufferedWriter writer = new BufferedWriter(new FileWriter(outFile));
                    body = socketIn.readLine() + "\r\n" + socketIn.readLine() + "\r\n" + socketIn.readLine() + "\r\n"
                            + socketIn.readLine();
                    if (outFile.exists()) {
                        writer.write(body);
                        System.out.println("entity body: \r\n" + body + "\r\nwritten to the .htm file!");
                        writer.close();
                    }
                } else {
                    resHead = fromServer + "\r\n" + socketIn.readLine() + "\r\n" + socketIn.readLine() + "\r\n"
                            + socketIn.readLine();
                    System.out.print(resHead);
                }

            } else {
                System.out.println("Server replies nothing!");
                break;
            }

            // Display a message on the standard output to ask the User whether to
            // continue. If yes, repeat steps 3 through 6. Otherwise, close all i/o streams,
            // TCP connection, and terminate the Client program.
            socketOut.flush();
            System.out.print("\r\nWould you like to make another request? Y/N: ");
            fromUser = sysIn.readLine().toUpperCase();
            if (fromUser.equals("N")) {
                socketOut.flush();
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