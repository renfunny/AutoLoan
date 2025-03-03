package com.example.autoloan.model;

import java.time.LocalDate;

public class LoanAmortization {
    private int paymentNo;
    private LocalDate paymentDate;
    private double paymentAmount;
    private double interestPayment;
    private double principalPayment;
    private double remainingBalance;

    public LoanAmortization(int paymentNo, LocalDate paymentDate, double paymentAmount, double interestPayment, double principalPayment, double remainingBalance) {
        this.paymentNo = paymentNo;
        this.paymentDate = paymentDate;
        this.paymentAmount = paymentAmount;
        this.interestPayment = interestPayment;
        this.principalPayment = principalPayment;
        this.remainingBalance = remainingBalance;
    }

    public int getPaymentNo() {
        return paymentNo;
    }

    public LocalDate getPaymentDate() {
        return paymentDate;
    }

    public double getPaymentAmount() {
        return paymentAmount;
    }

    public double getInterestPayment() {
        return interestPayment;
    }

    public double getPrincipalPayment() {
        return principalPayment;
    }

    public double getRemainingBalance() {
        return remainingBalance;
    }
}
