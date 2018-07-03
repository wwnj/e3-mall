package cn.e3mall.fast;

import cn.e3mall.common.utils.FastDFSClient;
import org.csource.fastdfs.*;
import org.junit.Test;

/**
 * Created by wb on 2018/6/19.
 */
public class FastDFSTest {

    @Test
    public void testUpload() throws Exception{
        //创建一个配置文件。文件名任意。内容就是tracker服务器的地址
        //使用全局对象加载配置文件。
        ClientGlobal.init("G:\\java txt\\ideaIU Porject\\e3-mall\\e3-manager-web\\src\\main\\resources\\conf\\client.conf");
        //创建一个TrackerClient对象
        TrackerClient trackerClient = new TrackerClient();
        //通过TrackClient获得一个TrackerServer对象
        TrackerServer trackerServer = trackerClient.getConnection();
        //创建一个StorageServer的引用，可以是null
        StorageServer storageServer = null;
        //创建一个StorageClient，参数需要TrackerServer和StorageServer
        StorageClient storageClient = new StorageClient(trackerServer,storageServer);
        //使用StoragerClient上传文件
        String[] strings = storageClient.upload_file("H:\\我的资料\\图片\\艾薇儿.jpg", "jpg", null);
        for (String string:strings)
            System.out.println(string);
    }

    @Test
    public void testFastDFSClient() throws Exception{
        FastDFSClient fastDFSClient = new FastDFSClient("G:\\java txt\\ideaIU Porject\\e3-mall\\e3-manager-web\\src\\main\\resources\\conf\\client.conf");
        String string = fastDFSClient.uploadFile("H:\\我的资料\\图片\\雪景.jpg");
        System.out.println(string);

    }
}
