/**
 * Team ApplePlus
 * Members: Ao Peng     20202688
 *          Oscar Yeoh  20403662
 *          KarYen Yap  20202149
 * 
 * Unit Test for Block Class
 *  - test the functions in Block Class 
 */

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.Test;

public class BlockUnitTests {
    @Test
    void testDeepCopy() {
        Block original = new Block(1, 2);
        Block copy = new Block(original);

        assertEquals(original.getX(), copy.getX());
        assertEquals(original.getY(), copy.getY());

        if (original.equals(copy)) {
            fail("not deep copy");
        }
    }

    @Test
    void testInvert() {
        Block b = new Block(2, 3);

        b.invertX();
        assertEquals(-2, b.getX());

        b.invertY();
        assertEquals(-3, b.getY());

        b.invertY();
        assertEquals(3, b.getY());
    }
}
