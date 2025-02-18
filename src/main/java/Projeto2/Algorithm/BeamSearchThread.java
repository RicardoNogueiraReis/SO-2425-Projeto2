package Projeto2.Algorithm;

import Projeto2.Solution;

import java.util.ArrayList;

public class BeamSearchThread extends Thread {
    private ArrayList<Solution> subBeam;
    private ArrayList<Solution> nextBeam;
    private Solution bestSolution;
    private BeamSearch beamSearch;
    private int iteracoesThread;
    private static int totalIteracoes = -1;
    private int numIteracoes;

    public BeamSearchThread(ArrayList<Solution> subBeam, ArrayList<Solution> nextBeam, Solution bestSolution, BeamSearch beamSearch, int iteracoesThread) {
        this.subBeam = subBeam;
        this.nextBeam = nextBeam;
        this.bestSolution = bestSolution;
        this.beamSearch = beamSearch;
        this.iteracoesThread = iteracoesThread;
        this.numIteracoes = 0;
    }

    @Override
    public void run() {
        for (int i = 0; i < iteracoesThread; i++) {

            ArrayList<Solution> threadNextBeam = new ArrayList<>();

            boolean encontrouSolucao = beamSearch.expand(subBeam, threadNextBeam, bestSolution);

            // Atualizar bestSolution
            synchronized (bestSolution) {
                if (encontrouSolucao && bestSolution.isMaxed()) {
                    totalIteracoes = i+1;

                    return; // Encontramos a solução ótima, terminar thread
                }
            }


            // Adicionar novas soluções à lista nextBeam
            synchronized (nextBeam) {
                nextBeam.addAll(threadNextBeam);
            }
        }
    }

    public static int getTotalIteracoes() {
        return totalIteracoes;
    }
}
