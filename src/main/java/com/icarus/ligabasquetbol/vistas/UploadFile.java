package com.icarus.ligabasquetbol.vistas;

import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class UploadFile extends Upload {
    public UploadFile(String caption, Receiver uploadReceiver) {
        super(caption, uploadReceiver);
        setImmediateMode(true);
        UploadInfoWindow uploadInfoWindow = new UploadInfoWindow(this,
                (FileReceiver) uploadReceiver);
        addStartedListener(event -> {
            if (uploadInfoWindow.getParent() == null) {
                UI.getCurrent().addWindow(uploadInfoWindow);
            }
            uploadInfoWindow.setClosable(false);
        });
    }

    public UploadFile() {
        this(null, new FileReceiver());
    }

    public UploadFile(String caption) {
        this(caption, new FileReceiver());
    }

    public File getFile() {
        return ((FileReceiver) getReceiver()).getFile();
    }

    private static class FileReceiver implements Receiver {
        private File file;

        @Override
        public OutputStream receiveUpload(final String filename, final String MIMEType) {
            FileOutputStream fos = null;
            try {
                file = new File(filename);
                if (!file.exists()) {
                    file.createNewFile();
                }
                fos = new FileOutputStream(file);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return fos;
        }

        public File getFile() {
            return file;
        }
    }

    private static class UploadInfoWindow extends Window implements
            StartedListener, ProgressListener,
            FailedListener, SucceededListener {
        private final Label state = new Label();
        private final Label fileName = new Label();
        private final ProgressBar progressBar = new ProgressBar();
        private final Button cancelButton;
        FileReceiver receiver;

        private UploadInfoWindow(final Upload upload, FileReceiver receiver) {
            this.receiver = receiver;
            setResizable(false);
            setDraggable(false);
            final FormLayout uploadInfoLayout = new FormLayout();
            setContent(uploadInfoLayout);
            uploadInfoLayout.setMargin(true);
            final HorizontalLayout stateLayout = new HorizontalLayout();
            stateLayout.setSpacing(true);
            stateLayout.addComponent(state);
            cancelButton = new Button("Cancelar");
            cancelButton.addClickListener(event -> upload.interruptUpload());
            cancelButton.setVisible(false);
            cancelButton.addStyleName(ValoTheme.BUTTON_DANGER);
            stateLayout.addComponent(cancelButton);
            stateLayout.setCaption("Estado actual");
            state.setValue("No se está haciendo nada");
            uploadInfoLayout.addComponent(stateLayout);
            fileName.setCaption("Nombre del archivo");
            uploadInfoLayout.addComponent(fileName);
            progressBar.setCaption("Progreso");
            progressBar.setVisible(false);
            uploadInfoLayout.addComponent(progressBar);
            upload.addStartedListener(this);
            upload.addProgressListener(this);
            upload.addFailedListener(this);
            upload.addSucceededListener(this);
        }

        // this method gets called immediately after upload is started
        @Override
        public void uploadStarted(final StartedEvent event) {
            progressBar.setValue(0f);
            progressBar.setVisible(true);
            UI.getCurrent().setPollInterval(500);
            state.setValue("Subiendo fotografía");
            fileName.setValue(event.getFilename());
            cancelButton.setVisible(true);
        }

        // this method gets called several times during the update
        @Override
        public void updateProgress(final long readBytes, final long contentLength) {
            progressBar.setValue(readBytes / (float) contentLength);
        }

        @Override
        public void uploadSucceeded(final SucceededEvent event) {
            state.setValue("Finalizado");
            progressBar.setVisible(false);
            cancelButton.setVisible(false);
            setClosable(true);
            fileName.setValue(receiver.getFile().getAbsolutePath());
        }

        @Override
        public void uploadFailed(final FailedEvent event) {
            setClosable(true);
        }
    }
}
