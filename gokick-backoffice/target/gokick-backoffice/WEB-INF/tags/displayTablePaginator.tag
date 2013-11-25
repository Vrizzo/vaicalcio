<%@tag description="Paginatore standard da usare in tutte le tabelle" pageEncoding="UTF-8" %>
<%@ taglib prefix="display" uri="/WEB-INF/taglib/displaytag-12.tld" %>
<display:setProperty name="paging.banner.group_size" value="1000"/>
<display:setProperty name="paging.banner.full">
    <p class="pagelinks">
        <a href="{2}">&laquo;</a>
        {0}
        <a href="{3}">&raquo;</a>
    </p>
</display:setProperty>
<display:setProperty name="paging.banner.first">
    <p class="pagelinks">
        <big>&laquo;</big>
        {0}
        <big>
            <a href="{3}">&raquo;</a>
        </big>
    </p>
</display:setProperty>
<display:setProperty name="paging.banner.last">
    <p class="pagelinks">
        <big>
            <a href="{2}">&laquo;</a>
        </big>
        {0}
        <big>&raquo;</big>
    </p>
</display:setProperty>
<display:setProperty name="paging.banner.onepage">
    <p class="pagelinks">
    <span>
      {0}
    </span>
    </p>
</display:setProperty>
