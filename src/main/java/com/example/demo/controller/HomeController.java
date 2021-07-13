package com.example.demo.controller;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.example.demo.model.service.comment.CommentArticleService;
import com.example.demo.model.service.forum.ForumService;
import com.example.demo.model.service.mall.InventoryService;


@Controller
@SessionAttributes({ "Vendor", "Person", "LoginID", "Category" })
public class HomeController {
	@Autowired
	CommentArticleService commentArticleService;
	@Autowired
	ForumService forumService;
	@Autowired
	ServletContext context;
	@Autowired
	InventoryService inventoryService;
	
	@GetMapping({"/index"})
	public String index(Model model) {
		
		
		model.addAttribute("most", forumService.getView());
		model.addAttribute("recent",forumService.getTime());
		model.addAttribute("mostgood", commentArticleService.GetTextGoodTotalMost());
		model.addAttribute("mostcheck", commentArticleService.GetCheckCountMost());
		model.addAttribute("mostnew", commentArticleService.GetNewMost());
		model.addAttribute("newestProduct", inventoryService.queryNewestProduct());
		
		return "index";
	}
	
	
}
