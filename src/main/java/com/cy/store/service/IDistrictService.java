package com.cy.store.service;

import com.cy.store.entity.District;

import java.util.List;

/**省市区列表信息业务层接口*/
public interface IDistrictService {

    /**
     * 根据父区域的代号查询区域的信息(省/市/区)
     * @param parent 父代号
     * @return 返回该父区域下的所有子区域信息
     */
    List<District> getByParent(String parent);

    /**
     * 根据地区code获取地区name
     * @param code 地区code
     * @return 返回地区name
     */
    String getNameByCode(String code);
}
