package org.example;

class UseEnum {
    public static void main(String[] args) {
        System.out.println(Enum.TrafficLight.RED.getSignification());
    }

    public class Enum {
        enum TrafficLight {
            RED("stop", "apple"),
            YELLOW("ready", "lemon"),
            GREEN("go", "grass");
            private final String signification, another;

            TrafficLight(String a, String s) {
                signification = a;
                another = s;
            }

            public String getSignification() {
                return signification;
            }

            public String getAnother() {
                return another;
            }
        }
    }
}
