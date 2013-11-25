<@s.property value="%{getText('${parameters.key}','${parameters.lang}')}"
escape="%{getTextEscapeHtml('${parameters.key?html}','${parameters.lang}')}"
escapeJavaScript="${parameters.get('escapeJavaScript')?html}"  />