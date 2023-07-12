package uz.bakhromjon.post;

import com.google.protobuf.Empty;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import net.devh.boot.grpc.server.service.GrpcService;
import uz.bakhromjon.grpc.post.*;

import java.util.List;

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
    public void deletePost(PostDeleteRequestGrpc request, StreamObserver<Empty> responseObserver) {
        service.delete(request.getId());

        responseObserver.onNext(Empty.newBuilder().build());
        responseObserver.onCompleted();
    }

    @Override
    public void getPostList(Empty request, StreamObserver<PostResponseListGrpc> responseObserver) {
        List<Post> postList = service.getAll();

        responseObserver.onNext(grpcMapper.toGrpcResponseList(postList));
        responseObserver.onCompleted();
    }


    @Override
    public void updatePost(PostUpdateRequestGrpc request, StreamObserver<PostResponseGrpc> responseObserver) {
        Post post = grpcMapper.toPost(request);
        post = service.update(post);
        responseObserver.onNext(grpcMapper.toGrpcResponse(post));
        responseObserver.onCompleted();
    }
}
