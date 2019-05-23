
import java.io.*;
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
        FileManager file = new FileManager("ants.txt");
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
