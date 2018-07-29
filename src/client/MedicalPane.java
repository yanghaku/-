package client;

import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import server.CalculationParameter.*;
import server.CalculationParameter;
import server.InfoObject;
import server.Medical.*;
import server.RegistedObj.ObjName;

import java.io.IOException;
import java.util.Iterator;


public class MedicalPane extends StackPane{
    private Main main;

    public MedicalPane(Main main){
        this.main=main;
        this.getChildren().add(medicine());
    }

    public VBox getLeftBar() {
        VBox vBox = new VBox();
        vBox.setSpacing(40);
        vBox.setAlignment(Pos.CENTER_LEFT);
        Button btMedicine = new Button("药品基本信息");
        btMedicine.setOnAction(e->main.push(medicine()));
        Button btTreament = new Button("诊疗项目信息");
        btTreament.setOnAction(e->main.push(treatment()));
        Button btFaci = new Button("服务设施项目");
        btFaci.setOnAction(e->main.push(facilities()));
        Button btInsti = new Button("定点医疗机构");
        btInsti.setOnAction(e->main.push(institution()));
        Button btDis = new Button("病种信息维护");
        btDis.setOnAction(e->main.push(disease()));
        Button btcal = new Button("报销计算参数");
        btcal.setOnAction(e->main.push(calParameter()));
        vBox.getChildren().addAll(btMedicine, btTreament, btFaci, btInsti, btDis,btcal);
        vBox.setStyle("-fx-font-size: 19;-fx-background-color: rgba(255,255,255,0.6);");
        return vBox;
    }


    private Pane calParameter(){
        main.setLeftBarActive(5);
        GridPane pane=new GridPane();
        CalculationParameter cal=main.getServerManage().getParmater();
        pane.add(new Label("封顶线:"),1,1);
        pane.add(new Label("在职员工:"),2,2);
        pane.add(new Label("退休员工:"),2,3);
        pane.add(new Label("享受低保的在职员工:"),2,4);
        pane.add(new Label("享受低保的退休员工:"),2,5);
        TextField workup=new TextField(String.valueOf(cal.getWorkedUp()));
        TextField retire=new TextField(String.valueOf(cal.getRetiredUp()));
        TextField Lwork=new TextField(String.valueOf(cal.getLworkedUp()));
        TextField Lretire=new TextField(String.valueOf(cal.getLretiredUp()));
        pane.add(workup,4,2);
        pane.add(retire,4,3);
        pane.add(Lwork,4,4);
        pane.add(Lretire,4,5);
        pane.add(new Label("起付标准"),1,7);
        pane.add(new Label("报销比例:"),1,8);
        TextField low=new TextField(String.valueOf(cal.getLowerBound()));
        TextField level1=new TextField(String.valueOf(cal.getLevel1()));
        TextField level2=new TextField(String.valueOf(cal.getLevel2()));
        TextField rate1=new TextField(String.valueOf(cal.getRate1()));
        TextField rate2=new TextField(String.valueOf(cal.getRate2()));
        TextField rate3=new TextField(String.valueOf(cal.getRate3()));
        TextField low_fu=new TextField();low_fu.textProperty().bindBidirectional(low.textProperty());
        pane.add(low_fu,2,7);
        pane.add(low,2,9);pane.add(new Label("~"),3,9);pane.add(level1,4,9);pane.add(rate1,5,9);
        TextField l1=new TextField();l1.textProperty().bindBidirectional(level1.textProperty());
        pane.add(l1,2,10);pane.add(new Label("~"),3,10);pane.add(level2,4,10);pane.add(rate2,5,10);
        TextField l2=new TextField();l2.textProperty().bindBidirectional(level2.textProperty());
        pane.add(l2,2,11);pane.add(new Label("~"),3,11);pane.add(rate3,5,11);
        TextField feng=new TextField(" 9999999");feng.setDisable(true);pane.add(feng,4,11);
        workup.setPrefWidth(200);Lretire.setPrefWidth(200);Lwork.setPrefWidth(200);retire.setPrefWidth(200);
        level1.setPrefWidth(200);l1.setPrefWidth(200);l2.setPrefWidth(200);level2.setPrefWidth(200);
        low.setPrefWidth(200);low_fu.setPrefWidth(200);
        rate1.setPrefWidth(70);rate2.setPrefWidth(70);rate3.setPrefWidth(70);
        Button btset=new Button("确认更改");
        pane.add(btset,4,13);
        pane.setAlignment(Pos.CENTER);
        btset.setOnAction(e->{
            try{
                CalculationParameter newca=new CalculationParameter(Double.parseDouble(workup.getText()),
                    Double.parseDouble(retire.getText()),Double.parseDouble(Lretire.getText()),
                    Double.parseDouble(Lwork.getText()),Double.parseDouble(low.getText()),
                    Double.parseDouble(level1.getText()),Double.parseDouble(level2.getText()),
                    Double.parseDouble(rate1.getText()),Double.parseDouble(rate2.getText()),Double.parseDouble(rate3.getText()));
                main.getServerManage().setParameter(main.getLoginId(),newca);
                AlertDialog.display("成功","保存成功!");
            }catch (NumberFormatException ex){
                AlertDialog.display("错误","信息格式错误,请重新填写!");
            }catch (IOException ioe){
                AlertDialog.display("错误","文件错误,保存失败");
            }
        });
        pane.setStyle("-fx-padding: 20px,20px,20px,20px;-fx-vgap:8;-fx-hgap:8;-fx-background-color: rgba(255,255,255,0.4)");
        return pane;
    }

    private VBox medicine(){
        main.setLeftBarActive(0);
        VBox pane=new VBox();
        HBox findBox=new HBox();
        TextField idText=new TextField();
        Button btfind=new Button("通过编号查询");
        findBox.getChildren().addAll(idText,btfind);
        Button btadd=new Button("增加新的药品");
        Button btall=new Button("显示所有药品名录");
        pane.getChildren().addAll(new Label("药品基本信息维护"),findBox,btadd,btall);
        btadd.setOnAction(e->main.push(medicineAdd()));
        btall.setOnAction(e->main.push(medicineAll()));
        btfind.setOnAction(e->{
            if(idText.getText()==null||idText.getText().length()==0){
                AlertDialog.display("错误","查找内容不能为空!");
                return;
            }
            InfoObject obj=main.getServerManage().find(ObjName.Medicine,idText.getText());
            if(obj instanceof Medicine){
                main.push(medicineDisplay(((Medicine) obj)));
            }else{
                AlertDialog.display("失败","查找失败,没有找到!");
            }
        });
        findBox.setStyle("-fx-alignment: center");
        pane.setStyle("-fx-spacing:10;-fx-alignment: center;");
        return pane;
    }


