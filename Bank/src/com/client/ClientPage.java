/**
 * 
 */
package com.client;

import java.awt.EventQueue;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;

/**
 * @author Raslan
 *
 */
public class ClientPage extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;


	private String operation="Deposit";
	String accountNumber;
	String method;
	String urlStr;
	String server;
	URL url;
	URLConnection urlc;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ClientPage frame = new ClientPage();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public ClientPage() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 325);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(153, 0, 102));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblEnterMoneyAmount = new JLabel("Enter Money Amount");
		lblEnterMoneyAmount.setBounds(44, 37, 119, 16);
		contentPane.add(lblEnterMoneyAmount);
		
		moneyAmount = new JTextField();
		moneyAmount.setBounds(218, 34, 97, 22);
		contentPane.add(moneyAmount);
		moneyAmount.setColumns(10);
		
		JLabel lblChooseOperation = new JLabel("Choose Operation");
		lblChooseOperation.setBounds(44, 76, 119, 16);
		contentPane.add(lblChooseOperation);
		String [] operations= {"Deposit","Withdraw"};	
	
		JComboBox<Object> operationSelect = new JComboBox<Object>(operations);
		operationSelect.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				operation = String.valueOf(operationSelect.getSelectedItem());
			}
		});
		operationSelect.setBounds(218, 73, 97, 22);
		contentPane.add(operationSelect);
		
		JButton btnSubmit = new JButton("Submit");
		btnSubmit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(moneyAmount.getText().equals(null) || moneyAmount.getText().length()==0)
					JOptionPane.showMessageDialog(null, "Please enter the money amount");
				else {
					
					
					String response="";
					if(method.equals("POST")) {
						response =sendPOST(moneyAmount.getText(), operation);
					}
					else if (method.equals("GET")) {
						response=sendGET(moneyAmount.getText(), operation); 
					}
					response=response.trim();
					JOptionPane.showMessageDialog(null, response);
					//System.out.println(response);
					
				}
			}
		});
		btnSubmit.setBounds(218, 117, 97, 25);
		contentPane.add(btnSubmit);
		
		JButton btnQuery = new JButton("Query");
		btnQuery.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String opNm=operationNumber.getText();
				String response="";
				if(opNm.equals(null) || opNm.length()==0 || 
						Integer.parseInt(opNm)>5 || Integer.parseInt(opNm)<1) {
					JOptionPane.showMessageDialog(null, "Please Enter a number between 1 and 5");
					return;
				}
				
				if(method.equals("POST")) {
					response=sendPOST(opNm,"Query");
				}
				if(method.equals("GET")) {
					response= sendGET(opNm,"Query");
				}
				response=response.trim();
				JOptionPane.showMessageDialog(null, response);
				
				//System.out.print(response);
			}
		});
		btnQuery.setBounds(218, 197, 97, 25);
		contentPane.add(btnQuery);
		
		JButton btnHistory = new JButton("History");
		btnHistory.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String response="";
				if(method.equals("POST"))
					response=sendPOST("1","History");
				if(method.equals("GET"))
					response=sendGET("1","History");
				JOptionPane.showMessageDialog(null, response);
				//System.out.println(response);
			}
		});
		btnHistory.setBounds(218, 240, 97, 25);
		contentPane.add(btnHistory);
		
		operationNumber = new JTextField();
		operationNumber.setBounds(218, 162, 97, 22);
		contentPane.add(operationNumber);
		operationNumber.setColumns(10);
		
		JLabel lblNumberOfOperations = new JLabel("Number of Operations");
		lblNumberOfOperations.setBounds(44, 156, 126, 34);
		contentPane.add(lblNumberOfOperations);
		
		JLabel lblViewAllTransactions = new JLabel("View All Transactions");
		lblViewAllTransactions.setBounds(44, 244, 126, 16);
		contentPane.add(lblViewAllTransactions);
	}
	
	String dataStr="";
	private JTextField moneyAmount;
	private JTextField operationNumber;
	public void addParameter(String ps, String vs)
	{
		if(ps.equals(null)|| vs.equals(null)|| ps.length()==0 || vs.length()==0)
			return;
		if(dataStr.length()>0) dataStr+="&";
		
		try {
			dataStr+=ps +"="+URLEncoder.encode(vs,"ASCII");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public String sendGET(String parameter,String parName)
	{
		String response="";
		urlStr = server;
		DataInputStream is;
		
		//String urlStr="http://localhost:7777/WebApplication1/index.jsp"+"?"+parName+"="+parameter+"&ID="+accountNumber;
		urlStr = server + "?"+parName+"="+parameter+"&ID="+accountNumber;
		//System.out.println(parName);
		//System.out.println(parameter);
		try {
			url=new URL(urlStr);
			urlc=url.openConnection();
			is= new DataInputStream(urlc.getInputStream());
			int b=-1;
			while ((b = is.read()) != -1) {
                if ((char) b == '\r') {
                    response += "\n";
                } else {
                    response = response + (char) b;
                };

			}
			
			
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		return response;
		
	}
	
	public String sendPOST(String parameter, String parName)
	{
		String response="";
		urlStr = server;
		InputStream in;
		OutputStream os;
		addParameter(parName,parameter );
		addParameter("ID",accountNumber);
		//String urlStr="http://localhost:7777/WebApplication1/index.jsp";
		//String urlStr="http://localhost:80/PHPServer/PHPServer.php";
		try {
			url=new URL(urlStr);
			urlc=url.openConnection();
			urlc.setDoOutput(true);
            urlc.setDoInput(true);
            urlc.setUseCaches(false);
            os=urlc.getOutputStream();
            os.write(dataStr.getBytes());
            os.close();
            
            in = urlc.getInputStream();
            int b=-1;
            while ((b = in.read()) != -1) {
                if ((char) b == '\r') {
                    response += "\n";
                } else {
                    response = response + (char) b;
                }
            }
            

		}
		catch (Exception e) {
            System.out.println(e.toString());
            
        }
		return response;
		
	}
}
