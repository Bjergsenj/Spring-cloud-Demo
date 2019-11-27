package com.example.clientserver.controller;

import com.example.clientserver.service.iface.FeignClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ClientController {

    @RequestMapping(value = "add", method = RequestMethod.GET)
    public String service(@RequestParam(value = "userId") String userId, @RequestParam(value = "userName") String userName) {
        System.out.println("执行了这里.");
        return "操作成功  userId为" + userId + "   userName为" + userName;
    }


    @Autowired
    FeignClientService feignClientService;

    @RequestMapping(value = "test", method = RequestMethod.GET)
    public String test(@RequestParam(value = "userId") String userId) {
        return feignClientService.add(userId);
    }
}
