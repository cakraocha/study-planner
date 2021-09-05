import java.sql.Statement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Profile implements ProfileInterface
{
    private String user;
    private String pass;

    private String userDB;
    private String passDB;

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

    @Override
    public String getUID()
    {
        return user;
    }

    @Override
    public String getPass()
    {
        return pass;
    }

    @Override
    public void getQuery()
    {
        System.out.printf("uid: %s\n", userDB);
        System.out.printf("pass: %s\n", passDB);
    }

    private void queryProfile(String user)
    {
        try
        {
            Connection connection = ConnectDB.connect();
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
