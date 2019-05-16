public class BlackAnt extends Ant {
    private int w1;
    private int w2;
    private int w3;
    private int w4;
    private int w5;

    public BlackAnt(int id, float x, float y, int w1, int w2, int w3, int w4, int w5) {
        super(id, x, y);
        this.w1 = w1;
        this.w2 = w2;
        this.w3 = w3;
        this.w4 = w4;
        this.w5 = w5;
    }

    public int getW1() {
        return w1;
    }

    public void setW1(int w1) {
        this.w1 = w1;
    }

    public int getW2() {
        return w2;
    }

    public void setW2(int w2) {
        this.w2 = w2;
    }

    public int getW3() {
        return w3;
    }

    public void setW3(int w3) {
        this.w3 = w3;
    }

    public int getW4() {
        return w4;
    }

    public void setW4(int w4) {
        this.w4 = w4;
    }

    public int getW5() {
        return w5;
    }

    public void setW5(int w5) {
        this.w5 = w5;
    }
}
