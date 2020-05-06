/*
 * IRCCommands.java - provides outbound functions for JavaBot
 *
 * Copyright (C) 2001 by Torsten Born
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

package org.javabot.engine;

import java.io.IOException;
import java.io.DataOutputStream;
import java.util.Calendar;

/** Library of functions executing IRC commands
 */
public class IRCCommands {
    
    //Funktion PingPong by Torsten Born
    //********************************************************************
    //**
    /** Sends a PONG message
     * @param response PONG response message
     * @param outbound Outbound stream to IRC server
     */    
    public static void pingpong(String response,DataOutputStream outbound) {
        
        try {
            outbound.writeBytes("PONG " + response +"\r\n");
        }
        catch (IOException ioe) {
            System.out.println("IOException: " + ioe);
        }
        
    }
    //**
    //********************************************************************
    //Ende der Funktion PingPong
    
    //Funktion Identify by Warren Milburn
    //********************************************************************
    //**
    /** Sends user identity
     * @param name Bots real name
     * @param nick Bots nickname
     * @param outbound Outbound stream to IRC server
     */    
    public static void identify(String name, String nick, DataOutputStream outbound) {
        
        try {
                outbound.writeBytes("user "+name+" 0 0 We will rock you\n");
                outbound.writeBytes("nick "+nick+"\n");
        }
        catch (IOException ioe) {
            System.out.println("IOException: " + ioe);
        }
        
    }
    //**
    //********************************************************************
    //Ende der Funktion Identify
    
    //Funktion writeBytes by Warren Milburn
    //********************************************************************
    //**
    /** Sends a raw string
     * @param all String to send
     * @param outbound Outbound stream to IRC server
     */    
    public static void writeBytes(String all, DataOutputStream outbound) {
        
        try {
                outbound.writeBytes(all);
        }
        catch (IOException ioe) {
            System.out.println("IOException: " + ioe);
        }
        
    }
    //**
    //********************************************************************
    //Ende der Funktion writeBytes
    
    //Funktion quit by Warren Milburn
    //********************************************************************
    //**
    /** Sends a quit message
     * @param outbound Outbound stream to IRC server
     * @param quitMessage Quit message
     */    
    public static void quit(DataOutputStream outbound, String quitMessage) {
        
        try {
            outbound.writeBytes("QUIT :" + quitMessage +"\r\n");
        }
        catch (IOException ioe) {
            System.out.println("IOException: " + ioe);
        }
        
    }
    //**
    //********************************************************************
    //Ende der Funktion quit
    
    //Funktion privmsg by Warren Milburn
    //********************************************************************
    //**
    /** Sends a private message
     * @param to Recipient string
     * @param message Message String
     * @param outbound Outbound stream to IRC server
     */    
    public static void privmsg(String to, String message,DataOutputStream outbound) {
        try {
            outbound.writeBytes("PRIVMSG "+to+" :"+message+"\r\n");
        }
        catch (IOException ioe){
            System.out.println("IOException: " + ioe);
        }
    }
    //**
    //********************************************************************
    //Ende der Funktion Privmsg

    //Funktion AutoVoice by Torsten Born
    //********************************************************************
    //**
    /** Sends a voice mode change
     * @param channel Channel
     * @param nick Nick to voice
     * @param outbound Outbound stream to IRC server
     */    
    public static void autovoice(String channel, String nick, DataOutputStream outbound) {
        
        try {
            outbound.writeBytes("mode " + channel + " +v " + nick + "\r\n");
        }
        catch (IOException ioe) {
            System.out.println("IOException: " + ioe);
        }
    }
    //**
    //********************************************************************
    //Ende der Funktion AutoVoice
    
    //Funktion Ban by Warren Milburn
    //********************************************************************
    //**
    /** Sends a ban message
     * @param channel Channel
     * @param banmask Banmask to apply
     * @param outbound Outbound stream to IRC server
     */    
    public static void ban(String channel, String banmask, DataOutputStream outbound) {
        
        try {
            outbound.writeBytes("mode " + channel + " +b " + banmask + "\r\n");
        }
        catch (IOException ioe) {
            System.out.println("IOException: " + ioe);
        }
    }
    //**
    //********************************************************************
    //Ende der Funktion Ban
    
    //Funktion UnBan by Warren Milburn
    //********************************************************************
    //**
    /** Sends an unban message
     * @param channel Channel
     * @param banmask Banmask to apply
     * @param outbound Outbound stream to IRC server
     */    
    public static void unban(String channel, String banmask, DataOutputStream outbound) {
        
        try {
            outbound.writeBytes("mode " + channel + " -b " + banmask + "\r\n");
        }
        catch (IOException ioe) {
            System.out.println("IOException: " + ioe);
        }
    }
    //**
    //********************************************************************
    //Ende der Funktion UnBan
    
