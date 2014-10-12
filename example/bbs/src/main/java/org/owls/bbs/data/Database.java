package org.owls.bbs.data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.owls.bbs.vo.BbsVO;

public class Database {
	
	private List<BbsVO> dataSource;
	
	private Database() {
		dataSource = new ArrayList<BbsVO>();
	}

	private void fillFeintData() throws Exception{
		for(int i = 1; i < 88; i ++){
			BbsVO vo = new BbsVO();
			int id = i;
			String title = "제목 " + i;
			String desc = title + " 내용";
			String writer = "system";
			Date date = new Date();
			
			vo.setId(id);
			vo.setWriter(writer);
			vo.setTitle(title);
			vo.setDesc(desc);
			vo.setRegdate(date);
			dataSource.add(vo);
		}
	}
	
	public void init() throws Exception{
		fillFeintData();
	}
	
	public BbsVO getContent(int id) throws Exception {
		BbsVO returnVal = new BbsVO();
		for(BbsVO vo : dataSource){
			if(vo.getId() == id){
				returnVal = vo;
			}
		}
		return returnVal;
	}
	
	public List<BbsVO> search(String key, String value) {
		List<BbsVO> filtered = new ArrayList<BbsVO>();
		for(BbsVO vo : dataSource){
			if(key.equals("title")){
				if(vo.getTitle().contains(value))
					filtered.add(vo);
			}else if(key.equals("desc")){
				if(vo.getDesc().contains(value))
					filtered.add(vo);
			}else if(key.equals("writer")){
				if(vo.getWriter().contains(value))
					filtered.add(vo);
			}
		}
		return filtered;
	}
	
	public List<BbsVO> list(int page){
		return dataSource;
	}
	
	public List<BbsVO> list(Map<String, Object> param){
		List<BbsVO> returnList = new ArrayList<BbsVO>();
		int start = Integer.parseInt(param.get("start").toString());
		int end = Integer.parseInt(param.get("end").toString());
		for(BbsVO vo : dataSource){
			if(vo.getId() >= start && vo.getId() <= end){
				returnList.add(vo);
			}
		}
		return returnList;
	}
	
	public int getTotal (){
		return dataSource.size();
	}
};