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
        System.out.println(" Exomye proposal size " + proposals.size());
        createTableBottomUp();
    }

    public void createTableBottomUp(){
        System.out.println("Ntemek ksekinaei h lysh");
        for(int i=1 ; i < size ; i+=2){
            //create the table for every pair
            System.out.println(i);
            System.out.println("lets see"+blackAnts.get(proposals.get(i)).getW1());
            System.out.println("lets see"+blackAnts.get(proposals.get(i)).getW2());
            System.out.println("lets see"+blackAnts.get(proposals.get(i)).getW3());
            System.out.println("lets see"+blackAnts.get(proposals.get(i)).getW4());
            System.out.println("lets see"+blackAnts.get(proposals.get(i)).getW5());
        }
    }






}
