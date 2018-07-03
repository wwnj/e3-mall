package cn.e3mall.item.controller;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by wb on 2018/6/25.
 */
@Controller
public class HtmlGenController {

    @Autowired
    private FreeMarkerConfigurer freeMarkerConfigurer;

    @RequestMapping("/genhtml")
    @ResponseBody
    public String genHtml() throws IOException, TemplateException {
        Configuration configuration = freeMarkerConfigurer.getConfiguration();
        //加载模板
        Template template = configuration.getTemplate("hello.ftl");
        //创建一个数据集
        Map data = new HashMap();
        data.put("hello",123456);
        //指定文件输出的路径及文件名
        Writer out = new FileWriter(new File("F:\\BaiduNetdiskDownload\\黑马程序员\\98.项目二：宜立方商城(80-93天）\\hello2.html"));
        //输出文件
        template.process(data,out);
        //关闭流
        out.close();

        return "OK";
    }
}
