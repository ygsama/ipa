package g.ygsama.ipa.service.invoke;

import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

@Service("commonService")
public class MongoCommonService {

    @Autowired
    private MongoTemplate mongoTemplate;

    public void save(JSONObject json, String collectionName) {
        mongoTemplate.save(json, collectionName);
    }

    public void update(JSONObject json, String collectionName) {
        // 获取并组装update的字段 K,V
        // 获取update的条件

        Query query = new Query(Criteria.where("retCode").is(json.getString("retCode")));
        Update update = new Update();
        json.forEach((k, v) -> {
            update.set(k, v);
        });
        mongoTemplate.upsert(query, update, collectionName);
    }

    public void list(JSONObject json, String collectionName) {
        mongoTemplate.save(json, collectionName);
    }
}
