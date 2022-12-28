import java.io.IOException;

public class MainFile {

    public static void main(String[] args) throws IOException {
        MyGraph G = new MyGraph("/Users/samu/Documents/Roehrentransportsystem.csv");
        System.out.println(G.adjacency);
        FordFulkerson algo = new FordFulkerson(G);
        algo.computeMaxFlow();
    }

}
