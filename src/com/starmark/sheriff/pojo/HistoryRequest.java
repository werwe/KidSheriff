package com.starmark.sheriff.pojo;

import lombok.Data;

@Data
public class HistoryRequest {
	String userId;
	int limit = 0;
}
