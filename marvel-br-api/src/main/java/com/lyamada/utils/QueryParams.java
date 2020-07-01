package main.java.com.lyamada.utils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class QueryParams {
    private Map<Object, Object> data = new HashMap<>();

    public Map<Object, Object> getData() {
        return this.data;
    }

    public QueryParams () {
        this.data.put("apikey", "9bd883247a88be2a1d1c4a015612c187");
        this.data.put("ts", 1);
        this.data.put("hash", "f15c03fc9e194cf02033a2053cf4408a");
    }

    public QueryParams (Map<Object, Object> params) {
        this.data.put("apikey", "9bd883247a88be2a1d1c4a015612c187");
        this.data.put("ts", 1);
        this.data.put("hash", "f15c03fc9e194cf02033a2053cf4408a");
        for (Map.Entry<Object,Object> entry : params.entrySet())  
            this.data.put(entry.getKey(),entry.getValue()); 
    }

    public static QueryParams from(Map<Object, Object> params) {
        return new QueryParams(params);
    }

    public void printQueryParams() {
        for (Map.Entry<Object,Object> entry : this.data.entrySet())  
            System.out.println("key: "+entry.getKey()+", Value: "+entry.getValue()); 
    }

    public final String queryParamsBuilder() {
        var builder = new StringBuilder();
        builder.append("?");
        System.out.println("size: "+builder.length());
        for (Map.Entry<Object, Object> entry : this.data.entrySet()) {
          if (builder.length() > 2) {
            builder.append("&");
          }
          builder
              .append(entry.getKey().toString());
          builder.append("=");
          builder
              .append(entry.getValue().toString());
        }
        return builder.toString();
    }
}