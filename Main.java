import java.util.Random;

//10, 20, 30, 40, 50, 100, 200, 500, 1000, 5000, 10000, 20000, 50000, 100000
//            long tempoIni = System.currentTimeMillis();
//            long tempoFim = System.currentTimeMillis();
//            long duracao = tempoFim - tempoIni;
//            System.out.println("Tempo total de execução: " + duracao + " milissegundos");

public class Main {
    public static void main(String[] args) {
        int tamanho = 20000;
        Ponto[] pontos = gerarPontosAleatorios(tamanho);

        //Passa os pontos para um vetor de Clusters
        Cluster[] clusters = new Cluster[pontos.length];
        for (int i = 0; i < pontos.length; i++) {
            clusters[i] = new Cluster(pontos[i]);
        }

        //Tira o tempo inicial
        long tempoIni = System.currentTimeMillis();

        //Calculo do Cluster Resultante (Naive)
        //Cluster clusterFinal = clusterizarNaive(clusters);

        //Calculo do Cluster Resultante (Fila de Prioridade)
        Cluster clusterFinal = clusterizarFilaDePrioridade(clusters);

        System.out.println("\nCluster Final: ");
        System.out.print("(" + clusterFinal.getPonto().getX() + ", " + clusterFinal.getPonto().getY() + ")");

        //Calculo do tempo de execução
        long tempoFim = System.currentTimeMillis();
        long duracao = tempoFim - tempoIni;
        System.out.println("\nTempo total de execucao: " + duracao + " milissegundos");

        //Árvore de Clusters
        //clusterFinal.arvore.imprimeEmOrdem();
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
        int j = 0;
        boolean novoClusterAdicionado = false;

        for (int i = 0; i < clusters.length; i++) {
            if (clusters[i] != novoCluster.getFilhoEsq() && clusters[i] != novoCluster.getFilhoDir()) {
                novosClusters[j] = clusters[i];
                j++;
            } else if (!novoClusterAdicionado) {
                novosClusters[j] = novoCluster;
                j++;
                novoClusterAdicionado = true;
            }
        }

        return novosClusters;
    }

    //Encontrar o Cluster final (Naive)
    private static Cluster clusterizarNaive(Cluster[] clusters) {
       while (clusters.length > 1) {
           // Obter a menor distância entre os clusters iniciais
            DistanciaEntreClusters menorDistancia = encontrarMenorDistancia(clusters);

           // Criar um novo cluster a partir dos dois clusters mais próximos
            Cluster novoCluster = new Cluster(menorDistancia.getC1(), menorDistancia.getC2());

           // Atualizar a lista de clusters
            clusters = atualizarClusters(clusters, novoCluster);
        }

        return clusters[0];
    }

    //Encontrar o Cluster final (Fila de Prioridade)
    public static Cluster clusterizarFilaDePrioridade(Cluster[] clusters) {
        FilaDePrioridade fila = new FilaDePrioridade(clusters.length * (clusters.length - 1) / 2);

        // Calcular todas as distâncias iniciais entre os clusters
        for (int i = 0; i < clusters.length - 1; i++) {
            for (int j = i + 1; j < clusters.length; j++) {
                DistanciaEntreClusters distancia = new DistanciaEntreClusters(clusters[i], clusters[j]);
                fila.insere(distancia);
            }
        }

        while (clusters.length > 1) {
            // Obter a menor distância da fila de prioridade
            DistanciaEntreClusters menorDistancia = fila.removeMin();

            // Criar um novo cluster a partir dos dois clusters mais próximos
            Cluster novoCluster = new Cluster(menorDistancia.getC1(), menorDistancia.getC2());

            // Atualizar a lista de clusters
            clusters = atualizarClusters(clusters, novoCluster);

            // Remover as distâncias relacionadas aos clusters c1 e c2 da fila
            fila.removerDistancias(menorDistancia.getC1(), menorDistancia.getC2());

            // Calcular as distâncias entre o novo cluster e os outros clusters restantes
            for (int i = 0; i < clusters.length; i++) {
                if (clusters[i] != novoCluster) {
                    DistanciaEntreClusters distancia = new DistanciaEntreClusters(clusters[i], novoCluster);
                    fila.insere(distancia);
                }
            }
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