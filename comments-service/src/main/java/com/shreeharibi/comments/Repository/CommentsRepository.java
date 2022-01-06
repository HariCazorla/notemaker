package com.shreeharibi.comments.Repository;

import com.shreeharibi.comments.Entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Class representing the Comments Repository
 */
@Repository
public interface CommentsRepository extends JpaRepository<Comment, String> {

    /**
     * Method returns all comments associated with a Note ID
     * @param noteid
     * @return
     */
    @Query("SELECT c FROM Comment c WHERE c.noteid = ?1")
    List<Comment> getCommentsByNoteId(String noteid);
}
