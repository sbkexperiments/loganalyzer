package org.sbk.hobby.loganalyzer.util;


import java.util.LinkedList;
import java.util.List;

import org.sbk.hobby.loganalyzer.model.LogEvent;
import org.sbk.hobby.loganalyzer.model.LogLocationInfo;

public class TestData {
	
	public static List<LogEvent> getTestLogEventBeanList() {
		List<LogEvent> logEventList = new LinkedList<LogEvent>();
		
		LogEvent le1 = new LogEvent();
		le1.setLogger("Delego.DelegoMT");
		le1.setTime("Thu, Mar 10, 2011 16:59:58 312 EST");
		le1.setTimestamp("1299794398312");
		le1.setLevel("INFO");
		le1.setThread("Thread-53");
		le1.setMessage("Message received on Thu Mar 10 16:59:58 EST 2011 from localhost/127.0.0.1:4783");
		LogLocationInfo lli1 = new LogLocationInfo();
		lli1.setClassName("Delego.DelegoMT");
		lli1.setMethod("run");
		lli1.setFile("DelegoMT.java");
		lli1.setLine("867");
		le1.setLocationInfo(lli1);
		
		logEventList.add(le1);

		LogEvent le2 = new LogEvent();
		le2.setLogger("Delego.DelegoMT");
		le2.setTime("Thu, Mar 10, 2011 16:59:59 031 EST");
		le2.setTimestamp("1299794399031");
		le2.setLevel("DEBUG");
		le2.setThread("Thread-53");
		le2.setMessage("DelegoDoc Request Logged to Audit Log.");
		LogLocationInfo lli2 = new LogLocationInfo();
		lli2.setClassName("Delego.DelegoMT");
		lli2.setMethod("logRequest");
		lli2.setFile("DelegoMT.java");
		lli2.setLine("787");
		le2.setLocationInfo(lli2);
		
		logEventList.add(le2);

		LogEvent le3 = new LogEvent();
		le3.setLogger("ClearingHouse.ClearingHouse");
		le3.setTime("Thu, Mar 10, 2011 16:59:59 078 EST");
		le3.setTimestamp("1299794399078");
		le3.setLevel("ERROR");
		le3.setThread("Thread-53");
		le3.setMessage("Channel instantiated Delego.Channel.NullChannel");
		LogLocationInfo lli3 = new LogLocationInfo();
		lli3.setClassName("ClearingHouse.ClearingHouse");
		lli3.setMethod("init()");
		lli3.setFile("ClearingHouse.java");
		lli3.setLine("904");
		le3.setLocationInfo(lli3);
		
		logEventList.add(le3);

		LogEvent le4 = new LogEvent();
		le4.setLogger("ClearingHouse.ClearingHouse");
		le4.setTime("Thu, Mar 10, 2011 16:59:59 078 EST");
		le4.setTimestamp("1299794399078");
		le4.setLevel("TRACE");
		le4.setThread("Thread-53");
		le4.setMessage("Channel instantiated Delego.Channel.NullChannel");
		LogLocationInfo lli4 = new LogLocationInfo();
		lli4.setClassName("ClearingHouse.ClearingHouse");
		lli4.setMethod("init()");
		lli4.setFile("ClearingHouse.java");
		lli4.setLine("904");
		le4.setLocationInfo(lli4);
		
		logEventList.add(le4);

		LogEvent le5 = new LogEvent();
		le5.setLogger("ClearingHouse.ClearingHouse");
		le5.setTime("Thu, Mar 10, 2011 16:59:59 078 EST");
		le5.setTimestamp("1299794399078");
		le5.setLevel("WARN");
		le5.setThread("Thread-53");
		le5.setMessage("Channel instantiated Delego.Channel.NullChannel");
		LogLocationInfo lli5 = new LogLocationInfo();
		lli5.setClassName("ClearingHouse.ClearingHouse");
		lli5.setMethod("init()");
		lli5.setFile("ClearingHouse.java");
		lli5.setLine("904");
		le5.setLocationInfo(lli5);
		
		logEventList.add(le5);

		LogEvent le6 = new LogEvent();
		le6.setLogger("ClearingHouse.ClearingHouse");
		le6.setTime("Thu, Mar 10, 2011 16:59:59 078 EST");
		le6.setTimestamp("1299794399078");
		le6.setLevel("FATAL");
		le6.setThread("Thread-53");
		le6.setMessage("Channel instantiated Delego.Channel.NullChannel");
		LogLocationInfo lli6 = new LogLocationInfo();
		lli6.setClassName("ClearingHouse.ClearingHouse");
		lli6.setMethod("init()");
		lli6.setFile("ClearingHouse.java");
		lli6.setLine("904");
		le6.setLocationInfo(lli6);
		
		logEventList.add(le6);


		return logEventList;
	}
	
}
