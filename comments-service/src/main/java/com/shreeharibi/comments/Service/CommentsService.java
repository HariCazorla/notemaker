package com.shreeharibi.comments.Service;

import com.shreeharibi.comments.Entity.Comment;
import com.shreeharibi.comments.Repository.CommentsRepository;
import com.shreeharibi.notemaker.comments.*;
import io.grpc.stub.StreamObserver;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.server.service.GrpcService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Class containing implementations of Comments service
 * Refer proto file for more information
 */
@GrpcService
@Slf4j
public class CommentsService extends CommentsServiceGrpc.CommentsServiceImplBase {

    @Autowired
    private CommentsRepository repository;

    /**
     * Method to return all comments associated with a note
     * @param request -> contains NoteId, refer proto file
     * @param responseObserver
     */
    @Override
    public void getComments(CommentSearchRequest request, StreamObserver<CommentSearchResponse> responseObserver) {
        log.info("Fetching all comments for note id " + request.getNoteId() + "...");
        CommentSearchResponse.Builder builder = CommentSearchResponse.newBuilder();
        List<CommentDto> commentDtoList =
                repository.getCommentsByNoteId(request.getNoteId())
                .stream()
                .map(comment -> CommentDto.newBuilder()
                        .setComment(comment.getComment())
                        .setNoteId(comment.getNoteid())
                        .build())
                .collect(Collectors.toList());
        responseObserver.onNext(builder.addAllComment(commentDtoList).build());
        responseObserver.onCompleted();
    }

    /**
     * Method to add a new comment to existing note
     * @param request -> CommentDto object, refer proto file
     * @param responseObserver
     */
    @Override
    public void addComment(AddNewCommentRequest request, StreamObserver<AddNewCommentResponse> responseObserver) {
        log.info("Processing add new comment request for note id " + request.getComment().getNoteId());
        AddNewCommentResponse.Builder builder = AddNewCommentResponse.newBuilder();
        Comment comment = new Comment();
        comment.setComment(request.getComment().getComment());
        comment.setNoteid(request.getComment().getNoteId());
        repository.save(comment);
        responseObserver.onNext(builder.setStatus(true).build());
        responseObserver.onCompleted();
        log.info("Request completed successfully, "+ comment.getId() +"...");
    }
}
