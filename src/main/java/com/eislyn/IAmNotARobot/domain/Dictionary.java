package com.eislyn.IAmNotARobot.domain;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.eislyn.IAmNotARobot.dataAccessAPI.HttpConnector;

/**
 * Get the response from HttpConnector class and map it into a list of PartOfSpeech objects,
 * when using, setWord() first before doing getResponseAsListOfClasses().
 * @author Eislyn
 * @since 16/10/2022
 */
public class Dictionary {
	private String word;
	
	/**
	 * Set the word before calling getResponseAsListOfClasses
	 * @param word Word to get response 
	 */
	public void setWord(String word) {
		this.word = word;
	}
	
	public String getWord() {
		return word;
	}
	
	/**
	 * Gets the response from HttpConnecter and maps the Json response into a list of PartOfSpeech objects.
	 * @return partOfSpeechList List of PartOfSpeech objects
	 */
	public List<PartOfSpeech> getResponseAsPartOfSpeechList() {
		if(word == null || word == "") {
			throw new IllegalArgumentException();
		}
		
		String urlStr = "https://api.dictionaryapi.dev/api/v2/entries/en/" + word;
		String response = HttpConnector.getResponse(urlStr);
		
		//partOfSpeechJsonArray that has partOfSpeech JsonObjects
		JSONArray partOfSpeechJsonArray = new JSONArray(response);
		
		List<PartOfSpeech> partOfSpeechList = new ArrayList<PartOfSpeech>();
		
		//Loop through partOfSpeech JsonArray
		for(int i=0; i<partOfSpeechJsonArray.length(); i++) {
			//One partOfSpeech JsonObject
			JSONObject partOfSpeechJsonObject = partOfSpeechJsonArray.getJSONObject(i);
			//One partOfSpeech JsonObject has a meaningsJsonArray
			JSONArray meaningsJsonArray = partOfSpeechJsonObject.getJSONArray("meanings");
			
			//Loop through meaningsJsonArray
			for(int j=0; j<meaningsJsonArray.length(); j++) {
				//One meaningJsonObject
				JSONObject meaningJsonObject = meaningsJsonArray.getJSONObject(j);
				
				//*Got partOfString data here*
				//One meaningJsonObject has one partOfSpeechString
				String partOfSpeechString = meaningJsonObject.getString("partOfSpeech");
				
				//Create a PartOfSpeech class
				PartOfSpeech partOfSpeechObject = new PartOfSpeech();
				//set part of speech of that one class
				partOfSpeechObject.setPartOfSpeech(partOfSpeechString);
				
				//One meaningJsonObject has one defintionsJsonArray
				JSONArray definitionsJsonArray = meaningJsonObject.getJSONArray("definitions");
				//Loop through definitionsJsonArray
				for(int k=0; k<definitionsJsonArray.length(); k++) {
					//One definitionJsonObject
					JSONObject definitionJsonObject = definitionsJsonArray.getJSONObject(k);
					
					//*Got definitionString here*
					//One definitionJsonObject has one definitionString
					String definitionString = definitionJsonObject.getString("definition");
					
					//Add the definitionString into partOfSpeech object definitionList
					partOfSpeechObject.addDefinitionList(definitionString);
				}
				
				//partOfSpeech object is constructed, add object to a list to store
				partOfSpeechList.add(partOfSpeechObject);
			}
		}
		//return the list
		return partOfSpeechList;
	}
}
