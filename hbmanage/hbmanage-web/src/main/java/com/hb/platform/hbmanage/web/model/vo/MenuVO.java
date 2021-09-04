package com.hb.platform.hbmanage.web.model.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * 菜单
 *
 * @author guoll
 * @date 2020/9/12
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MenuVO implements Serializable {
    // serialVersionUID
    private static final long serialVersionUID = -8390774202795927073L;
    // 页面索引
    private Long index;
    // 页面名称
    private String name;
    // 图标
    private String icon;
    // 页面链接
    private String url;
    // 父级页面索引
    private Long parentIndex;
    // 下级页面集合
    private List<MenuVO> children;

}
