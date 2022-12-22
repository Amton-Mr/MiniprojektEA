import java.io.IOException;
import java.util.HashMap;

public class MainFile {

    public static void main(String[] args) throws IOException {
        MyGraph G = new MyGraph("/home/antonthiel/Miniprojekt/Miniprojekt/Data/Roehrentransportsystem.csv");
        FordFulkerson algo = new FordFulkerson(G);

    }

}
