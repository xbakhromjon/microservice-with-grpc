package uz.bakhromjon.post;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class PostLoaderService {
    private final RestTemplate restTemplate = new RestTemplate();

    public PostRemoteResponse loadPosts() throws JsonProcessingException {
        ResponseEntity<String> response = restTemplate.getForEntity("https://gorest.co.in/public/v1/posts", String.class);
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(response.getBody(), PostRemoteResponse.class);
    }

    public PostRemoteResponse loadPosts(String url) throws JsonProcessingException {
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(response.getBody(), PostRemoteResponse.class);
    }


    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    static class PostRemoteResponse {
        private Meta meta;
        private List<Post> data;

        @Getter
        @Setter
        @AllArgsConstructor
        @NoArgsConstructor
        static class Meta {
            private Pagination pagination;
        }

        @Getter
        @Setter
        @AllArgsConstructor
        @NoArgsConstructor
        static class Pagination {
            private Long total;
            private Long pages;
            private Long page;
            private Long limit;
            private Links links;
        }

        @Getter
        @Setter
        @AllArgsConstructor
        @NoArgsConstructor
        static class Links {
            private String previous;
            private String current;
            private String next;
        }
    }
}
