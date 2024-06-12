package com.example.productManagement.customerRegistration.service;

import com.example.productManagement.keycloak.config.KeycloakConfiguration;
import com.example.productManagement.customerRegistration.entity.Customer;
import com.example.productManagement.customerRegistration.repository.CustomerRepository;
import com.example.productManagement.utilityCode.InputsForKeyCloakUserCreation;
import com.example.productManagement.utilityCode.MailService;
import com.example.productManagement.utilityCode.UseCode;
import jakarta.mail.MessagingException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.stereotype.Component;
import java.io.UnsupportedEncodingException;
import java.util.List;

@Slf4j
@Component
public class CustomerRegisterService {
    private final CustomerRepository userRepository;
    private final MailService mailService;
    private final UseCode useCode;
    private UsersResource usersResource;
    private   KeycloakConfiguration KeycloakConfiguration;

    public CustomerRegisterService(CustomerRepository userRepository,MailService mailService, UseCode useCode, KeycloakConfiguration keycloakConfiguration) {
        this.userRepository = userRepository;
        this.mailService = mailService;
        this.usersResource = keycloakConfiguration.getInstance().realm(keycloakConfiguration.realm).users();
        this.useCode = useCode;
    }


    public void saveUser(Customer customer, String getSiteURL) throws MessagingException, UnsupportedEncodingException {
        if(customer.getPassword().equals(customer.getConfirmPassword())) {
            String randomCode = RandomStringUtils.randomAlphanumeric(64);
            customer.setVerificationCode(randomCode);
            customer.setEnabled(false);

            customer.setRole(useCode.getCustomerRole());

             boolean isCreated=useCode.createKeyCloakUser(new InputsForKeyCloakUserCreation(customer.getMobileNumber(),customer.getFirstName(),
                     customer.getLastName(),customer.getEmail(),customer.getRole(),customer.getPassword()));
             if(isCreated) {
                 List<UserRepresentation> userList = usersResource.searchByEmail(customer.getEmail(), true);
                 customer.setKeyId(userList.get(0).getId());
                 userRepository.save(customer);
                 mailService.sendVerificationEmail(customer, getSiteURL);
             }

        }
        else log.info("wrongPassword");

    }


    public boolean verify(String verificationCode) {
        Customer user = userRepository.findByVerificationCode(verificationCode);

        if (user == null || user.isEnabled()) {
            return false;
        } else {
            user.setVerificationCode(null);
            user.setEnabled(true);
            userRepository.save(user);

            return true;
        }

    }


}
