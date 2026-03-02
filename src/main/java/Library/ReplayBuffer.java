package Library;

import java.util.Random;
import InverseKinematics.State;

public class ReplayBuffer {


    private int size;
    private int addIndex;
    private final int maxSize;
    private final BufferTransition[] replayBuffer;


    private Random random = new Random();//Should seed
    //Maybe should be singleton

    public ReplayBuffer() {
        maxSize = NetworkConstants.REPLAY_BUFFER_MAX_SIZE;
        replayBuffer = new BufferTransition[maxSize];
        size = 0;
        addIndex = 0;
    }

    public void addToReplayBuffer(State currentState, int actionIndex, double reward, State nextState, boolean isDone, boolean doneIllegalMove){
        BufferTransition newTransition = new BufferTransition(currentState,actionIndex,reward,nextState,isDone,doneIllegalMove);
        replayBuffer[addIndex] = newTransition;
        addIndex = (addIndex + 1)%maxSize;//resets addIndex to 0 if index reaches end

        if(size < maxSize)
            size++;
    }


    public BufferTransition getRandomFromReplayBuffer(){
        return replayBuffer[random.nextInt(size)];
    }

    public int getSize() {
        return size;
    }
}
