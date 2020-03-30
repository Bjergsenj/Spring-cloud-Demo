package com.example.userserver.model;

import lombok.Data;

import java.io.Serializable;

/**
 * description:
 * create: 2020/3/30 13:24
 *
 * @author NieMingXin
 * @version 1.0
 */
@Data
public class CompanyInfo implements Serializable {

    private Integer companyId;
    private String companyInfo;
    private String companyName;
    private Integer order;

}
