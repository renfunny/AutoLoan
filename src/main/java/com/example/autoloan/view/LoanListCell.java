package com.example.autoloan.view;

import com.example.autoloan.model.Loan;
import javafx.scene.control.ListCell;

public class LoanListCell extends ListCell<Loan> {
    @Override
    protected void updateItem(Loan loan, boolean empty) {
        super.updateItem(loan, empty);

        if(empty || loan == null){
            setText(null);
        } else {
            setText("Name: " + loan.getCustomer().getName() + " , Vehicle: " + loan.getVehicle().getType() + ", Rate: " + loan.getInterestRate() + "%");
        }
    }
}
