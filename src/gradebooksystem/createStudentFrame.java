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
    
     Boolean bRecordExists=false; //boolean will be used to check if record exists
     Boolean bSubjectAlreadyLinked = false;  //boolean will be used to check if subject exists
    String sIdNumber;
    String sName;
    String sSurname;
    String sDOB;
    String sGender;
    private List<String> sSelectedSubjects = new ArrayList<>();
    
    
   
    
       private void mGetValuesFromGUI(){
        //values from the user
         try {
          sIdNumber = txtID.getText();
          sName = txtName.getText();
          sSurname = txtSurname.getText();
          
           SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");//format
        sDOB= sdf1.format(dcDOB.getDate());
       
          // Get gender selection from radio buttons
         if (rdoMale.isSelected()) {
            sGender = "Male";
        } else if (rdoFemale.isSelected()) {
            sGender = "Female";
        } else {
            sGender = ""; // fallback to prevent null
        }
        
         } catch (Exception e) {
             e.printStackTrace();
              JOptionPane.showMessageDialog(null, "Enter correct input"+""+e);
             
         }
         
         // Get the selected subjects from the list box (returns a List<String>)
         lstSubjects.getSelectedValuesList(); // List<String>  
       
    }
       
        private void mSetvaluesToUpperCase(){
        //values will be uppercase
        
        sName = sName.toUpperCase();
        sSurname = sSurname.toUpperCase();
        sGender = sGender.toUpperCase();
        
       
        }
       
        private void mLoadListItem() {
         //Loading items from table to JList
         
         String sURL5 = "jdbc:mysql://localhost:3306/gradebook_system"; //Connection string to the database
        String sUser5 = "root"; //User name to connect to database
        String sPassword5 = "528_hloni"; //User password to connect to database
        java.sql.Connection conMySQLConnectionString; //Declares connection string named conMySQLConnectionString,it will contain the driver for the connection string to the database
        Statement stStatement = null; //Declares statement named stStatement which will contain sql statement
        ResultSet rs = null; //Declares statement named rs which will contain quiried data from the table
         
        try{
        conMySQLConnectionString = DriverManager.getConnection(sURL5,sUser5,sPassword5); //used to gain access to database
            stStatement = conMySQLConnectionString.createStatement();//This will instruct stStatement to execute SQL statement against the table in database
            String sQuery = "SELECT subject_name FROM subjects";
          
            stStatement.execute(sQuery);
         rs=stStatement.getResultSet();
         
          // Create sModel and load subject names
        DefaultListModel<String> sModel = new DefaultListModel<>();

        while (rs.next()) {
            sModel.addElement(rs.getString("subject_name")); //  For JList
        }

        lstSubjects.setModel(sModel);  //  Correct for JList
        
      

    } catch (Exception e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(this, "Error loading data", "Database Error", JOptionPane.ERROR_MESSAGE);
    }
        
       
}
        
         private void mCheckIfItemsExistInTable(){
         //Validation to prevent duplication
         
        String sURL1 = "jdbc:mysql://localhost:3306/gradebook_system"; //Connection string to the database
        String sUser1 = "root"; //User name to connect to database
        String sPassword1 = "528_hloni"; //User password to connect to database
        java.sql.Connection conMySQLConnectionString; //Declares connection string named conMySQLConnectionString,it will contain the driver for the connection string to the database
        Statement stStatement = null; //Declares statement named stStatement which will contain sql statement
        ResultSet rs = null; //Declares statement named rs which will contain quiried data from the table
        
        // try catch contains code to run the query against database table
        try {
            conMySQLConnectionString = DriverManager.getConnection(sURL1,sUser1,sPassword1); //used to gain access to database
            stStatement = conMySQLConnectionString.createStatement();//This will instruct stStatement to execute SQL statement against the table in database
           
            String sQuery = "SELECT * FROM students WHERE id_number = '" + sIdNumber +
        "' AND name = '" + sName + 
        "' AND surname = '" + sSurname +
        "' AND gender = '" + sGender + 
        "' AND date_of_birth = '" + sDOB + "'";
            
            stStatement.execute(sQuery); // Execute sql statements against the database table
            rs=stStatement.getResultSet();
            bRecordExists=rs.next(); //Confirm if the record exist or not in the database
            
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

    String sURL = "jdbc:mysql://localhost:3306/gradebook_system"; //Connection string to the database
    String sUser = "root"; //User name to connect to database
    String sPassword = "528_hloni"; //User password to connect to database
    java.sql.Connection conMySQLConnectionString; //Declares connection string named conMySQLConnectionString,it will contain the driver for the connection string to the database
    Statement stStatement = null; //Declares statement named stStatement which will contain sql statement
    ResultSet rs = null; //Declares statement named rs which will contain quiried data from the table

    try {
        conMySQLConnectionString = DriverManager.getConnection(sURL, sUser, sPassword);  //Used to gain access to database
        stStatement = conMySQLConnectionString.createStatement(); //This will instruct stStatement to execute SQL statement against the table in database

        String sQuery = "SELECT * FROM student_subjects WHERE student_id = " + studentId +
                          " AND subject_id = " + subjectId;

        stStatement.execute(sQuery); // Execute sql statements against the database table
        rs = stStatement.getResultSet();
        bSubjectAlreadyLinked = rs.next(); // TRUE if subject already exists

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
         
             private void mCreateStudent() {
                //Create a new student
    
        java.sql.Connection conMySQLConnectionString ; //Declares connection string named conMySQLConnectionString, it will contain the driver for the connection string to the database
        String sURL2 = "jdbc:mysql://localhost:3306/gradebook_system"; //Connection string to the database
        String sUser2 = "root"; //User name to connect to database
        String sPassword2 = "528_hloni"; //User password to connect to database
        try {
            conMySQLConnectionString = DriverManager.getConnection(sURL2,sUser2,sPassword2); //used to gain access to database
            Statement myStatement = conMySQLConnectionString.createStatement(); 
            sDOB = sDOB.replace("\"", ""); 
            String sInsert = "insert into students (id_number,name,surname,gender,date_of_birth) " + //Initialises the 'insert sql statement' to store the values inserted in the textfield
            "values ('" + sIdNumber + "','"+ sName+"','"+ sSurname + "','"+ sGender +"','"+sDOB+"')";
            myStatement.executeUpdate(sInsert); // Execute sql statements against the database table
            myStatement.close(); //Close connection of the database
            JOptionPane.showMessageDialog(null,"Complete");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e );
        
        }
        
    }  
             
              private void mCreateStudentSubjects() {
              // Create Subjects  
    
        java.sql.Connection conMySQLConnectionString ; //Declares connection string named conMySQLConnectionString, it will contain the driver for the connection string to the database
        String sURL7 = "jdbc:mysql://localhost:3306/gradebook_system"; //Connection string to the database
        String sUser7 = "root"; //User name to connect to database
        String sPassword7 = "528_hloni"; //User password to connect to database
        try {
            conMySQLConnectionString = DriverManager.getConnection(sURL7,sUser7,sPassword7); //used to gain access to database
           

        //  Get student_id using the ID number
        String sGetStudentIdQuery = "SELECT student_id FROM students WHERE id_number = ?";
        PreparedStatement pstStudent = conMySQLConnectionString.prepareStatement(sGetStudentIdQuery);
        pstStudent.setString(1, sIdNumber); // Assuming you've already collected intIdNumber from GUI
        ResultSet rsStudent = pstStudent.executeQuery();

        if (!rsStudent.next()) {
            JOptionPane.showMessageDialog(this, "Student not found.");
            return;
        }

        int iStudentId = rsStudent.getInt("student_id");

        //  Prepare statements
        String sGetSubjectIdQuery = "SELECT subject_id FROM subjects WHERE subject_name = ?";
        PreparedStatement pstSubject = conMySQLConnectionString.prepareStatement(sGetSubjectIdQuery);

        String sInsertLinkQuery = "INSERT INTO student_subjects (student_id, subject_id) VALUES (?, ?)";
        PreparedStatement pstInsert = conMySQLConnectionString.prepareStatement(sInsertLinkQuery);

        // Loop through selected subjects from JList
        for (String sSubjectName : sSelectedSubjects) {
            // Lookup subject_id
            pstSubject.setString(1, sSubjectName);
            ResultSet rsSubject = pstSubject.executeQuery();

            if (rsSubject.next()) {
                int iSubjectId = rsSubject.getInt("subject_id");

                //Check if already linked before inserting
                 mCheckIfSubjectAlreadyLinked(iStudentId, iSubjectId);
        if (!bSubjectAlreadyLinked) {
                pstInsert.setInt(1, iStudentId);
                pstInsert.setInt(2, iSubjectId);
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
          // Method to load the selected subjects from the JList into the sSelectedSubjects list
          
    sSelectedSubjects.clear(); //  clear before loading new selection
    for (Object subject : lstSubjects.getSelectedValuesList()) {   // Loop through each selected item in the JList
        sSelectedSubjects.add(subject.toString());// Convert each selected object to a String and add it to the sSelectedSubjects list
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
        lblMultiple = new javax.swing.JLabel();

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

        lblMultiple.setText("Note: To click multiple subjects, press CRTL and ENTER");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(166, 166, 166)
                        .addComponent(btnCreate)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnReturn)
                        .addGap(68, 68, 68))
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
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(lblGender, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(lblSubjects, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 66, Short.MAX_VALUE))
                                .addGap(12, 12, 12))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 200, Short.MAX_VALUE)
                                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(75, 75, 75)
                                .addComponent(dcDOB, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(28, 28, 28)
                                .addComponent(rdoMale, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(33, 33, 33)
                                .addComponent(rdoFemale, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(22, 22, 22))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(121, 121, 121))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addComponent(lblMultiple)
                                .addContainerGap())))))
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
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(42, 42, 42)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblSurname)
                            .addComponent(txtSurname, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblSubjects))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 96, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnCreate)
                            .addComponent(btnReturn))
                        .addGap(45, 45, 45)
                        .addComponent(btnClear)
                        .addGap(123, 123, 123))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(34, 34, 34)
                        .addComponent(lblMultiple, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNameActionPerformed

    private void btnCreateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCreateActionPerformed
      
        mLoadSelectedSubjectsFromJList();
         
        
        
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
        
         
        else if(sSelectedSubjects.isEmpty())
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
        if(bRecordExists==true)
        {
        bRecordExists=false;
        JOptionPane.showMessageDialog(null, "Student already exists");
        }
        else if (bRecordExists==false)
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
    private javax.swing.JLabel lblMultiple;
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
