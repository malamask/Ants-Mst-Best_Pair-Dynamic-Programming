import jdk.swing.interop.SwingInterOpUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;

public class DynamicProgramming {
    private HashMap<Integer,RedAnt> redAnts;
    private HashMap<Integer,BlackAnt> blackAnts;
    private HashMap<Integer,Integer> proposals;
    private int size;
    private ArrayList<Integer> weights;
    private ArrayList<Integer> rankOfWeights; // keep the sort of the weights to print them in the right
    private ArrayList<Integer> useOfWeights; // save how much times a weight is used
    PrintWriter writer;




    public DynamicProgramming(HashMap<Integer, RedAnt> redAnts, HashMap<Integer, BlackAnt> blackAnts, HashMap<Integer, Integer> proposals) throws FileNotFoundException, UnsupportedEncodingException {
        this.redAnts = redAnts;
        this.blackAnts = blackAnts;
       // proposals.put(1,2);
       // proposals.put(3,4);
        //proposals.put(5,6);
        this.proposals = proposals;
        size = blackAnts.size() + redAnts.size();
        File f = new File(System.getProperty("java.class.path"));
        File dir = f.getAbsoluteFile().getParentFile();
        String path = dir.toString();
        String outputfile = path + "\\" + "DynamicProgramming.txt";
        writer = new PrintWriter(outputfile, "UTF-8");
        //writer = new PrintWriter("DynamicProgramming.txt", "UTF-8");
    }

    public void executeAlgorythm(){

        createTableBottomUp();
    }

    public void createTableBottomUp(){
        int weightsNumber = 6; // +1 for the row with MAX.INTEGER
        for(int ant=1 ; ant < size  ; ant+=2){
            //create the table for every pair
            int capacity = redAnts.get(ant).getCapacity();

            this.weights = new ArrayList<>();
            this.rankOfWeights = new ArrayList<>(); // for print the results sorted after the loop
            this.useOfWeights = new ArrayList<>();
            for(int i = 0 ; i < weightsNumber ; i++){
                useOfWeights.add(i,0);
            }
            weights.add(0);
            weights.add(blackAnts.get(proposals.get(ant)).getW1());
            weights.add(blackAnts.get(proposals.get(ant)).getW2());
            weights.add(blackAnts.get(proposals.get(ant)).getW3());
            weights.add(blackAnts.get(proposals.get(ant)).getW4());
            weights.add(blackAnts.get(proposals.get(ant)).getW5());
            rankOfWeights.add(0);
            rankOfWeights.add(1);
            rankOfWeights.add(2);
            rankOfWeights.add(3);
            rankOfWeights.add(4);
            rankOfWeights.add(5);
            Integer[] sort = weights.toArray(new Integer[weights.size()]); // convert to array in order to sorting i as an array
            quickSort(sort,0,weights.size()-1);
            weights = new ArrayList<>(Arrays.asList(sort));
            //System.out.println("Capacity " + capacity);
            int [][] table = new int[weightsNumber][capacity+1]; //6, because we have five weights
            //implement two start conditions, [i,0] = 0 and  [ 0, j ] = ∞ ;
            for(int i = 0 ; i < weightsNumber ; i++){
                table[i][0] = 0;
            }
            for(int j = 1 ; j <= capacity ; j++){
                //table[0][j] = Integer.MAX_VALUE;// it "replaces" the infinity value
                table[0][j] = 15;
            }

            //initialize two values
            int x;
            int y;
            //here this for - loop fills the table, we use the relation [i,j] = min{[i-1,j], 1 + [i,j - Vi]}
            for(int i  = 1 ; i < weightsNumber ; i++){
                for(int j = 1 ; j <= capacity ; j++){
                    //calculate two values
                    x = table[i - 1][j];
                    if(j - weights.get(i) < 0){ // index is out of bounds, so the value is infinity
                        y = Integer.MAX_VALUE;
                    }else{
                        y = 1 + table[i][j - weights.get(i)];
                    }
                    if( x < y) {
                        table[i][j] = x;
                    }else{
                        table[i][j] = y;
                    }
                }

            }


            // Create combination for every pair, use table[][]
            if(table[weightsNumber-1][capacity] == 15){
            }else{
                int i = weightsNumber-1;
                int j = capacity ;
                while(j > 0){


                if(table[i-1][j] == table[i][j]  ) {
                    i = i - 1;
                }else if (table[i][j] == table[i][j - weights.get(i)] + 1){ // table[i][j] == 1 + table [i,j - weights(i)
                    j = j - weights.get(i);
                    //useOfWeights.get(i)++;
                    int value = useOfWeights.get(i); // get value
                    value = value + 1; // increment value
                    useOfWeights.set(i, value);
                }else{
                }
                }
                for(int klein = 0 ; klein < weightsNumber ; klein++){
                }
                saveResult(rankOfWeights,useOfWeights,ant);
            }
        }
        writer.close();
    }

    private void saveResult(ArrayList<Integer> rankOfWeights, ArrayList<Integer> useOfWeights,int ant){
        HashMap<Integer,Integer> rowToPrint = new HashMap<>();
        for(int i= 1 ; i < 6 ; i++ ){
            rowToPrint.put(rankOfWeights.get(i), useOfWeights.get(i));
        }
        writer.println(ant + "  " + proposals.get(ant) + "  " + rowToPrint.get(1) + " " + rowToPrint.get(2)+ " " + rowToPrint.get(3)+ " " + rowToPrint.get(4)+ " " + rowToPrint.get(5));
    }

    public void quickSort(Integer arr[], int begin, int end) {
        if (begin < end) {
            int partitionIndex = partition(arr, begin, end);

            quickSort(arr, begin, partitionIndex-1);
            quickSort(arr, partitionIndex+1, end);
        }
    }

    private int partition(Integer arr[], int begin, int end) {
        int pivot = arr[end];
        int i = (begin-1);

        for (int j = begin; j < end; j++) {
            if (arr[j] <= pivot) {
                i++;

                int swapTemp = arr[i];
                arr[i] = arr[j];
                arr[j] = swapTemp;

                int tempPair =  this.rankOfWeights.get(i);
                rankOfWeights.set(i,rankOfWeights.get(j));
                rankOfWeights.set(j,tempPair);
            }
        }

        int swapTemp = arr[i+1];
        arr[i+1] = arr[end];
        arr[end] = swapTemp;

        int tempPair =  this.rankOfWeights.get(i+1);
        rankOfWeights.set(i+1,rankOfWeights.get(end));
        rankOfWeights.set(end,tempPair);

        return i+1;
    }

    private boolean seedsCanCombined(ArrayList<Integer> weights,int capacity){
        Boolean result=true;
        for(int i = 0 ; i < weights.size() ; i++){
            //if()
        }
        return result;
    }


}
