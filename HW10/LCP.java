/*  
 * CS3700 - Networking and Distributed Computing - Spring 2023
 * Instructor: Dr. Weiying Zhu
 * Programmers: Alyssa Williams & Jesse Johnstone 
 * Description: HW10 - Dijkstra’s Algorithm & Forwarding Table
*/

/*  
 * Dijkstgra's Algorithm Pseudocode
 * 
 * Initialization:
 * N' = null; Y' = empty set;
 * for all nodes i 
 *      if i adjacent to u 
 *          then D(i) = c(u,i), p(i) = u
 *      else D(i) = infinity 
 * 
 * Main Algorithm: 
 * LOOP
 * find k not in N' such that D(k) is a minimum
 * add node k to N'
 * add edge (p(k), k) to Y' 
 * update D(i) and p(i) for all adjacent to k and not in N'
 *      if D(k) + c(k,i) < D(i)
 *          then D(i) = D(k) + c(k,i) and p(i) = k
 * UNITL ALL NODES IN N'
 */

package HW10;

import java.io.*;
import java.util.*;

public class LCP {
    private static final int MIN_NODES = 2;
    private static final File FILE_NAME = new File("HW10\\topo.txt");
    private static Scanner sc = new Scanner(System.in);
    private static int cost[][];
    ArrayList<Integer> n_prime = new ArrayList<>(); // data type? 
    ArrayList<Integer[]> y_prime;
    private static int[] D; 
    private static String[] p;

    // constructor to initialize cost matrix with infinity and 0s on the diagonal
    public LCP(int n) throws IOException {
        if (n < MIN_NODES) {
            System.out.println("There must be at least 2 routers!");
            System.exit(-1);
        } else {
            cost = new int[n][n];
            for (int i = 0; i < cost.length; i++) {
                for (int j = 0; j < cost.length; j++) {
                    if (i == j) {
                        cost[i][j] = 0;
                    } else {
                        cost[i][j] = Integer.MAX_VALUE;
                    }
                }
            }
            D = new int[n]; 
            p = new String[n];
            initialize();
            
        }

    }

    

    // Method initializes all data structures for Dijkstra's ALgorithm 
    private void initialize() throws IOException {

        // reads topo.txt and updates the cost matrix to have the cost values associated with the links in the file
        FileReader fr = new FileReader(FILE_NAME);
        BufferedReader br = new BufferedReader(fr); 
        String line = null; 
        String[] tmp;  
        int x,y,c; 
        try {
            while((line = br.readLine())!=null){
                tmp = line.split("\t");
                x = Integer.parseInt(tmp[0].trim());
                y = Integer.parseInt(tmp[1].trim());
                c = Integer.parseInt(tmp[2].trim());
                // TODO: add error handling for if cost given is < 0. Need to alert user what error the line is on and re-ask user for nodes and start process over 
                cost[x][y] = c;
                cost[y][x] = c; 
            }
            printCostMatrix(); // TODO: remove before turning in 
            // TODO: Initialize N' to have only V0
            n_prime.add(cost.length - (cost.length));
            // TODO: Initialize Y' to be an empty set
            y_prime = new ArrayList<>();
            // Initialize D with the first row of cost matrix as starting cost values 
            for(int i=0; i <D.length; i++){
                D[i] = cost[0][i];
            }
    

            // Initialize p with predecessor values - aka if c(u,i) is not infinity then p(i) = u
            for(int i=0; i <p.length; i++){
                if(cost[0][i] == 0 || cost[0][i] == Integer.MAX_VALUE){
                    p[i] = "-";
                } else {
                    p[i] = Integer.toString(0); 
                    
                }
                
            }
            // Print the initialization results
            printLCPResults();
        } catch (IOException e) {
            e.printStackTrace();
        } finally{ 
            br.close();
        }
    }

    // Method prints cost matrix
    private void printCostMatrix() {
        for (int i = 0; i < cost.length; i++) {
            for (int j = 0; j < cost.length; j++) {
                System.out.print(cost[i][j] + " ");
            }
            System.out.println();
        }
    }

    

    // TODO: As the intermediate results, at the end of Initialization and each iteration of the Loop, display the set N', the set Y', vector D and vector p 
    private void printLCPResults(){
        // TODO: Print set N' 
        System.out.println("The set N': " + n_prime.toString());

        // TODO: Print set Y'
        System.out.println("The set Y': " + y_prime.toString());

        // Print the least cost path array D
        System.out.println("Distance vector D: " + Arrays.toString(D));

        // Prints the predecessor node array p
        System.out.println("Predecessor vector p: " + Arrays.toString(p));

    }
    
    /* 
     * TODO: Once least path tree has been identified build up the forwarding table and Display in the format:
     * Destination\t\tLink
     * V0\t\t(V0, ...)
     * V1\t\t(V1, ...)
     * ... 
     * Vn-1\t\t(Vn-1, ...)
     */

     
    public static void main(String[] args) throws IOException {

        // Get user input for number of nodes - validate that user input is at least
        // MIN_NODES

        System.out.println("How many routers are in your network? (2 minimum): ");
        int nodeSize = sc.nextInt();

        LCP lcp = new LCP(nodeSize);

    }

}
