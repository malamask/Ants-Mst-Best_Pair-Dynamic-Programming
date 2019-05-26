import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
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

    public void executeGaleShapley() throws FileNotFoundException, UnsupportedEncodingException {
        findDistances();
        // find preferences for odd numbers
        createPreferences();
        createProposalTable();
        saveResult();
    }


    private void createPreferences(){
        //create preferences for red ants
        for(int i=1 ; i < size ; i +=2 ){ //size = size-1
            this.antsRank = new ArrayList<>();
            ArrayList<Float> rankAssist = new ArrayList<>(); // it contains the corresponidg distances
            for(int j=2 ; j < size ; j+=2 ){
                antsRank.add(j);
                rankAssist.add(distances[i][j]);
            }
            //quick sort the antsRank array and put the antsRank in the hashmap
            Float[] sort = rankAssist.toArray(new Float[rankAssist.size()]); // convert to array in order to sorting i as an array
            quickSort(sort,0,rankAssist.size()-1);
            //System.out.println("gia na doyme " + sort[14]);
            rankAssist = new ArrayList<Float>(Arrays.asList(sort));
            redPreferences.put(i,antsRank);
            for(int k=0 ; k < redPreferences.get(i).size() ; k ++){
            }
        }
        //create preferences for black ants

        for(int i=2 ; i < size ; i +=2 ){ //size = size-1
            this.antsRank = new ArrayList<>();
            ArrayList<Float> rankAssist = new ArrayList<>(); // it contains the corresponidg distances
            for(int j=1 ; j < size ; j+=2 ){
                antsRank.add(j);
                rankAssist.add(distances[i][j]);
            }
            //quick sort the antsRank array and put the antsRank in the hashmap
            Float[] sort = rankAssist.toArray(new Float[rankAssist.size()]); // convert to array in order to sorting i as an array
            quickSort(sort,0,rankAssist.size()-1);
            //System.out.println("gia na doyme " + sort[14]);
            rankAssist = new ArrayList<Float>(Arrays.asList(sort));
            blackPreferences.put(i,antsRank);
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

            if(chousedFrom.containsKey(redPreferences.get(i).get(0))){// if this black ant has alread choosed from other red ants, change the proposal hashmao
                //here we have to iterate the blackPreference ArrayList to find which red ants is more desirable
                if(blackPreferences.get(redPreferences.get(i).get(0)).indexOf(i) > blackPreferences.get(redPreferences.get(i).get(0)).indexOf(chousedFrom.get(redPreferences.get(i).get(0)))){// redPreferevnes.get(i) the same with the other red ant
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


        //Continue

        while(proposalUnion.size() < (size-1)/2){
            proposalUnion = new HashSet<>();// re - create the proposal union
            for(int i  = 1 ; i < size ; i+=2 ){
                if(chousedFrom.containsKey(redPreferences.get(i).get(0))){// if this black ant has alread choosed from other red ants, change the proposal hashmao
                    //here we have to iterate the blackPreference ArrayList to find which red ants is more desirable
                    if(blackPreferences.get(redPreferences.get(i).get(0)).indexOf(i) > blackPreferences.get(redPreferences.get(i).get(0)).indexOf(chousedFrom.get(redPreferences.get(i).get(0)))){// redPreferevnes.get(i) the same with the other red ant
                        //System.out.println("Zhmia");
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
                    proposalUnion.add(proposals.get(i));
                   // chousedFrom.put(redPreferences.get(i).get(0), i);
                    //redPreferences.get(i).remove(0);//so, at every loop, we chouse the index:0
                }
            }
        }


    }


    private void saveResult() throws FileNotFoundException, UnsupportedEncodingException {
        File f = new File(System.getProperty("java.class.path"));
        File dir = f.getAbsoluteFile().getParentFile();
        String path = dir.toString();
        String outputfile = path + "\\" + "galeShapley.txt";
        PrintWriter writer = new PrintWriter(outputfile, "UTF-8");
        //PrintWriter writer = new PrintWriter("GaleSapley.txt", "UTF-8");
        for(int i= 1 ; i < size; i+=2){
            writer.println(i + " " +proposals.get(i));
        }
        writer.close();
    }
    private void findDistances(){
        float xi,yi;
        float xj,yj;
        for (int i = 1; i < size ; i++) { //size = size-1
            if(i % 2 != 0){// odd number
                xi = redAnts.get(i).getX();
                yi = redAnts.get(i).getY();
            }else{ // even number
                xi = blackAnts.get(i).getX();
                yi = blackAnts.get(i).getY();
            }
            for (int j = 1; j < size ; j++) {
                if(j % 2 != 0){// odd number
                    xj = redAnts.get(j).getX();
                    yj = redAnts.get(j).getY();
                }else{ // even number
                    xj = blackAnts.get(j).getX();
                    yj = blackAnts.get(j).getY();
                }
                distances[i][j] = euclideanDistance(xi,yi,xj,yj);
            }
        }


    }
    private float euclideanDistance(float x1,float y1,float x2,float y2){
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


    public HashMap<Integer, Integer> getProposals() {
        return proposals;
    }

}

