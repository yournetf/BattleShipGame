package Project2SolutionF23;

public class Ship {

    private String name;
    private int size;
    private int hits;

    public Ship(String name, int size) {
        this.name = name;
        this.size = size;
    }

    public int getSize() {
        return size;
    }

    public void hit() {
        hits++;
    }

    public boolean isSunk() {
        return hits == size;
    }

    public String toString() {
        return name;
    }

}
