import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;


public abstract class HuffmanAbstract {

	protected Nodo primero;
	protected Nodo raizArbol;
	protected byte tabla[];
	protected String listaCodigos[];
	protected int tamano;
	protected int cont;

	public HuffmanAbstract() {
		primero = null;
		tamano = cont = 0;
	}

	public int readOrigen(InputStream lector) throws IOException, FileNotFoundException {
		int byteLeido;
		int numBytes = 0;
		if (lector != null) {
			int tabla[] = new int[256];
			do {
				byteLeido = lector.read();
				if (byteLeido != -1) {
					tabla[byteLeido]++;
					numBytes++;
				}
			} while (byteLeido != -1);
			generarHuffman(tabla);
		}
		return numBytes;
	}

	public void agregarElemento(Nodo miNodo) {
		if (primero == null) {
			primero = miNodo;
		} else {
			Nodo aux = primero;
			while (aux.getDerecha() != null)
				aux = aux.getDerecha();
			aux.setDerecha(miNodo);
			miNodo.setIzquierda(aux);
		}
		tamano++;
	}


	public void ordenar() {
		
		Nodo aux1 = primero;
		Nodo aux2 = aux1;
		
		int tempFrec;
		char tempLetra;
		boolean tempIsHoja;
		Nodo tempHijIzq;
		Nodo tempHijDer;

		while (aux1 != null) {

			while (aux2.getDerecha() != null) {
				if (aux2.getFrecuencia() > aux2.getDerecha().getFrecuencia()) {
					
					tempFrec = aux2.getFrecuencia();
					tempLetra = aux2.getLetra();
					tempIsHoja = aux2.isHoja();
					tempHijIzq = aux2.getHijoIzq();
					tempHijDer = aux2.getHijoDer();
					
					aux2.setFrecuencia(aux2.getDerecha().getFrecuencia());
					aux2.setLetra(aux2.getDerecha().getLetra());
					aux2.setTipo(aux2.getDerecha().isHoja());
					aux2.setHijoIzq(aux2.getDerecha().getHijoIzq());
					aux2.setHijoDer(aux2.getDerecha().getHijoDer());
					
					aux2.getDerecha().setFrecuencia(tempFrec);
					aux2.getDerecha().setLetra(tempLetra);
					aux2.getDerecha().setTipo(tempIsHoja);
					aux2.getDerecha().setHijoIzq(tempHijIzq);
					aux2.getDerecha().setHijoDer(tempHijDer);
				}
				
				aux2 = aux2.getDerecha();
			}
			aux2 = primero;
			aux1 = aux1.getDerecha();
		}
	}

	public void generarArbol() {
		while (this.primero.getDerecha() != null) {
			Nodo firstNode = this.primero;
			Nodo secondNode = firstNode.getDerecha();
			Nodo thirdNode;
			
			if (secondNode.getDerecha() != null) {
				thirdNode = secondNode.getDerecha();
			} else {
				thirdNode = null;
			}
			
			firstNode.setIzquierda(null);
			firstNode.setDerecha(null);
			secondNode.setIzquierda(null);
			secondNode.setDerecha(null);

			Nodo rootNode = new Nodo(firstNode, secondNode, firstNode.getFrecuencia() + secondNode.getFrecuencia());
			rootNode.setDerecha(thirdNode);
			if (thirdNode != null) {
				thirdNode.setIzquierda(rootNode);
			}
			this.primero = rootNode;

			ordenar();
		}
		this.raizArbol = this.primero;
		
	}


	public Nodo ubicarNodo(Nodo miNodo, Nodo aux2) {
		Nodo aux = aux2;

		while (aux != null) {
			if (miNodo.getFrecuencia() <= aux.getFrecuencia()) {
				aux.getIzquierda().setDerecha(miNodo);
				miNodo.setIzquierda(aux.getIzquierda());
				aux.setIzquierda(miNodo);
				miNodo.setDerecha(aux);
				return miNodo;
			}
			if (aux.getDerecha() == null) {
				aux.setDerecha(miNodo);
				miNodo.setIzquierda(aux);
				break;
			}
			aux = aux.getDerecha();
		}
		return miNodo;
	}


	public void generarListaCodigo(Nodo miNodo, String cadena) {
		if (miNodo != null) {
			if (miNodo.isHoja()) {
				if (cadena.isEmpty()) { // En el caso de que haya un solo nodo en el �rbol
					listaCodigos[cont] = "0";
				} else {
					listaCodigos[cont] = cadena;
				}
				tabla[cont] = (byte) miNodo.getLetra();
				cont++;
			}
			generarListaCodigo(miNodo.getHijoIzq(), cadena + "0");
			generarListaCodigo(miNodo.getHijoDer(), cadena + "1");
		}
	}


	public void generarHuffman(int tabla[]) {
		for (int i = 0; i < tabla.length; i++) {
			if (tabla[i] != 0) {
				System.out.println("Añadido: " + (char) i + " Freq: " + tabla[i]);
				Nodo aux = new Nodo((char) i, tabla[i], true);
				agregarElemento(aux);
			}
		}
		ordenar();
		this.imprimirLista(this.primero);
		generarArbol();
		this.tabla = new byte[tamano];
		listaCodigos = new String[tamano];
		generarListaCodigo(raizArbol, "");
	}


	public String getCodigo(byte valor) {
		for (int i = 0; i < tamano; i++)
			if (tabla[i] == valor)
				return listaCodigos[i];
		return null;
	}


	public String getCodigo(int i) {
		if ((i >= 0) && (i < tamano))
			return listaCodigos[i];
		else
			return null;

	}


	public String getCodigo(String st) {
		String stCodificada = "";
		if (st != null) {
			for (int i = 0; i < st.length(); i++) {
				byte valor = (byte) st.charAt(i);
				String codigo = getCodigo(valor);
				if (codigo != null) {
					stCodificada += codigo;
				} else {
					stCodificada += "_"; // Error, codigo inexistente
				}
			}
		}
		return stCodificada;
	}


	public byte[] getTabla() {
		return tabla;
	}


	public int getTamano() {
		return tamano;
	}


	protected void imprimirArbol(Nodo nodo, String offset) {
		if (nodo != null) {
			System.out.println(offset + nodo);
			imprimirArbol(nodo.getHijoIzq(), offset + "|   ");
			imprimirArbol(nodo.getHijoDer(), offset + "|   ");
		}
	}


	protected void imprimirLista(Nodo nodo) {
		if (nodo != null) {
			System.out.println(nodo);
			imprimirLista(nodo.getDerecha());
		}
	}


	protected void imprimirListaCodigos() {
		if (listaCodigos != null) {
			for (int i = 0; i < listaCodigos.length; i++) {
				System.out
						.println("simbolo = <" + (char) tabla[i] + "> codigo = " + listaCodigos[i]);
			}
		}
	}

}
