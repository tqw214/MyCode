package com.viger.mycode.shejimoshi;

import java.util.ArrayList;
import java.util.List;

/**
 *  应用责任链模式手写过滤器
 */
public class zerenlian2 {

    public static void main(String[] args) {
        Request myRequest = new Request();
        myRequest.request = "张三身高170，学历大专，跪求妹子给个机会认识";
        System.out.println("request:" + myRequest.request);
        Response myResponse = new Response();
        myResponse.response = "";
        Chain chain = new Chain();
        chain.add(new HeightFliter()).add(new EducationalBackGroundFilter());
        chain.doFilter(myRequest, myResponse);
        System.out.println("response:" + myResponse.response);
    }

    static class Request {
        String request;
    }

    static class Response {
        String response;
    }

    //模拟实现过滤器
    public static interface Filter {
        void doFilter(Request req, Response res);
    }

    //责任链
    public static class Chain implements Filter {

        private List<Filter> mFilters = new ArrayList<Filter>();

        public Chain add(Filter filter) {
            mFilters.add(filter);
            return this;
        }

        @Override
        public void doFilter(Request req, Response res) {
            for(Filter filter : mFilters) {
                filter.doFilter(req, res);
            }
        }
    }

    //HeightFliter
    public static class HeightFliter implements Filter {

        @Override
        public void doFilter(Request req, Response res) {
            req.request = req.request.replace("170", "个子有点矮");
            res.response += "【妹子挑剔，需要过滤身高】";
        }
    }

    public static class EducationalBackGroundFilter implements Filter {
        @Override
        public void doFilter(Request req, Response res) {
            req.request = req.request.replace("学历大专", "学历不高");
            res.response += "【妹子挑剔，需要过滤学历】";
        }
    }




}
