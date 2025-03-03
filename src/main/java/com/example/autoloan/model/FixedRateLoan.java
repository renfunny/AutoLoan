package com.example.autoloan.model;

import com.example.autoloan.util.LoanCalculation;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class FixedRateLoan implements LoanCalculation {
    private double amount;
    private double downPayment;
    private double interestRate;
    private int duration;
    private String frequency;
    private double totalPayments;

    public FixedRateLoan(double amount, double downPayment, double interestRate, int duration, String frequency) {
        this.amount = amount;
        this.downPayment = downPayment;
        this.interestRate = interestRate;
        this.duration = duration;
        this.frequency = frequency;
    }

    @Override
    public double calculateMonthlyPayment() {
        double loanAmount = amount -  downPayment;
        int paymentsPerYear = 0;

        if(frequency.equals("Weekly")){
            paymentsPerYear = 52;
        } else if(frequency.equals("Biweekly")){
            paymentsPerYear = 26;
        } else if(frequency.equals("Monthly")){
            paymentsPerYear = 12;
        }

        totalPayments = (int) (duration * (paymentsPerYear / 12.00));
        double ratePerPeriod = (interestRate / 100) / paymentsPerYear;
        double numerator = ratePerPeriod * loanAmount;
        double denominator = 1 - Math.pow(1 + ratePerPeriod, -totalPayments);

        return numerator / denominator;
    }

    @Override
    public List<LoanAmortization> generateAmortizationSchedule(){
        List<LoanAmortization> schedule = new ArrayList<>();

        double loanBalance = amount - downPayment;
        double periodicPayment = calculateMonthlyPayment();
        LocalDate paymentDate = LocalDate.now();

        double monthlyRate = (interestRate / 100) / 12;
        int paymentNo = 1;

        while (loanBalance > 0.0){
            double interestPayment = loanBalance * monthlyRate;
            double principalPayment = periodicPayment - interestPayment;

            if(principalPayment > loanBalance){
                principalPayment = loanBalance;
                periodicPayment = interestPayment +  principalPayment;
            }

            loanBalance -= principalPayment;
            if(loanBalance < 0){
                loanBalance = 0.00;
            }

            schedule.add(new LoanAmortization(paymentNo++, paymentDate, round(periodicPayment), round(interestPayment), round(principalPayment), round(loanBalance)));
            if (frequency.equals("Weekly")) {
                paymentDate = paymentDate.plusWeeks(1);
            } else if (frequency.equals("Biweekly")) {
                paymentDate = paymentDate.plusWeeks(2);
            } else if (frequency.equals("Monthly")) {
                paymentDate = paymentDate.plusMonths(1);
            }
        }
        return schedule;
    }

    private double round(double value){
        return Math.round(value * 100.0) / 100.0;
    }
}
