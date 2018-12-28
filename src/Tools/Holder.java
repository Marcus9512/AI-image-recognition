package Tools;

public class Holder implements Comparable<Holder> {
    int x;
    int y;
    int sol;
    double per;

    public Holder(int sol, double per) {
        this.sol = sol;
        this.per = per;
    }
    @Override
    public boolean equals(Object obj){
        if(!(obj instanceof  Holder))
            return false;
        Holder tmp = (Holder) obj;
        if(tmp.sol == sol && tmp.x == x && tmp.y == y && tmp.per == per )
            return true;
        return false;

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

    public int getSol(){
        return sol;
    }
    public double getHowClose(){
        return per;
    }

    @Override
    public int compareTo(Holder o) {
        if(per < o.per){
            return -1;
        }else if(per > o.per)
            return 1;
        return 0;
    }
}
