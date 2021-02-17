package com.rigby.pushit.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
public class User {

    @Id                                                         // primary key
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank @Email @Column(unique = true)
    private String email;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)      // este campo es ignorado al devolver la respuesta
    @NotBlank @Size(min = 4)
    private String password;

    private String name;

    @JsonIgnore
    @ManyToMany
    private List<User> contacts = new ArrayList<>();

    public void addContact(User user){
        contacts.add(user);
    }


}
