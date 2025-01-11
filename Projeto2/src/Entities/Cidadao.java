package Projeto2.src.Entities;

public class Cidadao extends Entity {
    private boolean chegouSupermercado;
    private static int totalCidadaos;

    public Cidadao(int id, int avenida, int rua) {
        super(id, avenida, rua);
        this.chegouSupermercado = false;

    }

    public Cidadao(int avenida, int rua) {
        this(++totalCidadaos, avenida, rua);

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
        return "Cidadão " + getId() + " | Avenida: " + getAvenida() + " | Rua: " + getRua() + " | Chegou? " + chegouSupermercado;
    }
}
