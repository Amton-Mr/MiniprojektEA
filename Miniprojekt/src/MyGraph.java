import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;


public class MyGraph {

    HashSet<Integer> nodes;				  // set of vertices of the graph
    HashMap<Integer, HashMap<Integer,Integer>> adjacency; // sparse weighted adjacency matrix of the graph
    HashMap<Integer, String> vertexNames;

    String[] indices;

    Integer s;

    Integer t;


    public MyGraph (String filename){
        this.s = Integer.MAX_VALUE;
        nodes = new HashSet<Integer>();
        adjacency = new HashMap<Integer, HashMap<Integer,Integer>>();
        HashMap<String, Integer> seenIds = new HashMap<String, Integer>();	// only required during construction
        vertexNames = new HashMap<Integer, String>();
        BufferedReader reader;
        int newID;
        adjacency.put(s, new HashMap<>());
        seenIds.put("s", this.s);
        vertexNames.put(this.s , "s");

        try{

            reader = new BufferedReader(new FileReader(filename));
            String line = reader.readLine();
            String [] vertices = line.split(";");

            for (int i = 1; i < vertices.length-1; i++) {
                vertexNames.put(i,vertices[i]);
                seenIds.put(vertices[i],i);
                adjacency.put(i, new HashMap<>());
            }

            line = reader.readLine();
            while(line != null){
                int i = 1; //aktueller Knoten
                String[] neighbors = line.split(";");
                newID = i; //wenn Knoten Kapazität hat wird er gesplittet

                String lastEntry = neighbors[neighbors.length-1];

                if(lastEntry.contains("Kapazitaet")){
                    System.out.println(lastEntry.substring(12));
                    int vertexCapacity = Integer.parseInt(lastEntry.substring(12));
                    newID = vertexNames.size()+1;
                    this.addVertex(newID);
                    this.addEdge(i,newID, vertexCapacity);
                }
                for(int j = 1; j < neighbors.length-1; j++){
                    if(neighbors[j]!= "0" || !neighbors[j].isEmpty() || neighbors[j] != null){
                        this.addEdge(newID,j, Integer.getInteger(neighbors[j]));
                    }
                }
                line = reader.readLine();
                i++;
            }
            this.t = seenIds.get("Z");
            for (String vertex: vertexNames.values()
                 ) {
                if(vertex.contains("L")){
                    this.addEdge(this.s,seenIds.get(vertex),1000);
                }
            }
            System.out.println(this.t);
        }
        catch(IOException e){
            e.printStackTrace();
        }


    }

    public MyGraph (HashMap<Integer, HashMap<Integer,Integer>> vertices){
        this.adjacency = vertices;
    }

    public MyGraph () {
        nodes = new HashSet<Integer>();
        adjacency = new HashMap<Integer, HashMap<Integer,Integer>>();
        vertexNames = new HashMap<Integer,String>();
    }

    public void addVertex (Integer v) {
        nodes.add(v);
        vertexNames.put(v,v.toString());
        adjacency.put(v, new HashMap<Integer,Integer>());
    }

    public void addVertex (Integer v, String name) {
        nodes.add(v);
        vertexNames.put(v,name);
        adjacency.put(v, new HashMap<Integer,Integer>());
    }


    public void addEdge (Integer v, Integer w, Integer l) {
        if (v == w) return;		// No loops!
        adjacency.get(v).put(w,l);
    }

    public int size () {
        return nodes.size();
    }

    /** Returns whether the given vertex ID belongs to the graph. */
    public boolean contains (Integer v) {
        return nodes.contains(v);
    }

    public int degree (Integer v) {
        return adjacency.get(v).size();
    }

    /** Returns whether vertices v and w are adjacent. */
    public boolean adjacent (Integer v, Integer w) {
        return adjacency.get(v).containsKey(w);
    }

    public HashSet<Integer> getVertices () {
        return nodes;
    }

    public int getEdgeCount () { /** Lege private Variable an */
        int edges = 0;
        for (int v : nodes)
            edges += adjacency.get(v).size();
        edges /= 2;
        return edges;
    }


    public Set<Integer> getNeighbors (Integer v) {
        return adjacency.get(v).keySet();
    }


    public int capacity (Integer v, Integer w) {
        if (adjacency.get(v).containsKey(w)){
            return adjacency.get(v).get(w);
        }
        else return Integer.MAX_VALUE;
    }

    public void setCapacity (Integer v, Integer w, Integer c) {
        if (adjacency.get(v).containsKey(w)){
            adjacency.get(v).put(w,c);
        }
    }


    public void deleteVertex (Integer vertex) {
        for (int neighbor : adjacency.get(vertex).keySet())
            adjacency.get(neighbor).remove(vertex);
        nodes.remove(vertex);
        vertexNames.remove(vertex);
    }

    public void deleteEdge (Integer u, Integer v){
        adjacency.get(u).remove(v);
    }
    public String getVertexName (Integer i) {
        return vertexNames.get(i);
    }

    public void printNetwork(){
        //System.out.println(s.toString());
        //System.out.println(t.toString());
        for (Integer v : this.nodes){
            for (Integer u: this.getNeighbors(v)){
                System.out.println(v.toString()+" "+u.toString()+" "+Float.toString(this.capacity(v,u)));
            }
        }
    }
    }
