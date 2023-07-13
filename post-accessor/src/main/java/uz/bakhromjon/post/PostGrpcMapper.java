package uz.bakhromjon.post;

import org.springframework.stereotype.Component;
import uz.bakhromjon.common.PageResponse;
import uz.bakhromjon.grpc.post.PostPageableResponseGrpc;
import uz.bakhromjon.grpc.post.PostResponseGrpc;
import uz.bakhromjon.grpc.post.PostUpdateRequestGrpc;

import java.util.List;

@Component
public class PostGrpcMapper {

    public Post toApplicationResponse(PostResponseGrpc source) {
        return new Post(source.getId(), source.getUserId(),
                source.getTitle(), source.getBody());
    }

    public List<Post> toApplicationResponse(List<PostResponseGrpc> sourceList) {
        return sourceList.stream().map(source -> new Post(source.getId(),
                source.getUserId(), source.getTitle(), source.getBody())).toList();
    }

    public PostUpdateRequestGrpc toPostUpdateRequestGrpc(Post source) {
        PostUpdateRequestGrpc.Builder builder = PostUpdateRequestGrpc.newBuilder();
        if (source.getId() != null) {
            builder.setId(source.getId());
        }
        if (source.getUserId() != null) {
            builder.setUserId(source.getUserId());
        }
        if (source.getTitle() != null) {
            builder.setTitle(source.getTitle());
        }
        if (source.getBody() != null) {
            builder.setBody(source.getBody());
        }
        return builder.build();
    }

    public PageResponse<Post> toApplicationPageResponse(PostPageableResponseGrpc source) {
        return new PageResponse<>(toApplicationResponse(source.getContentList()),
                source.getTotalElements(), source.getTotalPages(), source.getSize());
    }
}
