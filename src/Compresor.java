import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class Compresor {

	private long numBytes;
	private String rutaOrigen, rutaDestino;

	public Compresor(String rutaOrigen, String rutaDestino) {

		this.rutaOrigen = rutaOrigen;
		this.rutaDestino = rutaDestino;
	}


	public void compress() throws IOException, FileNotFoundException {

		String temp = "", buffer = "", code = "";
		int byteLeido = 0, tam, parteCod;
		byte tablaTemp[];
		HuffmanAbstract miHuffman = new Huffman();
		FileInputStream lector;
		FileOutputStream escritor;

		lector = new FileInputStream(rutaOrigen);
		numBytes = miHuffman.readOrigen(lector);
		lector.close();
		escritor = new FileOutputStream(rutaDestino);
		if (numBytes > 0) {
			lector = new FileInputStream(rutaOrigen);
			for (int i = 0; i < 4; i++) {
				escritor.write((byte) (numBytes % 256));
				numBytes /= 256;
			}

			tablaTemp = miHuffman.getTabla();

			tam = miHuffman.getTamano();
			escritor.write(tam);

			for (int i = 0; i < tam; i++) {
				escritor.write(tablaTemp[i]);
				code = miHuffman.getCodigo(i);
				parteCod = Integer.parseInt(code, 2);
				for (int j = 0; j < 4; j++) {
					escritor.write((byte) (parteCod % 256));
					parteCod /= 256;
				}

				escritor.write(numZero(code));
			}

			byteLeido = lector.read();
			while (byteLeido != -1) {
				code = miHuffman.getCodigo((byte) byteLeido);
				buffer += code;
				while (buffer.length() >= 8) {
					temp = buffer.substring(0, 8);
					buffer = buffer.substring(8);
					escritor.write(Integer.parseInt(temp, 2));
				}
				byteLeido = lector.read();
			}
			lector.close();

			if (buffer.length() > 0) {
				for (int j = buffer.length(); j < 8; j++)
					buffer += "0";
				escritor.write(Integer.parseInt(buffer, 2));
			}
		}
		escritor.close();
	}

	private int numZero(String code) {
		int cont = 0;
		while ((cont < code.length() - 1) && (code.charAt(cont) == '0')) {
			cont++;
		}
		return cont;
	}

}
