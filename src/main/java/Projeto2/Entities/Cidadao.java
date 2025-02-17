package Projeto2.Entities;

public class Cidadao extends Entity {
    private boolean chegouSupermercado;
    private static int totalCidadaos;

    public Cidadao(int avenida, int rua) {
        super(avenida, rua);
        this.chegouSupermercado = false;
    }

    public static int TotalCidadaos() {
        return totalCidadaos;
    }

    public boolean isChegouSupermercado() {
        return chegouSupermercado;
    }

    public void setChegouSupermercado(boolean chegouSupermercado) {
        this.chegouSupermercado = chegouSupermercado;
    }

    public void setChegouSupermercado() {
        this.chegouSupermercado = true;
    }

    @Override
    public String toString() {
        return "Cidadão -> Avenida: " + getAvenida() + " | Rua: " + getRua() + " | Chegou? " + chegouSupermercado;
    }
}