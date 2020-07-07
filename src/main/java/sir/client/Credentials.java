package sir.client;

public class Credentials {

    private static String serverName;
    private static String database;
    private static String driverType;
    private static String sid;
    private static String ip;
    private static String port;
    private static String user;
    private static String pass;
    private static String jdbc;


    public static String getServerName() {
        return serverName;
    }

    public static void setServerName(String serverName) {
        Credentials.serverName = serverName;
    }

    public static String getIp() {
        return ip;
    }

    public static void setIp(String ip) {
        Credentials.ip = ip;
    }

    public static String getPort() {
        return port;
    }

    public static void setPort(String port) {
        Credentials.port = port;
    }

    public static String getUser() {
        return user;
    }

    public static void setUser(String user) {
        Credentials.user = user;
    }

    public static String getPass() {
        return pass;
    }

    public static void setPass(String pass) {
        Credentials.pass = pass;
    }

    public static String getDatabase() {
        return database;
    }

    public static void setDatabase(String database) {
        Credentials.database = database;
    }

    public static String getDriverType() {
        return driverType;
    }

    public static void setDriverType(String driverType) {
        Credentials.driverType = driverType;
    }

    public static String getSid() {
        return sid;
    }

    public static void setSid(String sid) {
        Credentials.sid = sid;
    }

    public static String getJdbc() {
        return jdbc;
    }

    public static void setJdbc(String jdbc) {
        Credentials.jdbc = jdbc;
    }
}
