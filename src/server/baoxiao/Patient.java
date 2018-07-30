package server.baoxiao;


import server.CalculationParameter.*;
import server.InfoObject;
import java.util.Date;

/**
 * @author yangbo
 *
 * 人员就诊信息
 * 其中的数据包括:
 *   人员ID, 住院号(门诊号), 医疗类别, 定点医疗机构编号（医院编号）,定点医疗机构名称（医院名称
 *   入院日期,出院日期,医院等级,出院原因
 *   病种编码,入院诊断疾病名称
 *
 *
 */
public class Patient extends InfoObject {

    private String personId;  //人员id
    private PersonType personType;// 医疗人员类别
    private String institutionId; //定点医疗机构编号
    private String institutionName;//定点医疗机构名称
    private Date inDate;        //入院日期
    private Date outDate;       //出院日期
    private Level hospitalLevel; //医院等级
    private String reason;      //出院原因
    private String diseaseId;  //疾病编码
    private String diseaseName;//疾病名称
    private boolean done;      //是否报销了

    public Patient(String code,String personId,PersonType type,String institutionId,String institutionName,
                    Date inData,Date outDate,Level hospitalLevel,String reason,String diseaseId,String diseaseName){
        super(code);
        this.diseaseId=diseaseId;
        this.diseaseName=diseaseName;
        this.hospitalLevel=hospitalLevel;
        this.inDate=inData;
        this.outDate=outDate;
        this.personId=personId;
        this.institutionId=institutionId;
        this.institutionName=institutionName;
        this.personType=type;
        this.reason=reason;
        this.done=false;
    }

    public PersonType getPersonType() {
        return personType;
    }

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }

    public void setPersonType(PersonType personType) {
        this.personType = personType;
    }

    public void setHospitalLevel(Level hospitalLevel) {
        this.hospitalLevel = hospitalLevel;
    }

    public Level getHospitalLevel() {
        return hospitalLevel;
    }

    public Date getInDate() {
        return inDate;
    }

    public Date getOutDate() {
        return outDate;
    }

    public String getDiseaseId() {
        return diseaseId;
    }

    public String getDiseaseName() {
        return diseaseName;
    }

    public String getInstitutionId() {
        return institutionId;
    }

    public String getInstitutionName() {
        return institutionName;
    }

    public String getPersonId() {
        return personId;
    }

    public String getReason() {
        return reason;
    }

    public void setDiseaseId(String diseaseId) {
        this.diseaseId = diseaseId;
    }

    public void setDiseaseName(String diseaseName) {
        this.diseaseName = diseaseName;
    }

    public void setInDate(Date inDate) {
        this.inDate = inDate;
    }

    public void setInstitutionId(String institutionId) {
        this.institutionId = institutionId;
    }

    public void setInstitutionName(String institutionName) {
        this.institutionName = institutionName;
    }

    public void setOutDate(Date outDate) {
        this.outDate = outDate;
    }

    public void setPersonId(String personId) {
        this.personId = personId;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Patient)return this.code.equals(((Patient) obj).getCode());
        return false;
    }

}
