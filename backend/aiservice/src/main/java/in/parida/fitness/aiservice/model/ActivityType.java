package in.parida.fitness.aiservice.model;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum ActivityType {
    RUNNING, WALKING, SWIMMING, CYCLING, YOGA, CARDIO, STRETCHING, OTHER;

    @JsonCreator
    public static ActivityType fromString(String value) {
        try {
            return ActivityType.valueOf(value.toUpperCase());
        } catch (IllegalArgumentException e) {
            return ActivityType.OTHER;
        }
    }
}