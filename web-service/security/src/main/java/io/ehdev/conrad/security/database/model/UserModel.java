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

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "email_address")
    private String emailAddress;

    @OneToMany(mappedBy = "userModel")
    private Collection<UserTokenModel> tokenModels;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
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
}
