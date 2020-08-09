package com.thoughtworks.rslist.api;

import com.thoughtworks.rslist.domain.Vote;
import com.thoughtworks.rslist.entity.RsEventEntity;
import com.thoughtworks.rslist.entity.UserEntity;
import com.thoughtworks.rslist.repository.RsEventRepository;
import com.thoughtworks.rslist.repository.UserRepository;
import com.thoughtworks.rslist.repository.VoteRepository;
import com.thoughtworks.rslist.service.VoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;

@RestController
@ControllerAdvice
public class VoteController {


    @Autowired
    RsEventRepository rsEventRepository;
    @Autowired
    UserRepository userRepository;

    private final VoteService voteService;

    UserEntity userEntity;
    RsEventEntity rsEventEntity;

    public VoteController(VoteService voteService) {
        this.voteService = voteService;
    }


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
        voteService.userVote(rsEventId, voteInfo);
        return ResponseEntity.ok(null);
    }

}
