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
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MenuResponse implements Serializable {

    // serialVersionUID
    private static final long serialVersionUID = -1128889147856096856L;

    // 菜单列表
    private List<MenuVO> menuDatas;

}
