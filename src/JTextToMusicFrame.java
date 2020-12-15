import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.AbstractAction;

import java.awt.event.ActionEvent;

import javax.swing.Action;

//ADD BY CHRYS
import javax.swing.JFileChooser;

import org.jfugue.Player;


public class JTextToMusicFrame extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	private final Action playAction = new playAction();
	private final Action pauseAction = new pauseAction();
	private final Action searchAction = new SearchAction();
	
	//ADD BY CHRYS
	private  JFileChooser dialog;
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					JTextToMusicFrame frame = new JTextToMusicFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public JTextToMusicFrame() {
		setTitle("Text2Music Converter");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		textField = new JTextField();
		textField.setBounds(5, 5, 424, 166);
		contentPane.add(textField);
		textField.setColumns(10);
		
		JButton playButton = new JButton("> Play");
		playButton.setAction(playAction);
		playButton.setBounds(10, 205, 89, 23);
		contentPane.add(playButton);
		
		JButton pauseButton = new JButton("|| Pause");
		pauseButton.setAction(pauseAction);
		pauseButton.setBounds(109, 205, 89, 23);
		contentPane.add(pauseButton);
		
		JButton stopButton = new JButton("[] Stop");
		stopButton.setBounds(208, 205, 89, 23);
		contentPane.add(stopButton);
		
		//ADD BY CHRYS
		JButton searchButton = new JButton("Search");
		searchButton.setAction(searchAction);
		searchButton.setBounds(307, 205, 89, 23);
		contentPane.add(searchButton);
		
	}
	
	private class playAction extends AbstractAction {
		public playAction() {
			putValue(NAME, "> Play");
			putValue(SHORT_DESCRIPTION, "Executa música");
		}
		public void actionPerformed(ActionEvent e) {			 			
			Player player = new Player();
			player.play( Translator.tranlateText( textField.getText() ) );
		}
	}
	private class pauseAction extends AbstractAction {
		public pauseAction() {
			putValue(NAME, "|| Pause");
			putValue(SHORT_DESCRIPTION, "Para a música");
		}
		public void actionPerformed(ActionEvent e) {
		}
	}
	
	//ADD BY CHRYS
	private class SearchAction extends AbstractAction {
		public SearchAction() {
			putValue(NAME, "Search");
			putValue(SHORT_DESCRIPTION, "JfileChooser");
		}
		public void actionPerformed(ActionEvent e) {
			String textAreaFromFile = LoadTxtFile.selectFile();
			if(textAreaFromFile == "null"){
				textField.setText("###ERROR ON FILE ## PLEASE VERIFY FILE## ERROR ON FILE###");
			}else{
				textField.setText(textAreaFromFile);
			}
		}
	}
}
