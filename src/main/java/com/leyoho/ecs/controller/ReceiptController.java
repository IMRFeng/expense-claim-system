package com.leyoho.ecs.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.WebUtils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.leyoho.ecs.entity.Receipt;
import com.leyoho.ecs.service.impl.ReceiptServiceImpl;
import com.leyoho.ecs.vo.ReceiptViews;

import imageUtil.Image;
import imageUtil.ImageLoader;

/**
 * This controller is mainly used to capture users actions from the front page requests.
 * 
 * @author Victor Feng (nzvictor.feng@gmail.com)
 * 
 * @version 1.0
 */
@Controller
public class ReceiptController {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	ReceiptServiceImpl receiptService;

	@ModelAttribute("receipt")
	public Receipt getReceiptObject() {
		return new Receipt();
	}
	
	/**
	 * Gets receipt by its id.
	 * 
	 * @param id The receipt id
	 * @return String Json
	 * @throws Exception
	 */
	@RequestMapping(value="/getReceipt/{id}", method=RequestMethod.GET)
	public @ResponseBody String getReceiptById(@PathVariable String id) throws Exception {
		if(id == null || id.equals("")) {
			logger.error("getReceiptById", "id is null or empty");
			return "Error";
		}
		
		ObjectMapper mapper = new ObjectMapper();
		
		Receipt receipt = this.receiptService.getReceipt(id);

		return mapper.writerWithView(ReceiptViews.ReceiptVo.class).writeValueAsString(receipt);
	}
	
	/**
	 * Inserts a new receipt record to database.
	 * <p>
	 * Before insert the data, generate a binary stream for original image and its thumbnail.
	 * </p>
	 * 
	 * @param receipt The Receipt refers to a property of the Model object (the M in MVC).
	 * @param request HttpServletRequest, to get the real path of this project.
	 * @return String return the action after this method executed successfully.
	 * @throws Exception Throw the Exception to caller.
	 */
	@RequestMapping(value="/saveReceipt", method=RequestMethod.POST)
	public @ResponseBody String saveReceipt(@RequestBody String json, HttpServletRequest request) throws Exception {
		
		ObjectMapper mapper = new ObjectMapper();
		Receipt receipt = mapper.readerWithView(ReceiptViews.ReceiptVo.class).forType(Receipt.class).readValue(json);
		
		if (receipt != null) {
			String realPath = request.getSession().getServletContext().getRealPath("/");
			System.out.println("real path is " + realPath);
			System.out.println("1 " + request.getServletPath());
			System.out.println("2 " + request.getServletContext().getContextPath());
			System.out.println("3 " + request.getRequestURI());
			logger.info("realPath is " + realPath + 
					", ServletPath is " + request.getServletPath() +
					", ContextPath is " + request.getServletContext().getContextPath() +
					", RequestURI is " + request.getRequestURI());
			
			if (receipt.getFileName() != null && !receipt.getFileName().equals("")) {
				File file = new File(realPath.replaceAll(request.getServletContext().getContextPath()+"/", "") + receipt.getFileName());
				InputStream inputStream = new FileInputStream(file);
				receipt.setImage(inputStream);// Save original image to database

				String fileName = receipt.getFileName().substring(0, receipt.getFileName().lastIndexOf("."));
				String thumbnailName = realPath.replaceAll(request.getServletContext().getContextPath()+"/", "") + fileName + "_thumbnail.jpg";
				file = new File(thumbnailName);
				inputStream = new FileInputStream(file);
				receipt.setThumbnail(inputStream); // Save thumbnail to database
			}
			
			if(receipt.getReceiptId() == 0) {
				receiptService.insertData(receipt);
			} else {
				this.receiptService.updateData(receipt);
			}
		}

		return mapper.writerWithView(ReceiptViews.ReceiptVo.class).writeValueAsString(receipt);
	}

	/**
	 * Obtains the receipt original image from database and show it on the front page 'View Receipt'.
	 * 
	 * @param response HttpServletResponse, to set response content type to image/jpeg format.
	 * @param receiptID The parameter passed from the request.
	 * @throws IOException Throw the Exception to caller.
	 */
	@RequestMapping(value = "/getReceiptImage/{id}")
	public String getReceiptImage(HttpServletResponse response, @PathVariable("id") String receiptID)
			throws IOException {

		response.setContentType("image/jpeg");
		try {
			InputStream image = this.receiptService.getReceipt(receiptID).getImage();
			IOUtils.copy(image, response.getOutputStream());
			return null;
		} catch (IndexOutOfBoundsException ex) {
			logger.error("getReceiptImage Error", ex.getMessage());
			return "redirect:/fail";
		}
	}

