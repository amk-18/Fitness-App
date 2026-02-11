package in.parida.fitness.activityService.controller;

import in.parida.fitness.activityService.dto.ActivityRequest;
import in.parida.fitness.activityService.dto.ActivityResponse;
import in.parida.fitness.activityService.service.ActivityService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/activities")
@AllArgsConstructor
@Slf4j
public class ActivityController {

    private final ActivityService activityService;

    @PostMapping
    public ResponseEntity<?> trackActivity(@Valid @RequestBody ActivityRequest request) {
        log.info("=== Controller: Received POST request ===");
        log.info("User ID: {}", request.getUserId());
        log.info("Type: {}", request.getType());
        log.info("Duration: {}", request.getDuration());
        log.info("Start Time: {}", request.getStartTime());

        try {
            ActivityResponse response = activityService.trackActivity(request);
            log.info("Controller: Successfully processed request. Response ID: {}", response.getId());
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("Controller: Error processing request", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error: " + e.getMessage() +
                            "\nCause: " + (e.getCause() != null ? e.getCause().getMessage() : "Unknown"));
        }
    }

    @GetMapping("/test-db")
    public ResponseEntity<String> testDatabase() {
        try {
            long count = activityService.getActivityCount();
            return ResponseEntity.ok("Database connected! Activity count: " + count);
        } catch (Exception e) {
            log.error("Database test failed", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Database connection failed: " + e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<List<ActivityResponse>> getAllActivities() {
        try {
            List<ActivityResponse> activities = activityService.getAllActivities();
            return ResponseEntity.ok(activities);
        } catch (Exception e) {
            log.error("Error fetching activities", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ActivityResponse> getActivityById(@PathVariable String id) {
        try {
            ActivityResponse activity = activityService.getActivityById(id);
            return ResponseEntity.ok(activity);
        } catch (RuntimeException e) {
            log.error("Activity not found: {}", id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            log.error("Error fetching activity: {}", id, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/health")
    public ResponseEntity<String> healthCheck() {
        return ResponseEntity.ok("Activity Service is running");
    }


}