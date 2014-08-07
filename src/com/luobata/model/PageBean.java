package com.luobata.model;

public class PageBean {
	//�ڼ�ҳ
	private int page;
	//ÿҳ��¼��
	private int rows;
	//��ʼҳ
	private int start;
	
	
	public PageBean(int page, int rows) {
		super();
		this.page = page;
		this.rows = rows;
	}
	public int getPage() {
		return page;
	}
	public void setPage(int page) {
		this.page = page;
	}
	public int getRows() {
		return rows;
	}
	public void setRows(int rows) {
		this.rows = rows;
	}
	public int getStart() {
		return (page-1)*rows;
	}
	
	
	
}
