package com.mr.tusstar.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @author 董帅
 * @date 2020/7/16 - 23:43
 */
@Data
@Entity
@Table(name = "resume")
public class Resume {
    private int id;
    private String name;
    private String degree;
    private String birth;
    private String sex;
    private String nation;
    private String introduction;
    private String address;
    private String phone;
    private String email;
    private String school;
    private String department;
    private String major;
    private String educationalSystem;
    private String timeOfEnrollment;
    private String overHeadInfo;
    private int salary;
    private String internInfo;
}
