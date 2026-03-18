package SS9.smartsim.traffic;

public interface TrafficLightState {
    String getName();

    long getDurationMillis();

    TrafficLightState next();
}
