package com.study.groupware.util;

public class Paging {
	public String pageIndexList (int totalCnt, int current_page) {
		// 한 페이지에 출력될 게시물 수 (countList)
		// 한 화면에 출력될 페이지 수 (countPage)
		// 현재 페이지 번호 (이하 page)

		// 한 페이지에 출력될 게시물 수
		int countList = 10;

		// 한 페이지에 출력될 페이지 수
		int countPage = 10;

		// 현재 페이지 번호
		int page = current_page;

		// 모든 게시물의 수
		int totalCount = totalCnt;

		// 게시물이 105개면 105 / 10 = 10.5 
		// int형이기 때문에 10만 들어간다.
		int totalPage = totalCount / countList;

		// 나머지가 존재한다면 + 1
		if (totalCount % countList > 0) {
			totalPage++;
		}

		// 만약, 총 페이지 보다 현재 페이지가 높다면
		// 현재 페이지를 강제로 총 페이지로 변경한다.
		if (totalPage < page) {
			page = totalPage;
		}

		// 시작 페이지
		int startPage = ((page - 1) / 10) * 10 + 1;

		// 마지막 페이지
		int endPage = startPage + countPage - 1;

		// 마지막 페이지가 총 페이지 보다 크다면 
		// 마지막 페이지는 총 페이지가 된다.
		if (endPage > totalPage) {
			endPage = totalPage;
		}
		
		// 출력
		String result = "<ul class='pagination'>";
		
		if (startPage > 1) {
			result = "<li><a href=\"?page=1\">처음</a></li>";
		}

		if (page > 10) {
			result += "<li><a href=\"?page=" + (startPage - 1)  + "\">이전</a></li>";
		}

		for (int iCount = startPage; iCount <= endPage; iCount++) {
			if (iCount == page) {
				result += "<li class='active'><a href='?page=" + iCount  + "'>" + iCount + "</a></li>";
			} else {
				result += "<li><a href='?page=" + iCount  + "'>" + iCount + "</a></li>";
			}
		}

		if ((startPage + 10) < totalPage) {
			result += "<li><a href=\"?page=" + (startPage + 10)  + "\">다음</a></li>";
		}


		if (endPage < totalPage) {
			result += "<li><a href=\"?page=" + totalPage + "\">끝</a></li>";
		}
		
		result += "</ul>";
		
		return result;
	}
	
	public String pageIndexListAjax (int totalCnt, int current_page) {
		// 한 페이지에 출력될 게시물 수 (countList)
		// 한 화면에 출력될 페이지 수 (countPage)
		// 현재 페이지 번호 (이하 page)

		// 한 페이지에 출력될 게시물 수
		int countList = 10;

		// 한 페이지에 출력될 페이지 수
		int countPage = 10;

		// 현재 페이지 번호
		int page = current_page;

		// 모든 게시물의 수
		int totalCount = totalCnt;

		// 게시물이 105개면 105 / 10 = 10.5 
		// int형이기 때문에 10만 들어간다.
		int totalPage = totalCount / countList;

		// 나머지가 존재한다면 + 1
		if (totalCount % countList > 0) {
			totalPage++;
		}

		// 만약, 총 페이지 보다 현재 페이지가 높다면
		// 현재 페이지를 강제로 총 페이지로 변경한다.
		if (totalPage < page) {
			page = totalPage;
		}

		// 시작 페이지
		int startPage = ((page - 1) / 10) * 10 + 1;

		// 마지막 페이지
		int endPage = startPage + countPage - 1;

		// 마지막 페이지가 총 페이지 보다 크다면 
		// 마지막 페이지는 총 페이지가 된다.
		if (endPage > totalPage) {
			endPage = totalPage;
		}
		
		// 출력
		String result = "<ul id='pageIndexListAjax' class='pagination'>";
		
		if (startPage > 1) {
			result = "<li><a href='#' data-page='1'>처음</a></li>";
		}

		if (page > 10) {
			result += "<li><a href='#' data-page='" + (startPage - 1) + "'>이전</a></li>";
		}

		for (int iCount = startPage; iCount <= endPage; iCount++) {
			if (iCount == page) {
				result += "<li class='active'><a href='#' data-page='" + iCount  + "'>" + iCount + "</a></li>";
			} else {
				result += "<li><a href='#' data-page='" + iCount  + "'>" + iCount + "</a></li>";
			}
		}

		if ((startPage + 10) < totalPage) {
			result += "<li><a href='#' data-page='" + (startPage + 10)  + "'>다음</a></li>";
		}


		if (endPage < totalPage) {
			result += "<li><a href='#' data-page='" + totalPage + "'>끝</a></li>";
		}
		
		result += "</ul>";
		
		return result;
	}
	
	public String pageIndexLista (int totalCnt, int current_page, String ntc_div) {
		// 한 페이지에 출력될 게시물 수 (countList)
		// 한 화면에 출력될 페이지 수 (countPage)
		// 현재 페이지 번호 (이하 page)

		// 한 페이지에 출력될 게시물 수
		int countList = 10;

		// 한 페이지에 출력될 페이지 수
		int countPage = 10;

		// 현재 페이지 번호
		int page = current_page;

		// 모든 게시물의 수
		int totalCount = totalCnt;

		// 게시물이 105개면 105 / 10 = 10.5 
		// int형이기 때문에 10만 들어간다.
		int totalPage = totalCount / countList;

		// 나머지가 존재한다면 + 1
		if (totalCount % countList > 0) {
			totalPage++;
		}

		// 만약, 총 페이지 보다 현재 페이지가 높다면
		// 현재 페이지를 강제로 총 페이지로 변경한다.
		if (totalPage < page) {
			page = totalPage;
		}

		// 시작 페이지
		int startPage = ((page - 1) / 10) * 10 + 1;

		// 마지막 페이지
		int endPage = startPage + countPage - 1;

		// 마지막 페이지가 총 페이지 보다 크다면 
		// 마지막 페이지는 총 페이지가 된다.
		if (endPage > totalPage) {
			endPage = totalPage;
		}
		
		// 출력
		String result = "<ul class='pagination'>";
		
		if (startPage > 1) {
			result = "<li><a href=\"?"+ntc_div+"&page=1\">처음</a></li>";
		}

		if (page > 10) {
			result += "<li><a href=\"?"+ntc_div+"&page=" + (startPage - 1)  + "\">이전</a></li>";
		}

		for (int iCount = startPage; iCount <= endPage; iCount++) {
			if (iCount == page) {
				result += "<li class='active'><a href='?"+ntc_div+"&page=" + iCount  + "'>" + iCount + "</a></li>";
			} else {
				result += "<li><a href='?"+ntc_div+"&page=" + iCount  + "'>" + iCount + "</a></li>";
			}
		}

		if ((startPage + 10) < totalPage) {
			result += "<li><a href=\"?"+ntc_div+"&page=" + (startPage + 10)  + "\">다음</a></li>";
		}


		if (endPage < totalPage) {
			result += "<li><a href=\"?"+ntc_div+"&page=" + totalPage + "\">끝</a></li>";
		}
		
		result += "</ul>";
		
		return result;
	}
	
	
}
