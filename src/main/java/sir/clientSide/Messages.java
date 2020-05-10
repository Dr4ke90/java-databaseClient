package sir.clientSide;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Messages {
    private String time;
    private String mess;


    public Messages(String mess) {
        Date date = new Date();
        SimpleDateFormat f = new SimpleDateFormat("HH:mm:ss");
        this.time = f.format(date);
        this.mess = mess;
    }



    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getMess() {
        return mess;
    }

    public void setMess(String mess) {
        this.mess = mess;
    }
}
