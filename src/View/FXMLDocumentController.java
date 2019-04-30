/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import Interface.DraggedScene;
import Model.AFDP;
import Model.Escaner;
import static Model.Escaner.obtenerSimbolo;
import Model.Simbolo;
import com.jfoenix.controls.JFXTextArea;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.SingleSelectionModel;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;

/**
 *
 * @author Carlos Rodriguez
 */
public class FXMLDocumentController implements Initializable, DraggedScene {

    @FXML
    private Button btnScanner;
    @FXML
    private Button btnParser;
    @FXML
    private Button btnMore;
    @FXML
    private Button btnOpen;
    @FXML
    private TableView<Simbolo> tableView;
    @FXML
    private AnchorPane topBar;
    @FXML
    private Text txtKW;
    @FXML
    private Text txtNum;
    @FXML
    private Text txtId;
    @FXML
    private Text txtOp;
    @FXML
    private Text txtOther;

    private final TableColumn cadena = new TableColumn("Cadena");

    private final TableColumn tipo = new TableColumn("Tipo");

    private final TableColumn longitud = new TableColumn("Longitud");
    
    @FXML
    private TabPane tbPane;
    @FXML
    private Button btnExample;
    @FXML
    private Button btnRecognize;
    @FXML
    private JFXTextArea txtAreaCode;

    private void handleButtonAction(ActionEvent event) {

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Permite arrastrar el panel
        onDraggedScene(topBar);

        // Se establecen las columnas de la tabla
        cadena.setPrefWidth(220);
        tipo.setPrefWidth(220);
        longitud.setPrefWidth(218);
        // Se añaden las columnas
        tableView.getColumns().addAll(cadena, tipo, longitud);

    }

    @FXML
    private void exit(ActionEvent event) {
        System.exit(0);
    }

    @FXML
    private void openFile(ActionEvent event) throws IOException {
        //Escaner.abrirarchivo("src/Model/codigo.txt");

        FileChooser jfile = new FileChooser();
        configureFileChooser(jfile);

        File file = jfile.showOpenDialog(null);
        if (file != null) {
            String path = file.getAbsolutePath();
            updateTable(path);
        }
    }

    private static void configureFileChooser(
            final FileChooser fileChooser) {
        fileChooser.setTitle("Seleccionar .txt o .cpp");

        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("CPP", "*.cpp"),
                new FileChooser.ExtensionFilter("TXT", "*.txt")
        );
    }

    private void updateTable(String ruta) {
        Escaner.indice = 0;
        String cadenaArchivo = Escaner.leerArchivo(ruta);
        ArrayList<Simbolo> simbolos = new ArrayList<>();

        int c_kw = 0;
        int c_num = 0;
        int c_id = 0;
        int c_op = 0;
        int c_oth = 0;
        Simbolo simbolo;
        do {
            simbolo = obtenerSimbolo(cadenaArchivo);

            if (simbolo != null) {
                simbolos.add(simbolo);

                switch (simbolo.getTipo()) {
                    case "Palabra Reservada":
                        c_kw++;
                        break;

                    case "Identificador":
                        c_id++;
                        break;

                    case "Numero":
                        c_num++;
                        break;

                    case "Operador Relacional":
                    case "Operador Logico":
                    case "Operador Aritmetico":
                        c_op++;
                        break;

                    case "Caracter":
                    case "Cadena":
                    case "Flujo de programa":
                    case "Tipo de Dato":
                    case "Otro":
                        c_oth++;
                        break;

                }
            }
        } while (simbolo != null);

        ObservableList<Simbolo> simbolosList = FXCollections.observableArrayList(simbolos);

        cadena.setCellValueFactory(
                new PropertyValueFactory("cadena")
        );

        tipo.setCellValueFactory(
                new PropertyValueFactory("tipo")
        );

        longitud.setCellValueFactory(
                new PropertyValueFactory("longitud")
        );

        tableView.setItems(simbolosList);
        txtId.setText(String.valueOf(c_id));
        txtKW.setText(String.valueOf(c_kw));
        txtNum.setText(String.valueOf(c_num));
        txtOp.setText(String.valueOf(c_op));
        txtOther.setText(String.valueOf(c_oth));
    }

    @FXML
    private void goScanner(ActionEvent event) {
        SingleSelectionModel<Tab> selectionModel = tbPane.getSelectionModel();
        selectionModel.select(0);
    }

    @FXML
    private void goADFP(ActionEvent event) {
        SingleSelectionModel<Tab> selectionModel = tbPane.getSelectionModel();
        selectionModel.select(1);
    }

    @FXML
    private void showExample(ActionEvent event) {
        
    }

    @FXML
    private void recognize(ActionEvent event) {
        System.out.println(txtAreaCode.getText() + "$");
        Escaner.resetScan();
        AFDP auto = new AFDP();
        auto.completarPila(txtAreaCode.getText() + "$");
    }

}
