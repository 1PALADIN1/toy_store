import java.util.*;

public class ToyStore {
    private static final int INCREASE_AMOUNT = 20;
    private static final int DEFAULT_WEIGHT = 100;
    private static final int QUEUE_SIZE = 100;

    private final Map<Integer, Toy> store = new HashMap<>();
    private final Queue<Integer> toyQueue = new LinkedList<>();
    private final Random random = new Random();


    public Toy addNewToy(String name) throws RuntimeException {
        return addNewToy(name, DEFAULT_WEIGHT);
    }

    public Toy addNewToy(String name, int weight) throws RuntimeException {
        if (weight < 100) {
            throw new RuntimeException("Weight must be >= 100!");
        }

        Toy toy = new Toy(name, weight);
        toy.addAmount(INCREASE_AMOUNT); // отгружаем начальное кол-во игрушек
        store.put(toy.getId(), toy);
        return toy;
    }

    public void setWeight(int id, int weight) throws RuntimeException {
        if (!store.containsKey(id)) {
            throw new RuntimeException("Toy with id " + id + " not found!");
        }

        if (weight < 100) {
            throw new RuntimeException("Weight must be >= 100!");
        }

        store.get(id).setWeight(weight);
    }

    // разыграть игрушки
    public void generateToyQueue() {
        toyQueue.clear();

        int totalWeight = 0;
        for (Map.Entry<Integer, Toy> entry : store.entrySet()) {
            totalWeight += entry.getValue().getWeight();
        }

        int[] toyArr = new int[QUEUE_SIZE];
        int toyIndex = 0;
        for (Map.Entry<Integer, Toy> entry : store.entrySet()) {
            // вычисляем долю в массиве
            int weightSize = (int)((float)entry.getValue().getWeight() / totalWeight * QUEUE_SIZE);

            int fillSize = toyIndex + weightSize;
            if (fillSize > toyArr.length) {
                fillSize = toyArr.length;
            }
            for (int i = toyIndex; i < fillSize; i++) {
                toyArr[toyIndex] = entry.getValue().getId();
                toyIndex++;
            }
        }

        // дозаполняем оставшиеся ячейки в массиве первой игрушкой (если есть)
        for (int i = toyIndex; i < toyArr.length; i++) {
            toyArr[i] = store.entrySet().iterator().next().getValue().getId();
        }

        // перемешиваем массив
        for (int i = 0; i < toyArr.length; i++) {
            int swapIndex = random.nextInt(QUEUE_SIZE);
            if (i == swapIndex) {
                continue;
            }

            int tmp = toyArr[i];
            toyArr[i] = toyArr[swapIndex];
            toyArr[swapIndex] = tmp;
        }

        // заполняем очередь
        for (int id : toyArr) {
            toyQueue.offer(id);
        }
    }

    // получить следущий приз
    public Toy nextToy() {
        if (toyQueue.peek() == null) {
            return null;
        }

        int id = toyQueue.poll();
        return getPrize(id);
    }

    private Toy getPrize(int id) throws RuntimeException {
        if (!store.containsKey(id)) {
            throw new RuntimeException("Toy with id " + id + " not found!");
        }

        Toy toy = store.get(id);
        while (!toy.getOne()) {
            // отгружаем игрушки на склад, если не хватает для выдачи приза
            toy.addAmount(INCREASE_AMOUNT);
        }

        return toy;
    }
}
