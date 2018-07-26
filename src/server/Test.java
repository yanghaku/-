package server;

import server.Medical.Treatment;

import static server.CalculationParameter.Level.Two;
import static server.RegistedObj.ObjName.Treatment;

public class Test {
    public static void main(String[] args)throws Exception{
        Manage m=new Manage();
        //m.add("111",Treatment,new Treatment("code","aaaaa",Two,Two,"111","111","111","111","111"));

        System.out.println("init");
        Object obj=m.find(Treatment,"code");
        if(obj instanceof Treatment){
            System.out.println(((Treatment) obj).getPayType());
        }
        CalculationParameter cp=m.getParmater();
        System.out.println(cp.getRate(100));
    }
}
