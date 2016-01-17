package io.ehdev.conrad.security.user;

import io.ehdev.conrad.security.database.model.UserModel;

import java.util.UUID;

public class UserPrincipal {

    private UUID id;
    private String firstName;
    private String emailAddress;

    public UserPrincipal(UserModel userModel) {
        this(userModel.getId(), userModel.getFirstName(), userModel.getEmailAddress());
    }

    public UserPrincipal(UUID id, String firstName, String emailAddress) {
        this.id = id;
        this.firstName = firstName;
        this.emailAddress = emailAddress;
    }

    public UUID getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getEmailAddress() {
        return emailAddress;
    }
}
