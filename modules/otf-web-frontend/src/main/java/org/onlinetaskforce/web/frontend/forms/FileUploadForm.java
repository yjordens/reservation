package org.onlinetaskforce.web.frontend.forms;

import org.apache.wicket.markup.html.form.upload.FileUpload;
import org.apache.wicket.markup.html.form.upload.FileUploadField;
import org.apache.wicket.util.file.File;
import org.apache.wicket.util.file.Files;
import org.apache.wicket.util.file.Folder;
import org.apache.wicket.util.lang.Bytes;
import org.onlinetaskforce.common.log.Log;

import java.util.List;

/**
 * @author jordens
 * @since 17/03/13
 */
public class FileUploadForm extends BasicForm<Void>{
        private FileUploadField fileUploadField;

        /**
         * Construct.
         *
         * @param name
         *            Component name
         */
        public FileUploadForm(String name) {
            super(name);

            // set this form to multipart mode (allways needed for uploads!)
            setMultiPart(true);

            // Add one file input field
            add(fileUploadField = new FileUploadField("fileInput"));

            // Set maximum size to 100K for demo purposes
            setMaxSize(Bytes.kilobytes(100));
        }

        /**
         * @see org.apache.wicket.markup.html.form.Form#onSubmit()
         */
        @Override
        protected void onSubmit() {
            final List<FileUpload> uploads = fileUploadField.getFileUploads();
            if (uploads != null)
            {
                for (FileUpload upload : uploads)
                {
                    // Create a new file
                    File newFile = new File(getUploadFolder(), upload.getClientFileName());

                    // Check new file, delete if it already existed
                    checkFileExists(newFile);
                    try
                    {
                        // Save to new file
                        newFile.createNewFile();
                        upload.writeTo(newFile);

                        Log.info("saved file: " + upload.getClientFileName());
                    }
                    catch (Exception e)
                    {
                        throw new IllegalStateException("Unable to write file", e);
                    }
                }
            }
        }

    /**
     * Check whether the file allready exists, and if so, try to delete it.
     *
     * @param newFile
     *            the file to check
     */
    private void checkFileExists(File newFile)
    {
        if (newFile.exists())
        {
            // Try to delete the file
            if (!Files.remove(newFile))
            {
                throw new IllegalStateException("Unable to overwrite " + newFile.getAbsolutePath());
            }
        }
    }

    private Folder getUploadFolder() {
        Folder folder = new Folder(System.getProperty("java.io.tmpdir"), "wicket-uploads");
        // Ensure folder exists
        folder.mkdirs();
        return folder;
    }

    public FileUploadField getFileUploadField() {
        return fileUploadField;
    }

    public void setFileUploadField(FileUploadField fileUploadField) {
        this.fileUploadField = fileUploadField;
    }
}
