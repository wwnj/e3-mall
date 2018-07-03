package cn.e3mall.service;

import cn.e3mall.common.pojo.EasyUITreeNode;

import java.util.List;

/**
 * Created by wb on 2018/6/16.
 */
public interface ItemCatService {

    List<EasyUITreeNode> getItemCatList(Long parentId);
}
