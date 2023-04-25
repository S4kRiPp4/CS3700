package HW10;

import java.io.*;
import java.util.*;

public class LCP {
    private static final int MIN_NODES = 2;
    private static final File FILE_NAME = new File("HW10\\topo.txt");
    private static Scanner sc = new Scanner(System.in);
    private static int cost[][];
    ArrayList<Integer> n_prime = new ArrayList<>(); // data type? 
    ArrayList<Integer> y_prime;
    private static int[] D; 
    private static int[] p;
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
            p = new int[n];
            n_prime.add(0); // adding the first node 
            initCostMatrix();
            
        }

    }

    // Method reads topo.txt and updates the cost matrix to have the cost values
    // associated with the links in the file
    private void initCostMatrix() throws IOException {
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
                cost[x][y] = c;
                cost[y][x] = c; 
            }
            printCostMatrix();

            for(int i=0; i <D.length; i++){
                D[i] = cost[0][i];
            }
            printD();
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

    private void printD(){
        for(int i= 0; i< D.length; i++){
            System.out.print(D[i] + " ");
        }
    }

    public static void main(String[] args) throws IOException {

        // Get user input for number of nodes - validate that user input is at least
        // MIN_NODES

        System.out.println("How many routers are in your network? (2 minimum): ");
        int nodeSize = sc.nextInt();

        LCP lcp = new LCP(nodeSize);

    }

}
