import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class MyGraph implements Graph{

    HashMap<Integer, HashSet<Integer>> vertices;


    public MyGraph (String filename){
        vertices = new HashMap<>();
        BufferedReader reader;

        try{
            reader = new BufferedReader(new FileReader(filename));
            String line = reader.readLine();
            while(line != null){
                Integer vertex1 = Integer.valueOf(line.split(" ")[0]);
                Integer vertex2 = Integer.valueOf(line.split(" ")[1]);
                HashSet<Integer> neighbors = new HashSet<>();

                if(!vertices.containsKey(vertex1)){
                    vertices.put(vertex1, new HashSet<>());
                    vertices.get(vertex1).add(vertex2);

                }else{
                    vertices.get(vertex1).add(vertex2);
                }
                if(!vertices.containsKey(vertex2)){
                    vertices.put(vertex2, new HashSet<>());
                    vertices.get(vertex2).add(vertex1);

                }else {
                    vertices.get(vertex2).add(vertex1);
                }

                line = reader.readLine();
            }
        }
        catch(IOException e){
            e.printStackTrace();
        }


    }

    public MyGraph (HashMap<Integer, HashSet<Integer>> vertices){
        this.vertices = vertices;
    }

    @Override
    public void addVertex(Integer v) {
        if(!this.vertices.containsKey(v)){
            this.vertices.put(v, new HashSet<>());
        }
    }

    @Override
    public void addEdge(Integer v, Integer w) {
        if(!this.vertices.containsKey(v)){
            this.vertices.put(v,new HashSet<>());
        }
        this.vertices.get(v).add(w);
        if(!this.vertices.containsKey(w)){
            this.vertices.put(w,new HashSet<>());
        }
        this.vertices.get(w).add(v);
    }

    @Override
    public void deleteVertex(Integer v) {
        if(this.vertices.containsKey(v)){
            this.vertices.remove(v);
        }

    }

    @Override
    public void deleteEdge(Integer u, Integer v) {
        this.vertices.get(u).remove(v);
        this.vertices.get(v).remove(u);
    }

    public boolean containsEdge(Integer u, Integer v){
       return this.vertices.get(u).contains(v);
    }

    @Override
    public boolean contains(Integer v) {
        return this.vertices.containsKey(v);
    }

    @Override
    public int degree(Integer v) {
        return this.vertices.get(v).size();
    }

    @Override
    public boolean adjacent(Integer v, Integer w) {
        return this.vertices.get(v).contains(w);
    }

    @Override
    public Graph getCopy() {
        HashMap<Integer, HashSet<Integer>> newMap = new HashMap<>();
        for (Integer vertex: this.vertices.keySet()) {
            newMap.put(new Integer(vertex.intValue()), new HashSet<>((Collection) this.vertices.get(vertex).clone()));
        }
        return new MyGraph(newMap);
    }

    @Override
    public Set<Integer> getNeighbors(Integer v) {
        return this.vertices.get(v);
    }

    @Override
    public int size() {
        return this.vertices.size();
    }

    @Override
    public int getEdgeCount() {
        int edgeCounter = 0;
        for (Integer vertex: this.vertices.keySet()) {
            edgeCounter += this.vertices.get(vertex).size();
        }
        return edgeCounter/2;
    }

    @Override
    public Set<Integer> getVertices() {
        return this.vertices.keySet();
    }
}
