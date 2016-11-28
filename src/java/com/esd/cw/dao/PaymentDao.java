/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.esd.cw.dao;

import com.esd.cw.enums.Queries;
import com.esd.cw.model.Payment;
import com.esd.cw.model.User;
import com.esd.cw.util.Util;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author shaun
 */
public class PaymentDao {

    public PaymentDao() {

    }

    public void makePayment(Payment payment) {

        try {
            String dateString = new SimpleDateFormat("YYYY-MM-dd hh-mm-ss").format(payment.getDateOfPayment());
            DbBean.getInstance().runQuery(String.format(Queries.INSERT_PAYMENT.getSql(), payment.getMemberId(), payment.getTypeOfPayment(), payment.getPaymentAmount(), dateString));
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public List<Payment> findPaymentsForUser(String userId) {

        // define a list of users
        List<Payment> allPayments = new ArrayList<>();

        // define a hash map to store the result in
        ArrayList<HashMap> result = new ArrayList();

        try {
            // run the query and get a hash map of all rows
            result = DbBean.getInstance().select(String.format(Queries.SELECT_PAYMENTS_FOR_USER.getSql(), userId));
        } catch (SQLException e) {
            // error
            System.out.println("ERROR: PaymentDao().findPaymentsForUser() - " + e.toString());
        }

        // create list of users from the hash map generated by running the query
        for (HashMap r : result) {
            allPayments.add(
                    new Payment(                    
                            (String) r.get("mem_id"),
                            (String) r.get("type_of_payment"),
                            (float) r.get("amount"),
                            Util.getDateFromString(r.get("date").toString())
                            
                    )
            );
        }

        // return all the users
        return allPayments;
    }

}
