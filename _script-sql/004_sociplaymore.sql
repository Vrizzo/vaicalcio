
INSERT INTO `TRANSLATIONS` (`ID_TRANSLATION`,`TRANSLATION_TYPE`,`LANGUAGE`,`KEY_NAME`,`KEY_VALUE`,`DESCRIPTION`) VALUES 
(NULL,'1','it','label.title.partiteOrganizzate','partite organizzate',''),
(NULL,'1','pt','label.title.partiteOrganizzate','jogos organizados',''),
(NULL,'1','en','label.title.partiteOrganizzate','matches organized',''),
(NULL,'1','fr','label.title.partiteOrganizzate','rencontres organisées',''),
(NULL,'1','es','label.title.partiteOrganizzate','partidos organizados',''),
(NULL,'1','de','label.title.partiteOrganizzate','Spiele organisiert','');

INSERT INTO `TRANSLATIONS` (`ID_TRANSLATION`,`TRANSLATION_TYPE`,`LANGUAGE`,`KEY_NAME`,`KEY_VALUE`,`DESCRIPTION`) VALUES 
(NULL,'1','it','label.playMore.renewLink','http://www.gokick.org/services/associazione_iscrizioni.php',''),
(NULL,'1','pt','label.playMore.renewLink','http://www.gokick.org/services/associazione_iscrizioni.php',''),
(NULL,'1','en','label.playMore.renewLink','http://www.gokick.org/services/associazione_iscrizioni.php',''),
(NULL,'1','fr','label.playMore.renewLink','http://www.gokick.org/services/associazione_iscrizioni.php',''),
(NULL,'1','es','label.playMore.renewLink','http://www.gokick.org/services/associazione_iscrizioni.php',''),
(NULL,'1','de','label.playMore.renewLink','http://www.gokick.org/services/associazione_iscrizioni.php','');

ALTER TABLE `PlayMore_APS`.`anagrafica` 
ADD INDEX `IX_GOKICKER` (`id_gokicker` ASC) ;

CREATE VIEW `GoKick`.`V_PLAYMORE_PARTNERS` AS
select
	PlayMore_APS.anagrafica.id_gokicker as ID_USER,
	cast(`PlayMore_APS`.`anagrafica`.`tipo_socio` as SIGNED) as PARTNER_TYPE,
	PlayMore_APS.anagrafica.data_scadenza as EXPIRE_DATE
