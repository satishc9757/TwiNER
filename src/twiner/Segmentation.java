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
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author satishc
 */
 class Segmentation {
    
    public static Map<String,Double> pR = null; 
    private SegmentationUtility segHelp = null;
    public void segmentationOp()
    {
        try {
            
           // Map<String,Double> pR = new HashMap<String,Double>();
            pR = new HashMap<String,Double>();
            List<String> segmentationSet = null;
            Map<String,Double> resultOfSegmentation = null;
            JenaConnection jCon = null;
            segHelp = new SegmentationUtility();
            segHelp.calculateWordCount(pR);
            segHelp.displayWords(pR);
            
            
            File input = new File("/Users/satishc/Desktop/ProcessedTweets.txt");
            File output = new File("/Users/satishc/Desktop/Segmentation_set.txt");
            PrintWriter writerOver = new PrintWriter(output, "UTF-8");
            writerOver.println("");
            writerOver.close();
            
            PrintWriter writerAppend = new PrintWriter(new FileOutputStream(output,true));
            
            Scanner sc = new Scanner(input);
            sc.useDelimiter("\\|");
            
            for(int i=0;i<5;i++)
            {
                String tweet = sc.next();
                //System.out.println("Tweets from segmentation -- "+tweet);

                //segmentationSet = segmentationAlgo(sc.next(),5,5,pR);
                resultOfSegmentation = segmentationAlgo(tweet,5,5,pR);
            
                Set<String> cSValue = resultOfSegmentation.keySet();
                Iterator segSets = cSValue.iterator();
                while(segSets.hasNext())  
                {
                    writerAppend.append((String)segSets.next()+"\n");
                }
                writerAppend.append("|\n");
            
                 //should close before calling segmentationOp()
            
                
      
           
            //jCon = new JenaConnection();
            //jCon.verificationOp(resultOfSegmentation,pR,tweet);
            }    
            writerAppend.close();
            SegmentationProcessing sPR = new SegmentationProcessing();
            sPR.segmentationOp();
            
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Segmentation.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(Segmentation.class.getName()).log(Level.SEVERE, null, ex);
        } 
    }
   
    //This method performs the Segmentaion of tweet and returns the Segmentation set 
    private  Map<String,Double> segmentationAlgo(String tweet, int u, int e,Map<String,Double> pR)
    {
        Map<String,Double> cS = new HashMap<String,Double>();
       // Map<String,Double> pR = new HashMap<String,Double>();
        
        System.out.println(tweet);
        
        segHelp = new SegmentationUtility();
        
        int l = segHelp.numberOfWords(tweet); // Counting no. of words in tweet
        
        //Declaration and initialization of 2 dimensional arraylist for seements 
        List<ArrayList<String>> seg = new ArrayList<ArrayList<String>>();
        for(int i=0;i<l;i++)
        {
            seg.add(new ArrayList<String>());
        }
        
        
        ArrayList<String> temp1,temp2 =null;
        for(int i=1;i<=l;i++)
        {
            //String s1 = tweet.substring(0,i);
            String s1 = segHelp.subSegments(tweet, 1, i+1);
            double cSTemp = 0;
            if(i<=u)
            {
                //do not split s1
                if(!cS.containsKey(s1) || cS.get(s1)==null)
               {
                  // System.out.println(temp);
                   cSTemp = segHelp.calculateCS(s1,pR);
                   cS.put(s1,cSTemp);
                   
                   //System.out.println(cSTemp);
               
               }
               //Add s1 to Si as a possible segmentation of s1;
               ArrayList<String> cSI = seg.get(i-1);
               cSI.add(s1);
               
               
            }
            /*System.out.print("--Before--Contents of S"+i+" = ");
            for(String sf : seg.get(i-1))
                System.out.print(sf+" ");
             System.out.println("");*/
             
             
             
               //try different possible ways to segment s1
                for(int j=1;j<=i-1;j++)
                {
                    if(i-j<=u)
                    {
                        String s11="",s12="";
                        //s11 = s1.substring(0,j);
                        s11 = segHelp.subSegments(s1, 1, j+1);
                       // s12 = s1.substring(j);
                        s12 = segHelp.subSegments(s1, j+1, s1.length()+1);
                        
                        if(!cS.containsKey(s12) || cS.get(s12)==null)
                        {
                            
                            cSTemp = segHelp.calculateCS(s12,pR);
                            
                            cS.put(s12,cSTemp);
                        }
                        
                        temp1 = (ArrayList<String>)seg.get(j-1);
                        //Iterator<String> iter = temp1.iterator();
                        //while(iter.hasNext())
                        for(String sji : temp1)
                        {
                            //String sj = iter.next();
                            String sj = sji;
                            String s = sj +" "+ s12;
                            temp2 = (ArrayList<String>)seg.get(i-1);
                            temp2.add(s);
                           
                            //Calculate C(Sj) if it is not already calculated 
                            if(!cS.containsKey(sj)  || cS.get(sj)==null)
                            {
                                // System.out.println(temp);
                                
                                cSTemp = segHelp.calculateCS(sj,pR);
                                cS.put(sj,cSTemp);
               
                            }
                            
                            
                            //C(s12) is already calculated above
                            //We add C(Sj) and C(s12) to get C(s)
                            
                            Double cST = cS.get(s12) + cS.get(sj);
                            cS.put(s,cST);
                           
                        }
                    }
                  
                    
                    //Sorting Segmentation set S[i]
                    temp1 = (ArrayList<String>)seg.get(i-1);
                    for (int k = 0; k < temp1.size() - 1; k++) {
 
			for (int m = 1; m < (temp1.size() - k); m++) {
                            String sm1 = temp1.get(m - 1);
                            String sm2 = temp1.get(m);
                            String temp;
                            double d1 = cS.get(sm1);
                            double d2 = cS.get(sm2);
                            if (d1 < d2) {
					temp = temp1.get(m - 1);
					temp1.add(m - 1,temp1.get(m));
					temp1.add(m,temp);
				}
			}
                    
                    }
            
            
            
                }
                /*System.out.print("--After--Contents of S"+i+" = ");
                for(String sf : seg.get(i-1))
                System.out.print(sf+" ");
                System.out.println("");*/
    
                
        }
        
        
        
        try {
            
            File output23 = new File("/Users/satishc/Desktop/CS_value.txt");
            PrintWriter writer23 = new PrintWriter(output23, "UTF-8");
            Set<String> cSValue = cS.keySet();
            
            for(String key : cSValue)
            {
                //System.out.println(key+" : "+pR.get(key));
                writer23.println(key+" : "+cS.get(key));
            }
            
            writer23.close();
        } 
        catch (FileNotFoundException ex) {
            Logger.getLogger(Segmentation.class.getName()).log(Level.SEVERE, null, ex);
        } 
        catch (UnsupportedEncodingException ex) {
            Logger.getLogger(Segmentation.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        //Return S blonging to S[l] with highest value of cS
        /*temp1 = (ArrayList<String>)seg.get(l-1);
        return temp1;*/
        
        return cS;
    }

}    