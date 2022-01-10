package com.shreeharibi.aggregator.Service;

import com.shreeharibi.aggregator.Model.Note;
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

    public Boolean createNote(Note note) {
        NoteCreationRequest noteCreationRequest = NoteCreationRequest.newBuilder()
                                                .setTitle(note.getTitle())
                                                .setNote(note.getNote())
                                                .build();
        try {
            log.info("Invoking notes-microservice...");
            NoteCreationResponse response = noteStub.createNote(noteCreationRequest);
            return response.getResponse();
        } catch (Exception exception) {
            log.error("Failed to create note");
        }
        return false;
    }
}
