
package Dal;

/**
 * Class storing connection details required to connect to db_booking Database
 * @author Zuzanna Kanafa
 */
public class ConnectionDetails {
    private static final String userName = "root";
    private static final String passWord = "root";
    private static final String driver = "com.mysql.jdbc.Driver";
    private static final String url = "jdbc:mysql://localhost:8889/db_booking?autoReconnect=true";

    public static String getUserName() {
        return userName;
    }

    public static String getPassWord() {
        return passWord;
    }

    public static String getDriver() {
        return driver;
    }

    public static String getUrl() {
        return url;
    }
    
}
