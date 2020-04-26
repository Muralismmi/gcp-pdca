package com.helper;



import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.mortbay.log.Log;
import org.springframework.util.StringUtils;

import com.DAO.UserDAO;
import com.DAOImpl.UserDAOImpls;
import com.google.appengine.api.search.Cursor;
import com.google.appengine.api.search.Document;
import com.google.appengine.api.search.Field;
import com.google.appengine.api.search.Index;
import com.google.appengine.api.search.IndexSpec;
import com.google.appengine.api.search.PutException;
import com.google.appengine.api.search.Query;
import com.google.appengine.api.search.QueryOptions;
import com.google.appengine.api.search.Results;
import com.google.appengine.api.search.ScoredDocument;
import com.google.appengine.api.search.SearchException;
import com.google.appengine.api.search.SearchServiceConfig;
import com.google.appengine.api.search.SearchServiceFactory;
import com.google.appengine.api.search.SortExpression;
import com.google.appengine.api.search.SortOptions;
import com.google.appengine.api.search.StatusCode;


public class PartialTextSearch {
	private static Logger logger = Logger
			.getLogger(PartialTextSearch.class.getPackage().getName());    
 
	// method describe to fetch auto suggest based on querystring, limit, index ,Fields (Common Method for all auto suggest API fetch)
	public static List<HashMap<String,Object>> fetchAutoSuggestResults(String searchStr,String indexName,List<String> KeyList) throws Exception
	{
		Results<ScoredDocument> results=null;
		List<HashMap<String,Object>> resultList	=	new ArrayList<HashMap<String,Object>>();
		logger.info("Index:" +indexName);
		try
		{
			IndexSpec indexSpecObj=IndexSpec.newBuilder().setName(indexName).build();
			Index index=SearchServiceFactory.getSearchService().getIndex(indexSpecObj);
			QueryOptions queryOptionsObj=QueryOptions.newBuilder().setLimit(1000).build();
			logger.info("Query string In Search Index: "+searchStr);
			Query query=Query.newBuilder().setOptions(queryOptionsObj).build(searchStr);
			logger.info("Executed query :"+query);
			logger.info("index : "+index);
			results=index.search(query);
			logger.info("indexName : "+indexName);
			for(ScoredDocument documnet:results)
			{
				
				HashMap<String,Object> resultMap=new HashMap<String,Object>();
				for(String key:KeyList)
				{
					if(!(documnet.getFieldNames().contains(key)))
					{
						
						resultMap.put(key,"" );
					}
					else
					{
						
						resultMap.put(key, documnet.getOnlyField(key).getText());
					}
				}
				resultList.add(resultMap);
				
			}
			System.out.println("result Size::"+results.getNumberFound());
		}
		catch(Exception e)
		{
			logger.log(java.util.logging.Level.SEVERE, e.getMessage(), e);
			return resultList;
		}
		return resultList;
	}

    // method describe to save data into index (Common Method for all API search/Update)
     public static void saveorUpdateSearchIndex( List<HashMap<String,String>> content,String indexname,List<String> props,String idkey) 
     {
    	 if (content.size() != 0) {
 			for (HashMap<String,String> entrymap : content) {
 				Document.Builder builder = Document.newBuilder();
 				builder.setId(entrymap.get(idkey));
 				for(String key:props){
 					if(entrymap.get(key)!=null){
 						Field.Builder field = Field.newBuilder();
 						field.setName(key);
 						field.setText(entrymap.get(key));
 						builder.addField(field);
 						Field.Builder field1 = Field.newBuilder();
 						if(!key.equals("criteriaDetailsList") && !key.equals("relatedWCList") &&  !key.equals("deviationFieldSurvey") && !key.equals("deviationPartReturn") && !key.equals("deviationRecovery") && !key.equals("Annexes"))
 						{
 							field1.setName(key+"_1");
 							field1.setText(convertToPartialText(entrymap.get(key)));
 							builder.addField(field1);
 						}
 					}
 				}
 			
 				indexDocument(indexname, builder.build());  
 			}
 		}
     }
     
     
     //================== create full search doc using object ===============================

