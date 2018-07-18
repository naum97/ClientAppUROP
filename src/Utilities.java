import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.sql.Statement;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.sql.ResultSet;


public class Utilities {
    public static final Random rand = new Random();

    public  ArrayList<PreparedStatement> getListOfAllPreparedStatements(Connection c) throws IllegalAccessException, SQLException {
        List<PreparedStatement> list = new ArrayList<>(22);
        for(Field f : Queries.class.getDeclaredFields())
        {
            if(!(f.getName().equals("sql15_view") || f.getName().equals("sql15") || f.getName().equals("sql15_drop")) )
            list.add(c.prepareStatement((String) f.get(null)));
        /*sql1 goes to list(0)
        ...
        sql 14 goes to list(13)
        sql 16 goes to list (14)
        sql 22 goes to list(20)
         */
        }
        return (ArrayList<PreparedStatement>) list;
    }

    public PreparedStatement getPreparedStatement_22(PreparedStatement stmt22) throws SQLException {
        List<Integer> country_codes = getRandomNonRepeatingList(7,34,10);
        for(int i = 1; i <=7; i++)
        {
            stmt22.setString(i,Integer.toString(country_codes.get(i-1)));
            stmt22.setString(i+7, Integer.toString(country_codes.get(i-1)));
        }
        System.out.println("query 22 executed");

        return stmt22;
    }

    public PreparedStatement getPreparedStatement_21(PreparedStatement stmt21) throws SQLException {
        stmt21.setString(1, Constants.keysAsArray.get(rand.nextInt(Constants.keysAsArray.size())));
        System.out.println("query 21 executed");

        return stmt21;
    }

    public PreparedStatement getPreparedStatement_20(PreparedStatement stmt20) throws SQLException {
	    LocalDate date = LocalDate.of(rand.nextInt(5)+1993,1,1);
	    stmt20.setString(1, Constants.colors[rand.nextInt(Constants.colors.length)]+"%");
        stmt20.setDate(2, Date.valueOf(date));
        stmt20.setDate(3,Date.valueOf(date));
        
        stmt20.setString(4, Constants.keysAsArray.get(rand.nextInt(Constants.keysAsArray.size())));
        System.out.println("query 20 executed");

        return stmt20;
    }

    public PreparedStatement getPreparedStatement_19(PreparedStatement stmt19) throws SQLException {
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
        System.out.println("query 19 executed");

        return stmt19;
    }

    public PreparedStatement getPreparedStatement_18(PreparedStatement stmt18) throws SQLException {
        stmt18.setInt(1, rand.nextInt(4)+312);
        System.out.println("query 18 executed");

        return stmt18;
    }

    public PreparedStatement getPreparedStatement_17(PreparedStatement stmt17) throws SQLException {
        stmt17.setString(1,"BRAND#"+Integer.toString(rand.nextInt(5)+1)+Integer.toString(rand.nextInt(5)+1));
        stmt17.setString(2, Constants.container_1[rand.nextInt(Constants.container_1.length)] + " " + Constants.container_2[rand.nextInt(Constants.container_2.length)]);

        System.out.println("query 17 executed");

        return stmt17;
    }

