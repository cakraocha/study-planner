import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ConnectDB
{ 
  public static Connection createDB()
  {
    Connection conn = null;
    try
    {
      // create a database connection
      conn = DriverManager.getConnection("jdbc:sqlite:studyplanmain.db");
      Statement statement = conn.createStatement();
      statement.setQueryTimeout(30);  // set timeout to 30 sec.

      statement.executeUpdate("""
      CREATE TABLE IF NOT EXISTS profile(
        profile_id INT PRIMARY KEY,
        uid TEXT UNIQUE,
        pass TEXT)""");
      statement.executeUpdate("INSERT INTO profile VALUES('1', 'admin', 'adminhashed')");
      statement.executeUpdate("INSERT INTO profile VALUES('2', 'ocha', 'passocha')");
      ResultSet rs = statement.executeQuery("SELECT * FROM profile");
      while(rs.next())
      {
        // read the result set
        System.out.printf("Result from profile -- id: %d | uid: %s | pass: %s \n",
        rs.getInt("profile_id"), rs.getString("uid"), rs.getString("pass"));
      }

      // System.out.println("\nCleaning data....");
      // statement.executeUpdate("DELETE FROM profile");

      System.out.println("\nProvisioning tables..");
      // Creating table for subject
      statement.executeUpdate("""
      CREATE TABLE IF NOT EXISTS subject(
        subject_id INT PRIMARY KEY,
        profile_id INT,
        subject_name TEXT,
        subject_year DATE,
        subject_semester TEXT CHECK(subject_semester IN (
          '1', '2', 'SUMMER', 'WINTER', 'OTHER')),
        subject_lecturer TEXT
        )
      """);

      // Creating table for events
      statement.executeUpdate("""
      CREATE TABLE IF NOT EXISTS event(
        event_id INT PRIMARY KEY,
        profile_id INT,
        event_name TEXT,
        event_type TEXT CHECK(event_type IN ('ASSG', 'EXAM', 'OTHER')),
        event_date DATE
      )
      """);
      
    }
    catch(SQLException e)
    {
      // if the error message is "out of memory",
      // it probably means no database file is found
      System.err.println(e.getMessage());
    }

    return conn;
  }

  public static Connection connect()
  {
    Connection conn = null;
    try
    {
      // create a database connection
      conn = DriverManager.getConnection("jdbc:sqlite:studyplanmain.db");
      
    }
    catch(SQLException e)
    {
      // if the error message is "out of memory",
      // it probably means no database file is found
      System.err.println(e.getMessage());
    }

    return conn;
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
