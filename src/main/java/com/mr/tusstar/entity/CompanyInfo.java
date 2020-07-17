package com.mr.tusstar.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @author 董帅
 * @date 2020/7/17 - 12:46
 */
@Data
@Entity
@Table(name = "companyinfo")
public class CompanyInfo {
    private int id;
    private String name;
    private String type;
    private String scale;
    private String area;
    private int fund;
    private String industry;
    private String phone;
    private String email;
    private String introduction;
    private String listed;
    private String headQuarters;
    private String website;
    private String registerTime;
}
