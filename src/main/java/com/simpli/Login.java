package com.simpli;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/login")
public class Login extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String url = "jdbc:mysql://localhost:3306/ecommerce";
        String uname = "root";
        String pass = "soumyaranjan@261412";

        response.setContentType("text/html");

        String pIdParam = request.getParameter("product_id");
        int pId = 0; // Default value if parameter is not provided

        if (pIdParam != null && !pIdParam.isEmpty()) {
            pId = Integer.parseInt(pIdParam);
        }

        PrintWriter out = response.getWriter();

        String query = "SELECT * FROM products WHERE product_id=?";
        out.print("<h1>Displaying the Product Details...</h1>");
        out.print("<table border='1'><tr><th>product_id</th><th>name</th><th>description</th><th>price</th></tr>");

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection dbCon = DriverManager.getConnection(url, uname, pass);
            PreparedStatement st = dbCon.prepareStatement(query);

            st.setInt(1, pId);

            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                out.print("<tr><td>");
                out.println(rs.getInt(1));
                out.print("</td>");
                out.print("<td>");
                out.print(rs.getString(2));
                out.print("</td>");
                out.print("<td>");
                out.print(rs.getString(3));
                out.print("</td>");
                out.print("<td>");
                out.print(rs.getDouble(4));
                out.print("</td>");
                out.print("</tr>");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        out.print("</table>");
    }

}

