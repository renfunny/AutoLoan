package com.example.autoloan.util;

import com.example.autoloan.model.LoanAmortization;

import java.util.List;

public interface LoanCalculation {
    public abstract double calculateMonthlyPayment();
    public abstract List<LoanAmortization> generateAmortizationSchedule();
}
