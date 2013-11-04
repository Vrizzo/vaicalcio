
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

function changeCheckBox(selector, value)
{
  $$(selector).each(function(r){
    r.checked = value;
  });
}


function getProvincesByCountry(idCountry, onlyWithUsers, onlyWithSportCenters, withMatches, url)

{
  $('idWaitProvinces').show();
  var options = {
    method: 'GET',
    parameters: {
      idCountry: idCountry,
      onlyWithUsers: onlyWithUsers,
      onlyWithSportCenters: onlyWithSportCenters,
      withMatches: withMatches
    },
    onSuccess: function(t) {
      var response = t.responseJSON;
      $('idSelectProvince').options.length = 1;
      $('idSelectCity').options.length = 1;
      if(response.currentProvincesList.length == 0 )
      {
        $('idWaitProvinces').hide();
        return;
      }
      for (var j=0; j < response.currentProvincesList.length; j++)
      {
        var province = response.currentProvincesList[j];
        $('idSelectProvince').options[j+1] = new Option(province[1], province[0]);
      }
      $('idWaitProvinces').hide();
    }
  };
  new Ajax.Request(url, options);
}

function getCitiesByProvince(idProvince, onlyWithUsers, onlyWithSportCenters, withMatches, url)
{
  $('idWaitCities').style.visibility = 'visible';
  var options = {
    method: 'GET',
    parameters: {
      idProvince: idProvince,
      onlyWithUsers: onlyWithUsers,
      onlyWithSportCenters: onlyWithSportCenters,
      withMatches: withMatches
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


function getProvincesByCountryParams(idCountry, onlyWithUsers, onlyWithSportCenters, withMatches, url,waitId,selProvId,selCityId)

{
  waitId.show();
  var options = {
    method: 'GET',
    parameters: {
      idCountry: idCountry,
      onlyWithUsers: onlyWithUsers,
      onlyWithSportCenters: onlyWithSportCenters,
      withMatches: withMatches
    },
    onSuccess: function(t) {
      var response = t.responseJSON;
      selProvId.options.length = 1;
      selCityId.options.length = 1;
      if(response.currentProvincesList.length == 0 )
      {
        waitId.hide();
        return;
      }
      for (var j=0; j < response.currentProvincesList.length; j++)
      {
        var province = response.currentProvincesList[j];
        selProvId.options[j+1] = new Option(province[1], province[0]);
      }
      waitId.hide();
    }
  };
  new Ajax.Request(url, options);
}

function getCitiesByProvinceParams(idProvince, onlyWithUsers, onlyWithSportCenters, withMatches, url,waitId,selCityId)
{
  waitId.style.visibility = 'visible';
  var options = {
    method: 'GET',
    parameters: {
      idProvince: idProvince,
      onlyWithUsers: onlyWithUsers,
      onlyWithSportCenters: onlyWithSportCenters,
      withMatches: withMatches
    },
    onSuccess: function(t) {
      var response = t.responseJSON;
      selCityId.options.length = 1;
      if(response.currentCitiesList.length == 0 )
      {
        waitId.style.visibility = 'hidden';
        return;
      }
      for (var j=0; j < response.currentCitiesList.length; j++)
      {
        var city = response.currentCitiesList[j];
        selCityId.options[j+1] = new Option(city[1], city[0]);
      }
      waitId.style.visibility = 'hidden';
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

function openPopupUserDetails(url,userName)
{
  userName = userName.replace(/ /g,"");
  userName = userName.replace('.',"");
  var w = 624;
  var h = 515;
  var l = Math.floor((screen.width-w)/2);
  var t = Math.floor((screen.height-h)/2);
  var index = url.indexOf('goToUrl=', 0);
  var validUrl = url.substr(0, index) + 'goToUrl=' + escape(url.substr(index + 'goToUrl='.length));
  
  popupWindow = window.open( validUrl, '_userDetails_' + userName , 'width=' + w + ',height=' + h + ",top=" + t + ",left=" + l + ',status=0,toolbar=0,location=0,menubar=0,directories=0,resizable=0,scrollbars=0' );
  popupWindow.focus();
}

 