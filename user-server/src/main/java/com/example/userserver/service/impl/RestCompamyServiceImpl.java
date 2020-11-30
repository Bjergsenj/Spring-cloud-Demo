package com.example.userserver.service.impl;

import com.example.userserver.service.iface.RestCompamyService;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.elasticsearch.search.sort.FieldSortBuilder;
import org.elasticsearch.search.sort.ScoreSortBuilder;
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
    private RestHighLevelClient highLevelClient;

    @Override
    public void add() throws IOException {
        Map map = Maps.newHashMap();
        map.put("companyId", 1);
        map.put("companyName", "阿里巴巴");
        map.put("companyInfo", "国内著名电商公司");
        map.put("order", 3);

        Map map2 = Maps.newHashMap();
        map2.put("companyId", 2);
        map2.put("companyName", "京东");
        map2.put("companyInfo", "国内知名电商公司");
        map2.put("order", 2);

        Map map3 = Maps.newHashMap();
        map3.put("companyId", 3);
        map3.put("companyName", "亚马逊");
        map3.put("companyInfo", "国外著名电商公司");
        map3.put("order", 1);

        Map map4 = Maps.newHashMap();
        map4.put("companyId", 4);
        map4.put("companyName", "腾讯");
        map4.put("companyInfo", "国内著名社交游戏公司");
        map4.put("order", 6);

        Map map5 = Maps.newHashMap();
        map5.put("companyId", 5);
        map5.put("companyName", "谷歌");
        map5.put("companyInfo", "世界顶级科技公司");
        map5.put("order", 4);

        Map map6 = Maps.newHashMap();
        map6.put("companyId", 6);
        map6.put("companyName", "百度");
        map6.put("companyInfo", "国内知名搜索公司");
        map6.put("order", 5);

        /**
         * 数据index为paic, index等于关系型数据库中的库
         * type为company ,7.0之后统一为_doc 只能设置一个type ,7.0之前版本等同于关系型数据库中的table
         */
        IndexRequest indexRequest = new IndexRequest("paic", "company", "1").source(map);
        highLevelClient.index(indexRequest, RequestOptions.DEFAULT);
        IndexRequest indexRequest2 = new IndexRequest("paic", "company", "2").source(map2);
        highLevelClient.index(indexRequest2, RequestOptions.DEFAULT);
        IndexRequest indexRequest3 = new IndexRequest("paic", "company", "3").source(map3);
        highLevelClient.index(indexRequest3, RequestOptions.DEFAULT);
        IndexRequest indexRequest4 = new IndexRequest("paic", "company", "4").source(map4);
        highLevelClient.index(indexRequest4, RequestOptions.DEFAULT);
        IndexRequest indexRequest5 = new IndexRequest("paic", "company", "5").source(map5);
        highLevelClient.index(indexRequest5, RequestOptions.DEFAULT);
        IndexRequest indexRequest6 = new IndexRequest("paic", "company", "6").source(map6);
        highLevelClient.index(indexRequest6, RequestOptions.DEFAULT);
        Map map7 = Maps.newHashMap();
        map7.put("companyId", 7);
        map7.put("companyName", "haolaiwu");
        map7.put("companyInfo", "haolaiwuhahahah");
        map7.put("order", 7);
        IndexRequest indexRequest7 = new IndexRequest("paic", "company", "7").source(map7);
        highLevelClient.index(indexRequest7, RequestOptions.DEFAULT);

    }

    @Override
    public Object searchByQuery() {
        SearchRequest searchRequest = new SearchRequest("paic");
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        BoolQueryBuilder boolBuilder = QueryBuilders.boolQuery();
        //companyInfo字段如果加上.keyword则为精确查询，否则为模糊查询
        MatchQueryBuilder matchQueryBuilder = QueryBuilders.matchQuery("companyInfo", "电商");
        //multiMatchQuery一个关键在在多个字段中查询,匹配度到达70%则显示出来 companyInfo权重设置为10
//        MatchQueryBuilder matchQueryBuilder = QueryBuilders.multiMatchQuery("电商","companyInfo", "test","testName")
//                .minimumShouldMatch("70%").field("companyInfo",10);

        //Term query会去倒排索引中寻找确切的term，它并不知道分词器的存在，这种查询适合keyword、numeric、date等明确值的
//                TermQueryBuilder termQueryBuilder = QueryBuilders.termQuery("companyInfo.keyword", "电商");
        boolBuilder.must(matchQueryBuilder);
//                boolBuilder.filter(termQueryBuilder);
        sourceBuilder.query(boolBuilder);
        //分页
        sourceBuilder.from(0);
        sourceBuilder.size(10);
        //
        //      排序
        FieldSortBuilder fsb = SortBuilders.fieldSort("order");
        fsb.order(SortOrder.DESC);
//        sourceBuilder.from( (page-1)*size );
//        sourceBuilder.size(size);
        sourceBuilder.sort(fsb);
        //must搜索打分
        sourceBuilder.sort(new ScoreSortBuilder().order(SortOrder.DESC));
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
            SearchResponse response = highLevelClient.search(searchRequest, RequestOptions.DEFAULT);
            System.out.println(response);
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
                    //替换高亮转为实体类
//                    String collect = Arrays.stream(highlightField.fragments()).map(Text::string).collect(Collectors.joining("\n"));
//                    CompanyInfo companyInfo = JSONObject.parseObject(searchHit.getSourceAsString(), CompanyInfo.class);
//                    companyInfo.setCompanyInfo(collect);
//                    log.info(String.valueOf(companyInfo));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }

}

