
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
        String host, fromUser, fromServer, sendEmail, recEmail, subject, body, helo, mailFrom, rcptTo, data, message,
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
            // TODO: Wait for read, and display "220" response from server (MESSAGE NEEDS TO
            // COME FROM SERVER)

            socketOut.print(host + "\r\n");
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

            // Ask user for receivers e-mail address and save response
            System.out.print("Enter receiver's email address: ");
            recEmail = sysIn.readLine();

            // Ask user for email subject and save response
            System.out.print("Enter email subject: ");
            subject = sysIn.readLine();

            // Ask user for email contents and save response
            System.out.print("Enter email body: ");
            body = sysIn.readLine();

            // Construct and send HELO command to the SMTP server
            helo = "HELO " + sendEmail + "\r\n\r\n";
            System.out.println(helo);
            sendRTT = new Date().getTime();
            socketOut.print(helo);
            socketOut.flush();
            // Wait for server’s response and display it on the standard output
            // TODO: DO we need a while loop for every message???
            // while((fromServer = socketIn.readLine()) != null)
            // {
            fromServer = socketIn.readLine();
            recRTT = new Date().getTime();
            System.out.println("SMTP Server Response: " + fromServer);
            // Compute RTT of HELO and display to console
            rtt = recRTT - sendRTT;
            System.out.println("HELO Transmission RTT: " + Long.toString(rtt) + " ms\r\n");
            // }

            // TODO: (b)Construct and send the “MAIL FROM: <sender’s email address>” command
            // to SMTP server, wait for SMTP server’s response and display it on the
            // standard output.
            // TODO: Compute RTT of MAIL FROM
            mailFrom = "MAIL FROM: " + sendEmail + "\r\n";
            sendRTT = new Date().getTime();
            socketOut.print(mailFrom);
            socketOut.flush();
            fromServer = socketIn.readLine();
            recRTT = new Date().getTime();
            System.out.println("SMTP Server Response: " + fromServer);
            // Compute RTT of mailFrom and display to console
            rtt = recRTT - sendRTT;
            System.out.println("MAIL FROM: Transmission RTT: " + Long.toString(rtt) + " ms\r\n");
            // }

            // TODO: (c)Construct and send the “RCPT TO: <receiver’s email address>” command
            // to the SMTP server program, wait for SMTP server’s response and display it on
            // the standard output.
            // TODO: Compute RTT of RCPT TO
            rcptTo = "RCPT TO: " + recEmail + "\r\n";
            sendRTT = new Date().getTime();
            socketOut.print(rcptTo);
            socketOut.flush();
            fromServer = socketIn.readLine();
            recRTT = new Date().getTime();
            System.out.println("SMTP Server Response: " + fromServer);
            // Compute RTT of mailFrom and display to console
            rtt = recRTT - sendRTT;
            System.out.println("RCPT TO Transmission RTT: " + Long.toString(rtt) + " ms\r\n");

            // TODO: (d)Construct and send the “DATA” command to the SMTP server program,
            // wait for SMTP server’s response and display it on the standard output
            // TODO: Compute RTT of DATA
            data = "DATA\r\n";
            sendRTT = new Date().getTime();
            socketOut.print(data);
            socketOut.flush();
            fromServer = socketIn.readLine();
            recRTT = new Date().getTime();
            System.out.println("SMTP Server Response: " + fromServer);
            // Compute RTT of mailFrom and display to console
            rtt = recRTT - sendRTT;
            System.out.println("RCPT TO Transmission RTT: " + Long.toString(rtt) + " ms\r\n");

            // TODO: (e)Construct and send the Mail message to the SMTP server. The format
            // of this Mail message MUST follow the format
            // TODO: detailed on the slide titled “Mail message format”. Wait for SMTP
            // server’s response and display it on the standard output
            // TODO: Compute RTT of Mail Message
            message = "To: " + sendEmail + "\r\n" + "From: " + recEmail + "\r\n" + "Subject: " + subject + "\r\n\r\n"
                    + body + "\r\n" + ".\r\n";
            sendRTT = new Date().getTime();
            socketOut.print(message);
            socketOut.flush();
            fromServer = socketIn.readLine();
            recRTT = new Date().getTime();
            System.out.println("SMTP Server Response: " + fromServer);
            // Compute RTT of mailFrom and display to console
            rtt = recRTT - sendRTT;
            System.out.println("RCPT TO Transmission RTT: " + Long.toString(rtt) + " ms\r\n");

            /// TODO: Display a prompt message to ask the User whether to continue.
            // If yes, repeat steps 3 through 5. Otherwise, send a “QUIT” command to the
            /// SMTP server, display SMTP Server’s response, close TCP
            // connection, and terminate the Client program.
            System.out.print("\r\nWould you like to continue? Y/N: ");
            fromUser = sysIn.readLine().toUpperCase();
            if (fromUser.equals("N")) {
                String quit = "QUIT" + "\r\n";
                socketOut.print(quit);
                socketOut.flush();
                while ((fromServer = socketIn.readLine()) != null) {
                    System.out.println(fromServer);
                    break;
                }
                socketOut.close();
                socketIn.close();
                sysIn.close();
                tcpSocket.close();
                break;
            }

        }
    }

}