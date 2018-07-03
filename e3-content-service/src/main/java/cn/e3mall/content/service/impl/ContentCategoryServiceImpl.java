package cn.e3mall.content.service.impl;

import cn.e3mall.common.pojo.EasyUITreeNode;
import cn.e3mall.common.utils.E3Result;
import cn.e3mall.content.service.ContentCategoryService;
import cn.e3mall.mapper.TbContentCategoryMapper;
import cn.e3mall.pojo.TbContentCategory;
import cn.e3mall.pojo.TbContentCategoryExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Stack;

/**
 * Created by wb on 2018/6/21.
 */
@Service
public class ContentCategoryServiceImpl implements ContentCategoryService {

    @Autowired
    private TbContentCategoryMapper contentCategoryMapper;

    @Override
    public List<EasyUITreeNode> getContentCatList(Long parentId) {
        TbContentCategoryExample example = new TbContentCategoryExample();
        TbContentCategoryExample.Criteria criteria = example.createCriteria();
        criteria.andParentIdEqualTo(parentId);
        List<TbContentCategory> list = contentCategoryMapper.selectByExample(example);
        List<EasyUITreeNode> nodeList = new ArrayList<>();
        for (TbContentCategory tbContentCategory:list){
            EasyUITreeNode node = new EasyUITreeNode();
            node.setId(tbContentCategory.getId());
            node.setText(tbContentCategory.getName());
            node.setState(tbContentCategory.getIsParent()?"closed":"open");
            nodeList.add(node);
        }
        return nodeList;
    }

    @Override
    public E3Result addContentCategory(Long parentId, String name) {
        //创建一个tb_content_category表对应的pojo对象
        TbContentCategory contentCategory = new TbContentCategory();
        //设置pojo属性
        contentCategory.setParentId(parentId);
        //新添加的节点一定是叶子节点
        contentCategory.setIsParent(false);
        contentCategory.setName(name);
        //1-正常，2-删除
        contentCategory.setStatus(1);
        //默认排序就是1
        contentCategory.setSortOrder(1);
        contentCategory.setCreated(new Date());
        contentCategory.setUpdated(new Date());
        //插入到数据库
        contentCategoryMapper.insert(contentCategory);
        //查询父节点的isparent属性，如果不是true改为true
        TbContentCategory parent = contentCategoryMapper.selectByPrimaryKey(parentId);
        if(!parent.getIsParent()){
            parent.setIsParent(true);
            contentCategoryMapper.updateByPrimaryKey(parent);
        }
        //返回结果，返回E3Result，包含pojo
        return E3Result.ok(contentCategory);
    }

    @Override
    public E3Result updateContentCategory(Long id, String name) {
        TbContentCategory contentCategory = new TbContentCategory();
        contentCategory.setId(id);
        contentCategory.setName(name);
        contentCategoryMapper.updateByPrimaryKeySelective(contentCategory);
        return E3Result.ok();
    }

    @Override
    public E3Result deleteContentCategory(Long id) {
        TbContentCategory contentCategory = contentCategoryMapper.selectByPrimaryKey(id);
        TbContentCategoryExample example1 = new TbContentCategoryExample();
        TbContentCategoryExample.Criteria criteria1 = example1.createCriteria();
        criteria1.andParentIdEqualTo(contentCategory.getParentId());
        //先查与该节点具有相同的父节点Id的同一级节点有多少个，如果不止一个，则不需要修改父节点的IsParent属性
        List<TbContentCategory> list1 = contentCategoryMapper.selectByExample(example1);
        if(list1!=null) {
            if(list1.size()==1) {
                TbContentCategoryExample example2 = new TbContentCategoryExample();
                TbContentCategoryExample.Criteria criteria2 = example2.createCriteria();
                criteria2.andIdEqualTo(contentCategory.getParentId());
                List<TbContentCategory> list2 = contentCategoryMapper.selectByExample(example2);
                if(list2!=null && list2.size()>0) {
                    TbContentCategory contentCategory1 = list2.get(0);
                    contentCategory1.setIsParent(false);
                    contentCategory1.setUpdated(new Date());
                    contentCategoryMapper.updateByPrimaryKeySelective(contentCategory1);
                }
            }
        }

        Stack<TbContentCategory> stack = new Stack<>();
        stack.push(contentCategory);
        while (!stack.isEmpty()){
            contentCategory=stack.pop();
            if(contentCategory.getIsParent()) {
                TbContentCategoryExample example = new TbContentCategoryExample();
                TbContentCategoryExample.Criteria criteria = example.createCriteria();
                criteria.andParentIdEqualTo(contentCategory.getId());
                List<TbContentCategory> list = contentCategoryMapper.selectByExample(example);
                if (list != null && list.size() > 0)
                    for (TbContentCategory temp : list)
                        stack.push(temp);
                else
                    return E3Result.build(404, "错误", null);
            }
            contentCategoryMapper.deleteByPrimaryKey(contentCategory.getId());
        }
        return E3Result.ok();
    }
}
