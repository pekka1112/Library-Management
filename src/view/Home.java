/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import dao.BookDAO;
import model.MyBook;

import java.awt.Font;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

import Service.AuthorService;
import Service.CategoryService;

public class Home extends javax.swing.JFrame {

	BookDAO bookDAO = new BookDAO();

	public Home() {
		initComponents();
		DefaultTableCellRenderer renderers_SearchSach = (DefaultTableCellRenderer) tableSearchSach.getTableHeader()
				.getDefaultRenderer();
		renderers_SearchSach.setHorizontalAlignment(0);
		renderers_SearchSach.setVerticalAlignment(0);
		tableSearchSach.getTableHeader().setFont(new Font("Times New Roman", Font.BOLD, 16));
		tableSearchSach.setRowHeight(30);

		TableColumnModel columnModel_SearchSach = tableSearchSach.getColumnModel();
		columnModel_SearchSach.getColumn(0).setPreferredWidth(50);
		columnModel_SearchSach.getColumn(1).setPreferredWidth(350);
		columnModel_SearchSach.getColumn(2).setPreferredWidth(70);
		columnModel_SearchSach.getColumn(3).setPreferredWidth(70);
		columnModel_SearchSach.getColumn(4).setPreferredWidth(200);
		columnModel_SearchSach.getColumn(5).setPreferredWidth(100);
		columnModel_SearchSach.getColumn(6).setPreferredWidth(100);
		columnModel_SearchSach.getColumn(7).setPreferredWidth(80);
		showAllBook();
	}

