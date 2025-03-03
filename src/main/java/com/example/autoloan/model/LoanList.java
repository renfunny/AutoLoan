package com.example.autoloan.model;

import java.util.ArrayList;
import java.util.List;

public class LoanList {
    private List<Loan> loanList;

    public LoanList() {
        loanList = new ArrayList<>();
    }

    public void addLoan(Loan loan){
        loanList.add(loan);
    }

    public List<Loan> getLoanList(){
        return loanList;
    }

    public int getLoanListSize(){
        return loanList.size();
    }
}
