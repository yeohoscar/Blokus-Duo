/**
 * Team ApplePlus
 * Members: Ao Peng     20202688
 *          Oscar Yeoh  20403662
 *          KarYen Yap  20202149
 *
 * UI interface
 *  - holds methods needed by text and graphical UIs.
 */

package ui;

import model.Board;
import model.Player;

public interface UI {
    String getName();
    void displayFirstPlayer(String name);
    void updateUI(Player player, Board board);
    void displayResults(String result);
    void printCoordinateError();
}
