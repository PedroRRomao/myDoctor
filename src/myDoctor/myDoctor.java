package myDoctor;

import java.io.File;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.nio.file.Files;
import java.util.Map;
import java.util.Scanner;

public class myDoctor {
	public static void main(String[] args) throws Exception {
		
		Socket echoSocket = new Socket("127.0.0.1", 23456);
		
		//streams object input /output para comunicar com o server
		
		ObjectInputStream inStream = new ObjectInputStream(echoSocket.getInputStream());
		ObjectOutputStream outStream = new ObjectOutputStream(echoSocket.getOutputStream());
		
		// print line do boolean para saber o que aconteceu
		System.out.println("Cliente inicializado!");
		
		
		//Recebe os comandos
		/*
		Scanner scan = new Scanner(System.in); 
		System.out.println("Inserir opcoes na consola:");
		Map<String, String> option = userOptions.validate(scan.nextLine());
		*/
		Map<String, String> param = userOptions.validate("-u 2 -a 127.0.0.1:23456 -p 123 -md 200 31");
		
		
		String fileName = "pd.pdf"; //variavél temporária, conteúdo vai ser obtido pelo input do cliente no futuro
		
        File file = new File("clientDirectory/" + fileName);
        byte[] content = Files.readAllBytes(file.toPath());
        long fileBytes = file.length();
        
		outStream.writeObject(param.get("u"));
		outStream.writeObject(param.get("p"));
        outStream.writeObject(fileBytes);
        outStream.writeObject(fileName);
        outStream.writeObject(content);

		//Close streams
		outStream.close();
		inStream.close();
		//Close socket
		echoSocket.close();
	}

	
}

