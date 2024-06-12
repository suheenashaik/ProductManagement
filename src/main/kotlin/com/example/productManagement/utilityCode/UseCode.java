package com.example.productManagement.utilityCode;

import com.example.productManagement.keycloak.config.KeycloakConfiguration;
import org.jetbrains.annotations.NotNull;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.RoleMappingResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.stereotype.Component;

import javax.ws.rs.core.Response;
import java.util.List;

@Component
@EnableAutoConfiguration
@EnableWebSecurity
public class UseCode {

    private String getRole;
    private final RealmResource realmResource;
    private final List<RoleRepresentation> realmRoles;
   private final UsersResource usersResource;

     public UseCode(  @NotNull KeycloakConfiguration keycloakConfiguration){
        realmResource = keycloakConfiguration.getInstance().realm(keycloakConfiguration.realm);
         realmRoles = realmResource.roles().list();
        this.usersResource = keycloakConfiguration.getInstance().realm(keycloakConfiguration.realm).users();
     }
    public boolean createKeyCloakUser(@NotNull InputsForKeyCloakUserCreation input) {
        String password = input.getPassword();
        CredentialRepresentation credentialRepresentation = createPasswordCredentials(password);

        // Set realm roles to assign to the user
        RoleRepresentation role = realmResource.roles().get(input.getRole()).toRepresentation();

        UserRepresentation kcUser = new UserRepresentation();
        kcUser.setUsername(input.getUsername());
        kcUser.setFirstName(input.getFirstName());
        kcUser.setLastName(input.getLastName());
        kcUser.setCredentials(List.of(credentialRepresentation));
        kcUser.setEmail(input.getEmail());
        kcUser.setRealmRoles(List.of(role.getName()));
        kcUser.setEnabled(true);
        kcUser.setEmailVerified(true);

        Response user = usersResource.create(kcUser);

        if (user != null && user.getStatus() == 201) {
            List<UserRepresentation> userList = usersResource.searchByEmail(input.getEmail(), true);

            // Map specified customize role
            return keyCloakRoleMapper(userList, kcUser, role);
        } else {
            return false;
        }
    }
    private boolean keyCloakRoleMapper(@NotNull List<UserRepresentation> userList, @NotNull UserRepresentation kcUser, @NotNull RoleRepresentation role) {
        RoleMappingResource roleMappingResource = usersResource.get(userList.get(0).getId()).roles();
        roleMappingResource.realmLevel().add(List.of(role));

        kcUser.setRealmRoles(List.of(role.getName()));
        usersResource.get(userList.get(0).getId()).update(kcUser);
        return true;
    }
    @NotNull
    private CredentialRepresentation createPasswordCredentials(String password) {
        CredentialRepresentation credentialRepresentation = new CredentialRepresentation();
        credentialRepresentation.setTemporary(false);
        credentialRepresentation.setType(CredentialRepresentation.PASSWORD);
        credentialRepresentation.setValue(password);
        return credentialRepresentation;
    }
    public String getPublisherRole() {
        // assign realm roles
        for (RoleRepresentation role : realmRoles) {
            if (role.getName().equals("org-publisher")) {
                getRole = role.getName();
                break;
            }
        }
        return getRole;
    }

    public String getCustomerRole() {
        // assign realm roles
        for (RoleRepresentation role : realmRoles) {
            if (role.getName().equals("org-customer")) {
                getRole = role.getName();
                break;
            }
        }
        return getRole;
    }


}
