package io.github.lccezinha.mytravel.domain;

import java.util.Date;

public class Spent {
	private Long id;
	private Date date;
	private String category;
	private String description;
	private Double value;
	private String place;
	private Integer travelId;
	
	public Spent(){}
	
	public Spent(Long id, Date date, String category, String description,
			Double value, String place, Integer travelId) {
		super();
		this.id = id;
		this.date = date;
		this.category = category;
		this.description = description;
		this.value = value;
		this.setPlace(place);
		this.travelId = travelId;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Double getValue() {
		return value;
	}

	public void setValue(Double value) {
		this.value = value;
	}

	public Integer getTravelId() {
		return travelId;
	}

	public void setTravelId(Integer travelId) {
		this.travelId = travelId;
	}

	public String getPlace() {
		return place;
	}

	public void setPlace(String place) {
		this.place = place;
	}
	
}
