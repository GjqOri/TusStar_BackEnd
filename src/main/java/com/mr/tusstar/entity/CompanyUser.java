package com.mr.tusstar.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @author 董帅
 * @date 2020/7/16 - 11:54
 */
@Data
@Entity
@Table(name = "companyuser")
public class CompanyUser {
    private int id;
    private String email;
    private String password;
}
