package finalproject.evroutefinder.service;

import finalproject.evroutefinder.dto.AuthenticationResponse;
import finalproject.evroutefinder.dto.LoginRequest;
import finalproject.evroutefinder.dto.RegisterRequest;
import finalproject.evroutefinder.exceptions.EVRouteFinderException;
import finalproject.evroutefinder.model.AppUser;
import finalproject.evroutefinder.model.NotificationEmail;
import finalproject.evroutefinder.model.VerificationToken;
import finalproject.evroutefinder.repository.UserRepository;
import finalproject.evroutefinder.repository.VerificationTokenRepository;
import finalproject.evroutefinder.security.JwtProvider;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
@Transactional
public class AuthService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final VerificationTokenRepository verificationTokenRepository;
    private final MailService mailService;
    private final AuthenticationManager authenticationManager;
    private final JwtProvider jwtProvider;

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
        }

    public void verifyAccount(String token){
        Optional<VerificationToken> verificationToken = verificationTokenRepository.findByToken(token);
        verificationToken.orElseThrow(() -> new EVRouteFinderException("Invalid Token"));
        fetchUserAndEnable(verificationToken.get());
    }

    private void fetchUserAndEnable(VerificationToken verificationToken){
        String username = verificationToken.getAppUser().getUsername();
        AppUser appUser = userRepository.findByUsername(username).orElseThrow(()-> new EVRouteFinderException("User not found with name "
                + username));
        appUser.setEnabled(true);
        userRepository.save(appUser);
    }

    public AuthenticationResponse login(LoginRequest loginRequest){
        Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(),
                loginRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authenticate);
        String token = jwtProvider.generateToken(authenticate);
        return new AuthenticationResponse(token, loginRequest.getUsername());
    }
}
