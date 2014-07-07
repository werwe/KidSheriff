package com.starmark.sheriff.pojo;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;
import lombok.NoArgsConstructor;
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class LinkRequestData
{
	private String email;
	private String pushid;
	private List<String> parents;
}