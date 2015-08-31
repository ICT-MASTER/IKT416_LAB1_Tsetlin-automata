/**
 * Created by Per-Arne on 19.08.2015.
 */



import java.util.Random;
import java.util.UUID;

public class Tsetlin {



    // n is nunber of states on each arm
    // ex: n = 3
    // 1,2,3 | 4, 5, 6
    //  NO   |    YES
    // NO => < n / 2
    // YES => > n / 2
    int n;

    // Current state
    int state;

    // Summary count over "NO" and "YES"
    Pair<Integer, Integer> counter;

    // Unique identifier
    private final UUID guid;


    /**
     * Initializer for Tsetlin
     * @param n - Number of states per arm
     */
    public Tsetlin(int n) {
        this.n = n;

        // Create a unique ID
        this.guid = UUID.randomUUID();

        // Initialize the counter
        this.counter = new Pair<Integer, Integer>();
        this.counter.setFirst(0);
        this.counter.setSecond(0);

        // Select state between arms/sides (YES OR NO as initial)
        this.state = new Random(System.nanoTime()).nextInt((n+1) - n) + n;
    }

    /**
     * Punish the state value
     */
    public void punish() {
        if (this.state <= this.n)
            this.state += 1;
        else if (this.state > this.n)
            this.state -= 1;
    }

    /**
     * Reward the state value
     */
    public void reward() {
        if (this.state <= this.n && this.state > 1)
            this.state -= 1;
        else if (this.state > this.n && this.state < 2 * this.n)
            this.state += 1;
    }



    /**
     * Returns result of the dominant "arm"
     * @return - Resulting arm (True = YES, False = NO)
     */
    public boolean calculateResult()
    {
        // TRUE ON > n FALSE on < n
        // ex:
        // n = 3
        // if state = 4 then return TRUE
        // else FALSE
        // return this.state >= this.n;
        if (this.state > this.n) {
            this.counter.setSecond(this.counter.getSecond() + 1);
            return true;
        }

        this.counter.setFirst(this.counter.getFirst() + 1);
        return false;


    }

    public boolean finalResult(){
        if (this.state > this.n) {
            return true;
        }

        return false;
    }




}
