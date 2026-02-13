package in.parida.fitness.aiservice.service;

import in.parida.fitness.aiservice.model.Activity;
import in.parida.fitness.aiservice.model.Recommendation;
import in.parida.fitness.aiservice.repository.RecommendationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class ActivityMessageListener {

    private final ActivityAiService activityAiService;
    private final RecommendationRepository recommendationRepository;

    @KafkaListener(topics = "${kafka.topic.name}", groupId = "activity-processor-group")
    public void processActivity(Activity activity) {
        log.info("Received Activity for processing: {}" ,activity.getUserId());
        Recommendation recommendation=activityAiService.generateRecommendation(activity);
        recommendationRepository.save(recommendation);
    }

}
