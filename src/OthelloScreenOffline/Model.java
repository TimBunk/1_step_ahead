package OthelloScreenOffline;

public class Model {
    public int CalculateDifficulty(String difficulty){
        if (difficulty.equals("Gemiddeld")){
            return 0;
        }
        else {
            return 1;
        }
    }

    public int NumberOnBoard(int row, int column){
        return ((row*8)+ column);
    }
}
