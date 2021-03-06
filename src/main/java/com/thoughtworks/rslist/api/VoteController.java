package com.thoughtworks.rslist.api;

<<<<<<< HEAD
import com.thoughtworks.rslist.domain.RsEvent;
import com.thoughtworks.rslist.domain.User;
import com.thoughtworks.rslist.domain.Vote;
import com.thoughtworks.rslist.entity.RsEventEntity;
import com.thoughtworks.rslist.entity.UserEntity;
import com.thoughtworks.rslist.entity.VoteEntity;
import com.thoughtworks.rslist.exception.InvlidIndexException;
import com.thoughtworks.rslist.repository.RsEventRepository;
import com.thoughtworks.rslist.repository.UserRepository;
import com.thoughtworks.rslist.repository.VoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@RestController
public class VoteController {

    @Autowired
    VoteRepository voteRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    RsEventRepository rsEventRepository;

=======
import com.thoughtworks.rslist.domain.Vote;
import com.thoughtworks.rslist.entity.RsEventEntity;
import com.thoughtworks.rslist.entity.UserEntity;
import com.thoughtworks.rslist.repository.RsEventRepository;
import com.thoughtworks.rslist.repository.UserRepository;
import com.thoughtworks.rslist.repository.VoteRepository;
import com.thoughtworks.rslist.service.VoteService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class VoteController {

    private final VoteRepository voteRepository;
    private final UserRepository userRepository;
    private final RsEventRepository rsEventRepository;
    private final VoteService voteService;
>>>>>>> jpa-2

    UserEntity userEntity;
    RsEventEntity rsEventEntity;

<<<<<<< HEAD
=======
    public VoteController(VoteRepository voteRepository, UserRepository userRepository, RsEventRepository rsEventRepository, VoteService voteService) {
        this.voteRepository = voteRepository;
        this.userRepository = userRepository;
        this.rsEventRepository = rsEventRepository;
        this.voteService = voteService;
    }

>>>>>>> jpa-2

    void setUp() {
        userEntity = UserEntity.builder().name("idolice").age(19).email("a@b.com").gender("female")
                .phone("18888888888").vote(10).build();
        userEntity = userRepository.save(userEntity);
        rsEventEntity = RsEventEntity.builder()
                .userEntity(userEntity)
                .eventName("event name")
                .eventKeyword("keyword")
                .voteNum(0)
                .build();
        rsEventEntity = rsEventRepository.save(rsEventEntity);
    }

    @PostMapping("/rs/{rsEventId}/vote")
    public ResponseEntity userVote(@PathVariable Integer rsEventId,
                                   @RequestBody Vote voteInfo) {
<<<<<<< HEAD
        // 反序列化问题无时间  所以先把时间加上
        voteInfo.setTime(LocalDateTime.now());
        // 先看user表中是否存在这个用户
        Optional<RsEventEntity> rsEventEntity = rsEventRepository.findById(rsEventId);
        if (!rsEventEntity.isPresent()) {
            System.out.println("无此事件");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        Optional<UserEntity> userEntity = userRepository.findById(voteInfo.getUserId());
        if (!userEntity.isPresent()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } else {
            // 如果存在 查询用户表  获取当前用户剩余票数
            Integer userNowHavePickets = userEntity.get().getVote();
            // 如果投票大于当前用户剩余票数
            if (voteInfo.getVoteNum() > userNowHavePickets) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
            }
            // 更新用户票数  存入user数据库
            userEntity.get().setVote(userNowHavePickets - voteInfo.getVoteNum());
            userRepository.save(userEntity.get());
            // 更新热搜事件里面的热搜票数 && 用户信息  存入event数据库
            rsEventEntity.get().setVoteNum(voteInfo.getVoteNum());
            rsEventEntity.get().setUserEntity(userEntity.get());
            rsEventRepository.save(rsEventEntity.get());
            // 存入vote数据库
            VoteEntity voteEntity = VoteEntity.builder()
                    .user(userEntity.get())
                    .rsEvent(rsEventEntity.get())
                    .localDateTime(LocalDateTime.now())
                    .num(voteInfo.getVoteNum())
                    .build();
            voteRepository.save(voteEntity);
            return ResponseEntity.ok(null);
        }

=======
        voteService.userVote(rsEventId, voteInfo);
        return ResponseEntity.ok(null);
>>>>>>> jpa-2
    }

}
