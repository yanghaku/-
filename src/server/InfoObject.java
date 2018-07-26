package server;

import java.io.Serializable;

/**
 * @author yangbo
 *
 * 所有数据类的父类, 当数据可进入数据库存储时, 需要继承这个类
 *
 * 其中数据包括code, 唯一的标识码,一旦创建不可更改
 *
 * 其中实现了Serializable接口, 用于序列化和反序列化,用objectstream存,读
 *
 * 考虑到查询效率, 每个数据表都是用HashMap存储, 增删查改的时间复杂度都是 O(1)
 *
 * 每次都是一次性读入, 改动先缓存在内存中, 当更新数据或者关闭的时候再一次性写入文件中, 可以大大提升运行时的效率
 *
 */

public class InfoObject implements Serializable {
    private static final long serialVersionUID=1L;

    protected String code;

    public InfoObject(String code){
        this.code=code;
    }

    public String getCode() {
        return code;
    }

}
