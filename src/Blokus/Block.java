package src.Blokus;

public class Block {
    private int x;
    private int y;

    public Block(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Block(Block b) {
        this.x = b.getX();
        this.y = b.getY();
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
    
    public void invertX() {
        x *= -1;
    }

    public void invertY() {
        y *= -1;
    }
}
