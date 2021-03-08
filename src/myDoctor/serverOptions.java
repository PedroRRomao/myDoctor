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
import java.util.NavigableMap;
import java.util.Set;
import java.util.TreeMap;


public class serverOptions {
	
	static File f = new File ("serverDirectory/def_not_passwords.txt");
	static NavigableMap<String, userObject> users = new TreeMap<String, userObject>(); //mapa que contém todos os users formato <username,objecto>

	
/**
* Lê o ficheiro 'f' e popula o Map<String, userObject>users 
* com username como chave e um objecto userObject como valor
*/
public static void  populateUserMap() {
	
    String lineInFile;
    String[] s;
    
	try (BufferedReader br = new BufferedReader(new FileReader(f))) {
	    while ((lineInFile = br.readLine()) != null) {
	    	s = lineInFile.split(";");
	    	userObject u = new userObject (Integer.parseInt(s[0]),s[1],s[2],s[3]);
	    	users.put(s[1], u);
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
public static userObject getUser(String username) {
	try {
		return users.get(username);
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
public static void createUser(String username, String password,String type) throws IOException {

	Set<String> strings = new HashSet<String>();
	strings.add("utente");
	strings.add("medico");
	strings.add("admin");
	strings.add("tecnico");

	if (!strings.contains(type.toLowerCase())){
		System.out.println("Invalid user type.\nTypes are : admin, utente, medico ou tecnico");
	}
	
	else if(users.get(username)==null) {
		 int newId = users.lastEntry().getValue().getId()+2; //vai buscar o ultimo id no ficheiro e incrementa para ser adicionado ao novo user
		 String s = ";";
		 try(BufferedWriter bw = new BufferedWriter(new FileWriter(f,true))){
				bw.newLine();
				bw.append( newId +s+ username +s+ password +s+ type); //adiciona o user recem criado no ficheiro
			}	
			users.put(username, new userObject(newId,username,password, type)); //adiciona o user recem criado no mapa
			
			Path path = Paths.get("serverDirectory/"+username);
			Files.createDirectories(path);
			System.out.println("User" + username + "created along with directory");
	}
	 else {
		 System.out.println("User already exists");
	 }
}

 public static void login(String username,String password ) throws Exception {
	 userObject currentUser;
	 if(users.get(username)==null){
		 System.out.println("User doesnt exist");
	 }
	 else if(users.get(username).getPassword().equals(password)) {
		 currentUser = users.get(username);
		 System.out.println("Login Sucessfull \nUsername: " + currentUser.getUsername() + "\nClientType: " + currentUser.getType());
	 }
	 else {
		 System.out.println("Invalid password");
	 }
 }
 public static void teste() {
	 System.out.println(users.get("Miguel").getPassword());
 }
}