package com.thoughtworks.rslist.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@Table(name = "event")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RsEventEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Integer eventId;
    private String eventName;
    private String eventKeyword;
    private Integer voteNum;
    //private Integer userId;

    @ManyToOne
    private UserEntity userEntity;
}
