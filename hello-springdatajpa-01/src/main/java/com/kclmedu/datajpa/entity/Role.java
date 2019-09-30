package com.kclmedu.datajpa.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name="TBL_ROLE")
public class Role {

    @Id
    @GenericGenerator(name = "uuidGenerator",strategy = "uuid")
    @GeneratedValue(generator = "uuidGenerator")
    private String id;

    @Column
    private String name;

    @Column
    private String description;

    //表示与 User的多对多关系

    @ManyToMany(mappedBy = "roleSet")
    private Set<User> userSet;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Role role = (Role) o;
        return Objects.equals(id, role.id) &&
                Objects.equals(name, role.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

    @Override
    public String toString() {
        return "Role{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
