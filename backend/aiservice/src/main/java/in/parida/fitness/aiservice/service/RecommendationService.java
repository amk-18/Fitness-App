package in.parida.fitness.aiservice.service;

import in.parida.fitness.aiservice.model.Recommendation;
import in.parida.fitness.aiservice.repository.RecommendationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RecommendationService {

    private final RecommendationRepository repository;

    public List<Recommendation> getUserRecommendation(String userId) {
        return repository.findByUserId(userId);
    }

    public Recommendation getActivityRecommendation(String activityId) {
        return repository.findByActivityId(activityId)
                .orElseThrow(()-> new RuntimeException("No Recommendation found for this activity: "+activityId));
    }
}
