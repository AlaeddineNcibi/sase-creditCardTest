package edu.lhc.krr.sase.history;

import java.util.ArrayList;
import java.util.Collection;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;

public class MatchedRunDescriptor {
	private Multimap<Integer, String> eventIDToAttributeName;;
	private int nbAttributes;
	private int eventNumber;

	//private ArrayList<String> attributesList;
	private ArrayList<Integer> listOfIntegers;

	public MatchedRunDescriptor() {
		super();
		this.eventIDToAttributeName = ArrayListMultimap.create();
		this.nbAttributes = 0;
		this.eventNumber = 0;
	//	this.attributesList = new ArrayList<String>();
		this.listOfIntegers = new ArrayList<>();
	}

	public void addEventAttribute(int eventID ,String attributeName){
		this.eventIDToAttributeName.put(eventID, attributeName);
	}
	public Collection<String> getEventAttribute(int eventID ){
		return this.eventIDToAttributeName.get(eventID);
	}
	
	
	public int getNbAttributes() {
		return nbAttributes;
	}

	public void setNbAttributes(int nbAttributes) {
		this.nbAttributes = nbAttributes;
	}

	public ArrayList<Integer> getListOfIntegers() {
		return listOfIntegers;
	}

	public void setListOfIntegers(ArrayList<Integer> listOfIntegers) {
		this.listOfIntegers = listOfIntegers;
	}

	public void add(int value) {
		this.listOfIntegers.add(value);
	}

	public int getEventNumber() {
		return eventNumber;
	}

	public void setEventNumber(int filter) {
		this.eventNumber = filter;
	}

//	public ArrayList<String> getAttributesList() {
//		return attributesList;
//	}
//
//	public void setAttributesList(ArrayList<String> attributesList) {
//		this.attributesList = attributesList;
//	}
//
//	public void addAttributesList(String attributesName) {
//		this.attributesList.add(attributesName);
//		this.nbAttributes++;
//	}

}
