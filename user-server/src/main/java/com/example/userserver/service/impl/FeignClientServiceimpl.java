package com.example.userserver.service.impl;

import com.example.userserver.mapper.CaidanMapper;
import com.example.userserver.model.Caidan;
import com.example.userserver.model.CaidanExample;
import com.example.userserver.service.iface.FeignClientService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class FeignClientServiceimpl  implements FeignClientService {
    @Autowired
    private CaidanMapper caidanMapper;


    @Override
    public List<Caidan> all() {
        CaidanExample caidanExample = new CaidanExample();
        List<Caidan> caidans = caidanMapper.selectByExample(caidanExample);
        log.info("xxxxxxxxx");
        List<Caidan> deptTree = getChildrenTree(caidans, "0");
        return deptTree;
    }


    private List<Caidan> getChildrenTree(List<Caidan> all,String pid){
        List<Caidan> result =  new ArrayList<Caidan>();
        List<Caidan> children =  new ArrayList<Caidan>();
        for(Caidan caidan : all){
            if(caidan.getParentId().equals(pid)){
                Caidan deptScopeMode = new Caidan();
                deptScopeMode.setId(caidan.getId());
                deptScopeMode.setName(caidan.getName());
                deptScopeMode.setParentId(caidan.getParentId());
                children = getChildrenTree(all,caidan.getId());
                if(children.size() > 0){
                    deptScopeMode.setChildren(children);
                }
                result.add(deptScopeMode);
            }
        }
        return result;

    }
}
