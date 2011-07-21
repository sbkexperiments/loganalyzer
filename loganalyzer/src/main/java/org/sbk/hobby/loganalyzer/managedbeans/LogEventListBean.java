package org.sbk.hobby.loganalyzer.managedbeans;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;

import org.richfaces.component.UIExtendedDataTable;
import org.richfaces.event.FileUploadEvent;
import org.richfaces.model.UploadedFile;
import org.sbk.hobby.loganalyzer.extendeddatamodel.LogEventListDataModel;
import org.sbk.hobby.loganalyzer.logparser.LogParser;
import org.sbk.hobby.loganalyzer.model.LogEvent;
import org.sbk.hobby.loganalyzer.util.Util;


public class LogEventListBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -804659437825065974L;
	
	private List<LogEvent> logEventList;
	private LogEventListDataModel logEventListDataModel;
	private LogEvent selectedLogEvent;
	private Collection<Object> selection;
	private List<LogEvent> selectionItems = new ArrayList<LogEvent>();
	private String tableState;
	private Map<String,UploadedFile> uploadedLogFileMap = new HashMap<String,UploadedFile>();
	private Set<SelectItem> uploadedFileNames = new HashSet<SelectItem>();
	private String selectedLogFileName;

	//@PostConstruct //tomcat doesnt like this
	public void init() throws Exception {
		//logEventList = TestData.getTestLogEventBeanList();
		/*String logFileUrl = "file:///Z:\\Delego_5_Sandbox\\v2_5_3_patches\\Log\\ECD\\MT\\DelegoMT.2011-04-07.xml";
		LogParser logParser = new LogParser();
		logParser.setLogFileUrl(logFileUrl);
		logParser.parse();
		logEventList =  logParser.getLogEventList();
		selectedLogEvent = logEventList.get(0);
		logEventListDataModel = new LogEventListDataModel();
		logEventListDataModel.setDataList(logEventList);*/
		//logEventList = new ArrayList<LogEvent>();
		//logEventListDataModel = new LogEventListDataModel();
		//logEventListDataModel.setDataList(logEventList);
	}
	
    public void selectionListener(AjaxBehaviorEvent event) {
    	
        UIExtendedDataTable dataTable = (UIExtendedDataTable) event.getComponent();
        Object originalKey = dataTable.getRowKey();
        selectionItems.clear();
        for (Object selectionKey : selection) {
            dataTable.setRowKey(selectionKey);
            if (dataTable.isRowAvailable()) {
            	System.err.println("123");
                selectionItems.add((LogEvent) dataTable.getRowData());
                selectedLogEvent = selectionItems.get(0);
            }
        }
        dataTable.setRowKey(originalKey);
    }
    
    public void fileUploadListener(FileUploadEvent event) throws Exception {
    	System.err.println("fileUploadListener Called!! filename=" + event.getUploadedFile().getName());
    	UploadedFile file = event.getUploadedFile();
    	uploadedLogFileMap.put(file.getName(), file); 
    }
    
    public void selectedLogFileNameValueChangeListener(ValueChangeEvent event) {
    	String newSelectedFileName = (String)event.getNewValue();
		LogParser logParser = new LogParser();
		logParser.setInputStream(uploadedLogFileMap.get(newSelectedFileName).getInputStream());
		try {
			logParser.parse();
			logEventList = logParser.getLogEventList();
			if ((logEventList != null) && (logEventList.size() > 0)) {
				selectedLogEvent = logEventList.get(0);
			} else {
				selectedLogEvent = null;
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
		if (logEventListDataModel == null) {
			logEventListDataModel = new LogEventListDataModel();
		}
		
		logEventListDataModel.setDataList(logEventList);
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
	 * @return the logEventListDataModel
	 */
	public LogEventListDataModel getLogEventListDataModel() {		
		return logEventListDataModel;
	}

	/**
	 * @param logEventListDataModel the logEventListDataModel to set
	 */
	public void setLogEventListDataModel(LogEventListDataModel logEventListDataModel) {
		this.logEventListDataModel = logEventListDataModel;
	}

	/**
	 * @return the selectedLogEvent
	 */
	public LogEvent getSelectedLogEvent() {
		return selectedLogEvent;
	}

	/**
	 * @param selectedLogEvent the selectedLogEvent to set
	 */
	public void setSelectedLogEvent(LogEvent selectedLogEvent) {
		this.selectedLogEvent = selectedLogEvent;
	}

	/**
	 * @return the selection
	 */
	public Collection<Object> getSelection() {
		return selection;
	}

	/**
	 * @param selection the selection to set
	 */
	public void setSelection(Collection<Object> selection) {
		this.selection = selection;
	}

	/**
	 * @return the selectionItems
	 */
	public List<LogEvent> getSelectionItems() {
		return selectionItems;
	}

	/**
	 * @param selectionItems the selectionItems to set
	 */
	public void setSelectionItems(List<LogEvent> selectionItems) {
		this.selectionItems = selectionItems;
	}

	/**
	 * @return the tableState
	 */
	public String getTableState() {
		return tableState;
	}

	/**
	 * @param tableState the tableState to set
	 */
	public void setTableState(String tableState) {
		this.tableState = tableState;
	}
	

	/**
	 * @return the selectedLogFileName
	 */
	public String getSelectedLogFileName() {
		return selectedLogFileName;
	}

	/**
	 * @param selectedLogFileName the selectedLogFileName to set
	 */
	public void setSelectedLogFileName(String selectedLogFileName) {
		this.selectedLogFileName = selectedLogFileName;
	}

	/**
	 * @return the uploadedLogFileMap
	 */
	public Map<String, UploadedFile> getUploadedLogFileMap() {
		return uploadedLogFileMap;
	}

	/**
	 * @param uploadedLogFileMap the uploadedLogFileMap to set
	 */
	public void setUploadedLogFileMap(Map<String, UploadedFile> uploadedLogFileMap) {
		this.uploadedLogFileMap = uploadedLogFileMap;
	}

	/**
	 * @return the uploadedFileNames
	 */
	public Set<SelectItem> getUploadedFileNames() {
		setUploadedFileNames(Util.getSelectItemSet(uploadedLogFileMap.keySet()));
		return uploadedFileNames;
	}

	/**
	 * @param uploadedFileNames the uploadedFileNames to set
	 */
	public void setUploadedFileNames(Set<SelectItem> uploadedFileNames) {		
		this.uploadedFileNames = uploadedFileNames;
	}

}
