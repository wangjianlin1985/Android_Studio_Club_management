package com.mobileclient.handler;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.mobileclient.domain.Huodong;
public class HuodongListHandler extends DefaultHandler {
	private List<Huodong> huodongList = null;
	private Huodong huodong;
	private String tempString;
	@Override
	public void characters(char[] ch, int start, int length) throws SAXException {
		super.characters(ch, start, length);
		if (huodong != null) { 
            String valueString = new String(ch, start, length); 
            if ("huodongId".equals(tempString)) 
            	huodong.setHuodongId(new Integer(valueString).intValue());
            else if ("huodongName".equals(tempString)) 
            	huodong.setHuodongName(valueString); 
            else if ("huodongDesc".equals(tempString)) 
            	huodong.setHuodongDesc(valueString); 
            else if ("huodongTime".equals(tempString)) 
            	huodong.setHuodongTime(Timestamp.valueOf(valueString));
            else if ("shetuanObj".equals(tempString)) 
            	huodong.setShetuanObj(valueString); 
            else if ("huodongMemo".equals(tempString)) 
            	huodong.setHuodongMemo(valueString); 
        } 
	}

	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		super.endElement(uri, localName, qName);
		if("Huodong".equals(localName)&&huodong!=null){
			huodongList.add(huodong);
			huodong = null; 
		}
		tempString = null;
	}

	@Override
	public void startDocument() throws SAXException {
		super.startDocument();
		huodongList = new ArrayList<Huodong>();
	}

	@Override
	public void startElement(String uri, String localName, String qName,Attributes attributes) throws SAXException {
		super.startElement(uri, localName, qName, attributes);
        if ("Huodong".equals(localName)) {
            huodong = new Huodong(); 
        }
        tempString = localName; 
	}

	public List<Huodong> getHuodongList() {
		return this.huodongList;
	}
}
