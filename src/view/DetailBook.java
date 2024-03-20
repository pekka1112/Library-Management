/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;


import java.nio.channels.SelectableChannel;
import java.sql.ResultSet;
import java.sql.SQLException;

import Service.BookService;
import model.MyBook;
import view.UserTbHome;

/**
 *
 * @author Windows 10
 */
public class DetailBook extends javax.swing.JFrame {

	public String titleBook;
	public String authorName;
	public String publishedDate;
	public String categoryName;
	public String description;
	public String importDate;
	public String stockQuantity;
	
    public UserTbHome muonsach;
    public MyBook curSelectedBook = new MyBook();
    public BookService bookService = new BookService();
    
    public DetailBook(MyBook book) {
    	curSelectedBook = book;
    	initComponents();
    	initData();
    }
    
    private void initData() {
		// TODO Auto-generated method stub
    	int idBook = curSelectedBook.getId();
    	ResultSet rs = bookService.getDetailOfBook(idBook);
    	try {
			while(rs.next()) { 
				titleBook = rs.getString("title");
				authorName = curSelectedBook.getAuthor_id() + " - " + rs.getString("author_name");
				categoryName = curSelectedBook.getCategory_id() + " - " + rs.getString("category_name");
				publishedDate = rs.getString("published_date");
				importDate = rs.getString("import_date");
				stockQuantity = String.valueOf(rs.getInt("stock_quantity"));
				description = rs.getString("description");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	this.jl_title.setText(titleBook);
        this.jl_authorName.setText(authorName);
        this.jl_category.setText(categoryName);
        this.jl_publishedDate.setText(publishedDate);
        this.jl_importDate.setText(importDate);
        this.jl_stockQuantity.setText(stockQuantity);
        this.jta_description.setText(description);
	}

    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jl_importDate = new javax.swing.JLabel();
        jl_stockQuantity = new javax.swing.JLabel();
        jl_publishedDate = new javax.swing.JLabel();
        jl_category = new javax.swing.JLabel();
        jl_authorName = new javax.swing.JLabel();
        jl_authorNameLABLE = new javax.swing.JLabel();
        jl_categoryLABLE = new javax.swing.JLabel();
        jl_publishedDateLABLE = new javax.swing.JLabel();
        jl_importDateLABLE = new javax.swing.JLabel();
        jl_stockQuantityLABLE = new javax.swing.JLabel();
        jl_descriptionLABLE = new javax.swing.JLabel();
        jsp_description = new javax.swing.JScrollPane();
        jta_description = new javax.swing.JTextArea();
        jl_title = new javax.swing.JLabel();
        btn_exit = new javax.swing.JButton();
        btnThemMuon = new javax.swing.JButton();

        setTitle("Detail Book : " + curSelectedBook.getId());
        setBackground(new java.awt.Color(255, 255, 204));
        jPanel1.setBackground(new java.awt.Color(255, 255, 204));
        jPanel2.setBackground(new java.awt.Color(255, 255, 204));	

        jl_authorNameLABLE.setFont(new java.awt.Font("Times New Roman", 3, 18)); // NOI18N
        jl_authorNameLABLE.setForeground(new java.awt.Color(0, 0, 153));
        jl_authorNameLABLE.setText("Tên tác giả:");
        jl_authorName.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        jl_authorName.setForeground(new java.awt.Color(0, 51, 204));

        jl_categoryLABLE.setFont(new java.awt.Font("Times New Roman", 3, 18)); // NOI18N
        jl_categoryLABLE.setForeground(new java.awt.Color(0, 0, 153));
        jl_categoryLABLE.setText("Thể loại chính:");
        jl_category.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        jl_category.setForeground(new java.awt.Color(0, 51, 204));

        jl_publishedDateLABLE.setFont(new java.awt.Font("Times New Roman", 3, 18)); // NOI18N
        jl_publishedDateLABLE.setForeground(new java.awt.Color(0, 0, 153));
        jl_publishedDateLABLE.setText("Ngày xuất bản:");
        jl_publishedDate.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        jl_publishedDate.setForeground(new java.awt.Color(0, 51, 204));

        jl_importDateLABLE.setFont(new java.awt.Font("Times New Roman", 3, 18)); // NOI18N
        jl_importDateLABLE.setForeground(new java.awt.Color(0, 0, 153));
        jl_importDateLABLE.setText("Ngày nhập về thư viện:");
        jl_importDate.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        jl_importDate.setForeground(new java.awt.Color(0, 51, 204));

        jl_stockQuantityLABLE.setFont(new java.awt.Font("Times New Roman", 3, 18)); // NOI18N
        jl_stockQuantityLABLE.setForeground(new java.awt.Color(0, 0, 153));
        jl_stockQuantityLABLE.setText("Số lượng:");
        jl_stockQuantity.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        jl_stockQuantity.setForeground(new java.awt.Color(0, 51, 204));

        jl_descriptionLABLE.setFont(new java.awt.Font("Times New Roman", 3, 18)); // NOI18N
        jl_descriptionLABLE.setForeground(new java.awt.Color(0, 0, 153));
        jl_descriptionLABLE.setText("Tóm tắt nội dung:");
        jta_description.setEditable(false);
        jta_description.setBackground(new java.awt.Color(255, 255, 204));
        jta_description.setColumns(20);
        jta_description.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        jta_description.setForeground(new java.awt.Color(0, 51, 204));
        jta_description.setLineWrap(true);
        jta_description.setRows(5);
        jta_description.setBorder(null);
        jta_description.setCaretColor(new java.awt.Color(255, 255, 204));
        jsp_description.setViewportView(jta_description);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jl_publishedDateLABLE, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 139, Short.MAX_VALUE)
                            .addComponent(jl_categoryLABLE, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jl_authorNameLABLE, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jl_importDateLABLE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jl_publishedDate, javax.swing.GroupLayout.PREFERRED_SIZE, 407, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jl_category, javax.swing.GroupLayout.PREFERRED_SIZE, 397, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jl_authorName, javax.swing.GroupLayout.PREFERRED_SIZE, 397, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jl_importDate, javax.swing.GroupLayout.PREFERRED_SIZE, 348, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jl_descriptionLABLE, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jl_stockQuantityLABLE, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jl_stockQuantity, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGap(59, 59, 59))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jsp_description, javax.swing.GroupLayout.PREFERRED_SIZE, 367, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE)))))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jl_authorNameLABLE, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jl_authorName, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jl_categoryLABLE, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jl_category, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jl_publishedDateLABLE, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jl_publishedDate, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jl_importDateLABLE, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jl_importDate, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jl_stockQuantityLABLE, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jl_stockQuantity, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jl_descriptionLABLE, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(62, 62, 62)
                        .addComponent(jsp_description, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );

        jl_title.setFont(new java.awt.Font("Times New Roman", 1, 24)); 
        jl_title.setForeground(new java.awt.Color(0, 0, 153));
        jl_title.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jl_title.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/book (1).png"))); // NOI18N

        btn_exit.setFont(new java.awt.Font("Times New Roman", 1, 20));
        btn_exit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/remove (1).png"))); // NOI18N
        btn_exit.setText("Thoát");
        btn_exit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exitActionPerformed(evt);
            }
        });

        btnThemMuon.setFont(new java.awt.Font("Times New Roman", 1, 20)); // NOI18N
        btnThemMuon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/plus.png"))); // NOI18N
        btnThemMuon.setText("Thêm vào danh sách mượn");
        btnThemMuon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemMuonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(121, 121, 121)
                .addComponent(btnThemMuon)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btn_exit)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(92, Short.MAX_VALUE)
                .addComponent(jl_title, javax.swing.GroupLayout.PREFERRED_SIZE, 481, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(67, 67, 67))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 559, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(jl_title, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnThemMuon)
                    .addComponent(btn_exit))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        getAccessibleContext().setAccessibleParent(btnThemMuon);

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void exitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThoatCTActionPerformed
        this.dispose();
    }//GEN-LAST:event_btnThoatCTActionPerformed

    private void btnThemMuonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemMuonActionPerformed
        this.dispose();
    }//GEN-LAST:event_btnThemMuonActionPerformed

    public static void main(String args[]) {
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
            	new DetailBook(null);
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(DetailBook.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(DetailBook.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(DetailBook.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(DetailBook.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
            }
        });
    }

    private javax.swing.JButton btnThemMuon;
    private javax.swing.JButton btn_exit;
    private javax.swing.JLabel jl_importDateLABLE;
    private javax.swing.JLabel jl_stockQuantityLABLE;
    private javax.swing.JLabel jl_descriptionLABLE;
    private javax.swing.JLabel jl_authorNameLABLE;
    private javax.swing.JLabel jl_categoryLABLE;
    private javax.swing.JLabel jl_publishedDateLABLE;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jsp_description;
    private javax.swing.JLabel jl_importDate;
    private javax.swing.JLabel jl_category;
    private javax.swing.JLabel jl_publishedDate;
    private javax.swing.JTextArea jta_description;
    private javax.swing.JLabel jl_authorName;
    private javax.swing.JLabel jl_title;
    private javax.swing.JLabel jl_stockQuantity;
}
