
//pads left
String.prototype.lpad = function(padString, length) {
  var str = this;
  while (str.length < length)
    str = padString + str;
  return str;
}

//pads right
String.prototype.rpad = function(padString, length) {
  var str = this;
  while (str.length < length)
    str = str + padString;
  return str;
}

function showLastMessage() 
{
  new Ajax.Updater('lastMessage', URL_SHOWLASTMESSAGE,
  {
    parameters:{
      dummy: Math.random()
      }
  });
}

function countStop(object,chars,idShow)
{
      var test=object.value;
      remainingChars =chars - test.length;
      if (remainingChars>chars) remainingChars=chars;
      if (remainingChars<0) remainingChars=0;
      if (idShow!=null)
        $(idShow).innerHTML= '- ' +  remainingChars ;
      if(object.value.length > chars)
      {
        object.value=test.substring(0, chars);
      }
}

function hideDisplayFriendRequestsBox(idBox, displayRequest, hideRequest)
{
  if ($(idBox).style.display == 'none')
  {
    $(idBox).style.display = '';
    $(displayRequest).style.display = 'none';
    $(hideRequest).style.display = '';
  }
  else
  {
    $(idBox).style.display = 'none';
    $(displayRequest).style.display = '';
    $(hideRequest).style.display = 'none';
  }
}

function hideDisplayUserSearchBox(id, idHide, idDisplay)
{
  
  if ($(id).style.display == 'none')
  {
    $(id).style.display = '';
    $(idHide).style.display = '';
    $(idDisplay).style.display = 'none';
  }
  else
  {
    $(id).style.display = 'none';
    $(idHide).style.display = 'none';
    $(idDisplay).style.display = '';
  }
}

function hideDisplayMatchSearchBox(id, idHide, idDisplay, idHideDisplayValue)
{
  if ($(id).style.display == 'none')
  {
    $(id).style.display = '';
    $(idHide).style.display = '';
    $(idDisplay).style.display = 'none';
    $(idHideDisplayValue).value = true;
  }
  else
  {
    $(id).style.display = 'none';
    $(idHide).style.display = 'none';
    $(idDisplay).style.display = '';
    $(idHideDisplayValue).value = false;
  }
}

function displayMatchSearchBox(id, idHide, idDisplay)
{
  $(id).style.display = '';
  $(idHide).style.display = '';
  $(idDisplay).style.display = 'none';
}

function displayUserSearchBox(id, idHide, idDisplay)
{
   
  $(id).style.display = '';
  $(idHide).style.display = '';
  $(idDisplay).style.display = 'none';
  
}


function displayUserDetailsTab(tab)
{
  $('idBoxScheda').style.display = 'none';
  $('idBoxAmici').style.display = 'none';
  $('idBoxStatistiche').style.display = 'none';
  $('idBoxFeedback').style.display = 'none';

  $('idLinkScheda').className = '';
  $('idLinkAmici').className = '';
  $('idLinkStatistiche').className = '';
  $('idLinkFeedback').className = '';

  if(tab == 'scheda')
  {
    $('idBoxScheda').style.display = 'block';
    $('idLinkScheda').className = 'active';
  }
  else if(tab == 'amici')
  {
    $('idBoxAmici').style.display = 'block';
    $('idLinkAmici').className = 'active';
    
  }
  else if(tab == 'statistiche')
  {
    $('idBoxStatistiche').style.display = 'block';
    $('idLinkStatistiche').className = 'active';
  }
  else if(tab == 'feedback')
  {
    $('idBoxFeedback').style.display = 'block';
    $('idLinkFeedback').className = 'active';
  }
}

function openPopupSportCenterChoose(url)
{
  
  var w = 640;
  var h = 585;
  var l = Math.floor((screen.width-w)/2);
  var t = Math.floor((screen.height-h)/2);
  popupWindow = window.open( url, '_sportCenterChoose', 'width=' + w + ',height=' + h + ",top=" + t + ",left=" + l + ',status=0,toolbar=0,location=0,menubar=0,directories=0,resizable=0,scrollbars=1' );
  popupWindow.focus();
}

function openPopupPreviewMatchComment(url)
{
  var w = 624;
  var h = 515;
  var l = Math.floor((screen.width-w)/2);
  var t = Math.floor((screen.height-h)/2);
  popupWindow = window.open( url, '_previewMatchComment', 'width=' + w + ',height=' + h + ",top=" + t + ",left=" + l + ',status=0,toolbar=0,location=0,menubar=0,directories=0,resizable=0,scrollbars=1' );
  popupWindow.focus();
}

