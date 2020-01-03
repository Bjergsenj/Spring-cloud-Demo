package com.example.userserver.model;

import lombok.Data;

/**
 * description: TODO
 * create: 2020/1/3 16:08
 *
 * @author niemingxin
 */
@Data
public class Tests {

    private Long id;

    private String name;

    @Override
    public String toString() {
        return "Tests [id=" + id + ", name=" + name + "]";
    }

}
