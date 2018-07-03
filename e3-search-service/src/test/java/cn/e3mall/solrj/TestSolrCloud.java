package cn.e3mall.solrj;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.impl.CloudSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.junit.Test;

/**
 * Created by wb on 2018/6/24.
 */
public class TestSolrCloud {

    @Test
    public void testAddDocument() throws Exception{
        //创建一个集群的连接，应该使用CloudSlorServer创建
        CloudSolrServer solrServer = new CloudSolrServer("192.168.25.128:2182,192.168.25.128:2183,192.168.25.128:2184");
        //zkHost: zookeeper的地址列表
        //设置一个defaultColletion属性
        solrServer.setDefaultCollection("collection2");
        //创建一个文档对象
        SolrInputDocument document = new SolrInputDocument();
        //向文档对象添加域
        document.setField("id","solrcloud01");
        document.setField("item_title","测试商品01");
        document.setField("item_price",123);
        //把文档写入索引库
        solrServer.add(document);
        //提交
        solrServer.commit();
    }

    @Test
    public void testQueeyDocument() throws Exception{
        //创建一个集群的连接，应该使用CloudSlorServer创建
        CloudSolrServer solrServer = new CloudSolrServer("192.168.25.128:2182,192.168.25.128:2183,192.168.25.128:2184");
        //zkHost: zookeeper的地址列表
        //设置一个defaultColletion属性
        solrServer.setDefaultCollection("collection2");
        //创建一个查询对象
        SolrQuery query = new SolrQuery();
        //设置查询条件
        query.setQuery("*:*");
        //执行查询
        QueryResponse queryResponse = solrServer.query(query);
        //取查询结果
        SolrDocumentList solrDocumentList = queryResponse.getResults();
        System.out.println("总记录数："+solrDocumentList.getNumFound());
        //打印
        for (SolrDocument document:solrDocumentList){
            System.out.println(document.get("id"));
            System.out.println(document.get("item_title"));
            System.out.println(document.get("item_price"));
        }
    }
}
