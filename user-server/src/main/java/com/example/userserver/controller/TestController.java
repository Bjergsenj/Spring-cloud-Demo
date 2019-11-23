package com.example.userserver.controller;

import com.example.userserver.model.Caidan;
import com.example.userserver.service.iface.FeignClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
/**
 * 服务提供者
 */
public class TestController {


    @Autowired
    private FeignClientService feignClientService;

    @RequestMapping(value = "/caidan",method = RequestMethod.GET)
    public List<Caidan> caidan(){
        return feignClientService.all();
    }


}
