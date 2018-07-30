package server.baoxiao;


import server.InfoObject;

import java.util.Date;

/**
 * @author yangbo
 *
 * 结算信息类, 负责保存本次结算的结算信息
 *
 * 费用总额、报销金额、自费金额
 * 起付标准、分段计算中自费金额、报销封顶线、乙类项目自费金额、特检特治自费金额）
 *
 */
public class BillInfomation extends InfoObject {
    private String personid;//人员的id
    private Date time;     //报销时间
    private double total=0;   //总金额
    private double bao=0;    //报销金额
    private double zi=0;     //自费金额
    private double lowbound=0; //起付标准
    private double fenzi=0;   //分段计算中的自费金额
    private double yizi=0;    //乙类项目自费金额
    private double tezi=0;   //特检特治
    public BillInfomation(String code,String id){
        super(code);
        this.personid=id;
        this.time=new Date();
    }

    public void addTotal(double total) {
        this.total += total;
    }

    public double getTotal() {
        return total;
    }

    public void setBao(double bao) {
        this.bao = bao;
    }

    public Date getTime() {
        return time;
    }

    public double getBao() {
        return bao;
    }

    public double getFenzi() {
        return fenzi;
    }

    public double getTezi() {
        return tezi;
    }

    public double getLowbound() {
        return lowbound;
    }

    public double getYizi() {
        return yizi;
    }

    public void addBao(double bao) {
        this.bao += bao;
    }

    public double getZi() {
        return zi;
    }

    public String getPersonid() {
        return personid;
    }

    public void addZi(double zi) {
        this.zi += zi;
    }

    public void addFenzi(double fenzi) {
        this.fenzi += fenzi;
    }

    public void setLowbound(double lowbound) {
        this.lowbound = lowbound;
    }

    public void addTezi(double tezi) {
        this.tezi += tezi;
    }

    public void addYizi(double yizi) {
        this.yizi+=yizi;
    }
}
