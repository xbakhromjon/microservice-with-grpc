package uz.bakhromjon.post;

import org.springframework.stereotype.Component;
import uz.bakhromjon.common.PageResponse;
import uz.bakhromjon.grpc.post.PostPageableResponseGrpc;
import uz.bakhromjon.grpc.post.PostResponseGrpc;
import uz.bakhromjon.grpc.post.PostUpdateRequestGrpc;

import java.util.List;

@Component
public class PostGrpcMapper {

    public PostRemoteResponse toApplicationResponse(PostResponseGrpc grpcResponse) {
        return new PostRemoteResponse(grpcResponse.getId(), grpcResponse.getUserId(),
                grpcResponse.getTitle(), grpcResponse.getBody());
    }

    public List<PostRemoteResponse> toApplicationResponse(List<PostResponseGrpc> sourceList) {
        return sourceList.stream().map(source -> new PostRemoteResponse(source.getId(),
                source.getUserId(), source.getTitle(), source.getBody())).toList();
    }

    public PostUpdateRequestGrpc toPostUpdateRequestGrpc(PostRemoteResponse post) {
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

    public PageResponse<PostRemoteResponse> toApplicationPageResponse(PostPageableResponseGrpc grpcResponse) {
        return new PageResponse<>(toApplicationResponse(grpcResponse.getContentList()),
                grpcResponse.getTotalElements(), grpcResponse.getTotalPages(), grpcResponse.getSize());
    }
}
