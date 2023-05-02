package HW10;

import java.io.*;
import java.util.*;
import java.util.ArrayList;

public class TMP {
    private static final int MIN_NODES = 2;
    private static final File FILE_NAME = new File("HW10/topo.txt"); // TODO: turnin path - "topo.txt" mac/linux path-
                                                                     // HW10/topo.txt windows path- HW10\\topo.txt
    private static Scanner sc = new Scanner(System.in);
    private static int cost[][];
    ArrayList<Integer> n_prime;
    ArrayList<String[]> y_prime;
    private static int[] D;
    private static String[] p;
    public static void main(String[] args) throws IOException {
        System.out.println("How many routers are in your network? (2 minimum): ");
        int nodeSize = sc.nextInt();
        
        // reads topo.txt and updates the cost matrix to have the cost values associated
        // with the links in the file
        FileReader fr = new FileReader(FILE_NAME);
        BufferedReader br = new BufferedReader(fr);
        String line = null;
        String[] tmp;
        int x, y, c;
        int errorLine = 1;
        Boolean validated = false

    }
    
}
