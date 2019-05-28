
import java.io.*;
import java.net.URL;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.HashMap;
import java.util.Iterator;
public class AntsMain {

    public static void main(String[] args) throws Exception{
        //initialize the two hashmaps which contains the ants
        HashMap<Integer,RedAnt> redAnts;
        HashMap<Integer,BlackAnt> blackAnts;
        //initialize the filemanager
        //default file ants.txt
        String inputFile = "ants.txt";
         try {
             inputFile = args[0];
        }
        catch (ArrayIndexOutOfBoundsException e){
            System.out.println("ArrayIndexOutOfBoundsException caught, just use ants.txt file");
        }
        finally {

        }
        //find where is the .jar file
        File f = new File(System.getProperty("java.class.path"));
        File dir = f.getAbsoluteFile().getParentFile();
        String path = dir.toString();
        inputFile = path + "\\"  + inputFile;
        FileManager file = new FileManager(inputFile);
        file.createHashMaps();// two hashmaps for red and black ants

        //save
        redAnts = file.getRedAnts();
        blackAnts = file.getBlackAnts();
        // Create objects for the three algorythms
        //Kruskal
        Kruskal k = new Kruskal(redAnts,blackAnts);
        k.executeKruskal();
       // Gale Shapley
        GaleShapley g = new GaleShapley(redAnts,blackAnts);
        g.executeGaleShapley();
        //Dynamic Programming
        DynamicProgramming d = new DynamicProgramming(redAnts,blackAnts,g.getProposals());
        d.executeAlgorythm();



}

}
