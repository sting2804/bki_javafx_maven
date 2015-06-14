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

    private static Writer writer = null;

    public static void writeExcel(List<LoanInfo> loans) throws Exception {
        try {
            File file = new File("Loans.csv");
            writer = new BufferedWriter(new FileWriter(file));
            for (LoanInfo loan : loans) {
                String text = formatLoanToString(loan);
                writer.write(text);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            writer.flush();
            writer.close();
        }
    }

    private static String formatLoanToString(LoanInfo loan){
        String formattedLoan = "";
        formattedLoan = formattedLoan.concat(loan.getId()+",");
        formattedLoan = formattedLoan.concat(loan.getPerson().getName()+",");
        formattedLoan = formattedLoan.concat(loan.getPerson().getSurname()+",");
        formattedLoan = formattedLoan.concat(loan.getPerson().getPatronymic()+",");
        formattedLoan = formattedLoan.concat(loan.getPerson().getBirthday()+",");
        formattedLoan = formattedLoan.concat(loan.getPerson().getInn()+",");
        formattedLoan = formattedLoan.concat(loan.getPerson().getGender()+",");
        formattedLoan = formattedLoan.concat(loan.getPerson().getPassSerial()+",");
        formattedLoan = formattedLoan.concat(loan.getPerson().getPassNumber()+",");
        formattedLoan = formattedLoan.concat(loan.getBalance()+",");
        formattedLoan = formattedLoan.concat(loan.getInitAmount()+",");
        formattedLoan = formattedLoan.concat(loan.getInitDate()+",");
        formattedLoan = formattedLoan.concat(loan.getFinishDate()+",");
        formattedLoan = formattedLoan.concat(loan.getArrears()+",");
        formattedLoan = formattedLoan.concat(loan.getBank().getName()+",");
        formattedLoan = formattedLoan.concat(loan.getBank().getCode()+",");
        formattedLoan = formattedLoan.concat(loan.getCurrency().getName()+",");
        formattedLoan = formattedLoan.concat(loan.getCurrency().getCode()+"\n");
        return formattedLoan;
    }
}
