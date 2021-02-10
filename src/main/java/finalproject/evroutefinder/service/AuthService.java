package finalproject.evroutefinder.service;

import com.sun.xml.bind.v2.runtime.SchemaTypeTransducer;
import finalproject.evroutefinder.dto.RegisterRequest;
import finalproject.evroutefinder.model.AppUser;
import finalproject.evroutefinder.model.NotificationEmail;
import finalproject.evroutefinder.model.VerificationToken;
import finalproject.evroutefinder.repository.UserRepository;
import finalproject.evroutefinder.repository.VerificationTokenRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.beanvalidation.SpringValidatorAdapter;

import javax.transaction.Transactional;
import java.time.Instant;
import java.util.UUID;

@Service
@AllArgsConstructor
@Transactional
public class AuthService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final VerificationTokenRepository verificationTokenRepository;
    private MailService mailService;

    public void signup(RegisterRequest registerRequest) {
        AppUser appUser = new AppUser();
        appUser.setUsername(registerRequest.getUsername());
        appUser.setEmail(registerRequest.getEmail());
        appUser.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        appUser.setCreated(Instant.now());
        appUser.setEnabled(false);

        userRepository.save(appUser);

        String token = generateVerificationToken(appUser);
        mailService.sendMail(new NotificationEmail("Please Activate your Account",
                appUser.getEmail(), "Thank you for signing up to EV Route Finder, " +
                "please click on the below url to activate your account : " +
                "http://localhost:8080/api/auth/accountVerification/" + token));
    }

        private String generateVerificationToken(AppUser appUser){
            String token = UUID.randomUUID().toString();
            VerificationToken verificationToken = new VerificationToken();
            verificationToken.setToken(token);
            verificationToken.setAppUser(appUser);

            verificationTokenRepository.save(verificationToken);
            return token;
        };

}