 	public static void createFullSearchDocument( List<HashMap<String,Object>> content,String indexname,List<String> props,String idkey,List<String> textFields,List<String> dateFields) 
 	{
 		Log.info("textFields List"+ textFields);
 		try {
 			if (content.size() != 0)
 	 		{
 		 		for (HashMap<String,Object> entrymap : content) 
 		 		{
 			 		Document.Builder builder = Document.newBuilder();
 			 		builder.setId(String.valueOf(entrymap.get(idkey)));
 			 		for(String key:props)
 			 		{
 				 		if(entrymap.get(key)!=null)
 				 		{
 				 			Log.info("fields : "+key);
 					 		Field.Builder field = Field.newBuilder();
 					 		field.setName(key);
 					 		
 					 		if(dateFields.contains(key))
 					 		{
 					 			field.setDate(new Date(Long.valueOf(String.valueOf(entrymap.get(key)))));
 					 		}
// 					 		else if(textFields.contains(key))
// 					 		{
// 					 			HashMap<String,String> value1 = (HashMap<String, String>) entrymap.get(key);
// 					 			field.setText(value1.get("value"));
// 					 		}
 					 		else
 					 		{
 					 			field.setText(String.valueOf(entrymap.get(key)));
 					 		}
 					
 					 		builder.addField(field);
 					
 					 		if(!(textFields.contains(key)) && !(dateFields.contains(key)))
 					 		{
 					 			Field.Builder field1 = Field.newBuilder();
	 							field1.setName(key+"_1");
	 							field1.setText(convertToPartialText(entrymap.get(key).toString()));
	 							builder.addField(field1);
 					 		}
 				 		}
 				 		else
 				 		{
 				 			Field.Builder field = Field.newBuilder();
 					 		field.setName(key);
 					 		field.setText("");
 					 		builder.addField(field);
 				 		}
 			 		}
 			 		
 			 		indexDocument(indexname, builder.build());  
 		 		}
 	 		}
 		}
 		catch(Exception e)
		 {
			 e.printStackTrace();
			 System.out.println("Error");
		 }
 		
 	}	
     
     
     public static String convertToPartialText(String name) {

 		String[] strarr = name.split(" ");
 		List<String> a = new ArrayList<String>();
 		for (int j = 0; j < strarr.length; j++) {
 			for (int i = 1; i <= strarr[j].length(); i++) {
 				for (int k = 0; k < strarr[j].length(); k++) {
 					if ((k + i) > strarr[j].length()) {
 						break;
 					}
 					if (a.contains(strarr[j].substring(k, k + i))) {

 					} else {
 						a.add(strarr[j].substring(k, k + i));
 					}
 				}
 				a.remove(" ");

 			}

 		}

 		String resp = StringUtils.collectionToDelimitedString(a, " ");
 		//logger.warning("the len is" + resp.length());
 		return resp;
 	}
     
     public static void indexDocument(String indexName, Document doc) {
 		IndexSpec indexSpec = IndexSpec.newBuilder().setName(indexName).build();
 		Index index = SearchServiceFactory.getSearchService().getIndex(
 				indexSpec);

 		try {
 			// Put the Document in the Index. If the document is already
 			// existing, it will be overwritten
 			index.put(doc);
 			logger.info("index saved");
 		} catch (PutException e) {
 			if (StatusCode.TRANSIENT_ERROR.equals(e.getOperationResult()
 					.getCode())) {
 				// retry putting the document
 			}
 		}
 	}
     