    private GridPane medicineAdd(){
        GridPane pane=new GridPane();
        pane.add(new Label("编码"),1,1);
        pane.add(new Label("收费类别"),1,2);
        pane.add(new Label("中文名"),1,3);
        pane.add(new Label("英文名"),1,4);
        pane.add(new Label("最高限价(为数字)"),1,5);
        pane.add(new Label("药品剂型"),1,6);
        pane.add(new Label("药品计量单位"),1,7);
        pane.add(new Label("使用频次"),1,8);
        pane.add(new Label("用法"),1,9);
        pane.add(new Label("限定天数"),1,10);
        pane.add(new Label("药厂名称"),1,11);
        pane.add(new Label("产地"),1,12);
        pane.add(new Label("备注"),1,13);
        pane.add(new Label("处方药"),3,1);
        pane.add(new Label("收费项目等级"),3,2);
        pane.add(new Label("是否需要审批"),3,3);
        pane.add(new Label("医院等级"),3,4);
        pane.add(new Label("院内制剂标志"),3,5);
        pane.add(new Label("国药准字"),3,6);
        pane.add(new Label("国家目录编码"),3,7);
        TextField code=new TextField();
        pane.add(code,2,1);
        TextField payType=new TextField();
        pane.add(payType,2,2);
        TextField cName=new TextField();
        pane.add(cName,2,3);
        TextField eName=new TextField();
        pane.add(eName,2,4);
        TextField upperBound=new TextField();
        pane.add(upperBound,2,5);
        TextField type=new TextField();
        pane.add(type,2,6);
        TextField unit=new TextField();
        pane.add(unit,2,7);
        TextField frequency=new TextField();
        pane.add(frequency,2,8);
        TextField usage=new TextField();
        pane.add(usage,2,9);
        TextField upperDays=new TextField();
        pane.add(upperDays,2,10);
        TextField productedName=new TextField();
        pane.add(productedName,2,11);
        TextField productedArea=new TextField();
        pane.add(productedArea,2,12);
        TextField remark=new TextField();
        pane.add(remark,2,13);
        CheckBox isPrescription=new CheckBox();
        pane.add(isPrescription,4,1);
        ChoiceBox<String> payLevel=new ChoiceBox<>();
        pane.add(payLevel,4,2);
        payLevel.getItems().addAll("甲类","乙类","丙类");
        CheckBox needCheck=new CheckBox();
        pane.add(needCheck,4,3);
        ChoiceBox<String> hospitalLevel=new ChoiceBox<>();
        hospitalLevel.getItems().addAll("全部医院","一级医院","二级医院","三级医院","社区医院");
        pane.add(hospitalLevel,4,4);
        CheckBox isFromHospital=new CheckBox();
        pane.add(isFromHospital,4,5);
        TextField countryCheck=new TextField();
        countryCheck.setPrefWidth(120);
        pane.add(countryCheck,4,6);
        TextField countryCode=new TextField();
        countryCode.setPrefWidth(120);
        pane.add(countryCode,4,7);

        Button bt=new Button("提交");
        pane.add(bt,4,13);
        bt.setOnAction(e->{
            if(code.getText()==null||code.getText().length()==0){
                AlertDialog.display("错误","编号不能为空!");
                return;
            }
            try {
                Medicine me=new Medicine(code.getText(),payType.getText(),isPrescription.isSelected(),
                        StringToPayLevel(payLevel.getValue()),cName.getText(),eName.getText(),
                        needCheck.isSelected(),StringToHospitalLevel(hospitalLevel.getValue()),
                        Double.parseDouble(upperBound.getText()),isFromHospital.isSelected(),type.getText(),
                        unit.getText(),frequency.getText(),usage.getText(),upperDays.getText(),
                        productedName.getText(),productedArea.getText(),countryCheck.getText(),
                        countryCode.getText(),remark.getText());
                main.getServerManage().add(main.getLoginId(),ObjName.Medicine,me);
                AlertDialog.display("成功", "增加成功!");
                main.goBack();
            }catch (NumberFormatException nue){
                AlertDialog.display("错误","数字格式错误,请重新输入!");
            }
        });
        pane.setStyle("-fx-padding:20;-fx-hgap:10;-fx-vgap:8;-fx-alignment: center;-fx-font-size: 18px;-fx-background-color: rgba(255,255,255,0.6);");
        return pane;
    }


    private GridPane medicineDisplay(Medicine md){
        GridPane pane=new GridPane();
        pane.add(new Label("编码"),1,1);
        pane.add(new Label("收费类别"),1,2);
        pane.add(new Label("中文名"),1,3);
        pane.add(new Label("英文名"),1,4);
        pane.add(new Label("最高限价(为数字)"),1,5);
        pane.add(new Label("药品剂型"),1,6);
        pane.add(new Label("药品计量单位"),1,7);
        pane.add(new Label("使用频次(为整数)"),1,8);
        pane.add(new Label("用法"),1,9);
        pane.add(new Label("限定天数"),1,10);
        pane.add(new Label("药厂名称"),1,11);
        pane.add(new Label("产地"),1,12);
        pane.add(new Label("备注"),1,13);
        pane.add(new Label("处方药"),3,1);
        pane.add(new Label("收费项目等级"),3,2);
        pane.add(new Label("是否需要审批"),3,3);
        pane.add(new Label("医院等级"),3,4);
        pane.add(new Label("院内制剂标志"),3,5);
        pane.add(new Label("国药准字"),3,6);
        pane.add(new Label("国家目录编码"),3,7);
        TextField code=new TextField();
        code.setText(md.getCode());code.setDisable(true);
        pane.add(code,2,1);
        TextField payType=new TextField();
        payType.setText(md.getCode());
        pane.add(payType,2,2);
        TextField cName=new TextField();
        cName.setText(md.getcName());
        pane.add(cName,2,3);
        TextField eName=new TextField();
        eName.setText(md.geteName());
        pane.add(eName,2,4);
        TextField upperBound=new TextField();
        upperBound.setText(String.valueOf(md.getUpperBound()));
        pane.add(upperBound,2,5);
        TextField type=new TextField();
        type.setText(md.getType());
        pane.add(type,2,6);
        TextField unit=new TextField();
        unit.setText(md.getUnit());
        pane.add(unit,2,7);
        TextField frequency=new TextField();
        frequency.setText(md.getFrequency());
        pane.add(frequency,2,8);
        TextField usage=new TextField();
        usage.setText(md.getUsage());
        pane.add(usage,2,9);
        TextField upperDays=new TextField();
        upperDays.setText(md.getUpperDays());
        pane.add(upperDays,2,10);
        TextField productedName=new TextField();
        productedName.setText(md.getProductedName());
        pane.add(productedName,2,11);
        TextField productedArea=new TextField();
        productedArea.setText(md.getProductedArea());
        pane.add(productedArea,2,12);
        TextField remark=new TextField();
        remark.setText(md.getRemark());
        pane.add(remark,2,13);
        CheckBox isPrescription=new CheckBox();
        isPrescription.setSelected(md.isPrescription());
        pane.add(isPrescription,4,1);
        ChoiceBox<String> payLevel=new ChoiceBox<>();
        payLevel.setValue(PayLevelToString(md.getPayLevel()));
        pane.add(payLevel,4,2);
        payLevel.getItems().addAll("甲类","乙类","丙类");
        CheckBox needCheck=new CheckBox();
        pane.add(needCheck,4,3);
        ChoiceBox<String> hospitalLevel=new ChoiceBox<>();
        hospitalLevel.getItems().addAll("全部医院","一级医院","二级医院","三级医院","社区医院");
        hospitalLevel.setValue(HospitalLevelToString(md.getHospitalLevel()));
        pane.add(hospitalLevel,4,4);
        CheckBox isFromHospital=new CheckBox();
        isFromHospital.setSelected(md.isFromHospital());
        pane.add(isFromHospital,4,5);
        TextField countryCheck=new TextField();
        countryCheck.setText(md.getCountryCheck());
        countryCheck.setPrefWidth(120);
        pane.add(countryCheck,4,6);
        TextField countryCode=new TextField();
        countryCode.setText(md.getCountryCode());
        countryCode.setPrefWidth(120);
        pane.add(countryCode,4,7);

        Button btSet=new Button("保存修改");
        Button btdel=new Button("删除");
        Button btretrun=new Button("返回");
        pane.add(btSet,3,13);pane.add(btdel,4,13);pane.add(btretrun,5,13);
        btdel.setStyle("-fx-background-color: red");
        btretrun.setOnAction(e->main.goBack());
        btdel.setOnAction(e->{
            ConfirmDialog.display("警告","删除之后将无法找回,确认删除吗?");
            if(ConfirmDialog.buttonSelected.equals(ConfirmDialog.Response.YES)) {
                main.getServerManage().delete(main.getLoginId(),ObjName.Medicine,md.getCode());
                AlertDialog.display("成功", "删除成功!");
                main.goBack();
            }
        });
        btSet.setOnAction(e->{
            try {
                Medicine me=new Medicine(code.getText(),payType.getText(),isPrescription.isSelected(),
                        StringToPayLevel(payLevel.getValue()),cName.getText(),eName.getText(),
                        needCheck.isSelected(),StringToHospitalLevel(hospitalLevel.getValue()),
                        Double.parseDouble(upperBound.getText()),isFromHospital.isSelected(),type.getText(),
                        unit.getText(),frequency.getText(),usage.getText(),upperDays.getText(),
                        productedName.getText(),productedArea.getText(),countryCheck.getText(),
                        countryCode.getText(),remark.getText());
                if(main.getServerManage().set(main.getLoginId(),ObjName.Medicine,md.getCode(),me))
                {
                    AlertDialog.display("成功", "修改成功!");
                }else AlertDialog.display("失败","修改失败!");
            }catch (NumberFormatException nue){
                AlertDialog.display("错误","数字格式错误,请重新输入!");
            }
        });
        pane.setStyle("-fx-padding:20;-fx-hgap:10;-fx-vgap:8;-fx-alignment: center;-fx-font-size: 18px;-fx-background-color: rgba(255,255,255,0.6);");
        return pane;
    }

