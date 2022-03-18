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
        String lineRead,
                lineWrite;
        String[] classe;
        reader.nextLine();
        PrintWriter writer;

        writer = new PrintWriter("etude_metrique.csv");
        writer.println("NCLOC;DCP;NOCom;WMC;");

        while(reader.hasNextLine()) {
            lineRead = reader.nextLine();
            classe = lineRead.split(",");
            lineWrite = classe[1]+";"+classe[2].replace('.',',')+";"+classe[3]+";"+classe[4]+";";
            writer.println(lineWrite);
            metriqueClasses.add(new Metrique(classe[0],Integer.parseInt(classe[1]),
                    Float.parseFloat(classe[2]),Integer.parseInt(classe[3]),Integer.parseInt(classe[4])));
        }
        writer.close();
        reader.close();

        writer = new PrintWriter("tp2#2.txt");
        sondageDCPNOCom(writer);
        writer.close();

    }

    private static void sondageDCPNOCom(PrintWriter writer) {
        int tailleNOComInf=0,
                tailleNOComSup=0;
        float moyDCPNOComInf = 0,
                moyDCPNOComSup = 0;

        for (Metrique metriqueClass : metriqueClasses) {
            if (metriqueClass.NOCom<10) {
                moyDCPNOComInf+=metriqueClass.DCP;
                tailleNOComInf++;
            }
            if (metriqueClass.NOCom>10) {
                moyDCPNOComSup+=metriqueClass.DCP;
                tailleNOComSup++;
            }
        }
        moyDCPNOComInf = moyDCPNOComInf/tailleNOComInf;
        moyDCPNOComSup = moyDCPNOComSup/tailleNOComSup;

        writer.println("Nombre de classes ayant un NOCom inférieure à 10 : "+tailleNOComInf);
        writer.println("Moyenne de la DCP des classes ayant un NOCom inférieure à 10 : "+moyDCPNOComInf);
        writer.println();

        writer.println("Nombre de classes ayant un NOCom supérieure à 10 : "+tailleNOComSup);
        writer.println("Moyenne de la DCP des classes ayant un NOCom supérieure à 10 : "+moyDCPNOComSup);
        writer.println();

    }
}
