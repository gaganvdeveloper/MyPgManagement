package com.gsoft.MyPgManagementApplication.dto;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Pg {
	@Id
	private int id;
	private String name;
	private String city;
	private String state;
	private long phone;
	private String email;
}