    private BorderPane medicineAll(){
        BorderPane borderPane=new BorderPane();
        Label status=new Label("状态: 加载完成!");
        status.setStyle("-fx-font-size: 14px;-fx-background-color: rgba(255,255,255,0.7)");
        borderPane.setBottom(status);
        GridPane pane=new GridPane();
        pane.add(new Label("编号"),1,1);
        pane.add(new Label("收费类别"),2,1);
        pane.add(new Label("收费项目等级"),3,1);
        pane.add(new Label("中文名"),4,1);
        pane.add(new Label("英文名"),5,1);
        pane.add(new Label("医院等级"),6,1);
        Iterator<InfoObject> it=main.getServerManage().getAll(ObjName.Medicine);
        int i=2;
        while(it.hasNext()){
            Medicine md= ((Medicine) it.next());
            Label l1=new Label(md.getCode());
            Label l2=new Label(md.getPayType());
            Label l3=new Label(PayLevelToString(md.getPayLevel()));
            Label l4=new Label(md.getcName());
            Label l5=new Label(md.geteName());
            Label l6=new Label(HospitalLevelToString(md.getHospitalLevel()));
            Button btdel=new Button("删除");
            Button btset=new Button("修改");
            btdel.setStyle("-fx-background-color: red");
            btset.setOnAction(e->main.push(medicineDisplay(md)));
            btdel.setOnAction(e->{
                ConfirmDialog.display("警告","删除之后将不可恢复,确认要删除吗?");
                if(ConfirmDialog.buttonSelected.equals(ConfirmDialog.Response.YES)){
                    main.getServerManage().delete(main.getLoginId(),ObjName.Medicine,md.getCode());
                    btdel.setDisable(true);btset.setDisable(true);
                    status.setText("状态: 删除"+md.getCode()+"成功!");
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
            main.push(medicineAll());
        });
        borderPane.setTop(btf);
        pane.setAlignment(Pos.TOP_CENTER);
        ScrollPane scrollPane=new ScrollPane();
        scrollPane.setFitToWidth(true);
        scrollPane.setContent(pane);
        borderPane.setCenter(scrollPane);
        return borderPane;
    }

    private VBox treatment() {
        main.setLeftBarActive(1);
        VBox pane=new VBox();
        HBox findboxid=new HBox();
        HBox findboxname=new HBox();
        TextField idtext=new TextField();
        TextField nametext=new TextField();
        Button btid=new Button("使用编号查询");
        Button btname=new Button("使用名称查询");
        findboxid.getChildren().addAll(idtext,btid);
        findboxname.getChildren().addAll(nametext,btname);
        Button btadd=new Button("增加新的诊疗项目");
        Button btall=new Button("显示全部诊疗项目");
        pane.getChildren().addAll(new Label("诊疗项目信息维护"),findboxid,findboxname,btadd,btall);
        btadd.setOnAction(e->main.push(treatmentAdd()));
        btall.setOnAction(e->main.push(treatmentAll()));
        btid.setOnAction(e->{
            if(idtext.getText()==null||idtext.getText().length()==0){
                AlertDialog.display("错误","查找内容不能为空!");
                return;
            }
            InfoObject obj=main.getServerManage().find(ObjName.Treatment,idtext.getText());
            if(obj instanceof Treatment){
                main.push(treatmentDisplay(((Treatment) obj)));
            }else AlertDialog.display("失败","查找失败,没有找到!");
        });
        btname.setOnAction(e->{
            if(nametext.getText()==null||nametext.getText().length()==0){
                AlertDialog.display("错误","查找内容不能为空!");
                return;
            }
            Iterator<InfoObject> it=main.getServerManage().getAll(ObjName.Treatment);
            Treatment tr=null;
            String s=nametext.getText();
            while(it.hasNext()){
                tr= ((Treatment) it.next());
                if(s.equals(tr.getName()))break;
                else tr=null;
            }
            if(tr==null)AlertDialog.display("失败","查找失败,没有找到!");
            else main.push(treatmentDisplay(tr));
        });
        findboxid.setStyle("-fx-alignment: center");
        findboxname.setStyle("-fx-alignment: center");
        pane.setStyle("-fx-spacing:10;-fx-alignment: center;");
        return pane;
    }


    private GridPane treatmentAdd(){
        GridPane pane=new GridPane();
        pane.add(new Label("编号 "),1,1);
        pane.add(new Label("收费类别"),1,2);
        pane.add(new Label("收费项目等级"),1,3);
        pane.add(new Label("医院等级"),1,4);
        pane.add(new Label("是否需要审批"),1,5);
        pane.add(new Label("项目名称"),1,6);
        pane.add(new Label("单位"),1,7);
        pane.add(new Label("生产厂家"),1,8);
        pane.add(new Label("限制使用的范围"),1,9);
        pane.add(new Label("备注"),1,10);
        TextField code=new TextField();
        pane.add(code,2,1);
        TextField payType=new TextField();
        pane.add(payType,2,2);
        ChoiceBox<String> payLevel=new ChoiceBox<>();
        payLevel.getItems().addAll("甲类","乙类","丙类");
        pane.add(payLevel,2,3);
        ChoiceBox<String> hospitalLevel=new ChoiceBox<>();
        hospitalLevel.getItems().addAll("全部医院","一级医院","二级医院","三级医院","社区医院");
        pane.add(hospitalLevel,2,4);
        CheckBox needCheck=new CheckBox();
        pane.add(needCheck,2,5);
        TextField name=new TextField();
        pane.add(name,2,6);
        TextField unit=new TextField();
        pane.add(unit,2,7);
        TextField productedName=new TextField();
        pane.add(productedName,2,8);
        TextField range=new TextField();
        pane.add(range,2,9);
        TextField remark=new TextField();
        pane.add(remark,2,10);
        Button bt=new Button("提交");
        pane.add(bt,2,12);
        bt.setOnAction(e->{
            if(code.getText()==null||code.getText().length()==0){
                AlertDialog.display("错误","编号不能为空!");
                return;
            }
            Treatment tr=new Treatment(code.getText(),payType.getText(),StringToPayLevel(payLevel.getValue()),
                    StringToHospitalLevel(hospitalLevel.getValue()),needCheck.isSelected(),name.getText(),
                    unit.getText(),productedName.getText(),remark.getText(), range.getText());
            main.getServerManage().add(main.getLoginId(),ObjName.Treatment,tr);
            AlertDialog.display("成功","增加成功!");
            main.goBack();
        });
        pane.setStyle("-fx-padding: 20;-fx-hgap: 10;-fx-vgap:8;-fx-alignment: center;-fx-background-color: rgba(255,255,255,0.6);-fx-font-size: 20px");
        return pane;
    }

    private GridPane treatmentDisplay(Treatment tr){
        GridPane pane=new GridPane();
        pane.add(new Label("编号 "),1,1);
        pane.add(new Label("收费类别"),1,2);
        pane.add(new Label("收费项目等级"),1,3);
        pane.add(new Label("医院等级"),1,4);
        pane.add(new Label("是否需要审批"),1,5);
        pane.add(new Label("项目名称"),1,6);
        pane.add(new Label("单位"),1,7);
        pane.add(new Label("生产厂家"),1,8);
        pane.add(new Label("限制使用的范围"),1,9);
        pane.add(new Label("备注"),1,10);
        TextField code=new TextField();
        code.setText(tr.getCode());code.setDisable(true);
        pane.add(code,2,1);
        TextField payType=new TextField();
        payType.setText(tr.getPayType());
        pane.add(payType,2,2);
        ChoiceBox<String> payLevel=new ChoiceBox<>();
        payLevel.getItems().addAll("甲类","乙类","丙类");
        payLevel.setValue(PayLevelToString(tr.getPayLevel()));
        pane.add(payLevel,2,3);
        ChoiceBox<String> hospitalLevel=new ChoiceBox<>();
        hospitalLevel.getItems().addAll("全部医院","一级医院","二级医院","三级医院","社区医院");
        hospitalLevel.setValue(HospitalLevelToString(tr.getHospitalLevel()));
        pane.add(hospitalLevel,2,4);
        CheckBox needCheck=new CheckBox();
        needCheck.setSelected(tr.isNeedCheck());
        pane.add(needCheck,2,5);
        TextField name=new TextField();
        name.setText(tr.getName());
        pane.add(name,2,6);
        TextField unit=new TextField();
        unit.setText(tr.getUnit());
        pane.add(unit,2,7);
        TextField productedName=new TextField();
        productedName.setText(tr.getProductedName());
        pane.add(productedName,2,8);
        TextField range=new TextField();
        range.setText(tr.getRange());
        pane.add(range,2,9);
        TextField remark=new TextField();
        remark.setText(tr.getRemark());
        pane.add(remark,2,10);
        Button btdel=new Button("删除");
        btdel.setStyle("-fx-background-color: red");
        Button btReturn=new Button("返回");
        btReturn.setOnAction(e->main.goBack());
        Button btSet=new Button("保存修改");
        pane.add(btSet,2,11);
        pane.add(btdel,4,11);
        pane.add(btReturn,6,11);
        btdel.setOnAction(e->{
            ConfirmDialog.display("警告","删除之后将无法找回,确认要删除吗?");
            if(ConfirmDialog.buttonSelected.equals(ConfirmDialog.Response.YES)){
                main.getServerManage().delete(main.getLoginId(),ObjName.Treatment,tr.getCode());
                AlertDialog.display("成功","删除成功!");
                main.goBack();
            }
        });
        btSet.setOnAction(e->{
            Treatment newT=new Treatment(tr.getCode(),payType.getText(),StringToPayLevel(payLevel.getValue()),
                    StringToHospitalLevel(hospitalLevel.getValue()),needCheck.isSelected(),name.getText(),
                    unit.getText(),productedName.getText(),remark.getText(),range.getText());
            if(main.getServerManage().set(main.getLoginId(),ObjName.Treatment,tr.getCode(),newT)){
                AlertDialog.display("成功","修改成功!");
            }else AlertDialog.display("失败","修改失败!");
        });
        pane.setStyle("-fx-padding: 20;-fx-hgap: 10;-fx-vgap:8;-fx-alignment: center;-fx-background-color: rgba(255,255,255,0.6);-fx-font-size: 20px");
        return pane;
    }


    private BorderPane treatmentAll(){
        BorderPane borderPane=new BorderPane();
        Label status=new Label("状态: 加载完成!");
        status.setStyle("-fx-font-size: 14px;-fx-background-color: rgba(255,255,255,0.7)");
        borderPane.setBottom(status);
        GridPane pane=new GridPane();
        pane.add(new Label("编号:"),1,1);
        pane.add(new Label("收费类别"),2,1);
        pane.add(new Label("收费项目等级"),3,1);
        pane.add(new Label("医院等级"),4,1);
        pane.add(new Label("项目名称"),5,1);
        pane.add(new Label("备注"),6,1);
        Iterator<InfoObject> it=main.getServerManage().getAll(ObjName.Treatment);
        int i=2;
        while(it.hasNext()){
            Treatment tr= ((Treatment) it.next());
            Label l1=new Label(tr.getCode());
            Label l2=new Label(tr.getPayType());
            Label l3=new Label(PayLevelToString(tr.getPayLevel()));
            Label l4=new Label(HospitalLevelToString(tr.getHospitalLevel()));
            Label l5=new Label(tr.getName());
            Label l6=new Label(tr.getRemark());
            Button btdel=new Button("删除");
            Button btset=new Button("修改");
            btdel.setStyle("-fx-background-color: red");
            btset.setOnAction(e->main.push(treatmentDisplay(tr)));
            btdel.setOnAction(e->{
                ConfirmDialog.display("警告","删除之后将不可恢复,确认要删除吗?");
                if(ConfirmDialog.buttonSelected.equals(ConfirmDialog.Response.YES)){
                    main.getServerManage().delete(main.getLoginId(),ObjName.Treatment,tr.getCode());
                    btdel.setDisable(true);btset.setDisable(true);
                    status.setText("状态: 删除"+tr.getCode()+"成功!");
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
            main.push(treatmentAll());
        });
        borderPane.setTop(btf);
        pane.setAlignment(Pos.TOP_CENTER);
        ScrollPane scrollPane=new ScrollPane();
        scrollPane.setFitToWidth(true);
        scrollPane.setContent(pane);
        borderPane.setCenter(scrollPane);
        return borderPane;
    }


    private VBox facilities(){
        main.setLeftBarActive(2);
        VBox pane=new VBox();
        HBox findBox=new HBox();
        TextField idText=new TextField();
        Button btfind=new Button("通过编号查询");
        findBox.getChildren().addAll(idText,btfind);
        Button btadd=new Button("增加新的服务设施项目");
        Button btall=new Button("显示所有服务设施项目");
        pane.getChildren().addAll(new Label("服务设施项目信息维护"),findBox,btadd,btall);
        btadd.setOnAction(e->main.push(facilitiesAdd()));
        btall.setOnAction(e->main.push(facilitiesAll()));
        btfind.setOnAction(e->{
            if(idText.getText()==null||idText.getText().length()==0){
                AlertDialog.display("错误","查找内容不能为空!");
                return;
            }
            InfoObject obj=main.getServerManage().find(ObjName.Facilities,idText.getText());
            if(obj instanceof Facilities){
                main.push(facilitiesDisplay(((Facilities) obj)));
            }else{
                AlertDialog.display("失败","查找失败,没有找到!");
            }
        });
        findBox.setStyle("-fx-alignment: center");
        pane.setStyle("-fx-spacing:10;-fx-alignment: center;");
        return pane;
    }

    private GridPane facilitiesAdd(){
        GridPane pane=new GridPane();
        pane.add(new Label("编号 "),1,1);
        pane.add(new Label("名称 "),1,2);
        pane.add(new Label("收费类型"),1,3);
        pane.add(new Label("限制使用范围"),1,4);
        pane.add(new Label("备注 "),1,5);
        TextField code=new TextField();
        pane.add(code,2,1);
        TextField name=new TextField();
        pane.add(name,2,2);
        TextField payType=new TextField();
        pane.add(payType,2,3);
        TextField range=new TextField();
        pane.add(range,2,4);
        TextField remark=new TextField();
        pane.add(remark,2,5);
        Button bt=new Button("提交");
        pane.add(bt,2,7);
        bt.setOnAction(e->{
            if(code.getText()==null||code.getText().length()==0){
                AlertDialog.display("错误","编号不能为空!");
                return;
            }
            Facilities fa=new Facilities(code.getText(),name.getText(),payType.getText(),
                                            remark.getText(),range.getText());
            main.getServerManage().add(main.getLoginId(),ObjName.Facilities,fa);
            AlertDialog.display("成功","增加成功!");
            main.goBack();
        });
        pane.setStyle("-fx-padding: 20;-fx-hgap: 13;-fx-vgap:20;-fx-alignment: center;-fx-background-color: rgba(255,255,255,0.6)");
        return pane;
    }

    private GridPane facilitiesDisplay(Facilities fac){
        GridPane pane=new GridPane();
        pane.add(new Label("编号 "),1,1);
        pane.add(new Label("名称 "),1,2);
        pane.add(new Label("收费类型"),1,3);
        pane.add(new Label("限制使用范围"),1,4);
        pane.add(new Label("备注 "),1,5);
        TextField code=new TextField();
        code.setText(fac.getCode());code.setDisable(true);
        pane.add(code,2,1);
        TextField name=new TextField();
        name.setText(fac.getName());
        pane.add(name,2,2);
        TextField payType=new TextField();
        payType.setText(fac.getPayType());
        pane.add(payType,2,3);
        TextField range=new TextField();
        range.setText(fac.getRange());
        pane.add(range,2,4);
        TextField remark=new TextField();
        remark.setText(fac.getRemark());
        pane.add(remark,2,5);
        Button btReturn=new Button("返回");
        btReturn.setOnAction(e->main.goBack());
        Button btDel=new Button("删除");
        btDel.setStyle("-fx-background-color: red");
        pane.add(btDel,4,7);
        pane.add(btReturn,6,7);
        Button btSet=new Button("保存修改");
        pane.add(btSet,2,7);
        btDel.setOnAction(e->{
            ConfirmDialog.display("警告","删除后数据将不可恢复,确认删除?");
            if(ConfirmDialog.buttonSelected.equals(ConfirmDialog.Response.YES)){
                main.getServerManage().delete(main.getLoginId(), ObjName.Facilities,fac.getCode());
                AlertDialog.display("成功","删除成功!");
                main.goBack();
            }
        });
        btSet.setOnAction(e->{
            Facilities newF=new Facilities(fac.getCode(),name.getText(),payType.getText(),remark.getText(),
                                            range.getText());
            if(main.getServerManage().set(main.getLoginId(),ObjName.Facilities,fac.getCode(),newF)){
                AlertDialog.display("成功","修改成功!");
            }else AlertDialog.display("失败","修改失败!");
        });
        pane.setStyle("-fx-padding: 20;-fx-hgap: 13;-fx-vgap:20;-fx-alignment: center;-fx-background-color: rgba(255,255,255,0.6)");
        return pane;
    }


    private BorderPane facilitiesAll(){
        BorderPane borderPane=new BorderPane();
        Label status=new Label("状态: 加载完成!");
        status.setStyle("-fx-font-size: 14px;-fx-background-color: rgba(255,255,255,0.7)");
        borderPane.setBottom(status);
        GridPane pane=new GridPane();
        pane.add(new Label("编号"),1,1);
        pane.add(new Label("名称"),2,1);
        pane.add(new Label("收费类别"),3,1);
        pane.add(new Label("限制使用范围"),4,1);
        pane.add(new Label("备注"),5,1);
        Iterator<InfoObject> it=main.getServerManage().getAll(ObjName.Facilities);
        int i=2;
        while(it.hasNext()){
            Facilities fac= ((Facilities) it.next());
            Label l1=new Label(fac.getCode());
            Label l2=new Label(fac.getName());
            Label l3=new Label(fac.getPayType());
            Label l4=new Label(fac.getRange());
            Label l5=new Label(fac.getRemark());
            Button btdel=new Button("删除");
            Button btset=new Button("修改");
            btdel.setStyle("-fx-background-color: red");
            btset.setOnAction(e->main.push(facilitiesDisplay(fac)));
            btdel.setOnAction(e->{
                ConfirmDialog.display("警告","删除之后将不可恢复,确认要删除吗?");
                if(ConfirmDialog.buttonSelected.equals(ConfirmDialog.Response.YES)){
                    main.getServerManage().delete(main.getLoginId(),ObjName.Facilities,fac.getCode());
                    btdel.setDisable(true);btset.setDisable(true);
                    status.setText("状态: 删除"+fac.getCode()+"成功!");
                }
            });
            pane.add(l1,1,i);
            pane.add(l2,2,i);
            pane.add(l3,3,i);
            pane.add(l4,4,i);
            pane.add(l5,5,i);
            pane.add(btset,6,i);
            pane.add(btdel,7,i);
            ++i;
        }
        pane.setGridLinesVisible(true);
        Button btf=new Button("刷新");
        btf.setOnAction(e->{
            main.goBack();
            main.push(facilitiesAll());
        });
        borderPane.setTop(btf);
        pane.setAlignment(Pos.TOP_CENTER);
        ScrollPane scrollPane=new ScrollPane();
        scrollPane.setFitToWidth(true);
        scrollPane.setContent(pane);
        borderPane.setCenter(scrollPane);
        return borderPane;
    }


    private VBox institution(){
        main.setLeftBarActive(3);
        VBox pane=new VBox();
        HBox findboxid=new HBox();
        HBox findboxname=new HBox();
        TextField idtext=new TextField();
        TextField nametext=new TextField();
        Button btid=new Button("使用编号查询");
        Button btname=new Button("使用名称查询");
        findboxid.getChildren().addAll(idtext,btid);
        findboxname.getChildren().addAll(nametext,btname);
        Button btadd=new Button("增加新的定点医疗机构");
        Button btall=new Button("显示全部定点医疗机构");
        pane.getChildren().addAll(new Label("定点医疗机构信息维护"),findboxid,findboxname,btadd,btall);
        btadd.setOnAction(e->main.push(institutionAdd()));
        btall.setOnAction(e->main.push(institutionAll()));
        btid.setOnAction(e->{
            if(idtext.getText()==null||idtext.getText().length()==0){
                AlertDialog.display("错误","查找内容不能为空!");
                return;
            }
            InfoObject obj=main.getServerManage().find(ObjName.Institution,idtext.getText());
            if(obj instanceof Institution){
                main.push(institutionDisplay(((Institution) obj)));
            }else AlertDialog.display("失败","查找失败,没有找到!");
        });
        btname.setOnAction(e->{
            if(nametext.getText()==null||nametext.getText().length()==0){
                AlertDialog.display("错误","查找内容不能为空!");
                return;
            }
            Iterator<InfoObject> it=main.getServerManage().getAll(ObjName.Institution);
            Institution ins=null;
            String s=nametext.getText();
            while(it.hasNext()){
                ins= ((Institution) it.next());
                if(s.equals(ins.getName()))break;
                else ins=null;
            }
            if(ins==null)AlertDialog.display("失败","查找失败,没有找到!");
            else main.push(institutionDisplay(ins));
        });
        findboxid.setStyle("-fx-alignment: center");
        findboxname.setStyle("-fx-alignment: center");
        pane.setStyle("-fx-spacing:10;-fx-alignment: center;");
        return pane;
    }


    private GridPane institutionAdd(){
        GridPane pane=new GridPane();
        pane.add(new Label("编号"),1,1);
        pane.add(new Label("名称"),1,2);
        pane.add(new Label("医院等级"),1,3);
        pane.add(new Label("医疗机构类别"),1,4);
        pane.add(new Label("邮政编码"),1,5);
        pane.add(new Label("法人姓名"),1,6);
        pane.add(new Label("法人电话"),1,7);
        pane.add(new Label("联系人姓名"),1,8);
        pane.add(new Label("联系人电话"),1,9);
        pane.add(new Label("地址"),1,10);
        pane.add(new Label("备注"),1,11);
        TextField code=new TextField();
        pane.add(code,2,1);
        TextField name=new TextField();
        pane.add(name,2,2);
        ChoiceBox<String> hospitalLevel=new ChoiceBox<>();
        hospitalLevel.getItems().addAll("全部医院","一级医院","二级医院","三级医院","社区医院");
        pane.add(hospitalLevel,2,3);
        ChoiceBox<String> type=new ChoiceBox<>();
        type.getItems().addAll("医院","社区医院");
        pane.add(type,2,4);
        TextField postnum=new TextField();
        pane.add(postnum,2,5);
        TextField legalName=new TextField();
        pane.add(legalName,2,6);
        TextField legalnum=new TextField();
        pane.add(legalnum,2,7);
        TextField connectname=new TextField();
        pane.add(connectname,2,8);
        TextField connectnum=new TextField();
        pane.add(connectnum,2,9);
        TextField address=new TextField();
        pane.add(address,2,10);
        TextField remark=new TextField();
        pane.add(remark,2,11);
        Button bt=new Button("提交");
        pane.add(bt,2,14);
        bt.setOnAction(e->{
            if(code.getText()==null||code.getText().length()==0){
                AlertDialog.display("错误","编号不能为空!");
                return;
            }
            HostipalType hp=null;
            if(type.getValue()!=null){
                if(type.getValue().equals("医院"))hp=HostipalType.Hospital;
                else hp=HostipalType.Community;
            }
            try{
                Institution ins=new Institution(code.getText(),name.getText(),StringToHospitalLevel(hospitalLevel.getValue()),
                        hp,Integer.parseInt(postnum.getText()),legalName.getText(),Integer.parseInt(legalnum.getText()),
                        connectname.getText(),Integer.parseInt(connectnum.getText()),address.getText(),remark.getText());
                main.getServerManage().add(main.getLoginId(),ObjName.Institution,ins);
                AlertDialog.display("成功","增加成功!");
                main.goBack();
            }catch (NumberFormatException nue){
                AlertDialog.display("错误","请输入正确的数字!");
            }
        });
        pane.setStyle("-fx-background-color:rgba(255,255,255,0.6);-fx-vgap:9;-fx-hgap:13;-fx-font-size:20px;-fx-alignment: center");
        return pane;
    }

    private GridPane institutionDisplay(Institution ins){
        GridPane pane=new GridPane();
        pane.add(new Label("编号"),1,1);
        pane.add(new Label("名称"),1,2);
        pane.add(new Label("医院等级"),1,3);
        pane.add(new Label("医疗机构类别"),1,4);
        pane.add(new Label("邮政编码"),1,5);
        pane.add(new Label("法人姓名"),1,6);
        pane.add(new Label("法人电话"),1,7);
        pane.add(new Label("联系人姓名"),1,8);
        pane.add(new Label("联系人电话"),1,9);
        pane.add(new Label("地址"),1,10);
        pane.add(new Label("备注"),1,11);
        TextField code=new TextField();
        pane.add(code,2,1);
        code.setText(ins.getCode());code.setDisable(true);
        TextField name=new TextField();
        pane.add(name,2,2);
        name.setText(ins.getName());
        ChoiceBox<String> hospitalLevel=new ChoiceBox<>();
        hospitalLevel.getItems().addAll("全部医院","一级医院","二级医院","三级医院","社区医院");
        pane.add(hospitalLevel,2,3);
        hospitalLevel.setValue(HospitalLevelToString(ins.getHospitalLevel()));
        ChoiceBox<String> type=new ChoiceBox<>();
        type.getItems().addAll("医院","社区医院");
        if(ins.getType()!=null){
            if(ins.getType().equals(HostipalType.Hospital))type.setValue("医院");
            else type.setValue("社区医院");
        }
        pane.add(type,2,4);
        TextField postnum=new TextField();
        postnum.setText(String.valueOf(ins.getPostNum()));
        pane.add(postnum,2,5);
        TextField legalName=new TextField();
        pane.add(legalName,2,6);
        legalName.setText(ins.getName());
        TextField legalnum=new TextField();
        pane.add(legalnum,2,7);
        legalnum.setText(String.valueOf(ins.getLegalPhoneNum()));
        TextField connectname=new TextField();
        pane.add(connectname,2,8);
        connectname.setText(ins.getConnectName());
        TextField connectnum=new TextField();
        pane.add(connectnum,2,9);
        connectnum.setText(String.valueOf(ins.getConnectPhoneNum()));
        TextField address=new TextField();
        pane.add(address,2,10);
        address.setText(ins.getAddress());
        TextField remark=new TextField();
        pane.add(remark,2,11);
        remark.setText(ins.getRemark());
        Button btReturn=new Button("返回");
        btReturn.setOnAction(e->main.goBack());
        pane.add(btReturn,6,12);
        Button btDel=new Button("删除");
        pane.add(btDel,4,12);
        btDel.setStyle("-fx-background-color: red");
        btDel.setOnAction(e->{
            ConfirmDialog.display("警告","删除之后将无法找回,确认删除吗?");
            if(ConfirmDialog.buttonSelected.equals(ConfirmDialog.Response.YES)) {
                main.getServerManage().delete(main.getLoginId(),ObjName.Institution,ins.getCode());
                AlertDialog.display("成功","删除成功!");
                main.goBack();
            }
        });
        Button btSet=new Button("确认修改");
        pane.add(btSet,2,12);
        btSet.setOnAction(e->{
            HostipalType hp=null;
            if(type.getValue()!=null){
                if(type.getValue().equals("医院"))hp=HostipalType.Hospital;
                else hp=HostipalType.Community;
            }
            try{
                Institution newIns=new Institution(code.getText(),name.getText(),StringToHospitalLevel(hospitalLevel.getValue()),
                    hp,Integer.parseInt(postnum.getText()),legalName.getText(),Integer.parseInt(legalnum.getText()),
                    connectname.getText(),Integer.parseInt(connectnum.getText()),address.getText(),remark.getText());
                if(main.getServerManage().set(main.getLoginId(),ObjName.Institution,ins.getCode(),newIns)) {
                    AlertDialog.display("成功","修改成功!");
                }else AlertDialog.display("失败","修改失败");
            }catch (NumberFormatException nue){
                AlertDialog.display("错误","请输入正确的数字!");
            }
        });
        pane.setStyle("-fx-background-color:rgba(255,255,255,0.6);-fx-vgap:9;-fx-hgap:13;-fx-font-size:20px;-fx-alignment: center");
        return pane;
    }


    private BorderPane institutionAll(){
        BorderPane borderPane=new BorderPane();
        Label status=new Label("状态: 加载完成!");
        status.setStyle("-fx-font-size: 14px;-fx-background-color: rgba(255,255,255,0.7)");
        borderPane.setBottom(status);
        GridPane pane=new GridPane();
        pane.add(new Label("编号"),1,1);
        pane.add(new Label("机构名称"),2,1);
        pane.add(new Label("医院等级"),3,1);
        pane.add(new Label("法人姓名"),4,1);
        pane.add(new Label("联系人姓名"),5,1);
        pane.add(new Label("地址"),6,1);
        Iterator<InfoObject> it=main.getServerManage().getAll(ObjName.Institution);
        int i=2;
        while(it.hasNext()){
            Institution ins= ((Institution) it.next());
            Label l1=new Label(ins.getCode());
            Label l2=new Label(ins.getName());
            Label l3=new Label(HospitalLevelToString(ins.getHospitalLevel()));
            Label l4=new Label(ins.getLegalName());
            Label l5=new Label(ins.getConnectName());
            Label l6=new Label(ins.getAddress());
            Button btdel=new Button("删除");
            Button btset=new Button("修改");
            btdel.setStyle("-fx-background-color: red");
            btset.setOnAction(e->main.push(institutionDisplay(ins)));
            btdel.setOnAction(e->{
                ConfirmDialog.display("警告","删除之后将不可恢复,确认要删除吗?");
                if(ConfirmDialog.buttonSelected.equals(ConfirmDialog.Response.YES)){
                    main.getServerManage().delete(main.getLoginId(),ObjName.Institution,ins.getCode());
                    btdel.setDisable(true);btset.setDisable(true);
                    status.setText("状态: 删除"+ins.getCode()+"成功!");
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
            main.push(institutionAll());
        });
        borderPane.setTop(btf);
        pane.setAlignment(Pos.TOP_CENTER);
        ScrollPane scrollPane=new ScrollPane();
        scrollPane.setFitToWidth(true);
        scrollPane.setContent(pane);
        borderPane.setCenter(scrollPane);
        return borderPane;
    }

    private VBox disease(){
        main.setLeftBarActive(4);
        VBox pane=new VBox();
        HBox findBox=new HBox();
        TextField idText=new TextField();
        Button btfind=new Button("通过编号查询");
        findBox.getChildren().addAll(idText,btfind);
        Button btadd=new Button("增加新的病种信息");
        Button btall=new Button("显示所有病种信息");
        pane.getChildren().addAll(new Label("病种信息维护"),findBox,btadd,btall);
        btadd.setOnAction(e->main.push(diseaseAdd()));
        btall.setOnAction(e->main.push(diseaseAll()));
        btfind.setOnAction(e->{
            if(idText.getText()==null||idText.getText().length()==0){
                AlertDialog.display("错误","查找内容不能为空!");
                return;
            }
            InfoObject obj=main.getServerManage().find(ObjName.DiseaseSpecies,idText.getText());
            if(obj instanceof DiseaseSpecies){
                main.push(diseaseDisplay(((DiseaseSpecies) obj)));
            }else{
                AlertDialog.display("失败","查找失败,没有找到!");
            }
        });
        findBox.setStyle("-fx-alignment: center");
        pane.setStyle("-fx-spacing:10;-fx-alignment: center;");
        return pane;

    }


    private GridPane diseaseAdd(){
        GridPane pane=new GridPane();
        pane.add(new Label("编号 "),1,1);
        pane.add(new Label("名称 "),1,2);
        pane.add(new Label("类型 "),1,3);
        pane.add(new Label("能够注销"),1,4);
        pane.add(new Label("备注 "),1,5);
        TextField code=new TextField();
        TextField name=new TextField();
        TextField type=new TextField();
        CheckBox reimbursement=new CheckBox();
        TextField remark=new TextField();
        pane.add(code,2,1);
        pane.add(name,2,2);
        pane.add(type,2,3);
        pane.add(reimbursement,2,4);
        pane.add(remark,2,5);
        Button bt=new Button("提交");
        pane.add(bt,2,7);
        bt.setOnAction(e->{
            if(code.getText()==null||code.getText().length()==0){
                AlertDialog.display("错误","编号不能为空!");
                return;
            }
            DiseaseSpecies dis=new DiseaseSpecies(code.getText(),name.getText(),type.getText(),
                    reimbursement.isSelected(),remark.getText());
            main.getServerManage().add(main.getLoginId(),ObjName.DiseaseSpecies,dis);
            AlertDialog.display("成功","增加成功!");
            main.goBack();
        });
        pane.setStyle("-fx-padding: 20;-fx-hgap: 13;-fx-vgap:20;-fx-alignment: center");
        return pane;
    }


    private GridPane diseaseDisplay(DiseaseSpecies dis){
        GridPane pane=new GridPane();
        pane.add(new Label("编号 "),1,1);
        pane.add(new Label("名称 "),1,2);
        pane.add(new Label("类型 "),1,3);
        pane.add(new Label("能够注销"),1,4);
        pane.add(new Label("备注 "),1,5);
        TextField code=new TextField();
        code.setText(dis.getCode());code.setDisable(true);
        TextField name=new TextField();
        name.setText(dis.getName());
        TextField type=new TextField();
        type.setText(dis.getType());
        CheckBox reimbursement=new CheckBox();
        reimbursement.setSelected(dis.isReimbursement());
        TextField remark=new TextField();
        remark.setText(dis.getRemark());
        pane.add(code,2,1);
        pane.add(name,2,2);
        pane.add(type,2,3);
        pane.add(reimbursement,2,4);
        pane.add(remark,2,5);
        Button btReturn=new Button("返回");
        btReturn.setOnAction(e->main.goBack());
        pane.add(btReturn,5,7);
        Button btDel=new Button("删除");
        btDel.setStyle("-fx-background-color: red");
        pane.add(btDel,3,7);
        Button btSet=new Button("确认修改");
        pane.add(btSet,2,7);
        btDel.setOnAction(e->{
            ConfirmDialog.display("警告","删除之后将无法找回,确认删除吗?");
            if(ConfirmDialog.buttonSelected.equals(ConfirmDialog.Response.YES)) {
                main.getServerManage().delete(main.getLoginId(),ObjName.DiseaseSpecies,dis.getCode());
                AlertDialog.display("成功", "删除成功!");
                main.goBack();
            }
        });
        btSet.setOnAction(e->{
            DiseaseSpecies newDis=new DiseaseSpecies(code.getText(),name.getText(),type.getText(),
                    reimbursement.isSelected(),remark.getText());
            if(main.getServerManage().set(main.getLoginId(),ObjName.DiseaseSpecies,dis.getCode(),newDis)){
                AlertDialog.display("成功","修改成功!");
            }else AlertDialog.display("失败","修改失败!");
        });
        pane.setStyle("-fx-padding: 20;-fx-hgap: 13;-fx-vgap:20;-fx-alignment: center");
        return pane;
    }


    private BorderPane diseaseAll(){
        BorderPane borderPane=new BorderPane();
        Label status=new Label("状态: 加载完成!");
        status.setStyle("-fx-font-size: 14px;-fx-background-color: rgba(255,255,255,0.7)");
        borderPane.setBottom(status);
        GridPane pane=new GridPane();
        pane.add(new Label("编号"),1,1);
        pane.add(new Label("名称"),2,1);
        pane.add(new Label("类型"),3,1);
        pane.add(new Label("是否能够报销"),4,1);
        pane.add(new Label("备注"),5,1);
        Iterator<InfoObject> it=main.getServerManage().getAll(ObjName.DiseaseSpecies);
        int i=2;
        while(it.hasNext()){
            DiseaseSpecies dis= ((DiseaseSpecies) it.next());
            Label l1=new Label(dis.getCode());
            Label l2=new Label(dis.getName());
            Label l3=new Label(dis.getType());
            Label l4=new Label(String.valueOf(dis.isReimbursement()));
            Label l5=new Label(dis.getRemark());
            Button btdel=new Button("删除");
            Button btset=new Button("修改");
            btdel.setStyle("-fx-background-color: red");
            btset.setOnAction(e->main.push(diseaseDisplay(dis)));
            btdel.setOnAction(e->{
                ConfirmDialog.display("警告","删除之后将不可恢复,确认要删除吗?");
                if(ConfirmDialog.buttonSelected.equals(ConfirmDialog.Response.YES)){
                    main.getServerManage().delete(main.getLoginId(),ObjName.DiseaseSpecies,dis.getCode());
                    btdel.setDisable(true);btset.setDisable(true);
                    status.setText("状态: 删除"+dis.getCode()+"成功!");
                }
            });
            pane.add(l1,1,i);
            pane.add(l2,2,i);
            pane.add(l3,3,i);
            pane.add(l4,4,i);
            pane.add(l5,5,i);
            pane.add(btset,6,i);
            pane.add(btdel,7,i);
            ++i;
        }
        pane.setGridLinesVisible(true);
        Button btf=new Button("刷新");
        btf.setOnAction(e->{
            main.goBack();
            main.push(diseaseAll());
        });
        borderPane.setTop(btf);
        pane.setAlignment(Pos.TOP_CENTER);
        ScrollPane scrollPane=new ScrollPane();
        scrollPane.setFitToWidth(true);
        scrollPane.setContent(pane);
        borderPane.setCenter(scrollPane);
        return borderPane;
    }


    private Level StringToPayLevel(String s){
        if(s==null)return null;
        else if(s.equals("甲类"))return Level.One;
        else if(s.equals("乙类"))return Level.Two;
        else if(s.equals("丙类"))return Level.Three;
        else return null;
    }

    private String PayLevelToString(Level l){
        if(l==null)return null;
        else if(l.equals(Level.One))return "甲类";
        else if(l.equals(Level.Two))return "乙类";
        else if(l.equals(Level.Three))return "丙类";
        return null;
    }
    private Level StringToHospitalLevel(String s){
        if(s==null)return null;
        else if(s.equals("全部医院"))return Level.Zero;
        else if(s.equals("一级医院"))return Level.One;
        else if(s.equals("二级医院"))return Level.Two;
        else if(s.equals("三级医院"))return Level.Three;
        else if(s.equals("社区医院"))return Level.Four;
        return null;
    }

    private String HospitalLevelToString(Level l){
        if(l==null)return null;
        else if(l.equals(Level.Zero))return "全部医院";
        else if(l.equals(Level.One))return "一级医院";
        else if(l.equals(Level.Two))return "二级医院";
        else if(l.equals(Level.Three))return "三级医院";
        else if(l.equals(Level.Four))return "社区医院";
        return null;
    }


}
