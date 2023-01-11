import java.io.IOException;
import java.util.HashMap;

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
        System.out.println("Pro Runde sieht eine effektive Verteilung der Geschenke mit Vermeidung von L2 so aus:\n" +
                G.getVertexName(40) +" "+ flow.get(40) + "\n" +
                G.getVertexName(70) +" "+ flow.get(70) + "\n" +
                G.getVertexName(104) +" "+ flow.get(104) + "\n" +
                G.getVertexName(1) +" "+ flow.get(1) + "\n" +
                G.getVertexName(30) +" "+ flow.get(30) + "\n" +
                "Damit können " +sum+" Geschenke pro Runde befördert werden, was heißt das " + resets +
                " Resets gebraucht werden.\nDamit ergibt sich folgende Gesamtverteilung der Geschenke zum Start:\n"+
                G.getVertexName(40) +" "+ Math.round(flow.get(40)*factor) + "\n" +
                G.getVertexName(70) +" "+ Math.round(flow.get(70)*factor) + "\n" +
                G.getVertexName(104) +" "+ Math.round(flow.get(104)*factor) + "\n" +
                G.getVertexName(1) +" "+ Math.round(flow.get(1)*factor) + "\n" +
                G.getVertexName(30) +" "+ Math.round(flow.get(30)*factor) + "\n" );
    }

}
