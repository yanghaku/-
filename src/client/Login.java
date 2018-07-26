package client;

import javafx.geometry.Pos;
import javafx.stage.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import server.OperatorID;
import server.OperatorID.LoginErrors;

/**
 * @author yangbo
 *
 * 登录界面, 将输入的密码加密后传输,检查帐号密码正确后方可进入系统
 */
public class Login extends Stage{

    private Main main;
    private OperatorID ids;
	Login(Main main){
        this.main=main;
        ids=main.getServerManage().getOperator();

		initStyle(StageStyle.TRANSPARENT);
		setResizable(false);
		BorderPane pane=new BorderPane();
		//top
		HBox topPane=new HBox(10);
		topPane.setAlignment(Pos.BASELINE_RIGHT);
		Button btHide=new Button("_");
		Button btExit=new Button("X");
		btHide.setOnAction(e->this.setIconified(true));
		btExit.setOnAction(e->{
		    ConfirmDialog.display("确认退出","确认退出吗?");
		    if(ConfirmDialog.buttonSelected==ConfirmDialog.Response.YES)System.exit(0);
        });
        topPane.getChildren().addAll(btHide,btExit);
        topPane.setOnMouseDragged(e->{ //顶部拖动的时候拖动窗口
            this.setX(e.getScreenX());
            this.setY(e.getScreenY());
        });
        pane.setTop(topPane);
        // center
        GridPane centerPane=new GridPane();
        centerPane.setAlignment(Pos.CENTER);
        centerPane.setHgap(10);
        centerPane.setVgap(10);
        centerPane.add(new Label("呱呱呱"),3,2);

        centerPane.add(new Label("帐号: "),1,4);
        TextField account=new TextField();
        centerPane.add(account,3,4);
        centerPane.add(new Label("密码: "),1,5);
        PasswordField password=new PasswordField();
        centerPane.add(password,3,5);
        CheckBox memery=new CheckBox("记住密码");
        memery.setStyle("-fx-fill-width: 10;-fx-fill-height: 11;-fx-font-size: 14");
        memery.setSelected(false);
        centerPane.add(memery,1,6);
        Button btlogin=new Button("Login");
        centerPane.add(btlogin,3,6);
        btlogin.setOnAction(e->{
            if(check(account.getText(),hashPassword(password.getText()),memery.isSelected())) {
                this.close();
            }
        });
        pane.setCenter(centerPane);
		Scene scene=new Scene(pane,600,400);
		scene.getStylesheets().add(getClass().getResource("/login.css").toExternalForm());
		this.setScene(scene);
		this.showAndWait();
	}

	// 先本地检查, 后传到服务器上确认,若有记住密码,则保存
    boolean check(String account,String password,boolean memary){
	    if(account==null||account.length()==0){
	        AlertDialog.display("登录错误","帐号不能为空");
	        return false;
        }
        if(password==null||password.length()==0){
	        AlertDialog.display("登录错误","密码不能为空");
	        return false;
        }
        LoginErrors message=ids.judgeId(account,password);
	    if(message.equals(LoginErrors.passwordError)) {
            AlertDialog.display("登录错误","密码错误!");
            return false;
	    }
	    else if(message.equals(LoginErrors.idError)) {
            AlertDialog.display("登录错误","用户名不存在");
            return false;
	    }
	    else{
	        main.setLoginId(account);
	        if(memary)saveLocal(account,password);
	        return true;
        }
    }

    private void saveLocal(String account,String hashPassWord)
    {

    }

    // 将密码进行 Sha-1 加密
    public static String hashPassword(String primaryPasswd){
	    if(primaryPasswd==null || primaryPasswd.length()==0)return null;
	    char[] hexDigits = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
                'a', 'b', 'c', 'd', 'e', 'f'};
	    try{
            MessageDigest mdTmp = MessageDigest.getInstance("SHA1");
            mdTmp.update(primaryPasswd.getBytes("UTF-8"));
            byte[] md=mdTmp.digest();
            char[] buf = new char[md.length*2];
            int k=0;
            for(int i=0;i<md.length;++i) {
                byte byte0 = md[i];
                buf[k++] = hexDigits[byte0 >>> 4 & 0xf];
                buf[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(buf);
        }catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return  primaryPasswd;
    }
}