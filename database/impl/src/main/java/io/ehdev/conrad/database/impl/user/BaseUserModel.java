package io.ehdev.conrad.database.impl.user;

import io.ehdev.conrad.database.impl.token.UserTokenModel;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "user_model")
public class BaseUserModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(updatable = false, unique = true, name = "uuid")
    private UUID id;

    @Column(name = "name")
    private String name;

    @Column(name = "email_address")
    private String emailAddress;

    @OneToMany(mappedBy = "userModel")
    private List<UserTokenModel> tokens;

    public BaseUserModel() {
    }

    public BaseUserModel(String displayName, String email) {
        this.name = displayName;
        this.emailAddress = email;
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public List<UserTokenModel> getTokens() {
        return tokens;
    }
}
