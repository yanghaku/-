package server;

import java.io.*;
import java.util.StringTokenizer;

/**
 * @author yangbo
 *
 * 考虑到医疗参数类别较少, 数据也固定, 因此使用的是单个类来储存即可
 *
 * 数据操作直接由manage来调用
 *
 * 其中数据有:医疗人员类别,封顶线,医疗类别,报销比例等
 *
 */

public class CalculationParameter {
    // 以下枚举类型, 供系统使用
    public enum Level{
        Zero,One,Two,Three,Four,
    };
    public enum HostipalType{  // 医院类别
        Hospital,Community//医院, 社区
    }
    public enum PersonType {     // 医疗人员类别
        Oridary, Worked, Retired, LowWorked, LowRetired
        //在职职工,退休人员 ,享受最低保障的在职人员 ,享受最低保障的退休人员
    }

    private static final String fileName="database/parameter.txt";

    private double workedUp;  //在职员工的上限
    private double retiredUp; //退休人员的上限
    private double LworkedUp; //低保在职上限
    private double LretiredUp;//低保退休职工上限

    private double LowerBound; // 起付标准
    private double level1;     // 第一个边界
    private double rate1;      // 在[ lowerBound, level1 ] 内的比例
    private double level2;     // 第二个边界
    private double rate2;      // 在[ level1, level2 ] 内的比例
    private double rate3;      // 在[ level2, 封顶线 ] 内的比例


    public double getRate(double money) {
        if (money <= LowerBound) return 1;
        if (money <= level1) return rate1;
        if (money <= level2) return rate2;
        return rate3;
    }
    public double getUpperBound(PersonType pt) {//通过医疗人员类别获得对应的上限
        if(pt.equals(PersonType.LowRetired))return LretiredUp;
        if(pt.equals(PersonType.LowWorked))return LworkedUp;
        if(pt.equals(PersonType.Retired))return retiredUp;
        return workedUp;
    }

    // 设为包内可见, 只能由manage 确认权限之后调用
    void update()throws IOException {
        FileWriter fw=new FileWriter(fileName);
        fw.write(toString());
        fw.close();
    }
    public CalculationParameter(double workedUp,double retiredUp,double lretiredUp,double lworkedUp,
                double lowerBound,double level1,double level2,double rate1,double rate2,double rate3){
        this.workedUp=workedUp;
        this.retiredUp=retiredUp;
        this.LretiredUp=lretiredUp;
        this.LworkedUp=lworkedUp;
        this.LowerBound=lowerBound;
        this.level1=level1;
        this.level2=level2;
        this.rate1=rate1;
        this.rate2=rate2;
        this.rate3=rate3;
    }
    public CalculationParameter()throws IOException{
        BufferedReader br=new BufferedReader(new FileReader(fileName));
        StringTokenizer st=new StringTokenizer(br.readLine(),"_");
        br.close();
        workedUp=Double.parseDouble(st.nextToken());
        retiredUp=Double.parseDouble(st.nextToken());
        LretiredUp=Double.parseDouble(st.nextToken());
        LworkedUp=Double.parseDouble(st.nextToken());
        LowerBound=Double.parseDouble(st.nextToken());
        level1=Double.parseDouble(st.nextToken());
        level2=Double.parseDouble(st.nextToken());
        rate1=Double.parseDouble(st.nextToken());
        rate2=Double.parseDouble(st.nextToken());
        rate3=Double.parseDouble(st.nextToken());
    }

    public double getLevel1() {
        return level1;
    }

    public double getLevel2() {
        return level2;
    }

    public double getLowerBound() {
        return LowerBound;
    }

    public double getLretiredUp() {
        return LretiredUp;
    }

    public double getLworkedUp() {
        return LworkedUp;
    }

    public double getRate1() {
        return rate1;
    }

    public double getRate2() {
        return rate2;
    }

    public double getRate3() {
        return rate3;
    }

    public double getRetiredUp() {
        return retiredUp;
    }

    public double getWorkedUp() {
        return workedUp;
    }

    public void setLevel1(double level1) {
        this.level1 = level1;
    }

    public void setLevel2(double level2) {
        this.level2 = level2;
    }

    public void setLowerBound(double lowerBound) {
        LowerBound = lowerBound;
    }

    public void setRetiredUp(double retiredUp) {
        this.retiredUp = retiredUp;
    }

    public void setLretiredUp(double lretiredUp) {
        LretiredUp = lretiredUp;
    }

    public void setLworkedUp(double lworkedUp) {
        LworkedUp = lworkedUp;
    }

    public void setRate1(double rate1) {
        this.rate1 = rate1;
    }

    public void setRate2(double rate2) {
        this.rate2 = rate2;
    }

    public void setRate3(double rate3) {
        this.rate3 = rate3;
    }

    public void setWorkedUp(double workedUp) {
        this.workedUp = workedUp;
    }

    @Override
    public String toString() {
         /*
        文件的格式: 在职员工的上限_退休人员的上限_低保在职上限_低保退休职工上限
                    _最低标准_level1_level2_rate1_rate2_rate3

          例: 200000_150000_120000_100000_100_10000_20000_0.2_0.1_0.05

         */
        StringBuilder s=new StringBuilder();
        s.append(workedUp);s.append('_');
        s.append(retiredUp);s.append('_');
        s.append(LworkedUp);s.append('_');
        s.append(LretiredUp);s.append('_');
        s.append(this.LowerBound);s.append('_');
        s.append(level1);s.append('_');
        s.append(level2);s.append('_');
        s.append(rate1);s.append('_');
        s.append(rate2);s.append('_');
        s.append(rate3);s.append('\n');
        return s.toString();
    }
}