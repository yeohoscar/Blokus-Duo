/* 
 *  Represents the state of the game.
 */
public enum State {
    // First turn -> players are placing pieces on the starting spots
    FIRST,

    // Players are playing the games.
    MIDGAME,

    // Game is over, result is determined
    OVER,
}