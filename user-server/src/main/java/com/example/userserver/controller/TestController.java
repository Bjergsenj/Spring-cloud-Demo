package com.example.userserver.controller;

import com.example.userserver.service.iface.FeignClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {


    @Autowired
    private FeignClientService feignClientService;

    //现在在使用的feign方式,,
    @RequestMapping(value = "/select",method = RequestMethod.GET)
    public String query(@RequestParam(value = "userId")String userId, @RequestParam("userName")String userName){
        return feignClientService.add(userId,userName);
    }

    @RequestMapping(value = "/test",method = RequestMethod.GET)
    public String test(@RequestParam(value = "userId")String userId){
        return userId+" yes";
    }
}
