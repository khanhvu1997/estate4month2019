package com.khanhvu.paging;

public interface Pageble {
	//số trang
	Integer getPage();
	//trang đầu tiên xét
	Integer getOffset();
	//trang cuối cùng xét
	Integer getLimit();
	Sorter getSorter();
	}
