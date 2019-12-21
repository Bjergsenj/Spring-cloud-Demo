package com.example.order.service.iface;


import com.example.order.service.impl.FeignClientServiceimpl;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(value = "user-service",fallback = FeignClientServiceimpl.class)
public interface FeignClientService {


    @RequestMapping(value = "/caidan",method = RequestMethod.GET)
    String cairdan();
}
