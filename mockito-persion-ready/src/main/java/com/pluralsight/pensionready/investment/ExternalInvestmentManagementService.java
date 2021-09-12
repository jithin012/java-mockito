package com.pluralsight.pensionready.investment;

import java.util.Currency;

import com.pluralsight.pensionready.Account;

public class ExternalInvestmentManagementService implements InvestmentManagementService {

    public boolean addFunds(Account account, long investmentAmount, Currency investmentCcy) {
        //implementation not relevant for this course module
        return true;
    }

    public boolean buyInvestmentFund(Account account, String fundId, long investmentAmount, Currency investmentCcy) {
        //implementation not relevant for this course module
        return false;
    }

    public boolean sellInvestmentFund(Account account, String fundId, long investmentAmount, Currency investmentCcy) {
        //implementation not relevant for this course module
        return false;
    }
}
