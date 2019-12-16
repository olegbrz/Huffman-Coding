import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.swing.JButton;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import javax.swing.JTextField;


@SuppressWarnings("serial")
public class VentanaHuffman extends JFrame implements ActionListener {


	private JTextField rutaOrigen;
	private JButton openOrigen;
	private JRadioButton opDecompress;
	private JRadioButton opCompress;
	private JButton btnSalir;
	private JButton btnOperar;
	private JLabel tDestino;
	private JLabel tOrigen;
	private JLabel title;
	private JButton openDestino;
	private JTextField rutaDestino;
	private String rutaO, rutaD;
	private Compresor miCompresor;
	private Decompresor miDecompresor;

	public VentanaHuffman() {
		initGUI();
	}

	private void initGUI() {
		try {

			getContentPane().setLayout(null);
			getContentPane().setBackground(new java.awt.Color(0, 0, 0));
			this.setTitle("Compresor de Archivos");

			rutaOrigen = new JTextField();
			getContentPane().add(rutaOrigen);
			rutaOrigen.setBounds(21, 70, 308, 21);


			openOrigen = new JButton();
			getContentPane().add(openOrigen);
			openOrigen.setText("...");
			openOrigen.setBounds(343, 70, 35, 21);
			openOrigen.addActionListener(this);


			rutaDestino = new JTextField();
			getContentPane().add(rutaDestino);
			rutaDestino.setBounds(21, 112, 308, 21);


			openDestino = new JButton();
			getContentPane().add(openDestino);
			openDestino.setText("...");
			openDestino.setBounds(343, 112, 35, 21);
			openDestino.addActionListener(this);


			title = new JLabel();
			getContentPane().add(title);
			title.setText("Compresor de Archivos - Huffman");
			title.setBounds(21, 21, 210, 28);
			title.setForeground(new java.awt.Color(255, 255, 255));
			title.setFont(new java.awt.Font("Tahoma", 1, 11));


			tOrigen = new JLabel();
			getContentPane().add(tOrigen);
			tOrigen.setText("Origen:");
			tOrigen.setBounds(21, 49, 49, 21);
			tOrigen.setForeground(new java.awt.Color(255, 255, 255));


			tDestino = new JLabel();
			getContentPane().add(tDestino);
			tDestino.setText("Destino:");
			tDestino.setBounds(21, 91, 63, 21);
			tDestino.setForeground(new java.awt.Color(255, 255, 255));


			btnOperar = new JButton();
			getContentPane().add(btnOperar);
			btnOperar.setText("- Operar -");
			btnOperar.setBounds(260, 147, 91, 21);
			btnOperar.addActionListener(this);


			btnSalir = new JButton();
			getContentPane().add(btnSalir);
			btnSalir.setText("- Salir -");
			btnSalir.setBounds(360, 147, 77, 21);
			btnSalir.addActionListener(this);


			opCompress = new JRadioButton();
			getContentPane().add(opCompress);
			opCompress.setText("Comprimir");
			opCompress.setBounds(14, 147, 100, 21);
			opCompress.setForeground(new java.awt.Color(255, 255, 255));
			opCompress.setBackground(new java.awt.Color(0, 0, 0));
			opCompress.addActionListener(this);


			opDecompress = new JRadioButton();
			getContentPane().add(opDecompress);
			opDecompress.setText("Descomprimir");
			opDecompress.setBounds(110, 147, 140, 21);
			opDecompress.setForeground(new java.awt.Color(255, 255, 255));
			opDecompress.setBackground(new java.awt.Color(0, 0, 0));
			opDecompress.addActionListener(this);

			setSize(550, 223);
			setResizable(false);
			setVisible(true);
			setDefaultCloseOperation(EXIT_ON_CLOSE);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void actionPerformed(ActionEvent e) {

		if (e.getSource() == opCompress) {
			opDecompress.setSelected(false);
		}
		if (e.getSource() == opDecompress) {
			opCompress.setSelected(false);
		}
		if (e.getSource() == openOrigen) {

			JFileChooser file = new JFileChooser();
			file.setDialogTitle("Abrir fichero");
			file.setMultiSelectionEnabled(false);

			int res = file.showOpenDialog(this);

			if (res == JFileChooser.APPROVE_OPTION) {
				rutaO = file.getSelectedFile().getAbsolutePath();
				rutaOrigen.setText(rutaO);
			}
		}

		if (e.getSource() == openDestino) {

			JFileChooser file = new JFileChooser();
			file.setDialogTitle("Guardar fichero");
			file.setMultiSelectionEnabled(false);

			int res = file.showSaveDialog(this);

			if (res == JFileChooser.APPROVE_OPTION) {
				rutaD = file.getSelectedFile().getAbsolutePath();
				rutaDestino.setText(rutaD);
			}
		}

		if (e.getSource() == btnOperar) {

			if (opCompress.isSelected()) {
				try {
					miCompresor = new Compresor(rutaOrigen.getText(), rutaDestino.getText());
					miCompresor.compress();
					JOptionPane.showMessageDialog(null,
							"La operaci�n se ha terminado correctamente!!!");
				} catch (FileNotFoundException e1) {
					JOptionPane.showMessageDialog(null, "Fichero no encontrado!!!");
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			} else {
				if (opDecompress.isSelected()) {

					miDecompresor = new Decompresor(rutaOrigen.getText(), rutaDestino.getText());

					try {
						miDecompresor.deCompress();
						JOptionPane.showMessageDialog(null,
								"La operaci�n se ha terminado correctamente!!!");

					} catch (FileNotFoundException e1) {
						JOptionPane.showMessageDialog(null, "Fichero no encontrado!!!");
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				} else
					JOptionPane.showMessageDialog(null, "Seleccione una operaci�n.");
			}
		}
		if (e.getSource() == btnSalir) {
			System.exit(0);
		}

	}


}
