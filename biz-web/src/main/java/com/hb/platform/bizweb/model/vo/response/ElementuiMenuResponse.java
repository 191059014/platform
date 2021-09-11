package com.hb.platform.bizweb.model.vo.response;

import com.hb.platform.bizweb.model.dto.ElementuiMenu;
import lombok.AllArgsConstructor;
import lombok.Data;

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
public class ElementuiMenuResponse implements Serializable {

    // serialVersionUID
    private static final long serialVersionUID = -1128889147856096856L;

    // 菜单列表
    private List<ElementuiMenu> menuDatas;

}
