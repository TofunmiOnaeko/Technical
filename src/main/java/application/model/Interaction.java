package application.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Interaction")
public class Interaction {

    @Id
    @Column(name = "request_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int requestId;
    @Column(name = "request_uri")
    private String requestUri;
    @Column(name = "request_time_stamp")
    private LocalDateTime requestTimeStamp;
    @Column(name = "http_response_code")
    private int httpResponseCode;
    @Column(name = "country_code")
    private String requestCountryCode;
    @Column(name = "time_lapsed")
    private double timeLapsed;

}