from
	PlayMore_APS.anagrafica;

	/*
UPDATE `TRANSLATIONS` SET 
`KEY_VALUE` = 'Associazione Play More! - Socio Superstar'
WHERE `KEY_NAME` = 'label.title.superstar-active';
UPDATE `TRANSLATIONS` SET 
`KEY_VALUE` = 'Associazione Play More! - Socio Superstar (rinnovo entro {0} giorni)'
WHERE `KEY_NAME` = 'label.title.superstar-active-near-expire';
UPDATE `TRANSLATIONS` SET 
`KEY_VALUE` = 'Associazione Play More! - Quota associativa scaduta (ancora {0} giorni per rinnovare)'
WHERE `KEY_NAME` = 'label.title.superstar-active-expired';
UPDATE `TRANSLATIONS` SET 
`KEY_VALUE` = 'Associazione Play More! - Socio Supporter'
WHERE `KEY_NAME` = 'label.title.supporter-active';
UPDATE `TRANSLATIONS` SET 
`KEY_VALUE` = 'Associazione Play More! - Socio Supporter (rinnovo entro {0} giorni)'
WHERE `KEY_NAME` = 'label.title.supporter-active-near-expire';
UPDATE `TRANSLATIONS` SET 
`KEY_VALUE` = 'Associazione Play More! - Quota associativa scaduta (ancora {0} giorni per rinnovare)'
WHERE `KEY_NAME` = 'label.title.supporter-active-expired';
*/

	
INSERT INTO `TRANSLATIONS` (`ID_TRANSLATION`,`TRANSLATION_TYPE`,`LANGUAGE`,`KEY_NAME`,`KEY_VALUE`,`DESCRIPTION`,`REPLACEMENT_ENABLED`) VALUES 
(NULL,'1','it','label.title.superstar-active','Associazione Play More! - Socio Superstar','', 0),
(NULL,'1','pt','label.title.superstar-active','Associazione Play More! - Socio Superstar','', 0),
(NULL,'1','en','label.title.superstar-active','Associazione Play More! - Socio Superstar','', 0),
(NULL,'1','fr','label.title.superstar-active','Associazione Play More! - Socio Superstar','', 0),
(NULL,'1','es','label.title.superstar-active','Associazione Play More! - Socio Superstar','', 0),
(NULL,'1','de','label.title.superstar-active','Associazione Play More! - Socio Superstar','', 0);
INSERT INTO `TRANSLATIONS` (`ID_TRANSLATION`,`TRANSLATION_TYPE`,`LANGUAGE`,`KEY_NAME`,`KEY_VALUE`,`DESCRIPTION`,`REPLACEMENT_ENABLED`) VALUES 
(NULL,'1','it','label.title.superstar-active-near-expire','Associazione Play More! - Socio Superstar (rinnovo entro {0} giorni)','', 1),
(NULL,'1','pt','label.title.superstar-active-near-expire','Associazione Play More! - Socio Superstar (rinnovo entro {0} giorni)','', 1),
(NULL,'1','en','label.title.superstar-active-near-expire','Associazione Play More! - Socio Superstar (rinnovo entro {0} giorni)','', 1),
(NULL,'1','fr','label.title.superstar-active-near-expire','Associazione Play More! - Socio Superstar (rinnovo entro {0} giorni)','', 1),
(NULL,'1','es','label.title.superstar-active-near-expire','Associazione Play More! - Socio Superstar (rinnovo entro {0} giorni)','', 1),
(NULL,'1','de','label.title.superstar-active-near-expire','Associazione Play More! - Socio Superstar (rinnovo entro {0} giorni)','', 1);
INSERT INTO `TRANSLATIONS` (`ID_TRANSLATION`,`TRANSLATION_TYPE`,`LANGUAGE`,`KEY_NAME`,`KEY_VALUE`,`DESCRIPTION`,`REPLACEMENT_ENABLED`) VALUES 
(NULL,'1','it','label.title.superstar-active-expired','Associazione Play More! - Quota associativa scaduta (ancora {0} giorni per rinnovare)','', 1),
(NULL,'1','pt','label.title.superstar-active-expired','Associazione Play More! - Quota associativa scaduta (ancora {0} giorni per rinnovare)','', 1),
(NULL,'1','en','label.title.superstar-active-expired','Associazione Play More! - Quota associativa scaduta (ancora {0} giorni per rinnovare)','', 1),
(NULL,'1','fr','label.title.superstar-active-expired','Associazione Play More! - Quota associativa scaduta (ancora {0} giorni per rinnovare)','', 1),
(NULL,'1','es','label.title.superstar-active-expired','Associazione Play More! - Quota associativa scaduta (ancora {0} giorni per rinnovare)','', 1),
(NULL,'1','de','label.title.superstar-active-expired','Associazione Play More! - Quota associativa scaduta (ancora {0} giorni per rinnovare)','', 1);
INSERT INTO `TRANSLATIONS` (`ID_TRANSLATION`,`TRANSLATION_TYPE`,`LANGUAGE`,`KEY_NAME`,`KEY_VALUE`,`DESCRIPTION`,`REPLACEMENT_ENABLED`) VALUES 
(NULL,'1','it','label.title.supporter-active','Associazione Play More! - Socio Supporter','', 0),
(NULL,'1','pt','label.title.supporter-active','Associazione Play More! - Socio Supporter','', 0),
(NULL,'1','en','label.title.supporter-active','Associazione Play More! - Socio Supporter','', 0),
(NULL,'1','fr','label.title.supporter-active','Associazione Play More! - Socio Supporter','', 0),
(NULL,'1','es','label.title.supporter-active','Associazione Play More! - Socio Supporter','', 0),
(NULL,'1','de','label.title.supporter-active','Associazione Play More! - Socio Supporter','', 0);
INSERT INTO `TRANSLATIONS` (`ID_TRANSLATION`,`TRANSLATION_TYPE`,`LANGUAGE`,`KEY_NAME`,`KEY_VALUE`,`DESCRIPTION`,`REPLACEMENT_ENABLED`) VALUES 
(NULL,'1','it','label.title.supporter-active-near-expire','Associazione Play More! - Socio Supporter (rinnovo entro {0} giorni)','', 1),
(NULL,'1','pt','label.title.supporter-active-near-expire','Associazione Play More! - Socio Supporter (rinnovo entro {0} giorni)','', 1),
(NULL,'1','en','label.title.supporter-active-near-expire','Associazione Play More! - Socio Supporter (rinnovo entro {0} giorni)','', 1),
(NULL,'1','fr','label.title.supporter-active-near-expire','Associazione Play More! - Socio Supporter (rinnovo entro {0} giorni)','', 1),
(NULL,'1','es','label.title.supporter-active-near-expire','Associazione Play More! - Socio Supporter (rinnovo entro {0} giorni)','', 1),
(NULL,'1','de','label.title.supporter-active-near-expire','Associazione Play More! - Socio Supporter (rinnovo entro {0} giorni)','', 1);
INSERT INTO `TRANSLATIONS` (`ID_TRANSLATION`,`TRANSLATION_TYPE`,`LANGUAGE`,`KEY_NAME`,`KEY_VALUE`,`DESCRIPTION`,`REPLACEMENT_ENABLED`) VALUES 
(NULL,'1','it','label.title.supporter-active-expired','Associazione Play More! - Quota associativa scaduta (ancora {0} giorni per rinnovare)','', 1),
(NULL,'1','pt','label.title.supporter-active-expired','Associazione Play More! - Quota associativa scaduta (ancora {0} giorni per rinnovare)','', 1),
(NULL,'1','en','label.title.supporter-active-expired','Associazione Play More! - Quota associativa scaduta (ancora {0} giorni per rinnovare)','', 1),
(NULL,'1','fr','label.title.supporter-active-expired','Associazione Play More! - Quota associativa scaduta (ancora {0} giorni per rinnovare)','', 1),
(NULL,'1','es','label.title.supporter-active-expired','Associazione Play More! - Quota associativa scaduta (ancora {0} giorni per rinnovare)','', 1),
(NULL,'1','de','label.title.supporter-active-expired','Associazione Play More! - Quota associativa scaduta (ancora {0} giorni per rinnovare)','', 1);

