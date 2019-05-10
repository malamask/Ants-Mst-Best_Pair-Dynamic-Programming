public class RedAnt extends Ant {
    private int capacity;

    public RedAnt(int id, int x, int y, int capacity) {
        super(id, x, y);
        this.capacity = capacity; // red ant capacity
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }
}
