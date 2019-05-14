import java.util.HashMap;
import java.util.HashSet;

public class DynamicProgramming {
    private HashMap<Integer,RedAnt> redAnts;
    private HashMap<Integer,BlackAnt> blackAnts;
    private HashMap<Integer,Integer> proposals;
    private int size;



    public DynamicProgramming(HashMap<Integer, RedAnt> redAnts, HashMap<Integer, BlackAnt> blackAnts, HashMap<Integer, Integer> proposals) {
        this.redAnts = redAnts;
        this.blackAnts = blackAnts;
        this.proposals = proposals;
        size = blackAnts.size() + redAnts.size();
    }

    public void executeAlgorythm(){
        System.out.println("DYNAMIC PROGRAMMING AREA");
        System.out.println("Exomye redants " + redAnts.size());
        System.out.println("Exomye blackants " + blackAnts.size());
        System.out.println(" Exomye proposal size" + proposals.size());

    }




}
