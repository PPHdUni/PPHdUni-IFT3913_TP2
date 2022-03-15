import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class EtudeMetrique {

    private static ArrayList<Metrique> metriqueClasses = new ArrayList<Metrique>();
    public int sommeMed,
                sommeSup,
                sommeInf;

    public static void main(String[] args) throws IOException {

        File jfreechart = new File("jfreechart-stats.csv");
        Scanner reader = new Scanner(jfreechart);
        String line;
        String[] classe;
        reader.nextLine();

        while(reader.hasNextLine()) {
            line = reader.nextLine();
            classe = line.split(",");
            metriqueClasses.add(new Metrique(classe[0],Integer.parseInt(classe[1]),
                    Float.parseFloat(classe[2]),Integer.parseInt(classe[3]),Integer.parseInt(classe[4])));
        }
        reader.close();

        PrintWriter writer = new PrintWriter("etude_metrique.txt");
        BoiteAMoustache(writer);
        writer.close();

    }

    private static void BoiteAMoustache(PrintWriter writer) {
        float[] mediane = {0, 0, 0, 0};

        for (Metrique metriqueClass : metriqueClasses) {
            mediane[0] += metriqueClass.NLOC;
            mediane[1] += metriqueClass.DCP;
            mediane[2] += metriqueClass.NOCom;
            mediane[3] += metriqueClass.WMC;
        }

        for(int i = 0; i < mediane.length; i++)
        {
            mediane[i] = mediane[i]/ metriqueClasses.size();
        }
//        writer.println("Médiane NCLOC: "+mediane[0]);
//        writer.println("Médiane DCP: "+mediane[1]);
//        writer.println("Médiane NOCom: "+mediane[2]);
//        writer.println("Médiane WMC: "+mediane[3]);

        writer.println("Médiane NCLOC: "+mediane[0]);
        writer.println("Médiane DCP: "+mediane[1]);
        writer.println("Médiane NOCom: "+mediane[2]);
        writer.println("Médiane WMC: "+mediane[3]);
    }
}
