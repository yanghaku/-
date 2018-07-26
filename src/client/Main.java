package client;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.stage.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.*;
import server.Manage;

import java.io.IOException;
import java.util.Stack;

/**
 * @author yangbo
 *
 * 主界面,(登录后才能加载)
 * 同时, Main为主程序, 维护所有页面的信息, 维护server.Manage, 与数据库进行交互
 *
 */

public class Main extends Application{
	public static void main(String[] args) {Application.launch(args);}

	private String loginId;
	private Manage serverManage;

    private Scene mainScene;

    private Pane mainPane;
    private Stack<Pane> panes; // 维护一个栈,负责存储经过的页面, 实现页面的后退功能
    private BorderPane frame;  //维护当前显示的面板框架,其中 center 为内容


	public void start(Stage window){

		//初始化数据库
        try {
            serverManage = new Manage();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        new Login(this);

        frame=initFrame();
        panes=new Stack<>();
        mainPane=initMainPane();
        mainScene=new Scene(mainPane,900,700);
        mainScene.getStylesheets().add(getClass().getResource("/login.css").toExternalForm());
        window.setScene(mainScene);

        // 窗体设置
        window.setTitle("医疗保险报销系统");
        window.getIcons().add(new Image("file:media/image/1.png"));
        window.setOnCloseRequest(e->{
            try {
                serverManage.update();  //关闭窗口,结束应用之前将服务器更新;
            }catch(IOException ioe){
                AlertDialog.display("数据库错误","数据库更新错误");
            }
        });
        window.show();

	}

    public void goBack(){
	    // 如果为空,那么没有已过期的页面, 就到回到主页
        if(panes.empty()){
            mainScene.setRoot(mainPane);
            frame.setCenter(null); //同时释放掉当前页面的内存
        }
        else{
            frame.setCenter(panes.pop());
        }
    }

    public void goMain(){
	    while(!panes.empty())panes.pop();
	    frame.setCenter(null);
	    mainScene.setRoot(mainPane);
    }

    public void push(Pane pane){
	    // 管理方案是过期的页面才压入栈中, 当前的页面存在center里即可
        // 因此第一次push时, 开始的null不用压入栈中,并且需要将框架替换主界面
        if(panes.empty()){
            frame.setCenter(pane);
            mainScene.setRoot(frame);
        }
	    else{
            panes.push(((Pane) frame.getCenter()));
            frame.setCenter(pane);
        }
    }

	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}

	public String getLoginId() {
		return loginId;
	}

	public Manage getServerManage(){
	    return this.serverManage;
	}

	// 初始化主页面的面板
	public VBox initMainPane(){
        VBox vBox=new VBox();
        Button btM=new Button("医疗基本信息维护");
        Button btS=new Button("公共业务信息维护");
        Button btB=new Button("医保中心报销");
        Button btC=new Button("医疗待遇审批");
        Button btF=new Button("综合查询");
        btM.setOnAction(e->{ push(new MedicalPane(this));});
        btS.setOnAction(e->{ push(new SocialPane(this));});
        btB.setDisable(true);btC.setDisable(true);btF.setDisable(true);

        Label titles=new Label("欢迎进入医疗保险中心报销系统");
        vBox.setStyle("-fx-alignment:center;-fx-spacing:30");
        titles.setStyle("-fx-label-padding: 10,10,10,10");
        vBox.getChildren().addAll(titles,btM,btS,btB,btC,btF);
        return vBox;
    }

    // 返回框架的面板
    public BorderPane initFrame(){
	    BorderPane pane=new BorderPane();
        HBox hBox=new HBox();
        hBox.setSpacing(30);
        Button btBack=new Button("后退");
        btBack.setOnAction(e->goBack());
        Button btReturn=new Button("返回首页");
        btReturn.setOnAction(e->goMain());
        hBox.getChildren().addAll(btBack,btReturn,new Label("欢迎! "+loginId));
        pane.setTop(hBox);
        Label version=new Label("version-0.1.0");
        version.setStyle("-fx-font-size: 14px");
        pane.setBottom(version);
        return pane;
    }

}