package Game;

public class PlayerData {

    private int Portnumber;
    private String Ipadres;
    private String username;
    private int timeOutTime;
    private static volatile PlayerData playerData;

    private PlayerData() {
        this.Ipadres = "145.33.225.170";
        this.Portnumber = 7789;
        this.timeOutTime = 9500;
    }

    public static PlayerData getInstance(){
        if (playerData == null){
            synchronized (PlayerData.class){
                playerData = new PlayerData();
            }
        }
        return playerData;
    }

    public void setPortnumber(int portnumber) {
        Portnumber = portnumber;
    }

    public void setIpadres(String ipadres) {
        Ipadres = ipadres;
    }

    public String getUsername() {
        return username;
    }

    public String getIpadres()  {
        return Ipadres;
    }

    public int getPortnumber()  {
        return Portnumber;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getTimeOutTime() {
        return timeOutTime;
    }

    public void setTimeOutTime(int timeOutTime) {
        this.timeOutTime = timeOutTime;
    }
}