INSERT INTO `ENUM_EMAIL_CONFIGURATION_TYPES` (`EMAIL_CONFIGURATION_TYPE`, `LABEL`) VALUES 
('playMore.greaterThanZero', 'Email di notifica per gli utenti associati paly more che stanno per scadere'),
('playMore.equalZero', 'Email di notifica per gli utenti associati paly more che scadono nel giorno corrente'),
('playMore.lessThanZero', 'Email di notifica per gli utenti associati paly more che sono scaduti');


INSERT INTO `EMAIL_CONFIGURATIONS` (`EMAIL_CONFIGURATION_TYPE`, `KEY_NAME_SUBJECT`, `KEY_NAME_BODY`, `SMTP_SERVER`, `SMTP_AUTHENTICATION_ENABLED`, `SMTP_USER_NAME`, `SMTP_PASSWORD`, `SMTP_PORT`, `SMTP_SSL_ENABLED`, `FROM_NAME`, `FROM_EMAIL`, `TO`, `CC`, `BCC`, `HTML_FORMAT_ENABLED`, `IMAGES_FOLDER_PATH`, `ENABLED`) 
VALUES 
('playMore.greaterThanZero', 'email.subject.playMore.greaterThanZero', 'email.body.playMore.greaterThanZero', 'smtp', '1', 'noreply@gokick.org', 'fake', '465', '1', 'Associazione Play More!', 'playmore@gokick.org', '', 'playmore@gokick.org', 'playmore@gokick.org', '1', '', 1),
('playMore.equalZero', 'email.subject.playMore.equalZero', 'email.body.playMore.equalZero', 'smtp', '1', 'noreply@gokick.org', 'fake', '465', '1', 'Associazione Play More!', 'playmore@gokick.org', '', 'playmore@gokick.org', 'playmore@gokick.org', '1', '', 1),
('playMore.lessThanZero', 'email.subject.playMore.lessThanZero', 'email.body.playMore.lessThanZero', 'smtp', '1', 'noreply@gokick.org', 'fake', '465', '1', 'Associazione Play More!', 'playmore@gokick.org', '', 'playmore@gokick.org', 'playmore@gokick.org', '1', '', 1);

/*
delete from `TRANSLATIONS`
where `KEY_NAME` like 'email.subject.playMore%';

delete from `TRANSLATIONS`
where `KEY_NAME` like 'email.body.playMore%';
*/

