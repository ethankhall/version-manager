package io.ehdev.conrad.security.database.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "security_user_client_profile")
public class ClientUserProfileModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(updatable = false, unique = true, name = "uuid")
    private UUID id;

    @JsonIgnore
    @ManyToOne(optional = false)
    @JoinColumn(name = "user_uuid")
    private UserModel userModel;

    @Column(name = "provider_type")
    private String providerType;

    @Column(name = "provider_user_id")
    private String providerUserId;

    public ClientUserProfileModel() {

    }

    public ClientUserProfileModel(UserModel userModel, String providerType, String providerUserId) {
        this.userModel = userModel;
        this.providerType = providerType;
        this.providerUserId = providerUserId;
    }

    public UserModel getUserModel() {
        return userModel;
    }

    public void setUserModel(UserModel userModel) {
        this.userModel = userModel;
    }

    public String getProviderType() {
        return providerType;
    }

    public void setProviderType(String providerType) {
        this.providerType = providerType;
    }

    public String getProviderUserId() {
        return providerUserId;
    }

    public void setProviderUserId(String providerUserId) {
        this.providerUserId = providerUserId;
    }
}