    public PreparedStatement getPreparedStatement_16(PreparedStatement stmt16) throws SQLException {
        List<Integer> sizes = getRandomNonRepeatingList(8,50,1);
        stmt16.setString(1,"BRAND#"+Integer.toString(rand.nextInt(5)+1)+Integer.toString(rand.nextInt(5)+1));
        stmt16.setString(2, Constants.type1[rand.nextInt(Constants.type1.length)] + " " + Constants.type2[rand.nextInt(Constants.type2.length)] + "%");
        int n = 0;
        for(int i = 3; i <=10; i++) {
            stmt16.setInt(i, sizes.get(n)); n++;
        }
        System.out.println("query 16 executed");

        return stmt16;
    }
//FIX QUERIES ABOVE 15. CHECK THEM !!!!!
    public static synchronized void getPreparedStatement_15(Connection conn) throws SQLException {
        int year = Utilities.rand.nextInt(5)+1993; //for q4
        int month;
        if(year == 1997){ month = Utilities.rand.nextInt(10) + 1; } else {month = Utilities.rand.nextInt(12) + 1;}
        String sql;
        String date = String.format("%d-%02d-01", year, month);
        sql = Queries.sql15_view.replace("?", String.format("'%s'", date));
        conn.createStatement().execute(sql);
        ResultSet rs = conn.createStatement().executeQuery(Queries.sql15);
        while(rs.next()) {
            //System.out.println(rs.getString(1));
        }
        conn.createStatement().execute(Queries.sql15_drop);
        System.out.println("query 15 executed");

    }

    public PreparedStatement getPreparedStatement_14(PreparedStatement stmt14) throws SQLException {
	    LocalDate date = LocalDate.of(rand.nextInt(5)+1993,rand.nextInt(12)+1,1);
        stmt14.setDate(1, Date.valueOf(date));
        stmt14.setDate(2,Date.valueOf(date));
        System.out.println("query 14 executed");

        return stmt14;
    }

    public PreparedStatement getPreparedStatement_13(PreparedStatement stmt13) throws SQLException {
        stmt13.setString(1, "%" + Constants.words1[rand.nextInt(Constants.words1.length)] + "%" + Constants.words2[rand.nextInt(Constants.words2.length)] + "%");
        System.out.println("query 13 executed");

        return stmt13;
    }

    public PreparedStatement getPreparedStatement_12(PreparedStatement stmt12) throws SQLException {
        String mode1 = Constants.modes[rand.nextInt(Constants.modes.length)];
        String mode2 = Constants.modes[rand.nextInt(Constants.modes.length)];
	    LocalDate date = LocalDate.of(rand.nextInt(5)+1993,1,1);
        while(mode1.equals(mode2)) mode2 = Constants.modes[rand.nextInt(Constants.modes.length)]; //need to be different
        stmt12.setString(1,mode1);
        stmt12.setString(2,mode2);
        stmt12.setDate(3, Date.valueOf(date));
        stmt12.setDate(4,Date.valueOf(date));
        System.out.println("query 12 executed");

        return stmt12;
    }

    public PreparedStatement getPreparedStatement_11(PreparedStatement stmt11) throws SQLException {
        stmt11.setString(1, Constants.keysAsArray.get(rand.nextInt(Constants.keysAsArray.size())));
        stmt11.setDouble(2, 0.0001 * Constants.DB_SCALE_FACTOR);
        stmt11.setString(3, Constants.keysAsArray.get(rand.nextInt(Constants.keysAsArray.size())));
        System.out.println("query 11 executed");

        return stmt11;
    }

    public PreparedStatement getPreparedStatement_10(PreparedStatement stmt10) throws SQLException {
        int randomYear10 = rand.nextInt(3)+1993; //for q10
        int randomMonth10;
        if(randomYear10 == 1993) randomMonth10 = rand.nextInt(11)+2; else if(randomYear10 == 1995) randomMonth10 = 1; else randomMonth10 = rand.nextInt(12)+1;
        stmt10.setDate(1, Date.valueOf(LocalDate.of(randomYear10, randomMonth10, 1)));
        stmt10.setDate(2, Date.valueOf(LocalDate.of(randomYear10, randomMonth10, 1)));
        System.out.println("query 10 executed");

        return stmt10;
    }

    public PreparedStatement getPreparedStatement_9(PreparedStatement stmt9) throws SQLException {

        stmt9.setString(1,"%"+ Constants.colors[rand.nextInt(Constants.colors.length)] +"%");
        System.out.println("query 9 executed");

        return stmt9;
    }

