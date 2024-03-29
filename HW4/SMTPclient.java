
/*  
 * CS3700 - Networking and Distributed Computing - Spring 2023
 * Instructor: Dr. Weiying Zhu
 * Programmers: Alyssa Williams & Jesse Johnstone 
 * Description: HW04 - client program
*/
import java.io.*;
import java.net.*;
import java.util.*;

public class SMTPclient {
    public static void main(String[] args) throws IOException {
        boolean open = true;
        Socket tcpSocket = null;
        PrintWriter socketOut = null;
        BufferedReader socketIn = null;
        String host, hostConnect, fromUser, fromServer, sendEmail, recEmail, subject, body, helo, mailFrom, rcptTo,
                data, message,
                connection;
        long sendRTT, recRTT, rtt;
        int port = 5310; // 5140 Jesse, 5310 Alyssa

        BufferedReader sysIn = new BufferedReader(new InputStreamReader(System.in));

        // Ask user to input DNS or IP Address for server
        System.out.print("Please enter the DNS/IP address: ");
        fromUser = sysIn.readLine();
        host = fromUser;

        // Build TCP connection from server
        try {
            tcpSocket = new Socket(host, port);
            socketOut = new PrintWriter(tcpSocket.getOutputStream(), true);
            socketIn = new BufferedReader(new InputStreamReader(tcpSocket.getInputStream()));
            // Wait for read, and display "220" response from server
            hostConnect = host + "\r\n";
            socketOut.print(hostConnect);
            socketOut.flush();
            connection = socketIn.readLine();
            System.out.println(connection);

        } // Catch exceptions, terminate if error occurs
        catch (UnknownHostException e) {
            System.err.println("Unknown Host: " + host);
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for the connection to: " + host);
            System.exit(1);
        }

        while (open) {

            // Ask user for senders e-mail address and save response
            System.out.print("Enter sender's email address: ");
            sendEmail = sysIn.readLine();
            while (!(sendEmail.contains("@"))) {
                System.out.print("Invalid email format (123@example.com). Re-enter sender's email address: ");
                sendEmail = sysIn.readLine();
            }

            // Ask user for receivers e-mail address and save response
            System.out.print("Enter recipient email address: ");
            recEmail = sysIn.readLine();
            while (!(recEmail.contains("@"))) {
                System.out.print("Invalid email format (123@example.com). Re-enter recipient email address: ");
                recEmail = sysIn.readLine();
            }

            // Ask user for email subject and save response
            System.out.print("Enter email subject: ");
            subject = sysIn.readLine();

            // Ask user for email contents and save response
            System.out.print("Enter email body: ");
            body = sysIn.readLine();

            // Construct and send HELO command to the SMTP server
            helo = "HELO " + sendEmail + "\r\n";
            System.out.println(helo);
            sendRTT = new Date().getTime();
            socketOut.print(helo);
            socketOut.flush();
            // Wait for server’s response and display it on the standard output
            fromServer = socketIn.readLine();
            if (fromServer.contains("Hello")) {
                recRTT = new Date().getTime();
                System.out.println(fromServer);
                // Compute RTT of HELO and display to console
                rtt = recRTT - sendRTT;
                System.out.println("HELO Transmission RTT: " + Long.toString(rtt) + " ms\r\n");
            } else {
                while (fromServer.equals("503 5.5.2 Send hello first")) {
                    recRTT = new Date().getTime();
                    System.out.println(fromServer);
                    // Compute RTT of HELO and display to console
                    rtt = recRTT - sendRTT;
                    System.out.println("HELO Transmission RTT: " + Long.toString(rtt) + " ms\r\n");
                    socketOut.print(helo);
                    socketOut.flush();
                    fromServer = socketIn.readLine();
                }
            }

            // Construct and send the “MAIL FROM: <sender’s email address>” command and display on std out
            mailFrom = "MAIL FROM: " + sendEmail + "\r\n";
            sendRTT = new Date().getTime();
            socketOut.print(mailFrom);
            socketOut.flush();
            fromServer = socketIn.readLine();
            if (fromServer.equals("250 2.1.0 Sender OK")) {
                recRTT = new Date().getTime();
                System.out.println(fromServer);
                // Compute RTT of mailFrom and display to console
                rtt = recRTT - sendRTT;
                System.out.println("MAIL FROM: Transmission RTT: " + Long.toString(rtt) + " ms\r\n");

            } else {
                while (fromServer.equals("503 5.5.2 Need mail command")) {
                    recRTT = new Date().getTime();
                    System.out.println(fromServer);
                    // Compute RTT of HELO and display to console
                    rtt = recRTT - sendRTT;
                    System.out.println("MAIL FROM: Transmission RTT: " + Long.toString(rtt) + " ms\r\n");
                    socketOut.print(mailFrom);
                    socketOut.flush();
                    fromServer = socketIn.readLine();
                }
            }

            // Construct and send the “RCPT TO: <receiver’s email address>” command and display on std out
            rcptTo = "RCPT TO: " + recEmail + "\r\n";
            sendRTT = new Date().getTime();
            socketOut.print(rcptTo);
            socketOut.flush();
            fromServer = socketIn.readLine();
            if (fromServer.equals("250 2.1.5 Recipient OK")) {
                recRTT = new Date().getTime();
                System.out.println(fromServer);
                // Compute RTT of mailFrom and display to console
                rtt = recRTT - sendRTT;
                System.out.println("RCPT TO Transmission RTT: " + Long.toString(rtt) + " ms\r\n");

            } else {
                while (fromServer.equals("503 5.5.2 Need rcpt command")) {
                    recRTT = new Date().getTime();
                    System.out.println(fromServer);
                    // Compute RTT of mailFrom and display to console
                    rtt = recRTT - sendRTT;
                    System.out.println("RCPT TO Transmission RTT: " + Long.toString(rtt) + " ms\r\n");
                    socketOut.print(rcptTo);
                    socketOut.flush();
                    fromServer = socketIn.readLine();
                }
            }

            // Construct and send the “DATA” command and display on std out
            data = "DATA\r\n";
            sendRTT = new Date().getTime();
            socketOut.print(data);
            socketOut.flush();
            fromServer = socketIn.readLine();
            if (fromServer.equals("354 Start mail input; end with <CRLF>.<CRLF>")) {
                recRTT = new Date().getTime();
                System.out.println(fromServer);
                // Compute RTT of mailFrom and display to console
                rtt = recRTT - sendRTT;
                System.out.println("RCPT TO Transmission RTT: " + Long.toString(rtt) + " ms\r\n");
            } else {
                while (fromServer.equals("503 5.5.2 Need data command")) {
                    recRTT = new Date().getTime();
                    System.out.println(fromServer);
                    // Compute RTT of mailFrom and display to console
                    rtt = recRTT - sendRTT;
                    System.out.println("RCPT TO Transmission RTT: " + Long.toString(rtt) + " ms\r\n");
                    socketOut.print(data);
                    socketOut.flush();
                    fromServer = socketIn.readLine();
                }
            }

            // Construct and send the Mail message to the SMTP server
            message = "To: " + sendEmail + "\r\n" + "From: " + recEmail + "\r\n" + "Subject: " + subject + "\r\n\r\n"+ body + "\r\n\r\n.\r\n";
            System.out.println(message);
            sendRTT = new Date().getTime();
            socketOut.print(message);
            socketOut.flush();
            fromServer = socketIn.readLine();
            recRTT = new Date().getTime();
            System.out.println(fromServer);
            // Compute RTT of mailFrom and display to console
            rtt = recRTT - sendRTT;
            System.out.println("RCPT TO Transmission RTT: " + Long.toString(rtt) + "ms\r\n");

            // Display a prompt message to ask the User whether to continue.
            // If "N" send a “QUIT” command to the server, display SMTP Server’s response, close TCP
            // connection, and terminate the Client program, otherwise continue.
            System.out.print("\r\nWould you like to continue? Y/N: ");
            fromUser = sysIn.readLine().toUpperCase();
            if (fromUser.equals("N")) {
                String quit = "QUIT" + "\r\n";
                socketOut.print(quit);
                socketOut.flush();
                fromServer = socketIn.readLine();
                System.out.println(fromServer);
                socketOut.close();
                socketIn.close();
                sysIn.close();
                tcpSocket.close();
                open = false; 
                break;
            
            }

        }
    }

}