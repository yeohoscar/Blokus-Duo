/**
 * Team ApplePlus
 * Members: Ao Peng     20202688
 *          Oscar Yeoh  20403662
 *          KarYen Yap  20202149
 * 
 * Unit Test for Stock Class
 *  - test the functions in Stock Class
 */

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class StockUnitTests {
    @Test
    void testInit() {
        Stock s = new Stock();

        assertEquals(21, s.getPieces().size());
    }

    @Test
    void testPrint() {
        Stock s = new Stock();

        assertEquals("I1 I2 I3 I4 I5 V3 L4 Z4 O4 L5 T5 V5 N Z5 T4 P W U F X Y ", s.toString());

        for (Piece p : s.getPieces()) {
            if (p.getName().equals("L4")) {
                s.getPieces().remove(p);
                break;
            }
        }
        assertEquals("I1 I2 I3 I4 I5 V3 Z4 O4 L5 T5 V5 N Z5 T4 P W U F X Y ", s.toString());
    }
}
