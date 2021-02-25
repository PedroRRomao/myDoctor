package myDoctor;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class myDoctor {
	public static void main(String[] args) throws UnknownHostException, IOException, ClassNotFoundException {
		// TODO Auto-generated method stub
		// 1- criar o socket

		Socket echoSocket = new Socket("127.0.0.1", 23456);
		
		//streams object input /output para comunicar com o server
		
		ObjectInputStream inStream = new ObjectInputStream(echoSocket.getInputStream());
		ObjectOutputStream outStream = new ObjectOutputStream(echoSocket.getOutputStream());
		
		//escrever no socket duas strings
		String inputU1 = "Security";
		String inputU2 = "Verify";
		
		outStream.writeObject(inputU1);
		outStream.writeObject(inputU2);
		
		
		
		//ler do socket um boolean
		
		Boolean fromServer = (Boolean) inStream.readObject();
		
		
		// print line do boolean para saber o que aconteceu
		System.out.println("Cliente inicializado!");
		
		Scanner scan = new Scanner(System.in);  // Create a Scanner object
		System.out.println("Insert options in the console:");

	    String option = scan.nextLine();  // Read user input
		
		//Close streams
		outStream.close();
		inStream.close();
		//Close socket
		echoSocket.close();
	}

	
}

