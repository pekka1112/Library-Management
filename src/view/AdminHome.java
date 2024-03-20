package view;

import Service.AuthorService;
import Service.BookService;
import Service.CategoryService;
import Service.FineCollectionService;
import Service.LoanService;
import Service.UserTBService;
import dao.BookDAO;
import dao.ConnectDatabase;
import model.Category;
import model.MyBook;
import model.UserTB;
import java.awt.Color;
import java.awt.Font;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

public class AdminHome extends javax.swing.JFrame {

	private int tienPhat = 0;
	DefaultTableModel dtm;
	DefaultTableModel dtm_tableQLSQLS;

	DefaultTableModel dtm_Loan;
	DefaultTableModel dtm_LoanDetail;

	private BookDAO bookDAO;
	private UserTBService userTBService;
	private BookService bookService;
	private LoanService loanService;
	private AuthorService authorService;
	private CategoryService categoryService;
	private FineCollectionService fineCollectionService;
	public Connection connection = ConnectDatabase.getConnection();

	public AdminHome() throws SQLException {
		// init DAO for load Data
		userTBService = new UserTBService();
		bookService = new BookService();
		bookDAO = new BookDAO();
		loanService = new LoanService();
		categoryService = new CategoryService();
		authorService = new AuthorService();
		fineCollectionService = new FineCollectionService();
		// init GUI Components
		initComponents();
		// init data for TAB QuanLiPhieuMuon

		dtm_LoanDetail = new DefaultTableModel() {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		initData_QLMT_PM();
		disableBtn_QuanLiMuonTra_CTPM();

		// Danh mục sách
		disableInput_QLSQLTLS();
		initTableGUIFor_TableQLSQLTLS();
		

		// Sách
		intiAuthorIDFor_TABQLSQLS();
		initCategoryIDFor_TABQLSQLS();
		disableInput_QLS();
		refreshInput_QLS();
		initGUIForTableQLSQLS();
		initDataForTableQLSQLS();


		mokhoa.setEnabled(false);

		DefaultTableCellRenderer rendererxttdg = (DefaultTableCellRenderer) tableUserTBShowInfo.getTableHeader()
				.getDefaultRenderer();
		rendererxttdg.setHorizontalAlignment(0);
		tableUserTBShowInfo.getTableHeader().setFont(new Font("Times New Roman", Font.BOLD, 18));
		tableUserTBShowInfo.setRowHeight(30);

		TableColumnModel columnModelxttdg = tableUserTBShowInfo.getColumnModel();
		columnModelxttdg.getColumn(0).setPreferredWidth(20);
		columnModelxttdg.getColumn(1).setPreferredWidth(150);
		columnModelxttdg.getColumn(2).setPreferredWidth(150);
		columnModelxttdg.getColumn(3).setPreferredWidth(200);

		DefaultTableCellRenderer rendertkbd = (DefaultTableCellRenderer) tableStatUserTB.getTableHeader()
				.getDefaultRenderer();
		rendertkbd.setHorizontalAlignment(0);
		tableStatUserTB.getTableHeader().setFont(new Font("Times New Roman", Font.BOLD, 20));
		tableStatUserTB.setRowHeight(30);

		TableColumnModel columnModetkbd = tableUserTBShowInfo.getColumnModel();
		columnModetkbd.getColumn(0).setPreferredWidth(120);
		columnModetkbd.getColumn(1).setPreferredWidth(180);
		columnModetkbd.getColumn(2).setPreferredWidth(220);

		DefaultTableCellRenderer rendertks = (DefaultTableCellRenderer) tableStatBook.getTableHeader()
				.getDefaultRenderer();
		rendertks.setHorizontalAlignment(0);
		tableStatBook.getTableHeader().setFont(new Font("Times New Roman", Font.BOLD, 20));
		tableStatBook.setRowHeight(30);

		DefaultTableCellRenderer rendertktp = (DefaultTableCellRenderer) tableStatFineCollection.getTableHeader()
				.getDefaultRenderer();
		rendertktp.setHorizontalAlignment(0);
		tableStatFineCollection.getTableHeader().setFont(new Font("Times New Roman", Font.BOLD, 20));
		tableStatFineCollection.setRowHeight(30);

		// Khoi tao du lieu cho Tab Quan Ly Doc Gia
		this.initDataForTabUserTB();

		// Khoi tao du lieu cho Tab Tra Cuu
		this.initDataForTabSearchBook();
	}

	public void initDataForTabSearchBook() {
		DefaultTableCellRenderer dtcm = (DefaultTableCellRenderer) tableSearchSach.getTableHeader()
				.getDefaultRenderer();
		dtcm.setHorizontalAlignment(0);
		tableSearchSach.getTableHeader().setFont(new Font("Times New Roman", Font.BOLD, 20));
		tableSearchSach.setRowHeight(30);

		List<MyBook> allBook = bookDAO.getBookList();
		DefaultTableModel curTableModel = (DefaultTableModel) tableSearchSach.getModel();
		curTableModel.setRowCount(0);
		for (MyBook b : allBook) {
			curTableModel.addRow(new Object[] { b.getId(), b.getTitle(), b.getAuthor_id(), b.getCategory_id(),
					b.getDescription(), b.getPublished_date(), b.getImport_date(), b.getStock_quantity() });
		}
	}

	/**
	 * Init Data For Tab QLĐG When You Open It the first time
	 */
	public void initDataForTabUserTB() {
		DefaultTableCellRenderer rendererdg = (DefaultTableCellRenderer) tableUserTBManage.getTableHeader()
				.getDefaultRenderer();
		rendererdg.setHorizontalAlignment(0);
		tableUserTBManage.getTableHeader().setFont(new Font("Times New Roman", Font.BOLD, 18));
		tableUserTBManage.setRowHeight(30);

		TableColumnModel tcm_userManage = tableUserTBManage.getColumnModel();
		tcm_userManage.getColumn(0).setPreferredWidth(50);
		tcm_userManage.getColumn(1).setPreferredWidth(200);
		tcm_userManage.getColumn(2).setPreferredWidth(180);
		tcm_userManage.getColumn(3).setPreferredWidth(100);
		tcm_userManage.getColumn(4).setPreferredWidth(100);

		jtf_idUserTb.setText("");
		jtf_userTBName.setText("");
		jtf_dob.setText("");
		jtf_emailUserTB.setText("");
		jtf_phoneNumber.setText("");
		jtf_idUserTb.setEditable(true);

		List<UserTB> allUserTB = userTBService.getUserTBList();
		DefaultTableModel dtm_userManage = (DefaultTableModel) tableUserTBManage.getModel();
		dtm_userManage.setRowCount(0);
		DefaultTableModel dtm_userShow = (DefaultTableModel) tableUserTBShowInfo.getModel();
		dtm_userShow.setRowCount(0);

		allUserTB.forEach((user) -> {
			dtm_userManage.addRow(
					new Object[] { user.getId(), user.getFullname(), user.getEmail(), user.getPhone(), user.getDob() });
			dtm_userShow.addRow(new Object[] { user.getId(), user.getFullname(), user.getPassword(), user.getEmail(),
					user.getPhone(), user.getDob() });
		});
	}
	public void initTableGUIFor_TableQLSQLTLS() { 
		dtm = new DefaultTableModel() {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};

		table_QLS_QLTLS.setModel(dtm);
		dtm.addColumn("Mã thể loại");
		dtm.addColumn("Tên thể loại");
		dtm.addColumn("Mã thể loại cha");
		initTableDataFor_TableQLSQLTLS();
		
		
		DefaultTableCellRenderer renderer = (DefaultTableCellRenderer) table_QLS_QLTLS.getTableHeader().getDefaultRenderer();
		renderer.setHorizontalAlignment(0);
		table_QLS_QLTLS.getTableHeader().setFont(new Font("Times New Roman", Font.BOLD, 18));
		table_QLS_QLTLS.setRowHeight(30);

		TableColumnModel columnModel = table_QLS_QLTLS.getColumnModel();
		columnModel.getColumn(0).setPreferredWidth(100);
		columnModel.getColumn(1).setPreferredWidth(300);
		columnModel.getColumn(2).setPreferredWidth(100);
	}

	private void initTableDataFor_TableQLSQLTLS() {
		List<Category> l = categoryService.getCategories();
		for (Category c : l) {
			dtm.addRow(new Object[] { c.getId(), c.getName(), c.getParent_category_id() });
		}
	}

	public void enable_DM() {
		jtf_cID_TabQLSQLTLS.setEnabled(true);
		jtf_cNameID_TabQLSQLTLS.setEnabled(true);
	}

	public void initGUIForTableQLSQLS() {
		dtm_tableQLSQLS = new DefaultTableModel() {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};

		table_QLS_QLS.setModel(dtm_tableQLSQLS);
		dtm_tableQLSQLS.addColumn("ID");
		dtm_tableQLSQLS.addColumn("Tên sách");
		dtm_tableQLSQLS.addColumn("Thể loại");
		dtm_tableQLSQLS.addColumn("Tác giả");
		dtm_tableQLSQLS.addColumn("Ngày XB");
		dtm_tableQLSQLS.addColumn("Số lượng tồn");
		dtm_tableQLSQLS.addColumn("Ngày nhập về");
		dtm_tableQLSQLS.addColumn("Mô tả");

		DefaultTableCellRenderer renderers = (DefaultTableCellRenderer) table_QLS_QLS.getTableHeader()
				.getDefaultRenderer();
		renderers.setHorizontalAlignment(0);
		table_QLS_QLS.getTableHeader().setFont(new Font("Times New Roman", Font.BOLD, 20));
		table_QLS_QLS.setRowHeight(30);

		TableColumnModel columnModel1 = table_QLS_QLS.getColumnModel();
		columnModel1.getColumn(0).setPreferredWidth(30);
		columnModel1.getColumn(1).setPreferredWidth(200);
		columnModel1.getColumn(2).setPreferredWidth(100);
		columnModel1.getColumn(3).setPreferredWidth(100);
		columnModel1.getColumn(4).setPreferredWidth(150);
		columnModel1.getColumn(5).setPreferredWidth(50);
		columnModel1.getColumn(6).setPreferredWidth(150);
		columnModel1.getColumn(7).setPreferredWidth(200);
	}

	private void initDataForTableQLSQLS() {
		try {
			ResultSet rs = bookService.getBooksWithCateAuthorName();
			while (rs.next()) {
				dtm_tableQLSQLS.addRow(new Object[] { rs.getInt("id"), rs.getString("title"), rs.getString("name"),
						rs.getString("fullname"), rs.getString("published_date"), rs.getInt("stock_quantity"),
						rs.getString("import_date"), rs.getString("description") });
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	public void disableInput_QLSQLTLS() {
		jtf_cID_TabQLSQLTLS.setEnabled(false);
		jtf_cNameID_TabQLSQLTLS.setEnabled(false);
		btn_saveBook.setEnabled(false);
		btn_editBook.setEnabled(false);
	}

	public void enableInput_QLS() {
		jtf_titleBook.setEnabled(true);
		jcb_idCategory.setEnabled(true);
		jtf_stockQuantity.setEnabled(true);
		jtf_publishedDate.setEnabled(true);
		jtf_avaliableBook.setEnabled(true);
		jtf_importDate.setEnabled(true);
		jcb_authorID.setEnabled(true);
		jtf_description.setEnabled(true);
	}

	public void disableInput_QLS() {
		jtf_titleBook.setEnabled(false);
		jcb_idCategory.setEnabled(false);
		jtf_category.setEnabled(false);
		jtf_stockQuantity.setEnabled(false);
		jtf_publishedDate.setEnabled(false);
		jtf_avaliableBook.setEnabled(false);
		jtf_importDate.setEnabled(false);
		jcb_authorID.setEnabled(false);
		jtf_authorName.setEnabled(false);
		jtf_description.setEnabled(false);
	}

	public void refreshInput_QLS() {
		jtf_idBook.setText("");
		jtf_titleBook.setText("");
		jcb_idCategory.setSelectedIndex(-1);
		jtf_category.setText("");
		jtf_stockQuantity.setText("");
		jtf_publishedDate.setText("");
		jtf_avaliableBook.setText("");
		jtf_importDate.setText("");
		jcb_authorID.setSelectedIndex(-1);
		jtf_authorName.setText("");
		jtf_description.setText("");
	}

	public void initCategoryIDFor_TABQLSQLS() {
		jcb_idCategory.removeAllItems();
		try {
			ResultSet rs = categoryService.getCategorieID();
			while (rs.next()) {
				this.jcb_idCategory.addItem(rs.getString("id"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void intiAuthorIDFor_TABQLSQLS() {
		jcb_authorID.removeAllItems();
		try {
			ResultSet rs = authorService.getAuthorID();
			while (rs.next()) {
				this.jcb_authorID.addItem(rs.getString("id"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public int getSoNgayTre(String maPM, String maSach, String ngayThucTra) {
		String sql = "select ((DAY('" + ngayThucTra
				+ "') - DAY(pm.ngayMuon)) - pm.soNgayMuon) as 'S' from ChiTietPhieuMuon as ctpm, PhieuMuon as pm where ctpm.maPhieuMuon = pm.maPhieuMuon and ((DAY(ctpm.ngayThucTra) - DAY(pm.ngayMuon)) > pm.soNgayMuon) and maSach like '%"
				+ maSach + "%' and pm.maPhieuMuon like '%" + maPM + "%'";
		try {
		} catch (Exception e) {
			System.out.println("khong tinh dc");
		}
		return 0;
	}

	@SuppressWarnings("unchecked")
	// <editor-fold defaultstate="collapsed" desc="Generated
	// Code">//GEN-BEGIN:initComponents
	private void initComponents() {

		gioitinhbtngroup = new javax.swing.ButtonGroup();
		jPanel1 = new javax.swing.JPanel();
		jLabel6 = new javax.swing.JLabel();
		jLabel36 = new javax.swing.JLabel();
		btn_logout = new javax.swing.JButton();
		jLabel29 = new javax.swing.JLabel();
		jPanel2 = new javax.swing.JPanel();
		jtp_main = new javax.swing.JTabbedPane();
		jPanel3 = new javax.swing.JPanel();
		jtp_QLDG = new javax.swing.JTabbedPane();
		jPanel11 = new javax.swing.JPanel();
		jScrollPane3 = new javax.swing.JScrollPane();
		tableUserTBManage = new javax.swing.JTable();
		jLabel7 = new javax.swing.JLabel();
		jLabel8 = new javax.swing.JLabel();
		jLabel9 = new javax.swing.JLabel();
		jLabel10 = new javax.swing.JLabel();
		jLabel11 = new javax.swing.JLabel();
		jLabel12 = new javax.swing.JLabel();
		jtf_idUserTb = new javax.swing.JTextField();
		jtf_userTBName = new javax.swing.JTextField();
		gioitinhnam = new javax.swing.JRadioButton();
		gioitinhnu = new javax.swing.JRadioButton();
		jtf_phoneNumber = new javax.swing.JTextField();
		jtf_emailUserTB = new javax.swing.JTextField();
		btn_addNewUserTB = new javax.swing.JButton();
		updatedg = new javax.swing.JButton();
		khoatk = new javax.swing.JButton();
		jtf_dob = new javax.swing.JTextField();
		mokhoa = new javax.swing.JButton();
		btn_refresh = new javax.swing.JButton();
		jSeparator1 = new javax.swing.JSeparator();
		jPanel10 = new javax.swing.JPanel();
		jScrollPane2 = new javax.swing.JScrollPane();
		tableUserTBShowInfo = new javax.swing.JTable();
		jLabel5 = new javax.swing.JLabel();
		jtf_search = new javax.swing.JTextField();
		jPanel4 = new javax.swing.JPanel();
		jtp_QLS_QLS = new javax.swing.JTabbedPane();
		jPanel8 = new javax.swing.JPanel();
		jl_idBook = new javax.swing.JLabel();
		jtf_idBook = new javax.swing.JTextField();
		jl_titleBook = new javax.swing.JLabel();
		jtf_titleBook = new javax.swing.JTextField();
		jl_stockQuantity = new javax.swing.JLabel();
		jtf_stockQuantity = new javax.swing.JTextField();
		jl_avaliableBook = new javax.swing.JLabel();
		jtf_avaliableBook = new javax.swing.JTextField();
		jl_importDate = new javax.swing.JLabel();
		jtf_importDate = new javax.swing.JTextField();
		jl_publishedDate = new javax.swing.JLabel();
		jtf_publishedDate = new javax.swing.JTextField();
		jl_description = new javax.swing.JLabel();
		jtf_description = new javax.swing.JTextField();
		jl_authorName = new javax.swing.JLabel();
		jtf_authorName = new javax.swing.JTextField();
		jl_category = new javax.swing.JLabel();
		jtf_category = new javax.swing.JTextField();
		jScrollPane4 = new javax.swing.JScrollPane();
		table_QLS_QLS = new javax.swing.JTable();
		btn_addBook = new javax.swing.JButton();
		btn_editBook = new javax.swing.JButton();
		btn_saveBook = new javax.swing.JButton();
		btn_refreshAction = new javax.swing.JButton();
		jl_idCategory = new javax.swing.JLabel();
		jcb_idCategory = new javax.swing.JComboBox<>();
		jl_authorID = new javax.swing.JLabel();
		jcb_authorID = new javax.swing.JComboBox<>();
		jSeparator2 = new javax.swing.JSeparator();
		jPanel9 = new javax.swing.JPanel();
		jScrollPane1 = new javax.swing.JScrollPane();
		table_QLS_QLTLS = new javax.swing.JTable();
		jl_category_TABQLS_QLTLS = new javax.swing.JLabel();
		jl_cID_TABQLSQLTLS = new javax.swing.JLabel();
		jl_cNameID_TABQLSQLTLS = new javax.swing.JLabel();
		jtf_cID_TabQLSQLTLS = new javax.swing.JTextField();
		jtf_cNameID_TabQLSQLTLS = new javax.swing.JTextField();
		btn_ThemDMSach = new javax.swing.JButton();
		btn_LuuDMSach = new javax.swing.JButton();
		btn_SuaDMSach = new javax.swing.JButton();
		txt_timkiemDMSach = new javax.swing.JTextField();
		jLabel4 = new javax.swing.JLabel();
		btn_lammoi = new javax.swing.JButton();
		jPK_QuanLyPhieuMuon = new javax.swing.JPanel();
		jtp_QuanLiMuonTra = new javax.swing.JTabbedPane();
		Panel_DanhSachPM = new javax.swing.JPanel();
		jSPK_DSPM = new javax.swing.JScrollPane();
		table_listLoan = new javax.swing.JTable();
		btnK_themPM = new javax.swing.JButton();
		jl_titleTab_QLPM = new javax.swing.JLabel();
		Panel_ChiTietPM = new javax.swing.JPanel();
		jPanel12 = new javax.swing.JPanel();
		jLabel26 = new javax.swing.JLabel();
		jLabel27 = new javax.swing.JLabel();
		jLabel28 = new javax.swing.JLabel();
		jLabel30 = new javax.swing.JLabel();
		jdc_loan_date = new com.toedter.calendar.JDateChooser();
		K_maPM = new javax.swing.JTextField();
		jtf_BookID = new javax.swing.JTextField();
		jcb_stateBook = new javax.swing.JComboBox<>();
		jLabel38 = new javax.swing.JLabel();
		jLabel39 = new javax.swing.JLabel();
		jLabel40 = new javax.swing.JLabel();
		jPanel13 = new javax.swing.JPanel();
		jScrollPane6 = new javax.swing.JScrollPane();
		table_LoanDetail = new javax.swing.JTable();
		btnK_veTrangTruoc = new javax.swing.JButton();
		btnK_suaChiTiet = new javax.swing.JButton();
		btnK_luuChiTiet = new javax.swing.JButton();
		btnK_lamMoi = new javax.swing.JButton();
		jPanel6 = new javax.swing.JPanel();
		jTabbedPane5 = new javax.swing.JTabbedPane();
		jPanel14 = new javax.swing.JPanel();
		jLabel32 = new javax.swing.JLabel();
		jcb_statUserFunction = new javax.swing.JComboBox<>();
		jScrollPane7 = new javax.swing.JScrollPane();
		tableStatUserTB = new javax.swing.JTable();
		jLabel31 = new javax.swing.JLabel();
		jPanel15 = new javax.swing.JPanel();
		jLabel33 = new javax.swing.JLabel();
		jcb_statBookFunction = new javax.swing.JComboBox<>();
		jScrollPane8 = new javax.swing.JScrollPane();
		tableStatBook = new javax.swing.JTable();
		jLabel37 = new javax.swing.JLabel();
		jPanel16 = new javax.swing.JPanel();
		jLabel34 = new javax.swing.JLabel();
		cbb_chucNangThongKe2 = new javax.swing.JComboBox<>();
		jScrollPane9 = new javax.swing.JScrollPane();
		tableStatFineCollection = new javax.swing.JTable();
		jLabel41 = new javax.swing.JLabel();
		jPanel7 = new javax.swing.JPanel();
		timkiem = new javax.swing.JPanel();
		jLabel15 = new javax.swing.JLabel();
		textboxsearch = new javax.swing.JTextField();
		jButton11 = new javax.swing.JButton();
		jLabel16 = new javax.swing.JLabel();
		jLabel35 = new javax.swing.JLabel();
		jComboBox3 = new javax.swing.JComboBox<>();
		jComboBox4 = new javax.swing.JComboBox<>();
		jScrollPane5 = new javax.swing.JScrollPane();
		tableSearchSach = new javax.swing.JTable();

		setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
		setTitle("ADMIN_HOMEPAGE");

		jPanel1.setBackground(new java.awt.Color(255, 255, 204));

		jLabel6.setFont(new java.awt.Font("Times New Roman", 1, 26)); // NOI18N
		jLabel6.setForeground(new java.awt.Color(0, 0, 102));
		jLabel6.setText("QUẢN LÝ THƯ VIỆN");

		jLabel36.setFont(new java.awt.Font("Times New Roman", 1, 26)); // NOI18N
		jLabel36.setForeground(new java.awt.Color(0, 0, 102));
		jLabel36.setText(" TRƯỜNG ĐẠI HỌC NÔNG LÂM THÀNH PHỐ HCM - NLU");

		btn_logout.setBackground(new java.awt.Color(255, 204, 204));
		btn_logout.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
		btn_logout.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/choice.png"))); // NOI18N
		btn_logout.setText("Đăng Xuất");
		btn_logout.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				logoutAction(evt);
			}
		});

		jLabel29.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/icon1.jpg")));

		javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
		jPanel1.setLayout(jPanel1Layout);
		jPanel1Layout.setHorizontalGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
						.addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE).addComponent(jLabel29)
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
						.addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
								.addComponent(jLabel36).addGroup(jPanel1Layout.createSequentialGroup()
										.addComponent(jLabel6).addGap(221, 221, 221)))
						.addGap(35, 35, 35).addComponent(btn_logout).addContainerGap()));
		jPanel1Layout.setVerticalGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(javax.swing.GroupLayout.Alignment.TRAILING,
						jPanel1Layout.createSequentialGroup()
								.addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(jLabel29).addContainerGap())
				.addGroup(jPanel1Layout.createSequentialGroup().addGap(16, 16, 16)
						.addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
								.addGroup(jPanel1Layout.createSequentialGroup().addComponent(jLabel6)
										.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addComponent(jLabel36))
								.addComponent(btn_logout))
						.addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));

		jPanel2.setBackground(new java.awt.Color(204, 204, 255));

		jtp_main.setBackground(new java.awt.Color(255, 255, 204));
		jtp_main.setForeground(new java.awt.Color(0, 0, 102));
		jtp_main.setTabLayoutPolicy(javax.swing.JTabbedPane.SCROLL_TAB_LAYOUT);
		jtp_main.setTabPlacement(javax.swing.JTabbedPane.LEFT);
		jtp_main.setFont(new java.awt.Font("Times New Roman", 1, 20));
		jtp_main.setMaximumSize(new java.awt.Dimension(300, 300));
		jtp_main.setPreferredSize(new java.awt.Dimension(300, 300));
		jtp_main.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseClicked(java.awt.event.MouseEvent evt) {
				initBasicTableValueForTabTHONGKE(evt);
			}
		});

		jPanel3.setBackground(new java.awt.Color(204, 204, 255));

		jtp_QLDG.setForeground(new java.awt.Color(0, 0, 153));
		jtp_QLDG.setFont(new java.awt.Font("Times New Roman", 1, 20));

		jPanel11.setBackground(new java.awt.Color(255, 255, 204));

		tableUserTBManage.setFont(new java.awt.Font("Times New Roman", 0, 20));
		tableUserTBManage.setModel(new javax.swing.table.DefaultTableModel(new Object[][] {},
				new String[] { "Mã Tài Khoản", "Tên", "Email", "SĐT", "Ngày Sinh" }));
		tableUserTBManage.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseClicked(java.awt.event.MouseEvent evt) {
				showInfoUserWhenClickTableUserManage(evt);
			}
		});
		jScrollPane3.setViewportView(tableUserTBManage);

		jLabel7.setFont(new java.awt.Font("Times New Roman", 1, 20)); // NOI18N
		jLabel7.setText("Mã UserTB:");

		jLabel8.setFont(new java.awt.Font("Times New Roman", 1, 20)); // NOI18N
		jLabel8.setText("Tên UserTB:");

		jLabel10.setFont(new java.awt.Font("Times New Roman", 1, 20)); // NOI18N
		jLabel10.setText("SĐT:");

		jLabel11.setFont(new java.awt.Font("Times New Roman", 1, 20));
		jLabel11.setText("Email:");

		jLabel12.setFont(new java.awt.Font("Times New Roman", 1, 20));
		jLabel12.setText("Ngày Sinh");

		jtf_idUserTb.setFont(new java.awt.Font("Times New Roman", 0, 20));
		jtf_idUserTb.setForeground(new java.awt.Color(204, 0, 0));
		jtf_idUserTb.addKeyListener(new java.awt.event.KeyAdapter() {
			public void keyPressed(java.awt.event.KeyEvent evt) {
				idUserTBKeyPressedAction(evt);
			}
		});

		jtf_userTBName.setFont(new java.awt.Font("Times New Roman", 0, 20));
		jtf_userTBName.addKeyListener(new java.awt.event.KeyAdapter() {
			public void keyPressed(java.awt.event.KeyEvent evt) {
				userTBNameKeyPressed(evt);
			}
		});

		jtf_phoneNumber.setFont(new java.awt.Font("Times New Roman", 0, 20));
		jtf_phoneNumber.addKeyListener(new java.awt.event.KeyAdapter() {
			public void keyPressed(java.awt.event.KeyEvent evt) {
				phoneNumberKeyPressed(evt);
			}

			public void keyReleased(java.awt.event.KeyEvent evt) {
				phoneNumberKeyReleased(evt);
			}

			public void keyTyped(java.awt.event.KeyEvent evt) {
				phoneNumberKeyTyped(evt);
			}
		});

		jtf_emailUserTB.setFont(new java.awt.Font("Times New Roman", 0, 20));

		btn_addNewUserTB.setBackground(new java.awt.Color(255, 204, 204));
		btn_addNewUserTB.setFont(new java.awt.Font("Times New Roman", 1, 18));
		btn_addNewUserTB.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/plus.png"))); // NOI18N
		btn_addNewUserTB.setText("Thêm mới");
		btn_addNewUserTB.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				addNewUserTBActionPerformed(evt);
			}
		});

		updatedg.setBackground(new java.awt.Color(255, 204, 204));
		updatedg.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
		updatedg.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/exchange1.png"))); // NOI18N
		updatedg.setText("Sửa ");
		updatedg.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				updatedgActionPerformed(evt);
			}
		});

		jtf_dob.setFont(new java.awt.Font("Times New Roman", 0, 20)); // NOI18N
		jtf_dob.addFocusListener(new java.awt.event.FocusAdapter() {
			public void focusLost(java.awt.event.FocusEvent evt) {
				dobFocusLost(evt);
			}
		});
		jtf_dob.addKeyListener(new java.awt.event.KeyAdapter() {
			public void keyPressed(java.awt.event.KeyEvent evt) {
				dobKeyPressed(evt);
			}
		});

		btn_refresh.setBackground(new java.awt.Color(255, 204, 204));
		btn_refresh.setFont(new java.awt.Font("Times New Roman", 1, 18));
		btn_refresh.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/eraser.png"))); // NOI18N
		btn_refresh.setText("Làm mới");
		btn_refresh.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				reloadAction(evt);
			}
		});

		javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
		jPanel11.setLayout(jPanel11Layout);
		jPanel11Layout.setHorizontalGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(javax.swing.GroupLayout.Alignment.TRAILING,
						jPanel11Layout.createSequentialGroup().addContainerGap(141, Short.MAX_VALUE)
								.addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
										.addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel11Layout
												.createSequentialGroup().addGroup(jPanel11Layout
														.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING,
																false)
														.addGroup(
																jPanel11Layout.createSequentialGroup()
																		.addComponent(jLabel9).addGap(18, 18, 18)
																		.addComponent(
																				gioitinhnam)
																		.addGap(18, 18, 18).addComponent(gioitinhnu))
														.addGroup(jPanel11Layout
																.createSequentialGroup().addComponent(jLabel7)
																.addPreferredGap(
																		javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
																.addComponent(jtf_idUserTb))
														.addGroup(jPanel11Layout.createSequentialGroup()
																.addComponent(jLabel8)
																.addPreferredGap(
																		javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																.addComponent(jtf_userTBName,
																		javax.swing.GroupLayout.PREFERRED_SIZE, 246,
																		javax.swing.GroupLayout.PREFERRED_SIZE)))
												.addGap(18, 18, 18)
												.addGroup(jPanel11Layout
														.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
														.addComponent(jLabel10).addComponent(jLabel11)
														.addComponent(jLabel12))
												.addGap(18, 18, 18)
												.addGroup(jPanel11Layout
														.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING,
																false)
														.addComponent(jtf_emailUserTB,
																javax.swing.GroupLayout.DEFAULT_SIZE, 310,
																Short.MAX_VALUE)
														.addComponent(jtf_dob).addComponent(jtf_phoneNumber))
												.addGap(95, 95, 95))
										.addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel11Layout
												.createSequentialGroup().addComponent(btn_addNewUserTB)
												.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
												.addComponent(updatedg, javax.swing.GroupLayout.PREFERRED_SIZE, 111,
														javax.swing.GroupLayout.PREFERRED_SIZE)
												.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
												.addComponent(khoatk, javax.swing.GroupLayout.PREFERRED_SIZE, 205,
														javax.swing.GroupLayout.PREFERRED_SIZE)
												.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
												.addComponent(mokhoa, javax.swing.GroupLayout.PREFERRED_SIZE, 177,
														javax.swing.GroupLayout.PREFERRED_SIZE)
												.addGap(51, 51, 51)
												.addComponent(btn_refresh, javax.swing.GroupLayout.PREFERRED_SIZE, 151,
														javax.swing.GroupLayout.PREFERRED_SIZE)
												.addContainerGap())))
				.addGroup(jPanel11Layout.createSequentialGroup().addGroup(jPanel11Layout
						.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(jPanel11Layout.createSequentialGroup().addContainerGap().addComponent(jSeparator1))
						.addGroup(jPanel11Layout.createSequentialGroup().addGap(27, 27, 27).addComponent(jScrollPane3)))
						.addContainerGap()));
		jPanel11Layout.setVerticalGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel11Layout.createSequentialGroup()
						.addGap(37, 37, 37)
						.addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
								.addComponent(jLabel7).addComponent(jLabel10)
								.addComponent(jtf_idUserTb, javax.swing.GroupLayout.PREFERRED_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
								.addComponent(jtf_phoneNumber, javax.swing.GroupLayout.PREFERRED_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
						.addGap(32, 32, 32)
						.addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
								.addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
										.addComponent(jLabel12)
										.addComponent(jtf_dob, javax.swing.GroupLayout.PREFERRED_SIZE,
												javax.swing.GroupLayout.DEFAULT_SIZE,
												javax.swing.GroupLayout.PREFERRED_SIZE))
								.addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
										.addComponent(jLabel8).addComponent(jtf_userTBName,
												javax.swing.GroupLayout.PREFERRED_SIZE,
												javax.swing.GroupLayout.DEFAULT_SIZE,
												javax.swing.GroupLayout.PREFERRED_SIZE)))
						.addGap(26, 26, 26)
						.addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
								.addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
										.addComponent(jLabel11).addComponent(jtf_emailUserTB,
												javax.swing.GroupLayout.PREFERRED_SIZE,
												javax.swing.GroupLayout.DEFAULT_SIZE,
												javax.swing.GroupLayout.PREFERRED_SIZE))
								.addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
										.addComponent(jLabel9).addComponent(gioitinhnam).addComponent(gioitinhnu)))
						.addGap(38, 38, 38)
						.addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10,
								javax.swing.GroupLayout.PREFERRED_SIZE)
						.addGap(18, 18, 18)
						.addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
								.addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
										.addComponent(khoatk, javax.swing.GroupLayout.PREFERRED_SIZE, 41,
												javax.swing.GroupLayout.PREFERRED_SIZE)
										.addComponent(mokhoa, javax.swing.GroupLayout.PREFERRED_SIZE, 41,
												javax.swing.GroupLayout.PREFERRED_SIZE)
										.addComponent(btn_refresh, javax.swing.GroupLayout.PREFERRED_SIZE, 41,
												javax.swing.GroupLayout.PREFERRED_SIZE))
								.addComponent(btn_addNewUserTB, javax.swing.GroupLayout.PREFERRED_SIZE, 41,
										javax.swing.GroupLayout.PREFERRED_SIZE)
								.addComponent(updatedg, javax.swing.GroupLayout.PREFERRED_SIZE, 41,
										javax.swing.GroupLayout.PREFERRED_SIZE))
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 33, Short.MAX_VALUE)
						.addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 311,
								javax.swing.GroupLayout.PREFERRED_SIZE)
						.addContainerGap()));

		jtp_QLDG.addTab("Quản lý Độc giả", jPanel11);

		jPanel10.setBackground(new java.awt.Color(255, 255, 204));

		tableUserTBShowInfo.setFont(new java.awt.Font("Times New Roman", 0, 20));
		tableUserTBShowInfo.setModel(new javax.swing.table.DefaultTableModel(new Object[][] {},
				new String[] { "Mã tài khoản", "Tên", "Mật khẩu", "Email", "SĐT", "Ngày sinh" }));
		tableUserTBShowInfo.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseClicked(java.awt.event.MouseEvent evt) {
				showInfoUserWhenMouseClickedOnTable(evt);
			}
		});
		jScrollPane2.setViewportView(tableUserTBShowInfo);
		if (tableUserTBShowInfo.getColumnModel().getColumnCount() > 0) {
			tableUserTBShowInfo.getColumnModel().getColumn(2).setMinWidth(50);
			tableUserTBShowInfo.getColumnModel().getColumn(2).setPreferredWidth(90);
			tableUserTBShowInfo.getColumnModel().getColumn(2).setMaxWidth(100);
		}

		jLabel5.setFont(new java.awt.Font("Times New Roman", 1, 20)); // NOI18N
		jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/light-bulb.png"))); // NOI18N
		jLabel5.setText("Nhập thông tin bạn đọc:");

		jtf_search.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
		jtf_search.addKeyListener(new java.awt.event.KeyAdapter() {
			public void keyReleased(java.awt.event.KeyEvent evt) {
				searchUserTBAction(evt);
			}
		});

		javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
		jPanel10.setLayout(jPanel10Layout);
		jPanel10Layout.setHorizontalGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(jPanel10Layout.createSequentialGroup().addGap(28, 28, 28)
						.addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
								.addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 1017,
										javax.swing.GroupLayout.PREFERRED_SIZE)
								.addGroup(jPanel10Layout.createSequentialGroup().addComponent(jLabel5)
										.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
										.addComponent(jtf_search, javax.swing.GroupLayout.PREFERRED_SIZE, 297,
												javax.swing.GroupLayout.PREFERRED_SIZE)))
						.addContainerGap(12, Short.MAX_VALUE)));
		jPanel10Layout.setVerticalGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel10Layout.createSequentialGroup()
						.addGap(84, 84, 84)
						.addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
								.addComponent(jLabel5).addComponent(jtf_search, javax.swing.GroupLayout.PREFERRED_SIZE,
										35, javax.swing.GroupLayout.PREFERRED_SIZE))
						.addGap(51, 51, 51).addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 284,
								javax.swing.GroupLayout.PREFERRED_SIZE)
						.addContainerGap(195, Short.MAX_VALUE)));

		jtp_QLDG.addTab("Xem thông tin UserTB", jPanel10);

		javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
		jPanel3.setLayout(jPanel3Layout);
		jPanel3Layout.setHorizontalGroup(
				jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addComponent(jtp_QLDG));
		jPanel3Layout.setVerticalGroup(
				jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addComponent(jtp_QLDG));

		jtp_main.addTab("     QUẢN LÝ ĐỘC GIẢ ",
				new javax.swing.ImageIcon(getClass().getResource("/Images/reading.png")), jPanel3); // NOI18N

		jPanel4.setBackground(new java.awt.Color(204, 204, 255));

		// TAB QUẢN LÝ SÁCH
		jtp_QLS_QLS.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 153, 153)));
		jtp_QLS_QLS.setForeground(new java.awt.Color(0, 0, 153));
		jtp_QLS_QLS.setFont(new java.awt.Font("Times New Roman", 1, 20)); // NOI18N

		jPanel8.setBackground(new java.awt.Color(255, 255, 204));
		jPanel8.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));

		jl_idBook.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
		jl_idBook.setForeground(new java.awt.Color(102, 51, 0));
		jl_idBook.setText("Mã sách:");

		jtf_idBook.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
		jtf_idBook.setForeground(new java.awt.Color(255, 0, 0));
		jtf_idBook.setEnabled(false);

		jl_titleBook.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
		jl_titleBook.setForeground(new java.awt.Color(102, 51, 0));
		jl_titleBook.setText("Tên sách:");

		jtf_titleBook.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N

		jl_stockQuantity.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
		jl_stockQuantity.setForeground(new java.awt.Color(102, 51, 0));
		jl_stockQuantity.setText("Số lượng tồn:");

		jtf_stockQuantity.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N

		jl_avaliableBook.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
		jl_avaliableBook.setForeground(new java.awt.Color(102, 51, 0));
		jl_avaliableBook.setText("Trạng thái:");

		jtf_avaliableBook.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N

		jl_importDate.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
		jl_importDate.setForeground(new java.awt.Color(102, 51, 0));
		jl_importDate.setText("Ngày nhập về:");

		jtf_importDate.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N

		jl_publishedDate.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
		jl_publishedDate.setForeground(new java.awt.Color(102, 51, 0));
		jl_publishedDate.setText("Năm xuất bản:");

		jtf_publishedDate.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N

		jl_description.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
		jl_description.setForeground(new java.awt.Color(102, 51, 0));
		jl_description.setText("Mô tả:");

		jtf_description.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
		jtf_description.setHorizontalAlignment(javax.swing.JTextField.LEFT);

		jl_authorName.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
		jl_authorName.setForeground(new java.awt.Color(102, 51, 0));
		jl_authorName.setText("Tên tác giả:");

		jtf_authorName.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
		jtf_authorName.setEnabled(false);

		jl_category.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
		jl_category.setForeground(new java.awt.Color(102, 51, 0));
		jl_category.setText("Thể loại:");

		jtf_category.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
		jtf_category.setEnabled(false);

		table_QLS_QLS.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
		table_QLS_QLS.setModel(new javax.swing.table.DefaultTableModel(new Object[][] {

		}, new String[] {

		}));
		table_QLS_QLS.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseClicked(java.awt.event.MouseEvent evt) {
				tableQLSQLSMouseClicked(evt);
			}
		});
		jScrollPane4.setViewportView(table_QLS_QLS);

		btn_addBook.setBackground(new java.awt.Color(255, 204, 204));
		btn_addBook.setFont(new java.awt.Font("Times New Roman", 1, 18));
		btn_addBook.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/plus.png"))); // NOI18N
		btn_addBook.setText("Thêm");
		btn_addBook.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				addNewBookActionPerformed(evt);
			}
		});

		btn_editBook.setBackground(new java.awt.Color(255, 204, 204));
		btn_editBook.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
		btn_editBook.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/exchange1.png"))); // NOI18N
		btn_editBook.setText("Sửa");
		btn_editBook.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				editBookActionPerformed(evt);
			}
		});

		btn_saveBook.setBackground(new java.awt.Color(255, 204, 204));
		btn_saveBook.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
		btn_saveBook.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/luu.png"))); // NOI18N
		btn_saveBook.setText("Lưu");
		btn_saveBook.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				saveBookActionPerformed(evt);
			}
		});

		btn_refreshAction.setBackground(new java.awt.Color(255, 204, 204));
		btn_refreshAction.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
		btn_refreshAction.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/eraser.png"))); // NOI18N
		btn_refreshAction.setText("Làm mới");
		btn_refreshAction.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				refreshActionPerformed(evt);
			}
		});

		jl_idCategory.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
		jl_idCategory.setForeground(new java.awt.Color(102, 51, 0));
		jl_idCategory.setText("Mã thể loại:");

		jcb_idCategory.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
		jcb_idCategory.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				showCategoryActionPerformed(evt);
			}
		});

		jl_authorID.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
		jl_authorID.setForeground(new java.awt.Color(102, 51, 0));
		jl_authorID.setText("Mã tác giả:");

		jcb_authorID.setFont(new java.awt.Font("Times New Roman", 0, 18));
		jcb_authorID.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				showAuthorNameActionPerformed(evt);
			}
		});

		javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
		jPanel8.setLayout(jPanel8Layout);
		jPanel8Layout.setHorizontalGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(jPanel8Layout.createSequentialGroup()
						.addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
								.addGroup(javax.swing.GroupLayout.Alignment.TRAILING,
										jPanel8Layout.createSequentialGroup().addGap(0, 0, Short.MAX_VALUE)
												.addComponent(btn_addBook, javax.swing.GroupLayout.PREFERRED_SIZE, 128,
														javax.swing.GroupLayout.PREFERRED_SIZE)
												.addGap(14, 14, 14)
												.addComponent(btn_saveBook, javax.swing.GroupLayout.PREFERRED_SIZE, 118,
														javax.swing.GroupLayout.PREFERRED_SIZE)
												.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
												.addComponent(btn_editBook, javax.swing.GroupLayout.PREFERRED_SIZE, 119,
														javax.swing.GroupLayout.PREFERRED_SIZE)
												.addGap(177, 177, 177).addComponent(btn_refreshAction,
														javax.swing.GroupLayout.PREFERRED_SIZE, 154,
														javax.swing.GroupLayout.PREFERRED_SIZE))
								.addGroup(jPanel8Layout.createSequentialGroup().addGap(40, 40, 40)
										.addGroup(jPanel8Layout
												.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
												.addGroup(jPanel8Layout.createSequentialGroup().addGroup(jPanel8Layout
														.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
														.addComponent(jl_publishedDate).addComponent(jl_category)
														.addComponent(jl_stockQuantity)).addGap(18, 18, 18)
														.addGroup(jPanel8Layout
																.createParallelGroup(
																		javax.swing.GroupLayout.Alignment.LEADING)
																.addComponent(
																		jtf_stockQuantity)
																.addComponent(jtf_publishedDate)))
												.addGroup(
														jPanel8Layout.createSequentialGroup()
																.addGroup(jPanel8Layout.createParallelGroup(
																		javax.swing.GroupLayout.Alignment.LEADING)
																		.addComponent(jl_idBook)
																		.addComponent(jl_titleBook).addComponent(
																				jl_idCategory,
																				javax.swing.GroupLayout.PREFERRED_SIZE,
																				107,
																				javax.swing.GroupLayout.PREFERRED_SIZE))
																.addGroup(jPanel8Layout.createParallelGroup(
																		javax.swing.GroupLayout.Alignment.LEADING)
																		.addGroup(jPanel8Layout
																				.createSequentialGroup()
																				.addGap(22, 22,
																						22)
																				.addGroup(jPanel8Layout
																						.createParallelGroup(
																								javax.swing.GroupLayout.Alignment.LEADING,
																								false)
																						.addComponent(
																								jtf_titleBook,
																								javax.swing.GroupLayout.DEFAULT_SIZE,
																								267, Short.MAX_VALUE)
																						.addComponent(jtf_idBook)))
																		.addGroup(
																				javax.swing.GroupLayout.Alignment.TRAILING,
																				jPanel8Layout.createSequentialGroup()
																						.addPreferredGap(
																								javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																						.addGroup(jPanel8Layout
																								.createParallelGroup(
																										javax.swing.GroupLayout.Alignment.LEADING,
																										false)
																								.addComponent(
																										jtf_category)
																								.addComponent(
																										jcb_idCategory,
																										0, 263,
																										Short.MAX_VALUE))))))
										.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 43,
												Short.MAX_VALUE)
										.addGroup(jPanel8Layout
												.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
												.addComponent(jl_avaliableBook).addComponent(jl_importDate)
												.addComponent(jl_authorID).addComponent(jl_authorName)
												.addComponent(jl_description))
										.addGap(18, 18, 18)
										.addGroup(jPanel8Layout
												.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
												.addComponent(jtf_importDate, javax.swing.GroupLayout.DEFAULT_SIZE, 335,
														Short.MAX_VALUE)
												.addComponent(jtf_authorName, javax.swing.GroupLayout.DEFAULT_SIZE, 335,
														Short.MAX_VALUE)
												.addComponent(jtf_description, javax.swing.GroupLayout.DEFAULT_SIZE,
														335, Short.MAX_VALUE)
												.addComponent(jtf_avaliableBook).addComponent(jcb_authorID, 0,
														javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
										.addGap(64, 64, 64))
								.addGroup(jPanel8Layout.createSequentialGroup().addContainerGap()
										.addGroup(jPanel8Layout
												.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
												.addComponent(jScrollPane4, javax.swing.GroupLayout.Alignment.TRAILING)
												.addComponent(jSeparator2))))
						.addContainerGap()));
		jPanel8Layout
				.setVerticalGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(jPanel8Layout.createSequentialGroup().addContainerGap().addGroup(jPanel8Layout
								.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE).addComponent(jl_idBook)
								.addComponent(jtf_idBook, javax.swing.GroupLayout.PREFERRED_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
								.addComponent(jl_avaliableBook)
								.addComponent(jtf_avaliableBook, javax.swing.GroupLayout.PREFERRED_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
								.addGap(20, 20, 20)
								.addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
										.addComponent(jl_titleBook)
										.addComponent(jtf_titleBook, javax.swing.GroupLayout.PREFERRED_SIZE,
												javax.swing.GroupLayout.DEFAULT_SIZE,
												javax.swing.GroupLayout.PREFERRED_SIZE)
										.addComponent(jl_importDate).addComponent(jtf_importDate,
												javax.swing.GroupLayout.PREFERRED_SIZE,
												javax.swing.GroupLayout.DEFAULT_SIZE,
												javax.swing.GroupLayout.PREFERRED_SIZE))
								.addGap(18, 18, 18)
								.addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
										.addComponent(jl_idCategory)
										.addComponent(jcb_idCategory, javax.swing.GroupLayout.PREFERRED_SIZE,
												javax.swing.GroupLayout.DEFAULT_SIZE,
												javax.swing.GroupLayout.PREFERRED_SIZE)
										.addComponent(jl_authorID).addComponent(jcb_authorID,
												javax.swing.GroupLayout.PREFERRED_SIZE,
												javax.swing.GroupLayout.DEFAULT_SIZE,
												javax.swing.GroupLayout.PREFERRED_SIZE))
								.addGap(14, 14, 14)
								.addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
										.addComponent(jtf_category, javax.swing.GroupLayout.PREFERRED_SIZE,
												javax.swing.GroupLayout.DEFAULT_SIZE,
												javax.swing.GroupLayout.PREFERRED_SIZE)
										.addComponent(jl_category).addComponent(jl_authorName)
										.addComponent(jtf_authorName, javax.swing.GroupLayout.PREFERRED_SIZE,
												javax.swing.GroupLayout.DEFAULT_SIZE,
												javax.swing.GroupLayout.PREFERRED_SIZE))
								.addGap(18, 18, 18)
								.addGroup(jPanel8Layout
										.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
										.addGroup(jPanel8Layout.createSequentialGroup().addGroup(jPanel8Layout
												.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
												.addComponent(jtf_stockQuantity, javax.swing.GroupLayout.PREFERRED_SIZE,
														javax.swing.GroupLayout.DEFAULT_SIZE,
														javax.swing.GroupLayout.PREFERRED_SIZE)
												.addComponent(jl_stockQuantity).addComponent(jl_description))
												.addGap(21, 21, 21)
												.addGroup(jPanel8Layout
														.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
														.addComponent(jtf_publishedDate,
																javax.swing.GroupLayout.PREFERRED_SIZE,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																javax.swing.GroupLayout.PREFERRED_SIZE)
														.addComponent(jl_publishedDate)))
										.addComponent(jtf_description))
								.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
								.addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
								.addGroup(jPanel8Layout
										.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
										.addComponent(btn_addBook, javax.swing.GroupLayout.DEFAULT_SIZE,
												javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
										.addComponent(btn_refreshAction, javax.swing.GroupLayout.Alignment.TRAILING,
												javax.swing.GroupLayout.DEFAULT_SIZE,
												javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
										.addComponent(btn_saveBook, javax.swing.GroupLayout.Alignment.TRAILING,
												javax.swing.GroupLayout.DEFAULT_SIZE,
												javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
										.addComponent(btn_editBook, javax.swing.GroupLayout.Alignment.TRAILING,
												javax.swing.GroupLayout.DEFAULT_SIZE,
												javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
								.addGap(18, 18, 18).addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE,
										272, javax.swing.GroupLayout.PREFERRED_SIZE)
								.addGap(22, 22, 22)));

		jtp_QLS_QLS.addTab("Quản lý Sách", jPanel8);

		jPanel9.setBackground(new java.awt.Color(255, 255, 204));

		table_QLS_QLTLS.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
		table_QLS_QLTLS.setModel(new javax.swing.table.DefaultTableModel(new Object[][] {

		}, new String[] {

		}));
		table_QLS_QLTLS.setGridColor(new java.awt.Color(255, 255, 255));
		table_QLS_QLTLS.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseClicked(java.awt.event.MouseEvent evt) {
				table_QLSQLTLSMouseClicked(evt);
			}
		});
		jScrollPane1.setViewportView(table_QLS_QLTLS);

		jl_category_TABQLS_QLTLS.setFont(new java.awt.Font("Times New Roman", 1, 22)); // NOI18N
		jl_category_TABQLS_QLTLS.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/book.png"))); // NOI18N
		jl_category_TABQLS_QLTLS.setText("Thông tin danh mục Sách:");

		jl_cID_TABQLSQLTLS.setFont(new java.awt.Font("Times New Roman", 1, 20)); // NOI18N
		jl_cID_TABQLSQLTLS.setText("Mã thể loại:");

		jl_cNameID_TABQLSQLTLS.setFont(new java.awt.Font("Times New Roman", 1, 20)); // NOI18N
		jl_cNameID_TABQLSQLTLS.setText("Tên thể loại:");

		jtf_cID_TabQLSQLTLS.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N

		jtf_cNameID_TabQLSQLTLS.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N

		btn_ThemDMSach.setBackground(new java.awt.Color(255, 204, 204));
		btn_ThemDMSach.setFont(new java.awt.Font("Times New Roman", 1, 20)); // NOI18N
		btn_ThemDMSach.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/them.png"))); // NOI18N
		btn_ThemDMSach.setText("Thêm");
		btn_ThemDMSach.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				btn_ThemDMSachActionPerformed(evt);
			}
		});

		btn_LuuDMSach.setBackground(new java.awt.Color(255, 204, 204));
		btn_LuuDMSach.setFont(new java.awt.Font("Times New Roman", 1, 20)); // NOI18N
		btn_LuuDMSach.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/luu.png"))); // NOI18N
		btn_LuuDMSach.setText("Lưu");
		btn_LuuDMSach.setEnabled(false);
		btn_LuuDMSach.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				btn_LuuDMSachActionPerformed(evt);
			}
		});

		btn_SuaDMSach.setBackground(new java.awt.Color(255, 204, 204));
		btn_SuaDMSach.setFont(new java.awt.Font("Times New Roman", 1, 20)); // NOI18N
		btn_SuaDMSach.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/exchange1.png"))); // NOI18N
		btn_SuaDMSach.setText("Sửa");
		btn_SuaDMSach.setEnabled(false);
		btn_SuaDMSach.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				btn_SuaDMSachActionPerformed(evt);
			}
		});

		txt_timkiemDMSach.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
		txt_timkiemDMSach.addKeyListener(new java.awt.event.KeyAdapter() {
			public void keyReleased(java.awt.event.KeyEvent evt) {
				txt_timkiemDMSachKeyReleased(evt);
			}
		});

		jLabel4.setFont(new java.awt.Font("Times New Roman", 1, 20)); // NOI18N
		jLabel4.setForeground(new java.awt.Color(0, 0, 102));
		jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/searching.png"))); // NOI18N
		jLabel4.setText("Tìm kiếm");

		btn_lammoi.setBackground(new java.awt.Color(255, 204, 204));
		btn_lammoi.setFont(new java.awt.Font("Times New Roman", 1, 20)); // NOI18N
		btn_lammoi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/lammoi.png"))); // NOI18N
		btn_lammoi.setText("Làm mới");
		btn_lammoi.setEnabled(false);
		btn_lammoi.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				btn_lammoiActionPerformed(evt);
			}
		});

		javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
		jPanel9.setLayout(jPanel9Layout);
		jPanel9Layout.setHorizontalGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(jPanel9Layout.createSequentialGroup().addGroup(jPanel9Layout
						.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(jPanel9Layout.createSequentialGroup().addGap(85, 85, 85).addGroup(jPanel9Layout
								.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
								.addGroup(jPanel9Layout.createSequentialGroup().addComponent(jl_cID_TABQLSQLTLS).addGap(18, 18, 18)
										.addComponent(jtf_cID_TabQLSQLTLS))
								.addGroup(jPanel9Layout.createSequentialGroup().addComponent(jl_cNameID_TABQLSQLTLS).addGap(15, 15, 15)
										.addComponent(jtf_cNameID_TabQLSQLTLS, javax.swing.GroupLayout.PREFERRED_SIZE, 279,
												javax.swing.GroupLayout.PREFERRED_SIZE)))
								.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 22,
										Short.MAX_VALUE))
						.addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel9Layout.createSequentialGroup()
								.addContainerGap(123, Short.MAX_VALUE)
								.addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
										.addGroup(javax.swing.GroupLayout.Alignment.TRAILING,
												jPanel9Layout.createSequentialGroup().addComponent(jl_category_TABQLS_QLTLS).addGap(123,
														123, 123))
										.addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel9Layout
												.createSequentialGroup()
												.addGroup(jPanel9Layout
														.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
														.addComponent(btn_ThemDMSach,
																javax.swing.GroupLayout.PREFERRED_SIZE, 136,
																javax.swing.GroupLayout.PREFERRED_SIZE)
														.addComponent(btn_SuaDMSach,
																javax.swing.GroupLayout.PREFERRED_SIZE, 136,
																javax.swing.GroupLayout.PREFERRED_SIZE))
												.addGap(18, 18, 18)
												.addGroup(jPanel9Layout
														.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
														.addComponent(btn_lammoi).addComponent(btn_LuuDMSach,
																javax.swing.GroupLayout.PREFERRED_SIZE, 147,
																javax.swing.GroupLayout.PREFERRED_SIZE))
												.addGap(85, 85, 85)))))
						.addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
								.addGroup(jPanel9Layout.createSequentialGroup().addGap(5, 5, 5)
										.addComponent(txt_timkiemDMSach, javax.swing.GroupLayout.PREFERRED_SIZE, 345,
												javax.swing.GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
										.addComponent(jLabel4))
								.addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 482,
										javax.swing.GroupLayout.PREFERRED_SIZE))
						.addContainerGap(42, Short.MAX_VALUE)));
		jPanel9Layout.setVerticalGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(jPanel9Layout.createSequentialGroup().addGroup(jPanel9Layout
						.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(jPanel9Layout.createSequentialGroup().addGap(92, 92, 92)
								.addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
										.addComponent(txt_timkiemDMSach, javax.swing.GroupLayout.PREFERRED_SIZE, 37,
												javax.swing.GroupLayout.PREFERRED_SIZE)
										.addComponent(jLabel4))
								.addGap(38, 38, 38).addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE,
										352, javax.swing.GroupLayout.PREFERRED_SIZE))
						.addGroup(jPanel9Layout.createSequentialGroup().addGap(114, 114, 114).addComponent(jl_category_TABQLS_QLTLS)
								.addGap(44, 44, 44)
								.addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
										.addComponent(jl_cID_TABQLSQLTLS).addComponent(jtf_cID_TabQLSQLTLS,
												javax.swing.GroupLayout.PREFERRED_SIZE,
												javax.swing.GroupLayout.DEFAULT_SIZE,
												javax.swing.GroupLayout.PREFERRED_SIZE))
								.addGap(47, 47, 47)
								.addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
										.addComponent(jl_cNameID_TABQLSQLTLS).addComponent(jtf_cNameID_TabQLSQLTLS,
												javax.swing.GroupLayout.PREFERRED_SIZE,
												javax.swing.GroupLayout.DEFAULT_SIZE,
												javax.swing.GroupLayout.PREFERRED_SIZE))
								.addGap(43, 43, 43)
								.addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
										.addComponent(btn_ThemDMSach, javax.swing.GroupLayout.PREFERRED_SIZE, 40,
												javax.swing.GroupLayout.PREFERRED_SIZE)
										.addComponent(btn_LuuDMSach, javax.swing.GroupLayout.PREFERRED_SIZE, 40,
												javax.swing.GroupLayout.PREFERRED_SIZE))
								.addGap(18, 18, 18)
								.addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
										.addComponent(btn_SuaDMSach, javax.swing.GroupLayout.PREFERRED_SIZE, 38,
												javax.swing.GroupLayout.PREFERRED_SIZE)
										.addComponent(btn_lammoi, javax.swing.GroupLayout.PREFERRED_SIZE, 38,
												javax.swing.GroupLayout.PREFERRED_SIZE))))
						.addContainerGap(128, Short.MAX_VALUE)));

		jtp_QLS_QLS.addTab("Quản lý Thể Loại Sách", jPanel9);

		javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
		jPanel4.setLayout(jPanel4Layout);
		jPanel4Layout.setHorizontalGroup(
				jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addComponent(jtp_QLS_QLS));
		jPanel4Layout.setVerticalGroup(
				jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addComponent(jtp_QLS_QLS));

		jtp_main.addTab("          QUẢN LÝ SÁCH  ",
				new javax.swing.ImageIcon(getClass().getResource("/Images/books.png")), jPanel4); // NOI18N

		jPK_QuanLyPhieuMuon.setBackground(new java.awt.Color(204, 204, 255));

		jtp_QuanLiMuonTra.setForeground(new java.awt.Color(51, 0, 102));
		jtp_QuanLiMuonTra.setFont(new java.awt.Font("Times New Roman", 1, 20)); // NOI18N

		Panel_DanhSachPM.setBackground(new java.awt.Color(255, 255, 204));
		Panel_DanhSachPM.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N

		table_listLoan.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
		table_listLoan.setModel(new javax.swing.table.DefaultTableModel(new Object[][] {

		}, new String[] {

		}));
		table_listLoan.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseClicked(java.awt.event.MouseEvent evt) {
				tableListLoanMouseClicked(evt);
			}
		});
		jSPK_DSPM.setViewportView(table_listLoan);

		btnK_themPM.setBackground(new java.awt.Color(255, 204, 204));
		btnK_themPM.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
		btnK_themPM.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/plus.png"))); // NOI18N
		btnK_themPM.setText("Thêm phiếu mượn");
		btnK_themPM.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				btnK_themPMActionPerformed(evt);
			}
		});

		jl_titleTab_QLPM.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
		jl_titleTab_QLPM.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/bill.png"))); // NOI18N
		jl_titleTab_QLPM.setText("CÁC PHIẾU MƯỢN ĐÃ ĐĂNG KÝ");

		javax.swing.GroupLayout Panel_DanhSachPMLayout = new javax.swing.GroupLayout(Panel_DanhSachPM);
		Panel_DanhSachPM.setLayout(Panel_DanhSachPMLayout);
		Panel_DanhSachPMLayout.setHorizontalGroup(Panel_DanhSachPMLayout
				.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(Panel_DanhSachPMLayout.createSequentialGroup().addContainerGap(50, Short.MAX_VALUE)
						.addGroup(Panel_DanhSachPMLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
								.addComponent(jSPK_DSPM, javax.swing.GroupLayout.Alignment.TRAILING,
										javax.swing.GroupLayout.PREFERRED_SIZE, 978,
										javax.swing.GroupLayout.PREFERRED_SIZE)
								.addComponent(btnK_themPM, javax.swing.GroupLayout.Alignment.TRAILING))
						.addGap(29, 29, 29))
				.addGroup(Panel_DanhSachPMLayout.createSequentialGroup().addGap(316, 316, 316)
						.addComponent(jl_titleTab_QLPM, javax.swing.GroupLayout.PREFERRED_SIZE, 432,
								javax.swing.GroupLayout.PREFERRED_SIZE)
						.addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));
		Panel_DanhSachPMLayout
				.setVerticalGroup(Panel_DanhSachPMLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(Panel_DanhSachPMLayout.createSequentialGroup().addGap(60, 60, 60)
								.addComponent(jl_titleTab_QLPM, javax.swing.GroupLayout.PREFERRED_SIZE, 46,
										javax.swing.GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
								.addComponent(jSPK_DSPM, javax.swing.GroupLayout.PREFERRED_SIZE, 392,
										javax.swing.GroupLayout.PREFERRED_SIZE)
								.addGap(18, 18, 18)
								.addComponent(btnK_themPM, javax.swing.GroupLayout.PREFERRED_SIZE, 39,
										javax.swing.GroupLayout.PREFERRED_SIZE)
								.addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));

		jtp_QuanLiMuonTra.addTab("Danh sách phiếu mượn", Panel_DanhSachPM);

		Panel_ChiTietPM.setBackground(new java.awt.Color(255, 255, 204));

		jPanel12.setBackground(new java.awt.Color(204, 204, 255));
		jPanel12.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));

		jLabel26.setFont(new java.awt.Font("Times New Roman", 1, 20)); // NOI18N
		jLabel26.setText("Mã phiếu mượn:");

		jLabel27.setFont(new java.awt.Font("Times New Roman", 1, 20)); // NOI18N
		jLabel27.setText("Mã sách:");

		jLabel28.setFont(new java.awt.Font("Times New Roman", 1, 20)); // NOI18N
		jLabel28.setText("Ngày mượn :");

		jLabel30.setFont(new java.awt.Font("Times New Roman", 1, 20)); // NOI18N
		jLabel30.setText("Tình trạng sách:");

		jdc_loan_date.setDateFormatString("yyyy-MM-dd");
		jdc_loan_date.setFont(new java.awt.Font("Times New Roman", 0, 20)); // NOI18N

		K_maPM.setFont(new java.awt.Font("Times New Roman", 0, 20)); // NOI18N

		jtf_BookID.setFont(new java.awt.Font("Times New Roman", 0, 20)); // NOI18N

		jcb_stateBook.setFont(new java.awt.Font("Times New Roman", 0, 20)); // NOI18N
		jcb_stateBook.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Chưa trả", "Đã trả" }));
		jcb_stateBook.setSelectedIndex(-1);

		jLabel38.setFont(new java.awt.Font("Times New Roman", 1, 16)); // NOI18N
		jLabel38.setForeground(new java.awt.Color(102, 0, 0));
		jLabel38.setText("* Chú ý: - Nếu đã trả sách, tiền phạt được xác định như đã ghi trên phiếu");

		jLabel39.setFont(new java.awt.Font("Times New Roman", 1, 16)); // NOI18N
		jLabel39.setForeground(new java.awt.Color(102, 0, 0));
		jLabel39.setText("- Nếu chưa trả, tiền phạt là số ngày trễ * 50% giá trị của sách");

		javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
		jPanel12.setLayout(jPanel12Layout);
		jPanel12Layout.setHorizontalGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(jPanel12Layout.createSequentialGroup().addGroup(jPanel12Layout
						.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(jPanel12Layout.createSequentialGroup().addGap(49, 49, 49)
								.addComponent(jLabel26, javax.swing.GroupLayout.PREFERRED_SIZE, 167,
										javax.swing.GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED))
						.addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel12Layout.createSequentialGroup()
								.addContainerGap()
								.addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
										.addGroup(javax.swing.GroupLayout.Alignment.TRAILING,
												jPanel12Layout.createSequentialGroup()
														.addComponent(jLabel27, javax.swing.GroupLayout.PREFERRED_SIZE,
																94, javax.swing.GroupLayout.PREFERRED_SIZE)
														.addPreferredGap(
																javax.swing.LayoutStyle.ComponentPlacement.UNRELATED))
										.addGroup(javax.swing.GroupLayout.Alignment.TRAILING,
												jPanel12Layout.createSequentialGroup().addComponent(jLabel28).addGap(28,
														28, 28)))))
						.addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
								.addComponent(jtf_BookID, javax.swing.GroupLayout.DEFAULT_SIZE, 252, Short.MAX_VALUE)
								.addComponent(K_maPM).addComponent(jdc_loan_date, javax.swing.GroupLayout.DEFAULT_SIZE,
										252, Short.MAX_VALUE))
						.addGroup(
								jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
										.addGroup(jPanel12Layout.createSequentialGroup().addGap(68, 68, 68)
												.addComponent(jLabel30)
												.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
												.addComponent(jcb_stateBook, javax.swing.GroupLayout.PREFERRED_SIZE,
														244, javax.swing.GroupLayout.PREFERRED_SIZE))
										.addGroup(jPanel12Layout.createSequentialGroup().addGap(67, 67, 67)
												.addGroup(jPanel12Layout
														.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
														.addGroup(jPanel12Layout
																.createParallelGroup(
																		javax.swing.GroupLayout.Alignment.TRAILING)
																.addComponent(jLabel39,
																		javax.swing.GroupLayout.PREFERRED_SIZE, 292,
																		javax.swing.GroupLayout.PREFERRED_SIZE)
																.addComponent(jLabel38))
														.addGroup(jPanel12Layout.createSequentialGroup()
																.addGap(61, 61, 61).addComponent(jLabel40)))))
						.addContainerGap()));
		jPanel12Layout
				.setVerticalGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(jPanel12Layout.createSequentialGroup().addGap(43, 43, 43).addGroup(jPanel12Layout
								.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE).addComponent(jLabel26)
								.addComponent(K_maPM, javax.swing.GroupLayout.PREFERRED_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
								.addComponent(jLabel30)
								.addComponent(jcb_stateBook, javax.swing.GroupLayout.PREFERRED_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
								.addGap(18, 18, 18)
								.addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
										.addGroup(jPanel12Layout
												.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
												.addComponent(jLabel27).addComponent(jtf_BookID,
														javax.swing.GroupLayout.PREFERRED_SIZE,
														javax.swing.GroupLayout.DEFAULT_SIZE,
														javax.swing.GroupLayout.PREFERRED_SIZE))
										.addGroup(jPanel12Layout.createSequentialGroup().addComponent(jLabel38)
												.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
												.addComponent(jLabel39)))
								.addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
										.addGroup(jPanel12Layout.createSequentialGroup().addGap(9, 9, 9)
												.addGroup(jPanel12Layout
														.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
														.addComponent(jLabel28).addComponent(jdc_loan_date,
																javax.swing.GroupLayout.PREFERRED_SIZE, 33,
																javax.swing.GroupLayout.PREFERRED_SIZE)))
										.addGroup(jPanel12Layout.createSequentialGroup()
												.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
												.addComponent(jLabel40)))
								.addContainerGap(43, Short.MAX_VALUE)));

		jPanel13.setBackground(new java.awt.Color(255, 255, 204));

		table_LoanDetail.setFont(new java.awt.Font("Times New Roman", 0, 20)); // NOI18N
		table_LoanDetail.setModel(new javax.swing.table.DefaultTableModel(new Object[][] {

		}, new String[] {

		}));
		table_LoanDetail.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseClicked(java.awt.event.MouseEvent evt) {
				table_LoanDetailMouseClicked(evt);
			}
		});
		jScrollPane6.setViewportView(table_LoanDetail);

		btnK_veTrangTruoc.setBackground(new java.awt.Color(255, 204, 204));
		btnK_veTrangTruoc.setFont(new java.awt.Font("Times New Roman", 1, 20)); // NOI18N
		btnK_veTrangTruoc.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/icon_previous.png"))); // NOI18N
		btnK_veTrangTruoc.setText("Về trang trước");
		btnK_veTrangTruoc.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				btnK_veTrangTruocActionPerformed(evt);
			}
		});

		javax.swing.GroupLayout jPanel13Layout = new javax.swing.GroupLayout(jPanel13);
		jPanel13.setLayout(jPanel13Layout);
		jPanel13Layout.setHorizontalGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(javax.swing.GroupLayout.Alignment.TRAILING,
						jPanel13Layout.createSequentialGroup().addContainerGap(25, Short.MAX_VALUE)
								.addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
										.addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 1022,
												javax.swing.GroupLayout.PREFERRED_SIZE)
										.addComponent(btnK_veTrangTruoc, javax.swing.GroupLayout.PREFERRED_SIZE, 240,
												javax.swing.GroupLayout.PREFERRED_SIZE))
								.addContainerGap()));
		jPanel13Layout.setVerticalGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(javax.swing.GroupLayout.Alignment.TRAILING,
						jPanel13Layout.createSequentialGroup()
								.addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 271,
										javax.swing.GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
								.addComponent(btnK_veTrangTruoc, javax.swing.GroupLayout.PREFERRED_SIZE, 46,
										javax.swing.GroupLayout.PREFERRED_SIZE)
								.addContainerGap()));

		btnK_suaChiTiet.setBackground(new java.awt.Color(255, 204, 204));
		btnK_suaChiTiet.setFont(new java.awt.Font("Times New Roman", 1, 20)); // NOI18N
		btnK_suaChiTiet.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/exchange1.png"))); // NOI18N
		btnK_suaChiTiet.setText("Sửa thông tin");
		btnK_suaChiTiet.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				btnK_suaChiTietActionPerformed(evt);
			}
		});

		btnK_luuChiTiet.setBackground(new java.awt.Color(255, 204, 204));
		btnK_luuChiTiet.setFont(new java.awt.Font("Times New Roman", 1, 20)); // NOI18N
		btnK_luuChiTiet.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/luu.png"))); // NOI18N
		btnK_luuChiTiet.setText("Lưu ");
		btnK_luuChiTiet.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				
			}
		});

		btnK_lamMoi.setBackground(new java.awt.Color(255, 204, 204));
		btnK_lamMoi.setFont(new java.awt.Font("Times New Roman", 1, 20)); // NOI18N
		btnK_lamMoi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/eraser.png"))); // NOI18N
		btnK_lamMoi.setText("Làm mới");
		btnK_lamMoi.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				btnK_lamMoiActionPerformed(evt);
			}
		});

		javax.swing.GroupLayout Panel_ChiTietPMLayout = new javax.swing.GroupLayout(Panel_ChiTietPM);
		Panel_ChiTietPM.setLayout(Panel_ChiTietPMLayout);
		Panel_ChiTietPMLayout
				.setHorizontalGroup(Panel_ChiTietPMLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
						.addComponent(jPanel12, javax.swing.GroupLayout.DEFAULT_SIZE,
								javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(jPanel13, javax.swing.GroupLayout.DEFAULT_SIZE,
								javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addGroup(Panel_ChiTietPMLayout.createSequentialGroup()
								.addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(btnK_suaChiTiet)
								.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
								.addComponent(btnK_luuChiTiet, javax.swing.GroupLayout.PREFERRED_SIZE, 118,
										javax.swing.GroupLayout.PREFERRED_SIZE)
								.addGap(256, 256, 256).addComponent(btnK_lamMoi, javax.swing.GroupLayout.PREFERRED_SIZE,
										162, javax.swing.GroupLayout.PREFERRED_SIZE)
								.addContainerGap()));
		Panel_ChiTietPMLayout.setVerticalGroup(Panel_ChiTietPMLayout
				.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(Panel_ChiTietPMLayout.createSequentialGroup()
						.addComponent(jPanel12, javax.swing.GroupLayout.PREFERRED_SIZE,
								javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
						.addGap(31, 31, 31)
						.addGroup(Panel_ChiTietPMLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
								.addComponent(btnK_suaChiTiet, javax.swing.GroupLayout.PREFERRED_SIZE, 42,
										javax.swing.GroupLayout.PREFERRED_SIZE)
								.addComponent(btnK_luuChiTiet, javax.swing.GroupLayout.PREFERRED_SIZE, 42,
										javax.swing.GroupLayout.PREFERRED_SIZE)
								.addComponent(btnK_lamMoi, javax.swing.GroupLayout.PREFERRED_SIZE, 42,
										javax.swing.GroupLayout.PREFERRED_SIZE))
						.addGap(18, 18, 18).addComponent(jPanel13, javax.swing.GroupLayout.DEFAULT_SIZE,
								javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));

		jtp_QuanLiMuonTra.addTab("Chi tiết phiếu mượn", Panel_ChiTietPM);

		javax.swing.GroupLayout jPK_QuanLyPhieuMuonLayout = new javax.swing.GroupLayout(jPK_QuanLyPhieuMuon);
		jPK_QuanLyPhieuMuon.setLayout(jPK_QuanLyPhieuMuonLayout);
		jPK_QuanLyPhieuMuonLayout.setHorizontalGroup(jPK_QuanLyPhieuMuonLayout
				.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addComponent(jtp_QuanLiMuonTra));
		jPK_QuanLyPhieuMuonLayout.setVerticalGroup(jPK_QuanLyPhieuMuonLayout
				.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addComponent(jtp_QuanLiMuonTra));

		// TAB THỐNG KÊ
		jtp_main.addTab(" QUẢN LÝ MƯỢN TRẢ ", new javax.swing.ImageIcon(getClass().getResource("/Images/exchange.png")),
				jPK_QuanLyPhieuMuon); // NOI18N

		jPanel6.setBackground(new java.awt.Color(204, 204, 255));

		jTabbedPane5.setForeground(new java.awt.Color(0, 0, 102));
		jTabbedPane5.setFont(new java.awt.Font("Times New Roman", 1, 20)); // NOI18N

		jPanel14.setBackground(new java.awt.Color(255, 255, 204));
		jPanel14.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N

		jLabel32.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
		jLabel32.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/book.png"))); // NOI18N
		jLabel32.setText("Lựa chọn: ");

		jcb_statUserFunction.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
		jcb_statUserFunction.setForeground(new java.awt.Color(0, 0, 153));
		String[] selectForStat = { "Bạn đọc chưa trả sách dù đã hết hạn", "Bạn đọc mượn chưa đóng tiền phạt",
				"Bạn đọc mượn nhiều nhất" };
		jcb_statUserFunction.setModel(new javax.swing.DefaultComboBoxModel<>(selectForStat));
		jcb_statUserFunction.addItemListener(new java.awt.event.ItemListener() {
			public void itemStateChanged(java.awt.event.ItemEvent evt) {
				statUserFuncForTab_THONGKE(evt);
			}
		});

		tableStatUserTB.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
		tableStatUserTB.setModel(new javax.swing.table.DefaultTableModel(new Object[][] {

		}, new String[] {

		}));
		jScrollPane7.setViewportView(tableStatUserTB);

		jLabel31.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
		jLabel31.setForeground(new java.awt.Color(204, 0, 0));
		jLabel31.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/open-book.png"))); // NOI18N
		jLabel31.setText("Lựa chọn thông tin bạn muốn thống kê!");

		javax.swing.GroupLayout jPanel14Layout = new javax.swing.GroupLayout(jPanel14);
		jPanel14.setLayout(jPanel14Layout);
		jPanel14Layout.setHorizontalGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(jPanel14Layout.createSequentialGroup()
						.addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
								.addGroup(jPanel14Layout.createSequentialGroup().addGap(146, 146, 146)
										.addComponent(jLabel32).addGap(26, 26, 26)
										.addComponent(jcb_statUserFunction, javax.swing.GroupLayout.PREFERRED_SIZE, 308,
												javax.swing.GroupLayout.PREFERRED_SIZE))
								.addGroup(jPanel14Layout.createSequentialGroup().addGap(54, 54, 54)
										.addGroup(jPanel14Layout
												.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
												.addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 956,
														javax.swing.GroupLayout.PREFERRED_SIZE)
												.addComponent(jLabel31, javax.swing.GroupLayout.PREFERRED_SIZE, 363,
														javax.swing.GroupLayout.PREFERRED_SIZE))))
						.addContainerGap(47, Short.MAX_VALUE)));
		jPanel14Layout.setVerticalGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(jPanel14Layout.createSequentialGroup().addGap(91, 91, 91)
						.addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
								.addComponent(jLabel32, javax.swing.GroupLayout.PREFERRED_SIZE, 34,
										javax.swing.GroupLayout.PREFERRED_SIZE)
								.addComponent(jcb_statUserFunction, javax.swing.GroupLayout.PREFERRED_SIZE, 34,
										javax.swing.GroupLayout.PREFERRED_SIZE))
						.addGap(18, 18, 18)
						.addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 336,
								javax.swing.GroupLayout.PREFERRED_SIZE)
						.addGap(18, 18, 18).addComponent(jLabel31).addContainerGap(120, Short.MAX_VALUE)));

		jTabbedPane5.addTab("Thông Kê Bạn Đọc", jPanel14);

		jPanel15.setBackground(new java.awt.Color(255, 255, 204));

		jLabel33.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
		jLabel33.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/book.png"))); // NOI18N
		jLabel33.setText("Lựa chọn:");

		jcb_statBookFunction.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
		jcb_statBookFunction.setForeground(new java.awt.Color(0, 0, 153));
		String[] selectForStatBook = { "Top 5 sách được mượn nhiều nhất hiện tại",
				"Các cuốn sách đang không có ai mượn", "Danh sách các sách được đặt trước từ NXB",
				"Sách tồn tại trong thư viện lâu nhất" };
		jcb_statBookFunction.setModel(new javax.swing.DefaultComboBoxModel<>(selectForStatBook));
		jcb_statBookFunction.addItemListener(new java.awt.event.ItemListener() {
			public void itemStateChanged(java.awt.event.ItemEvent evt) {
				statBookFuncForTab_THONGKE(evt);
			}
		});

		tableStatBook.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
		tableStatBook.setModel(new javax.swing.table.DefaultTableModel(new Object[][] {

		}, new String[] {

		}));
		jScrollPane8.setViewportView(tableStatBook);

		jLabel37.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
		jLabel37.setForeground(new java.awt.Color(204, 0, 0));
		jLabel37.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/open-book.png"))); // NOI18N
		jLabel37.setText("Lựa chọn thông tin bạn muốn thống kê!");

		javax.swing.GroupLayout jPanel15Layout = new javax.swing.GroupLayout(jPanel15);
		jPanel15.setLayout(jPanel15Layout);
		jPanel15Layout.setHorizontalGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(jPanel15Layout.createSequentialGroup().addGap(109, 109, 109).addGroup(jPanel15Layout
						.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
						.addComponent(jLabel37, javax.swing.GroupLayout.PREFERRED_SIZE, 363,
								javax.swing.GroupLayout.PREFERRED_SIZE)
						.addGroup(jPanel15Layout.createSequentialGroup().addComponent(jLabel33).addGap(18, 18, 18)
								.addComponent(jcb_statBookFunction, javax.swing.GroupLayout.PREFERRED_SIZE, 409,
										javax.swing.GroupLayout.PREFERRED_SIZE))
						.addComponent(jScrollPane8, javax.swing.GroupLayout.PREFERRED_SIZE, 897,
								javax.swing.GroupLayout.PREFERRED_SIZE))
						.addContainerGap(51, Short.MAX_VALUE)));
		jPanel15Layout.setVerticalGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(jPanel15Layout.createSequentialGroup().addContainerGap(150, Short.MAX_VALUE)
						.addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
								.addComponent(jLabel33, javax.swing.GroupLayout.PREFERRED_SIZE, 34,
										javax.swing.GroupLayout.PREFERRED_SIZE)
								.addComponent(jcb_statBookFunction, javax.swing.GroupLayout.PREFERRED_SIZE, 34,
										javax.swing.GroupLayout.PREFERRED_SIZE))
						.addGap(18, 18, 18)
						.addComponent(jScrollPane8, javax.swing.GroupLayout.PREFERRED_SIZE, 281,
								javax.swing.GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED).addComponent(jLabel37)
						.addGap(123, 123, 123)));

		jTabbedPane5.addTab("Thống Kê Sách", jPanel15);

		jPanel16.setBackground(new java.awt.Color(255, 255, 204));

		jLabel34.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
		jLabel34.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/book.png"))); // NOI18N
		jLabel34.setText("Lựa chọn:");

		cbb_chucNangThongKe2.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
		cbb_chucNangThongKe2.setForeground(new java.awt.Color(0, 0, 153));
		cbb_chucNangThongKe2
				.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Tổng tiền phạt theo tháng" }));

		tableStatFineCollection.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
		tableStatFineCollection.setModel(new javax.swing.table.DefaultTableModel(new Object[][] {

		}, new String[] {

		}));
		jScrollPane9.setViewportView(tableStatFineCollection);

		jLabel41.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
		jLabel41.setForeground(new java.awt.Color(204, 0, 0));
		jLabel41.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/open-book.png"))); // NOI18N
		jLabel41.setText("Lựa chọn thông tin bạn muốn thống kê!");

		javax.swing.GroupLayout jPanel16Layout = new javax.swing.GroupLayout(jPanel16);
		jPanel16.setLayout(jPanel16Layout);
		jPanel16Layout.setHorizontalGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(jPanel16Layout.createSequentialGroup().addGap(77, 77, 77)
						.addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
								.addComponent(jLabel41, javax.swing.GroupLayout.PREFERRED_SIZE, 363,
										javax.swing.GroupLayout.PREFERRED_SIZE)
								.addComponent(jScrollPane9, javax.swing.GroupLayout.PREFERRED_SIZE, 940,
										javax.swing.GroupLayout.PREFERRED_SIZE)
								.addGroup(jPanel16Layout.createSequentialGroup().addComponent(jLabel34)
										.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
										.addComponent(cbb_chucNangThongKe2, javax.swing.GroupLayout.PREFERRED_SIZE, 352,
												javax.swing.GroupLayout.PREFERRED_SIZE)))
						.addContainerGap(40, Short.MAX_VALUE)));
		jPanel16Layout.setVerticalGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(jPanel16Layout.createSequentialGroup().addGap(105, 105, 105)
						.addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
								.addComponent(jLabel34, javax.swing.GroupLayout.PREFERRED_SIZE, 34,
										javax.swing.GroupLayout.PREFERRED_SIZE)
								.addComponent(cbb_chucNangThongKe2, javax.swing.GroupLayout.PREFERRED_SIZE, 34,
										javax.swing.GroupLayout.PREFERRED_SIZE))
						.addGap(30, 30, 30)
						.addComponent(jScrollPane9, javax.swing.GroupLayout.PREFERRED_SIZE, 260,
								javax.swing.GroupLayout.PREFERRED_SIZE)
						.addGap(18, 18, 18).addComponent(jLabel41).addContainerGap(170, Short.MAX_VALUE)));

		jTabbedPane5.addTab("Thông Kê Tiền Phạt", jPanel16);

		javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
		jPanel6.setLayout(jPanel6Layout);
		jPanel6Layout.setHorizontalGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addComponent(jTabbedPane5));
		jPanel6Layout.setVerticalGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addComponent(jTabbedPane5));

		jtp_main.addTab("                   THỐNG KÊ ",
				new javax.swing.ImageIcon(getClass().getResource("/Images/statistics.png")), jPanel6); // NOI18N

		jPanel7.setBackground(new java.awt.Color(204, 204, 255));

		timkiem.setBackground(new java.awt.Color(255, 255, 204));

		jLabel15.setFont(new java.awt.Font("Times New Roman", 1, 20)); // NOI18N
		jLabel15.setForeground(new java.awt.Color(0, 0, 255));
		jLabel15.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/searching.png"))); // NOI18N
		jLabel15.setText("Tìm kiếm sách:");
		textboxsearch.addKeyListener(new java.awt.event.KeyAdapter() {
			public void keyReleased(java.awt.event.KeyEvent evt) {
				textboxsearchKeyReleased(evt);
			}
		});

		jButton11.setBackground(new java.awt.Color(255, 204, 204));
		jButton11.setFont(new java.awt.Font("Times New Roman", 1, 18));
		jButton11.setText("Tìm kiếm");
		jButton11.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				findBookAction(evt);
			}
		});

		jLabel16.setFont(new java.awt.Font("Times New Roman", 0, 20)); // NOI18N
		jLabel16.setForeground(new java.awt.Color(0, 0, 102));
		jLabel16.setText("Lọc theo thể loại sách:");

		jLabel35.setFont(new java.awt.Font("Times New Roman", 0, 20)); // NOI18N
		jLabel35.setForeground(new java.awt.Color(0, 0, 102));
		jLabel35.setText("Lọc theo tên Tác Giả:");

		jComboBox3.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
		CategoryService cs = new CategoryService();
		List<String> categoryNames = cs.getCategoryNames();
		categoryNames.add(0, "Chọn tên Thể Loại");
		String[] categoryNameModel = categoryNames.toArray(new String[0]);
		jComboBox3.setModel(new javax.swing.DefaultComboBoxModel<>(categoryNameModel));
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

		tableSearchSach.setFont(new java.awt.Font("Times New Roman", 0, 20));
		tableSearchSach.setModel(new javax.swing.table.DefaultTableModel(new Object[][] {

		}, new String[] { "ID", "Tên sách", "Tác giả", "Thể loại", "Mô tả", "Ngày xuất bản", "Ngày nhập",
				"Số lượng" }));
		jScrollPane5.setViewportView(tableSearchSach);

		javax.swing.GroupLayout timkiemLayout = new javax.swing.GroupLayout(timkiem);
		timkiem.setLayout(timkiemLayout);
		timkiemLayout.setHorizontalGroup(timkiemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(timkiemLayout.createSequentialGroup().addGap(44, 44, 44).addGroup(timkiemLayout
						.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addComponent(jScrollPane5)
						.addGroup(timkiemLayout.createSequentialGroup()
								.addGroup(
										timkiemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
												.addComponent(jLabel35)
												.addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 184,
														javax.swing.GroupLayout.PREFERRED_SIZE)
												.addComponent(jLabel15))
								.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
								.addGroup(timkiemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
										.addGroup(timkiemLayout.createSequentialGroup()
												.addComponent(textboxsearch, javax.swing.GroupLayout.PREFERRED_SIZE,
														353, javax.swing.GroupLayout.PREFERRED_SIZE)
												.addGap(18, 18, 18).addComponent(jButton11,
														javax.swing.GroupLayout.PREFERRED_SIZE, 134,
														javax.swing.GroupLayout.PREFERRED_SIZE))
										.addGroup(timkiemLayout
												.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
												.addComponent(jComboBox4, javax.swing.GroupLayout.Alignment.LEADING, 0,
														javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
												.addComponent(jComboBox3, javax.swing.GroupLayout.Alignment.LEADING,
														javax.swing.GroupLayout.PREFERRED_SIZE, 247,
														javax.swing.GroupLayout.PREFERRED_SIZE)))
								.addGap(0, 315, Short.MAX_VALUE)))
						.addContainerGap()));
		timkiemLayout.setVerticalGroup(timkiemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(timkiemLayout.createSequentialGroup().addGap(58, 58, 58)
						.addGroup(timkiemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
								.addComponent(jButton11, javax.swing.GroupLayout.DEFAULT_SIZE, 34, Short.MAX_VALUE)
								.addComponent(textboxsearch)
								.addComponent(jLabel15, javax.swing.GroupLayout.Alignment.TRAILING))
						.addGap(36, 36, 36)
						.addGroup(timkiemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
								.addComponent(jLabel16).addComponent(jComboBox3, javax.swing.GroupLayout.PREFERRED_SIZE,
										35, javax.swing.GroupLayout.PREFERRED_SIZE))
						.addGroup(timkiemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
								.addGroup(
										timkiemLayout.createSequentialGroup().addGap(27, 27, 27).addComponent(jLabel35))
								.addGroup(timkiemLayout.createSequentialGroup().addGap(18, 18, 18)
										.addComponent(jComboBox4)))
						.addGap(18, 18, 18).addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 412,
								javax.swing.GroupLayout.PREFERRED_SIZE)
						.addContainerGap(43, Short.MAX_VALUE)));

		javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
		jPanel7.setLayout(jPanel7Layout);
		jPanel7Layout.setHorizontalGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGap(0, 1060, Short.MAX_VALUE)
				.addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addComponent(
						timkiem, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE,
						javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));
		jPanel7Layout.setVerticalGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGap(0, 687, Short.MAX_VALUE)
				.addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addComponent(
						timkiem, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE,
						javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));

		jtp_main.addTab("                      TRA CỨU",
				new javax.swing.ImageIcon(getClass().getResource("/Images/research.png")), jPanel7); // NOI18N

		javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
		jPanel2.setLayout(jPanel2Layout);
		jPanel2Layout.setHorizontalGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addComponent(jtp_main, javax.swing.GroupLayout.DEFAULT_SIZE, 1367, Short.MAX_VALUE));
		jPanel2Layout.setVerticalGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addComponent(jtp_main, javax.swing.GroupLayout.DEFAULT_SIZE, 692, Short.MAX_VALUE));

		jtp_main.getAccessibleContext().setAccessibleDescription("");

		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		layout.setHorizontalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE,
						Short.MAX_VALUE)
				.addGroup(javax.swing.GroupLayout.Alignment.TRAILING,
						layout.createSequentialGroup().addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE,
								javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE).addContainerGap()));
		layout.setVerticalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(layout.createSequentialGroup()
						.addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE,
								javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
						.addGap(5, 5, 5).addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE,
								javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));

		pack();
		setLocationRelativeTo(null);
	}// </editor-fold>//GEN-END:initComponents

	private void txt_timkiemDMSachKeyReleased(java.awt.event.KeyEvent evt) {// GEN-FIRST:event_txt_timkiemDMSachKeyReleased
		String query = txt_timkiemDMSach.getText();
		timkiem(query);
	}// GEN-LAST:event_txt_timkiemDMSachKeyReleased

	private void btn_SuaDMSachActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btn_SuaDMSachActionPerformed
		if (jtf_cID_TabQLSQLTLS.getText().trim().equals("") || jtf_cNameID_TabQLSQLTLS.getText().trim().equals("")) {
			JOptionPane.showMessageDialog(null, "Vui lòng chọn thông tin danh mục muốn sửa muốn sửa!");
		} else {
			int x = JOptionPane.showConfirmDialog(this, "Bạn có chắc chắn muốn thay đổi không?");
			if (x == JOptionPane.NO_OPTION) {
				return;
			} else {

				dtm.setRowCount(0);
				initTableDataFor_TableQLSQLTLS();
			}
		}
		disableInput_QLSQLTLS();
		jtf_cID_TabQLSQLTLS.setText("");
		jtf_cNameID_TabQLSQLTLS.setText("");
	}// GEN-LAST:event_btn_SuaDMSachActionPerformed

	private void btn_LuuDMSachActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btn_LuuDMSachActionPerformed
		if (jtf_cID_TabQLSQLTLS.getText().trim().equals("") || jtf_cNameID_TabQLSQLTLS.getText().trim().equals("")) {
			JOptionPane.showMessageDialog(null, "Bạn chưa nhập đủ thông tin!");
			jtf_cID_TabQLSQLTLS.setEnabled(false);
			jtf_cNameID_TabQLSQLTLS.setEnabled(false);
			jtf_cID_TabQLSQLTLS.setText("");
			jtf_cNameID_TabQLSQLTLS.setText("");
		} else {
			int x = JOptionPane.showConfirmDialog(this, "Bạn có chắc chắn muốn thêm không?");
			if (x == JOptionPane.NO_OPTION) {
				return;
			} else {

				dtm.setRowCount(0);
				initTableDataFor_TableQLSQLTLS();

				jtf_cID_TabQLSQLTLS.setEnabled(false);
				jtf_cNameID_TabQLSQLTLS.setEnabled(false);
				jtf_cID_TabQLSQLTLS.setText("");
				jtf_cNameID_TabQLSQLTLS.setText("");

			}
		}
		intiAuthorIDFor_TABQLSQLS();
		btn_LuuDMSach.setEnabled(false);
		btn_ThemDMSach.setEnabled(true);
		btn_SuaDMSach.setEnabled(false);

	}// GEN-LAST:event_btn_LuuDMSachActionPerformed

	private void btn_ThemDMSachActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btn_ThemDMSachActionPerformed
		jtf_cNameID_TabQLSQLTLS.requestFocus();
		enable_DM();
		btn_ThemDMSach.setEnabled(false);
		btn_LuuDMSach.setEnabled(true);
		btn_SuaDMSach.setEnabled(false);

		jtf_cID_TabQLSQLTLS.setText("");
		jtf_cNameID_TabQLSQLTLS.setText("");
	}// GEN-LAST:event_btn_ThemDMSachActionPerformed

	private void table_QLSQLTLSMouseClicked(java.awt.event.MouseEvent evt) {// GEN-FIRST:event_tbl_DMSachMouseClicked
		int selectedRow = table_QLS_QLTLS.getSelectedRow();
		jtf_cID_TabQLSQLTLS.setText((String.valueOf(table_QLS_QLTLS.getValueAt(selectedRow, 0))));
		jtf_cNameID_TabQLSQLTLS.setText((String) table_QLS_QLTLS.getValueAt(selectedRow, 1));
		jtf_cID_TabQLSQLTLS.setEnabled(true);
		jtf_cNameID_TabQLSQLTLS.setEnabled(true);
		btn_SuaDMSach.setEnabled(true);
		btn_ThemDMSach.setEnabled(false);
		btn_LuuDMSach.setEnabled(false);

	}// GEN-LAST:event_tbl_DMSachMouseClicked

	private void showAuthorNameActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_Hc_maDMActionPerformed
		try {
			int idAuthor = Integer.parseInt((String) jcb_authorID.getSelectedItem());
			ResultSet rs = authorService.getAuthorName(idAuthor);
			if (rs.next()) {
				jtf_authorName.setText(rs.getString("fullname"));
			}
		} catch (Exception e) {

		}
	}// GEN-LAST:event_Hc_maDMActionPerformed

	private void showCategoryActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_Hc_maTheLoaiActionPerformed
		try {
			int idCategory = Integer.parseInt((String) jcb_idCategory.getSelectedItem());
			ResultSet rs = bookService.getCategory(idCategory);
			if (rs.next()) {
				jtf_category.setText(rs.getString("name"));
			}
		} catch (Exception e) {
		}
	}// GEN-LAST:event_Hc_maTheLoaiActionPerformed

	private void refreshActionPerformed(java.awt.event.ActionEvent evt) {
		refreshInput_QLS();
		disableInput_QLS();
		btn_saveBook.setEnabled(false);
		btn_addBook.setEnabled(true);
		btn_editBook.setEnabled(false);
	}

	private void saveBookActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnH_luuSachActionPerformed
		if (jtf_titleBook.getText().trim().equals("") || jtf_category.getText().trim().equals("")
				|| jtf_authorName.getText().trim().equals("") || jtf_publishedDate.getText().trim().equals("")
				|| jtf_importDate.getText().trim().equals("")) {
			JOptionPane.showMessageDialog(null, "Bạn chưa nhập đủ thông tin!");
		} else {
			int x = JOptionPane.showConfirmDialog(this, "Bạn có chắc chắn muốn thêm không?");
			if (x == JOptionPane.NO_OPTION) {
				return;
			} else {

				// sach.setAnh(H_linkAnh.getText());
				;

				dtm_tableQLSQLS.setRowCount(0);
				initDataForTableQLSQLS();
			}
			refreshInput_QLS();
			btn_saveBook.setEnabled(false);
			btn_addBook.setEnabled(true);
			btn_editBook.setEnabled(false);
			// btnH_xoaSach.setEnabled(false);
		}
	}// GEN-LAST:event_btnH_luuSachActionPerformed

	private void editBookActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnH_suaSachActionPerformed
		if (jtf_titleBook.getText().trim().equals("") || jtf_category.getText().trim().equals("")
				|| jtf_authorName.getText().trim().equals("") || jtf_publishedDate.getText().trim().equals("")
				|| jtf_importDate.getText().trim().equals("")) {
			JOptionPane.showMessageDialog(null, "Vui lòng chọn thông tin sách muốn xóa!");
		} else {
			int x = JOptionPane.showConfirmDialog(this, "Bạn có chắc chắn muốn thay đổi không?");
			if (x == JOptionPane.NO_OPTION) {
				return;
			} else {


				dtm_tableQLSQLS.setRowCount(0);
				initDataForTableQLSQLS();
			}
		}
		btn_addBook.setEnabled(true);
		btn_editBook.setEnabled(false);
		btn_saveBook.setEnabled(false);
		disableInput_QLS();
	}// GEN-LAST:event_btnH_suaSachActionPerformed

	private void addNewBookActionPerformed(java.awt.event.ActionEvent evt) {
		enableInput_QLS();
		refreshInput_QLS();
		jtf_titleBook.requestFocus();
		btn_saveBook.setEnabled(true);
		btn_editBook.setEnabled(false);
		btn_addBook.setEnabled(false);
	}

	private void tableQLSQLSMouseClicked(java.awt.event.MouseEvent evt) {
		int selectedRow = table_QLS_QLS.getSelectedRow();
		enableInput_QLS();
		jtf_idBook.setText(String.valueOf(table_QLS_QLS.getValueAt(selectedRow, 0)));
		jtf_titleBook.setText(table_QLS_QLS.getValueAt(selectedRow, 1).toString());
		setValueForJCB_TABQLSQLS(String.valueOf(table_QLS_QLS.getValueAt(selectedRow, 0)));
		setValueFor_JTFAvaliable(String.valueOf(table_QLS_QLS.getValueAt(selectedRow, 0)));
		jtf_publishedDate.setText(table_QLS_QLS.getValueAt(selectedRow, 4).toString());
		jtf_stockQuantity.setText(String.valueOf(table_QLS_QLS.getValueAt(selectedRow, 5)));
		jtf_importDate.setText(table_QLS_QLS.getValueAt(selectedRow, 6).toString());
		jtf_description.setText(table_QLS_QLS.getValueAt(selectedRow, 7).toString());
		enableInput_QLS();
		btn_addBook.setEnabled(false);
		jtf_idBook.setEnabled(false);
		btn_editBook.setEnabled(true);
		btn_saveBook.setEnabled(false);
	}

	private void setValueFor_JTFAvaliable(String id) {
		// TODO Auto-generated method stub
		int bookID = Integer.valueOf(id);
		List<Integer> listID = new ArrayList<Integer>();
		try {
			ResultSet rs = bookService.getBookIDListNotBorrowed();
			while(rs.next()) { 
				listID.add(rs.getInt("id"));
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		if(!listID.contains(bookID)) { 
			jtf_avaliableBook.setText("Đã cho mượn");
		} else {  
			jtf_avaliableBook.setText("Chưa có ai mượn");
		}
	}

	private void setValueForJCB_TABQLSQLS(String id) {
		// TODO Auto-generated method stub
		int bookID = Integer.valueOf(id);
		int authorID = 0;
		int categoryID = 0;
		// chuyen bookID -> authorID
		try {
			ResultSet rs1 = authorService.getAuthorIDFromBookID(bookID);
			while(rs1.next()) {
				authorID = rs1.getInt("author_id");
			}
			ResultSet rs2 = categoryService.getCategoryIDFromBookID(bookID);
			while(rs2.next()) {
				categoryID = rs2.getInt("category_id");
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		// chuyen authorID -> index
		jcb_authorID.setSelectedIndex(authorID - 400 - 1);
		jcb_idCategory.setSelectedIndex(categoryID - 300 -1);
	}

	private void btnK_veTrangTruocActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnK_veTrangTruocActionPerformed

		jtp_QuanLiMuonTra.setSelectedIndex(0);
		Refresh_CTPM();

	}// GEN-LAST:event_btnK_veTrangTruocActionPerformed

	private void tableListLoanMouseClicked(java.awt.event.MouseEvent evt) {// GEN-FIRST:event_tblK_DSPMMouseClicked
		// TODO add your handling code here:
		jtp_QuanLiMuonTra.setSelectedIndex(1);
		int selectedRow = table_listLoan.getSelectedRow();
		K_maPM.setText(table_listLoan.getValueAt(selectedRow, 0).toString());
		initData_QLMT_CTPM();
	}// GEN-LAST:event_tblK_DSPMMouseClicked

	private void table_LoanDetailMouseClicked(java.awt.event.MouseEvent evt) {// GEN-FIRST:event_tblK_ChiTietMouseClicked
		// TODO add your handling code here:
		int selectedRow = table_LoanDetail.getSelectedRow();
		jtf_BookID.setText(table_LoanDetail.getValueAt(selectedRow, 1).toString());
		jtf_BookID.setEnabled(true);
		((JTextField) jdc_loan_date.getDateEditor().getUiComponent()).setText("");
		try {
			((JTextField) jdc_loan_date.getDateEditor().getUiComponent())
					.setText(table_LoanDetail.getValueAt(selectedRow, 4).toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		jdc_loan_date.setEnabled(true);
		jcb_stateBook.setEnabled(true);
		String s;
		try {
			s = (String) table_LoanDetail.getValueAt(selectedRow, 6);
			System.out.println(s.isEmpty());
		} catch (Exception e) {
			// TODO: handle exception
			s = "";
		}
		if (!s.isEmpty()) {
			jcb_stateBook.setSelectedIndex(1);
		} else {
			jcb_stateBook.setSelectedIndex(0);
		}
		btnK_suaChiTiet.setEnabled(true);
	}// GEN-LAST:event_tblK_ChiTietMouseClicked

	private void btnK_suaChiTietActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnK_suaChiTietActionPerformed
		// TODO add your handling code here:
		if (jdc_loan_date.getDate() == null) {
			jdc_loan_date.setEnabled(true);
			jcb_stateBook.setEnabled(true);
		} else {
			jdc_loan_date.setEnabled(false);
			jcb_stateBook.setEnabled(false);
		}

		btnK_luuChiTiet.setEnabled(true);
		btnK_suaChiTiet.setEnabled(false);
	}// GEN-LAST:event_btnK_suaChiTietActionPerformed


	private void btnK_lamMoiActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnK_lamMoiActionPerformed
		// TODO add your handling code here:
//        btnK_suaChiTiet.setEnabled(false);
//        ((JTextField)K_ngayThucTra.getDateEditor().getUiComponent()).setText("");
//        K_tienPhat.setText("");
//        K_ngayThucTra.setEnabled(false);
//        K_tinhTrangSach.setEnabled(false);
//        K_tienPhat.setEnabled(false);
		Refresh_CTPM();
	}// GEN-LAST:event_btnK_lamMoiActionPerformed

	private void btnK_themPMActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnK_themPMActionPerformed
		// TODO add your handling code here:
		this.setVisible(false);
	}// GEN-LAST:event_btnK_themPMActionPerformed

	private void showInfoUserWhenClickTableUserManage(java.awt.event.MouseEvent evt) {// GEN-FIRST:event_tableDocgiaMouseClicked
		DefaultTableModel dtm = (DefaultTableModel) tableUserTBManage.getModel();
		int selected = tableUserTBManage.getSelectedRow();
		if (selected != -1) {
			String id = dtm.getValueAt(selected, 0).toString();
			String fullname = dtm.getValueAt(selected, 1).toString();
			String email = dtm.getValueAt(selected, 2).toString();
			String phone = dtm.getValueAt(selected, 3).toString();
			String dob = dtm.getValueAt(selected, 4).toString();
			jtf_idUserTb.setEditable(false);
			jtf_userTBName.setText(fullname);
			jtf_dob.setText(dob);
			jtf_emailUserTB.setText(email);
			jtf_phoneNumber.setText(phone);
		} else {
			JOptionPane.showMessageDialog(null, "Vui Lòng Chọn Trước Khi khóa", "ERROR",
					JOptionPane.INFORMATION_MESSAGE);
		}
	}

	private void phoneNumberKeyPressed(java.awt.event.KeyEvent evt) {// GEN-FIRST:event_sdtKeyPressed
		int key = evt.getKeyCode();
		System.out.println(key);
		if ((key < 48 || key > 57) && key != 8 && (key < 96 || key > 105)) {
			if (key == 10) {
				jtf_dob.requestFocus();
			} else {
				JOptionPane.showMessageDialog(null, "Số Điện Thoại không Chứ Ký Tự Khác Số", "ERROR",
						JOptionPane.INFORMATION_MESSAGE);
				jtf_phoneNumber.setBorder(new LineBorder(Color.red, 1));
			}
		}
	}// GEN-LAST:event_sdtKeyPressed

	private void phoneNumberKeyTyped(java.awt.event.KeyEvent evt) {// GEN-FIRST:event_sdtKeyTyped
		String sodt = jtf_phoneNumber.getText();
		if (sodt.length() > 11 || sodt.length() < 9) {
			jtf_phoneNumber.setBorder(new LineBorder(Color.red, 1));
		} else {
			jtf_phoneNumber.setBorder(new LineBorder(Color.green, 1));
		}

	}// GEN-LAST:event_sdtKeyTyped

	private void addNewUserTBActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_themmoidgActionPerformed
		String madg = this.jtf_idUserTb.getText();
		String tendg = jtf_userTBName.getText();
		String email = jtf_emailUserTB.getText();
		String sdt = this.jtf_phoneNumber.getText();
		String ngaysinh = this.jtf_dob.getText();
		int gioitinh;

		if (madg.equals("") || tendg.equals("") || email.equals("") || sdt.equals("") || ngaysinh.equals("")) {
			JOptionPane.showMessageDialog(null, "Bạn chưa nhập đủ thông tin!");
			return;
		}

		if (gioitinhnam.isSelected())
			gioitinh = 1;
		else
			gioitinh = 2;

		DefaultTableModel tbadddg = (DefaultTableModel) tableUserTBManage.getModel();
		DefaultTableModel tbxttdg = (DefaultTableModel) tableUserTBShowInfo.getModel();
	}// GEN-LAST:event_themmoidgActionPerformed

	private void updatedgActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_updatedgActionPerformed
		DefaultTableModel tbxtt = (DefaultTableModel) tableUserTBManage.getModel();
		int selected = tableUserTBManage.getSelectedRow();
		if (selected != -1) {
			String madg = tbxtt.getValueAt(selected, 0).toString();
			String tenDg = jtf_userTBName.getText();
			String ngaySinh = jtf_dob.getText();
			int gioitinh;
			if (gioitinhnam.isSelected())
				gioitinh = 1;
			else
				gioitinh = 2;
			String email = jtf_emailUserTB.getText();
			String sdt = this.jtf_phoneNumber.getText();

				this.initDataForTabUserTB();
				JOptionPane.showMessageDialog(null, "Sữa Thành công");
			} else {
				JOptionPane.showMessageDialog(null, "Thông tin chưa chính xác, Sửa thất bại");
			}
			JOptionPane.showMessageDialog(null, "Vui Lòng Chọn Trước Khi khóa", "ERROR",
					JOptionPane.INFORMATION_MESSAGE);
	}// GEN-LAST:event_updatedgActionPerformed

	private void statUserFuncForTab_THONGKE(java.awt.event.ItemEvent evt) {// GEN-FIRST:event_cbb_chucNangThongKeItemStateChanged
		try {
			int index = jcb_statUserFunction.getSelectedIndex();
			if (index == 0) { // user chua tra sach
				List<UserTB> listUserHasntReturnBook = userTBService.getUserHasntReturnedBook();
				DefaultTableModel dtmStatUserTB = (DefaultTableModel) tableStatUserTB.getModel();
				dtmStatUserTB.setColumnCount(0);
				dtmStatUserTB.addColumn("Mã Tài Khoản");
				dtmStatUserTB.addColumn("Tên Tài Khoản");
				dtmStatUserTB.setRowCount(0);
				for (UserTB u : listUserHasntReturnBook) {
					dtmStatUserTB.addRow(new Object[] { u.getId(), u.getFullname() });
				}
			}
			if (index == 1) { // user chua dong tien phat
				ResultSet userStillInDebt = UserTBService.getUserStillInDebt();
				DefaultTableModel dtm = (DefaultTableModel) tableStatUserTB.getModel();
				dtm.setColumnCount(0);
				dtm.addColumn("Mã TK");
				dtm.addColumn("Tên");
				dtm.addColumn("Email");
				dtm.addColumn("SĐT");
				dtm.addColumn("Ngày bị phạt");
				dtm.addColumn("Số tiền chưa đóng");
				dtm.setRowCount(0);
				while (userStillInDebt.next()) {
					dtm.addRow(new Object[] { userStillInDebt.getInt("id"), userStillInDebt.getString("fullname"),
							userStillInDebt.getString("email"), userStillInDebt.getString("phone"),
							userStillInDebt.getString("create_date"), userStillInDebt.getDouble("fine_amount") });
				}
			}
			if (index == 2) { // top 5 user muon nhiu sach nhat
				ResultSet top5BorrowedUser = userTBService.getMostBorrowedUser();
				DefaultTableModel dtm = (DefaultTableModel) tableStatUserTB.getModel();
				dtm.setColumnCount(0);
				dtm.addColumn("Mã TK");
				dtm.addColumn("Tên TK");
				dtm.addColumn("Email");
				dtm.addColumn("SĐT");
				dtm.addColumn("Số lần mượn");
				dtm.setRowCount(0);
				while (top5BorrowedUser.next()) {
					dtm.addRow(new Object[] { top5BorrowedUser.getInt("id"), top5BorrowedUser.getString("fullname"),
							top5BorrowedUser.getString("email"), top5BorrowedUser.getString("phone"),
							top5BorrowedUser.getInt("solanmuon") });
				}
			}
		} catch (SQLException ex) {
			Logger.getLogger(AdminHome.class.getName()).log(Level.SEVERE, null, ex);
		}
	}// GEN-LAST:event_cbb_chucNangThongKeItemStateChanged

	private void statBookFuncForTab_THONGKE(java.awt.event.ItemEvent evt) {// GEN-FIRST:event_cbb_chucNangThongKe1ItemStateChanged
		try {
			int index = jcb_statBookFunction.getSelectedIndex();
			if (index == 0) {
				ResultSet list5BookMostBorrowed = bookService.get5BookMostBorrowed();
				DefaultTableModel sach = (DefaultTableModel) tableStatBook.getModel();
				sach.setColumnCount(0);
				sach.addColumn("Mã Sách");
				sach.addColumn("Tên Sách");
				sach.addColumn("Tác Giả");
				sach.addColumn("Thể Loại");
				sach.addColumn("Số Lần Mượn Sách");
				sach.setRowCount(0);
				while (list5BookMostBorrowed.next()) {
					sach.addRow(new Object[] { list5BookMostBorrowed.getInt("id"),
							list5BookMostBorrowed.getString("title"), list5BookMostBorrowed.getInt("author_id"),
							list5BookMostBorrowed.getInt("category_id"), list5BookMostBorrowed.getInt("So_lan_muon") });
				}
			}
			if (index == 1) {
				List<MyBook> listBookNotBorrowed = bookService.getBookIsNotBorrowingByUser();
				DefaultTableModel dtm = (DefaultTableModel) tableStatBook.getModel();
				dtm.setColumnCount(0);
				dtm.addColumn("ID");
				dtm.addColumn("Tên");
				dtm.addColumn("Mô tả");
				dtm.addColumn("Ngày XB");
				dtm.addColumn("Số lượng hiện có");
				dtm.setRowCount(0);
				for (MyBook myBook : listBookNotBorrowed) {
					dtm.addRow(new Object[] { myBook.getId(), myBook.getTitle(), myBook.getDescription(),
							myBook.getPublished_date(), myBook.getStock_quantity() });
				}
			}
			if (index == 2) {
				List<MyBook> listBookRegistration = bookService.getBookRegistration();
				DefaultTableModel dtm = (DefaultTableModel) tableStatBook.getModel();
				dtm.setColumnCount(0);
				dtm.addColumn("ID Sách");
				dtm.addColumn("Tên");
				dtm.addColumn("Tác giả");
				dtm.addColumn("Thể loại");
				dtm.addColumn("Mô tả");
				dtm.addColumn("Ngày XB");
				dtm.setRowCount(0);
				for (MyBook myBook : listBookRegistration) {
					dtm.addRow(new Object[] { myBook.getId(), myBook.getTitle(), myBook.getAuthor_id(),
							myBook.getCategory_id(), myBook.getDescription(), myBook.getPublished_date() });
				}
			}
			if (index == 3) {
				List<MyBook> books = bookService.getLongestBookInLib();
				DefaultTableModel dtm = (DefaultTableModel) tableStatBook.getModel();
				dtm.setColumnCount(0);
				dtm.addColumn("ID Sách");
				dtm.addColumn("Tên");
				dtm.addColumn("Tác giả");
				dtm.addColumn("Thể loại");
				dtm.addColumn("Mô tả");
				dtm.addColumn("Ngày XB");
				dtm.addColumn("Ngày nhập về");
				dtm.setRowCount(0);
				for (MyBook myBook : books) {
					dtm.addRow(new Object[] { myBook.getId(), myBook.getTitle(), myBook.getAuthor_id(),
							myBook.getCategory_id(), myBook.getDescription(), myBook.getPublished_date(),
							myBook.getImport_date() });
				}
			}
		} catch (SQLException ex) {
			Logger.getLogger(AdminHome.class.getName()).log(Level.SEVERE, null, ex);
		}
	}// GEN-LAST:event_cbb_chucNangThongKe1ItemStateChanged

	private void textboxsearchKeyReleased(java.awt.event.KeyEvent evt) {// GEN-FIRST:event_textboxsearchKeyReleased
		String keyword = jtf_search.getText();
		List<MyBook> allBook = bookDAO.getBookList();
		List<MyBook> result = new ArrayList<>();
		DefaultTableModel curTableModel = (DefaultTableModel) tableSearchSach.getModel();
		curTableModel.setRowCount(0);
		for (MyBook b : allBook) {
			if (b.getTitle().toLowerCase().contains(keyword.toLowerCase()))
				result.add(b);
		}
		for (MyBook b : result) {
			curTableModel.addRow(new Object[] { b.getId(), b.getTitle(), b.getAuthor_id(), b.getCategory_id(),
					b.getDescription(), b.getPublished_date(), b.getImport_date(), b.getStock_quantity() });
		}
	}// GEN-LAST:event_textboxsearchKeyReleased

	private void findBookAction(java.awt.event.ActionEvent evt) {
		String keyword = jtf_search.getText();
		List<MyBook> allBook = bookDAO.getBookList();
		List<MyBook> result = new ArrayList<>();
		DefaultTableModel curTableModel = (DefaultTableModel) tableSearchSach.getModel();
		curTableModel.setRowCount(0);
		for (MyBook b : allBook) {
			if (b.getTitle().toLowerCase().contains(keyword.toLowerCase()))
				result.add(b);
		}
		for (MyBook b : result) {
			curTableModel.addRow(new Object[] { b.getId(), b.getTitle(), b.getAuthor_id(), b.getCategory_id(),
					b.getDescription(), b.getPublished_date(), b.getImport_date(), b.getStock_quantity() });
		}
	}

	private void filterByCategoryAction(java.awt.event.ItemEvent evt) {
		String category = "3";
		int index = jComboBox3.getSelectedIndex();

		if (index == 0)
			return;
		if (index > 0 && index < 10) {
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
			curTableModel.addRow(new Object[] { b.getId(), b.getTitle(), b.getAuthor_id(), b.getCategory_id(),
					b.getDescription(), b.getPublished_date(), b.getImport_date(), b.getStock_quantity() });
		}
	}

	private void filterByAuthorNameAction(java.awt.event.ItemEvent evt) {
		String authorName = "4";
		int index = jComboBox4.getSelectedIndex();
		if (index == 0)
			return;
		if (index > 0 && index < 10) {
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
			curTableModel.addRow(new Object[] { b.getId(), b.getTitle(), b.getAuthor_id(), b.getCategory_id(),
					b.getDescription(), b.getPublished_date(), b.getImport_date(), b.getStock_quantity() });
		}
	}

	// hiển thị các table mode cơ bản khi click vào TAB THỐNG KÊ
	private void initBasicTableValueForTabTHONGKE(java.awt.event.MouseEvent evt) {
		try {
			// hiển thị bảng mặc định của Tab THỐNG KÊ - Thông kê Bạn Đọc
			List<UserTB> listUserHasntReturnBook = userTBService.getUserHasntReturnedBook();
			DefaultTableModel dtmStatUserTB = (DefaultTableModel) tableStatUserTB.getModel();
			dtmStatUserTB.setColumnCount(0);
			dtmStatUserTB.addColumn("Mã Tài Khoản");
			dtmStatUserTB.addColumn("Tên Tài Khoản");
			dtmStatUserTB.setRowCount(0);
			for (UserTB u : listUserHasntReturnBook) {
				dtmStatUserTB.addRow(new Object[] { u.getId(), u.getFullname() });
			}

			// hiển thị bảng mặc định của Tab THỐNG KÊ - Thông kê Sách
			ResultSet list5BookMostBorrowed = bookService.get5BookMostBorrowed();
			DefaultTableModel sach = (DefaultTableModel) tableStatBook.getModel();
			sach.setColumnCount(0);
			sach.addColumn("Mã Sách");
			sach.addColumn("Tên Sách");
			sach.addColumn("Tác Giả");
			sach.addColumn("Thể Loại");
			sach.addColumn("Số Lần Mượn Sách");
			sach.setRowCount(0);
			while (list5BookMostBorrowed.next()) {
				sach.addRow(new Object[] { list5BookMostBorrowed.getInt("id"), list5BookMostBorrowed.getString("title"),
						list5BookMostBorrowed.getInt("author_id"), list5BookMostBorrowed.getInt("category_id"),
						list5BookMostBorrowed.getInt("So_lan_muon") });
			}

			// hiển thị bảng mặc định của Tab THỐNG KÊ - Thông kê Tiền Phạt theo tháng
			ResultSet fineCollectionRS = fineCollectionService.getTotalFineInTime();
			DefaultTableModel tien = (DefaultTableModel) tableStatFineCollection.getModel();
			tien.setColumnCount(0);
			tien.setRowCount(0);
			tien.addColumn("Tháng");
			tien.addColumn("Tổng Tiền");
			while (fineCollectionRS.next()) {
				tien.addRow(
						new Object[] { fineCollectionRS.getString("Month"), fineCollectionRS.getString("TotalFine") });
			}

		} catch (SQLException ex) {
			Logger.getLogger(AdminHome.class.getName()).log(Level.SEVERE, null, ex);
		}

	}// GEN-LAST:event_jTP_mainMouseClicked

	private void searchUserTBAction(java.awt.event.KeyEvent evt) {// GEN-FIRST:event_textSearchKeyReleased
		String fullname = jtf_search.getText();
		int id;
		try {
			id = Integer.parseInt(jtf_search.getText());
		} catch (Exception e) {
			id = 0;
		}
		DefaultTableModel dtm_userShow = (DefaultTableModel) tableUserTBShowInfo.getModel();
		dtm_userShow.setRowCount(0);
		List<UserTB> allUserTB = userTBService.getUserTBList();
		List<UserTB> result = new ArrayList<>();

		for (UserTB u : allUserTB) {
			if (u.getId() == id)
				result.add(u);
			if (u.getFullname().toLowerCase().contains(fullname.toLowerCase()))
				result.add(u);
		}
		for (UserTB u : result) {
			dtm_userShow.addRow(new Object[] { u.getId(), u.getFullname(), u.getPassword(), u.getEmail(), u.getPhone(),
					u.getDob() });
		}
	}// GEN-LAST:event_textSearchKeyReleased

	private void mokhoaActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_mokhoaActionPerformed
		DefaultTableModel tbxtt = (DefaultTableModel) tableUserTBManage.getModel();
		int selected = tableUserTBManage.getSelectedRow();
		if (selected != -1) {
			String madg = tbxtt.getValueAt(selected, 0).toString();
			int xacnhan = JOptionPane.showConfirmDialog(null, "Bạn chắc chắn muốn mở tài khoản ?", "Xác nhận khóa",
					JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.ERROR_MESSAGE);
			if (xacnhan == 0) {
			}
		} else {
			JOptionPane.showMessageDialog(null, "Vui Lòng Chọn Trước Khi Mở khóa", "ERROR",
					JOptionPane.INFORMATION_MESSAGE);
		}
	}// GEN-LAST:event_mokhoaActionPerformed

	private void showInfoUserWhenMouseClickedOnTable(java.awt.event.MouseEvent evt) {
		DefaultTableModel dtm_userShow = (DefaultTableModel) tableUserTBShowInfo.getModel();
		DefaultTableModel dtm_userManage = (DefaultTableModel) tableUserTBManage.getModel();
		int selected = tableUserTBShowInfo.getSelectedRow();

		if (selected != -1) {
			jtp_QLDG.setSelectedIndex(1);
			String id = dtm_userShow.getValueAt(selected, 0).toString();
			String fullname = dtm_userShow.getValueAt(selected, 1).toString();
			String passwd = dtm_userShow.getValueAt(selected, 2).toString();
			String email = dtm_userShow.getValueAt(selected, 3).toString();
			String phone = dtm_userShow.getValueAt(selected, 4).toString();
			String dob = dtm_userShow.getValueAt(selected, 5).toString();

			jtf_idUserTb.setEditable(false);
			jtf_userTBName.setText(fullname);
			jtf_dob.setText(dob);
			jtf_emailUserTB.setText(email);
			jtf_phoneNumber.setText(phone);

			dtm_userManage.setRowCount(0);
			dtm_userManage.addRow(new Object[] { id, fullname, email, phone, dob });
			jtp_QLDG.setSelectedIndex(0);
		} else {
			JOptionPane.showMessageDialog(null, "Vui Lòng Chọn Trước Khi khóa", "ERROR",
					JOptionPane.INFORMATION_MESSAGE);
		}
	}

	private void reloadAction(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_mokhoa1ActionPerformed
		this.initDataForTabUserTB();

	}// GEN-LAST:event_mokhoa1ActionPerformed

	private void btn_lammoiActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btn_lammoiActionPerformed
		jtf_cID_TabQLSQLTLS.setEnabled(false);
		jtf_cNameID_TabQLSQLTLS.setEnabled(false);
		jtf_cID_TabQLSQLTLS.setText("");
		jtf_cNameID_TabQLSQLTLS.setText("");
	}// GEN-LAST:event_btn_lammoiActionPerformed

	private void logoutAction(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jButton1ActionPerformed
		this.setVisible(false);
		new Home().setVisible(true);
	}

	private String cutChar(String arry) {
		return arry.replaceAll("\\D+", "");
	}

	private void phoneNumberKeyReleased(java.awt.event.KeyEvent evt) {// GEN-FIRST:event_sdtKeyReleased
		jtf_phoneNumber.setText(cutChar(jtf_phoneNumber.getText()).trim());
		jtf_phoneNumber.setVisible(true);
	}// GEN-LAST:event_sdtKeyReleased

	private void dobFocusLost(java.awt.event.FocusEvent evt) {// GEN-FIRST:event_ngaysinhFocusLost
		String dateString = jtf_dob.getText();
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		df.setLenient(false); // set false để kiểm tra tính hợp lệ của date. VD: tháng 2 phải có 28-29 ngày,
								// năm có 12 tháng,....
		try {
			df.parse(dateString); // parse dateString thành kiểu Date
		} catch (ParseException e) { // quăng lỗi nếu dateString ko hợp lệ
			JOptionPane.showMessageDialog(this, "Sai định dạng! Nhâp lại! (VD: 2001-01-01");
			jtf_dob.requestFocus();
			jtf_dob.setText("");
		}
	}// GEN-LAST:event_ngaysinhFocusLost

	private void idUserTBKeyPressedAction(java.awt.event.KeyEvent evt) {// GEN-FIRST:event_madgKeyPressed
		if (evt.getKeyCode() == 10)
			jtf_userTBName.requestFocus();
	}// GEN-LAST:event_madgKeyPressed

	private void userTBNameKeyPressed(java.awt.event.KeyEvent evt) {// GEN-FIRST:event_tenTaikhoanKeyPressed
		if (evt.getKeyCode() == 10)
			jtf_phoneNumber.requestFocus();
	}// GEN-LAST:event_tenTaikhoanKeyPressed

	private void dobKeyPressed(java.awt.event.KeyEvent evt) {// GEN-FIRST:event_ngaysinhKeyPressed
		if (evt.getKeyCode() == 10)
			jtf_emailUserTB.requestFocus();
	}// GEN-LAST:event_ngaysinhKeyPressed

	private void timkiem(String query) {
		TableRowSorter<DefaultTableModel> tbl = new TableRowSorter<DefaultTableModel>(dtm);
		table_QLS_QLTLS.setRowSorter(tbl);
		tbl.setRowFilter(RowFilter.regexFilter(query));
	}

	private void disableBtn_QuanLiMuonTra_CTPM() {
		K_maPM.setEnabled(false);
		jdc_loan_date.setEnabled(false);
		jtf_BookID.setEnabled(false);
		jcb_stateBook.setEnabled(false);
		btnK_suaChiTiet.setEnabled(false);
		btnK_luuChiTiet.setEnabled(false);
	}

	private void initData_QLMT_PM() throws SQLException {
		dtm_Loan = new DefaultTableModel() {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};

		table_listLoan.setModel(dtm_Loan);
		dtm_Loan.addColumn("Mã phiếu mượn");
		dtm_Loan.addColumn("ID Bạn đọc");
		dtm_Loan.addColumn("Tên bạn đọc");
		dtm_Loan.addColumn("Ngày mượn");

		DefaultTableCellRenderer dtcr = (DefaultTableCellRenderer) table_listLoan.getTableHeader().getDefaultRenderer();
		dtcr.setHorizontalAlignment(0);
		table_listLoan.getTableHeader().setFont(new Font("Times New Roman", Font.BOLD, 18));
		table_listLoan.setRowHeight(30);

		TableColumnModel tcm_LoanList = table_listLoan.getColumnModel();
		tcm_LoanList.getColumn(0).setPreferredWidth(50);
		tcm_LoanList.getColumn(1).setPreferredWidth(50);
		tcm_LoanList.getColumn(2).setPreferredWidth(250);
		tcm_LoanList.getColumn(3).setPreferredWidth(200);

		ResultSet rs = loanService.getLoanList();
		while (rs.next()) {
			dtm_Loan.addRow(new Object[] { rs.getInt("id"), rs.getInt("userTb_id"), rs.getString("fullname"),
					rs.getString("loan_date") });
		}
	}

	private void initData_QLMT_CTPM() {
		table_LoanDetail.setModel(dtm_LoanDetail);
		dtm_LoanDetail.setColumnCount(0);
		dtm_LoanDetail.setRowCount(0);
		dtm_LoanDetail.addColumn("Mã PM");
		dtm_LoanDetail.addColumn("Mã sách");
		dtm_LoanDetail.addColumn("Tên sách");
		dtm_LoanDetail.addColumn("SL");
		dtm_LoanDetail.addColumn("Ngày mượn");
		dtm_LoanDetail.addColumn("Ngày trả dự kiến");
		dtm_LoanDetail.addColumn("Ngày thực trả");

		DefaultTableCellRenderer dtcr = (DefaultTableCellRenderer) table_LoanDetail.getTableHeader()
				.getDefaultRenderer();
		dtcr.setHorizontalAlignment(0);
		table_LoanDetail.getTableHeader().setFont(new Font("Times New Roman", Font.BOLD, 18));
		table_LoanDetail.setRowHeight(30);

		TableColumnModel columnModel_DSPM = table_LoanDetail.getColumnModel();
		columnModel_DSPM.getColumn(0).setPreferredWidth(40);
		columnModel_DSPM.getColumn(1).setPreferredWidth(40);
		columnModel_DSPM.getColumn(2).setPreferredWidth(200);
		columnModel_DSPM.getColumn(3).setPreferredWidth(40);
		columnModel_DSPM.getColumn(4).setPreferredWidth(150);
		columnModel_DSPM.getColumn(5).setPreferredWidth(150);
		columnModel_DSPM.getColumn(6).setPreferredWidth(150);

		int curSelectedLoanID = Integer.parseInt(K_maPM.getText());
		ResultSet rs = loanService.getLoanDetail(curSelectedLoanID);
		try {
			while (rs.next()) {
				try {
					String return_date = rs.getString("return_date");
					dtm_LoanDetail.addRow(new Object[] { rs.getInt("id"), rs.getInt("book_id"), rs.getString("title"),
							rs.getInt("quantity"), rs.getString("loan_date"), rs.getString("due_date"), return_date });
				} catch (Exception e) {
					// TODO: handle exception
					String message = "Chưa trả";
					dtm_LoanDetail.addRow(new Object[] { rs.getInt("id"), rs.getInt("book_id"), rs.getString("title"),
							rs.getInt("quantity"), rs.getString("loan_date"), rs.getString("due_date"), message });
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void Refresh_CTPM() {
		((JTextField) jdc_loan_date.getDateEditor().getUiComponent()).setText("");
		jdc_loan_date.setEnabled(false);
		jcb_stateBook.setEnabled(false);
		btnK_suaChiTiet.setEnabled(false);
		btnK_luuChiTiet.setEnabled(false);
		jtf_BookID.setText("");
		initData_QLMT_CTPM();
	}

	public void setDSPM() {
		jtp_main.setSelectedIndex(2);
	}

	public static void main(String args[]) {
		java.awt.EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					new AdminHome().setVisible(true);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
	}

	// Variables declaration - do not modify//GEN-BEGIN:variables
	private javax.swing.JTextField jtf_idBook;
	private javax.swing.JTextField jtf_publishedDate;
	private javax.swing.JTextField jtf_importDate;
	private javax.swing.JTextField jtf_stockQuantity;
	private javax.swing.JTextField jtf_avaliableBook;
	private javax.swing.JTextField jtf_authorName;
	private javax.swing.JTextField jtf_titleBook;
	private javax.swing.JTextField jtf_category;
	private javax.swing.JTextField jtf_description;
	private javax.swing.JComboBox<String> jcb_authorID;
	private javax.swing.JComboBox<String> jcb_idCategory;
	private javax.swing.JTextField K_maPM;
	private javax.swing.JTextField jtf_BookID;
	private com.toedter.calendar.JDateChooser jdc_loan_date;
	private javax.swing.JLabel jl_titleTab_QLPM;
	private javax.swing.JComboBox<String> jcb_stateBook;
	private javax.swing.JPanel Panel_ChiTietPM;
	private javax.swing.JPanel Panel_DanhSachPM;
	private javax.swing.JButton btn_saveBook;
	private javax.swing.JButton btn_editBook;
	private javax.swing.JButton btn_addBook;
	private javax.swing.JButton btnK_lamMoi;
	private javax.swing.JButton btnK_luuChiTiet;
	private javax.swing.JButton btnK_suaChiTiet;
	private javax.swing.JButton btnK_themPM;
	private javax.swing.JButton btnK_veTrangTruoc;
	private javax.swing.JButton btn_LuuDMSach;
	private javax.swing.JButton btn_SuaDMSach;
	private javax.swing.JButton btn_ThemDMSach;
	private javax.swing.JButton btn_refreshAction;
	private javax.swing.JButton btn_lammoi;
	private javax.swing.JComboBox<String> jcb_statUserFunction;
	private javax.swing.JComboBox<String> jcb_statBookFunction;
	private javax.swing.JComboBox<String> cbb_chucNangThongKe2;
	private javax.swing.JTextField jtf_emailUserTB;
	private javax.swing.ButtonGroup gioitinhbtngroup;
	private javax.swing.JRadioButton gioitinhnam;
	private javax.swing.JRadioButton gioitinhnu;
	private javax.swing.JButton btn_logout;
	private javax.swing.JButton jButton11;
	private javax.swing.JComboBox<String> jComboBox3;
	private javax.swing.JComboBox<String> jComboBox4;
	private javax.swing.JLabel jl_category_TABQLS_QLTLS;
	private javax.swing.JLabel jLabel10;
	private javax.swing.JLabel jLabel11;
	private javax.swing.JLabel jLabel12;
	private javax.swing.JLabel jl_idBook;
	private javax.swing.JLabel jl_titleBook;
	private javax.swing.JLabel jLabel15;
	private javax.swing.JLabel jLabel16;
	private javax.swing.JLabel jl_stockQuantity;
	private javax.swing.JLabel jl_avaliableBook;
	private javax.swing.JLabel jl_importDate;
	private javax.swing.JLabel jl_cID_TABQLSQLTLS;
	private javax.swing.JLabel jl_publishedDate;
	private javax.swing.JLabel jl_description;
	private javax.swing.JLabel jl_authorName;
	private javax.swing.JLabel jl_category;
	private javax.swing.JLabel jl_idCategory;
	private javax.swing.JLabel jl_authorID;
	private javax.swing.JLabel jLabel26;
	private javax.swing.JLabel jLabel27;
	private javax.swing.JLabel jLabel28;
	private javax.swing.JLabel jLabel29;
	private javax.swing.JLabel jl_cNameID_TABQLSQLTLS;
	private javax.swing.JLabel jLabel30;
	private javax.swing.JLabel jLabel31;
	private javax.swing.JLabel jLabel32;
	private javax.swing.JLabel jLabel33;
	private javax.swing.JLabel jLabel34;
	private javax.swing.JLabel jLabel35;
	private javax.swing.JLabel jLabel36;
	private javax.swing.JLabel jLabel37;
	private javax.swing.JLabel jLabel38;
	private javax.swing.JLabel jLabel39;
	private javax.swing.JLabel jLabel4;
	private javax.swing.JLabel jLabel40;
	private javax.swing.JLabel jLabel41;
	private javax.swing.JLabel jLabel5;
	private javax.swing.JLabel jLabel6;
	private javax.swing.JLabel jLabel7;
	private javax.swing.JLabel jLabel8;
	private javax.swing.JLabel jLabel9;
	private javax.swing.JPanel jPK_QuanLyPhieuMuon;
	private javax.swing.JPanel jPanel1;
	private javax.swing.JPanel jPanel10;
	private javax.swing.JPanel jPanel11;
	private javax.swing.JPanel jPanel12;
	private javax.swing.JPanel jPanel13;
	private javax.swing.JPanel jPanel14;
	private javax.swing.JPanel jPanel15;
	private javax.swing.JPanel jPanel16;
	private javax.swing.JPanel jPanel2;
	private javax.swing.JPanel jPanel3;
	private javax.swing.JPanel jPanel4;
	private javax.swing.JPanel jPanel6;
	private javax.swing.JPanel jPanel7;
	private javax.swing.JPanel jPanel8;
	private javax.swing.JPanel jPanel9;
	private javax.swing.JScrollPane jSPK_DSPM;
	private javax.swing.JScrollPane jScrollPane1;
	private javax.swing.JScrollPane jScrollPane2;
	private javax.swing.JScrollPane jScrollPane3;
	private javax.swing.JScrollPane jScrollPane4;
	private javax.swing.JScrollPane jScrollPane5;
	private javax.swing.JScrollPane jScrollPane6;
	private javax.swing.JScrollPane jScrollPane7;
	private javax.swing.JScrollPane jScrollPane8;
	private javax.swing.JScrollPane jScrollPane9;
	private javax.swing.JSeparator jSeparator1;
	private javax.swing.JSeparator jSeparator2;
	private javax.swing.JTabbedPane jtp_QuanLiMuonTra;
	private javax.swing.JTabbedPane jtp_main;
	private javax.swing.JTabbedPane jtp_QLS_QLS;
	private javax.swing.JTabbedPane jTabbedPane5;
	private javax.swing.JTabbedPane jtp_QLDG;
	private javax.swing.JButton khoatk;
	private javax.swing.JTextField jtf_idUserTb;
	private javax.swing.JButton mokhoa;
	private javax.swing.JButton btn_refresh;
	private javax.swing.JTextField jtf_dob;
	private javax.swing.JTextField jtf_phoneNumber;
	private javax.swing.JTable tableUserTBManage;
	private javax.swing.JTable tableSearchSach;
	private javax.swing.JTable tableStatUserTB;
	private javax.swing.JTable tableStatBook;
	private javax.swing.JTable tableStatFineCollection;
	private javax.swing.JTable tableUserTBShowInfo;
	private javax.swing.JTable table_QLS_QLS;
	private javax.swing.JTable table_LoanDetail;
	private javax.swing.JTable table_listLoan;
	private javax.swing.JTable table_QLS_QLTLS;
	private javax.swing.JTextField jtf_userTBName;
	private javax.swing.JTextField jtf_search;
	private javax.swing.JTextField textboxsearch;
	private javax.swing.JButton btn_addNewUserTB;
	private javax.swing.JPanel timkiem;
	private javax.swing.JTextField jtf_cID_TabQLSQLTLS;
	private javax.swing.JTextField jtf_cNameID_TabQLSQLTLS;
	private javax.swing.JTextField txt_timkiemDMSach;
	private javax.swing.JButton updatedg;
}
