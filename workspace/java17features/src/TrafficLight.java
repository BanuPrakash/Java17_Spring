public sealed interface TrafficLight /*permits  RedLight, GreenLight, YellowLight */{
}

// record for DTO, immutable objects
record RedLight() implements  TrafficLight {}

record GreenLight() implements  TrafficLight {}

record YellowLight() implements  TrafficLight {}