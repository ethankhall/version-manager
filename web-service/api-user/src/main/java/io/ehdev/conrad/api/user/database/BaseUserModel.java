package io.ehdev.conrad.api.user.database;

import io.ehdev.conrad.model.user.ConradUser;

import javax.persistence.*;
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

    public BaseUserModel() {

    }

    public BaseUserModel(String displayName, String email) {
        this.name = displayName;
        this.emailAddress = email;
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

    public ConradUser toUserModel() {
        return new ConradUser(id, name, emailAddress);
    }

}
