package com.qfedu.labsystem.service;

import com.qfedu.labsystem.pojo.Gadget;

import java.util.List;

public interface GadgetService {
    public List<Gadget> getGadgetList();
    
    void addGadget(Gadget gadget);
    
    void updateGadget(Gadget gadget);
    
    void deleteGadget(Integer id);

    List<Gadget> getGadgetListByLabId(Integer labId);
}
