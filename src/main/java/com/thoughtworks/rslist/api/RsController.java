package com.thoughtworks.rslist.api;

import com.thoughtworks.rslist.domain.RsEvent;
import com.thoughtworks.rslist.domain.User;
import com.thoughtworks.rslist.entity.RsEventEntity;
import com.thoughtworks.rslist.exception.CommenError;
import com.thoughtworks.rslist.exception.InvalidRequestParamException;
import com.thoughtworks.rslist.exception.InvlidIndexException;
import com.thoughtworks.rslist.repository.RsEventRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;


@RestController
@Slf4j
public class RsController {


    private final RsEventRepository rsEventRepository;

    public RsController(RsEventRepository rsEventRepository) {
        this.rsEventRepository = rsEventRepository;
    }


    private List<RsEvent> rsList = initRsEvent();

    private List<RsEvent> initRsEvent() {
        List<RsEvent> result = new ArrayList<>();
        User user = new User("xc", "male", 18, "a@b.com", "16666666666");
        result.add(new RsEvent("第一个事件", "无分类", 1));
        result.add(new RsEvent("第二个事件", "无分类", 2));
        result.add(new RsEvent("第三个事件", "无分类", 3));
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
        RsEventEntity rsEventEntity = RsEventEntity.builder().eventName(rsEvent.getEventName())
                .eventKeyword(rsEvent.getEventKeyword()).userId(rsEvent.getUserId()).build();
        rsEventRepository.save(rsEventEntity);
        Integer index = rsList.size();
        return ResponseEntity.ok(null);
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
