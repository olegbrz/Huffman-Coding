import java.awt.EventQueue;

public class Aplicacion {


	public static void main(String[] args) {

		EventQueue.invokeLater(new Runnable() {
			public void run() {
				@SuppressWarnings("unused")
				VentanaHuffman miVentana = new VentanaHuffman();
			}
		});

	}

}
