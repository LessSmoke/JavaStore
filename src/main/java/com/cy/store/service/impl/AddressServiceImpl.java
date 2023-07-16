package com.cy.store.service.impl;

import com.cy.store.entity.Address;
import com.cy.store.mapper.AddressMapper;
import com.cy.store.service.IAddressService;
import com.cy.store.service.IDistrictService;
import com.cy.store.service.exception.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**用户收货地址业务层实现类*/
@Service
public class AddressServiceImpl implements IAddressService {

    /**定义addressMapper对象,导入依赖*/
    @Autowired
    private AddressMapper addressMapper;

    /** 在添加用户的收货地址的业务层依赖于IDistrictService的业务层接口*/
    @Autowired
    private IDistrictService districtService;

    @Value("${user.address.max-count}")
    private Integer maxCount;

    @Override
    public void addNewAddress(Address address, Integer uid, String username) {
        // 判断用户的收货地址条目数是否达到上限
        Integer result = addressMapper.countByUid(uid);
        if(result >= maxCount){
            throw new AddressCountLimitException("用户收货地址数目已达上限无法继续添加");
        }
        // 将districtService接口中获取的省市区数据转移到address对象中，补全：省市区相关数据
        address.setProvinceName(districtService.getNameByCode(address.getProvinceCode()));
        address.setCityName(districtService.getNameByCode(address.getCityCode()));
        address.setAreaName(districtService.getNameByCode(address.getAreaCode()));

        // 补全address对象中的uid,isDefault值
        address.setUid(uid);
        Integer isDefault = result == 0 ? 1 : 0;
        address.setIsDefault(isDefault);
        // 补全四项日志
        address.setCreatedUser(username);
        address.setCreatedTime(new Date());
        address.setModifiedUser(username);
        address.setModifiedTime(new Date());

        //调用map层的insertAddress方法进行操作
        Integer rows = addressMapper.insertAddress(address);
        if(rows!=1){
            throw new InsertException("插入用户地址时产生了未知异常");
        }
    }

    @Override
    public List<Address> getByUid(Integer uid) {
        List<Address> list = addressMapper.findByUid(uid);
        // 对数据进行精简
        for(Address ad:list){
            //ad.setAid(null);
            //ad.setUid(null);
            ad.setProvinceCode(null);
            ad.setCityCode(null);
            ad.setAreaCode(null);
            ad.setZip(null);
            ad.setTel(null);
            ad.setIsDefault(null);
            ad.setCreatedTime(null);
            ad.setCreatedUser(null);
            ad.setModifiedTime(null);
            ad.setModifiedUser(null);
        }
        return list;
    }

    @Override
    public void setDefault(Integer aid, Integer uid, String username) {
        // 根据收货地址的aid来判断收货地址是否存在
        Address result = addressMapper.findByAid(aid);
        if(result==null){
            throw new AddressNotFoundException("收货地址不存在");
        }
        //检测获取到的收货地址数据是否归属于当前登录用户
        if(!result.getUid().equals(uid)){
            throw new AccessDeniedException("非法数据访问");
        }

        // 收货地址存在且访问合法，那么根据用户的uid将用户的所有收货地址is_default值都设置为0
        Integer rows = addressMapper.updateNonDefault(uid);
        if(rows < 1){
            throw new UpdateException("更新数据产生未知的错误");
        }

        // 根据aid将收货地址数据的is_default值设置为1
        rows = addressMapper.updateIsDefaultByAid(aid, username, new Date());
        if(rows!=1){
            throw new UpdateException("更新数据产生未知的错误");
        }
    }

    @Override
    public void deleteAddress(Integer aid, Integer uid, String username) {
        // 判断当前aid对应的地址信息是否属于当前登录用户
        Address result = addressMapper.findByAid(aid);
        if(result==null){
            throw new AddressNotFoundException("当前收货地址数据不存在异常");
        }
        if(!result.getUid().equals(uid)){
            throw new AccessDeniedException("非法数据访问");
        }

        // 此后判断result结果的is_Default字段是否为1
        Integer isDefault = result.getIsDefault();
        // 删除当前收货地址数据
        Integer rows = addressMapper.deleteByAid(aid);
        if(rows!= 1){
            throw new DeleteException("删除收货地址数据时产生未知的异常");
        }

        //根据之前记录的isDefault来进行后续的操作
        if(isDefault==1){
            //说明删除了默认收货地址因此需要重新定义新的收货地址
            Address lastModified = addressMapper.findLastModified(uid);
            //判断该uid下的收货地址除了删除的数据外是否还有别的数据，如果没有的话lastModified为null
            if (lastModified!=null){
                // 存在其他数据，将当前最近修改的数据设置为默认数据
                rows = addressMapper.updateIsDefaultByAid(lastModified.getAid(), username, new Date());
                // 判断更新是否成功
                if(rows!=1){
                    throw new UpdateException("更新数据时产生未知的异常");
                }
            }
        }

    }
}
