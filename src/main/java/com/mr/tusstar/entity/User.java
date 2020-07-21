package com.mr.tusstar.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @author 董帅
 * @date 2020/7/15 - 10:16
 */
@Data
@Entity
@Table(name = "user")
public class User {
    private int id;
    private String phone;
    private String name;
    private String email;
    private String password;
    private String registerTime;
    private String salt;
}
