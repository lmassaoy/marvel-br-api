package main.java.com.lyamada.utils;

import java.net.URI;
import java.net.http.HttpRequest;

public class HttpRequestHelper {
    private String baseURL;
    private QueryParams queryParams;

    public String getBaseURL() {
        return this.baseURL;
    }

    public QueryParams getQueryParams() {
        return this.queryParams;
    }

    public HttpRequestHelper(String baseURL, QueryParams queryParams) {
        this.baseURL = baseURL;
        this.queryParams = queryParams;
    }

    public static HttpRequestHelper from(String baseURL, QueryParams queryParams) {
        return new HttpRequestHelper(baseURL,queryParams);
    }
    
    public HttpRequest getRequest(){
        return HttpRequest.newBuilder()
            .GET()
            .uri(URI.create(this.baseURL+this.queryParams.queryParamsBuilder()))
            .setHeader("User-Agent", "Java 11 HttpClient Bot")
            .build();
    }
}