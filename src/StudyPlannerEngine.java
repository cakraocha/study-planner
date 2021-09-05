import java.io.File;
import java.util.Scanner;
import java.sql.Connection;
import java.sql.SQLException;

public class StudyPlannerEngine {
    public static Scanner scan = new Scanner(System.in);
    public static void main(String[] args) {
        // establish connection to database
        File dbfile = new File("studyplanmain.db");
        Connection conn = null;
        String uid;
        String pass;
        String userInput;
        Profile profile;

        if (dbfile.exists())
        {
            ConnectDB.connect();
            conn = ConnectDB.getConnection();
        }
        else
        {
            System.out.println("Database did not exists. Provisioning database..");
            ConnectDB.createDB();
            conn = ConnectDB.getConnection();
        }

        // welcoming message
        System.out.println("\nWELCOME TO STUDY PLANNER APP!\n");

        // login details
        System.out.printf("Enter your username: ");
        uid = scan.nextLine();
        System.out.printf("Enter your password: ");
        pass = scan.nextLine();

        // check login details to database
        // repeat until user gives the correct credential
        boolean success = false;
        while (!success)
        {
            try
            {
                profile = new Profile(uid, pass);
                success = true;
            }
            catch (StudyPlannerException SPE)
            {
                System.out.println(SPE.getMessage());
                System.out.print("Enter your username: ");
                uid = scan.nextLine();
                System.out.print("Enter your password: ");
                pass = scan.nextLine();
            }
        }

        // initiate Subject
        profile = new Profile(uid, pass);
        Subject subject = new Subject(profile);

        // greet user and shows subjects that already saved
        System.out.printf("\nHello %s\n\n", profile.getUID());
        System.out.println(subject.subjectsToString());

        // app engine
        while (true)
        {
            System.out.println("What would you like to do today?");
            System.out.println("1. Get your subjects");
            System.out.println("2. Add subject");
            System.out.println("3. Generate study plan");
            System.out.println();

            userInput = scan.nextLine();

            switch (userInput)
            {
                case "1":
                    System.out.println(subject.subjectsToString());
                    break;
                case "2":
                    System.out.print("Enter the subject name: ");
                    String subjectName = scan.nextLine();
                    System.out.print("Enter the subject year: ");
                    int subjectYear = Integer.parseInt(scan.nextLine());
                    System.out.print("Enter the subject semester: ");
                    String subjectSemester = scan.nextLine();
                    System.out.print("Enter the subject lecturer: ");
                    String subjectLecturer = scan.nextLine();
                    subject.addSubject(subjectName, subjectYear, subjectSemester, subjectLecturer);

                    System.out.println("Subject added!");
                    break;
                case "3":
                    System.out.println("To be constructed..");
                    break;
                case "exit":
                    ConnectDB.closeConn();
                    System.exit(0);
                default:
                    System.out.println("Unknown command..");;
            }
        }
    }
}
