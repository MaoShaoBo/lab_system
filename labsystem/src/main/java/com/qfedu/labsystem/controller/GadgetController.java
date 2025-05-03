package com.qfedu.labsystem.controller;

import com.qfedu.labsystem.pojo.Gadget;
import com.qfedu.labsystem.service.GadgetService;
import com.qfedu.labsystem.utils.ResultOBJ;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/adminapi/gadget")
public class GadgetController {
    @Autowired
    GadgetService gadgetService;

    @GetMapping
    public List<Gadget> getGadgetList(){
        return gadgetService.getGadgetList();
    }

    @PostMapping
    public ResultOBJ addGadget(@RequestBody Gadget gadget) {
        try {
            gadgetService.addGadget(gadget);
            return ResultOBJ.ADD_SUCCESS;
        } catch (Exception e) {
            return ResultOBJ.ADD_ERROR;
        }
    }

    @PutMapping("/{id}")
    public ResultOBJ updateGadget(@PathVariable Integer id, @RequestBody Gadget gadget) {
        try {
            gadget.setId(id);
            gadgetService.updateGadget(gadget);
            return ResultOBJ.UPDATE_SUCCESS;
        } catch (Exception e) {
            return ResultOBJ.UPDATE_ERROR;
        }
    }

    @DeleteMapping("/{id}")
    public ResultOBJ deleteGadget(@PathVariable Integer id) {
        try {
            gadgetService.deleteGadget(id);
            return ResultOBJ.DELETE_SUCCESS;
        } catch (Exception e) {
            return ResultOBJ.DELETE_ERROR;
        }
    }
}
