package myDoctor;

public class userObject {
	
	private Integer id;
	private String username;
	private String password;
	private String type;
	

    public userObject() {
    	this.id = null;
        this.username = null;
        this.password = null;
        this.type = null;
    }

    public userObject(int id, String username, String password,String type) {
    	try {
    	this.id = id;
    	this.username = username;
        this.password = password;
        this.type =type;
        }
    	catch (Exception e) {
    		e.printStackTrace();
    	}
    }
    
    public Integer getId() {
        return id;
      }
    
    public String getUsername() {
        return username;
      }
    
    public String getPassword() {
        return password;
      }
    
    public String getType() {
        return type;
      }
/*
    @Override
    public String toString()
    {
         return "Username: " + key.getKey() + " - Value: " + value.getValue();
    }
    */
}
