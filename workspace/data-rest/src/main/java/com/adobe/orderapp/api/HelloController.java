package com.adobe.orderapp.api;

import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@RepositoryRestController

public class HelloController {
    @RequestMapping(path = "hello", method = RequestMethod.GET)
    public String sayHello() {
        return "Hello World!!!";
    }
}
