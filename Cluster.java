public class Cluster implements Comparable<Cluster> {
    public Ponto ponto;
    private Cluster filhoEsq;
    private Cluster filhoDir;

    public Cluster(Ponto ponto) {
        this.ponto = ponto;
        this.filhoEsq = null;
        this.filhoDir = null;
    }

    public Cluster(Cluster filhoEsq, Cluster filhoDir) {
        this.ponto = calcularMedia(filhoEsq.getPonto(), filhoDir.getPonto());
        this.filhoEsq = filhoEsq;
        this.filhoDir = filhoDir;
    }

    public Ponto getPonto() {
        return ponto;
    }

    public Cluster getFilhoEsq() {
        return filhoEsq;
    }

    public void setFilhoEsq(Cluster filhoEsquerda) {
        this.filhoEsq = filhoEsq;
    }

    public Cluster getFilhoDir() {
        return filhoDir;
    }

    public void setFilhoDir(Cluster filhoDir) {
        this.filhoDir = filhoDir;
    }

    public Ponto calcularMedia(Ponto p1, Ponto p2) {
        double x = (p1.getX() + p2.getX()) / 2.0;
        double y = (p1.getY() + p2.getY()) / 2.0;
        return new Ponto(x, y);
    }

    @Override
    public int compareTo(Cluster cluster) {
        if (this.ponto.getX() == cluster.ponto.getX() && this.ponto.getY() == cluster.ponto.getY())
            return 0;
        else if (this.ponto.getX() > cluster.ponto.getX()) {
            return 1;
        }else
            return -1;
    }
}

