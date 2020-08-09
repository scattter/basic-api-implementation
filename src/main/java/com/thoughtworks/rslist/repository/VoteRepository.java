package com.thoughtworks.rslist.repository;

import com.thoughtworks.rslist.entity.VoteEntity;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface VoteRepository  extends PagingAndSortingRepository<VoteEntity, Integer> {
    List<VoteEntity> findAll();
}
