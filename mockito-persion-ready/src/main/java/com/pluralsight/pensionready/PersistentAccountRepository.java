package com.pluralsight.pensionready;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.pluralsight.pensionready.setup.BackgroundCheckResults;

public class PersistentAccountRepository implements AccountRepository {

    public boolean save(String id, String firstName, String lastName, String taxId, LocalDate dob, BackgroundCheckResults backgroundCheckResults) {
        //implementation not relevant for this course module
        return false;
    }

    public boolean isExpired(Account account) {
        return LocalDateTime.now().compareTo(account.getExpectedRetirement()) >= 0;
    }
}
