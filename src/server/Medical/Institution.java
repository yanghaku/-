package server.Medical;

import server.InfoObject;
import server.CalculationParameter.Level;
import server.CalculationParameter.HostipalType;
/**
 * @author yangbo
 * 定点医疗机构的信息
 * 其中数据有: 定点医疗机构编号, 服务机构名称, 医院等级, 医疗机构类别, 邮政编码, 法定代表人姓名,
 * 法人代表移动电话,联系人姓名,联系人移动电话,地址,备注
 *
 */
public class Institution extends InfoObject {
    String name;        //机构名字
    Level hospitalLevel;//医院的等级
    HostipalType type;  //医疗机构类别
    int postNum;        //邮政编码
    String legalName;   //法人姓名
    int legalPhoneNum;  //法人电话
    String connectName; //联系人姓名
    int connectPhoneNum;//联系人电话
    String address;     //地址
    String remark;      //备注
    
    public Institution(String code, String name, Level hospitalLevel,HostipalType type,
                       int postNum, String legalName, int legalPhoneNum, String connectName,
                       int connectPhoneNum, String address, String remark){
        super(code);
        this.name=name;
        this.hospitalLevel=hospitalLevel;
        this.type=type;
        this.postNum=postNum;
        this.legalName=legalName;
        this.legalPhoneNum=legalPhoneNum;
        this.connectName=connectName;
        this.connectPhoneNum=connectPhoneNum;
        this.address=address;
        this.remark=remark;
    }


    public String getName(){ return name; }

    public Level getHospitalLevel(){ return hospitalLevel; }

    public HostipalType getType(){return type; }

    public int getPostNum(){ return postNum; }

    public String getLegalName(){ return legalName; }

    public int getLegalPhoneNum(){ return legalPhoneNum; }

    public String getConnectName(){ return connectName; }

    public int getConnectPhoneNum(){ return connectPhoneNum; }

    public String getAddress(){ return address; }

    public String getRemark(){ return remark; }

    public void setName(String name){ this.name = name; }

    public void setHospitalLevel(Level hospitalLevel){ this.hospitalLevel = hospitalLevel; }

    public void setType(HostipalType type){ this.type = type; }

    public void setLegalName(String legalName){ this.legalName = legalName; }

    public void setLegalPhoneNum(int legalPhoneNum){ this.legalPhoneNum = legalPhoneNum; }

    public void setPostNum(int postNum){ this.postNum = postNum; }

    public void setConnectName(String connectName){ this.connectName = connectName; }

    public void setConnectPhoneNum(int connectPhoneNum){ this.connectPhoneNum = connectPhoneNum; }

    public void setAddress(String address){ this.address = address; }

    public void setRemark(String remark){ this.remark = remark; }

    @Override
    public boolean equals(Object obj) {
        return (obj instanceof Institution)&&(((Institution) obj).getCode().equals(code));
    }

    @Override
    public String toString() {
        return super.toString();
    }
}