    //Funktion Kick by Warren Milburn
    //********************************************************************
    //**
    /** Sends a kick message
     * @param channel Channel
     * @param nick Nick to kick
     * @param outbound Outbound stream to IRC server
     */    
    public static void kick(String channel, String nick, DataOutputStream outbound) {
        
        try {
            outbound.writeBytes("kick " + channel + " " + nick + "\r\n");
        }
        catch (IOException ioe) {
            System.out.println("IOException: " + ioe);
        }
    }
    //**
    //********************************************************************
    //Ende der Funktion Kick
    
    //Funktion Invite by Warren Milburn
    //********************************************************************
    //**
    /** Sends an invite message
     * @param channel Channel
     * @param nick Nick to invite
     * @param outbound Outbound stream to IRC server
     */    
    public static void invite(String channel, String nick, DataOutputStream outbound) {
        
        try {
            outbound.writeBytes("invite " + nick + " " + channel + "\r\n");
        }
        catch (IOException ioe) {
            System.out.println("IOException: " + ioe);
        }
    }
    //**
    //********************************************************************
    //Ende der Funktion Invite
    
    //Funktion OpMe by Warren Milburn
    //********************************************************************
    //**
    /** Sends an op mode change message
     * @param channel Channel
     * @param nick Nick to op
     * @param outbound Outbound stream to IRC server
     */    
    public static void opme(String channel, String nick, DataOutputStream outbound) {
        
        try {
            outbound.writeBytes("mode "+channel+" +o "+nick+"\r\n");
        }
        catch (IOException ioe) {
            System.out.println("IOException: " + ioe);
        }
    }
    
    //**
    //********************************************************************
    //Ende der Funktion OpMe
    
    //Funktion names by Warren Milburn
    //********************************************************************
    //**
    /** Sends a names message
     * @param channel Channel
     * @param outbound Outbound stream to IRC server
     */    
    public static void names(String channel, DataOutputStream outbound) {
        
        try {
            outbound.writeBytes("names "+channel+"\r\n");
        }
        catch (IOException ioe) {
            System.out.println("IOException: " + ioe);
        }
    }
    
    //**
    //********************************************************************
    //Ende der Funktion names
    
    //Funktion changeChannelLimit by Warren Milburn
    //********************************************************************
    //**
    /** Sends a channel limit message
     * @param channel Channel
     * @param channelLimit Maximum channel users
     * @param outbound Outbound stream to IRC server
     */    
    public static void changeChannelLimit(String channel, int channelLimit, DataOutputStream outbound) {
        
        try {
            outbound.writeBytes("mode "+channel+" +l " + channelLimit +"\r\n");
        }
        catch (IOException ioe) {
            System.out.println("IOException: " + ioe);
        }
    }
    
    //**
    //********************************************************************
    //Ende der Funktion changeChannelLimit
    
    //Funktion changeChannelModes by Warren Milburn
    //********************************************************************
    //**
    /** Sends a channel mode change message
     * @param channel Channel
     * @param modes Channel modes
     * @param outbound Outbound stream to IRC server
     */    
    public static void changeChannelModes(String channel, String modes, DataOutputStream outbound) {
        
        try {
            outbound.writeBytes("mode "+channel+" " + modes +"\r\n");
        }
        catch (IOException ioe) {
            System.out.println("IOException: " + ioe);
        }
    }
    
    //**
    //********************************************************************
    //Ende der Funktion changeChannelModes
    
    //Funktion cycle by Warren Milburn
    //********************************************************************
    //**
    /** Send a part and join to channel
     * @param channel Channel
     * @param outbound Outbound stream to IRC server
     */    
    public static void cycle(String channel, DataOutputStream outbound) {
        
        IRCCommands.part(channel, outbound);
        IRCCommands.join(channel, outbound);
    }
    
    //**
    //********************************************************************
    //Ende der Funktion cycle
    
    //Funktion part by Warren Milburn
    //********************************************************************
    //**
    /** Sends a part message
     * @param channel Channel
     * @param outbound Outbound stream to IRC server
     */    
    public static void part(String channel, DataOutputStream outbound) {
        
        try {
            outbound.writeBytes("part "+channel+"\n");
        }
        catch (IOException ioe) {
            System.out.println("IOException: " + ioe);
        }
    }
    
    //**
    //********************************************************************
    //Ende der Funktion part
    
    //Funktion join by Warren Milburn
    //********************************************************************
    //**
    /** Sends a join message
     * @param channel Channel
     * @param outbound Outbound stream to IRC server
     */    
    public static void join(String channel, DataOutputStream outbound) {
        
        try {
            outbound.writeBytes("join "+channel+"\n");
        }
        catch (IOException ioe) {
            System.out.println("IOException: " + ioe);
        }
    }
    
    //**
    //********************************************************************
    //Ende der Funktion join
    
