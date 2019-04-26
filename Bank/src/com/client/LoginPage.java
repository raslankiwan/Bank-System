package com.client;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JComboBox;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import java.awt.Color;

public class LoginPage extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtUsername;
	private String accountNumber;
	private String password;
	private String method="GET";
	private String urlStr="http://127.0.0.1/PHPServer.php";
	String server = urlStr;
	String dataStr="";
	static final String methods[]={"GET","POST"};
	static final String servers[]={
			"http://127.0.0.1/PHPServer.php",
			"http://localhost:8080/Bank/BankJSP.jsp",
			"http://localhost:8080/Bank/BankServlet"
			};
	URL url;
	URLConnection urlc;
	static LoginPage frame; 
	private JLabel lblSelectServer;
	private JLabel lblSelectMethod;
	private JLabel lblEnterUsername;
	private JLabel lblEnterPassword;
	private JPasswordField txtPassword;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					frame = new LoginPage();
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
	public LoginPage() {
		setTitle("Login");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 655, 300);
		contentPane = new JPanel();
		contentPane.setForeground(new Color(51, 204, 0));
		contentPane.setBackground(new Color(153, 0, 153));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		txtUsername = new JTextField();
		txtUsername.setBounds(160, 110, 392, 22);
		contentPane.add(txtUsername);
		txtUsername.setColumns(10);
		
		JButton btnLogin = new JButton("Login");
		btnLogin.setForeground(Color.GREEN);
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(method.equals("GET")||method.equals("POST"))
				{
					
					accountNumber=txtUsername.getText();
					password=String.valueOf(txtPassword.getPassword());
					if(accountNumber.equals("")) {
						JOptionPane.showMessageDialog(null, "Please Enter Your Account Number");
						return;
					}
					else {
						String response="";
						if(method.equals("POST")) {
							response=sendPOST();							
						}

						else if(method.equals("GET")) {
							response=sendGET();
						}
						response= response.trim();

						if(response.equals("True Password"))
						{
							frame.setVisible(false);

							ClientPage c= new ClientPage();

							c.accountNumber=txtUsername.getText();
							c.method=method;
							c.server=server;
							c.setVisible(true);							
							return;
						}
						else {
							JOptionPane.showMessageDialog(null, "Account Number or password are invalid "+response);
							response="";
							dataStr="";
							return;
						}
					}
				}
				else {
					JOptionPane.showMessageDialog(null, "Please Select HTTP Method");
				}
			}
		});
		btnLogin.setBounds(160, 198, 392, 25);
		contentPane.add(btnLogin);
		
		JComboBox<Object> serverSelect = new JComboBox<Object>(servers);
		serverSelect.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				urlStr = String.valueOf(serverSelect.getSelectedItem());
				server = urlStr;
			}
		});
		serverSelect.setBounds(160, 29, 392, 22);
		contentPane.add(serverSelect);
		

		JComboBox<Object> methodSelect = new JComboBox<Object>(methods);
		methodSelect.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				method = String.valueOf(methodSelect.getSelectedItem());
			}
		});
		methodSelect.setBounds(160, 75, 392, 22);
		contentPane.add(methodSelect);
		
		lblSelectServer = new JLabel("Select Server");
		lblSelectServer.setForeground(Color.GREEN);
		lblSelectServer.setBounds(12, 32, 113, 16);
		contentPane.add(lblSelectServer);
		
		lblSelectMethod = new JLabel("Select Method");
		lblSelectMethod.setForeground(Color.GREEN);
		lblSelectMethod.setBounds(12, 78, 113, 16);
		contentPane.add(lblSelectMethod);
		
		lblEnterUsername = new JLabel("Enter Username");
		lblEnterUsername.setForeground(Color.GREEN);
		lblEnterUsername.setBounds(12, 116, 113, 16);
		contentPane.add(lblEnterUsername);
		
		lblEnterPassword = new JLabel("Enter Password");
		lblEnterPassword.setForeground(Color.GREEN);
		lblEnterPassword.setBounds(12, 152, 113, 16);
		contentPane.add(lblEnterPassword);
		
		txtPassword = new JPasswordField();
		txtPassword.setBounds(160, 149, 392, 22);
		contentPane.add(txtPassword);
		
	}
	
	
	public void addParameter(String ps, String vs) {
		if(ps.equals(null)|| vs.equals(null)|| ps.length()==0 || vs.length()==0) {
			return;
		}
			
		if(dataStr.length()>0) {
			dataStr+="&";
		}
		
		try {
			dataStr+= ps + "=" + URLEncoder.encode(vs,"ASCII");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public String sendGET() {
		String response="";
		accountNumber=txtUsername.getText();
		password=String.valueOf(txtPassword.getPassword());
		DataInputStream is;
		// urlStr="http://localhost:7777/WebApplication1/index.jsp?ID="+accountNumber+"&password="+password;
		urlStr = server + "?ID="+accountNumber+"&password="+password;
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
	
	public String sendPOST() {
		String response="";
		urlStr = server;
		accountNumber=txtUsername.getText();
		password=String.valueOf(txtPassword.getPassword());
		InputStream in;
		OutputStream os;
		addParameter("ID", accountNumber);
		addParameter("password", password);
		// urlStr="http://localhost:7777/WebApplication1/index.jsp";
		
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
