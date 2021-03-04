/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.eventmobi.matheus.marsrover.rl4j;

import java.io.IOException;
import java.util.logging.Logger;
import org.deeplearning4j.rl4j.learning.sync.qlearning.QLearning;
import org.deeplearning4j.rl4j.learning.sync.qlearning.discrete.QLearningDiscreteDense;
import org.deeplearning4j.rl4j.network.dqn.DQNFactoryStdDense;
import org.deeplearning4j.rl4j.policy.DQNPolicy;
import org.nd4j.linalg.learning.config.RmsProp;

/**
 *
 * @author Matheus Araújo
 */
public class MarsRoverRl4jAgentTrainer {
    
    private static final boolean hard=false;
    private static final boolean useTime=false;
    private static final boolean useDistance=false;
    private static final boolean initRandom=false;
    private static final boolean randomGoal=false;
    private static final boolean randomObstacles=false;
    private static final boolean dieWhenCollisided=true;
    private static final boolean load=false;
    private static final boolean train=true;
    private static final boolean enderingDelayAtTraining=false;
    private static final boolean renderTraining=train&&true;
    private static final boolean infinityEpochSteps=true;
    private static final int maxSteps=150000*2;
    private static final int hiddenNodes=600;
    private static final int maxEpochSteps=15000;//200
    private static final int layers=6;
    private static final String envID="MarsRoverTrainedAgent"+hiddenNodes+"-"+layers+(initRandom?"InitRandom":"")+(useDistance?"Distance":"")+(useTime?"Time":"")+(randomGoal?"RandomGoal":"")+(randomObstacles?"RandomObstacles":"")+(hard?"Hard":"")+(dieWhenCollisided?"DieWhenCollisided":"")+(infinityEpochSteps?"InfinityEpochSteps":"");
    
    public static void main(String[] args) throws IOException {
        DQNPolicy<MarsRoverObservableSpace>  pol =null;
        if(load){
            pol = DQNPolicy.load(envID);
        }
        if(train){
            pol = trainAgentAndSavePolicy(envID);
        }
        
        loadTrainedAgent(pol, envID); //show off the trained agent.
        System.exit(0);
    }

    private static DQNPolicy<MarsRoverObservableSpace> trainAgentAndSavePolicy(String envID) throws IOException {

        // Q learning configuration. Note that none of these are specific to the mars rover problem.

        QLearning.QLConfiguration MARS_ROVER_QL = QLearning.QLConfiguration.builder()
                .seed(123)                //Random seed (for reproducability)
                .maxEpochStep(maxEpochSteps)        // Max step By epoch
                .maxStep(maxSteps)           // Max step
                .expRepMaxSize(150000)    // Max size of experience replay
                .batchSize(128)            // size of batches
                .targetDqnUpdateFreq(500) // target update (hard)
                .updateStart(10)          // num step noop warmup
                .rewardFactor(0.01)       // reward scaling
                .gamma(0.99)              // gamma
                .errorClamp(1.0)          // /td-error clipping
                .minEpsilon(0.1f)         // min epsilon
                .epsilonNbStep(1000)      // num step for eps greedy anneal
                .doubleDQN(true)          // double DQN
                .build();

        // The neural network used by the agent. Note that there is no need to specify the number of inputs/outputs.
        // These will be read from the gym environment at the start of training.
        DQNFactoryStdDense.Configuration MARS_ROVER_NET =
                DQNFactoryStdDense.Configuration.builder()
                        .l2(0)
                        .updater(new RmsProp(0.000025))
                        .numHiddenNodes(hiddenNodes)
                        .numLayer(layers)
                        .build();

        MarsRoverMDP mdp = new MarsRoverMDP();
        mdp.setEnvId(envID);
        mdp.setRover(MarsRoverMDP.initRover(hard, randomGoal, randomObstacles));
        mdp.setRenderRepresentation(renderTraining);
        mdp.setResetAtRandomPosition(initRandom);
        mdp.setUseDistanceToCalculateReward(useDistance);
        mdp.setUseTimeToCalculateReward(useTime);
        mdp.setUseHardPlateau(hard);
        mdp.setUseRandomGoal(randomGoal);
        mdp.setUseRandomObstacles(randomObstacles);
        mdp.setDieWhenCollisided(dieWhenCollisided);
        mdp.setRenderingDelay(enderingDelayAtTraining);
        mdp.setInfinityEpochSteps(infinityEpochSteps);
        //Create the solver.
        QLearningDiscreteDense<MarsRoverObservableSpace> dql = new QLearningDiscreteDense(mdp, MARS_ROVER_NET, MARS_ROVER_QL);

        dql.train();
        mdp.close();
        dql.getPolicy().save(envID);
        return dql.getPolicy(); //return the trained agent.
    }

    // pass in a generic policy and endID to allow access from other samples in this package..
    static void loadTrainedAgent(DQNPolicy<MarsRoverObservableSpace> pol, String envID) {
        //use the trained agent on a new similar mdp (but render it this time)

        //define the mdp 
        MarsRoverMDP mdp2 = new MarsRoverMDP();
        mdp2.setEnvId(envID);
        mdp2.setRover(MarsRoverMDP.initRover(hard, randomGoal, randomObstacles));
        mdp2.setRenderRepresentation(true);
        mdp2.setResetAtRandomPosition(initRandom);
        mdp2.setUseDistanceToCalculateReward(useDistance);
        mdp2.setUseTimeToCalculateReward(useTime);
        mdp2.setUseHardPlateau(hard);
        mdp2.setUseRandomGoal(randomGoal);
        mdp2.setUseRandomObstacles(randomObstacles);
        mdp2.setDieWhenCollisided(dieWhenCollisided);
        mdp2.setInfinityEpochSteps(infinityEpochSteps);
        mdp2.setRenderingDelay(true);
        //evaluate the agent
        System.out.println("EVALUATING AGENT");
        double rewards = 0;
        final int executions = 10;
        for (int i = 0; i < executions; i++) {
            mdp2.reset();
            double reward = pol.play(mdp2);
            rewards += reward;
            Logger.getAnonymousLogger().info("Reward: " + reward);
        }

        Logger.getAnonymousLogger().info("average: " + rewards/executions);
        mdp2.close();
    }
}
