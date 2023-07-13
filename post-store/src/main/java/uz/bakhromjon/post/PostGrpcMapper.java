package uz.bakhromjon.post;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import uz.bakhromjon.grpc.post.PostPageableResponseGrpc;
import uz.bakhromjon.grpc.post.PostResponseGrpc;
import uz.bakhromjon.grpc.post.PostUpdateRequestGrpc;

import java.util.List;

@Component
public class PostGrpcMapper {

    public PostResponseGrpc toGrpcResponse(Post source) {
        if (source == null) return null;

        PostResponseGrpc.Builder builder = PostResponseGrpc.newBuilder();
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

    public List<PostResponseGrpc> toGrpcResponse(List<Post> sourceList) {
        return sourceList.stream().map(this::toGrpcResponse).toList();
    }


    public Post toPost(PostUpdateRequestGrpc source) {
        return new Post(source.getId(),
                source.getUserId(),
                source.getTitle(),
                source.getBody());
    }

    public PostPageableResponseGrpc toPageableGrpcResponse(Page<Post> source) {
        PostPageableResponseGrpc.Builder builder = PostPageableResponseGrpc.newBuilder();
        builder.addAllContent(toGrpcResponse(source.getContent()));
        builder.setTotalElements(source.getTotalElements());
        builder.setTotalPages(source.getTotalPages());
        builder.setSize(source.getSize());
        return builder.build();
    }

}
