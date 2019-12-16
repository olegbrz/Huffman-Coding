import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class Decompresor {

	private String rutaOrigen;
	private String rutaDestino;

	public Decompresor(String rutaOrigen, String rutaDestino) {

		this.rutaOrigen = rutaOrigen;
		this.rutaDestino = rutaDestino;
	}


	public void deCompress() throws FileNotFoundException, IOException {

		String buffer = "", auxTemp, codigos[], zerosStr = "";
		int tamano = 0, byteLeido = 0, codigo, zeros = 0, i, j, numBytes = 0, cont = 0;
		byte tablaTemp[];
		FileOutputStream escritor;
		FileInputStream lector;
		boolean encontrado = false;

		lector = new FileInputStream(rutaOrigen);
		for (int k = 0; k < 4; k++)
			numBytes += (lector.read() * Math.pow(256, k));


		tamano = lector.read();
		if (tamano != -1) {


			tablaTemp = new byte[tamano];
			codigos = new String[tamano];
			escritor = new FileOutputStream(rutaDestino);

			for (i = 0; i < tamano; i++) {
				tablaTemp[i] = (byte) lector.read();
				codigo = 0;
				for (j = 0; j < 4; j++) {
					codigo += (lector.read() * Math.pow(256, j));
				}
				codigos[i] = Integer.toBinaryString(codigo);
				zeros = lector.read();
				zerosStr = "";
				for (j = 0; j < zeros; j++)
					zerosStr += "0";
				codigos[i] = zerosStr + codigos[i];
			}

			byteLeido = lector.read();
			buffer = SerieBits(byteLeido);
			auxTemp = "";
			i = 0;
			while (byteLeido != -1 && cont < numBytes) {
				auxTemp += buffer.charAt(i);
				j = 0;
				while ((j < codigos.length) && (!encontrado)) {
					if (auxTemp.equals(codigos[j])) {
						escritor.write(tablaTemp[j]);
						cont++; 
						encontrado = true;
					}
					j++;
				}
				i++;

				if (encontrado) {
					buffer = buffer.substring(i);
					i = 0;
					auxTemp = "";
					encontrado = false;
				}
				if (i == buffer.length()) {
					byteLeido = lector.read();
					buffer += SerieBits(byteLeido);

				}
			}
			escritor.close();
		}
		lector.close();

	}

	private String SerieBits(int byteLeido) {
		String temp = Integer.toBinaryString(byteLeido);
		while (temp.length() < 8)
			temp = "0" + temp;
		return temp;
	}
}

