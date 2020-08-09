package com.thoughtworks.rslist.api;

<<<<<<< HEAD
import com.fasterxml.jackson.annotation.JsonView;
import com.thoughtworks.rslist.domain.RsEvent;
import com.thoughtworks.rslist.domain.User;
import com.thoughtworks.rslist.exception.CommenError;
import com.thoughtworks.rslist.exception.InvalidRequestParamException;
import com.thoughtworks.rslist.exception.InvlidIndexException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
=======
import com.thoughtworks.rslist.domain.RsEvent;
import com.thoughtworks.rslist.domain.User;
import com.thoughtworks.rslist.entity.RsEventEntity;
import com.thoughtworks.rslist.entity.UserEntity;
import com.thoughtworks.rslist.exception.CommenError;
import com.thoughtworks.rslist.exception.InvalidRequestParamException;
import com.thoughtworks.rslist.exception.InvlidIndexException;
import com.thoughtworks.rslist.repository.RsEventRepository;
import com.thoughtworks.rslist.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
>>>>>>> jpa-2
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.DoubleStream;


import static jdk.nashorn.internal.runtime.regexp.joni.Config.log;

@RestController
@Slf4j
public class RsController {

<<<<<<< HEAD
    private List<RsEvent> rsList = initRsEvent();

    private List<RsEvent> initRsEvent() {
        List<RsEvent> result = new ArrayList<>();
        User user = new User("xc", "male", 18, "a@b.com", "16666666666");
        result.add(new RsEvent("第一个事件", "无分类", user));
        result.add(new RsEvent("第二个事件", "无分类", user));
        result.add(new RsEvent("第三个事件", "无分类", user));
        return result;
    }

    @GetMapping("/rs/{index}")  //
    public ResponseEntity<RsEvent> getOneRsEvent(@PathVariable Integer index) throws InvlidIndexException {

        if (index > rsList.size()) {
            throw new InvlidIndexException("invalid index");
        }
        return ResponseEntity.ok(rsList.get(index - 1));
    }

    @GetMapping("/rs/list")
    //@JsonView(RsEvent.PublicView.class)
    public ResponseEntity<List<RsEvent>> getRsEventBetween(@RequestParam(required = false) Integer start,
                                                           @RequestParam(required = false) Integer end) throws InvalidRequestParamException {
        if (start == null || end == null) {
            return ResponseEntity.ok(rsList);
        }
        if (start < 1 || end > rsList.size()) {
            throw new InvalidRequestParamException("invalid request param");
        }
        return ResponseEntity.ok(rsList.subList(start - 1, end));

    }

