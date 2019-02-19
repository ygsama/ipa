package g.ygsama.ipa.web;

import com.alibaba.fastjson.JSONObject;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/source")
public class InvokeController {

    /************     一个参数     *********/
    @RequestMapping(value = "/{arg0}", method = RequestMethod.GET)
    public Object invoke1(@PathVariable("arg0") String arg0) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("arg0", arg0);
        return jsonObject;
    }

    @RequestMapping(value = "/{arg0}", method = RequestMethod.POST)
    public Object invoke1Post(@PathVariable("arg0") String arg0) {
        return "Hello World";
    }

    @RequestMapping(value = "/{arg0}", method = RequestMethod.PUT)
    public Object invoke1Put(@PathVariable("arg0") String arg0) {
        return "Hello World";
    }

    @RequestMapping(value = "/{arg0}", method = RequestMethod.DELETE)
    public Object invoke1Delete(@PathVariable("arg0") String arg0) {
        return "Hello World";
    }

}
