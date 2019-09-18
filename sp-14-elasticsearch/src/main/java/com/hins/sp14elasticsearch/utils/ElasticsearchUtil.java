package com.hins.sp14elasticsearch.utils;

import com.alibaba.fastjson.JSON;
import com.hins.sp14elasticsearch.entity.EsEntity;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.ElasticsearchException;
import org.elasticsearch.action.DocWriteResponse;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.admin.indices.open.OpenIndexRequest;
import org.elasticsearch.action.admin.indices.open.OpenIndexResponse;
import org.elasticsearch.action.bulk.BulkItemResponse;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.*;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.*;
import org.elasticsearch.action.support.WriteRequest;
import org.elasticsearch.action.support.replication.ReplicationResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.*;
import org.elasticsearch.rest.RestStatus;
import org.elasticsearch.script.Script;
import org.elasticsearch.search.Scroll;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.FieldSortBuilder;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.*;

/**
 * elasticSearh 操作类
 * @author qixuan.chen
 * @date 2019-09-17 17:47
 */
@Component
public class ElasticsearchUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(ElasticsearchUtil.class);


    private static final String INDEX_KEY = "index";
    private static final String TYPE_KEY = "type";
    private static final String INDEX = "spider";
    private static final String TYPE = "doc";
    private static final String TIMESTAMP = "timestamp";


    @Autowired
    private RestHighLevelClient client;



    /**
     * 创建索引(默认分片数为5和副本数为1)
     * @param indexName
     * @throws IOException
     */
    public void createIndex(String indexName) throws IOException {
        if (checkIndexExists(indexName)) {
            LOGGER.error("\"index={}\"索引已经存在！", indexName);
            return;
        }
        CreateIndexRequest request = new CreateIndexRequest(indexName);
        request.mapping(generateBuilder());
        CreateIndexResponse response = client.indices().create(request, RequestOptions.DEFAULT);
        // 指示是否所有节点都已确认请求
        boolean acknowledged = response.isAcknowledged();
        // 指示是否在超时之前为索引中的每个分片启动了必需的分片副本数
        boolean shardsAcknowledged = response.isShardsAcknowledged();
        if (acknowledged || shardsAcknowledged) {
            LOGGER.info("创建索引成功！索引名称为{}", indexName);
        }
    }

    /**
     * ES7已经没有type的概念了？？
     * 创建索引(指定 index 和 type)
     * @param index
     * @param type
     * @throws IOException
     */
    public void createIndex(String index, String type) throws IOException {
        if (checkIndexExists(index)) {
            LOGGER.error("\"index={}\"索引已存在！", index);
            return;
        }
        CreateIndexRequest request = new CreateIndexRequest(index);
        //request.mapping(type,generateBuilder());

        CreateIndexResponse response = client.indices().create(request, RequestOptions.DEFAULT);
        boolean acknowledged = response.isAcknowledged();
        boolean shardsAcknowledged = response.isShardsAcknowledged();
        if (acknowledged || shardsAcknowledged) {
            LOGGER.info("索引创建成功！索引名称为{}", index);
        }
    }

    /**
     * 创建索引(传入参数：分片数、副本数)
     * @param indexName
     * @param shards
     * @param replicas
     * @throws IOException
     */
    public void createIndex(String indexName, int shards, int replicas) throws IOException {

    }

    /**
     * 删除索引
     * @param indexName
     * @throws IOException
     */
    public void deleteIndex(String indexName) throws IOException {
        try {
            client.indices().delete(new DeleteIndexRequest(indexName), RequestOptions.DEFAULT);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 判断索引是否存在
     * @param indexName
     * @return
     * @throws IOException
     */
    public boolean checkIndexExists(String indexName) {
        GetIndexRequest request = new GetIndexRequest(indexName);
        request.local(false);
        request.humanReadable(true);
        request.includeDefaults(false);
        try {
            return client.indices().exists(request, RequestOptions.DEFAULT);
        } catch (IOException e) {
            LOGGER.error("判断索引是否存在，操作异常！");
        }
        return false;
    }

    /**
     * 开启索引
     * @param indexName
     * @throws IOException
     */
    public void openIndex(String indexName) throws IOException{
        if (!checkIndexExists(indexName)) {
            LOGGER.error("索引不存在！");
            return;
        }
        OpenIndexRequest request = new OpenIndexRequest(indexName);
        OpenIndexResponse response = client.indices().open(request, RequestOptions.DEFAULT);
        if (response.isAcknowledged() || response.isShardsAcknowledged()) {
            LOGGER.info("{} 索引开启成功！", indexName);
        }
    }

    /**
     * 关闭索引
     * @param indexName
     * @throws IOException
     */
    public void closeIndex(String indexName) throws IOException {
        if (!checkIndexExists(indexName)) {
            LOGGER.error("索引不存在！");
            return;
        }

    }

    /**
     * 设置文档的静态映射(主要是为 message字段 设置ik分词器)
     * @param index
     * @param type
     * @throws IOException
     */
    public void setFieldsMapping(String index, String type) {

    }

    private XContentBuilder generateBuilder() throws IOException {
        XContentBuilder builder = XContentFactory.jsonBuilder();
        builder.startObject();
        builder.startObject("properties");
        builder.startObject("message");
        builder.field("type", "text");
        // 为message字段，设置分词器为 ik_smart(最粗粒度)
        builder.field("analyzer", "ik_smart");
        builder.endObject();
        builder.startObject(TIMESTAMP);
        builder.field("type", "date");
        // 设置 日志时间的格式为  毫秒数的long类型
        builder.field("format", "epoch_millis");
        builder.endObject();
        builder.endObject();
        builder.endObject();
        return builder;
    }


    ////////////////////////////////////////////////////////////////////


    /**
     * Description: 插入/更新一条记录
     *
     * @param index  index
     * @param entity 对象
     * @author fanxb
     * @date 2019/7/24 15:02
     */
    public void insertOrUpdateOne(String index, EsEntity entity) {
        IndexRequest request = new IndexRequest(index);
        request.id(entity.getId());
        request.source(JSON.toJSONString(entity.getData()), XContentType.JSON);
        try {
            client.index(request, RequestOptions.DEFAULT);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }




    /**
     * 增加文档
     * @param indexName
     * @param typeName
     * @param id
     */
    public void addDocByJson(String indexName, String typeName, String id, String jsonString) throws IOException{
        if (!JsonValidator.validate(jsonString)) {
            LOGGER.error("非法的json字符串，操作失败！");
            return;
        }
        if (!checkIndexExists(indexName)) {
            createIndex(indexName, typeName);
        }
        IndexRequest request = new IndexRequest(indexName, typeName, id).source(jsonString, XContentType.JSON);
        // request的opType默认是INDEX(传入相同id会覆盖原document，CREATE则会将旧的删除)
        // request.opType(DocWriteRequest.OpType.CREATE)
        IndexResponse response = null;
        try {
            response = client.index(request, RequestOptions.DEFAULT);

            String index = response.getIndex();
            String type = response.getType();
            String documentId = response.getId();
            if (response.getResult() == DocWriteResponse.Result.CREATED) {
                LOGGER.info("新增文档成功！ index: {}, type: {}, id: {}", index , type, documentId);
            } else if (response.getResult() == DocWriteResponse.Result.UPDATED) {
                LOGGER.info("修改文档成功！ index: {}, type: {}, id: {}", index , type, documentId);
            }
            // 分片处理信息
            ReplicationResponse.ShardInfo shardInfo = response.getShardInfo();
            if (shardInfo.getTotal() != shardInfo.getSuccessful()) {
                LOGGER.error("文档未写入全部分片副本！");
            }
            // 如果有分片副本失败，可以获得失败原因信息
            if (shardInfo.getFailed() > 0) {
                for (ReplicationResponse.ShardInfo.Failure failure : shardInfo.getFailures()) {
                    String reason = failure.reason();
                    LOGGER.error("副本失败原因：{}", reason);
                }
            }
        } catch (ElasticsearchException e) {
            if (e.status() == RestStatus.CONFLICT) {
                LOGGER.error("版本异常！");
            }
            LOGGER.error("文档新增失败！");
        }
    }

    /**
     * 查找文档
     * @param index
     * @param type
     * @param id
     * @return
     * @throws IOException
     */
    public Map<String, Object> getDocument(String index, String type, String id) throws IOException{
        Map<String, Object> resultMap = new HashMap<>();
        GetRequest request = new GetRequest(index, type, id);
        // 实时(否)
        request.realtime(false);
        // 检索之前执行刷新(是)
        request.refresh(true);

        GetResponse response = null;
        try {
            response = client.get(request, RequestOptions.DEFAULT);
        } catch (ElasticsearchException e) {
            if (e.status() == RestStatus.NOT_FOUND) {
                LOGGER.error("文档未找到，请检查参数！" );
            }
            if (e.status() == RestStatus.CONFLICT) {
                LOGGER.error("版本冲突！" );
            }
            LOGGER.error("查找失败！");
        }

        if(Objects.nonNull(response)) {
            if (response.isExists()) { // 文档存在
                resultMap = response.getSourceAsMap();
            } else {
                // 处理未找到文档的方案。 请注意，虽然返回的响应具有404状态代码，但仍返回有效的GetResponse而不是抛出异常。
                // 此时此类响应不持有任何源文档，并且其isExists方法返回false。
                LOGGER.error("文档未找到，请检查参数！" );
            }
        }
        return resultMap;
    }

    /**
     * 删除文档
     * @param index
     * @param type
     * @param id
     * @throws IOException
     */
    public void deleteDocument(String index, String type, String id) throws IOException {
        DeleteRequest request = new DeleteRequest(index, type, id);
        DeleteResponse response = null;
        try {
            response = client.delete(request, RequestOptions.DEFAULT);
        } catch (ElasticsearchException e) {
            if (e.status() == RestStatus.CONFLICT) {
                LOGGER.error("版本冲突！" );
            }
            LOGGER.error("删除失败!");
        }
        if (Objects.nonNull(response)) {
            if (response.getResult() == DocWriteResponse.Result.NOT_FOUND) {
                LOGGER.error("不存在该文档，请检查参数！");
            }
            LOGGER.info("文档已删除！");
            ReplicationResponse.ShardInfo shardInfo = response.getShardInfo();
            if (shardInfo.getTotal() != shardInfo.getSuccessful()) {
                LOGGER.error("部分分片副本未处理");
            }
            if (shardInfo.getFailed() > 0) {
                for (ReplicationResponse.ShardInfo.Failure failure : shardInfo.getFailures()) {
                    String reason = failure.reason();
                    LOGGER.error("失败原因：{}", reason);
                }
            }
        }
    }

    /**
     * 通过一个脚本语句(如："ctx._source.posttime=\"2018-09-18\"")更新文档
     * @param index
     * @param type
     * @param id
     * @param script
     */
    public void updateDocByScript(String index, String type, String id, String script) throws IOException{
        Script inline = new Script(script);
        UpdateRequest request = new UpdateRequest(index, type, id).script(inline);
        try {
            UpdateResponse response  = client.update(request, RequestOptions.DEFAULT);
            if (response.getResult() == DocWriteResponse.Result.UPDATED) {
                LOGGER.info("文档更新成功！");
            } else if (response.getResult() == DocWriteResponse.Result.DELETED) {
                LOGGER.error("\"index={},type={},id={}\"的文档已被删除，无法更新！", response.getIndex(), response.getType(), response.getId());
            } else if(response.getResult() == DocWriteResponse.Result.NOOP) {
                LOGGER.error("操作没有被执行！");
            }

            ReplicationResponse.ShardInfo shardInfo = response.getShardInfo();
            if (shardInfo.getTotal() != shardInfo.getSuccessful()) {
                LOGGER.error("部分分片副本未处理");
            }
            if (shardInfo.getFailed() > 0) {
                for (ReplicationResponse.ShardInfo.Failure failure : shardInfo.getFailures()) {
                    String reason = failure.reason();
                    LOGGER.error("未处理原因：{}", reason);
                }
            }
        } catch (ElasticsearchException e) {
            if (e.status() == RestStatus.NOT_FOUND) {
                LOGGER.error("不存在这个文档，请检查参数！" );
            } else if (e.status() == RestStatus.CONFLICT) {
                LOGGER.error("版本冲突异常！" );
            }
            LOGGER.error("更新失败！");
        }
    }

    /**
     * 通过一个JSON字符串更新文档(如果该文档不存在，则根据参数创建这个文档)
     * @param index
     * @param type
     * @param id
     * @param jsonString
     * @throws IOException
     */
    public void updateDocByJson(String index, String type, String id, String jsonString) throws IOException {
        if (!JsonValidator.validate(jsonString)) {
            LOGGER.error("非法的json字符串，操作失败！");
            return;
        }
        if (!checkIndexExists(index)) {
            createIndex(index, type);
        }
        UpdateRequest request = new UpdateRequest(index, type, id);
        request.doc(jsonString, XContentType.JSON);
        // 如果要更新的文档不存在，则根据传入的参数新建一个文档
        request.docAsUpsert(true);
        try {
            UpdateResponse response = client.update(request, RequestOptions.DEFAULT);
            String indexName = response.getIndex();
            String typeName = response.getType();
            String documentId = response.getId();
            if (response.getResult() == DocWriteResponse.Result.CREATED) {
                LOGGER.info("文档新增成功！index: {}, type: {}, id: {}", indexName, typeName, documentId);
            } else if (response.getResult() == DocWriteResponse.Result.UPDATED) {
                LOGGER.info("文档更新成功！");
            } else if (response.getResult() == DocWriteResponse.Result.DELETED) {
                LOGGER.error("\"index={},type={},id={}\"的文档已被删除，无法更新！", indexName, typeName, documentId);
            } else if (response.getResult() == DocWriteResponse.Result.NOOP) {
                LOGGER.error("操作没有被执行！");
            }

            ReplicationResponse.ShardInfo shardInfo = response.getShardInfo();
            if (shardInfo.getTotal() != shardInfo.getSuccessful()) {
                LOGGER.error("分片副本未全部处理");
            }
            if (shardInfo.getFailed() > 0) {
                for (ReplicationResponse.ShardInfo.Failure failure : shardInfo.getFailures()) {
                    String reason = failure.reason();
                    LOGGER.error("未处理原因：{}", reason);
                }
            }
        } catch (ElasticsearchException e) {
            if (e.status() == RestStatus.NOT_FOUND) {
                LOGGER.error("不存在这个文档，请检查参数！" );
            } else if (e.status() == RestStatus.CONFLICT) {
                LOGGER.error("版本冲突异常！" );
            }
            LOGGER.error("更新失败！");
        }
    }

    /**
     * 批量增加文档
     * @param params
     * @throws IOException
     */
    public void bulkAdd(List<Map<String, String>> params) throws IOException {
        BulkRequest bulkRequest = new BulkRequest();
        for (Map<String, String> dataMap : params) {
            String index = dataMap.getOrDefault(INDEX_KEY, INDEX);
            String type = dataMap.getOrDefault(TYPE_KEY, TYPE);
            String id = dataMap.get("id");
            String jsonString = dataMap.get("json");
            if (StringUtils.isNotBlank(id) && JsonValidator.validate(jsonString)) {
                IndexRequest request = new IndexRequest(index, type, id).source(jsonString, XContentType.JSON);
                bulkRequest.add(request);
            }
        }
        // 超时时间(2分钟)
        bulkRequest.timeout(TimeValue.timeValueMinutes(2L));
        // 刷新策略
        bulkRequest.setRefreshPolicy(WriteRequest.RefreshPolicy.WAIT_UNTIL);

        if (bulkRequest.numberOfActions() == 0) {
            LOGGER.error("参数错误，批量增加操作失败！");
            return;
        }
        BulkResponse bulkResponse = client.bulk(bulkRequest, RequestOptions.DEFAULT);
        // 全部操作成功
        if (!bulkResponse.hasFailures()) {
            LOGGER.info("批量增加操作成功！");
        } else {
            for (BulkItemResponse bulkItemResponse : bulkResponse) {
                if (bulkItemResponse.isFailed()) {
                    BulkItemResponse.Failure failure = bulkItemResponse.getFailure();
                    LOGGER.error("\"index={}, type={}, id={}\"的文档增加失败！", failure.getIndex(), failure.getType(), failure.getId());
                    LOGGER.error("增加失败详情: {}", failure.getMessage());
                } else {
                    LOGGER.info("\"index={}, type={}, id={}\"的文档增加成功！", bulkItemResponse.getIndex(), bulkItemResponse.getType(), bulkItemResponse.getId());
                }
            }
        }
    }

    /**
     * 批量更新文档
     * @param params
     * @throws IOException
     */
    public void bulkUpdate(List<Map<String, String>> params) throws IOException {
        BulkRequest bulkRequest = new BulkRequest();
        for (Map<String, String> dataMap : params) {
            String index = dataMap.getOrDefault(INDEX_KEY, INDEX);
            String type = dataMap.getOrDefault(TYPE_KEY, TYPE);
            String id = dataMap.get("id");
            String jsonString = dataMap.get("json");
            if (StringUtils.isNotBlank(id) && JsonValidator.validate(jsonString)) {
                UpdateRequest request = new UpdateRequest(index, type, id).doc(jsonString, XContentType.JSON);
                request.docAsUpsert(true);
                bulkRequest.add(request);
            }
        }
        if (bulkRequest.numberOfActions() == 0) {
            LOGGER.error("参数错误，批量更新操作失败！");
            return;
        }
        bulkRequest.timeout(TimeValue.timeValueMinutes(2L));
        bulkRequest.setRefreshPolicy(WriteRequest.RefreshPolicy.WAIT_UNTIL);
        BulkResponse bulkResponse = client.bulk(bulkRequest, RequestOptions.DEFAULT);
        if (!bulkResponse.hasFailures()) {
            LOGGER.info("批量更新操作成功！");
        } else {
            for (BulkItemResponse bulkItemResponse : bulkResponse) {
                if (bulkItemResponse.isFailed()) {
                    BulkItemResponse.Failure failure = bulkItemResponse.getFailure();
                    LOGGER.error("\"index={}, type={}, id={}\"的文档更新失败！", failure.getIndex(), failure.getType(), failure.getId());
                    LOGGER.error("更新失败详情: {}", failure.getMessage());
                } else {
                    LOGGER.info("\"index={}, type={}, id={}\"的文档更新成功！", bulkItemResponse.getIndex(), bulkItemResponse.getType(), bulkItemResponse.getId());
                }
            }
        }
    }

    /**
     * 批量删除文档
     * @param params
     * @throws IOException
     */
    public void bulkDelete(List<Map<String, String>> params) throws IOException {
        BulkRequest bulkRequest = new BulkRequest();
        for (Map<String, String> dataMap : params) {
            String index = dataMap.getOrDefault(INDEX_KEY, INDEX);
            String type = dataMap.getOrDefault(TYPE_KEY, TYPE);
            String id = dataMap.get("id");
            if (StringUtils.isNotBlank(id)){
                DeleteRequest request = new DeleteRequest(index, type, id);
                bulkRequest.add(request);
            }
        }
        if (bulkRequest.numberOfActions() == 0) {
            LOGGER.error("操作失败，请检查参数！");
            return;
        }
        bulkRequest.timeout(TimeValue.timeValueMinutes(2L));
        bulkRequest.setRefreshPolicy(WriteRequest.RefreshPolicy.WAIT_UNTIL);
        BulkResponse bulkResponse = client.bulk(bulkRequest, RequestOptions.DEFAULT);
        if (!bulkResponse.hasFailures()) {
            LOGGER.info("批量删除操作成功！");
        } else {
            for (BulkItemResponse bulkItemResponse : bulkResponse) {
                if (bulkItemResponse.isFailed()) {
                    BulkItemResponse.Failure failure = bulkItemResponse.getFailure();
                    LOGGER.error("\"index={}, type={}, id={}\"的文档删除失败！", failure.getIndex(), failure.getType(), failure.getId());
                    LOGGER.error("删除失败详情: {}", failure.getMessage());
                } else {
                    LOGGER.info("\"index={}, type={}, id={}\"的文档删除成功！", bulkItemResponse.getIndex(), bulkItemResponse.getType(), bulkItemResponse.getId());
                }
            }
        }
    }

    /**
     * 批量查找文档
     * @param params
     * @return
     * @throws IOException
     */
    public List<Map<String, Object>> multiGet(List<Map<String, String>> params) throws IOException {
        List<Map<String, Object>> resultList = new ArrayList<>();

        MultiGetRequest request = new MultiGetRequest();
        for (Map<String, String> dataMap : params) {
            String index = dataMap.getOrDefault(INDEX_KEY, INDEX);
            String type = dataMap.getOrDefault(TYPE_KEY, TYPE);
            String id = dataMap.get("id");
            if (StringUtils.isNotBlank(id)) {
                request.add(new MultiGetRequest.Item(index, type, id));
            }
        }
        request.realtime(false);
        request.refresh(true);
        MultiGetResponse response = client.mget(request, RequestOptions.DEFAULT);
        List<Map<String, Object>> list = parseMGetResponse(response);
        if (!list.isEmpty()) {
            resultList.addAll(list);
        }
        return resultList;
    }
    private List<Map<String, Object>> parseMGetResponse(MultiGetResponse response) {
        List<Map<String, Object>> list = new ArrayList<>();
        MultiGetItemResponse[] responses = response.getResponses();
        for (MultiGetItemResponse item : responses) {
            GetResponse getResponse = item.getResponse();
            if (Objects.nonNull(getResponse)) {
                if (!getResponse.isExists()) {
                    LOGGER.error("\"index={}, type={}, id={}\"的文档查找失败，请检查参数！", getResponse.getIndex(), getResponse.getType(), getResponse.getId());
                } else {
                    list.add(getResponse.getSourceAsMap());
                }
            } else {
                MultiGetResponse.Failure failure = item.getFailure();
                ElasticsearchException e = (ElasticsearchException) failure.getFailure();
                if (e.status() == RestStatus.NOT_FOUND) {
                    LOGGER.error("\"index={}, type={}, id={}\"的文档不存在！", failure.getIndex(), failure.getType(), failure.getId());
                } else if (e.status() == RestStatus.CONFLICT) {
                    LOGGER.error("\"index={}, type={}, id={}\"的文档版本冲突！", failure.getIndex(), failure.getType(), failure.getId());
                }
            }
        }
        return list;
    }

    /**
     * 根据条件搜索日志内容(参数level和messageKey不能同时为空)
     * @param level 日志级别，可以为空
     * @param messageKey 日志信息关键字，可以为空
     * @param startTime 日志起始时间，可以为空
     * @param endTime 日志结束时间，可以为空
     * @param size 返回记录数，可以为空，默认最大返回10条。该值必须小于10000，如果超过10000请使用  {@link #queryAllByConditions}
     * @return
     * @throws IOException
     */
    public List<Map<String, Object>> queryByConditions(String level, String messageKey, Long startTime, Long endTime, Integer size) throws IOException {
        List<Map<String, Object>> resultList = new ArrayList<>();
        if (StringUtils.isBlank(level) && StringUtils.isBlank(messageKey)) {
            LOGGER.error("参数level(日志级别)和messageKey(日志信息关键字)不能同时为空！");
            return resultList;
        }

        QueryBuilder query = generateQuery(level, messageKey, startTime, endTime);
        FieldSortBuilder order = SortBuilders.fieldSort(TIMESTAMP).order(SortOrder.DESC);
        SearchSourceBuilder searchBuilder = new SearchSourceBuilder();
        searchBuilder.timeout(TimeValue.timeValueMinutes(2L));
        searchBuilder.query(query);
        searchBuilder.sort(order);
        if (Objects.nonNull(size)) {
            searchBuilder.size(size);
        }

        SearchRequest request = new SearchRequest(INDEX).types(TYPE);
        request.source(searchBuilder);
        SearchResponse response = client.search(request, RequestOptions.DEFAULT);
        int failedShards = response.getFailedShards();
        if (failedShards > 0) {
            LOGGER.error("部分分片副本处理失败！");
            for (ShardSearchFailure failure : response.getShardFailures()) {
                String reason = failure.reason();
                LOGGER.error("分片处理失败原因：{}", reason);
            }
        }
        List<Map<String, Object>> list = parseSearchResponse(response);
        if (!list.isEmpty()) {
            resultList.addAll(list);
        }
        return resultList;
    }
    private QueryBuilder generateQuery(String level, String messageKey, Long startTime, Long endTime) {
        // term query(检索level)
        TermQueryBuilder levelQuery = null;
        if (StringUtils.isNotBlank(level)) {
            levelQuery = QueryBuilders.termQuery("level", level.toLowerCase());
        }
        // match query(检索message)
        MatchQueryBuilder messageQuery = null;
        if (StringUtils.isNotBlank(messageKey)) {
            messageQuery = QueryBuilders.matchQuery("message", messageKey);
        }
        // range query(检索timestamp)
        RangeQueryBuilder timeQuery = QueryBuilders.rangeQuery(TIMESTAMP);
        timeQuery.format("epoch_millis");
        if (Objects.isNull(startTime)) {
            if (Objects.isNull(endTime)) {
                timeQuery = null;
            } else {
                timeQuery.lte(endTime);
            }
        } else {
            if (Objects.isNull(endTime)) {
                timeQuery.gte(startTime);
            } else {
                timeQuery.gte(startTime).lte(endTime);
            }
        }
        // 将上述三个query组合
        BoolQueryBuilder boolQuery = QueryBuilders.boolQuery();
        if (Objects.nonNull(levelQuery)) {
            boolQuery.must(levelQuery);
        }
        if (Objects.nonNull(messageQuery)) {
            boolQuery.must(messageQuery);
        }
        if (Objects.nonNull(timeQuery)) {
            boolQuery.must(timeQuery);
        }
        return boolQuery;
    }
    private List<Map<String, Object>> parseSearchResponse(SearchResponse response){
        List<Map<String, Object>> resultList = new ArrayList<>();
        SearchHit[] hits = response.getHits().getHits();
        for (SearchHit hit : hits) {
            resultList.add(hit.getSourceAsMap());
        }
        return resultList;
    }

    /**
     * 根据条件，搜索全部符合的记录(参数level和messageKey不能同时为空)
     * @param level 日志级别，可以为空
     * @param messageKey 日志信息关键字，可以为空
     * @param startTime 日志起始时间，可以为空
     * @param endTime 日志结束时间，可以为空
     * @return
     */
    public List<Map<String, Object>> queryAllByConditions(String level, String messageKey, Long startTime, Long endTime) throws IOException {
        List<Map<String, Object>> resultList = new ArrayList<>();
        if (StringUtils.isBlank(level) && StringUtils.isBlank(messageKey)) {
            LOGGER.error("参数level(日志级别)和messageKey(日志信息关键字)不能同时为空！");
            return resultList;
        }

        QueryBuilder query = generateQuery(level, messageKey, startTime, endTime);
        FieldSortBuilder order = SortBuilders.fieldSort(TIMESTAMP).order(SortOrder.DESC);
        SearchSourceBuilder searchBuilder = new SearchSourceBuilder();
        searchBuilder.query(query).sort(order);
        searchBuilder.size(500);

        // 初始化 scroll 上下文
        SearchRequest request = new SearchRequest(INDEX).types(TYPE);
        final Scroll scroll = new Scroll(TimeValue.timeValueMinutes(1L));
        request.source(searchBuilder).scroll(scroll);
        SearchResponse response = client.search(request, RequestOptions.DEFAULT);
        String scrollId = response.getScrollId();
        SearchHit[] searchHits = response.getHits().getHits();
        // 把第一次scroll的数据添加到结果List中
        for (SearchHit searchHit : searchHits) {
            resultList.add(searchHit.getSourceAsMap());
        }
        // 通过传递scrollId循环取出所有相关文档
        while (searchHits != null && searchHits.length > 0) {
            SearchScrollRequest scrollRequest = new SearchScrollRequest(scrollId);
            scrollRequest.scroll(scroll);
            response = client.scroll(scrollRequest, RequestOptions.DEFAULT);
            scrollId = response.getScrollId();
            searchHits = response.getHits().getHits();
            // 循环添加剩下的数据
            for (SearchHit searchHit : searchHits) {
                resultList.add(searchHit.getSourceAsMap());
            }
        }
        // 清理 scroll 上下文
        ClearScrollRequest clearScrollRequest = new ClearScrollRequest();
        clearScrollRequest.addScrollId(scrollId);
        client.clearScroll(clearScrollRequest, RequestOptions.DEFAULT);
        return resultList;
    }


}