    @PostMapping("/rs/event")
    // @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity addRsEvent(@RequestBody @Valid RsEvent rsEvent) {
        rsList.add(rsEvent);
        Integer index = rsList.size();
        return ResponseEntity.created(null).header("index", index.toString()).build();
    }

    @PostMapping("/rs/list/modifyName/{index}")
    public ResponseEntity modifyRsEventName(@PathVariable Integer index,
                                            @RequestBody RsEvent rsEvent) {
        RsEvent newRsEvent = rsList.get(index - 1);
        String newName = rsEvent.getEventName();
        if (!newName.isEmpty()) {
            newRsEvent.setEventName(newName);
        }
        index -= 1;
        return ResponseEntity.created(null).header("index", index.toString()).build();
    }

    @PostMapping("/rs/list/modifyKeyword/{index}")
    public ResponseEntity modifyRsEventKeyword(@PathVariable Integer index,
                                               @RequestBody RsEvent rsEvent) {
        RsEvent newRsEvent = rsList.get(index - 1);
        String newKeyword = rsEvent.getEventKeyword();
        if (!newKeyword.isEmpty()) {
            newRsEvent.setEventKeyword(newKeyword);
        }
        index -= 1;
        return ResponseEntity.created(null).header("index", index.toString()).build();
    }

    @PostMapping("/rs/list/modify/{index}")
    public ResponseEntity modifyRsEventNameAndKeyword(@PathVariable Integer index,
                                                      @RequestBody RsEvent rsEvent) {
        RsEvent newRsEvent = rsList.get(index - 1);
        String newName = rsEvent.getEventName();
        String newKeyword = rsEvent.getEventKeyword();
        if (!newName.isEmpty()) {
            newRsEvent.setEventName(newName);
        }
        if (!newKeyword.isEmpty()) {
            newRsEvent.setEventKeyword(newKeyword);
        }
        index -= 1;
        return ResponseEntity.created(null).header("index", index.toString()).build();
    }

    @DeleteMapping("/rs/list/delete/{index}")
    public ResponseEntity deleteOneRsEvent(@PathVariable Integer index) {
        rsList.remove(index - 1);
        return ResponseEntity.ok(null);
=======

    private final RsEventRepository rsEventRepository;
    private final UserRepository userRepository;

    public RsController(RsEventRepository rsEventRepository, UserRepository userRepository) {
        this.rsEventRepository = rsEventRepository;
        this.userRepository = userRepository;
    }


    @GetMapping("/rs/{rsEventId}")
    public ResponseEntity<RsEventEntity> getOneRsEvent(@PathVariable Integer rsEventId) throws InvlidIndexException {
        Optional<RsEventEntity> rsEventEntity = rsEventRepository.findById(rsEventId);
        if (!rsEventEntity.isPresent()) {
            throw new InvlidIndexException("invalid index");
        } else {
            return ResponseEntity.ok(RsEventEntity.builder()
                    .eventName(rsEventEntity.get().getEventName())
                    .eventKeyword(rsEventEntity.get().getEventKeyword())
                    .eventId(rsEventEntity.get().getEventId())
                    .voteNum(rsEventEntity.get().getVoteNum())
                    .build());
        }
    }

    @GetMapping("/rs/list")
    public ResponseEntity<List<RsEvent>> getRsEventBetween(@RequestParam(required = false) Integer start,
                                                           @RequestParam(required = false) Integer end) throws InvalidRequestParamException {
        List<RsEventEntity> re = rsEventRepository.findAll();
        List<RsEvent> result = new ArrayList<>();
        if (start == null || end == null) {
            for (int i = 0; i < re.size(); i++) {
                result.add(new RsEvent(re.get(i - 1).getEventName(), re.get(i - 1).getEventKeyword(), null));
            }
            return ResponseEntity.ok(result);
        }
        if (start < 1 || end > rsEventRepository.findAll().size()
                || start > rsEventRepository.findAll().size() || end < 1 || end < start) {
            throw new InvalidRequestParamException("invalid request param");
        } else {
            for (int i = start; i <= end; i++) {
                result.add(new RsEvent(re.get(i - 1).getEventName(), re.get(i - 1).getEventKeyword(), null));
            }
            return ResponseEntity.ok(result);
        }
    }

    @PostMapping("/rs/event")
    public ResponseEntity addRsEvent(@RequestBody @Valid RsEvent rsEvent) {
        Optional<UserEntity> userEntity = userRepository.findById(rsEvent.getUserId());
        if (!userEntity.isPresent()) {
            return ResponseEntity.badRequest().build();
        }
        if (rsEvent.getEventName().isEmpty() || rsEvent.getEventKeyword().isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        RsEventEntity rsEventEntity = RsEventEntity.builder()
                .eventName(rsEvent.getEventName())
                .eventKeyword(rsEvent.getEventKeyword())
                .userEntity(userEntity.get())
                .build();
        rsEventRepository.save(rsEventEntity);
        return ResponseEntity.ok(null);
    }

    @PostMapping("/rs/{rsEventId}/update")
    public ResponseEntity updateRsEventWhenUserIdCampareEventId(@PathVariable Integer rsEventId,
                                                                @RequestBody @Valid RsEvent rsEvent) {
        Optional<UserEntity> userEntity = userRepository.findById(rsEvent.getUserId());
        Optional<RsEventEntity> rsEventEntity = rsEventRepository.findById(rsEventId);
        if (!userEntity.isPresent() || !rsEventEntity.isPresent()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } else {
            if (!rsEvent.getEventKeyword().isEmpty()) {
                rsEventEntity.get().setEventKeyword(rsEvent.getEventKeyword());
            }
            if (!rsEvent.getEventName().isEmpty()) {
                rsEventEntity.get().setEventName(rsEvent.getEventName());
            }
            rsEventRepository.save(rsEventEntity.get());
            return ResponseEntity.ok(null);
        }

    }


    @DeleteMapping("/rs/list/{rsEventId}/delete")
    public ResponseEntity deleteOneRsEvent(@PathVariable Integer rsEventId) {
        Optional<RsEventEntity> rsEventEntity = rsEventRepository.findById(rsEventId);
        if (!rsEventEntity.isPresent()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } else {
            rsEventRepository.delete(rsEventEntity.get());
            return ResponseEntity.ok(null);
        }
>>>>>>> jpa-2
    }

    @ExceptionHandler({InvlidIndexException.class,
            MethodArgumentNotValidException.class,
            InvalidRequestParamException.class})
    public ResponseEntity exceptionHandler(Exception error) {
        String message;
        CommenError commenError = new CommenError();

        log.error("{}.error :  {}", error.getClass(), error.getMessage());
        if (error instanceof MethodArgumentNotValidException) {
            message = "invalid param";
        } else {
            message = error.getMessage();
        }
        commenError.setError(message);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(commenError);
    }

}
