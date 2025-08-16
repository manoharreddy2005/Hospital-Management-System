
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
/**
 * Hello world!
 *
 */
public class Main
{
    public static void main( String[] args )
    {

        System.out.println( "Hello World!" );
        String url ="jdbc:mysql://localhost:3306/hospital";
        String user ="root";
        String password ="Mypassword@4170";
        try(Connection connect = DriverManager.getConnection(url,user,password)){
            System.out.println("Connected to database.");

        }catch(SQLException e){
            System.err.println("Failed to connect to database."+e.getMessage());

        }
    }
}
