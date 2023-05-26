package com.mobileclient.handler;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.mobileclient.domain.Shenqing;
public class ShenqingListHandler extends DefaultHandler {
	private List<Shenqing> shenqingList = null;
	private Shenqing shenqing;
	private String tempString;
	@Override
	public void characters(char[] ch, int start, int length) throws SAXException {
		super.characters(ch, start, length);
		if (shenqing != null) { 
            String valueString = new String(ch, start, length); 
            if ("shenqingId".equals(tempString)) 
            	shenqing.setShenqingId(new Integer(valueString).intValue());
            else if ("shentuanObj".equals(tempString)) 
            	shenqing.setShentuanObj(valueString); 
            else if ("name".equals(tempString)) 
            	shenqing.setName(valueString); 
            else if ("xuehao".equals(tempString)) 
            	shenqing.setXuehao(valueString); 
            else if ("zysj".equals(tempString)) 
            	shenqing.setZysj(valueString); 
            else if ("rhyy".equals(tempString)) 
            	shenqing.setRhyy(valueString); 
            else if ("userObj".equals(tempString)) 
            	shenqing.setUserObj(valueString); 
            else if ("sqTime".equals(tempString)) 
            	shenqing.setSqTime(valueString); 
            else if ("shenHeState".equals(tempString)) 
            	shenqing.setShenHeState(valueString); 
            else if ("shenHeResult".equals(tempString)) 
            	shenqing.setShenHeResult(valueString); 
        } 
	}

	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		super.endElement(uri, localName, qName);
		if("Shenqing".equals(localName)&&shenqing!=null){
			shenqingList.add(shenqing);
			shenqing = null; 
		}
		tempString = null;
	}

	@Override
	public void startDocument() throws SAXException {
		super.startDocument();
		shenqingList = new ArrayList<Shenqing>();
	}

	@Override
	public void startElement(String uri, String localName, String qName,Attributes attributes) throws SAXException {
		super.startElement(uri, localName, qName, attributes);
        if ("Shenqing".equals(localName)) {
            shenqing = new Shenqing(); 
        }
        tempString = localName; 
	}

	public List<Shenqing> getShenqingList() {
		return this.shenqingList;
	}
}
