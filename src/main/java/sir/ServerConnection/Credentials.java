package sir.ServerConnection;

public class Credentials {

    private static String name;
    private static String ip;
    private static String port;
    private static String user;
    private static String pass;


    public static String getName() {
        return name;
    }

    public static void setName(String name) {
        Credentials.name = name;
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


}
