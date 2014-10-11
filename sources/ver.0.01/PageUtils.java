//Edit Here. Sorry I do not familiar with sharing a project with git
package org.owls.no.more.paging;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This Class supports static methods which return information about board paging
 * There are two kinds of page information.
 * A) list paging information
 * B) view paging information (to save current page when back to list page)
 * @author juneyoungoh
 * @version 0.01
 * @since 2014.10.11
 */
public class PageUtils {
	
	private static final String STARTKEY = "start";
	private static final String ENDKEY = "end";
	private static final String PAGEKEY = "page";
	
	private int totalConCount = 0;
	private int totalPages = 1;
	private int totalUnits = 1;
	
	private int recordsPerPage = 10;
	private int pagesPerUnit = 10;
	private int currentPage = 1;
	private int currentUnit = 1;
	
	private int startPage = 1;
	private int endPage = 10;
	//private int prvUnit = 1;
	//private int nxtUnit = 1;
	
	// if pagesPerUnit is 10 and Currentpage is 37,
	// When you push < button, it goes to page 20.
	// If press > button, it goes 41	
	private int prvUnitPage = 1;
	private int nxtUnitPage = 1;
	
	
	private boolean ablePrv = false;
	private boolean ableNxt = false;
	private boolean ablePrvUnit = false;
	private boolean ableNxtUnit = false;
	
	public PageUtils(int totalConcount) {this.totalConCount =totalConcount;}
	public PageUtils(int totalConcount, int recordsPerPage) {this.totalConCount =totalConcount; this.recordsPerPage = recordsPerPage;}

	public PageUtils(int recordsPerPage, int pagesPerUnit, int totalConCount) {
		this.totalConCount = totalConCount;
		this.pagesPerUnit = pagesPerUnit;
		this.totalConCount = totalConCount;
	}

	/**
	 * This method returns paging informations below
	 * special terms - Unit : This means group of pages
	 * - ablePrv : If currentPage is lower than 1, it returns false
	 * - ableNxt : If currentPage is the last one, it returns false
	 * - ablePrvUnit : If currentUnit is lower than 1, it returns false
	 * - ableNxtUnit : If currentUnit is the last one, it returns false
	 * - currentPage : current page. it is same with parameter 'currentPage'
	 * - totalPage : count of total pages, If you want to implement 'last Page', it can be used
	 * - prvUnitPage : If currentPage is 37 and pages per unit is 10, it returns 21
	 * - nxtUnitPage : If currentPage is 37 and pages per unit is 10, it returns 41
	 * - unitEntry :
	 * @param totalConCount
	 * @param recordsPerPage
	 * @param currentPage
	 * @return
	 */
	public Map<String, Object> getPageInfos(int currentPage){
		Map<String, Object> pageInfo = new HashMap<String, Object>();
		List<Integer> pageEntry = new ArrayList<Integer>();
		
		//calculate totalPages
		if(totalConCount/recordsPerPage > 0){
			//over 1 pages
			totalPages = totalConCount/recordsPerPage;
			if(totalConCount%recordsPerPage > 0)
				totalPages += 1;
			if(currentPage < totalPages){
				ableNxt = true;
			}
			if(currentPage > 1){
				ablePrv = true;
			}
		}
		
		//calculate page and end page on current unit to show
		// 1 ~ 10 = 1 unit
		// 11 ~ 20 = 2 unit
		currentUnit = currentPage/pagesPerUnit + 1;
		if(currentPage%pagesPerUnit == 0){
			currentUnit -= 1;
		}
		
		if(totalPages > pagesPerUnit){
			//multiple units
			//calculate totalUnits
			if(totalPages/pagesPerUnit > 0){
				totalUnits = totalPages/pagesPerUnit;
				if(totalPages%pagesPerUnit > 0)
					totalUnits += 1;
			}
			
			endPage = currentUnit * pagesPerUnit;
			startPage = endPage - (pagesPerUnit - 1);
			
			if(currentUnit < totalUnits){
				ableNxtUnit = true;
				nxtUnitPage = (currentUnit * pagesPerUnit) + 1;
			}
			if(currentUnit > 1){
				ablePrvUnit = true;
				prvUnitPage = ((currentUnit - 1) * pagesPerUnit) - (pagesPerUnit - 1);
			}
		}else{
			
			totalUnits = 1;
			startPage = 1;
			endPage = totalPages;
		}
		
		for(int index = startPage; index <= endPage; index++){
			if(index < totalUnits*pagesPerUnit)
				pageEntry.add(index);
		}
		
		pageInfo.put("currentPage", currentPage);
		pageInfo.put("totalPages", totalPages);
		
		pageInfo.put("pageEntry", pageEntry);
		pageInfo.put("ablePrv", ablePrv);
		pageInfo.put("ableNxt", ableNxt);
		
		pageInfo.put("ablePrvUnit", ablePrvUnit);
		pageInfo.put("ableNxtUnit", ableNxtUnit);
		pageInfo.put("prvUnitPage", prvUnitPage);
		pageInfo.put("nxtUnitPage", nxtUnitPage);
		return pageInfo;
	}
	
	//== before page those informations need for query(getRange)
	//== Call from outside
	/**
	 * This method returns a map with key 'start' and 'end', 
	 * made for query
	 * @param CurrentPage
	 * @param recordsPerPage
	 * @param totalContentsCount
	 * @return Map<String, Object> with key 'start' and 'end'
	 */
	public Map<String, Object> getCurrentPageRange(int currentPage){
		Map<String, Object> startNend = new HashMap<String, Object>();
		int endRn = (currentPage)*recordsPerPage;
		startNend.put(STARTKEY, currentPage*recordsPerPage - (recordsPerPage - 1));
		if(endRn > totalConCount){
			startNend.put(ENDKEY, totalConCount);
		}else{
			startNend.put(ENDKEY, endRn);
		}
		return startNend;
	}
	
	/**
	 * The method returns a page number calculated by content order
	 * @param contentOrder : kind of order like rownum
	 * @return the page where this content should be
	 */
	public Map<String, Object> getCurrentPageRangeForView(int contentOrder){
		Map<String, Object> pageMap = new HashMap<String, Object>();
		
		if((contentOrder)/recordsPerPage > 0){
			currentPage = contentOrder/recordsPerPage;
			if((contentOrder)%recordsPerPage > 0)
				currentPage += 1;
		}
		
		pageMap.put(PAGEKEY, currentPage);
		return pageMap;
	}

	public void setRecordsPerPage(int recordsPerPage) {
		this.recordsPerPage = recordsPerPage;
	}

	public void setPagesPerUnit(int pagesPerUnit) {
		this.pagesPerUnit = pagesPerUnit;
	}
};