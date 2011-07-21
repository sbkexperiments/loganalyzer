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
import org.testng.annotations.Test;


public class LogParserTest {
	
	//@Test
	public void test() throws Exception {
		URL u = new URL("file:///Users/shyamkulkarni/Delego_5_Sandbox/v2_5_3_patches/Log/ECD/MT/DelegoMT.2011-03-10.xml");
		//URL u = new URL("file:///Users/shyamkulkarni/tmp/junk5.xml");
		InputStream in = u.openStream();
		
		ByteArrayInputStream bais1 = new ByteArrayInputStream("<le xmlns:log4j=\"http://jakarta.apache.org/log4j/\">".getBytes());
		ByteArrayInputStream bais2 = new ByteArrayInputStream("</le>".getBytes());
		
		SequenceInputStream sis = new SequenceInputStream(new SequenceInputStream(bais1,in),bais2);
		
		XMLInputFactory factory = XMLInputFactory.newInstance();
		XMLStreamReader parser = factory.createXMLStreamReader(sis);
	
		List<LogEvent> logEventList = new LinkedList<LogEvent>();
		
		LogEvent logEvent = null;
		LogLocationInfo locationInfo = null;
		
		while (true) {
		    int event = parser.next();
		    if (event == XMLStreamConstants.END_DOCUMENT) {
		       parser.close();
		       break;
		    }
		    if (event == XMLStreamConstants.START_ELEMENT) {
		    	/*String attr = "";
		    	for(int i=0 ; i<parser.getAttributeCount() ;i++) {
		    		if (i !=0 ) attr += ",";
		    		attr += parser.getAttributeLocalName(i) + "=" + parser.getAttributeValue(i) ;
		    	}
		        System.out.println(parser.getLocalName() + "-" + attr);*/
		        if (parser.getLocalName().equals("event")) {
		        	logEvent = new LogEvent();
		        	logEventList.add(logEvent);
		        	for(int i=0 ; i<parser.getAttributeCount() ;i++) {
		        		if (parser.getAttributeLocalName(i).equals("logger")) {
		        			logEvent.setLogger(parser.getAttributeValue(i));
		        		} else if (parser.getAttributeLocalName(i).equals("time")) {
		        			logEvent.setTime(parser.getAttributeValue(i));
		        		} else if (parser.getAttributeLocalName(i).equals("timestamp")) {
		        			logEvent.setTimestamp(parser.getAttributeValue(i));
		        		} else if (parser.getAttributeLocalName(i).equals("level")) {
		        			logEvent.setLevel(parser.getAttributeValue(i));
		        		} else if (parser.getAttributeLocalName(i).equals("thread")) {
		        			logEvent.setThread(parser.getAttributeValue(i));
		        		}
		        	}
		        } else if (parser.getLocalName().equals("message")) {
		        	logEvent.setMessage(parser.getElementText());
		        } else if (parser.getLocalName().equals("locationInfo")) {
		        	locationInfo = new LogLocationInfo();
		        	logEvent.setLocationInfo(locationInfo);
		        	for(int i=0 ; i<parser.getAttributeCount() ;i++) {
		        		if (parser.getAttributeLocalName(i).equals("class")) {
		        			locationInfo.setClassName(parser.getAttributeValue(i));
		        		} else if (parser.getAttributeLocalName(i).equals("method")) {
		        			locationInfo.setMethod(parser.getAttributeValue(i));
		        		} else if (parser.getAttributeLocalName(i).equals("file")) {
		        			locationInfo.setFile(parser.getAttributeValue(i));
		        		} else if (parser.getAttributeLocalName(i).equals("line")) {
		        			locationInfo.setLine(parser.getAttributeValue(i));
		        		}
		        	}
		        }		        
		    }
		}
		
		for(LogEvent le: logEventList) {
			printLogEvent(le);
		}

	}
	
	public void printLogEvent(LogEvent logEvent) {
		LogLocationInfo locationInfo = logEvent.getLocationInfo();
		System.out.println("<LogEvent>");
		System.out.println("\t logger=" + logEvent.getLogger());
		System.out.println("\t time=" + logEvent.getTime());
		System.out.println("\t timestamp=" + logEvent.getTimestamp());
		System.out.println("\t level=" + logEvent.getLevel());
		System.out.println("\t thread=" + logEvent.getThread());
		System.out.println("\t<Message>");
		System.out.println("\t\t" + logEvent.getMessage());
		System.out.println("\t</Message>");
		System.out.println("\t<LocationInfo>");
		System.out.println("\t\t class=" + locationInfo.getClassName());
		System.out.println("\t\t method=" + locationInfo.getMethod());
		System.out.println("\t\t file=" + locationInfo.getFile());
		System.out.println("\t\t line=" + locationInfo.getLine());
		System.out.println("\t</LocationInfo>");
		System.out.println("</LogEvent>");
	}

	//@Test
	public void testParse() {
		//String logFileUrl = "file:///Users/shyamkulkarni/Delego_5_Sandbox/v2_5_3_patches/Log/ECD/MT/DelegoMT.2011-03-10.xml";
		String logFileUrl = "file:///Users/shyamkulkarni/Documents/Delego/Customers/Coleman/DelegoSrv.2010-07-28.xml";
		LogParser lp = new LogParser();
		lp.setLogFileUrl(logFileUrl);
		try {
			lp.parse();
		} catch (Exception ex) {
			//TODO fail();
		}
		
		for(LogEvent le: lp.getLogEventList()) {
			printLogEvent(le);
		}
	}
}
