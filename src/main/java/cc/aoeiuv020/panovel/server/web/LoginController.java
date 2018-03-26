package cc.aoeiuv020.panovel.server.web;

import cc.aoeiuv020.panovel.server.common.Constants;
import cc.aoeiuv020.panovel.server.service.HomeService;
import cc.aoeiuv020.panovel.server.web.form.LoginForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@Controller
public class LoginController extends BaseController {

    @Autowired
    private HomeService homeService;


    @RequestMapping("login")
    public String login(LoginForm loginForm, BindingResult bindingResult, HttpSession session) {
        //这部分只是用做模拟，请从数据库中获取user对象
        Map<String, Object> user = new HashMap<>();
        user.put("username", "请修LoginController");
        session.setAttribute(Constants.SK_USER, user);
        return "redirect:home";
    }

    @RequestMapping("home")
    public String home(ModelMap modelMap) {

        modelMap.put("count", homeService.countBooks());

        return "home";
    }

}
