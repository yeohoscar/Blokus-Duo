package src.Blokus;

/**
 * Team ApplePlus
 * Members: Ao Peng     20202688
 *          Oscar Yeoh  20403662
 *          KarYen Yap  20202149
 * 
 * Represents state of the game
 */

public enum State {
    // First turn -> players are placing pieces on the starting spots
    FIRST,

    // Players are playing the games.
    MIDGAME,

    // Game is over, result is determined
    OVER,
}