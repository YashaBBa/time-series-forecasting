package com.example.client.controller;

import java.io.*;
import java.net.URL;
import java.sql.Date;
import java.util.Iterator;
import java.util.ResourceBundle;

import com.example.client.hendler.SessionHandler;
import com.example.client.math.MathService;
import com.example.client.math.impl.MathServiceImpl;
import com.example.client.model.Prognos;
import com.example.client.model.Result;
import com.example.client.model.enums.ControllerEnum;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.chart.*;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.usermodel.Cell;

import java.awt.Desktop;

public class PrognosController implements Initializable {

    public Button testFileButton;
    public Button exit;
    public Button historyButton;
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TableColumn<Prognos, Integer> value;

    @FXML
    private TableColumn<Prognos, Integer> date;

    @FXML
    private TableView<Prognos> tableObject;

    @FXML
    private Button prognosButton;

    @FXML
    private CheckBox sglaj;

    @FXML
    private Button sendButton;

    @FXML
    private TextField valueInput;

    @FXML
    private TextField dateInput;

    @FXML
    private RadioButton scuareMethod;

    @FXML
    private TextField stepCounter;


    @FXML
    private RadioButton linerTriger;

    @FXML
    private RadioButton pointTriger;

    @FXML
    private Button readFileButton;

    @FXML
    private TextField fileAdress;


    ObservableList<Prognos> list = FXCollections.observableArrayList(


    );
    ObservableList<Prognos> listMidl = FXCollections.observableArrayList();


    private Stage stage;
    ObservableList<Prognos> otklonPlus;
    ObservableList<Prognos> otklonMinus;

    ObservableList<Prognos> observableList;

    ObservableList<Prognos> mediana;

