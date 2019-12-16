public class Node {

	private char character;
	private int frequency;
	private boolean type;
	private Node left, right;
	private Node leftChild, rightChild;

	public Node(char character, int frequency, boolean type) {
		this.character = character;
		this.frequency = frequency;
		this.type = type;
	}

	public Node(Node leftChild, Node rightChild, int frequency) {
		this.leftChild = leftChild;
		this.rightChild = rightChild;
		this.frequency = frequency;
	}


	public Node getLeftChild() {
		return leftChild;
	}

	public void setLeftChild(Node leftChild) {
		this.leftChild = leftChild;
	}


	public Node getRightChild() {
		return rightChild;
	}


	public void setRightChild(Node rightChild) {
		this.rightChild = rightChild;
	}


	public Node getRight() {
		return right;
	}


	public void setRight(Node right) {
		this.right = right;
	}


	public Node getLeft() {
		return left;
	}


	public void setLeft(Node left) {
		this.left = left;
	}


	public char getCharacter() {
		return character;
	}


	public void setCharacter(char character) {
		this.character = character;
	}


	public int getFrequency() {
		return frequency;
	}


	public void setFrequency(int frequency) {
		this.frequency = frequency;
	}


	public boolean isLeaf() {
		return type;
	}


	public void setType(boolean type) {
		this.type = type;
	}

	public String toString() {
		return "Char: [" + getCharacter() + "] Freq: [" + getFrequency() + "]";
	}

}
