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

        /* TODO:
        * Estrutura das classes para representar Esquina e Cidadão
        * Classe para leitura de ficheiros
        * */

        /*
        Entity cidadao1 = new Cidadao(1, 1);
        Entity cidadao2 = new Cidadao(1, 2);
        Entity cidadao3 = new Cidadao(1, 3);

        System.out.println(cidadao1);
        System.out.println(cidadao3);
        System.out.println(cidadao2);

        Entity supermercado1 = new Supermercado(1, 1);
        Entity supermercado2 = new Supermercado(1, 2);
        Entity supermercado3 = new Supermercado(1, 3);

        System.out.println(supermercado1);
        System.out.println(supermercado2);
        System.out.println(supermercado3);
        */

        ArrayList<Supermercado> supermercados = new ArrayList<>();
        ArrayList<Cidadao> cidadaos = new ArrayList<>();

        try {
            DataReader dataReader = new DataReader("instances_2.txt", supermercados, cidadaos);

        } catch (IOException e) {
            System.out.println(e.getMessage());
            return;
        }

        for (Supermercado supermercado : supermercados) {
            System.out.println(supermercado);
        }

        for (Cidadao cidadao : cidadaos) {
            System.out.println(cidadao);
        }

    }
}