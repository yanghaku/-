package server.Social;

import server.InfoObject;
import server.CalculationParameter.PersonType;

import java.util.Date;

/**
 * @author yangbo
 *
 * 个人信息, 其中数据包括:
 * 个人 ID,证件类型,证件编号,姓名,性别,民族,出生日期,参加工作日期,离退休日期,离退休状态,户口所在地,
 * 文化程度,政治面貌,个人身份,用工形式,专业技术职务,国家职业资格等级(工人技术等),婚姻状况,行政职务,备注,
 * 单位编码,医疗人员类别,健康状况,劳模标志,干部标志,公务员标志,在编标志,居民类别,灵活就业标志,农民工标志,
 * 雇主标志,军转人员标志,社保卡号(系统自动生成),定点医疗机构编码
 */
public class Person extends InfoObject {
    private String idType;      // id类型
    private String idNum;       // id编号
    private String name;        // 姓名
    private PersonType type;    // 医疗人员类别
    private boolean Man;        // 性别
    private String race;        // 民族
    private Date birthDate;     // 出生日期
    private Date workDate;      // 工作日期
    private Date retire;        // 退休日期
    private boolean retired;    // 是否退休
    private String idArea;      // 户口所在地
    private String educated;    // 文化程度
    private String political;   // 政治面貌
    private String workForm;    // 工作形式
    private String remark;      // 备注
    private String associationCode;//单位编码
    private String health;      // 健康状况
    private boolean modelWork;  // 劳模标志
    private boolean leader;     // 干部标准
    private boolean inDataBase; // 在编标志
    private boolean farmer;     // 农民标志
    private boolean soldier;    // 军转人员标志
    private String protectId;   // 社保卡号
    private String instituton;  //定点医疗机构编号

    /*
        以下信息为个人在医保中心报销的信息,
    本年住院次数、上次出院时间、上次出院诊断、本年中心报销累计、本年个人自费累计、本年医疗费用累计
    */
    private int hospitalNum;  //住院次数
    private Date lastHospitalDate;//上次出院时间
    private String lastHospitalStatus;//上次住院诊断
    private double baoXiao;  //本年度中心报销累计
    private double ziFei;   // 本年度个人自费累计
    private double total;   // 本年度医疗费用累计


    public Person( String code,String idType,String idNum,String name,PersonType type,boolean Man,String race, Date birthDate,
           Date workDate,Date retire,boolean retired,String idArea,String educated,String political,
           String workForm,String remark,String associationCode,String health,boolean modelWork,
           boolean leader,boolean inDataBase, boolean farmer, boolean soldier,String instituton){
        super(code);
        this.idType=idType;
        this.idNum=idNum;
        this.name=name;
        this.Man=Man;
        this.race=race;
        this.birthDate=birthDate;
        this.workDate=workDate;
        this.remark=remark;
        this.retire=retire;
        this.retired=retired;
        this.idArea=idArea;
        this.educated=educated;
        this.political=political;
        this.workForm=workForm;
        this.associationCode=associationCode;
        this.type=type;
        this.health=health;
        this.modelWork=modelWork;
        this.leader=leader;
        this.inDataBase=inDataBase;
        this.farmer=farmer;
        this.soldier=soldier;
        this.instituton=instituton;
        this.protectId=null;
    }

    public PersonType getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public String getRemark() {
        return remark;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public Date getRetire() {
        return retire;
    }

    public Date getWorkDate() {
        return workDate;
    }

    public String getAssociationCode() {
        return associationCode;
    }

    public String getEducated() {
        return educated;
    }

    public String getHealth() {
        return health;
    }

    public String getIdArea() {
        return idArea;
    }

    public String getIdNum() {
        return idNum;
    }

    public String getInstituton() {
        return instituton;
    }

    public void setInstituton(String instituton) {
        this.instituton = instituton;
    }

    public String getIdType() {
        return idType;
    }

    public String getPolitical() {
        return political;
    }

    public String getProtectId() {
        return protectId;
    }

    public String getRace() {
        return race;
    }

    public String getWorkForm() {
        return workForm;
    }

    public boolean isModelWork() {
        return modelWork;
    }

    public boolean isFarmer() {
        return farmer;
    }

    public boolean isRetired() {
        return retired;
    }

    public boolean isInDataBase() {
        return inDataBase;
    }

    public boolean isLeader() {
        return leader;
    }

    public boolean isSoldier() {
        return soldier;
    }

    public void setType(PersonType type) {
        this.type = type;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAssociationCode(String associationCode) {
        this.associationCode = associationCode;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public void setEducated(String educated) {
        this.educated = educated;
    }

    public void setFarmer(boolean farmer) {
        this.farmer = farmer;
    }

    public void setHealth(String health) {
        this.health = health;
    }

    public void setIdNum(String idNum) {
        this.idNum = idNum;
    }

    public void setIdType(String idType) {
        this.idType = idType;
    }

    public void setIdArea(String idArea) {
        this.idArea = idArea;
    }

    public void setInDataBase(boolean inDataBase) {
        this.inDataBase = inDataBase;
    }

    public void setLeader(boolean leader) {
        this.leader = leader;
    }

    public void setMan(boolean man) {
        Man = man;
    }

    public void setModelWork(boolean modelWork) {
        this.modelWork = modelWork;
    }

    public void setPolitical(String political) {
        this.political = political;
    }

    public void setProtectId(String protectId) {
        this.protectId = protectId;
    }

    public void setRace(String race) {
        this.race = race;
    }

    public void setRetire(Date retire) {
        this.retire = retire;
    }

    public void setRetired(boolean retired) {
        this.retired = retired;
    }

    public void setSoldier(boolean soldier) {
        this.soldier = soldier;
    }

    public void setWorkDate(Date workDate) {
        this.workDate = workDate;
    }

    public boolean isMan() {
        return Man;
    }

    public void setWorkForm(String workForm) {
        this.workForm = workForm;
    }


    @Override
    public boolean equals(Object obj) {
        return (obj instanceof Person) && (((Person) obj).getCode().equals(code));
    }


    public Date getLastHospitalDate() {
        return lastHospitalDate;
    }

    public double getBaoXiao() {
        return baoXiao;
    }

    public double getTotal() {
        return total;
    }

    public double getZiFei() {
        return ziFei;
    }

    public void setLastHospitalDate(Date lastHospitalDate) {
        this.lastHospitalDate = lastHospitalDate;
    }

    public int getHospitalNum() {
        return hospitalNum;
    }

    public void setZiFei(double ziFei) {
        this.ziFei = ziFei;
    }

    public String getLastHospitalStatus() {
        return lastHospitalStatus;
    }

    public void setBaoXiao(double baoXiao) {
        this.baoXiao = baoXiao;
    }

    public void setHospitalNum(int hospitalNum) {
        this.hospitalNum = hospitalNum;
    }

    public void setLastHospitalStatus(String lastHospitalStatus) {
        this.lastHospitalStatus = lastHospitalStatus;
    }

    public void setTotal(double total) {
        this.total = total;
    }

}
