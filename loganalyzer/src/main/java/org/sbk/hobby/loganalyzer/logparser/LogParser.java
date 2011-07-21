package org.sbk.hobby.loganalyzer.logparser;


import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.SequenceInputStream;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamReader;

import org.sbk.hobby.loganalyzer.model.LogEvent;
import org.sbk.hobby.loganalyzer.model.LogLocationInfo;

public class LogParser {

	private String logFileUrl;
	private InputStream inputStream;
	private List<LogEvent> logEventList;
	
	private InputStream getLogFileInputStream() {
		try {
			if (logFileUrl != null) {
				URL u = new URL(logFileUrl);
				return u.openStream();
			} else {
				return inputStream;
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}
	
	public void parse() throws Exception {
		InputStream in = getLogFileInputStream();
		
		// Embed the contents of the log file in <le>..</le> XML element
		ByteArrayInputStream bais1 = new ByteArrayInputStream("<le xmlns:log4j=\"http://jakarta.apache.org/log4j/\">".getBytes());
		ByteArrayInputStream bais2 = new ByteArrayInputStream("</le>".getBytes());		
		SequenceInputStream sis = new SequenceInputStream(new SequenceInputStream(bais1,in),bais2);
		
		XMLInputFactory factory = XMLInputFactory.newInstance();
		XMLStreamReader parser = factory.createXMLStreamReader(sis);
	
		logEventList = new LinkedList<LogEvent>();
		
		LogEvent logEvent = null;
		LogLocationInfo locationInfo = null;
		
		while (true) {
		    int event = parser.next();
		    if (event == XMLStreamConstants.END_DOCUMENT) {
		       parser.close();
		       break;
		    }
		    if (event == XMLStreamConstants.START_ELEMENT) {
		        if (parser.getLocalName().equals(LogEvent.XmlTag.EVENT.getKey())) {
		        	logEvent = new LogEvent();
		        	logEventList.add(logEvent);
		        	for(int i=0 ; i<parser.getAttributeCount() ;i++) {
		        		if (parser.getAttributeLocalName(i).equals(LogEvent.XmlTag.LOGGER.getKey())) {
		        			logEvent.setLogger(parser.getAttributeValue(i));
		        		} else if (parser.getAttributeLocalName(i).equals(LogEvent.XmlTag.TIME.getKey())) {
		        			logEvent.setTime(parser.getAttributeValue(i));
		        		} else if (parser.getAttributeLocalName(i).equals(LogEvent.XmlTag.TIMESTAMP.getKey())) {
		        			logEvent.setTimestamp(parser.getAttributeValue(i));
		        		} else if (parser.getAttributeLocalName(i).equals(LogEvent.XmlTag.LEVEL.getKey())) {
		        			logEvent.setLevel(parser.getAttributeValue(i));
		        		} else if (parser.getAttributeLocalName(i).equals(LogEvent.XmlTag.THREAD.getKey())) {
		        			logEvent.setThread(parser.getAttributeValue(i));
		        		}
		        	}
		        } else if (parser.getLocalName().equals(LogEvent.XmlTag.MESSAGE.getKey())) {
		        	logEvent.setMessage(parser.getElementText());
		        } else if (parser.getLocalName().equals(LogEvent.XmlTag.THROWABLE.getKey())) {
		        	logEvent.setThrowable(parser.getElementText());	
		        } else if (parser.getLocalName().equals(LogEvent.XmlTag.LOCATION_INFO.getKey())) {
		        	locationInfo = new LogLocationInfo();
		        	logEvent.setLocationInfo(locationInfo);
		        	for(int i=0 ; i<parser.getAttributeCount() ;i++) {
		        		if (parser.getAttributeLocalName(i).equals(LogEvent.XmlTag.CLASS.getKey())) {
		        			locationInfo.setClassName(parser.getAttributeValue(i));
		        		} else if (parser.getAttributeLocalName(i).equals(LogEvent.XmlTag.METHOD.getKey())) {
		        			locationInfo.setMethod(parser.getAttributeValue(i));
		        		} else if (parser.getAttributeLocalName(i).equals(LogEvent.XmlTag.FILE.getKey())) {
		        			locationInfo.setFile(parser.getAttributeValue(i));
		        		} else if (parser.getAttributeLocalName(i).equals(LogEvent.XmlTag.LINE.getKey())) {
		        			locationInfo.setLine(parser.getAttributeValue(i));
		        		}
		        	}
		        }		        
		    }
		}
	}
	
	/**
	 * @return the logFileUrl
	 */
	public String getLogFileUrl() {
		return logFileUrl;
	}
	/**
	 * @param logFileUrl the logFileUrl to set
	 */
	public void setLogFileUrl(String logFileUrl) {
		this.logFileUrl = logFileUrl;
	}
	/**
	 * @return the inputStream
	 */
	public InputStream getInputStream() {
		return inputStream;
	}
	/**
	 * @param inputStream the inputStream to set
	 */
	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}

	/**
	 * @return the logEventList
	 */
	public List<LogEvent> getLogEventList() {
		return logEventList;
	}
	/**
	 * @param logEventList the logEventList to set
	 */
	public void setLogEventList(List<LogEvent> logEventList) {
		this.logEventList = logEventList;
	}
		
}
