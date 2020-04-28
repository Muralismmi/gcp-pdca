package com.helper;

import com.entity.User;
import com.google.appengine.api.search.*;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SearchDocumentHelper {
    private static final Logger logger = Logger.getLogger(SearchDocumentHelper.class.getName());
	
/*public static void createFullSearchDocument( List<HashMap<String,Object>> content,String indexname,List<String> props,String idkey) {
		
		if (content.size() != 0) {
			for (HashMap<String,Object> entrymap : content) {
				Document.Builder builder = Document.newBuilder();
				builder.setId(String.valueOf(entrymap.get(idkey)));
				for(String key:props){
					if(entrymap.get(key)!=null){
						Field.Builder field = Field.newBuilder();
						field.setName(key);
						if(entrymap.get(key) instanceof Long)
							field.setDate(new Date(Long.valueOf(String.valueOf(entrymap.get(key)))));
						else if(key.equals("comments") || key.equals("demoteComments")){
							HashMap<String,String> val =  (HashMap<String, String>) entrymap.get(key);
							field.setText(val.get("value"));
							//System.out.println(val.get("value"));
						}
						else
						field.setText(String.valueOf(entrymap.get(key)));
						builder.addField(field);
						Field.Builder field1 = Field.newBuilder();
						field1.setName(key+"_1");
						field1.setText(convertToPartialText(entrymap.get(key).toString()));
						builder.addField(field1);
					}
				}
		//	logger.warning("started indexing the documnent "+indexname);
				indexDocument(indexname, builder.build());  
			}
		}

	}*/


    public static void createFullSearchDocument(List<HashMap<String, Object>> content, String indexname, List<String> props, String idkey, List<String> textFields) {
        if (content.size() != 0) {
            for (HashMap<String, Object> entrymap : content) {

                Document.Builder builder = Document.newBuilder();
                builder.setId(String.valueOf(entrymap.get(idkey)));
                for (String key : props) {

                    if (entrymap.get(key) != null) {
                        Field.Builder field = Field.newBuilder();
                        field.setName(key);

                        if (entrymap.get(key) instanceof Long) {
                            if (key.equals("id") || key.equals("plantId") || key.equals("pillarId")) {
                                field.setText(String.valueOf(entrymap.get(key)));
                            } else {
                                field.setDate(new Date(Long.valueOf(String.valueOf(entrymap.get(key)))));
                            }

                        } else if (textFields.contains(key)) {

                            HashMap<String, String> value1 = (HashMap<String, String>) entrymap.get(key);
                            System.out.println("test --- 4 " + value1.get("value"));
                            field.setText(value1.get("value"));
                        } else
                            field.setText(String.valueOf(entrymap.get(key)));

                        builder.addField(field);

                        if (!(textFields.contains(key)) && entrymap.get(key).toString().length() < 100) {
                            Field.Builder field1 = Field.newBuilder();
                            field1.setName(key + "_1");
                            field1.setText(convertToPartialText(entrymap.get(key).toString()));
                            builder.addField(field1);
                        }

                    }
                }
//			logger.warning("started indexing the documnent "+indexname);
                indexDocument(indexname, builder.build());
            }
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

    public static void createSearchAppDocument(List<HashMap<String, String>> content, String indexname, List<String> props, String idkey) {
        try {
            if (content.size() != 0) {
                for (HashMap<String, String> entrymap : content) {
                    Document.Builder builder = Document.newBuilder();
                    builder.setId(entrymap.get(idkey));
                    for (String key : props) {
                        if (entrymap.get(key) != null && entrymap.get(key) != "null") {
    	                    	/*if(key.equals("createdOn") || key.equals("updatedOn"))
    	                    	{
    	                    		Field.Builder field=Field.newBuilder();
    	    						field.setName(key);
    	                    		if(Long.parseLong(entrymap.get(key))!=0L )
    	    						{
    	    							Date dateField=new Date(Long.parseLong(entrymap.get(key)));
    	    							field.setDate(dateField);
    	    							builder.addField(field);
    	    						}
    	                    	}*/
    	                    	/*else
    	                    	{*/
                            Field.Builder field = Field.newBuilder();
                            field.setName(key);
                            field.setText(entrymap.get(key));
                            builder.addField(field);
                            Field.Builder field1 = Field.newBuilder();
                            field1.setName(key + "_1");
                            field1.setText(convertToPartialText(entrymap.get(key)));
                            builder.addField(field1);
                            //}

                        }
                    }
                    indexDocument(indexname, builder.build());
                }
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE, e.getMessage(), e);
        }
    }

    public static void createSearchDocument(List<HashMap<String, String>> content, String indexname, List<String> props, String idkey) {

        if (content.size() != 0) {
            for (HashMap<String, String> entrymap : content) {
                Document.Builder builder = Document.newBuilder();
                builder.setId(entrymap.get(idkey));
                for (String key : props) {
                    if (entrymap.get(key) != null) {
                        Field.Builder field = Field.newBuilder();
                        field.setName(key);
                        field.setText(entrymap.get(key));
                        builder.addField(field);
                        Field.Builder field1 = Field.newBuilder();
                        field1.setName(key + "_1");
                        field1.setText(convertToPartialText(entrymap.get(key)));
                        builder.addField(field1);
                    }
                }

                indexDocument(indexname, builder.build());
            }
        }

    }

    public static void indexDocument(String indexName, Document doc) {
        IndexSpec indexSpec = IndexSpec.newBuilder().setName(indexName).build();
        Index index = SearchServiceFactory.getSearchService().getIndex(
                indexSpec);

        try {
            // Put the Document in the Index. If the document is already
            // existing, it will be overwritten
            index.put(doc);
            logger.info("saved index");
        } catch (PutException e) {

            logger.info(e.getMessage());
            if (StatusCode.TRANSIENT_ERROR.equals(e.getOperationResult()
                    .getCode())) {
                // retry putting the document
            }
        }
    }


    public static void deleteDocument(String docId, String IndexName) {
        logger.warning("insid delete search index" + docId + "IndexName :" + IndexName);
        IndexSpec indexSpec = IndexSpec.newBuilder().setName(IndexName).build();
        Index index = SearchServiceFactory.getSearchService().getIndex(indexSpec);
        try {
            index.delete(docId);
        } catch (PutException e) {
            if (StatusCode.TRANSIENT_ERROR.equals(e.getOperationResult()
                    .getCode())) {
                // retry putting the document
            }
        }
        // Delete the Records from the Index
    }

    private static Index getIndex(String searchindex) {
        IndexSpec indexSpec = IndexSpec.newBuilder().setName(searchindex).build();
        Index index = SearchServiceFactory.getSearchService().getIndex(indexSpec);
        return index;
    }

    public void deletedocsFromSearchIndex(String searchindex) {
        try {
            // looping because getRange by default returns up to 100 documents at a time
            while (true) {
                List<String> docIds = new ArrayList<>();
                GetRequest request = GetRequest.newBuilder().setReturningIdsOnly(true).build();
                GetResponse<Document> response = getIndex(searchindex).getRange(request);
                if (response.getResults().isEmpty()) {
                    break;
                }
                for (Document doc : response) {
                    docIds.add(doc.getId());
                }
                getIndex(searchindex).delete(docIds);
            }
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
    }

    public static List<HashMap<String, Object>> searchData(String searchStr, String indexName, List<String> KeyList) throws Exception {
        Results<ScoredDocument> results = null;
        List<HashMap<String, Object>> resultList = new ArrayList<HashMap<String, Object>>();
        try {
            IndexSpec indexSpecObj = IndexSpec.newBuilder().setName(indexName).build();
            Index index = SearchServiceFactory.getSearchService().getIndex(indexSpecObj);
            QueryOptions queryOptionsObj = QueryOptions.newBuilder().setNumberFoundAccuracy(25000).setLimit(500).build();
            logger.info("Query string In Search Index: " + searchStr);
            Query query = Query.newBuilder().setOptions(queryOptionsObj).build(searchStr);
            logger.info("Executed query :" + query);
            results = index.search(query);
            for (ScoredDocument documnet : results) {
                HashMap<String, Object> resultMap = new HashMap<String, Object>();
                for (String key : KeyList) {
                    if (!(documnet.getFieldNames().contains(key))) {
                        resultMap.put(key, "");
                    } else {
                        resultMap.put(key, documnet.getOnlyField(key).getText());
                    }
                }
                resultList.add(resultMap);

            }
            Long resultSize = results.getNumberFound();
            System.out.println("result Size::" + resultSize);
        } catch (Exception e) {
            logger.log(java.util.logging.Level.SEVERE, e.getMessage(), e);
            StringWriter sWriter = new StringWriter();
            PrintWriter pWriter = new PrintWriter(sWriter);
            e.printStackTrace(pWriter);
            String strStackTrace = sWriter.toString();
            return resultList;
        }
        return resultList;
    }

    public static List<String> searchDatabyField(String searchStr, String indexName, String fieldName, User user) throws Exception {
        Results<ScoredDocument> results = null;
        List<String> resultList = new ArrayList<String>();
        try {
            IndexSpec indexSpecObj = IndexSpec.newBuilder().setName(indexName).build();
            Index index = SearchServiceFactory.getSearchService().getIndex(indexSpecObj);
            QueryOptions queryOptionsObj = QueryOptions.newBuilder().setFieldsToReturn(fieldName, "id").build();

            logger.info("Query string In Search Index: " + searchStr);
            String queryString = "";
            if (!searchStr.trim().equals("")) {
                queryString = fieldName + "_1 = \"" + searchStr + "\"";
                if (indexName.equals("PLANT")) {
                    queryString += " OR code_1 = \"" + searchStr + "\"";
                }
            }


            Query query = Query.newBuilder().setOptions(queryOptionsObj).build(queryString);
            logger.info("Executed query :" + query);
            results = index.search(query);
            for (ScoredDocument documnet : results) {
                //	HashMap<String,Object> resultMap=new HashMap<String,Object>();

                if (!(documnet.getFieldNames().contains(fieldName))) {
                    //resultMap.put(fieldName, "");
                    //resultList.add(resultMap);
                } else {
                    resultList.add(documnet.getOnlyField(fieldName).getText() + "&&&&" + documnet.getOnlyField("id").getText());
                    //resultMap.put(fieldName, documnet.getOnlyField(fieldName).getText());
                }
            }
            Long resultSize = results.getNumberFound();
            System.out.println("result Size::" + resultSize);
        } catch (Exception e) {
            logger.log(java.util.logging.Level.SEVERE, e.getMessage(), e);
            StringWriter sWriter = new StringWriter();
            PrintWriter pWriter = new PrintWriter(sWriter);
            e.printStackTrace(pWriter);
            String strStackTrace = sWriter.toString();
            return resultList;
        }
        return resultList;
    }

    public static List<String> getUserWithRoleforPlant(String rolename, String plantName) throws Exception {
        Results<ScoredDocument> results = null;
        List<String> resultList = new ArrayList<String>();
        try {
            IndexSpec indexSpecObj = IndexSpec.newBuilder().setName("USER").build();
            Index index = SearchServiceFactory.getSearchService().getIndex(indexSpecObj);
            QueryOptions queryOptionsObj = QueryOptions.newBuilder().setFieldsToReturn("userEmail").build();

            //	logger.info("Query string In Search Index: "+searchStr);
            String queryString = "roles = " + rolename + " AND plantName = " + plantName + " AND active = " + true;
			/*if(!searchStr.trim().equals("")) {
				queryString = fieldName+"_1 = \""+searchStr+"\"";
				if(indexName.equals("PLANT")) {
					queryString += " OR code_1 = \""+searchStr+"\"";
				}
			}*/


            Query query = Query.newBuilder().setOptions(queryOptionsObj).build(queryString);
            logger.info("Executed query :" + query);
            results = index.search(query);
            for (ScoredDocument documnet : results) {
                //	HashMap<String,Object> resultMap=new HashMap<String,Object>();

                if (!(documnet.getFieldNames().contains("userEmail"))) {
                    //resultMap.put(fieldName, "");
                    //resultList.add(resultMap);
                } else {
                    resultList.add(documnet.getOnlyField("userEmail").getText());
                    //resultMap.put(fieldName, documnet.getOnlyField(fieldName).getText());
                }
            }
            Long resultSize = results.getNumberFound();
            System.out.println("result Size::" + resultSize);
        } catch (Exception e) {
            logger.log(java.util.logging.Level.SEVERE, e.getMessage(), e);
            StringWriter sWriter = new StringWriter();
            PrintWriter pWriter = new PrintWriter(sWriter);
            e.printStackTrace(pWriter);
            String strStackTrace = sWriter.toString();
            return resultList;
        }
        return resultList;
    }

    public static List<String> searchDatabyFieldfromConfiguration(String searchStr, String indexName, String fieldName) throws Exception {
        Results<ScoredDocument> results = null;
        List<String> resultList = new ArrayList<String>();
        try {
            IndexSpec indexSpecObj = IndexSpec.newBuilder().setName(indexName).build();
            Index index = SearchServiceFactory.getSearchService().getIndex(indexSpecObj);
            QueryOptions queryOptionsObj = QueryOptions.newBuilder().setFieldsToReturn(fieldName, "id").build();

            logger.info("Query string In Search Index: " + searchStr);

            String queryString = "";
            if (!searchStr.trim().isEmpty())
                queryString = fieldName + "_1 = \"" + searchStr + "\"";


            Query query = Query.newBuilder().setOptions(queryOptionsObj).build(queryString);
            logger.info("Executed query :" + query);
            results = index.search(query);
            for (ScoredDocument documnet : results) {
                //	HashMap<String,Object> resultMap=new HashMap<String,Object>();

                for (String str : documnet.getFieldNames()) {
                    System.out.println(str);
                }
                if (!(documnet.getFieldNames().contains(fieldName))) {
                    //resultMap.put(fieldName, "");
                    //resultList.add(resultMap);
                } else {
                    resultList.add(documnet.getOnlyField(fieldName).getText() + "&&&&" + documnet.getOnlyField("id").getText());
                    //resultMap.put(fieldName, documnet.getOnlyField(fieldName).getText());
                }
            }
            Long resultSize = results.getNumberFound();
            System.out.println("result Size::" + resultSize);
        } catch (Exception e) {
            logger.log(java.util.logging.Level.SEVERE, e.getMessage(), e);
            StringWriter sWriter = new StringWriter();
            PrintWriter pWriter = new PrintWriter(sWriter);
            e.printStackTrace(pWriter);
            String strStackTrace = sWriter.toString();
            return resultList;
        }
        return resultList;
    }

    public static List<Object> searchDatabyFieldfromConfigurationwithQuery(String searchStr, String indexName, String fieldName, String configuration) throws Exception {
        Results<ScoredDocument> results = null;
        List<Object> resultList = new ArrayList<Object>();
        try {
            System.out.println("updated");
            IndexSpec indexSpecObj = IndexSpec.newBuilder().setName(indexName).build();
            Index index = SearchServiceFactory.getSearchService().getIndex(indexSpecObj);
            QueryOptions queryOptionsObj = null;
            if (indexName.equals("USER")) {
                queryOptionsObj = QueryOptions.newBuilder().setFieldsToReturn(fieldName, "id", "firstName", "lastName").build();
            } else {
                queryOptionsObj = QueryOptions.newBuilder().setFieldsToReturn(fieldName, "id").build();
            }


            logger.info("Query string In Search Index: " + searchStr);
            String queryString;
            if (searchStr != null && !searchStr.isEmpty()) {
                queryString = fieldName + "_1 = \"" + searchStr + "\"";
                queryString += " AND " + configuration + "";
            } else {
                queryString = configuration;
            }

            Query query = Query.newBuilder().setOptions(queryOptionsObj).build(queryString);
            logger.info("Executed query :" + query);
            results = index.search(query);
            for (ScoredDocument documnet : results) {
                //	HashMap<String,Object> resultMap=new HashMap<String,Object>();

                for (String str : documnet.getFieldNames()) {
                    System.out.println(str);
                }
                if (!(documnet.getFieldNames().contains(fieldName))) {
                    //resultMap.put(fieldName, "");
                    //resultList.add(resultMap);
                } else {
                    if (indexName.equals("USER")) {
                        HashMap<String, Object> resultMap = new HashMap<String, Object>();
                        resultMap.put("EMAIL", documnet.getOnlyField(fieldName).getText());
                        resultMap.put("ID", documnet.getOnlyField("id").getText());
                        resultMap.put("firstName", documnet.getOnlyField("firstName").getText());
                        resultMap.put("lastName", documnet.getOnlyField("lastName").getText());
                        resultList.add(resultMap);

                    } else {
                        resultList.add(documnet.getOnlyField(fieldName).getText() + "&&&&" + documnet.getOnlyField("id").getText());
                    }

                    //resultMap.put(fieldName, documnet.getOnlyField(fieldName).getText());
                }
            }
            Long resultSize = results.getNumberFound();
            System.out.println("result Size::" + resultSize);
        } catch (Exception e) {
            logger.log(java.util.logging.Level.SEVERE, e.getMessage(), e);
            StringWriter sWriter = new StringWriter();
            PrintWriter pWriter = new PrintWriter(sWriter);
            e.printStackTrace(pWriter);
            String strStackTrace = sWriter.toString();
            return resultList;
        }
        return resultList;
    }

    public static HashMap<String, Object> getListFromSearchIndex(String searchindex, String searchString, String searchString1, String sort_field, String is_ascending, int limit, int offset, String filterfield, String filterfieldvalue, boolean isPlantManagerOnly, User objUser, String masterType) throws JsonGenerationException, JsonMappingException, IOException {


        String indexName = searchindex;
        IndexSpec indexSpec = IndexSpec.newBuilder().setName(indexName).build();
        Index index = SearchServiceFactory.getSearchService().getIndex(indexSpec);
        List<Object> returnlist = new ArrayList<Object>();
        HashMap<String, Object> resultMap = new HashMap<String, Object>();

        if (sort_field.isEmpty() || sort_field == null || sort_field.equalsIgnoreCase("")) {
            sort_field = "id";
        }

        String direction = "DESCENDING";

        if (is_ascending.equalsIgnoreCase("true")) {
            direction = "ASCENDING";
        }

        Map<String, Object> searchMap = new HashMap<String, Object>();
        try {
            System.out.println("sort_field from here" + sort_field);
            String queryString = searchString;
            SortOptions sortOptions = SortOptions.newBuilder().addSortExpression(SortExpression.newBuilder().setExpression(sort_field).setDirection(SortExpression.SortDirection.DESCENDING)).build();
            if (direction.equalsIgnoreCase("ASCENDING")) {
                sortOptions = SortOptions.newBuilder().addSortExpression(SortExpression.newBuilder().setExpression(sort_field).setDirection(SortExpression.SortDirection.ASCENDING)).build();
            }

            QueryOptions options = QueryOptions.newBuilder().setOffset(offset).setSortOptions(sortOptions).setLimit(limit).setNumberFoundAccuracy(25000).build();
            System.out.println(filterfield + " = \"" + filterfieldvalue + "\" AND " + queryString);
            Query query;
            String stringforquery = "";

            if (!filterfield.trim().isEmpty() && !filterfieldvalue.trim().isEmpty()) {
                stringforquery = filterfield + " = \"" + filterfieldvalue + "\"";
            }
            if (!queryString.trim().isEmpty() && !stringforquery.trim().isEmpty()) {
                stringforquery += " AND (" + queryString + ")";
            }
            if (!queryString.trim().isEmpty() && stringforquery.trim().isEmpty()) {
                stringforquery = "(" + queryString + ")";
            }
				/*if(queryString.trim().isEmpty() && !stringforquery.trim().isEmpty()){
					stringforquery = queryString;
				}*/
            if (!searchString1.trim().isEmpty())
                stringforquery += " OR " + searchString1;

            if (searchindex.equals("USER") && isPlantManagerOnly) {
                stringforquery += " AND plantId =" + objUser.getPlantId();
            }
            if (searchindex.equals("CONFIGURATION")) {
                stringforquery += " AND type =" + masterType;
            }

            System.out.println(stringforquery);
            query = Query.newBuilder().setOptions(options).build(stringforquery);
            System.out.println(query.toString());

            Results<ScoredDocument> results = index.search(query);
            Long resultSize = results.getNumberFound();
            for (ScoredDocument document : results) {
                HashMap<String, Object> object = new HashMap<String, Object>();
                for (String str : document.getFieldNames()) {

                    if (!str.endsWith("_1")) {
                        System.out.println(str + "  " + document.getOnlyField(str).getType());
                        if (document.getOnlyField(str).getType().toString().equals("DATE")) {
                            object.put(str, document.getOnlyField(str).getDate());
                        } else
                            object.put(str, document.getOnlyField(str).getText());
                    }

                }

                returnlist.add(object);
            }
            logger.info("returnlist In search :" + new ObjectMapper().writeValueAsString(returnlist));
            resultMap.put("data", returnlist);
            resultMap.put("recordsFiltered", resultSize);

        } catch (SearchException e) {
            if (StatusCode.TRANSIENT_ERROR.equals(e.getOperationResult()
                    .getCode())) {
                // retry putting the document
            }
        }
        return resultMap;
    }

    public static HashMap<String, Object> searchData(String searchStr, int limit, String indexName, String orderOptions, String column, int offset) throws Exception {
        Results<ScoredDocument> results = null;
        HashMap<String, Object> resultMap = new HashMap<String, Object>();
        try {
            IndexSpec indexSpecObj = IndexSpec.newBuilder().setName(indexName).build();
            Index index = SearchServiceFactory.getSearchService().getIndex(indexSpecObj);
            SortOptions sortOptionsObj = SortOptions.newBuilder().addSortExpression(SortExpression.newBuilder().setExpression(column).setDirection(SortExpression.SortDirection.ASCENDING)).build();
            if (orderOptions.equals("desc")) {
                sortOptionsObj = SortOptions.newBuilder().addSortExpression(SortExpression.newBuilder().setExpression(column).setDirection(SortExpression.SortDirection.DESCENDING)).build();
            }
            //log.warning("sort options created");
            //log.warning("offset :"+offset);
            QueryOptions queryOptionsObj = QueryOptions.newBuilder().setSortOptions(sortOptionsObj).setLimit(limit).setNumberFoundAccuracy(25000).setOffset(offset).build();
            System.out.println("Query comes" + searchStr);
            logger.info("Query string : " + searchStr);
            Query query = Query.newBuilder().setOptions(queryOptionsObj).build(searchStr);
            logger.info("query in searchData :" + query);
            results = index.search(query);

            Long resultSize = results.getNumberFound();
            System.out.println("matched records size" + resultSize);
            resultMap.put("totalrecord", resultSize);
            resultMap.put("results", results);
        } catch (Exception e) {
            resultMap.put("totalrecord", 0L);
            return resultMap;
        }
        return resultMap;
    }


    public static HashMap<String, Object> searchData1(String searchStr, String indexName) throws Exception {
        Results<ScoredDocument> results = null;
        HashMap<String, Object> resultMap = new HashMap<String, Object>();
        try {
            IndexSpec indexSpecObj = IndexSpec.newBuilder().setName(indexName).build();
            Index index = SearchServiceFactory.getSearchService().getIndex(indexSpecObj);
            //SortOptions sortOptionsObj=SortOptions.newBuilder().addSortExpression(SortExpression.newBuilder().setDirection(SortExpression.SortDirection.ASCENDING)).build();
            //log.warning("sort options created");
            //log.warning("offset :"+offset);
            QueryOptions queryOptionsObj = QueryOptions.newBuilder().build();
            System.out.println("Query comes" + searchStr);
            logger.info("Query string : " + searchStr);
            Query query = Query.newBuilder().setOptions(queryOptionsObj).build(searchStr);
            logger.info("query in searchData :" + query);
            results = index.search(query);
            Long resultSize = results.getNumberFound();
            System.out.println("matched records size" + resultSize);
            resultMap.put("totalrecord", resultSize);
            resultMap.put("results", results);
        } catch (Exception e) {
            resultMap.put("totalrecord", 0L);
            return resultMap;
        }
        return resultMap;
    }


}

