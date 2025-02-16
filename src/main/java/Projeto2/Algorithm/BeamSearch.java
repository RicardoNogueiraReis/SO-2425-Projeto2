package Projeto2.Algorithm;


import Projeto2.Entities.Cidadao;
import Projeto2.Entities.Cruzamento;
import Projeto2.Entities.Supermercado;
import Projeto2.Solution;
import com.brunomnsilva.smartgraph.graph.Edge;
import com.brunomnsilva.smartgraph.graph.Graph;
import com.brunomnsilva.smartgraph.graph.GraphEdgeList;
import com.brunomnsilva.smartgraph.graph.Vertex;

import java.util.ArrayList;

public class BeamSearch {

    private int beamWidth; // alfa
    private Graph<Cruzamento, Integer> cruzamentos;
    private int maxTime;
    private ArrayList<Cidadao> cidadaos;
    private ArrayList<Supermercado> supermercados;

    public BeamSearch(int beamWidth, Graph<Cruzamento, Integer> cruzamentos, int maxTime, ArrayList<Cidadao> cidadaos, ArrayList<Supermercado> supermercados) {
        this.beamWidth = beamWidth;
        this.cruzamentos = cruzamentos;
        this.maxTime = maxTime;
        this.cidadaos = cidadaos;
        this.supermercados = supermercados;
    }

    public Solution Find_Solution_Citizen_BS() {
        Solution bestSolution = new Solution(cidadaos.size());

        // Criar feixe inicial com caminhos iniciais
        ArrayList<Solution> beam = new ArrayList<>();
        beam.add(initialSolution());

        long startTime = System.currentTimeMillis();

        while (elapsedTime(startTime) < maxTime && !beam.isEmpty()) {
            ArrayList<Solution> nextBeam = new ArrayList<>();


            if (expand(beam, nextBeam, bestSolution)) {
                break; // Encontramos a melhor solução possível
            }

            // Expand will return true if the optimal solution is found
            if (nextBeam.size() > beamWidth) {
                beam = selectBestSolutions(nextBeam);

            } else {
                beam = nextBeam;
            }
        }

        return bestSolution;
    }

    private ArrayList<Solution> selectBestSolutions(ArrayList<Solution> beam) {
        return new ArrayList<>(beam.subList(0, Math.min(beamWidth, beam.size())));
    }


    private boolean updateBestSolution(Solution bestSolution, Solution novaSolucao) {
        int caminhosTerminados = novaSolucao.getNumeroDeCaminhosTerminados();

        if (caminhosTerminados > bestSolution.getNumeroDeCaminhosTerminados()) {
            bestSolution.copyFrom(novaSolucao); // Copiar a solução completa
            return true;
        }
        return false;
    }



    private boolean expand(ArrayList<Solution> beam, ArrayList<Solution> nextBeam, Solution bestSolution) {
        for (Solution sol : beam) {
            for (int i = 0; i < cidadaos.size(); i++) {
                ArrayList<Cruzamento> caminho = sol.getCaminho(i);

                // Se o caminho já terminou (chegou a um supermercado), ignoramos
                if (caminho.isEmpty() || caminho.get(caminho.size() - 1).isSupermercado()) {
                    continue;

                }

                // Obter o último nó do caminho do cidadão
                Cruzamento ultimoNo = caminho.get(caminho.size() - 1);
                Vertex<Cruzamento> ultimoNoVertex = encontrarVertex(ultimoNo);

                // Percorrer nós adjacentes ao último nó
                for (Edge<Integer, Cruzamento> edge : cruzamentos.incidentEdges(ultimoNoVertex)) { // Erro aqui
                    Vertex<Cruzamento>[] vertices = edge.vertices();
                    Vertex<Cruzamento> vizinhoVertex = (vertices[0] == ultimoNoVertex) ? vertices[1] : vertices[0];
                    Cruzamento vizinho = vizinhoVertex.element();

                    // Regras do pseudocódigo
                    if (caminho.contains(vizinho)) continue; // Evita ciclos
                    if (estaNoMeioDeOutroCaminho(sol, vizinho, i)) continue; // Evita colisões

                    // Criar nova solução expandida
                    Solution novaSolucao = sol.copy();
                    novaSolucao.adicionarPasso(i, vizinho);

                    // Atualizar BestSolution se for melhor
                    if (updateBestSolution(bestSolution, novaSolucao) && bestSolution.isMaxed()) {
                        return true;

                    }

                    nextBeam.add(novaSolucao);
                }
            }
        }
        return false;
    }

    private Vertex<Cruzamento> encontrarVertex(Cruzamento cruzamento) {
        for (Vertex<Cruzamento> v : cruzamentos.vertices()) {
            if (v.element().equals(cruzamento)) {
                return v;

            }

        }

        return null;
    }

    private Solution initialSolution() {
        Solution solucao = new Solution(cidadaos.size());

        for (int i = 0; i < cidadaos.size(); i++) {
            Cidadao c = cidadaos.get(i);
            solucao.adicionarPasso(i, new Cruzamento(c.getAvenida(), c.getRua())); // Ponto inicial
        }

        return solucao;
    }

    private long elapsedTime(long startTime) {
        return (System.currentTimeMillis() - startTime);
    }

    private boolean estaNoMeioDeOutroCaminho(Solution sol, Cruzamento vizinho, int cidadaoAtual) {

        for (int i = 0; i < sol.getCaminhos().size()-1; i++) {

            if (i == cidadaoAtual) { // Ignorar o caminho do próprio cidadão
                continue;
            }

            ArrayList<Cruzamento> caminhoOutroCidadao = sol.getCaminho(i);
            if (caminhoOutroCidadao.isEmpty()) {
                continue;
            }

            // Se for o nó inicial de outro cidadão, ignoramos
            // (cidadãos podem partilhar o início)
            Cruzamento inicio = caminhoOutroCidadao.get(0);

            if (inicio.equals(vizinho)) {
                continue;
            }

            // Se o caminho já terminou num supermercado, permitimos a sobreposição no fim
            Cruzamento cruzamentoFinal = caminhoOutroCidadao.get(caminhoOutroCidadao.size() - 1);
            if (cruzamentoFinal.isSupermercado() && cruzamentoFinal.equals(vizinho)) {
                continue;
            }

            // Se o nó está dentro do caminho e não é início nem fim, então está no meio
            if (caminhoOutroCidadao.contains(vizinho)) {
                return true; // Não podemos expandir para este nó
            }
        }

        return false; // Se passou todas as verificações, podemos expandir
    }

}

