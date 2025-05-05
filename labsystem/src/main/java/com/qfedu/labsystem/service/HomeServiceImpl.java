package com.qfedu.labsystem.service;

import com.qfedu.labsystem.dao.HomeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class HomeServiceImpl implements HomeService {
    @Autowired
    private HomeMapper homeMapper;

    @Override
    public Map<String, Integer> getCardData() {
        Map<String, Integer> result = new HashMap<>();
        result.put("userCount", homeMapper.getUserCount());
        result.put("labCount", homeMapper.getLabCount());
        result.put("gadgetCount", homeMapper.getGadgetCount());
        return result;
    }

    @Override
    public Object getLabBookData() {
        return homeMapper.getLabBookData();
    }
}
