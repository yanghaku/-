package server.Social;

import server.InfoObject;

/**
 * @author yangbo
 * 公共组织
 * 其中的数据包括:单位编号,单位名称,单位类型,地址,邮编,联系电话
 */
public class Association extends InfoObject {
    private String name;
    private String type;
    private String adress;
    private int postNum;
    private int phoneNum;

    public Association(String code,String name,String type,String adress,int postNum,int phoneNum){
        super(code);
        this.name=name;
        this.type=type;
        this.adress=adress;
        this.postNum=postNum;
        this.phoneNum=phoneNum;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public int getPhoneNum() {
        return phoneNum;
    }

    public int getPostNum() {
        return postNum;
    }

    public String getAdress() {
        return adress;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setPostNum(int postNum) {
        this.postNum = postNum;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public void setPhoneNum(int phoneNum) {
        this.phoneNum = phoneNum;
    }

    @Override
    public boolean equals(Object obj) {
        return (obj instanceof Association) && (((Association) obj).getCode().equals(code));
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
