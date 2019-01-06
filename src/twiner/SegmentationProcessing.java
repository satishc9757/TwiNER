/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package twiner;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author satishc
 */
public class SegmentationProcessing {
    
    public void segmentationOp()
    {
        PrintWriter output = null;
        PrintWriter outputAppend = null;
        try {
            
            File input = new File("/Users/satishc/Desktop/Segmentation_set.txt");
            
            output = new PrintWriter("/Users/satishc/Desktop/processedSegments.txt","UTF-8");
            output.println(""); //to erase all the previous contents of file
            output.close(); 
            
            Map<String,Integer> processedSegs = new HashMap<String,Integer>();
            Set<String> processedSegmentSet = new  HashSet<String>();
            
            Scanner scFromSegmentSet = new Scanner(input);//all the segments separated by pipe operator
            scFromSegmentSet.useDelimiter("\\|");
            
            outputAppend = new PrintWriter(new FileOutputStream(new File("/Users/satishc/Desktop/processedSegments.txt"),true));
            
            Scanner sc = null;
                    
            for(int j=0;j<5;j++)
            {
            
                sc = new Scanner(scFromSegmentSet.next());//segments of each tweet
                sc.useDelimiter("\n");
            
                while(sc.hasNext())
                {
                    processedSegmentSet.add(sc.next());
                 
                }
            
                ArrayList<Segment> finalProcessedSegmentSet = new  ArrayList<Segment>();
            
                SegmentationUtility segHelp = new SegmentationUtility();
                Iterator pSet = processedSegmentSet.iterator();
                while(pSet.hasNext())
                {
                    Segment t = new Segment();
                
                    t.seg = (String)pSet.next();
                    System.out.println(t.seg);
                    t.value = segHelp.searchUsingScanner(t.seg);
                
                    finalProcessedSegmentSet.add(t);
                }
            
                Collections.sort(finalProcessedSegmentSet);
                Segment temp;
                int sizeN = finalProcessedSegmentSet.size();
                for(int i=0;i<sizeN;i++)
                {
                    temp = finalProcessedSegmentSet.get(i);
                    outputAppend.println(temp.seg);
                    processedSegs.put(temp.seg, temp.value);
                //System.out.println(temp.seg+"\t"+temp.value);
                
                }
                
                outputAppend.println("|");
                
                
            }    
            
        } catch (FileNotFoundException ex) {
            Logger.getLogger(SegmentationProcessing.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(SegmentationProcessing.class.getName()).log(Level.SEVERE, null, ex);
        }
        finally
        {
            outputAppend.close();
        }
        
    }
    
    
}


class Segment implements Comparable<Segment>
{
    String seg;
    int value;

    @Override
    public int compareTo(Segment o) {
        return (int)(o.value - this.value);
    }

}
