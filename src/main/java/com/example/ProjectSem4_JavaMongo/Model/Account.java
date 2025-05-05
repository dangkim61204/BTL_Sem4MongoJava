package com.example.ProjectSem4_JavaMongo.Model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
@Document(collection = "accounts")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Account {
    @Id
    private String accountId;
    private String userName;
    private String password;
    private String fullName;
    private String picture;
    private String email;
    private boolean gender;
    private Date birthday;
    private String address;
    private String phone;
    private boolean active;
    private Date createDate;

    @DBRef
    private Set<AccountRole> accountRoles = new HashSet<>();
}
