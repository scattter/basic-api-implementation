package com.thoughtworks.rslist.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.List;

@Data
@Entity
@Table(name = "user")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserEntity {
    @Id
    @GeneratedValue
    private Integer id;
    private String name;
    private String gender;
    private Integer age;
    private String email;
    private String phone;
    private int vote;

    @OneToMany(cascade = CascadeType.REMOVE, mappedBy = "userEntity")
    private List<RsEventEntity> rsEventEntity;
}
