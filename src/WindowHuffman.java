import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JButton;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import javax.swing.JTextField;


@SuppressWarnings("serial")
public class WindowHuffman extends JFrame implements ActionListener {


	private JTextField originDir;
	private JButton openOrigin;
	private JRadioButton opDecompress;
	private JRadioButton opCompress;
	private JButton exitButton;
	private JButton startButton;
	private JLabel tDestination;
	private JLabel tOrigin;
	private JLabel title;
	private JButton openDestination;
	private JTextField destinationDir;
	private String oDir, dDir;
	private Compressor newCompressor;
	private Decompressor newDecompressor;
	private ImageIcon img;
	
	public WindowHuffman() {
		initGUI();
	}

	private void initGUI() {
		try {
			
			img = new ImageIcon("./assets/icon/icon.png");
			this.setIconImage(img.getImage());
			
			
			getContentPane().setLayout(null);
			getContentPane().setBackground(new java.awt.Color(245, 245, 245));
			this.setTitle("File encoder/decoder");

			originDir = new JTextField();
			getContentPane().add(originDir);
			originDir.setBounds(30, 70, 300, 21);


			openOrigin = new JButton();
			getContentPane().add(openOrigin);
			openOrigin.setText("Open");
			openOrigin.setBackground(new java.awt.Color(66,133,244));
			openOrigin.setForeground(new java.awt.Color(255,255,255));
			openOrigin.setFont(new java.awt.Font("Product Sans", 1, 12));
			openOrigin.setBounds(340, 70, 65, 21);
			openOrigin.addActionListener(this);


			destinationDir = new JTextField();
			getContentPane().add(destinationDir);
			destinationDir.setBounds(30, 112, 300, 21);


			openDestination = new JButton();
			getContentPane().add(openDestination);
			openDestination.setText("Save");
			openDestination.setBackground(new java.awt.Color(66,133,244));
			openDestination.setForeground(new java.awt.Color(255,255,255));
			openDestination.setFont(new java.awt.Font("Product Sans", 1, 12));
			openDestination.setBounds(340, 112, 65, 21);
			openDestination.addActionListener(this);


			title = new JLabel();
			getContentPane().add(title);
			title.setText("Huffman File Encoder/Decoder");
			title.setBounds(20, 10, 250, 28);
			title.setForeground(new java.awt.Color(0, 0, 0));
			title.setFont(new java.awt.Font("Product Sans", 1, 15));


			tOrigin = new JLabel();
			getContentPane().add(tOrigin);
			tOrigin.setText("Origin:");
			tOrigin.setFont(new java.awt.Font("Product Sans", 0, 14));
			tOrigin.setBounds(20, 49, 100, 21);
			tOrigin.setForeground(new java.awt.Color(0, 0, 0));


			tDestination = new JLabel();
			getContentPane().add(tDestination);
			tDestination.setText("Destination: ");
			tDestination.setFont(new java.awt.Font("Product Sans", 0, 14));
			tDestination.setBounds(20, 91, 100, 21);
			tDestination.setForeground(new java.awt.Color(0, 0, 0));


			startButton = new JButton();
			getContentPane().add(startButton);
			startButton.setText("Start");
			startButton.setBackground(new java.awt.Color(15,157,88));
			startButton.setForeground(new java.awt.Color(255,255,255));
			startButton.setFont(new java.awt.Font("Product Sans", 1, 12));
			startButton.setBounds(260, 180, 65, 21);
			startButton.addActionListener(this);


			exitButton = new JButton();
			getContentPane().add(exitButton);
			exitButton.setText("Exit");
			exitButton.setBackground(new java.awt.Color(219,68,55));
			exitButton.setForeground(new java.awt.Color(255,255,255));
			exitButton.setFont(new java.awt.Font("Product Sans", 1, 12));
			exitButton.setBounds(340, 180, 65, 21);
			exitButton.addActionListener(this);


			opCompress = new JRadioButton();
			getContentPane().add(opCompress);
			opCompress.setText("Compress");
			opCompress.setFont(new java.awt.Font("Product Sans", 1, 12));
			opCompress.setBounds(20, 140, 100, 21);
			opCompress.setForeground(new java.awt.Color(0, 0, 0));
			opCompress.setBackground(new java.awt.Color(245, 245, 245));
			opCompress.addActionListener(this);


			opDecompress = new JRadioButton();
			getContentPane().add(opDecompress);
			opDecompress.setText("Decompress");
			opDecompress.setFont(new java.awt.Font("Product Sans", 1, 12));
			opDecompress.setBounds(20, 160, 100, 21);
			opDecompress.setForeground(new java.awt.Color(0, 0, 0));
			opDecompress.setBackground(new java.awt.Color(245, 245, 245));
			opDecompress.addActionListener(this);

			setSize(450, 250);
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
		if (e.getSource() == openOrigin) {

			JFileChooser file = new JFileChooser();
			file.setDialogTitle("Open file");
			file.setMultiSelectionEnabled(false);

			int res = file.showOpenDialog(this);

			if (res == JFileChooser.APPROVE_OPTION) {
				oDir = file.getSelectedFile().getAbsolutePath();
				originDir.setText(oDir);
			}
		}

		if (e.getSource() == openDestination) {

			JFileChooser file = new JFileChooser();
			file.setDialogTitle("Save file");
			file.setMultiSelectionEnabled(false);

			int res = file.showSaveDialog(this);

			if (res == JFileChooser.APPROVE_OPTION) {
				dDir = file.getSelectedFile().getAbsolutePath();
				destinationDir.setText(dDir);
			}
		}

		if (e.getSource() == startButton) {

			if (opCompress.isSelected()) {
				try {
					newCompressor = new Compressor(originDir.getText(), destinationDir.getText());
					newCompressor.compress();
					JOptionPane.showMessageDialog(null, "Completed successfully!");
				} catch (FileNotFoundException e1) {
					JOptionPane.showMessageDialog(null, "File not found!");
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			} else {
				if (opDecompress.isSelected()) {

					newDecompressor = new Decompressor(originDir.getText(), destinationDir.getText());

					try {
						newDecompressor.decompress();
						JOptionPane.showMessageDialog(null, "Completed successfully!");

					} catch (FileNotFoundException e1) {
						JOptionPane.showMessageDialog(null, "File not found!");
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				} else
					JOptionPane.showMessageDialog(null, "Please, select an operation.");
			}
		}
		if (e.getSource() == exitButton) {
			System.exit(0);
		}

	}


}