    //Funktion lockChannel by Warren Milburn
    //********************************************************************
    //**
    /** Sends a lock channel message
     * @param channel Channel
     * @param outbound Outbound stream to IRC server
     */    
    public static void lockChannel(String channel,DataOutputStream outbound) {
        
        try {
            outbound.writeBytes("mode "+channel+" +mi \r\n");
        }
        catch (IOException ioe) {
            System.out.println("IOException: " + ioe);
        }
    }
    
    //**
    //********************************************************************
    //Ende der Funktion lockChannel
    
    //Funktion unlockChannel by Warren Milburn
    //********************************************************************
    //**
    /** Sends an unlock message
     * @param channel Channel
     * @param outbound Outbound stream to IRC server
     */    
    public static void unlockChannel(String channel,DataOutputStream outbound) {
        
        try {
            outbound.writeBytes("mode "+channel+" -mi \r\n");
        }
        catch (IOException ioe) {
            System.out.println("IOException: " + ioe);
        }
    }
    
    //**
    //********************************************************************
    //Ende der Funktion unlockChannel
    
    //Funktion VersionReplay by Torsten Born
    //********************************************************************
    //**
    /** Sends a version play private message to channel
     * @param channel Channel
     * @param outbound Outbound stream to IRC server
     */    
    public static void versionreplay(String channel,DataOutputStream outbound) {
        
        try {
            outbound.writeBytes("PRIVMSG "+channel+" :JavaBot Version 0.2 by Torsten Born\r\n");
        }
        catch (IOException ioe) {
            System.out.println("IOException: " + ioe);
        }
    }
    
    //**
    //********************************************************************
    //Ende der Funktion VersionReplay
    
    //Funktion playGreet by Warren Milburn
    //********************************************************************
    //**
    /** Sends a greet private message to channel
     * @param channel Channel
     * @param outbound Outbound stream to IRC server
     * @param greet Greet message
     */    
    public static void playGreet(String channel, DataOutputStream outbound, String greet) {
        
        try {
            outbound.writeBytes("PRIVMSG " + channel + " :" + greet + "\r\n");
        }
        catch (IOException ioe) {
            System.out.println("IOException: " + ioe);
        }
    }
    
    //**
    //********************************************************************
    //Ende der Funktion playGreet
    
    //Funktion Help by Torsten Born
    //********************************************************************
    //**
    /** Sends a help response message
     * @param channel Channel
     * @param outbound Outbound stream to IRC server
     */    
    public static void help(String channel,DataOutputStream outbound) {
        
        try {
            outbound.writeBytes("PRIVMSG "+channel+" :JavaBot Version 0.2 commands:\r\n");
            outbound.writeBytes("PRIVMSG "+channel+" :!version = shows the current Javabot version\r\n");
            outbound.writeBytes("PRIVMSG "+channel+" :!date    = shows the current time and date of the JavaBot Server\r\n");
        }
        catch (IOException ioe) {
            System.out.println("IOException: " + ioe);
        }
    }
    
    //**
    //********************************************************************
    //Ende der Funktion Help
    
    //Funktion DateReplay by Michael Oemler
    //********************************************************************
    //**
    /** Sends a date private message to channel
     * @param channel Channel
     * @param outbound Outbound stream to IRC server
     */    
    public static void datereplay(String channel,DataOutputStream outbound) {
        
        try {
            Calendar cal = Calendar.getInstance();
            String[] month = (new java.text.DateFormatSymbols()).getMonths();
            
            String day = "";
            
            int    value = cal.get(Calendar.DAY_OF_WEEK);
            switch (value) {
                case Calendar.SUNDAY:
                    day += "Sunday";
                    break;
                case Calendar.MONDAY:
                    day += "Monday";
                    break;
                case Calendar.TUESDAY:
                    day += "Thuesday";
                    break;
                case Calendar.WEDNESDAY:
                    day += "Wednesday";
                    break;
                case Calendar.THURSDAY:
                    day += "Thursday";
                    break;
                case Calendar.FRIDAY:
                    day += "Friday";
                    break;
                case Calendar.SATURDAY:
                    day += "Saturday";
                    break;
            }
            
            int     year = cal.get(Calendar.YEAR);
            int     today = cal.get(Calendar.DAY_OF_MONTH);
            int     houre = cal.get(Calendar.HOUR);
            String  minute = String.valueOf(cal.get(Calendar.MINUTE));
            int     am_pm = cal.get(Calendar.AM_PM);
            int 	cach = Integer.parseInt(minute);
            String	ap = "";
            
            if (cach < 10){
                minute = "0" + minute;
            }
            
            switch(am_pm){
                case 0: ap = "AM"; break;
                case 1: ap = "PM"; break;
            }
            
            outbound.writeBytes("PRIVMSG "+channel+" :Today is "+day+" "+today+". "+month[cal.get(Calendar.MONTH)]+" "+year+"; "+houre+":"+minute+" "+ap+"\r\n");
            
        }
        catch (IOException ioe) {
            System.out.println("IOException: " + ioe);
        }
        
    }
    
    //**
    //********************************************************************
    //Ende der Funktion DateReplay
    
}