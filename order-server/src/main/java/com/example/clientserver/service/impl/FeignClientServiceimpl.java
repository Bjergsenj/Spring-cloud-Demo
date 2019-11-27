package com.example.clientserver.service.impl;

import com.example.clientserver.service.iface.FeignClientService;
import org.springframework.stereotype.Component;

@Component
public class FeignClientServiceimpl  implements FeignClientService {
    @Override
    public String add(String userId) {
        return "Hystrix fallback ...";
    }
}
