/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package gradebooksystem;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author hloni
 */
public class studentResultFrame extends javax.swing.JFrame {

    private int iStudentId;
    private String sName;
    private String sSurname;
    
    public studentResultFrame(int iStudentId, String sName, String sSurname) {
        initComponents();
        this.iStudentId = iStudentId;
        this.sName = sName;
        this.sSurname = sSurname;
        
        lbllearner.setText(sName + " " + sSurname);
        
        
        
    }
    
     Boolean bRecordExists=false; //boolean will be used to check if record exists
     String sSubject;
     int iSubjectId;
     double dAverageMark;
     int iTerm;
     double dTotalTest;
     double dTotalExam;
     double dWeighTest;
     double dWeighExam;

    private studentResultFrame() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
    private void mLoadSubjectsIntoCB() {
    java.sql.Connection conMySQLConnectionString;    //Declares connection string named conMySQLConnectionString,it will contain the driver for the connection string to the database
    String sURL = "jdbc:mysql://localhost:3306/gradebook_system"; //Connection string to the database
    String sUser = "root";  //User name to connect to database
    String sPassword = "528_hloni"; //User password to connect to database
    PreparedStatement pst;

    try {
        conMySQLConnectionString = DriverManager.getConnection(sURL, sUser, sPassword); //used to gain access to database
        
        String sSelect = "SELECT sub.subject_name FROM student_subjects ss " +
                     "JOIN subjects sub ON ss.subject_id = sub.subject_id " +
                     "WHERE ss.student_id = ?";
         pst = conMySQLConnectionString.prepareStatement(sSelect); //SQL query to retrieve all records
        
        pst.setInt(1, iStudentId);  // `iStudentId` passed from the constructor
        ResultSet rs = pst.executeQuery();
        

        cbSubjects.removeAllItems(); // clear existing items

        while (rs.next()) {
            cbSubjects.addItem(rs.getString("subject_name"));
        }

    } catch (Exception e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(null, "Error loading subjects: " + e.getMessage());
        
    }
}
    
