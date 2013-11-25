
INSERT INTO `TRANSLATIONS` (`TRANSLATION_TYPE`,`LANGUAGE`,`KEY_NAME`,`KEY_VALUE`,`REPLACEMENT_ENABLED`,`HTML_ENABLED`,`DESCRIPTION`) 
VALUES (
'6',
'it',
'text.facebook.registrazione',
'      <p>
        L''autenticazione tramite facebook è avvenuta con successo, ma non stato possibile associare il tuo utente di facebbok ad un utente di GoKick.
      </p>
        
      <p>
        Se sei già un utente di GoKick usando però una email diversa da quella di facebook dovresti effettuare, solo per questa volta, il login anche con i dati di accesso di GoKick.
      </p>
       
      <p>
        Se invece non ti sei mai registrato a GoKick, clicca <a href="@@@SITE_URL@@@/userIntro" >qui</a> per farlo ora!
      </p>
',
'1',
'1',
'login con facebook: spiegazione');


INSERT INTO `TRANSLATIONS` (`TRANSLATION_TYPE`,`LANGUAGE`,`KEY_NAME`,`KEY_VALUE`,`DESCRIPTION`) 
VALUES 
('8','it','format.date_20','%1$td %1$tB h %1$tR','22 Novembre h 18:00');
	  
INSERT INTO `TRANSLATIONS` (`TRANSLATION_TYPE`,`LANGUAGE`,`KEY_NAME`,`KEY_VALUE`,`REPLACEMENT_ENABLED`,`HTML_ENABLED`,`DESCRIPTION`) 
VALUES 
( '1', 'it', 'label.preferenzeCondivisioneFacebook', '<strong>Condivisione Facebook</strong>&nbsp;-&nbsp;avvisa i miei amici FB quando:', '1', '1', 'Scheda preferenze titolo condivisione facebook');

INSERT INTO `TRANSLATIONS` (`TRANSLATION_TYPE`,`LANGUAGE`,`KEY_NAME`,`KEY_VALUE`,`REPLACEMENT_ENABLED`,`HTML_ENABLED`,`DESCRIPTION`) 
VALUES 
( '1', 'it', 'label.preferencesPostFacebookPartita', 'organizzo una partita', '1', '1', '');

INSERT INTO `TRANSLATIONS` (`TRANSLATION_TYPE`,`LANGUAGE`,`KEY_NAME`,`KEY_VALUE`,`REPLACEMENT_ENABLED`,`HTML_ENABLED`,`DESCRIPTION`) 
VALUES 
( '1', 'it', 'label.preferencesPostFacebookIscrizione', 'mi iscrivo a una partita', '1', '1', '');

INSERT INTO `TRANSLATIONS` (`TRANSLATION_TYPE`,`LANGUAGE`,`KEY_NAME`,`KEY_VALUE`,`REPLACEMENT_ENABLED`,`HTML_ENABLED`,`DESCRIPTION`) 
VALUES 
( '1', 'it', 'label.preferencesPostFacebookPagelle', 'pubblico le pagelle di una partita', '1', '1', '');

INSERT INTO `ENUM_TRANSLATION_TYPES` (`TRANSLATION_TYPE`,`LABEL`) VALUES ('9','FACEBOOK');

INSERT INTO `TRANSLATIONS` (`TRANSLATION_TYPE`,`LANGUAGE`,`KEY_NAME`,`KEY_VALUE`,`REPLACEMENT_ENABLED`,`HTML_ENABLED`,`DESCRIPTION`) 
VALUES 
( '9', 'it', 'facebook.postOnMatchCreation.picture_calcio', 'http://preview.gokick.org/contents/facebook/facebook_calcio_it.png', '0', '1', 'Immagine per il post di facebook per calcio it');

INSERT INTO `TRANSLATIONS` (`TRANSLATION_TYPE`,`LANGUAGE`,`KEY_NAME`,`KEY_VALUE`,`REPLACEMENT_ENABLED`,`HTML_ENABLED`,`DESCRIPTION`) 
VALUES 
( '9', 'it', 'facebook.postOnMatchCreation.message_calcio', 'Ho organizzato una partita di calcio su GoKick - The 1st Sport Community', '0', '1', 'Messaggio per il post di facebook per calcio it');

