package server.Medical;

import server.InfoCatalog;
import server.InfoObject;

import java.io.*;
import java.util.HashMap;

/**
 * @author yangbo
 *
 * 定点医疗机构信息集合类, 负责维护所以定点医疗机构的目录
 *
 * 其中行为可以:  增,删,查,改
 *
 */

public class InstitutionCatalog extends InfoCatalog {
    private static final String fileName="database/institution.dat";


    public InstitutionCatalog() throws IOException,ClassNotFoundException{
        ObjectInputStream in=new ObjectInputStream(new FileInputStream(fileName));
        Object obj=in.readObject();
        if( obj instanceof HashMap){
            objects=((HashMap<String,InfoObject>)obj);
        }else throw new IOException("Objects Error");
        in.close();
    }
    @Override
    public void add(InfoObject obj) {
        if(obj instanceof Institution)objects.put(obj.getCode(),obj);
    }

    @Override
    public boolean set(String code, InfoObject obj) {
        InfoObject old=objects.get(code);
        if(old==null)return false;
        return objects.replace(code,old,obj);
    }

    @Override
    public void update() throws IOException {
        ObjectOutputStream out=new ObjectOutputStream(new FileOutputStream(fileName));
        out.writeObject(objects);
        out.close();
    }
}