	/**
	 * Obtains the receipt thumbnail from database and show it on the front page 'View Receipt'.
	 * 
	 * @param response HttpServletResponse, to set response content type to image/jpeg format.
	 * @param receiptID The parameter passed from the request.
	 * @throws IOException Throw the Exception to caller.
	 */
	@RequestMapping(value = "/getReceiptThumbnail/{id}")
	public void getReceiptThumbnail(HttpServletResponse response, @PathVariable("id") String receiptID)
			throws IOException {

		response.setContentType("image/jpeg");
		InputStream isThumbnail = this.receiptService.getReceipt(receiptID).getThumbnail();

		IOUtils.copy(isThumbnail, response.getOutputStream());
	}

	/**
	 * Uploads the receipt picture to server with specify path.
	 * 
	 * <p>
	 * To check the 'uploadFiles' folder whether it was created, if not then create it.
	 * </p>
	 * <p>
	 * Set response character encoding and content type and get the PrintWriter
	 * through it. In addition, force convert the HttpServletRequest to
	 * MultipartHttpServletRequest in order to get the upload file that can get
	 * the original file name and its extension.
	 * </p>
	 * <p>
	 * If the uploaded file is empty or the size of it exceed 2M or the uploaded
	 * file format does not match 'jpg', 'jpeg', 'png' and 'gif' then give an
	 * error message to the front. Otherwise, transfer uploaded file to the
	 * specify path at the same time generate its thumbnail and save it to the
	 * same path.
	 * </p>
	 * <p>
	 * If all done and succeeded, then give the transfered image with the path to front page.
	 * </p>
	 * 
	 * @param request HttpServletRequest
	 * @param response The HttpServletResponse, to set character encoding, content type and get print writer.
	 * @return String Return information to invoker.
	 * @throws IOException Throw Exceptions to the caller.
	 */
	@RequestMapping(method = RequestMethod.POST, value = "/uploadReceiptPic")
	public String uploadReceiptPic(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String realPath = request.getSession().getServletContext().getRealPath("/") + "/uploadFiles/";
		File f = new File(realPath);
		if (!f.exists()) {
			f.mkdirs();
		}

		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");

		PrintWriter out = response.getWriter();
		// Force convert the HttpServletRequest to MultipartHttpServletRequest
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		// Get file by name.
		MultipartFile file = multipartRequest.getFile("uploadFile"); 
		// Get the original file name.
		String originalFilename = file.getOriginalFilename(); 

		String extension = originalFilename.substring(originalFilename.lastIndexOf(".") + 1, originalFilename.length());
		// Get the extension and set it to lower case.
		extension = extension.toLowerCase(); 
		// To obtain a new name using the current time in milliseconds for the uploaded image
		String newFileName = String.valueOf(System.currentTimeMillis()) + "." + extension;

		if (file.isEmpty()) {
			out.print("Error Msg: Please choose a file");
			out.flush(); // Flushe the stream PrintWriter
			return null;
		} else if (file.getSize() > 2097152) {
			out.print("Error Msg: The pic is too big, no more than 2M!");
			out.flush(); // Flushe the stream PrintWriter
			return null;
		} else {
			// To determine which kind of images format can be uploaded, if the format not match then give an error to the front caller.
			if (extension.equalsIgnoreCase("jpg") || extension.equalsIgnoreCase("jpeg")
					|| extension.equalsIgnoreCase("gif") || extension.equalsIgnoreCase("png")) {

				File localFile = new File(realPath + newFileName);
				// Transfer the uploaded image to specify path with image name.
				file.transferTo(localFile); 

				String fileName = newFileName.substring(0, newFileName.lastIndexOf("."));

				// This Image class is imported from an third party jar file for more details please refer to the URL:
				// http://www.gotoquiz.com/web-coding/programming/java-programming/create-thumbnails-and-avatars-in-java/
				Image image = ImageLoader.fromFile(realPath + newFileName);
				Image thumbnail = image.getResizedToSquare(50, 0.0);

				String thumbnailName = realPath + fileName + "_thumbnail.jpg";

				// Create a thumbnail for the new uploaded image.
				thumbnail.writeToJPG(new File(thumbnailName), 0.9f);

				out.print(request.getContextPath() + "/uploadFiles/" + newFileName);
				out.flush(); // Flushe the stream PrintWriter
			} else {
				out.print("Error Msg: Wrong pic format, only supports: JPEG, JPG, GIF, PNG.");
				out.flush(); // Flushe the stream PrintWriter
			}
		}

		return null;
	}

