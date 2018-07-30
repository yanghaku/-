package client;

import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import server.InfoObject;
import server.RegistedObj.ObjName;
import server.Social.*;
import server.CalculationParameter.PersonType;
import java.time.ZoneId;
import java.util.Date;
import java.util.Iterator;

/**
 * @author yangbo
 *
 * SocialPane 类,
 * 负责社会公务信息的管理界面组成与跳转
 *
 */
public class SocialPane extends StackPane{

    private Main main;

    public SocialPane(Main main){
        this.main=main;
        this.getChildren().add(association());
    }

    public VBox getLeftBar() {
        VBox vBox = new VBox();
        vBox.setSpacing(40);
        vBox.setAlignment(Pos.CENTER_LEFT);
        Button btPerson = new Button("参保人员信息");
        Button btAsso = new Button("参保单位信息");
        btAsso.setOnAction(e->main.push(association()));
        btPerson.setOnAction(e->main.push(person()));
        vBox.getChildren().addAll(btAsso,btPerson);
        vBox.setStyle("-fx-font-size: 19;-fx-background-color: rgba(255,255,255,0.6)");
        return vBox;
    }

    private VBox association() {
        main.setLeftBarActive(0);
        VBox pane=new VBox();
        HBox findboxid=new HBox();
        HBox findboxname=new HBox();

        TextField id=new TextField();
        TextField name=new TextField();
        Button btid=new Button("使用编号查询");
        Button btname=new Button("使用名称查询");
        findboxid.getChildren().addAll(id,btid);
        findboxname.getChildren().addAll(name,btname);
        Button btadd=new Button("增加新的参保单位");
        Button btAll=new Button("显示全部参保单位列表");
        pane.getChildren().addAll(new Label("单位基本信息维护"),findboxid,findboxname,btadd,btAll);
        btadd.setOnAction(e->main.push(associationAdd()));
        btAll.setOnAction(e->main.push(associationAll()));
        btid.setOnAction(e->{
            if(id.getText()==null||id.getText().length()==0){
                AlertDialog.display("错误","查找内容不能为空!");
                return;
            }
            InfoObject as=main.getServerManage().find(ObjName.Association,id.getText());
            if(as instanceof Association){
                main.push(associatonDislpay(((Association) as)));
            }else AlertDialog.display("失败","查找失败,没有找到!");
        });
        btname.setOnAction(e->{
            if(name.getText()==null||name.getText().length()==0){
                AlertDialog.display("错误","查找内容不能为空!");
                return;
            }
            Iterator<InfoObject> it=main.getServerManage().getAll(ObjName.Association);
            Association as=null;
            String s=name.getText();
            while(it.hasNext()){
                as=((Association) it.next());
                if(s.equals(as.getName()))break;
                else as=null;
            }
            if(as==null)AlertDialog.display("失败","查找失败,没有找到!");
            else main.push(associatonDislpay(as));
        });
        findboxid.setStyle("-fx-alignment: center");
        findboxname.setStyle("-fx-alignment: center");
        pane.setStyle("-fx-spacing:10;-fx-alignment: center;");
        return pane;
    }

    private GridPane associationAdd(){
        GridPane pane=new GridPane();
        pane.add(new Label("编号:"),1,1);
        pane.add(new Label("单位名称"),1,2);
        pane.add(new Label("单位类型"),1,3);
        pane.add(new Label("单位地址"),1,4);
        pane.add(new Label("邮编"),1,5);
        pane.add(new Label("联系电话"),1,6);
        TextField code=new TextField();
        TextField name=new TextField();
        TextField type=new TextField();
        TextField address=new TextField();
        TextField post=new TextField();
        TextField phone=new TextField();
        pane.add(code,2,1);
        pane.add(name,2,2);
        pane.add(type,2,3);
        pane.add(address,2,4);
        pane.add(post,2,5);
        pane.add(phone,2,6);
        Button bt=new Button("提交");
        pane.add(bt,2,7);
        bt.setOnAction(e->{
            if(code.getText()==null||code.getText().length()==0){
                AlertDialog.display("错误","编号不能为空!");
                return;
            }
            try{
                Association newAs=new Association(code.getText(),name.getText(),type.getText(),address.getText(),
                        Integer.parseInt(post.getText()),Integer.parseInt(phone.getText()));
                main.getServerManage().add(main.getLoginId(),ObjName.Association,newAs);
                AlertDialog.display("成功","增加成功!");
                main.goBack();
            }catch(NumberFormatException ne){
                AlertDialog.display("错误","数字格式错误");
            }
        });
        pane.setStyle("-fx-padding: 20;-fx-hgap: 13;-fx-vgap:20;-fx-alignment: center");
        return pane;
    }

