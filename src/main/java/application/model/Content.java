package application.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Content")
public class Content {

 @Id
 @Column(name = "uuid")
 private String uuId;

 @Column(name = "user_id")
 private String userId;

 @Column(name = "full_name")
 private String fullName;

 @Column(name = "likes")
 private String likes;

 @Column(name = "transport")
 private String transport;

 @Column(name = "avg_speed")
 private double avgSpeed;

 @Column(name = "top_speed")
 private double topSpeed;

}
