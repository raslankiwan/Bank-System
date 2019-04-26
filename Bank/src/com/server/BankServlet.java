package com.server;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mysql.jdbc.Connection;


/**
 * Servlet implementation class BankServlet
 */
@WebServlet("/BankServlet")
public class BankServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BankServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		Connection conn = null;
		String id = request.getParameter("ID");
		String password = request.getParameter("password");
		String deposit = request.getParameter("Deposit");
		String withdraw = request.getParameter("Withdraw");
		String userQuery = request.getParameter("Query");
		String history = request.getParameter("History");
		PrintWriter out = response.getWriter();
		Statement stmt;
		ResultSet res;
		PreparedStatement ps;
		String sqlQuery;
		
		try {
			DBConnection dbc = DBConnection.getInstance();
			conn = dbc.getConnection();
			stmt = conn.createStatement();
			
			if (id != null && password != null){
				
				sqlQuery = "Select * from users where ID= " + id;
				res = stmt.executeQuery(sqlQuery);
				if (res.next()) {
					if (res.getString("password").equals(password)) {
						out.println("True Password");
					} else {
						out.println("Wrong Password");
					}
				} 
			} else if (id != null && deposit != null && withdraw == null) {
				sqlQuery = "SELECT balance FROM users where ID=" + id;
				res = stmt.executeQuery(sqlQuery);
				if (res.next()) {
					int balance = Integer.parseInt(res.getString("balance"));
					balance += Integer.parseInt(deposit);
					sqlQuery = "UPDATE users SET balance= ? where ID= ?";
					ps = conn.prepareStatement(sqlQuery);
					ps.setInt(1, balance);
					ps.setString(2, id);
					ps.executeUpdate();
					
					sqlQuery = "INSERT INTO transactions (Type, Value, user_id) values(?, ?, ?)";
					ps = conn.prepareStatement(sqlQuery);
					ps.setString(1, "Deposit");
					ps.setString(2, deposit); 
					ps.setString(3, id);
					ps.execute();
					
					out.print("Operation Complete: Deposit: "+ deposit+"$");
					deposit = null;
				} 
			} else if (id != null && withdraw != null && deposit == null) {
				sqlQuery = "SELECT balance FROM users where ID=" + id;
				res = stmt.executeQuery(sqlQuery);
				if (res.next()) {
					int balance = Integer.parseInt(res.getString("balance"));
					balance -= Integer.parseInt(withdraw);
					if (balance < 0) {
						out.print("Not Enough");
					} else {
						sqlQuery = "UPDATE users SET balance= ? where ID= ?";
						ps = conn.prepareStatement(sqlQuery);
						ps.setInt(1, balance);
						ps.setString(2, id);
						ps.executeUpdate();
						
						sqlQuery = "INSERT INTO transactions (Type, Value, user_id) values(?, ?, ?)";
						ps = conn.prepareStatement(sqlQuery);
						ps.setString(1, "Withdraw");
						ps.setString(2, withdraw); 
						ps.setString(3, id);
						ps.execute();
						
						out.print("Operation Complete: Withdraw: "+ withdraw+"$");
						withdraw = null;
					}
				} 
			} else if (id != null && userQuery != null) {
				sqlQuery = "select * from transactions where user_id= " + id + " order by id desc";
				res = stmt.executeQuery(sqlQuery);
				int operationNumber = Integer.parseInt(userQuery);
				if (!res.next()) {
					out.println("No recent transactions");
				} else {
					do {
						String type = res.getString("Type");
						String value = res.getString("Value");
						String date = res.getString("Date");
						out.print(type + ": " + value + " on: " + date +"\n");
						
					} while (res.next() &&  --operationNumber != 0);
				}

				
			} else if (id != null && history != null) {
				sqlQuery = "select * from transactions where user_id=" +id;
				res = stmt.executeQuery(sqlQuery);
				if (!res.next()) {
					out.println("No Transactions");
				} else {
					do {
						String type = res.getString("Type");
						String value = res.getString("Value");
						String date = res.getString("Date");
						out.print(type + ": " + value + " on: " + date +"\n");
						
					} while (res.next());
				}
			} else {
					out.println("Illegal Operation");
				}
			
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
			
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
