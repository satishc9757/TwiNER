/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package twiner;

import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QueryFactory;
import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.rdf.model.Literal;
import com.hp.hpl.jena.sparql.engine.http.QueryEngineHTTP;
import static com.hp.hpl.jena.vocabulary.RDFS.Literal;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
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
 class JenaConnection {
    
    //public void verificationOp(Map<String,Double> resultOfSegmentation,Map<String,Double> cS,String tweet)
    public void verificationOp()        
    {
        PrintWriter writer = null,writer2=null,namedEntityAppend =null,dbpOutput=null;
        Map<String,Double> cS =null;
        String tweet = "";
        try {
            cS = Segmentation.pR;
            
            File input1 = new File("/Users/satishc/Desktop/ProcessedTweets.txt");
            Scanner sc = new Scanner(input1);
            sc.useDelimiter("\\|");
            
            
            /*tweet = sc.next();
            
            PosTagger pos = new PosTagger();
            Map<String,String> posTags = pos.posTagging(tweet);*/
            
            File output = new File("/Users/satishc/Desktop/named_entities.txt");
            writer = new PrintWriter(output, "UTF-8");
            writer.println("");
            writer.close();
            
            File output2 = new File("/Users/satishc/Desktop/spotlight_input.txt");
            writer2 = new PrintWriter(output2, "UTF-8");
            writer2.println("");
            writer2.close();
            
            File dbpediaOutput = new File("/Users/satishc/Desktop/spotlight_output.txt");
            dbpOutput = new PrintWriter(dbpediaOutput, "UTF-8");
            dbpOutput.println("");
            dbpOutput.close();
            
            /*File output1 = new File("/Users/satishc/Desktop/Segmentation_set.txt");
            writer1 = new PrintWriter(output1, "UTF-8");
            Set<String> cSValue = resultOfSegmentation.keySet();*/
            boolean value = false;
            
            File input = new File("/Users/satishc/Desktop/processedSegments.txt");
            
            namedEntityAppend = new PrintWriter(new FileOutputStream(output,true));
            
            Scanner scAll = new Scanner(input); //contains all processed segments of tweets 
            scAll.useDelimiter("\\|");
            Scanner sc1 = null;
            for(int i=0;i<2;i++)
            {    
               // tweet = sc.next();
                //namedEntityAppend.println(tweet+"\n");
                
                tweet = sc.next();
            
                PosTagger pos = new PosTagger();
                Map<String,String> posTags = pos.posTagging(tweet);
                
                
                sc1 = new Scanner(scAll.next());//conatins processed segments for single tweet
                sc1.useDelimiter("\n");
            
                /*for(String key : cSValue)
                {
                //System.out.println(key+" : "+pR.get(key));
                // writer23.println(key+" : "+pR.get(key));
                writer1.println(key);
                if(cS.get(key)!=null && cS.get(key)>=0.5)
                {
                    writer1.println(key);
                    value = entityVerification(key);
                    if(value)
                    {
                       writer.println(key);
                    }
                }
            }*/
                while(sc1.hasNext())
                {
                    String key = sc1.next();
               // writer1.println(key);
                    if(cS.get(key)!=null && cS.get(key)>=0.5 && posTags.containsKey(key) && posTags.get(key).equals("NN"))
                    {
                    //writer1.println(key);
                        value = entityVerification(key);
                        if(value)
                        {
                            namedEntityAppend.println(key);
                        }
                    }
            
                }
           // writer.close();
                namedEntityAppend.println("|");
                
            }
            
            
            
        } catch (FileNotFoundException ex) {
            Logger.getLogger(JenaConnection.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(JenaConnection.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            //writer.close();
            //writer1.close();
            //writer2.close();
            namedEntityAppend.close();
        }
        
        
        File outputDbs = new File("/Users/satishc/Desktop/spotlight_input.txt");
        PrintWriter appendDbsInput = null;
        File dbpediaOutput = new File("/Users/satishc/Desktop/spotlight_output.txt");
        PrintWriter appendDbpOutput = null;
        
            
        try{  
            
        File input1 = new File("/Users/satishc/Desktop/ProcessedTweets.txt");
            Scanner sc = new Scanner(input1); //to get single tweet to write for spotlight output 
            sc.useDelimiter("\\|");
            
           
           
        File inputNamedEntities = new File("/Users/satishc/Desktop/named_entities.txt");
        Scanner scNamedE = new Scanner(inputNamedEntities);
        scNamedE.useDelimiter("\\|");
        
        PrintWriter writerTemp = new PrintWriter("/Users/satishc/Desktop/afterAbsMatch.txt", "UTF-8");
        writerTemp.println("");//to erase previous data
        writerTemp.close();
        
        for(int j=0;j<2;j++)
        {
            //appendDbs = new PrintWriter(new FileOutputStream(outputDbs,true));
            appendDbsInput = new PrintWriter(outputDbs,"UTF-8");
            appendDbpOutput = new PrintWriter(new FileOutputStream(dbpediaOutput,true));//spotlight output
            tweet = sc.next();
            
           
            
            
            abstractMatchingInit(tweet,scNamedE.next()); //Calling this method to match abstract with the context of tweet
            DBpediaSpotlightClient db = new DBpediaSpotlightClient();
            
            
           //if(j!=0)
             //  appendDbs.println("\n");
               
             
            appendDbpOutput.println(tweet);
            appendDbpOutput.close();
           // PrintWriter writer11 = new PrintWriter("/Users/satishc/Desktop/spotlight_output.txt" ,"UTF-8");
            appendDbsInput.println(tweet);//spotlight input
            appendDbsInput.close();
            
            db.dbPediaExec();
            
            
        }
        }
        catch(Exception e)
            {
                System.out.println(e);
            }
            
    }    
    
    public boolean entityVerification(String seg)
    {
        seg = addUnderScore(seg);
        System.out.println(seg);
        String queryString=        
        "PREFIX dcterms:<http://purl.org/dc/terms/>\n" +
        "PREFIX rdfs:<http://www.w3.org/2000/01/rdf-schema#>\n" + 
        "PREFIX rdf:<http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n"+        
        "SELECT DISTINCT ?s ?label ?type ?l WHERE {\n" +
        "            ?s rdfs:label ?label . \n" +
        "            FILTER (lang(?label) = 'en'). \n" +
        "            ?label bif:contains \""+seg+"\" . \n" +
        "            ?s dcterms:subject ?sub . \n" +
        "            ?s rdf:type ?type . \n"+
        "            ?type rdfs:label ?l .\n"+
        "            FILTER (lang(?l) = 'en') \n"+        
        "}";

// now creating query object
        //Query query = QueryFactory.create(queryString);
// initializing queryExecution factory with remote service.
// **this actually was the main problem I couldn't figure out.**
        QueryEngineHTTP qexec = new QueryEngineHTTP("http://dbpedia.org/sparql", queryString);
        //QueryExecution qexec = QueryExecutionFactory.sparqlService("http://dbpedia.org/sparql", query);

//after it goes standard query execution and result processing which can
// be found in almost any Jena/SPARQL tutorial.
        try {
            ResultSet results = qexec.execSelect();
            for (; results.hasNext();) {
            QuerySolution soln = results.nextSolution();
            Literal sub = soln.getLiteral("label");
            Literal type = soln.getLiteral("l");
            //System.out.println(sub);
            //System.out.println(type);
            String t = type.toString();
                if(t.contains("person") || t.contains("location") || t.contains("organization") || t.contains("company") || t.contains("place"))
                {
                    return true;
                }
                
            }
        }
        catch(Exception e){
            System.out.println("Exception in JenaConnection : "+e);
        }
        finally {
            qexec.close();
        }   
        
       return false; 
    }
   
    //Abstract matching
    
    //public void abstractMatchingInit(String tweet)
    public void abstractMatchingInit(String tweet,String input1)
    {
        PrintWriter writer1 =null;
        try {
            //File input1 = new File("/Users/satishc/Desktop/named_entities.txt");
            
            
            writer1 = new PrintWriter(new FileOutputStream(new File("/Users/satishc/Desktop/afterAbsMatch.txt"),true));
            Scanner sca = new Scanner(input1);
            sca.useDelimiter("\\n");
            String result ="";
            
            writer1.println(tweet+"\n");
            while(sca.hasNext())
            {
                String named_entity = sca.next();
                //System.out.println("In Abs-----"+named_entity);
                result =  abstractMatching(named_entity,tweet);
                if(!result.equals("null"))
                {
                    System.out.println(result);
                    writer1.println(result);
                }
                
            }
            writer1.println("|");
            
        } catch (FileNotFoundException ex) {
            Logger.getLogger(JenaConnection.class.getName()).log(Level.SEVERE, null, ex);
        } /*catch (UnsupportedEncodingException ex) {
            Logger.getLogger(JenaConnection.class.getName()).log(Level.SEVERE, null, ex);
        }*/
        finally
        {
           writer1.close();
        }
    
    }
    
    public String abstractMatching(String entity,String tweet)
    {
        String ogEntity = entity;
        entity = addUnderScore(entity);
        ArrayList<AbstractCount> absCheckCount = new  ArrayList<>();
       
        
        String queryString =
        /*"PREFIX  rdfs:  <http://www.w3.org/2000/01/rdf-schema#>\n" +
        "PREFIX   dbo:  <http://dbpedia.org/ontology/>\n" +
        //"PREFIX   bif:  <bif:>\n" +
        "PREFIX rdf:<http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n" +
        "SELECT DISTINCT ?itemLabel ?item ?itemDescription ?l WHERE {\n"+
        "       ?item       rdfs:label    ?itemLabel . \n" +
        "       ?itemLabel  bif:contains  \""+entity+"\" . \n" +
        "       ?item       dbo:abstract  ?itemDescription . \n" +
        "       FILTER (lang(?itemDescription) = 'en'). \n" +
        "       FILTER (lang(?itemLabel) = 'en'). \n" +
        "       ?item rdf:type ?type . \n" +
        "       ?type rdfs:label ?l . \n" +
        "       FILTER (lang(?l) = 'en') \n" +
        "}" ;
        //"ORDER BY ?itemLabel";*/
        "PREFIX   dbo:  <http://dbpedia.org/ontology/>\n"+        
        "PREFIX rdfs:<http://www.w3.org/2000/01/rdf-schema#>\n" + 
        "PREFIX rdf:<http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n"+        
        "SELECT DISTINCT ?s ?label ?type ?l ?abs WHERE {\n" +
        "            ?s rdfs:label ?label . \n" +
        "            FILTER (lang(?label) = 'en'). \n" +
        "            ?label bif:contains \""+entity+"\" . \n" +
        "            ?s dbo:abstract ?abs . \n" +
        "            FILTER (lang(?abs) = 'en'). \n" +        
        "            ?s rdf:type ?type . \n"+
        "            ?type rdfs:label ?l .\n"+
        "            FILTER (lang(?l) = 'en') \n"+        
        "}";         
        QueryEngineHTTP qexec = new QueryEngineHTTP("http://dbpedia.org/sparql", queryString);
        
        try {
            ResultSet results = qexec.execSelect();
            //System.out.println("After exec abs--------");
            for (; results.hasNext();) {
            QuerySolution soln = results.nextSolution();
            Literal sub = soln.getLiteral("label");
            Literal type = soln.getLiteral("l");
            //System.out.println(sub);
            //System.out.println(type);
            String t = type.toString();
            String abs="",title = sub.toString();
            int absCount =0;
                if(t.contains("person") || t.contains("location") || t.contains("organization") || t.contains("company") || t.contains("place"))
                {
                    Literal abst = soln.getLiteral("abs");
                    abs = abst.toString();
                    
                    absCount = checkTweetContents(abs,tweet,ogEntity); 
                    AbstractCount tempObj = new AbstractCount();
                    tempObj.labelName = title;
                    tempObj.type = t;
                   // System.out.println("Lables in Abs "+tempObj.labelName);
                    tempObj.count = absCount;
                    absCheckCount.add(tempObj);
                }
                
            }
        }
        catch(Exception e){
            System.out.println("Exception in JenaConnection abs : "+e);
        }
        finally {
            qexec.close();
        }   
        int max = 0;
        AbstractCount t = null;
        for(AbstractCount a : absCheckCount)
        {
             if(a.count>max)
             {
                  max = a.count;
                  t = a;
             }
        }
         if(t!=null)
            return "Entity :- "+t.labelName+"\nType :- "+t.type+"\n";
         return "null";
    }
    

    private String addUnderScore(String seg) {
        
        Scanner scu = new Scanner(seg);
        String res = scu.next();
        while(scu.hasNext())
        {
            res = res+"_"+scu.next();
        }
        
        return res;
    }

    private int checkTweetContents(String abs, String tweet,String entity) {
        int count = 0;
        abs = abs.toLowerCase();
        String temp="";
        Scanner sct  = new Scanner(tweet);
        while(sct.hasNext())
        {
            temp = sct.next();
            if(!temp.equals(entity) && abs.contains(temp))
                count++;
        }
        
        
        return count;
    }
    
}


class AbstractCount 
{
   String labelName;
   String type;
   int count;

}