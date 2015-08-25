import java.util.List;

import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {
        int numAutomats = 15; // TODO <-- I DONT FAT THIS NO WORK?!?!
        int numIterations = 1000;
        int states = 6;

        long startTime = System.nanoTime();
        /////////////////////////////////////////////
        //
        // Step 1 - Create 5 Tsetlin Automata with actions “No” and “Yes”
        //
        /////////////////////////////////////////////
        List<Automat> automats = new ArrayList();
        for (int i = 0; i < numAutomats; i++)
        {
            Automat automat = new Automat(states);
            automats.add(automat);
            System.out.printf("Created automat with ID: %s\n", automat.guid);
        }


        /////////////////////////////////////////////
        //
        // Step 2 - Count the number of Tsetlin Automata that outputs a “Yes”-action
        // Note: Yes actions are beeing counted by the "numYes" variable inside the iteration
        // Note 2: Step 3 is inside Environment's probability class
        //
        /////////////////////////////////////////////
        for(int i = 0; i < numIterations; i++){

            // Automat results
            int numYes = 0;
            int numNo = 0;



            for (Automat automat : automats){

                // Calculate state result
                boolean action = automat.calculateResult();


                if(action)
                    numYes += 1;
                else
                    numNo += 1;

            } // -- for end


            // Create an environment and add all results from this rounds actions
            Environment env = new Environment(numYes, numNo);

            // Calculate the probability based on the results

            /////////////////////////////////////////////
            //
            // Step 3: If the number of “Yes”-actions is M Then:
            //      If M = 0 OR 1 OR 2 OR 3: Give each Automaton a reward with probability
            //          M ? 0.2, otherwise a penalty
            //      If M = 4 OR 5: Give each Automaton a reward with probability
            //          0.6 ? (M ? 3) ? 0.2, otherwise a penalty
            //
            /////////////////////////////////////////////
            env.probability();



            /////////////////////////////////////////////
            //
            // Remark: Generate the rewards independently for each automaton
            //
            /////////////////////////////////////////////
            for (Automat automat : automats){

                // Generate a verdict based on the probability and a random double from 0->1
                boolean verdict = env.verdict();

                if(verdict)
                    automat.punish();
                else
                    automat.reward();

                //System.out.println("New State: " + automat.state);
            }
        }

        Pair<Integer, Integer> result = new Pair();
        result.setFirst(0);
        result.setSecond(0);
        for (Automat automat : automats){

            if (automat.finalResult()) {
                result.setSecond(result.getSecond() + 1);
            }else
            {
                result.setFirst(result.getFirst() + 1);
            }


            System.out.printf("Result for %s: %s\n",automat.guid ,automat.counter.result());
        }

        System.out.println("Final: " + result.result());
        System.out.println("Time consumed : " + ((System.nanoTime() - startTime)/1000000) + "ms"); //ms



    }
}
