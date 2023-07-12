package uz.bakhromjon.post;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/posts")
@RequiredArgsConstructor
public class PostController {
    private final PostRemoteGrpcService remoteGrpcService;

    @GetMapping("/{id}")
    public ResponseEntity<Post> getOne(@PathVariable Long id) {
        Post response = remoteGrpcService.getOne(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<Post>> getList() {
        List<Post> postList = remoteGrpcService.getList();
        return ResponseEntity.ok(postList);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        remoteGrpcService.delete(id);
        return ResponseEntity.ok(null);
    }

    @PutMapping
    public ResponseEntity<Post> update(@RequestBody Post post) {
        Post response = remoteGrpcService.update(post);
        return ResponseEntity.ok(response);
    }
}
