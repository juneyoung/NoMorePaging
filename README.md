NoMorePaging
============

Purpose of this library >>

Every web page needs paging and it is really annoying to implement every time.
This library(NoMorePaging) is made to handle paging logic easily in java, jsp based web.


Usage >>

First, you generate PageInfos Object in Java source code.

You can generate PageInfos Object with :
- total contents count
- records per page
- units per page(I do not exactly know what should I call this. It is like group of pages)

And you can modify follow paging informations by set method(It is restricted) :
- records per page
- units per page

Below is a simple example with Spring MVC project.
I recommend to use this code in Service layer, not in controller.

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


And You can find those information in JSP like below.
It is in key what you named. In this case, it is "pageInfos".

So you can find ${[model name].[key what you named].[paging keywords]} 

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


Note >>

It can handle view to list paging, if It knows what is the index of this contents.
For Example you can handle view logic like below.

In Java :

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

In JSP :

``` jsp

		<body>
		<h3>${result.model.title }</h3>
		<h6>${result.model.writer }<small>${result.model.regdate }</small></h6>
		${result.model.desc }
		
		<a href="list?page=${result.pageInfos.page }">list</a>
		</body>

```


Note >>

Thanks for getting interesting with my project. And feel free to edit and use.
I know my logic is not good enough, so I will edit some and expect your advise.


Release Infos >>

- 2014.10.11 : very first commit(ver.0.01)