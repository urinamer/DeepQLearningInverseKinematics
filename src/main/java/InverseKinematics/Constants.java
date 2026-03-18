package InverseKinematics;

public class Constants {


    public static final String NETWORK_FILE = "Models/Network.csv";


    public static final double MAX_ENVIRONMENT_X = 10.5;
    public static final double MAX_ENVIRONMENT_Y = 10.5;
    public static final double MIN_ENVIRONMENT_X = -10.5;
    public static final double MIN_ENVIRONMENT_Y = -10.5;

//    public static final double ANGLE_CHANGE_STEP = 2;

    public static final double DEFAULT_X_BASE = 0;
    public static final double DEFAULT_Y_BASE = 0;
    public static final double DEFAULT_LINK_LENGTH = 5;
    public static final double DEFAULT_ANGLE1 = 90;
    public static final int DEFAULT_NUM_OF_LINKS = 2;




    public static final int MAX_STEPS_PER_EPISODE = 500;
    public static final int BATCH_SIZE = 128;
    public static final int MIN_NUM_OF_TRANSITIONS = 5000;
    public static final int STEPS_TO_UPDATE_TARGET_NETWORK = 5000;



    public static final double DISCOUNT_FACTOR = 0.9;
    public static final float EPSILON_DECAY = 0.9946f;//always change when changing numOfEpisodes
    public static final float HUBER_LOSS_ALPHA = 1.0f;

    public static final double MIN_DISTANCE = 0.5;
    public static final double DISTANCE_CLOSE = 2;
    public static final double STEP_SIZE = 2;
    public static final double STEP_SIZE_CLOSE = 0.5;

    public static final double REACHED_POINT_REWARD = 10;
    public static final double HITTING_WALLS_PENALTY = -5;
    public static final double TIME_WASTED_PENALTY = -0.1;
    public static final double REPEATED_ACTION_PENALTY = -5;
    public static final int REWARD_MULTIPLIER = 10;
    public static final int REWARD_MULTIPLIER_CLOSE = 20;




    public static final int NUM_OF_LAYERS = 3;
    public static final int NUM_OF_NEURONS_IN_LAYER = 64;

}
