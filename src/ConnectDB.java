import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ConnectDB
{
    public static void createDB()
    {
      Connection conn = null;
      try
      {
        // create a database connection
        conn = DriverManager.getConnection("jdbc:sqlite:studyplanmain.db");
        Statement statement = conn.createStatement();
        statement.setQueryTimeout(30);  // set timeout to 30 sec.
  
        statement.executeUpdate("CREATE TABLE IF NOT EXISTS profile(profile_id integer, uid string, pass string)");
        statement.executeUpdate("INSERT INTO profile VALUES(1, 'admin', 'adminhashed')");
        ResultSet rs = statement.executeQuery("SELECT * FROM profile");
        while(rs.next())
        {
          // read the result set
          System.out.printf("Result from profile -- id: %d | uid: %s | pass: %s \n",
          rs.getInt("profile_id"), rs.getString("uid"), rs.getString("pass"));
        }

        System.out.println("\nCleaning data....");
        statement.executeUpdate("DELETE FROM profile");

        // System.out.println("\nProvisioning tables..");
        // statement.executeUpdate("""
        // CREATE TABLE IF NOT EXISTS subject(
        //   subject_id integer,
        //   )
        // """);
        
      }
      catch(SQLException e)
      {
        // if the error message is "out of memory",
        // it probably means no database file is found
        System.err.println(e.getMessage());
      }
    }
  
    public static void connect()
    {
      Connection conn = null;
      try
      {
        // create a database connection
        conn = DriverManager.getConnection("jdbc:sqlite:studyplanmain.db");

        System.out.println("Connection to db has been established..");
        
      }
      catch(SQLException e)
      {
        // if the error message is "out of memory",
        // it probably means no database file is found
        System.err.println(e.getMessage());
      }
    }

    public static void closeConn()
    {
      Connection conn = null;
      try {
        conn = DriverManager.getConnection("jdbc:sqlite:studyplanmain.db");
        if (conn != null)
          conn.close();
      } catch (SQLException ex) {
        System.err.println(ex.getMessage());
      }
    }

}
