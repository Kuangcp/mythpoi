package com.kuangcp.mythpoi.utils.base;

import com.kuangcp.mythpoi.utils.Employee;
import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by https://github.com/kuangcp on 18-2-22  下午4:20
 *
 * @author kuangcp
 */
public class ConfigUtilTest {
    ConfigUtil configUtil = new ConfigUtil();

    @Test
    public void testGetFieldTitleMap() {
        Map<String, String> result = configUtil.getFieldTitleMap(Employee.class);
        for(Map.Entry<String, String> entry:result.entrySet()){
            System.out.println(entry.getKey() + " | " + entry.getValue());
        }
    }
}