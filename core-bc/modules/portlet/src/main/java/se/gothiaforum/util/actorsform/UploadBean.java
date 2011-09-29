package se.gothiaforum.util.actorsform;

import java.io.Serializable;

/**
 * @author simgo3
 * 
 */

public class UploadBean implements Serializable {

    private static final long serialVersionUID = 1L;

    private byte[] file;

    public UploadBean() {
        super();
    }

    public UploadBean(byte[] file) {
        super();
        setFile(file);
    }

    public void setFile(byte[] file) {
        this.file = file;
    }

    public byte[] getFile() {
        return file;
    }

}