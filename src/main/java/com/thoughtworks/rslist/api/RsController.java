package com.thoughtworks.rslist.api;

import com.fasterxml.jackson.annotation.JsonView;
import com.thoughtworks.rslist.domain.RsEvent;
import com.thoughtworks.rslist.domain.User;
import com.thoughtworks.rslist.exception.CommenError;
import com.thoughtworks.rslist.exception.InvalidRequestParamException;
import com.thoughtworks.rslist.exception.InvlidIndexException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
public class RsController {

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

        if (index > rsList.size()){
            throw new InvlidIndexException("invalid index");
        }
        return ResponseEntity.ok(rsList.get(index - 1));
    }

    @GetMapping("/rs/list")
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
    }

    @ExceptionHandler({InvlidIndexException.class,MethodArgumentNotValidException.class})
    public ResponseEntity exceptionHandler(Exception error){
        String message;
        CommenError commenError = new CommenError();
        if (error instanceof MethodArgumentNotValidException){
            message = "invalid param";
        }else {
            message = "invalid index";
        }
        commenError.setError(message);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(commenError);
    }

    @ExceptionHandler(InvalidRequestParamException.class)
    public ResponseEntity exceptionHandler(InvalidRequestParamException error){
        CommenError commenError = new CommenError();
        commenError.setError(error.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(commenError);
    }
}
