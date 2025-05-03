package com.qfedu.labsystem.service;

import com.qfedu.labsystem.pojo.Right;

import java.util.List;

public interface RightService {
    public List<Right> getRightList();

    void updateRightlist(Right right);

    void deleteRightList(Integer id);
}
