import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import  java.io.PrintWriter;
import java.io.*;

public class Kruskal {
    private HashMap<Integer,RedAnt> redAnts;
    private HashMap<Integer,BlackAnt> blackAnts;
    private int size;
    private ArrayList<Float> sortedDistances;
    private ArrayList<Integer> firstPair; // parallel arraylist with sortedDistances, it contains the first member from the pair
    private ArrayList<Integer> secondPair; // parallel arraylist with sortedDistances, it contains the second from the pair
    //private HashSet<Float> finalDistances; // final distances from the minimum spanning tree
    private HashSet<Integer> unionAnts; // union of ants
    HashMap<Integer,HashSet<Integer>> unions; // list of unions
    HashMap<Integer,Integer> nodePosition;//representative
    float treeWeight;//mst total weight
    int mstConnections [][];


    float [][] distances; // euclidean distacnes
    public Kruskal(HashMap<Integer, RedAnt> redAnts, HashMap<Integer, BlackAnt> blackAnts) {
        this.redAnts = redAnts;
        this.blackAnts = blackAnts;
        size = redAnts.size() + blackAnts.size() + 1 ; // because the ids is between 1 - n , so, we'll have a row and a column with zeros
        this.distances = new float[size][size];
        this.sortedDistances = new ArrayList<>();
        this.firstPair = new ArrayList<>();
        this.secondPair = new ArrayList<>();
        this.treeWeight = 0;


    }

    public void executeKruskal() throws FileNotFoundException, UnsupportedEncodingException {

        findDistances();
        sortDistances();
        findMST();
        saveResult();

    }
    //this fuction find the distances between the ants
    private void findDistances(){ // O(n^2) complexity

        float xi,yi;
        float xj,yj;
        for (int i = 1; i < size ; i++) {
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

    //this fuctin calculate the distances between two ants
    private float euclideanDistance(float x1,float y1,float x2,float y2){
        float xD = Math.abs(x1-x2);
        xD = (float) Math.pow(xD,2);
        float yD = Math.abs(y1-y2);
        yD = (float) Math.pow(yD,2);
        return (float) Math.sqrt(xD + yD);
    }

    private void sortDistances(){

        for(int i=1; i < size ; i ++ ){
            for(int j = i+1; j < size ; j++){
                sortedDistances.add(distances[i][j]);
                firstPair.add(i);
                secondPair.add(j);
            }
        }
       // simpletEST();
//        System.out.println(sortedDistances.get(14));

        Float[] sort = sortedDistances.toArray(new Float[sortedDistances.size()]); // convert to array in order to sorting i as an array
        quickSort(sort,0,sortedDistances.size()-1);
        //System.out.println("gia na doyme " + sort[14]);
        sortedDistances = new ArrayList<Float>(Arrays.asList(sort));
//        System.out.println("twra na doyme " + sortedDistances.get(5));

    }

    private void findMST(){
        int totalConnections = 0; // must be <= size -1
        int row = 0;
        mstConnections = new int [size-2][2]; // -2 because we add 1 and the constructor of the class for other operations
        this.unions = new HashMap<>();
        this.nodePosition = new HashMap<>(); // it has the position from any node in the unions HashSet
        //initialize unions HashSet
        for(int i = 1 ; i  < size ; i ++){ // size = size - 1
            unions.put(i,new HashSet<>());
        }

        //whole the procedure is based on one loop
        for(int i = 0 ; i < sortedDistances.size() ; i++ ){ // condition for end the
            if(!isCirclePath(firstPair.get(i),secondPair.get(i))){ // it can be added in the union
                totalConnections++;
                mstConnections[row][0] = firstPair.get(i);
                mstConnections[row][1] = secondPair.get(i);
                row++;
                treeWeight = treeWeight + sortedDistances.get(i);
                if(nodePosition.get(secondPair.get(i)) == null  ) {//first pair no register or start of the union
                    if(nodePosition.get(firstPair.get(i)) == null){
                        nodePosition.put(firstPair.get(i), firstPair.get(i));
                        nodePosition.put(secondPair.get(i), firstPair.get(i));
                    }else{
                        //nodePosition.put(firstPair.get(i), firstPair.get(i));
                        nodePosition.put(secondPair.get(i), nodePosition.get(firstPair.get(i)));
                    }
                    unions.get(nodePosition.get(firstPair.get(i))).add(firstPair.get(i));
                    unions.get(nodePosition.get(firstPair.get(i))).add(secondPair.get(i));
                }else if((nodePosition.get(secondPair.get(i)) == null )){
                    if(nodePosition.get(firstPair.get(i)) == null){
                        nodePosition.put(secondPair.get(i), secondPair.get(i));
                        nodePosition.put(firstPair.get(i), secondPair.get(i));
                    }else{
                        //nodePosition.put(firstPair.get(i), firstPair.get(i));
                        nodePosition.put(firstPair.get(i), nodePosition.get(secondPair.get(i)));
                    }
                    unions.get(nodePosition.get(secondPair.get(i))).add(secondPair.get(i));
                    unions.get(nodePosition.get(secondPair.get(i))).add(firstPair.get(i));
                }else if (nodePosition.get(secondPair.get(i)) != null && nodePosition.get(firstPair.get(i)) != null ) { // case that both of them are register, so we connect the two+ unions
                    //put the number in the first set, so we have to update the nodePosition hashmap
                    int setForDelete = nodePosition.get(secondPair.get(i));
                    for( int j = 0 ; j  < unions.get(nodePosition.get(secondPair.get(j))).size() ; j++){
                        unions.get(nodePosition.get(secondPair.get(j)));
                    }
                    for(int number : unions.get(nodePosition.get(secondPair.get(i)))){
                        unions.get(nodePosition.get(firstPair.get(i))).add(number);
                        nodePosition.put(number,nodePosition.get(firstPair.get(i)));
                    }
                    //System.out.println("σβήσιμο" + nodePosition.get(secondPair.get(i)));
                    unions.get(setForDelete).clear();
                }
            }else{ // this edge causes circle, skip number

            }
            if(totalConnections == size-2){
                i = sortedDistances.size();
            }
        }

    }

    //this fuction find if the current edge causes circle in the tree
    private boolean isCirclePath(int x,int y){ // checks if the link x - y causes a circle path, in order to approve it or not
            if(this.nodePosition.get(x)!=null && this.nodePosition.get(y)!= null){
                if(this.nodePosition.get(x) == this.nodePosition.get(y) ){
                    return true;
                }else{
                    return false;
                }
            }else{
                return false;
            }

    }

    //this fuction save the result at the .txt file
    private void saveResult() throws FileNotFoundException, UnsupportedEncodingException {
        File f = new File(System.getProperty("java.class.path"));
        File dir = f.getAbsoluteFile().getParentFile();
        String path = dir.toString();
        String outputfile = path + "\\" + "MstKruskal.txt";
        PrintWriter writer = new PrintWriter(outputfile, "UTF-8");
        //PrintWriter writer = new PrintWriter("mstKruskal.txt", "UTF-8");
        writer.println(treeWeight);
        for(int i= 0 ; i < mstConnections.length ; i++){
            writer.println(mstConnections[i][0] + "  " + mstConnections[i][1]);
        }
        writer.close();
    }

    //quick sort implementation
    private void quickSort(Float arr[], int begin, int end) {
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
                //swap pair ArrayLists
                //first pair
                int tempPair =  this.firstPair.get(i);
                firstPair.set(i,firstPair.get(j));
                firstPair.set(j,tempPair);
                //second pair
                tempPair =  this.secondPair.get(i);
                secondPair.set(i,secondPair.get(j));
                secondPair.set(j,tempPair);
            }
        }

        float swapTemp = arr[i+1];
        arr[i+1] = arr[end];
        arr[end] = swapTemp;
        //first pair
        int tempPair =  this.firstPair.get(i+1);
        firstPair.set(i+1,firstPair.get(end));
        firstPair.set(end,tempPair);
        //second pair
        tempPair =  this.secondPair.get(i+1);
        secondPair.set(i+1,secondPair.get(end));
        secondPair.set(end,tempPair);

        return i+1;
    }
    //end of sorting code

