//TODO: Ask user to input DNS or IP Address for server

//TODO:Build TCP connection from server
//TODO: Wait for read, and display "220" response from server
//TODO: Catch exceptions, terminate if error occurs

//TODO: Ask user for senders e-mail address, receivers e-mail address, subject, and email contents

//TODO: Use input for
//TODO: (a)Construct and send the “HELO <sender’s mail server domain name>” command (e.g., HELO xyz.com) to the SMTP server
//TODO: program, wait for server’s response and display it on the standard output
//TODO: (b)Construct and send the “MAIL FROM: <sender’s email address>” command to SMTP server, wait for SMTP server’s
//TODO: response and display it on the standard output.
//TODO: (c)Construct and send the “RCPT TO: <receiver’s email address>” command to the SMTP server program, wait for SMTP
//TODO: server’s response and display it on the standard output.
//TODO: (d)Construct and send the “DATA” command to the SMTP server program, wait for SMTP server’s response and display it
//TODO: on the standard output
//TODO: (e)Construct and send the Mail message to the SMTP server. The format of this Mail message MUST follow the format
//TODO: detailed on the slide titled “Mail message format”. Wait for SMTP server’s response and display it on the standard
//TODO: output
///TODO: (F)Display a prompt message to ask the User whether to continue. If yes, repeat steps 3 through 5. Otherwise, send a “QUIT”
//TODO: command to the SMTP server, display SMTP Server’s response, close TCP connection, and terminate the Client program.