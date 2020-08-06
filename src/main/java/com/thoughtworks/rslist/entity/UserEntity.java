package com.thoughtworks.rslist.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.*;

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
}
