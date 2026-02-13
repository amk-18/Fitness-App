package in.parida.fitness.aiservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDateTime;
import java.util.Map;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Activity {

    @Id
    private String id;

    @Field("user_id")
    private String userId;

    private ActivityType type;
    private Integer duration;

    @Field("calories_burned")
    private Integer caloriesBurned;

    @Field("start_time")
    private LocalDateTime startTime;

    @Field("metrics")
    private Map<String, Object> additionalMetrics;

    @CreatedDate
    @Field("created_at")
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Field("updated_at")
    private LocalDateTime updatedAt;
}