package com.qfedu.labsystem.pojo;

import lombok.Data;

import java.util.Date;

@Data
public class Bookgadget {
    private Integer id;
    private Integer gadget_id;
    private Date bookgadget_time;
    private Integer bookgadget_class;
    private String bookgadget_username;
    private String bookgadget_reason;
    private Integer bookgadget_state;

    private Gadget gadget;
}
