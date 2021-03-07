package myDoctor;

import java.io.File;
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
		
		//validação do utilizador
		try {
			v = params.get("u");
		}
		catch (NullPointerException e){
			throw new Exception("Erro no ID introduzido");	
		}
		
		//validação do endereço
		try {
			v = params.get("a");
		}
		catch (NullPointerException e){
			throw new Exception("Erro no endereço introduzido");	
		}
		
		//validação da password
		try {
			v = params.get("p");
		}
		catch (NullPointerException e){
			throw new Exception("Erro na password introduzida");	
		}
		
		//validação da listagem de documentos do utente -md
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
	
		return params;	
	}
}