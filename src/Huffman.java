import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;


public class Huffman {

	protected Node first;
	protected Node treeRoot;
	protected byte table[];
	protected String codeList[];
	protected int size;
	protected int iter;

	public Huffman() {
		first = null;
		size = iter = 0;
	}

	public int readOrigin(InputStream reader) throws IOException, FileNotFoundException {
		int readByte;
		int numBytes = 0;
		if (reader != null) {
			int table[] = new int[256];
			do {
				readByte = reader.read();
				if (readByte != -1) {
					table[readByte]++;
					numBytes++;
				}
			} while (readByte != -1);
			generateHuffman(table);
		}
		return numBytes;
	}

	public void addElement(Node newNode) {
		if (first == null) {
			first = newNode;
		} else {
			Node aux = first;
			while (aux.getRight() != null)
				aux = aux.getRight();
			aux.setRight(newNode);
			newNode.setLeft(aux);
		}
		size++;
	}


	public void sort() {
		
		Node aux1 = first;
		Node aux2 = aux1;
		
		int tempFreq;
		char tempChar;
		boolean tempIsLeaf;
		Node tempLeftChild;
		Node tempRightChild;

		while (aux1 != null) {

			while (aux2.getRight() != null) {
				if (aux2.getFrequency() > aux2.getRight().getFrequency()) {
					
					tempFreq = aux2.getFrequency();
					tempChar = aux2.getCharacter();
					tempIsLeaf = aux2.isLeaf();
					tempLeftChild = aux2.getLeftChild();
					tempRightChild = aux2.getRightChild();
					
					aux2.setFrequency(aux2.getRight().getFrequency());
					aux2.setCharacter(aux2.getRight().getCharacter());
					aux2.setType(aux2.getRight().isLeaf());
					aux2.setLeftChild(aux2.getRight().getLeftChild());
					aux2.setRightChild(aux2.getRight().getRightChild());
					
					aux2.getRight().setFrequency(tempFreq);
					aux2.getRight().setCharacter(tempChar);
					aux2.getRight().setType(tempIsLeaf);
					aux2.getRight().setLeftChild(tempLeftChild);
					aux2.getRight().setRightChild(tempRightChild);
				}
				
				aux2 = aux2.getRight();
			}
			aux2 = first;
			aux1 = aux1.getRight();
		}
	}

	public void generateTree() {
		while (this.first.getRight() != null) {
			Node firstNode = this.first;
			Node secondNode = firstNode.getRight();
			Node thirdNode;
			
			if (secondNode.getRight() != null) {
				thirdNode = secondNode.getRight();
			} else {
				thirdNode = null;
			}
			
			firstNode.setLeft(null);
			firstNode.setRight(null);
			secondNode.setLeft(null);
			secondNode.setRight(null);

			Node rootNode = new Node(firstNode, secondNode, firstNode.getFrequency() + secondNode.getFrequency());
			rootNode.setRight(thirdNode);
			if (thirdNode != null) {
				thirdNode.setLeft(rootNode);
			}
			this.first = rootNode;

			sort();
		}
		this.treeRoot = this.first;
		
	}


	public Node nodePosition(Node newNode, Node aux2) {
		Node aux = aux2;

		while (aux != null) {
			if (newNode.getFrequency() <= aux.getFrequency()) {
				aux.getLeft().setRight(newNode);
				newNode.setLeft(aux.getLeft());
				aux.setLeft(newNode);
				newNode.setRight(aux);
				return newNode;
			}
			if (aux.getRight() == null) {
				aux.setRight(newNode);
				newNode.setLeft(aux);
				break;
			}
			aux = aux.getRight();
		}
		return newNode;
	}


	public void generateCodeList(Node newNode, String string) {
		if (newNode != null) {
			if (newNode.isLeaf()) {
				if (string.isEmpty()) { // En el caso de que haya un solo nodo en el �rbol
					codeList[iter] = "0";
				} else {
					codeList[iter] = string;
				}
				table[iter] = (byte) newNode.getCharacter();
				iter++;
			}
			generateCodeList(newNode.getLeftChild(), string + "0");
			generateCodeList(newNode.getRightChild(), string + "1");
		}
	}


	public void generateHuffman(int table[]) {
		for (int i = 0; i < table.length; i++) {
			if (table[i] != 0) {
				Node aux = new Node((char) i, table[i], true);
				addElement(aux);
			}
		}
		sort();
		System.out.println("\nList of characters:");
		this.printList(this.first);
		generateTree();
		this.table = new byte[size];
		codeList = new String[size];
		generateCodeList(treeRoot, "");
	}


	public String getCode(byte value) {
		for (int i = 0; i < size; i++)
			if (table[i] == value)
				return codeList[i];
		return null;
	}


	public String getCode(int i) {
		if ((i >= 0) && (i < size))
			return codeList[i];
		else
			return null;

	}


	public String getCode(String string) {
		String encodedString = "";
		if (string != null) {
			for (int i = 0; i < string.length(); i++) {
				byte value = (byte) string.charAt(i);
				String code = getCode(value);
				if (code != null) {
					encodedString += code;
				} else {
					encodedString += "_"; // Error, codigo inexistente
				}
			}
		}
		return encodedString;
	}


	public byte[] getTable() {
		return table;
	}


	public int getSize() {
		return size;
	}


	protected void printTree(Node node, String offset) {
		if (node != null) {
			System.out.println(offset + node);
			printTree(node.getLeftChild(), offset + "|    ");
			printTree(node.getRightChild(), offset + "|    ");
		}
	}


	protected void printList(Node node) {
		if (node != null) {
			System.out.println(" - " + node);
			printList(node.getRight());
		}
	}


	protected void printCodeList() {
		System.out.println("\nCode list:");
		if (codeList != null) {
			for (int i = 0; i < codeList.length; i++) {
				System.out.println(" - Character: [" + (char)table[i] + "] Code: [" + codeList[i] + "]");
			}
		}
	}

	public static void main(String arg[]) throws FileNotFoundException, IOException {
		Huffman h = new Huffman();
		String text = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nullam nunc quam, eleifend ac sapien eget, pulvinar fermentum diam. Vestibulum volutpat lacus et urna sollicitudin facilisis. Nunc accumsan eu dolor vitae ornare. Duis libero orci, gravida ut auctor id, vehicula eget felis. Suspendisse consequat orci id felis feugiat luctus. Interdum et malesuada fames ac ante ipsum primis in faucibus. Proin dapibus ultricies cursus. Etiam congue hendrerit sem. Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vestibulum tortor lorem, pharetra aliquam lectus nec, iaculis mattis orci. Ut volutpat vel lorem in mollis. Praesent volutpat elit sit amet velit scelerisque, at consequat quam egestas. Mauris luctus tellus in augue vulputate ultricies. Quisque tincidunt lacinia lectus, molestie hendrerit turpis tincidunt sit amet. Nunc malesuada vel velit eu aliquam. Vestibulum ut vestibulum nisl.";
		InputStream is = new ByteArrayInputStream(text.getBytes("UTF-8"));
		h.readOrigin(is);
		h.printTree(h.treeRoot,"");
		h.printCodeList();
	}

}
