package com.mr.tusstar.entity;

import lombok.Data;

import javax.persistence.Entity;

/**
 * @author 董帅
 * @date 2020/7/19 - 15:10
 */
@Data
@Entity
public class Pending {
    private String name;
    private String school;
    private String major;
    private String jobName;
    private String phone;
    private String status;
}
