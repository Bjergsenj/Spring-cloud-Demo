package com.example.userserver.service.impl;

import com.example.userserver.service.iface.FeignClientService;
import org.springframework.stereotype.Component;

@Component
public class FeignClientServiceimpl  implements FeignClientService {
    @Override
    public String add(String userId, String userName) {
        return "Hystrix fallback ...";
    }
}
