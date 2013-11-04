<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ page import="java.util.*" %>
<%@ page import="net.sf.ehcache.*" %>
<%@ taglib prefix="c" uri="/WEB-INF/taglib/c.tld" %>
<%!
    //
    //CAMBIARE QUESTI VALORI IN FUNZIONE DELL'APPLICAZIONE DA GESTIRE (tutto ciò che inizia con XXX)
    public static final String SITE_URL = "http://localhost:8080/GoKickBackOffice";
    public static final String CACHE_PAGE_URL = "cache.action";
    public static final String SECURITY = "gokick-it";
%>
<%
    Boolean reload = false;
    Boolean inMemorySize = false;
    String security = request.getParameter("security") != null ? request.getParameter("security") : "";
    if (!security.equals(SECURITY))
    {
        security = ""; //Not authorized
    }
    String action = request.getParameter("action") != null ? request.getParameter("action") : "";
    String name = request.getParameter("name") != null ? request.getParameter("name") : "";

    if (action.equals("clear"))
    {
        if (CacheManager.getInstance().cacheExists(name))
        {
            CacheManager.getInstance().getCache(name).removeAll();
        }
        reload = true;
    }
    else if (action.equals("clearAll"))
    {
        CacheManager.getInstance().clearAll();
        for (String cacheName : CacheManager.getInstance().getCacheNames())
        {
            CacheManager.getInstance().getCache(cacheName).clearStatistics();
        }
        reload = true;
    }
    else if (action.equals("showInMemorySize"))
    {
        inMemorySize = true;
    }
    request.setAttribute("security", security);
    request.setAttribute("pageName", CACHE_PAGE_URL);
    request.setAttribute("reload", reload);
    request.setAttribute("inMemorySize", inMemorySize);

    if (!reload)
    {
        ArrayList<String> names = new ArrayList<String>();
        ArrayList<Cache> caches = new ArrayList<Cache>();
        ArrayList<Statistics> statistics = new ArrayList<Statistics>();
        ArrayList<String> inMemorySizeList = new ArrayList<String>();
        for (String cacheName : CacheManager.getInstance().getCacheNames())
        {
            names.add(cacheName);
        }
        Collections.sort(names);
        for (String cacheName : names)
        {
            Cache currentCache = CacheManager.getInstance().getCache(cacheName);
            caches.add(currentCache);
            statistics.add(currentCache.getStatistics());
            inMemorySizeList.add(inMemorySize ? String.format("%,10d", currentCache.calculateInMemorySize()) : "");
        }


        request.setAttribute("names", names);
        request.setAttribute("caches", caches);
        request.setAttribute("statistics", statistics);
        request.setAttribute("inMemorySizeList", inMemorySizeList);
    }
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="IT" lang="IT">
<head>
    <title>Cache Management</title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <meta name="robots" content="noindex,nofollow"/>
    <meta name="author" content="NewMedia Solutions"/>
    <style type="text/css">
        body {
            margin: 40px;
            padding: 0;
            background: #FFFFFF;
            font: normal 11px Arial;
            color: #404040;
        }

        a, a:visited {
            color: #404040;
        }

        a:hover {
            color: #808080;
        }

        table {
            width: 100%;
            border-collapse: collapse;
        }

        td, th {
            text-align: left;
            padding: 10px 10px 5px 10px;
        }

        th {
            background: #799ecb;
            color: #FFFFFF;
            border: none;
            border-bottom: solid 1px #eaeff8;
            font-size: 900;
        }

        td, th {
            border-bottom: solid 1px #eaeff8;
        }

        tr:hover td {
            background: #eaeff8;
        }

        h1 {
            margin: 0;
            padding: 0;
            font: normal 20px Arial;
            color: #799ecb;
        }

        h2 {
            margin: 0;
            padding: 5px 0 0 0;
            float: left;
            font: normal 15px Arial;
            color: #9ebbd9;
            float: right;
        }

        h2 a, h2 a:visited {
            color: #9ebbd9;
        }

        h2 a:hover {
            color: #808080;
        }

        big {
            font-size: 13px;
            font-weight: 900;
        }

        .clear {
            clear: both;
            margin: 0;
            padding: 0;
            font-size: 0;
        }

        .wrapper {
            margin: 0 auto;
            width: 980px;
        }

        .header {
            height: 35px;
            padding: 0 10px;
            margin: 20px auto 0 auto;
            border-bottom: solid 10px #799ecb;
        }

        .service {
            font-weight: 900;
        }

        .ok {
            color: #008000;
        }

        .ko {
            color: #FF1000;
        }

        .footer {
            border-top: solid 5px #9ebbd9;
            padding: 0 10px;
            color: #808080;
        }

        .footer a, .footer a:visited {
            color: #808080;
        }

        .footer p {
            line-height: 16px;
        }

        a.backTo, a.backTo:visited, a.backTo:hover {
            font: normal 16px Arial;
            color: #799ecb;
        }
    </style>

</head>
<body>
<h1>Cache Management :: <a class="backTo" href="http://<%= SITE_URL%>">Torna a <%= SITE_URL%>
</a></h1>

<c:choose>
    <c:when test="${security ne '' && reload eq false}">
        <p class="menu">
            <a href="${pageName}?security=${security}">Reload</a>
            .::.
            <a href="${pageName}?action=clearAll&security=${security}">Clear All</a>
            .::.
            <a href="${pageName}?action=showInMemorySize&security=${security}">Show in memory size</a>
        </p>
        <table class="data">
            <tr>
                <th>
                    &nbsp;
                </th>
                <th>Name</th>
                <th>Total size</th>
                <th>In memory&nbsp;/&nbsp;On disk size</th>
                <th>Total hits</th>
                <th>In memory&nbsp;/&nbsp;On disk hits</th>
                <c:if test="${inMemorySize}">
                    <th>In memory size (Kb)</th>
                </c:if>
            </tr>
            <c:forEach items="${names}" var="cacheName" varStatus="index">
                <tr>
                    <td>
                        <a href="${pageName}?action=clear&name=${cacheName}&security=${security}">Clear</a>
                    </td>
                    <td class="label">
                            ${cacheName}
                    </td>
                    <td>
                            ${caches[index.count-1].size}
                    </td>
                    <td>
                            ${caches[index.count-1].memoryStoreSize}
                        &nbsp;/&nbsp;
                            ${caches[index.count-1].diskStoreSize}
                    </td>
                    <td>
                            ${statistics[index.count-1].cacheHits}
                    </td>
                    <td>
                            ${statistics[index.count-1].inMemoryHits}
                        &nbsp;/&nbsp;
                            ${statistics[index.count-1].onDiskHits}
                    </td>
                    <c:if test="${inMemorySize}">
                        <td>
                                ${inMemorySizeList[index.count-1]}
                        </td>
                    </c:if>
                </tr>
            </c:forEach>
        </table>
    </c:when>
    <c:when test="${security eq ''}">
        <p>
            Not authorized!
        </p>
    </c:when>
    <c:when test="${reload eq true}">
        <script type="text/javascript" language="javascript">
            <c:url value="${pageName}" var="reloadUrl" >
            <c:param name="security" value="${security}" />
            </c:url>
            document.location.href = '${reloadUrl}';
        </script>
    </c:when>
    <c:otherwise>
        <p>
            Not managed....
        </p>
    </c:otherwise>
</c:choose>
</body>

</html>
