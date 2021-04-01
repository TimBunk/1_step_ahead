package TicTacToeScreen;

public class Model {
    public int CalculateDifficulty(String difficulty){
        if (difficulty.equals("Gemiddeld")){
            return 0;
        }
        else {
            return 1;
        }
    }

    public int TableToInt(int row, int column){
        if (row == 0){
            return column;
        }
        else if (row== 1){
            return column + 3;
        }
        else{
            return column + 6;
        }
    }
}
