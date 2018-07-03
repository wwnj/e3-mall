package cn.e3mall.search.service;

import cn.e3mall.common.pojo.SearchResult;

/**
 * Created by wb on 2018/6/23.
 */
public interface SearchService {

    SearchResult search(String keyword,int page,int rows) throws Exception;
}
