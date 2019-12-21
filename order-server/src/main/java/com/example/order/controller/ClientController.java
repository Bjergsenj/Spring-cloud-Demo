package com.example.order.controller;

import com.example.order.service.iface.FeignClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ClientController {

    @Autowired
    FeignClientService feignClientService;

    @RequestMapping(value = "test", method = RequestMethod.GET)
    public String test() {
        return feignClientService.cairdan();
    }
}
