/*
 * Stephen Turner, Computer Science BSc Year 3
 * University Of the West Of England
 */
package com.esd.cw.servlet;

import com.esd.cw.enums.Queries;
import com.esd.cw.services.ClaimService;
import com.esd.cw.services.MemberService;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author sturner
 */
@WebServlet(name = "claimServlet", urlPatterns = {"/claimServlet"})
public class ClaimServlet extends HttpServlet {

    ClaimService claimService = new ClaimService();;
   
    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("claim.jsp");
        dispatcher.forward(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        HttpSession session = request.getSession();

        //get inputs from form
        String test = (String) request.getParameter("amount");
        float claimAmount = Float.valueOf(test);
        String claimRationale = (String) request.getParameter("rationale");
        //get user from session
        com.esd.cw.model.User user = (com.esd.cw.model.User) session.getAttribute("user");
        
        String memId = user.getUserId();
        
        Map claimResponse = new HashMap();
        
        String claimResponseString = "Failed to make claim";
        try {
            claimResponse = claimService.validateClaim(user, claimAmount);
            claimResponseString = claimResponse.get("message").toString();
        } catch (SQLException | ParseException ex) {
            Logger.getLogger(ClaimServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        if (Boolean.valueOf((String) claimResponse.get("success"))) {
            
            try {
                claimService.makeClaim(claimAmount, claimRationale, memId);
            } catch (SQLException ex) {
                Logger.getLogger(ClaimServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }
        session.setAttribute("user", user);
        //set message and redirect back to page
        request.setAttribute("claimResponse", claimResponseString);
        request.getRequestDispatcher("claim.jsp").forward(request, response);
        
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
