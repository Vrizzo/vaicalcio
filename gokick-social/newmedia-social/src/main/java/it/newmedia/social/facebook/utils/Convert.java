package it.newmedia.social.facebook.utils;

import it.newmedia.social.facebook.dto.ReadPostResponseData;
import it.newmedia.social.facebook.dto.WritePostRequestData;
import it.newmedia.social.utils.JSONUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.util.*;

public class Convert
{

  public static List<ReadPostResponseData> jsonStringToDtoReadPostResponseDataList(String data)
  {
    ArrayList<ReadPostResponseData> list = new ArrayList<ReadPostResponseData>();

    JSONObject jsonObject = JSONObject.fromObject(data);
    JSONArray jsonDataArray = jsonObject.getJSONArray("data");

    for (Object aJsonDataArray : jsonDataArray)
    {
      JSONObject jsonPostObject = (JSONObject) aJsonDataArray;
      ReadPostResponseData post = new ReadPostResponseData();
      post.setIdPost(JSONUtil.getSafeProperty(jsonPostObject, "id"));
      post.setCaption(JSONUtil.getSafeProperty(jsonPostObject, "caption"));
      post.setPicture(JSONUtil.getSafeProperty(jsonPostObject, "picture"));
      post.setDescription(JSONUtil.getSafeProperty(jsonPostObject, "description"));
      post.setLink(JSONUtil.getSafeProperty(jsonPostObject, "link"));
      post.setMessage(JSONUtil.getSafeProperty(jsonPostObject, "message"));
      post.setName(JSONUtil.getSafeProperty(jsonPostObject, "name"));
      list.add(post);
    }
    return list;
  }


  public static Map<String, String> dtoWritePostRequestDataToMapParams(WritePostRequestData post)
  {
    Map<String, String> params = new HashMap<String, String>();
    safeAddToMap(params, "access_token", post.getAccessToken());
    safeAddToMap(params, "caption", post.getCaption());
    safeAddToMap(params, "description", post.getDescription());
    safeAddToMap(params, "link", post.getLink());
    safeAddToMap(params, "message", post.getMessage());
    safeAddToMap(params, "name", post.getName());
    safeAddToMap(params, "picture", post.getPicture());
    return params;
  }

  private static void safeAddToMap(Map<String, String> map, String key, String value)
  {
    if( value == null)
      return;
    map.put(key, value);
    
  }

}
