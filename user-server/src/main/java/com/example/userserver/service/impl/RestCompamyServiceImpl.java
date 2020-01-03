package com.example.userserver.service.impl;

import com.example.userserver.service.iface.RestCompamyService;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.elasticsearch.search.sort.FieldSortBuilder;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * description: TODO
 * create: 2020/1/3 16:11
 *
 * @author niemingxin
 */
@Service
@Slf4j
public class RestCompamyServiceImpl implements RestCompamyService {
    @Autowired
    RestHighLevelClient highLevelClient;
    @Override
    public void add() throws IOException {
        Map map = Maps.newHashMap();
        map.put("companyid", 1);
        map.put("companyName", "阿里巴巴");
        map.put("companyInfo", "国内著名电商公司");
        map.put("order", 3);

        Map map2 = Maps.newHashMap();
        map2.put("companyid", 2);
        map2.put("companyName", "京东");
        map2.put("companyInfo", "国内知名电商公司");
        map2.put("order", 2);

        Map map3 = Maps.newHashMap();
        map3.put("companyid", 3);
        map3.put("companyName", "亚马逊");
        map3.put("companyInfo", "国外著名电商公司");
        map3.put("order", 1);

        Map map4 = Maps.newHashMap();
        map4.put("companyid", 4);
        map4.put("companyName", "腾讯");
        map4.put("companyInfo", "国内著名社交游戏公司");
        map4.put("order", 6);

        Map map5 = Maps.newHashMap();
        map5.put("companyid", 5);
        map5.put("companyName", "谷歌");
        map5.put("companyInfo", "世界顶级科技公司");
        map5.put("order", 4);

        Map map6 = Maps.newHashMap();
        map6.put("companyid", 6);
        map6.put("companyName", "百度");
        map6.put("companyInfo", "国内知名搜索公司");
        map6.put("order", 5);
        IndexRequest indexRequest = new IndexRequest("paic", "company", "1").source(map);
        highLevelClient.index(indexRequest);
        IndexRequest indexRequest2 = new IndexRequest("paic", "company", "2").source(map2);
        highLevelClient.index(indexRequest2);
        IndexRequest indexRequest3 = new IndexRequest("paic", "company", "3").source(map3);
        highLevelClient.index(indexRequest3);
        IndexRequest indexRequest4 = new IndexRequest("paic", "company", "4").source(map4);
        highLevelClient.index(indexRequest4);
        IndexRequest indexRequest5 = new IndexRequest("paic", "company", "5").source(map5);
        highLevelClient.index(indexRequest5);
        IndexRequest indexRequest6 = new IndexRequest("paic", "company", "6").source(map6);
        highLevelClient.index(indexRequest6);
    }

    @Override
    public Object searchByQuery() {
        SearchRequest searchRequest = new SearchRequest("paic");
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();

        MatchQueryBuilder matchQueryBuilder = QueryBuilders.matchQuery("companyInfo", "电商");
        //        TermQueryBuilder termQueryBuilder = QueryBuilders.termQuery("companyInfo.keyword", "电商");
        BoolQueryBuilder boolBuilder = QueryBuilders.boolQuery();
        boolBuilder.must(matchQueryBuilder);
        //        boolBuilder.must(termQueryBuilder);
        sourceBuilder.query(boolBuilder);
        //分页
        sourceBuilder.from(0);
        sourceBuilder.size(10);
        //
        //      排序
        FieldSortBuilder fsb = SortBuilders.fieldSort("order");
        fsb.order(SortOrder.DESC);
        sourceBuilder.sort(fsb);

        //构建高亮体
        HighlightBuilder highlightBuilder = new HighlightBuilder();
        highlightBuilder.preTags("<span style='color:red'>");
        highlightBuilder.postTags("</span>");
        //高亮字段
        highlightBuilder.field("companyInfo").field("companyName");
        sourceBuilder.highlighter(highlightBuilder);

        searchRequest.source(sourceBuilder);
        List<Map<String, Object>> result = Lists.newLinkedList();
        try {
            SearchResponse response = highLevelClient.search(searchRequest);
            SearchHit[] searchHits = response.getHits().getHits();
            if (searchHits != null && searchHits.length > 0) {
                for (SearchHit searchHit : searchHits) {
                    Map<String, Object> map = searchHit.getSourceAsMap();
                    //替换高亮
                    Map<String, HighlightField> highlightFields = searchHit.getHighlightFields();
                    HighlightField highlightField = highlightFields.get("companyInfo");
                    String highlightCompanyInfo = highlightField.fragments()[0].toString();
                    map.put("companyInfo", highlightCompanyInfo);
                    result.add(map);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }

}

