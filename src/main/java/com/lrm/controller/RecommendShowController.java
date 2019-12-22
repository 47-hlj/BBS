package com.lrm.controller;

import com.lrm.po.Recommend;
import com.lrm.service.BlogService;
import com.lrm.service.RecommendService;
import com.lrm.vo.BlogQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class RecommendShowController {

    @Autowired
    private RecommendService recommendService;

    @Autowired
    private BlogService blogService;

    @GetMapping("/recommends/{id}")
    public String types(@PageableDefault(size = 8, sort = {"updateTime"}, direction = Sort.Direction.DESC) Pageable pageable,
                        @PathVariable Long id, Model model) {
        List<Recommend> recommends = recommendService.listRecommendTop(10000);
        if (id == -1) {
            id = recommends.get(0).getId();//得到第1个的id
        }
        System.out.println("the id=" +id);
        BlogQuery blogQuery = new BlogQuery();
        blogQuery.setRecommendId(id);
        model.addAttribute("recommends", recommends);
        model.addAttribute("page", blogService.listBlog(pageable, blogQuery));//通过得到的id得到页数
        model.addAttribute("activeRecommendId", id);
        return "recommends";
    }
}
