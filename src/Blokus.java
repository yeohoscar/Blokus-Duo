import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Blokus {
    private final List<Player> players;
    private final Board board;
    private State state;


    public Blokus() {
        this.players = new ArrayList<>(Arrays.asList(Player.initPlayer("X"), Player.initPlayer("O")));
        this.board = new Board();
        this.state = State.FIRST;
    }
    
    public boolean isFirstTurn() {
        return state == State.FIRST;
    }
}