package com.example.productManagement.publisherRegistration.service;

import com.example.productManagement.keycloak.config.KeycloakConfiguration;
import com.example.productManagement.publisherRegistration.entity.PublisherProfileDto;
import com.example.productManagement.publisherRegistration.repository.PublisherRepository;
import com.example.productManagement.utilityCode.InputsForKeyCloakUserCreation;
import com.example.productManagement.utilityCode.MailService;
import com.example.productManagement.utilityCode.UseCode;
import com.example.productManagement.publisherRegistration.entity.Publisher;
import jakarta.mail.MessagingException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.util.List;
@Slf4j
@Component
public class PublisherService {

    private final PublisherRepository publisherRepository;
    private final MailService mailService;
    private final UseCode useCode;
    private UsersResource usersResource;
    @Autowired
    private KeycloakConfiguration KeycloakConfiguration;

    public PublisherService(PublisherRepository publisherRepository,MailService mailService, UseCode useCode, KeycloakConfiguration KeycloakConfiguration) {
    this.publisherRepository = publisherRepository;
    this.mailService = mailService;
    this.useCode = useCode;
    this.usersResource=KeycloakConfiguration.getInstance().realm(KeycloakConfiguration.realm).users();
   }

    public void savePublisher(Publisher publisher, String getSiteURL) throws MessagingException, UnsupportedEncodingException {
        if(publisher.getPassword().equals(publisher.getConfirmPassword())) {
            String randomCode = RandomStringUtils.randomAlphanumeric(64);
            publisher.setVerificationCode(randomCode);
            publisher.setEnabled(false);

            publisher.setRole(useCode.getPublisherRole());

            boolean isCreated=useCode.createKeyCloakUser(new InputsForKeyCloakUserCreation(publisher.getMobileNumber(),publisher.getFirstName(),
                    publisher.getLastName(),publisher.getEmail(),publisher.getRole(),publisher.getPassword()));
            if(isCreated) {
                List<UserRepresentation> userList = usersResource.searchByEmail(publisher.getEmail(), true);
                publisher.setKeyId(userList.get(0).getId());
                publisherRepository.save(publisher);
                mailService.sendVerificationEmail(publisher, getSiteURL);
            }

        }
        else log.info("wrongPassword");

    }

    public PublisherProfileDto viewProfile(String mobileNumber) {
        Publisher profile=this.publisherRepository.findByMobile(mobileNumber);
        if(profile!=null){
            return new PublisherProfileDto(profile.getFirstName()+" "+profile.getLastName(),profile.getFirstName(),
                    profile.getLastName(),profile.getEmail(), profile.getMobileNumber(),profile.getAddress(),profile.getRole());
        }
        return null;
    }
    public String updateProfile(PublisherProfileDto dto){
        Publisher publisher=this.publisherRepository.findByMobile(dto.getMobileNumber());
        if(publisher!=null) {

            publisher.setFirstName(dto.getFirstName());
            publisher.setLastName(dto.getLastName());
            publisher.setEmail(dto.getEmail());
            publisher.setAddress(dto.getAddress());
            publisher.setRole(dto.getRole());
            this.publisherRepository.save(publisher);
            return "profile updated successfully";
        }
        else return "mobileNumber Not Found or you can not update mobile";
    }


}


