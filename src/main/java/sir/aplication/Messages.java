package sir.aplication;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;

public class Messages {
    private int id;
    private String time;
    private String mess;


    public Messages(String mess) {
        Date date = new Date();
        SimpleDateFormat f = new SimpleDateFormat("HH:mm:ss");
        AtomicInteger id = new AtomicInteger();
        id.set(1);
        this.id = id.getAndIncrement();
        this.time = f.format(date);
        this.mess = mess;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
