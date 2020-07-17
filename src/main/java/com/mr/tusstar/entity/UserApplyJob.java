package com.mr.tusstar.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @author 董帅
 * @date 2020/7/17 - 15:50
 */
@Data
@Entity
@Table(name = "userapplyjob")
public class UserApplyJob {
    private int id;
    private String phone;
    private String name;
    private String jobName;
    private String companyName;
    private String status;
    private String postTime;
}
