package ${package}.${moduleName}.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import ${mainPath}.common.utils.PageUtils;
import ${mainPath}.common.utils.Query;
import ${package}.${moduleName}.dao.${className}Dao;
import ${package}.${moduleName}.entity.${className}Entity;
import ${package}.${moduleName}.service.${className}Service;

import java.util.Map;


@Service("${classname}Service")
public class ${className}ServiceImpl implements ${className}Service {

    @Autowired
    private ${className}Dao ${classname}Dao;


    @Override
    public List<${className}Entity> findAll() {
        return ${classname}Dao.findAll();
    }


    @Override
    public  ${className}Entity findById(int id) {
        return ${classname}Dao.findById(id);
    }


    @Override
    public Integer save(${className}Entity ${classname}) {
        return ${classname}Dao.findAll();
    }


    @Override
    public Integer update(${className}Entity ${classname}) {
        return ${classname}Dao.update(${classname});
    }


    @Override
    public Integer deleteById(int id) {
        return ${classname}Dao.deleteById(id);
    }

}