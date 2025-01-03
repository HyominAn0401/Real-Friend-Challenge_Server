package site.examready2025.quiz.domain.quiz.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import site.examready2025.quiz.domain.response.entity.Response;
import site.examready2025.quiz.domain.user.entity.User;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "quiz")
public class Quiz {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "creator_user_id", nullable = false)
    private User creator;

    @Column(nullable = false, length = 50)
    private String title;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Setter
    @Column(nullable = false, unique = true, length = 36)
    private String shareKey;

    @OneToMany(mappedBy = "quiz")
    private List<Response> responses = new ArrayList<>();

    @Builder
    public Quiz(User creator, String title, LocalDateTime createdAt, String shareKey){
        this.creator = creator;
        this.title = title;
        this.createdAt = createdAt;
        this.shareKey = shareKey;
    }

    @PrePersist
    private void initializeShareKey() {
        if (this.shareKey == null) {
            this.shareKey = UUID.randomUUID().toString();
        }
    }
}