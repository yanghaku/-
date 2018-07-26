package server.Medical;

import server.InfoCatalog;
import server.InfoObject;

import java.io.*;
import java.util.HashMap;

/**
 * @author yangbo
 *
 * 诊疗项目信息集合类, 负责维护诊疗项目的目录
 *
 * 其中行为可以:  增,删,查,改
 *
 */

public class TreatmentCatalog extends InfoCatalog {
    private static final String fileName="database/treament.dat";

    public TreatmentCatalog()throws IOException,ClassNotFoundException{
        ObjectInputStream in=new ObjectInputStream(new FileInputStream(fileName));
        Object obj= in.readObject();
        if(obj instanceof HashMap){
            objects=((HashMap<String,InfoObject>)obj);
        }
    }


    @Override
    public void add(InfoObject obj) {
        if (obj instanceof Treatment) objects.put(obj.getCode(),obj);
    }

    @Override
    public boolean set(String code, InfoObject newObj) {
        InfoObject old=objects.get(code);
        if(old==null)return false;
        return objects.replace(code,old,newObj);
    }

    @Override
    public void update() throws IOException {
        ObjectOutputStream out=new ObjectOutputStream(new FileOutputStream(fileName));
        out.writeObject(objects);
        out.close();
    }

}
