package Projeto2.src.Entities;

public class Entity {
    private int id;
    private int avenida;
    private int rua;

    public Entity(int id, int avenida, int rua) {
        this.id = id;
        this.avenida = avenida;
        this.rua = rua;
    }

    public int getId() {
        return id;
    }

    public int getAvenida() {
        return avenida;
    }

    public int getRua() {
        return rua;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setAvenida(int avenida) {
        this.avenida = avenida;
    }

    public void setRua(int rua) {
        this.rua = rua;
    }
}
