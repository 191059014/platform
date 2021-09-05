package com.hb.platform.hbmanage.web.model.vo.response;

import com.hb.platform.hbmanage.web.model.dto.ElementuiTree;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 树形结构响应实体
 *
 * @version v0.1, 2020/9/18 18:40, create by huangbiao.
 */
@Data
@AllArgsConstructor
public class ElementuiTreeResponse implements Serializable {

    // serialVersionUID
    private static final long serialVersionUID = 6066278139558557896L;

    // 树所有数据
    private List<ElementuiTree> treeDataList;

}
