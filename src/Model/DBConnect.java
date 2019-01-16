package Model;

import java.sql.*;

/**
 *
 * @author Tim
 */
public class DBConnect {

    private Connection con;
    private Statement st;
    private ResultSet rs;
    private String userName;
    private String password;

    public DBConnect() {

        /**
         * **********************************
         * Connects to MySql Database.
           ***********************************
         */
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://52.206.157.109:3306/U04k77", "U04k77", "53688267207");
            st = con.createStatement();
        } catch (Exception ex) {
            System.out.println("erro: " + ex);
        }
    }

    //Does query and prints data.
    public void getData() {
        try {
            String query = "select * from user";
            rs = st.executeQuery(query);
            System.out.println("Records from Database");
            while (rs.next()) {
                String userName = rs.getString("userName");
                String password = rs.getString("password");
                System.out.println("Username: " + userName + " password: " + password);
            }
        } catch (Exception ex) {
            System.out.println("erro: " + ex);
        }
    }

    public boolean getLoginData(String UN, String PW) {
         boolean result = false;
        
        try {
            String userT = UN;
            String passT = PW;
           
            String query = "select userName,password from user where userName ='" + userT + "'and password ='" + passT + "'";
            System.out.println(query);
            rs = st.executeQuery(query);

            System.out.println("Attempting to login");
            int count = 0;
            while (rs.next()) {
                count = count + 1;

                if (count == 1) {
                    System.out.println("user found");
                    result = true;

                } else if (count > 1) {
                    System.out.println("user denied");
                } else {
                    System.out.println("user does not exsist");
                }
            }
        } catch (Exception ex) {
            System.out.println("erro: " + ex);
        }
        System.out.println(result);
    return result;
    }

}
