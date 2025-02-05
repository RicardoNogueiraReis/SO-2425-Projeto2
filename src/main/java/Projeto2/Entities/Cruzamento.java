package Projeto2.Entities;

public class Cruzamento {
    private int avenida;
    private int rua;

    public Cruzamento(int avenida, int rua) {
        this.avenida = avenida;
        this.rua = rua;
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

    @Override
    public String toString() {
        return "(" + avenida + ", " + rua + ")";
    }
}
