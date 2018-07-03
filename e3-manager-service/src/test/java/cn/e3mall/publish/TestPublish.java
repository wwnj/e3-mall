package cn.e3mall.publish;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by wb on 2018/6/21.
 */
public class TestPublish {

    @Test
    public void publishService() throws Exception{
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:spring/application*.xml");
        /*while(true){
            Thread.sleep(1000);
        }*/
        System.out.println("服务已经启动。。。");
        System.in.read();
        System.out.println("服务已经关闭。。。");
    }
}
