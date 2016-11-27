/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.esd.cw.dao;

import com.esd.cw.enums.Queries;
import com.esd.cw.model.Member;
import com.esd.cw.util.Util;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import javafx.scene.shape.QuadCurve;

/**
 *
 * @author shaun
 */
public class MemberDao {

    public MemberDao() {

    }

    public long getFirstMembership(String memId) throws SQLException, ParseException {

        // define a hash map to store the result in
        ArrayList<HashMap> result;
        long dateInMs = 0;
        result = DbBean.getInstance().select(String.format(Queries.SELECT_USER_FIRST_PAYMENT.getSql(), memId));

        if (result.size() > 0) {
            Date date = Util.getDateFromString(result.get(0).get("date").toString());

            dateInMs = date.getTime();
        }
        return dateInMs;
    }

    public List<Member> findAll() {

        // define a list of members
        List<Member> allMembers = new ArrayList<Member>();

        // define a hash map to store the result in
        ArrayList<HashMap> result = new ArrayList();

        try {
            // run the query and get a hash map of all rows
            result = DbBean.getInstance().select("SELECT * FROM Members");
        } catch (SQLException e) {

        }

        // create list of members from the hash map generated by running the query
        for (HashMap r : result) {
            allMembers.add(
                    new Member(
                            r.get("id").toString(),
                            r.get("name").toString(),
                            r.get("address").toString(),
                            Util.getDateFromString(r.get("dob").toString()),
                            Util.getDateFromString(r.get("dor").toString()),
                            r.get("status").toString(),
                            Float.parseFloat(r.get("balance").toString()),
                            Integer.parseInt(r.get("claims_remaining").toString())
                    )
            );
        }

        return allMembers;
    }

    public Member findById(String memberId) {

        // define a hash map to store the result in
        ArrayList<HashMap> result = new ArrayList();

        try {
            result = DbBean.getInstance().select("SELECT * FROM Members WHERE id='" + memberId + "'");
        } catch (SQLException e) {

        }

        if (result.size() > 0) {
            return new Member(
                    result.get(0).get("id").toString(),
                    result.get(0).get("name").toString(),
                    result.get(0).get("address").toString(),
                    Util.getDateFromString(result.get(0).get("dob").toString()),
                    Util.getDateFromString(result.get(0).get("dor").toString()),
                    result.get(0).get("status").toString(),
                    Float.parseFloat(result.get(0).get("balance").toString()),
                    Integer.parseInt(result.get(0).get("claims_remaining").toString())
            );
        } else {
            return new Member();
        }
    }

    public boolean updateMemberStatus(Member member) throws SQLException {

        DbBean.getInstance().runQuery(String.format(Queries.UPDATE_MEMBER_STATUS.getSql(), member.getStatus(), member.getMemberId()));

        return true;
    }

    public void deductAmountFromAllUsers(double toDeductFromEachUser) throws SQLException {
        DbBean.getInstance().runQuery(String.format(Queries.DEDUCT_AMOUNT_FROM_ALL_MEMBERS_BALANCE.getSql(), String.valueOf(toDeductFromEachUser)));
    }
}
