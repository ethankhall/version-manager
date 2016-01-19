package io.ehdev.conrad.security.database.model;

import javax.persistence.*;
import java.util.Collection;
import java.util.UUID;

@Entity
@Table(name = "security_user")
public class UserModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(updatable = false, unique = true, name = "uuid")
    private UUID id;

    @Column(name = "name")
    private String name;

    @Column(name = "email_address")
    private String emailAddress;

    @OneToMany(mappedBy = "userModel")
    private Collection<ClientUserProfileModel> clientUserProfileModels;

    @OneToMany(mappedBy = "userModel")
    private Collection<UserTokenModel> tokenModels;

    public UserModel() {
    }

    public UserModel(String name, String emailAddress) {
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

    public Collection<UserTokenModel> getTokenModels() {
        return tokenModels;
    }

    public void setTokenModels(Collection<UserTokenModel> tokenModels) {
        this.tokenModels = tokenModels;
    }

    public Collection<ClientUserProfileModel> getClientUserProfileModels() {
        return clientUserProfileModels;
    }

    public void setClientUserProfileModels(Collection<ClientUserProfileModel> clientUserProfileModels) {
        this.clientUserProfileModels = clientUserProfileModels;
    }
}
