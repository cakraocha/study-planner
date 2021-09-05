# Study Planner Application in Java
This is a simple application for generating study plan. Features included:<br/>
- A database to record subjects and user id
- Simple login mechanism
- See your subjects
- Add a subject
- Generate your study plan (*to be constructed*)

# Diagram
## Database Entity Relationship Diagram
![Database ERD](/diagram/ERD.png)

# How to run the app
## Prerequisites
- Java Development Kit (JDK) 13 or above
- SQLite Driver, you can find one in `/out/production/`

## Running the program
- Go to `/src/`
- Using your command line, type `javac ConnectDB.java Profile.java StudyPlannerException.java Subject.java StudyPlanner.java StudyPlannerEngine.java -d ../out/production/` and enter
- Change directory to `/out/production/`
- Type `java -classpath ".;sqlite-jdbc-3.36.0.3.jar" StudyPlannerEngine`. You can change the *classpath* into your own sqlite driver
- When prompted *username* and *password*, you can choose between:
    - uid: `ocha` | password: `passocha`
    - uid: `admin` | password: `adminhashed`
- When finished, type `exit` or **ctrl+c** for hard exit