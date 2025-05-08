package com.qfedu.labsystem.service;

import com.qfedu.labsystem.dao.BookMapper;
import com.qfedu.labsystem.dao.LabMapper;
import com.qfedu.labsystem.pojo.Book;
import com.qfedu.labsystem.pojo.Lab;
import com.qfedu.labsystem.pojo.Gadget;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class LabServiceImpl implements LabService {
    @Autowired
    LabMapper labMapper;

    @Autowired
    GadgetService gadgetService;

    @Autowired
    BookService bookService;

    @Override
    public List<Lab> getLabList(Lab lab) {
        return labMapper.getLabList(lab);
    }

    @Override
    public void addLabList(Lab lab) {
        labMapper.addLabList(lab);
    }

    @Override
    public void updateLabList(Lab lab) {
        labMapper.updateLabList(lab);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteLabList(Integer id) {
        // 检查实验室下是否有设备
        List<Gadget> gadgets = gadgetService.getGadgetListByLabId(id);
        if (gadgets != null && !gadgets.isEmpty()) {
            throw new RuntimeException("该实验室下存在设备，无法删除！");
        }
        // 如果没有设备
        // 1.删除实验室
        labMapper.deleteLabList(id);
    }
}
