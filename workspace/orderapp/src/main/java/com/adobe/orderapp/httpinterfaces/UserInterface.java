package com.adobe.orderapp.httpinterfaces;

import com.adobe.orderapp.dto.User;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;

import java.util.List;

@HttpExchange(url = "/users", accept = "application/json", contentType = "application/json")
public interface UserInterface {
    @GetExchange()
    List<User> getUsers();
}
