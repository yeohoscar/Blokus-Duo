package ApplePlus;

import SimpleBot.SimpleBotPlayer;
import model.Board;
import model.Player;
import ui.UI;
import ui.text.TextUI;

import java.util.Random;

public class WeightTesting {
    private static int fitness[][] = new int[10][10];
    private static WeightPlayer[] weightPlayers;

    public static void main(String[] args) {
        Random rand = new Random();
        weightPlayers = new WeightPlayer[10];
        for (int i = 0; i < 10; i++) {
            double weight1 = rand.nextDouble();
            double weight2 = rand.nextDouble(1-weight1);
            double weight3 = 1 - weight1 - weight2;
            weightPlayers[i] = new WeightPlayer(i, weight1, weight2, weight3);
        }

        for (int i = 0; i <= 1; i++) {
            for (int j = i+1; j <= 1; j++) {
                for (int k = 0; k < 1; k++) {
                    test(weightPlayers[i], weightPlayers[j]);
                }
            }
        }

        for (int i = 0; i < 10; i++) {
            int sum = 0;
            for (int j = 0; j < 10; j++) {
                if (i == j) {
                    continue;
                }
                sum += fitness[i][j];
            }
            System.out.println("Fitness for bot " + i + ": " + sum);
        }
    }

    public static void test(WeightPlayer p1, WeightPlayer p2) {
        Board board = new Board();
        Player[] players = new Player[Board.startLocations.length];
        UI ui;
        BlokusDuoWeight blokusDuoWeight;
        int activePlayer = new Random().nextInt(2);

        SimpleBotPlayer botPlayer1 = new ApplePlusBot(0, p1.getBuilderWeight(), p1.getBlockerWeight(), p1.getBigPieceWeight());
        botPlayer1.setBoard(board);
        players[0] = botPlayer1;

        SimpleBotPlayer botPlayer2 = new ApplePlusBot(1, p2.getBuilderWeight(), p2.getBlockerWeight(), p2.getBigPieceWeight());
        botPlayer2.setBoard(board);
        players[1] = botPlayer2;

        ui = new TextUI(board, players);
        for (Player player : players) {
            player.setUI(ui);
        }

        if (SimpleBotPlayer.class.isAssignableFrom(players[0].getClass())) {
            ((SimpleBotPlayer)players[0]).setOpponent(players[1]);
        }

        if (SimpleBotPlayer.class.isAssignableFrom(players[1].getClass())) {
            ((SimpleBotPlayer)players[1]).setOpponent(players[0]);
        }

        blokusDuoWeight = new BlokusDuoWeight(board,players,ui,activePlayer);

        int result = blokusDuoWeight.run();
        if (result == 1) {
            fitness[p1.getID()][p2.getID()] += 2;
        } else if (result == 0) {
            fitness[p1.getID()][p2.getID()] += 1;
            fitness[p2.getID()][p1.getID()] += 1;
        } else {
            fitness[p2.getID()][p1.getID()] += 2;
        }
    }
}