	@SuppressWarnings("unchecked")
	private void initComponents() {

		jPanel1 = new javax.swing.JPanel();
		jTabbedPane1 = new javax.swing.JTabbedPane();
		jPanel2 = new javax.swing.JPanel();
		textArea1 = new java.awt.TextArea();
		jPanel3 = new javax.swing.JPanel();
		jLabel3 = new javax.swing.JLabel();
		jLabel4 = new javax.swing.JLabel();
		jLabel5 = new javax.swing.JLabel();
		jLabel6 = new javax.swing.JLabel();
		jLabel7 = new javax.swing.JLabel();
		jLabel8 = new javax.swing.JLabel();
		jLabel9 = new javax.swing.JLabel();
		jLabel24 = new javax.swing.JLabel();
		jLabel25 = new javax.swing.JLabel();
		jLabel26 = new javax.swing.JLabel();
		jSeparator4 = new javax.swing.JSeparator();
		jLabel11 = new javax.swing.JLabel();
		jPanel6 = new javax.swing.JPanel();
		jtf_search = new javax.swing.JTextField();
		jLabel27 = new javax.swing.JLabel();
		jButton11 = new javax.swing.JButton();
		jComboBox3 = new javax.swing.JComboBox<>();
		jComboBox4 = new javax.swing.JComboBox<>();
		jLabel35 = new javax.swing.JLabel();
		jLabel28 = new javax.swing.JLabel();
		jScrollPane5 = new javax.swing.JScrollPane();
		tableSearchSach = new javax.swing.JTable();
		jPanel4 = new javax.swing.JPanel();
		jLabel13 = new javax.swing.JLabel();
		jLabel14 = new javax.swing.JLabel();
		jLabel15 = new javax.swing.JLabel();
		jLabel16 = new javax.swing.JLabel();
		jSeparator3 = new javax.swing.JSeparator();
		jLabel17 = new javax.swing.JLabel();
		jLabel18 = new javax.swing.JLabel();
		jLabel19 = new javax.swing.JLabel();
		jLabel20 = new javax.swing.JLabel();
		jLabel21 = new javax.swing.JLabel();
		jLabel22 = new javax.swing.JLabel();
		jLabel23 = new javax.swing.JLabel();
		jLabel1 = new javax.swing.JLabel();
		jLabel2 = new javax.swing.JLabel();
		jButton1 = new javax.swing.JButton();
		jSeparator1 = new javax.swing.JSeparator();
		jLabel10 = new javax.swing.JLabel();

		setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

		jPanel1.setBackground(new java.awt.Color(204, 204, 255));

		jTabbedPane1.setBackground(new java.awt.Color(204, 204, 255));
		jTabbedPane1.setTabPlacement(javax.swing.JTabbedPane.LEFT);
		jTabbedPane1.setFont(new java.awt.Font("Times New Roman", 1, 22)); 

		jPanel2.setBackground(new java.awt.Color(255, 255, 204));

		textArea1.setFont(new java.awt.Font("Times New Roman", 0, 21)); 
		textArea1.setForeground(new java.awt.Color(0, 51, 153));
		String library_introduction = "\n"
				+ " ** Thư viện của Trường Đại học Nông Lâm là nơi linh hoạt và đầy năng lượng, mang đến cho sinh viên " + "\n"
				+ "không chỉ là một nguồn tài nguyên tri thức đồ sộ mà còn là một không gian thú vị và đa dạng. Với cơ sở "+ "\n"
				+ "vật chất hiện đại và cập nhật, thư viện không chỉ là nơi lưu giữ những cuốn sách giáo trình quan trọng "+ "\n"
				+ "mà còn là trung tâm học thuật sôi động, hỗ trợ sinh viên trải nghiệm hành trình học tập của họ.\n"
				+ "\n"
				+ "\n"
				+ " ** Tại thư viện, sinh viên có thể truy cập vào một bảo tàng tri thức đa dạng, từ các tài liệu chuyên "+ "\n"
				+ "ngành về nông nghiệp đến những xu hướng nghiên cứu mới nhất. Các khu vực đọc sách thoáng đãng và các "+ "\n"
				+ "phòng học nhóm hiện đại tạo điều kiện tốt nhất cho việc nghiên cứu và thảo luận. Ngoài ra, thư viện "+ "\n"
				+ "còn cung cấp các dịch vụ trực tuyến, giúp sinh viên tiếp cận tài nguyên mọi nơi, mọi lúc.\n"
				+ "\n"
				+ "\n"
				+ " +++ Thư viện của Trường Đại học Nông Lâm không chỉ là nơi nâng cao kiến thức học thuật mà còn là điểm gặp "+ "\n"
				+ "gỡ và trao đổi ý kiến cho cộng đồng sinh viên. Với không khí tràn đầy sự sáng tạo và đồng lòng hướng "+ "\n"
				+ "tới sự phát triển, thư viện là nguồn động viên lớn, khuyến khích sinh viên khám phá và đạt được những "+ "\n"
				+ "thành công lớn trong hành trình học tập của họ.";
		textArea1.setText( library_introduction) ;

		javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
		jPanel2.setLayout(jPanel2Layout);
		jPanel2Layout.setHorizontalGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addComponent(textArea1, javax.swing.GroupLayout.Alignment.TRAILING,
						javax.swing.GroupLayout.DEFAULT_SIZE, 978, Short.MAX_VALUE));
		jPanel2Layout.setVerticalGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addComponent(textArea1, javax.swing.GroupLayout.Alignment.TRAILING,
						javax.swing.GroupLayout.DEFAULT_SIZE, 552, Short.MAX_VALUE));

		jTabbedPane1.addTab("TRANG CHỦ CHÍNH ",
				new javax.swing.ImageIcon(getClass().getResource("/Images/research.png")), jPanel2); 

		jtf_search.setFont(new java.awt.Font("Times New Roman", 0, 18));
		jtf_search.addKeyListener(new java.awt.event.KeyAdapter() {
			public void keyReleased(java.awt.event.KeyEvent evt) {
				jtf_searchKeyReleasedAction(evt);
			}
		});

		jLabel27.setFont(new java.awt.Font("Times New Roman", 1, 20)); 
		jLabel27.setForeground(new java.awt.Color(0, 0, 255));
		jLabel27.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/searching.png")));
		jLabel27.setText("Tìm kiếm sách:");

		jButton11.setBackground(new java.awt.Color(255, 204, 204));
		jButton11.setFont(new java.awt.Font("Times New Roman", 1, 18)); 
		jButton11.setText("Tìm kiếm");
		jButton11.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				findBookAction(evt);
			}
		});
 
		jComboBox3.setFont(new java.awt.Font("Times New Roman", 0, 18)); 
		CategoryService cs = new CategoryService();
		List<String> categoryNames = cs.getCategoryNames();
		categoryNames.add(0, "Chọn tên Thể Loại");
		String[] categoryNameModel = categoryNames.toArray(new String[0]);
		jComboBox3.setModel(new javax.swing.DefaultComboBoxModel<>(categoryNameModel)); // lấy data từ db và cho ra làm tên thể loại
		jComboBox3.addItemListener(new java.awt.event.ItemListener() {
			public void itemStateChanged(java.awt.event.ItemEvent evt) {
				filterByCategoryAction(evt);
			}
		});

		jComboBox4.setFont(new java.awt.Font("Times New Roman", 0, 18)); 
		AuthorService as = new AuthorService();
		List<String> authorName = as.getAuthorsName();
		authorName.add(0, "Chọn tên Tác Giả");
		String[] authorNameModel = authorName.toArray(new String[0]);
		jComboBox4.setModel(new javax.swing.DefaultComboBoxModel<>(authorNameModel));
		jComboBox4.addItemListener(new java.awt.event.ItemListener() {
			public void itemStateChanged(java.awt.event.ItemEvent evt) {
				filterByAuthorNameAction(evt);
			}
		});

		jLabel35.setFont(new java.awt.Font("Times New Roman", 0, 20)); 
		jLabel35.setForeground(new java.awt.Color(0, 0, 102));
		jLabel35.setText("Lọc theo tên tác giả:");

		jLabel28.setFont(new java.awt.Font("Times New Roman", 0, 20)); 
		jLabel28.setForeground(new java.awt.Color(0, 0, 102));
		jLabel28.setText("Lọc theo thể loại sách :");

		tableSearchSach.setFont(new java.awt.Font("Times New Roman", 0, 20)); 
		tableSearchSach.setModel(new javax.swing.table.DefaultTableModel(new Object[][] {},
				new String[] { "ID", "Tên sách", "Tác giả","Thể loại","Mô tả","Ngày xuất bản", "Ngày nhập" ,"Số lượng" }));
		jScrollPane5.setViewportView(tableSearchSach);
		jScrollPane5.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);


		javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
		jPanel6.setLayout(jPanel6Layout);
		jPanel6Layout.setHorizontalGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(jPanel6Layout.createSequentialGroup().addGap(44, 44, 44)
						.addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
								.addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 913,
										javax.swing.GroupLayout.PREFERRED_SIZE)
								.addGroup(jPanel6Layout.createSequentialGroup()
										.addGroup(jPanel6Layout
												.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
												.addComponent(jLabel35)
												.addComponent(jLabel28, javax.swing.GroupLayout.DEFAULT_SIZE, 184,
														Short.MAX_VALUE)
												.addComponent(
														jLabel27, javax.swing.GroupLayout.DEFAULT_SIZE,
														javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
										.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addGroup(jPanel6Layout
												.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
												.addGroup(jPanel6Layout.createSequentialGroup()
														.addComponent(jtf_search,
																javax.swing.GroupLayout.PREFERRED_SIZE, 353,
																javax.swing.GroupLayout.PREFERRED_SIZE)
														.addGap(18, 18, 18).addComponent(jButton11,
																javax.swing.GroupLayout.PREFERRED_SIZE, 134,
																javax.swing.GroupLayout.PREFERRED_SIZE))
												.addComponent(jComboBox4, javax.swing.GroupLayout.PREFERRED_SIZE, 197,
														javax.swing.GroupLayout.PREFERRED_SIZE)
												.addComponent(jComboBox3, javax.swing.GroupLayout.PREFERRED_SIZE, 247,
														javax.swing.GroupLayout.PREFERRED_SIZE))))
						.addContainerGap(19, Short.MAX_VALUE)));
		jPanel6Layout.setVerticalGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(jPanel6Layout.createSequentialGroup().addGap(58, 58, 58)
						.addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
								.addComponent(jButton11, javax.swing.GroupLayout.DEFAULT_SIZE, 34, Short.MAX_VALUE)
								.addComponent(jtf_search)
								.addComponent(jLabel27, javax.swing.GroupLayout.Alignment.TRAILING))
						.addGap(36, 36, 36)
						.addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
								.addComponent(jLabel28).addComponent(jComboBox3, javax.swing.GroupLayout.PREFERRED_SIZE,
										36, javax.swing.GroupLayout.PREFERRED_SIZE))
						.addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
								.addGroup(
										jPanel6Layout.createSequentialGroup().addGap(27, 27, 27).addComponent(jLabel35))
								.addGroup(jPanel6Layout.createSequentialGroup().addGap(18, 18, 18)
										.addComponent(jComboBox4)))
						.addGap(18, 18, 18)
						.addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 308, Short.MAX_VALUE)
						.addContainerGap()));

		jTabbedPane1.addTab("TRA CỨU SÁCH",
				new javax.swing.ImageIcon(getClass().getResource("/Images/statistics.png")), jPanel6); // NOI18N

		jLabel1.setFont(new java.awt.Font("Times New Roman", 1, 26)); // NOI18N
		jLabel1.setText("       QUẢN LÝ THƯ VIỆN");

		jLabel2.setFont(new java.awt.Font("Times New Roman", 1, 26)); // NOI18N
		jLabel2.setText("TRƯỜNG ĐẠI HỌC NÔNG LÂM TP.HCM - NLU ");

		jButton1.setFont(new java.awt.Font("Times New Roman", 0, 20)); // NOI18N
		jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/login.png"))); // NOI18N
		jButton1.setText("Đăng nhập");
		jButton1.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				loginAction(evt);
			}
		});

		jLabel10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/icon1.jpg"))); 

		javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
		jPanel1.setLayout(jPanel1Layout);
		jPanel1Layout.setHorizontalGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(jPanel1Layout.createSequentialGroup().addGroup(jPanel1Layout
						.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
						.addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 1353,
								javax.swing.GroupLayout.PREFERRED_SIZE)
						.addGroup(jPanel1Layout.createSequentialGroup()
								.addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 108,
										javax.swing.GroupLayout.PREFERRED_SIZE)
								.addGap(18, 18, 18)
								.addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
										.addGroup(javax.swing.GroupLayout.Alignment.TRAILING,
												jPanel1Layout.createSequentialGroup().addComponent(jLabel2).addGap(248,
														248, 248))
										.addGroup(javax.swing.GroupLayout.Alignment.TRAILING,
												jPanel1Layout.createSequentialGroup().addComponent(jLabel1)
														.addGap(302, 302, 302).addComponent(jButton1,
																javax.swing.GroupLayout.PREFERRED_SIZE, 167,
																javax.swing.GroupLayout.PREFERRED_SIZE)))))
						.addGap(0, 8, Short.MAX_VALUE))
				.addGroup(jPanel1Layout.createSequentialGroup().addContainerGap().addComponent(jSeparator1)
						.addContainerGap()));
		jPanel1Layout
				.setVerticalGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
								.addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
										.addGroup(jPanel1Layout.createSequentialGroup().addGap(37, 37, 37)
												.addGroup(jPanel1Layout
														.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
														.addComponent(jButton1).addComponent(jLabel1))
												.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
												.addComponent(jLabel2))
										.addGroup(jPanel1Layout.createSequentialGroup().addContainerGap().addComponent(
												jLabel10, javax.swing.GroupLayout.DEFAULT_SIZE,
												javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
								.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED,
										javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10,
										javax.swing.GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
								.addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 557,
										javax.swing.GroupLayout.PREFERRED_SIZE)));

		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		layout.setHorizontalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(layout.createSequentialGroup()
						.addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE,
								javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
						.addGap(0, 0, Short.MAX_VALUE)));
		layout.setVerticalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addComponent(
				jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE,
				javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE));

		pack();
		setLocationRelativeTo(null);
	}

	private void loginAction(java.awt.event.ActionEvent evt) {
		new Login().setVisible(true);
		this.setVisible(false);
	}
	private void filterByAuthorNameAction(java.awt.event.ItemEvent evt) {  
		String authorName = "4";
		int index = jComboBox4.getSelectedIndex();
		if (index == 0) return;
		if ( index > 0 && index < 10) { 
			authorName += "0";
			authorName += Integer.toString(index);
		} else { 
			authorName += Integer.toString(index);
		}
		System.out.println(authorName);
		List<MyBook> bookByAuthorName = bookDAO.getBookListByAuthorName(authorName);
		DefaultTableModel curTableModel = (DefaultTableModel) tableSearchSach.getModel();
		curTableModel.setRowCount(0);
		for (MyBook b : bookByAuthorName) {
			curTableModel.addRow(new Object[] {b.getId(), b.getTitle(), b.getAuthor_id(), b.getCategory_id(), b.getDescription(), b.getPublished_date(),b.getImport_date(),b.getStock_quantity()});
		}
	}
	private void showAllBook() { 
		List<MyBook> allBook = bookDAO.getBookList();
		DefaultTableModel curTableModel = (DefaultTableModel) tableSearchSach.getModel();
		curTableModel.setRowCount(0);
		for (MyBook b : allBook) {
			curTableModel.addRow(new Object[] {b.getId(), b.getTitle(), b.getAuthor_id(), b.getCategory_id(), b.getDescription(), b.getPublished_date(),b.getImport_date(),b.getStock_quantity()});
		}
	}
	private void filterByCategoryAction(java.awt.event.ItemEvent evt) {  
		String category = "3";
		int index = jComboBox3.getSelectedIndex();
		
		if (index == 0) return;
		if ( index > 0 && index < 10) { 
			category += "0";
			category += Integer.toString(index);
		} else { 
			category += Integer.toString(index);
		}
		System.out.println(category);
		List<MyBook> bookByAuthorName = bookDAO.getBookListByCategory(category);
		DefaultTableModel curTableModel = (DefaultTableModel) tableSearchSach.getModel();
		curTableModel.setRowCount(0);
		for (MyBook b : bookByAuthorName) {
			curTableModel.addRow(new Object[] {b.getId(), b.getTitle(), b.getAuthor_id(), b.getCategory_id(), b.getDescription(), b.getPublished_date(),b.getImport_date(),b.getStock_quantity()});
		}
	}
	private void findBookAction(java.awt.event.ActionEvent evt) {
		String keyword = jtf_search.getText();
		List<MyBook> allBook = bookDAO.getBookList();
		List<MyBook> result = new ArrayList<>();
		DefaultTableModel curTableModel = (DefaultTableModel) tableSearchSach.getModel();
		curTableModel.setRowCount(0);
		int i = 0;
		for (MyBook b : allBook) {
			if (b.getTitle().toLowerCase().contains(keyword.toLowerCase()))
				result.add(b);
		}
		for (MyBook b : result) {
			curTableModel.addRow(new Object[] {b.getId(), b.getTitle(), b.getAuthor_id(), b.getCategory_id(), b.getDescription(), b.getPublished_date(),b.getImport_date(),b.getStock_quantity()});
		}
	}
	private void jtf_searchKeyReleasedAction(java.awt.event.KeyEvent evt) {
		String keyword = jtf_search.getText();
		List<MyBook> allBook = bookDAO.getBookList();
		List<MyBook> result = new ArrayList<>();
		DefaultTableModel curTableModel = (DefaultTableModel) tableSearchSach.getModel();
		curTableModel.setRowCount(0);
		int i = 0;
		for (MyBook b : allBook) {
			if (b.getTitle().toLowerCase().contains(keyword.toLowerCase()))
				result.add(b);
		}
		for (MyBook b : result) {
			curTableModel.addRow(new Object[] {b.getId(), b.getTitle(), b.getAuthor_id(), b.getCategory_id(), b.getDescription(), b.getPublished_date(),b.getImport_date(),b.getStock_quantity()});
		}
	}// GEN-LAST:event_textboxsearchKeyReleased

	/**
	 * @param args the command line arguments
	 */
	public static void main(String args[]) {
		try {
			for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
				if ("Nimbus".equals(info.getName())) {
					javax.swing.UIManager.setLookAndFeel(info.getClassName());
					break;
				}
			}
		} catch (ClassNotFoundException ex) {
			java.util.logging.Logger.getLogger(Home.class.getName()).log(java.util.logging.Level.SEVERE, null,
					ex);
		} catch (InstantiationException ex) {
			java.util.logging.Logger.getLogger(Home.class.getName()).log(java.util.logging.Level.SEVERE, null,
					ex);
		} catch (IllegalAccessException ex) {
			java.util.logging.Logger.getLogger(Home.class.getName()).log(java.util.logging.Level.SEVERE, null,
					ex);
		} catch (javax.swing.UnsupportedLookAndFeelException ex) {
			java.util.logging.Logger.getLogger(Home.class.getName()).log(java.util.logging.Level.SEVERE, null,
					ex);
		}

		java.awt.EventQueue.invokeLater(new Runnable() {
			public void run() {
				new Home().setVisible(true);
			}
		});
	}

	private javax.swing.JButton jButton1;
	private javax.swing.JButton jButton11;
	private javax.swing.JComboBox<String> jComboBox3;
	private javax.swing.JComboBox<String> jComboBox4;
	private javax.swing.JLabel jLabel1;
	private javax.swing.JLabel jLabel10;
	private javax.swing.JLabel jLabel11;
	private javax.swing.JLabel jLabel13;
	private javax.swing.JLabel jLabel14;
	private javax.swing.JLabel jLabel15;
	private javax.swing.JLabel jLabel16;
	private javax.swing.JLabel jLabel17;
	private javax.swing.JLabel jLabel18;
	private javax.swing.JLabel jLabel19;
	private javax.swing.JLabel jLabel2;
	private javax.swing.JLabel jLabel20;
	private javax.swing.JLabel jLabel21;
	private javax.swing.JLabel jLabel22;
	private javax.swing.JLabel jLabel23;
	private javax.swing.JLabel jLabel24;
	private javax.swing.JLabel jLabel25;
	private javax.swing.JLabel jLabel26;
	private javax.swing.JLabel jLabel27;
	private javax.swing.JLabel jLabel28;
	private javax.swing.JLabel jLabel3;
	private javax.swing.JLabel jLabel35;
	private javax.swing.JLabel jLabel4;
	private javax.swing.JLabel jLabel5;
	private javax.swing.JLabel jLabel6;
	private javax.swing.JLabel jLabel7;
	private javax.swing.JLabel jLabel8;
	private javax.swing.JLabel jLabel9;
	private javax.swing.JPanel jPanel1;
	private javax.swing.JPanel jPanel2;
	private javax.swing.JPanel jPanel3;
	private javax.swing.JPanel jPanel4;
	private javax.swing.JPanel jPanel6;
	private javax.swing.JScrollPane jScrollPane5;
	private javax.swing.JSeparator jSeparator1;
	private javax.swing.JSeparator jSeparator3;
	private javax.swing.JSeparator jSeparator4;
	private javax.swing.JTabbedPane jTabbedPane1;
	private javax.swing.JTable tableSearchSach;
	private java.awt.TextArea textArea1;
	private javax.swing.JTextField jtf_search;
	// End of variables declaration//GEN-END:variables
}
