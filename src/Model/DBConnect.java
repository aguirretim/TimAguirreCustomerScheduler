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
    
    public DBConnect(){
    
        try{
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://52.206.157.109:3306/U04k77", "U04k77", "53688267207");
            st = con.createStatement();
        }
        catch(Exception ex){
            System.out.println("erro: "+ ex);           
        }}
    
    public void getData(){
        try{
        String query =  "select * from user";
            rs=st.executeQuery(query);
            System.out.println("Records from Database");
            while(rs.next()){
        String userName = rs.getString("userName");
        String password = rs.getString("password");
        System.out.println("Username: "+ userName+" password: "+password);
        }
        }catch(Exception ex){
        System.out.println("erro: "+ ex); 
        }
        }
    
}
