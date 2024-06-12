package com.example.productManagement.keycloak.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.productManagement.keycloak.dtos.LoginRequestDto;
import com.example.productManagement.keycloak.dtos.LoginResponseDto;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.http.impl.client.HttpClients;
import org.keycloak.authorization.client.AuthzClient;
import org.keycloak.authorization.client.Configuration;
import org.keycloak.representations.AccessTokenResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
public class LoginService {
    private static final Logger log = LoggerFactory.getLogger(LoginService.class);
    @Value("${keycloak1.auth-server-url}")
    private String authServerUrl;

    @Value("${keycloak1.realm}")
    private String realm;

    @Value("${keycloak1.resource}")
    private String clientId;

    @Value("${keycloak1.credentials.secret}")
    private String clientSecret;


    public String login(LoginRequestDto loginDto, HttpServletResponse res) throws IOException {

        Map<String, Object> clientCredentials = new HashMap<>();
        clientCredentials.put("secret", clientSecret);
        clientCredentials.put("provider", "secret");

        Configuration configuration =
                new Configuration(authServerUrl, realm, clientId, clientCredentials, HttpClients.createDefault());
        AuthzClient authzClient = AuthzClient.create(configuration);
        try {
            AccessTokenResponse response =
                    authzClient.obtainAccessToken(loginDto.getMobileNumber(), loginDto.getPassword());
            LoginResponseDto loginResponse = new LoginResponseDto();
            loginResponse.setAccessToken(response.getToken());
            loginResponse.setRefreshToken(response.getRefreshToken());
            String role=keycloakRoleExtract(loginResponse.getAccessToken());

            return role;

        } catch (Exception e) {

            res.sendError( HttpServletResponse.SC_UNAUTHORIZED,e.getMessage());
            return null;
        }
    }


    public String keycloakRoleExtract(String accessToken){
        //Role role = new Role();
        try {
            // Decode the JWT token
            DecodedJWT jwt = JWT.decode(accessToken);

            // Extract the payload from the token
            String payload = jwt.getPayload();

            // Decode the payload (Base64 URL encoded)
            byte[] decodedBytes = java.util.Base64.getUrlDecoder().decode(payload);
            String decodedPayload = new String(decodedBytes);

            // Parse the JSON payload
            ObjectMapper mapper = new ObjectMapper();
            JsonNode jsonNode = mapper.readTree(decodedPayload);

            // Extract roles from realm_access or resource_access
            JsonNode realmAccessNode = jsonNode.get("realm_access");
            if (realmAccessNode != null) {
                JsonNode rolesNode = realmAccessNode.get("roles");
                if (rolesNode != null) {
                    for (JsonNode roles : rolesNode) {
                        String roleName = roles.asText();
                        if (roleName.equals("org-customer")) {
                            System.out.println("org-customer");

                           return roleName;
                        }
                        if (roleName.equals("org-publisher")) {
                            System.out.println("org-publisher");
                            return  roleName;
                        }
                        if(roleName.equals("org-admin")) {}

                    }
                }
            }

           /* // If roles are under resource_access
            JsonNode resourceAccessNode = jsonNode.get("resource_access");
            if (resourceAccessNode != null) {
                resourceAccessNode.fields().forEachRemaining(entry -> {
                    JsonNode clientRolesNode = entry.getValue().get("roles");
                    if (clientRolesNode != null) {
                        for (JsonNode role : clientRolesNode) {

                        }
                    }
                });
            }
*/
        } catch (Exception e) {
            e.printStackTrace();
        }
       return null;
    }
}

