package uz.bakhromjon.post;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

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

    public boolean delete(Long postId) {
        try {
            repository.deleteById(postId);
            return true;
        } catch (EmptyResultDataAccessException ignored) {
            return false;
        }
    }

    public Page<Post> getAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    public Post getOne(Long id) {
        Optional<Post> postOptional = repository.findById(id);
        return postOptional.orElse(null);
    }

    public boolean update(Post post) {
        if (!repository.existsById(post.getId())) {
            return false;
        }
        repository.save(post);
        return true;
    }
}
