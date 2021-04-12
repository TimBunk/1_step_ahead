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

    /**
     * @param portnumber portnumber with which to establish a connection
     */
    public void setPortnumber(int portnumber) {
        Portnumber = portnumber;
    }

    /**
     * @param ipadres ipadres with which to establish a connection
     */
    public void setIpadres(String ipadres) {
        Ipadres = ipadres;
    }

    /**
     * @return the username entered by the client.
     */
    public String getUsername() {
        return username;
    }

    /**
     * @return the ipAdres entered by the client.
     */
    public String getIpadres()  {
        return Ipadres;
    }

    /**
     * @return the portNumber entered by the client.
     */
    public int getPortnumber()  {
        return Portnumber;
    }

    /**
     * @param username username which the client enters.
     */
    public void setUsername(String username) {
        this.username = username;
    }

    public int getTimeOutTime() {
        return timeOutTime;
    }

    /**
     * @param timeOutTime time the users wants to have a timeout.
     */
    public void setTimeOutTime(int timeOutTime) {
        this.timeOutTime = timeOutTime;
    }
}