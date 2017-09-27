package com.whitehatsec.monolith;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class DispatcherServlet extends HttpServlet {
    // process requests to encrypt / decryipt some input
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        String text = request.getParameter("text");
        PrintWriter writer = response.getWriter();

        if (action == "encrypt") {
            String cipherText = null;

            try {
                cipherText = Crypto.encrypt(text);
            } catch (Exception e) {
                cipherText = e.getMessage();
            }

            writer.println(cipherText);
        } else if (action == "decrypt") {
            String plainText = null;

            try {
                plainText = Crypto.decrypt(text);
            } catch (Exception e) {
                plainText = e.getMessage();
            }

            writer.println(plainText);
        } else {
            writer.println("unsupported action: " + action);
        }
    }

}