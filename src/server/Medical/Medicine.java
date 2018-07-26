package server.Medical;

import server.InfoObject;
import server.CalculationParameter.Level;
/**
 * 药品类
 * 其中数据包括: 药品编码, 收费类别, 处方药标志, 收费项目等级,
 * 药品名称, 英文名称, 药品剂量单位, 是否需要审批标志, 医院等级,
 * 最高限价, 院内制剂标志, 剂型, 使用频次, 用法, 限定天数, 药品商名,
 * 药厂名称, 国药准字, 备注, 国家目录编码, 限制使用范围, 产地;
 *
 * 实现的方法: set与get
 */

public class Medicine extends InfoObject {

    private String payType;          //收费类别
    private boolean isPrescription; //处方药
    private Level payLevel;         //收费项目等级, 其中甲类100%, 乙类 50%, 丙类 0%
    private String cName;           //中文名
    private String eName;           //英文名
    private boolean needCheck;      //是否需要审批
    private Level hospitalLevel;    //医院等级
    private double upperBound;      //最高限价
    private boolean isFromHospital; //院内制剂标志
    private String type;            //药品剂型
    private String unit;            //药品计量单位
    private int frequency;          //使用频次
    private String usage;           //用法
    private String upperDays;       //限定天数
    private String productedName;   //药厂名称
    private String productedArea;   //产地
    private String countryCheck;    //国药准字
    private String countryCode;     //国家目录编码
    private String remark;          //备注


    public Medicine(String code,String payType,boolean isPrescription,Level payLevel,
                    String cName,String eName,boolean needCheck, Level hospitalLevel,
                    double upperBound,boolean isFromHospital, String type,String unit,
                    int frequency,String usage,String upperDays, String productedName,
                    String productedArea,String countryCheck, String countryCode,String remark){
        super(code);
        this.payType=payType;
        this.isPrescription=isPrescription;
        this.payLevel=payLevel;
        this.cName=cName;
        this.eName=eName;
        this.needCheck=needCheck;
        this.hospitalLevel=hospitalLevel;
        this.upperBound=upperBound;
        this.isFromHospital=isFromHospital;
        this.type=type;
        this.unit=unit;
        this.frequency=frequency;
        this.usage=usage;
        this.upperDays=upperDays;
        this.productedName=productedName;
        this.productedArea=productedArea;
        this.countryCheck=countryCheck;
        this.countryCode=countryCode;
        this.remark=remark;
    }

    public String getPayType(){ return payType; }

    public boolean isPrescription(){return isPrescription; }

    public Level getPayLevel(){ return payLevel; }

    public String getcName() { return cName; }

    public String geteName(){ return eName; }

    public Level getHospitalLevel() { return hospitalLevel; }

    public boolean isNeedCheck() { return needCheck; }

    public double getUpperBound() { return upperBound; }

    public boolean isFromHospital() { return isFromHospital; }

    public String getType() { return type;}

    public String getUnit() {return unit; }

    public int getFrequency() { return frequency; }

    public String getUsage() { return usage; }

    public String getUpperDays(){ return upperDays; }

    public String getProductedName() { return productedName; }

    public String getProductedArea() { return productedArea; }

    public String getCountryCheck() { return countryCheck; }

    public String getCountryCode() { return countryCode; }

    public String getRemark() { return remark; }

    public void setPayType(String payType) { this.payType = payType; }

    public void setPrescription(boolean prescription) { isPrescription = prescription; }

    public void setPayLevel(Level payLevel) { this.payLevel = payLevel; }

    public void setcName(String cName){ this.cName=cName; }

    public void seteName(String eName){ this.eName=eName; }

    public void setNeedCheck(boolean needCheck) { this.needCheck = needCheck; }

    public void setHospitalLevel(Level hospitalLevel) { this.hospitalLevel = hospitalLevel; }

    public void setUpperBound(double upperBound) { this.upperBound = upperBound; }

    public void setFromHospital(boolean fromHospital) { isFromHospital = fromHospital; }

    public void setType(String type) { this.type = type; }

    public void setUnit(String unit) { this.unit = unit; }

    public void setFrequency(int frequency) { this.frequency = frequency; }

    public void setUsage(String usage) { this.usage = usage; }

    public void setUpperDays(String upperDays) { this.upperDays = upperDays; }

    public void setProductedName(String productedName) { this.productedName = productedName; }

    public void setProductedArea(String productedArea) { this.productedArea = productedArea; }

    public void setCountryCheck(String countryCheck) { this.countryCheck = countryCheck; }

    public void setCountryCode(String countryCode) { this.countryCode = countryCode; }

    public void setRemark(String remark) { this.remark = remark; }

    @Override
    public boolean equals(Object obj) {
        return (obj instanceof Medicine)&&(((Medicine) obj).getCode().equals(code));
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
