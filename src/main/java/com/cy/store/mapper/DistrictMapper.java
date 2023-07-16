package com.cy.store.mapper;

import com.cy.store.entity.District;

import java.util.List;

public interface DistrictMapper {

    /**
     * 根据父代号查询区域信息
     * @param parent 父代号
     * @return 某个父区域下的所有子区域列表
     */
    List<District> findByParent(String parent);

    /**
     * 根据code查询地区名称
     * @param code 地区代码
     * @return 返回地区名称
     */
    String findNameByCode(String code);
}
