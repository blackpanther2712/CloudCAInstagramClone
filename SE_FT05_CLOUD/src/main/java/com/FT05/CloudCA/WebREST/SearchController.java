package com.FT05.CloudCA.WebREST;

import com.FT05.CloudCA.Entity.User;
import com.FT05.CloudCA.Service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Controller
public class SearchController {


    @Autowired
    UserService userService;


    public static final String REST_SERVICE_URI = "https://search-user-search-wiyvojt6kxc6wrq63pzso72bwq.ap-southeast-1.es.amazonaws.com/users/_search?q=";



    @PostMapping("/search")
    public String showPage(Model model, @RequestParam("searchuser") String search) throws JSONException, ParseException, IOException {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = userService.findUserByEmail(auth.getName());

        List<User> followerList = currentUser.getFollowing();
        List<Long> followerId = new ArrayList<>();

        RestTemplate restTemplate = new RestTemplate();
        String json = restTemplate.getForObject(REST_SERVICE_URI+search, String.class);
        JSONObject jsonObject = new JSONObject(json);

        JSONArray hits = jsonObject.getJSONObject("hits").getJSONArray("hits");
        ObjectMapper objectMapper = new ObjectMapper();
        List<User> searchList = new ArrayList<>();

        if (hits != null) {

            for (int i = 0; i < hits.length(); i++) {
                JSONObject objAtIndex =  hits.optJSONObject(i);
                if (objAtIndex != null) {
                    JSONObject userJson = objAtIndex.getJSONObject("_source");
                    User user = objectMapper.readValue(userJson.toString(), User.class);
                    searchList.add(user);
                }
            }

        }

        for (User user : followerList) {
            followerId.add(user.getId());
        }

        for (User user : searchList) {
            if(followerId.contains(user.getId())) {
                user.setFollowIndicator("F");
                System.out.println("Follow1");
            }
            else {
                user.setFollowIndicator("N");
                System.out.println("Unfollow1");
            }
        }


        model.addAttribute("searchResult",searchList);
        model.addAttribute("currentUser", userService.findUserByEmail(auth.getName()));

        return "search";

    }


}

