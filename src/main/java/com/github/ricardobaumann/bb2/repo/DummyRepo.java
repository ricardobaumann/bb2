package com.github.ricardobaumann.bb2.repo;

import com.github.ricardobaumann.bb2.MyDummy2;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Optional;

@FeignClient(url = "http://localhost:8080/dummy", name = "dummy", decode404 = true)
public interface DummyRepo {

    @RequestMapping(method = RequestMethod.GET)
    Optional<MyDummy2> get();

}
