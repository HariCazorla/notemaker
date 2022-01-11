package com.shreeharibi.aggregator.Service;

import com.shreeharibi.aggregator.Model.Comment;
import com.shreeharibi.aggregator.Model.Note;
import com.shreeharibi.notemaker.comments.AddNewCommentRequest;
import com.shreeharibi.notemaker.comments.AddNewCommentResponse;
import com.shreeharibi.notemaker.comments.CommentDto;
import com.shreeharibi.notemaker.comments.CommentsServiceGrpc;
import com.shreeharibi.notemaker.notes.NoteCreationRequest;
import com.shreeharibi.notemaker.notes.NoteCreationResponse;
import com.shreeharibi.notemaker.notes.NoteServiceGrpc;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class AggregatorService {

    @GrpcClient("notes-service")
    private NoteServiceGrpc.NoteServiceBlockingStub noteStub;

    @GrpcClient("comments-service")
    private CommentsServiceGrpc.CommentsServiceBlockingStub commentStub;

    public Boolean createNote(Note note) {
        try {
            NoteCreationRequest noteCreationRequest = NoteCreationRequest.newBuilder()
                    .setTitle(note.getTitle())
                    .setNote(note.getNote())
                    .build();
            log.info("Invoking notes-microservice...");
            NoteCreationResponse response = noteStub.createNote(noteCreationRequest);
            return response.getResponse();
        } catch (Exception exception) {
            log.error("Failed to create note");
        }
        return false;
    }

    public Boolean addNewCommentToNote(Comment comment) {
        try {
            log.info("Invoking comments-microservice...");

            // Refer proto file for structure
            CommentDto commentDto = CommentDto.newBuilder()
                    .setComment(comment.getComment())
                    .setNoteId(comment.getNoteId())
                    .build();
            AddNewCommentRequest commentRequest = AddNewCommentRequest.newBuilder()
                    .setComment(commentDto)
                    .build();
            AddNewCommentResponse addNewCommentResponse = commentStub.addComment(commentRequest);
            if (addNewCommentResponse != null) {
                return addNewCommentResponse.getStatus();
            } else {
                return false;
            }
        } catch (Exception e) {
            log.error("Falied to add comment...");
        }
        return false;
    }
}
