import java.io.*;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

public abstract class LoadTxtFile{

	private static final String extension = ".txt";
	private static final String sucess = "Arquivo Carregado!";
	private static final String errorFile = "Arquivo Invalido!";
	private static final String missFile = "Nenhum arquivo Selecionado!";
	private static final String emptyFIle = "Arquivo vazio ou com erros!";
	private static String selectFile;



	public static String selectFile(){
		String stringFromTxt;
		JFileChooser dialog = new JFileChooser();

		if(dialog.showOpenDialog(null) == JFileChooser.APPROVE_OPTION){
			selectFile = dialog.getSelectedFile().getAbsolutePath();

			if(selectFile.endsWith(extension)){
				JOptionPane.showMessageDialog(null,sucess);
				stringFromTxt = fileRead();
				if(stringFromTxt == null){
					JOptionPane.showMessageDialog(null,emptyFIle);
					return "null";
				}else{
					return stringFromTxt;
				}
			}else{
				JOptionPane.showMessageDialog(null,errorFile);
				return "null";
			}
		}else{
			JOptionPane.showMessageDialog(null,missFile);
			return "null";
		}
	}


	private static String fileRead(){
		BufferedReader buffer = bufferOpen();
		
		if(buffer == null){
			return null;
		}else{
			try{
				String theReturnString = "";
				String textFromBuffer;
				
				while(buffer.ready()){
					textFromBuffer = buffer.readLine();

					theReturnString = theReturnString + textFromBuffer + "\n";
				}
				return theReturnString;
			}catch(IOException e){
				JOptionPane.showMessageDialog(null,errorFile);
				return null;
			}
		}

	}

	private static BufferedReader bufferOpen(){
		try{
			FileReader file = new FileReader(selectFile);
			BufferedReader buffer = new BufferedReader(file);
			
			return buffer;
		}catch(FileNotFoundException e){
			JOptionPane.showMessageDialog(null,errorFile);
			return null;
		}
	}



	/**
	public String open(String fileName){
		String Name = fileName.concat(extension);
		
		File file;
		file = new File(Name);
		
		String result = "";

		try{
			Scanner Arq = new Scanner(file);
			while(Arq.hasNextLine()){
				String Line = Arq.nextLine();
				result = result + Line + "\n";
			}
			Arq.close();
		}catch(IOException ioe) {
			ioe.printStackTrace();
		}
		
		return result;
	}
	*/
}