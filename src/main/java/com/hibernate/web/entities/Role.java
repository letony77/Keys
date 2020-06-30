package com.hibernate.web.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;

//@Entity
public class Role implements Serializable {
    @Id @GeneratedValue
    private Long role_id;
    private String name_role;
    @OneToMany(mappedBy = "role", fetch = FetchType.LAZY)
    private Collection<User> users;



    public Long getRole_id() {
        return role_id;
    }

    public void setRole_id(Long role_id) {
        this.role_id = role_id;
    }

    public String getName_role() {
        return name_role;
    }

    public void setName_role(String name_role) {
        this.name_role = name_role;
    }


    public Collection<User> getUsers() {
        return users;
    }

    public void setUsers(Collection<User> users) {
        this.users = users;
    }

    public Role(){
        super();
    }
    public Role(String name_role) {
        super();
        this.name_role = name_role;
    }
}
