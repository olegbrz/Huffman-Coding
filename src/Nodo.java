public class Nodo {

	private char letra;
	private int frecuencia;
	private boolean tipo;
	private Nodo derecha, izquierda;
	private Nodo hijoIzq, hijoDer;

	public Nodo(char letra, int frecuencia, boolean tipo) {
		this.letra = letra;
		this.frecuencia = frecuencia;
		this.tipo = tipo;
	}

	public Nodo(Nodo hijoIzq, Nodo hijoDer, int freq) {
		this.hijoIzq = hijoIzq;
		this.hijoDer = hijoDer;
		this.frecuencia = freq;
	}


	public Nodo getHijoIzq() {
		return hijoIzq;
	}

	public void setHijoIzq(Nodo hijoIzq) {
		this.hijoIzq = hijoIzq;
	}


	public Nodo getHijoDer() {
		return hijoDer;
	}


	public void setHijoDer(Nodo hijoDer) {
		this.hijoDer = hijoDer;
	}


	public Nodo getDerecha() {
		return derecha;
	}


	public void setDerecha(Nodo derecha) {
		this.derecha = derecha;
	}


	public Nodo getIzquierda() {
		return izquierda;
	}


	public void setIzquierda(Nodo izquierda) {
		this.izquierda = izquierda;
	}


	public char getLetra() {
		return letra;
	}


	public void setLetra(char letra) {
		this.letra = letra;
	}


	public int getFrecuencia() {
		return frecuencia;
	}


	public void setFrecuencia(int frecuencia) {
		this.frecuencia = frecuencia;
	}


	public boolean isHoja() {
		return tipo;
	}


	public void setTipo(boolean tipo) {
		this.tipo = tipo;
	}

	public String toString() {
		return "letra = <" + getLetra() + ">" + " frecuencia=" + getFrecuencia();
	}

}
