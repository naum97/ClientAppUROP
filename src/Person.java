import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.sql.Statement;


public class Person extends Thread {
    private final Connection connection;
    private final String querySelection;
    private final int numberOfIterations;
    private final Utilities generator = new Utilities();
    volatile Statement s;

    public Person(Connection connection, String querySelection, int numberOfIterations) {
        this.connection = connection;
        this.querySelection = querySelection;
        this.numberOfIterations = numberOfIterations;
    }

    public void run(){
        try{
            /* 
		FIX this !!!! Run full benchmark (all 22 queries in random order)
               For a 'numberOfIterations' iterations
             */
            if(querySelection.equals("all"))
            {
                executeAllQueries();
                connection.close();
                return;
            }
	   if(Integer.parseInt(querySelection) == 15)
	{
//THE PROBLEM IS WITH MULTIPLE THREADS CREATING THE SAME VIEW, POSSIBLE SOLUTION = MODIFY QUERY TO INITIALIZE DIFFERENT VIEW NAME EVERY TIME, ADD PARAMETER TO PREPARED STATEMENT
		s = connection.createStatement();

int year = Utilities.rand.nextInt(5)+1993; //for q4
        	int month;
        if(year == 1997){ month = Utilities.rand.nextInt(10) + 1; } else {month = Utilities.rand.nextInt(12) + 1;}
	String sql;
	String date = String.format("%d-%02d-01", year, month);
	sql = Queries.sql15_view.replace("?", String.format("'%s'", date));
	s.execute(sql);
		
		for(int i = 0; i < numberOfIterations; i++){
		ResultSet rs = s.executeQuery(Queries.sql15);
	while(rs.next()){System.out.println(rs.getString(1));}
s.execute(Queries.sql15_drop);
}

		return;
	}
            /* Run a selected query for a 'numberOfIterations' iterations */

            PreparedStatement stmt =			generator.prepareStatement(Integer.parseInt(querySelection), connection);

            for(int i = 0; i < numberOfIterations; i++)
            {
                executeQuery(Integer.parseInt(querySelection), stmt);
            }

            stmt.close();
            connection.close();

        } catch (SQLException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }
    private void executeQuery(int choice, PreparedStatement preparedStatement) throws SQLException {
	    
            generator.setParameters(choice, preparedStatement);
            ResultSet rs = preparedStatement.executeQuery();
	    System.out.println(preparedStatement);

            //check for empty ResultSet
	if(!rs.isBeforeFirst())
            {
                System.out.println("FAILURE");
            }

	    while(rs.next()){
			 System.out.println(rs.getString(1));
			//System.out.println(rs.getDouble(2));
			//System.out.println(rs.getDouble(3));
}
rs.close();
            
       }
    private void executeAllQueries() throws SQLException, IllegalAccessException {
        List<PreparedStatement> preparedStatementList = generator.getListOfAllPreparedStatements(connection);
        List<Integer> randomizer = Utilities.getRandomNonRepeatingList(22,22,1);

        for(int i = 0; i < numberOfIterations; i++)
        {
            executeQuery(randomizer.get(i), preparedStatementList.get(randomizer.get(i)-1));
        }
        for(PreparedStatement p : preparedStatementList)
        {
            p.close();
        }
    }
public Connection getConnection(){return this.connection;}
}
