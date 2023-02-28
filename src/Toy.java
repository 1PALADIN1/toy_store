public class Toy {
    private static int NEXT_ID = 1;

    private final int id;
    private final String name;
    private int amount;
    private int weight;

    public Toy(String name, int weight) {
        this.id = NEXT_ID;
        this.name = name;
        this.amount = 0;
        this.weight = weight;
        NEXT_ID++;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public int getWeight() {
        return weight;
    }

    public void addAmount(int amount) {
        this.amount += amount;
    }

    public int getAmount() {
        return amount;
    }

    public boolean getOne() {
        if (amount == 0) {
            return false;
        }

        amount--;
        return true;
    }

    @Override
    public String toString() {
        return name + " [ID:" + id + "]";
    }
}