     public static List<String> searchDatabyField(String searchStr,String indexName,String fieldName) throws Exception
 	{
 		Results<ScoredDocument> results=null;
 		List<String> resultList	=	new ArrayList<String>();
 		try
 		{
 			IndexSpec indexSpecObj=IndexSpec.newBuilder().setName(indexName).build();
 			Index index=SearchServiceFactory.getSearchService().getIndex(indexSpecObj);
 			QueryOptions queryOptionsObj=QueryOptions.newBuilder().setFieldsToReturn(fieldName).build();
 			
 			logger.info("Query string In Search Index: "+searchStr);
 			String queryString = fieldName+"_1 = \""+searchStr+"\"";
 			Query query=Query.newBuilder().setOptions(queryOptionsObj).build(queryString);
 			logger.info("Executed query :"+query);
 			results=index.search(query);
 			for(ScoredDocument documnet:results)
 			{
 					if(!(documnet.getFieldNames().contains(fieldName)))
 					{
 					}
 					else
 					{
 						resultList.add(documnet.getOnlyField(fieldName).getText());
 					}
 			}
 			Long resultSize=results.getNumberFound();
 			System.out.println("result Size::"+resultSize);
 		}
 		catch(Exception e)
 		{
 			logger.log(java.util.logging.Level.SEVERE, e.getMessage(), e);StringWriter sWriter = new StringWriter();PrintWriter pWriter = new PrintWriter(sWriter);e.printStackTrace(pWriter); String strStackTrace = sWriter.toString();
 			return resultList;
 		}
 		return resultList;
 	}
  
    
    //=============== fetch the count from search index ====================================
    public static Long findIndexCount(String indexName,String searchStr,int retryCount) throws Exception
	{
		Long resultSize	=	0L;
		try
		{
			
			IndexSpec indexSpecObj=IndexSpec.newBuilder().setName(indexName).build();
			Index index=SearchServiceFactory.getSearchService(SearchServiceConfig.newBuilder().setDeadline(120.0).build()).getIndex(indexSpecObj);
			QueryOptions queryOptionsObj=QueryOptions.newBuilder().setReturningIdsOnly(true).setNumberFoundAccuracy(25000).build();
			Query query=Query.newBuilder().setOptions(queryOptionsObj).build(searchStr);
			logger.warning("query : "+query);
			resultSize=index.search(query).getNumberFound();
			logger.warning("result found Size : "+resultSize);
		}
		catch(Exception e)
		{
			logger.log(java.util.logging.Level.SEVERE, e.getMessage(), e);
			if(retryCount<6)
			{
				logger.warning("retryCount : "+retryCount);
				retryCount+=1;
				findIndexCount(indexName, searchStr, retryCount);
			}
			else
			{
				resultSize	=	findIndexCountApproximately(indexName, searchStr, retryCount);
				return resultSize;
			}
		}
		return resultSize;
	}
	
	
	
	public static Long findIndexCountApproximately(String indexName,String searchStr,int retryCount) throws Exception
	{
		Long resultSize	=	0L;
		try
		{
			IndexSpec indexSpecObj=IndexSpec.newBuilder().setName(indexName).build();
			Index index=SearchServiceFactory.getSearchService(SearchServiceConfig.newBuilder().setDeadline(120.0).build()).getIndex(indexSpecObj);
			QueryOptions queryOptionsObj=QueryOptions.newBuilder().setReturningIdsOnly(true).build();
			Query query=Query.newBuilder().setOptions(queryOptionsObj).build(searchStr);
			logger.warning("query : "+query);
			resultSize=index.search(query).getNumberFound();
			logger.warning("result found Size : "+resultSize);
		}
		catch(Exception e)
		{
			logger.log(java.util.logging.Level.SEVERE, e.getMessage(), e);
			if(retryCount<6)
			{
				logger.warning("retryCount : "+retryCount);
				retryCount+=1;
				findIndexCount(indexName, searchStr, retryCount);
			}
			else
			{
				return resultSize;
			}
		}
		return resultSize;
	}

