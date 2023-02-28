public class Main {
    public static void main(String[] args) {
        ToyStore toyStore = new ToyStore();

        try {
            toyStore.addNewToy("Человек-паук");
            toyStore.addNewToy("Плюшевый медведь", 200);
            toyStore.addNewToy("Гоночный автомобиль", 300);
            Toy stickerPack = toyStore.addNewToy("Набор стикеров", 500);
            toyStore.setWeight(stickerPack.getId(), 600);
            toyStore.addNewToy("Пластелин", 250);
            toyStore.addNewToy("Тамагочи", 300);

            toyStore.generateToyQueue();
            Toy nextToy;
            while ((nextToy = toyStore.nextToy()) != null) {
                System.out.println("Got toy: " + nextToy);
            }
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
        }
    }
}