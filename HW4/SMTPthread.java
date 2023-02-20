package HW4;

public class SMTPthread {
    // TODO: Create a new thread for every incoming TCP connection request from a
    // TODO: SMTP client. Send the “220” response including server IP or DNS to client
    // TODO: Implement 3-phase data transfer procedure
    // TODO: a. Wait for, read, and display the “HELO ...” command from the SMTP client. If the incoming command is NOT “HELO
    // TODO:...”, sends “503 5.5.2 Send hello first” response to the SMTP client and repeat step 3.a.
    // TODO:b. Send the “250 <server’s ip> Hello <client’s ip>” response to the SMTP client.
    // TODO:c. Wait for, read, and display the “MAIL FROM: ...” command from the SMTP client. If the incoming command is NOT
    // TODO:“MAIL FROM: ...”, sends “503 5.5.2 Need mail command” response to the SMTP client and repeat step 3.c.
    // TODO:d. Send the “250 2.1.0 Sender OK” response to the SMTP client.
    // TODO:e. Wait for, read, and display the “RCPT TO: ...” command from the SMTP client. If the incoming command is NOT “RCPT
    // TODO:TO: ...”, send “503 5.5.2 Need rcpt command” response to the SMTP client and repeat step 3.e.
    // TODO:f. Send the “250 2.1.5 Recipient OK” response to the SMTP client.
    // TODO:g. Wait for, read, and display the “DATA” command from the SMTP client. If the incoming command is NOT “DATA”,
    // TODO:send “503 5.5.2 Need data command” response to the SMTP client and repeat step 3.g.
    // TODO:h. Send the “354 Start mail input; end with <CRLF>.<CRLF>” response to the SMTP client.
    // TODO:i. Wait for, read, and display the Mail message from the SMTP client line by line. (hint: “.” is the ending signature.)
    // TODO:j. Send the “250 Message received and to be delivered” response to the SMTP client.
    
    // TODO: repeat until quit command is read
    // TODO: upon receiving quit send the "221<servers IP> closing connection" to client
    // TODO: close all I/o streams and the TCP socket for client and terminate the thread for the client
    
}
