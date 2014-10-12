package org.owls.bbs.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.apache.log4j.Logger;
import org.owls.bbs.data.Database;
import org.owls.bbs.vo.BbsVO;
import org.owls.no.more.paging.PageInfos;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(value = {"/bbs"})
public class BbsController {
	
	@Inject
	Database ds;
	
	int recordesPerPage = 3;
	
	private final String MODEL_NM = "result";
	public Logger log = Logger.getLogger(BbsController.class);
	
	
	@RequestMapping(value = {"list"})
	public ModelAndView list(@RequestParam Map<String, Object> param) throws Exception{
		Map<String, Object> result = new HashMap<String, Object>();
		int page = 
				Integer.parseInt((
						(param.get("page") == null) ? "1" : param.get("page").toString()));
		
		//Use some utilities
		int totalCount = ds.getTotal();
		PageInfos pUtils = new PageInfos(totalCount, recordesPerPage);
		pUtils.setPagesPerUnit(3);
		List<BbsVO> list = ds.list(pUtils.getCurrentPageRange(page));
		Map<String, Object> pageInfos = pUtils.getPageInfos(page);
		//end Using utils
		
		result.put("list", list);
		result.put("pageInfos", pageInfos);
		return new ModelAndView("/bbs/list", MODEL_NM, result);
	}
	
	@RequestMapping(value = {"view"})
	public ModelAndView view(@RequestParam Map<String, Object> param) throws Exception {
		int id = Integer.parseInt(
				((param.get("id") == null) ? "0" : param.get("id").toString()));
		Map<String, Object> returnVal = new HashMap<String, Object>();
		//Use some utilities
		
		BbsVO model = ds.getContent(id);
		int totalCount = ds.getTotal();
		PageInfos pUtils = new PageInfos(totalCount, recordesPerPage);
		pUtils.setPagesPerUnit(3);
		
		returnVal.put("model", model);
		returnVal.put("pageInfos", pUtils.getCurrentPageRangeForView(id));
		//end Using utils		
		
		return new ModelAndView("/bbs/view", MODEL_NM, returnVal);
	}
};