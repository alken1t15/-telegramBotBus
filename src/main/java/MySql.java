import java.sql.*;
import java.util.ArrayList;

public class MySql {

    private ArrayList<String> arrayList = new ArrayList<>();
    private StringBuilder station = new StringBuilder();
    private int id;
    private String busName;
    private StringBuilder bussTime = new StringBuilder("D:\\BusTime\\");
    private StringBuilder bussMap = new StringBuilder("D:\\BusMap\\");

    public int getId() {
        return id;
    }

    public String getBusName() {
        return busName;
    }

    public StringBuilder getBussTime() {
        return bussTime;
    }

    public StringBuilder getBussMap() {
        return bussMap;
    }

    // JDBC URL, username and password of MySQL server
    private static final String url = "jdbc:mysql://localhost:3306/database1";
    private static final String user = "root";
    private static final String password = "root";

    // JDBC variables for opening and managing connection
    private static Connection con;
    private static Statement stmt;
    private static ResultSet rs;

    public void Sql(int b) {
        String query = "SELECT `id`, `busname`, `bussTime`, `bussMap` FROM database1.buss " +
                "WHERE `busname` = " + b + ";"; // нужно добавить переменную для айди


        try {
            // opening database connection to MySQL server
            con = DriverManager.getConnection(url, user, password);

            // getting Statement object to execute query
            stmt = con.createStatement();

            // executing SELECT query
            rs = stmt.executeQuery(query);

            while (rs.next()) {


                id = rs.getInt(1);
                busName = rs.getString(2);
                bussTime.append(rs.getString(3));
                bussMap.append(rs.getString(4));
                System.out.print("id: " + id + " Номер автобуса: " + busName + " Расписание автобуса: " + bussTime);
            }

        } catch (SQLException sqlEx) {
            sqlEx.printStackTrace();
        } finally {
            try {
                con.close();
            } catch (SQLException se) { /*can't do anything */ }
            try {
                stmt.close();
            } catch (SQLException se) { /*can't do anything */ }
            try {
                rs.close();
            } catch (SQLException se) { /*can't do anything */ }
        }
    }

    public void Sql2(String b) {


        String query = "SELECT * FROM database1.busstation where stationcol1 LIKE  '%" + b + "%' OR stationcol2 LIKE  '%" + b + "%' OR stationcol3 LIKE  '%" + b + "%' OR stationcol4 LIKE  '%" + b + "%' OR stationcol5 LIKE  '%" + b + "%' OR stationcol6 LIKE  '%" + b + "%' OR stationcol7 LIKE  '%" + b + "%' OR stationcol8 LIKE  '%" + b + "%' OR stationcol9 LIKE  '%" + b + "%' OR stationcol10 LIKE  '%" + b + "%' OR stationcol11 LIKE  '%" + b + "%' OR stationcol12 LIKE  '%" + b + "%' OR stationcol13 LIKE  '%" + b + "%' OR stationcol14 LIKE  '%" + b + "%' OR stationcol15 LIKE  '%" + b + "%' OR stationcol16 LIKE  '%" + b + "%' OR stationcol17 LIKE  '%" + b + "%' OR stationcol18 LIKE  '%" + b + "%' OR stationcol19 LIKE  '%" + b + "%' OR stationcol20 LIKE  '%" + b + "%' OR stationcol21 LIKE  '%" + b + "%' OR stationcol22 LIKE  '%" + b + "%' OR stationcol23 LIKE  '%" + b + "%' OR stationcol24 LIKE  '%" + b + "%' OR stationcol25 LIKE  '%" + b + "%' OR stationcol26 LIKE  '%" + b + "%' OR stationcol27 LIKE  '%" + b + "%' OR stationcol28 LIKE  '%" + b + "%' OR stationcol29 LIKE  '%" + b + "%' OR stationcol30 LIKE  '%" + b + "%' OR stationcol31 LIKE  '%" + b + "%' OR stationcol32 LIKE  '%" + b + "%' OR stationcol33 LIKE  '%" + b + "%' OR stationcol34 LIKE  '%" + b + "%' OR stationcol35 LIKE  '%" + b + "%' OR stationcol36 LIKE  '%" + b + "%' OR stationcol37 LIKE  '%" + b + "%' OR stationcol38 LIKE  '%" + b + "%' OR stationcol39 LIKE  '%" + b + "%' OR stationcol40 LIKE  '%" + b + "%' OR stationcol41 LIKE  '%" + b + "%' OR stationcol42 LIKE  '%" + b + "%'";

        try {
            // opening database connection to MySQL server
            con = DriverManager.getConnection(url, user, password);

            // getting Statement object to execute query
            stmt = con.createStatement();

            // executing SELECT query
            rs = stmt.executeQuery(query);

            while (rs.next()) {

                String busName = rs.getString(2);
                arrayList.add(busName);
            }

        } catch (SQLException sqlEx) {
            sqlEx.printStackTrace();
        } finally {
            //close connection ,stmt and resultset here
            try {
                con.close();
            } catch (SQLException se) { /*can't do anything */ }
            try {
                stmt.close();
            } catch (SQLException se) { /*can't do anything */ }
            try {
                rs.close();
            } catch (SQLException se) { /*can't do anything */ }
        }
        setStation();
    }

    public StringBuilder getStation() {
        return station;
    }

    public void setStation() {
        for (String str : arrayList) {
            station.append(str).append(" ");
        }
        arrayList.clear();
    }

    public void setStation2() {
        arrayList.clear();
    }
}