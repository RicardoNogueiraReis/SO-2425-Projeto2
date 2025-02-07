package Projeto2;

import Projeto2.Entities.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.brunomnsilva.smartgraph.graph.*;

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

        mostrarVizinhos(cruzamentos);


    }

    private static void createCruzamentos(Graph<Cruzamento, Integer> cruzamentos, int numberOfAvenidas, int numberOfRuas) {
        Map<String, Vertex<Cruzamento>> cruzamentoMap = new HashMap<>();

        // Criar cruzamentos
        for (int avenida = 1; avenida <= numberOfAvenidas; avenida++) {
            for (int rua = 1; rua <= numberOfRuas; rua++) {
                Cruzamento cruzamento = new Cruzamento(avenida, rua);
                Vertex<Cruzamento> v = cruzamentos.insertVertex(cruzamento);
                cruzamentoMap.put(avenida + "," + rua, v);
            }
        }

        int edge = 0;

        // Criar ligações entre cruzamentos adjacentes
        for (Vertex<Cruzamento> v : cruzamentos.vertices()) {
            Cruzamento node = v.element();
            int av = node.getAvenida();
            int ru = node.getRua();

            // Norte
            if (av > 1) {
                Vertex<Cruzamento> vizinhoNorte = cruzamentoMap.get((av - 1) + "," + ru);
                if (vizinhoNorte != null && !cruzamentos.areAdjacent(v, vizinhoNorte)) {
                    cruzamentos.insertEdge(v, vizinhoNorte, ++edge);
                }
            }

            // Sul
            if (av < numberOfAvenidas) {
                Vertex<Cruzamento> vizinhoSul = cruzamentoMap.get((av + 1) + "," + ru);
                if (vizinhoSul != null && !cruzamentos.areAdjacent(v, vizinhoSul)) {
                    cruzamentos.insertEdge(v, vizinhoSul, ++edge);
                }
            }

            // Este
            if (ru < numberOfRuas) {
                Vertex<Cruzamento> vizinhoEste = cruzamentoMap.get(av + "," + (ru + 1));
                if (vizinhoEste != null && !cruzamentos.areAdjacent(v, vizinhoEste)) {
                    cruzamentos.insertEdge(v, vizinhoEste, ++edge);
                }
            }

            // Oeste
            if (ru > 1) {
                Vertex<Cruzamento> vizinhoOeste = cruzamentoMap.get(av + "," + (ru - 1));
                if (vizinhoOeste != null && !cruzamentos.areAdjacent(v, vizinhoOeste)) {
                    cruzamentos.insertEdge(v, vizinhoOeste, ++edge);
                }
            }
        }
    }

    public static void mostrarVizinhos(Graph<Cruzamento, Integer> cruzamentos) {
        for (Vertex<Cruzamento> v : cruzamentos.vertices()) {

            Cruzamento node = v.element();
            System.out.println("Nó (" + node.getAvenida() + "," + node.getRua() + "):");

            for (Edge<Integer, Cruzamento> edge : cruzamentos.incidentEdges(v)) {


                Vertex<Cruzamento>[] vertices = edge.vertices();
                Vertex<Cruzamento> vizinho = (vertices[0] == v) ? vertices[1] : vertices[0];

                // Descobrir a direção do vizinho
                Cruzamento cruzamentoVizinho = vizinho.element();
                String direcao = getDirecao(node, cruzamentoVizinho);

                System.out.println("  " + direcao + " -> Nó (" + cruzamentoVizinho.getAvenida() + "," + cruzamentoVizinho.getRua() + ")");
            }

            System.out.println();
        }
    }

    // Função auxiliar para determinar a direção do vizinho
    private static String getDirecao(Cruzamento atual, Cruzamento vizinho) {
        if (vizinho.getAvenida() < atual.getAvenida()) {
            return "Nó a norte";
        }
        else if (vizinho.getAvenida() > atual.getAvenida()) {
            return "Nó a sul";
        }
        else if (vizinho.getRua() > atual.getRua()){
            return "Nó a este";
        }
        else if (vizinho.getRua() < atual.getRua()){
            return "Nó a oeste";
        }

        return "Nó desconhecido";
    }




}