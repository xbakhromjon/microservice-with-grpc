package uz.bakhromjon.post;

import org.springframework.stereotype.Component;
import uz.bakhromjon.grpc.post.PostResponseGrpc;
import uz.bakhromjon.grpc.post.PostResponseListGrpc;
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

    public PostResponseListGrpc toGrpcResponseList(List<Post> postList) {
        PostResponseListGrpc.Builder builder = PostResponseListGrpc.newBuilder();
        for (Post post : postList) {
            PostResponseGrpc grpcResponse = toGrpcResponse(post);
            builder.addPosts(grpcResponse);
        }
        return builder.build();
    }

    public Post toPost(PostUpdateRequestGrpc updateRequest) {
        return new Post(updateRequest.getId(),
                updateRequest.getUserId(),
                updateRequest.getTitle(),
                updateRequest.getBody());
    }

}
