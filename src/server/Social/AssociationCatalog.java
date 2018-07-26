package server.Social;


import server.InfoCatalog;
import server.InfoObject;

import java.io.*;
import java.util.HashMap;

/**
 * @author yangbo
 *
 * 组织的集合类, 负责维护所有的组织的目录
 * 其中实现的功能是  增删查改, 并且出现敏感操作的时候记录日志
 *
 */
public class AssociationCatalog extends InfoCatalog {
    private static final String fileName="database/association.dat";

    public AssociationCatalog()throws IOException,ClassNotFoundException{
        ObjectInputStream in=new ObjectInputStream(new FileInputStream(fileName));
        Object obj=in.readObject();
        if(obj instanceof HashMap){
            objects=((HashMap<String,InfoObject>)obj);
        }else throw new IOException("Objects Error");
    }
    @Override
    public void add(InfoObject obj) {
        if(obj instanceof Association)objects.put(obj.getCode(),obj);
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
