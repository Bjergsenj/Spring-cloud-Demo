package com.example.userserver.controller;

import com.alibaba.fastjson.JSONObject;
import com.example.userserver.model.AddressModel;
import com.example.userserver.model.Caidan;
import com.example.userserver.service.iface.FeignClientService;
import com.example.userserver.service.iface.RestCompamyService;
import com.example.userserver.utils.GdUtil;
import com.example.userserver.utils.RequestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

/**
 * 服务提供者
 */
@RestController
public class TestController {


    @Autowired
    private FeignClientService feignClientService;
    @Autowired
    private RestCompamyService restCompamyService;

    @RequestMapping(value = "/caidan", method = RequestMethod.GET)
    public List<Caidan> caidan() {
        return feignClientService.all();
    }

    @RequestMapping(value = "/address", method = RequestMethod.GET)
    @ResponseBody
    public AddressModel address(HttpServletRequest request) {
        String ipAddress = RequestUtils.getIpAddress(request);
        JSONObject s = GdUtil.getAll(ipAddress);
        System.out.println(s.toJSONString());                             //330100
        JSONObject rectangle = GdUtil.coordinateConvert(s.getString("rectangle"));
        System.out.println(rectangle.toJSONString());
        JSONObject regeocode = rectangle.getJSONObject("regeocode");
        String string = regeocode != null ? regeocode.getString("formatted_address") : "定位失败";
        return new AddressModel(ipAddress, string);
    }
    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String add() throws IOException {
        restCompamyService.add();
        return "xxxxxxx";
    }
    @RequestMapping(value = "/search", method = RequestMethod.GET)
    public Object search(){
        return restCompamyService.searchByQuery();
    }
}
