package Projeto2.src.Entities;

public class Supermercado extends Entity {
    private boolean ocupado;
    private static int totalSupermercados;

    public Supermercado(int id, int avenida, int rua) {
        super(id, avenida, rua);
        this.ocupado = false;

    }

    public Supermercado(int avenida, int rua) {
        this(++totalSupermercados, avenida, rua);

    }

    public static int totalSupermercados() {
        return totalSupermercados;
    }

    public boolean isOcupado() {
        return ocupado;
    }

    public void setOcupado(boolean ocupado) {
        this.ocupado = ocupado;
    }

    public void setOcupado() {
        this.ocupado = true;
    }

    @Override
    public String toString() {
        return "Supermercado " + getId() + " | Avenida: " + getAvenida() + " | Rua: " + getRua() + " | Ocupado? " + ocupado;
    }
}
