package Projeto2.Algorithm;


import Projeto2.Entities.Cruzamento;
import com.brunomnsilva.smartgraph.graph.Graph;
import com.brunomnsilva.smartgraph.graph.GraphEdgeList;
import com.brunomnsilva.smartgraph.graph.Vertex;

public class BeamSearch {

    private int beamWidth;
    private Graph<Cruzamento, Integer> cruzamentos;

    private int numberOfAvenidas; // NORTE-SUL
    private int numberOfRuas; // ESTE-OESTE

    public BeamSearch(int beamWidth) {
        this.beamWidth = beamWidth;

    }

    public int[] Find_Solution_Citizen_BS (int beamWidth, Graph<Cruzamento, Integer> cruzamentos) {
        return null;
    }
}
