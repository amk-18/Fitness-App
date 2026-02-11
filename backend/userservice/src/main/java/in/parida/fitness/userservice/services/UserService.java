package in.parida.fitness.userservice.services;

import in.parida.fitness.userservice.dto.RegisterRequest;
import in.parida.fitness.userservice.dto.UserResponse;
import in.parida.fitness.userservice.models.User;
import in.parida.fitness.userservice.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserService {


    private final UserRepository repo;

    public UserResponse register(RegisterRequest request) {

        if(repo.existsByEmail(request.getEmail())){
            throw new RuntimeException("Email already Exist");
        }
        User user = new User();
        user.setEmail(request.getEmail());
        user.setPassword(request.getPassword());
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());

        User savedUser= repo.save(user);
        UserResponse response= new UserResponse();
        response.setId(savedUser.getId());
        response.setPassword(savedUser.getPassword());
        response.setEmail(savedUser.getEmail());
        response.setFirstName(savedUser.getFirstName());
        response.setLastName(savedUser.getLastName());
        response.setCratedAt(savedUser.getCratedAt());
        response.setUpdatedAt(savedUser.getUpdatedAt());

        return response;

    }

    public UserResponse getUserProfile(String userId) {
        User user=repo.findById(userId)
                .orElseThrow(()->new RuntimeException(" No user Found "));
        UserResponse response= new UserResponse();
        response.setId(user.getId());
        response.setPassword(user.getPassword());
        response.setEmail(user.getEmail());
        response.setFirstName(user.getFirstName());
        response.setLastName(user.getLastName());
        response.setCratedAt(user.getCratedAt());
        response.setUpdatedAt(user.getUpdatedAt());
        return response;
    }


    public Boolean existByUserId(String userId) {
        return repo.existsById(userId);
    }
}
