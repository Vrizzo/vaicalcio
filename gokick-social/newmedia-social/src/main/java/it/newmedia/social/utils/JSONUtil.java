package it.newmedia.social.utils;

import net.sf.json.JSONObject;

public class JSONUtil
{
  public static String getProperty(String jsonData, String key)
  {
    JSONObject jsonObject = JSONObject.fromObject(jsonData);
    return getProperty(jsonObject, key, "");
  }

  public static String getSafeProperty(JSONObject jsonObject, String key)
  {
    return getProperty(jsonObject, key, "");
  }

  public static String getProperty(JSONObject jsonObject, String key, String defaultValue)
  {
    if (jsonObject.containsKey(key))
    {
      return jsonObject.getString(key);
    }
    return defaultValue;
  }


}
