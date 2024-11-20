package com.scm.scm10.entities;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "users")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User implements UserDetails {
    @Id
    private String userId;
    @Column(name = "user_name",nullable = false)
    private String name;
    @Column(unique = true,nullable = false)
    private String email;
    @Getter(value = AccessLevel.NONE)
    private String password;
    @Column(length = 1000)
    private String about;
    private String phoneNumber;
    @Column(length = 1000)
    private String profilePic;
    @Getter(value = AccessLevel.NONE)
    private Boolean enabled=false;
    private Boolean emailVerified=false;
    private Boolean phoneVerified=false;
    @Enumerated(value = EnumType.STRING)
    private Providers providers = Providers.SELF;
    private String providerUserId;

    private String emailToken;
    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL,fetch = FetchType.LAZY,orphanRemoval = true)
    private List<Contact> contactList=new ArrayList<>();

    @ElementCollection(fetch = FetchType.EAGER)
    List<String> roles = new ArrayList<>();
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles.stream().map(role -> new SimpleGrantedAuthority(role)).collect(Collectors.toList());
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    //email is a username for our project
    @Override
    public String getUsername() {
        return this.email;
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
    public boolean isEnabled()
    {
        return this.enabled;
    }


}
