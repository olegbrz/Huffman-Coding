import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class Decompressor {

	private String originDir;
	private String destinationDir;

	public Decompressor(String originDir, String destinationDir) {

		this.originDir = originDir;
		this.destinationDir = destinationDir;
	}


	public void decompress() throws FileNotFoundException, IOException {

		String buffer = "", auxTemp, codes[], zerosStr = "";
		int size = 0, readByte = 0, code, zeros = 0, i, j, numBytes = 0, iter = 0;
		byte tempTable[];
		FileOutputStream writer;
		FileInputStream reader;
		boolean found = false;

		reader = new FileInputStream(originDir);
		for (int k = 0; k < 4; k++)
			numBytes += (reader.read() * Math.pow(256, k));


		size = reader.read();
		if (size != -1) {


			tempTable = new byte[size];
			codes = new String[size];
			writer = new FileOutputStream(destinationDir);

			for (i = 0; i < size; i++) {
				tempTable[i] = (byte) reader.read();
				code = 0;
				for (j = 0; j < 4; j++) {
					code += (reader.read() * Math.pow(256, j));
				}
				codes[i] = Integer.toBinaryString(code);
				zeros = reader.read();
				zerosStr = "";
				for (j = 0; j < zeros; j++)
					zerosStr += "0";
				codes[i] = zerosStr + codes[i];
			}

			readByte = reader.read();
			buffer = SerieBits(readByte);
			auxTemp = "";
			i = 0;
			while (readByte != -1 && iter < numBytes) {
				auxTemp += buffer.charAt(i);
				j = 0;
				while ((j < codes.length) && (!found)) {
					if (auxTemp.equals(codes[j])) {
						writer.write(tempTable[j]);
						iter++; 
						found = true;
					}
					j++;
				}
				i++;

				if (found) {
					buffer = buffer.substring(i);
					i = 0;
					auxTemp = "";
					found = false;
				}
				if (i == buffer.length()) {
					readByte = reader.read();
					buffer += SerieBits(readByte);

				}
			}
			writer.close();
		}
		reader.close();

	}

	private String SerieBits(int readByte) {
		String temp = Integer.toBinaryString(readByte);
		while (temp.length() < 8)
			temp = "0" + temp;
		return temp;
	}
}

