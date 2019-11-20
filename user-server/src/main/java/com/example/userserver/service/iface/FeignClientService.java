package com.example.userserver.service.iface;


import com.example.userserver.service.impl.FeignClientServiceimpl;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "client-service",fallback = FeignClientServiceimpl.class)
public interface FeignClientService {

    @RequestMapping(value = "/add",method = RequestMethod.GET)
    String add(@RequestParam(value = "userId")String userId, @RequestParam("userName")String userName);

}
