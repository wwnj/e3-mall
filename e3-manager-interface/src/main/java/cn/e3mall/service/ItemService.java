package cn.e3mall.service;

import cn.e3mall.common.pojo.EasyUIDataGridResult;
import cn.e3mall.common.utils.E3Result;
import cn.e3mall.pojo.TbItem;

/**
 * Created by wb on 2018/6/13.
 */
public interface ItemService {

    TbItem getItemById(Long itemId);

    EasyUIDataGridResult getItemList(int page,int rows);

    E3Result addItem(TbItem tbItem,String desc);

    E3Result updateItem(TbItem tbItem,String desc);

    E3Result deleteItem(Long id);

    E3Result instockItem(Long id);

    E3Result reshelfItem(Long id);
}