    public PreparedStatement getPreparedStatement_8(PreparedStatement stmt8) throws SQLException {
        String nation_8 = Constants.keysAsArray.get(rand.nextInt(Constants.keysAsArray.size()));
        String region_8 = Constants.region2[Constants.nation_region.get(nation_8)];
        String x = Constants.type1[rand.nextInt(Constants.type1.length)] + " " + Constants.type2[rand.nextInt(Constants.type2.length)] + " " + Constants.type3[rand.nextInt(Constants.type3.length)];
        stmt8.setString(1, nation_8);
        stmt8.setString(2, region_8);
        stmt8.setString(3, x);
        System.out.println("query 8 executed");

        return stmt8;
    }

    public PreparedStatement getPreparedStatement_7(PreparedStatement stmt7) throws SQLException {
        String nation1_7 = Constants.keysAsArray.get(rand.nextInt(Constants.keysAsArray.size()));
        String nation2_7 = Constants.keysAsArray.get(rand.nextInt(Constants.keysAsArray.size()));
        while(nation1_7.equals(nation2_7)) nation2_7 = Constants.keysAsArray.get(rand.nextInt(Constants.keysAsArray.size())); // need two different random countries
        stmt7.setString(1,nation1_7);
        stmt7.setString(2,nation2_7);
        stmt7.setString(3,nation2_7);
        stmt7.setString(4,nation1_7);
        System.out.println("query 7 executed");

        return stmt7;
    }

    public PreparedStatement getPreparedStatement_6(PreparedStatement stmt6) throws SQLException {

        int index_q6 = rand.nextInt(Constants.seq_q6.length);
        int quantity_q6 = rand.nextInt(2)+24;
        LocalDate date = LocalDate.of(rand.nextInt(5)+1993,1,1);
        stmt6.setDate(1, Date.valueOf(date));
        stmt6.setDate(2,Date.valueOf(date));
        stmt6.setDouble(3, Constants.seq_q6[index_q6]);
        stmt6.setDouble(4, Constants.seq_q6[index_q6]);
        stmt6.setInt(5,quantity_q6);
        System.out.println("query 6 executed");

        return stmt6;
    }

    public PreparedStatement getPreparedStatement_5(PreparedStatement stmt5) throws SQLException {

        stmt5.setString(1, Constants.region2[rand.nextInt(5)]);
	    LocalDate date = LocalDate.of(rand.nextInt(5)+1993,1,1);
        stmt5.setDate(2, Date.valueOf(date));
        stmt5.setDate(3,Date.valueOf(date));
        System.out.println("query 5 executed");

        return stmt5;
    }

    public PreparedStatement getPreparedStatement_4(PreparedStatement stmt4) throws SQLException {


        int randomYear4 = rand.nextInt(5)+1993; //for q4
        int randomMonth4;
        if(randomYear4 == 1997){ randomMonth4 = rand.nextInt(10) + 1; } else {randomMonth4 = rand.nextInt(12) + 1;}
        LocalDate bla = LocalDate.of(randomYear4, randomMonth4, 1);
        Date newDate = Date.valueOf(bla);

        stmt4.setDate(1, newDate);
        stmt4.setDate(2, newDate);
        System.out.println("query 4 executed");

        return stmt4;
    }

    public PreparedStatement getPreparedStatement_3(PreparedStatement stmt3) throws SQLException {

        long minDay = LocalDate.of(1995, 3, 1).toEpochDay(); //for q3
        long maxDay = LocalDate.of(1995, 3, 31).toEpochDay(); //for q3
        long randomDay = ThreadLocalRandom.current().nextLong(minDay, maxDay);
        LocalDate randomDate = LocalDate.ofEpochDay(randomDay);
        Date date = Date.valueOf(randomDate);

        stmt3.setString(1, Constants.segments[rand.nextInt(Constants.segments.length)]);
        stmt3.setDate(2, date);
        stmt3.setDate(3,date);
        System.out.println("query 3 executed");

        return stmt3;
    }

