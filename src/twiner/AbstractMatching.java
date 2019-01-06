/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package twiner;

import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.rdf.model.Literal;
import com.hp.hpl.jena.sparql.engine.http.QueryEngineHTTP;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author satishc
 */
class AbstractMatching {
    
    
    public void abstractMatchingInit(String tweet)
    {
        PrintWriter writer1 =null;
        try {
            File input1 = new File("/Users/satishc/Desktop/named_entities.txt");
            writer1 = new PrintWriter("/Users/satishc/Desktop/afterAbsMatch.txt", "UTF-8");
            Scanner sca = new Scanner(input1);
            sca.useDelimiter("\n");
            String result ="";
            while(sca.hasNext())
            {
                //abstractMatching(sca.next(),tweet);
                //System.out.println(result);
                //writer1.println(result);
            }
            
            
        } catch (FileNotFoundException ex) {
            Logger.getLogger(JenaConnection.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(JenaConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
        finally
        {
           writer1.close();
        }
    
    }
    
    public  void abstractMatchingMethod(String entity)
    {
       // String entity = addUnderScore(entity);
       // ArrayList<AbstractCount> absCheckCount = new  ArrayList<>();
       
        
        String queryString=
    /*    "PREFIX  rdfs:  <http://www.w3.org/2000/01/rdf-schema#>\n" +
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
        //"PREFIX dcterms:<http://purl.org/dc/terms/>\n" +
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
        PrintWriter writer1 = null;
        try {
            writer1 = new PrintWriter("/Users/satishc/Desktop/afterAbsMatch.txt", "UTF-8");
            ResultSet results = qexec.execSelect();
            System.out.println("After exec abs--------");
            for (; results.hasNext();) {
             QuerySolution soln = results.nextSolution();
             Literal sub = soln.getLiteral("label");
             Literal type = soln.getLiteral("l");
             Literal abs = soln.getLiteral("abs");
             writer1.println("Label : "+sub.toString()+" type : "+type.toString()+" abstract : "+abs.toString());
            }
        }
        catch(Exception e){
            System.out.println("Exception in JenaConnection abs : "+e);
        }
        finally {
            qexec.close();
            writer1.close();
        }
           
    }
}
