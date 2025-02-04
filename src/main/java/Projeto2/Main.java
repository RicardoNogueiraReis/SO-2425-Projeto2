package Projeto2;

import Projeto2.Entities.*;

import java.io.IOException;
import java.util.ArrayList;

import com.brunomnsilva.smartgraph.graph.Edge;
import com.brunomnsilva.smartgraph.graph.Graph;
import com.brunomnsilva.smartgraph.graph.Vertex;
import com.brunomnsilva.smartgraph.graph.GraphEdgeList;

public class Main {
    public static void main(String[] args) throws IOException {


        /* Leitura de parâmetros */
        /* +++++++++++++++++++++ */

        Parameters params = new Parameters();

        try {
            params.setNomeFicheiro(args[0]);
            params.setLarguraFeixe(args[1]);

            params.setNumThreads(args[2]);
            params.setMilisegundos(args[3]);

            params.setIteracoesThread(args[4]);

        } catch (IOException e) {
            System.out.println(e.getMessage());

        }

        System.out.println(params);

        /* Leitura de ficheiros */
        /* ++++++++++++++++++++ */

        ArrayList<Supermercado> supermercados = new ArrayList<>();
        ArrayList<Cidadao> cidadaos = new ArrayList<>();

        try {
            DataReader dataReader = new DataReader("instances_2.txt", supermercados, cidadaos);

        } catch (IOException e) {
            System.out.println(e.getMessage());
            return;

        }


        /* Resultados da leitura de ficheiros */
        /* ++++++++++++++++++++++++++++++++++ */

        for (Supermercado supermercado : supermercados) {
            System.out.println(supermercado);

        }

        System.out.println("");

        for (Cidadao cidadao : cidadaos) {
            System.out.println(cidadao);
        }

    }
}