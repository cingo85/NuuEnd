package com.leadtek.nuu.form;

public class BasicFilterForm {
	
	private Integer draw = 0;
	private Integer start = 0;
	private Integer length = 10;
	public Integer getDraw() {
		return draw;
	}
	public void setDraw(Integer draw) {
		this.draw = draw;
	}
	public Integer getStart() {
		return start;
	}
	public void setStart(Integer start) {
		this.start = start;
	}
	public Integer getLength() {
		return length;
	}
	public void setLength(Integer length) {
		this.length = length;
	}
	
	public Integer getPage() {
		return start / length;
	}
	
	 
}