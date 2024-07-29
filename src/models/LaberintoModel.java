package models;

public class LaberintoModel {
    private int x;
    private int y;
    private int finX;
    private int finY;
    private LaberintoModel padre;

    // Constructor
    public LaberintoModel(int x, int y, int finX, int finY) {
        this.x = x;
        this.y = y;
        this.finX = finX;
        this.finY = finY;
    }

    // Getters
    public int getInicioX() {
        return x;
    }

    public int getInicioY() {
        return y;
    }

    public int getFinX() {
        return finX;
    }

    public int getFinY() {
        return finY;
    }

    public LaberintoModel getPadre() {
        return padre;
    }

    public void setPadre(LaberintoModel padre) {
        this.padre = padre;
    }
}