    //testing function
    public void simpletEST(){
        this.size = 11;
        firstPair = null;
        secondPair = null;
        sortedDistances = null;
        firstPair = new ArrayList<>();
        secondPair = new ArrayList<>();
        sortedDistances = new ArrayList<>();
        firstPair.add(0);
        secondPair.add(1);
        sortedDistances.add((float) 4);
        firstPair.add(0);
        secondPair.add(7);
        sortedDistances.add((float) 8);
        firstPair.add(1);
        secondPair.add(2);
        sortedDistances.add((float) 8);
        firstPair.add(1);
        secondPair.add(7);
        sortedDistances.add((float) 11);
        firstPair.add(2);
        secondPair.add(3);
        sortedDistances.add((float) 7);
        firstPair.add(2);
        secondPair.add(8);
        sortedDistances.add((float) 2);
        firstPair.add(2);
        secondPair.add(5);
        sortedDistances.add((float) 4);
        firstPair.add(3);
        secondPair.add(4);
        sortedDistances.add((float) 9);
        firstPair.add(3);
        secondPair.add(5);
        sortedDistances.add((float) 14);
        firstPair.add(4);
        secondPair.add(5);
        sortedDistances.add((float) 10);
        firstPair.add(5);
        secondPair.add(6);
        sortedDistances.add((float) 2);
        firstPair.add(6);
        secondPair.add(7);
        sortedDistances.add((float) 1);
        firstPair.add(6);
        secondPair.add(8);
        sortedDistances.add((float) 6);
        firstPair.add(7);
        secondPair.add(8);
        sortedDistances.add((float) 7);
    }

}
