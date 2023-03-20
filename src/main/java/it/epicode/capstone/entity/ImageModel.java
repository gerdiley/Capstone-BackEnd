package it.epicode.capstone.entity;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "image_model")
public class ImageModel {

	public ImageModel(String originalFilename, String contentType, byte[] bytes) {
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public int id;
	private String name; 
	private String type;
	
	@Column(length = 500000000)
	private byte[] picbyte;
	
	
}
