package com.example.chat_app.service;

import com.amazonaws.services.cognitoidp.AWSCognitoIdentityProvider;
import com.amazonaws.services.cognitoidp.model.*;
import com.example.chat_app.model.User;
import com.example.chat_app.repository.UserRepository;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.JWTParser;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.Map;

@Service
public class CognitoService {

    @Value("${spring.security.oauth2.client.registration.cognito.client-id}")
    private String clientId;

    @Value("${spring.security.oauth2.client.registration.cognito.client-secret}")
    private String clientSecret;

    @Value("${spring.security.oauth2.client.registration.cognito.scope}")
    private String scope;

    @Value("${spring.security.oauth2.client.provider.cognito.issuer-uri}")
    private String issuerUri;

    @Autowired
    private AWSCognitoIdentityProvider cognitoIdentityProvider;

    @Autowired
    private UserRepository userRepository;

    public User registerUser(String username, String email, String password) {
        // Set up the AWS Cognito registration request
        SignUpRequest signUpRequest = new SignUpRequest()
                .withClientId(clientId)
                .withUsername(username)
                .withPassword(password)
                .withUserAttributes(
                        new AttributeType().withName("email").withValue(email)
                );

        // Register the user with Amazon Cognito
        try {
            SignUpResult signUpResponse = cognitoIdentityProvider.signUp(signUpRequest);

            // Additional processing and user creation logic if needed

            User registeredUser = new User();
            registeredUser.setUsername(username);
            registeredUser.setEmail(email);
            registeredUser.setPassword(password);

            return userRepository.save(registeredUser);

        } catch (Exception e) {
            throw new RuntimeException("User registration failed: " + e.getMessage(), e);
        }
    }

    public User loginUser(String username, String password) {
        // Set up the authentication request
        InitiateAuthRequest authRequest = new InitiateAuthRequest()
                .withAuthFlow("USER_PASSWORD_AUTH")
                .withClientId(clientId)
                .withAuthParameters(
                        Map.of(
                                "USERNAME", username,   // Use email as the username
                                "PASSWORD", password
                        )
                );

        try {
            InitiateAuthResult authResult = cognitoIdentityProvider.initiateAuth(authRequest);
            System.out.println(authResult);
            AuthenticationResultType authResponse = authResult.getAuthenticationResult();

            // At this point, the user is successfully authenticated, and you can access JWT tokens:
            String accessToken = authResponse.getAccessToken();
            String idToken = authResponse.getIdToken();
            String refreshToken = authResponse.getRefreshToken();

            // Decode and verify the JWT tokens for user information
            Map<String, Object> decodedIdToken = decodeIdToken(idToken);

            // Retrieve email from decodedIdToken
            String email = (String) decodedIdToken.get("email");

            // Additional processing and user creation logic if needed
            // You can decode and verify the JWT tokens for user information

            User loggedInUser = new User();
            loggedInUser.setUsername(username);
            loggedInUser.setEmail(email);
            loggedInUser.setAccessToken(accessToken); // Store the token for future requests

            return loggedInUser;

        } catch (Exception e) {
            throw new RuntimeException("User login failed: " + e.getMessage(), e);
        }
    }

    private Map<String, Object> decodeIdToken(String idToken) throws ParseException {
        JWTClaimsSet claimsSet = JWTParser.parse(idToken).getJWTClaimsSet();
        return claimsSet.getClaims();
    }
}

