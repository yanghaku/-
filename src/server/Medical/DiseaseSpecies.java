package server.Medical;

import server.InfoObject;

/**
 * @author yangbo
 * 病种信息
 * 其中的数据有:疾病编码,病种名称,疾病种类,病种报销标志,备注
 */

public class DiseaseSpecies extends InfoObject {
    private String name;            //名字
    private String type;            //类型
    private boolean reimbursement;  //是否能报销
    private String remark;          //备注

    public DiseaseSpecies(String code,String name,String type,
                          boolean reibursement,String remark) {
        super(code);
        this.code = code;
        this.name = name;
        this.type = type;
        this.reimbursement = reibursement;
        this.remark = remark;
    }

    public String getName(){ return name; }

    public String getType(){ return type; }

    public boolean isReimbursement(){ return reimbursement; }

    public String getRemark(){ return remark; }

    public void setName(String name){ this.name = name; }

    public void setType(String type){ this.type = type; }

    public void setReimbursement(boolean reimbursement){ this.reimbursement = reimbursement; }

    public void setRemark(String remark){ this.remark = remark; }

    @Override
    public boolean equals(Object obj) {
        return (obj instanceof DiseaseSpecies)&&(((DiseaseSpecies) obj).getCode().equals(code));
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
