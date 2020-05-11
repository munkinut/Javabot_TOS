/*
 * ConfigUI.java - provides a Swing GUI configurator for JavaBot
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

import java.util.logging.Logger;

public class ConfigUI extends javax.swing.JFrame {

    final Logger log = Logger.getLogger(this.getClass().getName());

    private final boolean debug = false;
    private final org.javabot.gui.Configurator cf;

    /** Creates new form Configurator */
    public ConfigUI() {
        log.info("ConfigUI() called");
        cf = new org.javabot.gui.Configurator();
        initComponents();
        this.loadComponents();
    }
    
    private void loadComponents() {
        log.info("loadComponents() called");
        this.serverTextField.setText(cf.getServer());
        this.portTextField.setText("" + cf.getPort());
        this.nameTextField.setText(cf.getName());
        this.nicknameTextField.setText(cf.getNickname());
        this.channelTextField.setText(cf.getChannel());
        this.channelModesTextField.setText(cf.getChannelModes());
        this.autovoiceCheckBox.setSelected(cf.getAutovoice());
        this.autogreetCheckBox.setSelected(cf.getAutogreet());
        this.floodProtectionCheckBox.setSelected(cf.getFloodProtection());
        this.opmeCheckBox.setSelected(cf.getOpme());
        this.cycleForOpsCheckBox.setSelected(cf.getCycleForOps());
        this.dynamicLimitCheckBox.setSelected(cf.getDynamicLimit());
        this.joinHitsTextField.setText("" + cf.getJoinHits());
        this.joinTimeTextField.setText("" + cf.getJoinLimit());
        this.ctcpHitsTextField.setText("" + cf.getCtcpHits());
        this.ctcpTimeTextField.setText("" + cf.getCtcpLimit());
        this.privmsgHitsTextField.setText("" + cf.getPrivmsgHits());
        this.privmsgTimeTextField.setText("" + cf.getPrivmsgLimit());
        this.chanmsgHitsTextField.setText("" + cf.getChanmsgHits());
        this.chanmsgTimeTextField.setText("" + cf.getChanmsgLimit());
        this.colourHitsTextField.setText("" + cf.getColourHits());
        this.colourTimeTextField.setText("" + cf.getColourLimit());
        this.dccHitsTextField.setText("" + cf.getDccHits());
        this.dccTimeTextField.setText("" + cf.getDccLimit());
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    private void initComponents() {//GEN-BEGIN:initComponents
        log.info("initComponents() called");
        // Variables declaration - do not modify//GEN-BEGIN:variables
        javax.swing.JTabbedPane jTabbedPane1 = new javax.swing.JTabbedPane();
        javax.swing.JPanel optionsPanel = new javax.swing.JPanel();
        javax.swing.JLabel serverLabel = new javax.swing.JLabel();
            serverTextField = new javax.swing.JTextField();
        javax.swing.JLabel portLabel = new javax.swing.JLabel();
            portTextField = new javax.swing.JTextField();
        javax.swing.JLabel nameLabel = new javax.swing.JLabel();
            nameTextField = new javax.swing.JTextField();
        javax.swing.JLabel nicknameLabel = new javax.swing.JLabel();
            nicknameTextField = new javax.swing.JTextField();
        javax.swing.JLabel channelLabel = new javax.swing.JLabel();
            channelTextField = new javax.swing.JTextField();
        javax.swing.JLabel channelModesLabel = new javax.swing.JLabel();
            channelModesTextField = new javax.swing.JTextField();
        javax.swing.JPanel modesPanel = new javax.swing.JPanel();
            autovoiceCheckBox = new javax.swing.JCheckBox();
            autogreetCheckBox = new javax.swing.JCheckBox();
            floodProtectionCheckBox = new javax.swing.JCheckBox();
            opmeCheckBox = new javax.swing.JCheckBox();
            cycleForOpsCheckBox = new javax.swing.JCheckBox();
            dynamicLimitCheckBox = new javax.swing.JCheckBox();
        javax.swing.JPanel protectionPanel = new javax.swing.JPanel();
        javax.swing.JLabel floodTypeLabel = new javax.swing.JLabel();
        javax.swing.JLabel hitsLabel = new javax.swing.JLabel();
        javax.swing.JLabel timeLabel = new javax.swing.JLabel();
        javax.swing.JLabel joinLabel = new javax.swing.JLabel();
            joinHitsTextField = new javax.swing.JTextField();
            joinTimeTextField = new javax.swing.JTextField();
        javax.swing.JLabel ctcpLabel = new javax.swing.JLabel();
            ctcpHitsTextField = new javax.swing.JTextField();
            ctcpTimeTextField = new javax.swing.JTextField();
        javax.swing.JLabel privmsgLabel = new javax.swing.JLabel();
            privmsgHitsTextField = new javax.swing.JTextField();
            privmsgTimeTextField = new javax.swing.JTextField();
        javax.swing.JLabel chanmsgLabel = new javax.swing.JLabel();
            chanmsgHitsTextField = new javax.swing.JTextField();
            chanmsgTimeTextField = new javax.swing.JTextField();
        javax.swing.JLabel colourLabel = new javax.swing.JLabel();
            colourHitsTextField = new javax.swing.JTextField();
            colourTimeTextField = new javax.swing.JTextField();
        javax.swing.JLabel dccLabel = new javax.swing.JLabel();
            dccHitsTextField = new javax.swing.JTextField();
            dccTimeTextField = new javax.swing.JTextField();
        javax.swing.JPanel buttonPanel = new javax.swing.JPanel();
        javax.swing.JButton OKButton = new javax.swing.JButton();
        javax.swing.JButton cancelButton = new javax.swing.JButton();
        javax.swing.JButton resetButton = new javax.swing.JButton();
            
            setTitle("JavaBot Configurator");
            addWindowListener(new java.awt.event.WindowAdapter() {
                public void windowClosing(java.awt.event.WindowEvent evt) {
                    exitForm(evt);
                }
            });
            
            optionsPanel.setLayout(new java.awt.GridLayout(6, 2));
            
            serverLabel.setText("Server");
            optionsPanel.add(serverLabel);
            
            serverTextField.addActionListener(this::serverTextFieldActionPerformed);
            
            serverTextField.addFocusListener(new java.awt.event.FocusAdapter() {
                public void focusLost(java.awt.event.FocusEvent evt) {
                    serverTextFieldFocusLost(evt);
                }
            });
            
            optionsPanel.add(serverTextField);
            
            portLabel.setText("Port");
            optionsPanel.add(portLabel);
            
            portTextField.addActionListener(this::portTextFieldActionPerformed);
            
            portTextField.addFocusListener(new java.awt.event.FocusAdapter() {
                public void focusLost(java.awt.event.FocusEvent evt) {
                    portTextFieldFocusLost(evt);
                }
            });
            
            optionsPanel.add(portTextField);
            
            nameLabel.setText("Name");
            optionsPanel.add(nameLabel);
            
            nameTextField.addFocusListener(new java.awt.event.FocusAdapter() {
                public void focusLost(java.awt.event.FocusEvent evt) {
                    nameTextFieldFocusLost(evt);
                }
            });
            
            optionsPanel.add(nameTextField);
            
            nicknameLabel.setText("Nickname");
            optionsPanel.add(nicknameLabel);
            
            nicknameTextField.addFocusListener(new java.awt.event.FocusAdapter() {
                public void focusLost(java.awt.event.FocusEvent evt) {
                    nicknameTextFieldFocusLost(evt);
                }
            });
            
            optionsPanel.add(nicknameTextField);
            
            channelLabel.setText("Channel");
            optionsPanel.add(channelLabel);
            
            channelTextField.addActionListener(this::channelTextFieldActionPerformed);
            
            channelTextField.addFocusListener(new java.awt.event.FocusAdapter() {
                public void focusLost(java.awt.event.FocusEvent evt) {
                    channelTextFieldFocusLost(evt);
                }
            });
            
            optionsPanel.add(channelTextField);
            
            channelModesLabel.setText("Channel Modes");
            optionsPanel.add(channelModesLabel);
            
            channelModesTextField.addFocusListener(new java.awt.event.FocusAdapter() {
                public void focusLost(java.awt.event.FocusEvent evt) {
                    channelModesTextFieldFocusLost(evt);
                }
            });
            
            optionsPanel.add(channelModesTextField);
            
            jTabbedPane1.addTab("Options", optionsPanel);
          
          modesPanel.setLayout(new java.awt.GridLayout(6, 1));
            
            autovoiceCheckBox.setText("Autovoice");
            autovoiceCheckBox.addActionListener(this::autovoiceCheckBoxActionPerformed);
            
            modesPanel.add(autovoiceCheckBox);
            
            autogreetCheckBox.setText("Autogreet");
            autogreetCheckBox.addActionListener(this::autogreetCheckBoxActionPerformed);
            
            modesPanel.add(autogreetCheckBox);
            
            floodProtectionCheckBox.setText("Flood protection");
            floodProtectionCheckBox.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    floodProtectionCheckBoxActionPerformed(evt);
                }
            });
            
            modesPanel.add(floodProtectionCheckBox);
            
            opmeCheckBox.setText("Op me");
            opmeCheckBox.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    opmeCheckBoxActionPerformed(evt);
                }
            });
            
            modesPanel.add(opmeCheckBox);
            
            cycleForOpsCheckBox.setText("Cycle for ops");
            cycleForOpsCheckBox.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    cycleForOpsCheckBoxActionPerformed(evt);
                }
            });
            
            modesPanel.add(cycleForOpsCheckBox);
            
            dynamicLimitCheckBox.setText("Dynamic limit");
            dynamicLimitCheckBox.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    dynamicLimitCheckBoxActionPerformed(evt);
                }
            });
            
            modesPanel.add(dynamicLimitCheckBox);
            
            jTabbedPane1.addTab("Modes", modesPanel);
          
          protectionPanel.setLayout(new java.awt.GridLayout(7, 3));
            
            floodTypeLabel.setText("Flood Type");
            protectionPanel.add(floodTypeLabel);
            
            hitsLabel.setText("Hits");
            hitsLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
            protectionPanel.add(hitsLabel);
            
            timeLabel.setText("Time");
            timeLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
            protectionPanel.add(timeLabel);
            
            joinLabel.setText("Join");
            protectionPanel.add(joinLabel);
            
            joinHitsTextField.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    joinHitsTextFieldActionPerformed(evt);
                }
            });
            
            joinHitsTextField.addFocusListener(new java.awt.event.FocusAdapter() {
                public void focusLost(java.awt.event.FocusEvent evt) {
                    joinHitsTextFieldFocusLost(evt);
                }
            });
            
            protectionPanel.add(joinHitsTextField);
            
            joinTimeTextField.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    joinTimeTextFieldActionPerformed(evt);
                }
            });
            
            joinTimeTextField.addFocusListener(new java.awt.event.FocusAdapter() {
                public void focusLost(java.awt.event.FocusEvent evt) {
                    joinTimeTextFieldFocusLost(evt);
                }
            });
            
            protectionPanel.add(joinTimeTextField);
            
            ctcpLabel.setText("CTCP");
            protectionPanel.add(ctcpLabel);
            
            ctcpHitsTextField.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    ctcpHitsTextFieldActionPerformed(evt);
                }
            });
            
            ctcpHitsTextField.addFocusListener(new java.awt.event.FocusAdapter() {
                public void focusLost(java.awt.event.FocusEvent evt) {
                    ctcpHitsTextFieldFocusLost(evt);
                }
            });
            
            protectionPanel.add(ctcpHitsTextField);
            
            ctcpTimeTextField.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    ctcpTimeTextFieldActionPerformed(evt);
                }
            });
            
            ctcpTimeTextField.addFocusListener(new java.awt.event.FocusAdapter() {
                public void focusLost(java.awt.event.FocusEvent evt) {
                    ctcpTimeTextFieldFocusLost(evt);
                }
            });
            
            protectionPanel.add(ctcpTimeTextField);
            
            privmsgLabel.setText("Priv Msg");
            protectionPanel.add(privmsgLabel);
            
            privmsgHitsTextField.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    privmsgHitsTextFieldActionPerformed(evt);
                }
            });
            
            privmsgHitsTextField.addFocusListener(new java.awt.event.FocusAdapter() {
                public void focusLost(java.awt.event.FocusEvent evt) {
                    privmsgHitsTextFieldFocusLost(evt);
                }
            });
            
            protectionPanel.add(privmsgHitsTextField);
            
            privmsgTimeTextField.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    privmsgTimeTextFieldActionPerformed(evt);
                }
            });
            
            privmsgTimeTextField.addFocusListener(new java.awt.event.FocusAdapter() {
                public void focusLost(java.awt.event.FocusEvent evt) {
                    privmsgTimeTextFieldFocusLost(evt);
                }
            });
            
            protectionPanel.add(privmsgTimeTextField);
            
            chanmsgLabel.setText("Chan Msg");
            protectionPanel.add(chanmsgLabel);
            
            chanmsgHitsTextField.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    chanmsgHitsTextFieldActionPerformed(evt);
                }
            });
            
            chanmsgHitsTextField.addFocusListener(new java.awt.event.FocusAdapter() {
                public void focusLost(java.awt.event.FocusEvent evt) {
                    chanmsgHitsTextFieldFocusLost(evt);
                }
            });
            
            protectionPanel.add(chanmsgHitsTextField);
            
            chanmsgTimeTextField.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    chanmsgTimeTextFieldActionPerformed(evt);
                }
            });
            
            chanmsgTimeTextField.addFocusListener(new java.awt.event.FocusAdapter() {
                public void focusLost(java.awt.event.FocusEvent evt) {
                    chanmsgTimeTextFieldFocusLost(evt);
                }
            });
            
            protectionPanel.add(chanmsgTimeTextField);
            
            colourLabel.setText("Colour");
            protectionPanel.add(colourLabel);
            
            colourHitsTextField.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    colourHitsTextFieldActionPerformed(evt);
                }
            });
            
            colourHitsTextField.addFocusListener(new java.awt.event.FocusAdapter() {
                public void focusLost(java.awt.event.FocusEvent evt) {
                    colourHitsTextFieldFocusLost(evt);
                }
            });
            
            protectionPanel.add(colourHitsTextField);
            
            colourTimeTextField.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    colourTimeTextFieldActionPerformed(evt);
                }
            });
            
            colourTimeTextField.addFocusListener(new java.awt.event.FocusAdapter() {
                public void focusLost(java.awt.event.FocusEvent evt) {
                    colourTimeTextFieldFocusLost(evt);
                }
            });
            
            protectionPanel.add(colourTimeTextField);
            
            dccLabel.setText("DCC");
            protectionPanel.add(dccLabel);
            
            dccHitsTextField.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    dccHitsTextFieldActionPerformed(evt);
                }
            });
            
            dccHitsTextField.addFocusListener(new java.awt.event.FocusAdapter() {
                public void focusLost(java.awt.event.FocusEvent evt) {
                    dccHitsTextFieldFocusLost(evt);
                }
            });
            
            protectionPanel.add(dccHitsTextField);
            
            dccTimeTextField.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    dccTimeTextFieldActionPerformed(evt);
                }
            });
            
            dccTimeTextField.addFocusListener(new java.awt.event.FocusAdapter() {
                public void focusLost(java.awt.event.FocusEvent evt) {
                    dccTimeTextFieldFocusLost(evt);
                }
            });
            
            protectionPanel.add(dccTimeTextField);
            
            jTabbedPane1.addTab("Protection", protectionPanel);
          
          getContentPane().add(jTabbedPane1, java.awt.BorderLayout.NORTH);
          
          OKButton.setText("OK");
          OKButton.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
          OKButton.addActionListener(new java.awt.event.ActionListener() {
              public void actionPerformed(java.awt.event.ActionEvent evt) {
                  OKButtonActionPerformed(evt);
              }
          });
          
          buttonPanel.add(OKButton);
          
          cancelButton.setText("Cancel");
          cancelButton.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
          cancelButton.addActionListener(new java.awt.event.ActionListener() {
              public void actionPerformed(java.awt.event.ActionEvent evt) {
                  CancelButtonActionPerformed(evt);
              }
          });
          
          buttonPanel.add(cancelButton);
          
          resetButton.setText("Reset");
          resetButton.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
          resetButton.addActionListener(new java.awt.event.ActionListener() {
              public void actionPerformed(java.awt.event.ActionEvent evt) {
                  ResetButtonActionPerformed(evt);
              }
          });
          
          buttonPanel.add(resetButton);
          
          getContentPane().add(buttonPanel, java.awt.BorderLayout.CENTER);
        
        pack();
    }//GEN-END:initComponents

    private void ResetButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ResetButtonActionPerformed
        log.info("ResetButtonActionPerformed() called");
        cf.load();
        this.loadComponents();
    }//GEN-LAST:event_ResetButtonActionPerformed

    private void CancelButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CancelButtonActionPerformed
        log.info("CancelButtonActionPerformed() called");
        cf.load();
        dispose();
    }//GEN-LAST:event_CancelButtonActionPerformed

    private void OKButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_OKButtonActionPerformed
        log.info("OKButtonActionPerformed() called");
        cf.store();
        dispose();
    }//GEN-LAST:event_OKButtonActionPerformed

    private void dynamicLimitCheckBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_dynamicLimitCheckBoxActionPerformed
        log.info("dynamicLimitTextFieldActionPerformed() called");
        cf.setDynamicLimit(dynamicLimitCheckBox.isSelected());
    }//GEN-LAST:event_dynamicLimitCheckBoxActionPerformed

    private void cycleForOpsCheckBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cycleForOpsCheckBoxActionPerformed
        log.info("cycleForOpsTextFieldActionPerformed() called");
        cf.setCycleForOps(cycleForOpsCheckBox.isSelected());
    }//GEN-LAST:event_cycleForOpsCheckBoxActionPerformed

    private void opmeCheckBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_opmeCheckBoxActionPerformed
        log.info("opmeTextFieldActionPerformed() called");
        cf.setOpme(opmeCheckBox.isSelected());
    }//GEN-LAST:event_opmeCheckBoxActionPerformed

    private void autogreetCheckBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_autogreetCheckBoxActionPerformed
        log.info("autogreetTextFieldActionPerformed() called");
        cf.setAutogreet(autogreetCheckBox.isSelected());
    }//GEN-LAST:event_autogreetCheckBoxActionPerformed

    private void autovoiceCheckBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_autovoiceCheckBoxActionPerformed
        log.info("autovoiceTextFieldActionPerformed() called");
        cf.setAutovoice(autovoiceCheckBox.isSelected());
    }//GEN-LAST:event_autovoiceCheckBoxActionPerformed

    private void dccTimeTextFieldFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_dccTimeTextFieldFocusLost
        log.info("dccTimeTextFieldFocusLost() called");
        cf.setDccLimit(Integer.parseInt(dccTimeTextField.getText()));
    }//GEN-LAST:event_dccTimeTextFieldFocusLost

    private void dccTimeTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_dccTimeTextFieldActionPerformed
        log.info("dccTimeTextFieldActionPerformed() called");
        cf.setDccLimit(Integer.parseInt(dccTimeTextField.getText()));
    }//GEN-LAST:event_dccTimeTextFieldActionPerformed

    private void colourTimeTextFieldFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_colourTimeTextFieldFocusLost
        log.info("colourTimeTextFieldFocusLost() called");
        cf.setColourLimit(Integer.parseInt(colourTimeTextField.getText()));
    }//GEN-LAST:event_colourTimeTextFieldFocusLost

    private void colourTimeTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_colourTimeTextFieldActionPerformed
        log.info("colourTimeTextFieldActionPerformed() called");
        cf.setColourLimit(Integer.parseInt(colourTimeTextField.getText()));
    }//GEN-LAST:event_colourTimeTextFieldActionPerformed

    private void chanmsgTimeTextFieldFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_chanmsgTimeTextFieldFocusLost
        log.info("chanmsgTimeTextFieldFocusLost() called");
        cf.setChanmsgLimit(Integer.parseInt(chanmsgTimeTextField.getText()));
    }//GEN-LAST:event_chanmsgTimeTextFieldFocusLost

    private void chanmsgTimeTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chanmsgTimeTextFieldActionPerformed
        log.info("chanmsgTimeTextFieldActionPerformed() called");
        cf.setChanmsgLimit(Integer.parseInt(chanmsgTimeTextField.getText()));
    }//GEN-LAST:event_chanmsgTimeTextFieldActionPerformed

    private void privmsgTimeTextFieldFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_privmsgTimeTextFieldFocusLost
        log.info("privmsgTimeTextFieldFocusLost() called");
        cf.setPrivmsgLimit(Integer.parseInt(privmsgTimeTextField.getText()));
    }//GEN-LAST:event_privmsgTimeTextFieldFocusLost

    private void privmsgTimeTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_privmsgTimeTextFieldActionPerformed
        log.info("privmsgTimeTextFieldActionPerformed() called");
        cf.setPrivmsgLimit(Integer.parseInt(privmsgTimeTextField.getText()));
    }//GEN-LAST:event_privmsgTimeTextFieldActionPerformed

    private void ctcpTimeTextFieldFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_ctcpTimeTextFieldFocusLost
        log.info("ctcpTimeTextFieldFocusLost() called");
        cf.setCtcpLimit(Integer.parseInt(ctcpTimeTextField.getText()));
    }//GEN-LAST:event_ctcpTimeTextFieldFocusLost

    private void ctcpTimeTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ctcpTimeTextFieldActionPerformed
        log.info("ctcpTimeTextFieldActionPerformed() called");
        cf.setCtcpLimit(Integer.parseInt(ctcpTimeTextField.getText()));
    }//GEN-LAST:event_ctcpTimeTextFieldActionPerformed

    private void joinTimeTextFieldFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_joinTimeTextFieldFocusLost
        log.info("joinTimeTextFieldFocusLost() called");
        cf.setJoinLimit(Integer.parseInt(joinTimeTextField.getText()));
    }//GEN-LAST:event_joinTimeTextFieldFocusLost

    private void joinTimeTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_joinTimeTextFieldActionPerformed
        log.info("joinTimeTextFieldActionPerformed() called");
        cf.setJoinLimit(Integer.parseInt(joinTimeTextField.getText()));
    }//GEN-LAST:event_joinTimeTextFieldActionPerformed

    private void dccHitsTextFieldFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_dccHitsTextFieldFocusLost
        log.info("dccHitsTextFieldFocusLost() called");
        cf.setDccHits(Integer.parseInt(dccHitsTextField.getText()));
    }//GEN-LAST:event_dccHitsTextFieldFocusLost

    private void colourHitsTextFieldFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_colourHitsTextFieldFocusLost
        log.info("colourHitsTextFieldFocusLost() called");
        cf.setColourHits(Integer.parseInt(colourHitsTextField.getText()));
    }//GEN-LAST:event_colourHitsTextFieldFocusLost

    private void colourHitsTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_colourHitsTextFieldActionPerformed
        log.info("colourHitsTextFieldActionPerformed() called");
        cf.setColourHits(Integer.parseInt(colourHitsTextField.getText()));
    }//GEN-LAST:event_colourHitsTextFieldActionPerformed

    private void chanmsgHitsTextFieldFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_chanmsgHitsTextFieldFocusLost
        log.info("chanmsgHitsTextFieldFocusLost() called");
        cf.setChanmsgHits(Integer.parseInt(chanmsgHitsTextField.getText()));
    }//GEN-LAST:event_chanmsgHitsTextFieldFocusLost

    private void chanmsgHitsTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chanmsgHitsTextFieldActionPerformed
        log.info("chanmsgHitsTextFieldActionPerformed() called");
        cf.setChanmsgHits(Integer.parseInt(chanmsgHitsTextField.getText()));
    }//GEN-LAST:event_chanmsgHitsTextFieldActionPerformed

    private void privmsgHitsTextFieldFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_privmsgHitsTextFieldFocusLost
        log.info("privmsgHitsTextFieldFocusLost() called");
        cf.setPrivmsgHits(Integer.parseInt(privmsgHitsTextField.getText()));
    }//GEN-LAST:event_privmsgHitsTextFieldFocusLost

    private void privmsgHitsTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_privmsgHitsTextFieldActionPerformed
        log.info("privmsgHitsTextFieldActionPerformed() called");
        cf.setPrivmsgHits(Integer.parseInt(privmsgHitsTextField.getText()));
    }//GEN-LAST:event_privmsgHitsTextFieldActionPerformed

    private void ctcpHitsTextFieldFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_ctcpHitsTextFieldFocusLost
        log.info("ctcpHitsTextFieldFocusLost() called");
        cf.setCtcpHits(Integer.parseInt(ctcpHitsTextField.getText()));
    }//GEN-LAST:event_ctcpHitsTextFieldFocusLost

    private void ctcpHitsTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ctcpHitsTextFieldActionPerformed
        log.info("ctcpHitsTextFieldActionPerformed() called");
        cf.setCtcpHits(Integer.parseInt(ctcpHitsTextField.getText()));
    }//GEN-LAST:event_ctcpHitsTextFieldActionPerformed

    private void joinHitsTextFieldFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_joinHitsTextFieldFocusLost
        log.info("joinHitsTextFieldFocusLost() called");
        cf.setJoinHits(Integer.parseInt(joinHitsTextField.getText()));
    }//GEN-LAST:event_joinHitsTextFieldFocusLost

    private void joinHitsTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_joinHitsTextFieldActionPerformed
        log.info("joinHitsTextFieldActionPerformed() called");
        cf.setJoinHits(Integer.parseInt(joinHitsTextField.getText()));
    }//GEN-LAST:event_joinHitsTextFieldActionPerformed

    private void channelModesTextFieldFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_channelModesTextFieldFocusLost
        log.info("channelModesTextFieldFocusLost() called");
        cf.setChannelModes(channelModesTextField.getText());
    }//GEN-LAST:event_channelModesTextFieldFocusLost

    private void channelTextFieldFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_channelTextFieldFocusLost
        log.info("channelTextFieldFocusLost() called");
        cf.setChannel(channelTextField.getText());
    }//GEN-LAST:event_channelTextFieldFocusLost

    private void nicknameTextFieldFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_nicknameTextFieldFocusLost
        log.info("nicknameTextFieldFocusLost() called");
        cf.setNickname(nicknameTextField.getText());
    }//GEN-LAST:event_nicknameTextFieldFocusLost

    private void nameTextFieldFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_nameTextFieldFocusLost
        log.info("nameTextFieldFocusLost() called");
        cf.setName(nameTextField.getText());
    }//GEN-LAST:event_nameTextFieldFocusLost

    private void portTextFieldFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_portTextFieldFocusLost
        log.info("portTextFieldFocusLost() called");
        cf.setPort(Integer.parseInt(portTextField.getText()));
    }//GEN-LAST:event_portTextFieldFocusLost

    private void serverTextFieldFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_serverTextFieldFocusLost
        log.info("serverTextFieldFocusLost() called");
        cf.setServer(serverTextField.getText());
    }//GEN-LAST:event_serverTextFieldFocusLost

    private void serverTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_serverTextFieldActionPerformed
        log.info("serverTextFieldActionPerformed() called");
        cf.setServer(serverTextField.getText());
    }//GEN-LAST:event_serverTextFieldActionPerformed

    private void dccHitsTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_dccHitsTextFieldActionPerformed
        log.info("dccHitsTextFieldActionPerformed() called");
        cf.setDccHits(Integer.parseInt(dccHitsTextField.getText()));
    }//GEN-LAST:event_dccHitsTextFieldActionPerformed

    private void floodProtectionCheckBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_floodProtectionCheckBoxActionPerformed
        log.info("floodProtectionTextFieldActionPerformed() called");
        cf.setFloodProtection(floodProtectionCheckBox.isSelected());
    }//GEN-LAST:event_floodProtectionCheckBoxActionPerformed

    private void channelTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_channelTextFieldActionPerformed
        log.info("channelTextFieldActionPerformed() called");
        cf.setChannel(channelTextField.getText());
    }//GEN-LAST:event_channelTextFieldActionPerformed

    private void portTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_portTextFieldActionPerformed
        log.info("portTextFieldActionPerformed() called");
        cf.setPort(Integer.parseInt(portTextField.getText()));
    }//GEN-LAST:event_portTextFieldActionPerformed

    /** Exit the Application */
    private void exitForm(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_exitForm
        dispose();
    }//GEN-LAST:event_exitForm

    /**
    * @param args the command line arguments
    */
    @SuppressWarnings("deprecation")
    public static void main(String[] args) {
        new ConfigUI().show();
    }


    private javax.swing.JTextField serverTextField;
    private javax.swing.JTextField portTextField;
    private javax.swing.JTextField nameTextField;
    private javax.swing.JTextField nicknameTextField;
    private javax.swing.JTextField channelTextField;
    private javax.swing.JTextField channelModesTextField;
    private javax.swing.JCheckBox autovoiceCheckBox;
    private javax.swing.JCheckBox autogreetCheckBox;
    private javax.swing.JCheckBox floodProtectionCheckBox;
    private javax.swing.JCheckBox opmeCheckBox;
    private javax.swing.JCheckBox cycleForOpsCheckBox;
    private javax.swing.JCheckBox dynamicLimitCheckBox;
    private javax.swing.JTextField joinHitsTextField;
    private javax.swing.JTextField joinTimeTextField;
    private javax.swing.JTextField ctcpHitsTextField;
    private javax.swing.JTextField ctcpTimeTextField;
    private javax.swing.JTextField privmsgHitsTextField;
    private javax.swing.JTextField privmsgTimeTextField;
    private javax.swing.JTextField chanmsgHitsTextField;
    private javax.swing.JTextField chanmsgTimeTextField;
    private javax.swing.JTextField colourHitsTextField;
    private javax.swing.JTextField colourTimeTextField;
    private javax.swing.JTextField dccHitsTextField;
    private javax.swing.JTextField dccTimeTextField;
    // End of variables declaration//GEN-END:variables

}
