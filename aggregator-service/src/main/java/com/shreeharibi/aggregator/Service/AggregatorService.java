package com.shreeharibi.aggregator.Service;

import com.google.protobuf.Empty;
import com.shreeharibi.aggregator.Model.Comment;
import com.shreeharibi.aggregator.Model.Note;
import com.shreeharibi.notemaker.comments.*;
import com.shreeharibi.notemaker.notes.*;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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

    public List<String> fetchAllNotes() {
        List<String> notes = new ArrayList<>();
        try {
            log.info("Invoking notes microservice...");
            NotesResponse allNotes = noteStub.getAllNotes(Empty.newBuilder().build());
            log.info("found notes: " + allNotes.getNoteList().size());
            allNotes.getNoteList().stream()
                    .map(noteDto -> notes.add(noteDto.getId()))
                    .collect(Collectors.toList());
        } catch (Exception e) {
            log.error("Failed to fetch all notes...");
        }
        return notes;
    }

    public Boolean deleteCommentById(String commentId) {
        Boolean status = false;

        try {
            log.info("Invoking cooments microservice...");
            DeleteCommentRequest request = DeleteCommentRequest.newBuilder()
                    .setCommentId(commentId)
                    .build();
            DeleteCommentResponse deleteCommentResponse = commentStub.deleteComment(request);
            status = deleteCommentResponse.getStatus();
        } catch (Exception e) {
            log.error("Failed to delete comment...");
        }
        return status;
    }

    public Boolean editCommentById(String commentId, String comment) {
        Boolean status = false;

        try {
            log.info("Invoking cooments microservice...");
            EditCommentRequest request = EditCommentRequest.newBuilder()
                    .setCommentId(commentId)
                    .setComment(comment)
                    .build();
            EditCommentResponse editCommentResponse = commentStub.editComment(request);
            status = editCommentResponse.getStatus();
        } catch (Exception e) {
            log.error("Failed to edit comment...");
        }
        return status;
    }
}
