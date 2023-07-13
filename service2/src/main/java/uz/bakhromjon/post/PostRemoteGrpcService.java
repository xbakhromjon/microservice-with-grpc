package uz.bakhromjon.post;

import com.google.protobuf.BoolValue;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.bakhromjon.common.PageResponse;
import uz.bakhromjon.grpc.post.*;

@Service
@RequiredArgsConstructor
public class PostRemoteGrpcService {
    private final PostGrpcMapper grpcMapper;

    private final String remoteHost = "localhost";
    private final Integer remoteGrpcPort = 9000;

    public PostRemoteResponse getOne(Long postId) {
        ManagedChannel channel = ManagedChannelBuilder.forAddress(remoteHost, remoteGrpcPort)
                .usePlaintext()
                .build();

        PostServiceGrpc.PostServiceBlockingStub postServiceBlockingStub = PostServiceGrpc.newBlockingStub(channel);
        PostResponseGrpc grpcResponse = postServiceBlockingStub.getPost(PostGetRequestGrpc.newBuilder().setId(postId).build());
        channel.shutdown();

        return grpcMapper.toApplicationResponse(grpcResponse);
    }

    public PageResponse<PostRemoteResponse> getList(Integer page, Integer size) {
        ManagedChannel channel = ManagedChannelBuilder.forAddress(remoteHost, remoteGrpcPort)
                .usePlaintext()
                .build();

        PostServiceGrpc.PostServiceBlockingStub postServiceBlockingStub = PostServiceGrpc.newBlockingStub(channel);
        PostPageableResponseGrpc grpcResponseList = postServiceBlockingStub.getPostList(GetPostsPageableRequestGrpc.newBuilder()
                .setPage(page)
                .setSize(size)
                .build());
        channel.shutdown();

        return grpcMapper.toApplicationPageResponse(grpcResponseList);
    }

    public boolean delete(Long postId) {
        ManagedChannel channel = ManagedChannelBuilder.forAddress(remoteHost, remoteGrpcPort)
                .usePlaintext()
                .build();
        PostServiceGrpc.PostServiceBlockingStub postServiceBlockingStub = PostServiceGrpc.newBlockingStub(channel);
        BoolValue isDeleted = postServiceBlockingStub.deletePost(PostDeleteRequestGrpc.newBuilder().setId(postId).build());
        channel.shutdown();

        return isDeleted.getValue();
    }

    public boolean update(PostRemoteResponse post) {
        ManagedChannel channel = ManagedChannelBuilder.forAddress(remoteHost, remoteGrpcPort)
                .usePlaintext()
                .build();
        PostServiceGrpc.PostServiceBlockingStub postServiceBlockingStub = PostServiceGrpc.newBlockingStub(channel);
        BoolValue isUpdated = postServiceBlockingStub.updatePost(grpcMapper.toPostUpdateRequestGrpc(post));
        channel.shutdown();

        return isUpdated.getValue();
    }
}
