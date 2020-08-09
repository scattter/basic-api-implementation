package com.thoughtworks.rslist.service;

import com.thoughtworks.rslist.domain.Vote;
import com.thoughtworks.rslist.entity.RsEventEntity;
import com.thoughtworks.rslist.entity.UserEntity;
import com.thoughtworks.rslist.entity.VoteEntity;
import com.thoughtworks.rslist.exception.InvalidRequestParamException;
import com.thoughtworks.rslist.repository.RsEventRepository;
import com.thoughtworks.rslist.repository.UserRepository;
import com.thoughtworks.rslist.repository.VoteRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Optional;

@Configuration
public class VoteService {
    final RsEventRepository rsEventRepository;
    final UserRepository userRepository;
    final VoteRepository voteRepository;

    public VoteService(RsEventRepository rsEventRepository, UserRepository userRepository, VoteRepository voteRepository) {
        this.rsEventRepository = rsEventRepository;
        this.userRepository = userRepository;
        this.voteRepository = voteRepository;
    }

    @Bean
    public VoteService VoteService(RsEventRepository rsEventRepository, UserRepository userRepository, VoteRepository voteRepository) {
        return new VoteService(rsEventRepository, userRepository, voteRepository);
    }

    @Transactional
    public void userVote(Integer rsEventId, Vote voteInfo) {
        voteInfo.setTime(LocalDateTime.now());
        Optional<RsEventEntity> rsEventEntity = rsEventRepository.findById(rsEventId);
        if (!rsEventEntity.isPresent()) {
            throw new InvalidRequestParamException("invalid request param");
        }
        Optional<UserEntity> userEntity = userRepository.findById(voteInfo.getUserId());
        if (!userEntity.isPresent()) {
            throw new InvalidRequestParamException("invalid request param");
        } else {
            Integer userNowHavePickets = userEntity.get().getVote();
            if (voteInfo.getVoteNum() > userNowHavePickets) {
                throw new InvalidRequestParamException("invalid request param");
            }
            userEntity.get().setVote(userNowHavePickets - voteInfo.getVoteNum());
            userRepository.save(userEntity.get());
            rsEventEntity.get().setVoteNum(voteInfo.getVoteNum());
            rsEventEntity.get().setUserEntity(userEntity.get());
            rsEventRepository.save(rsEventEntity.get());
            VoteEntity voteEntity = VoteEntity.builder()
                    .user(userEntity.get())
                    .rsEvent(rsEventEntity.get())
                    .localDateTime(LocalDateTime.now())
                    .num(voteInfo.getVoteNum())
                    .build();
            voteRepository.save(voteEntity);
        }
    }
}