function openPopupEditMatchComment(url)
{
  var w = 624;
  var h = 410;
  var l = Math.floor((screen.width-w)/2);
  var t = Math.floor((screen.height-h)/2);
  popupWindow = window.open( url, '_editMatchComment', 'width=' + w + ',height=' + h + ",top=" + t + ",left=" + l + ',status=0,toolbar=0,location=0,menubar=0,directories=0,resizable=0,scrollbars=1' );
  popupWindow.focus();
}

function openPopupUserDetails(url,userName)
{
  userName = userName.replace(/ /g,"");
  userName = userName.replace('.',"");
  userName = userName.replace(/\'/g,"_");
  
  var w = 624;
  var h = 515;
  var l = Math.floor((screen.width-w)/2);
  var t = Math.floor((screen.height-h)/2);
  popupWindow = window.open( url, '_userDetails_' + userName , 'width=' + w + ',height=' + h + ",top=" + t + ",left=" + l + ',status=0,toolbar=0,location=0,menubar=0,directories=0,resizable=0,scrollbars=0' );
  popupWindow.focus();
}

function openPopupRegisterFriendToMatch(url)
{
  var w = 380;
  var h = 600;
  var l = Math.floor((screen.width-w)/2);
  var t = Math.floor((screen.height-h)/2);
  popupWindow = window.open( url, '_registerFriendToMatch', 'width=' + w + ',height=' + h + ",top=" + t + ",left=" + l + ',status=0,toolbar=0,location=0,menubar=0,directories=0,resizable=0,scrollbars=1' );
  popupWindow.focus();
}

function openPopupInviteFriends(url)
{
  var options = {
    strUrl: url,
    strWindowName: '_inviteFriendsToMatch',
    width: 510,
    height:445,
    centerOnSreen: true,
    scrollbars: 'yes'
  }
  openPopup(options);
}

function openPopupRemovePlayers(url)
{
  var w = 624;
  var h = 350;
  var l = Math.floor((screen.width-w)/2);
  var t = Math.floor((screen.height-h)/2);
  popupWindow = window.open( url, '_removePlayers', 'width=' + w + ',height=' + h + ",top=" + t + ",left=" + l + ',status=0,toolbar=0,location=0,menubar=0,directories=0,resizable=0,scrollbars=0' );
  popupWindow.focus();
}

function openPopupTermsAndConditions(url, name)
{
  var options = {
    strUrl: url,
    strWindowName: name,
    width: 650,
    height:515,
    scrollbars:'yes',
    centerOnSreen: true
  }
  openPopup(options);
}

function openPopupPrivacy(url, name)
{
  var options = {
    strUrl: url,
    strWindowName: name,
    width: 650,
    height:515,
    scrollbars:'yes',
    centerOnSreen: true
  }
  openPopup(options);
}

function openPopupNewsletter(url, name)
{
  var options = {
    strUrl: url,
    strWindowName: name,
    width: 624,
    height:515,
    centerOnSreen: true
  }
  openPopup(options);
}

function openPopup(options)
{
  if( options.strUrl == null )
    options.strUrl = '';
  var strWindowFeatures = '';
  if( isNaN(options.width) )
    options.width = 300;
  if( isNaN(options.height) )
    options.width = 300;
  if( options.scrollbars == null )
    options.scrollbars = '0';

  strWindowFeatures +=  'width=' + options.width + ',height=' + options.height + ',scrollbars=' + options.scrollbars;

  if( options.centerOnSreen )
  {
    var left = Math.floor((screen.width-options.width)/2);
    var top = Math.floor((screen.height-options.height)/2);
    strWindowFeatures += ",top=" + top + ",left=" + left;
  }
  strWindowFeatures += ',status=0,toolbar=0,location=0,menubar=0,directories=0,resizable=0';

  popupWindow = window.open( options.strUrl, options.strWindowName, strWindowFeatures );
  popupWindow.focus();
}

function getProvincesByCountry(idCountry, onlyWithUsers, onlyWithSportCenters, withMatches, url, inputOptions, pastMatches, usersOnMarket)
{
  
  if(inputOptions==null)
    inputOptions={startFrom: 1, callback: ''};
  $('idWaitProvinces').show();
  var options = {
    method: 'GET',
    parameters: {
      idCountry: idCountry,
      onlyWithUsers: onlyWithUsers,
      onlyWithSportCenters: onlyWithSportCenters,
      withMatches: withMatches,
      past: pastMatches,
      usersOnMarket: usersOnMarket
    },
    onSuccess: function(t) {
      var response = t.responseJSON;

     $('idSelectProvince').options.length = inputOptions.startFrom;
     $('idSelectCity').options.length = 1;
     
     
     if(response.currentProvincesList.length == 0 )
      {
        return;
      }
     
      for (var j=0; j < response.currentProvincesList.length; j++)
      {
        var province = response.currentProvincesList[j];
            $('idSelectProvince').options[j+inputOptions.startFrom] = new Option(province[1], province[0]);
      }
      
      $('idWaitProvinces').hide();
      if( inputOptions.callback != '')
        eval(inputOptions.callback);
    }
  };
  new Ajax.Request(url, options);
}

function getCitiesByProvince(idProvince, onlyWithUsers, onlyWithSportCenters, withMatches, url, pastMatches,usersOnMarket)
{
  $('idWaitCities').style.visibility = 'visible';
  var options = {
    method: 'GET',
    parameters: { 
      idProvince: idProvince,
      onlyWithUsers: onlyWithUsers,
      onlyWithSportCenters: onlyWithSportCenters,
      withMatches: withMatches,
      past: pastMatches,
      usersOnMarket: usersOnMarket
    },
    onSuccess: function(t) {
      var response = t.responseJSON;
      $('idSelectCity').options.length = 1;
      if(response.currentCitiesList.length == 0 )
      {
        $('idWaitCities').style.visibility = 'hidden';
        return;
      }
      for (var j=0; j < response.currentCitiesList.length; j++)
      {
        var city = response.currentCitiesList[j];
        $('idSelectCity').options[j+1] = new Option(city[1], city[0]);
      }
      $('idWaitCities').style.visibility = 'hidden';
    }
  };
  new Ajax.Request(url, options);
}

function getSportCenterByProvince(idProvince, url)
{
  $('idWaitSportCenter').style.visibility = 'visible';
  var options = {
    method: 'GET',
    parameters: {
      idProvince: idProvince
    },
    onSuccess: function(t) {
      var response = t.responseJSON;
      $('idSelectSportCenter').options.length = 1;
      if(response.currentSportCentersList.length == 0 )
      {
        $('idWaitSportCenter').style.visibility = 'hidden';
        return;
      }
      for (var j=0; j < response.currentSportCentersList.length; j++)
      {
        var sportCenter = response.currentSportCentersList[j];
        $('idSelectSportCenter').options[j+1] = new Option(sportCenter[1], sportCenter[0]);
      }
      $('idWaitSportCenter').style.visibility = 'hidden';
    }
  };
  new Ajax.Request(url, options);
}

function getSportCenterByCity(idCity, url)
{
  $('idWaitSportCenter').style.visibility = 'visible';
  var options = {
    method: 'GET',
    parameters: {
      idCity: idCity
    },
    onSuccess: function(t) {
      var response = t.responseJSON;
      $('idSelectSportCenter').options.length = 1;
      if(response.currentSportCentersList.length == 0 )
      {
        $('idWaitSportCenter').style.visibility = 'hidden';
        return;
      }
      for (var j=0; j < response.currentSportCentersList.length; j++)
      {
        var sportCenter = response.currentSportCentersList[j];
        $('idSelectSportCenter').options[j+1] = new Option(sportCenter[1], sportCenter[0]);
      }
      $('idWaitSportCenter').style.visibility = 'hidden';
    }
  };
  new Ajax.Request(url, options);
}

function showHideReportPlayerReview(reviewBoxClass, playerReviewShowLabelId, playerReviewHideLabelId)
{
  $$(reviewBoxClass).each(function(r){
    r.toggle();
  });

  if(!$(playerReviewShowLabelId).visible())
  {
    $(playerReviewShowLabelId).show();
    $(playerReviewHideLabelId).hide();
  }
  else
  {
    $(playerReviewShowLabelId).hide();
    $(playerReviewHideLabelId).show();
  }
}

function changeCheckBox(selector, value)
{
  $$(selector).each(function(r){
    r.checked = value;
  });
}

function openPopupHelp(helpRef)
{
  var options = {
    strUrl: URL_OPENHELP + '#' + helpRef,
    strWindowName: 'help',
    width: 624,
    height:515,
    centerOnSreen: true,
    scrollbars: '1'
  }
  openPopup(options);
}

function openFbLogin()
{
  var popupWindowFb = window.open( '/facebookLogin.action', '_fbLogin_', 'width=850,height=600,status=0,toolbar=0,location=0,menubar=0,directories=0,resizable=0,scrollbars=0' );
  popupWindowFb.focus();
  return false;
}
