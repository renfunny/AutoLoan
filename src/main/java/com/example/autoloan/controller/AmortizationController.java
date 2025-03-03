package com.example.autoloan.controller;

import com.example.autoloan.model.LoanAmortization;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.util.List;

public class AmortizationController {
    @FXML private TableView<LoanAmortization> amortizationTable;
    @FXML private TableColumn<LoanAmortization, Integer> paymentNoColumn;
    @FXML private TableColumn<LoanAmortization, String> paymentDateColumn;
    @FXML private TableColumn<LoanAmortization, Double> paymentAmountColumn;
    @FXML private TableColumn<LoanAmortization, Double> interestPaymentColumn;
    @FXML private TableColumn<LoanAmortization, Double> principalPaymentColumn;
    @FXML private TableColumn<LoanAmortization, Double> remainingBalanceColumn;

    private ObservableList<LoanAmortization> amortizationData = FXCollections.observableArrayList();
    private Stage stage;

    @FXML void initialize() {
        paymentNoColumn.setCellValueFactory(new PropertyValueFactory<>("paymentNo"));
        paymentDateColumn.setCellValueFactory(new PropertyValueFactory<>("paymentDate"));
        paymentAmountColumn.setCellValueFactory(new PropertyValueFactory<>("paymentAmount"));
        interestPaymentColumn.setCellValueFactory(new PropertyValueFactory<>("interestPayment"));
        principalPaymentColumn.setCellValueFactory(new PropertyValueFactory<>("principalPayment"));
        remainingBalanceColumn.setCellValueFactory(new PropertyValueFactory<>("remainingBalance"));

        amortizationTable.setItems(amortizationData);
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void setAmortizationData(List<LoanAmortization> data){
        amortizationData.clear();
        amortizationData.setAll(data);
    }

    public void handleClose(){
        if(stage != null){
            stage.close();
        }
    }

}
