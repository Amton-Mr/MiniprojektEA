import java.io.IOException;
import java.util.*;

public class MainFile {

    public static void main(String[] args) throws IOException {
        MyGraph G = new MyGraph("/Users/samu/Documents/Roehrentransportsystem.csv");
        FordFulkerson algo = new FordFulkerson(G);
        HashMap<Integer, Integer> flow = algo.computeMaxFlow();
        float sum = 0.0f;
        for (float f: flow.values()) {
            sum += f;
        }
        int resets = (int) Math.ceil(1000/sum);
        float factor = 1000/sum;
        System.out.println("Pro Runde sieht eine effektive Verteilung der Geschenke mit Vermeidung von L2 so aus:");
        for (Integer name: G.storageNames.keySet()) {
            System.out.println(G.getVertexName(name) +" "+ flow.get(name));
        }
        System.out.println("Damit können " +sum+" Geschenke pro Runde befördert werden, was heißt das " + resets +
                " Resets gebraucht werden.\nDamit ergibt sich folgende Gesamtverteilung der Geschenke zum Start:");
        for (Integer name: G.storageNames.keySet()) {
            System.out.println(G.getVertexName(name) +" "+ Math.round(flow.get(name)*factor));
        }
    }

}
