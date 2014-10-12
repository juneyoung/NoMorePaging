NoMorePaging
============

## Objectif de cette library >>

Chaque page web doit pagination et il est vraiment ennuyeux à mettre en œuvre à chaque fois.
Cette library(NoMorePaging) est fait pour gérer facilement la logique de pagination en java, jsp basé sur le Web.

## Usage - La manipulation de la 'list' page >>

Tout d'abord, vous générez PageInfos objet en code source Java.

Vous pouvez générer PageInfos objet avec :
- comptent contenu total
- enregistrements par page(nom de la variable est "recordsPerPage")
- units par page(Je ne sais pas exactement ce que dois-je appeler cela. C'est comme un groupe de pages)

Et vous pouvez modifier les informations de suivi de pagination par la méthode 'set'(Elle est limitée) :
- enregistrements par page
- units par page

Voici un exemple simple avec le projet Spring MVC.
Je recommande d'utiliser ce code dans la couche de service, pas de contrôleur.

### In Java :

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

Et vous pouvez trouver ces informations dans JSP comme ci-dessous.
Il est dans la clé dont vous avez nommé. Dans ce cas, il est "pageInfos".

Ainsi, vous pouvez trouver ${[model name].[key what you named].[paging keywords]} 

### In JSP :

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

C'est tout ce dont vous avez besoin pour obtenir des informations de pagination pour le web.

## Usage - La manipulation de la 'view' page (Comment passer sur 'list' page de la page 'view' page) >>

Il peut gérer 'view' page 'liste' page avec des informations de pagination, si Il sait ce qui est l'indice de ce contenu.
Par exemple, vous pouvez gérer 'view' logique comme ci-dessous.

### In Java :

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


## Connaissance commune >>

Merci pour obtenir intéressante avec mon projet. Et n'hésitez pas à modifier et à utiliser. 
Je sais que ma logique n'est pas assez bon, donc je vais modifier un peu et attendre de votre conseiller.


## L'istoire de la distribution >>

- 2014.10.11 : première commettre(ver.0.01)


# Methods

| nom de la method | paramètre | retour | détail |
|------------------|-----------|--------|--------|
|Map<String, Object> getPageInfos(int currentPage)| numéro de la page | Map<String, Object>(ref. 'List of paging keys') | Cette méthode renvoie Informations de pagination |
|Map<String, Object> getCurrentPageRange(int currentPage)| numéro de la page | Map<String,Object>(keys are 'start' and 'end') | Cette méthode renvoie Map avec les touches de la 'start' et 'end'. Ces moyens numéros 'rownum' des enregistrements  qui montre dans cette page |
|Map<String, Object> getCurrentPageRangeForView(int contentOrder)| Nombre de contenu actuel | Map<String,Object>(key is 'page') | Si le contenu de l'ordre est de 27 et enregistrements par page est de 10, cette méthode renvoie {page = "2"} |


# Liste des paging 'key' >>

- currentPage : numéro de la page
- totalPages  : le total du nombre de pages 
- pageEntry   : Éventail entier de Unit (du groupe de pages) 
- ablePrv	  : Si currentPage est inférieure à 1, elle renvoie false
- ableNxt	  : Si currentPage est la dernière, elle retourne false
- ablePrvUnit : Si currentUnit est inférieure à 1, elle renvoie false
- ableNxtUnit : Si currentUnit est la dernière, elle retourne false
- prvUnitPage : Numéro de page lorsque vous appuyez sur le bouton <<. Si la page courante est de 37, ce sera 21
- nxtUnitPage : Numéro de page lorsque vous appuyez sur le bouton >>. Si la page courante est de 37, ce sera 41

- page		  : Utilisez cette option lorsque vous rediriger vers de la 'list' page la 'view' page