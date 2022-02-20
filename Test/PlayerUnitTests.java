import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.ByteArrayInputStream;
import java.util.Scanner;

import org.junit.jupiter.api.Test;

public class PlayerUnitTests {
    @Test
    void testInitPlayer() {
        ByteArrayInputStream in = new ByteArrayInputStream("a\n".getBytes());
        Scanner s = new Scanner(in);
        Player p = Player.initPlayer("X", s);

        assertEquals("X", p.getColor());
        assertEquals("a", p.getName());
        s.close();
    }
}
