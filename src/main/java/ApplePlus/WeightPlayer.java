package ApplePlus;

public class WeightPlayer {
    private double blockerWeight;
    private double builderWeight;
    private double bigPieceWeight;
    private int ID;

    public WeightPlayer(int ID, double blockerWeight, double builderWeight, double bigPieceWeight) {
        this.builderWeight = builderWeight;
        this.blockerWeight = blockerWeight;
        this.bigPieceWeight = bigPieceWeight;
        this.ID = ID;
    }

    public double getBigPieceWeight() {
        return bigPieceWeight;
    }

    public double getBlockerWeight() {
        return blockerWeight;
    }

    public double getBuilderWeight() {
        return builderWeight;
    }

    public int getID() {
        return ID;
    }
}
