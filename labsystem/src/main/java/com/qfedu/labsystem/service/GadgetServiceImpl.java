package com.qfedu.labsystem.service;

import com.qfedu.labsystem.dao.GadgetMapper;
import com.qfedu.labsystem.pojo.Gadget;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GadgetServiceImpl implements GadgetService{
    @Autowired
    GadgetMapper gadgetMapper;
    
    @Override
    public List<Gadget> getGadgetList() {
        return gadgetMapper.getGadgetList();
    }
    
    @Override
    public void addGadget(Gadget gadget) {
        gadgetMapper.addGadget(gadget);
    }
    
    @Override
    public void updateGadget(Gadget gadget) {
        gadgetMapper.updateGadget(gadget);
    }
    
    @Override
    public void deleteGadget(Integer id) {
        gadgetMapper.deleteGadget(id);
    }
}
