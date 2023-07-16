package com.cy.store.entity;

import java.io.Serializable;
import java.util.Objects;

/**收货地址数据的实体类*/
public class Address extends BaseEntity implements Serializable {
    private Integer aid; //'收货地址id',
    private Integer uid; //'归属的用户id',
    private String name;//'收货人姓名',
    private String provinceName;//'省-名称',
    private String provinceCode;// '省-行政代号',
    private String cityName;//'市-名称',
    private String cityCode;//'市-行政代号',
    private String areaName;//'区-名称',
    private String areaCode;//'区-行政代号',
    private String zip;//'邮政编码',
    private String address;//'详细地址',
    private String phone;//'手机',
    private String tel;//'固话',
    private String tag;//'标签',
    private Integer isDefault;//'是否默认：0-不默认，1-默认',

    public Integer getAid() {
        return aid;
    }

    public void setAid(Integer aid) {
        this.aid = aid;
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProvinceName() {
        return provinceName;
    }

    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
    }

    public String getProvinceCode() {
        return provinceCode;
    }

    public void setProvinceCode(String provinceCode) {
        this.provinceCode = provinceCode;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public String getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public Integer getIsDefault() {
        return isDefault;
    }

    public void setIsDefault(Integer isDefault) {
        this.isDefault = isDefault;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Address adress = (Address) o;
        return Objects.equals(aid, adress.aid) && Objects.equals(uid, adress.uid) && Objects.equals(name, adress.name) && Objects.equals(provinceName, adress.provinceName) && Objects.equals(provinceCode, adress.provinceCode) && Objects.equals(cityName, adress.cityName) && Objects.equals(cityCode, adress.cityCode) && Objects.equals(areaName, adress.areaName) && Objects.equals(areaCode, adress.areaCode) && Objects.equals(zip, adress.zip) && Objects.equals(address, adress.address) && Objects.equals(phone, adress.phone) && Objects.equals(tel, adress.tel) && Objects.equals(tag, adress.tag) && Objects.equals(isDefault, adress.isDefault);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), aid, uid, name, provinceName, provinceCode, cityName, cityCode, areaName, areaCode, zip, address, phone, tel, tag, isDefault);
    }

    @Override
    public String toString() {
        return "Adress{" +
                "aid=" + aid +
                ", uid=" + uid +
                ", name='" + name + '\'' +
                ", provinceName='" + provinceName + '\'' +
                ", provinceCode='" + provinceCode + '\'' +
                ", cityName='" + cityName + '\'' +
                ", cityCode='" + cityCode + '\'' +
                ", areaName='" + areaName + '\'' +
                ", areaCode='" + areaCode + '\'' +
                ", zip='" + zip + '\'' +
                ", address='" + address + '\'' +
                ", phone='" + phone + '\'' +
                ", tel='" + tel + '\'' +
                ", tag='" + tag + '\'' +
                ", isDefault=" + isDefault +
                '}';
    }
}
