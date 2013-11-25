<@s.property value="%{getTextArgs('${parameters.key?html}', '${parameters.arg01}', '${parameters.arg02}', '${parameters.arg03}', '${parameters.arg04}')}"
escape="%{getTextEscapeHtml('${parameters.key?html}')}"
escapeJavaScript="${parameters.get('escapeJavaScript')?html}"  />