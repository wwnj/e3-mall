package cn.e3mall.service.impl;

import cn.e3mall.mapper.TbItemParamItemMapper;
import cn.e3mall.pojo.TbItemParamItem;
import cn.e3mall.pojo.TbItemParamItemExample;
import cn.e3mall.service.ItemParamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by wb on 2018/6/20.
 */
@Service
public class ItemParamServiceImpl implements ItemParamService {

    @Autowired
    private TbItemParamItemMapper itemParamItemMapper;

    @Override
    public TbItemParamItem getItemParamById(Long id) {
        TbItemParamItemExample itemParamItemExample = new TbItemParamItemExample();
        TbItemParamItemExample.Criteria criteria = itemParamItemExample.createCriteria();
        criteria.andItemIdEqualTo(id);
        List<TbItemParamItem> list = itemParamItemMapper.selectByExampleWithBLOBs(itemParamItemExample);
        if(list!=null && list.size()>0)
            return list.get(0);
        else
            return null;
    }
}
