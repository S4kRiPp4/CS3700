/*  
 * CS3700 - Networking and Distributed Computing - Spring 2023
 * Instructor: Dr. Weiying Zhu
 * Programmers: Alyssa Williams & Jesse Johnstone 
 * Description: HW03 - client program
*/

public class ClientTCPHTTP {
    public static void main(String[] args){
        // TODO: Ask user for DNS/IP of HTTP server 

        // TODO: create TCP connection to HTTP server with the Host Name input by User at the given port
        // TODO: display the RTT of TCP connection in ms(difference between the moments right before and after creating socket object). 
        // TODO: Catch any exception, terminate the program, and display error messages to terminal.

        // TODO: Ask the user to input the HTTP method type and save to string variable
        // TODO: Ask user to input name of the htm file requested and save to string variable
        // TODO: Ask user to input HTTP Version and save to string variable 
        // TODO: Ask user to input User-Agent and save to string variable 

        // TODO: From user input, construct ONE HTTP request message and send it to the HTTP server program over the TCP connection. 
        // TODO: HTTP request message should have the following lines with a “\r\n” at the end of each line:
        /*  The request line (hint: the URL should include a ‘/’ in front of the htm file name) The header line for the field “Host:”
         *  The header line for the field “User-Agent:”
         *  <empty line>
         */

        // TODO: Receive and interpret the HTTP response message from the HTTP Server program line by line
        // TODO: Display the RTT (File Transmission Time may be included) of HTTP query in ms as a single line (e.g., RTT = 1.089 ms)
        // TODO: Display the status line and header lines of the HTTP response message on the standard output
        // TODO: Save the data in the entity body to a .htm file to local directory if there is any.

        // TODO: Display a message on the standard output to ask the User whether to continue. If yes, repeat steps 3 through 6. Otherwise, close all i/o streams, TCP connection, and terminate the Client program.
        System.out.println("This is the Client");
    }

}