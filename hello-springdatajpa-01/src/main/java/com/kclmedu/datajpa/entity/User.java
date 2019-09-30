package com.kclmedu.datajpa.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Setter
@Getter
@Entity
@Table(name = "TBL_USER")
public class User {

    @Id
    @GenericGenerator(name = "uuidGenerator",strategy = "uuid")
    @GeneratedValue(generator = "uuidGenerator")
    private String id;
    @Column(nullable = false,unique = true)
    private String name;
    @Column(nullable = false)
    private String password;
    @Column(name = "create_date",updatable = false)
    private Date createDate;
    @Column(name="update_date")
    private Date updateDate;
    @Column(columnDefinition = "smallint check (status in(0,1))")
    private int status;  //0代表锁定， 非0代表正常

    //添加关系
    @OneToMany(mappedBy = "user",fetch = FetchType.LAZY)
    private List<Address> addressList;    //一对多

    @ManyToMany
    @JoinTable(name = "TBL_USER_ROLE",joinColumns =
            @JoinColumn(name = "user_id",referencedColumnName = "id"),
        inverseJoinColumns =
            @JoinColumn(name = "role_id", referencedColumnName = "id")
    )
    private Set<Role> roleSet;    //多对多

    //手动去写 hashcode equals 以及 toString 方法

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id) &&
                Objects.equals(name, user.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", createDate=" + createDate +
                ", updateDate=" + updateDate +
                ", status=" + status +
                '}';
    }
}
