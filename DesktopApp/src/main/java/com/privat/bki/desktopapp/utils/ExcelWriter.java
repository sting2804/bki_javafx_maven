package com.privat.bki.desktopapp.utils;

import com.privat.bki.business.entities.LoanInfo;
import org.springframework.stereotype.Component;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.Writer;
import java.util.List;

/**
 * Created by Home on 31.05.2015.
 */
@Component
public class ExcelWriter {

    Writer writer = null;
    List<LoanInfo> loans;

    public ExcelWriter() {
    }

    public ExcelWriter(List<LoanInfo> loans) {
        this.loans = loans;
    }

    public void writeExcel(List<LoanInfo> loans) throws Exception {
        try {
            File file = new File("Loans.csv.");
            writer = new BufferedWriter(new FileWriter(file));
            for (LoanInfo loan : loans) {
                String text = loan.toString() + "\n";
                writer.write(text);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            writer.flush();
            writer.close();
        }
    }
}
