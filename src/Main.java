import org.postgresql.ds.PGConnectionPoolDataSource;

import java.sql.Connection;
import java.sql.SQLException;


public class Main {
    private static String QUERY_SELECTION = "all";
    private static int NUMBER_OF_ITERATIONS = 10;
    private static int NUMBER_OF_THREADS = 3;


    public static void main(String args[] ) throws SQLException, ClassNotFoundException {
       if(args.length != 3) {
           System.out.println("Usage: java Main [querySelection] [numberOfIterations] [numberOfThreads] ");
           System.out.println();
           System.out.println("If left blank: eg java Main, the full TPC-H benchmark (all 22 queries) will ,by default, run on 3 client threads with 10 iterations each");
       } else {
           QUERY_SELECTION = args[0];
           NUMBER_OF_ITERATIONS = Integer.parseInt(args[1]);
           NUMBER_OF_THREADS = Integer.parseInt(args[2]);
       }
        PGConnectionPoolDataSource pool = new PGConnectionPoolDataSource();
        pool.setDatabaseName("nina");
        pool.setUser("postgres");

        Person[] person = new Person[NUMBER_OF_THREADS];
        for (int i = 0; i < NUMBER_OF_THREADS; i++) {
            Connection conn = pool.getConnection();
            person[i] = new Person(conn, QUERY_SELECTION, NUMBER_OF_ITERATIONS);
        }
        for (int i = 0; i < NUMBER_OF_THREADS; i++) {
            person[i].start();
        }
    }
}
