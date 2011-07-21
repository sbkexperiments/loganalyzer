package org.sbk.hobby.loganalyzer.extendeddatamodel;


import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.context.FacesContext;

import org.ajax4jsf.model.DataVisitor;
import org.ajax4jsf.model.ExtendedDataModel;
import org.ajax4jsf.model.Range;
import org.ajax4jsf.model.SequenceRange;
import org.sbk.hobby.loganalyzer.model.Identifiable;


/**
 * 
 */
public abstract class PageableDataModel<T extends Identifiable> extends ExtendedDataModel<T>
		implements Serializable
{	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4867858931741021295L;

	private Integer currentId;

	private Integer rowCount;

	private Map<Integer, T> wrappedData = new HashMap<Integer, T>();


	/**
	 * Returns an item by id.
	 * 
	 * @param start
	 * @param numberOfRows
	 * @return
	 */
	protected abstract T getItemById( Integer id );


	/**
	 * Returns a data page.
	 * 
	 * @param start
	 * @param numberOfRows
	 * @return
	 */
	protected abstract List<T> getItemsRanged( int start, int numberOfRows );


	/**
	 * Returns the total count for items.
	 * 
	 * @return
	 */
	protected abstract int getItemsTotalCount();

	protected abstract void runFilters();
	

	/**
	 * This method never called from framework. (non-Javadoc)
	 * 
	 * @see org.ajax4jsf.model.ExtendedDataModel#getRowKey()
	 */
	@Override
	public Object getRowKey()
	{
		return currentId;
	}


	/**
	 * This method normally called by Visitor before request Data Row.
	 */
	@Override
	public void setRowKey( Object key )
	{
		this.currentId = (Integer) key;
	}


	/**
	 * This is main part of Visitor pattern. Method called by framework many times during request
	 * processing.
	 */
	@Override
	public void walk( FacesContext context, DataVisitor visitor, Range range, Object argument )
	{
		//runFilters(); // Because getRowCount() called before walk()
		int firstRow = ((SequenceRange) range).getFirstRow();
		int numberOfRows = ((SequenceRange) range).getRows();
		
		System.err.println("PageableDataModel.walk: firstRow=" + firstRow + ", numberOfRows=" + numberOfRows + ", getItemsTotalCount=" + getItemsTotalCount());
		List<T> list = getItemsRanged( firstRow, numberOfRows );
		System.err.println("list.size()=" + list.size());
		for ( T item :  list)
		{
			wrappedData.put( item.getId(), item );
			visitor.process( context, item.getId(), argument );
		}
	}


	@Override
	public int getRowCount()
	{
		runFilters();
		rowCount = getItemsTotalCount();
		return rowCount;
	}


	/**
	 * This is main way to obtain data row. It is intensively used by framework. We strongly
	 * recommend use of local cache in that method.
	 */
	@Override
	public T getRowData()
	{
		if ( currentId == null )
		{
			return null;
		}
		else
		{
			T ret = wrappedData.get( currentId );
			if ( null == ret )
			{
				ret = getItemById( currentId );
				wrappedData.put( currentId, ret );
				return ret;
			}
			else
			{
				return ret;
			}
		}
	}


	/**
	 * Never called by framework.
	 */
	@Override
	public boolean isRowAvailable()
	{
		if ( null == currentId )
		{
			return false;
		}
		else
		{
			return (null != wrappedData.get( currentId ));
		}
	}


	/**
	 * Unused rudiment from old JSF staff.
	 */
	@Override
	public int getRowIndex()
	{
		return 0;
	}


	/**
	 * Unused rudiment from old JSF staff.
	 */
	@Override
	public void setRowIndex( int rowIndex )
	{
		throw new UnsupportedOperationException();
	}


	/**
	 * Unused rudiment from old JSF staff.
	 */
	@Override
	public Object getWrappedData()
	{
		throw new UnsupportedOperationException();
	}


	/**
	 * Unused rudiment from old JSF staff.
	 */
	@Override
	public void setWrappedData( Object data )
	{
		throw new UnsupportedOperationException();
	}

}
