package Projeto2;

import java.io.IOException;

public class Parameters {

    private String nomeFicheiro;
    private int larguraFeixe;
    private int numThreads;
    private int milisegundos;
    private int iteracoesThread;

    public Parameters() { }

    public String getNomeFicheiro() {
        return nomeFicheiro;
    }

    public int getLarguraFeixe() {
        return larguraFeixe;
    }

    public int getNumThreads() {
        return numThreads;
    }

    public int getMilisegundos() {
        return milisegundos;
    }

    public int getIteracoesThread() {
        return iteracoesThread;
    }

    public void setNomeFicheiro(String nomeFicheiro) throws IOException {

        if(nomeFicheiro.isEmpty()) {
            throw new IOException("ERRO: Nome do ficheiro em falta.");

        }

        this.nomeFicheiro = nomeFicheiro;

    }

    public void setLarguraFeixe(String larguraFeixe) throws IOException {

        this.larguraFeixe = Integer.parseInt(larguraFeixe);

        if (this.larguraFeixe <= 0) {
            throw new IOException("ERRO: Largura do feixe não pode ser menor ou igual a zero.");

        }

    }

    public void setNumThreads(String numThreads) throws IOException {

        this.numThreads = Integer.parseInt(numThreads);

        if (this.numThreads <= 0) {
            throw new IOException("ERRO: Número de threads não pode ser menor ou igual a zero.");

        }

    }

    public void setMilisegundos(String milisegundos) throws IOException {

        this.milisegundos = Integer.parseInt(milisegundos);

        if (this.milisegundos <= 0) {
            throw new IOException("ERRO: Milisegundos não podem ser menor ou igual a zero.");

        }

    }

    public void setIteracoesThread(String iteracoesThread) throws IOException {

        this.iteracoesThread = Integer.parseInt(iteracoesThread);

        if (this.iteracoesThread <= 0) {
            throw new IOException("ERRO: Número de iterações não pode ser menor ou igual a zero.");

        }

    }

    @Override
    public String toString() {
        return "\nFicheiro: " + nomeFicheiro +
               "\nLargura do feixe: " + larguraFeixe +
               "\nNúmero de threads: " + numThreads +
               "\nMilisegundos: " + milisegundos + " (" + (double) milisegundos/1000 + " Segundos)" +
               "\nNúmero de iterações para cada thread: " + iteracoesThread + "\n";
    }
}
