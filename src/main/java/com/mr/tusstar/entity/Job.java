package com.mr.tusstar.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @author 董帅
 * @date 2020/7/16 - 19:40
 */
@Data
@Entity
@Table(name = "job")
public class Job {
    private int id;
    private String name;
    private String jobName;
    private String nature;
    private String type;
    private String workLocation;
    private int salary;
    private String degree;
    private String experience;
    private String email;
    private String contactPhone;
    private String contactName;
    private int recruitingNumbers;
    private String jobWelfare;
    private String jobDesc;
    private String jobContent;
    private String postTime;
}
