package com.springsecurity.spring_security.respository;

import com.springsecurity.spring_security.models.Post;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends CrudRepository<Post,Long> {

}
