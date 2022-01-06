package com.shreeharibi.notes.Repository;

import com.shreeharibi.notes.Entity.Note;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Class to Model Note JPA Repository
 */
@Repository
public interface NoteRepository extends JpaRepository<Note, String> {
}
