/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package gradebooksystem;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;
import javax.swing.ListModel;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author hloni
 */
public class existingStudentFrame extends javax.swing.JFrame {

    /**
     * Creates new form existingStudentFrame
     */
    public existingStudentFrame() {
        initComponents();
    }
    
     Boolean boolRecordExists=false; //boolean will be used to check if record exists
     Boolean boolSubjectAlreadyLinked = false;
    String strIdNumber;
    String strName;
    String strSurname;
    String strDOB;
    String strGender;
    private List<String> selectedSubjects = new ArrayList<>();
    
    
      private void mGetValuesFromGUI(){
        //values from the user
         try {
          strIdNumber = txtID.getText();
          strName = txtName.getText();
          strSurname = txtSurname.getText();
          
           
       
          
           SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");//format
        strDOB= sdf1.format(dcDOB.getDate());
       
         
         if (rdoMale.isSelected()) {
            strGender = "Male";
        } else if (rdoFemale.isSelected()) {
            strGender = "Female";
        } else {
            strGender = ""; // fallback to prevent null
        }
        
         } catch (Exception e) {
             e.printStackTrace();
              JOptionPane.showMessageDialog(null, "Enter correct input"+""+e);
             
         }
         
         
       
        
       
    }
   
  
      private void mTableStudentsView() {
    int CC; // Column count
    java.sql.Connection conMySQLConnectionString;
    String URL3 = "jdbc:mysql://localhost:3306/gradebook_system";
    String User3 = "root";
    String Password3 = "528_hloni";
    PreparedStatement pst;
    
    try {
        conMySQLConnectionString = DriverManager.getConnection(URL3, User3, Password3);

        // Updated query to include subjects using JOINs and GROUP_CONCAT
        String sql = "SELECT s.student_id, s.id_number, s.name, s.surname, s.gender, s.date_of_birth, " +
                     "GROUP_CONCAT(sub.subject_name SEPARATOR ', ') AS subjects " +
                     "FROM students s " +
                     "LEFT JOIN student_subjects ss ON s.student_id = ss.student_id " +
                     "LEFT JOIN subjects sub ON ss.subject_id = sub.subject_id " +
                     "GROUP BY s.student_id";

        pst = conMySQLConnectionString.prepareStatement(sql);
        ResultSet Rs1 = pst.executeQuery();
        DefaultTableModel DFT = (DefaultTableModel) tblStudents.getModel();
        DFT.setRowCount(0); // Clear previous rows

        while (Rs1.next()) {
            Vector v2 = new Vector();
            v2.add(Rs1.getString("student_id"));
            v2.add(Rs1.getString("id_number"));
            v2.add(Rs1.getString("name"));
            v2.add(Rs1.getString("surname"));
            v2.add(Rs1.getString("date_of_birth"));
            v2.add(Rs1.getString("gender"));
            v2.add(Rs1.getString("subjects") == null ? "No subjects" : Rs1.getString("subjects")); // fallback
            DFT.addRow(v2);
        }

    } catch (Exception e) {
        JOptionPane.showMessageDialog(null, e);
    }
}
      
      
   
   
      private void mEditUpdateStudent()
             //  Update/Edit existing venues
   { 
    java.sql.Connection conMySQLConnectionString ; //Declares connection string named conMySQLConnectionString, it will contain the driver for the connection string to the database
        String URL3 = "jdbc:mysql://localhost:3306/gradebook_system"; //Connection string to the database
        String User3 = "root"; //User name to connect to database
        String Password3 = "528_hloni"; //User password to connect to database
        
        //Get selected row information
         DefaultTableModel model = (DefaultTableModel) tblStudents.getModel();//Get model of table
      int selectedIndex = tblStudents.getSelectedRow();
      int intStudentID = Integer.parseInt(model.getValueAt(selectedIndex,0).toString());
        try {
            conMySQLConnectionString = DriverManager.getConnection(URL3,User3,Password3); //used to gain access to database
            Statement euStatement = conMySQLConnectionString.createStatement(); 
            String strQuery = "Update students Set id_number = '" + strIdNumber + 
                  "', name = '" + strName + 
                  "', surname = '" + strSurname + 
                  "', gender = '" + strGender + 
                  "', date_of_birth = '" + strDOB +   
                  "' Where student_id = "+intStudentID;
            euStatement.executeUpdate(strQuery); // Execute sql statements against the database table
            euStatement.close(); //Close connection of the database
            JOptionPane.showMessageDialog(null,"Record Updated");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
           
        }
   }
      
      
       private void mDelete()
            // Delete student
   {
      java.sql.Connection conMySQLConnectionString ; //Declares connection string named conMySQLConnectionString, it will contain the driver for the connection string to the database
        String URL8 = "jdbc:mysql://localhost:3306/gradebook_system"; //Connection string to the database
        String User8 = "root"; //User name to connect to database
        String Password8 = "528_hloni"; //User password to connect to database
        
        //Get selected row information
         DefaultTableModel model = (DefaultTableModel) tblStudents.getModel();//Get model of table
      int selectedIndex = tblStudents.getSelectedRow();
      int intStudentID = Integer.parseInt(model.getValueAt(selectedIndex,0).toString());
        try {
            conMySQLConnectionString = DriverManager.getConnection(URL8,User8,Password8); //used to gain access to database
            Statement dtStatement = conMySQLConnectionString.createStatement(); 
            String strQuery = "DELETE FROM students WHERE student_id = '" + intStudentID + "' AND id_number = '" + strIdNumber +
                    "' AND name = '" + strName + "' AND surname = '" + strSurname +
                    "' AND gender = '" + strGender + "' AND date_of_birth = '" + strDOB + "'";
            
            dtStatement.executeUpdate(strQuery); // Execute sql statements against the database table
            dtStatement.close(); //Close connection of the database
            JOptionPane.showMessageDialog(null,"Record Deleted");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
           
        }  
       
       
   }    
   
   

   
   
    
     private void mSetvaluesToUpperCase(){
        //values will be uppercase
       
        
        strName = strName.toUpperCase();
        strSurname = strSurname.toUpperCase();
        strGender = strGender.toUpperCase();
        
       
        }
     
