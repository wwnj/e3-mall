package cn.e3mall.controller;

import cn.e3mall.common.utils.E3Result;
import cn.e3mall.pojo.TbItem;
import cn.e3mall.pojo.TbItemDesc;
import cn.e3mall.pojo.TbItemParamItem;
import cn.e3mall.service.ItemDescService;
import cn.e3mall.service.ItemParamService;
import cn.e3mall.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * 商品编辑Controller
 * Created by wb on 2018/6/20.
 */
@Controller
public class RestItemController {

    @Autowired
    private ItemDescService itemDescService;
    @Autowired
    private ItemParamService itemParamService;
    @Autowired
    private ItemService itemService;

    @RequestMapping("/rest/item/query/item/desc/{id}")
    @ResponseBody
    public E3Result getItemDescById(@PathVariable Long id){
        TbItemDesc itemDesc = itemDescService.getTbItemDescById(id);
        if(itemDesc!=null)
            return E3Result.ok(itemDesc);
        else
            return E3Result.build(404,"根据id查找不到对应的TbItemDesc",null);
    }

    @RequestMapping("/rest/item/param/item/query/{id}")
    @ResponseBody
    public E3Result getItemParamById(@PathVariable Long id){
        TbItemParamItem itemParamItem = itemParamService.getItemParamById(143771131488369L);
        return E3Result.ok(itemParamItem);
    }

    @RequestMapping("/rest/item/update")
    @ResponseBody
    public E3Result updateItemAndItemDesc(TbItem item,String desc){
        E3Result e3Result = itemService.updateItem(item, desc);
        return e3Result;
    }

    @RequestMapping("/rest/item/delete")
    @ResponseBody
    public E3Result deleteItemAndItemDesc(Long[] ids){
        if(ids!=null && ids.length>0) {
            for(Long id:ids)
                itemService.deleteItem(id);
            return E3Result.ok();
        }else{
            return E3Result.build(404,"未选中id",null);
        }
    }

    @RequestMapping("/rest/item/instock")
    @ResponseBody
    public E3Result instockItem(Long[] ids){
        if(ids!=null && ids.length>0) {
            for(Long id:ids)
                itemService.instockItem(id);
            return E3Result.ok();
        }else{
            return E3Result.build(404,"未选中id",null);
        }
    }

    @RequestMapping("/rest/item/reshelf")
    @ResponseBody
    public E3Result reshelfItem(Long[] ids){
        if(ids!=null && ids.length>0) {
            for(Long id:ids)
                itemService.reshelfItem(id);
            return E3Result.ok();
        }else{
            return E3Result.build(404,"未选中id",null);
        }
    }
}
