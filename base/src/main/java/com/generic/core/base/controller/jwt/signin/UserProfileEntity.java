package com.generic.core.base.controller.jwt.signin;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.generic.core.base.data.AuditableEntity;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@Table(name = "user_profile", schema = "cashbox")
public class UserProfileEntity extends AuditableEntity {
    private String username;
    private String email;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;
    private String firstName;
    private String middleName;
    private String lastName;
    private String role;
    private String status;
    private String mobileNumber;
    private String telephoneNumber;
    private String address;
    @Column(updatable = false, nullable = false)
    private UUID companyId;
    private String companyName;
    private String companyType;
    private String isLogin;
}
