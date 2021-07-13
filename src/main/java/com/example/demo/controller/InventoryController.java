package com.example.demo.controller;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.model.bean.mall.Product;
import com.example.demo.model.bean.mall.ProductPicture;
import com.example.demo.model.service.mall.InventoryService;
import com.example.demo.model.service.mall.ProductPictureService;

/**
 * 庫存controller
 * 
 * @author mirandachang
 *
 */
@Controller
public class InventoryController {

	@Autowired
	private InventoryService service;
	
	@Autowired
	private ProductPictureService productPictureService;

	@RequestMapping(value = "/onstockform")
	public String onstockForm() {
		return "form/onStockForm";
	}

	@RequestMapping(value = "/inventorytable")
	public String inventoryTable() {
		return "table/inventorytable";
	}
	
	@RequestMapping(value = "/inventoryPolicy")
	public String checkinventoryPolicy() {
		return "info/inventoryPolicy";
	}

	/**
	 * 新增商品庫存
	 * 
	 * @param product    商品資訊(不含圖片)
	 * @param fileupload (商品圖片)
	 * @param session    取得系統路徑
	 * @return 轉跳到庫存總覽
	 * @throws IllegalStateException
	 * @throws IOException
	 */
	@PostMapping(value = "/productOnStock")
	public String productOnStock(Product product, MultipartFile fileupload[]) throws Exception {
		String property = System.getProperty("line.separator");
		product.setBriefDescription(product.getBriefDescription().replaceAll(property,"<br>"));
		product.setSpec(product.getSpec().replaceAll(property,"<br>"));
		product.setProductDescription(product.getProductDescription().replaceAll(property,"<br>"));
		service.productOnStock(product);
		// 使用者有上傳圖片
		if (fileupload.length != 0) {
			for(int i = 0 ; i <fileupload.length ; i++) {
			String fileName = Math.round(Math.random()*10000)+fileupload[i].getOriginalFilename();
			// 將圖片及相對路徑保存至資料庫
			ProductPicture productPicture = new ProductPicture();
			productPicture.setProductPath(File.separator +"temp"+File.separator + fileName);
			productPicture.setProductPhoto(fileupload[i].getBytes());
			productPicture.setProduct(product);
			productPictureService.productPicOnstock(productPicture);
//			productPicture.setProductPath(File.separator +"housecallimage"+File.separator + fileName);
			// 同時下載到本機存放
			// 1.指定資料夾路徑
			String path = "D:\\Programming\\temp" + File.separator + fileName;
			System.out.println(path);
			// 2.確定該系統路徑下是否有指定的資料夾
			File file = new File("D:\\Programming\\temp");
			if (!(file.exists() && file.isDirectory())) {
				// 沒有,則建立資料夾
				file.mkdir();
			}
			// 有則直接寫入
			File file1 = new File(path);
			fileupload[i].transferTo(file1);
			}
		}
		return "redirect:/inventorytable";
	}

	/**
	 * 庫存總覽(含ajax分頁)
	 * 
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException @RequestParam(defaultValue="1")
	 */
	@PostMapping(value = "/inventorypage")
	@ResponseBody
	public Page<Product> stockList(Integer pageNo) {
		Page<Product> productlist = service.pageForInventory(pageNo, 10);
		return productlist;
	}

	/**
	 * 刪除庫存商品
	 * 
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */

	@DeleteMapping(value = "/deleteProduct")
	@ResponseBody
	public String deleteProduct(Integer productId) {
		try {
			service.productRemovedFromStock(productId);
			return "true";
		} catch (Exception e) {
			throw new RuntimeException();
		}
	}

	/**
	 * 
	 * 取得單一商品資訊
	 * 
	 * @param id
	 * @param map
	 * @return
	 */
	@GetMapping(value = "/getProductInfo/{productId}")
	public String getProductInfo(@PathVariable("productId") String id, Map<String, Object> map) {
		Product product = service.queryProductByID(Integer.valueOf(id));
		map.put("command", product);
		return "form/editProductForm";
	}

	
	/**
	 * 更新庫存商品資訊
	 * @param name
	 * @param value
	 * @param productId
	 * @param fileupload
	 * @return
	 */
	@PostMapping(value = "/updateProduct")
	@ResponseBody
	public Product updateProduct(@RequestParam(value = "name") String name,
			@RequestParam(value = "value", required = false) Object value,
			@RequestParam(value = "name1",required = false) String name1,
			@RequestParam(value = "value1", required = false) Object value1,
			@RequestParam(value = "productId") Integer productId){
		try {
		Map<String, Object> updateitems = new HashMap<String, Object>();
		
			// 使用者更新其他欄位
			if(name.equals("productPrice")||name.equals("productStock")) {
				updateitems.put(name,Integer.parseInt((String) value));
			}else if(name.equals("productDescription")||name.equals("briefDescription")||name.equals("spec")){
				String property = System.getProperty("line.separator");
				updateitems.put(name,((String) value).replaceAll(property,"<br>"));
			}else {
				updateitems.put(name, value);
			}
			
			if("subType".equals(name1) && value1.equals("請選擇商品分類")) {
				value1=null;
				updateitems.put(name1, value1);
			}else if(name1!=null){
				updateitems.put(name1, value1);
			}
		service.updateProductInfo(productId, updateitems);
		return service.queryProductByID(productId);
		}catch(Exception e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
	}
	
	
	 /**
	  * 更新圖片
	  * @param productId
	  * @param fileupload
	  * @return
	  * @throws Exception
	  */
	 @PostMapping(value = "/updateProductPic")
	 @ResponseBody
	 public List<ProductPicture> updateProductPic(@RequestParam(value = "productId") Integer productId,
	   @RequestParam(value = "fileupload", required = false) MultipartFile[] fileupload) throws Exception {
	  Product product = service.queryProductByID(productId);
	  // 使用者更新圖片欄位
	  if (fileupload.length != 0) {
	   for(int i = 0 ; i <fileupload.length ; i++) {
	   String fileName = Math.round(Math.random()*10000)+fileupload[i].getOriginalFilename();
	   ProductPicture productPicture = new ProductPicture();
	   productPicture.setProductPath(File.separator +"temp"+File.separator + fileName);
	   productPicture.setProductPhoto(fileupload[i].getBytes());
	   productPicture.setProduct(product);
	   productPictureService.productPicOnstock(productPicture);
	   
	   // 1.指定資料夾路徑
	   String path = "D:\\Programming\\temp" + File.separator + fileName;
	   System.out.println(path);
	   // 2.確定該系統路徑下是否有指定的資料夾
	   File file = new File("D:\\Programming\\temp");
	   if (!(file.exists() && file.isDirectory())) {
	   // 沒有,則建立資料夾
	   file.mkdir();
	   }
	   // 有則直接寫入
	   File file1 = new File(path);
	   fileupload[i].transferTo(file1);
	   }
	  }
	   return productPictureService.productPicList(product);
	 }
	
	 
	/**
	 * 刪除一張商品圖片
	 * @param picphotoId
	 * @return
	 */
	@GetMapping(value = "/deletepic/{picphotoId}")
	@ResponseBody
	public String deleteProductPic(@PathVariable("picphotoId")Integer picphotoId) {
		productPictureService.deletepic(picphotoId);
		return "true";
	}

}
