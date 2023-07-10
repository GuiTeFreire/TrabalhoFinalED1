public class DistanciaEntreClusters implements Comparable<DistanciaEntreClusters> {
    private Cluster c1, c2;
    private double distancia;

    public DistanciaEntreClusters(Cluster c1, Cluster c2) {
        this.c1 = c1;
        this.c2 = c2;
        this.distancia = Math.sqrt((c2.ponto.getX() - c1.ponto.getX()) * (c2.ponto.getX() - c1.ponto.getX())
                + (c2.ponto.getY() - c1.ponto.getY()) * (c2.ponto.getY() - c1.ponto.getY()));
    }

    public Cluster getC1() {
        return c1;
    }

    public Cluster getC2() {
        return c2;
    }

    public double getDistancia() {
        return distancia;
    }

    public int compareTo(DistanciaEntreClusters distancia2) {
        if (this.distancia > distancia2.distancia)
            return 1;
        else if (this.distancia < distancia2.distancia)
            return -1;
        else
            return 0;
    }
}
