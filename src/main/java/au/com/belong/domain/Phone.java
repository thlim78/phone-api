package au.com.belong.domain;

import au.com.belong.domain.enums.Status;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name="phone")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Phone {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String number;
    @Enumerated(EnumType.STRING)
    private Status status;
    @JsonIgnore
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="customer_id", nullable=false)
    private Customer customer;
    private LocalDateTime dateCreated;
    private LocalDateTime lastUpdated;

    @PrePersist
    private void onCreate() {
        dateCreated = LocalDateTime.now();
    }

    @PreUpdate
    private void onUpdate() {
        lastUpdated = LocalDateTime.now();
    }
}