	public static List<HashMap<String, Object>> fetchResultsByQueryString(String indexName, String searchStr) 
	{
		Results<ScoredDocument> results=null;
		List<HashMap<String,Object>> resultList	=	new ArrayList<HashMap<String,Object>>();
		IndexSpec indexSpecObj=IndexSpec.newBuilder().setName(indexName).build();
		Index index=SearchServiceFactory.getSearchService().getIndex(indexSpecObj);
		QueryOptions queryOptionsObj=QueryOptions.newBuilder().setNumberFoundAccuracy(25000).setLimit(1000).build();
		logger.info("Query string In Search Index: "+searchStr);
		Query query=Query.newBuilder().setOptions(queryOptionsObj).build(searchStr);
		logger.info("Executed query :"+query);
		results=index.search(query);
		for(ScoredDocument documnet:results)
		{
			HashMap<String,Object> resultMap=new HashMap<String,Object>();
			for(String str : documnet.getFieldNames())
			{
				resultMap.put(str, documnet.getOnlyField(str).getText());
				resultList.add(resultMap);
			}
		}
		return resultList;
	}

	
	/*public static HashMap<String, Object> searchDataByOffset(String indexName, String searchStr,int offset,int limit,String orderOptions,String orderColumn)  throws Exception
	{
		Results<ScoredDocument> results=null;
		//SearchIndexService searchService	=	new SearchIndexServiceImpls();
		UserDAO userDao	=	new UserDAOImpls();
		HashMap<String, Object> responseMap	=	new HashMap<String,Object>();
		List<HashMap<String,Object>> resultList	=	new ArrayList<HashMap<String,Object>>();
		IndexSpec indexSpecObj=IndexSpec.newBuilder().setName(indexName).build();
		Index index=SearchServiceFactory.getSearchService().getIndex(indexSpecObj);
		SortOptions sortOptions = SortOptions.newBuilder().addSortExpression(SortExpression.newBuilder().setExpression(orderColumn).setDirection(SortExpression.SortDirection.DESCENDING)).build();
		if(orderOptions.equalsIgnoreCase("asc"))
		{
	     sortOptions = SortOptions.newBuilder().addSortExpression(SortExpression.newBuilder().setExpression(orderColumn).setDirection(SortExpression.SortDirection.ASCENDING)).build();
		}
		QueryOptions queryOptionsObj=QueryOptions.newBuilder().setLimit(limit).setOffset(offset).setSortOptions(sortOptions).build();
		logger.info("Query string In Search Index: "+searchStr);
		Query query=Query.newBuilder().setOptions(queryOptionsObj).build(searchStr);
		logger.info("Executed query :"+query);
		results=index.search(query);
		Long resultSize=results.getNumberFound();
		for(ScoredDocument documnet:results)
		{
			HashMap<String,Object> resultMap=new HashMap<String,Object>();
			for(String str : documnet.getFieldNames())
			{
				if(!str.endsWith("_1"))
				{
					if(str!=null && !str.equals(""))
					{
						if(Constants.EMAIL_FIELDS.contains(str))
						{
							
							//HashMap<String,Object> userResponse	=	(HashMap<String, Object>) searchService.fetchValuesByQuery("userEmailUri = \""+documnet.getOnlyField(str).getText()+"\"","USER_INDEX",Arrays.asList("UserEmailId","userName"));
							HashMap<String,Object> userResponse	=	(HashMap<String, Object>) userDao.fetchUserObjByEmailUri(documnet.getOnlyField(str).getText());
							if(userResponse!=null)
							{
								resultMap.put(str+"_NAME",userResponse.get("userName"));
								resultMap.put(str,String.valueOf(userResponse.get("UserEmailId")));
								resultMap.put(str+"_URI",documnet.getOnlyField(str).getText());
							}
							
						}
						else if(Constants.DATE_FIELDS.contains(str))
						{
							//logger.info("str : "+str);
							if(documnet.getOnlyField(str).getDate()!=null)
							{
								resultMap.put(str, documnet.getOnlyField(str).getDate().getTime());
							}
						}
						else
						{
							resultMap.put(str, documnet.getOnlyField(str).getText());
						}
					}
					else
					{
						resultMap.put(str,"");
					}
				}
			}
			resultList.add(resultMap);
		}
		responseMap.put("data",resultList);
		//responseMap.put("recordsFiltered",resultSize);
		responseMap.put("recordsFiltered",findIndexCount(indexName, searchStr, 1));
		return responseMap;
	}*/
	
