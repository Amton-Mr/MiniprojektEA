import java.util.ArrayList;
import java.util.LinkedList;
import java.util.HashMap;
import java.util.List;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.File;
import java.util.Queue;

public class MaxFlowOpenAI {
    // Number of vertices in the graph
    private int numVertices;
    // Graph represented as an adjacency list
    private List<List<Edge>> adjacencyList;

    // Constructor
    public MaxFlowOpenAI(int numVertices) {
        this.numVertices = numVertices;
        this.adjacencyList = new ArrayList<>(numVertices);
        for (int i = 0; i < numVertices; i++) {
            this.adjacencyList.add(new ArrayList<>());
        }
    }

    // Add an edge to the graph
    public void addEdge(int from, int to, int capacity) {
        Edge forward = new Edge(from, to, capacity);
        Edge backward = new Edge(to, from, 0);
        forward.backward = backward;
        backward.backward = forward;
        adjacencyList.get(from).add(forward);
        adjacencyList.get(to).add(backward);
    }

    // Find the maximum flow from the source to the sink
    public int maximumFlow(int source, int sink) {
        int maxFlow = 0;

        // Edmonds-Karp algorithm
        while (true) {
            // Breadth-first search to find an augmenting path
            Queue<Integer> queue = new LinkedList<>();
            queue.offer(source);
            int[] predecessors = new int[numVertices];
            Edge[] predecessorEdge = new Edge[numVertices];
            for (int i = 0; i < numVertices; i++) {
                predecessors[i] = -1;
            }
            predecessors[source] = source;

            while (!queue.isEmpty() && predecessors[sink] == -1) {
                int u = queue.poll();
                for (Edge edge : adjacencyList.get(u)) {
                    int v = edge.to;
                    if (predecessors[v] == -1 && edge.remainingCapacity() > 0) {
                        queue.offer(v);
                        predecessors[v] = u;
			predecessorEdge[v] = edge;
                    }
                }
            }

            // If there is no augmenting path, we're done
            if (predecessors[sink] == -1) {
                break;
            }

            // Compute the maximum flow along the augmenting path
            int pathFlow = Integer.MAX_VALUE;
            for (int v = sink; v != source; v = predecessors[v]) {
                int u = predecessors[v];
                pathFlow = Math.min(pathFlow, predecessorEdge[v].remainingCapacity());
            }

            // Update the flow along the augmenting path
            for (int v = sink; v != source; v = predecessors[v]) {
                int u = predecessors[v];
		predecessorEdge[v].addFlow(pathFlow);
            }

            // Add the flow to the maximum flow
            maxFlow += pathFlow;
        }

        return maxFlow;
    }
    // Inner class representing an edge in the graph
    private static class Edge {
        int from, to;
        int capacity, flow;
        Edge backward; // The backward edge corresponding to this edge

        public Edge(int from, int to, int capacity) {
            this.from = from;
            this.to = to;
            this.capacity = capacity;
            this.flow = 0;
        }

        // Calculate the remaining capacity of this edge
        public int remainingCapacity() {
            return capacity - flow;
        }

        // Add flow to this edge
        public void addFlow(int flow) {
            this.flow += flow;
            this.backward.flow -= flow;
        }
    }

    // Test the Ford Fulkerson algorithm
    public static void main(String[] args) {
        
	HashMap<String,Integer> nameId =  new HashMap<String,Integer>();
	List<Integer> from = new ArrayList<Integer>();
	List<Integer> to = new ArrayList<Integer>();
	List<Integer> capacity = new ArrayList<Integer>();
	int id = 0;
	String source = "";
	String sink = "";
	try{
	    BufferedReader read =  new BufferedReader(new FileReader(new File(args[0])));
	    source = read.readLine().trim();
	    nameId.put(source,id++);
	    sink = read.readLine().trim();
	    nameId.put(sink,id++);
	    while (read.ready()){
		String line = read.readLine();
		String[] fields =  line.split(" ");
		if (! nameId.containsKey(fields[0])){
		    nameId.put(fields[0],id++);
		}
		if (! nameId.containsKey(fields[1])){
		    nameId.put(fields[1],id++);
		}
		from.add(nameId.get(fields[0]));
		to.add(nameId.get(fields[1]));
		capacity.add(Integer.parseInt(fields[2]));
	    }
	}
	catch (Exception e) {
	    e.printStackTrace();
	}
	// Create a graph with 6 vertices and 9 edges
        MaxFlowOpenAI graph = new MaxFlowOpenAI(id);
        for (int i=0;i<from.size();i++){
	    graph.addEdge(from.get(i),to.get(i),capacity.get(i));
	}
        // Compute the maximum flow from the source (0) to the sink (5)
        int maxFlow = graph.maximumFlow(nameId.get(source), nameId.get(sink));
        System.out.println("The maximum flow is: " + maxFlow); // Should print 23
    }
}
