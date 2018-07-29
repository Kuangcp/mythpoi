package com.kuangcp.mythpoi.utils.config;

import com.fasterxml.jackson.core.JsonEncoding;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.fasterxml.jackson.dataformat.yaml.YAMLGenerator;
import com.kuangcp.mythpoi.excel.base.MainConfig;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by https://github.com/kuangcp
 * 测试 使用 Jackson 的yml处理
 *
 * @author kuangcp
 */

public class JacksonYamlTest {

  private YAMLFactory factory = new YAMLFactory();

  // 将对象写入到Yml文件中去
  @Test
  public void testWrite() throws IOException {
    ObjectMapper mapper = new ObjectMapper(factory);
    MainConfig config = MainConfig.getInstance();
    config.setContentStartNum(11);
    config.setStartColNum(1);
    config.setTitleLastRowNum(3);
    config.setStartRowNum(1);
    // 保存到文件中
    factory.setCodec(mapper);
    YAMLGenerator generator = factory
        .createGenerator(new FileOutputStream("user.yml"), JsonEncoding.UTF8);
    generator.useDefaultPrettyPrinter();
    generator.writeObject(config);
    // 输出
    String result = mapper.writeValueAsString(config);
    System.out.println(result);
    Assert.assertNotNull(result);
  }

  @Test
  public void testRead() {
    ObjectMapper mapper = new ObjectMapper(factory);
    try {
      MainConfig user = mapper.readValue(new File("/home/kcp/test/user.yml"), MainConfig.class);

      Assert.assertNotNull(user);
      
      System.out.println(user.toString());
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

//    public int caculate(List<Integer> list) {
//        final Integer result = 0;
//        list.forEach(e -> {
//            if (e % 2 == 0) {
//                result += e;
//            }
//        });
//        return result;
//    }
}
