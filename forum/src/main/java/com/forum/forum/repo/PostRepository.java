package com.forum.forum.repo;

import com.forum.forum.models.Post;
import org.springframework.data.repository.CrudRepository;

//Для манипулирования БД
public interface PostRepository extends CrudRepository<Post, Long> {
}
