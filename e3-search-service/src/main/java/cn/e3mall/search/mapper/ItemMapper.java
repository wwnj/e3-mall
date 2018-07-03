package cn.e3mall.search.mapper;

import cn.e3mall.common.pojo.SearchItem;

import java.util.List;

/**
 * Created by wb on 2018/6/23.
 */
public interface ItemMapper {

    List<SearchItem> getItemList();

    SearchItem getItemById(long itemId);
}
