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

}