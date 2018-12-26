package Tools;

public class Holder {
    int sol;
    double per;

    public Holder(int sol, double per) {
        this.sol = sol;
        this.per = per;
    }
    public int getSol(){
        return sol;
    }
    public double getHowClose(){
        return per;
    }
}
