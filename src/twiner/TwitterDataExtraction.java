/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package twiner;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

/**
 *
 * @author satishc
 */
 class TwitterDataExtraction {
    private static ConfigurationBuilder cb = new ConfigurationBuilder();
    private static Twitter twitterInstance;
    private static Query queryForTwitter;  
    public static String KEYWORD = "";
    private static final String CONSUMER_KEY = "h2QX7wtUgr4EXFLr0aC0DpcnY";
    private static final String CONSUMER_SECRET = "vwaQRYt3AFH01E0nNr4dghKHvqvPhmtwc5o99JruqMrVqr6HVZ";
    private static final String ACCESS_TOKEN = "321595934-K9RucDujQW22VTqx2eqSbpQea6i4PEzvuhy38TSh";
    private static final String ACCESS_TOKEN_SECRET = "EZqO9qCuwORFTUZNpb1OyEquqao308vfEbHZstyxbrcpt";
    
    /*private static final String CONSUMER_KEY = "zghSbep6X52k1LkkCUhOzhb60";
    private static final String CONSUMER_SECRET = "VPrGecb3Uw3xxGJttPunQCw2TFYL1LqHEAkMLpV5johBDGX8Nm";
    private static final String ACCESS_TOKEN = "2928942888-wzJnHrlVkfuYz4XAPMBcd8Ohj4a0BqnqODikUr5";
    private static final String ACCESS_TOKEN_SECRET = "1jY7NpYQg1Q8UNbHSaG77YzYbt54WhN2k5GOKd3Yp4Ntz";*/
    void setup(String key)
    {
        KEYWORD = key;
        cb.setOAuthConsumerKey(CONSUMER_KEY);
        cb.setOAuthConsumerSecret(CONSUMER_SECRET);
        cb.setOAuthAccessToken(ACCESS_TOKEN);
        cb.setOAuthAccessTokenSecret(ACCESS_TOKEN_SECRET);
        
        twitterInstance = new TwitterFactory(cb.build()).getInstance();
        
        queryForTwitter = new Query(KEYWORD);
        //queryForTwitter.setCount(10);
        
        
    }
    
    void fetchAndDrawTweets() 
    {
        
        
        PrintWriter writer = null;
        try {
            int tweetCount = 0;
            int numberOfTweets = 500;
            long lastID = Long.MAX_VALUE;
            // File tweetOutput = new File("/Users/satishc/Desktop/nerin.txt");
            writer = new PrintWriter("/Users/satishc/Desktop/nerin.txt", "UTF-8");
            List<Status> tweets = new ArrayList<Status>();
            while (tweets.size () < numberOfTweets) 
            {
                if (numberOfTweets - tweets.size() > 100)
                    queryForTwitter.setCount(100);
                else 
                    queryForTwitter.setCount(numberOfTweets - tweets.size());
            
                try
                {
                    QueryResult result = twitterInstance.search(queryForTwitter); 
                    tweets.addAll(result.getTweets());
                    
                    for (Status t: tweets) 
                        if(t.getId() < lastID) lastID = t.getId();
            
                }
            
                catch(TwitterException te)
                {
                    System.out.println("Could not connect : "+te);   
                }
                queryForTwitter.setMaxId(lastID-1);
            }
            for (int i = 0; i < tweets.size(); i++) 
            {
                Status st = (Status) tweets.get(i);
                //String user = st.getUser().getName();
                String msg = st.getText();
                writer.println(msg+" |");
                tweetCount++;
                //System.out.println(msg);
                
                
            }
            System.out.println("\nTotal tweets retrieved are "+tweetCount);
            
        } catch (FileNotFoundException ex) {
            Logger.getLogger(TwitterDataExtraction.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(TwitterDataExtraction.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            writer.close();
        }
}
    }

