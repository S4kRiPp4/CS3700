
/*  
 * CS3700 - Networking and Distributed Computing - Spring 2023
 * Instructor: Dr. Weiying Zhu
 * Programmers: Alyssa Williams & Jesse Johnstone 
 * Description: HW03 - A thread is started to handle every client TCP connection to the server program
*/
import java.net.*;
import java.text.*;
import java.io.*;
import java.util.*;

public class SMTPthread extends Thread {
    private Socket clientTCPSocket = null;

    // constructor
    public SMTPthread(Socket socket) {
        clientTCPSocket = socket;
    }

    // method to handle each thread for every client
    public void run() {
        try {
            // output socket back to the client
            PrintWriter cSocketOut = new PrintWriter(clientTCPSocket.getOutputStream(), true);

            // input socket from the client
            BufferedReader cSocketIn = new BufferedReader(new InputStreamReader(clientTCPSocket.getInputStream()));

            // variables to display/send text to and from client
            String fromClient, toClient, msg, helo, mailFrom, rcptTo, data, message, serverIP, clientIP;
            Boolean open = true;

            // TODO: SMTP client. Send the “220” response including server IP or DNS to
            // client
            serverIP = cSocketIn.readLine();
            System.out.println(serverIP);
            clientIP = clientTCPSocket.getInetAddress().getHostAddress();
            msg = "220 " + serverIP;
            cSocketOut.println(msg);

            // Read requests and send responses until a null is read (happens when a
            // particular client closes the TCP connection)
            while (open) {
                fromClient = cSocketIn.readLine();
                System.out.println(fromClient);

                while (!(fromClient.contains("HELO"))) {
                    toClient = "503 5.5.2 Send hello first";
                    cSocketOut.println(toClient);
                    fromClient = cSocketIn.readLine();
                    System.out.println(fromClient);
                }
                toClient = "250 " + serverIP + " Hello " + clientIP;
                cSocketOut.println(toClient);

                while (!(fromClient.contains("MAIL FROM"))) {
                    toClient = "503 5.5.2 Need mail command";
                    cSocketOut.println(toClient);
                    fromClient = cSocketIn.readLine();
                    System.out.println(fromClient);
                }
                toClient = "250 2.1.0 Sender OK";
                cSocketOut.println(toClient);

                while (!(fromClient.contains("RCPT TO"))) {
                    toClient = "503 5.5.2 Need rcpt command";
                    cSocketOut.println(toClient);
                    fromClient = cSocketIn.readLine();
                    System.out.println(fromClient);
                }
                toClient = "250 2.1.5 Recipient OK";
                cSocketOut.println(toClient);

                while (!(fromClient.contains("DATA"))) {
                    toClient = "503 5.5.2 Need data command";
                    cSocketOut.println(toClient);
                    fromClient = cSocketIn.readLine();
                    System.out.println(fromClient);
                }
                toClient = "354 Start mail input; end with <CRLF>.<CRLF>";
                cSocketOut.println(toClient);

                // while (!(fromClient.contains("\r\n.\r\n"))) {
                //     fromClient = cSocketIn.readLine();
                //     System.out.println(fromClient);
                // }
                // toClient = "250 Message received and to be delivered\r\n";
                // cSocketOut.println(toClient);

                if (fromClient.contains("QUIT")) {
                    toClient = "221 " + serverIP + " closing connection";
                    cSocketOut.println(toClient);
                    open = false;
                }
            }

            // Close i/o streams and the TCP socket for the specific Client, and terminate
            // the thread for the specific client.
            cSocketOut.close();
            cSocketIn.close();
            clientTCPSocket.close();
        } catch (Exception e) {
            e.printStackTrace();

        }
    }
}
