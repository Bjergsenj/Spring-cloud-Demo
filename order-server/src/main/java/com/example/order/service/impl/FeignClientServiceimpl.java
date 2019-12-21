package com.example.order.service.impl;

import com.example.order.service.iface.FeignClientService;
import org.springframework.stereotype.Component;

@Component
public class FeignClientServiceimpl  implements FeignClientService {

    @Override
    public String cairdan() {
        return "Hystrix fallback ...";
    }
}