    private void mGetValuesFromGui(){
        
        try{
        sSubject = (String) cbSubjects.getSelectedItem();
        iSubjectId = mSubjectIdByName(sSubject);
        iTerm = Integer.parseInt(cbTerm.getSelectedItem().toString());
        dTotalTest = Double.parseDouble(txtTest.getText());
        dWeighTest = Double.parseDouble(txtWeighTest.getText());
        dTotalExam = Double.parseDouble(txtExams.getText());
        dWeighExam = Double.parseDouble(txtWeighExam.getText());
        
        // Calculate the final dAverage mark based on weights
        // (Test/100) * weight + (Exam/100) * weight
         dAverageMark = ((double) dTotalTest / 100) * dWeighTest
                   + ((double) dTotalExam / 100) * dWeighExam;
         
    
        
         } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null,"Enter correct input "+""+e);
        }
        
    
    
}
    
    
      private void mCheckIfItemsExistInTable(){
         //Validation to prevent duplication
         
        String sURL1 = "jdbc:mysql://localhost:3306/gradebook_system"; //Connection string to the database
        String sUser1 = "root"; //User name to connect to database
        String sPassword1 = "528_hloni"; //User password to connect to database
        java.sql.Connection conMySQLConnectionString; //Declares connection string named conMySQLConnectionString,it will contain the driver for the connection string to the database
        Statement stStatement = null; //Declares statement named stStatement which will contain sSelect statement
        ResultSet rs = null; //Declares statement named rs which will contain quiried data from the table
        
        // try catch contains code to run the query against database table
        try {
            conMySQLConnectionString = DriverManager.getConnection(sURL1,sUser1,sPassword1); //used to gain access to database
            stStatement = conMySQLConnectionString.createStatement();//This will instruct stStatement to execute SQL statement against the table in database
           
            String sQuery = "SELECT * FROM marks WHERE student_id = '" + iStudentId +
        "' AND subject_id = '" + iSubjectId + 
        "' AND term = '" + iTerm +
        "' AND test_total = '" + dTotalTest + 
        "' AND exam_total = '" + dTotalExam +
        "' AND test_weight = '" + dWeighTest +  
        "' AND exam_weight = '" + dWeighExam +          
        "' AND average_mark = '" + dAverageMark + "'";
            stStatement.execute(sQuery); // Execute sSelect statements against the database table
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
      
      private void mInsert()
              //Creating new marks
    {
        java.sql.Connection conMySQLConnectionString ; //Declares connection string named conMySQLConnectionString, it will contain the driver for the connection string to the database
        String sURL2 = "jdbc:mysql://localhost:3306/gradebook_system"; //Connection string to the database
        String sUser2 = "root"; //User name to connect to database
        String sPassword2 = "528_hloni"; //User password to connect to database
        try {
            conMySQLConnectionString = DriverManager.getConnection(sURL2,sUser2,sPassword2); //used to gain access to database
            Statement myStatement = conMySQLConnectionString.createStatement(); 
           String sInsert = "insert into marks (student_id,subject_id,term,test_total,exam_total,test_weight,exam_weight,average_mark) " + //Initialises the 'insert sSelect statement' to store the values inserted in the textfield
            "values ('" + iStudentId + "','"+ iSubjectId + "','" + iTerm + "','" + dTotalTest +"','"+ dTotalExam +"','" + dWeighTest + "','"+dWeighExam +"','" +dAverageMark + "')";
        
            myStatement.executeUpdate(sInsert); // Execute sSelect statements against the database table
            myStatement.close(); //Close connection of the database
            JOptionPane.showMessageDialog(null,"Complete");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        
        }
        
    }    
    
       private void mTableView(){
         //Viewing table
      String sURL5 = "jdbc:mysql://localhost:3306/gradebook_system"; //Declares connection string named conMySQLConnectionString, it will contain the driver for the connection string to the database
    String sUser5 = "root"; //User name to connect to database
    String sPassword5 = "528_hloni";  //User password to connect to database
     java.sql.Connection conMySQLConnectionString ; //Declares connection string named conMySQLConnectionString, it will contain the driver for the connection string to the database

    try{
        conMySQLConnectionString = DriverManager.getConnection(sURL5,sUser5,sPassword5); //used to gain access to database
        
         // SQL query:
        //  Gets subject names
        // Uses conditional aggregation to get marks for Term 1 to Term 4 per subject
        // Groups results by subject name
        String sql = """
            SELECT sub.subject_name,
                MAX(CASE WHEN m.term = 1 THEN m.average_mark ELSE NULL END) AS term1,
                MAX(CASE WHEN m.term = 2 THEN m.average_mark ELSE NULL END) AS term2,
                MAX(CASE WHEN m.term = 3 THEN m.average_mark ELSE NULL END) AS term3,
                MAX(CASE WHEN m.term = 4 THEN m.average_mark ELSE NULL END) AS term4
            FROM marks m
            JOIN subjects sub ON m.subject_id = sub.subject_id
            WHERE m.student_id = ?
            GROUP BY sub.subject_name
        """;

        // Prepare the SQL statement with a placeholder for student_id
        PreparedStatement pst = conMySQLConnectionString.prepareStatement(sql);
        pst.setInt(1, iStudentId); // Set the student ID to filter the marks
        
         // Execute the query
        ResultSet rs = pst.executeQuery();

        // Clear the existing rows in the table to avoid duplicates
        DefaultTableModel model = (DefaultTableModel) tblResults.getModel();
        model.setRowCount(0); // Clear table first

         // Iterate through each result row and add it to the JTable
        while (rs.next()) {
            Vector<Object> row = new Vector<>();
            row.add(rs.getString("subject_name"));
            row.add(rs.getObject("term1")); // can be NULL if no mark yet
            row.add(rs.getObject("term2"));
            row.add(rs.getObject("term3"));
            row.add(rs.getObject("term4"));
            model.addRow(row);
        }

    } catch (Exception e) {
        JOptionPane.showMessageDialog(null, e);
    }
}
       
       
       
         
         
         public void mEditUpdate() {
             
             // Get the model of the JTable to access its data
    DefaultTableModel model = (DefaultTableModel) tblResults.getModel();
    
     // Get index of the selected row
    int iSelectedIndex = tblResults.getSelectedRow();
    
    java.sql.Connection conMySQLConnectionString ; //Declares connection string named conMySQLConnectionString, it will contain the driver for the connection string to the database
        String sURL8 = "jdbc:mysql://localhost:3306/gradebook_system"; //Connection string to the database
        String sUser8 = "root"; //User name to connect to database
        String sPassword8 = "528_hloni"; //User password to connect to database
    
    
    
// Check if a row in the table is selected
    if (iSelectedIndex != -1) {
        try {
            // Get values from the table
            String sSubjectName = model.getValueAt(iSelectedIndex, 0).toString(); // Get Subject Name
            int iTerm = Integer.parseInt(cbTerm.getSelectedItem().toString()); // Get Term

            // Get marks and weights from text fields
            double dTestTotal = Double.parseDouble(txtTest.getText());
            double dExamTotal = Double.parseDouble(txtExams.getText());
            double dTestWeight = Double.parseDouble(txtWeighExam.getText());
            double dExamWeight = Double.parseDouble(txtWeighTest.getText());

            // Calculate dAverage mark
            double dAverage = (dTestTotal * (dTestWeight / 100)) + (dExamTotal * (dExamWeight / 100));

            // Get student_id (you should have stored it when opening this frame)
            int iStudentId = this.iStudentId;

            // Get subject_id from subject table using subject name
            conMySQLConnectionString = DriverManager.getConnection(sURL8,sUser8,sPassword8); //used to gain access to database
            PreparedStatement pstSubject = conMySQLConnectionString.prepareStatement("SELECT subject_id FROM subjects WHERE subject_name = ?");
            pstSubject.setString(1, sSubjectName);
            ResultSet rs = pstSubject.executeQuery();

            if (rs.next()) {
                int iSubjectId = rs.getInt("subject_id");

                // Update the correct record in marks table
                PreparedStatement pstUpdate = conMySQLConnectionString.prepareStatement("""
                    UPDATE marks 
                    SET test_total = ?, exam_total = ?, test_weight = ?, exam_weight = ?, average_mark = ?
                    WHERE student_id = ? AND subject_id = ? AND term = ? """);

                pstUpdate.setDouble(1, dTestTotal);
                pstUpdate.setDouble(2, dExamTotal);
                pstUpdate.setDouble(3, dTestWeight);
                pstUpdate.setDouble(4, dExamWeight);
                pstUpdate.setDouble(5, dAverage);
                pstUpdate.setInt(6, iStudentId);
                pstUpdate.setInt(7, iSubjectId);
                pstUpdate.setInt(8, iTerm);
                
               // Execute the update query
                int rowsUpdated = pstUpdate.executeUpdate();

                if (rowsUpdated > 0) {
                    JOptionPane.showMessageDialog(this, "Marks updated successfully.");
                    mTableView(); // Reload data into table
                } else {
                    JOptionPane.showMessageDialog(this, "No matching record found to update.");
                }
            } else {
                JOptionPane.showMessageDialog(this, "Subject not found in database.");
            }

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    } else {
        JOptionPane.showMessageDialog(this, "Please select a row to edit.");
    }
}
         
     
           
           
           
           public void mDelete() {
    DefaultTableModel model = (DefaultTableModel) tblResults.getModel();
    int iSelectedIndex = tblResults.getSelectedRow();

    if (iSelectedIndex != -1) {
        try {
            // Get values from selected row and GUI
            String sSubjectName = model.getValueAt(iSelectedIndex, 0).toString(); // Get subject name from the selected row
            int iTerm = Integer.parseInt(cbTerm.getSelectedItem().toString()); // Get the selected iTerm from the combo box

            // DB Connection details
            java.sql.Connection conMySQLConnectionString; //Declares connection string named conMySQLConnectionString, it will contain the driver for the connection string to the database
            String sURL = "jdbc:mysql://localhost:3306/gradebook_system";  //Connection string to the database
            String sUser = "root"; //User name to connect to database
            String sPassword = "528_hloni"; //User password to connect to database

            conMySQLConnectionString = DriverManager.getConnection(sURL, sUser, sPassword); //used to gain access to database

            // Get subject_id using subject_name
            PreparedStatement pstSubject = conMySQLConnectionString.prepareStatement(
                "SELECT subject_id FROM subjects WHERE subject_name = ?"
            );
            pstSubject.setString(1, sSubjectName);
            ResultSet rs = pstSubject.executeQuery();

             // If subject exists in database, proceed with deletion
            if (rs.next()) {
                int iSubjectId = rs.getInt("subject_id");

                // Delete record from marks table
                PreparedStatement pstDelete = conMySQLConnectionString.prepareStatement(
                    "DELETE FROM marks WHERE student_id = ? AND subject_id = ? AND term = ?"
                );
                pstDelete.setInt(1, this.iStudentId);
                pstDelete.setInt(2, iSubjectId);
                pstDelete.setInt(3, iTerm);

                // Execute delete query
                int iRowsDeleted = pstDelete.executeUpdate();

                if (iRowsDeleted > 0) {
                    JOptionPane.showMessageDialog(this, "Mark deleted successfully.");
                } else {
                    JOptionPane.showMessageDialog(this, "No matching record found to delete.");
                }
            } else {
                JOptionPane.showMessageDialog(this, "Subject not found in database.");
            }

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }
           }
           
           
           
           
           
          

           
           private int mSubjectIdByName(String sSubject) {
     iSubjectId = -1; // Default value in case the subject is not found

    String sURL9 = "jdbc:mysql://localhost:3306/gradebook_system"; //Connection string to the database
    String sUser9 = "root"; //User name to connect to database
    String sPassword9 = "528_hloni"; //User password to connect to database
    String sSelect = "SELECT subject_id FROM subjects WHERE subject_name = ?"; 
    

    try (
        java.sql.Connection conMySQLConnectionString = DriverManager.getConnection(sURL9, sUser9, sPassword9);
        PreparedStatement pst = conMySQLConnectionString.prepareStatement(sSelect)
    ) {
        pst.setString(1, sSubject); //User password to connect to database
        ResultSet rs = pst.executeQuery(); //User password to connect to database

        if (rs.next()) {
            //User password to connect to database
            iSubjectId = rs.getInt("subject_id");
        } else {
            //User password to connect to database
            JOptionPane.showMessageDialog(null, "Subject '" + sSubject + "' not found in database.");
        }

    } catch (Exception e) {
        JOptionPane.showMessageDialog(null, e);
    }
//User password to connect to database
    return iSubjectId;
}
   
    
    
    
    
    
    private void mClearTextFields(){ //This will clear textfields once the values have been captured
        
        
        cbSubjects.setSelectedIndex(0);
        cbTerm.setSelectedIndex(0);
        txtTest.setText("");
        txtExams.setText("");
        txtWeighTest.setText("");
        txtWeighExam.setText("");
        txtAverage.setText("");
       
        
    }
    
    
   private void mPrintReport() {
    String sURL = "jdbc:mysql://localhost:3306/gradebook_system";
    String sUser = "root";
    String sPassword = "528_hloni";
    java.sql.Connection conMySQLConnectionString;
    
    try {
        conMySQLConnectionString = DriverManager.getConnection(sURL, sUser, sPassword);
        // SQL query to get all marks for the student
        String sql = """
            SELECT sub.subject_name,
            MAX(CASE WHEN m.term = 1 THEN m.average_mark ELSE NULL END) AS term1,
            MAX(CASE WHEN m.term = 2 THEN m.average_mark ELSE NULL END) AS term2,
            MAX(CASE WHEN m.term = 3 THEN m.average_mark ELSE NULL END) AS term3,
            MAX(CASE WHEN m.term = 4 THEN m.average_mark ELSE NULL END) AS term4
            FROM marks m
            JOIN subjects sub ON m.subject_id = sub.subject_id
            WHERE m.student_id = ?
            GROUP BY sub.subject_name
            ORDER BY sub.subject_name
        """;
        
        PreparedStatement pst = conMySQLConnectionString.prepareStatement(sql);
        pst.setInt(1, iStudentId);
        ResultSet rs = pst.executeQuery();
        
        // Create filename with student name
        String sFileName = sName + "_" + sSurname + "_Academic_Report.txt";
        
        // Use FileWriter to create the text file
        java.io.FileWriter fileWriter = new java.io.FileWriter(sFileName);
        java.io.PrintWriter printWriter = new java.io.PrintWriter(fileWriter);
        
        // Write report header
        printWriter.println("===============================================");
        printWriter.println("           GRADE 12 REPEAT CENTER");
        printWriter.println("              STUDENT REPORT");
        printWriter.println("===============================================");
        printWriter.println();
        printWriter.println("Student Name: " + sName + " " + sSurname);
        printWriter.println("Student ID: " + iStudentId);
        printWriter.println("Report Generated: " + java.time.LocalDateTime.now().format(java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")));
        printWriter.println();
        printWriter.println("===============================================");
        printWriter.println("                ACADEMIC RESULTS");
        printWriter.println("===============================================");
        printWriter.println();
        
        // Format for subject results
        printWriter.printf("%-20s %-10s %-10s %-10s %-10s%n", "SUBJECT", "TERM 1", "TERM 2", "TERM 3", "TERM 4");
        printWriter.println("------------------------------------------------------------");
        
        boolean bHasData = false;
        
        // Write subject marks
        while (rs.next()) {
            bHasData = true;
            String subjectName = rs.getString("subject_name");
            Object term1 = rs.getObject("term1");
            Object term2 = rs.getObject("term2");
            Object term3 = rs.getObject("term3");
            Object term4 = rs.getObject("term4");
            
            // Format marks with pass/fail indication
            String sTerm1Str = formatMark(term1);
            String sTerm2Str = formatMark(term2);
            String sTerm3Str = formatMark(term3);
            String sTerm4Str = formatMark(term4);
            
            printWriter.printf("%-20s %-10s %-10s %-10s %-10s%n", 
                subjectName, sTerm1Str, sTerm2Str, sTerm3Str, sTerm4Str);
        }
        
        if (!bHasData) {
            printWriter.println("No academic records found for this student.");
        }
        
        printWriter.println();
        printWriter.println("===============================================");
        printWriter.println("Please Note Pass Mark: 30%");
        printWriter.println("Report End");
        printWriter.println("===============================================");
        
        // Close file writers
        printWriter.close();
        fileWriter.close();
        
        JOptionPane.showMessageDialog(this, 
            "Academic report has been generated successfully!\n" +
            "File saved as: " + sFileName + "\n" +
            "Location: " + System.getProperty("user.dir"), 
            "Report Generated", 
            JOptionPane.INFORMATION_MESSAGE);
        
    } catch (Exception e) {
        JOptionPane.showMessageDialog(this, 
            "Error generating report: " + e.getMessage(), 
            "Error", 
            JOptionPane.ERROR_MESSAGE);
        e.printStackTrace();
    }
}

// Helper method to format marks with pass/fail indication
private String formatMark(Object mark) {
    if (mark == null) {
    return "N/A";
    }
    
    double dMarkValue = (Double) mark;
    String sStatus = dMarkValue >= 30 ? "P" : "F"; // P for Pass, F for Fail (30% pass mark)
    return String.format("%.1f(%s)", dMarkValue, sStatus);
}    
    
    
    

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        Subject = new javax.swing.JPanel();
        lbllearner = new javax.swing.JLabel();
        lblSubject = new javax.swing.JLabel();
        lblTest = new javax.swing.JLabel();
        lblExams = new javax.swing.JLabel();
        lblTerm = new javax.swing.JLabel();
        lblWeighs1 = new javax.swing.JLabel();
        lblWeighs2 = new javax.swing.JLabel();
        cbSubjects = new javax.swing.JComboBox<>();
        cbTerm = new javax.swing.JComboBox<>();
        txtTest = new javax.swing.JTextField();
        txtWeighTest = new javax.swing.JTextField();
        txtExams = new javax.swing.JTextField();
        txtWeighExam = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblResults = new javax.swing.JTable();
        btnInsert = new javax.swing.JButton();
        btnEdit = new javax.swing.JButton();
        btnDelete = new javax.swing.JButton();
        btnClear = new javax.swing.JButton();
        btnPrintReport = new javax.swing.JButton();
        btnReturn = new javax.swing.JButton();
        btnMainMenu = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        txtAverage = new javax.swing.JTextArea();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        lbllearner.setFont(new java.awt.Font("Showcard Gothic", 0, 18)); // NOI18N
        lbllearner.setText("Learner name & surname");

        lblSubject.setText("Subject :");

        lblTest.setText("Total Test % :");

        lblExams.setText("Total Exams% :");

        lblTerm.setText("Term");

        lblWeighs1.setText("Weighs % :");

        lblWeighs2.setText("Weighs % :");

        cbTerm.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "1", "2", "3", "4" }));
        cbTerm.setSelectedIndex(-1);

        tblResults.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Subject", "Term 1", "Term 2", "Term 3", "Term 4"
            }
        ));
        tblResults.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblResultsMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblResults);

        btnInsert.setText("Insert");
        btnInsert.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnInsertActionPerformed(evt);
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

        btnClear.setText("Clear");
        btnClear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnClearActionPerformed(evt);
            }
        });

        btnPrintReport.setText("Print Report");
        btnPrintReport.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPrintReportActionPerformed(evt);
            }
        });

        btnReturn.setText("Return");
        btnReturn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnReturnActionPerformed(evt);
            }
        });

        btnMainMenu.setText("Main Menu");
        btnMainMenu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMainMenuActionPerformed(evt);
            }
        });

        txtAverage.setColumns(20);
        txtAverage.setRows(5);
        jScrollPane2.setViewportView(txtAverage);

        jLabel1.setFont(new java.awt.Font("Showcard Gothic", 2, 12)); // NOI18N
        jLabel1.setText("<html>Note: To edit term mark, click on specific term column</html>");

        javax.swing.GroupLayout SubjectLayout = new javax.swing.GroupLayout(Subject);
        Subject.setLayout(SubjectLayout);
        SubjectLayout.setHorizontalGroup(
            SubjectLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(SubjectLayout.createSequentialGroup()
                .addGap(63, 63, 63)
                .addGroup(SubjectLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(SubjectLayout.createSequentialGroup()
                        .addGroup(SubjectLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(lblSubject, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblExams, javax.swing.GroupLayout.DEFAULT_SIZE, 85, Short.MAX_VALUE)
                            .addComponent(lblTest, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGroup(SubjectLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(SubjectLayout.createSequentialGroup()
                                .addGap(35, 35, 35)
                                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(SubjectLayout.createSequentialGroup()
                                .addGroup(SubjectLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(SubjectLayout.createSequentialGroup()
                                        .addGap(11, 11, 11)
                                        .addComponent(cbSubjects, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(SubjectLayout.createSequentialGroup()
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addGroup(SubjectLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(txtExams, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(txtTest, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                .addGap(104, 104, 104)
                                .addGroup(SubjectLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addGroup(SubjectLayout.createSequentialGroup()
                                        .addGroup(SubjectLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(lblWeighs1, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(lblWeighs2, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addGroup(SubjectLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(txtWeighExam, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(txtWeighTest, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addGroup(SubjectLayout.createSequentialGroup()
                                        .addComponent(lblTerm, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(36, 36, 36)
                                        .addComponent(cbTerm, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 81, Short.MAX_VALUE))
                    .addGroup(SubjectLayout.createSequentialGroup()
                        .addGroup(SubjectLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(btnClear)
                            .addComponent(btnInsert))
                        .addGap(121, 121, 121)
                        .addGroup(SubjectLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnEdit)
                            .addComponent(btnPrintReport)
                            .addComponent(btnMainMenu))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(SubjectLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnDelete)
                            .addComponent(btnReturn))
                        .addGap(45, 45, 45)))
                .addGroup(SubjectLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(SubjectLayout.createSequentialGroup()
                        .addGap(100, 100, 100)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 301, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(127, 127, 127))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, SubjectLayout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 500, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(65, 65, 65))))
            .addGroup(SubjectLayout.createSequentialGroup()
                .addGap(372, 372, 372)
                .addComponent(lbllearner, javax.swing.GroupLayout.PREFERRED_SIZE, 321, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        SubjectLayout.setVerticalGroup(
            SubjectLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(SubjectLayout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(lbllearner, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(52, 52, 52)
                .addGroup(SubjectLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(SubjectLayout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 45, Short.MAX_VALUE))
                    .addGroup(SubjectLayout.createSequentialGroup()
                        .addGroup(SubjectLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblSubject)
                            .addComponent(lblTerm)
                            .addComponent(cbSubjects, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cbTerm, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(30, 30, 30)
                        .addGroup(SubjectLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtTest, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblTest)
                            .addComponent(lblWeighs1)
                            .addComponent(txtWeighTest, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(52, 52, 52)
                        .addGroup(SubjectLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblExams)
                            .addComponent(lblWeighs2)
                            .addComponent(txtExams, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtWeighExam, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(SubjectLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnInsert)
                            .addComponent(btnEdit)
                            .addComponent(btnDelete))
                        .addGap(60, 60, 60)
                        .addGroup(SubjectLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnPrintReport)
                            .addComponent(btnReturn)
                            .addComponent(btnClear))
                        .addGap(46, 46, 46)))
                .addGroup(SubjectLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnMainMenu)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(53, 53, 53))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(Subject, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(Subject, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnPrintReportActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPrintReportActionPerformed
         mPrintReport();
    }//GEN-LAST:event_btnPrintReportActionPerformed

    private void btnReturnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnReturnActionPerformed
         existingStudentFrame frmExisting = new existingStudentFrame();
       frmExisting.setVisible(true);
       this.setVisible(false);
        frmExisting.setLocationRelativeTo(null);
    }//GEN-LAST:event_btnReturnActionPerformed

    private void btnMainMenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMainMenuActionPerformed
        
       mainFrame frmMain = new mainFrame();
       frmMain.setVisible(true);
       this.setVisible(false);
        frmMain.setLocationRelativeTo(null);
    }//GEN-LAST:event_btnMainMenuActionPerformed

    private void btnClearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnClearActionPerformed
        mClearTextFields();
    }//GEN-LAST:event_btnClearActionPerformed

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        mLoadSubjectsIntoCB();
        mTableView();
    }//GEN-LAST:event_formWindowOpened

    private void btnInsertActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnInsertActionPerformed
    // validation
        if (cbSubjects.getSelectedIndex() == -1)
        {
            JOptionPane.showMessageDialog(null,"The field cannot be left empty");
            cbSubjects.requestFocusInWindow();
        }
        else if (cbTerm.getSelectedIndex() == -1)
        {
            JOptionPane.showMessageDialog(null, "The field cannot be left empty");
            cbTerm.requestFocusInWindow();
        }
        else if (txtTest.getText().equals(""))
        {
            JOptionPane.showMessageDialog(null, "The field cannot be left empty");
            txtTest.requestFocusInWindow();
        }
        else if (txtWeighTest.getText().equals(""))
        {
            JOptionPane.showMessageDialog(null, "The field cannot be left empty");
            txtWeighTest.requestFocusInWindow();
        }
        else if (txtExams.getText().equals(""))
        {
            JOptionPane.showMessageDialog(null, "The field cannot be left empty");
            txtExams.requestFocusInWindow();
        }
        else if (txtWeighExam.getText().equals(""))
        {
            JOptionPane.showMessageDialog(null, "The field cannot be left empty");
            txtWeighExam.requestFocusInWindow();
        }
        else
        {
            mGetValuesFromGui();
        mCheckIfItemsExistInTable();
        
        //if the record exist then show jOptionPane message then set boolean record to false
        //if the record doesnt exist then create passeneger,update the table then clear textfields
        if(bRecordExists==true)
        {
        bRecordExists=false;
        JOptionPane.showMessageDialog(null, "Already exists");
        }
        else if (bRecordExists==false)
        {
        mInsert();
        mTableView();
        mClearTextFields();
        }    
             
        }    
    }//GEN-LAST:event_btnInsertActionPerformed

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
        
            
         int iSelectedIndex = tblResults.getSelectedRow();
    if (iSelectedIndex == -1) { // No row is selected
        JOptionPane.showMessageDialog(null, "Please select a row to delete.");
    }else{
      
    //Add confirmation JOptionPane dialog before deleting a record
 int iResponse = JOptionPane.showConfirmDialog(null, "Are You Sure You Want To DELETE this Marks",
        "Select An Option",JOptionPane.YES_NO_OPTION,JOptionPane.ERROR_MESSAGE);
        
       if (iResponse == JOptionPane.YES_OPTION){
        mGetValuesFromGui();
        mDelete();  
        mTableView();
        mClearTextFields();
    }
       else{
           JOptionPane.showMessageDialog(null, "Delete Cancelled");
       }
    }    
    }//GEN-LAST:event_btnDeleteActionPerformed

    private void tblResultsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblResultsMouseClicked
             //selected row from table will appear on textfields 
    
             
              int iSelectedRow = tblResults.getSelectedRow();
    int iSelectedColumn = tblResults.getSelectedColumn();

    if (iSelectedRow == -1 || iSelectedColumn == -1) return;

    // Get subject name from first column (column 0)
    String sSubjectName = tblResults.getValueAt(iSelectedRow, 0).toString();

    // Get Term number from column index
    int iTerm = iSelectedColumn; // term2 = column 2 ⇒ iTerm = 2, etc.
    
    if (iTerm < 1 || iTerm > 4) return; // Prevent clicking on the subject column (index 0)

    // Set combo boxes
    cbSubjects.setSelectedItem(sSubjectName);
    cbTerm.setSelectedItem(String.valueOf(iTerm));
    
    
     String sURL4 = "jdbc:mysql://localhost:3306/gradebook_system";
    String sUser4 = "root";
    String sPassword4 = "528_hloni"; // 
    PreparedStatement pst;
    java.sql.Connection conMySQLConnectionString ;
 int iSubjectId = mSubjectIdByName(sSubjectName);
    
        

    // Now fetch from DB
    try {
       conMySQLConnectionString = DriverManager.getConnection(sURL4, sUser4, sPassword4);
    String sSelect =  "SELECT test_total, test_weight, exam_total, exam_weight, average_mark FROM marks " +
        "WHERE subject_Id = ? AND term = ? AND student_Id = ?";
    pst = conMySQLConnectionString.prepareStatement(sSelect);
        
  
        pst.setInt(1, iSubjectId);
        pst.setInt(2, iTerm);
        pst.setInt(3, iStudentId); // Replace with actual variable for selected student
        
        ResultSet rs = pst.executeQuery();

        if (rs.next()) {
            txtTest.setText(rs.getString("test_total"));
            txtWeighTest.setText(rs.getString("test_weight"));
            txtExams.setText(rs.getString("exam_total"));
            txtWeighExam.setText(rs.getString("exam_weight"));
            txtAverage.setText("The Average Term Mark is "+rs.getString("average_mark"));
        } else {
            // If no data found, clear fields
            txtTest.setText("");
            txtWeighTest.setText("");
            txtExams.setText("");
            txtWeighExam.setText("");
            txtAverage.setText("");
        }

    } catch (SQLException e) {
        JOptionPane.showMessageDialog(this, "Error loading mark data: " + e.getMessage());
    }

    
    }//GEN-LAST:event_tblResultsMouseClicked

    private void btnEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditActionPerformed
        int iSelectedIndex = tblResults.getSelectedRow();
        if (iSelectedIndex == -1) { // No row is selected
            JOptionPane.showMessageDialog(null, "Please select a row to edit.");
        }else{

            mGetValuesFromGui();
            mCheckIfItemsExistInTable();
            //if the record exist then show jOptionPane message then set boolean record to false
            //if the record doesnt exist then create passeneger,update the table then clear textfields
            if(bRecordExists==true)
            {
                bRecordExists=false;
                JOptionPane.showMessageDialog(null, "Already exists");
            }
            else if (bRecordExists==false)
            {
                mEditUpdate();
                mTableView();
                mClearTextFields();
            }
        }
    }//GEN-LAST:event_btnEditActionPerformed

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
            java.util.logging.Logger.getLogger(studentResultFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(studentResultFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(studentResultFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(studentResultFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new studentResultFrame().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel Subject;
    private javax.swing.JButton btnClear;
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnEdit;
    private javax.swing.JButton btnInsert;
    private javax.swing.JButton btnMainMenu;
    private javax.swing.JButton btnPrintReport;
    private javax.swing.JButton btnReturn;
    private javax.swing.JComboBox<String> cbSubjects;
    private javax.swing.JComboBox<String> cbTerm;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lblExams;
    private javax.swing.JLabel lblSubject;
    private javax.swing.JLabel lblTerm;
    private javax.swing.JLabel lblTest;
    private javax.swing.JLabel lblWeighs1;
    private javax.swing.JLabel lblWeighs2;
    private javax.swing.JLabel lbllearner;
    private javax.swing.JTable tblResults;
    private javax.swing.JTextArea txtAverage;
    private javax.swing.JTextField txtExams;
    private javax.swing.JTextField txtTest;
    private javax.swing.JTextField txtWeighExam;
    private javax.swing.JTextField txtWeighTest;
    // End of variables declaration//GEN-END:variables
}
