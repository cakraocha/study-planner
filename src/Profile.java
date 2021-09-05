import java.sql.Statement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Profile
{
    private String user;
    private String pass;

    private String userDB;
    private String passDB;

    /**
     * Constructor for profile
     * @param user String user from the user input
     * @param pass String password from the user input
     * @throws StudyPlannerException when credentials do not match db info
     */
    public Profile(String user, String pass)
    {
        // Check to database, throw exception if does not exist
        queryProfile(user);
        if (userDB == null)
        throw new StudyPlannerException("Username not exists. Please try again!");

        if (!passDB.equals(pass))
        throw new StudyPlannerException("Password does not match. Please try again!");

        this.user = user;
        this.pass = pass;
    }

    /**
     * Return the user ID
     * @return String UID
     */
    public String getUID()
    {
        return user;
    }

    /**
     * This is just for debug for the user password
     * @return String pass
     */
    public String getPass()
    {
        return pass;
    }

    /**
     * This is just for debug in printing query information
     */
    public void getQuery()
    {
        System.out.printf("uid: %s\n", userDB);
        System.out.printf("pass: %s\n", passDB);
    }

    /**
     * Helper method for querying profile to database
     * @param user String UID
     * @throws SQLException when query fails
     */
    private void queryProfile(String user)
    {
        try
        {
            Connection connection = ConnectDB.getConnection();
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);  // set timeout to 30 sec.

            ResultSet rs = statement.executeQuery(String.format("""
            SELECT * FROM profile
            WHERE uid = '%s'
            """, user)
            );

            userDB = rs.getString("uid");
            passDB = rs.getString("pass");
        }
        catch(SQLException e)
        {
            System.err.println(e.getMessage());
        }
    }
}
