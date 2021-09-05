import java.io.File;

public class StudyPlannerEngine {
    public static void main(String[] args) {
        // Establish connection to database
        File dbfile = new File("studyplanmain.db");

        if (dbfile.exists())
            ConnectDB.connect();
        else
        {
            System.out.println("Database did not exists. Provisioning database..");
            ConnectDB.createDB();
        }
        
        ConnectDB.closeConn();
    }
}
