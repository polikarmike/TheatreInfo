package edu.fa.user;

import edu.fa.model.entities.users.User;
import edu.fa.model.entities.users.UserType;
import edu.fa.repository.auth.UserRepository;
import edu.fa.repository.auth.UserTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
@RequiredArgsConstructor
public class UserInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final UserTypeRepository userTypeRepository;

    @Value("${admin.username}")
    private String adminUsername;

    @Value("${admin.firstName}")
    private String adminFirstName;

    @Value("${admin.lastName}")
    private String adminLastName;

    @Value("${admin.email}")
    private String adminEmail;

    @Value("${admin.password}")
    private String adminPassword;

    @Override
    public void run(String... args) {

        UserType adminType = userTypeRepository.findByName("ADMIN")
                .orElseGet(() -> userTypeRepository.save(new UserType(null, "ADMIN")));

        UserType userType = userTypeRepository.findByName("USER")
                .orElseGet(() -> userTypeRepository.save(new UserType(null, "USER")));

        userRepository.findByUsername(adminUsername).ifPresent(userRepository::delete);

        User admin = new User();
        admin.setUsername(adminUsername);
        admin.setFirstName(adminFirstName);
        admin.setLastName(adminLastName);
        admin.setEmail(adminEmail);
        admin.setPassword(new BCryptPasswordEncoder().encode(adminPassword));
        admin.setUserTypes(Set.of(adminType));
        userRepository.save(admin);
    }
}
