package view;

import Service.UserTBService;
import dao.BookDAO;
import dao.UserTBDAO;
import model.MyBook;
import Service.AuthorService;
import Service.BookService;
import Service.CategoryService;

import java.awt.Color;
import java.awt.Font;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.RowFilter;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableRowSorter;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;

public class UserTbHome extends javax.swing.JFrame {

	DefaultTableModel dtm_book;
	DefaultTableModel dtm_bookLoan;
	
	public String sachDaChon;
	boolean isLoaded = false;
	public String maTaiKhoan;
	int tongMuon = 0;
	int gioiHan;
	
	private BookDAO bookDAO; 
	private BookService bookService; 
	private UserTBService userTBService; 
	private AuthorService authorService;
	private CategoryService categoryService;

	public UserTbHome(String matk, int soluongmuon) {
		maTaiKhoan = matk;
		bookDAO = new BookDAO();
		bookService = new BookService();
		userTBService = new UserTBService();
		authorService = new AuthorService();
		categoryService = new CategoryService();
		initComponents();

		initTable_TABTT();
		initAuthorNameForCCB_TABTT();
		initCategoryNameForCCB_TABTT();
		initTable_TABDMS();
		
		gioiHan = 3 - soluongmuon;
		isLoaded = true;
		
		lblGioiHan.setText(String.valueOf(gioiHan)); // Set giới hạn mượn
		setTongMuon();
	}

