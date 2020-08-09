package com.thoughtworks.rslist.service;

import com.thoughtworks.rslist.domain.User;
import com.thoughtworks.rslist.entity.UserEntity;
import com.thoughtworks.rslist.exception.InvalidRequestParamException;
import com.thoughtworks.rslist.repository.RsEventRepository;
import com.thoughtworks.rslist.repository.UserRepository;
import com.thoughtworks.rslist.repository.VoteRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Configuration
public class UserService {
    final RsEventRepository rsEventRepository;
    final UserRepository userRepository;
    final VoteRepository voteRepository;

    public UserService(RsEventRepository rsEventRepository, UserRepository userRepository, VoteRepository voteRepository) {
        this.rsEventRepository = rsEventRepository;
        this.userRepository = userRepository;
        this.voteRepository = voteRepository;
    }

    @Bean
    public UserService UserService(RsEventRepository rsEventRepository, UserRepository userRepository, VoteRepository voteRepository) {
        return new UserService(rsEventRepository, userRepository, voteRepository);
    }

    @Transactional
    public void register(User user) {
        UserEntity userEntity = UserEntity.builder()
                .name(user.getName())
                .gender(user.getGender())
                .age(user.getAge())
                .email(user.getEmail())
                .phone(user.getPhone())
                .vote(10)
                .build();
        userRepository.save(userEntity);
    }

    public List<UserEntity> getUsers() {
        List<UserEntity> userEntity = userRepository.findAll();
        return userEntity;
    }

    @Transactional
    public void deleteUser(Integer id) {
        if (!userRepository.findById(id).isPresent()) {
            throw new InvalidRequestParamException("invalid request param");
        } else {
            userRepository.deleteById(id);
        }
    }
}
