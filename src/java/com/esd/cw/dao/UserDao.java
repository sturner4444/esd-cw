/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.esd.cw.dao;

import com.esd.cw.Queries;
import com.esd.cw.model.Member;
import com.esd.cw.model.User;
import com.sun.istack.internal.logging.Logger;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;

/**
 *
 * @author shaun
 */
public class UserDao extends AbstractDao {

    public UserDao() {
        super();
    }

    public List<User> findAll() {

        // define a list of users
        List<User> allUsers = new ArrayList<User>();

        // define a hash map to store the result in
        ArrayList<HashMap> result = new ArrayList();

        try {
            // run the query and get a hash map of all rows
            result = super.select("SELECT * FROM users");
        } catch (SQLException e) {
            // error
            System.out.println("ERROR: UserDao().findAll() - " + e.toString());
        }

        // create list of users from the hash map generated by running the query
        for (HashMap r : result) {
            allUsers.add(
                    new User(
                            r.get("id").toString(),
                            r.get("password").toString(),
                            r.get("status").toString(),
                            Boolean.valueOf(r.get("is_admin").toString())
                    )
            );
        }

        // return all the users
        return allUsers;
    }

    public User findById(String userId) {

        // define a hash map to store the result in
        ArrayList<HashMap> result = new ArrayList();

        try {
            result = super.select("SELECT * FROM users WHERE id='" + userId + "'");
        } catch (SQLException e) {

        }

        if (result.size() > 0) {
            return new User(
                    result.get(0).get("id").toString(),
                    result.get(0).get("password").toString(),
                    result.get(0).get("status").toString(),
                    Boolean.valueOf(result.get(0).get("is_admin").toString())
            );
        } else {
            return new User();
        }
    }

    public boolean userExists(String userId) {
        // define a hash map to store the result in
        ArrayList<HashMap> result = new ArrayList();

        try {
            result = super.select("SELECT * FROM users WHERE id='" + userId + "'");
        } catch (SQLException e) {

        }

        return result.size() > 0;
    }

    public boolean registerUser(User user, Member member) {
        try {
            String dob = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss").format(member.getDateOfBirth());
            String dor = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss").format(member.getDateOfRegistration());
            insert(String.format(Queries.INSERT_USER.getStatement(), user.getUserId(), user.getPassword(), user.getUserStatus(), user.isIsAdmin()));
            insert(String.format(Queries.INSERT_MEMBER.getStatement(), member.getMemberId(), member.getName(), member.getAddress(), dob, dor, member.getStatus(), member.getBalance(), member.getClaimsRemaining()));
            return true;
        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
    }

    boolean insertUser(User user) {
        return true;
    }

    boolean updateUser(User user) {
        return true;
    }

    boolean deleteUser(User user) {
        return true;
    }
}
