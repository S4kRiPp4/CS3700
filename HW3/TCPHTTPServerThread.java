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


public class TCPHTTPServerThread extends Thread{
    private Socket clientTCPSocket = null; 
    

    // constructor 
    public TCPHTTPServerThread(Socket socket){
        clientTCPSocket = socket; 
    }


    // method to handle each thread for every client 
    public void run(){
        try{
            // output socket back to the client 
            PrintWriter cSocketOut = new PrintWriter(clientTCPSocket.getOutputStream(), true); 

            // input socket from the client 
            BufferedReader cSocketIn = new BufferedReader(new InputStreamReader(clientTCPSocket.getInputStream()));

            // variables to display/send text to and from client
            String fromClient, toClient, msg; 

            // Read requests and send responses until a null is read (happens when a particular client closesthe TCP connection)
            while((fromClient = cSocketIn.readLine()) != null){
                String reqMethod, httpVersion, status, timeStamp, server, data;
                String[] userReq; 
                String[] reqLine;
                File file; 
                DateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
                Date date = new Date();
                timeStamp = "Date: " + df.format(date) +"\r\n";
                server = "Server: msudenver.edu\r\n";
                data = "\r\n\r\n\r\n\r\n";
                // TODO: Read, display to the standard output, and interpret incoming HTTP request message line by line
                /*
                * If the method given in the request line is NOT “GET”, it is a “400 Bad Request” case
                * If the file specified in the URL does not exit/cannot be open, it is a “404 Not Found” case
                * Otherwise, it is a “200 OK” case
                */     

                msg  = fromClient + "\r\n" + cSocketIn.readLine() + "\r\n" + cSocketIn.readLine() + "\r\n" + cSocketIn.readLine() +"\r\n";
                userReq = msg.split("\n");
                reqLine = userReq[0].split("\s");
                reqMethod = reqLine[0];
                file = new File (reqLine[1].toString());
                httpVersion = reqLine[2].trim();
            

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

                if(reqMethod.equals("GET") && (file.exists() && file.canRead())){
                    status = " 200 OK\r\n";
                    toClient = httpVersion + status + timeStamp + server + data; 
                    System.out.println(toClient);
                    cSocketOut.print(toClient); 
                    cSocketOut.flush();
                } else if (!reqMethod.equals("GET")){
                    status = "400 Bad Request\r\n";
                    toClient = httpVersion + status + timeStamp + server; 
                    System.out.println(toClient);
                    cSocketOut.print(toClient); 
                    cSocketOut.flush();
                } else if (!(file.exists() && file.canRead())){
                    status = "404 Not Found\r\n";
                    toClient = httpVersion + status + timeStamp + server; 
                    System.out.println(toClient);
                    cSocketOut.print(toClient); 
                    cSocketOut.flush();
                }

                // if the incoming message from the client is N for no, proceed to closure of the thread 
                if (fromClient.equals("N"))
                    System.out.println("this client is done.");
                    break;
            }
            
            // Close i/o streams and the TCP socket for the specific Client, and terminate the thread for the specific client. 
            // Hint: when this happens, the parent thread is still alive doing Steps 1 and 2 forever, unless the Server process is killed/terminated. 
            cSocketOut.close();
            cSocketIn.close();
            clientTCPSocket.close();
        } catch(Exception e){
            e.printStackTrace();
        }
    }
}