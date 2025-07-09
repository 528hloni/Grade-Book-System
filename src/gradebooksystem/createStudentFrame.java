/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package gradebooksystem;

import java.util.List;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;
import java.sql.PreparedStatement;
import java.util.ArrayList;


/**
 *
 * @author hloni
 */
public class createStudentFrame extends javax.swing.JFrame {

    /**
     * Creates new form createStudentFrame
     */
    public createStudentFrame() {
        initComponents();
    }
    
     Boolean boolRecordExists=false; //boolean will be used to check if record exists
     Boolean boolSubjectAlreadyLinked = false;  //boolean will be used to check if record exist
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
         
         lstSubjects.getSelectedValuesList(); // List<String>
       
        
       
    }
       
        private void mSetvaluesToUpperCase(){
        //values will be uppercase
       
        
        strName = strName.toUpperCase();
        strSurname = strSurname.toUpperCase();
        strGender = strGender.toUpperCase();
        
       
        }
       
        private void mLoadListItem() {
         //Loading items from Event table(event name)
         
         String URL5 = "jdbc:mysql://localhost:3306/gradebook_system"; //Connection string to the database
        String User5 = "root"; //User name to connect to database
        String Password5 = "528_hloni"; //User password to connect to database
        java.sql.Connection conMySQLConnectionString; //Declares connection string named conMySQLConnectionString,it will contain the driver for the connection string to the database
        Statement stStatement = null; //Declares statement named stStatement which will contain sql statement
        ResultSet rs = null; //Declares statement named rs which will contain quiried data from the table
         
        try{
        conMySQLConnectionString = DriverManager.getConnection(URL5,User5,Password5); //used to gain access to database
            stStatement = conMySQLConnectionString.createStatement();//This will instruct stStatement to execute SQL statement against the table in database
            String strQuery = "SELECT subject_name FROM subjects";
          
            stStatement.execute(strQuery);
         rs=stStatement.getResultSet();
         
          // Create model and load subject names
        DefaultListModel<String> model = new DefaultListModel<>();

        while (rs.next()) {
            model.addElement(rs.getString("subject_name")); //  For JList
        }

        lstSubjects.setModel(model);  //  Correct for JList
        
      

    } catch (Exception e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(this, "Error loading data", "Database Error", JOptionPane.ERROR_MESSAGE);
    }
        
       
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
         
         private void mCheckIfSubjectAlreadyLinked(int studentId, int subjectId) {
    // Validation to prevent duplication

    String URL = "jdbc:mysql://localhost:3306/gradebook_system";
    String User = "root";
    String Password = "528_hloni";
    java.sql.Connection conMySQLConnectionString;
    Statement stStatement = null;
    ResultSet rs = null;

    try {
        conMySQLConnectionString = DriverManager.getConnection(URL, User, Password);
        stStatement = conMySQLConnectionString.createStatement();

        String strQuery = "SELECT * FROM student_subjects WHERE student_id = " + studentId +
                          " AND subject_id = " + subjectId;

        stStatement.execute(strQuery);
        rs = stStatement.getResultSet();
        boolSubjectAlreadyLinked = rs.next(); // TRUE if subject already exists

    } catch (Exception e) {
        JOptionPane.showMessageDialog(null, e);
    } finally {
        try {
            if (stStatement != null) stStatement.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Statement not closed: " + e);
        }
    }
}
         
             private void mCreateStudent()
                //Create a new attendee
    {
        java.sql.Connection conMySQLConnectionString ; //Declares connection string named conMySQLConnectionString, it will contain the driver for the connection string to the database
        String URL2 = "jdbc:mysql://localhost:3306/gradebook_system"; //Connection string to the database
        String User2 = "root"; //User name to connect to database
        String Password2 = "528_hloni"; //User password to connect to database
        try {
            conMySQLConnectionString = DriverManager.getConnection(URL2,User2,Password2); //used to gain access to database
            Statement myStatement = conMySQLConnectionString.createStatement(); 
            strDOB = strDOB.replace("\"", ""); 
            String sqlinsert = "insert into students (id_number,name,surname,gender,date_of_birth) " + //Initialises the 'insert sql statement' to store the values inserted in the textfield
            "values ('" + strIdNumber + "','"+ strName+"','"+ strSurname + "','"+ strGender +"','"+strDOB+"')";
            myStatement.executeUpdate(sqlinsert); // Execute sql statements against the database table
            myStatement.close(); //Close connection of the database
            JOptionPane.showMessageDialog(null,"Complete");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e );
        
        }
        
    }  
             
              private void mCreateStudentSubjects()
                
    {
        java.sql.Connection conMySQLConnectionString ; //Declares connection string named conMySQLConnectionString, it will contain the driver for the connection string to the database
        String URL7 = "jdbc:mysql://localhost:3306/gradebook_system"; //Connection string to the database
        String User7 = "root"; //User name to connect to database
        String Password7 = "528_hloni"; //User password to connect to database
        try {
            conMySQLConnectionString = DriverManager.getConnection(URL7,User7,Password7); //used to gain access to database
           

        // Step 1: Get student_id using the ID number
        String getStudentIdQuery = "SELECT student_id FROM students WHERE id_number = ?";
        PreparedStatement pstStudent = conMySQLConnectionString.prepareStatement(getStudentIdQuery);
        pstStudent.setString(1, strIdNumber); // Assuming you've already collected intIdNumber from GUI
        ResultSet rsStudent = pstStudent.executeQuery();

        if (!rsStudent.next()) {
            JOptionPane.showMessageDialog(this, "Student not found.");
            return;
        }

        int studentId = rsStudent.getInt("student_id");

        // Step 2: Prepare statements
        String getSubjectIdQuery = "SELECT subject_id FROM subjects WHERE subject_name = ?";
        PreparedStatement pstSubject = conMySQLConnectionString.prepareStatement(getSubjectIdQuery);

        String insertLinkQuery = "INSERT INTO student_subjects (student_id, subject_id) VALUES (?, ?)";
        PreparedStatement pstInsert = conMySQLConnectionString.prepareStatement(insertLinkQuery);

        // Step 3: Loop through selected subjects from JList
        for (String subjectName : selectedSubjects) {
            // Lookup subject_id
            pstSubject.setString(1, subjectName);
            ResultSet rsSubject = pstSubject.executeQuery();

            if (rsSubject.next()) {
                int subjectId = rsSubject.getInt("subject_id");

                //Check if already linked before inserting
                 mCheckIfSubjectAlreadyLinked(studentId, subjectId);
        if (!boolSubjectAlreadyLinked) {
                pstInsert.setInt(1, studentId);
                pstInsert.setInt(2, subjectId);
                pstInsert.executeUpdate();
        }
            }
        }

        JOptionPane.showMessageDialog(this, "Subjects successfully linked to student.");

    } catch (Exception e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(this, "Error linking subjects: " + e.getMessage());
    }
}
              private void mLoadSelectedSubjectsFromJList() {
    selectedSubjects.clear(); //  clear before loading new selection
    for (Object subject : lstSubjects.getSelectedValuesList()) {
        selectedSubjects.add(subject.toString());
    }
}
        
      
       
        private void mClearTextFields(){ //This will clear textfields once the values have been captured
        txtID.setText("");
        txtName.setText("");
        txtSurname.setText("");
        dcDOB.setDate(null);
        buttonGroup1.clearSelection();
        lstSubjects.clearSelection();
       
        
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
        lblCreateStudent = new javax.swing.JLabel();
        lblID = new javax.swing.JLabel();
        lblName = new javax.swing.JLabel();
        lblSurname = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        lblSubjects = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        lstSubjects = new javax.swing.JList<>();
        txtID = new javax.swing.JTextField();
        txtName = new javax.swing.JTextField();
        txtSurname = new javax.swing.JTextField();
        dcDOB = new com.toedter.calendar.JDateChooser();
        btnCreate = new javax.swing.JButton();
        lblGender = new javax.swing.JLabel();
        rdoMale = new javax.swing.JRadioButton();
        rdoFemale = new javax.swing.JRadioButton();
        btnReturn = new javax.swing.JButton();
        btnClear = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        lblCreateStudent.setText("Create Student");

        lblID.setText("Identity Number :");

        lblName.setText("Name :");

        lblSurname.setText("Surname :");

        jLabel5.setText("Date Of Birth :");

        lblSubjects.setText("Subjects :");

        jScrollPane1.setViewportView(lstSubjects);

        txtName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNameActionPerformed(evt);
            }
        });

        btnCreate.setText("Create");
        btnCreate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCreateActionPerformed(evt);
            }
        });

        lblGender.setText("Gender :");

        buttonGroup1.add(rdoMale);
        rdoMale.setText("Male");

        buttonGroup1.add(rdoFemale);
        rdoFemale.setText("Female");

        btnReturn.setText("Return");
        btnReturn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnReturnActionPerformed(evt);
            }
        });

        btnClear.setText("Clear");
        btnClear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnClearActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(103, 103, 103)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                    .addComponent(lblID, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(38, 38, 38))
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                    .addComponent(lblName, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(88, 88, 88)))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(lblSurname, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(txtID, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtName, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtSurname, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 216, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(lblGender, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(lblSubjects, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 66, Short.MAX_VALUE))))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(166, 166, 166)
                        .addComponent(btnCreate)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnReturn)
                        .addGap(68, 68, 68)))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(75, 75, 75)
                                .addComponent(dcDOB, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(21, 21, 21)
                                .addComponent(rdoMale, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(33, 33, 33)
                                .addComponent(rdoFemale, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(49, 49, 49))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(119, 119, 119))))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(423, 423, 423)
                        .addComponent(lblCreateStudent, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(175, 175, 175)
                        .addComponent(btnClear)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(33, 33, 33)
                .addComponent(lblCreateStudent)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(64, 64, 64)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblID)
                            .addComponent(txtID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(75, 75, 75)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(dcDOB, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel5))))
                .addGap(32, 32, 32)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblName)
                    .addComponent(txtName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblGender)
                    .addComponent(rdoMale)
                    .addComponent(rdoFemale))
                .addGap(42, 42, 42)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblSurname)
                            .addComponent(txtSurname, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblSubjects))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 81, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnCreate)
                            .addComponent(btnReturn))
                        .addGap(45, 45, 45)
                        .addComponent(btnClear)
                        .addGap(123, 123, 123))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(21, 21, 21))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNameActionPerformed

    private void btnCreateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCreateActionPerformed
      
        mLoadSelectedSubjectsFromJList();
         //Create
        
        
        //Validation
      if(txtID.getText().equals(""))
        {
            JOptionPane.showMessageDialog(null,"The field cannot be left empty");
            txtID.requestFocusInWindow();
        }
        else if(txtName.getText().equals(""))
        {
        JOptionPane.showMessageDialog(null,"The field cannot be left empty");
        txtName.requestFocusInWindow();
        }
        else if(txtSurname.getText().equals(""))
        {
        JOptionPane.showMessageDialog(null,"The field cannot be left empty");
        txtSurname.requestFocusInWindow();
        }
         else if(dcDOB.getDate() == null)
        {
        JOptionPane.showMessageDialog(null,"The DOB field cannot be left empty");
        dcDOB.requestFocusInWindow();
        }
         else if(buttonGroup1.getSelection() == null)
        {    
        JOptionPane.showMessageDialog(null,"The Gender field cannot be left empty");   
        }
        
         
        else if(selectedSubjects.isEmpty())
        {
         JOptionPane.showMessageDialog(this,"Please select at least one subject");
       }
        else
        {
          
        mGetValuesFromGUI();
        mSetvaluesToUpperCase();
        mCheckIfItemsExistInTable();
        
        
        //if the record exist then show jOptionPane message then set boolean record to false
        //if the record doesnt exist then create passeneger,update the table then clear textfields
        if(boolRecordExists==true)
        {
        boolRecordExists=false;
        JOptionPane.showMessageDialog(null, "Student already exists");
        }
        else if (boolRecordExists==false)
        {
        mCreateStudent();  //Create Student
        mCreateStudentSubjects();  //Link Subjects
        mClearTextFields();
        JOptionPane.showMessageDialog(null, "New Student Created, Continue to 'Existing Student Button'");
         mainFrame frmMain = new mainFrame();
       frmMain.setVisible(true);
       this.setVisible(false);
        
        }    
        }     







    }//GEN-LAST:event_btnCreateActionPerformed

    private void btnReturnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnReturnActionPerformed
        mainFrame frmMain = new mainFrame();
       frmMain.setVisible(true);
       this.setVisible(false);
    }//GEN-LAST:event_btnReturnActionPerformed

    private void btnClearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnClearActionPerformed
        mClearTextFields();
    }//GEN-LAST:event_btnClearActionPerformed

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        mLoadListItem();   //Load items(subjects) from database to jList
    }//GEN-LAST:event_formWindowOpened

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
            java.util.logging.Logger.getLogger(createStudentFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(createStudentFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(createStudentFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(createStudentFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new createStudentFrame().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnClear;
    private javax.swing.JButton btnCreate;
    private javax.swing.JButton btnReturn;
    private javax.swing.ButtonGroup buttonGroup1;
    private com.toedter.calendar.JDateChooser dcDOB;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblCreateStudent;
    private javax.swing.JLabel lblGender;
    private javax.swing.JLabel lblID;
    private javax.swing.JLabel lblName;
    private javax.swing.JLabel lblSubjects;
    private javax.swing.JLabel lblSurname;
    private javax.swing.JList<String> lstSubjects;
    private javax.swing.JRadioButton rdoFemale;
    private javax.swing.JRadioButton rdoMale;
    private javax.swing.JTextField txtID;
    private javax.swing.JTextField txtName;
    private javax.swing.JTextField txtSurname;
    // End of variables declaration//GEN-END:variables
}
