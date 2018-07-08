import java.sql.*;
import java.sql.Date;
import java.util.*;
import java.time.LocalDate;
import java.util.concurrent.ThreadLocalRandom;


public class Main {
    public static final Random rand = new Random();

    public static void main(String args[] ) {

            Connection c = null;
            PreparedStatement stmt1, stmt2, stmt3, stmt4, stmt5, stmt6, stmt7, stmt8, stmt9, stmt10, stmt11, stmt12, stmt13, stmt14, stmt15, stmt16, stmt17
                    ,stmt18, stmt19, stmt20, stmt21, stmt22= null;

        try {
            c = getConnection();
            System.out.println("Opened database successfully");

           stmt5 = getPreparedStatement_5(c);
            ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

            ResultSet rs = stmt5.executeQuery();
            while ( rs.next() ) {
                System.out.println(rs.getString(1));
            }
            rs.close();
            stmt5.close();
            c.close();
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName()+": "+ e.getMessage() );
            System.exit(0);
        }
        System.out.println("Operation done successfully");
    }



    private static PreparedStatement getPreparedStatement_22(Connection c) throws SQLException {
        PreparedStatement stmt22;
        stmt22 = c.prepareStatement(Utilities.sql22);
        List<Integer> country_codes = getRandomNonRepeatingList(7,34,10);
        for(int i = 1; i <=7; i++)
        {
            stmt22.setInt(i,country_codes.get(i));
            stmt22.setInt(i+7, country_codes.get(i));
        }
        return stmt22;
    }

    private static PreparedStatement getPreparedStatement_21(Connection c) throws SQLException {
        PreparedStatement stmt21;
        stmt21 = c.prepareStatement(Utilities.sql21);
        stmt21.setString(1, Utilities.keysAsArray.get(rand.nextInt(Utilities.keysAsArray.size())));
        return stmt21;
    }

    private static PreparedStatement getPreparedStatement_20(Connection c) throws SQLException {
        PreparedStatement stmt20;
        stmt20 = c.prepareStatement(Utilities.sql20);
        stmt20.setDate(1, Date.valueOf(LocalDate.of(rand.nextInt(5)+1993,1,1)));
        stmt20.setDate(2,Date.valueOf(LocalDate.of(rand.nextInt(5)+1993,1,1)));
        stmt20.setString(3, Utilities.colors[rand.nextInt(Utilities.colors.length)]);
        stmt20.setString(4, Utilities.keysAsArray.get(rand.nextInt(Utilities.keysAsArray.size())));
        return stmt20;
    }

    private static PreparedStatement getPreparedStatement_19(Connection c) throws SQLException {
        PreparedStatement stmt19;
        stmt19 = c.prepareStatement(Utilities.sql19);
        int quantity1 = rand.nextInt(10)+1;
        int quantity2 = rand.nextInt(11)+10;
        int quantity3 = rand.nextInt(11)+20;
        stmt19.setString(1,"BRAND#"+Integer.toString(rand.nextInt(5)+1)+Integer.toString(rand.nextInt(5)+1));
        stmt19.setInt(2,quantity1);
        stmt19.setInt(3,quantity1);
        stmt19.setString(4,"BRAND#"+Integer.toString(rand.nextInt(5)+1)+Integer.toString(rand.nextInt(5)+1));
        stmt19.setInt(5,quantity2);
        stmt19.setInt(6,quantity2);
        stmt19.setString(7,"BRAND#"+Integer.toString(rand.nextInt(5)+1)+Integer.toString(rand.nextInt(5)+1));
        stmt19.setInt(8,quantity3);
        stmt19.setInt(9,quantity3);
        return stmt19;
    }

    private static PreparedStatement getPreparedStatement_18(Connection c) throws SQLException {
        PreparedStatement stmt18;
        stmt18 = c.prepareStatement(Utilities.sql18);
        stmt18.setInt(1, rand.nextInt(4)+312);
        return stmt18;
    }

    private static PreparedStatement getPreparedStatement_17(Connection c) throws SQLException {
        PreparedStatement stmt17;
        stmt17 = c.prepareStatement(Utilities.sql17);
        stmt17.setString(1,"BRAND#"+Integer.toString(rand.nextInt(5)+1)+Integer.toString(rand.nextInt(5)+1));
        stmt17.setString(2, Utilities.container_1[rand.nextInt(Utilities.container_1.length)] + " " + Utilities.container_2[rand.nextInt(Utilities.container_2.length)]);
        return stmt17;
    }

    private static PreparedStatement getPreparedStatement_16(Connection c) throws SQLException {
        PreparedStatement stmt16;
        stmt16 = c.prepareStatement(Utilities.sql16);
        List<Integer> sizes = getRandomNonRepeatingList(8,50,1);
        stmt16.setString(1,"BRAND#"+Integer.toString(rand.nextInt(5)+1)+Integer.toString(rand.nextInt(5)+1));
        stmt16.setString(2, Utilities.type1[rand.nextInt(Utilities.type1.length)] + " " + Utilities.type2[rand.nextInt(Utilities.type2.length)] + "%");
        int n = 0;
        for(int i = 3; i <=10; i++) {
            stmt16.setInt(i, sizes.get(n)); n++;
        }
        return stmt16;
    }

    private static PreparedStatement getPreparedStatement_15(Connection c) throws SQLException {
        PreparedStatement stmt15;
        stmt15 = c.prepareStatement(Utilities.sql15);
        int randomYear4 = rand.nextInt(5)+1993; //for q4
        int randomMonth4;
        if(randomYear4 == 1997){ randomMonth4 = rand.nextInt(10) + 1; } else {randomMonth4 = rand.nextInt(12) + 1;}
        stmt15.setDate(1, Date.valueOf(LocalDate.of(randomYear4,randomMonth4,1)));
        stmt15.setDate(2,Date.valueOf(LocalDate.of(randomYear4,randomMonth4,1)));
        return stmt15;
    }

    private static PreparedStatement getPreparedStatement_14(Connection c) throws SQLException {
        PreparedStatement stmt14;
        stmt14 = c.prepareStatement(Utilities.sql14);
        stmt14.setDate(1, Date.valueOf(LocalDate.of(rand.nextInt(5)+1993, rand.nextInt(12)+1,1)));
        stmt14.setDate(2,Date.valueOf(LocalDate.of(rand.nextInt(5)+1993, rand.nextInt(12)+1,1)));
        return stmt14;
    }

    private static PreparedStatement getPreparedStatement_13(Connection c) throws SQLException {
        PreparedStatement stmt13;
        stmt13 = c.prepareStatement(Utilities.sql13);
        stmt13.setString(1, "%" + Utilities.words1[rand.nextInt(Utilities.words1.length)] + "%" + Utilities.words2[rand.nextInt(Utilities.words2.length)] + "%");
        return stmt13;
    }

    private static PreparedStatement getPreparedStatement_12(Connection c) throws SQLException {
        PreparedStatement stmt12;
        stmt12 = c.prepareStatement(Utilities.sql12);
        String mode1 = Utilities.modes[rand.nextInt(Utilities.modes.length)];
        String mode2 = Utilities.modes[rand.nextInt(Utilities.modes.length)];
        while(mode1.equals(mode2)) mode2 = Utilities.modes[rand.nextInt(Utilities.modes.length)]; //need to be different
        stmt12.setString(1,mode1);
        stmt12.setString(2,mode2);
        stmt12.setDate(3, Date.valueOf(LocalDate.of(rand.nextInt(5)+1993,1,1)));
        stmt12.setDate(4,Date.valueOf(LocalDate.of(rand.nextInt(5)+1993,1,1)));
        return stmt12;
    }

    private static PreparedStatement getPreparedStatement_11(Connection c) throws SQLException {
        PreparedStatement stmt11;
        stmt11 = c.prepareStatement(Utilities.sql11);
        stmt11.setString(1, Utilities.keysAsArray.get(rand.nextInt(Utilities.keysAsArray.size())));
        stmt11.setDouble(2, 0.0001 * Utilities.DB_SCALE_FACTOR);
        stmt11.setString(3, Utilities.keysAsArray.get(rand.nextInt(Utilities.keysAsArray.size())));
        return stmt11;
    }

    private static PreparedStatement getPreparedStatement_10(Connection c) throws SQLException {
        PreparedStatement stmt10;
        stmt10 = c.prepareStatement(Utilities.sql10);
        int randomYear10 = rand.nextInt(3)+1993; //for q10
        int randomMonth10;
        if(randomYear10 == 1993) randomMonth10 = rand.nextInt(11)+2; else if(randomYear10 == 1995) randomMonth10 = 1; else randomMonth10 = rand.nextInt(12)+1;
        stmt10.setDate(1, Date.valueOf(LocalDate.of(randomYear10, randomMonth10, 1)));
        stmt10.setDate(2, Date.valueOf(LocalDate.of(randomYear10, randomMonth10, 1)));
        return stmt10;
    }

    private static PreparedStatement getPreparedStatement_9(Connection c) throws SQLException {
        PreparedStatement stmt9;
        stmt9 = c.prepareStatement(Utilities.sql9);

        stmt9.setString(1,"%"+ Utilities.colors[rand.nextInt(Utilities.colors.length)] +"%");
        return stmt9;
    }

    private static PreparedStatement getPreparedStatement_8(Connection c) throws SQLException {
        PreparedStatement stmt8;
        stmt8 = c.prepareStatement(Utilities.sql8);
        String nation_8 = Utilities.keysAsArray.get(rand.nextInt(Utilities.keysAsArray.size()));
        String region_8 = Utilities.region2[Utilities.nation_region.get(nation_8)];
        String x = Utilities.type1[rand.nextInt(Utilities.type1.length)] + " " + Utilities.type2[rand.nextInt(Utilities.type2.length)] + " " + Utilities.type3[rand.nextInt(Utilities.type3.length)];
        stmt8.setString(1, nation_8);
        stmt8.setString(2, region_8);
        stmt8.setString(3, x);
        return stmt8;
    }

    private static PreparedStatement getPreparedStatement_7(Connection c) throws SQLException {
        PreparedStatement stmt7;
        stmt7  = c.prepareStatement(Utilities.sql7);
        String nation1_7 = Utilities.keysAsArray.get(rand.nextInt(Utilities.keysAsArray.size()));
        String nation2_7 = Utilities.keysAsArray.get(rand.nextInt(Utilities.keysAsArray.size()));
        while(nation1_7.equals(nation2_7)) nation2_7 = Utilities.keysAsArray.get(rand.nextInt(Utilities.keysAsArray.size())); // need two different random countries
        stmt7.setString(1,nation1_7);
        stmt7.setString(2,nation2_7);
        stmt7.setString(3,nation2_7);
        stmt7.setString(4,nation1_7);
        return stmt7;
    }

    private static PreparedStatement getPreparedStatement_6(Connection c) throws SQLException {
        PreparedStatement stmt6;
        stmt6 = c.prepareStatement(Utilities.sql6);
        int index_q6 = rand.nextInt(Utilities.seq_q6.length);
        int quantity_q6 = rand.nextInt(2)+24;
        stmt6.setDate(1, Date.valueOf(LocalDate.of(rand.nextInt(5)+1993,1,1)));
        stmt6.setDate(2,Date.valueOf(LocalDate.of(rand.nextInt(5)+1993,1,1)));
        stmt6.setDouble(3, Utilities.seq_q6[index_q6]);
        stmt6.setDouble(4, Utilities.seq_q6[index_q6]);
        stmt6.setInt(5,quantity_q6);
        return stmt6;
    }

    private static PreparedStatement getPreparedStatement_5(Connection c) throws SQLException {
        PreparedStatement stmt5;
        stmt5 = c.prepareStatement(Utilities.sql5);

        stmt5.setString(1, Utilities.region2[rand.nextInt(5)]);
        stmt5.setDate(2, Date.valueOf(LocalDate.of(rand.nextInt(5)+1993,1,1)));
        stmt5.setDate(3,Date.valueOf(LocalDate.of(rand.nextInt(5)+1993,1,1)));
        return stmt5;
    }

    private static PreparedStatement getPreparedStatement_4(Connection c) throws SQLException {
        PreparedStatement stmt4;
        stmt4 = c.prepareStatement(Utilities.sql4);

        int randomYear4 = rand.nextInt(5)+1993; //for q4
        int randomMonth4;
        if(randomYear4 == 1997){ randomMonth4 = rand.nextInt(10) + 1; } else {randomMonth4 = rand.nextInt(12) + 1;}
        LocalDate bla = LocalDate.of(randomYear4, randomMonth4, 1);
        Date newDate = Date.valueOf(bla);

        stmt4.setDate(1, newDate);
        stmt4.setDate(2, newDate);
        return stmt4;
    }

    private static PreparedStatement getPreparedStatement_3(Connection c) throws SQLException {
        PreparedStatement stmt3;
        stmt3 = c.prepareStatement(Utilities.sql3);

        long minDay = LocalDate.of(1995, 3, 1).toEpochDay(); //for q3
        long maxDay = LocalDate.of(1995, 3, 31).toEpochDay(); //for q3
        long randomDay = ThreadLocalRandom.current().nextLong(minDay, maxDay);
        LocalDate randomDate = LocalDate.ofEpochDay(randomDay);
        Date date = Date.valueOf(randomDate);

        stmt3.setString(1, Utilities.segments[rand.nextInt(5)]);
        stmt3.setDate(2, date);
        stmt3.setDate(3,date);
        return stmt3;
    }

    private static PreparedStatement getPreparedStatement_2(Connection c) throws SQLException {
        PreparedStatement stmt2;
        stmt2 = c.prepareStatement(Utilities.sql2);
        int range_q2 = rand.nextInt(50) + 1; // for query 2 [1-50]

        int index2 = rand.nextInt(5);
        stmt2.setInt(1,range_q2);
        stmt2.setString(2,"%"+ Utilities.type3[index2]);
        stmt2.setString(3, Utilities.region2[index2]);
        return stmt2;
    }

    private static PreparedStatement getPreparedStatement_1(Connection c) throws SQLException {

        PreparedStatement stmt1;
        stmt1 = c.prepareStatement(Utilities.sql1);
        int range_q1 = rand.nextInt(61) + 60; //for query 1 [60-120]

        stmt1.setString(1,Integer.toString(range_q1) + "day"); //range  = [60-120]
        return stmt1;
    }

    private static Connection getConnection() throws ClassNotFoundException, SQLException {
        Connection c;
        Class.forName("org.postgresql.Driver");
        c = DriverManager
                .getConnection("jdbc:postgresql://localhost:5432/nina",
                        "postgres", "");
        c.setAutoCommit(false);
        return c;
    }

    private static  List<Integer> getRandomNonRepeatingList(int length, int max, int min) {
        List<Integer> sizes = new ArrayList<>();
        while(sizes.size() < length) {
            int x = rand.nextInt(max-min+1)+min;
            if(!sizes.contains(x))sizes.add(x);
        }
        return sizes;
    }
}
