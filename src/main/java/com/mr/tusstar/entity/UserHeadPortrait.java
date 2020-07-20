package com.mr.tusstar.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @author 董帅
 * @date 2020/7/20 - 9:40
 */
@Data
@Entity
@Table(name = "userheadportrait")
public class UserHeadPortrait {
    int id;
    String path;
}
