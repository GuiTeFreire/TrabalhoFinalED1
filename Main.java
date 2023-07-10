import java.util.Random;

//10, 20, 30, 40, 50, 100, 200, 500, 1000, 5000, 10000, 20000, 50000, 100000
//            long tempoIni = System.currentTimeMillis();
//            long tempoFim = System.currentTimeMillis();
//            long duracao = tempoFim - tempoIni;
//            System.out.println("Tempo total de execução: " + duracao + " milissegundos");

public class Main {
    public static void main(String[] args) {
        int tamanho = 5;
        Ponto[] pontos = gerarPontosAleatorios(tamanho);
        Arvbin<Cluster>[] arvoreClusters = new Arvbin[pontos.length];

        double somaX = 0, somaY = 0;

        //Passa os pontos para um vetor de Clusters
        Cluster[] clusters = new Cluster[pontos.length];
        for (int i = 0; i < pontos.length; i++) {
            clusters[i] = new Cluster(pontos[i]);
            arvoreClusters[i] = new Arvbin<>(clusters[i]);
            somaX += pontos[i].getX();
            somaY += pontos[i].getY();
        }
        double mediaX = somaX / pontos.length;
        double mediaY = somaY / pontos.length;
        Ponto centroideFinal = new Ponto(mediaX, mediaY);

        System.out.println("Pontos Iniciais: ");
        for (int i = 0; i < pontos.length; i++) {
            System.out.print("(" + pontos[i].getX() + ", " + pontos[i].getY() + ")");
        }

        Cluster clusterFinal = clusterizarNaive(clusters);
        System.out.println("\nCluster Final: ");
        System.out.print("(" + clusterFinal.getPonto().getX() + ", " + clusterFinal.getPonto().getY() + ")");
        System.out.println("\nCentroide Final: ");
        System.out.print("(" + centroideFinal.getX() + ", " + centroideFinal.getY() + ")");
    }

    //Encontrar a menor distância entre 2 Clusters do vetor
    public static DistanciaEntreClusters encontrarMenorDistancia(Cluster[] clusters){
        DistanciaEntreClusters menorDistancia = null;
        double menorDistanciaValor = Double.MAX_VALUE;

        for (int i = 0; i < clusters.length - 1; i++) {
            for (int j = i + 1; j < clusters.length; j++) {
                DistanciaEntreClusters distancia = new DistanciaEntreClusters(clusters[i], clusters[j]);

                if (distancia.getDistancia() < menorDistanciaValor) {

                    menorDistanciaValor = distancia.getDistancia();
                    menorDistancia = new DistanciaEntreClusters(clusters[i], clusters[j]);
                }
            }
        }

        return menorDistancia;
    }

    //Recriar outro vetor atualizado de Clusters
    private static Cluster[] atualizarClusters(Cluster[] clusters, Cluster novoCluster) {
        Cluster[] novosClusters = new Cluster[clusters.length - 1];
        int novoIndex = 0;
        boolean novoClusterAdicionado = false;

        for (int i = 0; i < clusters.length; i++) {
            if (clusters[i] != novoCluster.getFilhoEsq() && clusters[i] != novoCluster.getFilhoDir()) {
                novosClusters[novoIndex++] = clusters[i];
            } else if (!novoClusterAdicionado) {
                novosClusters[novoIndex++] = novoCluster;
                novoClusterAdicionado = true;
            }
        }

        return novosClusters;
    }

    //Encontrar o Cluster final
    private static Cluster clusterizarNaive(Cluster[] clusters) {
       while (clusters.length > 1) {
            DistanciaEntreClusters menorDistancia = encontrarMenorDistancia(clusters);
            Cluster novoCluster = new Cluster(menorDistancia.getC1(), menorDistancia.getC2());
            clusters = atualizarClusters(clusters, novoCluster);
        }

        return clusters[0];
    }

    //Gerar pontos aleatorios
    public static Ponto[] gerarPontosAleatorios(int numPontos) {
        Random random = new Random();
        Ponto[] pontos = new Ponto[numPontos];

        for (int i = 0; i < numPontos; i++) {
            double x = random.nextInt(numPontos);
            double y = random.nextInt(numPontos);
            pontos[i] = new Ponto(x, y);
        }

        return pontos;
    }
}