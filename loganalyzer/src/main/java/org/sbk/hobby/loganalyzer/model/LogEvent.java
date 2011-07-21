package org.sbk.hobby.loganalyzer.model;


import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.sbk.hobby.loganalyzer.util.Util;

public class LogEvent extends Identifiable implements Serializable {
	
	public static enum XmlTag {
		EVENT("event"),
		LOGGER("logger"),
		TIME("time"),
		TIMESTAMP("timestamp"),
		LEVEL("level"),
		THREAD("thread"),
		MESSAGE("message"),
		THROWABLE("throwable"),
		LOCATION_INFO("locationInfo"),
		CLASS("class"),
		METHOD("method"),
		FILE("file"),
		LINE("line")
		;
		
		private final String key;
		
		/** Constructor.
		 * @param s Key.
		 */
		private XmlTag(String k)
		{
			this.key = k;
		}

		/**
		 * @return the key
		 */
		public String getKey() {
			return key;
		}

	}
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6187788526803981718L;
	private String logger;
	private String time;
	private String timestamp;
	private String level;
	private String thread;
	private String message;
	private String throwable;
	private LogLocationInfo locationInfo;
	private String color;
	
	private static Map<String,String> colorLevelMap = new HashMap<String,String>();
	static {
		colorLevelMap.put("TRACE", "#CCCCCC");
		colorLevelMap.put("DEBUG", "#99FF66");
		colorLevelMap.put("INFO", "#99FFFF");
		colorLevelMap.put("WARN", "#FF9966");
		colorLevelMap.put("ERROR", "#FF6633");
		colorLevelMap.put("FATAL", "#FF3300");
	}
	/**
	 * @return the logger
	 */
	public String getLogger() {
		return logger;
	}
	/**
	 * @param logger the logger to set
	 */
	public void setLogger(String logger) {
		this.logger = logger;
	}
	/**
	 * @return the time
	 */
	public String getTime() {
		return time;
	}
	/**
	 * @param time the time to set
	 */
	public void setTime(String time) {
		this.time = time;
	}
	/**
	 * @return the timestamp
	 */
	public String getTimestamp() {
		return timestamp;
	}
	/**
	 * @param timestamp the timestamp to set
	 */
	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}
	/**
	 * @return the level
	 */
	public String getLevel() {
		return level;
	}
	/**
	 * @param level the level to set
	 */
	public void setLevel(String level) {
		this.level = level;
	}
	/**
	 * @return the thread
	 */
	public String getThread() {
		return thread;
	}
	/**
	 * @param thread the thread to set
	 */
	public void setThread(String thread) {
		this.thread = thread;
	}
	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}
	/**
	 * @param message the message to set
	 */
	public void setMessage(String message) {
		this.message = message;
	}	
	/**
	 * @return the throwable
	 */
	public String getThrowable() {
		return throwable;
	}
	/**
	 * @param throwable the throwable to set
	 */
	public void setThrowable(String throwable) {
		this.throwable = throwable;
	}
	/**
	 * @return the locationInfo
	 */
	public LogLocationInfo getLocationInfo() {
		return locationInfo;
	}
	/**
	 * @param locationInfo the locationInfo to set
	 */
	public void setLocationInfo(LogLocationInfo locationInfo) {
		this.locationInfo = locationInfo;
	}
	
	public void print() {
		System.out.println("log LEVEL=" + getLevel());
	}
	/**
	 * @return the color
	 */
	public String getColor() {
		color = colorLevelMap.get(getLevel());
		return color;
	}
	/**
	 * @param color the color to set
	 */
	public void setColor(String color) {
		this.color = color;
	}	
	
	public boolean isFiltered(XmlTag attribute, String value, String comparator) {
		if ((value == null) || (value.trim().length() == 0)) {
			return true;
		}
		if (attribute == XmlTag.TIME) {
			if ((time != null) && (value != null) && (value.trim().length() > 0)) {
				return (time.toLowerCase().indexOf(value.trim().toLowerCase()) != -1);
			}
		} else if (attribute == XmlTag.TIMESTAMP) {
			if ((timestamp != null) && (value != null) && (value.trim().length() > 0)) {
				return (timestamp.toLowerCase().indexOf(value.trim().toLowerCase()) != -1);
			}
		} else if (attribute == XmlTag.LEVEL) {		
			if ((comparator == null) || (comparator.trim().length() == 0) || "  ".equalsIgnoreCase(comparator)) {
				return true;
			}
			List<String> allowedLevels = Util.getAllowedLogLevels(comparator, value);
			for(String lvl: allowedLevels) {
				if(lvl.equalsIgnoreCase(level)) {
					return true;
				}
			}
		} else if (attribute == XmlTag.LOGGER) {
			if ((logger != null) && (value != null) && (value.trim().length() > 0)) {
				return (logger.toLowerCase().indexOf(value.trim().toLowerCase()) != -1);
			}
		} else if (attribute == XmlTag.MESSAGE) {
			if ((message != null) && (value != null) && (value.trim().length() > 0)) {
				return (message.toLowerCase().indexOf(value.trim().toLowerCase()) != -1);
			}
		} else if (attribute == XmlTag.THROWABLE) {
			if ((throwable != null) && (value != null) && (value.trim().length() > 0)) {
				return (throwable.toLowerCase().indexOf(value.trim().toLowerCase()) != -1);
			}
		} else if (attribute == XmlTag.THREAD) {
			if ((thread != null) && (value != null) && (value.trim().length() > 0)) {
				return (thread.toLowerCase().indexOf(value.trim().toLowerCase()) != -1);
			}
		}
		
		return false;
	}
	
}
