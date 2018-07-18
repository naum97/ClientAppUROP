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

    public Person(Connection connection, String querySelection, int numberOfIterations) {
        this.connection = connection;
        this.querySelection = querySelection;
        this.numberOfIterations = numberOfIterations;
    }

    public void run(){
        try{
            /* 
	            Run full benchmark (all 22 queries in random order)
               For a 'numberOfIterations' iterations
             */
            if(querySelection.equals("all"))
            {
		for(int i = 1; i <= numberOfIterations; i++){
                executeAllQueries();}
                connection.close();
                return;
            }

            /* Run a selected query for a 'numberOfIterations' iterations */

            PreparedStatement stmt = generator.prepareStatement(Integer.parseInt(querySelection), connection);

            for(int i = 0; i < numberOfIterations; i++)
            {
                executeQuery(Integer.parseInt(querySelection), stmt);
            }
            try {
                stmt.close();
                connection.close();
            } catch (NullPointerException e) //fix NullPointerException
            {
                //do nothing
            }

        } catch (SQLException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }
    private void executeQuery(int choice, PreparedStatement preparedStatement) throws SQLException {
            /*This is needed due to concurrency issues with q15*/
	        if(choice == 15){
	            Utilities.getPreparedStatement_15(connection);
	            return;
            }
            generator.setParameters(choice, preparedStatement);
            ResultSet rs = preparedStatement.executeQuery();
	       // System.out.println(preparedStatement);

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
            List<Integer> randomizer = generator.getRandomNonRepeatingList(22,22,1); //values [1-22]

         /*sql1 goes to list(0)
        ...
        sql 14 goes to list(13)
        sql 16 goes to list (14)
        sql 22 goes to list(20)
         */

        for(int i = 0; i < 22; i++)
        {
            if(randomizer.get(i) < 15) {
                executeQuery(randomizer.get(i), preparedStatementList.get(randomizer.get(i) - 1));
            } else if(randomizer.get(i) > 15){
                executeQuery(randomizer.get(i), preparedStatementList.get(randomizer.get(i) - 2));
            } else Utilities.getPreparedStatement_15(connection);
        }
        for(PreparedStatement p : preparedStatementList)
        {
            p.close();
        }
    }
}