	public static void createSearchDocument( List<HashMap<String,String>> content,String indexname,List<String> props,String idkey) {
		
		if (content.size() != 0) {
			for (HashMap<String,String> entrymap : content) {
				Document.Builder builder = Document.newBuilder();
				builder.setId(entrymap.get(idkey));
				for(String key:props){
					if(entrymap.get(key)!=null){
						Field.Builder field = Field.newBuilder();
						field.setName(key);
						field.setText(entrymap.get(key));
						builder.addField(field);
						Field.Builder field1 = Field.newBuilder();
						field1.setName(key+"_1");
						field1.setText(convertToPartialText(entrymap.get(key)));
						builder.addField(field1);
					}
				}
			
				indexDocument(indexname, builder.build());  
			}
		}
	}
	
	// method describe to fetch results of list based on querystring, Start, end , sort option, Index name (Common Method for all search API fetch)
   /* public static HashMap<String, Object> fetchAdminSettingsResults(String searchindex,String searchString,String searchString1,String sort_field,String order,int limit,int offset,HashMap<String,String> filterDataQuery) 
    {
    	String loggedInUser = UserUtil.getCurrentUser();
        String indexName = searchindex;
		IndexSpec indexSpec = IndexSpec.newBuilder().setName(indexName).build();
		Index index = SearchServiceFactory.getSearchService().getIndex(indexSpec);
		List<Object> returnlist = new ArrayList<Object>();
		HashMap<String,Object> resultMap = new HashMap<String,Object>();
		List<String> param = new ArrayList<String>();
		searcn searchService = new SearchIndexServiceImpls();
		
		if(sort_field.isEmpty() || sort_field == null || sort_field.equalsIgnoreCase(""))
		{
			sort_field = "id" ;
		}
		logger.info(" INDEXNAME here "+ indexName);
		Map<String, Object> searchMap = new HashMap<String, Object>();
		UserDAO userDao	=	new UserDAOImpls();
		try {
				System.out.println("sort_field from here "+sort_field);
				String queryString =  searchString;
				logger.info( " queryString "+queryString);
				SortOptions sortOptions = SortOptions.newBuilder().addSortExpression(SortExpression.newBuilder().setExpression(sort_field).setDirection(SortExpression.SortDirection.DESCENDING)).build();
				if(order.equalsIgnoreCase("asc"))
				{
			     sortOptions = SortOptions.newBuilder().addSortExpression(SortExpression.newBuilder().setExpression(sort_field).setDirection(SortExpression.SortDirection.ASCENDING)).build();
				}
				QueryOptions options = QueryOptions.newBuilder().setOffset(offset).setSortOptions(sortOptions).setLimit(limit).setNumberFoundAccuracy(25000).build();
				Query query;
				if(!searchString1.trim().isEmpty())
				{
					queryString +=" OR "+searchString1;
				}
				logger.info("queryString with Global search Str "+queryString);
				if(!queryString.trim().isEmpty() && queryString != "")
				{
					queryString += " AND (isActive = true OR active = true) " ;
				}
				else
				{
					queryString += " (isActive = true OR active = true) " ;
				}
				query=Query.newBuilder().setOptions(options).build(queryString);
				System.out.println("Final query"+query);
				Results<ScoredDocument> results = index.search(query);
				Long resultSize=results.getNumberFound();
				for (ScoredDocument document : results)
				{
					HashMap<String,Object> object = new HashMap<String,Object>();
					for(String str : document.getFieldNames())
					{
						if(!str.endsWith("_1"))
						{
							if(Constants.EMAIL_FIELDS.contains(str))
							{
								//HashMap<String,Object> userResponse	=	(HashMap<String, Object>) searchService.fetchValuesByQuery("userEmailUri = \""+document.getOnlyField(str).getText()+"\"","USER_INDEX",Arrays.asList("UserEmailId","userName"));
								HashMap<String,Object> userResponse	=	(HashMap<String, Object>) userDao.fetchUserObjByEmailUri(document.getOnlyField(str).getText());
								if(userResponse!=null)
								{
									object.put(str+"_NAME",userResponse.get("userName"));
									object.put(str,String.valueOf(userResponse.get("UserEmailId")));
									object.put(str+"_URI",document.getOnlyField(str).getText());
								}
							}
							else if(Constants.DATE_FIELDS.contains(str))
							{
								logger.info("str : "+str);
								if(document.getOnlyField(str).getDate()!=null)
								{
								object.put(str, document.getOnlyField(str).getDate().getTime());
							}
							}
							else
							{
								object.put(str, document.getOnlyField(str).getText());
							}
						}
					}
					logger.info(" object "+ object);
					returnlist.add(object);
				}
				resultMap.put("data", returnlist);
				resultMap.put("recordsFiltered", resultSize);
				logger.info(" returnlist " +returnlist.size());
		} 
		catch (SearchException e)
		{
			resultMap.put("data", "");
			resultMap.put("recordsFiltered", 0);
			e.printStackTrace();
			if (StatusCode.TRANSIENT_ERROR.equals(e.getOperationResult()
					.getCode())) {
				// retry putting the document
			}
		}
		return resultMap;
    }*/
    
