package server;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;

/**
 * @author yangbo
 *
 * 储存信息集合的父类
 *
 * 负责保存每个相应类型对象的数据, 建立索引,
 *
 * 维护信息, 能够进行增删查改
 *
 *
 */

public abstract class InfoCatalog implements Iterable<InfoObject>{

    protected HashMap<String,InfoObject> objects;

    public void delete(String code){
        objects.remove(code);
    }

    public InfoObject find(String code){
        return objects.get(code);
    }

    public abstract void add(InfoObject obj);

    public abstract boolean set(String code,InfoObject obj);

    public abstract void update() throws IOException;

    // 返回值的迭代器, 只可以遍历获取信息, 但是不能修改;
    public Iterator<InfoObject> iterator(){
        return this.objects.values().iterator();
    }
}
