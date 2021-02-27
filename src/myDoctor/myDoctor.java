package myDoctor;

import java.io.File;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.nio.file.Files;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class myDoctor {
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		// 1- criar o socket

		Socket echoSocket = new Socket("127.0.0.1", 23456);
		
		//streams object input /output para comunicar com o server
		
		ObjectInputStream inStream = new ObjectInputStream(echoSocket.getInputStream());
		ObjectOutputStream outStream = new ObjectOutputStream(echoSocket.getOutputStream());
		
		//escrever no socket duas strings
		String id = "Security";
		String password = "Verify";
		
		// print line do boolean para saber o que aconteceu
		System.out.println("Cliente inicializado!");
		
		
		//Recebe os comandos
		/*
		Scanner scan = new Scanner(System.in); 
		System.out.println("Inserir opcoes na consola:");
		Map<String, String> option = userOptions.validate(scan.nextLine());
		*/
		Map<String, String> option = userOptions.validate("-u miguel -a 12345:123 -p 1234 -md");
		
		String fileName = "pd.pdf"; //variavél temporária, conteúdo vai ser obtido pelo input do cliente no futuro
		
        File file = new File("clientDirectory/" + fileName);
        byte[] content = Files.readAllBytes(file.toPath());
        long fileBytes = file.length();
        
		outStream.writeObject(id);
		outStream.writeObject(password);
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

