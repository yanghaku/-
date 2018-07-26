package server.Medical;

import server.InfoObject;
import server.CalculationParameter.Level;
/**
 *  @author yangbo
 *
 *  诊疗项目信息
 *  项目编码, 收费类别, 收费项目等级, 医院等级, 是否需要审批标志,
 *  项目名称, 单位, 生产厂家, 备注, 限制使用范围
 */
public class Treatment extends InfoObject{
    private String payType;       //收费类别
    private Level payLevel;       //收费项目等级
    private Level hospitalLevel;  //医院等级
    private boolean needCheck;    //是否需要审批
    private String name;          //项目名称
    private String unit;          //单位
    private String productedName; //生产厂家
    private String remark;        //备注
    private String range;         //限制使用的范围

    public Treatment(String code, String payType, Level payLevel, Level hospitalLevel,
                     String name, String unit, String productedName,
                     String remark, String range){
        super(code);
        this.payType=payType;
        this.payLevel=payLevel;
        this.hospitalLevel=hospitalLevel;
        this.needCheck=needCheck;
        this.name=name;
        this.unit=unit;
        this.productedName=productedName;
        this.remark=remark;
        this.range=range;
    }


    public String getPayType(){return this.payType;}

    public Level getPayLevel(){return this.payLevel;}

    public Level getHospitalLevel(){return this.hospitalLevel;}

    public boolean isNeedCheck() { return needCheck;}

    public String getName(){return this.name;}

    public String getUnit(){return this.unit;}

    public String getProductedName(){return this.productedName;}

    public String getRemark(){return this.remark;}

    public String getRange(){return this.range;}

    public void setPayType(String payType){ this.payType = payType; }

    public void setPayLevel(Level payLevel){ this.payLevel = payLevel; }

    public void setHospitalLevel(Level hospitalLevel){ this.hospitalLevel = hospitalLevel; }

    public void setNeedCheck(boolean needCheck){ this.needCheck = needCheck; }

    public void setName(String name){ this.name = name; }

    public void setUnit(String unit){ this.unit = unit; }

    public void setProductedName(String productedName){ this.productedName = productedName; }

    public void setRemark(String remark) { this.remark = remark; }

    public void setRange(String range){ this.range = range; }

    @Override
    public boolean equals(Object obj) {
        return (obj instanceof Treatment) && code.equals(((Treatment) obj).getCode());
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
