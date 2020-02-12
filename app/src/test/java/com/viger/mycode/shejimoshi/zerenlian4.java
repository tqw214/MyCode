package com.viger.mycode.shejimoshi;

import java.util.ArrayList;
import java.util.List;

public class zerenlian4 {

    public static void main(String[] args) {
        Request myRequest = new Request();
        myRequest.request = "张三身高170，学历大专，跪求妹子给个机会认识";
        System.out.println("request:" + myRequest.request);
        Response myResponse = new Response();
        myResponse.response = "";
        FilterChain chain = new FilterChain();
        chain.add(new HeightFliter()).add(new EducationalBackGroundFilter());
        chain.doFilter(myRequest, myResponse, chain);
        System.out.println("response:" + myResponse.response);
    }
    static class Request {
        String request;
    }

    static class Response {
        String response;
    }

    static class FilterChain {
        private List<Filter> list = new ArrayList<Filter>();
        private int index = 0;

        public FilterChain add(Filter filter) {
            list.add(filter);
            return this;
        }

        public void doFilter(Request req, Response res, FilterChain filterChain) {
            if(index == list.size()) {
                return;
            }
            Filter filter = list.get(index);
            index++;
            filter.doFilter(req, res, filterChain);
        }
    }

    static interface Filter {
        void doFilter(Request req, Response res, FilterChain filterChain);
    }

    static class HeightFliter implements Filter {
        @Override
        public void doFilter(Request req, Response res, FilterChain filterChain) {
            req.request = req.request.replace("170", "个子有点矮");
            filterChain.doFilter(req,res,filterChain);
            res.response += "【妹子挑剔，需要过滤身高】";
        }
    }

    static class EducationalBackGroundFilter implements Filter {
        @Override
        public void doFilter(Request myRequest, Response myResponse, FilterChain myFilterChain) {
            myRequest.request = myRequest.request.replace("学历大专", "学历不高");
            myFilterChain.doFilter(myRequest, myResponse, myFilterChain);
            myResponse.response += "【妹子挑剔，需要过滤学历】";
        }
    }
}
