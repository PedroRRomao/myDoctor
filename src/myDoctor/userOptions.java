package myDoctor;

import java.io.File;
import java.util.LinkedHashMap;
import java.util.Map;


public class userOptions {
	
	static String lastComand = null; //ultimo comando md mx c etc.
	final static Map <String, String> params = new LinkedHashMap<String,String>();
	
	public static Map<String, String> validate(String a) throws Exception {	
		String[] temp = null;
		temp = a.split("-",0);	//"-u 1 -a 123:456" = [] [u 1] [a 123:456]
		String temp2[] = null; 
		for(int i = 1; i<temp.length;i++) {
			temp2 = temp[i].split(" ",2); //dá split só á primeira ocorrência de espaço em branco ex:"a b c" =[a],[bc]
			 try {
				 params.put(temp2[0],temp2[1]);
				 lastComand = temp2[0];
		      } catch(ArrayIndexOutOfBoundsException e) {
				params.put(temp2[0], null);
		      }
		}
		//verifica se os comandos que não sejam mu e md têm argumentos.
		if(lastComand!="mu" || lastComand!="md") {
			if(params.get(lastComand).isEmpty()) {
				throw new Exception("O comando " + lastComand + " precisa de argumentos");
			}
		}

		//MOVER PARA SERVER	
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
	
	public static String getLastCommand() {
		return lastComand;	
	}
}