	/**
	 * Deletes uploaded receipt image and thumbnail which were stored on the server drive via file name.
	 * 
	 * @param request To get all of parameters passed by it.
	 * @param response The HttpServletResponse which is mainly to give a response for the request.
	 * @return String To send an success/failure message to the request/response.
	 */
	@RequestMapping(value = "/removeReceiptPic", method = RequestMethod.POST)
	public @ResponseBody String removeReceiptPic(HttpServletRequest request, HttpServletResponse response) {

		String fileName = request.getParameter("fileName");
		String realFilePath = request.getSession().getServletContext().getRealPath("/") + fileName;

		String back = "";

		File file = new File(realFilePath);
		// If the file exists and not a directory then delete it (original image)
		if (file.exists() && !file.isDirectory()) {
			file.delete();
			back = "successMessage : " + true;
		} else {
			back = "errorMessage : The file does not exist.";
		}

		fileName = fileName.substring(0, fileName.lastIndexOf("."));
		// generate the thumbnail name
		String thumbnailName = fileName + "_thumbnail.jpg"; 

		file = new File(request.getSession().getServletContext().getRealPath("/") + thumbnailName);
		// If the file exists and not a directory then delete it (thumbnail)
		if (file.exists() && !file.isDirectory()) {
			file.delete();
		}

		try {
			// Constructs a new String by decoding the specified array of bytes using the specified charset
			back = new String(back.getBytes("UTF-8"), "ISO8859_1");
		} catch (UnsupportedEncodingException e) {
			logger.error("removeReceiptPic", e.getMessage());
		}

		return back;
	}

	/**
	 * To set the legitimate expense, which is 'Yes' now, to 'No' by receipt id.
	 * Get the receipt id from the request and using it to retrieve the Receipt
	 * Object, then set claim to 'No' and update it to database.
	 * 
	 * @param request The HttpServletRequest that get the receipt id from it.
	 * @param response The HttpServletResponse
	 * @return String To send an success/failure message to the request/response.
	 */
	@RequestMapping(value = "/setToClaimed", method = RequestMethod.POST)
	public @ResponseBody String setToClaimed(HttpServletRequest request, HttpServletResponse response) {
		String back = "";
		String receiptId = WebUtils.findParameterValue(request, "receiptId");

		try {
			Receipt receipt = receiptService.getReceipt(receiptId);
			receipt.setClaim("No");
			receiptService.updateData(receipt);
			back = "success";
		} catch (Exception ex) {
			logger.error("setToClaimed", ex.getMessage());
			back = "failure " + ex.getMessage();
		}

		return back;
	}

	/**
	 * Deals the fail action.
	 * 
	 * @return ModelAndView Set the page to show failure content.
	 */
	@RequestMapping("/fail")
	public ModelAndView fail() {

		return new ModelAndView("fail");
	}

	/**
	 * Obtains all of the receipt records from database and show them to end users.
	 * 
	 * @return ModelAndView Specify which page will be displayed.
	 */
	@RequestMapping("/getReceiptList")
	public ModelAndView getReceiptList() {
		// get receipt list from receipt service.
		List<Receipt> receiptList = receiptService.getReceiptList();
		
		return new ModelAndView("receiptList", "receiptList", receiptList);
	}

	/**
	 * Deletes a receipt by receipt id and then relocate to the View Receipt
	 * page to show the latest receipt list.
	 * 
	 * @param id The receipt id which is passed from the request.
	 * 
	 * @return String Indicate redirect to which page or do what action after delete the receipt.
	 */
	@RequestMapping("/delete")
	public String deleteReceipt(@RequestParam String id) {

		receiptService.deleteData(id);// deleting receipt by ID

		return "redirect:/getReceiptList";
	}

}