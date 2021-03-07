package myDoctor;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

public class userOptions {
	
	public static Map<String, String> validate(String a) throws Exception {
		
		final Map<String, String> params = new HashMap<>();
		
		String[] temp = null; //String que guarda o split em "-" da string de comandos original
		temp = a.split("-",0);	
		String temp2[] = null; //String auxiliar no ciclo que separa os comandos e os valores
	
		for(int i = 1; i<temp.length;i++) {
			temp2 = temp[i].split(" ",0);
			 try {
				 params.put(temp2[0],temp2[1]);
		      } catch(ArrayIndexOutOfBoundsException e) {
				params.put(temp2[0], null);
		      }
		}
		
		String v = null;
		
		//valida��o do utilizador
		try {
			v = params.get("u");
		}
		catch (NullPointerException e){
			throw new Exception("Erro no ID introduzido");	
		}
		
		//valida��o do endere�o
		try {
			v = params.get("a");
		}
		catch (NullPointerException e){
			throw new Exception("Erro no endere�o introduzido");	
		}
		
		//valida��o da password
		try {
			v = params.get("p");
		}
		catch (NullPointerException e){
			throw new Exception("Erro na password introduzida");	
		}

		//valida��o da listagem de documentos do utente -md
		if(params.containsKey("md")) {
			
			File file = new File("clientDirectory");
			String[] l = file.list();
			System.out.println("Ficheiros do utente: " + params.get("u"));
			
			if(l == null) {
				System.out.println("Nenhum ficheiro na directoria");
			}
			else {
				for(int i=0; i<l.length ;i++) {
					System.out.println(l[i]);
				}
			}
		}
		
		// Cria��o de utilizadores
		if(params.containsKey("c")) {
			
			if(params.get("u").compareTo("1") == 0) {
				
				try(FileWriter fw = new FileWriter("serverDirectory/def_not_passwords.txt", true);
					    BufferedWriter bw = new BufferedWriter(fw);
					    PrintWriter out = new PrintWriter(bw))
					{
					    out.println(temp2[1]+";"+temp2[2]+";"+temp2[3]+";"+temp2[4]);
					    System.out.println("O utilizador "+temp2[2]+" com o ID "+ temp2[1] + " vai ser criado.");
					    System.out.println("Utilizador "+ temp2[2] + " foi criado!");
					    bw.close();
					    out.close();
					} catch (IOException e) {
					    //exception handling left as an exercise for the reader
					}
			}
			
			else {
				System.out.println("Apenas admins podem criar utilizadores.");
			}
			
		}
		return params;	
	}
}