package com.evento.eventorpa.controller;

import com.evento.eventorpa.AdminSelenium;
import com.evento.eventorpa.EventoSelenium;
import com.evento.eventorpa.entity.User;
import com.evento.eventorpa.repository.UserRepository;
import org.openqa.selenium.WebElement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.springframework.web.servlet.function.RequestPredicates.GET;

@RestController
public class RpaController {

    @Autowired
    UserRepository userRepository;

    @GetMapping("/start")
    public String test() throws MalformedURLException {

        EventoSelenium evento = new EventoSelenium();
        List<Integer> idlist= new ArrayList<>();
        String userid = "ghb8302@naver.com";

        List<User> ids = userRepository.findByUseridOrderByIdDesc(userid);

        for (User id : ids) {
            idlist.add(id.getContentid());
        }


        evento.Start(idlist);

        return "Hello World!";
    }

    //퍼오기
    @GetMapping("/add")
    public String add() throws MalformedURLException {

        AdminSelenium evento = new AdminSelenium();
        evento.Init();

        evento.ADD();

        return "Hello World!";
    }

    @GetMapping("/get")
    public String get() throws MalformedURLException {

        AdminSelenium evento = new AdminSelenium();
        evento.Init();

        String userid = "ghb8302@naver.com";

        evento.Adminset(userid);

        boolean lastcheck = true;
        boolean firstcheck = true;

        while (lastcheck) {

            Map map = evento.GET(userid,firstcheck);

            if (map == null) {
                System.out.println("끝!");
                break;
            }

            firstcheck = false;

            List<WebElement> list = (List<WebElement>) map.get("list");
            lastcheck = (Boolean) map.get("bnext");

            for (WebElement td : list) {

                //저장
                String content = td.getText();

                if (!"".equals(content)) {

                    int contentid = Integer.parseInt(content);

                    User user = userRepository.findByContentid(contentid);

                    if (user != null) {
                        System.out.println(contentid + "는 이미 등록한 자료입니다. 여기서 종료.");
                        lastcheck = false;
                        break;
                    }

                    User newuser = new User();

                    newuser.setUserid(userid);
                    newuser.setContentid(contentid);

                    userRepository.save(newuser);
                }
            }

            if (!lastcheck) {
                System.out.println("마지막 페이지입니다.");
                break;
            }
        }

        return "Hello World!";
    }
}
