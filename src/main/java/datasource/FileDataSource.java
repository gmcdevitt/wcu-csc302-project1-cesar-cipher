package datasource;

import org.apache.commons.io.IOUtils;

import java.io.*;

public class FileDataSource implements DataSource {
    private InputStream source;

    public FileDataSource(String source) {
        try {
            this.source = new FileInputStream(new File(source));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(String.format("could not open stream at source '%s'", source), e);
        }
    }

    public char[] getContents() {
        try {
            return IOUtils.toCharArray(source);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
