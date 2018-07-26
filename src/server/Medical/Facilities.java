package server.Medical;

import server.InfoObject;

/**
 *  服务设施项目
 * @author yangbo
 *  服务设施的信息: 医疗服务设施编码 ,服务设施名称 ,收费类别 ,备注 ,限制使用范围
 */


public class Facilities extends InfoObject {
    private String name;    //名字
    private String payType;    //收费类别
    private String remark;  //备注
    private String range;   //限制的范围

    public Facilities(String code, String name, String payType, String remark, String range){
        super(code);
        this.code=code;
        this.name=name;
        this.payType=payType;
        this.remark=remark;
        this.range=range;
    }

    public String getName() { return name; }

    public String getPayType() { return payType; }

    public String getRemark() { return remark; }

    public String getRange() { return range; }

    public void setName(String name) { this.name = name; }

    public void setPayType(String payType) { this.payType = payType; }

    public void setRemark(String remark) { this.remark = remark; }

    public void setRange(String range) { this.range = range; }

    @Override
    public boolean equals(Object obj) {
        return (obj instanceof Facilities) && (((Facilities) obj).getCode().equals(code));
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
