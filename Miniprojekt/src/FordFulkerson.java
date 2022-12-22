import java.util.*;

public class FordFulkerson{
    MyGraph g;
    private HashMap<Integer, HashMap<Integer,Integer>> flow; // current flow values
    private LinkedList<Integer> P; //augmenting path
    private Integer b; //value of augmentation
    public FordFulkerson(MyGraph g){
	this.g = g;
    }

    public float computeMaxFlow(){
	//g.printNetwork();

	float val = 0.0f;
	//initialize flow
	flow = new HashMap<Integer, HashMap<Integer,Integer>>();
	for (Integer v: g.getVertices()){
	    flow.put(v,new HashMap<Integer,Integer>());
	    for (Integer u: g.getNeighbors(v)){
		flow.get(v).put(u,0);
	    }
	}
	while (augment()){
	    val = val+b;
	    System.out.println("Current flow value "+val);
	}
	/*
	HashMap<Integer,Float> outflow = new HashMap<Integer,Float>();
	HashMap<Integer,Float> inflow = new HashMap<Integer,Float>();
	for (Integer v: g.getVertices()){
	    outflow.put(v,0.0f);
	    inflow.put(v,0.0f);
	}
	for (Integer v: g.getVertices()){
	    for (Integer u: flow.get(v).keySet()){
		if( flow.get(v).get(u)>0.0){
		    outflow.put(v,outflow.get(v)+flow.get(v).get(u));
		    inflow.put(u,inflow.get(u)+flow.get(v).get(u));
		    System.out.println(v+" "+u+" "+flow.get(v).get(u));
		}
	    }
	}
	for (Integer v: g.getVertices()){
	    if (!(outflow.get(v).equals(inflow.get(v)))) 
		System.out.println(v+" "+outflow.get(v)+ " "+inflow.get(v));
	}
	*/
	return val;
    }

    private boolean augment(){//returns true if and only if augmenting path has been found
	HashSet <Integer> visited = new HashSet<Integer>();
	P = new LinkedList<Integer>();
	b = Integer.MAX_VALUE;
	if (DFS(g.s,visited)){//residual graph has augmenting path
	    //update flow and residual graph
	    System.out.println("Found augmenting path, increasing flow by "+b);
	    Integer prev = g.s;
	    for (Integer v: P){
		if (v==g.s) continue;
		//if e ∈ E : f (e) ← f (e) + b
		//network edge goes from prev to v
		if (flow.get(prev).containsKey(v)){
		    Integer fe = flow.get(prev).get(v);
		    flow.get(prev).put(v,fe+b);
		    Integer c = g.capacity(prev,v);
		    if (c<=b+0.01){
			//System.out.println("removing edge from residual graph");
			g.deleteEdge(prev,v);
		    }
		    else {
			g.setCapacity(prev,v,c-b);
		    }
		    if (g.adjacent(v,prev)){
			c = g.capacity(v,prev);
			g.setCapacity(v,prev,c+b);
		    }
		    else {
			g.addEdge(v,prev,b);
		    }
		}
		else {//network edge goes from v to prev , so eR ∈ E : f (eR ) ← f (eR ) − b
		    int fe = flow.get(v).get(prev);
		    flow.get(v).put(prev,fe-b);
		    //check if edge from v to prev is in the residual graph
		    if (g.adjacent(v,prev)){
			Integer c = g.capacity(v,prev);
			g.setCapacity(prev,v,c+b);
		    }
		    else {
			g.addEdge(v,prev,b);
		    }
		    //edge from prev to v must be in the residual graph
		    Integer c = g.capacity(prev,v);
		    if (c<=b+0.00001){
			g.deleteEdge(prev,v);
		    }
		    else {
			g.setCapacity(v,prev,c-b);
		    }
		}
		prev=v;
	    }
	    return true;
	}
	else {
	    return false;
	}
	
    }

    private boolean DFS(Integer v, HashSet <Integer> visited){
	if (v==g.t){
	    P.addFirst(v);
	    return true;
	}
	else {
	    visited.add(v);
	    for (Integer u: g.getNeighbors(v)){
		if (!visited.contains(u)){
		    if (DFS(u,visited)){
			P.addFirst(v);
			b = Math.min(b,g.capacity(v,u));
			return true;
		    }
		    
		}
	    }
	    return false;
	}
    }
    
}
