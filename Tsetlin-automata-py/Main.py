import random
import uuid
import sys
import operator
import os

class Automata(object):


    def __init__(self, states):

        # Do not allow odd numbers (Should never happen unless 3.5 example)
        if (states * 2) %  2 != 0:
            raise ValueError("Cannot use a odd number")

        self.states = states
        self.state = random.randint(self.states, (self.states) + 1)
        self.id = uuid.uuid1()
        # print("Automata: {0} - Init state: {1}").format(self.id, self.state)


        self.tmpyes = 0
        self.tmpno = 0


    def isYes(self):
        total_states = self.states * 2
        if self.state > (total_states / 2):
            return True
        else:
            return False


    def reward(self):

        total_states = self.states * 2

        # Positive side
        if self.state > (total_states / 2):
            #print ("Reward {0} --> {1}").format(self.state, min(self.state + 1, total_states))
            self.state = min(self.state + 1, total_states)
        # Negative side
        else:
            #print ("Reward {0} --> {1}").format(self.state, max(self.state - 1, 1))
            self.state = max(self.state - 1, 1)

    def punish(self):
        total_states = self.states * 2
        # Positive side
        if self.state > (total_states / 2):
            #print ("Punish {0} --> {1}").format(self.state, self.state - 1)
            self.state = self.state - 1
        # Negative side
        else:
            #print ("Punish {0} --> {1}").format(self.state, self.state + 1)
            self.state = self.state + 1



class Environment(object):

    def __init__(self, yes, num_automatas):
        self.yes = yes # M
        self.num_automatas = num_automatas
        self.prob = 0


    def probability(self):

        #percent_yes = (self.yes / self.num_automatas) * 100.0

        # Above 60% YES
        if self.yes <= 3:
            self.prob = self.yes * 0.2  # yes=5, probl =1 ... yes=0, prob = 0,
        else:
            self.prob = 0.6 - (self.yes - 3) * 0.2  # yes=5, 0.6-2*0.2 = 0.5


    def doReward(self):

        r_num = random.uniform(0.0, 1.0)

        if r_num >= self.prob:
            return False
        else:
            return True



# Logic ----------------
states = int(sys.argv[1])
num_automata = 5
iterations = int(sys.argv[3])
times =  int(sys.argv[2])

# Create automatas
automata_list = [Automata(states) for x in range(num_automata)]
result = {}


for x in range(times):
    for x in range(iterations):

    # Count YES
        yes = 0
        for automata in automata_list:
            if automata.isYes():
                automata.tmpyes = automata.tmpyes + 1 # TODO
                yes = yes + 1
            else:
                automata.tmpno = automata.tmpno + 1 # TODO


    # Create environment and calculate probability
    env = Environment(yes, len(automata_list))
    env.probability()

    for automata in automata_list:
        if env.doReward():# and False:
            #print "Rewarding"
            automata.reward()
        else:
            #print "Punishing"
            automata.punish()


    # Generate result for iteration
    result_key = "{0}/{1}".format(yes, len(automata_list) - yes)
    if result_key not in result:
        result[result_key] = 1
    else:
        result[result_key] = result[result_key] + 1

    open('data.dat', 'w').close()
    for key, value in sorted(result.items(), key=operator.itemgetter(1), reverse=True):
    #print("{0}: {1}").format(key, value)
        with open ('data.dat', 'a') as f: f.write (key+ "   " + str(value) + '\n')
        #os.system("python termgraph.py data.dat")
