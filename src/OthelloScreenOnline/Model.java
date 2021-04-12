package OthelloScreenOnline;

public class Model {
    public int CalculateDifficulty(String difficulty){
        if (difficulty.equals("Gemiddeld")){
            return 0;
        }
        else {
            return 1;
        }
    }

    public String CalculateStringDifficulty(int dif){
        if (dif == 0){
            return "Gemiddeld";
        }else {
            return "Moeilijk";
        }
    }
}
