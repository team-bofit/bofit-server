package org.sopt.bofit.domain.comment.repository;

import org.sopt.bofit.domain.comment.entity.CommentImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentImageRepository extends JpaRepository<CommentImage, Long> {

}
