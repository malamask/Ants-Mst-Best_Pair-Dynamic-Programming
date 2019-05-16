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
    private ArrayList<Integer> rankOfWeights;




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
        for(int ant=1 ; ant < size - 3 ; ant+=2){
            //create the table for every pair
            int capacity = redAnts.get(ant).getCapacity();
            System.out.println(ant);
            System.out.println("lets see "+blackAnts.get(proposals.get(ant)).getW1());
            System.out.println("lets see "+blackAnts.get(proposals.get(ant)).getW2());
            System.out.println("lets see "+blackAnts.get(proposals.get(ant)).getW3());
            System.out.println("lets see "+blackAnts.get(proposals.get(ant)).getW4());
            System.out.println("lets see "+blackAnts.get(proposals.get(ant)).getW5());
            this.weights = new ArrayList<>();
            this.rankOfWeights = new ArrayList<>(); // for print the results sorted after the loop
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
           // System.out.println("After sorting " + weights.get(0) + " thesi " + rankOfWeights.get(0));
            System.out.println("Capacity " + capacity);
            System.out.println("After sorting " + weights.get(1)+ " thesi " + rankOfWeights.get(1));
            System.out.println("After sorting " + weights.get(2)+ " thesi " + rankOfWeights.get(2));
            System.out.println("After sorting " + weights.get(3)+ " thesi " + rankOfWeights.get(3));
            System.out.println("After sorting " + weights.get(4)+ " thesi " + rankOfWeights.get(4));
            System.out.println("After sorting " + weights.get(5)+ " thesi " + rankOfWeights.get(5));
            int [][] table = new int[6][capacity+1]; //6, because we have five weights
            //implement two start conditions, [i,0] = 0 and  [ 0, j ] = ∞ ;
            for(int i = 0 ; i < 6 ; i++){
                table[i][0] = 0;
            }
            for(int j = 1 ; j < capacity ; j++){
                //table[0][j] = Integer.MAX_VALUE;// it "replaces" the infinity value
                table[0][j] = 100;
            }
            for(int i  = 0 ; i < 6 ; i++){
                for(int j = 0 ; j < capacity ; j++){
                    System.out.print(table[i][j] + " ");
                }
                System.out.println("");
            }
            //initialize two values
            int x;
            int y;
            //here this for - loop fills the table, we use the relation [i,j] = min{[i-1,j], 1 + [i,j - Vi]}
            for(int i  = 1 ; i < 6 ; i++){
                for(int j = 1 ; j < capacity ; j++){
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
            for(int i  = 0 ; i < 6 ; i++){
                for(int j = 0 ; j < capacity ; j++){
                    System.out.print(table[i][j] + " ");
                }
                System.out.println("");
            }

        }
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




}
