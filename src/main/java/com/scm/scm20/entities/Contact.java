package com.scm.scm20.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Contact {

    @Id
    private String id;
    private String name;
    private String phoneNumber;
    private String email;
    private String address;
    private String picture;
    @Column(length = 1000)
    private String description;
    private boolean favorite=false;
    private String linkdInLink;
    private String facebookLink;

    private String cloudinaryImagePublicId;

    @ManyToOne
    @JsonIgnore
    private User user;

    @OneToMany(mappedBy = "contact",cascade = CascadeType.ALL,fetch = FetchType.EAGER,orphanRemoval=true)
    private List<SocialLink> socialLinks=new ArrayList<>();


}
