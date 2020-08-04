package com.thoughtworks.rslist.api;

import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class RsController {
    private List<String> rsList = Arrays.asList("第一条事件", "第二条事件", "第三条事件").stream().collect(Collectors.toList());


    @GetMapping("/rs/{index}")
    public String getOneRsEvent(@PathVariable int index) {
        return rsList.get(index - 1);
    }

    @GetMapping("/rs/list")
    public String getRsEventBetween(@RequestParam(required = false) Integer start,
                                    @RequestParam(required = false) Integer end) {
        if (start == null || end == null) {
            return rsList.toString();
        }
        return rsList.subList(start - 1, end).toString();
    }

    @PostMapping("/rs/event")
    public boolean addRsEvent(@RequestBody String rsEvent) {
        return rsList.add(rsEvent);
    }
}
