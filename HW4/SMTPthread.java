
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
            String fromClient, toClient, msg, serverIP, clientIP;
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
            // TODO: Implement 3-phase data transfer procedure
            fromClient = cSocketIn.readLine();
            while (open) {
                
                System.out.println("FROM CLIENT: " + fromClient);
                while (!(fromClient.contains("HELO"))) {
                    // TODO:...”, sends “503 5.5.2 Send hello first” response to the SMTP client and
                    // repeat step 3.a.
                    toClient = "503 5.5.2 Send hello first";
                    cSocketOut.println(toClient);
                    System.out.println("Sent Message: " + toClient);

                    fromClient = cSocketIn.readLine();
                    System.out.println("FROM CLIENT: " + fromClient);
                }
                // TODO:b. Send the “250 <server’s ip> Hello <client’s ip>” response to the SMTP
                // client.
                toClient = "250 " + serverIP + " Hello " + clientIP;
                cSocketOut.println(toClient);
                System.out.println("Sent Message: " + toClient);

                // TODO:c. Wait for, read, and display the “MAIL FROM: ...” command from the
                // SMTP client. If the incoming command is NOT
                fromClient = cSocketIn.readLine();
                System.out.println("FROM CLIENT: " + fromClient);
                while (!(fromClient.contains("MAIL FROM"))) {
                    // TODO:“MAIL FROM: ...”, sends “503 5.5.2 Need mail command” response to the
                    // SMTP client and repeat step 3.c.
                    toClient = "503 5.5.2 Need mail command";
                    cSocketOut.println(toClient);
                    System.out.println("Sent Message: " + toClient);

                    fromClient = cSocketIn.readLine();
                    System.out.println("FROM CLIENT: " + fromClient);
                }
                // TODO:d. Send the “250 2.1.0 Sender OK” response to the SMTP client.
                toClient = "250 2.1.0 Sender OK";
                cSocketOut.println(toClient);
                System.out.println("Sent Message: " + toClient);

                // TODO:e. Wait for, read, and display the “RCPT TO: ...” command from the SMTP
                // client. If the incoming command is NOT “RCPT
                fromClient = cSocketIn.readLine();
                System.out.println("FROM CLIENT: " + fromClient);
                while (!(fromClient.contains("RCPT TO"))) {
                    // TODO:TO: ...”, send “503 5.5.2 Need rcpt command” response to the SMTP client
                    // and repeat step 3.e.
                    toClient = "503 5.5.2 Need rcpt command";
                    cSocketOut.println(toClient);
                    System.out.println("Sent Message: " + toClient);

                    fromClient = cSocketIn.readLine();
                    System.out.println("FROM CLIENT: " + fromClient);
                }
                // TODO:f. Send the “250 2.1.5 Recipient OK” response to the SMTP client.
                toClient = "250 2.1.5 Recipient OK";
                cSocketOut.println(toClient);
                System.out.println("Sent Message: " + toClient);

                // TODO:g. Wait for, read, and display the “DATA” command from the SMTP client.
                // If the incoming command is NOT “DATA”,
                fromClient = cSocketIn.readLine();
                System.out.println("FROM CLIENT: " + fromClient);
                while (!(fromClient.contains("DATA"))) {
                    // TODO:send “503 5.5.2 Need data command” response to the SMTP client and
                    // repeat step 3.g.
                    toClient = "503 5.5.2 Need data command";
                    cSocketOut.println(toClient);
                    System.out.println("Sent Message: " + toClient);

                    fromClient = cSocketIn.readLine();
                    System.out.println("FROM CLIENT: " + fromClient);
                }
                // TODO:h. Send the “354 Start mail input; end with <CRLF>.<CRLF>” response to
                // the SMTP client.
                toClient = "354 Start mail input; end with <CRLF>.<CRLF>";
                cSocketOut.println(toClient);
                System.out.println("Sent Message: " + toClient + "End Transmission.");

                // TODO:i. Wait for, read, and display the Mail message from the SMTP client
                // line by line. (hint: “.” is the ending signature.)
                boolean crlf = false;
                while ((fromClient = cSocketIn.readLine()) != null) {
                    System.out.println(fromClient);
                    if (crlf && fromClient.equals(".")) {
                        // TODO:j. Send the “250 Message received and to be delivered” response tothe
                        // SMTP client.
                        toClient = "250 Message received and to be delivered";
                        cSocketOut.println(toClient);
                        break;
                    } else {
                        if (fromClient.length() == 0) {
                            crlf = true;
                            System.out.println("CRLF FOUND!");
                        } else {
                            crlf = false;
                            System.out.println("THE CRLF WAS RESET");
                        }

                    }
                    System.out.println("LOOP WAITING ON INPUT");
                }

                // // TODO: repeat until quit command is read
                fromClient = cSocketIn.readLine();
                System.out.println("FROM CLIENT: " + fromClient);
                if (fromClient.contains("QUIT")) {
                    // TODO: upon receiving quit send the "221<servers IP> closing connection" to
                    // client
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
