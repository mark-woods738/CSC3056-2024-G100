/* ===========================================================
 * JFreeChart : a free chart library for the Java(tm) platform
 * ===========================================================
 *
 * (C) Copyright 2000-2006, by Object Refinery Limited and Contributors.
 *
 * Project Info:  http://www.jfree.org/jfreechart/index.html
 *
 * This library is free software; you can redistribute it and/or modify it 
 * under the terms of the GNU Lesser General Public License as published by 
 * the Free Software Foundation; either version 2.1 of the License, or 
 * (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful, but 
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY 
 * or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public 
 * License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, 
 * USA.  
 *
 * [Java is a trademark or registered trademark of Sun Microsystems, Inc. 
 * in the United States and other countries.]
 *
 * ---------------------
 * ServletUtilities.java
 * ---------------------
 * (C) Copyright 2002-2006, by Richard Atkinson and Contributors.
 *
 * Original Author:  Richard Atkinson;
 * Contributor(s):   J?rgen Hoffman;
 *                   David Gilbert (for Object Refinery Limited);
 *                   Douglas Clayton;
 *
 * $Id: ServletUtilities.java,v 1.4 2006/01/11 12:24:20 mungady Exp $
 *
 * Changes
 * -------
 * 19-Aug-2002 : Version 1;
 * 20-Apr-2003 : Added additional sendTempFile method to allow MIME type 
 *               specification and modified original sendTempFile method to 
 *               automatically set MIME type for JPEG and PNG files
 * 23-Jun-2003 : Added additional sendTempFile method at the request of 
 *               J?rgen Hoffman;
 * 07-Jul-2003 : Added more header information to streamed images;
 * 19-Aug-2003 : Forced images to be stored in the temporary directory defined 
 *               by System property java.io.tmpdir, rather than default (RA);
 * 24-Mar-2004 : Added temp filename prefix attribute (DG);
 * 09-Mar-2005 : Added "one time" file option (DG);
 * 10-Jan-2006 : Updated API docs and reformatted (DG);
 *
 */

package org.jfree.chart.servlet;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.jfree.chart.ChartRenderingInfo;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;

/**
 * Utility class used for servlet related JFreeChart operations.
 */
public class ServletUtilities {

    /** The filename prefix. */
    private static String tempFilePrefix = "jfreechart-";
    
    /** A prefix for "one time" charts. */
    private static String tempOneTimeFilePrefix = "jfreechart-onetime-";
    
    /**
     * Returns the prefix for the temporary file names generated by this class.
     * 
     * @return The prefix (never <code>null</code>).
     */
    public static String getTempFilePrefix() {
        return ServletUtilities.tempFilePrefix;   
    }
    
    /**
     * Sets the prefix for the temporary file names generated by this class.
     * 
     * @param prefix  the prefix (<code>null</code> not permitted).
     */
    public static void setTempFilePrefix(String prefix) {
        if (prefix == null) {
            throw new IllegalArgumentException("Null 'prefix' argument.");   
        }
        ServletUtilities.tempFilePrefix = prefix;
    }
    
    /**
     * Returns the prefix for "one time" temporary file names generated by
     * this class.
     * 
     * @return The prefix.
     */
    public static String getTempOneTimeFilePrefix() {
        return ServletUtilities.tempOneTimeFilePrefix;
    }
    
    /**
     * Sets the prefix for the "one time" temporary file names generated by 
     * this class.
     * 
     * @param prefix  the prefix (<code>null</code> not permitted).
     */
    public static void setTempOneTimeFilePrefix(String prefix) {
        if (prefix == null) {
            throw new IllegalArgumentException("Null 'prefix' argument.");   
        }
        ServletUtilities.tempOneTimeFilePrefix = prefix;
    }
    
    /**
     * Saves the chart as a PNG format file in the temporary directory.
     *
     * @param chart  the JFreeChart to be saved.
     * @param width  the width of the chart.
     * @param height  the height of the chart.
     * @param session  the HttpSession of the client (if <code>null</code>, the
     *                 temporary file is marked as "one-time" and deleted by 
     *                 the {@link DisplayChart} servlet right after it is
     *                 streamed to the client).
     *
     * @return The filename of the chart saved in the temporary directory.
     *
     * @throws IOException if there is a problem saving the file.
     */
    public static String saveChartAsPNG(JFreeChart chart, int width, int height,
            HttpSession session) throws IOException {
        
        return ServletUtilities.saveChartAsPNG(chart, width, height, null, 
                session);
        
    }

