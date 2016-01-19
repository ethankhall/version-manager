package io.ehdev.conrad.security.database.model;

import io.ehdev.conrad.model.user.UserModel;

import javax.persistence.*;
import java.util.Collection;
import java.util.UUID;

@Entity
@Table(name = "security_user")
public class SecurityUserModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(updatable = false, unique = true, name = "uuid")
    private UUID id;

    @Column(name = "name")
    private String name;

    @Column(name = "email_address")
    private String emailAddress;

    @OneToMany(mappedBy = "userModel")
    private Collection<SecurityUserClientProfileModel> clientUserProfileModels;

    @OneToMany(mappedBy = "userModel")
    private Collection<SecurityUserTokenModel> tokenModels;

    public SecurityUserModel() {
    }

    public SecurityUserModel(String name, String emailAddress) {
        this.name = name;
        this.emailAddress = emailAddress;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public Collection<SecurityUserTokenModel> getTokenModels() {
        return tokenModels;
    }

    public void setTokenModels(Collection<SecurityUserTokenModel> tokenModels) {
        this.tokenModels = tokenModels;
    }

    public Collection<SecurityUserClientProfileModel> getClientUserProfileModels() {
        return clientUserProfileModels;
    }

    public void setClientUserProfileModels(Collection<SecurityUserClientProfileModel> clientUserProfileModels) {
        this.clientUserProfileModels = clientUserProfileModels;
    }

    public UserModel toUserModel() {
        return new UserModel(id, name, emailAddress);
    }
}
