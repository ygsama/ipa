package g.ygsama.ipa.service.system;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.mongodb.BasicDBObject;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.model.Filters;
import g.ygsama.ipa.domain.ResultJson;
import org.bson.BasicBSONObject;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service("systemService")
public class MongoSystemService {

    @Autowired
    private MongoTemplate mongoTemplate;

    public JSONObject createProject(JSONObject json) {
        ResultJson resultJson;
        try {
            mongoTemplate.save(json, "__projects__");
            resultJson = new ResultJson();
        }catch (Exception e){
            resultJson = new ResultJson(1);
        }
        return resultJson;
    }

    public void updateProject(JSONObject json, String collectionName) {
        // 获取并组装update的字段 K,V
        // 获取update的条件

        Query query = new Query(Criteria.where("retCode").is(json.getString("retCode")));
        Update update = new Update();
        json.forEach((k, v) -> update.set(k, v));
        mongoTemplate.upsert(query, update, collectionName);
    }

    public List<Object> listProject(int curPage, int pageSize , int totalPage) {
//        mongoTemplate.find();
        MongoCollection<Document> collection = mongoTemplate.getCollection("__projects__");
//        collection.find(Filters.eq("","")).limit(10);
//        Bson orderBy = new BasicDBObject("_id", 1);
        MongoCursor<Document> iterator = collection.find().skip((curPage - 1) * pageSize).limit(pageSize).iterator();
        JSONArray jsonArray = new JSONArray();
        iterator.forEachRemaining(document -> jsonArray.add(document));
        return jsonArray;
    }

    public void delProject(JSONObject json, String collectionName) {
        mongoTemplate.save(json, collectionName);
    }

}
