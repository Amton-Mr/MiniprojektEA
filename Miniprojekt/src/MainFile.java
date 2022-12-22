import java.io.IOException;
import java.util.HashMap;

public class MainFile {

    public static void main(String[] args) throws IOException {
        MyGraph test = new MyGraph("/home/antonthiel/Miniprojekt/Miniprojekt/Data/Roehrentransportsystem.csv");

        HashMap<Integer, HashMap<Integer, Integer>> testMap = new HashMap<>();

        System.out.println(test.vertexNames);


    }

}
