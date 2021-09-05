import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ConnectDB
{ 
  private static Connection conn = null;

  /**
   * Provision a database from scratch
   */
  public static void createDB()
  {
    try
    {
      // create a database connection
      conn = DriverManager.getConnection("jdbc:sqlite:studyplanmain.db");
      Statement statement = conn.createStatement();
      statement.setQueryTimeout(30);  // set timeout to 30 sec.

      // when creating db, also create 2 dummies for uid with password
      statement.executeUpdate("""
      CREATE TABLE IF NOT EXISTS profile(
        uid TEXT UNIQUE,
        pass TEXT)""");
      statement.executeUpdate("INSERT INTO profile VALUES('admin', 'adminhashed')");
      statement.executeUpdate("INSERT INTO profile VALUES('ocha', 'passocha')");
      ResultSet rs = statement.executeQuery("SELECT * FROM profile");
      while(rs.next())
      {
        // read the result set
        System.out.printf("Result from profile -- uid: %s | pass: %s \n",
        rs.getString("uid"), rs.getString("pass"));
      }

      System.out.println("\nProvisioning tables..");
      // Creating table for subject
      statement.executeUpdate("""
      CREATE TABLE IF NOT EXISTS subject(
        uid TEXT,
        subject_name TEXT,
        subject_year INT,
        subject_semester TEXT CHECK(subject_semester IN (
          '1', '2', 'SUMMER', 'WINTER', 'OTHER')),
        subject_lecturer TEXT
        )
      """);

      // Creating table for events
      statement.executeUpdate("""
      CREATE TABLE IF NOT EXISTS event(
        uid TEXT,
        event_name TEXT,
        event_type TEXT CHECK(event_type IN ('ASSG', 'EXAM', 'OTHER')),
        event_date DATE
      )
      """);
      
    }
    catch(SQLException e)
    {
      System.err.println(e.getMessage());
    }
  }

  /**
   * Connect to an existing database
   */
  public static void connect()
  {
    conn = null;
    try
    {
      // create a database connection
      conn = DriverManager.getConnection("jdbc:sqlite:studyplanmain.db");

    }
    catch(SQLException e)
    {
      System.err.println(e.getMessage());
    }
  }

  /**
   * Return the connection that has been established
   * @return Connection connection
   */
  public static Connection getConnection()
  {
    return conn;
  }

  /**
   * Closing the connection to database
   */
  public static void closeConn()
  {
    try {
      conn = DriverManager.getConnection("jdbc:sqlite:studyplanmain.db");
      if (conn != null)
        conn.close();
    } catch (SQLException ex) {
      System.err.println(ex.getMessage());
    }
  }
}
