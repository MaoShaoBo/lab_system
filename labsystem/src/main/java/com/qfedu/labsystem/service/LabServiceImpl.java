package com.qfedu.labsystem.service;

import com.qfedu.labsystem.dao.LabMapper;
import com.qfedu.labsystem.pojo.Lab;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class LabServiceImpl implements LabService{
    @Autowired
    LabMapper labMapper;
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

    @Override
    public void deleteLabList(Integer id) {
        labMapper.deleteLabList(id);
    }
}
