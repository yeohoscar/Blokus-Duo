import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws Exception {
        
    }

    public void gameStart() {
        Board board = new Board();
        
        Scanner s = new Scanner(System.in);
        System.out.println("Enter Player 1 name: ");
        String tmp = s.nextLine();
        Player One = new Player(tmp);

        System.out.println("Enter Player 2 name: ");
        tmp = s.nextLine();
        Player Two = new Player(tmp);
        s.close();
    }
}
