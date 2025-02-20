package com.scm.scm20.entities;


import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Entity(name="user")
@Table(name = "users")

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class User implements UserDetails {
    @Id
    private String userId;
    @Column(name="user_name", nullable=false)
    private String name;

    @Getter(value = AccessLevel.NONE)
    @Column(nullable=false)
    private String password;

    @Column(unique = true, nullable=false)
    private String email;
    @Column(length = 1000)
    private String about;
    @Column(length = 1000)
    private String profilePic;


    private String phoneNumber;
    private String address="no address";
    private String birthday;
    private String cloudinaryImagePublicId;

    private boolean enabled=false;
    private boolean emailVerified=false;
    private boolean phoneNumberVerified=false;

   // @Builder.Default     //use this annotation when use builder to set value
    @Enumerated(value = EnumType.STRING)
    //SELF,GOOGLE,FACEBOOK,TWITTER,LINKEDIN,GITHUB
    private Providers providers=Providers.SELF;
    private String providerUserId;


    private String emailToken;


    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL,fetch=FetchType.LAZY,orphanRemoval=true)
    private List<Contact> contacts=new ArrayList<>();

    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> roleList=new ArrayList<>();
    //list of roles[USER,ADMIN]
    //Collection of SimpleGrantedAuthority [roles{ADMIN,USER}]
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<SimpleGrantedAuthority> roles = roleList.stream().map(role -> new SimpleGrantedAuthority(role)).collect(Collectors.toList());
        return roles;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public boolean isEnabled() {
        return this.enabled;
    }
    @Override
    public String getPassword() {
        return this.password;
    }
}
