package Projeto2.Entities;

import java.util.ArrayList;

public class Cruzamento {
    private int avenida;
    private int rua;

    private ArrayList<Cidadao> cidadaos;
    private ArrayList<Supermercado> supermercados;

    private String quantosSupermercados;

    public Cruzamento(int avenida, int rua) {
        this.avenida = avenida;
        this.rua = rua;

        this.cidadaos = new ArrayList<>();
        this.supermercados = new ArrayList<>();

        quantosSupermercados = "";
    }

    public int getAvenida() {
        return avenida;
    }

    public int getRua() {
        return rua;
    }

    public void setAvenida(int avenida) {
        this.avenida = avenida;
    }

    public void setRua(int rua) {
        this.rua = rua;
    }

    public ArrayList<Cidadao> getCidadaos() {
        return cidadaos;
    }

    public ArrayList<Supermercado> getSupermercados() {
        return supermercados;
    }

    public void adicionarCidadao(Cidadao c) {
        cidadaos.add(c);
    }

    public void adicionarSupermercado(Supermercado s) {
        quantosSupermercados = supermercados.size() + " Supermercado(s)";

        supermercados.add(s);
    }

    public boolean isSupermercado() {
        return !supermercados.isEmpty();
    }

    @Override
    public String toString() {
        return "Cruzamento (" + avenida + "," + rua + ") |" + supermercados.size() + "|";

    }

    @Override
    public boolean equals(Object obj) {
        Cruzamento that = (Cruzamento) obj;
        return avenida == that.avenida && rua == that.rua;
    }

}
