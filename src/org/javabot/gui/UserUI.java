/*
 * UserUI.java - provides the GUI for JavaBot user management
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

public class UserUI extends javax.swing.JFrame {

    Logger log = Logger.getLogger(this.getClass().getName());

    private final UserUIConfigurator userUIConfigurator;
    
    /** Creates new form UserUI */
    public UserUI() {
        log.info("UserUI() called");
        userUIConfigurator = new UserUIConfigurator();
        initComponents();
        // setJMenuBar(this.userUIMenuBar);
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    private void initComponents() {//GEN-BEGIN:initComponents
        // Variables declaration - do not modify//GEN-BEGIN:variables
        javax.swing.JMenuBar userUIMenuBar = new javax.swing.JMenuBar();
        javax.swing.JMenu userMenu = new javax.swing.JMenu();
        javax.swing.JMenuItem saveExitMenuItem = new javax.swing.JMenuItem();
        javax.swing.JMenuItem exitMenuItem = new javax.swing.JMenuItem();
            userTreePopupMenu = new javax.swing.JPopupMenu();
        javax.swing.JMenuItem addUserMenuItem = new javax.swing.JMenuItem();
            removeUserMenuItem = new javax.swing.JMenuItem();
        javax.swing.JSplitPane jSplitPane1 = new javax.swing.JSplitPane();
        javax.swing.JScrollPane treeScrollPane = new javax.swing.JScrollPane();
            javax.swing.tree.DefaultMutableTreeNode top = new javax.swing.tree.DefaultMutableTreeNode("Users");
            this.createNodes(top);
            userTree = new javax.swing.JTree(top);
        javax.swing.JPanel userInfoPanel = new javax.swing.JPanel();
        javax.swing.JTabbedPane jTabbedPane1 = new javax.swing.JTabbedPane();
        javax.swing.JPanel userInfo = new javax.swing.JPanel();
        javax.swing.JLabel botnickLabel = new javax.swing.JLabel();
            botnickTextField = new javax.swing.JTextField();
        javax.swing.JLabel hostmaskLabel = new javax.swing.JLabel();
            hostmaskTextField = new javax.swing.JTextField();
        javax.swing.JLabel passwordLabel = new javax.swing.JLabel();
            passwordTextField = new javax.swing.JTextField();
        javax.swing.JLabel greetLabel = new javax.swing.JLabel();
            greetTextField = new javax.swing.JTextField();
        javax.swing.JPanel userFlags = new javax.swing.JPanel();
            friendCheckBox = new javax.swing.JCheckBox();
            voiceCheckBox = new javax.swing.JCheckBox();
            opCheckBox = new javax.swing.JCheckBox();
            masterCheckBox = new javax.swing.JCheckBox();
            ownerCheckBox = new javax.swing.JCheckBox();
        javax.swing.JPanel buttonPanel = new javax.swing.JPanel();
        javax.swing.JButton okButton = new javax.swing.JButton();
            applyButton = new javax.swing.JButton();
            cancelButton = new javax.swing.JButton();
            
            userUIMenuBar.setEnabled(false);
            userMenu.setText("Users");
            saveExitMenuItem.setText("Save and Exit");
            saveExitMenuItem.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    saveExitMenuItemActionPerformed(evt);
                }
            });
            
            userMenu.add(saveExitMenuItem);
            exitMenuItem.setText("Exit");
            exitMenuItem.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    exitMenuItemActionPerformed(evt);
                }
            });
            
            userMenu.add(exitMenuItem);
            userUIMenuBar.add(userMenu);
          addUserMenuItem.setText("Add User");
          addUserMenuItem.addActionListener(new java.awt.event.ActionListener() {
              public void actionPerformed(java.awt.event.ActionEvent evt) {
                  addUserMenuItemActionPerformed(evt);
              }
          });
          
          userTreePopupMenu.add(addUserMenuItem);
          removeUserMenuItem.setText("Remove User");
          removeUserMenuItem.addActionListener(new java.awt.event.ActionListener() {
              public void actionPerformed(java.awt.event.ActionEvent evt) {
                  removeUserMenuItemActionPerformed(evt);
              }
          });
          
          userTreePopupMenu.add(removeUserMenuItem);
          
            setTitle("User Config");
            addWindowListener(new java.awt.event.WindowAdapter() {
                public void windowClosing(java.awt.event.WindowEvent evt) {
                    exitForm(evt);
                }
            });
            
            jSplitPane1.setDividerSize(5);
            jSplitPane1.setPreferredSize(new java.awt.Dimension(350, 160));
            treeScrollPane.setMinimumSize(new java.awt.Dimension(100, 22));
            userTree.addMouseListener(new java.awt.event.MouseAdapter() {
                public void mousePressed(java.awt.event.MouseEvent evt) {
                    userTreeMousePressed(evt);
                }
                public void mouseReleased(java.awt.event.MouseEvent evt) {
                    userTreeMouseReleased(evt);
                }
            });
            
            userTree.addTreeSelectionListener(new javax.swing.event.TreeSelectionListener() {
                public void valueChanged(javax.swing.event.TreeSelectionEvent evt) {
                    userTreeValueChanged(evt);
                }
            });
            
            treeScrollPane.setViewportView(userTree);
            
            jSplitPane1.setLeftComponent(treeScrollPane);
          
          userInfoPanel.setLayout(new java.awt.BorderLayout());
                
                userInfo.setLayout(new java.awt.GridLayout(4, 2));
                
                botnickLabel.setText("Botnick");
                userInfo.add(botnickLabel);
                
                botnickTextField.addActionListener(new java.awt.event.ActionListener() {
                    public void actionPerformed(java.awt.event.ActionEvent evt) {
                        botnickTextFieldActionPerformed(evt);
                    }
                });
                
                botnickTextField.addFocusListener(new java.awt.event.FocusAdapter() {
                    public void focusLost(java.awt.event.FocusEvent evt) {
                        botnickTextFieldFocusLost(evt);
                    }
                });
                
                botnickTextField.addInputMethodListener(new java.awt.event.InputMethodListener() {
                    public void inputMethodTextChanged(java.awt.event.InputMethodEvent evt) {
                        botnickTextFieldInputMethodTextChanged(evt);
                    }
                    public void caretPositionChanged(java.awt.event.InputMethodEvent evt) {
                    }
                });
                
                userInfo.add(botnickTextField);
                
                hostmaskLabel.setText("Hostmask");
                userInfo.add(hostmaskLabel);
                
                hostmaskTextField.addActionListener(new java.awt.event.ActionListener() {
                    public void actionPerformed(java.awt.event.ActionEvent evt) {
                        hostmaskTextFieldActionPerformed(evt);
                    }
                });
                
                hostmaskTextField.addFocusListener(new java.awt.event.FocusAdapter() {
                    public void focusLost(java.awt.event.FocusEvent evt) {
                        hostmaskTextFieldFocusLost(evt);
                    }
                });
                
                hostmaskTextField.addInputMethodListener(new java.awt.event.InputMethodListener() {
                    public void inputMethodTextChanged(java.awt.event.InputMethodEvent evt) {
                        hostmaskTextFieldInputMethodTextChanged(evt);
                    }
                    public void caretPositionChanged(java.awt.event.InputMethodEvent evt) {
                    }
                });
                
                userInfo.add(hostmaskTextField);
                
                passwordLabel.setText("Password");
                userInfo.add(passwordLabel);
                
                passwordTextField.addActionListener(new java.awt.event.ActionListener() {
                    public void actionPerformed(java.awt.event.ActionEvent evt) {
                        passwordTextFieldActionPerformed(evt);
                    }
                });
                
                passwordTextField.addFocusListener(new java.awt.event.FocusAdapter() {
                    public void focusLost(java.awt.event.FocusEvent evt) {
                        passwordTextFieldFocusLost(evt);
                    }
                });
                
                passwordTextField.addInputMethodListener(new java.awt.event.InputMethodListener() {
                    public void inputMethodTextChanged(java.awt.event.InputMethodEvent evt) {
                        passwordTextFieldInputMethodTextChanged(evt);
                    }
                    public void caretPositionChanged(java.awt.event.InputMethodEvent evt) {
                    }
                });
                
                userInfo.add(passwordTextField);
                
                greetLabel.setText("Greet");
                userInfo.add(greetLabel);
                
                greetTextField.addActionListener(new java.awt.event.ActionListener() {
                    public void actionPerformed(java.awt.event.ActionEvent evt) {
                        greetTextFieldActionPerformed(evt);
                    }
                });
                
                greetTextField.addFocusListener(new java.awt.event.FocusAdapter() {
                    public void focusLost(java.awt.event.FocusEvent evt) {
                        greetTextFieldFocusLost(evt);
                    }
                });
                
                greetTextField.addInputMethodListener(new java.awt.event.InputMethodListener() {
                    public void inputMethodTextChanged(java.awt.event.InputMethodEvent evt) {
                        greetTextFieldInputMethodTextChanged(evt);
                    }
                    public void caretPositionChanged(java.awt.event.InputMethodEvent evt) {
                    }
                });
                
                userInfo.add(greetTextField);
                
                jTabbedPane1.addTab("Info", userInfo);
              
              //userFlags.setLayout(new java.awt.GridLayout(5, 1));
                
                friendCheckBox.setText("Friend");
                friendCheckBox.addActionListener(new java.awt.event.ActionListener() {
                    public void actionPerformed(java.awt.event.ActionEvent evt) {
                        friendCheckBoxActionPerformed(evt);
                    }
                });
                
                userFlags.add(friendCheckBox);
                
                voiceCheckBox.setText("Voice");
                voiceCheckBox.addActionListener(new java.awt.event.ActionListener() {
                    public void actionPerformed(java.awt.event.ActionEvent evt) {
                        voiceCheckBoxActionPerformed(evt);
                    }
                });
                
                userFlags.add(voiceCheckBox);
                
                opCheckBox.setText("Op");
                opCheckBox.addActionListener(new java.awt.event.ActionListener() {
                    public void actionPerformed(java.awt.event.ActionEvent evt) {
                        opCheckBoxActionPerformed(evt);
                    }
                });
                
                userFlags.add(opCheckBox);
                
                masterCheckBox.setText("Master");
                masterCheckBox.addActionListener(new java.awt.event.ActionListener() {
                    public void actionPerformed(java.awt.event.ActionEvent evt) {
                        masterCheckBoxActionPerformed(evt);
                    }
                });
                
                userFlags.add(masterCheckBox);
                
                ownerCheckBox.setText("Owner");
                ownerCheckBox.addActionListener(new java.awt.event.ActionListener() {
                    public void actionPerformed(java.awt.event.ActionEvent evt) {
                        ownerCheckBoxActionPerformed(evt);
                    }
                });
                
                userFlags.add(ownerCheckBox);
                
                jTabbedPane1.addTab("Flags", userFlags);
              
              userInfoPanel.add(jTabbedPane1, java.awt.BorderLayout.CENTER);
            
            okButton.setText("OK");
              okButton.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
              okButton.addActionListener(new java.awt.event.ActionListener() {
                  public void actionPerformed(java.awt.event.ActionEvent evt) {
                      okButtonActionPerformed(evt);
                  }
              });
              
              buttonPanel.add(okButton);
              
              applyButton.setText("Apply");
              applyButton.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
              applyButton.setEnabled(false);
              applyButton.addActionListener(new java.awt.event.ActionListener() {
                  public void actionPerformed(java.awt.event.ActionEvent evt) {
                      applyButtonActionPerformed(evt);
                  }
              });
              
              buttonPanel.add(applyButton);
              
              cancelButton.setText("Cancel");
              cancelButton.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
              cancelButton.addActionListener(new java.awt.event.ActionListener() {
                  public void actionPerformed(java.awt.event.ActionEvent evt) {
                      cancelButtonActionPerformed(evt);
                  }
              });
              
              buttonPanel.add(cancelButton);
              
              userInfoPanel.add(buttonPanel, java.awt.BorderLayout.SOUTH);
            
            jSplitPane1.setRightComponent(userInfoPanel);
          
          getContentPane().add(jSplitPane1, java.awt.BorderLayout.CENTER);
        
        pack();
    }//GEN-END:initComponents

    private void clearForms() {
        log.info("clearForms() called");
        this.botnickTextField.setText("");
        this.hostmaskTextField.setText("");
        this.passwordTextField.setText("");
        this.greetTextField.setText("");
        this.friendCheckBox.setSelected(false);
        this.voiceCheckBox.setSelected(false);
        this.opCheckBox.setSelected(false);
        this.masterCheckBox.setSelected(false);
        this.ownerCheckBox.setSelected(false);
    }
    
    private void removeUserMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_removeUserMenuItemActionPerformed
        // Add your handling code here:
        javax.swing.tree.DefaultTreeModel model = (javax.swing.tree.DefaultTreeModel)userTree.getModel();
        javax.swing.tree.DefaultMutableTreeNode node = (javax.swing.tree.DefaultMutableTreeNode)userTree.getLastSelectedPathComponent();
        org.javabot.user.User user = (org.javabot.user.User)node.getUserObject();
        if (userUIConfigurator.delUser(user.getNick())) {
            model.removeNodeFromParent(node);
            this.clearForms();
        }
    }//GEN-LAST:event_removeUserMenuItemActionPerformed

    private void addUserMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addUserMenuItemActionPerformed
        // Add your handling code here:
        org.javabot.util.RandomStringGenerator rsg = new org.javabot.util.RandomStringGenerator();
        String newBotnick = rsg.generate();
        String newBotpass = rsg.generate();
        if (userUIConfigurator.addUser(newBotnick, newBotpass)) {
            javax.swing.tree.DefaultTreeModel model = (javax.swing.tree.DefaultTreeModel)userTree.getModel();
            javax.swing.tree.DefaultMutableTreeNode newNode = new javax.swing.tree.DefaultMutableTreeNode(newBotnick);
            newNode.setUserObject(userUIConfigurator.getUser(newBotnick));
            javax.swing.tree.DefaultMutableTreeNode topNode = (javax.swing.tree.DefaultMutableTreeNode)model.getRoot();
            int lastChildIndex = model.getChildCount(topNode);
            
            model.insertNodeInto(newNode, topNode, lastChildIndex);
            javax.swing.tree.TreePath path = new javax.swing.tree.TreePath(newNode.getPath());
            userTree.expandPath(new javax.swing.tree.TreePath(topNode.getPath()));
            userTree.setSelectionPath(path);
        }
    }//GEN-LAST:event_addUserMenuItemActionPerformed

    private void doPopupHandle(java.awt.event.MouseEvent evt) {
        if (evt.isPopupTrigger()) {
            javax.swing.tree.TreePath treePath = userTree.getPathForLocation(evt.getX(), evt.getY());
            if (treePath != null) {
                userTree.setSelectionPath(treePath);
                javax.swing.tree.DefaultMutableTreeNode treeNode = (javax.swing.tree.DefaultMutableTreeNode)treePath.getLastPathComponent();
                if (treeNode != null) {
                    removeUserMenuItem.setEnabled(treeNode.isLeaf() && !treeNode.isRoot());
                    userTreePopupMenu.show(evt.getComponent(), evt.getX(), evt.getY());
                }
            }
        }
    }
    
    private void userTreeMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_userTreeMouseReleased
        // Add your handling code here:
        // Required to handle isPopupTrigger in Windows
        this.doPopupHandle(evt);
    }//GEN-LAST:event_userTreeMouseReleased

    private void userTreeMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_userTreeMousePressed
        // Add your handling code here:
        // Required to handle isPopupTrigger in Solaris
        this.doPopupHandle(evt);
    }//GEN-LAST:event_userTreeMousePressed

    @SuppressWarnings("deprecation")
    private boolean isRightClick(java.awt.event.MouseEvent evt) {
        return (evt.getModifiersEx() & java.awt.event.InputEvent.BUTTON3_MASK) == java.awt.event.InputEvent.BUTTON3_MASK;
    }
    private void greetTextFieldInputMethodTextChanged(java.awt.event.InputMethodEvent evt) {//GEN-FIRST:event_greetTextFieldInputMethodTextChanged
        // Add your handling code here:
        applyButton.setEnabled(true);
    }//GEN-LAST:event_greetTextFieldInputMethodTextChanged

    private void passwordTextFieldInputMethodTextChanged(java.awt.event.InputMethodEvent evt) {//GEN-FIRST:event_passwordTextFieldInputMethodTextChanged
        // Add your handling code here:
        applyButton.setEnabled(true);
    }//GEN-LAST:event_passwordTextFieldInputMethodTextChanged

    private void hostmaskTextFieldInputMethodTextChanged(java.awt.event.InputMethodEvent evt) {//GEN-FIRST:event_hostmaskTextFieldInputMethodTextChanged
        // Add your handling code here:
        applyButton.setEnabled(true);
    }//GEN-LAST:event_hostmaskTextFieldInputMethodTextChanged

    private void botnickTextFieldInputMethodTextChanged(java.awt.event.InputMethodEvent evt) {//GEN-FIRST:event_botnickTextFieldInputMethodTextChanged
        // Add your handling code here:
        applyButton.setEnabled(true);
    }//GEN-LAST:event_botnickTextFieldInputMethodTextChanged

    private void applyButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_applyButtonActionPerformed
        // Add your handling code here:
        userUIConfigurator.storeUsers();
        applyButton.setEnabled(false);
        cancelButton.setEnabled(false);
    }//GEN-LAST:event_applyButtonActionPerformed

    private void hostmaskTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_hostmaskTextFieldActionPerformed
        // Add your handling code here:
        userUIConfigurator.setNodeHostmask(hostmaskTextField.getText());
    }//GEN-LAST:event_hostmaskTextFieldActionPerformed

    private void exitMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exitMenuItemActionPerformed
        // Add your handling code here:
        userUIConfigurator.reloadUsers();
        dispose();
    }//GEN-LAST:event_exitMenuItemActionPerformed

    private void cancelButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelButtonActionPerformed
        // Add your handling code here:
        userUIConfigurator.reloadUsers();
        dispose();
    }//GEN-LAST:event_cancelButtonActionPerformed

    private void okButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_okButtonActionPerformed
        // Add your handling code here:
        userUIConfigurator.storeUsers();
        dispose();
    }//GEN-LAST:event_okButtonActionPerformed

    private void ownerCheckBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ownerCheckBoxActionPerformed
        // Add your handling code here:
        //userUIConfigurator.setNodeOwner(ownerCheckBox.isSelected());
        applyButton.setEnabled(true);
    }//GEN-LAST:event_ownerCheckBoxActionPerformed

    private void masterCheckBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_masterCheckBoxActionPerformed
        // Add your handling code here:
        //userUIConfigurator.setNodeMaster(masterCheckBox.isSelected());
        applyButton.setEnabled(true);
    }//GEN-LAST:event_masterCheckBoxActionPerformed

    private void opCheckBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_opCheckBoxActionPerformed
        // Add your handling code here:
        //userUIConfigurator.setNodeOp(opCheckBox.isSelected());
        applyButton.setEnabled(true);
    }//GEN-LAST:event_opCheckBoxActionPerformed

    private void voiceCheckBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_voiceCheckBoxActionPerformed
        // Add your handling code here:
        //userUIConfigurator.setNodeVoice(voiceCheckBox.isSelected());
        applyButton.setEnabled(true);
    }//GEN-LAST:event_voiceCheckBoxActionPerformed

    private void friendCheckBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_friendCheckBoxActionPerformed
        // Add your handling code here:
        //userUIConfigurator.setNodeFriend(friendCheckBox.isSelected());
        applyButton.setEnabled(true);
    }//GEN-LAST:event_friendCheckBoxActionPerformed

    private void greetTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_greetTextFieldActionPerformed
        // Add your handling code here:
        userUIConfigurator.setNodeGreet(greetTextField.getText());
    }//GEN-LAST:event_greetTextFieldActionPerformed

    private void greetTextFieldFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_greetTextFieldFocusLost
        // Add your handling code here:
        userUIConfigurator.setNodeGreet(greetTextField.getText());
    }//GEN-LAST:event_greetTextFieldFocusLost

    private void passwordTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_passwordTextFieldActionPerformed
        // Add your handling code here:
        userUIConfigurator.setNodePassword(passwordTextField.getText());
    }//GEN-LAST:event_passwordTextFieldActionPerformed

    private void passwordTextFieldFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_passwordTextFieldFocusLost
        // Add your handling code here:
        userUIConfigurator.setNodePassword(passwordTextField.getText());
    }//GEN-LAST:event_passwordTextFieldFocusLost

    private void hostmaskTextFieldFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_hostmaskTextFieldFocusLost
        // Add your handling code here:
        userUIConfigurator.setNodeHostmask(hostmaskTextField.getText());
    }//GEN-LAST:event_hostmaskTextFieldFocusLost

    private void botnickTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botnickTextFieldActionPerformed
        // Add your handling code here:
        userUIConfigurator.setNodeNick(botnickTextField.getText());
        userTree.repaint();
    }//GEN-LAST:event_botnickTextFieldActionPerformed

    private void botnickTextFieldFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_botnickTextFieldFocusLost
        // Add your handling code here:
        userUIConfigurator.setNodeNick(botnickTextField.getText());
        userTree.repaint();
    }//GEN-LAST:event_botnickTextFieldFocusLost

    private void userTreeValueChanged(javax.swing.event.TreeSelectionEvent evt) {//GEN-FIRST:event_userTreeValueChanged
        
        javax.swing.tree.DefaultMutableTreeNode node = (javax.swing.tree.DefaultMutableTreeNode)
                           userTree.getLastSelectedPathComponent();
        
        if (node != null) {
            //noinspection StatementWithEmptyBody
            if (node.isLeaf() && !node.isRoot()) {
                userUIConfigurator.loadUser(node);
                botnickTextField.setText(userUIConfigurator.getNodeNick());
                hostmaskTextField.setText(userUIConfigurator.getNodeHostmask());
                passwordTextField.setText(userUIConfigurator.getNodePassword());
                greetTextField.setText(userUIConfigurator.getNodeGreet());
                friendCheckBox.setSelected(userUIConfigurator.getNodeFriend());
                voiceCheckBox.setSelected(userUIConfigurator.getNodeVoice());
                opCheckBox.setSelected(userUIConfigurator.getNodeOp());
                masterCheckBox.setSelected(userUIConfigurator.getNodeMaster());
                ownerCheckBox.setSelected(userUIConfigurator.getNodeOwner());
            } else {
            }
        }
    }//GEN-LAST:event_userTreeValueChanged

    private void saveExitMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveExitMenuItemActionPerformed
        userUIConfigurator.storeUsers();
        dispose();
    }//GEN-LAST:event_saveExitMenuItemActionPerformed

    private void createNodes(javax.swing.tree.DefaultMutableTreeNode top) {
        userUIConfigurator.createNodes(top);
    }
    
    /** Exit the Application */
    private void exitForm(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_exitForm
        userUIConfigurator.reloadUsers();
        dispose();
    }//GEN-LAST:event_exitForm

    /**
    * @param args the command line arguments
    */
    @SuppressWarnings("deprecation")
    public static void main(String[] args) {
        new UserUI().show();
    }


    private javax.swing.JPopupMenu userTreePopupMenu;
    private javax.swing.JMenuItem removeUserMenuItem;
    private javax.swing.JTree userTree;
    private javax.swing.JTextField botnickTextField;
    private javax.swing.JTextField hostmaskTextField;
    private javax.swing.JTextField passwordTextField;
    private javax.swing.JTextField greetTextField;
    private javax.swing.JCheckBox friendCheckBox;
    private javax.swing.JCheckBox voiceCheckBox;
    private javax.swing.JCheckBox opCheckBox;
    private javax.swing.JCheckBox masterCheckBox;
    private javax.swing.JCheckBox ownerCheckBox;
    private javax.swing.JButton applyButton;
    private javax.swing.JButton cancelButton;
    // End of variables declaration//GEN-END:variables

}
