/*
 * UserUIConfigurator.java - provides the model for JavaBot UserUI
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

import org.javabot.user.User;

import javax.swing.tree.DefaultMutableTreeNode;

public class UserUIConfigurator {
    
    private final org.javabot.user.UserManager um;
    private User currentNode;
    
    /** Creates new UserUIConfigurator */
    public UserUIConfigurator() {
        um = new org.javabot.user.UserManager();
        currentNode = null;
    }
    
    public void createNodes(DefaultMutableTreeNode top) {
        java.util.Vector<User> users = um.getUsers();
        DefaultMutableTreeNode userNode;
        User user;
        for (int i = 0; i < users.size(); i++) {
            user = users.elementAt(i);
            userNode = new DefaultMutableTreeNode(user);
            top.add(userNode);
        }
    }
    
    public boolean addUser(String botnick, String botpass) {
        return um.addUser(botnick, botpass);
    }
    
    public boolean delUser(String botnick) {
        return um.delUser(botnick);
    }
    
    public User getUser(String botnick) {
        return um.getUserByBotnick(botnick);
    }
    
    public void loadUser(DefaultMutableTreeNode node) {
        currentNode = (User)node.getUserObject();
    }
    
    public void reloadUsers() {
        um.reloadUsers();
    }
    
    public void storeUsers() {
        um.saveUsers();
    }
    
    public String getNodeNick() {
        return currentNode.getNick();
    }

    public String getNodePassword() {
        return currentNode.getPassword();
    }

    public String getNodeHostmask() {
        return currentNode.getHostmask();
    }

    public String getNodeGreet() {
        return currentNode.getGreet();
    }
    
    public boolean getNodeFriend() {
        return currentNode.isFriend();
    }

    public boolean getNodeVoice() {
        return currentNode.isVoice();
    }

    public boolean getNodeOp() {
        return currentNode.isOp();
    }

    public boolean getNodeMaster() {
        return currentNode.isMaster();
    }

    public boolean getNodeOwner() {
        return currentNode.isOwner();
    }
    
    public void setNodeNick(String nick) {
        currentNode.setNick(nick);
    }
    
    public void setNodeHostmask(String hostmask) {
        currentNode.setHostmask(hostmask);
    }

    public void setNodePassword(String password) {
        currentNode.setPassword(password);
    }
    
    public void setNodeGreet(String greet) {
        currentNode.setGreet(greet);
    }
    
    public void setNodeFriend(boolean friend) {
        currentNode.setFriend(friend);
    }

    public void setNodeVoice(boolean voice) {
        currentNode.setVoice(voice);
    }

    public void setNodeOp(boolean op) {
        currentNode.setOp(op);
    }

    public void setNodeMaster(boolean master) {
        currentNode.setMaster(master);
    }

    public void setNodeOwner(boolean owner) {
        currentNode.setOwner(owner);
    }

}
