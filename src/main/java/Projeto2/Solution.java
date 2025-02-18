package Projeto2;

import Projeto2.Entities.Cruzamento;

import java.util.ArrayList;

public class Solution {

    private ArrayList<ArrayList<Cruzamento>> caminhos;

    public Solution(int numCidadaos) {
        caminhos = new ArrayList<>();

        for (int i = 0; i < numCidadaos; i++) {
            caminhos.add(new ArrayList<>());
        }
    }


    public ArrayList<ArrayList<Cruzamento>> getCaminhos() {
        return caminhos;
    }

    public ArrayList<Cruzamento> getCaminho(int cidadaoId) {
        return caminhos.get(cidadaoId);
    }

    public void adicionarPasso(int cidadaoId, Cruzamento cruzamento) {
        caminhos.get(cidadaoId).add(cruzamento);
    }

    public Solution copy() {
        Solution novaSolucao = new Solution(this.caminhos.size());

        for (int i = 0; i < this.caminhos.size(); i++) {
            ArrayList<Cruzamento> caminhoOriginal = this.caminhos.get(i);
            if (caminhoOriginal != null) {
                novaSolucao.caminhos.set(i, new ArrayList<>(caminhoOriginal));
            } else {
                novaSolucao.caminhos.set(i, new ArrayList<>()); // Inicializa um novo caminho vazio se o original for null
            }
        }

        return novaSolucao;
    }


    /**
     * Percorremos todos os caminhos e verificamos se acabam num supermercado.
     * @return true se todos os caminhos (cidadãos) chegarem a um supermercado, falso se houver pelo menos um que não chegou.
     */
    public synchronized boolean isMaxed() {
        for (ArrayList<Cruzamento> caminho : caminhos) {
            if (caminho == null || (!caminho.isEmpty() && !caminho.get(caminho.size() - 1).isSupermercado())) {
                return false;

            }
        }
        return true;
    }

    public synchronized int getNumeroDeCaminhosTerminados() {
        int count = 0;
        for (ArrayList<Cruzamento> caminho : caminhos) {
            if (caminho != null && !caminho.isEmpty() && caminho.get(caminho.size() - 1).isSupermercado()) {
                count++;
            }
        }
        return count;
    }


    public void copyFrom(Solution outra) {
        this.caminhos.clear(); // Apaga os caminhos antigos

        for (ArrayList<Cruzamento> caminho : outra.caminhos) {
            this.caminhos.add(new ArrayList<>(caminho)); // Copia cada caminho individualmente
        }
    }

    @Override
    public String toString() {

        /*
        String coordenadas = "";

        for (int i = 0; i < this.caminhos.size(); i++) {
            coordenadas += "Cidadão " + i + ": " + this.caminhos.get(i) + "\n";

        }// */

        String solucao = "";

        solucao += "{\n";

        boolean isSupermercado = false;

        for (ArrayList<Cruzamento> caminho : caminhos) {
            solucao += "\t[[ ";
            for (Cruzamento c : caminho) {
                solucao += "(" + c.getAvenida() + ", " + c.getRua() + ") ";
                isSupermercado = c.isSupermercado();
            }

            if (isSupermercado) {
                solucao += "]]S \n";

            }else {
                solucao += "]] \n";

            }

        }

        solucao += "\n}";

        return solucao;

    }


}
