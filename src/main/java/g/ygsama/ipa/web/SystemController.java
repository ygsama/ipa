package g.ygsama.ipa.web;

import com.alibaba.fastjson.JSONObject;
import g.ygsama.ipa.filter.CORSFilter;
import g.ygsama.ipa.service.system.MongoSystemService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/system")
public class SystemController {

    private static Log log = LogFactory.getLog(CORSFilter.class);

    @Autowired
    private MongoSystemService systemService;

    @RequestMapping(value = "/project", method = RequestMethod.POST)
    public Object addProject(@RequestBody JSONObject obj) {
        JSONObject res = systemService.createProject(obj);
        return res;
    }


    @RequestMapping(value = "/project/{_id}", method = RequestMethod.POST)
    public Object updateProject(@RequestParam String _id, @RequestBody JSONObject obj) {

        return obj;
    }

    @RequestMapping(value = "/project", method = RequestMethod.GET)
    public List<Object> listProject(@RequestParam(required = false,defaultValue = "1") int curPage,
                                    @RequestParam(required = false,defaultValue = "10") int pageSize,
                                    @RequestParam(required = false,defaultValue = "0") int totalPage) {
        return systemService.listProject(curPage, pageSize, totalPage);
    }

    @RequestMapping(value = "/project", method = RequestMethod.DELETE)
    public Object delProject(@RequestBody JSONObject json) {

        return json;
    }

    @RequestMapping(value = "/api", method = RequestMethod.POST)
    public Object addApi(@RequestBody JSONObject obj) {

        return obj;
    }


    @RequestMapping(value = "/api/{_id}", method = RequestMethod.POST)
    public Object updateApi(@RequestParam String _id, @RequestBody JSONObject obj) {

        return obj;
    }

    @RequestMapping(value = "/api", method = RequestMethod.GET)
    public Object listApi() {
        return new JSONObject();
    }

    @RequestMapping(value = "/api", method = RequestMethod.DELETE)
    public Object delApi(@RequestBody JSONObject json) {

        return json;
    }
}
