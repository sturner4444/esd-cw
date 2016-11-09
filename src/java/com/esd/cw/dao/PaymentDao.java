/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.esd.cw.dao;

import com.esd.cw.Queries;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author shaun
 */
public class PaymentDao {

    public PaymentDao() {

    }

    public void makePayment(float amount, String typeOfPayment, String memId, Date date) {

        try {
            String dateString = new SimpleDateFormat("YYYY-MM-dd hh-mm-ss").format(date);
            DbBean.getInstance().runQuery(String.format(Queries.INSERT_PAYMENT.getStatement(), memId, typeOfPayment, amount, dateString));
        } catch (Exception e) {
            System.out.println(e);
        }
    }

}
