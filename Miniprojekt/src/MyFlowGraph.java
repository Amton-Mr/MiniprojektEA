import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import java.util.HashMap;
import java.util.Set;

public class MyFlowGraph implements  Graph{

    HashMap<Integer, HashMap<Integer, Integer>> adjacencies = new HashMap<>();

    public MyFlowGraph(String filename) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(filename));
        String line = null;
        while ((line = reader.readLine()) != null) {
            System.out.println(line);
        }
    }
    @Override
    public void addVertex(Integer v) {

    }

    @Override
    public void addEdge(Integer v, Integer w) {

    }

    @Override
    public void deleteVertex(Integer v) {

    }

    @Override
    public void deleteEdge(Integer u, Integer v) {

    }

    @Override
    public boolean contains(Integer v) {
        return false;
    }

    @Override
    public int degree(Integer v) {
        return 0;
    }

    @Override
    public boolean adjacent(Integer v, Integer w) {
        return false;
    }

    @Override
    public Graph getCopy() {
        return null;
    }

    @Override
    public Set<Integer> getNeighbors(Integer v) {
        return null;
    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    public int getEdgeCount() {
        return 0;
    }

    @Override
    public Set<Integer> getVertices() {
        return null;
    }


}
