package server.baoxiao;

import server.InfoCatalog;
import server.InfoObject;

import java.io.*;
import java.util.HashMap;

/**
 * @author yangbo
 *
 * 就诊信息的集合类, 负责维护所有的就诊信息
 *
 */

public class PatientCatalog extends InfoCatalog {
    private static final String fileName="database/patient.dat";

    public PatientCatalog()throws IOException,ClassNotFoundException{
        ObjectInputStream in=new ObjectInputStream(new FileInputStream(fileName));
        Object obj=in.readObject();
        if(obj instanceof HashMap){
            objects= ((HashMap<String,InfoObject>) obj);
        }else throw new IOException("Object Error");
    }
    @Override
    public void add(InfoObject obj) {
        if(obj instanceof Patient)objects.put(obj.getCode(),obj);
    }

    @Override
    public boolean set(String code, InfoObject obj) {
        InfoObject old=objects.get(code);
        if(old==null)return false;
        else return objects.replace(code,old,obj);
    }

    @Override
    public void update() throws IOException {
        ObjectOutputStream out=new ObjectOutputStream(new FileOutputStream(fileName));
        out.writeObject(objects);
        out.close();
    }

}