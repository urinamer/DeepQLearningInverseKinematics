public class Constants {


    public static final String NETWORK_FILE = "Models/Network.csv";

    public static final double MAX_ENVIRONMENT_X = 10;
    public static final double MAX_ENVIRONMENT_Y = 10;
    public static final double ANGLE_CHANGE_STEP = 10;
    public static final double DEFAULT_LINK_LENGTH = 0.5;
    public static final double DEFAULT_ANGLE = 90;
    public static final int DEFAULT_NUM_OF_LINKS = 2;




    public static final int MAX_STEPS_PER_EPISODE = 10000;
    public static final int BATCH_SIZE = 10;
    public static final int MIN_NUM_OF_TRANSITIONS = 100;
    public static final int STEPS_TO_UPDATE_TARGET_NETWORK = 10;
    public static final int REPLAY_BUFFER_MAX_SIZE = 1000;


    public static final double STEP_SIZE = 1;
    public static final double DISCOUNT_FACTOR = 0.9;
    public static final double LEARNING_RATE = 0.1;

    public static final double DISTANCE_MIN_MARGIN = 0.1;
    public static final double REWARD = 1;
    public static final double REACHED_POINT_REWARD = 10;
    public static final double PUNISHMENT = -1;
    public static final double HITTING_WALLS_PENALTY = -0.01;


    public static final double BIAS_STARTER_VALUE = 0.01;



    public static final int NUM_OF_LAYERS = 3;
    public static final int NUM_OF_NEURONS_IN_LAYER = 5;

}
