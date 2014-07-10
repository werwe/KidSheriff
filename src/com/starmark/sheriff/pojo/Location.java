package com.starmark.sheriff.pojo;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class Location {
	private String date;
	private double lat;
	private double lng;
}