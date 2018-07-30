package server;

import java.io.*;
import java.util.HashMap;
import java.util.StringTokenizer;

/**
 * @author yangbo
 *
 * 操作员的ID的控制类, 负责保存所有操作员的id与密码, 并通过与文件的交互将数据持久化存储
 *
 * 同时维护一组 root 帐号密码, 负责管理系统, 并且可以增加操作员的帐号
 *
 * 接口: judgeId(id,password) 查询密码是否匹配
 *      addId(id,password) 增加ID
 *
 */

public class OperatorID {
    public enum LoginErrors{
        ok,passwordError,idError
    };

    private HashMap<String,String> ids=new HashMap<>();
    private String rootId="root";
    private String rootPs="f7c3bc1d808e04732adf679965ccc34ca7ae3441";//123456789

    public OperatorID()throws IOException {
        BufferedReader br=new BufferedReader(new FileReader("database/id.txt"));
        String s;
        while((s=br.readLine())!=null){
            StringTokenizer st=new StringTokenizer(s);
            ids.put(st.nextToken(),st.nextToken());
        }
    }

    public LoginErrors judgeId(String id,String password){
        String s=ids.get(id);
        if(s==null)return LoginErrors.idError;
        if(s.equals(password))return LoginErrors.ok;
        return LoginErrors.passwordError;
    }

    // 增加id ,系统管理员才可用
    public boolean addId(String opId,String opPass,String id,String password){
        if(opId.equals(rootId)&&opPass.equals(rootPs)) {
            ids.put(id, password);
            try {
                FileWriter fw = new FileWriter("database/id.txt", true);
                fw.write("\n"+id + " " + password);
                fw.close();
                return true;
            }catch (IOException e){
                e.printStackTrace();
                return false;
            }
        }else return false;
    }
}
