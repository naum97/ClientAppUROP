import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;


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
            /* Run full benchmark (all 22 queries in random order)
               For a 'numberOfIterations' iterations
             */
            if(querySelection.equals("all"))
            {
                executeAllQueries();
                connection.close();
                return;
            }

            /* Run a selected query for a 'numberOfIterations' iterations */

            PreparedStatement stmt = generator.prepareStatement(Integer.parseInt(querySelection), connection);

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

            //check for empty ResultSet
            if(rs.isBeforeFirst())
            {
                System.out.println("Success");
            }

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
}
