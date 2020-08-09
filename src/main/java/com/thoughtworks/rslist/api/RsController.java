package com.thoughtworks.rslist.api;

import com.thoughtworks.rslist.domain.RsEvent;
import com.thoughtworks.rslist.entity.RsEventEntity;
import com.thoughtworks.rslist.entity.UserEntity;
import com.thoughtworks.rslist.exception.CommenError;
import com.thoughtworks.rslist.exception.InvalidRequestParamException;
import com.thoughtworks.rslist.exception.InvlidIndexException;
import com.thoughtworks.rslist.repository.RsEventRepository;
import com.thoughtworks.rslist.repository.UserRepository;
import com.thoughtworks.rslist.service.RsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@RestController
@Slf4j
public class RsController {


    private final RsEventRepository rsEventRepository;
    private final UserRepository userRepository;
    private final RsService rsService;

    public RsController(RsEventRepository rsEventRepository, UserRepository userRepository, RsService rsService) {
        this.rsEventRepository = rsEventRepository;
        this.userRepository = userRepository;
        this.rsService = rsService;
    }


    @GetMapping("/rs/{rsEventId}")
    public ResponseEntity getOneRsEvent(@PathVariable Integer rsEventId) throws InvlidIndexException {
        RsEventEntity result = rsService.getOneRsEvent(rsEventId);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/rs/list")
    public ResponseEntity<List<RsEvent>> getRsEventBetween(@RequestParam(required = false) Integer start,
                                                           @RequestParam(required = false) Integer end) throws InvalidRequestParamException {
        List<RsEvent> result = rsService.getRsEventBetween(start, end);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/rs/event")
    public ResponseEntity addRsEvent(@RequestBody @Valid RsEvent rsEvent) {
        rsService.addRsEvent(rsEvent);
        return ResponseEntity.ok(null);
    }

    @PostMapping("/rs/{rsEventId}/update")
    public ResponseEntity updateRsEventWhenUserIdCampareEventId(@PathVariable Integer rsEventId,
                                                                @RequestBody @Valid RsEvent rsEvent) {
        rsService.updateRsEventWhenUserIdCampareEventId(rsEventId,rsEvent);
        return ResponseEntity.ok(null);
    }


    @DeleteMapping("/rs/list/{rsEventId}/delete")
    public ResponseEntity deleteOneRsEvent(@PathVariable Integer rsEventId) {
        rsService.deleteOneRsEvent(rsEventId);
        return ResponseEntity.ok(null);
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
