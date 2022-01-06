package com.shreeharibi.notes.Service;

import com.google.protobuf.Empty;
import com.shreeharibi.notemaker.notes.*;
import com.shreeharibi.notes.Entity.Note;
import com.shreeharibi.notes.Repository.NoteRepository;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Class containing implementations of Notes service
 * Refer proto file for more information
 */

@GrpcService
public class NotesService extends NoteServiceGrpc.NoteServiceImplBase {

    @Autowired
    private NoteRepository repository;

    /**
     * Method to add a new note
     * @param request
     * @param responseObserver
     */
    @Override
    public void createNote(NoteCreationRequest request, StreamObserver<NoteCreationResponse> responseObserver) {
        NoteCreationResponse.Builder builder = NoteCreationResponse.newBuilder();
        Note note = new Note();
        note.setNote(request.getNote());
        repository.save(note);
        responseObserver.onNext(builder.setResponse(true).build());
        responseObserver.onCompleted();
    }

    /**
     * Method to fetch all the Notes
     * @param request
     * @param responseObserver
     */
    @Override
    public void getAllNotes(Empty request, StreamObserver<NotesResponse> responseObserver) {
        NotesResponse.Builder builder = NotesResponse.newBuilder();
        List<NoteDto> NotesDtoList = repository.findAll()
                .stream()
                .map(note -> NoteDto.newBuilder()
                .setNote(note.getNote())
                .setId(note.getId())
                .build())
                .collect(Collectors.toList());
        responseObserver.onNext(builder.addAllNote(NotesDtoList).build());
        responseObserver.onCompleted();
    }
}
