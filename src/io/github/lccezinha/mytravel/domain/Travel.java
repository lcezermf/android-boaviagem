package io.github.lccezinha.mytravel.domain;

import java.util.Date;

public class Travel {
	private Long id;
	private String destiny;
	private Integer travelKind;
	private Date dateFinish;
	private Date dateStart;
	private Double budget;
	private Integer peopleQuantity;
	
	public Travel(){}

	public Travel(Long id, String destiny, Integer travelKind, Date dateFinish,
			Date dateStart, Double budget, Integer peopleQuantity) {
		super();
		this.id = id;
		this.destiny = destiny;
		this.travelKind = travelKind;
		this.dateFinish = dateFinish;
		this.dateStart = dateStart;
		this.budget = budget;
		this.peopleQuantity = peopleQuantity;
	}



	public Long getId(){
		return id;
	}
	
	public void setId(Long id){
		this.id = id;
	}

	public String getDestiny() {
		return destiny;
	}

	public void setDestiny(String destiny) {
		this.destiny = destiny;
	}

	public Integer getTravelKind() {
		return travelKind;
	}

	public void setTravelKind(Integer travelKind) {
		this.travelKind = travelKind;
	}

	public Date getDateFinish() {
		return dateFinish;
	}

	public void setDateFinish(Date dateFinish) {
		this.dateFinish = dateFinish;
	}

	public Date getDateStart() {
		return dateStart;
	}

	public void setDateStart(Date dateStart) {
		this.dateStart = dateStart;
	}

	public Double getBudget() {
		return budget;
	}

	public void setBudget(Double budget) {
		this.budget = budget;
	}

	public Integer getPeopleQuantity() {
		return peopleQuantity;
	}

	public void setPeopleQuantity(Integer peopleQuantity) {
		this.peopleQuantity = peopleQuantity;
	}
	
	
	
}
