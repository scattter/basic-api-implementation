package com.thoughtworks.rslist.api;

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

    UserEntity userEntity;
    RsEventEntity rsEventEntity;

    public VoteController(VoteRepository voteRepository, UserRepository userRepository, RsEventRepository rsEventRepository, VoteService voteService) {
        this.voteRepository = voteRepository;
        this.userRepository = userRepository;
        this.rsEventRepository = rsEventRepository;
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
