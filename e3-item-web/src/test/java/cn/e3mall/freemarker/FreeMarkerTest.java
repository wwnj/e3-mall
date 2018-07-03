package cn.e3mall.freemarker;

import freemarker.template.Configuration;
import freemarker.template.Template;
import org.junit.Test;

import java.io.File;
import java.io.FileWriter;
import java.io.Writer;
import java.util.*;

/**
 * Created by wb on 2018/6/25.
 */
public class FreeMarkerTest {

    @Test
    public void testFreemMarker() throws Exception{
        //1.创建一个模板文件
        //2.创建一个Configuration对象
        Configuration configuration = new Configuration(Configuration.getVersion());
        //3.设置模板文件保存的目录
        configuration.setDirectoryForTemplateLoading(new File("G:\\java txt\\ideaIU Porject\\e3-mall\\e3-item-web\\src\\main\\webapp\\WEB-INF\\ftl"));
        //4.模板文件的编码格式，一般是utf-8
        configuration.setDefaultEncoding("utf-8");
        //5.加载一个模板文件，创建一个模板对象
        //Template template = configuration.getTemplate("hello.ftl");
        Template template = configuration.getTemplate("student.ftl");
        //6.创建一个数据集。可以是pojo也可以是map。推荐使用map
        Map data = new HashMap();
        data.put("hello","hello freemarker");
        //创建一个pojo对象
        Student student = new Student(1,"小明",18,"孝陵卫");
        data.put("student",student);
        //添加一个list
        List<Student> studentList = new ArrayList<>();
        studentList.add(new Student(1,"小明1",18,"孝陵卫"));
        studentList.add(new Student(2,"小明2",19,"孝陵卫"));
        studentList.add(new Student(3,"小明3",20,"孝陵卫"));
        studentList.add(new Student(4,"小明4",21,"孝陵卫"));
        studentList.add(new Student(5,"小明5",22,"孝陵卫"));
        studentList.add(new Student(6,"小明6",23,"孝陵卫"));
        studentList.add(new Student(7,"小明7",24,"孝陵卫"));
        data.put("studentList",studentList);
        //添加日期类型
        data.put("date",new Date());
        //null空值的测试
        data.put("val",123);
        //7.创建一个Writer对象，指定输出文件的路径及文件名
        //Writer out = new FileWriter(new File("F:\\BaiduNetdiskDownload\\黑马程序员\\98.项目二：宜立方商城(80-93天）\\hellofreemarker.txt"));
        Writer out = new FileWriter(new File("F:\\BaiduNetdiskDownload\\黑马程序员\\98.项目二：宜立方商城(80-93天）\\student.html"));
        //8.生成静态页面
        template.process(data,out);
        //9.关闭流
        out.close();
    }
}
