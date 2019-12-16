import java.awt.EventQueue;

public class Aplication {


	public static void main(String[] args) {

		EventQueue.invokeLater(new Runnable() {
			public void run() {
				@SuppressWarnings("unused")
				WindowHuffman newWindow = new WindowHuffman();
			}
		});

	}

}
