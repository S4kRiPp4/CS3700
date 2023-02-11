/*  
 * CS3700 - Networking and Distributed Computing - Spring 2023
 * Instructor: Dr. Weiying Zhu
 * Programmers: Alyssa Williams & Jesse Johnstone 
 * Description: HW03 - server program
*/


public class ServerTCPHTTP{
    public static void main (String[] args){

        // TODO: Listen to the given port and wait for a connection request from a HTTP Client
        // TODO: Create a new thread for every incoming TCP connection request from a HTTP client
        // TODO: Read, display to the standard output, and interpret incoming HTTP request message line by line
        /*
         * If the method given in the request line is NOT “GET”, it is a “400 Bad Request” case
         * If the file specified in the URL does not exit/cannot be open, it is a “404 Not Found” case
         * Otherwise, it is a “200 OK” case
         */


         // TODO: Construct ONE HTTP response message and send it to the HTTP client program over the TCP connection based on the cases above.
          /* HTTP Respnse includes the follwing lines with a "\r\n" at the end of each line:
           * 
           * The status line
           * The header line for the field “Date:”
           * The header line for the field “Server: ”, you may use any value of your choice
           * <empty line>
           * Data read from the requested HTML file line by line ... (hint: for the 200 OK case only)
           * <empty line> (**200 OK case ONLY**)
           * <empty line> (**200 OK case ONLY**)
           * <empty line> (**200 OK case ONLY**)
           * <empty line> (**200 OK case ONLY**)
           */

           // TODO: Read requests and send responses until a null is read (happens when a particular client closesthe TCP connection)
           // TODO: Close i/o streams and the TCP socket for the specific Client, and terminate the thread for the specific client. 
           // Hint: when this happens, the parent thread is still alive doing Steps 1 and 2 forever, unless the Server process is killed/terminated. 
        System.out.println("Server works");
    }
}