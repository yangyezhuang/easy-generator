package com.easy.generator.model;

import lombok.Data;

/**
 * ColumnClass
 *
 * @author: YZ.YANG
 * @date: 2023-09-16 21:00
 */
@Data
public class ColumnClass {
    /**
     * 对应java属性的名字
     */
    private String propertyName;
    /**
     * 数据库中的名字
     */
    private String columnName;
    /**
     * 字段类型
     */
    private String type;
    /**
     * 备注
     */
    private String remark;
    /**
     * 字段是不是一个主键
     */
    private Boolean isPrimary;

}

