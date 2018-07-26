package server;

import server.Medical.*;
import server.Social.*;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.TreeMap;
import java.util.HashMap;

/**
 * @author yangbo
 *
 * 数据类的注册, 增加新的数据类的时候, 注册到这个类里面,就可以被 manage 管理
 *
 */

public class RegistedObj {
    public enum ObjName{
        Medicine,
        Treatment,
        Institution,
        Facilities,
        DiseaseSpecies,
        Person,
        Association,
    };
    public static final ObjName[] ObjList= new ObjName[]{
            ObjName.Medicine,
            ObjName.Treatment,
            ObjName.Institution,
            ObjName.Facilities,
            ObjName.DiseaseSpecies,
            ObjName.Person,
            ObjName.Association,
    };
    public static TreeMap<ObjName,InfoCatalog> init() throws IOException,ClassNotFoundException {
        TreeMap<ObjName,InfoCatalog> ans= new TreeMap<>();

        ans.put(ObjName.Medicine,new MedicineCatalog());
        ans.put(ObjName.Treatment,new TreatmentCatalog());
        ans.put(ObjName.Institution,new InstitutionCatalog());
        ans.put(ObjName.Facilities,new FacilitiesCatalog());
        ans.put(ObjName.DiseaseSpecies,new DiseaseSpeciesCatalog());
        ans.put(ObjName.Person,new PersonCatalog());
        ans.put(ObjName.Association,new AssociationCatalog());

        return ans;
    }
    public static void clearAndBuild()throws IOException{
        ObjectOutputStream out=new ObjectOutputStream(new FileOutputStream("database/medicine.dat"));
        out.writeObject(new HashMap<String,InfoObject>());
        out.close();
        out=new ObjectOutputStream(new FileOutputStream("database/treament.dat"));
        out.writeObject(new HashMap<String,InfoObject>());
        out.close();
        out=new ObjectOutputStream(new FileOutputStream("database/institution.dat"));
        out.writeObject(new HashMap<String,InfoObject>());
        out.close();
        out=new ObjectOutputStream(new FileOutputStream("database/diseaseSpecies.dat"));
        out.writeObject(new HashMap<String,InfoObject>());
        out.close();
        out=new ObjectOutputStream(new FileOutputStream("database/association.dat"));
        out.writeObject(new HashMap<String,InfoObject>());
        out.close();
        out=new ObjectOutputStream(new FileOutputStream("database/person.dat"));
        out.writeObject(new HashMap<String,InfoObject>());
        out.close();
        out=new ObjectOutputStream(new FileOutputStream("database/facilities.dat"));
        out.writeObject(new HashMap<String,InfoObject>());
        out.close();
    }
}
