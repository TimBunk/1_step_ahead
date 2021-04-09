package Othello;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ValidMovesTest {

    OthelloBoard board = new OthelloBoard();

    @BeforeEach
    void setup(){
        board.initializeBoard(64);
        //A series of moves that led to an illegal move at position 56
        board.placeMove(19, 'Z');
        board.placeMove(18,'W');
        board.placeMove(26, 'Z');
        board.placeMove(20,'W');
        board.placeMove(21, 'Z');
        board.placeMove(34,'W');
        board.placeMove(25, 'Z');
        board.placeMove(14,'W');
        board.placeMove(42, 'Z');
        board.placeMove(50,'W');
        board.placeMove(43, 'Z');
        board.placeMove(33,'W');
        board.placeMove(13, 'Z');
        board.placeMove(5,'W');
        board.placeMove(29, 'Z');
        board.placeMove(22,'W');
        board.placeMove(7, 'Z');
        board.placeMove(23,'W');
        board.placeMove(37, 'Z');
        board.placeMove(16,'W');
        board.placeMove(24, 'Z');
        board.placeMove(32,'W');
        board.placeMove(40, 'Z');
        board.placeMove(45,'W');
        board.placeMove(58, 'Z');
        board.placeMove(48,'W');
        board.placeMove(11, 'Z');
        board.placeMove(41,'W');
        board.placeMove(38, 'Z');
        board.placeMove(3,'W');
        board.placeMove(2, 'Z');
        board.placeMove(1,'W');
        board.placeMove(44, 'Z');
        board.placeMove(59,'W');
        board.placeMove(51, 'Z');
        board.placeMove(57,'W');
        board.placeMove(10, 'Z');
        board.placeMove(39,'W');
        board.placeMove(47, 'Z');
        board.placeMove(17,'W');
        board.placeMove(31, 'Z');
        board.placeMove(53,'W');
        board.placeMove(4, 'Z');
        board.placeMove(55,'W');
        board.placeMove(0, 'Z');
        board.placeMove(6,'W');
        board.placeMove(12, 'Z');
        board.placeMove(15,'W');
        board.placeMove(63, 'Z');
        board.placeMove(52,'W');
        board.placeMove(60, 'Z');
    }

    @Test
    void isMoveValidTest(){
        assertEquals(false, board.isMoveValidLeft(56, 'W'));
        assertEquals(false, board.isMoveValidRight(56, 'W'));
        assertEquals(false, board.isMoveValidDown(56, 'W'));
        assertEquals(false, board.isMoveValidUp(56, 'W'));
        assertEquals(false, board.isMoveValidLeftUp(56, 'W'));
        assertEquals(false, board.isMoveValidRightUp(56, 'W'));
        assertEquals(false, board.isMoveValidLeftDown(56, 'W'));
        assertEquals(false, board.isMoveValidRightDown(56, 'W'));
    }
}
