package it.epicode.capstone.entity;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@JsonIdentityInfo(
	    generator = ObjectIdGenerators.PropertyGenerator.class, 
	    property = "id")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "ads")
public class Ad {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	private String title;
	private String description;
	@Enumerated(EnumType.STRING)
	private Category category;
	private int likes;
	private LocalDate expirationDate;
	private String img;
	@ManyToOne
	
	@JoinColumn(name="user_id", referencedColumnName = "id")
	private User user;
	
//	@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL )
//	@JoinTable(name = "ad_images", 
//		joinColumns = { @JoinColumn(name= "ad_id") },
//		inverseJoinColumns = { @JoinColumn(name = "image_id")}
//	)
//	private Set<ImageModel> productImages;
	
	@ManyToMany (fetch = FetchType.LAZY,cascade = CascadeType.MERGE)
	@OnDelete(action = OnDeleteAction.CASCADE)
	@JoinTable(
			name = "ads_likes",
			joinColumns = @JoinColumn(name="ad_id"),
			inverseJoinColumns = @JoinColumn(name="profile_id")
			)
	private List<Profile> likesList;
	
	
}
