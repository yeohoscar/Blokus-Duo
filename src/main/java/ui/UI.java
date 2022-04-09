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
import model.piece.Stock;

public interface UI {
    String getName();
    void displayFirstPlayer(String name);
    void displayResults(String result);
    void printCoordinateError();
    //void printUI(Board board, String name, int playerNo, Stock stock);
}
