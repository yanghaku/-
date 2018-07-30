package client;

import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import server.CalculationParameter;
import server.InfoObject;
import server.CalculationParameter.*;
import server.Medical.*;
import server.RegistedObj.ObjName;
import server.Social.Person;
import server.baoxiao.*;

import java.time.ZoneId;
import java.util.*;


/**
 * @author yangbo
 *
 * 报销界面模块
 */

public class BaoxiaoPane extends StackPane {

    private Main main;

    public BaoxiaoPane(Main main) {
        this.main = main;
        getChildren().add(baoxiao());
    }

    public VBox getLeftBar() {
        VBox vBox = new VBox();
        vBox.setSpacing(30);
        vBox.setAlignment(Pos.CENTER_LEFT);
        Button btBaoxiao = new Button("报销与预结算");
        btBaoxiao.setOnAction(e -> main.push(baoxiao()));
        Button btPatient = new Button("就诊信息维护");
        btPatient.setOnAction(e -> main.push(patient()));
        Button btPre = new Button("处方信息维护");
        btPre.setOnAction(e -> main.push(prescription()));
        Button btperson = new Button("就诊人员信息");
        btperson.setOnAction(e->main.push(sickPerson()));
        Button btQu = new Button("取消报销");
        btQu.setOnAction(e->main.push(quXiao()));
        vBox.getChildren().addAll(btBaoxiao, btPatient, btPre, btperson, btQu);
        vBox.setStyle("-fx-font-size: 19;-fx-background-color: rgba(255,255,255,0.6)");
        return vBox;
    }

    private VBox prescription() {
        main.setLeftBarActive(2);
        VBox pane = new VBox();
        HBox findBox = new HBox();
        TextField idText = new TextField();
        Button btfind = new Button("通过编号查询");
        findBox.getChildren().addAll(idText, btfind);
        Button btadd = new Button("增加新的处方");
        Button btall = new Button("显示所有处方信息");
        pane.getChildren().addAll(new Label("处方信息维护"), findBox, btadd, btall);
        btadd.setOnAction(e -> main.push(prescriptionAdd()));
        btall.setOnAction(e -> main.push(prescriptionAll()));
        btfind.setOnAction(e -> {
            if (idText.getText() == null || idText.getText().length() == 0) {
                AlertDialog.display("错误", "查找内容不能为空!");
                return;
            }
            InfoObject obj = main.getServerManage().find(ObjName.Prescription, idText.getText());
            if (obj instanceof Prescription) {
                main.push(prescriptionDisplay(((Prescription) obj)));
            } else {
                AlertDialog.display("失败", "查找失败,没有找到!");
            }
        });
        findBox.setStyle("-fx-alignment: center");
        pane.setStyle("-fx-spacing:10;-fx-alignment: center;");
        return pane;
    }


    private Pane prescriptionAdd() {
        GridPane pane = new GridPane();
        pane.add(new Label("编号 "), 1, 1);
        pane.add(new Label("住院号"), 1, 2);
        pane.add(new Label("项目编号"), 1, 3);
        pane.add(new Label("项目类型"), 1, 4);
        pane.add(new Label("单价"), 1, 5);
        pane.add(new Label("数量"), 1, 6);
        pane.add(new Label("人员编号"),1,7);
        TextField code = new TextField();
        TextField id = new TextField();
        TextField contentId = new TextField();
        ChoiceBox<String> contentType=new ChoiceBox<>();
        contentType.getItems().addAll("药品","诊疗项目","服务设施");
        TextField unitPrice = new TextField();
        TextField num = new TextField();
        TextField personId=new TextField();
        pane.add(code, 2, 1);
        pane.add(id, 2, 2);
        pane.add(contentId, 2, 3);
        pane.add(contentType, 2, 4);
        pane.add(unitPrice, 2, 5);
        pane.add(num, 2, 6);
        pane.add(personId,2,7);
        Button bt = new Button("提交");
        pane.add(bt, 2, 8);
        bt.setOnAction(e -> {
            if (code.getText() == null || code.getText().length() == 0) {
                AlertDialog.display("错误", "编号不能为空!");
                return;
            }
            try {
                Prescription pre = new Prescription(code.getText(), id.getText(), contentId.getText(),
                        contentType.getValue(), Double.parseDouble(unitPrice.getText()), Integer.parseInt(num.getText()),personId.getText());
                main.getServerManage().add(main.getLoginId(), ObjName.Prescription, pre);
                AlertDialog.display("成功", "增加成功!");
                main.goBack();
            } catch (NumberFormatException nue) {
                AlertDialog.display("错误", "数字格式错误!");
            }
        });
        pane.setStyle("-fx-padding: 20;-fx-hgap: 13;-fx-vgap:20;-fx-alignment: center");
        return pane;
    }

