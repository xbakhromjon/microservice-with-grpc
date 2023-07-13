package uz.bakhromjon.post;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "Post")
@Table(name = "post")
public class Post {
    @Id
    private Long id;
    @JsonProperty(value = "user_id")
    private Long userId;

    private String title;

    @Column(columnDefinition = "text")
    private String body;
}
