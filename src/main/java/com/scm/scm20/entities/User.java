package com.scm.scm20.entities;


import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity(name="user")
@Table(name = "users")

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class User {
    @Id
    private String userId;
    @Column(name="user_name", nullable=false)
    private String name;
    @Column(nullable=false)
    private String password;
    @Column(unique = true, nullable=false)
    private String email;
    @Column(length = 1000)
    private String about;
    @Column(length = 1000)
    private String profilePic;
    private String phoneNumber;

    private boolean enabled=false;
    private boolean emailVerified=false;
    private boolean phoneNumberVerified=false;

   // @Builder.Default     //use this annotation when use builder to set value
    @Enumerated(value = EnumType.STRING)
    //SELF,GOOGLE,FACEBOOK,TWITTER,LINKEDIN,GITHUB
    private Providers providers=Providers.SELF;
    private String providerUserId;



    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL,fetch=FetchType.LAZY,orphanRemoval=true)
    private List<Contact> contacts=new ArrayList<>();


}