         private void mCheckIfItemsExistInTable(){
         //Validation to prevent duplication
         
        String URL1 = "jdbc:mysql://localhost:3306/gradebook_system"; //Connection string to the database
        String User1 = "root"; //User name to connect to database
        String Password1 = "528_hloni"; //User password to connect to database
        java.sql.Connection conMySQLConnectionString; //Declares connection string named conMySQLConnectionString,it will contain the driver for the connection string to the database
        Statement stStatement = null; //Declares statement named stStatement which will contain sql statement
        ResultSet rs = null; //Declares statement named rs which will contain quiried data from the table
        
        // try catch contains code to run the query against database table
        try {
            conMySQLConnectionString = DriverManager.getConnection(URL1,User1,Password1); //used to gain access to database
            stStatement = conMySQLConnectionString.createStatement();//This will instruct stStatement to execute SQL statement against the table in database
           
            String strQuery = "SELECT * FROM students WHERE id_number = '" + strIdNumber +
        "' AND name = '" + strName + 
        "' AND surname = '" + strSurname +
        "' AND gender = '" + strGender + 
        "' AND date_of_birth = '" + strDOB + "'";
            stStatement.execute(strQuery); // Execute sql statements against the database table
            rs=stStatement.getResultSet();
            boolRecordExists=rs.next(); //Confirm if the record exist or not in the database
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        } finally { //final block has try catch that closes connection of the database
            try {
                stStatement.close();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Connection String not closed"+""+e);
            }
  
        }
    }
         
     
         