    public PreparedStatement getPreparedStatement_2(PreparedStatement stmt2) throws SQLException {


        stmt2.setInt(1,rand.nextInt(50) + 1); // for query 2 [1-50]
        stmt2.setString(2,"%"+ Constants.type3[rand.nextInt(Constants.type3.length)]);
	    int index = rand.nextInt(Constants.region2.length);
        stmt2.setString(3, Constants.region2[index]);
	    stmt2.setString(4, Constants.region2[index]);
        System.out.println("query 2 executed");

        return stmt2;
    }

    public PreparedStatement getPreparedStatement_1(PreparedStatement stmt1) throws SQLException {

        stmt1.setString(1,Integer.toString(rand.nextInt(61) + 60) + "day"); //range  = [60-120]
        System.out.println("query 1 executed");
        return stmt1;
    }

    public  PreparedStatement prepareStatement(int choice, Connection c) throws SQLException {
        switch (choice)
        {
            case 1 : return  c.prepareStatement(Queries.sql1);
            case 2 :  return c.prepareStatement(Queries.sql2);
            case 3 :  return c.prepareStatement(Queries.sql3);
            case 4 :  return c.prepareStatement(Queries.sql4);
            case 5 :  return c.prepareStatement(Queries.sql5);
            case 6 :  return c.prepareStatement(Queries.sql6);
            case 7 :  return c.prepareStatement(Queries.sql7);
            case 8 :  return c.prepareStatement(Queries.sql8);
            case 9 :  return c.prepareStatement(Queries.sql9);
            case 10 : return c.prepareStatement(Queries.sql10);
            case 11:  return c.prepareStatement(Queries.sql11);
            case 12 : return c.prepareStatement(Queries.sql12);
            case 13:  return c.prepareStatement(Queries.sql13);
            case 14:  return c.prepareStatement(Queries.sql14);
            case 16:  return c.prepareStatement(Queries.sql16);
            case 17:  return c.prepareStatement(Queries.sql17);
            case 18:  return c.prepareStatement(Queries.sql18);
            case 19:  return c.prepareStatement(Queries.sql19);
            case 20:  return c.prepareStatement(Queries.sql20);
            case 21:  return c.prepareStatement(Queries.sql21);
            case 22:  return c.prepareStatement(Queries.sql22);
            default: return null;
        }}
    public  PreparedStatement setParameters(int choice, PreparedStatement p) throws SQLException {
        switch (choice)
        {
            case 1 : return getPreparedStatement_1(p);
            case 2 : return getPreparedStatement_2(p);
            case 3 : return getPreparedStatement_3(p);
            case 4 : return getPreparedStatement_4(p);
            case 5 : return getPreparedStatement_5(p);
            case 6 : return getPreparedStatement_6(p);
            case 7 : return getPreparedStatement_7(p);
            case 8 : return getPreparedStatement_8(p);
            case 9 : return getPreparedStatement_9(p);
            case 10 :return getPreparedStatement_10(p);
            case 11: return getPreparedStatement_11(p);
            case 12 :return getPreparedStatement_12(p);
            case 13: return getPreparedStatement_13(p);
            case 14: return getPreparedStatement_14(p);
            case 16: return getPreparedStatement_16(p);
            case 17: return getPreparedStatement_17(p);
            case 18: return getPreparedStatement_18(p);
            case 19: return getPreparedStatement_19(p);
            case 20: return getPreparedStatement_20(p);
            case 21: return getPreparedStatement_21(p);
            case 22: return getPreparedStatement_22(p);
            default: return null;
        }
    }
    public  List<Integer> getRandomNonRepeatingList(int length, int max, int min) {
        List<Integer> sizes = new ArrayList<>(length);
        while(sizes.size() < length) {
            int x = rand.nextInt(max-min+1)+min;
            if(!sizes.contains(x))sizes.add(x);
        }
        return sizes;
    }

}
