package server.baoxiao;

import server.InfoCatalog;
import server.InfoObject;
import java.io.*;
import java.util.HashMap;

public class PrescriptionCatalog extends InfoCatalog {
    private static final String fileName="database/prescription.dat";

    public PrescriptionCatalog()throws IOException,ClassNotFoundException{
        ObjectInputStream in=new ObjectInputStream(new FileInputStream(fileName));
        Object obj=in.readObject();
        if(obj instanceof HashMap){
            objects= ((HashMap<String,InfoObject>) obj);
        }else throw new IOException("Objects Error");
    }

    @Override
    public void add(InfoObject obj) {
        if(obj instanceof Prescription)objects.put(obj.getCode(),obj);
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