INSERT INTO `TRANSLATIONS` (`ID_TRANSLATION`,`TRANSLATION_TYPE`,`LANGUAGE`,`KEY_NAME`,`KEY_VALUE`, `REPLACEMENT_ENABLED`, `DESCRIPTION`) VALUES 
(NULL,'1','it','email.subject.playMore.greaterThanZero','Associazione Play More! - Rinnova la tua quota -###PLAY_MORE_EXPIRE_DAYS### giorni','1', ''),
(NULL,'1','pt','email.subject.playMore.greaterThanZero','Associazione Play More! - Rinnova la tua quota -###PLAY_MORE_EXPIRE_DAYS### giorni','1', ''),
(NULL,'1','en','email.subject.playMore.greaterThanZero','Associazione Play More! - Rinnova la tua quota -###PLAY_MORE_EXPIRE_DAYS### giorni','1', ''),
(NULL,'1','fr','email.subject.playMore.greaterThanZero','Associazione Play More! - Rinnova la tua quota -###PLAY_MORE_EXPIRE_DAYS### giorni','1', ''),
(NULL,'1','es','email.subject.playMore.greaterThanZero','Associazione Play More! - Rinnova la tua quota -###PLAY_MORE_EXPIRE_DAYS### giorni','1', ''),
(NULL,'1','de','email.subject.playMore.greaterThanZero','Associazione Play More! - Rinnova la tua quota -###PLAY_MORE_EXPIRE_DAYS### giorni','1', '');
INSERT INTO `TRANSLATIONS` (`ID_TRANSLATION`,`TRANSLATION_TYPE`,`LANGUAGE`,`KEY_NAME`,`KEY_VALUE`, `REPLACEMENT_ENABLED`, `DESCRIPTION`) VALUES 
(NULL,'1','it','email.body.playMore.greaterThanZero','email.body.playMore.greaterThanZero','1', ''),
(NULL,'1','pt','email.body.playMore.greaterThanZero','email.body.playMore.greaterThanZero','1', ''),
(NULL,'1','en','email.body.playMore.greaterThanZero','email.body.playMore.greaterThanZero','1', ''),
(NULL,'1','fr','email.body.playMore.greaterThanZero','email.body.playMore.greaterThanZero','1', ''),
(NULL,'1','es','email.body.playMore.greaterThanZero','email.body.playMore.greaterThanZero','1', ''),
(NULL,'1','de','email.body.playMore.greaterThanZero','email.body.playMore.greaterThanZero','1', '');

INSERT INTO `TRANSLATIONS` (`ID_TRANSLATION`,`TRANSLATION_TYPE`,`LANGUAGE`,`KEY_NAME`,`KEY_VALUE`, `REPLACEMENT_ENABLED`, `DESCRIPTION`) VALUES 
(NULL,'1','it','email.subject.playMore.equalZero','Associazione Play More! - Rinnova la tua quota','1', ''),
(NULL,'1','pt','email.subject.playMore.equalZero','Associazione Play More! - Rinnova la tua quota','1', ''),
(NULL,'1','en','email.subject.playMore.equalZero','Associazione Play More! - Rinnova la tua quota','1', ''),
(NULL,'1','fr','email.subject.playMore.equalZero','Associazione Play More! - Rinnova la tua quota','1', ''),
(NULL,'1','es','email.subject.playMore.equalZero','Associazione Play More! - Rinnova la tua quota','1', ''),
(NULL,'1','de','email.subject.playMore.equalZero','Associazione Play More! - Rinnova la tua quota','1', '');
INSERT INTO `TRANSLATIONS` (`ID_TRANSLATION`,`TRANSLATION_TYPE`,`LANGUAGE`,`KEY_NAME`,`KEY_VALUE`, `REPLACEMENT_ENABLED`, `DESCRIPTION`) VALUES 
(NULL,'1','it','email.body.playMore.equalZero','email.body.playMore.equalZero','1', ''),
(NULL,'1','pt','email.body.playMore.equalZero','email.body.playMore.equalZero','1', ''),
(NULL,'1','en','email.body.playMore.equalZero','email.body.playMore.equalZero','1', ''),
(NULL,'1','fr','email.body.playMore.equalZero','email.body.playMore.equalZero','1', ''),
(NULL,'1','es','email.body.playMore.equalZero','email.body.playMore.equalZero','1', ''),
(NULL,'1','de','email.body.playMore.equalZero','email.body.playMore.equalZero','1', '');

