package Game;

import javax.sound.sampled.Port;

public class PlayerData {

    private int Portnumber;

    private String Ipadres;

    private String username;

    public PlayerData(String username) {
        this.username = username;
        this.Ipadres = "145.33.225.170";
        //this.Ipadres = "localhost";
        this.Portnumber = 7789;
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
}