package in.parida.fitness.activityService.dto;

import in.parida.fitness.activityService.model.ActivityType;
import lombok.Data;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Map;

@Data
public class ActivityRequest {

    @NotBlank(message = "User ID is required")
    private String userId;

    @NotNull(message = "Activity type is required")
    private ActivityType type;

    @Min(value = 1, message = "Duration must be at least 1 minute")
    private Integer duration;

    @Min(value = 1, message = "Calories burned must be at least 1")
    private Integer caloriesBurned;

    @NotNull(message = "Start time is required")
    private LocalDateTime startTime;

    private Map<String, Object> additionalMetrics;
}