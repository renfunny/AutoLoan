package com.example.autoloan.controller;

import com.example.autoloan.model.Loan;
import com.example.autoloan.view.LoanListCell;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.util.List;
import java.util.function.Consumer;

public class SavedRateController {
    @FXML private ListView<Loan> savedRatesListView;

    private Consumer<Loan> onLoanSelected;
    private Stage stage;

    @FXML
    public void initialize() {
        savedRatesListView.setOnMouseClicked(this::handleDoubleClick);
    }

    public void setStage(Stage stage){
        this.stage = stage;
    }

    public void setSavedRates(List<Loan> loans, Consumer<Loan> onLoanSelected) {
        this.onLoanSelected = onLoanSelected;
        savedRatesListView.getItems().clear();
        savedRatesListView.getItems().addAll(loans);

        savedRatesListView.setCellFactory(param -> new LoanListCell());
    }

    @FXML
    public void handleDoubleClick(MouseEvent event){
        if (event.getClickCount() == 2) {
            Loan selectedLoan = savedRatesListView.getSelectionModel().getSelectedItem();
            if(selectedLoan !=  null && onLoanSelected != null){
                onLoanSelected.accept(selectedLoan);
                closeWindow();
            }
        }
    }

    @FXML
    public void handleClose(){
        closeWindow();
    }

    public void closeWindow(){
        if(stage != null){
            stage.close();
        }
    }
}