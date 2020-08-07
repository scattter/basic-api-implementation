package com.thoughtworks.rslist.repository;

import com.thoughtworks.rslist.entity.RsEventEntity;
import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;
import java.util.List;

public interface RsEventRepository extends CrudRepository<RsEventEntity, Integer> {
    List<RsEventEntity> findAll();

    //RsEventEntity updateRsEvent(RsEventEntity rsEventEntity);
    // 最后数据库级联的时候需要把这里删除  才可以
    // @Transactional
    // void deleteAllByUserId(Integer userId);
}
