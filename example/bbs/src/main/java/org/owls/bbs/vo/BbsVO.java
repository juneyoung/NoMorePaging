package org.owls.bbs.vo;

import java.util.Date;

public class BbsVO {
	private int id;
	private String writer;
	private String title;
	private String desc;
	private Date regdate;
	private Integer reads = 0;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Integer getReads() {
		return reads;
	}
	public void setReads(Integer reads) {
		this.reads = reads;
	}
	public String getWriter() {
		return writer;
	}
	public void setWriter(String writer) {
		this.writer = writer;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public Date getRegdate() {
		return regdate;
	}
	public void setRegdate(Date regdate) {
		this.regdate = regdate;
	}
	@Override
	public String toString() {
		return "BbsVO [id=" + id + ", writer=" + writer + ", title=" + title
				+ ", desc=" + desc + ", regdate=" + regdate + ", reads="
				+ reads + "]";
	}
}
