package com.hb.platform.hbmanage.web.controller;

import com.hb.platform.hbmanage.web.common.enums.ResourceType;
import com.hb.platform.hbmanage.web.model.vo.MenuResponse;
import com.hb.platform.hbmanage.web.model.vo.MenuVO;
import com.hb.platform.unic.base.model.Result;
import com.hb.platform.unic.rbac.dao.dobj.SysPermissionDO;
import com.hb.platform.unic.rbac.service.ISysPermissionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 菜单
 *
 * @version v0.1, 2021-08-23 23:06:14, create by Mr.Huang.
 */
@RestController
@RequestMapping("/menu")
public class MenuController {

    /**
     * 日志
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(MenuController.class);

    /**
     * 权限信息表服务层
     */
    @Resource
    private ISysPermissionService sysPermissionService;

    /**
     * 查询用户下的菜单列表
     *
     * @return 分页结果
     */
    @GetMapping("/getPrivateMenuDatas")
    public Result<MenuResponse> getPrivateMenuDatas() {
        List<SysPermissionDO> allList = sysPermissionService.selectList(new SysPermissionDO());
        allList = allList.stream().filter(p -> !ResourceType.BUTTON.getValue().equals(p.getResourceType()))
            .collect(Collectors.toList());
        List<SysPermissionDO> topList =
            allList.stream().filter(access -> access.getParentId() == null).collect(Collectors.toList());
        List<MenuVO> menuList = findChildrenMenuCycle(allList, topList);
        return Result.success(MenuResponse.builder().menuDatas(menuList).build());
    }

    /**
     * 递归查找菜单
     *
     * @param allList
     *            所有权限
     * @param childList
     *            当前权限信息
     * @return 菜单列表
     */
    private List<MenuVO> findChildrenMenuCycle(List<SysPermissionDO> allList, List<SysPermissionDO> childList) {
        List<MenuVO> menuList = new ArrayList<>();
        childList.forEach(access -> {
            MenuVO menu = MenuVO.builder().index(access.getId()).name(access.getPermissionName()).icon(access.getIcon())
                .url(access.getUrl()).parentIndex(access.getParentId()).build();
            List<SysPermissionDO> cList =
                allList.stream().filter(acc -> access.getId().equals(acc.getParentId())).collect(Collectors.toList());
            menu.setChildren(findChildrenMenuCycle(allList, cList));
            menuList.add(menu);
        });
        return menuList.size() > 0 ? menuList : null;
    }

}
