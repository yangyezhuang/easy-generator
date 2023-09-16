package com.easy.generator.model;

import lombok.Data;

import java.util.List;

/**
 * TableClass
 *
 * @author: YZ.YANG
 * @date: 2023-09-16 21:00
 */
@Data
public class TableClass {
    /**
     * 表名
     */
    private String tableName;
    /**
     * model文件名
     */
    private String modelName;
    /**
     * service文件名
     */
    private String serviceName;
    /**
     * mapper文件名
     */
    private String mapperName;
    /**
     * controller文件名
     */
    private String controllerName;
    /**
     * 包名
     */
    private String packageName;
    /**
     * 字段
     */
    private List<ColumnClass> columns;

}

