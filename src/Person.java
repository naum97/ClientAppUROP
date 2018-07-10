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
    private List<PreparedStatement> preparedStatementList = null;

    public Person(Connection connection, String querySelection, int numberOfIterations) {
        this.connection = connection;
        this.querySelection = querySelection;
        this.numberOfIterations = numberOfIterations;
    }

    public void run(){
        try{
            if(querySelection.equals("all"))
            {
                preparedStatementList = generator.getListOfAllPreparedStatements(connection);
                for(int i = 1; i <= numberOfIterations; i++)
                {
                    int index = Utilities.rand.nextInt(preparedStatementList.size());
                    generator.setParameters(index+1, preparedStatementList.get(index));
                    ResultSet rs = preparedStatementList.get(index).executeQuery();

                    while(rs.next())
                    {
                        //do nothing
                    }

                }
                return;
            }
            PreparedStatement stmt = generator.prepareStatement(Integer.parseInt(querySelection), connection);
            for(int i = 1; i <= numberOfIterations; i++)
            {
                generator.setParameters(Integer.parseInt(querySelection), stmt);
                ResultSet rs = stmt.executeQuery();

                while (rs.next())
                {
                    System.out.println(rs.getString(1));
                }

                sleep(2000);

            }
            stmt.close();
            connection.close();
        }catch (InterruptedException e)
        {
            System.out.println("Person was interrupted!");
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
