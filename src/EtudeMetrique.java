import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class EtudeMetrique {

    public static void main(String[] args) throws IOException {

        File jfreechart = new File("jfreechart-stats.csv");
        Scanner reader = new Scanner(jfreechart);
        String line;
        String[] classe;
        reader.nextLine();

        while(reader.hasNextLine()) {
            line = reader.nextLine();
            classe = line.split(",");
        }
        reader.close();

    }
}
