
UPDATE TRANSLATIONS SET KEY_VALUE='Il sito potrebbe essere temporaneamente non disponibile per degli aggiornamenti sul server' WHERE KEY_NAME='message.homeManutenzione' AND LANGUAGE = 'it';
UPDATE TRANSLATIONS SET KEY_VALUE='The site could be temporarily unavailable for updates on the server' WHERE KEY_NAME='message.homeManutenzione' AND LANGUAGE = 'en';
UPDATE TRANSLATIONS SET KEY_VALUE='Le site pourrait être temporairement indisponible pour mises à jour sur le serveur' WHERE KEY_NAME='message.homeManutenzione' AND LANGUAGE = 'fr';
UPDATE TRANSLATIONS SET KEY_VALUE='El sitio podría estar temporalmente no disponible para las actualizaciones en el servidor' WHERE KEY_NAME='message.homeManutenzione' AND LANGUAGE = 'es';
UPDATE TRANSLATIONS SET KEY_VALUE='Könnte die Website vorübergehend nicht verfügbar sein für Updates auf dem Server' WHERE KEY_NAME='message.homeManutenzione' AND LANGUAGE = 'de';


CREATE TABLE `ENUM_COBRAND_TYPES` (
  `COBRAND_TYPE` varchar(20) NOT NULL,
  `LABEL` varchar(200) NOT NULL,
  PRIMARY KEY (`COBRAND_TYPE`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO `ENUM_COBRAND_TYPES` (`COBRAND_TYPE`,`LABEL`) VALUES ('Complete','Cobrand completo (gokick)');
INSERT INTO `ENUM_COBRAND_TYPES` (`COBRAND_TYPE`,`LABEL`) VALUES ('Small','Cobrand senza col dx');

CREATE TABLE COBRANDS 
(
  ID_COBRAND int(11) NOT NULL AUTO_INCREMENT,
  CODE varchar(200) NOT NULL,
  DOMAIN varchar(200) NOT NULL,
  SITE_URL varchar(200) NOT NULL,
  GATEWAY_URL varchar(200) NOT NULL,
  GOOGLE_ANALYTICS_KEY varchar(200),
  HOME_PAGE_URL varchar(200) NOT NULL,
  TYPE varchar(200) NOT NULL,
  PRIMARY KEY (`ID_COBRAND`)
) 
ENGINE=InnoDB DEFAULT CHARSET=utf8;
ALTER TABLE `COBRANDS` ADD CONSTRAINT 
`ENUM_COBRAND_TYPES_COBRANDS_FK1` FOREIGN KEY(`TYPE`) REFERENCES `ENUM_COBRAND_TYPES`(`COBRAND_TYPE`) 
ON DELETE RESTRICT ON UPDATE RESTRICT;
ALTER TABLE `COBRANDS` ADD UNIQUE `IX_COBRANDS_CODE`(`CODE`);

/* #### DEVEL 
INSERT INTO `COBRANDS` (`ID_COBRAND`,`CODE`,`DOMAIN`,`SITE_URL`, `GATEWAY_URL`,`GOOGLE_ANALYTICS_KEY`,`HOME_PAGE_URL`,`TYPE`) 
VALUES (NULL,'GOKICK','localhost','http://localhost:8080', 'http://localhost:8080/cobrand','','http://localhost:8080/home.action','Complete');

INSERT INTO `COBRANDS` (`ID_COBRAND`,`CODE`,`DOMAIN`,`SITE_URL`, `GATEWAY_URL`,`GOOGLE_ANALYTICS_KEY`,`HOME_PAGE_URL`,`TYPE`) 
VALUES (NULL,'METRONEWS','devel-metronews','http://devel-metronews:8080','http://devel-metronews:8080/cobrand','','http://devel-metronews:8080/home.action','Small');
*/

INSERT INTO `COBRANDS` (`ID_COBRAND`,`CODE`,`DOMAIN`,`SITE_URL`, `GATEWAY_URL`,`GOOGLE_ANALYTICS_KEY`,`HOME_PAGE_URL`,`TYPE`) 
VALUES (NULL,'GOKICK','www.gokick.org','http://www.gokick.org', 'http://www.gokick.org/cobrand','UA-13164750-2','http://www.gokick.org/home.action','Complete');

INSERT INTO `COBRANDS` (`ID_COBRAND`,`CODE`,`DOMAIN`,`SITE_URL`, `GATEWAY_URL`,`GOOGLE_ANALYTICS_KEY`,`HOME_PAGE_URL`,`TYPE`) 
VALUES (NULL,'METRONEWS','metronews.gokick.org','http://metronews.gokick.org', 'http://metronews.gokick.org/cobrand','','www.metronews.it/master.php?pagina=gokick.php','Small');

ALTER TABLE `COBRANDS` 
	ADD COLUMN `FACEBOOK_APP_ID` varchar(200) NULL COMMENT '' AFTER `TYPE`, 
	ADD COLUMN `FACEBOOK_APP_CODE` varchar(200) NULL COMMENT '' AFTER `FACEBOOK_APP_ID`;

UPDATE COBRANDS SET FACEBOOK_APP_ID = '391829677541963', FACEBOOK_APP_CODE = '1eeba94a126d5455cbe8f2574646fc51' WHERE CODE = 'METRONEWS';

/*
Aggiornare i valori di app id e code per facebook per i cobrand

aggiungere label
	message.headerTopBarCiao
	
*/


	
ALTER TABLE `USERS` ADD COLUMN `COBRAND_CODE` varchar(200) COMMENT '' AFTER `FACEBOOK_POST_ON_MATCH_RECORDED`;

UPDATE USERS SET COBRAND_CODE = 'GOKICK';

ALTER TABLE USERS ADD CONSTRAINT 
`COBRANDS_USERS_FK1` FOREIGN KEY(`COBRAND_CODE`) REFERENCES `COBRANDS`(`CODE`) 
ON DELETE RESTRICT ON UPDATE RESTRICT;


CREATE TABLE `ENUM_AUDIT_TYPES` (
  `AUDIT_TYPE` varchar(20) NOT NULL,
  `LABEL` varchar(200) NOT NULL,
  PRIMARY KEY (`AUDIT_TYPE`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO `ENUM_AUDIT_TYPES` (`AUDIT_TYPE`,`LABEL`) VALUES ('UserLogin','Login utente');

CREATE TABLE AUDITS 
(
  ID_AUDIT int(11) NOT NULL AUTO_INCREMENT,
  TYPE varchar(200) NOT NULL,
  ID_USER int(11),
  COBRAND_CODE varchar(200),
  IP_ADDRESS varchar(200),
  INFOS varchar(1000),
  REF_DATETIME datetime NOT NULL,
  PRIMARY KEY (`ID_AUDIT`)
) 
ENGINE=InnoDB DEFAULT CHARSET=utf8;

ALTER TABLE `AUDITS` ADD CONSTRAINT 
`ENUM_AUDIT_TYPES_AUDITS_FK1` FOREIGN KEY(`TYPE`) REFERENCES `ENUM_AUDIT_TYPES`(`AUDIT_TYPE`) 
ON DELETE RESTRICT ON UPDATE RESTRICT;

ALTER TABLE `AUDITS` ADD CONSTRAINT 
`USERS_AUDITS_FK1` FOREIGN KEY(`ID_USER`) REFERENCES `USERS`(`ID_USER`) 
ON DELETE RESTRICT ON UPDATE RESTRICT;

ALTER TABLE `AUDITS` ADD CONSTRAINT 
`COBRANDS_AUDITS_FK1` FOREIGN KEY(`COBRAND_CODE`) REFERENCES `COBRANDS`(`CODE`) 
ON DELETE RESTRICT ON UPDATE RESTRICT;

INSERT INTO `TRANSLATIONS` (`ID_TRANSLATION`,`TRANSLATION_TYPE`,`LANGUAGE`,`KEY_NAME`,`KEY_VALUE`,`DESCRIPTION`) VALUES (NULL,'1','it','label.news','News','');
INSERT INTO `TRANSLATIONS` (`ID_TRANSLATION`,`TRANSLATION_TYPE`,`LANGUAGE`,`KEY_NAME`,`KEY_VALUE`,`DESCRIPTION`) VALUES (NULL,'1','pt','label.news','News','');
INSERT INTO `TRANSLATIONS` (`ID_TRANSLATION`,`TRANSLATION_TYPE`,`LANGUAGE`,`KEY_NAME`,`KEY_VALUE`,`DESCRIPTION`) VALUES (NULL,'1','en','label.news','News','');
INSERT INTO `TRANSLATIONS` (`ID_TRANSLATION`,`TRANSLATION_TYPE`,`LANGUAGE`,`KEY_NAME`,`KEY_VALUE`,`DESCRIPTION`) VALUES (NULL,'1','fr','label.news','News','');
INSERT INTO `TRANSLATIONS` (`ID_TRANSLATION`,`TRANSLATION_TYPE`,`LANGUAGE`,`KEY_NAME`,`KEY_VALUE`,`DESCRIPTION`) VALUES (NULL,'1','es','label.news','News','');
INSERT INTO `TRANSLATIONS` (`ID_TRANSLATION`,`TRANSLATION_TYPE`,`LANGUAGE`,`KEY_NAME`,`KEY_VALUE`,`DESCRIPTION`) VALUES (NULL,'1','de','label.news','News','');

INSERT INTO `TRANSLATIONS` (`ID_TRANSLATION`,`TRANSLATION_TYPE`,`LANGUAGE`,`KEY_NAME`,`KEY_VALUE`,`DESCRIPTION`) VALUES (NULL,'1','it','label.boxAltoSinistra','<div class="fixIcon"><img src="/images/icon_gk.png" alt=""/></div>','');
INSERT INTO `TRANSLATIONS` (`ID_TRANSLATION`,`TRANSLATION_TYPE`,`LANGUAGE`,`KEY_NAME`,`KEY_VALUE`,`DESCRIPTION`) VALUES (NULL,'1','pt','label.boxAltoSinistra','<div class="fixIcon"><img src="/images/icon_gk.png" alt=""/></div>','');
INSERT INTO `TRANSLATIONS` (`ID_TRANSLATION`,`TRANSLATION_TYPE`,`LANGUAGE`,`KEY_NAME`,`KEY_VALUE`,`DESCRIPTION`) VALUES (NULL,'1','en','label.boxAltoSinistra','<div class="fixIcon"><img src="/images/icon_gk.png" alt=""/></div>','');
INSERT INTO `TRANSLATIONS` (`ID_TRANSLATION`,`TRANSLATION_TYPE`,`LANGUAGE`,`KEY_NAME`,`KEY_VALUE`,`DESCRIPTION`) VALUES (NULL,'1','fr','label.boxAltoSinistra','<div class="fixIcon"><img src="/images/icon_gk.png" alt=""/></div>','');
INSERT INTO `TRANSLATIONS` (`ID_TRANSLATION`,`TRANSLATION_TYPE`,`LANGUAGE`,`KEY_NAME`,`KEY_VALUE`,`DESCRIPTION`) VALUES (NULL,'1','es','label.boxAltoSinistra','<div class="fixIcon"><img src="/images/icon_gk.png" alt=""/></div>','');
INSERT INTO `TRANSLATIONS` (`ID_TRANSLATION`,`TRANSLATION_TYPE`,`LANGUAGE`,`KEY_NAME`,`KEY_VALUE`,`DESCRIPTION`) VALUES (NULL,'1','de','label.boxAltoSinistra','<div class="fixIcon"><img src="/images/icon_gk.png" alt=""/></div>','');

INSERT INTO `TRANSLATIONS` (`ID_TRANSLATION`,`TRANSLATION_TYPE`,`LANGUAGE`,`KEY_NAME`,`KEY_VALUE`,`DESCRIPTION`) VALUES (NULL,'1','it','message.headerTopBarCiao','Ciao','');
INSERT INTO `TRANSLATIONS` (`ID_TRANSLATION`,`TRANSLATION_TYPE`,`LANGUAGE`,`KEY_NAME`,`KEY_VALUE`,`DESCRIPTION`) VALUES (NULL,'1','pt','message.headerTopBarCiao','Ciao','');
INSERT INTO `TRANSLATIONS` (`ID_TRANSLATION`,`TRANSLATION_TYPE`,`LANGUAGE`,`KEY_NAME`,`KEY_VALUE`,`DESCRIPTION`) VALUES (NULL,'1','en','message.headerTopBarCiao','Hi','');
INSERT INTO `TRANSLATIONS` (`ID_TRANSLATION`,`TRANSLATION_TYPE`,`LANGUAGE`,`KEY_NAME`,`KEY_VALUE`,`DESCRIPTION`) VALUES (NULL,'1','fr','message.headerTopBarCiao','Salut','');
INSERT INTO `TRANSLATIONS` (`ID_TRANSLATION`,`TRANSLATION_TYPE`,`LANGUAGE`,`KEY_NAME`,`KEY_VALUE`,`DESCRIPTION`) VALUES (NULL,'1','es','message.headerTopBarCiao','Hole','');
INSERT INTO `TRANSLATIONS` (`ID_TRANSLATION`,`TRANSLATION_TYPE`,`LANGUAGE`,`KEY_NAME`,`KEY_VALUE`,`DESCRIPTION`) VALUES (NULL,'1','de','message.headerTopBarCiao','Hallo','');

UPDATE `TRANSLATIONS` SET `KEY_VALUE`=', ¡Bienvenido a la comunidad internacional del futbol jugado!' WHERE (`ID_TRANSLATION`='14041');
UPDATE `TRANSLATIONS` SET `KEY_VALUE`=', Willkommen bei der internationalen Fußballgemeinschaft!\r\n' WHERE (`ID_TRANSLATION`='13150');  
UPDATE `TRANSLATIONS` SET `KEY_VALUE`=', Bienvenue dans la Communauté internationale du football !' WHERE (`ID_TRANSLATION`='10630');  
UPDATE `TRANSLATIONS` SET `KEY_VALUE`=', Welcome to the international community of football players! ' WHERE (`ID_TRANSLATION`='9794');  
UPDATE `TRANSLATIONS` SET `KEY_VALUE`=', Benvenuto nella community internazionale del calcio giocato!\r\n    ' WHERE (`ID_TRANSLATION`='663');  

UPDATE `LANGUAGES` SET `LABEL`='Español' WHERE (`LANGUAGE`='es');
UPDATE `LANGUAGES` SET `LABEL`='Français' WHERE (`LANGUAGE`='fr');
UPDATE `LANGUAGES` SET `LABEL`='Deutsch' WHERE (`LANGUAGE`='de');

INSERT INTO `TRANSLATIONS` (`ID_TRANSLATION`,`TRANSLATION_TYPE`,`LANGUAGE`,`KEY_NAME`,`KEY_VALUE`,`DESCRIPTION`) VALUES (NULL,'9','it','facebook.postSiteName','www.gokick.org','');
INSERT INTO `TRANSLATIONS` (`ID_TRANSLATION`,`TRANSLATION_TYPE`,`LANGUAGE`,`KEY_NAME`,`KEY_VALUE`,`DESCRIPTION`) VALUES (NULL,'9','pt','facebook.postSiteName','www.gokick.org','');
INSERT INTO `TRANSLATIONS` (`ID_TRANSLATION`,`TRANSLATION_TYPE`,`LANGUAGE`,`KEY_NAME`,`KEY_VALUE`,`DESCRIPTION`) VALUES (NULL,'9','en','facebook.postSiteName','www.gokick.org','');
INSERT INTO `TRANSLATIONS` (`ID_TRANSLATION`,`TRANSLATION_TYPE`,`LANGUAGE`,`KEY_NAME`,`KEY_VALUE`,`DESCRIPTION`) VALUES (NULL,'9','fr','facebook.postSiteName','www.gokick.org','');
INSERT INTO `TRANSLATIONS` (`ID_TRANSLATION`,`TRANSLATION_TYPE`,`LANGUAGE`,`KEY_NAME`,`KEY_VALUE`,`DESCRIPTION`) VALUES (NULL,'9','es','facebook.postSiteName','www.gokick.org','');
INSERT INTO `TRANSLATIONS` (`ID_TRANSLATION`,`TRANSLATION_TYPE`,`LANGUAGE`,`KEY_NAME`,`KEY_VALUE`,`DESCRIPTION`) VALUES (NULL,'9','de','facebook.postSiteName','www.gokick.org','');

