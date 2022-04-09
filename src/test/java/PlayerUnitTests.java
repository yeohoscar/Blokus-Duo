/**
 * Team ApplePlus
 * Members: Ao Peng     20202688
 *          Oscar Yeoh  20403662
 *          KarYen Yap  20202149
 * 
 * Unit Test for Player Class
 *  - test the functions in Player Class
 */

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.ByteArrayInputStream;
import java.util.Scanner;

import model.Board;
import model.Player;
import org.junit.jupiter.api.Test;

import ui.UI;
import ui.text.*;

public class PlayerUnitTests {
    @Test
    void testInitPlayer() {
        ByteArrayInputStream in = new ByteArrayInputStream("a\n".getBytes());
        Scanner s = new Scanner(in);
        UI ui = new TextUI();
        Player player = new TextPlayer("X", (TextUI)ui);

        assertEquals("X", player.getColor());
        assertEquals("a", player.getName());
        s.close();
    }
}
