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


    /************     两个参数     *********/
    @RequestMapping(value = "/{arg0}/{arg1}", method = RequestMethod.GET)
    public Object invoke2(@PathVariable("arg0") String arg0,
                          @PathVariable("arg1") String arg1) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("arg0", arg0);
        jsonObject.put("arg1", arg1);
        return jsonObject;
    }

    @RequestMapping(value = "/{arg0}/{arg1}", method = RequestMethod.POST)
    public Object invoke2Post(@PathVariable("arg0") String arg0,
                              @PathVariable("arg1") String arg1,
                              @RequestBody Object body) {
        return "Hello World";
    }

    @RequestMapping(value = "/{arg0}/{arg1}", method = RequestMethod.PUT)
    public Object invoke2Put(@PathVariable("arg0") String arg0,
                             @PathVariable("arg1") String arg1,
                             @RequestBody Object body) {
        return "Hello World";
    }

    @RequestMapping(value = "/{arg0}/{arg1}", method = RequestMethod.DELETE)
    public Object invoke2Delete(@PathVariable("arg0") String arg0,
                                @PathVariable("arg1") String arg1) {
        return "Hello World";
    }

    /************     三个参数     *********/
    @RequestMapping(value = "/{arg0}/{arg1}/{arg2}", method = RequestMethod.GET)
    public Object invoke3(@PathVariable("arg0") String arg0,
                          @PathVariable("arg1") String arg1,
                          @PathVariable("arg2") String arg2) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("arg0", arg0);
        jsonObject.put("arg1", arg1);
        jsonObject.put("arg2", arg2);
        return jsonObject;
    }

    @RequestMapping(value = "/{arg0}/{arg1}/{arg2}", method = RequestMethod.POST)
    public Object invoke3Post(@PathVariable("arg0") String arg0,
                              @PathVariable("arg1") String arg1,
                              @PathVariable("arg2") String arg2,
                              @RequestBody Object body) {
        return "Hello World";
    }

    @RequestMapping(value = "/{arg0}/{arg1}/{arg2}", method = RequestMethod.PUT)
    public Object invoke3Put(@PathVariable("arg0") String arg0,
                             @PathVariable("arg1") String arg1,
                             @PathVariable("arg2") String arg2,
                             @RequestBody Object body) {
        return "Hello World";
    }

    @RequestMapping(value = "/{arg0}/{arg1}/{arg2}", method = RequestMethod.DELETE)
    public Object invoke3Delete(@PathVariable("arg0") String arg0,
                                @PathVariable("arg1") String arg1,
                                @PathVariable("arg2") String arg2) {
        return "Hello World";
    }
}
