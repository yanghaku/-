package server.baoxiao;

import server.InfoObject;

/**
 * @author yangbo
 *
 * 处方类: 其中保存的数据有: 住院号（门诊号），项目编码, 项目类别, 单价,数量,金额
 */

public class Prescription extends InfoObject {

    private String id;    // 住院号
    private String contentId; //项目编号;
    private String contentType; //项目类型
    private double unitPrice; //单价
    private int  num;       //数量
    private double price;   //金额
    private String personId;

    public Prescription(String code,String id,String contentId,String contentType,double unitPrice,int num,String personId){
        super(code);
        this.id=id;
        this.contentId=contentId;
        this.contentType=contentType;
        this.unitPrice=unitPrice;
        this.num=num;
        this.price=unitPrice*num;
        this.personId=personId;
    }


    public double getPrice() {
        return price;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public String getContentId() {
        return contentId;
    }

    public int getNum() {
        return num;
    }

    public String getId() {
        return id;
    }

    public String getContentType() {
        return contentType;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public void setContentId(String contentId) {
        this.contentId = contentId;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setPersonId(String personId) {
        this.personId = personId;
    }

    public String getPersonId() {
        return personId;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Prescription)return this.equals(((Prescription) obj).getCode());
        return false;
    }
}
