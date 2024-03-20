package view;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.ImageIcon;
import java.awt.Color;
import java.awt.Font;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JPasswordField;
import javax.swing.UIManager;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.awt.event.ActionEvent;
import javax.swing.JSeparator;
import javax.swing.border.LineBorder;

import dao.*;
import model.Account;
import model.UserTB;

import javax.swing.JComboBox;

public class Login extends JFrame {

	private static Login instance;
	public static boolean isLogin;
	private JPanel contentPane;
	private JTextField jtf_username;
	private JPasswordField jtf_password;
	public JButton userLoginBtn, exitBtn;
	private JComboBox jcb_role;
	private UserTBDAO userTbDAO ;

	/**
	 * Create the frame.
	 */
	public Login() {
		userTbDAO = new UserTBDAO();
		this.setTitle("Đăng Nhập Tài Khoản");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 545, 300);
		this.setLocationRelativeTo(null);

		// design frame
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);

		JPanel jpImage = new JPanel();
		jpImage.setBounds(0, 0, 249, 261);
		contentPane.add(jpImage);
		jpImage.setLayout(null);

		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setIcon(new ImageIcon(Login.class.getResource("/Images/bannerLogin.png")));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(0, 0, 251, 261);
		jpImage.add(lblNewLabel);

		JPanel jpLogin = new JPanel();
		jpLogin.setBackground(new Color(255, 255, 255));
		jpLogin.setBounds(248, 0, 280, 261);
		contentPane.add(jpLogin);
		jpLogin.setLayout(null);

		JPanel panel = new JPanel();
		panel.setBorder(new LineBorder(new Color(128, 64, 0), 3));
		panel.setBounds(10, 11, 260, 239);
		jpLogin.add(panel);
		panel.setLayout(null);

		JLabel lblNewLabel_1 = new JLabel("ĐĂNG NHẬP");
		lblNewLabel_1.setVerticalAlignment(SwingConstants.TOP);
		lblNewLabel_1.setForeground(new Color(64, 0, 0));
		lblNewLabel_1.setFont(new Font("SansSerif", Font.BOLD, 25));
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1.setBounds(0, 11, 260, 39);
		panel.add(lblNewLabel_1);

		jtf_username = new JTextField();
		jtf_username.setFont(new Font("Tahoma", Font.BOLD, 11));
		jtf_username.setColumns(10);
		jtf_username.setBounds(10, 73, 240, 23);
		jtf_username.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				txt_tenDangNhapActionPerformed(evt);
			}

			private void txt_tenDangNhapActionPerformed(ActionEvent evt) {
				// TODO Auto-generated method stub

			}
		});
		jtf_username.addKeyListener(new java.awt.event.KeyAdapter() {
			public void keyPressed(java.awt.event.KeyEvent evt) {
				txt_tenDangNhapKeyPressed(evt);
			}

			private void txt_tenDangNhapKeyPressed(KeyEvent evt) {
				// TODO Auto-generated method stub
				if (evt.getKeyCode() == 10) {
					jtf_password.requestFocus();
				}
			}
		});
		panel.add(jtf_username);

		exitBtn = new JButton("Thoát");
		exitBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				new Home().setVisible(true);
				setVisible(false);
			}
		});
		exitBtn.setBackground(new Color(0, 0, 255));
		exitBtn.setBounds(139, 205, 111, 23);
		panel.add(exitBtn);

		userLoginBtn = new JButton("Đăng Nhập");
		userLoginBtn.setBackground(new Color(0, 255, 128));
		userLoginBtn.setBounds(10, 205, 111, 23);
		userLoginBtn.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				btn_DangNhapActionPerformed(evt);
			}
		});
		panel.add(userLoginBtn);

		jtf_password = new JPasswordField();
		jtf_password.setFont(new Font("Tahoma", Font.BOLD, 11));
		jtf_password.setBounds(10, 132, 240, 23);
		panel.add(jtf_password);

		JLabel lblNewLabel_2_1 = new JLabel("Tên Đăng Nhập :");
		lblNewLabel_2_1.setForeground(new Color(0, 64, 64));
		lblNewLabel_2_1.setFont(new Font("SansSerif", Font.BOLD, 13));
		lblNewLabel_2_1.setBounds(10, 48, 240, 23);
		panel.add(lblNewLabel_2_1);

		JLabel lblNewLabel_2_1_1 = new JLabel("Mật Khẩu :");
		lblNewLabel_2_1_1.setForeground(new Color(0, 64, 64));
		lblNewLabel_2_1_1.setFont(new Font("SansSerif", Font.BOLD, 13));
		lblNewLabel_2_1_1.setBounds(10, 107, 240, 23);
		panel.add(lblNewLabel_2_1_1);

		JLabel lblNewLabel_2_1_1_1 = new JLabel("Vai Trò : ");
		lblNewLabel_2_1_1_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_2_1_1_1.setForeground(new Color(0, 64, 64));
		lblNewLabel_2_1_1_1.setFont(new Font("SansSerif", Font.BOLD, 13));
		lblNewLabel_2_1_1_1.setBounds(10, 166, 81, 23);
		panel.add(lblNewLabel_2_1_1_1);

		String role[] = { "User", "Admin" };
		jcb_role = new JComboBox(role);
		jcb_role.setFont(new Font("SansSerif", Font.ITALIC, 12));
		jcb_role.setBounds(101, 166, 149, 22);
		panel.add(jcb_role);

	}

	protected void btn_DangNhapActionPerformed(ActionEvent evt) {
		if (jtf_username.getText().equals("") || jtf_password.getPassword().equals("")) {
			JOptionPane.showMessageDialog(null, "Bạn chưa nhập đủ thông tin!");
		} else {
			if (jcb_role.getSelectedItem().equals("User")) {
				Account acc = new Account();
				acc.setUsername(jtf_username.getText().trim());
				acc.setPassword(String.valueOf(jtf_password.getPassword()).trim());
				try {
					if (checkAccount(acc)) {
						JOptionPane.showMessageDialog(rootPane, "Bạn đã đăng nhập thành công!");
						String idUser = jtf_username.getText();
						UserTbHome userHome = new UserTbHome(idUser, 0);
						userHome.setVisible(true);
						dispose();
					} else {
						System.out.println(acc.getUsername());
						System.out.println(acc.getPassword());
						JOptionPane.showMessageDialog(rootPane, "Đăng nhập thất bại! Vui lòng kiểm tra lại!", "ERROR", JOptionPane.ERROR_MESSAGE);
						jtf_password.setText("");
						jtf_username.setText("");
						jtf_username.requestFocus();
					}
				} catch (Exception ex) {
				}
			} else {
				Account admin = new Account();
				admin.setUsername(jtf_username.getText().trim());
				admin.setPassword(String.valueOf(jtf_password.getPassword()).trim());
				try {
					if (checkAdmin(admin)) {
						JOptionPane.showMessageDialog(rootPane, "Bạn đã đăng nhập thành công!");
						new AdminHome().setVisible(true);
						this.setVisible(false);
					} else {
						JOptionPane.showMessageDialog(rootPane, "Đăng nhập thất bại! Vui lòng kiểm tra lại!", "ERROR", JOptionPane.ERROR_MESSAGE);
						jtf_username.requestFocus();
						jtf_password.setText("");
						jtf_username.setText("");
					}
				} catch (Exception ex) {
				}
			}
		}
	}

	private boolean checkAdmin(Account admin) {
		// TODO Auto-generated method stub
		List<Account> list_admin = userTbDAO.getAdminList();
		System.out.println(list_admin.size());
		for (Account account : list_admin) {
			System.out.println(admin.getUsername());
			System.out.println(admin.getPassword());
			System.out.println(account.getUsername());
			System.out.println(account.getPassword());
			if (admin.equals(account)) {
				return true;
			}
		}
		return false;
	}

	private boolean checkAccount(Account acc) {
		// TODO Auto-generated method stub
		List<Account> list_account = userTbDAO.getAccountList();
		for (Account account : list_account) {
			if (acc.equals(account)) {
				return true;
			}
		}
		return false;
	}

	public boolean checkValidInput() {
		// TODO Auto-generated method stub
		if (jtf_username.getText().equals("")) {
			String errorMessage = "Ban chua nhap UserName!";
			JOptionPane.showMessageDialog(this, errorMessage, "Lỗi", JOptionPane.ERROR_MESSAGE);
			return false;
		}
		if (jtf_password.getText().equals("")) {
			String errorMessage = "Ban chua nhap Password!";
			JOptionPane.showMessageDialog(this, errorMessage, "Lỗi", JOptionPane.ERROR_MESSAGE);
			return false;
		}
		return true;
	}

	public boolean login() {
		String tenDangNhap = jtf_username.getText();
		String matKhau = jtf_password.getText();
		String errorMessage = "Username hoac Password khong dung !!!";
		JOptionPane.showMessageDialog(this, errorMessage, "Lỗi", JOptionPane.ERROR_MESSAGE);
		return false;
	}

	public static Login getInstance() {
		if (instance == null) {
			synchronized (Login.class) {
				if (instance == null) {
					instance = new Login();
				}
			}
		}
		return instance;
	}

	public static void main(String[] args) {
		new Login().setVisible(true);
	}
}
