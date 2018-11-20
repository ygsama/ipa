package g.ygsama.ipa.domain;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

public class ResultJson extends JSONObject {

    public ResultJson() {
        super();
        super.put("code", "00000");
        super.put("msg", "success");
    }

    public ResultJson(int b) {
        super();
        super.put("code", "FFFFF");
        super.put("msg", "failed");
    }

    public ResultJson(Object json) {
        super();
        super.put("code", "00000");
        super.put("msg", "success");
        super.put("data", json);
    }


    public ResultJson(String code, String msg) {
        super();
        super.put("code", code);
        super.put("msg", msg);
    }

    public ResultJson(String code, String msg, JSON json) {
        super();
        super.put("code", code);
        super.put("msg", msg);
        super.put("data", json);
    }
}
