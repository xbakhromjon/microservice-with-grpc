package uz.bakhromjon.post;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.bakhromjon.common.PageResponse;

@RestController
@RequestMapping("/posts")
@RequiredArgsConstructor
public class PostController {
    private final PostRemoteGrpcService remoteGrpcService;

    @GetMapping("/{id}")
    public ResponseEntity<PostRemoteResponse> getOne(@PathVariable Long id) {
        PostRemoteResponse response = remoteGrpcService.getOne(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<PageResponse<PostRemoteResponse>> getList(@RequestParam(defaultValue = "0") Integer page,
                                                                    @RequestParam(defaultValue = "10") Integer size) {
        PageResponse<PostRemoteResponse> postList = remoteGrpcService.getList(page, size);
        return ResponseEntity.ok(postList);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> delete(@PathVariable Long id) {
        return ResponseEntity.ok(remoteGrpcService.delete(id));
    }

    @PutMapping
    public ResponseEntity<Boolean> update(@RequestBody PostRemoteResponse post) {
        return ResponseEntity.ok(remoteGrpcService.update(post));
    }
}
