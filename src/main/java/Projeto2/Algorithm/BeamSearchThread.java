package Projeto2.Algorithm;

import Projeto2.Solution;

import java.util.ArrayList;

public class BeamSearchThread extends Thread {
    private ArrayList<Solution> subBeam;
    private ArrayList<Solution> nextBeam;
    private Solution bestSolution;
    private BeamSearch beamSearch;
    private int iteracoesThread;

    public BeamSearchThread(ArrayList<Solution> subBeam, ArrayList<Solution> nextBeam, Solution bestSolution, BeamSearch beamSearch, int iteracoesThread) {
        this.subBeam = subBeam;
        this.nextBeam = nextBeam;
        this.bestSolution = bestSolution;
        this.beamSearch = beamSearch;
        this.iteracoesThread = iteracoesThread;
    }

    @Override
    public void run() {
        for (int i = 0; i < iteracoesThread; i++) {
            ArrayList<Solution> threadNextBeam = new ArrayList<>();

            boolean encontrouSolucao = beamSearch.expand(subBeam, threadNextBeam, bestSolution);

            // Atualizar bestSolution de forma sincronizada
            synchronized (bestSolution) {
                if (encontrouSolucao && bestSolution.isMaxed()) {
                    return; // Encontramos a solução ótima, terminar thread
                }
            }

            // Adicionar novas soluções à lista nextBeam de forma sincronizada
            synchronized (nextBeam) {
                nextBeam.addAll(threadNextBeam);
            }
        }
    }
}
