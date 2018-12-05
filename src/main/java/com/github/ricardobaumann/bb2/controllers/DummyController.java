package com.github.ricardobaumann.bb2.controllers;

import com.github.ricardobaumann.bb2.MyDummy;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

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

}
