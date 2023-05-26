package com.mobileclient.handler;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.mobileclient.domain.Shetuan;
public class ShetuanListHandler extends DefaultHandler {
	private List<Shetuan> shetuanList = null;
	private Shetuan shetuan;
	private String tempString;
	@Override
	public void characters(char[] ch, int start, int length) throws SAXException {
		super.characters(ch, start, length);
		if (shetuan != null) { 
            String valueString = new String(ch, start, length); 
            if ("stUserName".equals(tempString)) 
            	shetuan.setStUserName(valueString); 
            else if ("password".equals(tempString)) 
            	shetuan.setPassword(valueString); 
            else if ("shetuanName".equals(tempString)) 
            	shetuan.setShetuanName(valueString); 
            else if ("shetuanPhoto".equals(tempString)) 
            	shetuan.setShetuanPhoto(valueString); 
            else if ("shetuanDesc".equals(tempString)) 
            	shetuan.setShetuanDesc(valueString); 
            else if ("bornDate".equals(tempString)) 
            	shetuan.setBornDate(Timestamp.valueOf(valueString));
            else if ("fuzeren".equals(tempString)) 
            	shetuan.setFuzeren(valueString); 
            else if ("telephone".equals(tempString)) 
            	shetuan.setTelephone(valueString); 
            else if ("shetuanMemo".equals(tempString)) 
            	shetuan.setShetuanMemo(valueString); 
        } 
	}

	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		super.endElement(uri, localName, qName);
		if("Shetuan".equals(localName)&&shetuan!=null){
			shetuanList.add(shetuan);
			shetuan = null; 
		}
		tempString = null;
	}

	@Override
	public void startDocument() throws SAXException {
		super.startDocument();
		shetuanList = new ArrayList<Shetuan>();
	}

	@Override
	public void startElement(String uri, String localName, String qName,Attributes attributes) throws SAXException {
		super.startElement(uri, localName, qName, attributes);
        if ("Shetuan".equals(localName)) {
            shetuan = new Shetuan(); 
        }
        tempString = localName; 
	}

	public List<Shetuan> getShetuanList() {
		return this.shetuanList;
	}
}
