public class TrafficLightExample {
    public static void main(String[] args) {
        TrafficLight light = new YellowLight();
        switch (light) {
            case RedLight r -> System.out.println("Wait!!!");
            case YellowLight y -> System.out.println("Ready!!!" + y);
            case GreenLight g -> System.out.println("Go!!!");
            // no need for default
        }
    }
}
