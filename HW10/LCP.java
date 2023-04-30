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
 * N' = {V0}; Y' = empty set;
 * for all nodes i 
 *      if i adjacent to V0 
 *          then D(i) = c(V0,i), p(i) = V0
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

package HW10; //TODO: remove before adding to server to turn in 

import java.io.*;
import java.util.*;

public class LCP {
    private static final int MIN_NODES = 2;
    private static final File FILE_NAME = new File("HW10/topo.txt"); // TODO: turnin path - "topo.txt" mac/linux path- HW10/topo.txt windows path- HW10\\topo.txt 
    private static Scanner sc = new Scanner(System.in);
    private static int cost[][];
    ArrayList<Integer> n_prime; 
    ArrayList<String[]> y_prime; 
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

    

    // Method initializes all data structures for Dijkstra's Algorithm 
    private void initialize() throws IOException {

        // reads topo.txt and updates the cost matrix to have the cost values associated with the links in the file
        FileReader fr = new FileReader(FILE_NAME);
        BufferedReader br = new BufferedReader(fr); 
        String line = null; 
        String[] tmp;  
        int x,y,c; 
        int errorLine = 1; 
        try {
            while((line = br.readLine())!=null){
                tmp = line.split("\t");
                x = Integer.parseInt(tmp[0].trim());
                y = Integer.parseInt(tmp[1].trim());
                c = Integer.parseInt(tmp[2].trim());
                
                // TODO: NOT WORKING PROPERLY Check if the cost is negative - alert user what error the line was on and start process over. 
                if (c < 0) {
                    System.out.println("Error: Negative cost value found on line " + errorLine + ". Please fix the input file and try again.");
                    br.close();
                    main(null); // Call the main method to restart the process
                    return; // Terminate the current initialize() method
                }
                cost[x][y] = c;
                cost[y][x] = c; 
                errorLine++;
            }
            // printCostMatrix(); // remove before turning in 
            
            // Initialize N' to have only V0
            n_prime = new ArrayList<>();
            n_prime.add(0);

            //Initialize Y' to be an empty set
            y_prime = new ArrayList<>();

            // Initialize D with the first row of cost matrix as starting cost values 
            for(int i=0; i <D.length; i++){
                D[i] = cost[0][i];
            }
    
            // Initialize p with predecessor values - aka if c(V0,i) is not infinity then p(i) = V0
            for(int i=0; i <p.length; i++){
                if(cost[0][i] == 0 || cost[0][i] == Integer.MAX_VALUE){
                    p[i] = "-";
                } else {
                    p[i] = "V0"; 
                    
                }
                
            }
            // Print the initialization results
            System.out.println("Initialization Results");
            printLCPResults();
        } catch (IOException e) {
            e.printStackTrace();
        } finally{ 
            br.close();
        }
    }

    // Method prints cost matrix -- not needed for submission but helpful for testing 
    private void printCostMatrix() {
        for (int i = 0; i < cost.length; i++) {
            for (int j = 0; j < cost.length; j++) {
                System.out.print(cost[i][j] + " ");
            }
            System.out.println();
        }
    }

    

    // As the intermediate results, at the end of Initialization and each iteration of the Loop, display the set N', the set Y', vector D and vector p 
    private void printLCPResults(){
        // Print set N' 
        System.out.print("The set N': [");
        for (int i = 0; i < n_prime.size(); i++) {
            System.out.print("V" + n_prime.get(i));
            if (i < n_prime.size() - 1) {
                System.out.print(", ");
            }
        }
        System.out.println("]");

        // Print set Y'
        System.out.print("The set Y': [");
        for (int i = 0; i < y_prime.size(); i++) {
            String[] edge = y_prime.get(i);
            System.out.print(Arrays.toString(edge));
            if (i < y_prime.size() - 1) {
                System.out.print(", ");
            }
        }
        System.out.println("]");

        // Print the least cost path array D
        System.out.println("Distance vector D: " + Arrays.toString(D));

        // Prints the predecessor node array p
        System.out.println("Predecessor vector p: " + Arrays.toString(p) + "\n");

    }


    // Helper method to find k not in N' such that D(k) is a minimum
    private int findMinIndex(int[] D, ArrayList<Integer> n_prime) {
        int minIndex = -1;
        int minValue = Integer.MAX_VALUE;
    
        for (int i = 0; i < D.length; i++) {
            if (!n_prime.contains(i) && D[i] < minValue) {
                minValue = D[i];
                minIndex = i;
            }
        }
    
        return minIndex;
    }
    
    // Method for main algorithm logic 
    public void run(int n){
        
        int iteration = 0;
        // Loop through nodes and until all nodes are in N' update data structures 
        while (n_prime.size() < n){
            
            // update the iteration count
            iteration++;
            // find k not in N' such that D(k) is a minimum
            int k = findMinIndex(D, n_prime); // index of minimum node at each iteration
            
            // add node k to N'
            n_prime.add(k);
            // add edge (p(k), k) to Y' 
            String[] edge = {p[k], "V"+Integer.toString(k)};
            y_prime.add(edge);
            
            // Update D(i) and p(i) for all nodes adjacent to k and not in N'
            for (int i = 0; i < cost[k].length; i++) {
                // Check if node i is adjacent to k and not in N'
                if ((cost[k][i] != 0 && cost[k][i] != Integer.MAX_VALUE) && !n_prime.contains(i)) {
                    // If D(k) + c(k,i) < D(i), update D(i) and p(i)
                    if (D[k] + cost[k][i] < D[i]) {
                        D[i] = D[k] + cost[k][i];
                        p[i] = "V" + Integer.toString(k);
                    }
                }
            }
            
            // print loop results 
            System.out.println("Intermediate results for iteration " + iteration);
            printLCPResults();
        }

    }
    
    
    

    // Method to print the forwarding table and Display in the required format - based on pseudo code from slide 57
     public void printForwardingTable() {
        System.out.println("Forwarding Table:");
        System.out.println("Destination\tLink");
    
        // loop through predecessor array p
        for (int i = 0; i < p.length; i++) {
            // i is in N' and i is not the source node V0
            if (i != 0 && n_prime.contains(i)) { 
                int j = i;
                // Continue until p(j) is equal to V0
                while (!p[j].equals("V0")) { 
                    // Get the integer part of the predecessor node's string representation
                    j = Integer.parseInt(p[j].substring(1)); 
                }
                
                String destination = "V" + i;
                String link = "(V0, V" + j + ")";
                System.out.println(destination + "\t\t" + link);
            }
        }
    }

     
    public static void main(String[] args) throws IOException {

        // Get user input for number of nodes - validate that user input is at least MIN_NODES

        System.out.println("How many routers are in your network? (2 minimum): ");
        int nodeSize = sc.nextInt();

        LCP lcp = new LCP(nodeSize);
        lcp.run(nodeSize);
        lcp.printForwardingTable();
    }

}
