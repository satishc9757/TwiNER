package twiner;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author akshay
 */
 class Preprocessing_screen extends javax.swing.JFrame {

    /**
     * Creates new form Preprocessing
     */
    public Preprocessing_screen() {
        initComponents();
        setSize(810,540);
        try {
            File input2 = new File("/Users/satishc/Desktop/ProcessedTweets.txt");
            File input1 = new File("/Users/satishc/Desktop/nerin.txt");
            
            Scanner sc1 =new Scanner(input1);
            sc1.useDelimiter("\\|");
             
            DefaultListModel dlm = new DefaultListModel();
            
            while(sc1.hasNext()){
                dlm.addElement(sc1.next());
                dlm.addElement("\n");
            }
            
            
            Scanner sc2 = new Scanner(input2);
            sc2.useDelimiter("\\|");
             
            DefaultListModel dlm1 = new DefaultListModel();
            
            while(sc2.hasNext()){
                dlm1.addElement(sc2.next());
                dlm1.addElement("\n");
            }
            
            orginal.setModel(dlm);
            preprocessed.setModel(dlm1);
            
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Preprocessing_screen.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jFrame1 = new javax.swing.JFrame();
        jFrame2 = new javax.swing.JFrame();
        jFrame3 = new javax.swing.JFrame();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        segment = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        orginal = new javax.swing.JList();
        jScrollPane2 = new javax.swing.JScrollPane();
        preprocessed = new javax.swing.JList();
        displayTweets = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();

        javax.swing.GroupLayout jFrame1Layout = new javax.swing.GroupLayout(jFrame1.getContentPane());
        jFrame1.getContentPane().setLayout(jFrame1Layout);
        jFrame1Layout.setHorizontalGroup(
            jFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        jFrame1Layout.setVerticalGroup(
            jFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jFrame2Layout = new javax.swing.GroupLayout(jFrame2.getContentPane());
        jFrame2.getContentPane().setLayout(jFrame2Layout);
        jFrame2Layout.setHorizontalGroup(
            jFrame2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        jFrame2Layout.setVerticalGroup(
            jFrame2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jFrame3Layout = new javax.swing.GroupLayout(jFrame3.getContentPane());
        jFrame3.getContentPane().setLayout(jFrame3Layout);
        jFrame3Layout.setHorizontalGroup(
            jFrame3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        jFrame3Layout.setVerticalGroup(
            jFrame3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(null);

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 21)); // NOI18N
        jLabel1.setText("                  Preprocessing");
        getContentPane().add(jLabel1);
        jLabel1.setBounds(241, 26, 330, 30);

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        jLabel2.setText("Obtained Tweet");
        getContentPane().add(jLabel2);
        jLabel2.setBounds(35, 96, 181, 31);

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        jLabel3.setText("Preprocessed Tweet");
        getContentPane().add(jLabel3);
        jLabel3.setBounds(484, 96, 181, 31);

        segment.setFont(new java.awt.Font("Lucida Grande", 1, 14)); // NOI18N
        segment.setText("Segmentation");
        segment.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                segmentActionPerformed(evt);
            }
        });
        getContentPane().add(segment);
        segment.setBounds(560, 470, 140, 40);

        jScrollPane1.setViewportView(orginal);

        getContentPane().add(jScrollPane1);
        jScrollPane1.setBounds(35, 133, 311, 330);

        jScrollPane2.setViewportView(preprocessed);

        getContentPane().add(jScrollPane2);
        jScrollPane2.setBounds(484, 133, 310, 330);

        displayTweets.setFont(new java.awt.Font("Lucida Grande", 1, 14)); // NOI18N
        displayTweets.setText("Refresh");
        displayTweets.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                displayTweetsActionPerformed(evt);
            }
        });
        getContentPane().add(displayTweets);
        displayTweets.setBounds(140, 470, 90, 40);

        jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/twiner/Background.jpg"))); // NOI18N
        jLabel5.setText("jLabel5");
        getContentPane().add(jLabel5);
        jLabel5.setBounds(0, 0, 810, 520);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void displayTweetsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_displayTweetsActionPerformed
        try {
            File input2 = new File("/Users/satishc/Desktop/ProcessedTweets.txt");
            File input1 = new File("/Users/satishc/Desktop/nerin.txt");
            
            Scanner sc1 =new Scanner(input1);
            sc1.useDelimiter("\\|");
             
            DefaultListModel dlm = new DefaultListModel();
            
            while(sc1.hasNext()){
                dlm.addElement(sc1.next());
                dlm.addElement("\n");
            }
            
            
            Scanner sc2 = new Scanner(input2);
            sc2.useDelimiter("\\|");
             
            DefaultListModel dlm1 = new DefaultListModel();
            
            while(sc2.hasNext()){
                dlm1.addElement(sc2.next());
                dlm1.addElement("\n");
            }
            
            orginal.setModel(dlm);
            preprocessed.setModel(dlm1);
            
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Preprocessing_screen.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
    }//GEN-LAST:event_displayTweetsActionPerformed

    private void segmentActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_segmentActionPerformed
        Segmentation seg = new Segmentation();
        seg.segmentationOp();
        
        Segmentation_screen segs = new Segmentation_screen();
        setVisible(false);
        segs.setVisible(true);
    }//GEN-LAST:event_segmentActionPerformed

    /**
     * @param args the command line arguments
     */
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton displayTweets;
    private javax.swing.JFrame jFrame1;
    private javax.swing.JFrame jFrame2;
    private javax.swing.JFrame jFrame3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JList orginal;
    private javax.swing.JList preprocessed;
    private javax.swing.JButton segment;
    // End of variables declaration//GEN-END:variables
}
