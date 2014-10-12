NoMorePaging
============

## 라이브러리의 목적 >>

 모든 웹 웹 페이지에서는 페이징 기능이 필요합니다. 생각보다 오류없이 페이징을 작성하기는 쉽지 않구요.
이 라이브러리(NoMorePaging) 에서는 Java, jsp 기반의 웹 어플리케이션에서 보다 간편하게 오류없는
페이징을 구현할 수 있도록 개발되었습니다. 


## 사용법 - 리스트 페이지에서 >>

우선 자바코드에서 PageInfos Object 를 생성합니다.

생성시에는 아래와 같은 부분들이 필요합니다 :
- 총 컨텐츠의 갯수 
- 한 페이지에 보여질 항목(레코드)의 갯수 
- 한 페이지에 보여질 페이지 네비게이션(아래에 < 1 2 3 4 5 > 가 보이는 부분. 소스에서는 unit 입니다) 갯수 

제한되긴 하지만 생성 후에도 set 메소드를 통하여 아래와 같은 정보를 수정할 수 있습니다 :
- 한 페이지에 보여질 항목(레코드)의 갯수 
- 한 페이지에 보여질 페이지 네비게이션 갯수 

아래는 Spring MVC 에서 사용하는 예입니다. 예제라서 컨트롤러에서 페이징 처리를 했는데,
서비스에서 처리해서 컨트롤러로 반환하는 방식을 권장합니다. 

### Java :

``` java

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

```

 JSP 에서 정보를 색인할 때에는 아래와 같은 방식으로 접근합니다.
 ${[스프링에서 지정한 모델명].[페이지 정보를 담은 키값].[페이징 정보에 담기는 정보의 키 - 라이브러리에서 정해져 있습니다]}
 
 이 경우에는 스프링에서는 result 라는 모델명을 사용하였고, 그 모델에 "pageInfos" 라는 키로 담았습니다.


### JSP :

```jsp

	<div id="paging" align="center">
		<c:if test="${result.pageInfos.ablePrvUnit }">
			<a href="list?page=${result.pageInfos.prvUnitPage }">◀◀︎</a>
		</c:if>
	
		<c:if test="${result.pageInfos.ablePrv }">
			<a href="list?page=${result.pageInfos.currentPage - 1 }">◀︎</a>
		</c:if>
		
		<c:forEach var="page" items="${result.pageInfos.pageEntry}">
			<a href="list?page=${page}">${page}</a>
		</c:forEach>
		
		<c:if test="${result.pageInfos.ableNxt }">
			<a href="list?page=${result.pageInfos.currentPage + 1 }">▷</a>
		</c:if>
		
		<c:if test="${result.pageInfos.ableNxtUnit }">
			<a href="list?page=${result.pageInfos.nxtUnitPage }">▷▷</a>
		</c:if>
		
	</div>

```

웹 페이징 개발을 위해 개발자가 할 건 이미 만들어진 정보를 키로 찾아사용하는 것이 전부입니다.


## 사용법 - 뷰 페이지에서(예를 들어 3페이지 게시글을 보다 리스트로 가는데, 3 페이지로 가는 법) >>

 NoMorePaging 에서는 뷰에서 리스트로 갈 때, 해당 게시글이 자신이 몇번째 게시글인지만 알고 있다면,
 리스트로 돌아갈 때 해당 페이지로 돌아갈 수 있습니다. 
 아래 예제에서 하는 법을 알려드립니다. 

### Java :

``` java

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

```

### In JSP :

``` jsp

		<body>
		<h3>${result.model.title }</h3>
		<h6>${result.model.writer }<small>${result.model.regdate }</small></h6>
		${result.model.desc }
		
		<a href="list?page=${result.pageInfos.page }">list</a>
		</body>

```


## 노트 >>

제 프로젝트에 관심을 가져주셔서 감사합니다. 마음껏 퍼쓰시고 고쳐주세요.
아직 버전 0.01 인 만큼 로직이 엉성한 부분도 있습니다. 
조언을 주시면 적극 반영하도록 하겠습니다 :D 


## 배포 정보 >>

- 2014.10.11 : 첫번째 커밋 (ver.0.01)


# 라이브러리가 제공하는 키 목록 

- currentPage : 현재 페이지 정보
- totalPages  : 전체 페이지의 수 
- pageEntry   : 배열 형태로 아래 네비게이션(?)에 뿌려질 페이지의 배열입니다. 예를 들면 1 2 3 4 5 
- ablePrv	  : 이전 페이지로 돌아갈 수 있는지. 맨 처음 페이지라면 false를 반환합니다
- ableNxt	  : 다음 페이지로 넘어갈 수 있는지. 맨 마지막 페이지라면 false를 반환합니다
- ablePrvUnit : 이전 페이지그룹(네비게이션, 유닛) 으로 돌아갈 수 있는지. 맨 처음이라면 false를 반환합니다
- ableNxtUnit : 다음 페이지그룹(네비게이션, 유닛) 으로 넘어갈 수 있는지. 맨 마지막이라면 false를 반환합니다
- prvUnitPage : 이전 페이지그룹의 페이지 넘버. 예를 들어 현재가 37 페이지라면, 이 값은 21이 됩니다
- nxtUnitPage : 다음 페이지그룹의 페이지 넘버. 예를 들어 현재가 37 페이지라면, 이 값은 41이 됩니다

- page		  : 뷰에서 해당 컨텐츠가 돌아가야 하는 리스트의 페이지 

# 제공되는 메소드

| 메소드명 | 파라미터 | 반환값 | 기능 |
|-|-|-|-|
|public Map<String, Object> getPageInfos(int currentPage)| 현재 페이지 | 맵(키는 '라이브러리가 제공하는 키 목록 항목 참조') | 현재 페이지에서 필요한 페이징 정보를 반환 |
|public Map<String, Object> getCurrentPageRange(int currentPage)| 현재 페이지 | 맵(키는 'start'와 'end') | 현재 페이지에 필요한 레코드를 가져오기 위해 시작값과 끝값을 계산해서 반환 |
|public Map<String, Object> getCurrentPageRangeForView(int contentOrder)| 뷰에서 현재 콘텐츠의 순서 | 맵(키는 'page') | 뷰에서 리스트 전환시 컨텐츠 순서를 가지고 페이지를 계산해서 반환 |