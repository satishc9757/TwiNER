/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package twiner;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author satishc
 */
 class Twiner {

    /**
     * @param args the command line arguments
     */
    
    private static Segmentation seg = null;
    private static JenaConnection jCon = null;
    //private static TwitterDataExtraction dte = null;
    private static TweetPreprocessing tPr = null;
    private static SegmentationProcessing sPR = null; 
    
    
    
   /* public static void main(String[] args) {
       
        
        //Comparison cpr = new Comparison();
        //cpr.setVisible(true);
        
        PosTagger pTag = new PosTagger();
        pTag.posTagging("batteri develop isro india electric vehicle");
        
      //sPR = new SegmentationProcessing();
      //sPR.segmentationOp();
        /* dte = new TwitterDataExtraction();
        dte.setup();
        dte.fetchAndDrawTweets();
        
        tPr = new TweetPreprocessing();
        tPr.preprocessop();
            
        seg = new Segmentation();
        seg.segmentationOp();*/
        
        
        
        //jCon = new JenaConnection();
        //boolean v = jCon.entityVerification("Modi");
        
       /*SegmentationUtility su = new  SegmentationUtility();
        String te = su.subSegments("hello how are u mate", 2, 5);
        System.out.println(te);
            /* DBpediaSpotlightClient db = new DBpediaSpotlightClient();
            
            try{
            db.dbPediaExec();
            }
            catch(Exception e)
            {
            System.out.println(e);
            }*/
        //}
        
    //}  
        
}
    
