package com.github.ricardobaumann.bb2.controllers;

import com.github.ricardobaumann.bb2.MyDummy;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Map;

@RestController
public class DummyController {

    @GetMapping("/dummy")
    ResponseEntity<MyDummy> get() {
        return ResponseEntity.ok(new MyDummy());
    }

    @PostMapping("/dummy")
    MyDummy post(MyDummy myDummy) {
        return myDummy;
    }

    @RequestMapping(value = "/customers/{customerId}/ads/{adId}/features/{feature}",
            method = RequestMethod.DELETE)
    Map<String, Object> unbook(@PathVariable("customerId") Long customerId, @PathVariable("adId") Long adId, @PathVariable("feature") String feature,
                               @RequestHeader(value = "X-Mobile-Initiator", required = false) String header) {

        return Collections.singletonMap("status", "ok");

    }

}