INSERT INTO `TRANSLATIONS` (`TRANSLATION_TYPE`,`LANGUAGE`,`KEY_NAME`,`KEY_VALUE`,`REPLACEMENT_ENABLED`,`HTML_ENABLED`,`DESCRIPTION`) 
VALUES 
( '9', 'it', 'facebook.postOnMatchRegistration.message_calcio', 'Mi sono iscirtto a una partita di calcio su GoKick - The 1st Sport Community', '0', '1', 'Messaggio per il post di facebook per calcio it');

INSERT INTO `TRANSLATIONS` (`TRANSLATION_TYPE`,`LANGUAGE`,`KEY_NAME`,`KEY_VALUE`,`REPLACEMENT_ENABLED`,`HTML_ENABLED`,`DESCRIPTION`) 
VALUES 
( '9', 'it', 'facebook.postOnMatchRecorded.message_calcio', 'Ho giocato una partita di calcio su GoKick - The 1st Sport Community', '0', '1', 'messaggio post di facebook per calcio it');

INSERT INTO `TRANSLATIONS` (`TRANSLATION_TYPE`,`LANGUAGE`,`KEY_NAME`,`KEY_VALUE`,`REPLACEMENT_ENABLED`,`HTML_ENABLED`,`DESCRIPTION`) 
VALUES 
( '1', 'it', 'label.orario', 'Orario', '0', '0', '');


INSERT INTO `TRANSLATIONS` (`TRANSLATION_TYPE`,`LANGUAGE`,`KEY_NAME`,`KEY_VALUE`,`REPLACEMENT_ENABLED`,`HTML_ENABLED`,`DESCRIPTION`) 
SELECT 
	TRANSLATIONS.`TRANSLATION_TYPE`,LL.LANGUAGE,TRANSLATIONS.`KEY_NAME`, CONCAT( '#TODO#', TRANSLATIONS.`KEY_VALUE`),TRANSLATIONS.`REPLACEMENT_ENABLED`,TRANSLATIONS.`HTML_ENABLED`,TRANSLATIONS.`DESCRIPTION`
FROM 
TRANSLATIONS,
(
SELECT NULL AS TRANSLATION_TYPE, 'pt' AS LANGUAGE, NULL AS KEY_NAME, NULL AS KEY_VALUE,  NULL AS REPLACEMENT_ENABLED, NULL AS HTML_ENABLED, NULL AS DESCRIPTION FROM DUAL
UNION ALL
SELECT NULL AS TRANSLATION_TYPE, 'en' AS LANGUAGE, NULL AS KEY_NAME, NULL AS KEY_VALUE,  NULL AS REPLACEMENT_ENABLED, NULL AS HTML_ENABLED, NULL AS DESCRIPTION FROM DUAL
UNION ALL
SELECT NULL AS TRANSLATION_TYPE, 'fr' AS LANGUAGE, NULL AS KEY_NAME, NULL AS KEY_VALUE,  NULL AS REPLACEMENT_ENABLED, NULL AS HTML_ENABLED, NULL AS DESCRIPTION FROM DUAL
UNION ALL
SELECT NULL AS TRANSLATION_TYPE, 'es' AS LANGUAGE, NULL AS KEY_NAME, NULL AS KEY_VALUE,  NULL AS REPLACEMENT_ENABLED, NULL AS HTML_ENABLED, NULL AS DESCRIPTION FROM DUAL
UNION ALL
SELECT NULL AS TRANSLATION_TYPE, 'de' AS LANGUAGE, NULL AS KEY_NAME, NULL AS KEY_VALUE,  NULL AS REPLACEMENT_ENABLED, NULL AS HTML_ENABLED, NULL AS DESCRIPTION FROM DUAL
) AS LL
WHERE
TRANSLATIONS.`LANGUAGE` = 'it' and TRANSLATIONS.`KEY_NAME` in(
 'format.date_20',
 'label.preferenzeCondivisioneFacebook',
 'label.preferencesPostFacebookPartita',
 'label.preferencesPostFacebookIscrizione',
 'label.preferencesPostFacebookPagelle',
 'text.facebook.registrazione',
 'facebook.postOnMatchCreation.picture_calcio',
 'facebook.postOnMatchCreation.message_calcio',
 'facebook.postOnMatchRegistration.message_calcio',
 'facebook.postOnMatchRecorded.message_calcio',
 'label.orario');




