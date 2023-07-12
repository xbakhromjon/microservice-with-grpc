package uz.bakhromjon.post;

import com.google.protobuf.Empty;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.bakhromjon.grpc.post.*;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostRemoteGrpcService {
    private final PostGrpcMapper grpcMapper;

    public Post getOne(Long postId) {
        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 9000)
                .usePlaintext()
                .build();

        PostServiceGrpc.PostServiceBlockingStub postServiceBlockingStub = PostServiceGrpc.newBlockingStub(channel);
        PostResponseGrpc grpcResponse = postServiceBlockingStub.getPost(PostGetRequestGrpc.newBuilder().setId(postId).build());

        channel.shutdown();
        return grpcMapper.toApplicationResponse(grpcResponse);
    }

    public List<Post> getList() {
        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 9000)
                .usePlaintext()
                .build();

        PostServiceGrpc.PostServiceBlockingStub postServiceBlockingStub = PostServiceGrpc.newBlockingStub(channel);
        PostResponseListGrpc grpcResponseList = postServiceBlockingStub.getPostList(Empty.newBuilder().build());

        channel.shutdown();
        return grpcMapper.toApplicationResponse(grpcResponseList);
    }

    public void delete(Long postId) {
        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 9000)
                .usePlaintext()
                .build();
        PostServiceGrpc.PostServiceBlockingStub postServiceBlockingStub = PostServiceGrpc.newBlockingStub(channel);
        postServiceBlockingStub.deletePost(PostDeleteRequestGrpc.newBuilder().setId(postId).build());
        channel.shutdown();
    }

    public Post update(Post post) {
        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 9000)
                .usePlaintext()
                .build();
        PostServiceGrpc.PostServiceBlockingStub postServiceBlockingStub = PostServiceGrpc.newBlockingStub(channel);
        PostResponseGrpc grpcResponse = postServiceBlockingStub.updatePost(grpcMapper.toPostUpdateRequestGrpc(post));
        channel.shutdown();
        return grpcMapper.toApplicationResponse(grpcResponse);
    }
}
