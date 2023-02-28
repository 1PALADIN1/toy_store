import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class Main {
    private static final String OUTPUT_FILE = "output.txt";

    public static void main(String[] args) {
        ToyStore toyStore = new ToyStore();

        FileOutputStream fileStream = null;
        try {
            System.out.println("Configure toys");
            toyStore.addNewToy("Человек-паук");
            toyStore.addNewToy("Плюшевый медведь", 200);
            toyStore.addNewToy("Гоночный автомобиль", 300);
            Toy stickerPack = toyStore.addNewToy("Набор стикеров", 500);
            toyStore.setWeight(stickerPack.getId(), 600);
            toyStore.addNewToy("Пластелин", 250);
            toyStore.addNewToy("Тамагочи", 300);

            File outputFile = new File(OUTPUT_FILE);
            outputFile.createNewFile();
            fileStream = new FileOutputStream(outputFile, false);

            System.out.println("Generate toy prizes queue...");
            toyStore.generateToyQueue();

            System.out.println("Write prizes to file");
            Toy nextToy;
            int toyNumber = 1;
            while ((nextToy = toyStore.nextToy()) != null) {
                fileStream.write((toyNumber + ". "+ nextToy + "\n").getBytes(StandardCharsets.UTF_8));
                toyNumber++;
            }

            System.out.println("File successfully saved!");
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
        } catch (IOException e) {
            System.out.println("File error: " + e);
        } finally {
            if (fileStream != null) {
                try {
                    fileStream.close();
                } catch (IOException e) {
                    System.out.println("Close file error: " + e);
                }
            }
        }
    }
}