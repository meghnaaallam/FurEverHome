package utilities;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DbConnection {
	private static final String URL = "jdbc:mysql://localhost:3306/fureverhomedb";
    private static final String USER_NAME = "root";
    private static final String RAMIT_PASSWORD = "qwertyuiop";
    private static final String MEGHNA_PASSWORD = "root";
    private static final String SHREYA_PASSWORD = "Friends_1306";
    
    private static Connection connection = null;
    private static Statement statement = null;
    
    /* Creating Connection*/
    public static void connection(){
       try{
    	   System.out.println(MEGHNA_PASSWORD);
            connection = DriverManager.getConnection(URL, USER_NAME, SHREYA_PASSWORD);
            System.out.println(connection);
            statement = connection.createStatement();
            System.out.println("Connection Opened");
        }catch(SQLException e){   
        	e.printStackTrace();
            //JOptionPane.showMessageDialog(null, "Connection is not Opened ! " + e.getMessage());      
        }
    }
    
    /*  Selecting Query */
    public static ResultSet selectQuery(String query) {
        try{
            connection();            
            return statement.executeQuery(query);
        }catch(SQLException e){
        	e.printStackTrace();
            return null;
            
        }
    }
    
    
    /*  Executing Query */
    public static boolean query(String query) {
        try{
            connection();
            return statement.execute(query);
        }catch(SQLException e){
        	e.printStackTrace();
        }
		return true;
    }
    
    
    /* Prepares the data first then execute it */
     public static PreparedStatement getPreStatement(String query)
     {
       try {
           return statement.getConnection().prepareStatement(query);
         } catch (SQLException e) {}
       return null;
     }

	public static Integer updateQuery(String squery) {
		// TODO Auto-generated method stub
	       try{
	            connection();            
	            return statement.executeUpdate(squery);
	        }catch(SQLException e){
	        	e.printStackTrace();
	            return null;
	            
	        }
	}
}