    private Pane prescriptionDisplay(Prescription pre) {
        GridPane pane = new GridPane();
        pane.add(new Label("编号 "), 1, 1);
        pane.add(new Label("住院号"), 1, 2);
        pane.add(new Label("项目编号"), 1, 3);
        pane.add(new Label("项目类型"), 1, 4);
        pane.add(new Label("单价"), 1, 5);
        pane.add(new Label("数量"), 1, 6);
        pane.add(new Label("人员编号"),1,7);
        TextField code = new TextField();
        code.setText(pre.getCode());
        TextField id = new TextField();
        id.setText(pre.getId());
        TextField contentId = new TextField();
        contentId.setText(pre.getContentId());
        ChoiceBox<String> contentType=new ChoiceBox<>();
        contentType.getItems().addAll("药品","诊疗项目","服务设施");
        contentType.setValue(pre.getContentType());
        TextField unitPrice = new TextField();
        unitPrice.setText(String.valueOf(pre.getUnitPrice()));
        TextField num = new TextField();
        num.setText(String.valueOf(pre.getNum()));
        TextField personId=new TextField();
        personId.setText(pre.getId());
        pane.add(code, 2, 1);
        pane.add(id, 2, 2);
        pane.add(contentId, 2, 3);
        pane.add(contentType, 2, 4);
        pane.add(unitPrice, 2, 5);
        pane.add(num, 2, 6);
        pane.add(personId,2, 7);
        Button btReturn = new Button("返回");
        btReturn.setOnAction(e -> main.goBack());
        pane.add(btReturn, 5, 8);
        Button btDel = new Button("删除");
        btDel.setStyle("-fx-background-color: red");
        pane.add(btDel, 3, 8);
        Button btSet = new Button("确认修改");
        pane.add(btSet, 2, 8);
        btDel.setOnAction(e -> {
            ConfirmDialog.display("警告", "删除之后将无法找回,确认删除吗?");
            if (ConfirmDialog.buttonSelected.equals(ConfirmDialog.Response.YES)) {
                main.getServerManage().delete(main.getLoginId(), ObjName.Prescription, pre.getCode());
                AlertDialog.display("成功", "删除成功!");
                main.goBack();
            }
        });
        btSet.setOnAction(e -> {
            try {
                Prescription newPre = new Prescription(code.getText(), id.getText(), contentId.getText(),
                        contentType.getValue(), Double.parseDouble(unitPrice.getText()), Integer.parseInt(num.getText()),personId.getText());

                if (main.getServerManage().set(main.getLoginId(), ObjName.Prescription, pre.getCode(), newPre)) {
                    AlertDialog.display("成功", "修改成功!");
                } else AlertDialog.display("失败", "修改失败!");
            } catch (NumberFormatException nue) {
                AlertDialog.display("错误", "数字格式错误!");
            }
        });
        pane.setStyle("-fx-padding: 20;-fx-hgap: 13;-fx-vgap:20;-fx-alignment: center");
        return pane;
    }


    private Pane prescriptionAll() {
        BorderPane borderPane = new BorderPane();
        Label status = new Label("状态: 加载完成!");
        status.setStyle("-fx-font-size: 14px;-fx-background-color: rgba(255,255,255,0.7)");
        borderPane.setBottom(status);
        GridPane pane = new GridPane();
        pane.add(new Label("编号"), 1, 1);
        pane.add(new Label("住院号"), 2, 1);
        pane.add(new Label("项目编号"), 3, 1);
        pane.add(new Label("项目类型"), 4, 1);
        pane.add(new Label("单价"), 5, 1);
        pane.add(new Label("数量"), 6, 1);
        pane.add(new Label("价格"), 7, 1);
        Iterator<InfoObject> it = main.getServerManage().getAll(ObjName.Prescription);
        int i = 2;
        while (it.hasNext()) {
            Prescription pre = ((Prescription) it.next());
            Label l1 = new Label(pre.getCode());
            Label l2 = new Label(pre.getId());
            Label l3 = new Label(pre.getContentId());
            Label l4 = new Label(pre.getContentType());
            Label l5 = new Label(String.valueOf(pre.getUnitPrice()));
            Label l6 = new Label(String.valueOf(pre.getNum()));
            Label l7 = new Label(String.valueOf(pre.getPrice()));
            Button btdel = new Button("删除");
            Button btset = new Button("修改");
            btdel.setStyle("-fx-background-color: red");
            btset.setOnAction(e -> main.push(prescriptionDisplay(pre)));
            btdel.setOnAction(e -> {
                ConfirmDialog.display("警告", "删除之后将不可恢复,确认要删除吗?");
                if (ConfirmDialog.buttonSelected.equals(ConfirmDialog.Response.YES)) {
                    main.getServerManage().delete(main.getLoginId(), ObjName.Prescription, pre.getCode());
                    btdel.setDisable(true);
                    btset.setDisable(true);
                    status.setText("状态: 删除" + pre.getCode() + "成功!");
                }
            });
            pane.add(l1, 1, i);
            pane.add(l2, 2, i);
            pane.add(l3, 3, i);
            pane.add(l4, 4, i);
            pane.add(l5, 5, i);
            pane.add(l6, 6, i);
            pane.add(l7, 7, i);
            pane.add(btset, 8, i);
            pane.add(btdel, 9, i);
            ++i;
        }
        pane.setGridLinesVisible(true);
        Button btf = new Button("刷新");
        btf.setOnAction(e -> {
            main.goBack();
            main.push(prescriptionAll());
        });
        borderPane.setTop(btf);
        pane.setAlignment(Pos.TOP_CENTER);
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setFitToWidth(true);
        scrollPane.setContent(pane);
        borderPane.setCenter(scrollPane);
        return borderPane;
    }

