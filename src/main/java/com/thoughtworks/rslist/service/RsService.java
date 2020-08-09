package com.thoughtworks.rslist.service;

import com.thoughtworks.rslist.domain.RsEvent;
import com.thoughtworks.rslist.entity.RsEventEntity;
import com.thoughtworks.rslist.entity.UserEntity;
import com.thoughtworks.rslist.exception.CommenError;
import com.thoughtworks.rslist.exception.InvalidRequestParamException;
import com.thoughtworks.rslist.exception.InvlidIndexException;
import com.thoughtworks.rslist.repository.RsEventRepository;
import com.thoughtworks.rslist.repository.UserRepository;
import com.thoughtworks.rslist.repository.VoteRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class RsService {
    final RsEventRepository rsEventRepository;
    final UserRepository userRepository;
    final VoteRepository voteRepository;

    public RsService(RsEventRepository rsEventRepository, UserRepository userRepository, VoteRepository voteRepository) {
        this.rsEventRepository = rsEventRepository;
        this.userRepository = userRepository;
        this.voteRepository = voteRepository;
    }

    public RsEventEntity getOneRsEvent(Integer rsEventId) {
        Optional<RsEventEntity> rsEventEntity = rsEventRepository.findById(rsEventId);
        if (!rsEventEntity.isPresent()) {
            throw new InvlidIndexException("invalid index");
        } else {
            return RsEventEntity.builder()
                    .eventName(rsEventEntity.get().getEventName())
                    .eventKeyword(rsEventEntity.get().getEventKeyword())
                    .eventId(rsEventEntity.get().getEventId())
                    .voteNum(rsEventEntity.get().getVoteNum())
                    .build();
        }
    }

    public List<RsEvent> getRsEventBetween(Integer start, Integer end) throws InvalidRequestParamException {
        List<RsEventEntity> re = rsEventRepository.findAll();
        List<RsEvent> rsEventList = new ArrayList<>();
        if (start == null || end == null) {
            for (int i = 0; i < re.size(); i++) {
                rsEventList.add(new RsEvent(re.get(i).getEventName(), re.get(i).getEventKeyword(), null));
            }
            return rsEventList;
        }
        if (start < 1 || end > rsEventRepository.findAll().size()
                || start > rsEventRepository.findAll().size() || end < 1 || end < start) {
            throw new InvalidRequestParamException("invalid request param");
        } else {
            for (int i = start; i <= end; i++) {
                rsEventList.add(new RsEvent(re.get(i - 1).getEventName(), re.get(i - 1).getEventKeyword(), null));
            }
            return rsEventList;
        }
    }

    public void addRsEvent(RsEvent rsEvent){
        Optional<UserEntity> userEntity = userRepository.findById(rsEvent.getUserId());
        if (!userEntity.isPresent()) {
            throw new InvalidRequestParamException("invalid request param");
        }
        if (rsEvent.getEventName().isEmpty() || rsEvent.getEventKeyword().isEmpty()) {
            throw new InvalidRequestParamException("invalid request param");
        }
        RsEventEntity rsEventEntity = RsEventEntity.builder()
                .eventName(rsEvent.getEventName())
                .eventKeyword(rsEvent.getEventKeyword())
                .userEntity(userEntity.get())
                .build();
        rsEventRepository.save(rsEventEntity);
    }

    public void updateRsEventWhenUserIdCampareEventId(Integer rsEventId,RsEvent rsEvent){
        Optional<UserEntity> userEntity = userRepository.findById(rsEvent.getUserId());
        Optional<RsEventEntity> rsEventEntity = rsEventRepository.findById(rsEventId);
        if (!userEntity.isPresent() || !rsEventEntity.isPresent()) {
            throw new InvalidRequestParamException("invalid request param");
        } else {
            if (!rsEvent.getEventKeyword().isEmpty()) {
                rsEventEntity.get().setEventKeyword(rsEvent.getEventKeyword());
            }
            if (!rsEvent.getEventName().isEmpty()) {
                rsEventEntity.get().setEventName(rsEvent.getEventName());
            }
            rsEventRepository.save(rsEventEntity.get());
        }
    }

    public void deleteOneRsEvent(Integer rsEventId){
        Optional<RsEventEntity> rsEventEntity = rsEventRepository.findById(rsEventId);
        if (!rsEventEntity.isPresent()) {
            throw new InvalidRequestParamException("invalid request param");
        } else {
            rsEventRepository.delete(rsEventEntity.get());
        }
    }

}