    private GridPane associatonDislpay(Association as){
        GridPane pane=new GridPane();
        pane.add(new Label("编号:"),1,1);
        pane.add(new Label("单位名称"),1,2);
        pane.add(new Label("单位类型"),1,3);
        pane.add(new Label("单位地址"),1,4);
        pane.add(new Label("邮编"),1,5);
        pane.add(new Label("联系电话"),1,6);
        TextField code=new TextField(as.getCode());
        code.setDisable(true);//编号不可修改
        TextField name=new TextField(as.getName());
        TextField type=new TextField(as.getType());
        TextField address=new TextField(as.getAdress());
        TextField post=new TextField(String.valueOf(as.getPostNum()));
        TextField phone=new TextField(String.valueOf(as.getPhoneNum()));
        pane.add(code,2,1);
        pane.add(name,2,2);
        pane.add(type,2,3);
        pane.add(address,2,4);
        pane.add(post,2,5);
        pane.add(phone,2,6);
        Button btdelete=new Button("删除");btdelete.setStyle("-fx-background-color: red");
        Button btset=new Button("保存修改");
        Button btreturn=new Button("返回");
        btreturn.setOnAction(e->main.goBack());
        pane.add(btset,2,7);
        pane.add(btdelete,3,7);
        pane.add(btreturn,4,7);
        btset.setOnAction(e->{
            try{
                Association newAs=new Association(code.getText(),name.getText(),type.getText(),address.getText(),
                        Integer.parseInt(post.getText()),Integer.parseInt(phone.getText()));
                if(main.getServerManage().set(main.getLoginId(),ObjName.Association,newAs.getCode(),newAs))
                    AlertDialog.display("成功","修改成功!");
                else AlertDialog.display("失败","修改失败");
            }catch(NumberFormatException ne){
                AlertDialog.display("错误","数字格式错误");
            }
        });
        btdelete.setOnAction(e->{
            ConfirmDialog.display("警告","删除之后将无法找回,确认删除吗?");
            if(ConfirmDialog.buttonSelected.equals(ConfirmDialog.Response.YES)) {
                main.getServerManage().delete(main.getLoginId(), ObjName.Association, as.getCode());
                AlertDialog.display("成功", "删除成功!");
                main.goBack();
            }
        });
        pane.setStyle("-fx-padding: 20;-fx-hgap: 13;-fx-vgap:20;-fx-alignment: center");
        return pane;
    }

    private Pane associationAll(){
        BorderPane borderPane=new BorderPane();
        GridPane pane=new GridPane();
        Label status=new Label("状态: 加载完成");
        status.setStyle("-fx-font-size: 14px;-fx-background-color: rgba(255,255,255,0.7)");
        pane.add(new Label("编号:"),1,1);
        pane.add(new Label("单位名称"),2,1);
        pane.add(new Label("单位类型"),3,1);
        pane.add(new Label("单位地址"),4,1);
        pane.add(new Label("邮编"),5,1);
        pane.add(new Label("联系电话"),6,1);
        Iterator<InfoObject> it=main.getServerManage().getAll(ObjName.Association);
        int i=2;
        while(it.hasNext()){
            Association as= ((Association) it.next());
            Label l1=new Label(as.getCode());
            Label l2=new Label(as.getName());
            Label l3=new Label(as.getType());
            Label l4=new Label(as.getAdress());
            Label l5=new Label(String.valueOf(as.getPostNum()));
            Label l6=new Label(String.valueOf(as.getPhoneNum()));
            Button btset=new Button("修改");
            Button btdel=new Button("删除");
            btdel.setStyle("-fx-background-color: red");
            btset.setOnAction(e->main.push(associatonDislpay(as)));
            btdel.setOnAction(e->{
                ConfirmDialog.display("警告","删除之后将不可恢复,确认要删除吗?");
                if(ConfirmDialog.buttonSelected.equals(ConfirmDialog.Response.YES)){
                    main.getServerManage().delete(main.getLoginId(),ObjName.Association,as.getCode());
                    btdel.setDisable(true);btset.setDisable(true);
                    status.setText("状态: 删除"+as.getCode()+"成功!");
                }
            });
            pane.add(l1,1,i);
            pane.add(l2,2,i);
            pane.add(l3,3,i);
            pane.add(l4,4,i);
            pane.add(l5,5,i);
            pane.add(l6,6,i);
            pane.add(btset,8,i);
            pane.add(btdel,9,i);
            ++i;
        }
        pane.setGridLinesVisible(true);
        Button btf=new Button("刷新");
        borderPane.setTop(btf);
        btf.setOnAction(e->{
            main.goBack();
            main.push(this.associationAll());
        });
        pane.setAlignment(Pos.TOP_CENTER);
        ScrollPane scrollPane=new ScrollPane();
        scrollPane.setFitToWidth(true);
        scrollPane.setContent(pane);
        borderPane.setCenter(scrollPane);
        borderPane.setBottom(status);
        return borderPane;
    }



