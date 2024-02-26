package com.adobe.orderapp.api;

import org.springframework.data.rest.webmvc.BasePathAwareController;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@BasePathAwareController
public class HelloController {
    @RequestMapping(path = "test", method = RequestMethod.GET)
    public @ResponseBody  String sayHello() {
        System.out.println("Hello!!!");
        return "Hello World!!!";
    }
}
