package projekt.wsb.linkbinder.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
class RedirectController {

    @RequestMapping(value = "/redirectToSignUpPage", method = RequestMethod.GET)
    String redirectToSignUpPage() {
        return "registration";
    }

    @RequestMapping(value = "/redirectToHomePage", method = RequestMethod.GET)
    String redirectToHomePage() {
        return "index";
    }
}