    /**
     * Saves the chart as a PNG format file in the temporary directory and
     * populates the {@link ChartRenderingInfo} object which can be used to 
     * generate an HTML image map.
     *
     * @param chart  the chart to be saved (<code>null</code> not permitted).
     * @param width  the width of the chart.
     * @param height  the height of the chart.
     * @param info  the ChartRenderingInfo object to be populated 
     *              (<code>null</code> permitted).
     * @param session  the HttpSession of the client (if <code>null</code>, the
     *                 temporary file is marked as "one-time" and deleted by 
     *                 the {@link DisplayChart} servlet right after it is
     *                 streamed to the client).
     *
     * @return The filename of the chart saved in the temporary directory.
     *
     * @throws IOException if there is a problem saving the file.
     */
    public static String saveChartAsPNG(JFreeChart chart, int width, int height,
            ChartRenderingInfo info, HttpSession session) throws IOException {

        if (chart == null) {
            throw new IllegalArgumentException("Null 'chart' argument.");   
        }
        ServletUtilities.createTempDir();
        String prefix = ServletUtilities.tempFilePrefix;
        if (session == null) {
            prefix = ServletUtilities.tempOneTimeFilePrefix;
        }
        File tempFile = File.createTempFile(prefix, ".png", 
                new File(System.getProperty("java.io.tmpdir")));
        ChartUtilities.saveChartAsPNG(tempFile, chart, width, height, info);
        if (session != null) {
            ServletUtilities.registerChartForDeletion(tempFile, session);
        }
        return tempFile.getName();

    }

    /**
     * Saves the chart as a JPEG format file in the temporary directory.
     * <p>
     * SPECIAL NOTE: Please avoid using JPEG as an image format for charts,
     * it is a "lossy" format that introduces visible distortions in the
     * resulting image - use PNG instead.  In addition, note that JPEG output
     * is supported by JFreeChart only for JRE 1.4.2 or later.
     * 
     * @param chart  the JFreeChart to be saved.
     * @param width  the width of the chart.
     * @param height  the height of the chart.
     * @param session  the HttpSession of the client (if <code>null</code>, the
     *                 temporary file is marked as "one-time" and deleted by 
     *                 the {@link DisplayChart} servlet right after it is
     *                 streamed to the client).
     *
     * @return The filename of the chart saved in the temporary directory.
     *
     * @throws IOException if there is a problem saving the file.
     */
    public static String saveChartAsJPEG(JFreeChart chart, int width, 
                                         int height, HttpSession session) 
            throws IOException {

        return ServletUtilities.saveChartAsJPEG(chart, width, height, null, 
                session);
        
    }

    /**
     * Saves the chart as a JPEG format file in the temporary directory and
     * populates the <code>ChartRenderingInfo</code> object which can be used 
     * to generate an HTML image map.
     * <p>
     * SPECIAL NOTE: Please avoid using JPEG as an image format for charts,
     * it is a "lossy" format that introduces visible distortions in the
     * resulting image - use PNG instead.  In addition, note that JPEG output
     * is supported by JFreeChart only for JRE 1.4.2 or later.
     *
     * @param chart  the chart to be saved (<code>null</code> not permitted).
     * @param width  the width of the chart
     * @param height  the height of the chart
     * @param info  the ChartRenderingInfo object to be populated
     * @param session  the HttpSession of the client (if <code>null</code>, the
     *                 temporary file is marked as "one-time" and deleted by 
     *                 the {@link DisplayChart} servlet right after it is
     *                 streamed to the client).
     *
     * @return The filename of the chart saved in the temporary directory
     *
     * @throws IOException if there is a problem saving the file.
     */
    public static String saveChartAsJPEG(JFreeChart chart, int width, 
            int height, ChartRenderingInfo info, HttpSession session)
            throws IOException {

        if (chart == null) {
            throw new IllegalArgumentException("Null 'chart' argument.");   
        }
        
        ServletUtilities.createTempDir();
        String prefix = ServletUtilities.tempFilePrefix;
        if (session == null) {
            prefix = ServletUtilities.tempOneTimeFilePrefix;   
        }
        File tempFile = File.createTempFile(prefix, ".jpeg", 
                new File(System.getProperty("java.io.tmpdir")));
        ChartUtilities.saveChartAsJPEG(tempFile, chart, width, height, info);
        if (session != null) {
            ServletUtilities.registerChartForDeletion(tempFile, session);
        }
        return tempFile.getName();

    }

    /**
     * Creates the temporary directory if it does not exist.  Throws a 
     * <code>RuntimeException</code> if the temporary directory is 
     * <code>null</code>.  Uses the system property <code>java.io.tmpdir</code> 
     * as the temporary directory.  This sounds like a strange thing to do but 
     * my temporary directory was not created on my default Tomcat 4.0.3 
     * installation.  Could save some questions on the forum if it is created 
     * when not present.
     */
    protected static void createTempDir() {
        String tempDirName = System.getProperty("java.io.tmpdir");
        if (tempDirName == null) {
            throw new RuntimeException("Temporary directory system property " 
                    + "(java.io.tmpdir) is null.");
        }

        // create the temporary directory if it doesn't exist
        File tempDir = new File(tempDirName);
        if (!tempDir.exists()) {
            tempDir.mkdirs();
        }
    }

