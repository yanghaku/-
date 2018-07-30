package client;


import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import server.*;
import server.RegistedObj.ObjName;
import server.Social.*;
import server.baoxiao.Patient;
import server.baoxiao.Prescription;
import java.util.Iterator;

/**
 * 综合查询模块
 *
 */
public class Query extends VBox {

    private Main main;

    public Query(Main main){
        this.main=main;
        this.setAlignment(Pos.CENTER);
        this.setSpacing(20);
        HBox h1=new HBox();
        h1.setSpacing(10);
        h1.setAlignment(Pos.CENTER);
        ChoiceBox<String> choice=new ChoiceBox<>();
        choice.setValue("ID");choice.getItems().addAll("ID","姓名");
        h1.getChildren().addAll(new Label("请选择查询人员的方式"),choice);
        HBox h2=new HBox();
        h2.setAlignment(Pos.CENTER);
        h2.setSpacing(10);
        TextField textField=new TextField();
        Button btfind=new Button("查询");
        h2.getChildren().addAll(textField,btfind);
        GridPane pane=new GridPane();
        pane.setStyle("-fx-background-color:rgba(255,255,255,0.6);-fx-hgap:10;-fx-vgap:10;-fx-padding:50,50;-fx-alignment:center");
        HBox h3=new HBox();
        Button btclear=new Button("清空");
        btclear.setOnAction(e->pane.getChildren().remove(5,pane.getChildren().size()));
        h3.setAlignment(Pos.BOTTOM_RIGHT);
        h3.setStyle("-fx-font-size: 20;-fx-padding: 60,60");
        pane.add(new Label("编号"),1,1);
        pane.add(new Label("名字"),2,1);
        pane.add(new Label("证件类型"),3,1);
        pane.add(new Label("证件编号"),4,1);
        pane.add(new Label("社保卡号"),5,1);
        btfind.setOnAction(e->{
            if(textField.getText()==null||textField.getText().length()==0){
                AlertDialog.display("错误","查询内容不能为空!");
                return;
            }
            pane.getChildren().remove(5,pane.getChildren().size());
            if(choice.getValue().equals("ID")) {
                InfoObject obj = main.getServerManage().find(RegistedObj.ObjName.Person,textField.getText());
                if (obj == null) {
                    AlertDialog.display("失败", "查找失败,没有找到!");
                } else if (obj instanceof Person) personfindadd(2, ((Person) obj), pane);
            }
            else {
                if (textField.getText() == null || textField.getText().length() == 0) {
                    AlertDialog.display("错误", "查询内容不能为空!");
                    return;
                }
                Person per;
                Iterator<InfoObject> it = main.getServerManage().getAll(RegistedObj.ObjName.Person);
                int i = 2;
                boolean finff = true;
                while (it.hasNext()) {
                    per = ((Person) it.next());
                    if (per.getName().equals(textField.getText())) {
                        personfindadd(i, per, pane);
                        finff = false;
                    }
                    ++i;
                }
                if (finff) {
                    AlertDialog.display("失败", "查找失败,没有找到!");
                }
            }
        });
        this.getChildren().addAll(h1,h2,pane,h3);
    }

    private void personfindadd(int i,Person per,GridPane pane){
        pane.add(new Label(per.getCode()), 1, i);
        pane.add(new Label(per.getName()), 2, i);
        pane.add(new Label(per.getIdType()), 3, i);
        pane.add(new Label(per.getIdNum()), 4, i);
        pane.add(new Label(per.getProtectId()), 5, i);
        Button bt = new Button("查看详情");
        bt.setOnAction(ee->main.push(query(per)));
        pane.add(bt, 6, i);
    }

