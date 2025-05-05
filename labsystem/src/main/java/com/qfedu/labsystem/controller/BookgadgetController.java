package com.qfedu.labsystem.controller;

import com.qfedu.labsystem.pojo.Bookgadget;
import com.qfedu.labsystem.service.BookgadgetService;
import com.qfedu.labsystem.utils.ResultOBJ;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/adminapi/bookgadget")
public class BookgadgetController {
    @Autowired
    private BookgadgetService bookgadgetService;

    @PostMapping
    public ResultOBJ addBookgadget(@RequestBody Bookgadget bookgadget) {
        try {
            bookgadgetService.addBookgadget(bookgadget);
            return ResultOBJ.ADD_SUCCESS;
        } catch (Exception e) {
            return ResultOBJ.ADD_ERROR;
        }
    }

    @PostMapping("/select")
    public List<Bookgadget> getBookgadgetList(@RequestBody Bookgadget bookgadget) {
        return bookgadgetService.getBookgadgetList(bookgadget);
    }

    @GetMapping
    public List<Bookgadget> getBookgadgetListByUsername(@RequestParam(required = false) String bookgadget_username,
                                                      @RequestParam(required = false) Integer bookgadget_state) {
        return bookgadgetService.getBookgadgetListByUsername(bookgadget_username, bookgadget_state);
    }

    @DeleteMapping("/{id}")
    public ResultOBJ deleteBookgadget(@PathVariable Integer id) {
        try {
            bookgadgetService.deleteBookgadget(id);
            return ResultOBJ.DELETE_SUCCESS;
        } catch (Exception e) {
            return ResultOBJ.DELETE_ERROR;
        }
    }

    @PutMapping("/{id}")
    public ResultOBJ updateBookgadget(@PathVariable Integer id, @RequestBody Bookgadget bookgadget) {
        try {
            bookgadget.setId(id);
            bookgadgetService.updateBookgadget(bookgadget);
            return ResultOBJ.UPDATE_SUCCESS;
        } catch (Exception e) {
            return ResultOBJ.UPDATE_ERROR;
        }
    }
}
