package org.sbk.hobby.loganalyzer.util;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.faces.model.SelectItem;

public class Util {

	public static Map<String,Integer> logLevelMap = new HashMap<String,Integer>();
	
	static {
		logLevelMap.put("TRACE", new Integer("1"));
		logLevelMap.put("DEBUG", new Integer("2"));
		logLevelMap.put("INFO", new Integer("3"));
		logLevelMap.put("WARN", new Integer("4"));
		logLevelMap.put("ERROR", new Integer("5"));
		logLevelMap.put("FATAL", new Integer("6"));
	}
	
	public static List<String> getAllowedLogLevels(String comparator, String selectedLogLevel) {
		List<String> list = new ArrayList<String>();
		//System.err.println("comparator='" + comparator + "', selectedLogLevel='" + selectedLogLevel + "'");
		if ((comparator == null) || (comparator.equalsIgnoreCase("null")) || (selectedLogLevel == null)) {
			for(String level : logLevelMap.keySet()) {
				list.add(level);
			}	
		} else if (comparator.equalsIgnoreCase("==")) {
			updateLogLevelsEqualTo(selectedLogLevel, list);
		} else if (comparator.equalsIgnoreCase("!=")) {
			updateLogLevelsNotEqualTo(selectedLogLevel, list);
		} else if (comparator.equalsIgnoreCase("<")) {
			updateLogLevelsLessThan(selectedLogLevel, list);
		} else if (comparator.equalsIgnoreCase("<=")) {
			updateLogLevelsLessThan(selectedLogLevel, list);
			updateLogLevelsEqualTo(selectedLogLevel, list);
		} else if (comparator.equalsIgnoreCase(">")) {
			updateLogLevelsGreaterThan(selectedLogLevel, list);
		} else if (comparator.equalsIgnoreCase(">=")) {
			updateLogLevelsGreaterThan(selectedLogLevel, list);
			updateLogLevelsEqualTo(selectedLogLevel, list);
		}
		
		return list;
	}
	
	public static void updateLogLevelsEqualTo(String selectedLogLevel, List<String> list) {
		list.add(selectedLogLevel);
	}
	
	public static void updateLogLevelsNotEqualTo(String selectedLogLevel, List<String> list) {
		for(String level : logLevelMap.keySet()) {
			if (!selectedLogLevel.equalsIgnoreCase(level)) {
				list.add(level);
			}
		}
	}
	
	public static void updateLogLevelsLessThan(String selectedLogLevel, List<String> list) {
		Integer selectedLogLevelValue = logLevelMap.get(selectedLogLevel);
		for(String level : logLevelMap.keySet()) {
			Integer currentLogLevelValue = logLevelMap.get(level);
			if (currentLogLevelValue <  selectedLogLevelValue) {
				list.add(level);
			}
		}		
	}
	
	public static void updateLogLevelsGreaterThan(String selectedLogLevel, List<String> list) {
		Integer selectedLogLevelValue = logLevelMap.get(selectedLogLevel);
		for(String level : logLevelMap.keySet()) {
			Integer currentLogLevelValue = logLevelMap.get(level);
			if (currentLogLevelValue >  selectedLogLevelValue) {
				list.add(level);
			}
		}		
	}
	
	public static Set<SelectItem> getSelectItemSet(Set<String> selectItems) {
		Set<SelectItem> siSet = new HashSet<SelectItem>();
		for(String siValue: selectItems) {
	    	SelectItem si = new SelectItem();
	    	si.setLabel(siValue);
	    	si.setValue(siValue);
	    	siSet.add(si);
		}
		
		return siSet;
	}

}