    private VBox person(){
        main.setLeftBarActive(1);
        VBox pane=new VBox();
        HBox findboxid=new HBox();
        HBox findboxname=new HBox();
        TextField id=new TextField();
        TextField name=new TextField();
        Button btid=new Button("通过编号查询");
        Button btname=new Button("使用姓名查询");
        findboxid.getChildren().addAll(id,btid);
        findboxname.getChildren().addAll(name,btname);
        Button btadd=new Button("增加新的人员信息");
        Button btAll=new Button("显示全部人员信息");
        pane.getChildren().addAll(new Label("人员基本信息维护"),findboxname,findboxid,btadd,btAll);
        btadd.setOnAction(e->main.push(personAdd()));
        btAll.setOnAction(e->main.push(personAll()));
        btid.setOnAction(e->{
            if(id.getText()==null||id.getText().length()==0){
                AlertDialog.display("错误","查找内容不能为空!");
                return;
            }
            InfoObject obj=main.getServerManage().find(ObjName.Person,id.getText());
            if(obj instanceof Person){
                main.push(personDisplay(((Person) obj)));
            }else{
                AlertDialog.display("失败","查找失败,没有找到!");
            }
        });
        btname.setOnAction(e->{
            if(name.getText()==null||name.getText().length()==0){
                AlertDialog.display("错误","查找内容不能为空!");
                return;
            }
            Iterator<InfoObject> it=main.getServerManage().getAll(ObjName.Person);
            Person ps=null;
            String s=name.getText();
            while(it.hasNext()){
                ps= ((Person) it.next());
                if(s.equals(ps.getName()))break;
                else ps=null;
            }
            if(ps==null)AlertDialog.display("失败","查找失败,没有找到!");
            else main.push(personDisplay(ps));
        });
        findboxid.setStyle("-fx-alignment: center");
        findboxname.setStyle("-fx-alignment: center");
        pane.setStyle("-fx-spacing:10;-fx-alignment: center;");
        return pane;
    }