    @FXML
    void doPrognos(ActionEvent event) throws IOException, CloneNotSupportedException {
        stage = new Stage();

        //defining the axes
        final NumberAxis xAxis = new NumberAxis();
        final NumberAxis yAxis = new NumberAxis();
        xAxis.setLabel("Дата");
        //creating the chart
        // final ScatterChart<Number, Number> lineChart = new ScatterChart<Number, Number>(xAxis, yAxis);
        XYChart<Number, Number> lineChart;
        if (linerTriger.isSelected()) {
            lineChart = new LineChart<>(xAxis, yAxis);
        } else {
            lineChart = new ScatterChart<>(xAxis, yAxis);
        }



        //defining a series
        XYChart.Series series = new XYChart.Series();
        XYChart.Series mainSeries = new XYChart.Series();
        XYChart.Series seriesMinus = new XYChart.Series();
        XYChart.Series seriesPlus = new XYChart.Series();
        XYChart.Series madianaG = new XYChart.Series();

        mainSeries.setName("Все значения");
        series.setName("Значения прогнонизрования");
        seriesMinus.setName("Отрицательный отклон");
        seriesPlus.setName("Положительный отклон");
        madianaG.setName("Медиана");


        //   Scene scene = new Scene(lineChart, 800, 600);
        Scene scene = new Scene(new Group());
        lineChart.getData().addAll(series, mainSeries, seriesMinus, seriesPlus, madianaG);


        if (sglaj.isSelected()) {
            MathService mathService = new MathServiceImpl();
            ObservableList<Prognos> newList = mathService.doSglaj(list, 3);
            for (Prognos prognos : newList) {
                series.getData().add(new XYChart.Data(prognos.getDate(), prognos.getValue()));
            }

        }


        if (scuareMethod.isSelected()) {
            MathService mathService = new MathServiceImpl();


            int stepC = 0;
            if (!stepCounter.getText().isEmpty()) {
                observableList = mathService.doLessSquareMethod(list, Integer.parseInt(stepCounter.getText()));
            } else {
                observableList = mathService.doLessSquareMethod(list, 0);
            }
            otklonPlus = mathService.doOtclonPlus(list, observableList, 1);
            otklonMinus = mathService.doOtclonMinus(list, observableList, 1);

            for (Prognos prognos : observableList) {
                series.getData().add(new XYChart.Data(prognos.getDate(), prognos.getValue()));
            }
            for (Prognos prognos : list) {
                mainSeries.getData().add(new XYChart.Data(prognos.getDate(), prognos.getValue()));
            }
            for (Prognos prognos : otklonPlus) {
                seriesPlus.getData().add(new XYChart.Data(prognos.getDate(), prognos.getValue()));
            }
            for (Prognos prognos : otklonMinus) {
                seriesMinus.getData().add(new XYChart.Data(prognos.getDate(), prognos.getValue()));
            }
            mediana = mathService.findMediana(list);
            for (Prognos prognos : mediana) {
                madianaG.getData().add(new XYChart.Data(prognos.getDate(), prognos.getValue()));
            }


        }

        final VBox vbox = new VBox();
        final HBox hbox = new HBox();

        final Button add = new Button("Save as Excel");
        final Button remove = new Button("Save as Word");
        TextField fildeAdress = new TextField();

        hbox.setSpacing(10);
        hbox.getChildren().addAll(add, remove, fildeAdress);

        vbox.getChildren().addAll(lineChart, hbox);
        hbox.setPadding(new Insets(10, 10, 10, 50));

        ((Group) scene.getRoot()).getChildren().add(vbox);

        add.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                writeInExcel(fildeAdress.getText());
                Result result = new Result();
                result.setAnalizWayId(1);
                result.setTimestap(observableList.size());
                java.util.Date date2 = new java.util.Date();
                Date date1 = new Date(date2.getTime());
                result.setDate(date1);
                result.setResult(fildeAdress.getText() + ".xlsx");
                saveResult(result);

            }
        });
        remove.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                try {
                    Result result2 = new Result();
                    result2.setAnalizWayId(1);
                    result2.setTimestap(observableList.size());
                    result2.setResult(fildeAdress.getText() + ".doc");
                    java.util.Date date2 = new java.util.Date();
                    Date date1 = new Date(date2.getTime());
                    result2.setDate(date1);
                    writeInWord(fildeAdress.getText());
                    saveResult(result2);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        for (XYChart.Data<Number, Number> data : lineChart.getData().get(0).getData()) {
            data.getNode().addEventHandler(MouseEvent.ANY,
                    new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(MouseEvent e) {
                            lineChart.setTitle("Дата "+data.getXValue()+"\nОбъем продаж "+data.getYValue());


                        }
                    });
        }
        for (XYChart.Data<Number, Number> data : lineChart.getData().get(1).getData()) {
            data.getNode().addEventHandler(MouseEvent.ANY,
                    new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(MouseEvent e) {
                            lineChart.setTitle("Дата "+data.getXValue()+"\nОбъем продаж "+data.getYValue());


                        }
                    });
        }
        for (XYChart.Data<Number, Number> data : lineChart.getData().get(2).getData()) {
            data.getNode().addEventHandler(MouseEvent.ANY,
                    new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(MouseEvent e) {
                            lineChart.setTitle("Дата "+data.getXValue()+"\nОбъем продаж "+data.getYValue());


                        }
                    });
        }
        for (XYChart.Data<Number, Number> data : lineChart.getData().get(3).getData()) {
            data.getNode().addEventHandler(MouseEvent.ANY,
                    new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(MouseEvent e) {
                            lineChart.setTitle("Дата "+data.getXValue()+"\nОбъем продаж "+data.getYValue());


                        }
                    });
        }
        for (XYChart.Data<Number, Number> data : lineChart.getData().get(4).getData()) {
            data.getNode().addEventHandler(MouseEvent.ANY,
                    new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(MouseEvent e) {
                            lineChart.setTitle("Дата "+data.getXValue()+"\nОбъем продаж "+data.getYValue());


                        }
                    });
        }



        stage.setScene(scene);
        stage.show();

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        date.setCellValueFactory(new PropertyValueFactory<Prognos, Integer>("date"));
        value.setCellValueFactory(new PropertyValueFactory<Prognos, Integer>("value"));

        tableObject.setItems(list);
    }

    public void sglaj(ActionEvent actionEvent) {

    }

    public void onSendButtonAction(ActionEvent actionEvent) {
        Prognos prognos = new Prognos();

        prognos.setDate(Integer.parseInt(dateInput.getText()));
        if (!valueInput.getText().isEmpty())
            prognos.setValue(Double.parseDouble(valueInput.getText()));
        list.add(prognos);
        tableObject.setItems(list);
    }

    @FXML
    void readFileAction(ActionEvent event) {
        String adress = fileAdress.getText();
        try {
            FileInputStream fileInputStream = new FileInputStream(adress);

            Workbook workbook = new XSSFWorkbook(fileInputStream);
            Sheet sheet = workbook.getSheetAt(0);
            Iterator<Row> rowIterator = sheet.iterator();
            rowIterator.next();
            if (rowIterator.hasNext())
                list.clear();
            while (rowIterator.hasNext()) {
                Row row = rowIterator.next();

                list.add(new Prognos((int) row.getCell(0).getNumericCellValue(), row.getCell(1).getNumericCellValue()));

            }


        } catch (IOException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();

        }


    }

    public void opentTestFile(ActionEvent actionEvent) {
        Desktop desktop = null;
        if (Desktop.isDesktopSupported()) {
            desktop = Desktop.getDesktop();
        }
        try {
            desktop.open(new File("read.xlsx"));
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    public void writeInExcel(String fildeAdress) {
        String adress = fileAdress.getText();
        try {
            FileOutputStream fileInputStream = new FileOutputStream(fildeAdress + ".xlsx");

            Workbook workbook = new XSSFWorkbook();
            Sheet sheet0 = workbook.createSheet("x");
            Row row = sheet0.createRow(0);
            Cell cellx = row.createCell(0);
            Cell celly = row.createCell(1);
            Cell cellT = row.createCell(2);
            Cell cellPlus = row.createCell(3);
            Cell cellMinus = row.createCell(4);

            cellx.setCellValue("x");
            celly.setCellValue("y");
            cellT.setCellValue("Trend");
            cellPlus.setCellValue("Plus");
            cellMinus.setCellValue("Minus");

            for (int i = 1; i < list.size(); i++) {
                Row row1 = sheet0.createRow(i);
                cellx = row1.createCell(0);
                celly = row1.createCell(1);
                cellT = row1.createCell(2);
                cellPlus = row1.createCell(3);
                cellMinus = row1.createCell(4);

                cellx.setCellValue(list.get(i - 1).getDate());
                celly.setCellValue(list.get(i - 1).getValue());
                cellT.setCellValue(observableList.get(i - 1).getValue());
                cellPlus.setCellValue(otklonPlus.get(i - 1).getValue());
                cellMinus.setCellValue(otklonMinus.get(i - 1).getValue());


            }

            workbook.write(fileInputStream);
            fileInputStream.close();

        } catch (IOException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();

        }
    }

    public void writeInWord(String fildeAdress) throws IOException {
        FileWriter fileOutputStream = new FileWriter(new File(fildeAdress + ".doc"));
        String result = "";
        double midl = 0;

        fileOutputStream.write("x/y\n");
        for (int i = 0; i < list.size(); i++) {
            result = list.get(i).toString();
            midl += list.get(i).getValue();

            fileOutputStream.write(result + "\n");
        }
        fileOutputStream.write("x/Trend/PlusOnklon/MinusOtklon\n");
        for (int i = 0; i < observableList.size(); i++) {
            result = observableList.get(i).toString();
            fileOutputStream.write(result + " " + otklonPlus.get(i).getValue() + " " + otklonMinus.get(i).getValue() + "\n");

        }
        fileOutputStream.write(" max " + list.stream().max((x, y) -> Double.compare(x.getValue(), y.getValue())).toString() + "\n");
        midl = midl / list.size();
        fileOutputStream.write("midl " + midl);
        fileOutputStream.close();


    }

    private ObjectInputStream inputStream = null;
    private ObjectOutputStream outputStream = null;

    private void saveResult(Result result) {
        SessionHandler sessionHandler = SessionHandler.getInstance();
        if (sessionHandler.getSocket().isConnected()) {

            try {
                inputStream = sessionHandler.getInputStream();
                outputStream = sessionHandler.getOutputStream();

                outputStream.writeObject(ControllerEnum.SAVERESULT);
                outputStream.writeObject(result);
                Integer id = SessionHandler.userId;
                outputStream.writeObject(id);
                Boolean readResult = (Boolean) inputStream.readObject();


            } catch (IOException e) {

            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }

        }
    }

    public void exitButton(ActionEvent actionEvent) {
        SessionHandler.roleID = 0;
        exit.getScene().getWindow().hide();
        SceneChanger sceneChanger = SceneChanger.getInstance();
        sceneChanger.FXML_page("hello-view.fxml");
    }

    public void onHystoryButtonAction(ActionEvent actionEvent) {
        SessionHandler.roleID = 0;
        exit.getScene().getWindow().hide();
        SceneChanger sceneChanger = SceneChanger.getInstance();
        sceneChanger.FXML_page("history.fxml");
    }
}