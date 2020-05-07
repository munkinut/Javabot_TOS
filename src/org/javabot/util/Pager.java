/*
 * Pager.java - pages Vectors into pages of smaller Vectors
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

import java.util.*;

/** Takes a vector of objects and splits it into a set of smaller vectors (pages).
 * @author W Milburn
 * @version 0.1
 */
public class Pager {

    private final Vector toPage;
    private final Hashtable pages;
    private int pageIndex;

/** Constructs a Pager.
 * @param v Vector of objects you want to page.
 * @param x Number of objects you require per page.
 */    
    public Pager(Vector v, int x) {
        toPage = v;
        pages = this.pageBy(x);
        pageIndex = 0;
    }
    
/** Constructs a Pager.
 */    
    public Pager() {
        toPage = null;
        pages = null;
    }
    
/** Returns the size of the Vector to page.
 * @return Size of Vector to page.
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
    public Vector next() {
        Vector v = (Vector)pages.get(pageIndex);
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
    public Vector previous() {
        pageIndex--;
        return (Vector)pages.get(pageIndex);
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
    
/** Pages the pre-loaded vector of objects into a hash of pages keyed by page number, and containing x objects per page.
 * @param x Index of the page to return.
 * @return A Hashtable of Vectors, keyed by page number.
 */    
    public Hashtable pageBy(int x) {

        int pageCount = 0;
        Vector v;
        Hashtable ht = new Hashtable();
        Enumeration e = toPage.elements();
        while (e.hasMoreElements()) {
            v = new Vector();
            for (int i = 0; i < x; i++) {
                if (e.hasMoreElements()) {
                    Object o = e.nextElement();
                    v.add(o);
                }
            }
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
 * @return A Hashtable of Vectors indexed by page number.
 */    
    public Hashtable getAllPages() {
        return pages;
    }
    
/** Returns a page, by page number.
 * @param x Index of page to return.
 * @return A Vector of objects representing a particular page.
 */    
    public Vector getPage(int x) {
        Integer key = x;
        return (Vector)pages.get(key);
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
