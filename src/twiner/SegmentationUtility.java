/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package twiner;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author satishc
 */
public class SegmentationUtility {
    public static Map<String,Integer> wordCount;
 // File path where tweets after preprocessing are stored
    private static final String tweetsFilePath = "/Users/satishc/Desktop/Input_for_Segmentation.txt";
    
/*This method is used for calculating word count before starting with Segmentation operation */    
    public void calculateWordCount(Map<String,Double> pR)  
    {
        Scanner tweets = null;
        try {
            tweets = new Scanner(new File(tweetsFilePath));
           // wordCount = new HashMap<String,Integer>();
            String temp= "";
            Double currCount = 0.0;
            while(tweets.hasNext())
            {
               temp = tweets.next();
               
               if(!pR.containsKey(temp))
               {
                  // System.out.println(temp);
                   pR.put(temp,1.0);
               }
               else
               {
                  
                   currCount = pR.get(temp);
                   currCount++;
                   pR.put(temp,currCount);
                   //System.out.println(temp+" : "+wordCount.get(temp));
               }
            }
        } 
        
        catch (FileNotFoundException ex) {
            Logger.getLogger(SegmentationUtility.class.getName()).log(Level.SEVERE, null, ex);
        }
        finally
        {
            try
            {
                if (tweets != null)
                    tweets.close();
            }
            catch (Exception e)
            {
                System.err.println("Exception while closing scanner " + e.toString());
            }
        }
    }
    
    public void displayWords(Map<String,Double> pR)
    {
        Set<String> tweetWords = pR.keySet();
        PrintWriter writer = null;
        try {
            writer = new PrintWriter("/Users/satishc/Desktop/wordCountOutput.txt", "UTF-8");
            for(String key : tweetWords)
            {
                //System.out.println(key+" : "+pR.get(key));
                writer.println(key+" : "+pR.get(key));
            }
        
        }
        catch (FileNotFoundException ex) {
            System.err.println("Exception while  " + ex.toString());
        } 
        catch (UnsupportedEncodingException ex) {
            System.err.println("Exception while " + ex.toString());
        }
        finally
        {
            try
            {
                if (writer != null)
                    writer.close();
            }
            catch (Exception e)
            {
                System.err.println("Exception while closing writer " + e.toString());
            }
        }
        
    }
    //This method is used for finding count of different combination of words from tweets
    public int searchUsingScanner(String searchQuery) 
    {
        int count=0;
        searchQuery = searchQuery.trim();
        Scanner scanner = null;
        try
        {
            scanner = new Scanner(new File(tweetsFilePath));
            while (scanner.hasNextLine())
            {
                String line = scanner.nextLine();
                if (line.contains(searchQuery))
                {
                    count++;
                }
            }
        }
        catch (FileNotFoundException ex) {
            Logger.getLogger(SegmentationUtility.class.getName()).log(Level.SEVERE, null, ex);
        }        
        finally
        {
            try
            {
                if (scanner != null)
                    scanner.close();
            }
            catch (Exception e)
            {
                System.err.println("Exception while closing scanner " + e.toString());
            }
        }

        return count;
    }
    
    public int numberOfWords(String tweet)
    {
        int count = 0;
        Scanner t = new Scanner(tweet);
        while(t.hasNext())
        {
            count++;
            String temp = t.next();
        }
        
        return count;
    }

    public double calculateCS(String s1,Map pR) 
    {
       double num,den=0,PMI=0,cS=0;
       int n =  numberOfWords(s1);
       
       //Calculating numerator of PMI
       num = calculatePR(s1,pR);
               
       //Calculating denominator of PMI
       for(int i=1;i<=n;i++)
       {
           den = den + (calculatePR(s1.substring(0,i),pR) * calculatePR(s1.substring(i,n),pR));
       }    
       den = den/(n-1);
       
       //PMI = Math.log(num/den);
       PMI = num/den;
       
       cS = 1/(1 + Math.exp(-PMI));
       
       return cS;
    }
    
    public double calculatePR(String s1,Map pR)
    {
        double num =0;
        if(!pR.containsKey(s1))
       {
           num = searchUsingScanner(s1);
           pR.put(s1, num);
       }
       else
           num = (double)pR.get(s1);
        
        return num;
    }
    
    public String subSegments(String s, int i, int j)
    {
        String s1 = "";
        int count = 1;
        Scanner scs = new Scanner(s);
        while(i>count && scs.hasNext())
        {
            scs.next();
            count++;
        }
        
        while(count<j && scs.hasNext())
        {
            if(s1.equals(""))
                s1 = scs.next();
            else
                s1 = s1+" "+scs.next();
            count++;
        }    
        
        
        return s1;
    }
    
}
