package Provider;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MysqlService {
    static private Connection con=null;
    public static Connection getConnection(){
        if(con==null){
            try {
                String password = "j2$#%9fjaa$#%Jfj";
                String username = "root";
                con= DriverManager.getConnection("jdbc:mysql://localhost:3306/HotelManagementSystem", username, password);
                System.out.println("Success");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return con;
    }
}

