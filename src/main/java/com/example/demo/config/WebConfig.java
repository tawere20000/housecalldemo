package com.example.demo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.example.demo.interceptor.LonginInterceptor;

@Configuration
public class WebConfig implements WebMvcConfigurer{

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		
		registry.addInterceptor(new LonginInterceptor())
				.addPathPatterns("/**")
				.excludePathPatterns("/loginCheck","/css/**","/icons/**","/images/**","/js/**","/cookieLogin/**",
						"/plugins/**","/backendlogin","/temp/**",
						"/mallindex.html","/shop.html","/mall/**","/quickView/**","/singleProduct/**","/shoppingCart/quickview","/ordercomplete.html",
						"/index","/data/**","/login","/registerMember","/checkEmailAjax","/checkAccountAjax","/forgetPassword","/registerVendor","/picture/**",
						"/CommentArticles","/ArticleInner/**","/CheckcountPlus/**","/CommentArticleschangeOnlyPageNo","/CommentArticleschange",
						"/comment/**","/articlepicture/**",
						"/tsimage/**","/troubleindex","/OrderInner/**",
						"/page/**","/AllForumsP","/AllForums","/forum/**","/Checkcount/**","/forumStoreAjax","/forumPicture/**");
	}
}
