package in.parida.fitness.activityService.service;

import in.parida.fitness.activityService.dto.ActivityRequest;
import in.parida.fitness.activityService.dto.ActivityResponse;
import in.parida.fitness.activityService.model.Activity;
import in.parida.fitness.activityService.repository.ActivityRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
// REMOVE @Transactional annotation
public class ActivityService {

    private final ActivityRepository activityRepository;
    private final UserValidationService userValidationService;

    public ActivityResponse trackActivity(ActivityRequest request) {

        log.info("=== START trackActivity ===");
        boolean isValidUser=userValidationService.validateUser(request.getUserId());
        if (!isValidUser){
            log.error("Not a valid User");
            throw new RuntimeException("Invalid User: "+ request.getUserId());
        }
        log.info("Received request: userId={}, type={}, duration={}, calories={}, startTime={}",
                request.getUserId(), request.getType(), request.getDuration(),
                request.getCaloriesBurned(), request.getStartTime());

        if (request.getAdditionalMetrics() != null) {
            log.info("Additional metrics keys: {}", request.getAdditionalMetrics().keySet());
        }

        try {
            Activity activity = Activity.builder()
                    .userId(request.getUserId())
                    .type(request.getType())
                    .duration(request.getDuration())
                    .caloriesBurned(request.getCaloriesBurned())
                    .startTime(request.getStartTime())
                    .additionalMetrics(request.getAdditionalMetrics())
                    .build();

            log.info("Built Activity object - ID will be generated");

            // Save and immediately flush to ensure persistence
            Activity savedActivity = activityRepository.save(activity);

            // Force flush to database
//            activityRepository.flush();

            log.info("Saved activity with generated ID: {}", savedActivity.getId());
            log.info("CreatedAt: {}, UpdatedAt: {}",
                    savedActivity.getCreatedAt(), savedActivity.getUpdatedAt());

            ActivityResponse response = mapToResponse(savedActivity);
            log.info("=== END trackActivity - Success ===");
            return response;

        } catch (Exception e) {
            log.error("=== END trackActivity - Error: {} ===", e.getMessage(), e);
            throw new RuntimeException("Failed to save activity: " + e.getMessage(), e);
        }
    }

    private ActivityResponse mapToResponse(Activity savedActivity) {
        ActivityResponse response = new ActivityResponse();
        response.setId(savedActivity.getId());
        response.setUserId(savedActivity.getUserId());
        response.setType(savedActivity.getType());
        response.setDuration(savedActivity.getDuration());
        response.setCaloriesBurned(savedActivity.getCaloriesBurned());
        response.setStartTime(savedActivity.getStartTime());
        response.setAdditionalMetrics(savedActivity.getAdditionalMetrics());
        response.setCreatedAt(savedActivity.getCreatedAt());
        response.setUpdatedAt(savedActivity.getUpdatedAt());

        log.info("Mapped response with ID: {}", response.getId());
        return response;
    }

    public List<ActivityResponse> getAllActivities() {
        List<Activity> activities = activityRepository.findAll();
        log.info("Found {} activities in database", activities.size());
        return activities.stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public ActivityResponse getActivityById(String id) {
        Activity activity = activityRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Activity not found with id: " + id));
        return mapToResponse(activity);
    }

    public long getActivityCount() {
        long count = activityRepository.count();
        log.info("Database count: {}", count);
        return count;
    }
}