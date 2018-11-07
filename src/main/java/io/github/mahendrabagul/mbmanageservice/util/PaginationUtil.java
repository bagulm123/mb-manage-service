/*
 * ******************************************************
 *  * Copyright (C) 2018-2019 Mahendra Bagul <bagulm123@gmail.com>
 *  *
 *  * This file is part of MB Manage Service.
 *  *
 *  * MB Manage Service can not be copied and/or distributed without the express
 *  * permission of Mahendra Bagul
 *  ******************************************************
 */

package io.github.mahendrabagul.mbmanageservice.util;

import static io.github.mahendrabagul.mbmanageservice.util.Constants.X_TOTAL_COUNT;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.web.util.UriComponentsBuilder;

/**
 * Utility class for handling pagination.
 */
public final class PaginationUtil {


  private PaginationUtil() {
  }

  public static <T> HttpHeaders generatePaginationHttpHeaders(Page<T> page, String baseUrl) {

    HttpHeaders headers = new HttpHeaders();
    headers.add(X_TOTAL_COUNT, Long.toString(page.getTotalElements()));
    String link = "";
    if ((page.getNumber() + 1) < page.getTotalPages()) {
      link = "<" + generateUri(baseUrl, page.getNumber() + 1, page.getSize()) + ">; rel=\"next\",";
    }
    // prev link
    if ((page.getNumber()) > 0) {
      link += "<" + generateUri(baseUrl, page.getNumber() - 1, page.getSize()) + ">; rel=\"prev\",";
    }
    // last and first link
    int lastPage = 0;
    if (page.getTotalPages() > 0) {
      lastPage = page.getTotalPages() - 1;
    }
    link += "<" + generateUri(baseUrl, lastPage, page.getSize()) + ">; rel=\"last\",";
    link += "<" + generateUri(baseUrl, 0, page.getSize()) + ">; rel=\"first\"";
    headers.add(HttpHeaders.LINK, link);
    return headers;
  }

  private static String generateUri(String baseUrl, int page, int size) {
    return UriComponentsBuilder.fromUriString(baseUrl).queryParam("page", page)
        .queryParam("size", size).toUriString();
  }

  public static <T> HttpHeaders generateSearchPaginationHttpHeaders(String query, Page<T> page,
      String baseUrl) {
    String escapedQuery;
    try {
      escapedQuery = URLEncoder.encode(query, "UTF-8");
    } catch (UnsupportedEncodingException e) {
      throw new RuntimeException(e);
    }
    HttpHeaders headers = new HttpHeaders();
    headers.add("X-Total-Count", Long.toString(page.getTotalElements()));
    String link = "";
    if ((page.getNumber() + 1) < page.getTotalPages()) {
      link = "<" + generateUri(baseUrl, page.getNumber() + 1, page.getSize()) + "&query="
          + escapedQuery + ">; rel=\"next\",";
    }
    // prev link
    if ((page.getNumber()) > 0) {
      link += "<" + generateUri(baseUrl, page.getNumber() - 1, page.getSize()) + "&query="
          + escapedQuery + ">; rel=\"prev\",";
    }
    // last and first link
    int lastPage = 0;
    if (page.getTotalPages() > 0) {
      lastPage = page.getTotalPages() - 1;
    }
    link += "<" + generateUri(baseUrl, lastPage, page.getSize()) + "&query=" + escapedQuery
        + ">; rel=\"last\",";
    link += "<" + generateUri(baseUrl, 0, page.getSize()) + "&query=" + escapedQuery
        + ">; rel=\"first\"";
    headers.add(HttpHeaders.LINK, link);
    return headers;
  }
}

