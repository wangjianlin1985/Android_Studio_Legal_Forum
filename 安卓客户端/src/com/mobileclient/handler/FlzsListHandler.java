package com.mobileclient.handler;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.mobileclient.domain.Flzs;
public class FlzsListHandler extends DefaultHandler {
	private List<Flzs> flzsList = null;
	private Flzs flzs;
	private String tempString;
	@Override
	public void characters(char[] ch, int start, int length) throws SAXException {
		super.characters(ch, start, length);
		if (flzs != null) { 
            String valueString = new String(ch, start, length); 
            if ("zsId".equals(tempString)) 
            	flzs.setZsId(new Integer(valueString).intValue());
            else if ("title".equals(tempString)) 
            	flzs.setTitle(valueString); 
            else if ("zsPhoto".equals(tempString)) 
            	flzs.setZsPhoto(valueString); 
            else if ("zsDesc".equals(tempString)) 
            	flzs.setZsDesc(valueString); 
            else if ("author".equals(tempString)) 
            	flzs.setAuthor(valueString); 
            else if ("publish".equals(tempString)) 
            	flzs.setPublish(valueString); 
            else if ("publishDate".equals(tempString)) 
            	flzs.setPublishDate(Timestamp.valueOf(valueString));
            else if ("viewNum".equals(tempString)) 
            	flzs.setViewNum(new Integer(valueString).intValue());
            else if ("zsFile".equals(tempString)) 
            	flzs.setZsFile(valueString); 
        } 
	}

	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		super.endElement(uri, localName, qName);
		if("Flzs".equals(localName)&&flzs!=null){
			flzsList.add(flzs);
			flzs = null; 
		}
		tempString = null;
	}

	@Override
	public void startDocument() throws SAXException {
		super.startDocument();
		flzsList = new ArrayList<Flzs>();
	}

	@Override
	public void startElement(String uri, String localName, String qName,Attributes attributes) throws SAXException {
		super.startElement(uri, localName, qName, attributes);
        if ("Flzs".equals(localName)) {
            flzs = new Flzs(); 
        }
        tempString = localName; 
	}

	public List<Flzs> getFlzsList() {
		return this.flzsList;
	}
}
