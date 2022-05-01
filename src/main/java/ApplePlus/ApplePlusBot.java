package ApplePlus;

import SimpleBot.SimpleBotPlayer;
import model.Board;
import model.Gamepiece;
import model.Location;
import model.Move;

import java.util.ArrayList;
import java.util.List;

public class ApplePlusBot extends SimpleBotPlayer {
    protected static class Node<T> {
        private List<Node<T>> children = new ArrayList<Node<T>>();
        private Node<T> parent = null;
        private T data = null;
        private double value = 0.0;

        public Node(T data, double value) {
            this.data = data;
            this.value = value;
        }

        public Node(T data, Node<T> parent) {
            this.data = data;
            this.parent = parent;
        }

        public T getData() {
            return data;
        }

        public double getValue() {
            return value;
        }

        public Node<T> getParent() {
            return parent;
        }

        public List<Node<T>> getChildren() {
            return children;
        }

        public void setData(T data, double value) {
            this.data = data;
            this.value = value;
        }

        public void setParent(Node<T> parent) {
            this.parent = parent;
        }

        public void addChild(Node<T> child) {
            child.setParent(this);
            this.children.add(child);
        }

        public void addChild(T data, double value) {
            this.addChild(new Node<T>(data, value));
        }

        public void addChildren(List<Node<T>> children) {
            for (Node<T> node : children) {
                node.setParent(this);
            }
            this.children.addAll(children);
        }

        public boolean isLeafNode() {
            return this.children.isEmpty();
        }
    }

    Node<Move> optimal = null;
    private int builderWeight = 1;
    private int blockerWeight = 1;
    private int bigPieceWeight = 1;

    public ApplePlusBot(int playerno) {
        super(playerno);
    }

    @Override
    public Move makeMove(Board board) {
        if (isFirstMove) {
            isFirstMove = false;
            // Play gamepiece "F" at the starting location in default orientation
            return new Move(
                    this,
                    new Gamepiece(getGamepieceSet().get("F")),
                    "F",
                    new Location(Board.startLocations[getPlayerNo()]));
        } else {
            ArrayList<Move> moves = getPlayerMoves(this, board);
            return getOptimalMove(moves);
        }
    }

    /**
     * Iterates through bot's moves and gets the best move
     *
     * @param moves the bot's available moves
     * @return optimal move
     */
    private Move getOptimalMove(ArrayList<Move> moves) {
        /*double maxPoints = -1000;
        Move optimalMove = null;
        for (Move move : moves) {
            double points = gradeMove(move, moves.size());
            if (maxPoints < points) {
                //System.out.println(moves.size());
                maxPoints = points;
                optimalMove = move;
            }
        }*/

        Node<Move> root = new Node<Move>(null, 0.0);

        for (int i = 0; i < moves.size(); i++) {
            double point = gradeMove(moves.get(i), moves.size(), board);
            root.addChild(moves.get(i), point);
            /*root.addChild(moves.get(i), 0.0);
            possible.makeMove(moves.get(i));
            ArrayList<Move> opMove = getPlayerMoves(this.opponent, possible);
            for (int j = 0; j < opMove.size(); j++) {
                List<Node<Move>> child = root.getChildren();
                double point = gradeMove(opMove.get(j), opMove.size(), possible);
                child.get(i).addChild(opMove.get(j), point);
            }*/
        }

        minimax(root, 3, Double.MIN_VALUE, Double.MAX_VALUE, true);

        //System.out.println(optimal.getData().getGamepieceName() + " " + optimal.getData().getLocation().getX() + " " + optimal.getData().getLocation().getY());
        return optimal.getData();
    }

    /**
     * Calculates how good the move is and assigns it points based on how good the move is.
     *
     * @param move move to be graded
     * @return the points the move got
     */
    public double gradeMove(Move move, int numMoves, Board b) {
        return builder(move, numMoves, b) * builderWeight + blocker(move, b) * blockerWeight + bigPiece(move) * bigPieceWeight;
    }
    
    private double bigPiece(Move move) {
        return move.getGamepiece().getLocations().length / this.getGamepieceSet().getPieces().size();
    }

