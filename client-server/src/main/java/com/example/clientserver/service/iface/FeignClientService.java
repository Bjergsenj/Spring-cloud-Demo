package com.example.clientserver.service.iface;


import com.example.clientserver.service.impl.FeignClientServiceimpl;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "user-service",fallback = FeignClientServiceimpl.class)
public interface FeignClientService {

    @RequestMapping(value = "/test",method = RequestMethod.GET)
    String add(@RequestParam(value = "userId") String userId);
}
