package com.example.autoloan.controller;

import com.example.autoloan.model.*;
import com.example.autoloan.model.FixedRateLoan;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class AutoLoanController {
    @FXML private TextField nameTextField;
    @FXML private TextField phoneTextField;
    @FXML private TextField cityTextField;
    @FXML private ChoiceBox<String> provinceChoiceBox;
    @FXML private RadioButton carRadioBtn;
    @FXML private RadioButton truckRadioBtn;
    @FXML private RadioButton vanRadioBtn;
    @FXML private RadioButton newRadioBtn;
    @FXML private RadioButton usedRadioBtn;
    @FXML private TextField priceTextField;
    @FXML private TextField downPaymentTextField;
    @FXML private RadioButton zeroNineRadioBtn;
    @FXML private RadioButton oneNineRadioBtn;
    @FXML private RadioButton twoNineRadioBtn;
    @FXML private RadioButton otherRadioBtn;
    @FXML private TextField otherTextField;
    @FXML private RadioButton weeklyRadioBtn;
    @FXML private RadioButton biweeklyRadioBtn;
    @FXML private RadioButton monthlyRadioBtn;
    @FXML private Slider durationSlider;
    @FXML private Label estimateRateLabel;
    @FXML private Button clearBtn;
    @FXML private Button calculateBtn;
    @FXML private Button saveBtn;
    @FXML private Button showAmortizationBtn;
    @FXML private Button showSavedBtn;

    private LoanList  loanList;

    @FXML
    public void initialize(){
        this.loanList = new LoanList();

        clearBtn.setOnAction(event -> clearFields());
        calculateBtn.setOnAction(event -> calculateLoan());
        saveBtn.setOnAction(event -> saveCurrentRate());
        showAmortizationBtn.setOnAction(event -> openAmortizationView());
        showSavedBtn.setOnAction(event -> opensSavedRatesView());

        provinceChoiceBox.getItems().addAll("ON", "QC", "BC", "AB", "MB", "SK", "NS", "NB", "NL", "PE");

        ToggleGroup vehicleTypeGroup = new ToggleGroup();
        carRadioBtn.setToggleGroup(vehicleTypeGroup);
        truckRadioBtn.setToggleGroup(vehicleTypeGroup);
        vanRadioBtn.setToggleGroup(vehicleTypeGroup);

        ToggleGroup vehicleUsageGroup = new ToggleGroup();
        newRadioBtn.setToggleGroup(vehicleUsageGroup);
        usedRadioBtn.setToggleGroup(vehicleUsageGroup);

        ToggleGroup interestRateGroup = new ToggleGroup();
        zeroNineRadioBtn.setToggleGroup(interestRateGroup);
        oneNineRadioBtn.setToggleGroup(interestRateGroup);
        twoNineRadioBtn.setToggleGroup(interestRateGroup);
        otherRadioBtn.setToggleGroup(interestRateGroup);

        interestRateGroup.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue == otherRadioBtn) {
                otherTextField.setDisable(false);
            } else {
                otherTextField.setDisable(true);
            }
        });

        ToggleGroup paymentFrequencyGroup = new ToggleGroup();
        weeklyRadioBtn.setToggleGroup(paymentFrequencyGroup);
        biweeklyRadioBtn.setToggleGroup(paymentFrequencyGroup);
        monthlyRadioBtn.setToggleGroup(paymentFrequencyGroup);

        provinceChoiceBox.setValue("ON");
        carRadioBtn.setSelected(true);
        newRadioBtn.setSelected(true);
        zeroNineRadioBtn.setSelected(true);
        monthlyRadioBtn.setSelected(true);
    }

    public void clearFields(){
        nameTextField.clear();
        phoneTextField.clear();
        cityTextField.clear();
        provinceChoiceBox.setValue("ON");
        carRadioBtn.setSelected(true);
        newRadioBtn.setSelected(true);
        priceTextField.clear();
        downPaymentTextField.clear();
        zeroNineRadioBtn.setSelected(true);
        otherTextField.clear();
        monthlyRadioBtn.setSelected(true);
        estimateRateLabel.setText("0.00");
    }

    public void calculateLoan(){
        try{
            double price = Double.parseDouble(priceTextField.getText());
            double downPayment = Double.parseDouble(downPaymentTextField.getText());
            int durationMonths = (int)  durationSlider.getValue();

            double interestRate = 0.0;
            if(zeroNineRadioBtn.isSelected()){
                interestRate = 0.9;
            } else if(oneNineRadioBtn.isSelected()){
                interestRate = 1.99;
            } else if(twoNineRadioBtn.isSelected()){
                interestRate = 2.99;
            } else if (otherRadioBtn.isSelected()){
                try {
                    interestRate = Double.parseDouble(otherTextField.getText());
                } catch (NumberFormatException e) {
                    showErrorPopup("Invalid Interest Rate!");
                    return;
                }
            }

            String frequency = "";
            if(weeklyRadioBtn.isSelected()){
                frequency = "Weekly";
            } else if(biweeklyRadioBtn.isSelected()){
                frequency = "Biweekly";
            } else if(monthlyRadioBtn.isSelected()){
                frequency = "Monthly";
            }

            FixedRateLoan loan = new FixedRateLoan(price, downPayment, interestRate, durationMonths, frequency);

            double payment = loan.calculateMonthlyPayment();

            estimateRateLabel.setText(String.format("$%.2f %s", payment, frequency));
        } catch (NumberFormatException e){
            showErrorPopup(("Failed to Calculate Loan!"));
        }
    }

    public void saveCurrentRate(){
        try{
            String customerName = nameTextField.getText();
            String phoneNumber = phoneTextField.getText();
            String city = cityTextField.getText();
            String province = provinceChoiceBox.getValue();

            Customer customer = new Customer(customerName, phoneNumber, city, province);

            String vehicleType = "";
            if(carRadioBtn.isSelected()){
                vehicleType = "Car";
            } else if(truckRadioBtn.isSelected()){
                vehicleType = "Truck";
            } else if(vanRadioBtn.isSelected()){
                vehicleType = "Family Van";
            }
            String vehicleUsage = "";
            if(newRadioBtn.isSelected()){
                vehicleUsage = "New";
            } else if(usedRadioBtn.isSelected()){
                vehicleUsage = "Used";
            }
            double price = Double.parseDouble(priceTextField.getText());

            Vehicle vehicle = new Vehicle(vehicleType, vehicleUsage, price);

            double downPayment = Double.parseDouble(downPaymentTextField.getText());

            int durationMonths = (int)  durationSlider.getValue();

            double interestRate = 0.0;
            if(zeroNineRadioBtn.isSelected()){
                interestRate = 0.9;
            } else if(oneNineRadioBtn.isSelected()){
                interestRate = 1.99;
            } else if(twoNineRadioBtn.isSelected()){
                interestRate = 2.99;
            } else if (otherRadioBtn.isSelected()){
                try {
                    interestRate = Double.parseDouble(otherTextField.getText());
                } catch (NumberFormatException e) {
                    showErrorPopup("Invalid Interest Rate!");
                    return;
                }
            }

            String frequency = "";
            if(weeklyRadioBtn.isSelected()){
                frequency = "Weekly";
            } else if(biweeklyRadioBtn.isSelected()){
                frequency = "Biweekly";
            } else if(monthlyRadioBtn.isSelected()){
                frequency = "Monthly";
            }

            Loan savedLoan = new Loan(price, downPayment, interestRate, durationMonths, frequency, customer, vehicle);
            loanList.addLoan(savedLoan);

            showSuccessPopUp("Loan Saved!");
        } catch (NumberFormatException e){
            showErrorPopup("Invalid Inputs!");
        }
    }

    public void opensSavedRatesView(){
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/autoloan/savedRates-view.fxml"));
            Parent root = loader.load();

            SavedRateController savedRatesController = loader.getController();

            savedRatesController.setSavedRates(loanList.getLoanList(), this::loadSelectedLoan);

            Stage stage = new Stage();
            stage.setTitle("Saved Rates");
            stage.setScene(new Scene(root));

            savedRatesController.setStage(stage);

            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            showErrorPopup("Error Loading SavedRates!");
        }
    }

    public void openAmortizationView(){
        try {
            double price = Double.parseDouble(priceTextField.getText());
            double downPayment = Double.parseDouble(downPaymentTextField.getText());
            int durationMonths = (int) durationSlider.getValue();
            double interestRate = 0.0;
            if(zeroNineRadioBtn.isSelected()){
                interestRate = 0.9;
            } else if(oneNineRadioBtn.isSelected()){
                interestRate = 1.99;
            } else if(twoNineRadioBtn.isSelected()){
                interestRate = 2.99;
            } else if (otherRadioBtn.isSelected()){
                try {
                    interestRate = Double.parseDouble(otherTextField.getText());
                } catch (NumberFormatException e) {
                    showErrorPopup("Invalid Interest Rate!");
                    return;
                }
            }

            String frequency = "";
            if (weeklyRadioBtn.isSelected()) frequency = "Weekly";
            if (biweeklyRadioBtn.isSelected()) frequency = "Biweekly";
            if (monthlyRadioBtn.isSelected()) frequency = "Monthly";

            // Create loan and generate amortization schedule
            FixedRateLoan loan = new FixedRateLoan(price, downPayment, interestRate, durationMonths, frequency);
            List<LoanAmortization> schedule = loan.generateAmortizationSchedule();

            // Load Amortization View
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/autoloan/amortization-view.fxml"));
            Parent root = loader.load();

            AmortizationController controller = loader.getController();
            controller.setAmortizationData(schedule);

            Stage stage = new Stage();
            stage.setTitle("Amortization Schedule");
            stage.setScene(new Scene(root));
            
            controller.setStage(stage);
            
            stage.show();
        } catch (IOException | NumberFormatException e) {
            showErrorPopup("Failed to Open Amortization View! Make sure all input values are correct.");
        }
    }

    public void loadSelectedLoan(Loan loan) {
        nameTextField.setText(loan.getCustomer().getName());
        phoneTextField.setText(loan.getCustomer().getPhone());
        cityTextField.setText(loan.getCustomer().getCity());
        provinceChoiceBox.setValue(loan.getCustomer().getProvince());

        switch (loan.getVehicle().getType()) {
            case "Car":
                carRadioBtn.setSelected(true);
                break;
            case "Truck":
                truckRadioBtn.setSelected(true);
                break;
            case "Family Van":
                vanRadioBtn.setSelected(true);
                break;
            default:
                break;
        }

        switch (loan.getVehicle().getAge()) {
            case "New":
                newRadioBtn.setSelected(true);
                break;
            case "Used":
                usedRadioBtn.setSelected(true);
                break;
            default:
                break;
        }

        priceTextField.setText(String.valueOf(loan.getVehicle().getPrice()));
        downPaymentTextField.setText(String.valueOf(loan.getDownPayment()));

        double interestRate = loan.getInterestRate();
        if (interestRate == 0.9) {
            zeroNineRadioBtn.setSelected(true);
        } else if (interestRate == 1.99) {
            oneNineRadioBtn.setSelected(true);
        } else if (interestRate == 2.99) {
            twoNineRadioBtn.setSelected(true);
        } else {
            otherRadioBtn.setSelected(true);
            otherTextField.setText(String.valueOf(loan.getInterestRate()));
        }

        switch (loan.getFrequency()) {
            case "Weekly":
                weeklyRadioBtn.setSelected(true);
                break;
            case "Biweekly":
                biweeklyRadioBtn.setSelected(true);
                break;
            case "Monthly":
                monthlyRadioBtn.setSelected(true);
                break;
            default:
                break;
        }
        durationSlider.setValue(loan.getDuration());
        calculateLoan();
    }

    private void showSuccessPopUp(String message) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Success");
        alert.setHeaderText("Success");
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void showErrorPopup(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("An error occurred");
        alert.setContentText(message);
        alert.showAndWait();
    }
}