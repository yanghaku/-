package server.Social;


import server.InfoCatalog;
import server.InfoObject;

import java.io.*;
import java.util.HashMap;

/**
 * @author yangbo
 *
 * 个人信息的集合类, 负责维护所有人的信息的目录
 *
 * 其中实现的功能是   增删查改
 */

public class PersonCatalog extends InfoCatalog {
    private static final String fileName="database/person.dat";


    public PersonCatalog() throws IOException,ClassNotFoundException{
        ObjectInputStream in=new ObjectInputStream(new FileInputStream(fileName));
        Object obj=in.readObject();
        if(obj instanceof HashMap){
            objects=((HashMap<String,InfoObject>)obj);
        }else throw new IOException("Objects Error");
    }
    @Override
    public void add(InfoObject obj) {
        if(obj instanceof Person)objects.put(obj.getCode(),obj);
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
