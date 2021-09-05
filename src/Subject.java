import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class Subject {
    private ArrayList<String> subjects = new ArrayList<String>();
    // private String subjectName;
    // private int subjectYear;
    // private String subjectSemester;
    // private String subjectLecturer;
    private Profile profile;
    private ResultSet rs;

    /**
     * Constructor for subject
     * @param profile user profile such as id and pw
     */
    public Subject(Profile profile)
    {
        // Constructor
        this.profile = profile;
        updateSubjects();
    }

    /**
     * Get the list of subject name
     * @return ArrayList of subject name
     */
    public ArrayList<String> getSubjects()
    {
        updateSubjects();
        return subjects;
    }

    /**
     * Stringify list of subject name
     * @return String list of subject name
     */
    public String subjectsToString()
    {
        updateSubjects();
        String subjectsToString = "Here are list of your subjects: "; 
        for (String s:subjects)
        {
            subjectsToString += s + " | ";
        }

        return subjectsToString;
    }

    /**
     * A method to add subject information to the database
     * @param subjectName the subject name from user input
     * @param subjectYear the subject year from user input
     * @param subjectSemester the subject semester from user input
     * @param subjectLecturer the subject lecturer from user input
     */
    public void addSubject(String subjectName, int subjectYear, String subjectSemester,
    String subjectLecturer)
    {
        try
        {
            Connection connection = ConnectDB.getConnection();
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);  // set timeout to 30 sec.

            statement.executeUpdate(String.format("""
            INSERT INTO subject VALUES(
                '%s', '%s', '%d', '%s', '%s')""",
                profile.getUID(),
                subjectName,
                subjectYear,
                subjectSemester,
                subjectLecturer));

            updateSubjects();
            
        }
        catch(SQLException e)
        {
            System.err.println(e.getMessage());
        }
    }

    /**
     * A helper method to update subject name
     * by querying to the database
     */
    private void updateSubjects()
    {
        try
        {
            Connection connection = ConnectDB.getConnection();
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);  // set timeout to 30 sec.

            rs = statement.executeQuery(String.format("""
            SELECT * FROM subject
            WHERE uid = '%s'
            """, profile.getUID())
            );

            // empty the subjects
            subjects.clear();

            // add the query result to subjects
            while(rs.next())
            {
                // fetch the subjects data
                subjects.add(rs.getString("subject_name"));
            }
            
        }
        catch(SQLException e)
        {
            System.err.println(e.getMessage());
        }
    }
}
