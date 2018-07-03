package cn.e3mall.service.impl;

import cn.e3mall.common.jedis.JedisClient;
import cn.e3mall.common.pojo.EasyUIDataGridResult;
import cn.e3mall.common.utils.E3Result;
import cn.e3mall.common.utils.IDUtils;
import cn.e3mall.common.utils.JsonUtils;
import cn.e3mall.mapper.TbItemDescMapper;
import cn.e3mall.mapper.TbItemMapper;
import cn.e3mall.pojo.TbItem;
import cn.e3mall.pojo.TbItemDesc;
import cn.e3mall.pojo.TbItemDescExample;
import cn.e3mall.pojo.TbItemExample;
import cn.e3mall.service.ItemService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import java.util.Date;
import java.util.List;

/**
 * 商品管理Service
 * Created by wb on 2018/6/13.
 */
@Service
public class ItemServiceImpl implements ItemService {

    @Autowired
    private TbItemMapper itemMapper;
    @Autowired
    private TbItemDescMapper itemDescMapper;
    @Autowired
    private JmsTemplate jmsTemplate;
    //@Resource，先查找id为topicDestination的bean，没有在查找Destination类型的bean
    @Resource
    private Destination topicDestination;
    @Autowired
    private JedisClient jedisClient;

    @Value("${REDIS_ITEM_PRE}")
    private String REDIS_ITEM_PRE;
    @Value("${ITEM_CACHE_EXPIRE}")
    private int ITEM_CACHE_EXPIRE;

    @Override
    public TbItem getItemById(Long itemId) {
        //查询缓存
        try {
            String json = jedisClient.get(REDIS_ITEM_PRE + ":" + itemId + ":BASE");
            if(StringUtils.isNotBlank(json)){
                TbItem tbItem = JsonUtils.jsonToPojo(json, TbItem.class);
                return tbItem;
            }

        }catch (Exception e){
            e.printStackTrace();
        }
        //如果缓存中没有查询数据库
        //根据主键查询
        //TbItem tbItem = itemMapper.selectByPrimaryKey(itemId);
        //设置查询条件
        TbItemExample example = new TbItemExample();
        TbItemExample.Criteria criteria = example.createCriteria();
        criteria.andIdEqualTo(itemId);
        //执行查询
        List<TbItem> tbItems = itemMapper.selectByExample(example);
        if (tbItems!=null &&tbItems.size()>0) {
            //把结果添加到缓存
            try {
                jedisClient.set(REDIS_ITEM_PRE+":"+itemId+":BASE", JsonUtils.objectToJson(tbItems.get(0)));
                //设置过期时间
                jedisClient.expire(REDIS_ITEM_PRE+":"+itemId+":BASE",ITEM_CACHE_EXPIRE);
            }catch (Exception e){
                e.printStackTrace();
            }
            return tbItems.get(0);
        }
        else
            return null;
    }

    @Override
    public EasyUIDataGridResult getItemList(int page, int rows) {
        //设置分页信息
        PageHelper.startPage(page,rows);
        //执行查询
        TbItemExample example = new TbItemExample();
        List<TbItem> list = itemMapper.selectByExample(example);
        //创建一个返回值对象
        EasyUIDataGridResult result = new EasyUIDataGridResult();
        result.setRows(list);
        //取分页结果
        PageInfo<TbItem> pageInfo = new PageInfo<>(list);
        //取总记录数
        long total = pageInfo.getTotal();
        result.setTotal(total);
        return result;
    }

    @Override
    public E3Result addItem(TbItem tbItem, String desc) {
        //生成商品Id
        final long itemId = IDUtils.genItemId();
        //补全item的属性
        tbItem.setId(itemId);
        //1-正常，2-下架，3-删除
        tbItem.setStatus((byte)1);
        tbItem.setCreated(new Date());
        tbItem.setUpdated(new Date());
        //向商品表插入数据
        itemMapper.insert(tbItem);
        //创建一个商品描述表对应的pojo对象
        TbItemDesc itemDesc = new TbItemDesc();
        //补全属性
        itemDesc.setItemId(itemId);
        itemDesc.setItemDesc(desc);
        itemDesc.setCreated(new Date());
        itemDesc.setUpdated(new Date());
        //向商品描述表插入数据
        itemDescMapper.insert(itemDesc);
        //发送一个商品添加消息
        jmsTemplate.send(topicDestination, new MessageCreator() {
            @Override
            public Message createMessage(Session session) throws JMSException {
                return session.createTextMessage(itemId+"");
            }
        });
        //返回成功
        return E3Result.ok();
    }

    @Override
    public E3Result updateItem(TbItem tbItem, String desc) {
        tbItem.setUpdated(new Date());
        itemMapper.updateByPrimaryKeySelective(tbItem);
        //创建一个商品描述表对应的pojo对象
        TbItemDesc itemDesc = new TbItemDesc();
        //补全属性
        itemDesc.setItemId(tbItem.getId());
        itemDesc.setItemDesc(desc);
        itemDesc.setUpdated(new Date());
        TbItemDescExample itemDescExample = new TbItemDescExample();
        TbItemDescExample.Criteria criteria = itemDescExample.createCriteria();
        criteria.andItemIdEqualTo(itemDesc.getItemId());
        //向商品描述表插入数据
        itemDescMapper.updateByExampleSelective(itemDesc,itemDescExample);
        //返回成功
        return E3Result.ok();
    }

    @Override
    public E3Result deleteItem(Long id) {
        itemMapper.deleteByPrimaryKey(id);
        int result = itemDescMapper.deleteByPrimaryKey(id);
        if(result==1)
            return E3Result.ok();
        else
            return E3Result.build(404,"上架商品失败",null);
    }

    @Override
    public E3Result instockItem(Long id) {
        TbItem item = new TbItem();
        item.setId(id);
        //1-正常，2-下架，3-删除
        item.setStatus((byte)2);
        int result = itemMapper.updateByPrimaryKeySelective(item);
        if(result==1)
            return E3Result.ok();
        else
            return E3Result.build(404,"上架商品失败",null);
    }

    @Override
    public E3Result reshelfItem(Long id) {
        TbItem item = new TbItem();
        item.setId(id);
        //1-正常，2-下架，3-删除
        item.setStatus((byte)3);
        int result = itemMapper.updateByPrimaryKeySelective(item);
        if(result==1)
            return E3Result.ok();
        else
            return E3Result.build(404,"上架商品失败",null);
    }
}
