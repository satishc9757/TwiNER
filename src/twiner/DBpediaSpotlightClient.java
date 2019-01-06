/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package twiner;

/**
 *
 * @author satishc
 */
import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.methods.GetMethod;
import org.dbpedia.spotlight.exceptions.AnnotationException;
import org.dbpedia.spotlight.model.DBpediaResource;
import org.dbpedia.spotlight.model.Text;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;



 class DBpediaSpotlightClient extends AnnotationClient {

	//private final static String API_URL = "http://jodaiber.dyndns.org:2222/";
    //private final static String API_URL = "http://spotlight.dbpedia.org/";
    private final static String API_URL = "http://spotlight.sztaki.hu:2222/";
	private static final double CONFIDENCE = 0.4;
	private static final int SUPPORT = 0;
        //private static List<Response> res = new ArrayList<Response>();
	
	//public List<DBpediaResource> extract(Text text) throws AnnotationException {
        public List<Response> extract(Text text) throws AnnotationException {
            
		String spotlightResponse;
		try {
			GetMethod getMethod = new GetMethod(API_URL + "rest/annotate/?" 
					+"confidence=" + CONFIDENCE
					+ "&support=" + SUPPORT
					+ "&text=" + URLEncoder.encode(text.text(), "utf-8"));
                                       // +"confidence=" + CONFIDENCE);
			getMethod.addRequestHeader(new Header("Accept", "application/json"));

			spotlightResponse = request(getMethod);
		} catch (UnsupportedEncodingException e) {
			throw new AnnotationException("Could not encode text.", e);
		}

		assert spotlightResponse != null;

		JSONObject resultJSON = null;
		JSONArray entities = null;

		try {
                       // PrintWriter writer = new PrintWriter("/Users/satishc/Desktop/spot_json.json", "UTF-8");
                        System.out.println("Opening spot_json.json file... ");
			resultJSON = new JSONObject(spotlightResponse);
                        
                        
			System.out.println("writing to json file.. ");
                        entities = resultJSON.getJSONArray("Resources");
                        
                        
                        System.out.println("writing to json file.. ");
		} catch (JSONException e) {
			throw new AnnotationException("Received invalid response from DBpedia Spotlight API.:-"+e);
		}
                catch(Exception te){
                             System.out.println("Cannot find file : "+te);   
                }

		//LinkedList<DBpediaResource> resources = new LinkedList<DBpediaResource>();
                List<Response> res = new ArrayList<Response>();
		for(int i = 0; i < entities.length(); i++) {
			try {
                                
				JSONObject entity = entities.getJSONObject(i);
                                System.out.println(entity.toString());
				//resources.add(
				//		new DBpediaResource(entity.getString("@URI"),
				//				Integer.parseInt(entity.getString("@support"))));
                                res.add(new Response(entity.getString("@URI"),entity.getString("@types")));

			} catch (JSONException e) {
                LOG.error("JSON exception "+e);
            }
                        

		}


		//return resources;
                return res;
	}
        public void dbPediaExec() throws Exception {
          
          
        DBpediaSpotlightClient c = new DBpediaSpotlightClient ();

//        File input = new File("/home/pablo/eval/manual/AnnotationText.txt");
//        File output = new File("/home/pablo/eval/manual/systems/Spotlight.list");

        //File input = new File("/home/pablo/eval/cucerzan/cucerzan.txt");
        //File output = new File("/home/pablo/eval/cucerzan/systems/cucerzan-Spotlight.set");

//        File input = new File("/home/pablo/eval/wikify/gold/WikifyAllInOne.txt");
//        File output = new File("/home/pablo/eval/wikify/systems/Spotlight.list");

        File input = new File("/Users/satishc/Desktop/spotlight_input.txt");
        File output = new File("/Users/satishc/Desktop/spotlight_output.txt");
        

        c.evaluate(input, output);
        //evaluateOutputStrings();
        
        
        

//        SpotlightClient c = new SpotlightClient(api_key);
//        List<DBpediaResource> response = c.extract(new Text(text));
//        PrintWriter out = new PrintWriter(manualEvalDir+"AnnotationText-Spotlight.txt.set");
//        System.out.println(response);

    }
        
    
        
        



}

class Response {

    private  String namedEntity;
    private  String type;
    
    Response(String ne,String t)
    {
        namedEntity = ne;
        type = t;
    }

    public void parseStrings()
    {
        if(namedEntity!=null && !namedEntity.equals(""))
        {
            String ne="";
            Scanner neScan = new Scanner(namedEntity);
            neScan.useDelimiter("/");
            while(neScan.hasNext())
            {
               ne = neScan.next();
            }
            namedEntity = ne;
        }
        if(type!=null && !type.equals(""))
        {
            String t="";
            Scanner neScan = new Scanner(type);
            neScan.useDelimiter(",");
            while(neScan.hasNext())
            {
               t = neScan.next();
               if(t.substring(0,6).equals("Schema"))
               {
                   type = t.substring(7);
                   break;
               }
                 
            }
            
        }
    }
    
    public String getNamedEntity()
    {
       return namedEntity;
    }
    
    public String getType()
    {
       return type;
    }
    
}