    private VBox patient() {
        main.setLeftBarActive(1);
        VBox pane = new VBox();
        HBox findBox = new HBox();
        TextField idText = new TextField();
        Button btfind = new Button("通过住院号查询");
        findBox.getChildren().addAll(idText, btfind);
        Button btadd = new Button("增加新的人员就诊信息");
        Button btall = new Button("显示所有人员就诊信息");
        pane.getChildren().addAll(new Label("人员就诊信息维护"), findBox, btadd, btall);
        btadd.setOnAction(e -> main.push(patientAdd()));
        btall.setOnAction(e -> main.push(patientAll()));
        btfind.setOnAction(e -> {
            if (idText.getText() == null || idText.getText().length() == 0) {
                AlertDialog.display("错误", "查找内容不能为空!");
                return;
            }
            InfoObject obj = main.getServerManage().find(ObjName.Patient, idText.getText());
            if (obj instanceof Patient) {
                main.push(patientDisplay(((Patient) obj)));
            } else {
                AlertDialog.display("失败", "查找失败,没有找到!");
            }
        });
        findBox.setStyle("-fx-alignment: center");
        pane.setStyle("-fx-spacing:10;-fx-alignment: center;");
        return pane;
    }


    private Pane patientAdd() {
        GridPane pane = new GridPane();
        pane.add(new Label("住院号"), 1, 1);
        pane.add(new Label("就诊人员ID"), 1, 2);
        pane.add(new Label("医疗人员类别类别"), 1, 3);
        pane.add(new Label("定点医疗机构编号"), 1, 4);
        pane.add(new Label("定点医疗机构名称"), 1, 5);
        pane.add(new Label("入院日期"), 1, 6);
        pane.add(new Label("出院日期"), 1, 7);
        pane.add(new Label("医院等级"), 1, 8);
        pane.add(new Label("出院原因"), 1, 9);
        pane.add(new Label("疾病编码"), 1, 10);
        pane.add(new Label("疾病名称"), 1, 11);
        TextField code = new TextField();
        TextField personid = new TextField();
        ChoiceBox<String> persontype = new ChoiceBox<>();
        persontype.getItems().addAll("在职职工", "退休人员", "享受低保在职人员", "享受低保退休人员");
        TextField inscode = new TextField();
        TextField insname = new TextField();
        DatePicker inhospital = new DatePicker();
        DatePicker outhospital = new DatePicker();
        ChoiceBox<String> hospitalLevel = new ChoiceBox<>();
        hospitalLevel.getItems().addAll("全部医院", "一级医院", "二级医院", "三级医院", "社区医院");
        TextField reason = new TextField();
        TextField discode = new TextField();
        TextField disname = new TextField();
        pane.add(code, 2, 1);
        pane.add(personid, 2, 2);
        pane.add(persontype, 2, 3);
        pane.add(inhospital, 2, 6);
        pane.add(outhospital, 2, 7);
        pane.add(inscode, 2, 4);
        pane.add(insname, 2, 5);
        pane.add(hospitalLevel, 2, 8);
        pane.add(reason, 2, 9);
        pane.add(discode, 2, 10);
        pane.add(disname, 2, 11);
        Button bt = new Button("提交");
        pane.add(bt, 2, 13);
        bt.setOnAction(e -> {
            if (code.getText() == null || code.getText().length() == 0) {
                AlertDialog.display("错误", "编号不能为空!");
                return;
            }
            PersonType s = null;
            String ty = persontype.getValue();
            if (ty == null) ;
            else if (ty.equals("在职职工")) s = PersonType.Worked;
            else if (ty.equals("退休人员")) s = PersonType.Retired;
            else if (ty.equals("享受低保在职人员")) s = PersonType.LowWorked;
            else s = PersonType.LowRetired;
            Date in = null, out = null;
            if (inhospital.getValue() != null)
                in = Date.from(inhospital.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
            if (outhospital.getValue() != null)
                out = Date.from(outhospital.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
            Patient patient = new Patient(code.getText(), personid.getText(), s, inscode.getText(),
                    insname.getText(), in, out, StringToHospitalLevel(hospitalLevel.getValue()), reason.getText(), discode.getText(), disname.getText());
            main.getServerManage().add(main.getLoginId(), ObjName.Patient, patient);
            AlertDialog.display("成功", "增加成功!");
            main.goBack();
        });
        pane.setStyle("-fx-padding: 20;-fx-hgap: 13;-fx-vgap:12;-fx-alignment: center;-fx-background-color: rgba(255,255,255,0.6);-fx-font-size: 20");
        return pane;
    }

    private Pane patientDisplay(Patient pa) {
        GridPane pane = new GridPane();
        pane.add(new Label("住院号"), 1, 1);
        pane.add(new Label("就诊人员ID"), 1, 2);
        pane.add(new Label("医疗人员类别类别"), 1, 3);
        pane.add(new Label("定点医疗机构编号"), 1, 4);
        pane.add(new Label("定点医疗机构名称"), 1, 5);
        pane.add(new Label("入院日期"), 1, 6);
        pane.add(new Label("出院日期"), 1, 7);
        pane.add(new Label("医院等级"), 1, 8);
        pane.add(new Label("出院原因"), 1, 9);
        pane.add(new Label("疾病编码"), 1, 10);
        pane.add(new Label("疾病名称"), 1, 11);
        TextField code = new TextField();
        code.setText(pa.getCode());
        TextField personid = new TextField();
        personid.setText(pa.getPersonId());
        ChoiceBox<String> persontype = new ChoiceBox<>();
        persontype.getItems().addAll("在职职工", "退休人员", "享受低保在职人员", "享受低保退休人员");
        persontype.setValue(PersonTypeToString(pa.getPersonType()));
        TextField inscode = new TextField();
        inscode.setText(pa.getInstitutionId());
        TextField insname = new TextField();
        insname.setText(pa.getInstitutionName());
        DatePicker inhospital = new DatePicker();
        if (pa.getInDate() != null)
            inhospital.setValue(pa.getInDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
        DatePicker outhospital = new DatePicker();
        if (pa.getOutDate() != null)
            outhospital.setValue(pa.getOutDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
        ChoiceBox<String> hospitalLevel = new ChoiceBox<>();
        hospitalLevel.getItems().addAll("全部医院", "一级医院", "二级医院", "三级医院", "社区医院");
        hospitalLevel.setValue(HospitalLevelToString(pa.getHospitalLevel()));
        TextField reason = new TextField();
        reason.setText(pa.getReason());
        TextField discode = new TextField();
        discode.setText(pa.getDiseaseId());
        TextField disname = new TextField();
        disname.setText(pa.getDiseaseName());
        pane.add(code, 2, 1);
        pane.add(personid, 2, 2);
        pane.add(persontype, 2, 3);
        pane.add(inhospital, 2, 6);
        pane.add(outhospital, 2, 7);
        pane.add(inscode, 2, 4);
        pane.add(insname, 2, 5);
        pane.add(hospitalLevel, 2, 8);
        pane.add(reason, 2, 9);
        pane.add(discode, 2, 10);
        pane.add(disname, 2, 11);
        Button btReturn = new Button("返回");
        btReturn.setOnAction(e -> main.goBack());
        pane.add(btReturn, 5, 12);
        Button btDel = new Button("删除");
        btDel.setStyle("-fx-background-color: red");
        pane.add(btDel, 3, 12);
        Button btSet = new Button("确认修改");
        pane.add(btSet, 2, 12);
        btDel.setOnAction(e -> {
            ConfirmDialog.display("警告", "删除之后将无法找回,确认删除吗?");
            if (ConfirmDialog.buttonSelected.equals(ConfirmDialog.Response.YES)) {
                main.getServerManage().delete(main.getLoginId(), ObjName.Patient, pa.getCode());
                AlertDialog.display("成功", "删除成功!");
                main.goBack();
            }
        });
        btSet.setOnAction(e -> {
            PersonType s = null;
            String ty = persontype.getValue();
            if (ty == null) ;
            else if (ty.equals("在职职工")) s = PersonType.Worked;
            else if (ty.equals("退休人员")) s = PersonType.Retired;
            else if (ty.equals("享受低保在职人员")) s = PersonType.LowWorked;
            else s = PersonType.LowRetired;
            Date in = null, out = null;
            if (inhospital.getValue() != null)
                in = Date.from(inhospital.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
            if (outhospital.getValue() != null)
                out = Date.from(outhospital.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
            Patient patient = new Patient(code.getText(), personid.getText(), s, inscode.getText(),
                    insname.getText(), in, out, StringToHospitalLevel(hospitalLevel.getValue()), reason.getText(), discode.getText(), disname.getText());
            if (main.getServerManage().set(main.getLoginId(), ObjName.Patient, pa.getCode(), patient)) {
                AlertDialog.display("成功", "修改成功!");
            } else AlertDialog.display("失败", "修改失败!");
        });
        pane.setStyle("-fx-padding: 20;-fx-hgap: 13;-fx-vgap:10;-fx-alignment: center;-fx-font-size:20px;-fx-background-color:rgba(255,255,255,0.6)");
        return pane;
    }

    private Pane patientAll() {
        BorderPane borderPane = new BorderPane();
        Label status = new Label("状态: 加载完成!");
        status.setStyle("-fx-font-size: 14px;-fx-background-color: rgba(255,255,255,0.7)");
        borderPane.setBottom(status);
        GridPane pane = new GridPane();
        pane.add(new Label("住院号"), 1, 1);
        pane.add(new Label("就诊人员ID"), 2, 1);
        pane.add(new Label("医疗人员类别"), 3, 1);
        pane.add(new Label("定点医疗机构名称"), 4, 1);
        pane.add(new Label("医院等级"), 5, 1);
        pane.add(new Label("疾病名称"), 6, 1);
        Iterator<InfoObject> it = main.getServerManage().getAll(ObjName.Patient);
        int i = 2;
        while (it.hasNext()) {
            Patient pa = ((Patient) it.next());
            Label l1 = new Label(pa.getCode());
            Label l2 = new Label(pa.getPersonId());
            Label l3 = new Label(PersonTypeToString(pa.getPersonType()));
            Label l4 = new Label(pa.getInstitutionName());
            Label l5 = new Label(HospitalLevelToString(pa.getHospitalLevel()));
            Label l6 = new Label(pa.getDiseaseName());
            Button btdel = new Button("删除");
            Button btset = new Button("修改");
            btdel.setStyle("-fx-background-color: red");
            btset.setOnAction(e -> main.push(patientDisplay(pa)));
            btdel.setOnAction(e -> {
                ConfirmDialog.display("警告", "删除之后将不可恢复,确认要删除吗?");
                if (ConfirmDialog.buttonSelected.equals(ConfirmDialog.Response.YES)) {
                    main.getServerManage().delete(main.getLoginId(), ObjName.Patient, pa.getCode());
                    btdel.setDisable(true);
                    btset.setDisable(true);
                    status.setText("状态: 删除" + pa.getCode() + "成功!");
                }
            });
            pane.add(l1, 1, i);
            pane.add(l2, 2, i);
            pane.add(l3, 3, i);
            pane.add(l4, 4, i);
            pane.add(l5, 5, i);
            pane.add(l6, 6, i);
            pane.add(btset, 7, i);
            pane.add(btdel, 8, i);
            ++i;
        }
        pane.setGridLinesVisible(true);
        Button btf = new Button("刷新");
        btf.setOnAction(e -> {
            main.goBack();
            main.push(patientAll());
        });
        borderPane.setTop(btf);
        pane.setAlignment(Pos.TOP_CENTER);
        pane.setStyle("-fx-font-size:19px");
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setFitToWidth(true);
        scrollPane.setContent(pane);
        borderPane.setCenter(scrollPane);
        return borderPane;
    }

    private Pane baoxiao() {
        main.setLeftBarActive(0);
        VBox vBox = new VBox();
        Label label = new Label("请输入要报销的就诊信息编号:");
        TextField textField = new TextField();
        Button bt = new Button("预结算");
        HBox hBox = new HBox();
        hBox.getChildren().addAll(label, textField);
        vBox.getChildren().addAll(hBox, bt);
        bt.setOnAction(e->{
            if(textField.getText()==null||textField.getText().length()==0){
                AlertDialog.display("错误","查询的数据不能为空!");
                return;
            }
            InfoObject obj=main.getServerManage().find(ObjName.Patient,textField.getText());
            if(obj==null){
                AlertDialog.display("失败","没有查到!");
            }
            else if(obj instanceof Patient)
            {
                Patient pa=(((Patient) obj));
                if(pa.isDone()){
                    AlertDialog.display("失败","此结算信息已经结算过了!");
                    return;
                }
                main.push(yujiesuan(pa));
            }
        });
        vBox.setAlignment(Pos.CENTER);
        hBox.setAlignment(Pos.CENTER);
        vBox.setStyle("-fx-background-color: rgba(255,255,255,0.6)");
        return vBox;
    }


    private Pane yujiesuan(Patient pa) {
        String id = pa.getCode();
        Prescription pre;
        BillInfomation bill = new BillInfomation(id, pa.getPersonId());
        CalculationParameter parameter = main.getServerManage().getParmater();
        bill.setLowbound(parameter.getLowerBound());
        double kebao=0;// 可报销的总金额

        Iterator<InfoObject> it = main.getServerManage().getAll(ObjName.Prescription);
        while (it.hasNext()) {
            pre = ((Prescription) it.next());
            //如果不等于, 跳过
            if(!id.equals(pre.getId()))continue;
            switch (pre.getContentType()) {
                case ("药品"): {
                    Medicine me = ((Medicine) main.getServerManage().find(ObjName.Medicine, pre.getContentId()));
                    bill.addTotal(pre.getPrice());
                    if (me.isNeedCheck()) {//审批,不可报
                        if (me.getPayLevel().equals(Level.Two)) bill.addYizi(pre.getPrice());
                        break;
                    }
                    if (me.getPayLevel().equals(Level.Two)) {//乙类
                        if (pa.getHospitalLevel().equals(Level.Three)) { //三级医院开的
                            if(me.getHospitalLevel().equals(Level.Three)) {
                                // 报销
                                double bao = pre.getPrice();
                                if (pre.getUnitPrice() > me.getUpperBound()) {
                                    bao = me.getUpperBound() * pre.getNum();
                                    bill.addYizi(pre.getPrice() - bao);
                                }
                                bao = bao/2;
                                bill.addYizi(bao);
                                kebao += bao;
                            }else bill.addYizi(pre.getPrice());
                        }else if (pa.getHospitalLevel().equals(Level.Two)) {//二级医院开的
                            if(me.getHospitalLevel().equals(Level.Two) || me.getHospitalLevel().equals(Level.Three)) {
                                double bao = pre.getPrice();
                                if (pre.getUnitPrice() > me.getUpperBound()) {
                                    bao = me.getUpperBound() * pre.getNum();
                                    bill.addYizi(pre.getPrice() - bao);
                                }
                                bill.addYizi(bao/2);
                                kebao += bao/2;
                            } else bill.addYizi(pre.getPrice());
                        } else {//一级或者全部医院开的药
                            double bao = pre.getPrice();
                            if (pre.getUnitPrice() > me.getUpperBound()) {
                                bao = me.getUpperBound() * pre.getNum();
                                bill.addYizi(pre.getPrice() - bao);
                            }
                            bao /= 2;
                            bill.addYizi(bao);
                            kebao+=bao;
                        }
                    } else if (me.getPayLevel().equals(Level.One)) {//甲类
                        if (pa.getHospitalLevel().equals(Level.Three)) { //三级医院开的
                            if (me.getHospitalLevel().equals(Level.Three)) {
                                if (pre.getUnitPrice() > me.getUpperBound()) {
                                    kebao+=me.getUpperBound()*pre.getNum();
                                }
                                else kebao += pre.getPrice();
                            }
                        } else if (pa.getHospitalLevel().equals(Level.Two)) {//二级医院开的
                            if (me.getHospitalLevel().equals(Level.Two) || me.getHospitalLevel().equals(Level.Three)){
                                if (pre.getUnitPrice() > me.getUpperBound()) {
                                    kebao+=me.getUpperBound()*pre.getNum();
                                }
                                else kebao += pre.getPrice();
                            }
                        } else {//一级或者全部医院开的药
                            if (pre.getUnitPrice() > me.getUpperBound()) {
                                kebao+=me.getUpperBound()*pre.getNum();
                            }
                            else kebao += pre.getPrice();
                        }
                    }
                    break;
                }
                case("诊疗项目"):{
                    Treatment treatment= ((Treatment) main.getServerManage().find(ObjName.Treatment, pre.getId()));
                    if(treatment.isNeedCheck())break;//不可报
                    if(pa.getHospitalLevel().equals(Level.Three)&&(treatment.getHospitalLevel().equals(Level.Two)
                            ||treatment.getHospitalLevel().equals(Level.One)))break;
                    if(pa.getHospitalLevel().equals(Level.Two)&&treatment.getHospitalLevel().equals(Level.One))break;
                    kebao+=pre.getPrice();
                }
                case("服务设施"):{//服务设施类全额报销
                    kebao+=pre.getPrice();
                }
                default:break;
            }
        }// end while   循环把可报销的金额加起来,算出报销的;

        if (kebao > parameter.getLevel2()) {
            double xuyao=kebao-parameter.getLevel1();
            kebao=parameter.getLevel1();
            bill.addBao(xuyao * parameter.getRate3());
            bill.addFenzi(xuyao*(1-parameter.getRate3()));
        }
        if (kebao > parameter.getLevel1()) {
            double xuyao = kebao - parameter.getLevel1();
            kebao = parameter.getLevel1();
            bill.addBao(xuyao * parameter.getRate2());
            bill.addFenzi(xuyao*(1.0-parameter.getRate2()));
        }
        if (kebao > parameter.getLowerBound()) {
            double xuyao = kebao - parameter.getLowerBound();
            kebao = parameter.getLowerBound();
            bill.addBao(xuyao * parameter.getRate1());
            bill.addBao(xuyao *(1.0-parameter.getRate1()));
        }
        Person person= ((Person) main.getServerManage().find(ObjName.Person, pa.getPersonId()));
        double leiji=person.getBaoXiao();
        double fengding=parameter.getUpperBound(person.getType());
        if(leiji+bill.getBao()>fengding)bill.setBao(fengding-leiji);
        bill.addZi(bill.getTotal()-bill.getBao()+kebao);

        GridPane pane=new GridPane();
        pane.add(new Label("姓名"),1,1);pane.add(new Label(person.getName()),2,1);
        pane.add(new Label("个人编号"),3,1);pane.add(new Label(person.getCode()),4,1);
        pane.add(new Label("就诊医院"),1,2);pane.add(new Label(pa.getInstitutionName()),2,2);
        pane.add(new Label("疾病名称"),3,2);pane.add(new Label(pa.getDiseaseName()),4,2);
        pane.add(new Label("结算明细"),1,3);
        pane.add(new Label("起付标准"),1,4);pane.add(new Label(String.valueOf(bill.getLowbound())),2,4);
        pane.add(new Label("报销金额"),1,5);pane.add(new Label(String.valueOf(bill.getBao())),2,5);
        pane.add(new Label("乙类项目自费"),1,6);pane.add(new Label(String.valueOf(bill.getYizi())),2,6);
        pane.add(new Label("特检特治自费"),1,7);pane.add(new Label(String.valueOf(bill.getTezi())),2,7);
        pane.add(new Label("分段计算中自费金额"),1,8);pane.add(new Label(String.valueOf(bill.getFenzi())),2,8);
        pane.add(new Label("自费金额"),1,9);pane.add(new Label(String.valueOf(bill.getZi())),2,9);
        pane.add(new Label("费用总额"),1,10);pane.add(new Label(String.valueOf(bill.getTotal())),2,10);

        Button btReturn=new Button("返回");
        btReturn.setOnAction(e->main.goBack());
        pane.add(btReturn,4,12);
        Button btjie=new Button("结算");
        btjie.setOnAction(e-> {
            main.push(jiesuan(bill, person));
            person.setLastHospitalDate(pa.getOutDate());
            pa.setDone(true);
        });
        pane.add(btjie,2,12);
        pane.setStyle("-fx-font-size: 23px;-fx-alignment:center;-fx-hgap:9;-fx-vgap:6;-fx-background-color:rgba(255,255,255,0.5)");
        return pane;
    }

    private Pane jiesuan(BillInfomation bill,Person per){
        VBox vBox=new VBox();
        vBox.getChildren().add(new Label("报销成功!"));
        Button btreturn=new Button("返回");
        vBox.getChildren().add(btreturn);
        per.setBaoXiao(bill.getBao()+per.getBaoXiao());
        per.setTotal(per.getTotal()+bill.getTotal());
        per.setZiFei(per.getZiFei()+bill.getZi());
        vBox.setSpacing(20);
        Button bt=new Button("打印单据");
        bt.setDisable(true);
        vBox.getChildren().add(bt);
        vBox.setAlignment(Pos.CENTER);
        main.getServerManage().add(main.getLoginId(),ObjName.BillInformation,bill);
        btreturn.setOnAction(e->{
            main.goBack();
            main.goBack();
        });
        return vBox;
    }

    private Pane sickPerson(){
        main.setLeftBarActive(3);
        VBox vBox=new VBox();
        HBox h1=new HBox();
        HBox h2=new HBox();
        TextField id=new TextField();
        Button btid=new Button("查询");
        h1.getChildren().addAll(new Label("通过人员编号查询"),id,btid);
        TextField name=new TextField("");
        Button btname=new Button("查询");
        h2.getChildren().addAll(new Label("通过人员编号查询"),name,btname);
        GridPane pane=new GridPane();
        pane.add(new Label("编号"),1,1);
        pane.add(new Label("名字"),2,1);
        pane.add(new Label("证件类型"),3,1);
        pane.add(new Label("证件编号"),4,1);
        pane.add(new Label("社保卡号"),5,1);
        Button btclear=new Button("清空");
        h1.setAlignment(Pos.BOTTOM_CENTER);
        h2.setAlignment(Pos.BOTTOM_CENTER);
        pane.setAlignment(Pos.CENTER);
        pane.setStyle("-fx-vgap: 13;-fx-hgap:10;-fx-padding:40,60;-fx-font-size:20;-fx-background-color:rgba(255,255,255,0.6)");
        vBox.setAlignment(Pos.CENTER);
        vBox.setSpacing(20);
        HBox h3=new HBox();h3.getChildren().add(btclear);h3.setAlignment(Pos.BOTTOM_RIGHT);
        h3.setStyle("-fx-font-size:20;-fx-padding:40,40;");
        vBox.getChildren().addAll(h1,h2,pane,h3);
        btclear.setOnAction(e->pane.getChildren().remove(5,pane.getChildren().size()));
        btid.setOnAction(e->{
            pane.getChildren().remove(5,pane.getChildren().size());
            if(id.getText()==null||id.getText().length()==0){
                AlertDialog.display("错误","查询内容不能为空!");
                return;
            }
            InfoObject obj=main.getServerManage().find(ObjName.Person,id.getText());
            if(obj==null){
                AlertDialog.display("失败","查找失败,没有找到!");
            }
            else if(obj instanceof Person)personfindadd(2, ((Person) obj),pane);
        });
        btname.setOnAction(e->{
            pane.getChildren().remove(5,pane.getChildren().size());
            if(name.getText()==null||name.getText().length()==0){
                AlertDialog.display("错误","查询内容不能为空!");
                return;
            }
            Person per;
            Iterator<InfoObject> it=main.getServerManage().getAll(ObjName.Person);
            int i=2;boolean finff=true;
            while(it.hasNext()) {
                per= ((Person) it.next());
                if (per.getName().equals(name.getText())){ personfindadd(i,per,pane);finff=false;}
                ++i;
            }
            if (finff) {
                AlertDialog.display("失败", "查找失败,没有找到!");
            }
        });
        return vBox;
    }
    private void personfindadd(int i,Person per,GridPane pane){
        pane.add(new Label(per.getCode()), 1, i);
        pane.add(new Label(per.getName()), 2, i);
        pane.add(new Label(per.getIdType()), 3, i);
        pane.add(new Label(per.getIdNum()), 4, i);
        pane.add(new Label(per.getProtectId()), 5, i);
        Button bt = new Button("查看详情");
        bt.setOnAction(ee->main.push(sickpersonDisplay(per)));
        pane.add(bt, 6, i);
    }

    private Pane sickpersonDisplay(Person per){
        GridPane pane=new GridPane();
        pane.add(new Label("人员编号:"),1,1);
        pane.add(new Label(per.getCode()),2,1);
        pane.add(new Label("人员姓名:"),1,2);
        pane.add(new Label(per.getName()),2,2);
        pane.add(new Label("证件号:"),1,3);
        pane.add(new Label(per.getIdNum()),2,3);
        pane.add(new Label("住院次数:"),1,4);
        pane.add(new Label(String.valueOf(per.getHospitalNum())),2,4);
        pane.add(new Label("上次出院时间"),1,5);
        String s="未知";
        if(per.getLastHospitalDate()!=null)s=per.getLastHospitalDate().toString();
        pane.add(new Label(s),2,5);
        pane.add(new Label("上次出院诊断"),1,6);
        pane.add(new Label(per.getLastHospitalStatus()),2,6);
        pane.add(new Label("本年度中心报销累计"),1,7);
        pane.add(new Label(String.valueOf(per.getBaoXiao())),2,7);
        pane.add(new Label("本年度个人自费累计"),1,8);
        pane.add(new Label(String.valueOf(per.getZiFei())),2,8);
        pane.add(new Label("本年度医疗费用累计"),1,9);
        pane.add(new Label(String.valueOf(per.getTotal())),2,9);
        Button btcon=new Button("确认");
        pane.add(btcon,2,10);
        pane.setAlignment(Pos.CENTER);
        pane.setStyle("-fx-background-color:rgba(255,255,255,0.5);-fx-hgap:14;-fx-vgap:9;-fx-font-size:20");
        btcon.setOnAction(e->main.goBack());
        return pane;
    }


    private Pane quXiao()
    {
        main.setLeftBarActive(4);
        VBox vBox=new VBox();
        HBox h1=new HBox();
        HBox h2=new HBox();
        ChoiceBox<String> choiceBox=new ChoiceBox<>();
        choiceBox.getItems().addAll("人员ID","就诊信息ID");
        choiceBox.setValue("就诊信息ID");
        h1.getChildren().addAll(new Label("请选择查询条件"),choiceBox);
        TextField textField=new TextField();
        Button btfind=new Button("查询");
        h2.getChildren().addAll(textField,btfind);
        h2.setStyle("-fx-padding: 20,20");
        h1.setStyle("-fx-padding: 50,40;-fx-font-size:19");
        GridPane gridPane=new GridPane();
        gridPane.add(new Label("报销时间"),1,1);
        gridPane.add(new Label("总金额"),2,1);
        gridPane.add(new Label("报销金额"),3,1);
        gridPane.add(new Label("自费金额"),4,1);

        btfind.setOnAction(e->{
            if(textField.getText()==null||textField.getText().length()==0){
                AlertDialog.display("错误","查询内容不能为空!");
                return;
            }
            if(choiceBox.getValue().equals("人员ID")) {
                Iterator<InfoObject> it = main.getServerManage().getAll(ObjName.BillInformation);
                int i = 2;
                BillInfomation bill;
                boolean faxian = true;
                while (it.hasNext()) {
                    bill = ((BillInfomation) it.next());
                    if (!bill.getPersonid().equals(textField.getText())) continue;
                    Patient pa = ((Patient) main.getServerManage().find(ObjName.Patient, bill.getCode()));
                    if (pa.isDone()) continue;
                    addFindBill(bill, gridPane, i);
                    faxian = false;
                    ++i;
                }
                if (faxian) AlertDialog.display("失败", "查询失败,没有搜索到信息!");
            }else{
                InfoObject obj=main.getServerManage().find(ObjName.BillInformation,textField.getText());
                if(obj==null){
                    AlertDialog.display("失败","没有找到!");
                }else if(obj instanceof BillInfomation)addFindBill(((BillInfomation) obj),gridPane,2);
            }
        });
        HBox h3=new HBox();
        Button btclear=new Button("清空");
        h3.setAlignment(Pos.BOTTOM_RIGHT);
        h3.setStyle("-fx-padding: 70,40");
        btclear.setOnAction(e->gridPane.getChildren().remove(7,gridPane.getChildren().size()));
        h3.getChildren().add(btclear);
        gridPane.setStyle("-fx-background-color:rgba(255,255,255,0.6);-fx-vgap:10;-fx-hgap:15;-fx-font-size:20");
        vBox.getChildren().addAll(h1,h2,gridPane,h3);
        h1.setAlignment(Pos.CENTER);
        h2.setAlignment(Pos.CENTER);
        gridPane.setAlignment(Pos.CENTER);
        vBox.setAlignment(Pos.TOP_CENTER);
        return vBox;
    }

    private void addFindBill(BillInfomation bill,GridPane pane,int i){
        pane.add(new Label(bill.getTime().toString()),1,i);
        pane.add(new Label(String.valueOf(bill.getTotal())),2,i);
        pane.add(new Label(String.valueOf(bill.getBao())),3,i);
        pane.add(new Label(String.valueOf(bill.getZi())),4,i);
        Button bt=new Button("取消报销");
        pane.add(bt,5,i);
        bt.setOnAction(e->{
            ConfirmDialog.display("警告!","确认取消报销吗?");
            if(ConfirmDialog.buttonSelected.equals(ConfirmDialog.Response.YES)){
                bt.setDisable(true);
                Person per= ((Person) main.getServerManage().find(ObjName.Person, bill.getPersonid()));
                per.setZiFei(per.getZiFei()-bill.getZi());
                per.setTotal(per.getTotal()-bill.getTotal());
                per.setBaoXiao(per.getBaoXiao()-bill.getBao());
                Patient pa= ((Patient) main.getServerManage().find(ObjName.Patient, bill.getCode()));
                pa.setDone(false);
                BillInfomation newBill=new BillInfomation(pa.getCode(),per.getCode());
                newBill.setBao(-bill.getBao());
                newBill.setLowbound(bill.getBao());
                newBill.addZi(-bill.getZi());
                newBill.addYizi(-bill.getYizi());
                newBill.addFenzi(-bill.getFenzi());
                newBill.addTezi(-bill.getTezi());
                newBill.addTotal(-bill.getTotal());
                main.getServerManage().add(main.getLoginId(),ObjName.BillInformation,newBill);
            }
        });
    }

    public static Level StringToHospitalLevel(String s){
        if(s==null)return null;
        else if(s.equals("全部医院"))return Level.Zero;
        else if(s.equals("一级医院"))return Level.One;
        else if(s.equals("二级医院"))return Level.Two;
        else if(s.equals("三级医院"))return Level.Three;
        else if(s.equals("社区医院"))return Level.Four;
        else return null;
    }

    public static String HospitalLevelToString(Level l){
        if(l==null)return null;
        else if(l.equals(Level.Zero))return "全部医院";
        else if(l.equals(Level.One))return "一级医院";
        else if(l.equals(Level.Two))return "二级医院";
        else if(l.equals(Level.Three))return "三级医院";
        else if(l.equals(Level.Four))return "社区医院";
        else return null;
    }

    public static String PersonTypeToString(PersonType py){
        if(py==null)return null;
        else if(py.equals(PersonType.Worked))return "在职职工";
        else if(py.equals(PersonType.Retired))return "退休人员";
        else if(py.equals(PersonType.LowRetired))return "享受低保在职人员";
        return "享受低保退休人员";
    }

}
