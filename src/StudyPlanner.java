import java.sql.Connection;
import java.util.Calendar;

public class StudyPlanner {
    private String user;
    private Calendar startTime;
    private Calendar endTime;

    /*
	 * A constructor for set the start time of the study to 9 am and the end
	 * time to 5 pm
	 */
    public StudyPlanner(String user, String pass)
    {
        startTime = Calendar.getInstance();
        endTime = Calendar.getInstance();

        startTime.set(startTime.get(Calendar.YEAR), startTime.get(Calendar.MONTH), startTime.get(Calendar.DAY_OF_MONTH),
				9, 0, 0);
		startTime.set(Calendar.MILLISECOND, 0);

		endTime.set(endTime.get(Calendar.YEAR), endTime.get(Calendar.MONTH), endTime.get(Calendar.DAY_OF_MONTH), 17, 0,
				0);
		endTime.set(Calendar.MILLISECOND, 0);
    }
}
