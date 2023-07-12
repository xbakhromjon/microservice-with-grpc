package uz.bakhromjon.post;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository repository;
    private final PostLoaderService loaderService;


    public void loadAndSave() throws JsonProcessingException {
        PostLoaderService.PostRemoteResponse remoteResponse = loaderService.loadPosts();
        repository.saveAll(remoteResponse.getData());
        int i = 1;
        while (i < 2) {
            remoteResponse = loaderService.loadPosts(remoteResponse.getMeta().getPagination().getLinks().getNext());
            repository.saveAll(remoteResponse.getData());
            i++;
        }
    }

    public void delete(Long postId) {
        repository.deleteById(postId);
    }

    public List<Post> getAll() {
        return repository.findAll();
    }

    public Post getOne(Long id) {
        Optional<Post> postOptional = repository.findById(id);
        return postOptional.orElse(null);
    }

    public Post update(Post post) {
        return repository.save(post);
    }
}
