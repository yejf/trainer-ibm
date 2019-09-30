package com.kclmedu.datajpa.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Objects;


@Setter
@Getter
@Entity
@Table(name = "TBL_ADDRESS")
public class Address {
    @Id
    @GenericGenerator(name = "uuidGenerator",strategy = "uuid")
    @GeneratedValue(generator = "uuidGenerator")
    private String id;

    @Column
    private String state;
    @Column
    private String province;
    @Column
    private String city;
    @Column
    private String detail;
    @Column
    private String receiver;
    @Column
    private String phone;

    //关联关系
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id",nullable = false)
    private User user;  //多对一

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Address address = (Address) o;
        return Objects.equals(id, address.id) &&
                Objects.equals(receiver, address.receiver) &&
                Objects.equals(phone, address.phone);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, receiver, phone);
    }

    @Override
    public String toString() {
        return "Address{" +
                "id='" + id + '\'' +
                ", state='" + state + '\'' +
                ", province='" + province + '\'' +
                ", city='" + city + '\'' +
                ", detail='" + detail + '\'' +
                ", receiver='" + receiver + '\'' +
                ", phone='" + phone + '\'' +
                '}';
    }
}
