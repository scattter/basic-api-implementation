package com.thoughtworks.rslist.api;

<<<<<<< HEAD
import com.thoughtworks.rslist.domain.RsEvent;
import com.thoughtworks.rslist.domain.User;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
=======
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
>>>>>>> master

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
public class RsController {
<<<<<<< HEAD

    private List<RsEvent> rsList = initRsEvent();

    private List<RsEvent> initRsEvent() {
        List<RsEvent> result = new ArrayList<>();
        User user = new User("xc","male",18,"a@b.com","16666666666");
        result.add(new RsEvent("第一个事件", "无分类",user));
        result.add(new RsEvent("第二个事件", "无分类",user));
        result.add(new RsEvent("第三个事件", "无分类",user));
        return result;
    }

    @GetMapping("/rs/{index}")
    public RsEvent getOneRsEvent(@PathVariable int index) {
        return rsList.get(index - 1);
    }

    @GetMapping("/rs/list")
    public List<RsEvent> getRsEventBetween(@RequestParam int start,
                                           @RequestParam int end) {
        return rsList.subList(start - 1, end);

    }

    @PostMapping("/rs/event")
    public void addRsEvent(@RequestBody @Validated RsEvent rsEvent) {
        rsList.add(rsEvent);
    }

    @PostMapping("/rs/list/modifyName/{index}")
    public void modifyRsEventName(@PathVariable int index,
                                  @RequestBody RsEvent rsEvent) {
        RsEvent newRsEvent = rsList.get(index - 1);

        String newName = rsEvent.getEventName();

        if (!newName.isEmpty()) {
            newRsEvent.setEventName(newName);
        }
    }

    @PostMapping("/rs/list/modifyKeyword/{index}")
    public void modifyRsEventKeyword(@PathVariable int index,
                                     @RequestBody RsEvent rsEvent) {
        RsEvent newRsEvent = rsList.get(index - 1);

        String newKeyword = rsEvent.getEventKeyword();

        if (!newKeyword.isEmpty()) {
            newRsEvent.setEventKeyword(newKeyword);
        }
    }

    @PostMapping("/rs/list/modify/{index}")
    public void modifyRsEventNameAndKeyword(@PathVariable int index,
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
    }

    @DeleteMapping("/rs/list/delete/{index}")
    public void deleteOneRsEvent(@PathVariable int index) {
        rsList.remove(index - 1);
    }
=======
  private List<String> rsList = Arrays.asList("第一条事件", "第二条事件", "第三条事件");

  @GetMapping("/rs/list")
  public String getRsList(){
      return rsList.toString();
  }
>>>>>>> master
}
