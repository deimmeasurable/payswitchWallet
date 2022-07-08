package com.example.paytwitch.data.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Document
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User {
    private String firstName;
    private String lastName;
    @Id
    private String email;
    private List<String> country= new ArrayList<String>();
    private String password;

    private Set<Role> roles;

    public User(String email, String password) {
        this.email = email;
        this.password = password;

    }
    public User(String email, String password, RoleType roleType) {
        this.email = email;
        this.password = password;
        if (roles == null){
            roles = new HashSet<>();
        }
        roles.add(new Role(roleType));
    }
    public void addRole(Role role){
        if (this.roles == null){
            this.roles = new HashSet<>();
        }
        roles.add(role);
    }
}
