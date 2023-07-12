package uz.bakhromjon.post;

import org.springframework.stereotype.Component;
import uz.bakhromjon.grpc.post.PostResponseGrpc;
import uz.bakhromjon.grpc.post.PostResponseListGrpc;
import uz.bakhromjon.grpc.post.PostUpdateRequestGrpc;

import java.util.List;

@Component
public class PostGrpcMapper {

    public Post toApplicationResponse(PostResponseGrpc grpcResponse) {
        return new Post(grpcResponse.getId(), grpcResponse.getUserId(), grpcResponse.getTitle(), grpcResponse.getBody());
    }

    public List<Post> toApplicationResponse(PostResponseListGrpc grpcResponseList) {
        return grpcResponseList.getPostsList().stream().map(grpcResponse -> toApplicationResponse(grpcResponse)).toList();
    }

    public PostUpdateRequestGrpc toPostUpdateRequestGrpc(Post post) {
        PostUpdateRequestGrpc.Builder builder = PostUpdateRequestGrpc.newBuilder();
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
}
