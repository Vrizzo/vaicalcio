<%@ page contentType="text/html" pageEncoding="UTF-8" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="u" tagdir="/WEB-INF/tags/" %>
<u:showForCobrands codes="GOKICK">
  <div class="header">
    <a href="<s:url action='calendar!viewCalendar' ><s:param name='defaultSearch' value='true' /></s:url>">
      <img src="<s:url value="/images/header.jpg" encode="false" />" alt="www.gokick.org"  />
    </a>
    <u:loggedOut>
    <ul class="langBar">
      <s:iterator value="languageList">
        <s:if test="userContext.language.language == language">
          <li>
            <img src="<s:url value="/images/languages/" encode="false" />${language}.png"/>
          </li>
        </s:if>
        <s:else>
          <li>
            <a href="<s:url includeParams="all" includeContext="true" ><s:param name="request_locale" value="locale" /></s:url>">
                <img src="<s:url value="/images/languages/" encode="false" />${language}.png"/>
            </a>
          </li>
        </s:else>
      </s:iterator>
    </ul>
    </u:loggedOut>
  </div>
</u:showForCobrands>
<u:showForCobrands codes="METRONEWS">
  <div class="header">
    <a href="<s:url action='calendar!viewCalendar' ><s:param name='defaultSearch' value='true' /></s:url>">
      <img src="<s:url value="/images/header_metrogokick.jpg" encode="false" />" alt="www.gokick.org"  />
    </a>
    <u:loggedOut>
      <ul class="langBar">
        <s:iterator value="languageList">
          <s:if test="userContext.language.language == language">
            <li>
              <img src="<s:url value="/images/languages/" encode="false" />${language}.png"/>
            </li>
          </s:if>
          <s:else>
            <li>
              <a href="<s:url includeParams="all" includeContext="true" ><s:param name="request_locale" value="locale" /></s:url>">
                <img src="<s:url value="/images/languages/" encode="false" />${language}.png"/>
              </a>
            </li>
          </s:else>
        </s:iterator>
      </ul>
    </u:loggedOut>
  </div>
</u:showForCobrands>
<u:showForCobrands codes="PROUSALL">
  <div class="header">
    <a href="<s:url action='calendar!viewCalendar' ><s:param name='defaultSearch' value='true' /></s:url>">
      <img src="<s:url value="/images/header_prousall.jpg" encode="false" />" alt="www.gokick.org"  />
    </a>
    <u:loggedOut>
      <ul class="langBar">
        <s:iterator value="languageList">
          <s:if test="userContext.language.language == language">
            <li>
              <img src="<s:url value="/images/languages/" encode="false" />${language}.png"/>
            </li>
          </s:if>
          <s:else>
            <li>
              <a href="<s:url includeParams="all" includeContext="true" ><s:param name="request_locale" value="locale" /></s:url>">
                <img src="<s:url value="/images/languages/" encode="false" />${language}.png"/>
              </a>
            </li>
          </s:else>
        </s:iterator>
      </ul>
    </u:loggedOut>
  </div>
</u:showForCobrands>
<u:showForCobrands codes="MOSCOVA">
  <div class="header">
    <a href="<s:url action='calendar!viewCalendar' ><s:param name='defaultSearch' value='true' /></s:url>">
      <img src="<s:url value="/images/header_moscova.jpg" encode="false" />" alt="www.gokick.org"  />
    </a>
    <u:loggedOut>
      <ul class="langBar">
        <s:iterator value="languageList">
          <s:if test="userContext.language.language == language">
            <li>
              <img src="<s:url value="/images/languages/" encode="false" />${language}.png"/>
            </li>
          </s:if>
          <s:else>
            <li>
              <a href="<s:url includeParams="all" includeContext="true" ><s:param name="request_locale" value="locale" /></s:url>">
                <img src="<s:url value="/images/languages/" encode="false" />${language}.png"/>
              </a>
            </li>
          </s:else>
        </s:iterator>
      </ul>
    </u:loggedOut>
  </div>
</u:showForCobrands>

<u:printLastMessage />