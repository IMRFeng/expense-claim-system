package com.leyoho.ecs.entity;

import java.io.InputStream;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonView;
import com.leyoho.ecs.vo.ReceiptViews;

@Entity
public class Receipt {
	
	@Id
	@GeneratedValue
	@Column(name="id")
	@JsonView(ReceiptViews.ReceiptVo.class)
	private int receiptId;
	@Column(name="date_stamp")
	@JsonView(ReceiptViews.ReceiptVo.class)
	private String date;
	@JsonView(ReceiptViews.ReceiptVo.class)
	private String description;
	@JsonView(ReceiptViews.ReceiptVo.class)
	private String category;
	@JsonView(ReceiptViews.ReceiptVo.class)
	private double cost; // NZD
	@JsonView(ReceiptViews.ReceiptVo.class)
	private String claim;
	@Column(name="dollar_type")
	@JsonView(ReceiptViews.ReceiptVo.class)
	private String dollarType;
	@Column(name="file_name")
	@JsonView(ReceiptViews.ReceiptVo.class)
	private String fileName;
	@Column(name="pre_cost")
	@JsonView(ReceiptViews.ReceiptVo.class)
	private double preCost; // Original dollar cost e.g. USD or AUD
	@JsonView(ReceiptViews.ReceiptVo.class)
	private double rates;
	@JsonView(ReceiptViews.ReceiptNotShown.class)
	private InputStream image;
	@JsonView(ReceiptViews.ReceiptNotShown.class)
	private InputStream thumbnail;
	
	public InputStream getImage() {
		return image;
	}

	public void setImage(InputStream image) {
		this.image = image;
	}

	public InputStream getThumbnail() {
		return thumbnail;
	}

	public void setThumbnail(InputStream thumbnail) {
		this.thumbnail = thumbnail;
	}

	public double getRates() {
		return rates;
	}

	public void setRates(double rates) {
		this.rates = rates;
	}

	public double getPreCost() {
		return preCost;
	}

	public void setPreCost(double preCost) {
		this.preCost = preCost;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getDollarType() {
		return dollarType;
	}

	public void setDollarType(String dollarType) {
		this.dollarType = dollarType;
	}

	public int getReceiptId() {
		return receiptId;
	}

	public void setReceiptId(int receiptId) {
		this.receiptId = receiptId;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public double getCost() {
		return cost;
	}

	public void setCost(double cost) {
		this.cost = cost;
	}

	public String getClaim() {
		return claim;
	}

	public void setClaim(String claim) {
		this.claim = claim;
	}
}
