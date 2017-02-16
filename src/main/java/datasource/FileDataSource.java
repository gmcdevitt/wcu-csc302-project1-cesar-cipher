/*
 *     codes.gerard.cryptography, a simple, expandable app dealing with cryptography
 *     Copyright (C) 2017 Gerard McDevitt
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

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
