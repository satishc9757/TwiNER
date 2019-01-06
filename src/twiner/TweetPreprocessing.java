/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package twiner;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import static twiner.Stopwords.isStemmedStopword;
import static twiner.Stopwords.isStopword;
import static twiner.Stopwords.removeStemmedStopWords;
import static twiner.Stopwords.removeStopWords;
import static twiner.Stopwords.stemString;

/**
 *
 * @author akshay
 */
 class TweetPreprocessing 
{
    private final static String URL_REGEX = "((www\\.[\\s]+)|(https?://[^\\s]+))"; 
 private final static String CONSECUTIVE_CHARS = "([a-z])\\1{1,}"; 
 private final static String STARTS_WITH_NUMBER = "[1-9]\\s*(\\w+)"; 
 
 public void preprocessop()
 {
        try {
            File input = new File("/Users/satishc/Desktop/nerin.txt");
            File output1 = new File("/Users/satishc/Desktop/ProcessedTweets.txt");
            PrintWriter writer1 = new PrintWriter(output1, "UTF-8");
            File output2 = new File("/Users/satishc/Desktop/Input_for_Segmentation.txt");
            PrintWriter writer2 = new PrintWriter(output2, "UTF-8");
            // output=preprocess(input);
            Scanner sc = new Scanner(input);
            sc.useDelimiter("\\|");
            while(sc.hasNext())
            {
                String t = sc.next();
                System.out.println(t);
                preprocess(t,writer1,writer2);
            }
            writer1.close();
            writer2.close();
        } 
        catch (FileNotFoundException ex) {
            Logger.getLogger(TweetPreprocessing.class.getName()).log(Level.SEVERE, null, ex);
        } 
        catch (IOException ex) {
            Logger.getLogger(TweetPreprocessing.class.getName()).log(Level.SEVERE, null, ex);
        }
 
 }


 private void preprocess(String tweet,PrintWriter writer1,PrintWriter writer2) throws FileNotFoundException, IOException
 { 
  
    //Scanner sc;
     boolean value;
     String temp="";
     
    //String tweet;
    
        /*try {
            Scanner sc = new Scanner(input);
            //sc.useDelimiter(" ");
            while (sc.hasNextLine()) 
            {
              tweet = sc.nextLine();*/
     
    //Coverting into lowecase 
    tweet = tweet.toLowerCase(); 
              
    // Remove urls 
    tweet = tweet.replaceAll(URL_REGEX, ""); 
 
    // Remove @username 
    tweet = tweet.replaceAll("@([^\\s]+)", ""); 
 
  // Remove character repetition 
  //tweet = tweet.replaceAll(CONSECUTIVE_CHARS, "$1"); 
 
    // Remove words starting with a number 
    tweet = tweet.replaceAll(STARTS_WITH_NUMBER, ""); 
 
    // Remove hashtags
    tweet = tweet.replaceAll("#([^\\s]+)","");
  
    //Remove punctuations
    tweet = tweet.replaceAll("[^a-zA-Z ]", "");
  
  //String string = null;
  
    Scanner sn= new Scanner(tweet);
  
    Stopwords st = new Stopwords();
    Stemmer sm = new Stemmer();
    String processedTweet = "";
    while(sn.hasNext())
    {
        temp = sn.next();
        value = st.isStopword(temp);
    
        if(value==false)
        {
            temp = sm.stem(temp);
            processedTweet = processedTweet + temp + " ";
            //writer1.print(temp+" ");
            //writer2.print(temp+" ");
        }
     
  //System.out.print(tweet+" ");
  
    }
    SegmentationUtility su = new SegmentationUtility();
    int noOfWords = su.numberOfWords(processedTweet);
    if(processedTweet.contains(TwitterDataExtraction.KEYWORD) && noOfWords>3)
    {
        writer1.println(processedTweet+" |");
        writer2.println(processedTweet);
    }
    
  //System.out.print(tweet+" ");
  
  /*st.isStemmedStopword(tweet);
  st.removeStopWords(tweet);
  st.removeStemmedStopWords(tweet);
  st.stemString(tweet);
  //stemStringSet(tweet);*/
             
           // }
    //writer.close();
       // } 
     
        
 } 

    
}
