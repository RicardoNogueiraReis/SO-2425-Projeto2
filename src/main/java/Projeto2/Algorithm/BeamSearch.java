package Projeto2.Algorithm;


import Projeto2.Entities.Cidadao;
import Projeto2.Entities.Cruzamento;
import Projeto2.Entities.Supermercado;
import Projeto2.Solution;
import com.brunomnsilva.smartgraph.graph.Edge;
import com.brunomnsilva.smartgraph.graph.Graph;
import com.brunomnsilva.smartgraph.graph.Vertex;

import java.util.ArrayList;

public class BeamSearch {

    private int beamWidth; // alfa
    private Graph<Cruzamento, Integer> cruzamentos;
    private int maxTime;
    private ArrayList<Cidadao> cidadaos;
    private ArrayList<Supermercado> supermercados;

    // Métricas
    private long totalTime;
    private int numCaminhosTerminados;

    public BeamSearch(int beamWidth, Graph<Cruzamento, Integer> cruzamentos, int maxTime, ArrayList<Cidadao> cidadaos, ArrayList<Supermercado> supermercados) {
        this.beamWidth = beamWidth;
        this.cruzamentos = cruzamentos;
        this.maxTime = maxTime;
        this.cidadaos = cidadaos;
        this.supermercados = supermercados;
    }

    public Solution Find_Solution_Citizen_BS(int numThreads, int iteracoesThread) {
        Solution bestSolution = new Solution(cidadaos.size());

        // Criar feixe inicial com caminhos iniciais
        ArrayList<Solution> beam = new ArrayList<>();
        beam.add(initialSolution());

        long startTime = System.currentTimeMillis();

        // int numTotalIterations;

        while (elapsedTime(startTime) < maxTime && !beam.isEmpty()) {
            ArrayList<Solution> nextBeam = new ArrayList<>();
            ArrayList<Thread> threads = new ArrayList<>();

            // Dividir o feixe pelas threads
            int tamanhoSubLista = (int) Math.ceil((double) beam.size() / numThreads);

            for (int i = 0; i < numThreads; i++) {
                int inicio = i * tamanhoSubLista;
                int fim = Math.min(inicio + tamanhoSubLista, beam.size());

                if (inicio >= beam.size()) break; // Evita criar threads desnecessárias

                ArrayList<Solution> subBeam = new ArrayList<>(beam.subList(inicio, fim));
                BeamSearchThread thread = new BeamSearchThread(subBeam, nextBeam, bestSolution, this, iteracoesThread);
                threads.add(thread);
            }

            for (Thread thread : threads) {
                thread.start();

            }

            for (Thread t : threads) {
                try {
                    t.join();
                } catch (InterruptedException e) {
                    System.out.println(e.getMessage());
                }
            }

            // Verificar se encontramos a melhor solução possível
            if (bestSolution.isMaxed() || nextBeam.isEmpty()) {
                break;
            }

            // Escolher as melhores soluções para a próxima iteração
            if (nextBeam.size() > beamWidth) {
                beam = selectBestSolutions(nextBeam);
            } else {
                beam = nextBeam;
            }
        }

        this.totalTime = elapsedTime(startTime); // Tempo total da execução

        // Métricas
        //System.out.println("Número de cidadãos que chegaram ao supermercado: " + bestSolution.getNumeroDeCaminhosTerminados() + " (de " + cidadaos.size() + " cidadãos no total e com " + supermercados.size() + " supermercados)");
        //System.out.println("Tempo total da execução: " + totalTime + " milisegundos" + " (" + (double) totalTime / 1000 + " segundos)\n");
        // System.out.println("Número de iterações até encontrar a solução: " + BeamSearchThread.getTotalIteracoes() + "\n");

        this.numCaminhosTerminados = bestSolution.getNumeroDeCaminhosTerminados();

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



    public boolean expand(ArrayList<Solution> beam, ArrayList<Solution> nextBeam, Solution bestSolution) {
        for (Solution sol : beam) {
            for (int i = 0; i < cidadaos.size(); i++) {
                ArrayList<Cruzamento> caminho = sol.getCaminho(i);

                if (caminho == null || caminho.isEmpty() || caminho.get(caminho.size() - 1).isSupermercado()) {
                    continue;
                }

                Cruzamento ultimoNo = caminho.get(caminho.size() - 1);
                Vertex<Cruzamento> ultimoNoVertex = encontrarVertex(ultimoNo);

                if (ultimoNoVertex == null) {
                    continue;
                }

                for (Edge<Integer, Cruzamento> edge : cruzamentos.incidentEdges(ultimoNoVertex)) {
                    Vertex<Cruzamento>[] vertices = edge.vertices();
                    Vertex<Cruzamento> vizinhoVertex = (vertices[0] == ultimoNoVertex) ? vertices[1] : vertices[0];
                    Cruzamento vizinho = vizinhoVertex.element();

                    if (caminho.contains(vizinho)) continue;
                    if (estaNoMeioDeOutroCaminho(sol, vizinho, i)) continue;

                    Solution novaSolucao = sol.copy();
                    novaSolucao.adicionarPasso(i, vizinho);

                    synchronized (bestSolution) {
                        if (updateBestSolution(bestSolution, novaSolucao) && bestSolution.isMaxed()) {
                            return true;
                        }
                    }

                    synchronized (nextBeam) {
                        nextBeam.add(novaSolucao);
                    }
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
            solucao.adicionarPasso(i, new Cruzamento(c.getAvenida(), c.getRua()));
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

    @Override
    public String toString() {
        System.out.println("Tempo: " + totalTime + " milisegundos");
        System.out.println("Cidadãos na solução: " + numCaminhosTerminados + "/ C(" + cidadaos.size() + ") / S(" + supermercados.size() + ")");

        return "";
    }
}

