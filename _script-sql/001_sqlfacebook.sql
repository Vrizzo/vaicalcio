ALTER TABLE `USERS` MODIFY COLUMN `ID_FACEBOOK` varchar(200) NULL DEFAULT NULL AFTER `PLAYED_CHALLENGES`;

INSERT INTO `SITE_CONFIGURATIONS` (`KEY_CONF`,`VALUE_CONF`,`DESCRIPTION`) 
VALUES ('configuration.faceBookApp.url','http://www.facebook.com/dialog/oauth?scope=read_stream,publish_stream,offline_access','Url per autenticarsi su facebbok, contiene già dei parametri')

INSERT INTO `SITE_CONFIGURATIONS` (`KEY_CONF`,`VALUE_CONF`,`DESCRIPTION`) 
VALUES ('configuration.facebookWs.url','http://preview.gokick.org/social/facebook?wsdl','url per l''uso dei servizio newmedia-social');
INSERT INTO `SITE_CONFIGURATIONS` (`KEY_CONF`,`VALUE_CONF`,`DESCRIPTION`) 
VALUES ('configuration.facebookWs.timeout','60000','timeout per l''uso dei servizio newmedia-social');
INSERT INTO `SITE_CONFIGURATIONS` (`KEY_CONF`,`VALUE_CONF`,`DESCRIPTION`) 
VALUES ('configuration.facebookWs.username','preview','username per l''uso dei servizio newmedia-social');
INSERT INTO `SITE_CONFIGURATIONS` (`KEY_CONF`,`VALUE_CONF`,`DESCRIPTION`) 
VALUES ('configuration.facebookWs.password','test','password per l''uso dei servizio newmedia-social');
UPDATE `SITE_CONFIGURATIONS`
SET 
	`KEY_CONF` = REPLACE(`KEY_CONF`, 'faceBook', 'facebook')
WHERE 
	`KEY_CONF` LIKE '%face%';
	  
ALTER TABLE `USERS` 
	CHANGE COLUMN `ID_FACEBOOK` `FACEBOOK_ID_USER` varchar(200) character set utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '' AFTER `PLAYED_CHALLENGES`, 
	CHANGE COLUMN `ACCESS_TOKEN_FACEBOOK` `FACEBOOK_ACCESS_TOKEN` varchar(1000) character set utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '' AFTER `FACEBOOK_ID_USER`, 
	ADD COLUMN `FACEBOOK_POST_ON_MATCH_CREATION` tinyint(4) NOT NULL DEFAULT 1 COMMENT '' AFTER `FACEBOOK_ACCESS_TOKEN`, 
	ADD COLUMN `FACEBOOK_POST_ON_MATCH_REGISTRATION` tinyint(4) NOT NULL DEFAULT 1 COMMENT '' AFTER `FACEBOOK_POST_ON_MATCH_CREATION`, 
	ADD COLUMN `FACEBOOK_POST_ON_MATCH_RECORDED` tinyint(4) NOT NULL DEFAULT 1 COMMENT '' AFTER `FACEBOOK_POST_ON_MATCH_REGISTRATION`;
	  