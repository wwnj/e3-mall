package cn.e3mall.content.service;

import cn.e3mall.common.pojo.EasyUITreeNode;
import cn.e3mall.common.utils.E3Result;

import java.util.List;

/**
 * Created by wb on 2018/6/21.
 */
public interface ContentCategoryService {

    List<EasyUITreeNode> getContentCatList(Long parentId);

    E3Result addContentCategory(Long parentId,String name);

    E3Result updateContentCategory(Long id,String name);

    E3Result deleteContentCategory(Long id);
}
