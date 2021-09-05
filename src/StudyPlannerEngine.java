import java.io.File;
import java.util.Scanner;
import java.sql.Connection;
import java.sql.SQLException;

public class StudyPlannerEngine {
    public static Scanner scan = new Scanner(System.in);
    public static void main(String[] args) {
        // Establish connection to database
        File dbfile = new File("studyplanmain.db");
        Connection conn = null;
        String uid;
        String pass;

        if (dbfile.exists())
            conn = ConnectDB.connect();
        else
        {
            System.out.println("Database did not exists. Provisioning database..");
            conn = ConnectDB.createDB();
        }

        // Welcoming message
        System.out.println("\nWELCOME TO STUDY PLANNER APP!\n");

        // Login details
        System.out.printf("Enter your username: ");
        uid = scan.nextLine();
        System.out.printf("Enter your password: ");
        pass = scan.nextLine();

        boolean success = false;
        while (!success)
        {
            try
            {
                Profile profile = new Profile(uid, pass);
                success = true;
            }
            catch (StudyPlannerException SPE)
            {
                System.out.println(SPE.getMessage());
                System.out.printf("Enter your username: ");
                uid = scan.nextLine();
                System.out.printf("Enter your password: ");
                pass = scan.nextLine();
            }
        }
        
        // DEBUG
        // profile.getQuery();

        ConnectDB.closeConn();
    }
}
