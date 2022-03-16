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

        float[] quartSup = {0, 0, 0, 0};
        int[] tailleSup = {0, 0, 0, 0};
        float[] quartInf = {0, 0, 0, 0};
        int[] tailleInf = {0, 0, 0, 0};
        float[] longueur = new float[4],
                limSup = new float[4],
                limInf = new float[4];

        float valeurMetrique=0;

        for (Metrique metriqueClass : metriqueClasses) {

            for(int i = 0; i < mediane.length; i++)
            {
                if(i==0) {valeurMetrique=metriqueClass.NLOC;}
                if(i==1) {valeurMetrique=metriqueClass.DCP;}
                if(i==2) {valeurMetrique=metriqueClass.NOCom;}
                if(i==3) {valeurMetrique=metriqueClass.WMC;}

                if(valeurMetrique>mediane[i]) {
                    quartSup[i] += valeurMetrique;
                    tailleSup[i]++;
                }
                if(valeurMetrique<mediane[i]) {
                    quartInf[i] += valeurMetrique;
                    tailleInf[i]++;
                }
            }
        }

        for(int i = 0; i < mediane.length; i++)
        {
            quartSup[i] = quartSup[i] / tailleSup[i];
            quartInf[i] = quartInf[i] / tailleInf[i];
            longueur[i] = quartSup[i] - quartInf[i];
            limSup[i] = (float) (quartSup[i] + 1.5*longueur[i]);
            limInf[i] = (float) (quartInf[i] - 1.5*longueur[i]);
        }

//        ArrayList<Metrique> nclocExt = new ArrayList<Metrique>();
//        ArrayList<Metrique> dcpExt = new ArrayList<Metrique>();
//        ArrayList<Metrique> nocomExt = new ArrayList<Metrique>();
//        ArrayList<Metrique> wmcExt = new ArrayList<Metrique>();
        ArrayList<Float>[] pointsExt = new ArrayList[4];
        for(int i = 0; i < 4; i++)
        {
            pointsExt[i] = new ArrayList<Float>();
        }

        for (Metrique metriqueClass : metriqueClasses) {

            for(int i = 0; i < 4; i++)
            {
                if(i==0) {valeurMetrique=metriqueClass.NLOC;}
                if(i==1) {valeurMetrique=metriqueClass.DCP;}
                if(i==2) {valeurMetrique=metriqueClass.NOCom;}
                if(i==3) {valeurMetrique=metriqueClass.WMC;}

                if(valeurMetrique>limSup[i] || valeurMetrique<limInf[i]) {
                    pointsExt[i].add(valeurMetrique);
                }
            }
        }

        String metriqueType = "";
        for(int i = 0; i < 4; i++)
        {
            if(i==0) {metriqueType = "NCLOC";}
            if(i==1) {metriqueType = "DCP";}
            if(i==2) {metriqueType = "NOCom";}
            if(i==3) {metriqueType = "WMC";}

            writer.println("Médiane de "+metriqueType+": "+mediane[i]);
            writer.println("Quartile supérieure de "+metriqueType+": "+quartSup[i]);
            writer.println("Quartile inférieure de "+metriqueType+": "+quartInf[i]);
            writer.println("Longueur de "+metriqueType+": "+longueur[i]);
            writer.println("Limite supérieure de "+metriqueType+": "+limSup[i]);
            writer.println("Limite inférieure de "+metriqueType+": "+limInf[i]);
            writer.print("Points extrêmes de "+metriqueType+": ");
            for (Float pointExt : pointsExt[i]) {
                writer.print(pointExt+", ");
            }
            writer.println();
            writer.println();
        }

    }
}
