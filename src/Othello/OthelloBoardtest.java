package Othello;

import Shared.AbstractBoard;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;

public class OthelloBoardtest extends OthelloBoard {

    /**
     * @param characterToMove The character for which we want to find valid moves
     * @return Array of the positions of valid moves
     */
    @Override
    public int[] findValidMoves(char characterToMove) {
        ArrayList<Integer> validMoves = new ArrayList<>();
        for (int i = 0; i < board.length; i++) {
            if(isMoveValid(i, characterToMove)){
                validMoves.add(i);
            }
        }
        return validMoves.stream().mapToInt(Integer::intValue).toArray();
    }
}
