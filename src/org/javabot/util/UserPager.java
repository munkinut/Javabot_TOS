/*
 * Pager.java - pages ArrayLists into pages of smaller ArrayLists
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

package org.javabot.util;

import org.javabot.user.User;

import java.util.*;

/** Takes a ArrayList of objects and splits it into a set of smaller ArrayLists (pages).
 * @author W Milburn
 * @version 0.1
 */
public class UserPager {

    private ArrayList<User> toPage = new ArrayList<>();
    private Hashtable<Integer, ArrayList> pages = new Hashtable<>();
    private int pageIndex;

/** Constructs a Pager.
 * @param v ArrayList of objects you want to page.
 * @param x Number of objects you require per page.
 */    
    public UserPager(ArrayList<User> v, int x) {
        toPage = v;
        pages = this.pageBy(x);
        pageIndex = 0;
    }
    
/** Constructs a Pager.
 */    
    public UserPager() {
        toPage = null;
        pages = null;
    }

    /** Returns the size of the ArrayList to page.
 * @return Size of ArrayList to page.
 */    
    public int numberOfResults() {
        if (toPage != null) {
            return toPage.size();
        }
        else return 0;
    }
    
/** Returns the next page of objects.
 * @return The next page of objects.
 */    
    public ArrayList next() {
        ArrayList v = (ArrayList) pages.get(pageIndex);
        pageIndex++;
        return v;
    }
    
/** Determines the presence of a next page.
 * @return True or false.
 */    
    public boolean hasNext() {
        boolean b = false;
        if ((!pages.isEmpty()) && (pages != null) && (pageIndex < pages.size())) {
            b = true;
        }
        return b;
    }
    
/** Returns the previous page of objects.
 * @return The previous page of objects.
 */    
    public ArrayList previous() {
        pageIndex--;
        return (ArrayList)pages.get(pageIndex);
    }
    
/** Determines the presence of a previous page.
 * @return True or false.
 */    
    public boolean hasPrevious() {
        boolean b = false;
        if ((!pages.isEmpty()) && (pages != null) && (pageIndex > 0)) {
            b = true;
        }
        return b;
    }
    
/** Pages the pre-loaded ArrayList of objects into a hash of pages keyed by page number, and containing x objects per page.
 * @param x Index of the page to return.
 * @return A Hashtable of ArrayLists, keyed by page number.
 */    
    public Hashtable<Integer, ArrayList> pageBy(int x) {

        int pageCount = 0;
        ArrayList<Object> v;
        Hashtable<Integer, ArrayList> ht = new Hashtable<>();
        Iterator e = toPage.iterator();
        while (e.hasNext()) {
            User user = (User)e.next();
            v = new ArrayList<Object>();
            v.add(user);
            ht.put(pageCount, v);
            pageCount++;
        }
        return ht;
    }
    
/** Returns the number of pages.
 * @return The number of pages.
 */    
    public int numberOfPages() {
        return pages.size();
    }
    
/** Returns a hash of all the pages, keyed by page number
 * @return A Hashtable of ArrayLists indexed by page number.
 */    
    public Hashtable getAllPages() {
        return pages;
    }
    
/** Returns a page, by page number.
 * @param x Index of page to return.
 * @return A ArrayList of objects representing a particular page.
 */    
    public ArrayList getPage(int x) {
        Integer key = x;
        return (ArrayList)pages.get(key);
    }
    
/** Returns an enumeration of pages.
 * @return An Enumeration of the pages.
 */    
    public Enumeration getPages() {
        return pages.elements();
    }
    
/** Returns an Enumeration of the page numbers.
 * @return An enumeration of the page numbers.
 */    
    public Enumeration getPageNumbers() {
        return pages.keys();
    }
}