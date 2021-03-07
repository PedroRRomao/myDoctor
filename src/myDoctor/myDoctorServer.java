package myDoctor;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Files;
import java.util.Scanner;


public class myDoctorServer {
	public static void main(String[] args) {
		System.out.println("servidor: main");
		myDoctorServer server = new myDoctorServer();
		server.startServer();
	}

	public void startServer (){
		ServerSocket sSoc = null;
        
		try {
			sSoc = new ServerSocket(23456);
		} catch (IOException e) {
			System.err.println(e.getMessage());
			System.exit(-1);
		}
         
		while(true) {
			try {
				Socket inSoc = sSoc.accept();
				ServerThread newServerThread = new ServerThread(inSoc);
				newServerThread.start();
		    }
		    catch (IOException e) {
		        e.printStackTrace();
		    }
		    
		}
		//sSoc.close();
	}


	//Threads utilizadas para comunicacao com os clientes
	class ServerThread extends Thread {

		private Socket socket = null;

		ServerThread(Socket inSoc) {
			socket = inSoc;
			System.out.println("thread do server para cada cliente");
		}
 
		public void run(){
			try {
				
				File file = new File("serverDirectory/def_not_passwords.txt");
				if (file.createNewFile()) {
					FileWriter myWriter = new FileWriter("serverDirectory/def_not_passwords.txt");
					Scanner scan = new Scanner(System.in);  // Create a Scanner object
				    System.out.println("Creating Admin. Enter password:");

				    String admin_passwrd = scan.nextLine();  // Read user input
					myWriter.write("1; Administrador_base;"+admin_passwrd+";admin");
					myWriter.close();
					System.out.println("File created: " + file.getName());
				} else {
					System.out.println("File accessed.");
				}
				
				ObjectOutputStream outStream = new ObjectOutputStream(socket.getOutputStream());
				ObjectInputStream inStream = new ObjectInputStream(socket.getInputStream());
			
				try {
					String user = (String)inStream.readObject(); //recebe user
					String passwd = (String)inStream.readObject(); //recebe password
					long fileBytes = (long)(inStream.readObject()); //recebe tamanho do ficheiro
					String fileName = (String)inStream.readObject(); //recebe o nome do ficheiro
					
					//o processo abaixo de gravação do ficheiro poderá ser simplificado
					byte[] content = (byte[]) inStream.readObject(); //recebe o ficheiro
					File fileReceived = new File ("serverDirectory/" + fileName); //cria o namespace
					Files.write(fileReceived.toPath(), content); //grava o conteudo do ficheiro "content" para o namespace
					System.out.println("thread: depois de receber a password e o user: " + user);
					System.out.println("Tamanho do ficheiro recebido: "+ fileBytes + " bytes");
					
				}catch (ClassNotFoundException e1) {
					e1.printStackTrace();
				}

				outStream.close();
				inStream.close();
 			
				socket.close();

			} catch (IOException e) {
				e.printStackTrace();

			}
		}
	}
}

