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
	
	else if(users.get(id) == null) {
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

 public static String login(Integer id,String password ) throws Exception {
	 userObject currentUser;
	 if(users.get(id)==null){
		 return "User doesnt exist";
	 }
	 else if(users.get(id).getPassword().equals(password)) {
		 currentUser = users.get(id);
		 return "Login Sucessfull \nUsername: " + currentUser.getUsername() + "\nClientType: " + currentUser.getType();
	 }
	 else {
		 return "Invalid password";
	 }
 }
 
 public static String checkCmd(Integer id,String password,String comand,String args) {
	//TODO Validação da porcaria toda ou aqui ou em funcoes auxiliares
	 return "Para já não valida nada"; 
 }
 
 /**
 * Itera o mapa users e cria uma string com todos os utilizadores.
 * @return string que contem todos os utilizadores já fromatada e pronta a imprimir
 */
 public static String createMd() {
	 userObject user;
	 String output ="";
	 for (Entry<Integer, userObject> entry : users.entrySet()) {
		 user = entry.getValue();
		 output += "Id: " + user.getId() +" Name: "+user.getUsername() +" Type:" + user.getType()+"\n";
	}
	return output;
 }
 
 public static String[] reply(Integer id,String password,String comand,String args) throws Exception {
	 String[] rply = new String[2];
	 rply[0]=login(id,password);
	 rply[1]=checkCmd(id,password,comand,args);
	 return rply;
 }
}