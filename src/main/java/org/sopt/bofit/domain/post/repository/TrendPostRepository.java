package org.sopt.bofit.domain.post.repository;


import java.util.List;
import org.sopt.bofit.domain.post.entity.Post;
import org.sopt.bofit.domain.post.entity.TrendPost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface TrendPostRepository extends JpaRepository <TrendPost, Long> {

    @Query("""
      select tp.post
      from TrendPost tp
      where tp in :trendPosts
      order by tp.ranking asc
    """)
    List<Post> findAllPostsByTrendPosts(@Param("trendPosts") List<TrendPost> trendPosts);
}
