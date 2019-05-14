import java.util.Arrays;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.HashSet;

public class GaleShapley {
    private HashMap<Integer,BlackAnt> blackAnts;
    private int size;
    private HashMap<Integer,RedAnt> redAnts;
    float [][] distances;
    private int antsNumber;
    private HashMap<Integer, ArrayList<Integer> > redPreferences;
    private HashMap<Integer , ArrayList<Integer>> blackPreferences;
    private ArrayList<Integer> antsRank;
    private HashMap<Integer,Integer> proposals;
    private HashSet<Integer> proposalUnion;
    private HashMap<Integer,Integer> chousedFrom; // hashset for black ants, it used to search with low cost when is choosed the same black ant

    public GaleShapley( HashMap<Integer, RedAnt> redAnts,HashMap<Integer, BlackAnt> blackAnts) {
        this.blackAnts = blackAnts;
        this.redAnts = redAnts;
        size  = blackAnts.size() + redAnts.size() + 1; // for making simplier the distaces table
        antsNumber = size-1;
        this.distances = new float[size][size];
        //redPreferenes = new HashMap<Integer,new Integer([size/2])>();
        this.redPreferences = new HashMap<Integer , ArrayList<Integer>>();
        this.blackPreferences = new HashMap<Integer , ArrayList<Integer>>();
        this.proposals = new HashMap<>();
        this.proposalUnion = new HashSet<>();
        this.chousedFrom = new HashMap<>();
    }

    public void executeGaleShapley(){
        findDistances();
        // find preferences for odd numbers
        createPreferences();
        createProposalTable();
    }


    private void createPreferences(){
        //create preferences for red ants
        for(int i=1 ; i < size ; i +=2 ){ //size = size-1
            this.antsRank = new ArrayList<>();
            ArrayList<Float> rankAssist = new ArrayList<>(); // it contains the corresponidg distances
            for(int j=2 ; j < size ; j+=2 ){
                System.out.println("twra blepoyme kserw gw asxeta pragmata " + distances[i][j]);
                antsRank.add(j);
                rankAssist.add(distances[i][j]);
            }
            //quick sort the antsRank array and put the antsRank in the hashmap
            Float[] sort = rankAssist.toArray(new Float[rankAssist.size()]); // convert to array in order to sorting i as an array
            quickSort(sort,0,rankAssist.size()-1);
            //System.out.println("gia na doyme " + sort[14]);
            rankAssist = new ArrayList<Float>(Arrays.asList(sort));
            redPreferences.put(i,antsRank);
            System.out.println("to redpreferenves exei megethos " + redPreferences.size() );
            for(int k=0 ; k < redPreferences.get(i).size() ; k ++){
               // System.out.println("Stoixeio me protereotita  " + (k+1) +" : " + + redPreferences.get(i).get(k));
            }
        }
        //create preferences for black ants

        for(int i=2 ; i < size ; i +=2 ){ //size = size-1
            this.antsRank = new ArrayList<>();
            ArrayList<Float> rankAssist = new ArrayList<>(); // it contains the corresponidg distances
            for(int j=1 ; j < size ; j+=2 ){
                System.out.println("twra blepoyme kserw gw asxeta pragmata " +distances[i][j]);
                antsRank.add(j);
                rankAssist.add(distances[i][j]);
            }
            //quick sort the antsRank array and put the antsRank in the hashmap
            Float[] sort = rankAssist.toArray(new Float[rankAssist.size()]); // convert to array in order to sorting i as an array
            quickSort(sort,0,rankAssist.size()-1);
            //System.out.println("gia na doyme " + sort[14]);
            rankAssist = new ArrayList<Float>(Arrays.asList(sort));
            blackPreferences.put(i,antsRank);
            System.out.println("to redpreferenves exei megethos " + blackPreferences.size());
            for(int k=0 ; k < blackPreferences.get(i).size() ; k ++){
               // System.out.println("Stoixeio me protereotita  " + (k+1) +" : " + + blackPreferences.get(i).get(k));
            }
        }

    }

