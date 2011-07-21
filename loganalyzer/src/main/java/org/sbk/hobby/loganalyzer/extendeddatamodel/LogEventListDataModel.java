package org.sbk.hobby.loganalyzer.extendeddatamodel;


import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.context.FacesContext;

import org.ajax4jsf.model.DataVisitor;
import org.ajax4jsf.model.Range;
import org.sbk.hobby.loganalyzer.model.LogEvent;

public class LogEventListDataModel extends PageableDataModel<LogEvent>
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -1842436177444319406L;
	private List<LogEvent> logEventList;
	private Map<Integer, LogEvent> currentData;
	private Map<Integer, LogEvent> masterData;
	private String timestampFilter;
	private String timeFilter;
	private String levelFilter;
	private String levelFilterComparator;
	private String loggerFilter;
	private String messageFilter;
	private String throwableFilter;
	private String threadFilter;
	private Collection<Object> selection;
	private String pageNumber = "1";
	

	@Override
	protected LogEvent getItemById( Integer id )
	{
		if (currentData == null) {
			return null;
		}
		return currentData.get( id );
	}


	@Override
	protected List<LogEvent> getItemsRanged( int start, int numberOfRows )
	{
		if (currentData == null) {
			return null;
		}
		Collection<LogEvent> set = currentData.values();
		List<LogEvent> list = new ArrayList<LogEvent>( set );
		Collections.sort( list, new Comparator<LogEvent>()	{
										public int compare( LogEvent o1, LogEvent o2 )	{
											return o1.getId().compareTo( o2.getId() );
										}										
									} );
		if (start >= list.size()) {
			return new ArrayList<LogEvent>();
		}
		int end = start + numberOfRows;
		if (end > list.size()) {
			end = list.size();
		}
		return list.subList( start, end );
	}


	@Override
	protected int getItemsTotalCount()
	{
		if (currentData == null) {
			return 0;
		}
		return currentData.size();
	}

	/**
	 * @return the data
	 */
	public Map<Integer, LogEvent> getData() {
		return currentData;
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


	/**
	 * @param currentData the currentData to set
	 */
	public void setCurrentData(Map<Integer, LogEvent> data) {
		this.currentData = data;
	}

	
	
	/**
	 * @return the timestampFilter
	 */
	public String getTimestampFilter() {
		return timestampFilter;
	}


	/**
	 * @param timestampFilter the timestampFilter to set
	 */
	public void setTimestampFilter(String timestampFilter) {
		this.timestampFilter = timestampFilter;
	}


	/**
	 * @return the timeFilter
	 */
	public String getTimeFilter() {
		return timeFilter;
	}


	/**
	 * @param timeFilter the timeFilter to set
	 */
	public void setTimeFilter(String timeFilter) {
		this.timeFilter = timeFilter;
	}


	/**
	 * @return the levelFilter
	 */
	public String getLevelFilter() {
		return levelFilter;
	}


	/**
	 * @param levelFilter the levelFilter to set
	 */
	public void setLevelFilter(String levelFilter) {
		this.levelFilter = levelFilter;
	}


	/**
	 * @return the levelFilterComparator
	 */
	public String getLevelFilterComparator() {
		return levelFilterComparator;
	}


	/**
	 * @param levelFilterComparator the levelFilterComparator to set
	 */
	public void setLevelFilterComparator(String levelFilterComparator) {
		this.levelFilterComparator = levelFilterComparator;
	}


	/**
	 * @return the loggerFilter
	 */
	public String getLoggerFilter() {
		return loggerFilter;
	}


	/**
	 * @param loggerFilter the loggerFilter to set
	 */
	public void setLoggerFilter(String loggerFilter) {
		this.loggerFilter = loggerFilter;
	}


	/**
	 * @return the messageFilter
	 */
	public String getMessageFilter() {
		return messageFilter;
	}


	/**
	 * @param messageFilter the messageFilter to set
	 */
	public void setMessageFilter(String messageFilter) {
		this.messageFilter = messageFilter;
	}


	/**
	 * @return the throwableFilter
	 */
	public String getThrowableFilter() {
		return throwableFilter;
	}


	/**
	 * @param throwableFilter the throwableFilter to set
	 */
	public void setThrowableFilter(String throwableFilter) {
		this.throwableFilter = throwableFilter;
	}


	/**
	 * @return the masterData
	 */
	public Map<Integer, LogEvent> getMasterData() {
		return masterData;
	}


	/**
	 * @param masterData the masterData to set
	 */
	public void setMasterData(Map<Integer, LogEvent> masterData) {
		this.masterData = masterData;
	}

	/**
	 * @return the threadFilter
	 */
	public String getThreadFilter() {
		return threadFilter;
	}

	/**
	 * @param threadFilter the threadFilter to set
	 */
	public void setThreadFilter(String threadFilter) {
		this.threadFilter = threadFilter;
	}
	
	/**
	 * @return the selection
	 */
	public Collection<Object> getSelection() {
		if (selection == null) {
			selection = new ArrayList<Object>();
		}
		
		selection.clear();
		selection.add((LogEvent)currentData.get(new Integer(0)));
		return selection;
	}

	/**
	 * @param selection the selection to set
	 */
	public void setSelection(Collection<Object> selection) {
		this.selection = selection;
	}

	/**
	 * @return the pageNumber
	 */
	public String getPageNumber() {
		return pageNumber;
	}

	/**
	 * @param pageNumber the pageNumber to set
	 */
	public void setPageNumber(String pageNumber) {
		this.pageNumber = pageNumber;
	}


	public void setDataList(List<LogEvent> list) {
		setLogEventList(list);
		masterData = new HashMap<Integer, LogEvent>();
		if (list != null) {			
			int id = 0;
			for(LogEvent event: list) {
				event.setId(new Integer(id));
				masterData.put(new Integer(id), event);
				id++;
			}
		}
		currentData = masterData;
	}


	@Override
	protected void runFilters() {
		/*System.err.println("LogEventListDataModel.runFilters: threadFilter=" + threadFilter);
		Map<Integer, LogEvent> newData = runThreadFilter(masterData, threadFilter);
		System.err.println("LogEventListDataModel.runFilters: currentData.size()=" + currentData.size());
		System.err.println("LogEventListDataModel.runFilters: newData.size()=" + newData.size());*/
		
		Map<Integer, LogEvent> newData = new HashMap<Integer, LogEvent>();
		
		for(Integer id: masterData.keySet()) {
			LogEvent logEvent = masterData.get(id);
			if ( 	logEvent.isFiltered(LogEvent.XmlTag.TIME, timeFilter, "")
					&& logEvent.isFiltered(LogEvent.XmlTag.TIMESTAMP, timestampFilter, "")
					&& logEvent.isFiltered(LogEvent.XmlTag.LEVEL, levelFilter, levelFilterComparator)
					&& logEvent.isFiltered(LogEvent.XmlTag.LOGGER, loggerFilter, "")					 
					&& logEvent.isFiltered(LogEvent.XmlTag.MESSAGE, messageFilter, "")
					&& logEvent.isFiltered(LogEvent.XmlTag.THROWABLE, throwableFilter, "")
					&& logEvent.isFiltered(LogEvent.XmlTag.THREAD, threadFilter, "")
					) {
				newData.put(id, logEvent);
			}
		}
		
		currentData = newData;
	}
	
	@Override
	public void walk( FacesContext context, DataVisitor visitor, Range range, Object argument ) {
		super.walk(context, visitor, range, argument);
		setPageNumber("4");
	}
	
}
