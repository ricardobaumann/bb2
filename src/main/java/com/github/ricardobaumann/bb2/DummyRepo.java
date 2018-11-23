package com.github.ricardobaumann.bb2;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(url = "http://localhost:8080/dummy", name = "dummy")
public interface DummyRepo {

    @RequestMapping(method = RequestMethod.GET)
    MyDummy2 get();

}
