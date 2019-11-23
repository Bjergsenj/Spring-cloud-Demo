package com.example.userserver.service.iface;


import com.example.userserver.model.Caidan;
import com.example.userserver.service.impl.FeignClientServiceimpl;
import org.springframework.cloud.openfeign.FeignClient;

import java.util.List;

/**
 * 服务提供者
 */
public interface FeignClientService {

    List<Caidan> all();

}