    public static	HashMap<String,Object> getReportData(Cursor cursor,String searchindex,String searchText,String returnfield,int limit,List<String> textTypeFields) throws JsonParseException, JsonMappingException, IOException 
	{
		String indexName = searchindex;
		HashMap<String,Object> finalMap=new HashMap<String,Object>();

		IndexSpec indexSpec = IndexSpec.newBuilder().setName(indexName).build();
		Index index = SearchServiceFactory.getSearchService().getIndex(indexSpec);
		List<HashMap<String,String>> returnlist = new ArrayList<HashMap<String,String>>();
		try 
		{
			String queryString =  searchText;
				QueryOptions options = QueryOptions.newBuilder()
					     .setLimit(limit)
					     .setCursor(cursor)
					     .setNumberFoundAccuracy(25000)
					     .build();
				Query query = Query.newBuilder()
					     .setOptions(options)
					     .build(queryString);
					 
				System.out.println("query from here 391"+query);
				Results<ScoredDocument> results = index.search(query);
			    cursor = results.getCursor();
				logger.warning("results.getNumberReturned() : "+results.getNumberReturned());
				logger.warning("results.getNumberFound() : "+results.getNumberFound());
				for (ScoredDocument document : results) 
				{
						HashMap<String,String> resultmap = new HashMap<String,String>();
						for(String key :document.getFieldNames())
						{
							if(!key.endsWith("_1"))
							{
								if(textTypeFields.contains(key))
								{
									if(document.getOnlyField(key).getText()!=null && !document.getOnlyField(key).getText().trim().isEmpty())
									{
										String content = document.getOnlyField(key).getText();
										if(content.startsWith("{"))
										{
											content = content.substring(1, content.length()-1);    
											resultmap.put(key, content.replaceFirst("value=", ""));
										}
									}
								}
								else if(document.getOnlyField(key).getType().name().equals("DATE"))
								{
									if(document.getOnlyField(key).getDate()!=null)
									{
										resultmap.put(key, String.valueOf(document.getOnlyField(key).getDate().getTime()));
									}
								}
								else
								{
									resultmap.put(key, document.getOnlyField(key).getText());
								}
							}
						}
						returnlist.add(resultmap);
				}
			logger.warning("Final result : "+String.valueOf(returnlist.size()));
			finalMap.put("result", returnlist);
			finalMap.put("cursor", cursor);
		} 
		catch (SearchException e)
		{
			e.printStackTrace();
			logger.info("Exception ocuured during getReportData : "+e.getStackTrace());
			logger.log(Level.SEVERE,e.getMessage());
		}
		return finalMap;
	}
}