package com.easy.generator.service;


import com.easy.generator.model.ColumnClass;
import com.easy.generator.model.RespBean;
import com.easy.generator.model.TableClass;
import com.easy.generator.utils.DBUtils;
import com.google.common.base.CaseFormat;
import freemarker.cache.ClassTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 * GenerateCodeService
 *
 * @author: YZ.YANG
 * @date: 2023-09-16 21:07
 */
@Service
public class GenerateCodeService {

    public static Configuration cfg = null;

    static {
        cfg = new Configuration(Configuration.VERSION_2_3_30);
        cfg.setTemplateLoader(new ClassTemplateLoader(GenerateCodeService.class, "/templates" ));
        cfg.setDefaultEncoding("UTF-8" );
    }

    /**
     * 生成文件
     *
     * @param tableClassList
     * @param realPath
     * @return
     */
    public RespBean generateCode(List<TableClass> tableClassList, String realPath) {
        try {
            // 加载模板
            Template modelTemplate = cfg.getTemplate("Entity.java.ftl" );
            Template mapperJavaTemplate = cfg.getTemplate("Mapper.java.ftl" );
            Template mapperXmlTemplate = cfg.getTemplate("Mapper.xml.ftl" );
            Template serviceTemplate = cfg.getTemplate("Service.java.ftl" );
            Template serviceImplTemplate = cfg.getTemplate("ServiceImpl.java.ftl" );
            Template controllerTemplate = cfg.getTemplate("Controller.java.ftl" );

            Connection connection = DBUtils.getConnection();
            DatabaseMetaData metaData = connection.getMetaData();

            for (TableClass tableClass : tableClassList) {
                ResultSet columns = metaData.getColumns(connection.getCatalog(), null, tableClass.getTableName(), null);
                ResultSet primaryKeys = metaData.getPrimaryKeys(connection.getCatalog(), null, tableClass.getTableName());
                List<ColumnClass> columnClassList = new ArrayList<>();
                while (columns.next()) {
                    String column_name = columns.getString("COLUMN_NAME" );
                    String type_name = columns.getString("TYPE_NAME" );
                    String remarks = columns.getString("REMARKS" );
                    ColumnClass columnClass = new ColumnClass();
                    columnClass.setRemark(remarks);
                    columnClass.setColumnName(column_name);
                    columnClass.setType(type_name);
                    columnClass.setPropertyName(CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL, column_name));
                    primaryKeys.first();
                    while (primaryKeys.next()) {
                        String pkName = primaryKeys.getString("COLUMN_NAME" );
                        if (column_name.equals(pkName)) {
                            columnClass.setIsPrimary(true);
                        }
                    }
                    columnClassList.add(columnClass);
                }
                tableClass.setColumns(columnClassList);
                String path = realPath + "/" + tableClass.getPackageName().replace(".", "/" );

                // 生成文件
                generate(modelTemplate, tableClass, path + "/model/" );
                generate(mapperJavaTemplate, tableClass, path + "/mapper/" );
                generate(mapperXmlTemplate, tableClass, path + "/mapper/" );
                generate(serviceTemplate, tableClass, path + "/service/" );
                generate(serviceImplTemplate, tableClass, path + "/service/impl/" );
                generate(controllerTemplate, tableClass, path + "/controller/" );
            }
            return RespBean.ok("代码已生成", realPath);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return RespBean.error("生成失败" );
    }


    /**
     * 写入文件
     *
     * @param template
     * @param tableClass
     * @param path
     * @throws IOException
     * @throws TemplateException
     */
    private void generate(Template template, TableClass tableClass, String path) throws IOException, TemplateException {
        File folder = new File(path);
        if (!folder.exists()) {
            folder.mkdirs();
        }
        String fileName = path + "/" + tableClass.getModelName() + template.getName().replace(".ftl", "" ).replace("Model", "" );
        FileOutputStream fos = new FileOutputStream(fileName);
        OutputStreamWriter out = new OutputStreamWriter(fos);
        template.process(tableClass, out);
        fos.close();
        out.close();
    }
}

