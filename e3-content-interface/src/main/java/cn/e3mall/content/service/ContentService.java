package cn.e3mall.content.service;

import cn.e3mall.common.pojo.EasyUIDataGridResult;
import cn.e3mall.common.utils.E3Result;
import cn.e3mall.pojo.TbContent;

import java.util.List;

/**
 * Created by wb on 2018/6/21.
 */
public interface ContentService {

    EasyUIDataGridResult getContentList(Long categoryId,int page,int rows);

    E3Result addContent(TbContent content);

    E3Result editContent(TbContent content);

    E3Result deleteContent(Long[] ids);

    List<TbContent> getContentListByCategoryId(Long cid);
}
