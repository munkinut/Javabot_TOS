/*
 * AboutDialog.java - provides an About box for JavaBot
 *
 * Copyright (C) 2001 by Warren Milburn
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 *
 */

package org.javabot.gui;

import java.awt.*;
import java.util.logging.Logger;

public class AboutDialog extends javax.swing.JDialog {

    Logger log = Logger.getLogger(this.getClass().getName());

    /** Creates new form AboutDialog */
    public AboutDialog(java.awt.Frame parent,boolean modal) {
        super (parent, modal);
        initComponents ();
        pack ();
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the FormEditor.
     */
    private void initComponents() {//GEN-BEGIN:initComponents
        // Variables declaration - do not modify//GEN-BEGIN:variables
        javax.swing.JButton jButton1 = new javax.swing.JButton();
        javax.swing.JTextArea jTextArea1 = new javax.swing.JTextArea();
        setTitle("About JavaBot");
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                closeDialog();
            }
        }
        );
        
        jButton1.setPreferredSize(new java.awt.Dimension(21, 23));
        jButton1.setBorder(new javax.swing.border.SoftBevelBorder(0));
        jButton1.setText("OK");
        jButton1.addActionListener(this::jButton1ActionPerformed);
        
        getContentPane().add(jButton1, java.awt.BorderLayout.SOUTH);
        
        
        jTextArea1.setEditable(false);
        jTextArea1.setFont(new java.awt.Font ("Andale Mono", Font.PLAIN, 11));
        jTextArea1.setText("JavaBot\n\nFounded by Torsten Born (c) 2001\n\nMaintainer : Warren Milburn\n\nDevelopers : Torsten Born\n             Warren Milburn\n             Michael Oemler\n\nhttp://www.javabot.org\n");
        jTextArea1.setBackground(java.awt.Color.lightGray);
        
        getContentPane().add(jTextArea1, java.awt.BorderLayout.CENTER);
        
    }//GEN-END:initComponents

  private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        setVisible (false);
        dispose ();
  }//GEN-LAST:event_jButton1ActionPerformed

    /** Closes the dialog */
    private void closeDialog() {//GEN-FIRST:event_closeDialog
        setVisible (false);
        dispose ();
    }//GEN-LAST:event_closeDialog

    /**
    * @param args the command line arguments
    */
    @SuppressWarnings("deprecation")
    public static void main (String[] args) {
        new AboutDialog (new javax.swing.JFrame (), true).show();
    }


    // End of variables declaration//GEN-END:variables

}
