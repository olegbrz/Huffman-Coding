import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class Compressor {

	private long numBytes;
	private String originDir, destinationDir;

	public Compressor(String originDir, String destinationDir) {

		this.originDir = originDir;
		this.destinationDir = destinationDir;
	}


	public void compress() throws IOException, FileNotFoundException {

		String temp = "", buffer = "", code = "";
		int readByte = 0, size, codePart;
		byte tempTable[];
		Huffman newHuffman = new Huffman();
		FileInputStream reader;
		FileOutputStream writer;

		reader = new FileInputStream(originDir);
		numBytes = newHuffman.readOrigin(reader);
		reader.close();
		writer = new FileOutputStream(destinationDir);
		if (numBytes > 0) {
			reader = new FileInputStream(originDir);
			for (int i = 0; i < 4; i++) {
				writer.write((byte) (numBytes % 256));
				numBytes /= 256;
			}

			tempTable = newHuffman.getTable();

			size = newHuffman.getSize();
			writer.write(size);

			for (int i = 0; i < size; i++) {
				writer.write(tempTable[i]);
				code = newHuffman.getCode(i);
				codePart = Integer.parseInt(code, 2);
				for (int j = 0; j < 4; j++) {
					writer.write((byte) (codePart % 256));
					codePart /= 256;
				}

				writer.write(numZero(code));
			}

			readByte = reader.read();
			while (readByte != -1) {
				code = newHuffman.getCode((byte) readByte);
				buffer += code;
				while (buffer.length() >= 8) {
					temp = buffer.substring(0, 8);
					buffer = buffer.substring(8);
					writer.write(Integer.parseInt(temp, 2));
				}
				readByte = reader.read();
			}
			reader.close();

			if (buffer.length() > 0) {
				for (int j = buffer.length(); j < 8; j++)
					buffer += "0";
				writer.write(Integer.parseInt(buffer, 2));
			}
		}
		writer.close();
	}

	private int numZero(String code) {
		int iter = 0;
		while ((iter < code.length() - 1) && (code.charAt(iter) == '0')) {
			iter++;
		}
		return iter;
	}

}
