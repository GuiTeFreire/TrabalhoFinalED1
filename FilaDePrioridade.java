public class FilaDePrioridade{
    private int n;
    private int tam;
    public DistanciaEntreClusters[] vetor;

    public FilaDePrioridade(int tamanho)
    {
        n = 0;
        tam = tamanho;
        vetor = new DistanciaEntreClusters[tamanho+1];
    }

    public FilaDePrioridade(int tamanho, DistanciaEntreClusters[] v)
    {
        tam = tamanho;
        vetor = new DistanciaEntreClusters[tamanho+1];
        n = tamanho;

        for( int i = 0; i < tamanho; i++ )
            vetor[ i + 1 ] = v[ i ];

        constroiHeap();
    }

    public boolean vazia()
    {
        return n == 0;
    }

    public void fazVazia()
    {
        n = 0;
    }

    public void imprime()
    {
        for(int i = 1; i <= n; i++)
            System.out.print(vetor[i] + " ");

        System.out.println();
    }

    public void imprimeTamanho()
    {
        for(int i = 1; i <= tam; i++)
            System.out.print(vetor[i] + " ");

        System.out.println();
    }

    public DistanciaEntreClusters min()
    {
        if (this.vazia())
        {
            System.out.println("Fila de prioridades vazia!");
            return null;
        }

        return vetor[1];
    }

    public DistanciaEntreClusters removeMin()
    {
        DistanciaEntreClusters itemMin;

        if(this.vazia())
        {
            System.out.println("Fila de prioridades vazia!");
            return null;
        }

        itemMin = vetor[1];
        vetor[1] = vetor[n];
        n--;
        refaz(1);

        return itemMin;
    }

    private void constroiHeap()
    {
        for( int i = n / 2; i > 0; i-- )
            refaz(i);
    }

    private void refaz(int i)
    {
        DistanciaEntreClusters x = vetor[ i ];

        while(2*i <= n)
        {
            int filhoEsq, filhoDir, menorFilho;

            filhoEsq = 2*i;
            filhoDir = 2*i + 1;

            menorFilho = filhoEsq;

            if(filhoDir <= n)
            {
                if(vetor[ filhoDir ].compareTo(vetor[ filhoEsq ]) < 0)
                    menorFilho = filhoDir;
            }

            if(vetor[ menorFilho ].compareTo(x) < 0)
                vetor [ i ] = vetor[ menorFilho ];
            else
                break;

            i = menorFilho;
        }

        vetor[ i ] = x;
    }

    public void insere(DistanciaEntreClusters x)
    {
        if (tam == n)
        {
            System.out.println("Fila de prioridades cheia!");
            return;
        }

        n++;
        int pos = n;

        vetor[0] = x;

        while(x.compareTo(vetor[pos/2]) < 0)
        {
            vetor[pos] = vetor[ pos/2 ];
            pos /= 2;
        }

        vetor[pos] = x;
    }

    public void insere(DistanciaEntreClusters[] distancias) {
        if (tam - n < distancias.length) {
            System.out.println("Fila de prioridades cheia!");
            return;
        }

        for (DistanciaEntreClusters distancia : distancias) {
            n++;
            int pos = n;
            vetor[0] = distancia;

            while (distancia.compareTo(vetor[pos/2]) < 0) {
                vetor[pos] = vetor[pos/2];
                pos /= 2;
            }

            vetor[pos] = distancia;
        }
    }

    public void heapsort()
    {
        DistanciaEntreClusters x;
        int resta = n;
        int raiz = 1;
        constroiHeap();

        while (resta > 1)
        {
            x = vetor[raiz];
            vetor[raiz] = vetor[resta];
            vetor[resta] = x;
            resta--;
            n--;
            refaz(raiz);
        }
    }

    public int getTam() {
        return tam;
    }
}