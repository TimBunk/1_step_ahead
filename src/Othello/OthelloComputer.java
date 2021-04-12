package Othello;

import Shared.AbstractBoard;
import Shared.AbstractPlayer;
import Shared.Pair;

import java.util.*;
import java.util.concurrent.*;

public abstract class OthelloComputer extends AbstractPlayer {

    private ExecutorService executorService;
    private int depth;
    private long maxTimeInMilliseconds;
    private final long biasMaxTimeInMilliseconds = 100;
    private final long overTimeInMilliseconds = 50;

    /**
     * Constructor
     * @param depth The depth is used for the minimax algorithm
     * @param maxTimeInMilliseconds The amount of time we have to calculate the best possible move
     */
    OthelloComputer(int depth, long maxTimeInMilliseconds) {
        executorService = Executors.newFixedThreadPool(32);
        this.depth = depth;
        this.maxTimeInMilliseconds = maxTimeInMilliseconds;
    }

    /**
     * @param board The board on which the move is placed
     * @return The place on the board where we want to do a move
     */
    @Override
    public int doMove(AbstractBoard board) {
        // Sla de startTime op
        long startTime = System.currentTimeMillis();
        // Loop door alle tiles van het bord
        int[] validMoves = board.findValidMoves(getCharacter());
        LinkedList<Pair<Integer, Pair<OthelloMinimaxWorker, Future<Integer>>>> futures = new LinkedList<>();
        for (int i = 0; i < validMoves.length; i++) {
            // Maak een clone van het board en plaats de move erin
            AbstractBoard cloneOfBoard = board.clone();
            cloneOfBoard.placeMove(validMoves[i], getCharacter());
            // Maak een minimaxworker om te berekenen hoe goed de move was
            OthelloMinimaxWorker omw = new OthelloMinimaxWorker(this, depth, cloneOfBoard);
            futures.add(new Pair(validMoves[i], new Pair(omw, executorService.submit(omw))));
        }

        // bestEvaluation en bestMove houden bij welke move de best evalutie heeft
        int bestEvaluation = Integer.MIN_VALUE;
        int bestMove = validMoves[0];
        // Loop met een interator door alle futures heen
        Iterator<Pair<Integer, Pair<OthelloMinimaxWorker, Future<Integer>>>> it = futures.iterator();
        int completedWokers = 0;
        int completedWokersOverTime = 0;
        while (it.hasNext()) {
            // Extract het eerste element
            Pair<Integer, Pair<OthelloMinimaxWorker, Future<Integer>>> pair = it.next();
            int move = pair.getElement0();
            Pair<OthelloMinimaxWorker, Future<Integer>> pair2 = pair.getElement1();
            OthelloMinimaxWorker omw = pair2.getElement0();
            Future<Integer> f = pair2.getElement1();
            // Check of de tijd nog niet verstreken is die we hebben
            long elapsedTime = System.currentTimeMillis() - startTime;
            if (elapsedTime <= maxTimeInMilliseconds) {
                try {
                    // Pak de evaluatie voor de move uit de future. De future is wel tijdsgebonden dus als die niet op tijd
                    // Kan berekene wat de evaluatie is voor die move dan skippen we de move en gaan we door met de volgende
                    int evaluation = f.get(maxTimeInMilliseconds - biasMaxTimeInMilliseconds - elapsedTime, TimeUnit.MILLISECONDS);
                    // De code wordt alleen uitgevoerd als de future op tijd klaar is anders krijg je een TimeoutException
                    completedWokers++;
                    // Bekijk of deze move beter is dan de vorige moves
                    if (evaluation > bestEvaluation) {
                        bestMove = move;
                        bestEvaluation = evaluation;
                    }
                } catch (TimeoutException e) {
                    // Deze exception treed op wanneer de future niet op tijd klaar was
                    e.printStackTrace();
                    // Stop de OthelloMinimaxWorker en probeer nogmaals de waarde te pakken als dat lukt binnen de overTime
                    omw.stop();
                    int evaluation = 0;
                    elapsedTime = System.currentTimeMillis() - startTime;
                    try {
                        evaluation = f.get(maxTimeInMilliseconds - overTimeInMilliseconds - elapsedTime, TimeUnit.MILLISECONDS);
                        // De code wordt alleen uitgevoerd als de future op tijd klaar is anders krijg je een TimeoutException
                        completedWokers++;
                        completedWokersOverTime++;
                        // Bekijk of deze move beter is dan de vorige moves
                        if (evaluation > bestEvaluation) {
                            bestMove = move;
                            bestEvaluation = evaluation;
                        }
                    } catch (InterruptedException interruptedException) {
                        interruptedException.printStackTrace();
                    } catch (ExecutionException executionException) {
                        executionException.printStackTrace();
                    } catch (TimeoutException timeoutException) {
                        timeoutException.printStackTrace();
                        System.out.println("Timeout in overtime");
                    }

                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } finally {
                    // Verwijder het element uit de linkedlist
                    it.remove();
                }
            }
        }
        // Print uit hoeveel workers er op tijd klaar waren
        System.out.println("" + completedWokers + " van de " + validMoves.length + " workers zijn binnen de tijd klaar.");
        System.out.println("" + completedWokersOverTime + " hier van moesten vroegtijdig stoppen.");
        // Geef een goede move terug
        return bestMove;
    }

    /**
     * @param board The board to be evaluated
     * @return How good the board is, evaluated as how many stones more are controlled than by the opponent
     */
    protected abstract int eval(AbstractBoard board);
}