    /**
     * Adds a {@link ChartDeleter} object to the session object with the name 
     * <code>JFreeChart_Deleter</code> if there is not already one bound to the 
     * session and adds the filename to the list of charts to be deleted.
     *
     * @param tempFile  the file to be deleted.
     * @param session  the HTTP session of the client.
     */
    protected static void registerChartForDeletion(File tempFile, 
            HttpSession session) {

        //  Add chart to deletion list in session
        if (session != null) {
            ChartDeleter chartDeleter 
                = (ChartDeleter) session.getAttribute("JFreeChart_Deleter");
            if (chartDeleter == null) {
                chartDeleter = new ChartDeleter();
                session.setAttribute("JFreeChart_Deleter", chartDeleter);
            }
            chartDeleter.addChart(tempFile.getName());
        }
        else {
            System.out.println("Session is null - chart will not be deleted");
        }
    }

    /**
     * Binary streams the specified file in the temporary directory to the
     * HTTP response in 1KB chunks.
     * 
     * @param filename  the name of the file in the temporary directory.
     * @param response  the HTTP response object.
     * 
     * @throws IOException  if there is an I/O problem.
     */
    public static void sendTempFile(String filename, 
            HttpServletResponse response) throws IOException {

        File file = new File(System.getProperty("java.io.tmpdir"), filename);
        ServletUtilities.sendTempFile(file, response);
    }

    /**
     * Binary streams the specified file to the HTTP response in 1KB chunks.
     *
     * @param file  the file to be streamed.
     * @param response  the HTTP response object.
     *
     * @throws IOException if there is an I/O problem.
     */
    public static void sendTempFile(File file, HttpServletResponse response)
            throws IOException {

        String mimeType = null;
        String filename = file.getName();
        if (filename.length() > 5) {
            if (filename.substring(filename.length() - 5, 
                    filename.length()).equals(".jpeg")) {
                mimeType = "image/jpeg";
            } 
            else if (filename.substring(filename.length() - 4, 
                    filename.length()).equals(".png")) {
                mimeType = "image/png";
            }
        }
        ServletUtilities.sendTempFile(file, response, mimeType);
    }

    /**
     * Binary streams the specified file to the HTTP response in 1KB chunks.
     *
     * @param file  the file to be streamed.
     * @param response  the HTTP response object.
     * @param mimeType  the mime type of the file, null allowed.
     *
     * @throws IOException if there is an I/O problem.
     */
    public static void sendTempFile(File file, HttpServletResponse response,
                                    String mimeType) throws IOException {

        if (file.exists()) {
            BufferedInputStream bis = new BufferedInputStream(
                    new FileInputStream(file));

            //  Set HTTP headers
            if (mimeType != null) {
                response.setHeader("Content-Type", mimeType);
            }
            response.setHeader("Content-Length", String.valueOf(file.length()));
            SimpleDateFormat sdf = new SimpleDateFormat(
                    "EEE, dd MMM yyyy HH:mm:ss z");
            sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
            response.setHeader("Last-Modified", 
                    sdf.format(new Date(file.lastModified())));

            BufferedOutputStream bos = new BufferedOutputStream(
                    response.getOutputStream());
            byte[] input = new byte[1024];
            boolean eof = false;
            while (!eof) {
                int length = bis.read(input);
                if (length == -1) {
                    eof = true;
                } 
                else {
                    bos.write(input, 0, length);
                }
            }
            bos.flush();
            bis.close();
            bos.close();
        }
        else {
            throw new FileNotFoundException(file.getAbsolutePath());
        }
        return;
    }

    /**
     * Perform a search/replace operation on a String
     * There are String methods to do this since (JDK 1.4)
     *
     * @param inputString  the String to have the search/replace operation.
     * @param searchString  the search String.
     * @param replaceString  the replace String.
     *
     * @return The String with the replacements made.
     */
    public static String searchReplace(String inputString,
                                       String searchString,
                                       String replaceString) {

        int i = inputString.indexOf(searchString);
        if (i == -1) {
            return inputString;
        }

        String r = "";
        r += inputString.substring(0, i) + replaceString;
        if (i + searchString.length() < inputString.length()) {
            r += searchReplace(inputString.substring(i + searchString.length()),
                    searchString, replaceString);
        }

        return r;
    }

}