    private Pane personAdd(){
        GridPane pane=new GridPane();
        pane.add(new Label("人员编号"),1,1);
        pane.add(new Label("证件类型"),1,2);
        pane.add(new Label("证件编号"),1,3);
        pane.add(new Label("姓名"),1,4);
        pane.add(new Label("医疗人员类别"),1,5);
        pane.add(new Label("民族"),1,6);
        pane.add(new Label("户口所在地"),1,7);
        pane.add(new Label("文化程度"),1,8);
        pane.add(new Label("政治面貌"),1,9);
        pane.add(new Label("工作形式"),1,10);
        pane.add(new Label("备注"),1,13);
        pane.add(new Label("单位编码"),1,12);
        pane.add(new Label("健康状况"),1,11);
        pane.add(new Label("定点医疗机构编号"),1,14);
        pane.add(new Label("性别"),4,1);
        pane.add(new Label("是否退休"),4,2);
        pane.add(new Label("劳模标志"),4,3);
        pane.add(new Label("干部标志"),4,4);
        pane.add(new Label("在编标志"),4,5);
        pane.add(new Label("农民标志"),4,6);
        pane.add(new Label("军转人员标志"),4,7);
        pane.add(new Label("出生日期"),4,8);
        pane.add(new Label("工作日期"),4,9);
        pane.add(new Label("退休日期"),4,10);

        TextField code=new TextField();
        pane.add(code,2,1);
        TextField idtype=new TextField();
        pane.add(idtype,2,2);
        TextField idnum=new TextField();
        pane.add(idnum,2,3);
        TextField name=new TextField();
        pane.add(name,2,4);
        ChoiceBox<String> personType=new ChoiceBox<>();
        personType.getItems().addAll("在职职工","退休人员","享受低保在职人员","享受低保退休人员");
        pane.add(personType,2,5);
        TextField race=new TextField();
        pane.add(race,2,6);
        TextField idArea=new TextField();
        pane.add(idArea,2,7);
        TextField educated=new TextField();
        pane.add(educated,2,8);
        TextField political=new TextField();
        pane.add(political,2,9);
        TextField workform=new TextField();
        pane.add(workform,2,10);
        TextField remark=new TextField();
        pane.add(remark,2,13);
        TextField associationCode=new TextField();
        pane.add(associationCode,2,12);
        TextField health=new TextField();
        pane.add(health,2,11);
        TextField ins=new TextField();
        pane.add(ins,2,14);

        ChoiceBox<String> man= new ChoiceBox<>();
        man.getItems().addAll("男","女");
        pane.add(man,5,1);
        CheckBox retired=new CheckBox();
        pane.add(retired,5,2);
        CheckBox modelwork=new CheckBox();
        pane.add(modelwork,5,3);
        CheckBox leader=new CheckBox();
        pane.add(leader,5,4);
        CheckBox indatabase=new CheckBox();
        pane.add(indatabase,5,5);
        CheckBox farmer=new CheckBox();
        pane.add(farmer,5,6);
        CheckBox soldier=new CheckBox();
        pane.add(soldier,5,7);
        DatePicker birth=new DatePicker();
        pane.add(birth,5,8);birth.setMaxWidth(140);
        DatePicker workdata=new DatePicker();
        pane.add(workdata,5,9);workdata.setMaxWidth(140);
        DatePicker retire=new DatePicker();
        pane.add(retire,5,10);retire.setMaxWidth(140);
        pane.setStyle("-fx-padding: 20;-fx-hgap: 10;-fx-vgap:8;-fx-alignment: center;-fx-font-size: 18px;-fx-background-color: rgba(255,255,255,0.65)");
        Button btadd=new Button("确认增加");
        btadd.setOnAction(e->{
            if(code.getText()==null||code.getText().length()==0){
                AlertDialog.display("错误","编号不能为空!");
                return;
            }
            PersonType s=null;
            String ty=personType.getValue();
            if(ty==null);
            else if(ty.equals("在职职工"))s=PersonType.Worked;
            else if(ty.equals("退休人员"))s=PersonType.Retired;
            else if(ty.equals("享受低保在职人员"))s=PersonType.LowWorked;
            else s=PersonType.LowRetired;
            boolean xing=false;
            ty=man.getValue();
            if(ty!=null)xing=ty.equals("男");
            Date bd=null,wd=null,rd=null;
            if(birth.getValue()!=null)bd=Date.from(birth.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
            if(workdata.getValue()!=null)wd=Date.from(workdata.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
            if(retire.getValue()!=null)rd=Date.from(retire.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
            Person ps=new Person(code.getText(),idtype.getText(),idnum.getText(),name.getText(),s,xing,race.getText(),
                    bd,wd,rd,retired.isSelected(),idArea.getText(),educated.getText(),political.getText(),workform.getText(),
                    remark.getText(),associationCode.getText(),health.getText(),modelwork.isSelected(),leader.isSelected(),
                    indatabase.isSelected(),farmer.isSelected(),soldier.isSelected(),ins.getText());
            main.getServerManage().add(main.getLoginId(),ObjName.Person,ps);
            AlertDialog.display("成功","增加成功!");
            main.goBack();
        });
        pane.add(btadd,5,13);
        return pane;
    }

    private GridPane personDisplay(Person ps){

        GridPane pane=new GridPane();
        pane.add(new Label("人员编号"),1,1);
        pane.add(new Label("证件类型"),1,2);
        pane.add(new Label("证件编号"),1,3);
        pane.add(new Label("姓名"),1,4);
        pane.add(new Label("医疗人员类别"),1,5);
        pane.add(new Label("民族"),1,6);
        pane.add(new Label("户口所在地"),1,7);
        pane.add(new Label("文化程度"),1,8);
        pane.add(new Label("政治面貌"),1,9);
        pane.add(new Label("工作形式"),1,10);
        pane.add(new Label("备注"),1,13);
        pane.add(new Label("单位编码"),1,12);
        pane.add(new Label("健康状况"),1,11);
        pane.add(new Label("定点医疗机构编号"),1,14);

        pane.add(new Label("性别"),4,1);
        pane.add(new Label("是否退休"),4,2);
        pane.add(new Label("劳模标志"),4,3);
        pane.add(new Label("干部标志"),4,4);
        pane.add(new Label("在编标志"),4,5);
        pane.add(new Label("农民标志"),4,6);
        pane.add(new Label("军转人员标志"),4,7);
        pane.add(new Label("出生日期"),4,8);
        pane.add(new Label("工作日期"),4,9);
        pane.add(new Label("退休日期"),4,10);

        TextField code=new TextField(ps.getCode());
        pane.add(code,2,1);code.setDisable(true);
        TextField idtype=new TextField(ps.getIdType());
        pane.add(idtype,2,2);
        TextField idnum=new TextField(ps.getIdNum());
        pane.add(idnum,2,3);
        TextField name=new TextField(ps.getName());
        pane.add(name,2,4);
        ChoiceBox<String> personType=new ChoiceBox<>();
        personType.getItems().addAll("在职职工","退休人员","享受低保在职人员","享受低保退休人员");
        personType.setValue(PersonTypeToString(ps.getType()));
        pane.add(personType,2,5);
        TextField race=new TextField(ps.getRace());
        pane.add(race,2,6);
        TextField idArea=new TextField(ps.getIdArea());
        pane.add(idArea,2,7);
        TextField educated=new TextField(ps.getEducated());
        pane.add(educated,2,8);
        TextField political=new TextField(ps.getPolitical());
        pane.add(political,2,9);
        TextField workform=new TextField(ps.getWorkForm());
        pane.add(workform,2,10);
        TextField remark=new TextField(ps.getRemark());
        pane.add(remark,2,13);
        TextField associationCode=new TextField(ps.getAssociationCode());
        pane.add(associationCode,2,12);
        TextField health=new TextField(ps.getHealth());
        pane.add(health,2,11);

        TextField ins=new TextField();
        pane.add(ins,2,14);
        ins.setText(ps.getInstituton());
        ChoiceBox<String> man= new ChoiceBox<>();
        man.getItems().addAll("男","女");
        if(ps.isMan())man.setValue("男");
        else man.setValue("女");
        pane.add(man,5,1);
        CheckBox retired=new CheckBox();
        retired.setSelected(ps.isRetired());
        pane.add(retired,5,2);
        CheckBox modelwork=new CheckBox();
        modelwork.setSelected(ps.isModelWork());
        pane.add(modelwork,5,3);
        CheckBox leader=new CheckBox();
        leader.setSelected(ps.isLeader());
        pane.add(leader,5,4);
        CheckBox indatabase=new CheckBox();
        indatabase.setSelected(ps.isInDataBase());
        pane.add(indatabase,5,5);
        CheckBox farmer=new CheckBox();
        farmer.setSelected(ps.isFarmer());
        pane.add(farmer,5,6);
        CheckBox soldier=new CheckBox();
        soldier.setSelected(ps.isSoldier());
        pane.add(soldier,5,7);
        DatePicker birth=new DatePicker();
        if(ps.getBirthDate()!=null)birth.setValue(ps.getBirthDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
        pane.add(birth,5,8);birth.setMaxWidth(140);
        DatePicker workdata=new DatePicker();
        if(ps.getWorkDate()!=null)workdata.setValue(ps.getWorkDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
        pane.add(workdata,5,9);workdata.setMaxWidth(140);
        DatePicker retire=new DatePicker();
        if(ps.getRetire()!=null)retire.setValue(ps.getRetire().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
        pane.add(retire,5,10);retire.setMaxWidth(140);
        pane.setStyle("-fx-padding: 20;-fx-hgap: 10;-fx-vgap:8;-fx-alignment: center;-fx-font-size: 18px;-fx-background-color: rgba(255,255,255,0.65)");
        Button btSet=new Button("保存修改");
        Button btDele=new Button("删除");
        Button btReturn=new Button("返回");
        pane.add(btSet,4,13);pane.add(btDele,5,13);pane.add(btReturn,6,13);
        btDele.setStyle("-fx-background-color: red");
        btReturn.setOnAction(e->main.goBack());
        btSet.setOnAction(e->{
            PersonType s=null;
            String ty=personType.getValue();
            if(ty==null);
            else if(ty.equals("在职职工"))s=PersonType.Worked;
            else if(ty.equals("退休人员"))s=PersonType.Retired;
            else if(ty.equals("享受低保在职人员"))s=PersonType.LowWorked;
            else s=PersonType.LowRetired;
            boolean xing=false;
            ty=man.getValue();
            if(ty!=null)xing=ty.equals("男");
            Date bd=null,wd=null,rd=null;
            if(birth.getValue()!=null)bd=Date.from(birth.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
            if(workdata.getValue()!=null)wd=Date.from(workdata.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
            if(retire.getValue()!=null)rd=Date.from(retire.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
            Person newPs=new Person(ps.getCode(),idtype.getText(),idnum.getText(),name.getText(),s,xing,race.getText(),
                    bd,wd,rd,retired.isSelected(),idArea.getText(),educated.getText(),political.getText(),workform.getText(),
                    remark.getText(),associationCode.getText(),health.getText(),modelwork.isSelected(),leader.isSelected(),
                    indatabase.isSelected(),farmer.isSelected(),soldier.isSelected(),ins.getText());
            if(main.getServerManage().set(main.getLoginId(),ObjName.Person,ps.getCode(),newPs)){
                AlertDialog.display("成功","修改保存成功!");
            }else AlertDialog.display("失败","修改失败!");
        });
        btDele.setOnAction(e->{
            ConfirmDialog.display("警告","删除之后将不可找回,是否确认删除?");
            if(ConfirmDialog.buttonSelected.equals(ConfirmDialog.Response.YES)){
                main.getServerManage().delete(main.getLoginId(),ObjName.Person,ps.getCode());
                AlertDialog.display("成功","删除成功!");
                main.goBack();
            }
        });
        return pane;
    }

    private BorderPane personAll(){
        BorderPane borderPane=new BorderPane();
        Label status=new Label("状态: 加载完成!");
        status.setStyle("-fx-font-size: 14px;-fx-background-color: rgba(255,255,255,0.7)");
        borderPane.setBottom(status);
        GridPane pane=new GridPane();
        pane.add(new Label("编号:"),1,1);
        pane.add(new Label("身份证号"),2,1);
        pane.add(new Label("姓名"),3,1);
        pane.add(new Label("医疗人员类别"),4,1);
        pane.add(new Label("户口所在地"),5,1);
        pane.add(new Label("备注"),6,1);
        Iterator<InfoObject> it=main.getServerManage().getAll(ObjName.Person);
        int i=2;
        while(it.hasNext()){
            Person ps= ((Person) it.next());
            Label l1=new Label(ps.getCode());
            Label l2=new Label(ps.getIdNum());
            Label l3=new Label(ps.getName());
            Label l4=new Label(PersonTypeToString(ps.getType()));
            Label l5=new Label(ps.getIdArea());
            Label l6=new Label(ps.getRemark());
            Button btdel=new Button("删除");
            Button btset=new Button("修改");
            btdel.setStyle("-fx-background-color: red");
            btset.setOnAction(e->main.push(personDisplay(ps)));
            btdel.setOnAction(e->{
                ConfirmDialog.display("警告","删除之后将不可恢复,确认要删除吗?");
                if(ConfirmDialog.buttonSelected.equals(ConfirmDialog.Response.YES)){
                    main.getServerManage().delete(main.getLoginId(),ObjName.Person,ps.getCode());
                    btdel.setDisable(true);btset.setDisable(true);
                    status.setText("状态: 删除"+ps.getCode()+"成功!");
                }
            });
            pane.add(l1,1,i);
            pane.add(l2,2,i);
            pane.add(l3,3,i);
            pane.add(l4,4,i);
            pane.add(l5,5,i);
            pane.add(l6,6,i);
            pane.add(btset,7,i);
            pane.add(btdel,8,i);
            ++i;
        }
        pane.setGridLinesVisible(true);
        Button btf=new Button("刷新");
        btf.setOnAction(e->{
            main.goBack();
            main.push(personAll());
        });
        borderPane.setTop(btf);
        pane.setAlignment(Pos.TOP_CENTER);
        ScrollPane scrollPane=new ScrollPane();
        scrollPane.setFitToWidth(true);
        scrollPane.setContent(pane);
        borderPane.setCenter(scrollPane);
        return borderPane;
    }

    private String PersonTypeToString(PersonType py){
        if(py==null)return null;
        else if(py.equals(PersonType.Worked))return "在职职工";
        else if(py.equals(PersonType.Retired))return "退休人员";
        else if(py.equals(PersonType.LowRetired))return "享受低保在职人员";
        else return "享受低保退休人员";
    }

}
