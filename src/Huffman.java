import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;


public class Huffman extends HuffmanAbstract {

	/*
	 * El metodo generarArbol() se encuentra en la clase HuffmanAbstract
	 * 
	 * El metodo ordenar() fue modificado para que pudiera ordenar
	 * tambien los nodos que son raices de arboles
	 */

	public static void main(String arg[]) throws FileNotFoundException, IOException {
		HuffmanAbstract h = new Huffman();
		String text = "j'aime aller sur le bord de l'eau les jeudis ou les jours impairs";
		InputStream is = new ByteArrayInputStream(text.getBytes("UTF-8"));
		h.readOrigin(is);
		h.printTree(h.treeRoot,"");
		h.printCodeList();
	}


}