         private void mClearTextFields(){ //This will clear textfields once the values have been captured
        txtID.setText("");
        txtName.setText("");
        txtSurname.setText("");
        dcDOB.setDate(null);
        buttonGroup1.clearSelection();
        
         }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        lblExistingStudent = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblStudents = new javax.swing.JTable();
        btnNext = new javax.swing.JButton();
        btnReturn = new javax.swing.JButton();
        btnEdit = new javax.swing.JButton();
        btnDelete = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        lblID = new javax.swing.JLabel();
        lblName = new javax.swing.JLabel();
        lblSurname = new javax.swing.JLabel();
        lblDOB = new javax.swing.JLabel();
        lblGender = new javax.swing.JLabel();
        txtID = new javax.swing.JTextField();
        txtName = new javax.swing.JTextField();
        txtSurname = new javax.swing.JTextField();
        dcDOB = new com.toedter.calendar.JDateChooser();
        rdoMale = new javax.swing.JRadioButton();
        rdoFemale = new javax.swing.JRadioButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        lblExistingStudent.setText("Existing Student");

        tblStudents.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "Student_ID", "ID Number", "Name", "Surname", "D.O.B", "Gender", "Subjects"
            }
        ));
        tblStudents.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblStudentsMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblStudents);

        btnNext.setText("Next");
        btnNext.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNextActionPerformed(evt);
            }
        });

        btnReturn.setText("Return");
        btnReturn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnReturnActionPerformed(evt);
            }
        });

        btnEdit.setText("Edit");
        btnEdit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditActionPerformed(evt);
            }
        });

        btnDelete.setText("Delete");
        btnDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteActionPerformed(evt);
            }
        });

        lblID.setText("ID Number:");

        lblName.setText("Name:");

        lblSurname.setText("Surname:");

        lblDOB.setText("DOB:");

        lblGender.setText("Gender:");

        buttonGroup1.add(rdoMale);
        rdoMale.setText("Male");

        buttonGroup1.add(rdoFemale);
        rdoFemale.setText("Female");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(14, 14, 14)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblName, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblDOB, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblID, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblSurname, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(22, 22, 22)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(txtSurname, javax.swing.GroupLayout.DEFAULT_SIZE, 97, Short.MAX_VALUE)
                                    .addComponent(txtName)
                                    .addComponent(txtID)))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(14, 14, 14)
                                .addComponent(dcDOB, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(lblGender, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(rdoMale, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(rdoFemale, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(17, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblID)
                    .addComponent(txtID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(35, 35, 35)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblName)
                    .addComponent(txtName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(35, 35, 35)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblSurname)
                    .addComponent(txtSurname, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(32, 32, 32)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lblDOB)
                    .addComponent(dcDOB, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(30, 30, 30)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblGender)
                    .addComponent(rdoMale)
                    .addComponent(rdoFemale))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(434, 434, 434)
                        .addComponent(lblExistingStudent, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(144, 144, 144)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(btnDelete)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(btnNext)
                                .addGap(173, 173, 173)
                                .addComponent(btnEdit)))
                        .addGap(163, 163, 163)
                        .addComponent(btnReturn))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(58, 58, 58)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 702, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(77, 77, 77)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(82, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(lblExistingStudent, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane1))
                .addGap(37, 37, 37)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnNext)
                    .addComponent(btnReturn)
                    .addComponent(btnEdit))
                .addGap(28, 28, 28)
                .addComponent(btnDelete)
                .addGap(164, 164, 164))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 733, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 33, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnNextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNextActionPerformed
        
        int selectedIndex = tblStudents.getSelectedRow();
        if (selectedIndex == -1) {
            JOptionPane.showMessageDialog(null,"Please select a student");
        }else {
            DefaultTableModel model = (DefaultTableModel) tblStudents.getModel();
            
            int intStudentId = Integer.parseInt(model.getValueAt(selectedIndex,0).toString());
            String strName = model.getValueAt(selectedIndex,2).toString();
            String strSurname = model.getValueAt(selectedIndex, 3).toString();
            
             studentResultFrame frmStudentResult = new studentResultFrame(intStudentId, strName,strSurname);
       frmStudentResult.setVisible(true);
       this.setVisible(false);
        }
        
        
        
        
        
        
    }//GEN-LAST:event_btnNextActionPerformed

    private void btnReturnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnReturnActionPerformed
        // TODO add your handling code here:
         mainFrame frmMain = new mainFrame();
       frmMain.setVisible(true);
       this.setVisible(false);
    }//GEN-LAST:event_btnReturnActionPerformed

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
       mTableStudentsView();
    }//GEN-LAST:event_formWindowOpened

    private void btnEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditActionPerformed
        // TODO add your handling code here:
        
        int selectedIndex = tblStudents.getSelectedRow(); // NOTE: use tblStudents now
    if (selectedIndex == -1) {
        JOptionPane.showMessageDialog(null, "Please select a row to edit.");
    } else {
        mGetValuesFromGUI();
        mSetvaluesToUpperCase();
        mCheckIfItemsExistInTable();

        if (boolRecordExists == true) {
            boolRecordExists = false;
            JOptionPane.showMessageDialog(null, "Student already exists");
        } else if (boolRecordExists == false) {
            mEditUpdateStudent(); // Call your updated method
            mTableStudentsView(); // Refresh the table with new data
            mClearTextFields();   // Clear inputs
        }
    }

    }//GEN-LAST:event_btnEditActionPerformed

    private void tblStudentsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblStudentsMouseClicked
        // TODO add your handling code here:
        
    
    DefaultTableModel model = (DefaultTableModel) tblStudents.getModel();
    int selectedIndex = tblStudents.getSelectedRow();

    // Fill text fields
    txtID.setText(model.getValueAt(selectedIndex, 1).toString());
    txtName.setText(model.getValueAt(selectedIndex, 2).toString());
    txtSurname.setText(model.getValueAt(selectedIndex, 3).toString());
    // Set date of birth
    try {
        String dobString = model.getValueAt(selectedIndex, 4).toString(); // format: yyyy-MM-dd
        java.util.Date date = new SimpleDateFormat("yyyy-MM-dd").parse(dobString);
        dcDOB.setDate(date);
    } catch (Exception e) {
        e.printStackTrace();
    }


    // Set gender radio buttons
    String gender = model.getValueAt(selectedIndex, 5).toString();
    if (gender.equalsIgnoreCase("Male")) {
        rdoMale.setSelected(true);
    } else if (gender.equalsIgnoreCase("Female")) {
        rdoFemale.setSelected(true);
    }

    
   

    }//GEN-LAST:event_tblStudentsMouseClicked

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
        //Delete
         
         int selectedIndex = tblStudents.getSelectedRow();
    if (selectedIndex == -1) { // No row is selected
        JOptionPane.showMessageDialog(null, "Please select a row to delete.");
    }else{
      
    //Add confirmation JOptionPane dialog before deleting a record
 int response = JOptionPane.showConfirmDialog(null, "Are You Sure You Want To DELETE this Student",
        "Select An Option",JOptionPane.YES_NO_OPTION,JOptionPane.ERROR_MESSAGE);
        
       if (response == JOptionPane.YES_OPTION){
        mGetValuesFromGUI();
        mDelete();  
        mTableStudentsView();
        mClearTextFields();
    }
       else{
           JOptionPane.showMessageDialog(null, "Delete Cancelled");
       }
    }    
    }//GEN-LAST:event_btnDeleteActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(existingStudentFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(existingStudentFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(existingStudentFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(existingStudentFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new existingStudentFrame().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnEdit;
    private javax.swing.JButton btnNext;
    private javax.swing.JButton btnReturn;
    private javax.swing.ButtonGroup buttonGroup1;
    private com.toedter.calendar.JDateChooser dcDOB;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblDOB;
    private javax.swing.JLabel lblExistingStudent;
    private javax.swing.JLabel lblGender;
    private javax.swing.JLabel lblID;
    private javax.swing.JLabel lblName;
    private javax.swing.JLabel lblSurname;
    private javax.swing.JRadioButton rdoFemale;
    private javax.swing.JRadioButton rdoMale;
    private javax.swing.JTable tblStudents;
    private javax.swing.JTextField txtID;
    private javax.swing.JTextField txtName;
    private javax.swing.JTextField txtSurname;
    // End of variables declaration//GEN-END:variables
}