    /**
     * Grades move based on builder
     * Not completed
     *
     * @param move
     * @return
     */
    private int builder(Move move, int numMovesBefore, Board b) {
        Board possibleBoard = new Board(b);
        possibleBoard.makeMove(move);

        getGamepieceSet().remove(move.getGamepieceName());
        int numMovesAfter = getPlayerMoves(this, possibleBoard).size();
        getGamepieceSet().getPieces().put(move.getGamepieceName(), move.getGamepiece());

        return numMovesAfter - numMovesBefore;
    }

    private int blocker(Move move, Board b) {
        Board possibleBoard = new Board(b);
        possibleBoard.makeMove(move);

        int before = getPlayerMoves(this.opponent, b).size();
        int after = getPlayerMoves(this.opponent, possibleBoard).size();

        return before - after;
    }

    private double minimax(Node<Move> point, int depth, double alpha, double beta, boolean maximizingPlayer) {
        if (depth == 0 || point.isLeafNode()) {
            return point.getValue();
        }

        if (maximizingPlayer) {
            double max = -10000;

            for (Node<Move> n : point.getChildren()) {
                double value = minimax(n, depth - 1, alpha, beta, false);
                max = Math.max(max, value);
                if (max == value) {
                    optimal = n;
                }
                if (max >= beta) {
                    break;
                }
                alpha = Math.max(alpha, max);
            }

            return max;
        } else {
            double min = Double.MAX_VALUE;

            for (Node<Move> n : point.getChildren()) {
                double value = minimax(n, depth - 1, alpha, beta, true);
                min = Math.min(min, value);
                if (min == value) {
                    optimal = n;
                }
                if (min <= alpha) {
                    break;
                }
                beta = Math.min(beta, min);
            }

            return min;
        }
    }

    /*private void findMedian(ArrayList<Double> pointList, ArrayList<Move> moves) {
        sort(pointList, moves, 0, pointList.size() - 1);
        System.out.println("a" + pointList.size() + " " + moves.size());

        int quartile = pointList.size() * 25 / 100;

        for (int i = 0, j = pointList.size() - 2; i < quartile; i++, j-=2) {
            pointList.remove(0);
            pointList.remove(j);
            moves.remove(0);
            moves.remove(j);
        }

        System.out.println("b" + pointList.size() + " " + moves.size());
    }

    private void merge(ArrayList<Double> points, ArrayList<Move> moves, int left, int mid, int right) {
        int n1 = mid - left + 1;
        int n2 = right - mid;

        ArrayList<Double> leftPoint = new ArrayList<>();
        ArrayList<Double> rightPoint = new ArrayList<>();
        ArrayList<Move> leftMove = new ArrayList<>();
        ArrayList<Move> rightMove = new ArrayList<>();

        for (int i = 0; i < n1; i++) {
            leftPoint.add(points.get(left + i));
            leftMove.add(moves.get(left + i));
        }
        for (int j = 0; j < n2; j++) {
            rightPoint.add(points.get(mid + 1 + j));
            rightMove.add(moves.get(mid + 1 + j));
        }

        int i = 0, j = 0;
        int k = left;

        while (i < n1 && j < n2) {
            if (leftPoint.get(i) <= rightPoint.get(j)) {
                points.set(k, leftPoint.get(i));
                moves.set(k, leftMove.get(i));
                i++;
            } else {
                points.set(k, rightPoint.get(j));
                moves.set(k, rightMove.get(j));
                j++;
            }
            k++;
        }

        while (i < n1) {
            points.set(k, leftPoint.get(i));
            moves.set(k, leftMove.get(i));
            i++;
            k++;
        }

        while (j < n2) {
            points.set(k, rightPoint.get(j));
            moves.set(k, rightMove.get(j));
            j++;
            k++;
        }
    }

    private void sort(ArrayList<Double> points, ArrayList<Move> moves, int left, int right) {
        if (left < right) {
            int mid = (right + left) / 2;

            sort(points, moves, left, mid);
            sort(points, moves, mid + 1, right);

            merge(points, moves, left, mid, right);
        }
    }*/
}
