package com.qfedu.labsystem.dao;

import com.qfedu.labsystem.pojo.Gadget;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface GadgetMapper {
    public List<Gadget> getGadgetList();
    
    void addGadget(Gadget gadget);
    
    void updateGadget(Gadget gadget);
    
    void deleteGadget(Integer id);
}
