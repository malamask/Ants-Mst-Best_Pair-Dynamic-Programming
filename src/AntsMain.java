
import java.io.*;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.HashMap;
import java.util.Iterator;
public class AntsMain {

    public static void main(String[] args) throws Exception{
        //variables for ants
        int j = 1; // ant colour - id
        int pos; // position for hashmap
        String number ;
        String st; // current string from .txt file
        Hashtable<Integer,Integer> redAnt = new Hashtable<Integer, Integer>();
        Hashtable<Integer,Integer> blackAnt = new Hashtable<Integer, Integer>();
        HashMap<Integer,RedAnt> redAnts = new HashMap<Integer,RedAnt>();
        HashMap<Integer,BlackAnt> blackAnts = new HashMap<Integer,BlackAnt>();
        //variables for files
        File file = new File("ants.txt");
        BufferedReader br = new BufferedReader(new FileReader(file));
        //collect red and black ants
        while ((st = br.readLine()) != null){
            System.out.println(st);
            number = "";
            if(j % 2 != 0){ // red ants
                System.out.println("Peritos");
                pos=1;
                // String seperator
                for (int i = 0; i < st.length(); i++) {
                    if (st.charAt(i) == ' ') {
                        System.out.println("Number = " + number);
                        redAnt.put(pos,Integer.parseInt(number));
                        number = "";
                        pos++;
                    }else{
                        number = number + String.valueOf(st.charAt(i));

                    }
                }
                System.out.println("Number = " + number);
                redAnt.put(pos,Integer.parseInt(number));
                for( int i = 1 ; i <= redAnt.size() ; i++){
                    System.out.println(" O arithmos einai o " + redAnt.get(i));
                }
                RedAnt ant = new RedAnt(redAnt.get(1),redAnt.get(2),redAnt.get(3),redAnt.get(4));
                redAnts.put(j,ant);
                System.out.println("To Redant einai iso me " + redAnts.size());
                // capacity of red ant
                number = "";
            }else{
                System.out.println("Artios"); // black ants
                //String seperator
                pos=1;
                // String seperator
                for (int i = 0; i < st.length(); i++) {
                    if (st.charAt(i) == ' ') {
                        System.out.println("Number = " + number);
                        blackAnt.put(pos,Integer.parseInt(number));
                        number = "";
                        pos++;
                    }else{
                        number = number + String.valueOf(st.charAt(i));

                    }
                }
                System.out.println("Number = " + number);
                blackAnt.put(pos,Integer.parseInt(number));
                for( int i = 1 ; i <= blackAnt.size() ; i++){
                    System.out.println(" O arithmos einai o " + blackAnt.get(i));
                }
                System.out.println("size = " + blackAnt.size());
                BlackAnt ant = new BlackAnt(blackAnt.get(1),blackAnt.get(2),blackAnt.get(3),blackAnt.get(4),blackAnt.get(5),blackAnt.get(6),blackAnt.get(7),blackAnt.get(8));
                blackAnts.put(j,ant);
                System.out.println("To Blackant einai iso me " + blackAnts.size());
                number = "";


            }
            j++;
        }
        // question A, Kruskal Algorithm

        Iterator it = redAnts.entrySet().iterator();
        while (it.hasNext()) {
            HashMap.Entry pair = (HashMap.Entry)it.next();
            RedAnt temp = (RedAnt) pair.getValue();
            System.out.println(pair.getKey() + " = " + temp.getCapacity() );
           // it.remove(); // avoids a ConcurrentModificationException
        }
        System.out.println("Kai tvra to black ant");
         it = blackAnts.entrySet().iterator();
        while (it.hasNext()) {
            HashMap.Entry pair = (HashMap.Entry)it.next();
            BlackAnt temp = (BlackAnt) pair.getValue();
            System.out.println(pair.getKey() + " = " + temp.getW1() );
            //it.remove(); // avoids a ConcurrentModificationException
        }
        //System.out.println("redants" + redAnts.s);
       // Kruskal k = new Kruskal(redAnts,blackAnts);
        //k.executeKruskal();
        GaleShapley g = new GaleShapley(redAnts,blackAnts);
        g.executeGaleShapley();


}

}
