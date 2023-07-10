import java.util.Deque;
import java.util.Iterator;

public class Arvbin<T extends Comparable<T>>{
	public T val;
	public Arvbin<T> esq, dir;
	public Arvbin(T valor){
		val = valor;
		esq = null;
		dir = null;
	}
	public Arvbin(T valor, Arvbin<T> arvEsq, Arvbin<T> arvDir){
		val = valor;
		esq = arvEsq;
		dir = arvDir;
	}
	public T retornaVal()
	{
		return val;
	}
	public Arvbin<T> retornaEsq()
	{
		return esq;
	}
	public Arvbin<T> retornaDir()
	{
		return dir;
	}
	public void defineVal(T valor)
	{
		val = valor;
	}
	public void defineEsq(Arvbin<T> filho)
	{
		esq = filho;
	}
	public void defineDir(Arvbin<T> filho)
	{
		dir = filho;
	}

	public int tamanho(){
		if (esq == null && dir == null)
			return 1;
		else return 1 + esq.tamanho() + dir.tamanho();
	}
	public Arvbin<T> busca(T valor){
		Arvbin<T> ret;

		/* Se é igual ao valor armazenado, não precisa procurar mais. O nó é a raiz. */
		if (valor.compareTo(val) == 0)
		{
			return this;
		}

		/* Senão, começa procurando na subárvore esquerda. */
		if (esq != null)
		{
			ret = esq.busca(valor);

			if (ret != null)
				return ret;
		}

		/* Se chega a esse ponto, estará na árvore se, e somente se, 
	     estiver na subárvore direita */
		if (dir != null)
			return dir.busca(valor);

		return null;
	}
	public int calculaAltura(){
		if((esq == null) && (dir == null))
			return 0;

		int altEsq = 0, altDir = 0;

		if(esq != null)
			altEsq = esq.calculaAltura();

		if(dir != null)
			altDir = dir.calculaAltura();

		return 1 + Math.max(altEsq, altDir);
	}
	private void imprimeCaminho(Deque<T> caminhos){
		Iterator<T> iterator = caminhos.iterator();

		while(iterator.hasNext())
		{
			System.out.print(iterator.next() + " -> ");
		}

		System.out.println();
	}
	public void imprimePreOrdem(){
		System.out.print("(" + val);
		if (esq != null)
			esq.imprimePreOrdem();
		if (dir != null)
			dir.imprimePreOrdem();
		System.out.print(")");
	}
	public void imprimePosOrdem(){
		System.out.print("(");
		if (esq != null)
			esq.imprimePosOrdem();
		if (dir != null)
			dir.imprimePosOrdem();
		System.out.print(val + ")");
	}
	public void imprimeEmOrdem(){
		System.out.print("(");
		if (esq != null)
			esq.imprimeEmOrdem();
		System.out.print(val);
		if (dir != null)
			dir.imprimeEmOrdem();
		System.out.print(")");
	}
	public static Integer retornaSomaSubArvore(Arvbin<Integer> no){
		if(no == null)
			return 0;

		int soma = 0;

		soma += retornaSomaSubArvore(no.esq);

		soma += retornaSomaSubArvore(no.dir);

		soma += no.val;

		return soma;
	}
	public boolean eSimilar(Arvbin<T> arvore){
		if((dir == null && esq == null) && (arvore.dir == null && arvore.esq == null))
			return true;

		if((dir == null && arvore.dir != null) || (esq != null && arvore.esq == null))
			return false;

		if((dir != null && arvore.dir == null) || (esq == null && arvore.esq != null))
			return false;

		boolean similarEsquerda = esq.eSimilar(arvore.esq);

		boolean similarDireita = dir.eSimilar(arvore.dir);

		return similarDireita && similarEsquerda;
	}
	public void delete(T valor){
		Arvbin<T> noRemover = busca(valor);

		if (noRemover == null) {
			System.out.println("O valor não está presente na árvore.");
			return;
		}

		Arvbin<T> pai = buscaPai(valor);

		if (noRemover.esq == null && noRemover.dir == null) {
			if (pai == null) {
				val = null;
			} else if (noRemover == pai.esq) {
				pai.esq = null;
			} else {
				pai.dir = null;
			}
		}

		else if (noRemover.esq == null) {
			if (pai == null) {
				val = noRemover.dir.val;
				esq = noRemover.dir.esq;
				dir = noRemover.dir.dir;
			} else if (noRemover == pai.esq) {
				pai.esq = noRemover.dir;
			} else {
				pai.dir = noRemover.dir;
			}
		} else if (noRemover.dir == null) {
			if (pai == null) {
				val = noRemover.esq.val;
				esq = noRemover.esq.esq;
				dir = noRemover.esq.dir;
			} else if (noRemover == pai.esq) {
				pai.esq = noRemover.esq;
			} else {
				pai.dir = noRemover.esq;
			}
		}

		else {
			Arvbin<T> sucessor = noRemover.dir;
			Arvbin<T> paiSucessor = noRemover;

			while (sucessor.esq != null) {
				paiSucessor = sucessor;
				sucessor = sucessor.esq;
			}

			noRemover.val = sucessor.val;

			if (sucessor == paiSucessor.esq)
				paiSucessor.esq = sucessor.dir;
			else
				paiSucessor.dir = sucessor.dir;
		}
	}
	public Arvbin<T> buscaPai(T valor){
		Arvbin<T> ret;

		if ((esq != null && esq.val.compareTo(valor) == 0) || (dir != null && dir.val.compareTo(valor) == 0))
			return this;


		if (esq != null){
			ret = esq.buscaPai(valor);

			if (ret != null)
				return ret;
		}

		if (dir != null)
			return dir.buscaPai(valor);

		return null;
	}
	public void tornaRaiz(T valor){
		if (busca(valor) == null) {
			return;
		}

		if (buscaPai(valor) == null) {
			return;
		}

		this.delete(valor);

		T valAtual = this.val;
		this.val = valor;

		Arvbin<T> raizAnterior = new Arvbin<T>(valAtual);

		raizAnterior.esq = this.esq;
		raizAnterior.dir = this.dir;
		this.esq = raizAnterior;
		this.dir = null;
	}
	public boolean eBalanceada(){
		if (esq == null && dir == null)
			return true;

		int alturaEsq;
		int alturaDir;
		boolean balanco;

		if (esq != null && dir == null){
			alturaEsq = esq.calculaAltura();
			alturaDir = 0;
		}else if (esq == null && dir != null){
			alturaDir = dir.calculaAltura();
			alturaEsq = 0;
		}else{
			alturaEsq = esq.calculaAltura();
			alturaDir = dir.calculaAltura();
		}

		if ((alturaEsq - alturaDir) >= -1 && (alturaEsq - alturaDir) <= 1 ){
			balanco = true;
		}else
			balanco = false;

		boolean balancoEsq, balancoDir;

		if (esq != null && dir == null){
			balancoEsq = esq.eBalanceada();
			balancoDir = true;
		}else if (esq == null && dir != null){
			balancoEsq = true;
			balancoDir = dir.eBalanceada();
		}else{
			balancoEsq = esq.eBalanceada();
			balancoDir = dir.eBalanceada();
		}

		return balanco && balancoEsq && balancoDir;
	}

}