    private Pane query(Person per){
        BorderPane borderPane=new BorderPane();
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
        pane.setStyle("-fx-background-color:rgba(255,255,255,0.5);-fx-hgap:14;-fx-vgap:15;-fx-font-size:19");
        btcon.setOnAction(e->main.goBack());
        borderPane.setLeft(pane);

        GridPane panePre = new GridPane();
        panePre.add(new Label("编号"), 1, 1);
        panePre.add(new Label("住院号"), 2, 1);
        panePre.add(new Label("项目编号"), 3, 1);
        panePre.add(new Label("项目类型"), 4, 1);
        panePre.add(new Label("单价"), 5, 1);
        panePre.add(new Label("数量"), 6, 1);
        panePre.add(new Label("价格"), 7, 1);
        Iterator<InfoObject> it = main.getServerManage().getAll(ObjName.Prescription);
        int i = 2;
        while (it.hasNext()) {
            Prescription pre = ((Prescription) it.next());
            if(!pre.getPersonId().equals(per.getCode()))continue;
            Label l1 = new Label(pre.getCode());
            Label l2 = new Label(pre.getId());
            Label l3 = new Label(pre.getContentId());
            Label l4 = new Label(pre.getContentType());
            Label l5 = new Label(String.valueOf(pre.getUnitPrice()));
            Label l6 = new Label(String.valueOf(pre.getNum()));
            Label l7 = new Label(String.valueOf(pre.getPrice()));
            Button btset = new Button("查看详情");
            btset.setOnAction(e -> main.push(DisPre(pre)));
            panePre.add(l1, 1, i);
            panePre.add(l2, 2, i);
            panePre.add(l3, 3, i);
            panePre.add(l4, 4, i);
            panePre.add(l5, 5, i);
            panePre.add(l6, 6, i);
            panePre.add(l7, 7, i);
            panePre.add(btset, 8, i);
            ++i;
        }
        panePre.setGridLinesVisible(true);
        panePre.setAlignment(Pos.TOP_CENTER);
        ScrollPane scrollPanePre = new ScrollPane();
        scrollPanePre.setFitToWidth(true);
        scrollPanePre.setContent(panePre);


        GridPane panePa = new GridPane();
        panePa.add(new Label("住院号"), 1, 1);
        panePa.add(new Label("就诊人员ID"), 2, 1);
        panePa.add(new Label("医疗人员类别"), 3, 1);
        panePa.add(new Label("定点医疗机构名称"), 4, 1);
        panePa.add(new Label("医院等级"), 5, 1);
        panePa.add(new Label("疾病名称"), 6, 1);
        it = main.getServerManage().getAll(ObjName.Patient);
        i = 2;
        while (it.hasNext()) {
            Patient pa = ((Patient) it.next());
            if(!pa.getPersonId().equals(per.getCode()))continue;
            Label l1 = new Label(pa.getCode());
            Label l2 = new Label(pa.getPersonId());
            Label l3 = new Label(BaoxiaoPane.PersonTypeToString(pa.getPersonType()));
            Label l4 = new Label(pa.getInstitutionName());
            Label l5 = new Label(BaoxiaoPane.HospitalLevelToString(pa.getHospitalLevel()));
            Label l6 = new Label(pa.getDiseaseName());
            Button btset = new Button("查看详情");
            btset.setOnAction(e -> main.push(DisPa(pa)));
            panePa.add(l1, 1, i);
            panePa.add(l2, 2, i);
            panePa.add(l3, 3, i);
            panePa.add(l4, 4, i);
            panePa.add(l5, 5, i);
            panePa.add(l6, 6, i);
            panePa.add(btset, 7, i);
            ++i;
        }
        panePa.setGridLinesVisible(true);
        panePa.setStyle("-fx-font-size:19px");
        ScrollPane scrollPanePa = new ScrollPane();
        scrollPanePa.setFitToWidth(true);
        scrollPanePa.setContent(panePa);

        VBox vb=new VBox();
        VBox v1=new VBox();
        v1.getChildren().addAll(new Label("人员所有处方列表"),scrollPanePre);
        v1.setAlignment(Pos.TOP_CENTER);
        vb.setSpacing(50);
        VBox v2=new VBox();
        v2.getChildren().addAll(new Label("人员所有诊疗信息列表"),scrollPanePa);
        v2.setAlignment(Pos.BOTTOM_CENTER);
        vb.getChildren().addAll(v1,v2);
        borderPane.setCenter(vb);
        return borderPane;
    }
    public Pane DisPre(Prescription pre){
        GridPane pane = new GridPane();
        pane.add(new Label("编号 "), 1, 1);
        pane.add(new Label("住院号"), 1, 2);
        pane.add(new Label("项目编号"), 1, 3);
        pane.add(new Label("项目类型"), 1, 4);
        pane.add(new Label("单价"), 1, 5);
        pane.add(new Label("数量"), 1, 6);
        pane.add(new Label("价格"),1,7);
        pane.add(new Label(pre.getCode()),2,1);
        pane.add(new Label(pre.getId()),2,2);
        pane.add(new Label(pre.getContentId()),2,3);
        pane.add(new Label(pre.getContentType()),2,4);
        pane.add(new Label(String.valueOf(pre.getUnitPrice())),2,5);
        pane.add(new Label(String.valueOf(pre.getNum())),2,6);
        pane.add(new Label(String.valueOf(pre.getPrice())),2,7);
        Button btcom=new Button("确认");
        pane.add(btcom,2,9);
        btcom.setOnAction(e->main.goBack());
        pane.setStyle("-fx-padding: 20;-fx-hgap: 13;-fx-alignment: center;-fx-background-color:rgba(255,255,255,0.6)");
        return pane;
    }
    private Pane DisPa(Patient pa){
        GridPane pane = new GridPane();
        pane.add(new Label("住院号"), 1, 1);
        pane.add(new Label(pa.getCode()),2,1);
        pane.add(new Label("就诊人员ID"), 1, 2);
        pane.add(new Label(pa.getPersonId()),2,2);
        pane.add(new Label("医疗人员类别类别"), 1, 3);
        pane.add(new Label(BaoxiaoPane.PersonTypeToString(pa.getPersonType())),2,3);
        pane.add(new Label("定点医疗机构编号"), 1, 4);
        pane.add(new Label(pa.getInstitutionId()),2,4);
        pane.add(new Label("定点医疗机构名称"), 1, 5);
        pane.add(new Label(pa.getInstitutionName()),2,5);
        pane.add(new Label("入院日期"), 1, 6);
        String s="未知";
        if(pa.getInDate()!=null)s=pa.getInDate().toString();
        pane.add(new Label(s),1,7);
        s="未知";
        if(pa.getOutDate()!=null)s=pa.getOutDate().toString();
        pane.add(new Label("出院日期"), 1, 7);
        pane.add(new Label(s),2,7);
        pane.add(new Label("医院等级"), 1, 8);
        pane.add(new Label(BaoxiaoPane.HospitalLevelToString(pa.getHospitalLevel())),2,8);
        pane.add(new Label("出院原因"), 1, 9);
        pane.add(new Label(pa.getReason()),2,9);
        pane.add(new Label("疾病编码"), 1, 10);
        pane.add(new Label(pa.getDiseaseId()),2,10);
        pane.add(new Label("疾病名称"), 1, 11);
        pane.add(new Label(pa.getDiseaseName()),2,11);
        Button bt=new Button("确认");
        pane.add(bt,2,12);
        bt.setOnAction(e->main.goBack());
        pane.setStyle("-fx-padding: 20;-fx-hgap: 13;-fx-vgap:12;-fx-alignment: center;-fx-background-color: rgba(255,255,255,0.6);-fx-font-size: 20");
        return pane;
    }
}
