package uz.bakhromjon.post;

import com.google.protobuf.BoolValue;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import net.devh.boot.grpc.server.service.GrpcService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import uz.bakhromjon.grpc.post.*;

@GrpcService
@RequiredArgsConstructor
public class PostServiceGrpcImpl extends PostServiceGrpc.PostServiceImplBase {
    private final PostService service;
    private final PostGrpcMapper grpcMapper;


    @Override
    public void getPost(PostGetRequestGrpc request, StreamObserver<PostResponseGrpc> responseObserver) {
        Post post = service.getOne(request.getId());
        responseObserver.onNext(grpcMapper.toGrpcResponse(post));
        responseObserver.onCompleted();
    }

    @Override
    public void deletePost(PostDeleteRequestGrpc request, StreamObserver<BoolValue> responseObserver) {
        boolean isDeleted = service.delete(request.getId());

        responseObserver.onNext(BoolValue.of(isDeleted));
        responseObserver.onCompleted();
    }

    @Override
    public void getPostList(GetPostsPageableRequestGrpc request, StreamObserver<PostPageableResponseGrpc> responseObserver) {
        Page<Post> postPage = service.getAll(PageRequest.of(request.getPage(), request.getSize()));

        responseObserver.onNext(grpcMapper.toPageableGrpcResponse(postPage));
        responseObserver.onCompleted();
    }


    @Override
    public void updatePost(PostUpdateRequestGrpc request, StreamObserver<BoolValue> responseObserver) {
        Post post = grpcMapper.toPost(request);
        boolean isUpdated = service.update(post);
        responseObserver.onNext(BoolValue.of(isUpdated));
        responseObserver.onCompleted();
    }
}
