
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
        file.createHashMaps();
        //save
        redAnts = file.getRedAnts();
        blackAnts = file.getBlackAnts();
        // Create objects for the three algorythms - Kryskal,GaleShapley,Dynamic Programming

        Kruskal k = new Kruskal(redAnts,blackAnts);
        k.executeKruskal();

       // GaleShapley g = new GaleShapley(redAnts,blackAnts);
       // g.executeGaleShapley();

        //DynamicProgramming d = new DynamicProgramming(redAnts,blackAnts,g.getProposals());
        //d.executeAlgorythm();


}

}
