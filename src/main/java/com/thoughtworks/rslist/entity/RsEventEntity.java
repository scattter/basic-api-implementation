package com.thoughtworks.rslist.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "event")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RsEventEntity {
    @Id
    @GeneratedValue
    private Integer id;
    private String eventName;
    private String eventKeyword;
    private Integer userId;
}
