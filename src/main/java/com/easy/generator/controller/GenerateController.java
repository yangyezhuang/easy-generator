package com.easy.generator.controller;

import com.easy.generator.model.Db;
import com.easy.generator.model.RespBean;
import com.easy.generator.model.TableClass;
import com.easy.generator.service.GenerateCodeService;
import com.easy.generator.utils.DBUtils;
import com.google.common.base.CaseFormat;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Controller
 *
 * @author: YZ.YANG
 * @date: 2023-09-16 20:58
 */
@Slf4j
@RestController
public class GenerateController {

    @Autowired
    private GenerateCodeService generateCodeService;

    @Value("${generate.path}" )
    private String generatePath;


    /**
     * 连接数据库
     *
     * @param db
     * @return
     */
    @RequestMapping(path = "/connect", method = RequestMethod.POST)
    public RespBean connect(@RequestBody Db db) {
        log.info("db:" + db);
        Connection con = DBUtils.initDb(db);
        log.info("conn:" + con);

        if (con != null) {
            return RespBean.ok("数据库连接成功" );
        }
        return RespBean.error("数据库连接失败" );
    }


    /**
     * 配置数据库
     *
     * @param map
     * @return
     */
    @RequestMapping(path = "/config", method = RequestMethod.POST)
    public RespBean config(@RequestBody Map<String, String> map) {
        log.info("map:" + map);
        String packageName = map.get("packageName" );
        try {
            Connection connection = DBUtils.getConnection();
            DatabaseMetaData metaData = connection.getMetaData();
            ResultSet tables = metaData.getTables(connection.getCatalog(), null, null, null);
            List<TableClass> tableClassList = new ArrayList<>();

            while (tables.next()) {
                TableClass tableClass = new TableClass();
                tableClass.setPackageName(packageName);
                String table_name = tables.getString("TABLE_NAME" );
                String modelName = CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL, table_name);
                tableClass.setTableName(table_name);
                tableClass.setModelName(modelName);
                tableClass.setControllerName(modelName + "Controller" );
                tableClass.setMapperName(modelName + "Mapper" );
                tableClass.setServiceName(modelName + "Service" );
                tableClassList.add(tableClass);
            }

            return RespBean.ok("数据库信息读取成功", tableClassList);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return RespBean.error("数据库信息读取失败" );
    }


    /**
     * 生成代码
     *
     * @param tableClassList
     * @return
     */
    @RequestMapping(path = "/generateCode", method = RequestMethod.POST)
    public RespBean generateCode(@RequestBody List<TableClass> tableClassList) {
        return generateCodeService.generateCode(tableClassList, generatePath);
    }

}
