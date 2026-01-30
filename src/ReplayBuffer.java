import java.util.ArrayList;
import java.util.Random;

public class ReplayBuffer {


    private int size;
    private  ArrayList<BufferTransition> replayBuffer;


    private Random random = new Random();//Should seed
    //Maybe should be singleton

    public ReplayBuffer() {
        replayBuffer = new ArrayList<>();
        size = 0;
    }

    public void addToReplayBuffer(State currentState, Action action, double reward, State nextState){
        BufferTransition newTransition = new BufferTransition(currentState,action,reward,nextState);
        replayBuffer.add(newTransition);
        size++;
    }


    public BufferTransition getRandomFromReplayBuffer(){
        return replayBuffer.get(random.nextInt(size));
    }
}
