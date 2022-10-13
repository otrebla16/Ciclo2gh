package reto2;

import java.awt.EventQueue;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import java.io.File;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import javax.swing.JFileChooser;

/**
 * Esta es la clase principal del proyecto que mapea un archivo JSON y lo convierte en un CSV
 * @author Alberto Pérez Arroyo
 *
 */
public class MainFrame extends JFrame {

	/**
	 * Aplicación principal (GUI)
	 */
	private static final long serialVersionUID = 5684764222944364761L;
	private JPanel contentPane;
	private File jsonFile;
	
	/**
	 * Método para lanzar la aplicación.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainFrame frame = new MainFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Método para la creación del GUI mediante código automático de Eclipse.
	 */
	public MainFrame() {
		setTitle("Reto 2: Convertidor JSON a CSV");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 141);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblSelectFile = new JLabel("Selecciona el archivo JSON:");
		lblSelectFile.setBounds(10, 11, 301, 14);
		contentPane.add(lblSelectFile);
		
		JLabel lblPath = new JLabel("Archivo seleccionado:");
		lblPath.setBounds(10, 36, 301, 51);
		contentPane.add(lblPath);
		
		/*
		 * Cuando el usuario de clic en el botón seleccionar, se realizará la búsqueda del archivo en su computadora
		 */
		JButton btnSelectFile = new JButton("Seleccionar");
		btnSelectFile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser file_chooser = new JFileChooser(); //componente para que el usuario pueda seleccionar el archivo JSON.
				FileNameExtensionFilter filter = new FileNameExtensionFilter("Archivos JSON", "json", "JSON"); //preparamos el filtro para que solo busque archivos JSON.			
				file_chooser.setFileFilter(filter); //establecemos el filtro para que solo busque archivos JSON
				
				if(file_chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
					jsonFile = file_chooser.getSelectedFile(); //variable que almacena el archivo JSON seleccionado por el usuario.
					lblPath.setText("<html> Archivo seleccionado: <br>" + jsonFile.getAbsolutePath() + "</html>");
				}
			}
		});
		btnSelectFile.setBounds(321, 7, 103, 23);
		contentPane.add(btnSelectFile);
		
		/*
		 * Cuando el usuario de clic en el botón convertir, se ejecutará el procesamiento
		 */
		JButton btnConvert = new JButton("Convertir");
		btnConvert.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				if(jsonFile == null) {
					JOptionPane.showMessageDialog(null, "Primero seleccione un archivo", "Error", JOptionPane.ERROR_MESSAGE);
				}else {
					JsonConverter converter = new JsonConverter(); 
					converter.convertFile(jsonFile); //ejecutamos la conversión
				}			
			}
		});
		btnConvert.setBounds(321, 50, 103, 23);
		contentPane.add(btnConvert);
	}
}
