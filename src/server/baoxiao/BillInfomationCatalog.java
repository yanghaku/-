package server.baoxiao;

import server.InfoCatalog;
import server.InfoObject;
import java.io.*;
import java.util.HashMap;


/**
 * @author yangbo
 *
 * 结算信息的维护类
 *
 */

public class BillInfomationCatalog extends InfoCatalog {
    private static final String filename="database/billinfrmation.dat";


    public BillInfomationCatalog()throws IOException,ClassNotFoundException{
        ObjectInputStream in=new ObjectInputStream(new FileInputStream(filename));
        Object obj=in.readObject();
        if(obj instanceof HashMap){
            objects= ((HashMap<String,InfoObject>) obj);
        }else throw new IOException("Object Error");
    }

    @Override
    public void add(InfoObject obj) {
        if(obj instanceof BillInfomation)objects.put(obj.getCode(),obj);
    }

    @Override
    public boolean set(String code, InfoObject obj) {
        InfoObject old=objects.get(code);
        if(old==null)return false;
        else return objects.replace(code,old,obj);
    }


    @Override
    public void update() throws IOException {
        ObjectOutputStream out=new ObjectOutputStream(new FileOutputStream(filename));
        out.writeObject(objects);
        out.close();
    }
}

