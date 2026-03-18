package SS9.smartsim.traffic;

public interface TrafficLightObserver {
    void onLightChanged(TrafficLightState newState);
}
