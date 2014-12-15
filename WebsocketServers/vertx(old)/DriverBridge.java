import java.sql.Connection;
import java.sql.SQLException;
import java.sql.DriverManager;

public class DriverBridge {

    public static final void loadDriver(String cls)
    throws ClassNotFoundException {
        Class.forName(cls);
    }

    public static final Connection getConnection(String url, String user, String pass) throws SQLException {
        return DriverManager.getConnection(url, user, pass);
    }
}