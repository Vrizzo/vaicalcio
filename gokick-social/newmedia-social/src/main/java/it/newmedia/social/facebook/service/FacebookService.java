package it.newmedia.social.facebook.service;

import it.newmedia.social.facebook.dto.ReadPostRequest;
import it.newmedia.social.facebook.dto.ReadPostResponse;
import it.newmedia.social.facebook.dto.WritePostRequest;
import it.newmedia.social.facebook.dto.WritePostResponse;

public interface FacebookService
{

	ReadPostResponse readLastPosts(ReadPostRequest request);

  WritePostResponse writePosts(WritePostRequest request);
}