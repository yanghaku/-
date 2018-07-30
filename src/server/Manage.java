package server;

import java.io.IOException;
import java.util.Date;
import java.util.Iterator;
import java.util.TreeMap;
import server.RegistedObj.ObjName;
import server.Log.Log;

/**
 * @author yangbo
 *
 * 数据库服务器的管理, 对内负责管理所有数据, 对外负责提供数据查改的接口服务
 *
 * 同时管理器负责记录日志, 记录所有敏感操作等
 *
 * 对内的数据包括 医疗基本信息, 公共业务 两大模块的数据;
 *
 * 这个manage类的主要作用就是对外提供数据服务接口, 并且对操作权限控制检查, 同时将日志记录
 *
 *  文件型的数据库, 将数据库某项内容保存在单个索引文件里面, 与传统的数据库相比,
 *  数据量少的时候有速度优势, 但是数据量大了就不行了, 因为数据库初始化的时候需要把文件全部load到内存中(<=100MB)
 *
 */


public class Manage{

    private TreeMap<ObjName,InfoCatalog> catalogs;
    private CalculationParameter calculationParameter;// 计算参数简单而且数据少, 因此单独维护
    private OperatorID operator;                      // 操作员的集合, 单独维护

    public Manage() throws IOException,ClassNotFoundException{
            operator=new OperatorID();
            catalogs=RegistedObj.init();
            calculationParameter=new CalculationParameter();
    }

    public boolean set(String idName,ObjName objname,String code,InfoObject newObj){
        Log.addLog((new Date()).toString()+"_"+idName+"_set_"+objname.toString()+"_"+code+"\r\n");
        return catalogs.get(objname).set(code,newObj);
    }

    public void delete(String idName,ObjName objName,String code){
        Log.addLog((new Date()).toString()+"_"+idName+"_delete_"+objName.toString()+"_"+code+"\r\n");
        catalogs.get(objName).delete(code);
    }

    public InfoObject find(ObjName objName,String code){
        return catalogs.get(objName).find(code);
    }

    public void add(String idName,ObjName objName,InfoObject obj) {
        Log.addLog((new Date()).toString()+"_"+idName+"_add_"+objName.toString()+"_"+obj.code+"\r\n");
        catalogs.get(objName).add(obj);
    }

    public Iterator<InfoObject> getAll(ObjName objName){
        return this.catalogs.get(objName).iterator();
    }

    public void update()throws IOException{
        for(int i=0;i<RegistedObj.ObjList.length;++i){
            catalogs.get(RegistedObj.ObjList[i]).update();
        }
    }

    public CalculationParameter getParmater(){
        return this.calculationParameter;
    }
    public void setParameter(String Id,CalculationParameter newObj)throws IOException{
        Log.addLog((new Date()).toString()+"_"+Id+"_set_calculationParameter\r\n");
        this.calculationParameter=newObj;
        this.calculationParameter.update();
    }

    public OperatorID getOperator() {
        return operator;
    }

    /**
     *   数据库的初始化(重新构建) 所有文件都会清空, 系统管理员才可用
     */
    public void clearAndBuild(String Id)throws IOException{
        Log.addLog((new Date()).toString()+"_"+Id+"_cleardatabase\r\n");
        RegistedObj.clearAndBuild();
    }
}
