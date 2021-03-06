/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


import java.io.File;
import java.io.IOException;
import javax.swing.JFileChooser;
import org.micromanager.internal.MMStudio;
import org.micromanager.internal.utils.ReportingUtils;

/**
 *
 * @author seamus.holden@epfl.ch
 * Released under the BSD license, see LICENSE.txt
 */
public class FastAcqDialog extends javax.swing.JDialog{

   private FastAcquisitionMMPlugin fastAcq_ = null;
   private MMStudio gui_ = null;
   private RunAcquisition runAcq_ = null;

   private int nFrame_ = 1000;
   private String rootDirName_ = "";
   private String imNamePrefix_ = "testIm";
   private double imSatPc_ = 0.2;
   
   /**
    * Creates new form FastAcqDialog
    */
   public FastAcqDialog(MMStudio gui_, boolean modal, FastAcquisitionMMPlugin fastAcq) {
      initComponents();

      this.fastAcq_ = fastAcq;
      runAcq_ = new RunAcquisition(gui_);

      reloadSettings();
   }

   /**
    * This method is called from within the constructor to initialize the form.
    * WARNING: Do NOT modify this code. The content of this method is always
    * regenerated by the Form Editor.
    */
   @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel4 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jTextField_nFrame = new javax.swing.JTextField();
        jTextField_rootDir = new javax.swing.JTextField();
        jTextField_imName = new javax.swing.JTextField();
        jButton_acquire = new javax.swing.JButton();
        jButton_stop = new javax.swing.JButton();
        jButton_chooseDir = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        jTextField_imSatPc = new javax.swing.JTextField();

        jLabel4.setText("jLabel4");

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Fast acquisition");

        jLabel1.setText("Number of frames");

        jLabel2.setText("Directory root");

        jLabel3.setText("Name prefix");

        jTextField_nFrame.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField_nFrameActionPerformed(evt);
            }
        });

        jTextField_rootDir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField_rootDirActionPerformed(evt);
            }
        });

        jTextField_imName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField_imNameActionPerformed(evt);
            }
        });

        jButton_acquire.setText("Acquire!");
        jButton_acquire.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_acquireActionPerformed(evt);
            }
        });

        jButton_stop.setText("Stop");
        jButton_stop.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_stopActionPerformed(evt);
            }
        });

        jButton_chooseDir.setText("...");
        jButton_chooseDir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_chooseDirActionPerformed(evt);
            }
        });

        jLabel5.setText("Display saturation (%)");

        jTextField_imSatPc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField_imSatPcActionPerformed(evt);
            }
        });

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .addContainerGap()
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(layout.createSequentialGroup()
                        .add(jButton_acquire)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(jButton_stop, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 73, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                    .add(layout.createSequentialGroup()
                        .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(jLabel1)
                            .add(jLabel2)
                            .add(jLabel3))
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(layout.createSequentialGroup()
                                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING, false)
                                    .add(jTextField_rootDir)
                                    .add(jTextField_imName, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 173, Short.MAX_VALUE))
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                .add(jButton_chooseDir))
                            .add(layout.createSequentialGroup()
                                .add(jTextField_nFrame, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 67, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                                .add(jLabel5)
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                .add(jTextField_imSatPc, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 55, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(org.jdesktop.layout.GroupLayout.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jLabel1)
                    .add(jTextField_nFrame, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(jLabel5)
                    .add(jTextField_imSatPc, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jLabel2)
                    .add(jTextField_rootDir, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(jButton_chooseDir))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jLabel3)
                    .add(jTextField_imName, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jButton_acquire)
                    .add(jButton_stop))
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

   private void jTextField_nFrameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField_nFrameActionPerformed
      String nFrameStr =jTextField_nFrame.getText();
      int nFrameTmp;
      // check if it's a valid int 
      try {
         nFrameTmp = Integer.parseInt(nFrameStr);
         nFrame_ = nFrameTmp;
      }
      catch( NumberFormatException n){
         // dont do anything, nFrame_ retains its old value
      }
      
      jTextField_nFrame.setText(Integer.toString(nFrame_));
   }//GEN-LAST:event_jTextField_nFrameActionPerformed

   private void jTextField_rootDirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField_rootDirActionPerformed
      rootDirName_ = jTextField_rootDir.getText();
   }//GEN-LAST:event_jTextField_rootDirActionPerformed

   private void jButton_chooseDirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_chooseDirActionPerformed
      JFileChooser chooser = new JFileChooser();
      chooser.setDialogTitle("Select target directory");
      chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
      int returnVal = chooser.showOpenDialog(this);
      if(returnVal == JFileChooser.APPROVE_OPTION) {
         try {
            File f = chooser.getSelectedFile();
            rootDirName_= f.getCanonicalPath();
            jTextField_rootDir.setText(rootDirName_);
            
         } catch (IOException ex) {
            ReportingUtils.logError(ex, "Unable to get path");
         }
      }
   }//GEN-LAST:event_jButton_chooseDirActionPerformed

   private void jButton_acquireActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_acquireActionPerformed
      runAcq_.startAcquisition(rootDirName_,imNamePrefix_,nFrame_,true,imSatPc_);
   }//GEN-LAST:event_jButton_acquireActionPerformed

   private void jButton_stopActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_stopActionPerformed
      runAcq_.stopAcquisition();
      // TODO add your handling code here:
   }//GEN-LAST:event_jButton_stopActionPerformed

   private void jTextField_imSatPcActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField_imSatPcActionPerformed
      String imSatPcStr =jTextField_imSatPc.getText();
      double imSatPcTmp;
      // check if it's a valid int 
      try {
         imSatPcTmp = Double.parseDouble(imSatPcStr);
         imSatPc_ = imSatPcTmp;
      }
      catch( NumberFormatException n){
         // dont do anything, imSatPc_ retains its old value
      }
      
      jTextField_imSatPc.setText(Double.toString(imSatPc_));
      // TODO add your handling code here:
   }//GEN-LAST:event_jTextField_imSatPcActionPerformed

   private void jTextField_imNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField_imNameActionPerformed
      imNamePrefix_ = jTextField_imName.getText();
   }//GEN-LAST:event_jTextField_imNameActionPerformed
   
   private void reloadSettings() {
      jTextField_nFrame.setText(Integer.toString(nFrame_));
      jTextField_imSatPc.setText(Double.toString(imSatPc_));
      jTextField_rootDir.setText(rootDirName_);
      jTextField_imName.setText(imNamePrefix_);
   }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton_acquire;
    private javax.swing.JButton jButton_chooseDir;
    private javax.swing.JButton jButton_stop;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JTextField jTextField_imName;
    private javax.swing.JTextField jTextField_imSatPc;
    private javax.swing.JTextField jTextField_nFrame;
    private javax.swing.JTextField jTextField_rootDir;
    // End of variables declaration//GEN-END:variables


}