    private void createProposalTable(){
        //Initialize HashMap
        for(int i  = 1 ; i < size ; i+=2){
            proposals.put(i,redPreferences.get(i).get(0));
            proposalUnion.add(redPreferences.get(i).get(0));
            System.out.println("initiliazie");

            if(chousedFrom.containsKey(redPreferences.get(i).get(0))){// if this black ant has alread choosed from other red ants, change the proposal hashmao
                System.out.println("exei epilegei hdh to " + redPreferences.get(i).get(0) + " apo to " + chousedFrom.get(redPreferences.get(i).get(0)));
                //here we have to iterate the blackPreference ArrayList to find which red ants is more desirable
                if(blackPreferences.get(redPreferences.get(i).get(0)).indexOf(i) > blackPreferences.get(redPreferences.get(i).get(0)).indexOf(chousedFrom.get(redPreferences.get(i).get(0)))){// redPreferevnes.get(i) the same with the other red ant
                    System.out.println(blackPreferences.get(redPreferences.get(i).get(0)).indexOf(i) + " to murmigki " + i + " kai " + blackPreferences.get(redPreferences.get(i).get(0)).indexOf(chousedFrom.get(redPreferences.get(i).get(0))) + " to murmigi " + chousedFrom.get(redPreferences.get(i).get(0)) );
                    System.out.println("Zhmia");
                    redPreferences.get(i).remove(0);//remove the current preference
                    proposals.put(i,redPreferences.get(i).get(0));// update the proposal table
                    proposalUnion.add(redPreferences.get(i).get(0));
                    redPreferences.get(i).remove(0);//remove also this preferenve because it's already used

                }else{ // current ant is more desirable from the previοus

                    redPreferences.get(chousedFrom.get(redPreferences.get(i).get(0))).remove(0);//remove the current preference
                    proposals.put(chousedFrom.get(redPreferences.get(i).get(0)),redPreferences.get(chousedFrom.get(redPreferences.get(i).get(0))).get(0));
                    proposalUnion.add(redPreferences.get(chousedFrom.get(redPreferences.get(i).get(0))).get(0));
                    redPreferences.get(chousedFrom.get(redPreferences.get(i).get(0))).remove(0);//remove also this preferenve because it's already used
                }
                //System.out.println("timh kserw gw " + redPreferences.get(i));
                chousedFrom.put(redPreferences.get(i).get(0), i);
            }else{
                chousedFrom.put(redPreferences.get(i).get(0), i);
                redPreferences.get(i).remove(0);//so, at every loop, we chouse the index:0
            }


        }

        for(int i  = 1 ; i < size ; i+=2){
            System.out.println(i + " " + proposals.get(i));
        }
        //Continue

        System.out.println("to proposalUnion einai iso me " + proposalUnion.size());
        while(proposalUnion.size() < (size-1)/2){
            System.out.println("to proposalUnion einai iso me " + proposalUnion.size());
            for(int i  = 1 ; i < size ; i+=2 ){

            }
        }


    }
    private void findDistances(){
        int xi,yi;
        int xj,yj;
        for (int i = 1; i < size ; i++) { //size = size-1
            if(i % 2 != 0){// odd number
                xi = redAnts.get(i).getX();
                yi = redAnts.get(i).getY();
                System.out.println("syntetagmenes " + xi + yi);
            }else{ // even number
                System.out.println("brike ayto to apotelesma kai mpaine gia artio"+ i % 2);
                xi = blackAnts.get(i).getX();
                yi = blackAnts.get(i).getY();
            }
            for (int j = 1; j < size ; j++) {
                if(j % 2 != 0){// odd number
                    System.out.println("petaei null pointer gia j= " + j);
                    xj = redAnts.get(j).getX();
                    yj = redAnts.get(j).getY();
                }else{ // even number
                    xj = blackAnts.get(j).getX();
                    yj = blackAnts.get(j).getY();
                }
                distances[i][j] = euclideanDistance(xi,yi,xj,yj);
                System.out.println("Mpike apostasi " + distances[i][j]);
            }
        }

        for(int i = 1 ; i < size ; i++){
            for(int j = 1 ; j < size ; j++){
                System.out.println(distances[i][j]);
            }
        }

        System.out.println("size =" + size);
    }
    private float euclideanDistance(int x1,int y1,int x2,int y2){
        float xD = Math.abs(x1-x2);
        xD = (float) Math.pow(xD,2);
        float yD = Math.abs(y1-y2);
        yD = (float) Math.pow(yD,2);
        return (float) Math.sqrt(xD + yD);
    }

    //sorting code
    //parallel the two arraylists antsRank and rankAssist
    public void quickSort(Float arr[], int begin, int end) {
        if (begin < end) {
            int partitionIndex = partition(arr, begin, end);
            quickSort(arr, begin, partitionIndex-1);
            quickSort(arr, partitionIndex+1, end);
        }
    }

    private int partition(Float arr[], int begin, int end) {
        float pivot = arr[end];
        int i = (begin-1);

        for (int j = begin; j < end; j++) {
            if (arr[j] <= pivot) {
                i++;

                float swapTemp = arr[i];
                arr[i] = arr[j];
                arr[j] = swapTemp;
                //sort ants rank
                int tempPair =  this.antsRank.get(i);
                antsRank.set(i,antsRank.get(j));
                antsRank.set(j,tempPair);
            }
        }

        float swapTemp = arr[i+1];
        arr[i+1] = arr[end];
        arr[end] = swapTemp;

        int tempPair =  this.antsRank.get(i+1);
        antsRank.set(i+1,antsRank.get(end));
        antsRank.set(end,tempPair);

        return i+1;
    }
    //end of sorting code
}

