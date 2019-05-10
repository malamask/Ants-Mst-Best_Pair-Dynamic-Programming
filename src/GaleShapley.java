import java.util.HashMap;

public class GaleShapley {
    private HashMap<Integer,BlackAnt> blackAnts;
    private int size;
    private HashMap<Integer,RedAnt> redAnts;
    float [][] distances;

    public GaleShapley( HashMap<Integer, RedAnt> redAnts,HashMap<Integer, BlackAnt> blackAnts) {
        this.blackAnts = blackAnts;
        this.redAnts = redAnts;
        size  = blackAnts.size() + redAnts.size();
        this.distances = new float[size+1][size+1];
    }

    public void executeGaleShapley(){
        findDistances();

    }


    //private void

    private void findDistances(){
        int xi,yi;
        int xj,yj;
        for (int i = 1; i < size ; i++) {
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
}
