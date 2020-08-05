package com.thoughtworks.rslist.api;

import com.fasterxml.jackson.annotation.JsonView;
import com.thoughtworks.rslist.domain.RsEvent;
import com.thoughtworks.rslist.domain.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
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

    @GetMapping("/rs/{index}")
    public ResponseEntity<RsEvent> getOneRsEvent(@PathVariable Integer index) {
        return ResponseEntity.ok(rsList.get(index - 1));
    }

    @GetMapping("/rs/list")
    public ResponseEntity<List<RsEvent>> getRsEventBetween(@RequestParam(required = false) Integer start,
                                                           @RequestParam(required = false) Integer end) {
        if (start == null || end == null) {
            return ResponseEntity.ok(rsList);
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
}
