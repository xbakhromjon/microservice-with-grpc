package uz.bakhromjon.post;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import uz.bakhromjon.grpc.post.PostPageableResponseGrpc;
import uz.bakhromjon.grpc.post.PostResponseGrpc;
import uz.bakhromjon.grpc.post.PostUpdateRequestGrpc;

import java.util.List;

@Component
public class PostGrpcMapper {

    public PostResponseGrpc toGrpcResponse(Post post) {
        if (post == null) return null;

        PostResponseGrpc.Builder builder = PostResponseGrpc.newBuilder();
        if (post.getId() != null) {
            builder.setId(post.getId());
        }
        if (post.getUserId() != null) {
            builder.setUserId(post.getUserId());
        }
        if (post.getTitle() != null) {
            builder.setTitle(post.getTitle());
        }
        if (post.getBody() != null) {
            builder.setBody(post.getBody());
        }

        return builder.build();
    }

    public List<PostResponseGrpc> toGrpcResponse(List<Post> postList) {
        return postList.stream().map(this::toGrpcResponse).toList();
    }


    public Post toPost(PostUpdateRequestGrpc updateRequest) {
        return new Post(updateRequest.getId(),
                updateRequest.getUserId(),
                updateRequest.getTitle(),
                updateRequest.getBody());
    }

    public PostPageableResponseGrpc toPageableGrpcResponse(Page<Post> postPage) {
        PostPageableResponseGrpc.Builder builder = PostPageableResponseGrpc.newBuilder();
        builder.addAllContent(toGrpcResponse(postPage.getContent()));
        builder.setTotalElements(postPage.getTotalElements());
        builder.setTotalPages(postPage.getTotalPages());
        builder.setSize(postPage.getSize());
        return builder.build();
    }

}
