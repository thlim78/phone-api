package au.com.belong.domain;

import lombok.*;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name="customer")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String firstName;
    private String lastName;
    @OneToMany(mappedBy = "customer")
    private Set<Phone> phones;
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
