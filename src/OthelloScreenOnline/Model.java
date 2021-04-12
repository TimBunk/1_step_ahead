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
}
