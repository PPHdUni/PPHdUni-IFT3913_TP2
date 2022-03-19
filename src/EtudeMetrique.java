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


//        File spearman = new File("tp2#4.csv");
//        reader = new Scanner(spearman);
//        writer = new PrintWriter("tp2#4.txt");
//        spearman(writer, reader);
//        writer.close();
//        reader.close();

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

    private static void spearman(PrintWriter writer, Scanner reader) {

        String lineRead;
        String[] lineDiv;
        int i = 0,
                n = metriqueClasses.size();
        double[] d = new double[3],
                coefSpearman = new double[3],
                coefSpearman1 = {0,0,0},
                coefSpearman2 = new double[3];
        float[][] rangMetrique = new float[4][n];

        reader.nextLine();
        while(reader.hasNextLine()) {
            lineRead = reader.nextLine();
            lineDiv = lineRead.split(";");
            for (int j=0; j<4;j++){
                rangMetrique[j][i] = Float.parseFloat(lineDiv[5+j]);
            }
            i++;
        }

        for (i=0; i<n;i++){
            for (int j=0; j<3;j++){
                d[j] = rangMetrique[3][i] - rangMetrique[j][i];
                coefSpearman1[j] += Math.pow(d[j], 2);
            }
        }

        for (int j=0; j<3;j++){
            coefSpearman2[j] = 6*coefSpearman1[j]/(n*(Math.pow(n, 2)-1));
            coefSpearman[j] = 1 - coefSpearman2[j];
            writer.println(coefSpearman1[j]);
        }

        writer.println("Le coefficient de Spearman entre WMC et NCLOC est : "+coefSpearman[0]);
        writer.println();
        writer.println("Le coefficient de Spearman entre WMC et DCP est : "+coefSpearman[1]);
        writer.println();
        writer.println("Le coefficient de Spearman entre WMC et NOCom est : "+coefSpearman[2]);
        writer.println();

    }
}
