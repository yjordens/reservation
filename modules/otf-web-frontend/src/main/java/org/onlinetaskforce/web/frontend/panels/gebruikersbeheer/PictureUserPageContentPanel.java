package org.onlinetaskforce.web.frontend.panels.gebruikersbeheer;

import org.apache.wicket.markup.html.form.Form;
import org.onlinetaskforce.web.frontend.fileupload.FileDownloadTemplate;
import org.onlinetaskforce.web.frontend.fileupload.FileUploadBar;
import org.onlinetaskforce.web.frontend.fileupload.FileUploadGallery;
import org.onlinetaskforce.web.frontend.fileupload.FileUploadTemplate;
import org.onlinetaskforce.web.frontend.forms.FileUploadForm;
import org.onlinetaskforce.web.frontend.panels.BasicPanel;

/**
 * Reusable basic UserPanel
 * @author jordens
 * @since 8/03/13
 */
public class PictureUserPageContentPanel extends BasicPanel {
    private FileUploadForm form;

    /**
     * Initializes the view
     *
     * @param id The id
     */
    public PictureUserPageContentPanel(String id) {
        super(id);
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();

        //form = new FileUploadForm("form");

        //form.add(new UploadProgressBar("progress", form, form.getFileUploadField()));
        //add(form);

        FileUploadBar fileUpload = new FileUploadBar("fileUpload");
        add(fileUpload);

        // The gallery that can be used to view the uploaded files
        // Optional
        FileUploadGallery preview = new FileUploadGallery("preview");
        add(preview);

        // The template used by jquery.fileupload-ui.js to show the files
        // scheduled for upload (i.e. the added files).
        // Optional
        FileUploadTemplate uploadTemplate = new FileUploadTemplate("uploadTemplate");
        add(uploadTemplate);

        // The template used by jquery.fileupload-ui.js to show the uploaded files
        // Optional
        FileDownloadTemplate downloadTemplate = new FileDownloadTemplate("downloadTemplate");
        add(downloadTemplate);
    }

    public Form getForm() {
        return form;
    }

    public void setForm(FileUploadForm form) {
        this.form = form;
    }
}