INSERT INTO `TRANSLATIONS` (`ID_TRANSLATION`,`TRANSLATION_TYPE`,`LANGUAGE`,`KEY_NAME`,`KEY_VALUE`, `REPLACEMENT_ENABLED`, `DESCRIPTION`) VALUES 
(NULL,'1','it','email.subject.playMore.lessThanZero','Associazione Play More! - Rinnova la tua quota','1', ''),
(NULL,'1','pt','email.subject.playMore.lessThanZero','Associazione Play More! - Rinnova la tua quota','1', ''),
(NULL,'1','en','email.subject.playMore.lessThanZero','Associazione Play More! - Rinnova la tua quota','1', ''),
(NULL,'1','fr','email.subject.playMore.lessThanZero','Associazione Play More! - Rinnova la tua quota','1', ''),
(NULL,'1','es','email.subject.playMore.lessThanZero','Associazione Play More! - Rinnova la tua quota','1', ''),
(NULL,'1','de','email.subject.playMore.lessThanZero','Associazione Play More! - Rinnova la tua quota','1', '');
INSERT INTO `TRANSLATIONS` (`ID_TRANSLATION`,`TRANSLATION_TYPE`,`LANGUAGE`,`KEY_NAME`,`KEY_VALUE`, `REPLACEMENT_ENABLED`, `DESCRIPTION`) VALUES 
(NULL,'1','it','email.body.playMore.lessThanZero','email.body.playMore.lessThanZero','1', ''),
(NULL,'1','pt','email.body.playMore.lessThanZero','email.body.playMore.lessThanZero','1', ''),
(NULL,'1','en','email.body.playMore.lessThanZero','email.body.playMore.lessThanZero','1', ''),
(NULL,'1','fr','email.body.playMore.lessThanZero','email.body.playMore.lessThanZero','1', ''),
(NULL,'1','es','email.body.playMore.lessThanZero','email.body.playMore.lessThanZero','1', ''),
(NULL,'1','de','email.body.playMore.lessThanZero','email.body.playMore.lessThanZero','1', '');

UPDATE `TRANSLATIONS` SET
`KEY_VALUE` = '<html>
<body>
Ciao ###FIRSTNAME###,<br/>
ti informiamo che la tua quota associativa scadrà tra ###PLAY_MORE_EXPIRE_DAYS### giorni.<br/>
Rinnova il tuo sostegno all\'Associazione Play More! per continuare a diffondere i nostri valori sui campi di tutto il mondo!<br/>
<br/>
Per rinnovare clicca il seguente link:<br/>
<a href="http://www.gokick.org/services/rinnovo.php">http://www.gokick.org/services/rinnovo.php</a><br/>
<br/>
Grazie,<br/>
Luigi De Micco e Pietro Palvarini<br/>
Presidente e Vice Presidente Play More!
</body></html>'
WHERE `KEY_NAME` = 'email.body.playMore.greaterThanZero' AND `KEY_VALUE` = 'email.body.playMore.greaterThanZero';

UPDATE `TRANSLATIONS` SET
`KEY_VALUE` = '<html>
<body>
Ciao ###FIRSTNAME###,<br/>
ti informiamo che la tua quota associativa scade oggi.<br/>
Rinnova il tuo sostegno all\'Associazione Play More! per continuare a diffondere i nostri valori sui campi di tutto il mondo!<br/>
<br/>
Per rinnovare clicca il seguente link:<br/>
<a href="http://www.gokick.org/services/rinnovo.php">http://www.gokick.org/services/rinnovo.php</a><br/>
<br/>
Grazie,<br/>
Luigi De Micco e Pietro Palvarini<br/>
Presidente e Vice Presidente Play More!
</body></html>'
WHERE `KEY_NAME` = 'email.body.playMore.equalZero' AND `KEY_VALUE` = 'email.body.playMore.equalZero';

UPDATE `TRANSLATIONS` SET
`KEY_VALUE` = '<html>
<body>
Ciao ###FIRSTNAME###,<br/>
ti informiamo che la tua quota associativa è scaduta.<br/>
Rinnova il tuo sostegno all\'Associazione Play More! per continuare a diffondere i nostri valori sui campi di tutto il mondo!<br/>
<br/>
Per rinnovare clicca il seguente link:<br/>
<a href="http://www.gokick.org/services/rinnovo.php">http://www.gokick.org/services/rinnovo.php</a><br/>
<br/>
Hai ancora ###PLAY_MORE_EXPIRE_DAYS_DIFF### giorni per rimanere socio mantenendo l\'icona Play More! accanto al tuo nome in GoKick.org<br/>
<br/>
Grazie,<br/>
Luigi De Micco e Pietro Palvarini<br/>
Presidente e Vice Presidente Play More!
</body></html>'
WHERE `KEY_NAME` = 'email.body.playMore.lessThanZero' AND `KEY_VALUE` = 'email.body.playMore.lessThanZero';



