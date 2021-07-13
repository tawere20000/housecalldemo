$(function() {
		//回覆按讚回覆按讚回覆按讚回覆按讚回覆按讚回覆按讚回覆按讚回覆按讚回覆按讚回覆按讚回覆按讚回覆按讚回覆按讚回覆按讚回覆按讚回覆按讚回覆按讚回覆按讚回覆按讚回覆按讚
		//回覆按讚回覆按讚回覆按讚回覆按讚回覆按讚回覆按讚回覆按讚回覆按讚回覆按讚回覆按讚回覆按讚回覆按讚回覆按讚回覆按讚回覆按讚回覆按讚回覆按讚回覆按讚回覆按讚回覆按讚
		$(".ReplyJudge0")
				.each(
						function() {
							let flag1 = true;
							let flag2 = true;
							$(this)
									.children(".ReplyImg1")
									.each(
											function() {
												$(this)
														.mouseover(
																function() {
																	if (flag1) {
																		$(this)
																				.prop(
																						"src",
																						"/comment/commentimages/實心讚.jpg")
																	}
																})
														.mouseout(
																function() {
																	if (flag1) {
																		$(this)
																				.prop(
																						"src",
																						"/comment/commentimages/空讚.jpg")
																	}
																})
														.click(
																function() {
																	if (flag1) {
																		$(this)
																				.prop(
																						"src",
																						"/comment/commentimages/實心讚.jpg")
																		$(this)
																				.next()
																				.next()
																				.prop(
																						"src",
																						"/comment/commentimages/空倒讚.jpg")
																		flag1 = false;
																		flag2 = true;
																		let good = $(
																				this)
																				.prev()
																				.prev()
																				.prev()
																				.val()
																		let g = parseInt(good) + 1
																		let bad = $(
																				this)
																				.prev()
																				.prev()
																				.val()
																		let ajaxReplyId = $(
																				this)
																				.prev()
																				.val()
																		$(this)
																				.next()
																				.text(
																						g);
																		$(this)
																				.next()
																				.next()
																				.next()
																				.text(
																						bad);
																		$
																				.ajax({
																					url : "/ReplyJudgeAjax",
																					type : "POST",
																					data : {
																						"ajaxReplyJudgeResult" : 1,
																						"ajaxReplyId" : ajaxReplyId
																					},
																					dataType : "JSON",
																					success : function(
																							result) {

																					}
																				})
																	} else {
																		$(this)
																				.prop(
																						"src",
																						"/comment/commentimages/空讚.jpg")
																		flag1 = true;
																		let good = $(
																				this)
																				.prev()
																				.prev()
																				.prev()
																				.val()
																		let bad = $(
																				this)
																				.prev()
																				.prev()
																				.val()
																		let ajaxReplyId = $(
																				this)
																				.prev()
																				.val()
																		$(this)
																				.next()
																				.text(
																						good);
																		$(this)
																				.next()
																				.next()
																				.next()
																				.text(
																						bad);
																		$
																				.ajax({
																					url : "/ReplyJudgeAjax",
																					type : "POST",
																					data : {
																						"ajaxReplyJudgeResult" : 1,
																						"ajaxReplyId" : ajaxReplyId
																					},
																					dataType : "JSON",
																					success : function(
																							result) {

																					}
																				})
																	}
																})

											})
							$(this)
									.children(".ReplyImg2")
									.each(
											function() {
												$(this)
														.mouseover(
																function() {
																	if (flag2) {
																		$(this)
																				.prop(
																						"src",
																						"/comment/commentimages/實倒讚.jpg")
																	}
																})
														.mouseout(
																function() {
																	if (flag2) {
																		$(this)
																				.prop(
																						"src",
																						"/comment/commentimages/空倒讚.jpg")
																	}
																})
														.click(
																function() {
																	if (flag2) {
																		$(this)
																				.prop(
																						"src",
																						"/comment/commentimages/實倒讚.jpg")
																		$(this)
																				.prev()
																				.prev()
																				.prop(
																						"src",
																						"/comment/commentimages/空讚.jpg")
																		flag2 = false;
																		flag1 = true;
																		let good = $(
																				this)
																				.prev()
																				.prev()
																				.prev()
																				.prev()
																				.prev()
																				.val()
																		let bad = $(
																				this)
																				.prev()
																				.prev()
																				.prev()
																				.prev()
																				.val()
																		let b = parseInt(bad) + 1
																		let ajaxReplyId = $(
																				this)
																				.prev()
																				.prev()
																				.prev()
																				.val()
																		$(this)
																				.prev()
																				.text(
																						good);
																		$(this)
																				.next()
																				.text(
																						b);
																		$
																				.ajax({
																					url : "/ReplyJudgeAjax",
																					type : "POST",
																					data : {
																						"ajaxReplyJudgeResult" : 2,
																						"ajaxReplyId" : ajaxReplyId
																					},
																					dataType : "JSON",
																					success : function(
																							result) {
																					}
																				})

																	} else {
																		$(this)
																				.prop(
																						"src",
																						"/comment/commentimages/空倒讚.jpg")
																		flag2 = true;
																		let good = $(
																				this)
																				.prev()
																				.prev()
																				.prev()
																				.prev()
																				.prev()
																				.val()
																		let bad = $(
																				this)
																				.prev()
																				.prev()
																				.prev()
																				.prev()
																				.val()
																		let ajaxReplyId = $(
																				this)
																				.prev()
																				.prev()
																				.prev()
																				.val()
																		$(this)
																				.prev()
																				.text(
																						good);
																		$(this)
																				.next()
																				.text(
																						bad);
																		$
																				.ajax({
																					url : "/ReplyJudgeAjax",
																					type : "POST",
																					data : {
																						"ajaxReplyJudgeResult" : 2,
																						"ajaxReplyId" : ajaxReplyId
																					},
																					dataType : "JSON",
																					success : function(
																							result) {

																					}
																				})
																	}
																});
											})
						})
		$(".ReplyJudge1")
				.each(
						function() {
							let flag1 = false;
							let flag2 = true;
							$(this)
									.children(".ReplyImg1")
									.each(
											function() {
												$(this)
														.mouseover(
																function() {
																	if (flag1) {
																		$(this)
																				.prop(
																						"src",
																						"/comment/commentimages/實心讚.jpg")
																	}
																})
														.mouseout(
																function() {
																	if (flag1) {
																		$(this)
																				.prop(
																						"src",
																						"/comment/commentimages/空讚.jpg")
																	}
																})
														.click(
																function() {
																	if (flag1) {
																		$(this)
																				.prop(
																						"src",
																						"/comment/commentimages/實心讚.jpg")
																		$(this)
																				.next()
																				.next()
																				.prop(
																						"src",
																						"/comment/commentimages/空倒讚.jpg")
																		flag1 = false;
																		flag2 = true;
																		let good = $(
																				this)
																				.prev()
																				.prev()
																				.prev()
																				.val()
																		let g = parseInt(good)
																		let bad = $(
																				this)
																				.prev()
																				.prev()
																				.val()
																		let ajaxReplyId = $(
																				this)
																				.prev()
																				.val()
																		$(this)
																				.next()
																				.text(
																						g);
																		$(this)
																				.next()
																				.next()
																				.next()
																				.text(
																						bad);
																		$
																				.ajax({
																					url : "/ReplyJudgeAjax",
																					type : "POST",
																					data : {
																						"ajaxReplyJudgeResult" : 1,
																						"ajaxReplyId" : ajaxReplyId
																					},
																					dataType : "JSON",
																					success : function(
																							result) {

																					}
																				})
																	} else {
																		$(this)
																				.prop(
																						"src",
																						"/comment/commentimages/空讚.jpg")
																		flag1 = true;
																		let good = $(
																				this)
																				.prev()
																				.prev()
																				.prev()
																				.val()
																		let g = parseInt(good) - 1
																		let bad = $(
																				this)
																				.prev()
																				.prev()
																				.val()
																		let ajaxReplyId = $(
																				this)
																				.prev()
																				.val()
																		$(this)
																				.next()
																				.text(
																						g);
																		$(this)
																				.next()
																				.next()
																				.next()
																				.text(
																						bad);
																		$
																				.ajax({
																					url : "/ReplyJudgeAjax",
																					type : "POST",
																					data : {
																						"ajaxReplyJudgeResult" : 1,
																						"ajaxReplyId" : ajaxReplyId
																					},
																					dataType : "JSON",
																					success : function(
																							result) {

																					}
																				})
																	}
																})

											})
							$(this)
									.children(".ReplyImg2")
									.each(
											function() {
												$(this)
														.mouseover(
																function() {
																	if (flag2) {
																		$(this)
																				.prop(
																						"src",
																						"/comment/commentimages/實倒讚.jpg")
																	}
																})
														.mouseout(
																function() {
																	if (flag2) {
																		$(this)
																				.prop(
																						"src",
																						"/comment/commentimages/空倒讚.jpg")
																	}
																})
														.click(
																function() {
																	if (flag2) {
																		$(this)
																				.prop(
																						"src",
																						"/comment/commentimages/實倒讚.jpg")
																		$(this)
																				.prev()
																				.prev()
																				.prop(
																						"src",
																						"/comment/commentimages/空讚.jpg")
																		flag2 = false;
																		flag1 = true;
																		let good = $(
																				this)
																				.prev()
																				.prev()
																				.prev()
																				.prev()
																				.prev()
																				.val()
																		let g = parseInt(good) - 1
																		let bad = $(
																				this)
																				.prev()
																				.prev()
																				.prev()
																				.prev()
																				.val()
																		let b = parseInt(bad) + 1
																		let ajaxReplyId = $(
																				this)
																				.prev()
																				.prev()
																				.prev()
																				.val()
																		$(this)
																				.prev()
																				.text(
																						g);
																		$(this)
																				.next()
																				.text(
																						b);
																		$
																				.ajax({
																					url : "/ReplyJudgeAjax",
																					type : "POST",
																					data : {
																						"ajaxReplyJudgeResult" : 2,
																						"ajaxReplyId" : ajaxReplyId
																					},
																					dataType : "JSON",
																					success : function(
																							result) {
																					}
																				})

																	} else {
																		$(this)
																				.prop(
																						"src",
																						"/comment/commentimages/空倒讚.jpg")
																		flag2 = true;
																		let good = $(
																				this)
																				.prev()
																				.prev()
																				.prev()
																				.prev()
																				.prev()
																				.val()
																		let g = parseInt(good) - 1
																		let bad = $(
																				this)
																				.prev()
																				.prev()
																				.prev()
																				.prev()
																				.val()
																		let ajaxReplyId = $(
																				this)
																				.prev()
																				.prev()
																				.prev()
																				.val()
																		$(this)
																				.prev()
																				.text(
																						g);
																		$(this)
																				.next()
																				.text(
																						bad);
																		$
																				.ajax({
																					url : "/ReplyJudgeAjax",
																					type : "POST",
																					data : {
																						"ajaxReplyJudgeResult" : 2,
																						"ajaxReplyId" : ajaxReplyId
																					},
																					dataType : "JSON",
																					success : function(
																							result) {

																					}
																				})
																	}
																});
											})
						})
		$(".ReplyJudge2")
				.each(
						function() {
							let flag1 = true;
							let flag2 = false;
							$(this)
									.children(".ReplyImg1")
									.each(
											function() {
												$(this)
														.mouseover(
																function() {
																	if (flag1) {
																		$(this)
																				.prop(
																						"src",
																						"/comment/commentimages/實心讚.jpg")
																	}
																})
														.mouseout(
																function() {
																	if (flag1) {
																		$(this)
																				.prop(
																						"src",
																						"/comment/commentimages/空讚.jpg")
																	}
																})
														.click(
																function() {
																	if (flag1) {
																		$(this)
																				.prop(
																						"src",
																						"/comment/commentimages/實心讚.jpg")
																		$(this)
																				.next()
																				.next()
																				.prop(
																						"src",
																						"/comment/commentimages/空倒讚.jpg")
																		flag1 = false;
																		flag2 = true;
																		let good = $(
																				this)
																				.prev()
																				.prev()
																				.prev()
																				.val()
																		let g = parseInt(good) + 1
																		let bad = $(
																				this)
																				.prev()
																				.prev()
																				.val()
																		let b = parseInt(bad) - 1
																		let ajaxReplyId = $(
																				this)
																				.prev()
																				.val()
																		$(this)
																				.next()
																				.text(
																						g);
																		$(this)
																				.next()
																				.next()
																				.next()
																				.text(
																						b);
																		$
																				.ajax({
																					url : "/ReplyJudgeAjax",
																					type : "POST",
																					data : {
																						"ajaxReplyJudgeResult" : 1,
																						"ajaxReplyId" : ajaxReplyId
																					},
																					dataType : "JSON",
																					success : function(
																							result) {

																					}
																				})
																	} else {
																		$(this)
																				.prop(
																						"src",
																						"/comment/commentimages/空讚.jpg")
																		flag1 = true;
																		let good = $(
																				this)
																				.prev()
																				.prev()
																				.prev()
																				.val()
																		let bad = $(
																				this)
																				.prev()
																				.prev()
																				.val()
																		let b = parseInt(bad) - 1
																		let ajaxReplyId = $(
																				this)
																				.prev()
																				.val()
																		$(this)
																				.next()
																				.text(
																						good);
																		$(this)
																				.next()
																				.next()
																				.next()
																				.text(
																						b);
																		$
																				.ajax({
																					url : "/ReplyJudgeAjax",
																					type : "POST",
																					data : {
																						"ajaxReplyJudgeResult" : 1,
																						"ajaxReplyId" : ajaxReplyId
																					},
																					dataType : "JSON",
																					success : function(
																							result) {

																					}
																				})
																	}
																})

											})
							$(this)
									.children(".ReplyImg2")
									.each(
											function() {
												$(this)
														.mouseover(
																function() {
																	if (flag2) {
																		$(this)
																				.prop(
																						"src",
																						"/comment/commentimages/實倒讚.jpg")
																	}
																})
														.mouseout(
																function() {
																	if (flag2) {
																		$(this)
																				.prop(
																						"src",
																						"/comment/commentimages/空倒讚.jpg")
																	}
																})
														.click(
																function() {
																	if (flag2) {
																		$(this)
																				.prop(
																						"src",
																						"/comment/commentimages/實倒讚.jpg")
																		$(this)
																				.prev()
																				.prev()
																				.prop(
																						"src",
																						"/comment/commentimages/空讚.jpg")
																		flag2 = false;
																		flag1 = true;
																		let good = $(
																				this)
																				.prev()
																				.prev()
																				.prev()
																				.prev()
																				.prev()
																				.val()
																		let bad = $(
																				this)
																				.prev()
																				.prev()
																				.prev()
																				.prev()
																				.val()
																		let b = parseInt(bad)
																		let ajaxReplyId = $(
																				this)
																				.prev()
																				.prev()
																				.prev()
																				.val()
																		$(this)
																				.prev()
																				.text(
																						good);
																		$(this)
																				.next()
																				.text(
																						b);
																		$
																				.ajax({
																					url : "/ReplyJudgeAjax",
																					type : "POST",
																					data : {
																						"ajaxReplyJudgeResult" : 2,
																						"ajaxReplyId" : ajaxReplyId
																					},
																					dataType : "JSON",
																					success : function(
																							result) {
																					}
																				})

																	} else {
																		$(this)
																				.prop(
																						"src",
																						"/comment/commentimages/空倒讚.jpg")
																		flag2 = true;
																		let good = $(
																				this)
																				.prev()
																				.prev()
																				.prev()
																				.prev()
																				.prev()
																				.val()
																		let bad = $(
																				this)
																				.prev()
																				.prev()
																				.prev()
																				.prev()
																				.val()
																		let b = parseInt(bad) - 1
																		let ajaxReplyId = $(
																				this)
																				.prev()
																				.prev()
																				.prev()
																				.val()
																		$(this)
																				.prev()
																				.text(
																						good);
																		$(this)
																				.next()
																				.text(
																						b);
																		$
																				.ajax({
																					url : "/ReplyJudgeAjax",
																					type : "POST",
																					data : {
																						"ajaxReplyJudgeResult" : 2,
																						"ajaxReplyId" : ajaxReplyId
																					},
																					dataType : "JSON",
																					success : function(
																							result) {

																					}
																				})
																	}
																});
											})
						})

		//回覆按讚回覆按讚回覆按讚回覆按讚回覆按讚回覆按讚回覆按讚回覆按讚回覆按讚回覆按讚回覆按讚回覆按讚回覆按讚回覆按讚回覆按讚回覆按讚回覆按讚回覆按讚回覆按讚回覆按讚
		//回覆按讚回覆按讚回覆按讚回覆按讚回覆按讚回覆按讚回覆按讚回覆按讚回覆按讚回覆按讚回覆按讚回覆按讚回覆按讚回覆按讚回覆按讚回覆按讚回覆按讚回覆按讚回覆按讚回覆按讚
		//文章按讚文章按讚文章按讚文章按讚文章按讚文章按讚文章按讚文章按讚文章按讚文章按讚文章按讚文章按讚文章按讚文章按讚文章按讚文章按讚文章按讚文章按讚文章按讚文章按讚文章按讚文章按讚文章按讚文章按讚文章按讚文章按讚
		//文章按讚文章按讚文章按讚文章按讚文章按讚文章按讚文章按讚文章按讚文章按讚文章按讚文章按讚文章按讚文章按讚文章按讚文章按讚文章按讚文章按讚文章按讚文章按讚文章按讚文章按讚文章按讚文章按讚文章按讚文章按讚文章按讚
		$(".AticleJudge0")
				.each(
						function() {
							let flag1 = true;
							let flag2 = true;
							$(this)
									.children(".AticleImg1")
									.each(
											function() {
												$(this)
														.mouseover(
																function() {
																	if (flag1) {
																		$(this)
																				.prop(
																						"src",
																						"/comment/commentimages/實心讚.jpg")
																	}
																})
														.mouseout(
																function() {
																	if (flag1) {
																		$(this)
																				.prop(
																						"src",
																						"/comment/commentimages/空讚.jpg")
																	}
																})
														.click(
																function() {
																	if (flag1) {
																		$(this)
																				.prop(
																						"src",
																						"/comment/commentimages/實心讚.jpg")
																		$(this)
																				.next()
																				.next()
																				.prop(
																						"src",
																						"/comment/commentimages/空倒讚.jpg")
																		flag1 = false;
																		flag2 = true;
																		let good = $(
																				this)
																				.prev()
																				.prev()
																				.prev()
																				.val()
																		let g = parseInt(good) + 1
																		let bad = $(
																				this)
																				.prev()
																				.prev()
																				.val()
																		let ajaxArticleId = $(
																				this)
																				.prev()
																				.val()
																		$(this)
																				.next()
																				.text(
																						g);
																		$(this)
																				.next()
																				.next()
																				.next()
																				.text(
																						bad);
																		$
																				.ajax({
																					url : "/ArticleJudgeAjax",
																					type : "POST",
																					data : {
																						"ajaxArticleJudgeResult" : 1,
																						"ajaxArticleId" : ajaxArticleId
																					},
																					dataType : "JSON",
																					success : function(
																							result) {

																					}
																				})
																	} else {
																		$(this)
																				.prop(
																						"src",
																						"/comment/commentimages/空讚.jpg")
																		flag1 = true;
																		let good = $(
																				this)
																				.prev()
																				.prev()
																				.prev()
																				.val()
																		let bad = $(
																				this)
																				.prev()
																				.prev()
																				.val()
																		let ajaxArticleId = $(
																				this)
																				.prev()
																				.val()
																		$(this)
																				.next()
																				.text(
																						good);
																		$(this)
																				.next()
																				.next()
																				.next()
																				.text(
																						bad);
																		$
																				.ajax({
																					url : "/ArticleJudgeAjax",
																					type : "POST",
																					data : {
																						"ajaxArticleJudgeResult" : 1,
																						"ajaxArticleId" : ajaxArticleId
																					},
																					dataType : "JSON",
																					success : function(
																							result) {

																					}
																				})
																	}
																})

											})
							$(this)
									.children(".AticleImg2")
									.each(
											function() {
												$(this)
														.mouseover(
																function() {
																	if (flag2) {
																		$(this)
																				.prop(
																						"src",
																						"/comment/commentimages/實倒讚.jpg")
																	}
																})
														.mouseout(
																function() {
																	if (flag2) {
																		$(this)
																				.prop(
																						"src",
																						"/comment/commentimages/空倒讚.jpg")
																	}
																})
														.click(
																function() {
																	if (flag2) {
																		$(this)
																				.prop(
																						"src",
																						"/comment/commentimages/實倒讚.jpg")
																		$(this)
																				.prev()
																				.prev()
																				.prop(
																						"src",
																						"/comment/commentimages/空讚.jpg")
																		flag2 = false;
																		flag1 = true;
																		let good = $(
																				this)
																				.prev()
																				.prev()
																				.prev()
																				.prev()
																				.prev()
																				.val()
																		let bad = $(
																				this)
																				.prev()
																				.prev()
																				.prev()
																				.prev()
																				.val()
																		let b = parseInt(bad) + 1
																		let ajaxArticleId = $(
																				this)
																				.prev()
																				.prev()
																				.prev()
																				.val()
																		$(this)
																				.prev()
																				.text(
																						good);
																		$(this)
																				.next()
																				.text(
																						b);
																		$
																				.ajax({
																					url : "/ArticleJudgeAjax",
																					type : "POST",
																					data : {
																						"ajaxArticleJudgeResult" : 2,
																						"ajaxArticleId" : ajaxArticleId
																					},
																					dataType : "JSON",
																					success : function(
																							result) {
																					}
																				})

																	} else {
																		$(this)
																				.prop(
																						"src",
																						"/comment/commentimages/空倒讚.jpg")
																		flag2 = true;
																		let good = $(
																				this)
																				.prev()
																				.prev()
																				.prev()
																				.prev()
																				.prev()
																				.val()
																		let bad = $(
																				this)
																				.prev()
																				.prev()
																				.prev()
																				.prev()
																				.val()
																		let ajaxArticleId = $(
																				this)
																				.prev()
																				.prev()
																				.prev()
																				.val()
																		$(this)
																				.prev()
																				.text(
																						good);
																		$(this)
																				.next()
																				.text(
																						bad);
																		$
																				.ajax({
																					url : "/ArticleJudgeAjax",
																					type : "POST",
																					data : {
																						"ajaxArticleJudgeResult" : 2,
																						"ajaxArticleId" : ajaxArticleId
																					},
																					dataType : "JSON",
																					success : function(
																							result) {

																					}
																				})
																	}
																});
											})
						})
		$(".AticleJudge1")
				.each(
						function() {
							let flag1 = false;
							let flag2 = true;
							$(this)
									.children(".AticleImg1")
									.each(
											function() {
												$(this)
														.mouseover(
																function() {
																	if (flag1) {
																		$(this)
																				.prop(
																						"src",
																						"/comment/commentimages/實心讚.jpg")
																	}
																})
														.mouseout(
																function() {
																	if (flag1) {
																		$(this)
																				.prop(
																						"src",
																						"/comment/commentimages/空讚.jpg")
																	}
																})
														.click(
																function() {
																	if (flag1) {
																		$(this)
																				.prop(
																						"src",
																						"/comment/commentimages/實心讚.jpg")
																		$(this)
																				.next()
																				.next()
																				.prop(
																						"src",
																						"/comment/commentimages/空倒讚.jpg")
																		flag1 = false;
																		flag2 = true;
																		let good = $(
																				this)
																				.prev()
																				.prev()
																				.prev()
																				.val()
																		let g = parseInt(good)
																		let bad = $(
																				this)
																				.prev()
																				.prev()
																				.val()
																		let ajaxArticleId = $(
																				this)
																				.prev()
																				.val()
																		$(this)
																				.next()
																				.text(
																						g);
																		$(this)
																				.next()
																				.next()
																				.next()
																				.text(
																						bad);
																		$
																				.ajax({
																					url : "/ArticleJudgeAjax",
																					type : "POST",
																					data : {
																						"ajaxArticleJudgeResult" : 1,
																						"ajaxArticleId" : ajaxArticleId
																					},
																					dataType : "JSON",
																					success : function(
																							result) {

																					}
																				})
																	} else {
																		$(this)
																				.prop(
																						"src",
																						"/comment/commentimages/空讚.jpg")
																		flag1 = true;
																		let good = $(
																				this)
																				.prev()
																				.prev()
																				.prev()
																				.val()
																		let g = parseInt(good) - 1
																		let bad = $(
																				this)
																				.prev()
																				.prev()
																				.val()
																		let ajaxArticleId = $(
																				this)
																				.prev()
																				.val()
																		$(this)
																				.next()
																				.text(
																						g);
																		$(this)
																				.next()
																				.next()
																				.next()
																				.text(
																						bad);
																		$
																				.ajax({
																					url : "/ArticleJudgeAjax",
																					type : "POST",
																					data : {
																						"ajaxArticleJudgeResult" : 1,
																						"ajaxArticleId" : ajaxArticleId
																					},
																					dataType : "JSON",
																					success : function(
																							result) {

																					}
																				})
																	}
																})

											})
							$(this)
									.children(".AticleImg2")
									.each(
											function() {
												$(this)
														.mouseover(
																function() {
																	if (flag2) {
																		$(this)
																				.prop(
																						"src",
																						"/comment/commentimages/實倒讚.jpg")
																	}
																})
														.mouseout(
																function() {
																	if (flag2) {
																		$(this)
																				.prop(
																						"src",
																						"/comment/commentimages/空倒讚.jpg")
																	}
																})
														.click(
																function() {
																	if (flag2) {
																		$(this)
																				.prop(
																						"src",
																						"/comment/commentimages/實倒讚.jpg")
																		$(this)
																				.prev()
																				.prev()
																				.prop(
																						"src",
																						"/comment/commentimages/空讚.jpg")
																		flag2 = false;
																		flag1 = true;
																		let good = $(
																				this)
																				.prev()
																				.prev()
																				.prev()
																				.prev()
																				.prev()
																				.val()
																		let g = parseInt(good) - 1
																		let bad = $(
																				this)
																				.prev()
																				.prev()
																				.prev()
																				.prev()
																				.val()
																		let b = parseInt(bad) + 1
																		let ajaxArticleId = $(
																				this)
																				.prev()
																				.prev()
																				.prev()
																				.val()
																		$(this)
																				.prev()
																				.text(
																						g);
																		$(this)
																				.next()
																				.text(
																						b);
																		$
																				.ajax({
																					url : "/ArticleJudgeAjax",
																					type : "POST",
																					data : {
																						"ajaxArticleJudgeResult" : 2,
																						"ajaxArticleId" : ajaxArticleId
																					},
																					dataType : "JSON",
																					success : function(
																							result) {
																					}
																				})

																	} else {
																		$(this)
																				.prop(
																						"src",
																						"/comment/commentimages/空倒讚.jpg")
																		flag2 = true;
																		let good = $(
																				this)
																				.prev()
																				.prev()
																				.prev()
																				.prev()
																				.prev()
																				.val()
																		let g = parseInt(good) - 1
																		let bad = $(
																				this)
																				.prev()
																				.prev()
																				.prev()
																				.prev()
																				.val()
																		let ajaxArticleId = $(
																				this)
																				.prev()
																				.prev()
																				.prev()
																				.val()
																		$(this)
																				.prev()
																				.text(
																						g);
																		$(this)
																				.next()
																				.text(
																						bad);
																		$
																				.ajax({
																					url : "/ArticleJudgeAjax",
																					type : "POST",
																					data : {
																						"ajaxArticleJudgeResult" : 2,
																						"ajaxArticleId" : ajaxArticleId
																					},
																					dataType : "JSON",
																					success : function(
																							result) {

																					}
																				})
																	}
																});
											})
						})
		$(".AticleJudge2")
				.each(
						function() {
							let flag1 = true;
							let flag2 = false;
							$(this)
									.children(".AticleImg1")
									.each(
											function() {
												$(this)
														.mouseover(
																function() {
																	if (flag1) {
																		$(this)
																				.prop(
																						"src",
																						"/comment/commentimages/實心讚.jpg")
																	}
																})
														.mouseout(
																function() {
																	if (flag1) {
																		$(this)
																				.prop(
																						"src",
																						"/comment/commentimages/空讚.jpg")
																	}
																})
														.click(
																function() {
																	if (flag1) {
																		$(this)
																				.prop(
																						"src",
																						"/comment/commentimages/實心讚.jpg")
																		$(this)
																				.next()
																				.next()
																				.prop(
																						"src",
																						"/comment/commentimages/空倒讚.jpg")
																		flag1 = false;
																		flag2 = true;
																		let good = $(
																				this)
																				.prev()
																				.prev()
																				.prev()
																				.val()
																		let g = parseInt(good) + 1
																		let bad = $(
																				this)
																				.prev()
																				.prev()
																				.val()
																		let b = parseInt(bad) - 1
																		let ajaxArticleId = $(
																				this)
																				.prev()
																				.val()
																		$(this)
																				.next()
																				.text(
																						g);
																		$(this)
																				.next()
																				.next()
																				.next()
																				.text(
																						b);
																		$
																				.ajax({
																					url : "/ArticleJudgeAjax",
																					type : "POST",
																					data : {
																						"ajaxArticleJudgeResult" : 1,
																						"ajaxArticleId" : ajaxArticleId
																					},
																					dataType : "JSON",
																					success : function(
																							result) {

																					}
																				})
																	} else {
																		$(this)
																				.prop(
																						"src",
																						"/comment/commentimages/空讚.jpg")
																		flag1 = true;
																		let good = $(
																				this)
																				.prev()
																				.prev()
																				.prev()
																				.val()
																		let bad = $(
																				this)
																				.prev()
																				.prev()
																				.val()
																		let b = parseInt(bad) - 1
																		let ajaxArticleId = $(
																				this)
																				.prev()
																				.val()
																		$(this)
																				.next()
																				.text(
																						good);
																		$(this)
																				.next()
																				.next()
																				.next()
																				.text(
																						b);
																		$
																				.ajax({
																					url : "/ArticleJudgeAjax",
																					type : "POST",
																					data : {
																						"ajaxArticleJudgeResult" : 1,
																						"ajaxArticleId" : ajaxArticleId
																					},
																					dataType : "JSON",
																					success : function(
																							result) {

																					}
																				})
																	}
																})

											})
							$(this)
									.children(".AticleImg2")
									.each(
											function() {
												$(this)
														.mouseover(
																function() {
																	if (flag2) {
																		$(this)
																				.prop(
																						"src",
																						"/comment/commentimages/實倒讚.jpg")
																	}
																})
														.mouseout(
																function() {
																	if (flag2) {
																		$(this)
																				.prop(
																						"src",
																						"/comment/commentimages/空倒讚.jpg")
																	}
																})
														.click(
																function() {
																	if (flag2) {
																		$(this).prop("src","/comment/commentimages/實倒讚.jpg")
																		$(this).prev().prev().prop("src","/comment/commentimages/空讚.jpg")
																		flag2 = false;
																		flag1 = true;
																		let good = $(this).prev().prev().prev().prev().prev().val()
																		let bad = $(this).prev().prev().prev().prev().val()
																		let b = parseInt(bad)
																		let ajaxArticleId = $(this).prev().prev().prev().val()
																		$(this).prev().text(good);
																		$(this).next().text(b);
																		$.ajax({
																					url : "/ArticleJudgeAjax",
																					type : "POST",
																					data : {
																						"ajaxArticleJudgeResult" : 2,
																						"ajaxArticleId" : ajaxArticleId
																					},
																					dataType : "JSON",
																					success : function(
																							result) {
																					}
																				})

																	} else {
																		$(this)
																				.prop(
																						"src",
																						"/comment/commentimages/空倒讚.jpg")
																		flag2 = true;
																		let good = $(this).prev().prev().prev().prev().prev().val()
																		let bad = $(this).prev().prev().prev().prev().val()
																		let b = parseInt(bad) - 1
																		let ajaxArticleId = $(
																				this)
																				.prev()
																				.prev()
																				.prev()
																				.val()
																		$(this).prev().text(good);
																		$(this).next().text(b);
																		$.ajax({
																					url : "/ArticleJudgeAjax",
																					type : "POST",
																					data : {
																						"ajaxArticleJudgeResult" : 2,
																						"ajaxArticleId" : ajaxArticleId
																					},
																					dataType : "JSON",
																					success : function(
																							result) {

																					}
																				})
																	}
																});
											})
						})
		//文章按讚文章按讚文章按讚文章按讚文章按讚文章按讚文章按讚文章按讚文章按讚文章按讚文章按讚文章按讚文章按讚文章按讚文章按讚文章按讚文章按讚文章按讚文章按讚文章按讚文章按讚文章按讚文章按讚文章按讚文章按讚文章按讚
		//文章按讚文章按讚文章按讚文章按讚文章按讚文章按讚文章按讚文章按讚文章按讚文章按讚文章按讚文章按讚文章按讚文章按讚文章按讚文章按讚文章按讚文章按讚文章按讚文章按讚文章按讚文章按讚文章按讚文章按讚文章按讚文章按讚

		// 文章儲存ajax文章儲存ajax文章儲存ajax文章儲存ajax文章儲存ajax文章儲存ajax文章儲存ajax文章儲存ajax文章儲存ajax文章儲存ajax

		$(".AticleStore0").each(function() {
			let flag = true
			$(this).children("button").each(function() {
				let ajaxArticleId = $(this).prev().val()
				$(this).click(function() {
					console.log(ajaxArticleId)
					if (flag) {
						flag = false
						$(this).html("取消儲存")
						$.ajax({
							url : "/StoreAjax",
							type : "POST",
							data : {
								"ajaxArticleId" : ajaxArticleId
							},
							dataType : "JSON",
							success : function(result) {
								console.log(result)

							}
						})
					} else {
						$(this).html("儲存文章")
						flag = true
						$.ajax({
							url : "/StoreAjax",
							type : "POST",
							data : {
								"ajaxArticleId" : ajaxArticleId
							},
							dataType : "JSON",
							success : function(result) {
								console.log(result)
							}
						})

					}
				})

			})

		})
		$(".AticleStore1").each(function() {
			let flag = false
			$(this).children("button").each(function() {
				let ajaxArticleId = $(this).prev().val()
				$(this).click(function() {
					console.log(ajaxArticleId)
					if (flag) {
						$(this).html("取消儲存")
						flag = false
						$.ajax({
							url : "/StoreAjax",
							type : "POST",
							data : {
								"ajaxArticleId" : ajaxArticleId
							},
							dataType : "JSON",
							success : function(result) {
								console.log(result)
							}
						})
					} else {
						$(this).html("儲存文章")
						flag = true
						$.ajax({
							url : "/StoreAjax",
							type : "POST",
							data : {
								"ajaxArticleId" : ajaxArticleId
							},
							dataType : "JSON",
							success : function(result) {
								console.log(result)
							}
						})

					}
				})

			})

		})
		// 文章儲存ajax文章儲存ajax文章儲存ajax文章儲存ajax文章儲存ajax文章儲存ajax文章儲存ajax文章儲存ajax文章儲存ajax文章儲存ajax文章儲存ajax文章儲存ajax
		//文章檢舉文章檢舉文章檢舉文章檢舉文章檢舉文章檢舉文章檢舉文章檢舉文章檢舉文章檢舉文章檢舉文章檢舉文章檢舉文章檢舉文章檢舉文章檢舉文章檢舉文章檢舉文章檢舉文章檢舉文章檢舉
		$(".AticleReport0").each(function() {
			$(this).children(".AticleReportButton0").each(function() {
				let ajaxArticleId = $(this).prev().val()
				$(this).click(function() {
					console.log(ajaxArticleId)
					$(this).parent(".AticleReport0").html("您已完成檢舉!待管理員查證&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp")
					$.ajax({
						url : "/ReportCommentArticleAjax",
						type : "POST",
						data : {
							"ajaxViolationId" : 1,
							"ajaxArticleId" : ajaxArticleId
						},
						dataType : "JSON",
						success : function(result) {
						}
					})

				})

			})
			$(this).children(".AticleReportButton1").each(function() {
				let ajaxArticleId = $(this).prev().prev().val()
				$(this).click(function() {
					console.log(ajaxArticleId)
					$(this).parent(".AticleReport0").html("您已完成檢舉!待管理員查證&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp")
					$.ajax({
						url : "/ReportCommentArticleAjax",
						type : "POST",
						data : {
							"ajaxViolationId" : 2,
							"ajaxArticleId" : ajaxArticleId
						},
						dataType : "JSON",
						success : function(result) {
						}
					})

				})

			})

		})
		//文章檢舉文章檢舉文章檢舉文章檢舉文章檢舉文章檢舉文章檢舉文章檢舉文章檢舉文章檢舉文章檢舉文章檢舉文章檢舉文章檢舉文章檢舉文章檢舉文章檢舉文章檢舉文章檢舉文章檢舉文章檢舉文章檢舉
		//回覆檢舉	回覆檢舉	回覆檢舉	回覆檢舉	回覆檢舉	回覆檢舉	回覆檢舉	回覆檢舉	回覆檢舉	回覆檢舉	回覆檢舉	回覆檢舉	回覆檢舉	回覆檢舉		
		$(".ReplyReport0").each(function() {
			$(this).children(".ReplyReportButton0").each(function() {
				let ajaxReplyId = $(this).prev().val()
				$(this).click(function() {
					console.log(ajaxReplyId)
					$(this).parent(".ReplyReport0").html("您已完成檢舉!待管理員查證")
					$.ajax({
						url : "/ReportCommentReplyAjax",
						type : "POST",
						data : {
							"ajaxReplyViolationId" : 1,
							"ajaxReplyId" : ajaxReplyId
						},
						dataType : "JSON",
						success : function(result) {
						}
					})

				})

			})

		})

		//回覆檢舉	回覆檢舉	回覆檢舉	回覆檢舉	回覆檢舉	回覆檢舉	回覆檢舉	回覆檢舉	回覆檢舉	回覆檢舉	回覆檢舉	回覆檢舉	回覆檢舉	回覆檢舉		
		$(".deleteitem").click(function() {
			return confirm("確定要將此項目刪除嗎?")
		})
		$(".updateitem").click(function() {
			return confirm("確定要將此項目編輯嗎?")
		})

	});