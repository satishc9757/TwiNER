/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package twiner;

import edu.stanford.nlp.tagger.maxent.MaxentTagger;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

/**
 *
 * @author satishc
 */
public class PosTagger {
    
    
    public Map<String,String> posTagging(String input)
    {
        Map<String,String> posTags = new HashMap();
        try{ 
            // Initialize the tagger
            MaxentTagger tagger = new MaxentTagger("taggers/bidirectional-distsim-wsj-0-18.tagger");
            //PrintWriter output = new PrintWriter("","UTF-8");
            // The tagged string
            String taggedOutput = tagger.tagString(input);
        
            System.out.println("The input is : "+input);
            System.out.println("The tagged output is : "+taggedOutput);
            
            
            //Now scanning the output to store the string and its tag as key value pair
            Scanner sc = new Scanner(taggedOutput);
            while(sc.hasNext())
            {
                Scanner sc1 = new Scanner(sc.next());
                sc1.useDelimiter("/");
                
                while(sc1.hasNext())
                {
                    posTags.put(sc1.next(),sc1.next());
                }
                
            }
            
            Set<String> st = posTags.keySet();
            
            for(String key : st)
            {
              System.out.println(key+" : "+posTags.get(key));  
            
            }
        }
        catch(Exception e){
            System.out.println(e);
        }

        return posTags;
    }
    
}
