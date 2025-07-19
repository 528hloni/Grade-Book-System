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

    private int intStudentId;
    private String strName;
    private String strSurname;
    
    public studentResultFrame(int intStudentId, String strName, String strSurname) {
        initComponents();
        this.intStudentId = intStudentId;
        this.strName = strName;
        this.strSurname = strSurname;
        
        lbllearner.setText( strName + " " + strSurname);
        
        
        
    }
    
     Boolean boolRecordExists=false; //boolean will be used to check if record exists
     String strSubject;
     int intSubjectId;
     double averageMark;
     int intTerm;
     double dblTotalTest;
     double dblTotalExam;
     double dblWeighTest;
     double dblWeighExam;

    private studentResultFrame() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
    private void mLoadSubjectsIntoCB() {
    java.sql.Connection conMySQLConnectionString;    
    String URL = "jdbc:mysql://localhost:3306/gradebook_system";
    String User = "root";
    String Password = "528_hloni"; // 
    PreparedStatement pst;

    try {
        conMySQLConnectionString = DriverManager.getConnection(URL, User, Password);
        
        String sql = "SELECT sub.subject_name FROM student_subjects ss " +
                     "JOIN subjects sub ON ss.subject_id = sub.subject_id " +
                     "WHERE ss.student_id = ?";
         pst = conMySQLConnectionString.prepareStatement(sql);
        
        pst.setInt(1, intStudentId);  // `studentId` passed from the constructor
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
        strSubject = (String) cbSubjects.getSelectedItem();
        intSubjectId = mSubjectIdByName(strSubject);
        intTerm = Integer.parseInt(cbTerm.getSelectedItem().toString());
        dblTotalTest = Double.parseDouble(txtTest.getText());
        dblWeighTest = Double.parseDouble(txtWeighTest.getText());
        dblTotalExam = Double.parseDouble(txtExams.getText());
        dblWeighExam = Double.parseDouble(txtWeighExam.getText());
        
        
         averageMark = ((double) dblTotalTest / 100) * dblWeighTest
                   + ((double) dblTotalExam / 100) * dblWeighExam;
         
    
        
         } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null,"Enter correct input "+""+e);
        }
        
     //   txtAverage.setText(String.valueOf("The Term Average is: "+averageMark+ "%"));
    
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
           
            String strQuery = "SELECT * FROM marks WHERE student_id = '" + intStudentId +
        "' AND subject_id = '" + intSubjectId + 
        "' AND term = '" + intTerm +
        "' AND test_total = '" + dblTotalTest + 
        "' AND exam_total = '" + dblTotalExam +
        "' AND test_weight = '" + dblWeighTest +  
        "' AND exam_weight = '" + dblWeighExam +          
        "' AND average_mark = '" + averageMark + "'";
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
      
      private void mInsert()
              //Creating new venues
    {
        java.sql.Connection conMySQLConnectionString ; //Declares connection string named conMySQLConnectionString, it will contain the driver for the connection string to the database
        String URL2 = "jdbc:mysql://localhost:3306/gradebook_system"; //Connection string to the database
        String User2 = "root"; //User name to connect to database
        String Password2 = "528_hloni"; //User password to connect to database
        try {
            conMySQLConnectionString = DriverManager.getConnection(URL2,User2,Password2); //used to gain access to database
            Statement myStatement = conMySQLConnectionString.createStatement(); 
           String sqlinsert = "insert into marks (student_id,subject_id,term,test_total,exam_total,test_weight,exam_weight,average_mark) " + //Initialises the 'insert sql statement' to store the values inserted in the textfield
            "values ('" + intStudentId + "','"+ intSubjectId + "','" + intTerm + "','" + dblTotalTest +"','"+ dblTotalExam +"','" + dblWeighTest + "','"+dblWeighExam +"','" +averageMark + "')";
        
            myStatement.executeUpdate(sqlinsert); // Execute sql statements against the database table
            myStatement.close(); //Close connection of the database
            JOptionPane.showMessageDialog(null,"Complete");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        
        }
        
    }    
    
       private void mTableView(){
         //Viewing table
      String URL5 = "jdbc:mysql://localhost:3306/gradebook_system";
    String User5 = "root";
    String Password5 = "528_hloni";
     java.sql.Connection conMySQLConnectionString ;

    try{
        conMySQLConnectionString = DriverManager.getConnection(URL5,User5,Password5);
        String sql = """
            SELECT 
                sub.subject_name,
                MAX(CASE WHEN m.term = 1 THEN m.average_mark ELSE NULL END) AS term1,
                MAX(CASE WHEN m.term = 2 THEN m.average_mark ELSE NULL END) AS term2,
                MAX(CASE WHEN m.term = 3 THEN m.average_mark ELSE NULL END) AS term3,
                MAX(CASE WHEN m.term = 4 THEN m.average_mark ELSE NULL END) AS term4
            FROM marks m
            JOIN subjects sub ON m.subject_id = sub.subject_id
            WHERE m.student_id = ?
            GROUP BY sub.subject_name
        """;

        PreparedStatement pst = conMySQLConnectionString.prepareStatement(sql);
        pst.setInt(1, intStudentId);
        ResultSet rs = pst.executeQuery();

        DefaultTableModel model = (DefaultTableModel) tblResults.getModel();
        model.setRowCount(0); // Clear table first

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
       
         private void mEditUpdates()
             //  Update/Edit existing venues
   { 
    java.sql.Connection conMySQLConnectionString ; //Declares connection string named conMySQLConnectionString, it will contain the driver for the connection string to the database
        String URL3 = "jdbc:mysql://localhost:3306/gradebook_system"; //Connection string to the database
        String User3 = "root"; //User name to connect to database
        String Password3 = "528_hloni"; //User password to connect to database
     
        //Get selected row information
         DefaultTableModel model = (DefaultTableModel) tblResults.getModel();//Get model of table
      int selectedIndex = tblResults.getSelectedRow();
      
      int intMarkId = Integer.parseInt(model.getValueAt(selectedIndex,0).toString());
    String subjectName = model.getValueAt(selectedIndex, 0).toString();
    
        try {
            conMySQLConnectionString = DriverManager.getConnection(URL3,User3,Password3); //used to gain access to database
            Statement euStatement = conMySQLConnectionString.createStatement(); 
            String strQuery = "Update marks Set student_id = '" + intStudentId +
                  "', subject_id = '" + intSubjectId + 
                  "', term = '" + intTerm + 
                  "', test_total = '" + dblTotalTest + 
                  "', exam_total = '" + dblTotalExam + 
                  "', test_weight = '" + dblWeighTest + 
                  "', exam_weight = '" + dblWeighExam +
                  "', average = '" + averageMark + 
                  "' Where mark_id = "+intMarkId;
            euStatement.executeUpdate(strQuery); // Execute sql statements against the database table
            euStatement.close(); //Close connection of the database
            JOptionPane.showMessageDialog(null,"Record Updated");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
         
        }
   }
         
         public void mEditUpdate() {
    DefaultTableModel model = (DefaultTableModel) tblResults.getModel();
    int selectedIndex = tblResults.getSelectedRow();
    
    java.sql.Connection conMySQLConnectionString ; //Declares connection string named conMySQLConnectionString, it will contain the driver for the connection string to the database
        String URL8 = "jdbc:mysql://localhost:3306/gradebook_system"; //Connection string to the database
        String User8 = "root"; //User name to connect to database
        String Password8 = "528_hloni"; //User password to connect to database
    
    
    

    if (selectedIndex != -1) {
        try {
            // Get values from the table
            String subjectName = model.getValueAt(selectedIndex, 0).toString(); // column 0 = Subject Name
            int term = Integer.parseInt(cbTerm.getSelectedItem().toString());

            // Get marks and weights from text fields
            double testTotal = Double.parseDouble(txtTest.getText());
            double examTotal = Double.parseDouble(txtExams.getText());
            double testWeight = Double.parseDouble(txtWeighExam.getText());
            double examWeight = Double.parseDouble(txtWeighTest.getText());

            // Calculate average mark
            double average = (testTotal * (testWeight / 100)) + (examTotal * (examWeight / 100));

            // Get student_id (you should have stored it when opening this frame)
            int studentId = this.intStudentId;

            // Get subject_id from subject table using subject name
            conMySQLConnectionString = DriverManager.getConnection(URL8,User8,Password8); //used to gain access to database
            PreparedStatement pstSubject = conMySQLConnectionString.prepareStatement("SELECT subject_id FROM subjects WHERE subject_name = ?");
            pstSubject.setString(1, subjectName);
            ResultSet rs = pstSubject.executeQuery();

            if (rs.next()) {
                int subjectId = rs.getInt("subject_id");

                // Update the correct record in marks table
                PreparedStatement pstUpdate = conMySQLConnectionString.prepareStatement("""
                    UPDATE marks 
                    SET test_total = ?, exam_total = ?, test_weight = ?, exam_weight = ?, average_mark = ?
                    WHERE student_id = ? AND subject_id = ? AND term = ? """);

                pstUpdate.setDouble(1, testTotal);
                pstUpdate.setDouble(2, examTotal);
                pstUpdate.setDouble(3, testWeight);
                pstUpdate.setDouble(4, examWeight);
                pstUpdate.setDouble(5, average);
                pstUpdate.setInt(6, studentId);
                pstUpdate.setInt(7, subjectId);
                pstUpdate.setInt(8, term);

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
    int selectedIndex = tblResults.getSelectedRow();

    if (selectedIndex != -1) {
        try {
            // Get values from selected row and GUI
            String subjectName = model.getValueAt(selectedIndex, 0).toString(); // column 0 = subject name
            int term = Integer.parseInt(cbTerm.getSelectedItem().toString());

            // DB Connection details
            java.sql.Connection conMySQLConnectionString;
            String URL = "jdbc:mysql://localhost:3306/gradebook_system";
            String User = "root";
            String Password = "528_hloni";

            conMySQLConnectionString = DriverManager.getConnection(URL, User, Password);

            // Get subject_id using subject_name
            PreparedStatement pstSubject = conMySQLConnectionString.prepareStatement(
                "SELECT subject_id FROM subjects WHERE subject_name = ?"
            );
            pstSubject.setString(1, subjectName);
            ResultSet rs = pstSubject.executeQuery();

            if (rs.next()) {
                int subjectId = rs.getInt("subject_id");

                // Delete record from marks table
                PreparedStatement pstDelete = conMySQLConnectionString.prepareStatement(
                    "DELETE FROM marks WHERE student_id = ? AND subject_id = ? AND term = ?"
                );
                pstDelete.setInt(1, this.intStudentId);
                pstDelete.setInt(2, subjectId);
                pstDelete.setInt(3, term);

                int rowsDeleted = pstDelete.executeUpdate();

                if (rowsDeleted > 0) {
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
           
           
           
           
           
          

           
           private int mSubjectIdByName(String strSubject) {
     intSubjectId = -1; // Local variable, initialize with -1

    String URL9 = "jdbc:mysql://localhost:3306/gradebook_system";
    String User9 = "root";
    String Password9 = "528_hloni";
    String sql = "SELECT subject_id FROM subjects WHERE subject_name = ?"; 

    try (
        java.sql.Connection conMySQLConnectionString = DriverManager.getConnection(URL9, User9, Password9);
        PreparedStatement pst = conMySQLConnectionString.prepareStatement(sql)
    ) {
        pst.setString(1, strSubject);
        ResultSet rs = pst.executeQuery();

        if (rs.next()) {
            intSubjectId = rs.getInt("subject_id");
        } else {
            JOptionPane.showMessageDialog(null, "Subject '" + strSubject + "' not found in database.");
        }

    } catch (Exception e) {
        JOptionPane.showMessageDialog(null, e);
    }

    return intSubjectId;
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

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

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

        javax.swing.GroupLayout SubjectLayout = new javax.swing.GroupLayout(Subject);
        Subject.setLayout(SubjectLayout);
        SubjectLayout.setHorizontalGroup(
            SubjectLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(SubjectLayout.createSequentialGroup()
                .addGap(63, 63, 63)
                .addGroup(SubjectLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
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
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(SubjectLayout.createSequentialGroup()
                        .addGroup(SubjectLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(lblSubject, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblExams, javax.swing.GroupLayout.DEFAULT_SIZE, 85, Short.MAX_VALUE)
                            .addComponent(lblTest, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(34, 34, 34)
                        .addGroup(SubjectLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(SubjectLayout.createSequentialGroup()
                                .addGroup(SubjectLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(txtTest, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(cbSubjects, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtExams, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(103, 103, 103)
                                .addGroup(SubjectLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, SubjectLayout.createSequentialGroup()
                                        .addComponent(lblTerm, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(54, 54, 54)
                                        .addComponent(cbTerm, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(SubjectLayout.createSequentialGroup()
                                        .addGroup(SubjectLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                            .addComponent(lblWeighs2, javax.swing.GroupLayout.DEFAULT_SIZE, 70, Short.MAX_VALUE)
                                            .addComponent(lblWeighs1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addGroup(SubjectLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(txtWeighTest, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(txtWeighExam, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                            .addGroup(SubjectLayout.createSequentialGroup()
                                .addGap(1, 1, 1)
                                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 24, Short.MAX_VALUE)))
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 500, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addGroup(SubjectLayout.createSequentialGroup()
                .addGap(422, 422, 422)
                .addComponent(lbllearner, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE)
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
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
                .addComponent(btnMainMenu)
                .addGap(89, 89, 89))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(Subject, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(Subject, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnPrintReportActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPrintReportActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnPrintReportActionPerformed

    private void btnReturnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnReturnActionPerformed
         existingStudentFrame frmExisting = new existingStudentFrame();
       frmExisting.setVisible(true);
       this.setVisible(false);
    }//GEN-LAST:event_btnReturnActionPerformed

    private void btnMainMenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMainMenuActionPerformed
        
       mainFrame frmMain = new mainFrame();
       frmMain.setVisible(true);
       this.setVisible(false);
    }//GEN-LAST:event_btnMainMenuActionPerformed

    private void btnClearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnClearActionPerformed
        mClearTextFields();
    }//GEN-LAST:event_btnClearActionPerformed

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        mLoadSubjectsIntoCB();
        mTableView();
    }//GEN-LAST:event_formWindowOpened

    private void btnInsertActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnInsertActionPerformed
    
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
        if(boolRecordExists==true)
        {
        boolRecordExists=false;
        JOptionPane.showMessageDialog(null, "Already exists");
        }
        else if (boolRecordExists==false)
        {
        mInsert();
        mTableView();
        mClearTextFields();
        }    
             
        }    
    }//GEN-LAST:event_btnInsertActionPerformed

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
        
            
         int selectedIndex = tblResults.getSelectedRow();
    if (selectedIndex == -1) { // No row is selected
        JOptionPane.showMessageDialog(null, "Please select a row to delete.");
    }else{
      
    //Add confirmation JOptionPane dialog before deleting a record
 int response = JOptionPane.showConfirmDialog(null, "Are You Sure You Want To DELETE this Marks",
        "Select An Option",JOptionPane.YES_NO_OPTION,JOptionPane.ERROR_MESSAGE);
        
       if (response == JOptionPane.YES_OPTION){
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
    
             
              int selectedRow = tblResults.getSelectedRow();
    int selectedColumn = tblResults.getSelectedColumn();

    if (selectedRow == -1 || selectedColumn == -1) return;

    // Get subject name from first column (column 0)
    String subjectName = tblResults.getValueAt(selectedRow, 0).toString();

    // Get term number from column index
    int term = selectedColumn; // term2 = column 2 ⇒ term = 2, etc.
    
    if (term < 1 || term > 4) return; // Prevent clicking on the subject column (index 0)

    // Set combo boxes
    cbSubjects.setSelectedItem(subjectName);
    cbTerm.setSelectedItem(String.valueOf(term));
    
    
     String URL4 = "jdbc:mysql://localhost:3306/gradebook_system";
    String User4 = "root";
    String Password4 = "528_hloni"; // 
    PreparedStatement pst;
    java.sql.Connection conMySQLConnectionString ;
 int intSubjectId = mSubjectIdByName(subjectName);
    
        

    // Now fetch from DB
    try {
       conMySQLConnectionString = DriverManager.getConnection(URL4, User4, Password4);
    String sql2 =  "SELECT test_total, test_weight, exam_total, exam_weight, average_mark FROM marks " +
        "WHERE subject_Id = ? AND term = ? AND student_Id = ?";
    pst = conMySQLConnectionString.prepareStatement(sql2);
        
  
        pst.setInt(1, intSubjectId);
        pst.setInt(2, term);
        pst.setInt(3, intStudentId); // Replace with actual variable for selected student
        
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
        int selectedIndex = tblResults.getSelectedRow();
        if (selectedIndex == -1) { // No row is selected
            JOptionPane.showMessageDialog(null, "Please select a row to edit.");
        }else{

            mGetValuesFromGui();
            mCheckIfItemsExistInTable();
            //if the record exist then show jOptionPane message then set boolean record to false
            //if the record doesnt exist then create passeneger,update the table then clear textfields
            if(boolRecordExists==true)
            {
                boolRecordExists=false;
                JOptionPane.showMessageDialog(null, "Already exists");
            }
            else if (boolRecordExists==false)
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
