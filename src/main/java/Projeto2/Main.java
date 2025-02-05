package Projeto2;

import Projeto2.Entities.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

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

        DataReader dataReader;

        try {
            dataReader = new DataReader(params.getNomeFicheiro(), supermercados, cidadaos);

        } catch (IOException e) {
            System.out.println(e.getMessage());
            return;

        }

        Graph<Cruzamento, Integer> cruzamentos = new GraphEdgeList<>();;

        createCruzamentos( cruzamentos, dataReader.getAvenidas(), dataReader.getRuas() );

        for (Vertex<Cruzamento> v : cruzamentos.vertices()) {
            System.out.println(v.toString());
        }

        /*
        long tempoInicio = System.currentTimeMillis();

        long tempoMaximo = params.getMilisegundos();

         */

        /* Resultados da leitura de ficheiros */
        /* ++++++++++++++++++++++++++++++++++ */
        /*
        for (Supermercado supermercado : supermercados) {
            System.out.println(supermercado);

        }

        System.out.println();

        for (Cidadao cidadao : cidadaos) {
            System.out.println(cidadao);

        }
        */

    }

    private static void createCruzamentos(Graph<Cruzamento, Integer> cruzamentos, int numberOfAvenidas, int numberOfRuas) {

        Map<String, Vertex<Cruzamento>> cruzamentoMap = new HashMap<>();


        for (int avenida = 1; avenida <= numberOfAvenidas; avenida++) {
            for (int rua = 1; rua <= numberOfRuas; rua++) {
                Cruzamento cruzamento = new Cruzamento(avenida, rua);
                Vertex<Cruzamento> v = cruzamentos.insertVertex(cruzamento);

                cruzamentoMap.put(avenida + "," + rua, v);
            }
        }

        for (Vertex<Cruzamento> v : cruzamentos.vertices()) {
            Cruzamento node = v.element();
            int av = node.getAvenida();
            int ru = node.getRua();

            // Norte
            if (av > 1) {
                Vertex<Cruzamento> vizinhoNorte = cruzamentoMap.get((av - 1) + "," + ru);
                if (vizinhoNorte != null && cruzamentos.areAdjacent(v, vizinhoNorte)) {
                    cruzamentos.insertEdge(v, vizinhoNorte, 1);
                }
            }

            // Sul
            if (av < numberOfAvenidas) {
                Vertex<Cruzamento> vizinhoSul = cruzamentoMap.get((av + 1) + "," + ru);
                if (vizinhoSul != null && cruzamentos.areAdjacent(v, vizinhoSul)) {
                    cruzamentos.insertEdge(v, vizinhoSul, 1);
                }
            }

            // Este
            if (ru < numberOfRuas) {
                Vertex<Cruzamento> vizinhoEste = cruzamentoMap.get(av + "," + (ru + 1));
                if (vizinhoEste != null && cruzamentos.areAdjacent(v, vizinhoEste)) {
                    cruzamentos.insertEdge(v, vizinhoEste, 1);
                }
            }

            // Oeste
            if (ru > 1) {
                Vertex<Cruzamento> vizinhoOeste = cruzamentoMap.get(av + "," + (ru - 1));
                if (vizinhoOeste != null && cruzamentos.areAdjacent(v, vizinhoOeste)) {
                    cruzamentos.insertEdge(v, vizinhoOeste, 1);
                }
            }
        }


    }



}