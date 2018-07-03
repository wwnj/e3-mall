package cn.e3mall.activemq;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.junit.Test;

import javax.jms.*;

/**
 * Created by wb on 2018/6/24.
 */
public class ActiveMqTest {

    /**
     * 点到点形式发送消息
     * @throws Exception
     */
    @Test
    public void testQueueProducer() throws Exception{
        //1.创建一个连接工厂对象，需要指定服务的ip及端口
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://192.168.25.128:61616");
        //2.使用工厂对象创建一个Connection对象
        Connection connection = connectionFactory.createConnection();
        //3.开启连接，调用Connection对象的start方法
        connection.start();
        //4.创建一个session对象
        //第一个参数：是否开启事务。如果true开启事务，第二个参数无意义。一般不开启事务
        //第二个参数：应答模式。一般自动应答或者手动应答。一般自动应答
        Session session = connection.createSession(false,Session.AUTO_ACKNOWLEDGE);
        //5.使用session对象创建一个destination对象(两种形式queue、topic)
        Queue queue = session.createQueue("test-queue");
        //6.使用session对象创建一个producer对象
        MessageProducer producer = session.createProducer(queue);
        //7.创建一个message对象，可以使用TextMessage
        /*TextMessage textMessage = new ActiveMQTextMessage();
        textMessage.setText("Hello Activemq");*/
        TextMessage textMessage = session.createTextMessage("Hello Activemq");
        //8.发送消息
        producer.send(textMessage);
        //9.关闭资源
        producer.close();
        session.close();
        connection.close();
    }

    @Test
    public void testQueueConsumer() throws Exception{
        //1.创建一个连接工厂对象，需要指定服务的ip及端口
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://192.168.25.128:61616");
        //2.使用工厂对象创建一个Connection对象
        Connection connection = connectionFactory.createConnection();
        //3.开启连接，调用Connection对象的start方法
        connection.start();
        //4.创建一个session对象
        //第一个参数：是否开启事务。如果true开启事务，第二个参数无意义。一般不开启事务
        //第二个参数：应答模式。一般自动应答或者手动应答。一般自动应答
        Session session = connection.createSession(false,Session.AUTO_ACKNOWLEDGE);
        //5.使用session对象创建一个destination对象(两种形式queue、topic)
        Queue queue = session.createQueue("spring-queue");
        //6.使用session对象创建一个consumer对象
        MessageConsumer consumer = session.createConsumer(queue);
        //7.接收消息
        consumer.setMessageListener(new MessageListener() {
            @Override
            public void onMessage(Message message) {
                //8.打印结果
                TextMessage textMessage = (TextMessage) message;
                String text;
                try {
                    text = textMessage.getText();
                    System.out.println(text);
                }catch (JMSException e){
                    e.printStackTrace();
                }
            }
        });
        //9.等待接收消息
        System.in.read();
        //10.关闭资源
        consumer.close();
        session.close();
        connection.close();
    }

    @Test
    public void testTopicProducer() throws Exception{
        //1.创建一个连接工厂对象，需要指定服务的ip及端口
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://192.168.25.128:61616");
        //2.使用工厂对象创建一个Connection对象
        Connection connection = connectionFactory.createConnection();
        //3.开启连接，调用Connection对象的start方法
        connection.start();
        //4.创建一个session对象
        //第一个参数：是否开启事务。如果true开启事务，第二个参数无意义。一般不开启事务
        //第二个参数：应答模式。一般自动应答或者手动应答。一般自动应答
        Session session = connection.createSession(false,Session.AUTO_ACKNOWLEDGE);
        //5.使用session对象创建一个destination对象(两种形式queue、topic)
        Topic topic = session.createTopic("test-topic");
        //6.使用session对象创建一个producer对象
        MessageProducer producer = session.createProducer(topic);
        //7.创建一个message对象，可以使用TextMessage
        /*TextMessage textMessage = new ActiveMQTextMessage();
        textMessage.setText("Hello Activemq");*/
        TextMessage textMessage = session.createTextMessage("topic message");
        //8.发送消息
        producer.send(textMessage);
        //9.关闭资源
        producer.close();
        session.close();
        connection.close();
    }

    @Test
    public void testTopicConsumer() throws Exception{
        //1.创建一个连接工厂对象，需要指定服务的ip及端口
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://192.168.25.128:61616");
        //2.使用工厂对象创建一个Connection对象
        Connection connection = connectionFactory.createConnection();
        //3.开启连接，调用Connection对象的start方法
        connection.start();
        //4.创建一个session对象
        //第一个参数：是否开启事务。如果true开启事务，第二个参数无意义。一般不开启事务
        //第二个参数：应答模式。一般自动应答或者手动应答。一般自动应答
        Session session = connection.createSession(false,Session.AUTO_ACKNOWLEDGE);
        //5.使用session对象创建一个destination对象(两种形式queue、topic)
        Topic topic = session.createTopic("test-topic");
        //6.使用session对象创建一个consumer对象
        MessageConsumer consumer = session.createConsumer(topic);
        //7.接收消息
        consumer.setMessageListener(new MessageListener() {
            @Override
            public void onMessage(Message message) {
                //8.打印结果
                TextMessage textMessage = (TextMessage) message;
                String text;
                try {
                    text = textMessage.getText();
                    System.out.println(text);
                }catch (JMSException e){
                    e.printStackTrace();
                }
            }
        });
        System.out.println("topic消费者1已经启动。。。");
        //9.等待接收消息
        System.in.read();
        //10.关闭资源
        consumer.close();
        session.close();
        connection.close();
    }
}
