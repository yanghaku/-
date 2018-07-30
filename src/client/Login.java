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
		StackPane stackPane=new StackPane();
		BorderPane pane=new BorderPane();
		//top
		HBox topPane=new HBox(10);
		topPane.setAlignment(Pos.BASELINE_RIGHT);
		Button btHide=new Button("_");
		Button btExit=new Button("X");
		btExit.setId("exit");
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
        centerPane.add(new Label("欢迎登陆医疗保险报销系统"),3,2);

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
        Button btnew=new Button("增加id");
        btnew.setStyle("-fx-font-size:13");
        centerPane.add(btnew,4,6);
        btnew.setOnAction(e->{
            addId(pane);
        });
        pane.setCenter(centerPane);
        stackPane.getChildren().add(pane);
		Scene scene=new Scene(stackPane,600,450);
		try {
            scene.getStylesheets().add(getClass().getResource("../main.css").toExternalForm());
        }catch (Exception e){
		    e.printStackTrace();
        }
		this.setScene(scene);
		this.showAndWait();
	}

	private void addId(BorderPane borderPane){
	    GridPane pane=new GridPane();
	    pane.add(new Label("管理员帐号"),1,1);
	    pane.add(new Label("管理员密码"),1,2);
        pane.add(new Label("新的帐号"),1,3);
        pane.add(new Label("新的密码"),1,4);
        TextField manageId=new TextField();
        PasswordField mamagePassword=new PasswordField();
        TextField newId=new TextField();
        TextField newPass=new TextField();
        pane.add(manageId,2,1);
        pane.add(mamagePassword,2,2);
        pane.add(newId,2,3);
        pane.add(newPass,2,4);
        Button bt=new Button("提交");
        pane.add(bt,2,5);
        Node node=borderPane.getCenter();
        borderPane.setCenter(pane);
        Button btre=new Button("返回");
        btre.setStyle("-fx-font-size:19;");
        pane.add(btre,3,5);
        pane.setAlignment(Pos.CENTER);
        pane.setStyle("-fx-hgap:8;-fx-vgap: 13");
        btre.setOnAction(e->borderPane.setCenter(node));
        bt.setOnAction(e->{
            if(manageId.getText()==null||mamagePassword.getText()==null||newId.getText()==null||newPass.getText()==null){
                AlertDialog.display("错误","请输全信息!");
                return;
            }
            if(ids.addId(manageId.getText(),hashPassword(mamagePassword.getText()),newId.getText(),hashPassword(newPass.getText()))){
                AlertDialog.display("成功","增加成功!");
                borderPane.setCenter(node);
            }else AlertDialog.display("错误","增加失败!");
        });
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