	private void initComponents() {

		jTabbedPane1 = new javax.swing.JTabbedPane();
		jPanel3 = new javax.swing.JPanel();
		jPanel4 = new javax.swing.JPanel();
		jtf_findBook = new javax.swing.JTextField();
		jcb_categoryName = new javax.swing.JComboBox<>();
		jcb_authorName = new javax.swing.JComboBox<>();
		btnReset = new javax.swing.JButton();
		jLabel5 = new javax.swing.JLabel();
		jScrollPane2 = new javax.swing.JScrollPane();
		table_allBook = new javax.swing.JTable();
		jSeparator2 = new javax.swing.JSeparator();
		jPanel2 = new javax.swing.JPanel();
		btn_detailBook = new javax.swing.JButton();
		jPanel1 = new javax.swing.JPanel();
		jl_title_TABDSM = new javax.swing.JLabel();
		jSeparator1 = new javax.swing.JSeparator();
		btnXoaMuon = new javax.swing.JButton();
		jLabel4 = new javax.swing.JLabel();
		lblTongMuon = new javax.swing.JLabel();
		jScrollPane1 = new javax.swing.JScrollPane();
		table_TABDSM = new javax.swing.JTable();
		lblGioiHan = new javax.swing.JLabel();
		lblGioiHan1 = new javax.swing.JLabel();
		jLabel1 = new javax.swing.JLabel();
		jLabel6 = new javax.swing.JLabel();
		cbbHenTra = new javax.swing.JComboBox<>();
		btnXacNhan = new javax.swing.JButton();
		jLabel36 = new javax.swing.JLabel();
		btn_logout = new javax.swing.JButton();
		jLabel8 = new javax.swing.JLabel();
		jLabel3 = new javax.swing.JLabel();

		setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
		setTitle("USER_HOME PAGE --- ID USER : " + maTaiKhoan);
		setBackground(new java.awt.Color(255, 204, 204));

		jTabbedPane1.setBackground(new java.awt.Color(204, 204, 255));
		jTabbedPane1.setTabPlacement(javax.swing.JTabbedPane.LEFT);
		jTabbedPane1.setFont(new java.awt.Font("Times New Roman", 1, 22)); // NOI18N

		jPanel3.setBackground(new java.awt.Color(255, 255, 204));

		jPanel4.setBackground(new java.awt.Color(255, 255, 204));
		jPanel4.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
		jPanel4.setDoubleBuffered(false);

		jtf_findBook.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
		jtf_findBook.addKeyListener(new java.awt.event.KeyAdapter() {
			public void keyReleased(java.awt.event.KeyEvent evt) {
				findBookKeyReleased(evt);
			}
		});

		jcb_categoryName.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
		jcb_categoryName.addItemListener(new java.awt.event.ItemListener() {
			public void itemStateChanged(java.awt.event.ItemEvent evt) {
				cbbDanhMucItemStateChanged(evt);
			}
		});

		jcb_authorName.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
		jcb_authorName.addItemListener(new java.awt.event.ItemListener() {
			public void itemStateChanged(java.awt.event.ItemEvent evt) {
				cbbDanhMucItemStateChanged(evt);
			}
		});

		btnReset.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
		btnReset.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/eraser.png"))); // NOI18N
		btnReset.setText("Làm mới");
		btnReset.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				btnResetActionPerformed(evt);
			}
		});

		jLabel5.setFont(new java.awt.Font("Times New Roman", 1, 20)); // NOI18N
		jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
		jLabel5.setText("Tìm kiếm");

		javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
		jPanel4.setLayout(jPanel4Layout);
		jPanel4Layout.setHorizontalGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(jPanel4Layout.createSequentialGroup()
						.addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 108,
								javax.swing.GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
						.addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
								.addGroup(jPanel4Layout.createSequentialGroup()
										.addComponent(jcb_categoryName, 0, javax.swing.GroupLayout.DEFAULT_SIZE,
												Short.MAX_VALUE)
										.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
										.addComponent(jcb_authorName, javax.swing.GroupLayout.PREFERRED_SIZE, 215,
												javax.swing.GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
										.addComponent(btnReset, javax.swing.GroupLayout.PREFERRED_SIZE, 147,
												javax.swing.GroupLayout.PREFERRED_SIZE))
								.addComponent(jtf_findBook, javax.swing.GroupLayout.PREFERRED_SIZE, 692,
										javax.swing.GroupLayout.PREFERRED_SIZE))
						.addContainerGap(30, Short.MAX_VALUE)));
		jPanel4Layout.setVerticalGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(jPanel4Layout.createSequentialGroup().addGap(9, 9, 9)
						.addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
								.addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, 50, Short.MAX_VALUE)
								.addComponent(jtf_findBook, javax.swing.GroupLayout.PREFERRED_SIZE, 38,
										javax.swing.GroupLayout.PREFERRED_SIZE))
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
						.addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
								.addComponent(jcb_categoryName, javax.swing.GroupLayout.PREFERRED_SIZE, 39,
										javax.swing.GroupLayout.PREFERRED_SIZE)
								.addComponent(jcb_authorName, javax.swing.GroupLayout.PREFERRED_SIZE, 39,
										javax.swing.GroupLayout.PREFERRED_SIZE)
								.addComponent(btnReset, javax.swing.GroupLayout.PREFERRED_SIZE, 39,
										javax.swing.GroupLayout.PREFERRED_SIZE))));

		table_allBook.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
		table_allBook
				.setModel(new javax.swing.table.DefaultTableModel(new Object[][] { {}, {}, {}, {} }, new String[] {

				}));
		jScrollPane2.setViewportView(table_allBook);

		jPanel2.setBackground(new java.awt.Color(255, 255, 204));

		btn_detailBook.setFont(new java.awt.Font("Times New Roman", 1, 20)); // NOI18N
		btn_detailBook.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/book.png"))); // NOI18N
		btn_detailBook.setText("Xem chi tiết sách");
		btn_detailBook.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				showDetailBookActionPerformed(evt);
			}
		});

		javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
		jPanel2.setLayout(jPanel2Layout);
		jPanel2Layout.setHorizontalGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
						.addContainerGap(128, Short.MAX_VALUE).addComponent(btn_detailBook).addContainerGap()));
		jPanel2Layout.setVerticalGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(jPanel2Layout.createSequentialGroup().addContainerGap().addComponent(btn_detailBook)
						.addContainerGap(16, Short.MAX_VALUE)));

		javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
		jPanel3.setLayout(jPanel3Layout);
		jPanel3Layout.setHorizontalGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(jPanel3Layout.createSequentialGroup().addContainerGap().addGroup(jPanel3Layout
						.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
						.addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING)
						.addComponent(jSeparator2)
						.addGroup(javax.swing.GroupLayout.Alignment.TRAILING,
								jPanel3Layout.createSequentialGroup().addGap(0, 0, Short.MAX_VALUE).addComponent(
										jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
						.addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE,
								javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
						.addContainerGap()));
		jPanel3Layout.setVerticalGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(jPanel3Layout.createSequentialGroup().addGap(7, 7, 7)
						.addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE,
								javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
						.addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 2,
								javax.swing.GroupLayout.PREFERRED_SIZE)
						.addGap(23, 23, 23)
						.addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 245, Short.MAX_VALUE)
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED).addComponent(jPanel2,
								javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE,
								javax.swing.GroupLayout.PREFERRED_SIZE)));

		jTabbedPane1.addTab("TRA CỨU                   ",
				new javax.swing.ImageIcon(getClass().getResource("/Images/research.png")), jPanel3); // NOI18N

		jPanel1.setBackground(new java.awt.Color(255, 255, 204));

		jl_title_TABDSM.setBackground(new java.awt.Color(255, 255, 204));
		jl_title_TABDSM.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
		jl_title_TABDSM.setForeground(new java.awt.Color(0, 0, 102));
		jl_title_TABDSM.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/tutorial (1).png"))); // NOI18N
		jl_title_TABDSM.setText("THÔNG TIN SÁCH ĐƯỢC MƯỢN");

		btnXoaMuon.setBackground(new java.awt.Color(255, 204, 204));
		btnXoaMuon.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
		btnXoaMuon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/xoa.png"))); // NOI18N
		btnXoaMuon.setText("Xóa");
		btnXoaMuon.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				btnXoaMuonActionPerformed(evt);
			}
		});

		jLabel4.setFont(new java.awt.Font("Times New Roman", 1, 20)); // NOI18N
		jLabel4.setText("Tổng lượt mượn:");

		lblTongMuon.setFont(new java.awt.Font("Times New Roman", 0, 24)); // NOI18N
		lblTongMuon.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);

		table_TABDSM.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
		table_TABDSM.setModel(new javax.swing.table.DefaultTableModel(new Object[][] { {}, {}, {}, {} }, new String[] {

		}));
		jScrollPane1.setViewportView(table_TABDSM);

		lblGioiHan.setFont(new java.awt.Font("Times New Roman", 0, 24)); // NOI18N
		lblGioiHan.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);

		lblGioiHan1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
		lblGioiHan1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
		lblGioiHan1.setText("/");

		jLabel1.setFont(new java.awt.Font("Times New Roman", 2, 14)); // NOI18N
		jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
		jLabel1.setText("(Đọc kỹ danh sách trước khi gửi phiếu mượn)");

		jLabel6.setFont(new java.awt.Font("Times New Roman", 1, 20)); // NOI18N
		jLabel6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/schedule.png"))); // NOI18N
		jLabel6.setText("Chọn ngày hẹn trả:");

		cbbHenTra.setFont(new java.awt.Font("Times New Roman", 0, 20)); // NOI18N
		cbbHenTra.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "7", "14", "20", "30", "45", "90" }));
		cbbHenTra.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				cbbHenTraActionPerformed(evt);
			}
		});

		btnXacNhan.setBackground(new java.awt.Color(255, 204, 204));
		btnXacNhan.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
		btnXacNhan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/book.png"))); // NOI18N
		btnXacNhan.setText("Xác nhận");
		btnXacNhan.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				btnXacNhanActionPerformed(evt);
			}
		});

		javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
		jPanel1.setLayout(jPanel1Layout);
		jPanel1Layout.setHorizontalGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(jPanel1Layout.createSequentialGroup().addGroup(jPanel1Layout
						.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(jPanel1Layout.createSequentialGroup().addContainerGap().addGroup(jPanel1Layout
								.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
								.addComponent(jSeparator1)
								.addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 834, Short.MAX_VALUE)
								.addComponent(jl_title_TABDSM, javax.swing.GroupLayout.DEFAULT_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addGroup(jPanel1Layout.createSequentialGroup().addGap(282, 282, 282).addComponent(
										jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 552, Short.MAX_VALUE))
								.addGroup(jPanel1Layout.createSequentialGroup().addGap(16, 16, 16).addComponent(jLabel6)
										.addGap(15, 15, 15)
										.addComponent(cbbHenTra, javax.swing.GroupLayout.PREFERRED_SIZE, 85,
												javax.swing.GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED,
												javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
										.addComponent(btnXacNhan, javax.swing.GroupLayout.PREFERRED_SIZE, 167,
												javax.swing.GroupLayout.PREFERRED_SIZE))))
						.addGroup(
								jPanel1Layout.createSequentialGroup().addGap(19, 19, 19).addComponent(jLabel4)
										.addGap(18, 18, 18)
										.addComponent(lblTongMuon, javax.swing.GroupLayout.PREFERRED_SIZE, 35,
												javax.swing.GroupLayout.PREFERRED_SIZE)
										.addGap(2, 2, 2)
										.addComponent(lblGioiHan1, javax.swing.GroupLayout.PREFERRED_SIZE, 13,
												javax.swing.GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addComponent(lblGioiHan, javax.swing.GroupLayout.DEFAULT_SIZE,
												javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
										.addGap(18, 18, 18)
										.addComponent(btnXoaMuon, javax.swing.GroupLayout.PREFERRED_SIZE, 111,
												javax.swing.GroupLayout.PREFERRED_SIZE)
										.addGap(8, 8, 8)))
						.addContainerGap()));
		jPanel1Layout.setVerticalGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(jPanel1Layout.createSequentialGroup()
						.addComponent(jl_title_TABDSM, javax.swing.GroupLayout.PREFERRED_SIZE, 66,
								javax.swing.GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
						.addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 206,
								javax.swing.GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
						.addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
								.addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
										.addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 36,
												javax.swing.GroupLayout.PREFERRED_SIZE)
										.addComponent(lblTongMuon, javax.swing.GroupLayout.PREFERRED_SIZE, 36,
												javax.swing.GroupLayout.PREFERRED_SIZE))
								.addComponent(lblGioiHan, javax.swing.GroupLayout.PREFERRED_SIZE, 36,
										javax.swing.GroupLayout.PREFERRED_SIZE)
								.addComponent(lblGioiHan1, javax.swing.GroupLayout.PREFERRED_SIZE, 36,
										javax.swing.GroupLayout.PREFERRED_SIZE)
								.addComponent(btnXoaMuon, javax.swing.GroupLayout.PREFERRED_SIZE, 39,
										javax.swing.GroupLayout.PREFERRED_SIZE))
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
						.addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE,
								javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
						.addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 30,
								javax.swing.GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
						.addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
								.addComponent(btnXacNhan, javax.swing.GroupLayout.PREFERRED_SIZE, 41,
										javax.swing.GroupLayout.PREFERRED_SIZE)
								.addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 56,
										javax.swing.GroupLayout.PREFERRED_SIZE)
								.addComponent(cbbHenTra, javax.swing.GroupLayout.PREFERRED_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
						.addGap(68, 68, 68)));

		jTabbedPane1.addTab("DANH SÁCH MƯỢN",
				new javax.swing.ImageIcon(getClass().getResource("/Images/contact-list.png")), jPanel1); // NOI18N

		jLabel36.setFont(new java.awt.Font("Times New Roman", 1, 26)); // NOI18N
		jLabel36.setForeground(new java.awt.Color(0, 0, 102));
		jLabel36.setText(" TRƯỜNG ĐẠI HỌC NÔNG LÂM TP.HCM - NLU");

		btn_logout.setBackground(new java.awt.Color(255, 204, 204));
		btn_logout.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
		btn_logout.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/choice.png"))); // NOI18N
		btn_logout.setText("Đăng xuất");
		btn_logout.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				logOutAction(evt);
			}
		});

		jLabel8.setFont(new java.awt.Font("Times New Roman", 1, 26)); // NOI18N
		jLabel8.setForeground(new java.awt.Color(0, 0, 102));
		jLabel8.setText("QUẢN LÝ THƯ VIỆN");

		jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/icon1.jpg"))); // NOI18N

		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		layout.setHorizontalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
						.addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE).addComponent(jLabel3)
						.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
								.addGroup(layout.createSequentialGroup()
										.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addComponent(jLabel36))
								.addGroup(layout.createSequentialGroup().addGap(297, 297, 297).addComponent(jLabel8)))
						.addGap(18, 18, 18).addComponent(btn_logout, javax.swing.GroupLayout.PREFERRED_SIZE, 168,
								javax.swing.GroupLayout.PREFERRED_SIZE)
						.addContainerGap())
				.addComponent(jTabbedPane1));
		layout.setVerticalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(layout.createSequentialGroup()
						.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
								.addGroup(layout.createSequentialGroup().addGap(16, 16, 16)
										.addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 43,
												javax.swing.GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addComponent(jLabel36, javax.swing.GroupLayout.DEFAULT_SIZE,
												javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
								.addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
										.addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
										.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
												.addComponent(jLabel3, javax.swing.GroupLayout.Alignment.TRAILING)
												.addComponent(btn_logout, javax.swing.GroupLayout.Alignment.TRAILING))))
						.addGap(24, 24, 24).addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 471,
								javax.swing.GroupLayout.PREFERRED_SIZE)));

		pack();
		setLocationRelativeTo(null);
	}
	
	public void initTable_TABTT () { 
		dtm_book = new DefaultTableModel();
		dtm_book.addColumn("ID");
		dtm_book.addColumn("Tên");
		dtm_book.addColumn("Tác giả");
		dtm_book.addColumn("Thể Loại");
		dtm_book.addColumn("Năm xuất bản");
		dtm_book.addColumn("Mô tả");
		table_allBook.setModel(dtm_book);
		
		DefaultTableCellRenderer renderer = (DefaultTableCellRenderer) table_allBook.getTableHeader().getDefaultRenderer();
		renderer.setHorizontalAlignment(0);
		table_allBook.getTableHeader().setFont(new Font("Times New Roman", Font.BOLD, 18));
		table_allBook.setRowHeight(30);

		TableColumnModel columnModel = table_allBook.getColumnModel();
		columnModel.getColumn(0).setPreferredWidth(20);
		columnModel.getColumn(1).setPreferredWidth(200);
		columnModel.getColumn(2).setPreferredWidth(50);
		columnModel.getColumn(3).setPreferredWidth(50);
		columnModel.getColumn(4).setPreferredWidth(150);
		columnModel.getColumn(5).setPreferredWidth(200);
		
		initDataTable_TABTT();
	}
	
	public void initTable_TABDMS () { 
		dtm_bookLoan = new DefaultTableModel();
		dtm_bookLoan.addColumn("ID Sách");
		dtm_bookLoan.addColumn("Tên");
		dtm_bookLoan.addColumn("Tác giả");
		dtm_bookLoan.addColumn("Thể Loại");
		dtm_bookLoan.addColumn("Năm xuất bản");
		dtm_bookLoan.addColumn("Mô tả");
		table_TABDSM.setModel(dtm_bookLoan);
		
		DefaultTableCellRenderer renderer = (DefaultTableCellRenderer) table_TABDSM.getTableHeader().getDefaultRenderer();
		renderer.setHorizontalAlignment(0);
		table_TABDSM.getTableHeader().setFont(new Font("Times New Roman", Font.BOLD, 18));
		table_TABDSM.setRowHeight(30);

		TableColumnModel columnModel = table_TABDSM.getColumnModel();
		columnModel.getColumn(0).setPreferredWidth(20);
		columnModel.getColumn(1).setPreferredWidth(200);
		columnModel.getColumn(2).setPreferredWidth(50);
		columnModel.getColumn(3).setPreferredWidth(50);
		columnModel.getColumn(4).setPreferredWidth(150);
		columnModel.getColumn(5).setPreferredWidth(200);
		
		dtm_bookLoan.setRowCount(0);
		int idUser = Integer.parseInt(maTaiKhoan);
		List<MyBook> list = bookDAO.getLoanBookListOfUser(idUser);
		for (MyBook b : list) {
			dtm_bookLoan.addRow(new Object[] { b.getId(), b.getTitle(), b.getAuthor_id(), b.getCategory_id(), 
								b.getPublished_date(), b.getDescription()});
		}
	}
	
	public void initDataTable_TABTT() {
		dtm_book.setRowCount(0);
		List<MyBook> list = bookDAO.getBookList();
		for (MyBook b : list) {
			dtm_book.addRow(new Object[] { b.getId(), b.getTitle(), b.getAuthor_id(), b.getCategory_id(), 
								b.getPublished_date(), b.getDescription()});
		}
	}

	private void timkiem(String query) {
		TableRowSorter<DefaultTableModel> tbl = new TableRowSorter<DefaultTableModel>(dtm_book);
		table_allBook.setRowSorter(tbl);
		tbl.setRowFilter(RowFilter.regexFilter(query));
	}

	public boolean ktDSMuon(String masach) {
		TableRowSorter<DefaultTableModel> tbl = new TableRowSorter<DefaultTableModel>(dtm_bookLoan);
		tbl.setRowFilter(RowFilter.regexFilter(masach));
		if (tbl.getViewRowCount() != 0)
			return true;
		return false;
	}


	public void setTongMuon() {
		tongMuon = dtm_bookLoan.getRowCount();
		lblTongMuon.setText(String.valueOf(tongMuon));
		if (tongMuon > gioiHan)
			lblTongMuon.setForeground(Color.red);
		else
			lblTongMuon.setForeground(Color.black);
	}


	public void initAuthorNameForCCB_TABTT() {
		jcb_authorName.removeAllItems();
		jcb_authorName.addItem("Tên tác giả");
		List<String> authors = authorService.getAuthorsName();
		for (String a : authors) {
			jcb_authorName.addItem(a);
		}
	}

	public void initCategoryNameForCCB_TABTT() {
		jcb_categoryName.removeAllItems();
		jcb_categoryName.addItem("Thể loại sách");
		List<String> categoryNames = categoryService.getCategoryNames();
		for (String c : categoryNames) {
			jcb_categoryName.addItem(c);
		}
	}
	// Chức năng mượn sách

	private void btnXoaMuonActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnXoaMuonActionPerformed
		int row = table_TABDSM.getSelectedRow();
		if (row == -1)
			JOptionPane.showMessageDialog(UserTbHome.this, "Chọn một cuốn sách để xóa", "Lỗi",
					JOptionPane.ERROR_MESSAGE);
		else
			dtm_bookLoan.removeRow(row);
		setTongMuon();
	}// GEN-LAST:event_btnXoaMuonActionPerformed

	private void btnXacNhanActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnXacNhanActionPerformed
		if (tongMuon == 0) {
			JOptionPane.showMessageDialog(UserTbHome.this, "Danh sách mượn trống!", "Thất bại",
					JOptionPane.ERROR_MESSAGE);
		} else if (tongMuon > gioiHan)
			if (gioiHan == 0)
				JOptionPane.showMessageDialog(UserTbHome.this,
						"Bạn đã hết lượt mượn," + " trả sách đã mượn để được mượn tiếp!", "Thất bại",
						JOptionPane.ERROR_MESSAGE);
			else
				JOptionPane.showMessageDialog(UserTbHome.this,
						"Vượt quá số lượng sách được phép mượn! " + "Hãy xóa bớt danh sách mượn", "Thất bại",
						JOptionPane.ERROR_MESSAGE);
		else {
//              Thêm mới một phiếu mượn

//              Thêm các sách vào chi tiết phiếu mượn
			int rowCount = table_TABDSM.getRowCount();
			for (int i = 0; i < rowCount; i++) {
			}
			gioiHan -= rowCount;
//              setGioiHanMuon(gioiHan);
			lblGioiHan.setText(String.valueOf(gioiHan));

			JOptionPane.showMessageDialog(UserTbHome.this,
					"Xác nhận thành công!" + "\nMời bạn đến thư viện để nhận sách trong thời gian sớm nhất.");
			dtm_bookLoan.setRowCount(0);
			setTongMuon();
		}
	}

	private void showDetailBookActionPerformed(java.awt.event.ActionEvent evt) {
		int row = table_allBook.getSelectedRow();
		if (row == -1) {
			JOptionPane.showMessageDialog(UserTbHome.this, "Vui lòng chọn 1 cuốn sách để xem chi tiết!", "Thông báo",
					JOptionPane.INFORMATION_MESSAGE);
		} else {
			String idBookSelected = String.valueOf(table_allBook.getValueAt(row, 0));
			MyBook b = bookService.getBookByID(Integer.parseInt(idBookSelected));
			DetailBook detailBook_JFRAME = new DetailBook(b);
			detailBook_JFRAME.setVisible(true);
			detailBook_JFRAME.muonsach = this;
		}
	}

	private void btnResetActionPerformed(java.awt.event.ActionEvent evt) {
		initDataTable_TABTT();
		timkiem("");
		jtf_findBook.setText("");
		jcb_categoryName.setSelectedIndex(0);
		jcb_authorName.setSelectedIndex(0);
	}

	private void cbbDanhMucItemStateChanged(java.awt.event.ItemEvent evt) {// GEN-FIRST:event_cbbDanhMucItemStateChanged
		if (isLoaded) {
			String dmSelected = jcb_categoryName.getSelectedItem().toString();
			String tlSelected = jcb_authorName.getSelectedItem().toString();

			if (dmSelected.equalsIgnoreCase("Danh mục sách") == false
					&& tlSelected.equalsIgnoreCase("Thể loại") == false) {
				dtm_book.setRowCount(0);
				;

			} else {
				if (dmSelected.equalsIgnoreCase("Danh mục sách") == false
						&& tlSelected.equalsIgnoreCase("Thể loại") == true) {

					dtm_book.setRowCount(0);

				} else {
				}
			}
		}
	}// GEN-LAST:event_cbbDanhMucItemStateChanged

	private void findBookKeyReleased(java.awt.event.KeyEvent evt) {// GEN-FIRST:event_txtTimSachKeyReleased
		String keyword = jtf_findBook.getText();
		List<MyBook> allBook = bookDAO.getBookList();
		List<MyBook> result = new ArrayList<>();
		DefaultTableModel curTableModel = (DefaultTableModel) table_TABDSM.getModel();
		curTableModel.setRowCount(0);
		for (MyBook b : allBook) {
			if (b.getTitle().toLowerCase().contains(keyword.toLowerCase()))
				result.add(b);
		}
		for (MyBook b : result) {
			curTableModel.addRow(new Object[] { b.getId(), b.getTitle(), b.getAuthor_id(), b.getCategory_id(),
					b.getDescription(), b.getPublished_date(), b.getImport_date(), b.getStock_quantity() });
		}
	}// GEN-LAST:event_txtTimSachKeyReleased

	private void logOutAction(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jButton1ActionPerformed
		new Login().setVisible(true);
		this.setVisible(false);
	}// GEN-LAST:event_jButton1ActionPerformed

	private void cbbHenTraActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_cbbHenTraActionPerformed
		// TODO add your handling code here:
	}// GEN-LAST:event_cbbHenTraActionPerformed

	/**
	 * @param args the command line arguments
	 */
	public static void main(String args[]) {
		try {
			for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
				new UserTbHome("1", 3).setVisible(true);
				;
				if ("Nimbus".equals(info.getName())) {
					javax.swing.UIManager.setLookAndFeel(info.getClassName());
					break;
				}
			}
		} catch (ClassNotFoundException ex) {
			java.util.logging.Logger.getLogger(UserTbHome.class.getName()).log(java.util.logging.Level.SEVERE, null,
					ex);
		} catch (InstantiationException ex) {
			java.util.logging.Logger.getLogger(UserTbHome.class.getName()).log(java.util.logging.Level.SEVERE, null,
					ex);
		} catch (IllegalAccessException ex) {
			java.util.logging.Logger.getLogger(UserTbHome.class.getName()).log(java.util.logging.Level.SEVERE, null,
					ex);
		} catch (javax.swing.UnsupportedLookAndFeelException ex) {
			java.util.logging.Logger.getLogger(UserTbHome.class.getName()).log(java.util.logging.Level.SEVERE, null,
					ex);
		}
	}

	// Variables declaration - do not modify//GEN-BEGIN:variables
	private javax.swing.JButton btnReset;
	private javax.swing.JButton btnXacNhan;
	private javax.swing.JButton btn_detailBook;
	private javax.swing.JButton btnXoaMuon;
	private javax.swing.JComboBox<String> jcb_categoryName;
	private javax.swing.JComboBox<String> cbbHenTra;
	private javax.swing.JComboBox<String> jcb_authorName;
	private javax.swing.JButton btn_logout;
	private javax.swing.JLabel jLabel1;
	private javax.swing.JLabel jl_title_TABDSM;
	private javax.swing.JLabel jLabel3;
	private javax.swing.JLabel jLabel36;
	private javax.swing.JLabel jLabel4;
	private javax.swing.JLabel jLabel5;
	private javax.swing.JLabel jLabel6;
	private javax.swing.JLabel jLabel8;
	private javax.swing.JPanel jPanel1;
	private javax.swing.JPanel jPanel2;
	private javax.swing.JPanel jPanel3;
	private javax.swing.JPanel jPanel4;
	private javax.swing.JScrollPane jScrollPane1;
	private javax.swing.JScrollPane jScrollPane2;
	private javax.swing.JSeparator jSeparator1;
	private javax.swing.JSeparator jSeparator2;
	private javax.swing.JTabbedPane jTabbedPane1;
	private javax.swing.JLabel lblGioiHan;
	private javax.swing.JLabel lblGioiHan1;
	private javax.swing.JLabel lblTongMuon;
	private javax.swing.JTable table_TABDSM;
	private javax.swing.JTable table_allBook;
	private javax.swing.JTextField jtf_findBook;
	// End of variables declaration//GEN-END:variables
}
