import java.util.Random;

/**
 * Created by Christian on 19.08.2015.
 */
public class Environment {


    private final Random rand;
    private final int numNo;
    private final int numYes;
    private Double probability;


    /**
     *
     * @param numYes
     * @param numNo
     */
    public Environment(int numYes, int numNo){

        this.numYes = numYes; // M
        this.numNo = numNo;


        this.rand = new Random(System.nanoTime());

        this.probability = null;

    }



    // Decides the punishment based on current choice.
    public void probability () {

        // Total number of results
        int totalResults = this.numNo + this.numYes;

        if (this.numYes <= Math.ceil(totalResults / 2)) {
            this.probability = this.numYes * 0.2;
        }
        else {
            this.probability = 0.6 - (this.numYes - 3) * 0.2;
        }


    }

    public boolean verdict(){

        if (this.rand.nextDouble() > this.probability)
            return true;
        else
            return false;
    }



}
