package myDoctor;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Map.Entry;
import java.util.NavigableMap;
import java.util.Set;
import java.util.TreeMap;

import com.sun.xml.internal.bind.v2.runtime.unmarshaller.XsiNilLoader.Array;


public class serverOptions {
	
	static File f = new File ("serverDirectory/def_not_passwords.txt");
	static NavigableMap<Integer, userObject> users = new TreeMap<Integer, userObject>(); //mapa que contém todos os users formato <username,objecto>
	
	
/**
* Lê o ficheiro 'f' e popula o Map<String, userObject>users 
* com username como chave e um objecto userObject como valor
*/
public static void  populateUserMap() {
    String lineInFile;
    String[] s;
    int idAux = 0; 
    
	try (BufferedReader br = new BufferedReader(new FileReader(f))) {
	    while ((lineInFile = br.readLine()) != null) {
	    	s = lineInFile.split(";");
	    	idAux = Integer.parseInt(s[0]);
	    	userObject u = new userObject (idAux,s[1],s[2],s[3]);
	    	users.put(idAux, u);
	    }
	} catch (FileNotFoundException e) {
		System.out.println("File not found");
		e.printStackTrace();
	} catch (IOException e) {
		System.out.println("IO error");
		e.printStackTrace();
	}
}

/**
* Retorna um objecto userObject que corresponda ao username dado
* @param  username
* @return um objecto com username correspondente ou null caso não exista.
*/
public static userObject getUser(Integer id) {
	try {
		return users.get(id);
	}
	catch(Exception e) {
		System.out.println("No user found");
		return null;
	}
}

/**
* Verifica se o tipo de utilizador é válido e se o username já está atribuido, 
* caso contrário cria o utilizador e a sua respectiva directoria
* @param username
* @param password
* @param type
*/

public static void createUser(Integer id , String username, String password,String type) throws IOException {

	Set<String> strings = new HashSet<String>();
	strings.add("utente");
	strings.add("medico");
	strings.add("admin");
	strings.add("tecnico");

	if (!strings.contains(type.toLowerCase())){
		System.out.println("Invalid user type.\nTypes are : admin, utente, medico ou tecnico");
	}
	
	else if(users.get(id).getId() == null) {
		 String s = ";";
		 try(BufferedWriter bw = new BufferedWriter(new FileWriter(f,true))){
				bw.newLine();
				bw.append( id +s+ username +s+ password +s+ type); //adiciona o user recem criado no ficheiro
			}	
			users.put(id, new userObject(id,username,password, type)); //adiciona o user recem criado no mapa
			
			Path path = Paths.get("serverDirectory/"+id);
			Files.createDirectories(path);
			System.out.println("User " + id + " "+ username + " created along with directory");
	}
	 else {
		 System.out.println("User already exists");
	 }
}

public static userObject login(Integer id,String password ) throws Exception {
	
    userObject currentUser;
    if(users.get(id)==null){
        System.out.println("User doesnt exist");
        return null;
    }
    else if(users.get(id).getPassword().equals(password)) {
        currentUser = users.get(id);
        System.out.println("Login Sucessfull \nUsername: " + currentUser.getUsername() + "\nClientType: " + currentUser.getType());
        return currentUser;
    }
    else {
        System.out.println("Invalid password");
        return null;
    }
}
 
public static void fetch(Integer userId) {
	 
	 	File file = new File("serverDirectory/userFiles/"+userId);
		String[] l = file.list();
		System.out.println("Ficheiros do utente: " + users.get(userId).getUsername());
		
		if(l == null) {
			System.out.println("Nenhum ficheiro na directoria");
		}
		else {
			for(int i=0; i<l.length ;i++) {
				System.out.println(l[i]);
			}
		}
 }
 
public static void teste(Integer id, String password, String command) throws Exception {
	userObject userLogin = login(id, password);
	String comando = null;
	try {
		comando = command.split(" ")[0];
		System.out.println("Algo");
	} catch(Exception e){
		comando = command;
	}
	
//	String argumentos = command.split(" ")[1];
	
	if (userLogin == null) {
		System.out.println("Algo correu mal");
	}
	
	else {
		
		switch(comando) {
		  case "mu":
			  if (userLogin.getType() == "utente") {
				  System.out.println("Não tens permissões necessárias para aceder a esta opção.");
			  }
			  else {
				  userObject user;
				  for (Entry<Integer, userObject> entry : users.entrySet()) {
				         user = entry.getValue();
				         System.out.println("Id: " + user.getId() +" Name: "+user.getUsername() +" Type:" + user.getType());
				    }

			  }
		    break;
		  case "md":
			  if (userLogin.getType() != "utente") {
				  System.out.println("Esta opção apenas é valida para utentes.");
			  }
			  else {
				  fetch(id);
			  }
		    break;
		  case "mx":
			  if (userLogin.getType() != "medico") {
				  System.out.println("Esta opção apenas é valida para médicos.");
			  }
			  else {
				  
			  }
			    break;
		  case "d":

			    break;
		  case "du":

			    break;
		  case "su":

			    break;
		  case "c":

			    break;
		  default:
			  System.out.println("unknown command");
		}
	}
	
 }
}