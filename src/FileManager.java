import java.io.*;
import java.util.HashMap;
import java.util.Hashtable;

public class FileManager {
    String filename;
    HashMap<Integer,RedAnt> redAnts;
    HashMap<Integer,BlackAnt> blackAnts;

    public FileManager (String filename ){
        this.filename = filename;

    }

    public void createHashMaps() throws IOException {
        int j = 1; // ant colour - id
        int pos; // position for hashmap
        String number ;
        String st; // current string from .txt file
        Hashtable<Integer,Integer> redAnt = new Hashtable<Integer, Integer>();
        Hashtable<Integer,Integer> blackAnt = new Hashtable<Integer, Integer>();
        Hashtable<Integer,Float> redAntXy = new Hashtable<Integer, Float>();// in case of float coordinates
        Hashtable<Integer,Float> blackAntXy = new Hashtable<Integer, Float>();//in case of float coordinates
        this.redAnts = new HashMap<Integer,RedAnt>();
        this.blackAnts = new HashMap<Integer,BlackAnt>();
        //variables for files
        File file = new File(filename);
        BufferedReader br = new BufferedReader(new FileReader(file));
        //collect red and black ants
        while ((st = br.readLine()) != null){
            number = "";
            if(j % 2 != 0){ // red ants
                pos=1;
                // String seperator
                for (int i = 0; i < st.length(); i++) {
                    if (st.charAt(i) == ' ') {
                        if(number.contains(String.valueOf('.')) || (pos == 2 || pos == 3)){ // case of float coordinates
                            redAntXy.put(pos,Float.parseFloat(number));
                        }else{
                            redAnt.put(pos,Integer.parseInt(number));

                        }
                        number = "";
                        pos++;
                    }else{
                        number = number + String.valueOf(st.charAt(i));

                    }
                }
                redAnt.put(pos,Integer.parseInt(number));

                RedAnt ant = new RedAnt(redAnt.get(1),redAntXy.get(2),redAntXy.get(3),redAnt.get(4));
                redAnts.put(j,ant);
                // capacity of red ant
                number = "";
            }else{
                //System.out.println(st);
                //String seperator
                pos=1;
                // String seperator
                for (int i = 0; i < st.length(); i++) {
                    if (st.charAt(i) == ' ') {
                        if(number.contains(String.valueOf('.')) || (pos == 2 || pos == 3) ){
                            blackAntXy.put(pos,Float.parseFloat(number));
                        }else{
                            blackAnt.put(pos,Integer.parseInt(number));
                        }
                        number = "";
                        pos++;
                    }else{
                        number = number + String.valueOf(st.charAt(i));

                    }
                }
                blackAnt.put(pos,Integer.parseInt(number));
                for( int i = 1 ; i <= blackAnt.size() ; i++){
                    //System.out.println(" O arithmos einai o " + blackAnt.get(i));
                }
                BlackAnt ant = new BlackAnt(blackAnt.get(1),blackAntXy.get(2),blackAntXy.get(3),blackAnt.get(4),blackAnt.get(5),blackAnt.get(6),blackAnt.get(7),blackAnt.get(8));
                blackAnts.put(j,ant);
                number = "";


            }
            j++;
        }
    }

    public HashMap<Integer, RedAnt> getRedAnts() {
        return redAnts;
    }

    public HashMap<Integer, BlackAnt> getBlackAnts() {
        return blackAnts;
    }
}
